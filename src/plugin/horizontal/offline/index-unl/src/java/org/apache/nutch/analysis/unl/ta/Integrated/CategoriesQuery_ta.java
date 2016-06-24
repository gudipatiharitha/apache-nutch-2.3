package org.apache.nutch.analysis.unl.ta.Integrated;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.nutch.analysis.unl.ta.Integrated.Symbols.MULTIWORDLIST;
import static org.apache.nutch.analysis.unl.ta.Integrated.Symbols.LN_TAMIL;

/**
 * CategoriesQuery_ta Class to extract information from Tamil Query.
 *
 * @author root
 *
 */
public class CategoriesQuery_ta {

    public static Symbol_Filter filter = new Symbol_Filter();
    public static TreeSet<String> multiWords = new TreeSet<String>();
    public static boolean isMultiWordsLoaded = false;
    public BST bs = new BST();
    /**
     * singleton class
     */
    private static CategoriesQuery_ta objQuerycategorization = null;

    public static CategoriesQuery_ta instanceOf() {
        if (objQuerycategorization == null) {
            objQuerycategorization = new CategoriesQuery_ta();
        }
        return objQuerycategorization;
    }

    /**
     * Language Identifier Pattern to check whether the Tamil Query word is
     * Multiword or not.
     *
     * @param lnPattern
     * @param lnIdentifierResult
     * @return
     */
    public ArrayList<String> multiwordPatternCheck(ArrayList<Integer> lnPattern, ArrayList<LangIdentifierObject> lnIdentifierResult) {
        ArrayList<String> multiwordCheck = new ArrayList<String>();
        //System.out.println("Size"+lnPattern.size());		 				
        int wordCounter = 0;
        int taWordCounter = 0;
        String checkMultiword = "";
        for (Integer i : lnPattern) {
            wordCounter++;
            //System.out.println(i);
            if (i == 1) {
                taWordCounter++;
                //checkMultiword+="a"+" ";
                checkMultiword += lnIdentifierResult.get(wordCounter - 1).getWord() + " ";
                if (wordCounter == lnPattern.size()) {
                    //System.out.println("1.Multiword"+checkMultiword);
                    multiwordCheck.add(checkMultiword);
                }
            } else {
                if (taWordCounter > 1) {
                    //System.out.println("2.Multiword"+checkMultiword);
                    multiwordCheck.add(checkMultiword);
                }
                taWordCounter = 0;
                checkMultiword = "";
            }
        }

        return multiwordCheck;
    }

    /**
     * Multiword Checker
     *
     * @param queryStr
     */
    private boolean multiwordCheck(String str) {
        populateMultiWords();
        if (multiWords.contains(str)) {
            return true;
        }
        return false;
    }

