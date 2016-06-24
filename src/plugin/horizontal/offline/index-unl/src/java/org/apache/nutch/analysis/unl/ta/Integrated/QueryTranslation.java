package org.apache.nutch.analysis.unl.ta.Integrated;

import java.util.ArrayList;
import org.apache.nutch.analysis.unl.ta.FinalLLImpl;

public class QueryTranslation {

    public static FreqSort freqsort = new FreqSort();
    public ArrayList<String> singleWordConcept;
    FinalLLImpl[] llq = new FinalLLImpl[3];
    /**
     * Single ton class
     */
    private static QueryTranslation objQuery_Translation_Expansion = null;

    public static QueryTranslation getInstance() {
        if (objQuery_Translation_Expansion == null) {
            objQuery_Translation_Expansion = new QueryTranslation();
        }
        return objQuery_Translation_Expansion;
    }

    public FinalLLImpl[] getQuerygraph(CoreeObject core, boolean online_process) {
        if (core.getLanguage().equals("ta")) {

            llq = new TamilQueryGraph().translateAll(core, online_process);

        } else {
            // call English Query Graph
            new EnglishQueryGraph().translateAll(core, online_process);
            llq = EnglishQueryGraph.multilist_UNLQuery();//get translated query
        }
        return llq;
    }

    public static void main(String args[]) {
        CoreeObject obj = new CoreeObject();

        //objx = new CoreeObject();
        obj.setActualQuery("செ‌ன்னை‌");//கட்டிடக்கலையில் திருச்சியும் தஞ்சையும்
        //obj.setActualQuery("கட்டிடக்கலையில் திருச்சியும் தஞ்சையும்");
        obj.setLanguage("ta");
        CoreeObject objx = new QueryProcessing().processQuery(obj);
        System.out.println("QE Actual Qry:" + objx.getActualQuery());
        System.out.println("QE translated:" + objx.getTamilTranslatedConcepts());
        System.out.println("QE Modified Qry:" + objx.getModifiedQuery());
        System.out.println("QE Ta Concept:" + objx.getTamilConcepts());
        System.out.println("QE MW:" + objx.getMultiwords());
        System.out.println("QE Ea Concept:" + objx.getEnglishConcepts());
        System.out.println("QE AND:" + objx.getAndConcepts_ta());
        System.out.println("QE Quotes:" + objx.getDoubleQuotesConcepts_ta());
        new QueryTranslation().getQuerygraph(objx, false);

        obj.setActualQuery("மதுரைக்கு செல்லும் வழி");//கட்டிடக்கலையில் திருச்சியும் தஞ்சையும்    		
        obj.setLanguage("ta");
        objx = new QueryProcessing().processQuery(obj);
        /*
         * System.out.println("QE Actual Qry:" + objx.getActualQuery());
         * System.out.println("QE translated:" +
         * objx.getTamilTranslatedConcepts()); System.out.println("QE Modified
         * Qry:" + objx.getModifiedQuery()); System.out.println("QE Ta Concept:"
         * + objx.getTamilConcepts()); System.out.println("QE MW:" +
         * objx.getMultiwords()); System.out.println("QE Ea Concept:" +
         * objx.getEnglishConcepts()); System.out.println("QE AND:" +
         * objx.getAndConcepts_ta()); System.out.println("QE Quotes:" +
         * objx.getDoubleQuotesConcepts_ta());
         */
        new QueryTranslation().getQuerygraph(objx, false);

        obj.setActualQuery("மதுரைக்கு go வழி ");//மதுரைக்கு செல்ல வழி   		
        obj.setLanguage("ta");
        objx = new QueryProcessing().processQuery(obj);
        /*
         * System.out.println("QE Actual Qry:" + objx.getActualQuery());
         * System.out.println("QE translated:" +
         * objx.getTamilTranslatedConcepts()); System.out.println("QE Modified
         * Qry:" + objx.getModifiedQuery()); System.out.println("QE Ta Concept:"
         * + objx.getTamilConcepts()); System.out.println("QE MW:" +
         * objx.getMultiwords()); System.out.println("QE Ea Concept:" +
         * objx.getEnglishConcepts()); System.out.println("QE AND:" +
         * objx.getAndConcepts_ta()); System.out.println("QE Quotes:" +
         * objx.getDoubleQuotesConcepts_ta());
         */
        new QueryTranslation().getQuerygraph(objx, false);

        obj.setActualQuery("The வழி to Madurai");
        obj.setLanguage("ta");
        objx = new QueryProcessing().processQuery(obj);
        /*
         * System.out.println("QE Actual Qry:" + objx.getActualQuery());
         * System.out.println("QE translated:" +
         * objx.getTamilTranslatedConcepts()); System.out.println("QE Modified
         * Qry:" + objx.getModifiedQuery()); System.out.println("QE Ta Concept:"
         * + objx.getTamilConcepts()); System.out.println("QE MW:" +
         * objx.getMultiwords()); System.out.println("QE Ea Concept:" +
         * objx.getEnglishConcepts()); System.out.println("QE AND:" +
         * objx.getAndConcepts_ta()); System.out.println("QE Quotes:" +
         * objx.getDoubleQuotesConcepts_ta());
         */
        new QueryTranslation().getQuerygraph(objx, false);



        //obj.setActualQuery(" america \"தமிழ் சினிமா\" அகத்தியர்+அருவி apple செட்டிநாடு+உணவு \"ஊட்டி மலை\" ");

        //obj.setActualQuery(" america அருவி  roll");
        //obj.setActualQuery(" america \"தமிழ் சினிமா\" அகத்தியர்+அருவி apple செட்டிநாடு+உணவு \"ஊட்டி மலை\" ");
    		/*
         * obj.setActualQuery(" america \"tamil cinema\" agathiyar+waterfalls
         * apple chettinad+food \"ooty mountain\" "); obj.setLanguage("en");
         *
         * CoreeObject objx = new QueryProcessing().processQuery(obj);
         * System.out.println("QE Actual Qry:" + objx.getActualQuery());
         * System.out.println("QE translated:" +
         * objx.getTamilTranslatedConcepts()); System.out.println("QE Modified
         * Qry:" + objx.getModifiedQuery()); System.out.println("QE Ta Concept:"
         * + objx.getTamilConcepts()); System.out.println("QE MW:" +
         * objx.getMultiwords()); System.out.println("QE Ea Concept:" +
         * objx.getEnglishConcepts()); System.out.println("QE AND:" +
         * objx.getAndConcepts_ta()); System.out.println("QE Quotes:" +
         * objx.getDoubleQuotesConcepts_ta());
         *
         * new QueryTranslation().getQuerygraph(objx);
         *
         * obj.setActualQuery(" america \"தமிழ் சினிமா\" அகத்தியர்+அருவி apple
         * செட்டிநாடு+உணவு \"ஊட்டி மலை\" "); //obj.setActualQuery(" america
         * \"tamil cinema\" agathiyar+waterfalls apple chettinad+food \"ooty
         * mountain\" "); obj.setLanguage("ta");
         *
         * objx = new QueryProcessing().processQuery(obj);
         * System.out.println("QE Actual Qry:" + objx.getActualQuery());
         * System.out.println("QE translated:" +
         * objx.getTamilTranslatedConcepts()); System.out.println("QE Modified
         * Qry:" + objx.getModifiedQuery()); System.out.println("QE Ta Concept:"
         * + objx.getTamilConcepts()); System.out.println("QE MW:" +
         * objx.getMultiwords()); System.out.println("QE Ea Concept:" +
         * objx.getEnglishConcepts()); System.out.println("QE AND:" +
         * objx.getAndConcepts_ta()); System.out.println("QE Quotes:" +
         * objx.getDoubleQuotesConcepts_ta());
         *
         * new QueryTranslation().getQuerygraph(objx);
         *
         *
         * obj = new CoreeObject(); obj.setActualQuery("செட்டிநாடு உணவு
         * அகத்தியர் அருவி"); obj.setLanguage("ta"); objx = new
         * QueryProcessing().processQuery(obj); System.out.println("QE Actual
         * Qry:" + objx.getActualQuery()); System.out.println("QE translated:" +
         * objx.getTamilTranslatedConcepts()); System.out.println("QE Modified
         * Qry:" + objx.getModifiedQuery()); System.out.println("QE Ta Concept:"
         * + objx.getTamilConcepts()); System.out.println("QE MW:" +
         * objx.getMultiwords()); System.out.println("QE Ea Concept:" +
         * objx.getEnglishConcepts()); System.out.println("QE AND:" +
         * objx.getAndConcepts_ta()); System.out.println("QE Quotes:" +
         * objx.getDoubleQuotesConcepts_ta());
         *
         * new QueryTranslation().getQuerygraph(objx);
         *
         *
         * /*obj.setActualQuery(" america \"Tamil Cinima\" அகத்தியர்+அருவி
         * apple செட்டிநாடு+உணவு \"ooty mountain\""); //obj.setActualQuery("
         * america \"tamil cinema\" agathiyar+waterfalls apple chettinad+food
         * \"ooty mountain\" "); obj.setLanguage("en");
         *
         * CoreeObject objx = new QueryProcessing().processQuery(obj);
         * System.out.println("QE Actual Qry:" + objx.getActualQuery());
         * System.out.println("QE translated:" +
         * objx.getTamilTranslatedConcepts()); System.out.println("QE Modified
         * Qry:" + objx.getModifiedQuery()); System.out.println("QE Ta Concept:"
         * + objx.getTamilConcepts()); System.out.println("QE MW:" +
         * objx.getMultiwords()); System.out.println("QE Ea Concept:" +
         * objx.getEnglishConcepts()); System.out.println("QE AND:" +
         * objx.getAndConcepts_ta()); System.out.println("QE Quotes:" +
         * objx.getDoubleQuotesConcepts_ta());
         *
         * new QueryTranslation().getQuerygraph(objx);
         */

        /*
         * obj = new CoreeObject(); obj = new CoreeObject();
         * obj.setActualQuery("america and apple"); obj.setLanguage("en");
         *
         * objx = new QueryProcessing().processQuery(obj);
         * System.out.println("QE Actual Qry:" + objx.getActualQuery());
         * System.out.println("QE translated:" +
         * objx.getTamilTranslatedConcepts()); System.out.println("QE Modified
         * Qry:" + objx.getModifiedQuery()); System.out.println("QE Ta Concept:"
         * + objx.getTamilConcepts()); System.out.println("QE MW:" +
         * objx.getMultiwords()); System.out.println("QE Ea Concept:" +
         * objx.getEnglishConcepts()); System.out.println("QE AND:" +
         * objx.getAndConcepts_ta()); System.out.println("QE Quotes:" +
         * objx.getDoubleQuotesConcepts_ta());
         *
         * new QueryTranslation().getQuerygraph(objx);
         */

    }
}
