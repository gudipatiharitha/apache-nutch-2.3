package org.apache.nutch.analysis.unl.ta.Integrated;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.TreeSet;
import org.apache.nutch.analysis.unl.ta.*;
import static org.apache.nutch.analysis.unl.ta.Integrated.Symbols.DICT_LIST_PATH;
import static org.apache.nutch.analysis.unl.ta.Integrated.Symbols.DICT_PATH;

public class EnglishQueryGraph {

    public ArrayList<String> singleWordConcept;
    public FreqSort freqsrt;
    public ArrayList arl_TempQW = new ArrayList();
    int QW_count;
    CRC crc;
    Rules qt;
    public static org.apache.nutch.analysis.unl.ta.FinalLLImpl[] ll;
    public QueryExpansion_iof_ta iof;
    //public static QueryExpansionUNL expansion = new QueryExpansionUNL();

    /**
     * QueryTranslation Constructor
     */
    public EnglishQueryGraph() {
        crc = new CRC();
        qt = new Rules();
        freqsrt = new FreqSort();
        arl_TempQW = new ArrayList();
        ll = new org.apache.nutch.analysis.unl.ta.FinalLLImpl[3];
        singleWordConcept = new ArrayList<String>();
        QW_count = 0;
        iof = QueryExpansion_iof_ta.instance();
    }