    /**
     * Populate Multiword in the Tree Set once.
     */
    private void populateMultiWords() {
        if (isMultiWordsLoaded) {
            return;
        }
        String string = null;
        BufferedReader bufferedReader = getBufferedReader(MULTIWORDLIST);
        try {
            while ((string = bufferedReader.readLine()) != null) {
                multiWords.add(string.substring(0, string.indexOf("/")));
            }
        } catch (IOException ex) {
            Logger.getLogger(CategoriesQuery_ta.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        closeBufferedReader(bufferedReader);
        isMultiWordsLoaded = true;
    }

    private BufferedReader getBufferedReader(String fileName) {
        try {
            return (new BufferedReader(new FileReader(fileName)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CategoriesQuery_ta.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return null;
    }

    private void closeBufferedReader(BufferedReader bufferedReader) {
        try {
            bufferedReader.close();
        } catch (IOException ex) {
            Logger.getLogger(CategoriesQuery_ta.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    /**
     * to get the root from the analyser
     *
     * @param Coree object
     * @return stemmed word.
     */
    public String StemmedWord(String query, CoreeObject obj) {
        if (obj.getLanguage().equals(LN_TAMIL)) {
            return getAnalyserOut(query);
        } else {
            return query;
        }
        // return null;
    }

    /**
     * getAnalyser to get all the morphological information from Analyser module
     *
     * @param query word
     * @return morphological information
     */
    public String getAnalyserOut(String query) {
        String getRoot = "";
        // to get the Analyser Output
        String getAnalyzerOP = org.apache.nutch.analysis.unl.ta.Analyser.analyseF(query, true);// to get the Morphological Analyser
        // output

        if (getAnalyzerOP.contains("<unknown>")
                || getAnalyzerOP.contains("count=4")) { // if Analyser output
            // for the Query word
            // contains <unknown>
            // return query word
            // else return root
            // word.
            getRoot = query;
        } else if (!getAnalyzerOP.contains("<unknown>")
                && !getAnalyzerOP.contains("count")
                && !getAnalyzerOP.contains("Noun")
                && !getAnalyzerOP.contains("Entity")
                && !getAnalyzerOP.contains("Verb")
                && !getAnalyzerOP.contains("a") && !getAnalyzerOP.contains("e")
                && !getAnalyzerOP.contains("i") && !getAnalyzerOP.contains("o")
                && !getAnalyzerOP.contains("u")) {
            getRoot = filter.remove_Symbol(getAnalyzerOP);
        } else {
            String splitAnalrzerOP[] = getAnalyzerOP.split("\n");
            if (splitAnalrzerOP.length >= 2) {
                getRoot = splitAnalrzerOP[1].substring(0,
                        splitAnalrzerOP[1].indexOf("<")).trim();
            }
        }
        // System.out.println("Root"+getRoot);
        return getRoot;
    }

    /**
     * For New Language Identifier Multword check
     *
     * @param inputString
     * @return
     */
    private ArrayList<String> getMultiWord(ArrayList<String> mwCheck) {
        ArrayList<String> multiword = new ArrayList<String>();
        for (String inputstring : mwCheck) {
            String Multiword = "";
            String[] splitstring = inputstring.split(" ");
            int lengthofinput = splitstring.length;
            for (int sequencelength = lengthofinput; sequencelength > 0; sequencelength--) {
                for (int startindex = 0; startindex + sequencelength <= lengthofinput; startindex++) {
                    String temp = "";
                    for (int index = startindex; index <= startindex
                            + sequencelength - 1; index++) {
                        temp = temp + " " + splitstring[index];
                    }
                    if (multiwordCheck(temp.trim()) == true) {
                        // Multiword = Multiword + temp + "%";
                        // StemQWord.add(temp);
                        multiword.add(temp.trim());
                    }
                }
            }
        }
        //obj.setMultiwords(multiword);
        System.out.println(">>>>>>" + multiword.toString());
        return multiword;
    }

    /**
     * For old Language Identifier Multword check
     *
     * @param inputString
     * @return
     */
    private ArrayList<String> MultiWord(String query) {
        ArrayList<String> multiword = new ArrayList<String>();
        String Multiword = "";
        String[] splitstring = query.split(" ");
        int lengthofinput = splitstring.length;
        for (int sequencelength = lengthofinput; sequencelength > 0; sequencelength--) {
            for (int startindex = 0; startindex + sequencelength <= lengthofinput; startindex++) {
                String temp = "";
                for (int index = startindex; index <= startindex
                        + sequencelength - 1; index++) {
                    temp = temp + " " + splitstring[index];
                }
                if (multiwordCheck(temp.trim()) == true) {
                    // Multiword = Multiword + temp + "%";
                    // StemQWord.add(temp);
                    multiword.add(temp.trim());
                    //System.out.println("multiword:"+temp);
                }
            }
        }

        //obj.setMultiwords(multiword);
        return multiword;
    }

    /**
     * processAll method to collected all information like analyser, multiword
     * etc from the differed methods
     *
     * @param core object
     * @param lnIdentifierResult language identifier result
     * @return core object with all its information.
     */
    public CoreeObject processAll(CoreeObject core, ArrayList<LangIdentifierObject> lnIdentifierResult) {
        // Single Concepts with analysed output
        ArrayList<String> singleConcepts = new ArrayList<String>();
        ArrayList<String> singleConceptsWithoutAnalyser = new ArrayList<String>();
        String translatedOutput = "";

        // for English
        ArrayList<String> en_SingleConcepts = new ArrayList<String>();

        //to get And-Concept
        ArrayList<String> andConcepts = new ArrayList<String>();

        for (LangIdentifierObject tem : lnIdentifierResult) {
            if (tem.getLanguage().equals("ta")) {
                translatedOutput += tem.getWord() + " ";
                singleConceptsWithoutAnalyser.add(tem.getWord());// tamil concept with out stemming			
                String analysedWord = StemmedWord(tem.getWord(), core);
                //System.out.println("without A:"+tem.getWord()+"\twith:"+analysedWord);
                singleConcepts.add(analysedWord);// tamil concept with stemming																						
                //System.out.println("ta:"+analysedWord);
            } else { // for english word					
                bs = new BST();
                bs = Translate_en_to_ta.getInstance().get_bst();
                String translate = bs.retrive_tamilword(tem.getWord().trim().toLowerCase());
                translatedOutput += translate + " ";

                //en_SingleConcepts.add(tem.getWord());// tamil concept with stemming
                //System.out.println("en:"+tem.getWord());
            }
        }

        // Multiword
        ArrayList<Integer> lnPattern = LanguageIdentifier.instanceOf().identifyLanguage_MW(core.getModifiedQuery());
        //System.out.println("lpattern"+lnPattern.toString());						

        //to set translated Query Word
        //System.out.println(translatedOutput);
        core.setTamilTranslatedConcepts(translatedOutput);

        // add single tamil term
        core.setTamilConcepts(singleConcepts);
        if (core.getTamilTranslatedConcepts() == null) {
            core.setTamilTranslatedConcepts(core.getModifiedQuery());
        }



        ArrayList<String> multiword = MultiWord(core.getTamilTranslatedConcepts());
        core.setMultiwords(multiword);
        //System.out.println("multiword:::"+multiword.toString());


        // add single English term
        core.setEnglishConcepts(en_SingleConcepts);
        return core;
    }
}
