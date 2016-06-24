package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.enconversion.unl.ta;

//import org.apache.nutch.analysis.unl.ta.Analyser;
import java.lang.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
//import clia.unl.unicode.enconverter.Rules;
//import org.apache.nutch.util.NutchConfiguration;
//import org.apache.hadoop.conf.Configuration;

public class SentExtr {

    public PrintStream ps2;
    public ArrayList<String> sent = new ArrayList<String>(); // to store the
    // sentences
    public ArrayList<String> root = new ArrayList<String>(); // to store the
    // root word
    public ArrayList<String> po_st = new ArrayList<String>(); // to store the
    // part of
    // sentences
    public static Hashtable countCheck = new Hashtable();
    public Hashtable getcountCheck = new Hashtable();
    public ArrayList putdocidcount = new ArrayList();
    public ArrayList wordcount = new ArrayList(); // to store the frequency
    // count
    public ArrayList termfreqct = new ArrayList(); // to store the term freq
    public ArrayList<String> stop = new ArrayList<String>(); // to store the
    // stop words
    public ArrayList<String> impwords = new ArrayList<String>(); // to store
    // the imp
    // words
    public int totalwords; // to store the size of root array list
    public int total_imp_pos; // number of important part of sentences for a
    // document
    public String fcount = "";
    public String s = "";
    public String getCount = "";
    public String getfName = "";

//	Configuration conf = NutchConfiguration.create();
//	String path = conf.get("UNL resource");
    public SentExtr() // empty constructor
    {
    }

    // function to find the important part of sentences
    public Hashtable impSentExtr(String filename, String did) {
        try {
            String fn = org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome()+"/resource/unl/ta/SentenceExtraction/Input/" + filename;
            getfName = filename;
            //	System.out.println("Sentence Extraction:"+fn);
            File empFile = new File(fn);

            String list[] = {" கி", "பி", "மீ", "ரூ", " பி", ".கா", "ஏ",
                "ஆர்", " த", " வ", "ச", "ஜி", "ஆ", " டி", "எ ", ".சா",
                " சி", " ஜஸ்டிஸ்", ".கி", " மு", "7467", "76", "உ", "கீ", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "ஐ", ".சி ", "க", ".உ", ".ஐ", "மி", "கெ", "ஊ", ".சி", " ஈ",
                ".வே", ".ரா", " என்", " (கி", " கிலோ", " வ", "ஏப்", "எஃப்",
                ".என்", " பு", ".வி", ".டி", " தி", ". சா", " எஸ்", ".இ", ".அ", " வை",
                " மா", " அரு", ".எச்", ".எல்", ".அரு", " வி", "கோ", "எம்", " திருமதி",
                " திரு", ".எஸ்", " இ", ".எம்", ".ஆர்", " ப", ".நோ", " தா", ".கூ",
                ".மு", " எம்"};

            int k = 0;
            int y = 0;
            if (empFile.length() > 25) {
                FileReader fr = new FileReader(fn);
                BufferedReader br = new BufferedReader(fr);
                //		System.out.println(filename);
                while ((s = br.readLine()) != null) {
                    PrintStream allout;
                    Pattern pat = Pattern.compile("[./]");
                    String strs[] = pat.split(s);

                    int j = strs.length;
                    for (int i = 0; i < j; i++) {
                        String element = strs[i];
                        if (element == null || element.length() == 0) {
                            continue;
                        } else {
                            sent.add(k, strs[i]);
                            k++;
                        }
                    }
                }
            } else {
                //	System.out.println("EmptyFiles:"+filename);
            }

            //	System.out.println("SENT:"+sent);
            // allout.println("Sentences in sent array List:\n"+sent);
            while (y < sent.size() - 1) {
                String str = sent.get(y) + " ";
                str = str.trim();
                int x = 0;
                int flag = 0;
                while ((x < list.length)) {
                    if (str.endsWith(list[x])
                            || ((str.length() < 3) && (str.length() != 0))) {
                        String str1 = sent.get(y + 1) + " ";
                        str = str + " " + str1;
                        sent.set(y, str);
                        sent.remove(y + 1);
                        flag = 1;
                    }
                    x++;
                }
                if (flag == 0) {
                    y++;
                }
            }
        } catch (IOException e) {
        }
        getcountCheck = wordfreqcount(did); // to find the freqency of all root
        // words in a doc
        //	findtermfreq();// to find term frequency of all root words in a doc

        //	float a = findmax(); // to find the first maximum term freq
        //	getfow(a);// to find the words with first max term freq

        // to find the freq occuring words for seocnd max freq

        //	float b = findmax(); // to find the second maximum term freq
        //	getfow(b); // to find the words with second max term freq

        // to find the sentences having freq occuring words

        getfowsent(filename);
        // ////System.out.println("The get count check inside the sent
        // Extract"+getcountCheck);
        return getcountCheck;
    }

