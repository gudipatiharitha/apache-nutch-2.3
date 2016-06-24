package org.apache.nutch.analysis.unl.ta.Integrated;

import java.util.ArrayList;

public class CoreeObject {
    //Query Information

    private String actualQuery;
    private String modifiedQuery;
    private String language;
    //Query Expansion
    private ArrayList<String> tamilConceptWithoutAnalyser = new ArrayList<String>();
    private ArrayList<String> rootWords = new ArrayList<String>();
    private ArrayList<String> uwconceptid = new ArrayList<String>();
    private ArrayList<String> multiwords = new ArrayList<String>();
    private ArrayList<String> tamilConcepts = new ArrayList<String>();
    private ArrayList<String> andConcepts_ta = new ArrayList<String>();
    private ArrayList<String> andConcepts_en = new ArrayList<String>();
    private ArrayList<String> englishConcepts = new ArrayList<String>();
    private ArrayList<String> doubleQuotesConcepts_ta = new ArrayList<String>();
    private ArrayList<String> doubleQuotesConcepts_en = new ArrayList<String>();
    private ArrayList<EnglishConceptObject> EnglishConceptInfo = new ArrayList<EnglishConceptObject>();
    private String EnglishConceptGraph = null;
    private String tamilTranslatedConcepts = null;
    private ArrayList<LangIdentifierObject> lnIdentifierResult;
    //Query Translation
    private String unlgraph_actualQuery;
    private ArrayList<String> en_headWord;
    private ArrayList<String> ta_headWord;
    //private ArrayList<unlConceptObject> unlgraph_Query;
    //private ArrayList<unlConceptObject> unlgraph_QueryExpansion;
    //private ArrayList<unlConceptObject> unlgraph_ICLIOFExpansion;

    /**
     * @return the uwconcept
     */
    public ArrayList<String> getUwconceptid() {
        return uwconceptid;
    }

    /**
     * @param englishConceptGraph the englishConceptGraph to set
     */
    public void setUwconceptid(ArrayList<String> Uwconceptid) {
        uwconceptid = Uwconceptid;
    }

    /**
     * @return the actualQuery
     */
    public String getActualQuery() {
        return actualQuery;
    }

    /**
     * @return the englishConceptGraph
     */
    public String getEnglishConceptGraph() {
        return EnglishConceptGraph;
    }

    /**
     * @param englishConceptGraph the englishConceptGraph to set
     */
    public void setEnglishConceptGraph(String englishConceptGraph) {
        EnglishConceptGraph = englishConceptGraph;
    }

    /**
     * @return the englishConceptInfo
     */
    public ArrayList<EnglishConceptObject> getEnglishConceptInfo() {
        return EnglishConceptInfo;
    }

    /**
     * @param englishConceptInfo the englishConceptInfo to set
     */
    public void setEnglishConceptInfo(ArrayList<EnglishConceptObject> englishConceptInfo) {
        EnglishConceptInfo = englishConceptInfo;
    }

    /**
     * @param actualQuery the actualQuery to set
     */
    public void setActualQuery(String actualQuery) {
        this.actualQuery = actualQuery;
    }

    /**
     * @return the modifiedQuery
     */
    public String getModifiedQuery() {
        return modifiedQuery;
    }

