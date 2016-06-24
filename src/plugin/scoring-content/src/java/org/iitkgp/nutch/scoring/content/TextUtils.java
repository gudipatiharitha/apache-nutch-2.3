/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iitkgp.nutch.scoring.content;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringEscapeUtils;

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
                            "\"","\\/","\\#","\\^","\\?","\\{","\\}","\\[","\\]", "\\(","\\)",
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
        gText = gText.replaceAll("&[aA][mM][pP];", "&").replaceAll("[\\s]+", " ");
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

        if (hexCode.length() < 4) {
            return hexCode;
        } else {
            char[] ch = hexCode.toCharArray();
            for (int i = 0; i < ch.length; i++) {
                if (ch[i] == '&') {
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
        inText = inText.replaceAll("([0-9]+)\\s+([0-9]+)", " ");
        return RemoveDotsComma(inText);
    }
    
    /** Removes the Sequence of numbers with spaces */
    public static String RemoveDotsComma(String inText) {
        inText = inText.replaceAll("([,])", ", ").replaceAll("([.])", ". ");
        inText = inText.replaceAll("([.,»�◗·]+)\\s+([.,»�◗·]+)", " ");
//        inText = inText.replaceAll("([»]+)\\s+([»]+)", " ");
        inText = inText.replaceAll("([-]+)\\s+([-]+)", " ");
        inText = inText.replaceAll("(\\s+[a-z]{1})\\s+([a-z]{1}\\s+)", " ");
        inText = inText.replaceAll("(..[.,]+)([.,]+)", "").replaceAll("[\\s]+([,])", ", ").replaceAll("[\\s]+([.])", ". ");
        return inText.replaceAll("[\\s]+", " ");
    }
    
    public static void WriteToFile(File file, String content) {
        WriteToFile(file.getAbsolutePath(), content);
    }

    public static void WriteToFile(String file, String content) {
        try {
            File cfile = new File(file);
            BufferedWriter out = new BufferedWriter(new FileWriter(cfile, true));
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
    
    public static String WipeSpecialChars(String inText) {
        return inText.replaceAll("[?{}\\[\\]()!<>+*•◗·@|‘’:&\\;/\\^\\%\\$\\#\\']", " ").replaceAll("\\+", " ");
    }
    
    /** Prints the TIME difference */
    public static void printTime(long starttime, long endtime) {
        long totaltime = (endtime - starttime)/1000;
        System.out.print("\nTime Taken: [Hours: " +totaltime/60/60+ ", Minutes: " +
                totaltime/60%60+ ", Seconds: " +totaltime%60%60+ "]");
    }
    
    public static String FilterMultipleOccurrences(String inText) {
        System.out.println(inText);
        String toRet = "", prev = ""; boolean flag = false;
        StringBuilder token = new StringBuilder();

        String[] toks = inText.split("[\\s]+");
        int len = toks.length;
        
        prev = toks[0]; toRet += prev;
        System.out.println(toks[0]);
        for (int i = 1; i < len; i++) {
            System.out.println(toks[i]);
            if ( toks[i].equalsIgnoreCase(prev) ) {
                toRet += " " +toks[i];
                prev = toks[i];
            }
            System.out.println(toRet);
        }
        System.out.println(toRet);
        return toRet.trim();        
    }
    
    public static String getUTF8(String inText) {
        String txt = null;
        inText = StringEscapeUtils.escapeHtml(inText);
        try {
            txt = new String(inText.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TextUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return txt;
    }

    /** Get UTF - 8 String from the Given Text */
    public static String getUTF8String(String myText) {
        byte[] byteArray = null;
        try {
            byteArray = myText.getBytes("UTF-8");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return byteArray.toString();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        System.out.println(TextUtils.FindLargerCommonSequence("", "Welcome to India"));
        

////        System.out.println("UniCode Text: "+TextUtils.ConvertHexToUnicode(text));
////        System.out.println("Time Taken: "+(System.currentTimeMillis()-stTime));
////        
        String text = "Damodar River - Informative researched article on Damodar River in l l l l l l l l l l l l l l l l l l l l l Damodar ";
        System.out.println("Input: " + text);
        text = TextUtils.RemoveNumsSpaces(text);
        System.out.println("Check: " + text);
//        
//        System.out.println("Dupli: " + TextUtils.FilterMultipleOccurrences(text));

//        text = "welcome to India and see you அன்புள்ள நண்பருக்கு 54 1 2 8 4 \\ . ; 0 } [ ]  as ; ( 8 & ^ ^ % $ # @ ! _ +_ - 5 6 3 9 6 10 57 6 9 5 4 5 8 7 3 4 4 2 33 5 2 6 7 5 3 3 2 8 4 !! . . . . . . . . , , , , , , , , ,  ........ said....";
//        stTime = System.currentTimeMillis();        
//        System.out.println("Check: " + TextUtils.WipeSpecialChars(text));
//        System.out.println("Time Taken: "+(System.currentTimeMillis()-stTime));
//        
//        stTime = System.currentTimeMillis();        
//        System.out.println("Check: " + TextUtils.WipeSpecialChars(text));
//        System.out.println("Time Taken: "+(System.currentTimeMillis()-stTime));

        String textFrag = "welcome to India &copy;";
        System.out.println("Text(UTF-8): " + TextUtils.getUTF8(textFrag));
        System.out.println("Text(UTF-8): " + (char) 169);
        System.out.println("Text(UTF-8): " + TextUtils.getUTF8String(textFrag));

        textFrag = ConvertHexToUnicode(textFrag);
        System.out.println("HEX  : " + textFrag);
        
        
        text = "http://www.google.co.in/search?q=eiffel+tower&#38;hl=en&#38;gbv=2&#38;biw=1286&#38;bih=668&#38;prmd=imvns&#38;ei=7zHLTqOCNY6rrAfTssniDA&#38;start=10&#38;sa=N";
        text = "http://www.google.com/search?q=eiffel+tower&#38;hl=en&#38;gbv=2&#38;biw=1286&#38;bih=668&#38;prmd=imvns&#38;ei=7zHLTqOCNY6rrAfTssniDA&#38;start=40&#38;sa=N";
        System.out.println("HEX: " + ConvertHexToUnicode(text));
      
    }
}
