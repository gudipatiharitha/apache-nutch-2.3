package org.apache.nutch.analysis.unl.ta;

import java.io.*;
import java.util.*;
import java.lang.*;
import org.apache.commons.codec.binary.Base64;

public class MultiWordExtraction {

    MWDict mwdict = new MWDict();

//	String mwe = "";
//	int now = 0;
    public ArrayList processMW(ArrayList<String> anal, ArrayList<String> root, ArrayList<String> pos) {
        ArrayList final_list = new ArrayList();
        try {
            String mwe = "";
            ArrayList get_list = new ArrayList();
            //	System.out.println("MWE:"+"\n"+root+"\n"+pos);
            for (int i = 0; i < root.size(); i++) {
                String lc = pos.get(i).toString();
                String tw = root.get(i).toString();

                if ((lc.equals("Noun")) || (lc.equals("Entity"))) {
                    //int hc = tw.hashCode();
                    String hc = new String(Base64.encodeBase64(("" + tw).getBytes())); // Modified
                    int indx = root.indexOf(tw);
                    //	System.out.println("root Index:"+indx);
                    String anal_value = anal.get(indx).toString();
                    if (mwdict.mwdict.containsKey(hc)) {
                        LinkedList ll = (LinkedList) mwdict.mwdict.get(hc);
                        //	System.out.println("ll----------->"+ll + "\tsize\t"+ll.size());
                        int len = ll.size();
                        for (int j = 0; j < len; j++) {
                            LinkedList get_mw_ll = (LinkedList) ll.get(j);
                            String mw = (String) get_mw_ll.get(0);
                            String mw_hw = (String) get_mw_ll.get(1);
                            String mw_uw = (String) get_mw_ll.get(2);
                            int now = (Integer) get_mw_ll.get(3);
                            String fw = (String) get_mw_ll.get(4);
                            String mwe_tuid = (String) get_mw_ll.get(5);
                            String mwe_cuid = (String) get_mw_ll.get(6);

                            int endIndex; // = now-1;							
                            int toIndex; // = indx + now;

                            ArrayList new_list = get_Multiwords(mw);
                            if (root.size() == now) {
                                endIndex = now - 1;
                                toIndex = indx + now;
                                get_list.addAll(root);
                                //	System.out.println("Inside if Index:"+indx+"+"+endIndex+"("+now+")"+"==="+toIndex);
                            } else if (root.size() < now) {
                                endIndex = now - 1;
                                toIndex = indx + endIndex;
                                get_list.addAll(root.subList(indx, toIndex));
                                //	System.out.println("Inside Else if Index:"+indx+"+"+endIndex+"("+now+")"+"==="+toIndex);
                            } else {
                                //	System.out.println("count Mismatch");
                                endIndex = now - 1;
                                toIndex = indx + now;
                                get_list.addAll(root.subList(indx, toIndex));
                                //	System.out.println("Inside else Index:"+indx+"+"+endIndex+"("+now+")"+"==="+toIndex);
                            }
                            //	}		
                            //	System.out.println("NEW LIST:---------->"+new_list+"\t"+get_list);
                            if (get_list.containsAll(new_list)) {
                                //		System.out.println("MULTIWORD MATCHING--------->"+new_list);
                                String multiword = mw + "/" + mw_hw + "/" + mw_uw + "/" + lc + "/" + mwe_tuid + "/" + mwe_cuid + "/" + anal_value;
                                final_list.add(multiword);
                                break;
                            } else {
                                //	System.out.println("ELSE NOT A MULTIWORD");
                            }
                            get_list.clear();
                        }
                    }
                    //		mwe += tw + " ";
                }
            }
        } catch (Exception e) {
            //	e.printStackTrace();
        }
        return final_list;
    }

    public ArrayList get_Multiwords(String mwe) {
        ArrayList mwe_list = new ArrayList();
        try {
            StringTokenizer strToken = new StringTokenizer(mwe, " ");
            while (strToken.hasMoreTokens()) {
                String word = strToken.nextToken().trim();
                mwe_list.add(word);
            }
        } catch (Exception e) {
        }
        return mwe_list;
    }
}