    /**
     * @param modifiedQuery the modifiedQuery to set
     */
    public void setModifiedQuery(String modifiedQuery) {
        this.modifiedQuery = modifiedQuery;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the tamilConceptWithoutAnalyser
     */
    public ArrayList<String> getTamilConceptWithoutAnalyser() {
        return tamilConceptWithoutAnalyser;
    }

    /**
     * @param tamilConceptWithoutAnalyser the tamilConceptWithoutAnalyser to set
     */
    public void setTamilConceptWithoutAnalyser(
            ArrayList<String> tamilConceptWithoutAnalyser) {
        this.tamilConceptWithoutAnalyser = tamilConceptWithoutAnalyser;
    }

    /**
     * @return the rootWords
     */
    public ArrayList<String> getRootWords() {
        return rootWords;
    }

    /**
     * @param rootWords the rootWords to set
     */
    public void setRootWords(ArrayList<String> rootWords) {
        this.rootWords = rootWords;
    }

    /**
     * @return the multiwords
     */
    public ArrayList<String> getMultiwords() {
        return multiwords;
    }

    /**
     * @param multiwords the multiwords to set
     */
    public void setMultiwords(ArrayList<String> multiwords) {
        this.multiwords = multiwords;
    }

    /**
     * @return the tamilConcepts
     */
    public ArrayList<String> getTamilConcepts() {
        return tamilConcepts;
    }

    /**
     * @param tamilConcepts the tamilConcepts to set
     */
    public void setTamilConcepts(ArrayList<String> tamilConcepts) {
        this.tamilConcepts = tamilConcepts;
    }

    /**
     * @return the andConcepts_ta
     */
    public ArrayList<String> getAndConcepts_ta() {
        return andConcepts_ta;
    }

    /**
     * @param andConcepts_ta the andConcepts_ta to set
     */
    public void setAndConcepts_ta(ArrayList<String> andConcepts_ta) {
        this.andConcepts_ta = andConcepts_ta;
    }

    /**
     * @return the andConcepts_en
     */
    public ArrayList<String> getAndConcepts_en() {
        return andConcepts_en;
    }

    /**
     * @param andConcepts_en the andConcepts_en to set
     */
    public void setAndConcepts_en(ArrayList<String> andConcepts_en) {
        this.andConcepts_en = andConcepts_en;
    }

    /**
     * @return the englishConcepts
     */
    public ArrayList<String> getEnglishConcepts() {
        return englishConcepts;
    }

    /**
     * @param englishConcepts the englishConcepts to set
     */
    public void setEnglishConcepts(ArrayList<String> englishConcepts) {
        this.englishConcepts = englishConcepts;
    }

    /**
     * @return the doubleQuotesConcepts_ta
     */
    public ArrayList<String> getDoubleQuotesConcepts_ta() {
        return doubleQuotesConcepts_ta;
    }

    /**
     * @param doubleQuotesConcepts_ta the doubleQuotesConcepts_ta to set
     */
    public void setDoubleQuotesConcepts_ta(ArrayList<String> doubleQuotesConcepts_ta) {
        this.doubleQuotesConcepts_ta = doubleQuotesConcepts_ta;
    }

    /**
     * @return the doubleQuotesConcepts_en
     */
    public ArrayList<String> getDoubleQuotesConcepts_en() {
        return doubleQuotesConcepts_en;
    }

    /**
     * @param doubleQuotesConcepts_en the doubleQuotesConcepts_en to set
     */
    public void setDoubleQuotesConcepts_en(ArrayList<String> doubleQuotesConcepts_en) {
        this.doubleQuotesConcepts_en = doubleQuotesConcepts_en;
    }

    /**
     * @return the tamilTranslatedConcepts
     */
    public String getTamilTranslatedConcepts() {
        return tamilTranslatedConcepts;
    }

    /**
     * @param tamilTranslatedConcepts the tamilTranslatedConcepts to set
     */
    public void setTamilTranslatedConcepts(String tamilTranslatedConcepts) {
        this.tamilTranslatedConcepts = tamilTranslatedConcepts;
    }

    /**
     * @return the lnIdentifierResult
     */
    public ArrayList<LangIdentifierObject> getLnIdentifierResult() {
        return lnIdentifierResult;
    }

    /**
     * @param lnIdentifierResult the lnIdentifierResult to set
     */
    public void setLnIdentifierResult(
            ArrayList<LangIdentifierObject> lnIdentifierResult) {
        this.lnIdentifierResult = lnIdentifierResult;
    }

    /**
     * @return the unlgraph_actualQuery
     */
    public String getUnlgraph_actualQuery() {
        return unlgraph_actualQuery;
    }

    /**
     * @param unlgraph_actualQuery the unlgraph_actualQuery to set
     */
    public void setUnlgraph_actualQuery(String unlgraph_actualQuery) {
        this.unlgraph_actualQuery = unlgraph_actualQuery;
    }

    /**
     * @return the en_headWord
     */
    public ArrayList<String> getEn_headWord() {
        return en_headWord;
    }

    /**
     * @param en_headWord the en_headWord to set
     */
    public void setEn_headWord(ArrayList<String> en_headWord) {
        this.en_headWord = en_headWord;
    }

    /**
     * @return the ta_headWord
     */
    public ArrayList<String> getTa_headWord() {
        return ta_headWord;
    }

    /**
     * @param ta_headWord the ta_headWord to set
     */
    public void setTa_headWord(ArrayList<String> ta_headWord) {
        this.ta_headWord = ta_headWord;
    }
}
