package org.apache.nutch.analysis.unl.ta.Integrated;

/**
 * Query Processing : Query Processing class extract the tamil terms and
 * important quotes and (+) And terms
 *
 * @author :TacoLa Lab @Organization :AUCEG
 */
import java.util.ArrayList;
import static org.apache.nutch.analysis.unl.ta.Integrated.DebugSetting.DEBUGMODE;

public class QueryProcessing {

    /**
     * to extract the And concepts (+) symbols
     *
     * @param queryword
     * @return
     */
    public CoreeObject getANDConcepts(CoreeObject core) {
        ArrayList<String> andconcept_ta = new ArrayList<String>();
        ArrayList<String> andconcept_en = new ArrayList<String>();
        if (core.getActualQuery().contains("+")) {
            String[] splitConcept = core.getActualQuery().split(" ");
            for (String temp : splitConcept) {
                if (temp.contains("+")) {
                    String templn = LanguageIdentifier.instanceOf().getLanguage(temp);
                    if (templn.equals("ta")) {
                        andconcept_ta.add(temp);
                    } else {
                        andconcept_en.add(temp);
                    }
                    //andconcept.add(temp.replace("+", " "));
                }//if
            }//for
        }//if

        //System.out.println("andconcept_ta:"+andconcept_ta);
        //System.out.println("andconcept_en:"+andconcept_en);
        core.setAndConcepts_ta(andconcept_ta);
        core.setAndConcepts_en(andconcept_en);
        return core;
    }

    /**
     * to extract very important concepts
     *
     * @param queryword
     * @return
     */
    public CoreeObject getQuotedConcepts(CoreeObject core) {
        String[] splitword = core.getActualQuery().split("");
        ArrayList<String> quotedConcept_ta = new ArrayList<String>();
        ArrayList<String> quotedConcept_en = new ArrayList<String>();

        String importantword = "";
        for (int i = 0; i < splitword.length; i++) {
            //System.out.println(splitword[i]);
            if (splitword[i].equals("\"")) {
                i = i + 1;
                for (int j = i; j < splitword.length; j++) {
                    if (splitword[j].equals("\"")) {
                        i = j;
                        break;
                    }
                    importantword += splitword[j];
                    //System.out.println(":::"+sample[j]);
                }

                //System.out.println(">>>"+importantword);
            }
            if (importantword.length() > 0) {
                String language = LanguageIdentifier.instanceOf().getLanguage(importantword);
                if (language.equals("ta")) {
                    quotedConcept_ta.add(importantword);
                } else {
                    quotedConcept_en.add(importantword);
                }
                //quotedwords.add(importantword);
            }
            importantword = "";
        }
        core.setDoubleQuotesConcepts_ta(quotedConcept_ta);
        core.setDoubleQuotesConcepts_en(quotedConcept_en);
        //System.out.println(quotedwords.toString());
        return core;
    }