    public Hashtable wordfreqcount(String did) {


        //taLocale.getAvailableLocales();	

        int ct = 0;
        try {
            BufferedReader st = new BufferedReader(new FileReader(org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome()+"/resource/unl/tabstop.txt"));

            // load stop word list from file into array list
            // allout.println("Inside word frequency count");
            String k = "";
            StringBuffer stp = new StringBuffer();
            while ((k = st.readLine()) != null) {
                stp.append(k + "\n");
            }
            StringTokenizer tok = new StringTokenizer(stp.toString());
            // ////System.out.println(tok.countTokens());
            while (tok.hasMoreTokens()) {
                String sword = tok.nextToken();
                stop.add(sword);

            }
            // ps1 = new PrintStream(new FileOutputStream(new
            // File("/usr/output/analout1.txt"),true));
            // to split doc into words and analyze it

            for (int p = 0; p < sent.size(); p++) {
                String sentstr = sent.get(p).toString().trim();

                sentstr = sentstr.replaceAll("\\s+", " ");
                StringTokenizer strToken1 = new StringTokenizer(sentstr,
                        "  ..*?-██ —;+»°←→↓↑•-“',’_:\")(!|");
                StringTokenizer strToken2 = null;
                String res = " ";

                //	System.out.println("Sent"+sentstr);
                boolean isTamil = true;
                while (strToken1.hasMoreTokens()) {

                    String word = strToken1.nextToken();
                    //	System.out.println("word:"+word);
                    String word1 = "";
                    String word2 = "";
                    String prev, next;
                    //		if(!word.contains("\\s+")){	

                    String analysed = Analyser.analyseF(word, true);
                    strToken2 = new StringTokenizer(analysed,
                            ":\n<>=,+»°•←→.↓↑*;:?-“'\"&", false);
                    //	}else{

                    //	}
                    //	System.out.println("analysed:"+analysed);
                    if (analysed.indexOf("Error") != -1) {
                        word = "";
                    } else if (word.equals("தொகு")) {
                    }/*
                     * else if (analysed.indexOf("Adjectival Noun") != -1) {
                     * prev = strToken2.nextToken().trim(); word1 = prev; next =
                     * strToken2.nextToken().trim(); while
                     * (!(next.equals("Adjectival Noun"))) { prev = next; next =
                     * strToken2.nextToken().trim(); } word2 = prev; } else if
                     * (analysed.indexOf("Interrogative Noun") != -1) { prev =
                     * strToken2.nextToken().trim(); word1 = prev; next =
                     * strToken2.nextToken().trim(); while
                     * (!(next.equals("Interrogative Noun"))) { prev = next;
                     * next = strToken2.nextToken().trim(); } word2 = prev; }
                     * else if (analysed.indexOf("Non Tamil Noun") != -1) { prev
                     * = strToken2.nextToken().trim(); word1 = prev; next =
                     * strToken2.nextToken().trim(); while (!(next.equals("Non
                     * Tamil Noun"))) { prev = next; next =
                     * strToken2.nextToken().trim(); } word2 = prev; } else if
                     * (analysed.indexOf("Noun") != -1) { prev =
                     * strToken2.nextToken().trim(); word1 = prev; next =
                     * strToken2.nextToken().trim(); while
                     * (!(next.equals("Noun"))) { prev = next; next =
                     * strToken2.nextToken().trim(); } word2 = prev; } else if
                     * (analysed.indexOf("Entity") != -1) { prev =
                     * strToken2.nextToken().trim(); word1 = prev; next =
                     * strToken2.nextToken().trim(); while
                     * (!(next.equals("Entity"))) { prev = next; next =
                     * strToken2.nextToken().trim(); } word2 = prev; } else if
                     * (analysed.indexOf("unknown") != -1) { word1 =
                     * strToken2.nextToken().trim(); if (word1.length() >= 3){
                     * if( (word1.charAt(0)>=2946)&&(word1.charAt(0)<=3066) ){
                     * word2 = word1; }else{ //	System.out.println("WORD1 NOT
                     * VALID:"+word1); word1 = ""; } }else{ word1 = ""; }
                     *
                     * } else {
                     *
                     * word1 = strToken2.nextToken().trim(); word2 =
                     * strToken2.nextToken().trim(); }else{
                     *
                     * }
                     */
                    if (word != null) {
                        res += word + " ";
                    }
                    if ((analysed.indexOf("Verbal Participle Suffix") != -1)
                            && (analysed.indexOf("Instrumental Case") == -1)
                            && (analysed.indexOf("Noun") == -1)
                            && (analysed.indexOf("Relative Participle Suffix ") == -1)) {
                        po_st.add(res + " " + "#");
                        //	po_st.add(res);
                        res = " ";
                    } else if ((analysed.indexOf("Dative Case") != -1)
                            && (analysed.indexOf("உம்") != -1)) {
                        po_st.add(res + " " + "#");
                        //	po_st.add(res);
                        res = " ";
                    } else if ((analysed.indexOf("Conditional Suffix") != -1)
                            || (analysed.indexOf("Conjunction") != -1)) {
                        po_st.add(res + " " + "#");
                        //	po_st.add(res);
                        res = " ";
                    } else if (word.indexOf("போது") != -1) {
                        po_st.add(res + " " + "#");
                        //	po_st.add(res);
                        res = " ";
                    } else {
                        //res += word + " ";
                    }

                    int b = checkstop(word2); // to check for stop word
                    if (b == 0) {
                        int a = check(word2); // to check whether that root
                        // word is already existing
                        if (a == 0) {
                            root.add(word2);
                            wordcount.add(new Integer(1));
                        }
                    }
                    totalwords = root.size();
                    //	}
                } // while
                //	System.out.println("res:"+res);
                po_st.add(res);
            } // for		
            st.close();
        } // try
        catch (Exception e) {
            e.printStackTrace();
        }
        return countCheck;
    }

