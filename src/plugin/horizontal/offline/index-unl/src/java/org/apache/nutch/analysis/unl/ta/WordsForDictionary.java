package org.apache.nutch.analysis.unl.ta;

import java.io.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

//import org.apache.nutch.analysis.unl.ta.*;
public class WordsForDictionary {

    ArrayList al = new ArrayList();
    String result;
    String word_tag;
    String sent_tag;

    public void init() {
        result = "";
        word_tag = "";
        sent_tag = "";
    }

    public String Get_AnalyseWords(String sentence) {
        try {
            init();
            StringTokenizer split_sent = new StringTokenizer(sentence, " ,;");
            int count = split_sent.countTokens();
            int i = 0;
            String word = "";
            String word1 = "";
            String prev = "";
            String next = "";
            while (i < count) {
                word = split_sent.nextToken().trim();
                String analysed = Analyser.analyseF(word, true);
                //		System.out.println(analysed);
                StringTokenizer get_rootword = new StringTokenizer(analysed, ":\n<>,=•°*;:?-'\"&", false);
                int firstindex = analysed.indexOf('<', analysed.indexOf('>'));
                String analmod = analysed.substring(firstindex);
                prev = get_rootword.nextToken().trim();
                word1 = prev;
                next = get_rootword.nextToken().trim();
                //	System.out.println("Prev-Next-Word1:"+prev +":"+word1+":"+next);
                if ((!next.contains("count")) && (!next.contains("Error")) && (!analysed.contains("Numbers")) && (!analysed.contains("count=3")) && (!word1.equals("0")) && (word1.length() > 1) && (!word1.contains(".")) && (!word1.contains(" "))) {
                    if (!next.isEmpty()) {
                        word_tag = "#" + " " + next;
                        sent_tag = word_tag + "==>>:" + " " + sentence;
                    }
                    //	result = sent_tag;
                    //	word_tag = "";
                    //	sent_tag = "";
                    System.out.println(sent_tag);
                } else if ((next.contains("count")) || (analysed.contains("Unknown"))) {
                    if ((word1.contains("Error")) || (word1.contains("count"))) {
                    } else {
                        word_tag = "#" + " " + word1;
                        sent_tag = word_tag + "==>>:" + " " + sentence;
                        System.out.println(sent_tag);
                    }
                } else if (next.contains("Error")) {
                    word_tag = "";
                    sent_tag = "";
                }
                /**
                 * else{ word_tag = "#"+ " "+next; sent_tag = word_tag
                 * +"==>>:"+" "+sentence;
        				}
                 */
                i++;
            }
        } catch (Exception e) {
        }
        return sent_tag;
    }

    public void readFiles() {
        try {
            File fn = new File(org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome()+"resource/unl/ta/SentenceExtraction/Input/content/");
            File[] list = fn.listFiles();
            for (int i = 0; i < list.length; i++) {
                String filename = list[i].getName();
                al.add(filename);
            }
            //    System.out.println("Files:"+al+al.size());
        } catch (Exception e) {
        }
    }

    public void writeintoFile(String filename, String tag_out) {
        try {
            Writer output = null;
            File fn1 = new File(org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome()+"resource/unl/ta/Dictionary/Output/");
            File dir = new File(org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome()+"resource/unl/ta/Dictionary/Output/" + filename);
            dir.createNewFile();
            output = new BufferedWriter(new FileWriter(dir, false));
            output.write(tag_out);
            output.close();
        } catch (Exception e) {
        }
    }

    public void readDocs() {

        // SemiRules r = new SemiRules();
        String s = null;
        FileReader fr = null;
        String temp = null;
        String taggedOutput = "";
        String str = "";
        String sent = "", recv = "", token = "", fn = "";
        int temp1 = 0, j = 0;
        String tag_file = "";
        readFiles();
        try {
            for (int cnt = 0; cnt < al.size(); cnt++) {
                //  while ((s = br.readLine()) != null) {
                s = al.get(cnt).toString();
                fn = org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome()+"resource/unl/ta/SentenceExtraction/Input/content/" + s;
                tag_file = s;
                //  	System.out.println("fn:" + fn);
                StringBuffer docbuff = new StringBuffer();
                fr = new FileReader(fn);
                // System.out.println("fr:"+fr);
                StreamTokenizer st = new StreamTokenizer(fr);

                while (st.nextToken() != st.TT_EOF) {
                    temp = "";
                    temp1 = 0;
                    if (st.ttype == st.TT_WORD) {
                        temp = st.sval;
                    } else if (st.ttype == st.TT_NUMBER) {
                        temp1 = (int) st.nval;
                        temp = Integer.toString(temp1);
                    }
                    if (temp != null) {
                        docbuff.append(temp + " ");
                    }
                }
                //	System.out.println("docbuff:"+docbuff);
                StringTokenizer sentToken1 = new StringTokenizer(docbuff.toString().trim(), ".", false);
                j = 0;
                while (sentToken1.hasMoreTokens()) {
                    sent = sentToken1.nextToken();
                    recv = Get_AnalyseWords(sent);
                    taggedOutput += recv + "\n";
                }
                //   taggedOutput = recv;
                //   System.out.println("Tagged output:"+taggedOutput);
                // writeintoFile(tag_file, taggedOutput);
                taggedOutput = "";
            }
            //	}
        } catch (Exception e) {
        }
    }

    public static void main(String args[]) {
        WordsForDictionary analyse_words = new WordsForDictionary();
        analyse_words.readDocs();
    }
}
