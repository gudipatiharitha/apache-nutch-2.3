package org.apache.nutch.analysis.unl.ta.Integrated;

import java.util.ArrayList;

/**
 * @version 1.0 @category Query Language Identifier.
 * @author Tamil Computing Lab.
 */
class LanguageIdentifier {

    // To decide whether the query wants multiword check.
    private ArrayList<Integer> lnPattern;

    /**
     * @return the lnPattern
     */
    public ArrayList<Integer> getLnPattern() {
        return lnPattern;
    }

    /**
     * @param lnPattern the lnPattern to set
     */
    public void setLnPattern(ArrayList<Integer> lnPattern) {
        this.lnPattern = lnPattern;
    }

    /**
     * Language Identifier. In lnPattern 0-English & 1-Tamil
     *
     * @param word
     * @return
     */
    public String getLanguage(String word) {
        lnPattern = new ArrayList<Integer>();
        StringBuffer strBuffer = new StringBuffer();
        ////System.out.println("getLanguage"+word);
        String language = "ta";
        word = word + " "; // to Split based on space, to get the last word, White Space Appended.
        for (int i = 0; i <= word.length() - 1; i++) {
            int c = word.charAt(i);
            if (c == '+' || c == '\"' || c == ' ') {
                continue;
            }
            if ((c <= 122 && c >= 97) || (c >= 65 && c <= 90)) { // To Filter English Language					 
                language = "en";
                ////System.out.println("En:"+(char)c);
            } else {  // To Filter Tamil Language
                ////System.out.println("Ta:"+(char)c);
                language = "ta";
            }
        }
        ////System.out.println("Word :"+word+"\tLanguage:"+language);
        return language;
    }

    /**
     * Language Identifier. In lnPattern 0-English & 1-Tamil
     *
     * @param word
     * @return
     */
    public ArrayList<LangIdentifierObject> identifyLanguage(String word) {
        lnPattern = new ArrayList<Integer>();
        int lnCode = 0;
        ArrayList<LangIdentifierObject> queryConcept = new ArrayList<LangIdentifierObject>();
        int conceptCounter = 0;
        StringBuffer strBuffer = new StringBuffer();
        LangIdentifierObject identifierObject = new LangIdentifierObject();
        word = word + " "; // to Split based on space, to get the last word, White Space Appended.
        for (int i = 0; i <= word.length() - 1; i++) {
            int c = word.charAt(i);

            if (c != 32) { // 32 Denotes White Space.
                if ((c <= 122 && c >= 97) || (c >= 65 && c <= 90)) { // To Filter English Language
                    ////System.out.println("Eng:"+(char)c);
                    identifierObject.setLanguage("en"); // ln patter for english					 
                    lnCode = 0;
                    strBuffer.append((char) c);
                } else {  // To Filter Tamil Language
                    ////System.out.println("Tam:"+(char)c);
                    identifierObject.setLanguage("ta"); // ln patter for tamil					 
                    lnCode = 1;
                    strBuffer.append((char) c);
                }
            } else {
                conceptCounter++; // Concept Counter
                lnPattern.add(lnCode);
                identifierObject.setPosition(conceptCounter);
                identifierObject.setWord(strBuffer.toString());
                queryConcept.add(identifierObject); // Adding objecrt in to the ArrayList
                ////System.out.println("Tam:"+strBuffer.toString());
                strBuffer = new StringBuffer();  // to clear String Buffer
                identifierObject = new LangIdentifierObject(); // to create new object.
            }
        }
        return queryConcept;
    }

    /**
     * to identify the language code to process multiword
     */
    /**
     * Language Identifier. In lnPattern 0-English & 1-Tamil
     *
     * @param word
     * @return
     */
    public ArrayList<Integer> identifyLanguage_MW(String word) {
        lnPattern = new ArrayList<Integer>();
        int lnCode = 0;
        int conceptCounter = 0;
        for (int i = 0; i <= word.length() - 1; i++) {
            int c = word.charAt(i);
            if (c != 32) { // 32 Denotes White Space.
                if ((c <= 122 && c >= 97) || (c >= 65 && c <= 90)) { // To Filter English Language
                    ////System.out.println("Eng:"+(char)c);							 
                    lnCode = 0;
                } else {  // To Filter Tamil Language
                    ////System.out.println("Tam:"+(char)c);					 					
                    lnCode = 1;
                }
            } else {
                conceptCounter++; // Concept Counter
                lnPattern.add(lnCode);
                ////System.out.println("Tam:"+strBuffer.toString());		 		
            }
        }
        return lnPattern;
    }

    /**
     * Language Identifier Pattern to check whether the Tamil Query word is
     * Multiword or not. It 0 to english and 1 to tamil words
     *
     * @param lnPattern
     * @param lnIdentifierResult
     * @return
     */
    public ArrayList<String> multiwordPatternCheck(ArrayList<Integer> lnPattern, ArrayList<LangIdentifierObject> lnIdentifierResult) {
        ArrayList<String> multiwordCheck = new ArrayList<String>();
        ////System.out.println("Size"+lnPattern.size());		 				
        int wordCounter = 0;
        int taWordCounter = 0;
        String checkMultiword = "";
        for (Integer i : lnPattern) {
            wordCounter++;
            ////System.out.println(i);
            if (i == 1) {
                taWordCounter++;
                //checkMultiword+="a"+" ";
                checkMultiword += lnIdentifierResult.get(wordCounter - 1).getWord() + " ";
                if (wordCounter == lnPattern.size()) {
                    ////System.out.println("1.Multiword"+checkMultiword);
                    multiwordCheck.add(checkMultiword);
                }
            } else {
                if (taWordCounter > 1) {
                    ////System.out.println("2.Multiword"+checkMultiword);
                    multiwordCheck.add(checkMultiword);
                }
                taWordCounter = 0;
                checkMultiword = "";
            }
        }

        return multiwordCheck;
    }
    /**
     * Single ton class
     */
    private static LanguageIdentifier lnidentifierobject = null;

    public static LanguageIdentifier instanceOf() {
        if (lnidentifierobject == null) {
            lnidentifierobject = new LanguageIdentifier();
        }
        return lnidentifierobject;
    }

    /**
     * Main Method.
     *
     * @param args
     */
    public static void main(String args[]) {

        LanguageIdentifier identifier = new LanguageIdentifier();

        ArrayList<LangIdentifierObject> temp = identifier.identifyLanguage("do அறம் விருப்பு விருப்பு do do do விருப்பு விருப்பு like விருப்பு விருப்பு விருப்பு விருப்பு like");
        for (LangIdentifierObject obj : temp) {
            //System.out.println("temp:"+obj.getPosition()+"--"+obj.getWord()+"--"+obj.getLanguage());
        }
        ArrayList<Integer> lnPattern = new LanguageIdentifier().identifyLanguage_MW("do அறம் விருப்பு விருப்பு do do do விருப்பு விருப்பு like விருப்பு விருப்பு விருப்பு விருப்பு like");
        ArrayList<String> objv = identifier.multiwordPatternCheck(lnPattern, temp);

        for (String obj : objv) {
            //System.out.println("temp:"+obj);
        }
    }
}
