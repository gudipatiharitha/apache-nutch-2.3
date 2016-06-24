/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iitkgp.cel.parse.html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 *
 * @author Dr Rajendra
 */
public class TextUtils {

    public TextUtils() {
    }
    
    /**
     * This method eliminates duplicate terms in a given text fragment using hashing
     * @param myText The given text fragment
     * @return String The text with unique terms
     */
    public static String GetUniqueTerms(String myText) {
        StringBuilder toReturn = new StringBuilder(4096);
        Set se = new HashSet();
        StringTokenizer nn = new StringTokenizer(myText);
        while (nn.hasMoreElements()) {
            String term = nn.nextElement().toString().trim();
            if(se.add(term)) {    /** else obj is a Duplicate entry */
                toReturn.append(term).append(" ");
            }
        }
        return toReturn.toString().trim();
    }

    /** 
     * This method filters out special symbols 
     * @param Wiped String The given text
     * @return String Text with no special symbols
     */
    public static String WipeOutSpecials(String Wiped) {
        String cleanWiped = " ";
        cleanWiped = Wiped.replaceAll("&nbsp;", " ").replaceAll(",", " ").replaceAll("\\|", " ").replaceAll("&gt;", " ").replaceAll("&lt;", " ").replaceAll("&rsquo;", "'");
        Wiped = cleanWiped.replaceAll("&curren;", " ").replaceAll("&copy;", " ").replaceAll("&quot;", " ").replaceAll("&reg;", " ").replaceAll("&amp;", " ");

        String[] mySpecials = {"-","&raquo;","next","back","top","&nbsp",";","=","%","'",
                            "\"","\\/","\\#","\\#","\\^","\\?","\\{","\\}","\\[","\\]", "\\(","\\)",
                            "!","<",">","< ref >","\\+","\\*", "\\•","@", "\\|", "‘", "’",":","\\&", "�"};
        for(int i=0; i < mySpecials.length; i++){
            Wiped = Wiped.replaceAll(mySpecials[i]," ");
        }
        return Wiped;
//        return EmptySpaceRemover(Wiped).trim();
    }

    /**
     * This method simply replaces empty spaces with single space in the given <text>
     * @param text String with / without empty spaces
     * @return String with terms separated with a single space
     */
    public static String EmptySpaceRemover(String text) {
        return text.replaceAll("[\\s]+", " ");
    }
    
    /** 
     * This method converts '&amp;' to '&' symbol
     * @param gText The text with markups for special symbols like &, <, >, etc
     * @return The refined text
     */
    public static String ReplaceEscapeChars(String gText) {
        gText = gText.replaceAll("&[aA][mM][pP];", "&").replaceAll("([0-9]+)\\s+([0-9]+)", "").replaceAll("[\\s]+", " ");
        return gText;
    }
    
    /**
     * Method to convert Hex representation to Unicode using Linear Scan
     * @param hexCode String Hex Code representation
     * @return String Unicode text
     */
    public static String ConvertHexToUnicode(String hexCode) {
        String toRet = "", nums = ""; boolean flag = false;
        StringBuilder token = new StringBuilder();

        char[] ch = hexCode.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if ( ch[i] == '&' ) {
                flag = true;
            }
            
            /** Updating the Chain */
            if (flag) {
                token.append(ch[i]);
                if (ch[i] == ';') {
                    nums = token.toString().substring(2, token.toString().indexOf(";"));
                    try {
                        toRet += (char) Integer.parseInt(nums);
                    } catch (Exception e) {
                        toRet += token;
                    }
                    flag = false;
                    token.setLength(0);
                }
            } else {
                toRet += ch[i];
            }
        }
        