    public int check(String s) {
        int cnt = 0;

        while (cnt < root.size()) {
            String temp = root.get(cnt).toString();
            if (temp.equals(s) == true) {
                addcount(cnt);
                return 1;
            }
            cnt++;
        }
        return 0;
    }

    // to check whether the given word is in stopword list
    public int checkstop(String s) {
        int cnt = 0;

        while (cnt < stop.size()) {
            String temp = stop.get(cnt).toString();
            if (temp.equals(s) == true) {
                // addcount(cnt);
                return 1;
            }
            cnt++;
        }
        return 0;
    }

    // to increase the freq if the given word is already existing in root word
    // list
    public void addcount(int cnt) {
        Object temp = wordcount.get(cnt);
        int t = ((Integer) temp).intValue();
        t = t + 1;
        wordcount.set(cnt, new Integer(t));
        int t1 = ((Integer) (wordcount.get(cnt))).intValue();
    }

    // function to find the maximum freq
    public float fow() {
        int n = 0;
        float t1 = 0, freq = 0;
        float t2;
        int maxcnt = 1;

        while (n < termfreqct.size()) {
            t2 = ((Float) (termfreqct.get(n))).floatValue();
            if (t1 < t2) {
                maxcnt = n;
                freq = t2;
                t1 = t2;
            }
            n++;
        }
        return freq;
    }

    // to write the freq count in a file
    public void display() {

        int k = 0;
        StringBuffer str = new StringBuffer();
        StringBuffer str1 = new StringBuffer();
        while (k < totalwords) {
            str.append(wordcount.get(k).toString() + "\n");
            k++;
        }
        int j = 0;
        while (j < totalwords) {
            str1.append(root.get(j).toString() + "\n");
            j++;
        }
        StringTokenizer strToken1 = new StringTokenizer(str1.toString());
        StringTokenizer strToken2 = new StringTokenizer(str.toString());
        while (strToken1.hasMoreTokens() && strToken2.hasMoreTokens()) {
            String word = strToken1.nextToken() + "\t\t\t"
                    + strToken2.nextToken();
        }
    }

