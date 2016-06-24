package org.apache.nutch.analysis.unl.ta.Integrated;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import org.apache.nutch.analysis.unl.ta.*;

public class IntegratedSentExtr extends SentExtr {

    public String CNT = "";

    public ArrayList<String> _impSentExtr(String fc, String did) {
        try {
            CNT = "";
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
            if (fc.length() >= 25) {
                StringReader fr = new StringReader(fc);
                BufferedReader br = new BufferedReader(fr);
                while ((s = br.readLine()) != null) {
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

            while (y < sent.size() - 1) {
                String str = sent.get(y) + " ";
                str = str.trim();
                int x = 0;
                int flag = 0;
                while ((x < list.length)) {
                    if (str.endsWith(list[x]) || ((str.length() < 3) && (str.length() != 0))) {
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
            getcountCheck = wordfreqcount(did); // to find the freqency of all root
            findtermfreq();// to find term frequency of all root words in a doc
            //getfowsent(filename);
            CNT = getfowsent();
        } catch (IOException e) {
        }
        return sent;
    }

    public String getfowsent() {
        String concat = "";
        try {
            int i = 0;
            int total_pos;
            StringBuffer sbuf = new StringBuffer();
            total_pos = po_st.size();
            while (i < total_pos) {
                String st = po_st.get(i).toString().trim();
                int cnt = 0, flag = 0;

                while (cnt < impwords.size()) {
                    String temp = impwords.get(cnt).toString().trim();
                    cnt++;
                }
                if (flag >= 0) {
                    sbuf.append(st + "/");
                }
                i++;
            }
            if (sbuf != null) {
                int suff = 0;
                int cnt1 = 0;
                StringTokenizer strToken1 = new StringTokenizer(sbuf.toString(), "/", false);
                total_imp_pos = strToken1.countTokens();
                while (strToken1.hasMoreTokens()) {
                    String word = strToken1.nextToken() + ".";
                    //ps2.println(word);
                    concat += (word + "\n");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return concat;

    }
}
