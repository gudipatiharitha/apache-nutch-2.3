package org.apache.nutch.analysis.unl.ta.Integrated;

import java.util.ArrayList;
import static org.apache.nutch.analysis.unl.ta.Integrated.Symbols.LN_TAMIL;

public class CategoriesQuery_en {

    private static CategoriesQuery_en objQuerycategorization = null;

    public static CategoriesQuery_en instanceOf() {
        if (objQuerycategorization == null) {
            objQuerycategorization = new CategoriesQuery_en();
        }
        return objQuerycategorization;
    }

    /**
     * Analyser
     *
     * @param query
     * @return
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
            getRoot = Symbol_Filter.instanceOf().remove_Symbol(getAnalyzerOP);
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
     *
     * @param obj
     * @return
     */
    public String StemmedWord(String query, CoreeObject obj) {
        if (obj.getLanguage().equals(LN_TAMIL)) {
            return getAnalyserOut(query);
        } else {
            return query;
        }
        // return null;
    }

    public CoreeObject process(CoreeObject core, ArrayList<LangIdentifierObject> lnIdentifierResult) {
        // Single Concepts with analysed output
        ArrayList<String> singleConcepts = new ArrayList<String>();
        ArrayList<String> singleConceptsWithoutAnalyser = new ArrayList<String>();
        String translatedOutput = "";
        // for English
        ArrayList<String> en_SingleConcepts = new ArrayList<String>();
        //System.out.println("Size:"+lnIdentifierResult.size());
        for (LangIdentifierObject tem : lnIdentifierResult) {
            if (tem.getLanguage().equals("ta")) {
                singleConceptsWithoutAnalyser.add(tem.getWord());// tamil concept with out stemming			
                String analysedWord = StemmedWord(tem.getWord(), core);
                singleConcepts.add(analysedWord);// tamil concept with stemming				
                //System.out.println("ta:"+analysedWord);
            } else { // for english word
                en_SingleConcepts.add(tem.getWord());// tamil concept with stemming
                BST bs = new BST();
                bs = Translate_en_to_ta.getInstance().get_bst();
                String translate = bs.retrive_tamilword(tem.getWord().trim().toLowerCase());
                if (translate != null) {
                    String analysedWord = StemmedWord(translate, core);
                    singleConcepts.add(analysedWord);// tamil concept with stemming
                }
                //System.out.println("en:"+tem.getWord());
            }
        }
        core.setLnIdentifierResult(lnIdentifierResult);
        //to set translated Query Word
        //System.out.println(translatedOutput);
        core.setTamilTranslatedConcepts(translatedOutput);
        // add single tamil term
        core.setTamilConcepts(singleConcepts);
        // add single English term
        core.setEnglishConcepts(en_SingleConcepts);
        return core;
    }
}