    public void findtermfreq() {

        int k = 0;
        float totalfreq = 0;
        while (k < totalwords) {
            float temp = Float.parseFloat(wordcount.get(k).toString().trim());
            totalfreq = totalfreq + temp;
            k++;
        }

        k = 0;
        while (k < totalwords) {
            float temp = Float.parseFloat(wordcount.get(k).toString().trim());
            float tf = temp / totalfreq;
            termfreqct.add(tf);
            k++;
        }

        if (root.indexOf("count") != -1) {
            int tempindex = root.indexOf("count");
            termfreqct.set(tempindex, new Float(0));
        }
    }

    // function to find the maximum freq
    public float findmax() {
        int n = 0;
        float t1 = 0, freq = 0;
        float t2;
        int maxcnt = 1;

        try {

            while (n < termfreqct.size()) {

                t2 = ((Float) (termfreqct.get(n))).floatValue();
                if (t1 < t2) {
                    maxcnt = n;
                    freq = t2;
                    t1 = t2;
                }
                n++;

            }
        } catch (Exception e) {
        }
        return freq;
    }

    // function to find the frequently occurring words in the doc
    public void getfow(float freq) {

        int n = 0, ind;

        try {

            while (n < termfreqct.size()) {
                float t2 = ((Float) (termfreqct.get(n))).floatValue();
                if (freq == t2) {
                    ind = n;
                    String temp = root.get(ind).toString();
                    termfreqct.set(ind, new Float(0));
                    if (temp.equals("count")) {
                    } else {
                        impwords.add(temp);
                    }

                }
                n++;

            }
        } catch (Exception e) {
        }
    }
    // function to get the sentence in which the FOW occurs

    public void getfowsent(String filename) {
        try {

            int i = 0;
            int total_pos;

            StringBuffer sbuf = new StringBuffer();

            // allout.println("Inside get FOW sentences");

            total_pos = po_st.size();

            while (i < total_pos) {
                String st = po_st.get(i).toString().trim();
                int cnt = 0, flag = 0;

                while (cnt < impwords.size()) {
                    String temp = impwords.get(cnt).toString().trim();

                    /**
                     * if(st.indexOf(temp)!=-1) { flag++; }
                     */
                    cnt++;
                }
                if (flag >= 0) {
                    sbuf.append(st + "/");
                }
                i++;
            }
            //	System.out.println("SBUFFER:"+sbuf);
            if (sbuf != null) {
                //String prefix = "enc";
                int suff = 0;
                int cnt1 = 0;
                String fn = org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome()+"/resource/unl/ta/SentenceExtraction/Output/" + filename;
                File f_w = new File(fn);
                String fn1 = org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome()+"/resource/unl/ta/SentenceExtraction/Output/"
                        + filename.substring(0, filename.lastIndexOf("/"));
                File f_f = new File(fn1);
                /**
                 * StringTokenizer strToken = new
                 * StringTokenizer(f_w.getName(),"."); String did =
                 * strToken.nextToken(); String other = strToken.nextToken();
                 */
                String fn2 = fn1 + "/" + f_w.getName();
                ps2 = new PrintStream(
                        new FileOutputStream(new File(fn2), false));
                StringTokenizer strToken1 = new StringTokenizer(
                        sbuf.toString(), "/", false);
                //		System.out.println("SIZE:"+total_pos);
                total_imp_pos = strToken1.countTokens();
                String concat = "";
                while (strToken1.hasMoreTokens()) {
                    //	cnt1++;
                    String word = strToken1.nextToken() + ".";
                    //String word = strToken1.nextToken() + "."+ "\n";
                    ps2.println(word);
                    /**
                     * concat += word; if(!(concat.endsWith("#.\n")) ){ if(cnt1
                     * >= 100){ String fn2 = fn1 + "/" + did+"-"+suff+".txt";
                     * ps2 = new PrintStream( new FileOutputStream(new
                     * File(fn2), false)); suff++; //	concat =
                     * concat.replace(".\n", "."); ps2.print(concat); cnt1 = 0;
                     * concat = ""; }
					}
                     */
                }
                /**
                 * if(cnt1 != total_pos){ String fn2 = fn1 + "/" +
                 * did+"-"+suff+".txt"; ps2 = new PrintStream( new
                 * FileOutputStream(new File(fn2), false)); //	suff++; //	concat
                 * = concat.replace(".\n", "."); ps2.print(concat); cnt1 = 0;
                 * suff = 0; concat = ""; }
                 */
                ps2.close();

            }

        } catch (Exception e) {
        }

    }
    /**
     * public void main(String args[]) { new SentExtr(); }
     */
}
