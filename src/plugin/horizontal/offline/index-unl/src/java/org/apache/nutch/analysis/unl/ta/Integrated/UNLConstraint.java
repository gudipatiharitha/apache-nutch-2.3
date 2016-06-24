package org.apache.nutch.analysis.unl.ta.Integrated;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import static org.apache.nutch.analysis.unl.ta.Integrated.Symbols.DICT_LIST_PATH;
import static org.apache.nutch.analysis.unl.ta.Integrated.Symbols.DICT_PATH;

public class UNLConstraint {

    public static Hashtable<String, ArrayList<String>> uwWords = new Hashtable<String, ArrayList<String>>();
    public static Hashtable<String, ArrayList<String>> POS = new Hashtable<String, ArrayList<String>>();
    public static boolean isuwWordsLoaded = false;
    public static ArrayList<String> temp = new ArrayList<String>();
    public static FreqSort freqsort = new FreqSort();
    public ArrayList<String> singleWordConcept;

    private void loadUWList(String filename) {
        String pos = filename.substring(filename.lastIndexOf("/") + 1, filename.lastIndexOf("."));
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filename));
            String tem = "";
            while ((tem = br.readLine()) != null) {
                String[] splituw = tem.split("/");
                if (splituw.length == 2) {
                    if (!temp.contains(splituw[0])) {
                        //constraint
                        ArrayList<String> newData = new ArrayList<String>();
                        newData.add(splituw[1]);
                        uwWords.put(splituw[0], newData);
                        temp.add(splituw[0]);
                        //POS
                        newData = new ArrayList<String>();
                        newData.add(pos);
                        POS.put(splituw[0], newData);
                    } else {
                        ArrayList<String> newData = uwWords.get(splituw[0]);
                        if (!newData.contains(splituw[1])) {
                            newData.add(splituw[1]);
                            uwWords.remove(splituw[0]);
                            uwWords.put(splituw[0], newData);
                            //pos
                            newData = new ArrayList<String>();
                            newData = POS.get(splituw[0]);
                            newData.add(pos);
                            POS.remove(splituw[0]);
                            POS.put(splituw[0], newData);
                        }
                    }
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String getPOS(String word) {
        if (POS.get(word) != null) {
            return POS.get(word).get(0);
        }
        return null;
    }

    public String getUNLKB(String word) {
        if (isuwWordsLoaded == false) {
            System.out.println("Index going to Load...");
            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader(DICT_LIST_PATH));
                String tem = "";
                while ((tem = br.readLine()) != null) {
                    loadUWList(DICT_PATH + tem);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            isuwWordsLoaded = true;
            System.out.println("Index Loading Completed...");
        }

        if (uwWords.get(word) != null) {
            return uwWords.get(word).get(0);
        }
        return null;
    }

    public String constuctGraph_MixedMode(String word, int i) {
        //System.out.println("Im in Eng Graph");
        String graph = "";
        try {
            String output = word + ";" + word + ";" + getUNLKB(word) + ";" + getPOS(word) + ";" + i;
            graph += output + "#";
            //graph +=graph+"#";			            
        } catch (Exception e) {
        }
        //System.out.println("[s]#[w]#" + graph + "[/w]#[r]#[/r]#[/s]#");
        return "[s]#[w]#" + graph + "[/w]#[r]#[/r]#[/s]#";
    }

    public String constuctGraph(CoreeObject obj) {
        String graph = "";
        try {
            for (int i = 0; i < obj.getEnglishConcepts().size(); i++) {
                String output = obj.getEnglishConcepts().get(i) + ";" + obj.getEnglishConcepts().get(i) + ";" + getUNLKB(obj.getEnglishConcepts().get(i)) + ";" + getPOS(obj.getEnglishConcepts().get(i)) + ";" + (i + 1);
                graph += output + "#";
                //graph +=graph+"#";			
            }
        } catch (Exception e) {
        }
        //System.out.println("[s]#[w]#" + graph + "[/w]#[r]#[/r]#[/s]#");
        return "[s]#[w]#" + graph + "[/w]#[r]#[/r]#[/s]#";
    }
    /**
     * Singleton class
     *
     * @param args
     */
    private static UNLConstraint unlConstraint = null;

    public static UNLConstraint instance() {
        if (unlConstraint == null) {
            unlConstraint = new UNLConstraint();
        }
        return unlConstraint;
    }

    public static void main(String args[]) {
        UNLConstraint trans = new UNLConstraint();
        String sample = trans.getUNLKB("chennai");
        System.out.println("Size:" + UNLConstraint.uwWords.size());
        System.out.println("Size:" + UNLConstraint.temp.size());
        System.out.println("=-->" + sample);
    }
}