    /**
     * to translate the expanded query word to UW Concept and populate those UW
     * concepts in to Multilist
     *
     * @param expquery Query word with expanded word
     */
    public void translateAll(CoreeObject core, boolean onlineprocess) {
        String transoutForIclIof = "";
        String trans_out_ActualQry = "";
        try {
            String actualqueryword = "";
            //core  = constuctGraph(core);
            int position = 1;
            for (LangIdentifierObject object : core.getLnIdentifierResult()) {
                String modifiedencon = "";
                if (object.getLanguage().equals("en")) {
                    actualqueryword += getQueryWordInformation(object.getWord(), position) + "#";
                    //System.out.println(":::"+actualqueryword);
                } else {
                    System.out.println("Word Debugging:" + object.getWord());
                    String sample = qt.enconvert(object.getWord(), onlineprocess);
                    System.out.println("Debugging:" + sample);
                    sample = sample.substring(sample.indexOf("[w]#") + 4, sample.indexOf("[/w]#"));
                    if (sample.length() > 1) {
                        sample = sample.replace("#", "");
                        String[] splitwords = sample.split(";");
                        if (splitwords.length > 0) {
                            for (int z = 0; z < splitwords.length; z++) {
                                if (z == 0) {
                                    continue;
                                } else if (z == 1) {
                                    modifiedencon += splitwords[z] + ";" + splitwords[z] + ";";
                                    continue;
                                }
                                modifiedencon += splitwords[z] + ";";
                            }
                        }
                    }
                    actualqueryword += modifiedencon + "#";
                    //actualqueryword += sample+"#";
                    //System.out.println(":::"+actualqueryword);
                }
                position++;
            }
            //System.out.println("Actual QueryWord:"+actualqueryword);
            core.setEnglishConceptGraph(actualqueryword);

            //String trans_out_ActualQry = core.getEnglishConceptGraph(); 
            //System.out.println("Enslish Translate Query:"+core.getEnglishConceptGraph());

            //icl and iof combination
            if (actualqueryword.contains(";")) {
                transoutForIclIof = core.getEnglishConceptGraph(); // transoutForIclIof to process the enconverted output for ICL IOF Combination.		           
            }

            //construct graph [0]
            trans_out_ActualQry = "[s]#[w]#" + core.getEnglishConceptGraph() + "[/w]#[r]#[/r]#[/s]#";
            core = constructActualQryGraph(trans_out_ActualQry, core);// populate the all the Actual Query word concepts into the multilist[0]
            // System.out.println("Graph constructed on [0]..."+core.getEnglishConcepts());

            /**
             * To Filter the Expanded Query Word based on Tourism Domine
             */
            ArrayList getAllExpansion = new ArrayList();
            for (String constraint : core.getEnglishConcepts()) {
                ArrayList getExpansion = new ArrayList();
                String concept = constraint;
                int hashcode = concept.hashCode();

                //System.out.println("Concept:"+concept);
                //System.out.println("hashvalue:"+hashcode);	            	 	            

                if (hashcode != 0) {
                    getExpansion = QueryExpansion_crcindex_ta.instanceOf().processUNLMW(new Integer(hashcode));// to get the Expansion for the Query word
                    //getExpansion = expansion.processUNLMW(new Integer(getHashcode),"");	                    
                    getAllExpansion.addAll(getExpansion);
                    /*
                     * for(Object o : getExpansion){ CRC c = (CRC) o;
                     * System.out.println("==="+c.c1 + "-" + c.tam1 + "-" + c.c2
                     * + "-" + c.tam2); }
                     */
                    singleWordConcept.add(concept);// to avoid the duplicate concepts in the multilist.
                }//if


            }
            //System.out.println("--->"+getAllExpansion.size());
            //ArrayList getAllExpansion = getQueryExpansion(core);

            ArrayList getNounEntity = new ArrayList();
            for (Object o : getAllExpansion) {
                CRC c = (CRC) o;
                // to get the tourism oriented concepts.
                if (!c.c1.contains("icl>action") && !c.c1.contains("agt>") && !c.c1.contains("obj>") && !c.c1.contains("mod>") && !c.c1.contains("aoj>") && !c.c1.contains("mod<")) {
                    if (!c.c2.contains("icl>action") && !c.c2.contains("agt>") && !c.c2.contains("obj>") && !c.c2.contains("mod>") && !c.c2.contains("aoj>") && !c.c2.contains("mod<")) {
                        getNounEntity.add(c);
                        ////System.out.println("Noun Test"+c.c1 + "*" + c.tam1 + "*" + c.c2 + "*" + c.tam2);	
                    }
                }
            }

            /*
             * for (Object ob : getNounEntity) { CRC c = (CRC) ob;
             * ////System.out.println("***"+ c.c1 + "-" + c.tam1 + "-" + c.c2 +
             * "-" + c.tam2); }
             */

            ArrayList sortedArylis = freqsort.getObjArray(getNounEntity);// sorting based on frequeny count across the document
            //System.out.println("Result*****:"+sortedArylis.size());


            //System.out.println("Expansion Graph going to construct...");
            constructExpansionGraph(sortedArylis, 1, singleWordConcept);//graph construct for Expansion in multilist[1]
            //constructExpansionGraph(sortedArylis, 1, singleWordConcept);//graph construct for Expansion in multilist[1]


            /*
             * for(Object ob:getNounEntity){ CRC c=(CRC)ob;
             * System.out.println("***"+ c.c1 + "-" + c.tam1 + "-" + c.c2 + "-"
             * + c.tam2); }
             */


            //ArrayList getIclIofExpansion = CombinationIclIof(transoutForIclIof);// to check UWConcept has iof + icl or One & Only Icl
            //constructExpansionGraph(getIclIofExpansion, 2, core.getEn_headWord());//graph construct for IclIof Expansion in multilist[2]
            //System.out.println("Debugging:ICLIOF Completed");

            for (int i = 0; i < 3; i++) {
                HeadNode h1 = new HeadNode();
                h1 = ll[i].head;
                ConceptNode c11 = new ConceptNode();
                c11 = h1.colnext;

                ConceptToNode c22 = new ConceptToNode();
                while (c11 != null) {
                    c22 = c11.rownext;
                    System.out.println("concept in Miltilist[" + i + "] :" + c11.gn_word + "\t" + c11.uwconcept + "\t" + c11.queryTag);
                    if (c22 != null) {
                        while (c22 != null) {
                            //System.out.println("rownext id"+c22.uwtoconcept);
                            c22 = c22.rownext;
                        }
                    }
                    //System.out.println("\n");
                    c11 = c11.colnext;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Multiuword checker
     *
     * @param g_word is user Query Word
     * @return MW if Query Word is Multiword else return empty String (" ")
     */
    private String chkMultiword(String g_word) {
        String MWtag_Qw = "";
        String[] QW_MWCheck = g_word.split(" ");
        int QW_MWCheckCnt = QW_MWCheck.length;
        if (QW_MWCheckCnt >= 2) {
            MWtag_Qw = "MW";
        } else {
            MWtag_Qw = "";
            ////System.out.println("Not Multiword"+MWtag_Qw);
        }
        return MWtag_Qw;
    }

    /**
     * To populate the Actual Query Word into the Multiword
     */
    private CoreeObject constructActualQryGraph(String translatedOP, CoreeObject core) {
        //String transOutx = "[s]#[w]#ஷாஜகான்;shajahan;icl>name                        ;Entity;1#தாஜ்மஹால்;tajmahal;icl>building                        ;Entity;2#[/w]#[r]#2    pos    1#[/r]#[/s]# & [s]#[w]#எகிப்து;egypt;iof>country                        ;Entity;1#[/w]#[r]#[/r]#[/s]# & [s]#[w]#தனுஷ்கோடி;dhanushkodi;iof>place                        ;Entity;1#[/w]#[r]#[/r]#[/s]# ";
        try {
            EnglishQueryGraph.ll[0] = new FinalLLImpl();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String relnGraph = "";
        String splitEncovOP[] = translatedOP.split("&");//

        // to get the uwconcept for query expansion
        ArrayList<String> UWConcepts = new ArrayList<String>();

        for (String transOut : splitEncovOP) {
            //System.out.println("--->"+transOut);
            int position = 1;
            ArrayList<String> getConcept = new ArrayList<String>();
            TreeSet<String> getReln = new TreeSet<String>();

            String splitTransOut[] = transOut.split("#");
            while (splitTransOut.length > position) {
                ////System.out.println("split length :"+splitTransOut.length);
                if (splitTransOut[position].contains("[w]")) {                        //to get the concepts in the translated Output.                    
                    while (!splitTransOut[++position].contains("[/w]")) {
                        getConcept.add(splitTransOut[position]);
                    }
                } else if (splitTransOut[position].contains("[r]")) {                  //to get the relation in the translated Output.                                                        
                    while (!splitTransOut[++position].contains("[/r]")) {
                        getReln.add(splitTransOut[position]);
                    }
                }
                position++;
            }

            // if there is any relation between the concepts below code to add huristic relation
            if (transOut.contains("2#") && getReln.size() == 0) {                                            //to check for more than 1 concept.
                //if (getReln.size() == 0) {                    
                for (int j = 1; j <= getConcept.size() - 1; j++) {                  //Array list started from 0, set Size -1 and start from 0
                    int dupJval = j;
                    if (getConcept.get(j).contains("mod<thing")) {
                        //relnGraph += (dupJval + 1) + "	" + "mod" + "	" + (dupJval) + "#";     // to set mod relation huristically
                        relnGraph = (dupJval + 1) + "	" + "mod" + "	" + (dupJval) + "#";     // to set mod relation huristically
                        getReln.add(relnGraph);
                    } else {
                        //relnGraph += (dupJval + 1) + "	" + "pos" + "	" + (dupJval) + "#";     // to set mod relation huristically
                        relnGraph = (dupJval + 1) + "	" + "pos" + "	" + (dupJval) + "#";     // to set mod relation huristically
                        getReln.add(relnGraph);
                    }
                }
            }


            ArrayList tamilCon = new ArrayList();   // to avoid duplicates


            int sentenceId = 0;
            for (String conInfo : getConcept) {
                String[] getConInfo = conInfo.split(";");

                System.out.println("Debugging Size:" + getConInfo.length);
                if (!tamilCon.contains(getConInfo[0]) && getConInfo.length > 1) {
                    // for English
                    System.out.println("Debugging:" + getConInfo[0] + "\t" + getConInfo[1] + "(" + getConInfo[2] + ")");
                    EnglishQueryGraph.ll[0].addConcept(getConInfo[0], getConInfo[1] + "(" + getConInfo[2] + ")", String.valueOf(++sentenceId), "1", "1", getConInfo[3], "", "", "", "", "", getQueryTag(getConInfo[0], core), chkMultiword(getConInfo[0])); // to add concepts in to the multilist
                    UWConcepts.add(getConInfo[1] + "(" + getConInfo[2] + ")");
                    ////System.out.println("=-->"+getConInfo[0]+"   "+ getConInfo[1] +" "+"(" + getConInfo[0] + ")"+"   "+ String.valueOf(sentenceId)+"   "+ getConInfo[3] +" "+"QW"+"   *** "+ chkMultiword(getConInfo[0]));
                    tamilCon.add(getConInfo[0]);
                }
            }

            if (getReln.size() > 0) {                                           //to add reln into the ll list
                for (String relId : getReln) {
                    ////System.out.println("relation id"+relId);
                    relId = relId.replaceAll("#", "");
                    ////System.out.println("relation id"+relId);
                    String[] relnInfo = relId.split("	");
                    ////System.out.println("Size:"+getReln.size()+"  "+ relId +"\t single concept"+relnInfo[0]);
                    EnglishQueryGraph.ll[0].addRelation(relnInfo[1]);
                    ////System.out.println("----->"+relnInfo[0]+" "+relnInfo[2]+" "+ relnInfo[1]);
                    ConceptToNode cn = EnglishQueryGraph.ll[0].addCT_Concept(relnInfo[0], relnInfo[2], relnInfo[1], "1", "1");
                    EnglishQueryGraph.ll[0].addCT_Relation(cn);
                }
            }
        }
        core.setEnglishConcepts(UWConcepts);
        return core;
    }

    /**
     * to get the Query tage whether Tag is AQW or MW
     *
     * @param word
     * @return
     */
    private String getQueryTag(String word, CoreeObject core) {
        //System.out.println("Core"+core.getAndConcepts_ta());
        //System.out.println("Core"+core.getDoubleQuotesConcepts_ta());
        String[] temp = word.split(" ");
        if (temp.length > 1) {
            return "MW";
        } else if (core.getAndConcepts_ta().toString().contains(word)) {
            int i = 1;
            String tag = "";
            for (String str : core.getAndConcepts_ta()) {
                //System.out.println(">>"+str +"Word:"+word);
                if (str.contains(word)) {
                    tag = "AND" + i;
                }
                i++;
            }
            //System.out.println(">>"+tag);
            return tag;
        } else if (core.getAndConcepts_en().toString().contains(word)) {
            int i = 1;
            String tag = "";
            for (String str : core.getAndConcepts_en()) {
                //System.out.println(">>"+str +"Word:"+word);
                if (str.contains(word)) {
                    tag = "AND" + i;
                }
                i++;
            }
            //System.out.println(">>"+tag);
            return tag;
        } else if (core.getDoubleQuotesConcepts_ta().toString().contains(word)) {
            int i = 1;
            String tag = "";
            for (String str : core.getDoubleQuotesConcepts_ta()) {
                //System.out.println(">>"+str +"Word:"+word);
                if (str.contains(word)) {
                    tag = "QTS" + i;
                }
                i++;
            }
            //System.out.println(">>"+tag);
            return tag;
            //return "QTS";
        } else if (core.getDoubleQuotesConcepts_en().toString().contains(word)) {
            int i = 1;
            String tag = "";
            for (String str : core.getDoubleQuotesConcepts_en()) {
                //System.out.println(">>"+str +"Word:"+word);
                if (str.contains(word)) {
                    tag = "QTS" + i;
                }
                i++;
            }
            //System.out.println(">>"+tag);
            return tag;
            //return "QTS";
        } else {
            return "QW";
        }
    }

    /**
     * To Populate the CRC from Index into the multilist ll[1]
     *
     * @param Inp is CRC object in ArrayList
     * @throws java.lang.Exception
     */
    private void constructExpansionGraph(ArrayList Expansion, int ls, ArrayList singleWordConcept) throws Exception {

        ArrayList getTamilCon = new ArrayList();
        String multiwordTag = "";
        int conceptcount = 0;
        ArrayList toconcept = new ArrayList();
        ArrayList conceptids = new ArrayList();
        String frmconceptid = "";
        String toconceptid = "";
        int flag = 0;
        try {
            ll[ls] = new FinalLLImpl();
            //System.out.println("Single wrd concept:"+ls);
            //System.out.println("Single wrd concept:"+Expansion.size());
            for (Object getExpansionObj : Expansion) {
                CRC c = (CRC) getExpansionObj;
                //System.out.println("Single wrd concept:"+(String) c.c1);
                getTamilCon.add((String) c.c1);
                //multiwordTag = chkMultiword((String) c.c1);
                multiwordTag = "";

                conceptcount++;
                frmconceptid = String.valueOf(conceptcount);

                //System.out.println("Single wrd concept:"+frmconceptid);
                if (singleWordConcept.contains((String) c.c1)) {
                    ll[ls].addConcept((String) c.c1, (String) c.c1, frmconceptid, "1", "1", (String) c.pos1, "", "", "", "", "", "QW", multiwordTag);//tagmw,MWtag_Qw
                } else {
                    ll[ls].addConcept((String) c.c1, (String) c.c1, frmconceptid, "1", "1", (String) c.pos1, "", "", "", "", "", "EQW", multiwordTag);//tagmw,MWtag_Qw
                }


                multiwordTag = chkMultiword((String) c.c2);   // multiword check

                if (!toconcept.contains((String) c.c2)) {
                    conceptcount++;
                    toconceptid = String.valueOf(conceptcount);

                    if (singleWordConcept.contains((String) c.c2)) {
                        ll[ls].addConcept((String) c.c2, (String) c.c2, toconceptid, "1", "1", (String) c.pos2, "", "", "", "", "", "QW", multiwordTag);//tagsw+EQWCnt,""
                    } else {
                        ll[ls].addConcept((String) c.c2, (String) c.c2, toconceptid, "1", "1", (String) c.pos2, "", "", "", "", "", "CQW", multiwordTag);//tagsw+EQWCnt,""
                    }
                    toconcept.add((String) c.c2);
                    conceptids.add(toconceptid);
                    flag = 1;
                }


                if (flag == 1) {
                    int ind = toconcept.indexOf((String) c.c2);
                    String tocon = conceptids.get(ind).toString();
                    String conceptfrm = frmconceptid;
                    ConceptToNode cn = new ConceptToNode();
                    cn = ll[ls].addCT_Concept(conceptfrm, tocon, (String) c.rel, "1", "1");
                    ll[ls].addCT_Relation(cn);
                } else {
                    String conceptfrm = frmconceptid;
                    String conceptto = toconceptid;
                    ConceptToNode cn = new ConceptToNode();
                    cn = ll[ls].addCT_Concept(conceptfrm, conceptto, (String) c.rel, "1", "1");
                    ll[ls].addCT_Relation(cn);
                }
            }
        } catch (Exception e) {
        }
    }

    //old version
    private ArrayList CombinationIclIof(String translatedOP) {

        String[] getTransCon = translatedOP.split("#");
        //System.out.println("length :" + getTransCon.length);
        ArrayList<String> iofExpansion = new ArrayList<String>();
        ArrayList getIofExpansion = new ArrayList();


        for (int i = 0; i < getTransCon.length; i++) {
            iof = new QueryExpansion_iof_ta().instance();
            if (getTransCon.length > 1 && ((i + 1) < getTransCon.length)) {
                //கன்னியாகுமரி;kanniyakumari;iof>place;Entity;1                
                if (getTransCon[i].contains("iof>") && getTransCon[i + 1].contains("icl>")) {
                    String[] toConceptInfo = getTransCon[i + 1].split(";");
                    String getSuperClasConcept = toConceptInfo[1].toString().trim();
                    String iofConstrain = "(iof>" + getSuperClasConcept + ")";
                    //System.out.println("constraint:"+iofConstrain);

                    // to get the IOF information (idly etc ...)
                    iofExpansion = iof.processSuper(iofConstrain.trim());

                    if (iofExpansion.size() <= 0) {
                        String getSuperClasConcept1 = toConceptInfo[2].substring(toConceptInfo[2].indexOf(">") + 1, toConceptInfo[2].length());
                        iofConstrain = "(iof>" + getSuperClasConcept1 + ")";
                        iofExpansion = iof.processSuper(iofConstrain.trim());
                    }

                    for (String frmconcept : iofExpansion) {
                        String Temp_iof1[] = getTransCon[i].split(";");
                        String Temp_iof2[] = frmconcept.split(";");

                        crc = new CRC();
                        crc.tam1 = Temp_iof2[0];
                        crc.c1 = Temp_iof2[1] + "(" + Temp_iof2[2] + ")";
                        crc.pos1 = Temp_iof2[3];
                        crc.rel = "None";
                        crc.tam2 = Temp_iof1[0];
                        crc.c2 = Temp_iof1[1] + "(" + Temp_iof1[2] + ")";
                        crc.pos2 = Temp_iof1[3];
                        getIofExpansion.add(crc);
                    }//for*/
                }
            } else {
                ////System.out.println("i'm in else part " + getTransCon[i] + " " + getTransCon.length);
                if (getTransCon[i].contains("icl>") && getTransCon.length == 1) {

                    String[] toConceptInfo = getTransCon[i].split(";");
                    String getSuperClasConcept = toConceptInfo[1].toString().trim();
                    String iofConstrain = "(iof>" + getSuperClasConcept + ")";
                    //System.out.println("iofConstrain " + iofConstrain);

                    iofExpansion = iof.processSuper(iofConstrain.trim());
                    if (iofExpansion.size() <= 0) {
                        String getSuperClasConcept1 = toConceptInfo[2].substring(toConceptInfo[2].indexOf(">") + 1, toConceptInfo[2].length());
                        iofConstrain = "(iof>" + getSuperClasConcept1 + ")";
                        iofExpansion = iof.processSuper(iofConstrain.trim());
                    }

                    for (String frmconcept : iofExpansion) {
                        String Temp_iof1[] = getTransCon[i].split(";");
                        String Temp_iof2[] = frmconcept.split(";");
                        crc = new CRC();
                        crc.tam1 = Temp_iof2[0];
                        crc.c1 = Temp_iof2[1] + "(" + Temp_iof2[2] + ")";
                        crc.pos1 = Temp_iof2[3];
                        crc.rel = "None";
                        crc.tam2 = Temp_iof1[0];
                        crc.c2 = Temp_iof1[1] + "(" + Temp_iof1[2] + ")";
                        crc.pos2 = Temp_iof1[3];
                        getIofExpansion.add(crc);
                    }
                }
            }//else// if len > i+1
        }// for

        return getIofExpansion;
    }

    /**
     * @return multilist ll[0] ll[1] and ll[2] to search
     */
    public static FinalLLImpl[] multilist_UNLQuery() {
        return (ll);
    }
    public static Hashtable<String, ArrayList<String>> uwWords = new Hashtable<String, ArrayList<String>>();
    public static Hashtable<String, ArrayList<String>> POS = new Hashtable<String, ArrayList<String>>();
    public static boolean isuwWordsLoaded = false;
    public static ArrayList<String> temp = new ArrayList<String>();
    public static FreqSort freqsort = new FreqSort();

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

    /**
     * to get the Query Word info's POS, UNL constraint and position
     *
     * @param word
     * @param position
     * @return
     */
    public String getQueryWordInformation(String word, int position) {
        //System.out.println("Im in Eng Graph");
        String output = "";
        try {
            String unlconstraint = getUNLKB(word);
            String pos = getPOS(word);
            if (pos.equals("uwdict")) {
                pos = "Entity";
            }
            output = word + ";" + word + ";" + unlconstraint + ";" + pos + ";" + position;


        } catch (Exception e) {
        }
        //System.out.println("[s]#[w]#" + graph + "[/w]#[r]#[/r]#[/s]#");
        //return "[s]#[w]#" + graph + "[/w]#[r]#[/r]#[/s]#";	        	        
        return output;
    }

    public CoreeObject constuctGraph(CoreeObject core) {
        //System.out.println("Im in Eng Graph");
        ArrayList<EnglishConceptObject> concept = new ArrayList<EnglishConceptObject>();
        String graph = "";
        int i = 1;
        EnglishConceptObject enobj;
        try {
            String[] querySplit = core.getModifiedQuery().split(" ");
            for (String word : querySplit) {
                enobj = new EnglishConceptObject();
                String unlconstraint = getUNLKB(word);
                String pos = getPOS(word);
                if (pos.equals("uwdict")) {
                    pos = "Entity";
                }
                String output = word + ";" + word + ";" + unlconstraint + ";" + pos + ";" + i;
                enobj.setHeadword(word);
                enobj.setUwword(unlconstraint);
                enobj.setPos(pos);
                enobj.setConceptid(i);
                concept.add(enobj);
                //String output = word + ";" + word + ";" + getUNLKB(word) + ";" + getPOS(word) + ";" + i;
                graph += output + "#";
                //graph +=graph+"#";
                i++;
            }
        } catch (Exception e) {
        }
        //System.out.println("[s]#[w]#" + graph + "[/w]#[r]#[/r]#[/s]#");
        //return "[s]#[w]#" + graph + "[/w]#[r]#[/r]#[/s]#";
        core.setEnglishConceptInfo(concept);
        core.setEnglishConceptGraph(graph);
        return core;
    }

    /*
     * public String constuctGraph(CoreeObject obj) { String graph = ""; try {
     * for (int i = 0; i < obj.getEnglishConcepts().size(); i++) { String output
     * = obj.getEnglishConcepts().get(i) + ";" + obj.getEnglishConcepts().get(i)
     * + ";" + getUNLKB(obj.getEnglishConcepts().get(i)) + ";" +
     * getPOS(obj.getEnglishConcepts().get(i)) + ";" + (i + 1); graph += output
     * + "#"; //graph +=graph+"#"; } } catch (Exception e) { }
     * System.out.println("[s]#[w]#" + graph + "[/w]#[r]#[/r]#[/s]#"); return
     * "[s]#[w]#" + graph + "[/w]#[r]#[/r]#[/s]#"; }
     */
    public static void main(String args[]) {
        //ஆமெரிக்கா செட்டிநாடு உணவுவகைகள் அகத்தியர் அருவி ஆப்பிள் செட்டிநாடு உணவுவகைகள்
        CoreeObject core = new CoreeObject();
        core.setActualQuery("america and apple அருவி order roll");
        core.setModifiedQuery("america and apple order roll");
        core.setLanguage("en");
        new EnglishQueryGraph().translateAll(core, true);
    }
}