        return toRet.trim();        
    }

    /**
     * This method finds the larger string. If one string is contained in another string, then it returns the larger string
     * @param strOne 
     * @param strTwo 
     * @return String larger string which contains the other one as a substring
     */
    public static String FindLargerCommonSequence(String strOne, String strTwo) {
        String max = (strOne.length() > strTwo.length()) ? strOne : strTwo;
        String min = (strOne.length() > strTwo.length()) ? strTwo : strOne;
        int minLargerIndex = (min.length() > 0) ? min.length(): 0;
        return ((max.subSequence(0, minLargerIndex)).length() >= 0 ) ? max : min;
    }
    
    /** Removes the Sequence of numbers with spaces */
    public static String RemoveNumsSpaces(String inText) {
        inText = inText.replaceAll("([0-9]+)\\s+([0-9]+)", "");
        return RemoveDotsComma(inText);
    }

    /** Removes the Sequence of numbers with spaces */
    public static String RemoveDotsComma(String inText) {
        inText = inText.replaceAll("([,])", ", ").replaceAll("([.])", ". ");
        inText = inText.replaceAll("([.,]+)\\s+([.,]+)", " ");
        inText = inText.replaceAll("(..[.,]+)([.,]+)", "").replaceAll("[\\s]+", " ").replaceAll("[\\s]+([.])", ". ");
        return inText;
    }
    
    public static void WriteToFile(File file, String content) {
        WriteToFile(file.getAbsolutePath(), content);
    }

    public static void WriteToFile(String file, String content) {
        try {
            File cfile = new File(file);
            BufferedWriter out = new BufferedWriter(new FileWriter(cfile));
            out.write( content );
            out.close();
        } catch (Exception e) {
        }
    }

    /**
     * From the given text content, filter out top K terms
     * @param text String Given Text Content
     * @param topK String TopK terms (by their Term Frequency)
     * @return String having Top K terms
     */
    public static String getTopKterms(String text, int topK) {
        String toRet = "";
        if (text.trim().isEmpty()) {
            return toRet;
        } else {
            Map<String, Integer> strCount = new HashMap<String, Integer>();
            String[] toks = text.split("[\\s]+");
            if (toks.length > 0) {
                for ( int i = 0; i < toks.length; i++ ) {
                    String term = toks[i].trim();
                    int ctr = 1;
                    if (strCount.containsKey(term)) {
                        ctr = strCount.get(term) + 1;
                        strCount.put(term, ctr);
                    } else {
                        strCount.put(term, ctr);
                    }
                }

                strCount = sortMapStrIntByInt(strCount);
                int max = (strCount.keySet().size() < topK) ? strCount.keySet().size() : topK;
                Iterator<String> itr = strCount.keySet().iterator();
                int stop = 1;
                while (stop < max) {
                    String word = itr.next();
                    toRet += word + " ";
//                    System.out.println(word + " - " + strCount.get(word));
                    stop++;
                }
            }
        }
        return toRet.trim();
    }

    
    public static Map<String, Integer> sortMapStrIntByInt(Map<String, Integer> map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                    return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        Map result = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry)it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        System.out.println(TextUtils.FindLargerCommonSequence("", "Welcome to India"));
        
        String text = "hello ব&#2494;ড়&#2495;ত&#2503; ফ&#2507;ন ল&#2494;গ&#2494;ল&#2494;ম&#2404; &#244; আম&#2494;র গ&#2499;হকত&#2509;র&#2496; প&#2508;লম&#2496; যথ&#2503;ষ&#2509;টই অব&#2494;ক&#2404; জ&#2494;নত&#2503; চ&#2494;ইল আম&#2494;র পরবর&#2509;ত&#2496; পদক&#2509;ষ&#2503;প ক&#2495;? জ&#2494;ন&#2494;ল&#2494;ম দমদম এয়&#2494;রপ&#2507;র&#2509;ট&#2503; গ&#2495;য়&#2503; ব&#2495;স&#2509;ত&#2494;র&#2495;ত খবর ন&#2503;ব&#2404; ও পর&#2494;মর&#2509;শ দ&#2495;ল গ&#2507;য়&#2494;র হ&#2507;ট&#2503;ল&#2503; ফ&#2507;ন কর&#2503; জ&#2494;নত&#2503; ব&#2497;ক&#2495;&#2434; ঠ&#2495;কমত হয়&#2503;ছ&#2503; ক&#2495;ন&#2494;&#2404; গ&#2507;য়&#2494;র হ&#2507;ট&#2503;ল স&#2497;খস&#2494;গর&#2503; ফ&#2507;ন ল&#2494;গ&#2494;ল&#2494;ম&#2404; জ&#2494;নত&#2503; চ&#2494;ইল&#2494;ম ২৪ ত&#2494;র&#2495;খ থ&#2503;ক&#2503; ২৮ ত&#2494;র&#2495;খ অবধ&#2495; T.T.M.I এর ম&#2494;ধ&#2509;যম&#2503; য&#2503; র&#2497;ম ব&#2497;ক&#2495;&#2434; কর&#2494; আছ&#2503; ত&#2494; confirmed ক&#2495;ন&#2494;&#2404; খ&#2494;ন&#2495;ক ব&#2494;দ&#2503; ত&#2494;র&#2494; জ&#2494;ন&#2494;ল, &#2476;&#2494;&#2434;&#2482;&#2494;&#2480; welcome &#2476;&#2494;&#2524;&#2468;&#2495; how &#2495;&#2455; &#mp;" ;

        long stTime = System.currentTimeMillis();
        System.out.println("UniCode Text: "+TextUtils.ConvertHexToUnicode(text));
        System.out.println("Time Taken: "+(System.currentTimeMillis()-stTime));
        
        text = "welcome to India and see you.Anand visite myhouse,then 54 1 2 8 4 5 6 3 9 6 10 57 6 9 5 4 5 8 7 3 4 4 2 33 5 2 6 7 5 3 3 2 8 4 !! . . . . . . . . , , , , , , , , ,  ........ ";
        System.out.println("Check: " + TextUtils.RemoveNumsSpaces(text));
    }
}