    /*
     * public CoreeObject getQuotedConcepts(CoreeObject core){ ArrayList<String>
     * quotedConcept_ta = new ArrayList<String>(); ArrayList<String>
     * quotedConcept_en = new ArrayList<String>();
     * if(core.getActualQuery().contains("\"")){ String temp =
     * core.getActualQuery().substring(core.getActualQuery().indexOf("\"")+1,
     * core.getActualQuery().lastIndexOf("\""));
     *
     * String templn = LanguageIdentifier.instanceOf().getLanguage(temp);
     * //System.out.println("Language in QP:"+templn); if(templn.equals("ta")){
     * quotedConcept_ta.add(temp); }else{ quotedConcept_en.add(temp); }
     * //System.out.println("impconcept_ta:"+quotedConcept_ta);
     * //System.out.println("impconcept_en:"+quotedConcept_en);
     *
     * core.setDoubleQuotesConcepts_ta(quotedConcept_ta);
     * core.setDoubleQuotesConcepts_en(quotedConcept_en); } return core; }
     */
    /**
     * ProcessQuery Method to extract all the informations like +words, quoted
     * words, remove symbols, multiword and language identifier from the query
     * words
     *
     * @param core object must contain query word and language
     * @return core object with Query Processing result.
     */
    public CoreeObject processQuery(CoreeObject core) {

        //extract very important concepts from the query (identified by "" and (+) and logic )
        core = getANDConcepts(core);
        core = getQuotedConcepts(core);

        // filter symbols
        core.setModifiedQuery(Symbol_Filter.instanceOf().remove_Symbol(core.getActualQuery()));

        // Identify the language of Query Word
        ArrayList<LangIdentifierObject> identifiedWords = new ArrayList<LangIdentifierObject>();
        identifiedWords = LanguageIdentifier.instanceOf().identifyLanguage(core.getModifiedQuery());

        //call method based on the language input
        if (core.getLanguage().equals("ta")) {
            // CategoriesQuery_ta
            core = CategoriesQuery_ta.instanceOf().processAll(core, identifiedWords);
            //System.out.println("Multiword:"+core.getMultiwords());

        } else {
            // CategoriesQuery_en
            core = CategoriesQuery_en.instanceOf().process(core, identifiedWords);
        }

        if (DEBUGMODE == true) {
            System.out.println("Query Word:" + core.getActualQuery());
            System.out.println("Query Language:" + core.getLanguage());
            System.out.println("Query Modified as:" + core.getModifiedQuery());
            System.out.println("And Concept:" + core.getAndConcepts_ta() + "======" + core.getAndConcepts_en());
            System.out.println("Important Concept:" + core.getDoubleQuotesConcepts_ta() + "======" + core.getDoubleQuotesConcepts_en());

            for (LangIdentifierObject obj : identifiedWords) {
                System.out.println("Language Identifier:" + obj.getPosition() + "--" + obj.getWord() + "--" + obj.getLanguage());
            }
        }
        return core;
    }

    /**
     * Main method only for debugging.
     *
     * @param args
     */
    public static void main(String args[]) {
        //core.setActualQuery("america செட்டிநாடு+உணவுவகைகள்+அகத்தியர் அருவி apple \"செட்டிநாடு உணவுவகைகள்\" \"apple ipod\" ");
        //core.setLanguage("ta");
        CoreeObject obj = new CoreeObject();
        obj = new CoreeObject();
        obj.setActualQuery("america செட்டிநாடு+உணவுவகைகள்+அகத்தியர் அருவி apple \"செட்டிநாடு உணவுவகைகள்\" \"apple ipod\" ");
        //obj.setActualQuery("america செட்டிநாடு+உணவுவகைகள்+அகத்தியர் அருவி apple \"செட்டிநாடு உணவுவகைகள்\" ");
        //obj.setActualQuery("செட்டிநாடு உணவு அகத்தியர் அருவி");
        //obj.setActualQuery("செட்டிநாடு உணவுவகைகள் அகத்தியர் அருவி");
        //obj.setLanguage("ta");
        obj.setActualQuery("அகத்தியர் falls");
        obj.setLanguage("en");

        CoreeObject objx = new QueryProcessing().processQuery(obj);
        System.out.println("QE Actual Qry:" + objx.getActualQuery());
        System.out.println("QE translated:" + objx.getTamilTranslatedConcepts());
        System.out.println("QE Modified Qry:" + objx.getModifiedQuery());
        System.out.println("QE Ta Concept:" + objx.getTamilConcepts());
        System.out.println("QE MW:" + objx.getMultiwords());
        System.out.println("QE Ea Concept:" + objx.getEnglishConcepts());
        System.out.println("QE AND:" + objx.getAndConcepts_ta());
        System.out.println("QE Quotes:" + objx.getDoubleQuotesConcepts_ta());


        //new QueryProcessing().processQuery(core);				
        //core.setActualQuery("america செட்டிநாடு+உணவுவகைகள்+அகத்தியர் அருவி apple \"செட்டிநாடு உணவுவகைகள்\" apple+fruit");
        //core.setLanguage("en");
        //new QueryProcessing().processQuery(core);

    }
}
