package org.apache.nutch.analysis.unl.ta.Integrated;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;


//import org.apache.nutch.search.unl.database.QueryExpansion_DB;
//import org.apache.nutch.analysis.unl.ta.CRC;

import org.apache.nutch.analysis.unl.ta.*;

public class TamilQueryGraph {

    public ArrayList<String> singleWordConcept;
    public FreqSort freqsrt;
    public ArrayList arl_TempQW = new ArrayList();
    int QW_count;
    CRC crc;
    Rules qt;
    //public org.apache.nutch.analysis.unl.ta.FinalLLImpl[] ll;
    public QueryExpansion_iof_ta iof;
//    public static QueryExpansionUNL expansion = new QueryExpansionUNL();

    /**
     * QueryTranslation Constructor
     */
    public TamilQueryGraph() {
        crc = new CRC();
        qt = new Rules();
        freqsrt = new FreqSort();
        arl_TempQW = new ArrayList();
        // ll = new org.apache.nutch.analysis.unl.ta.FinalLLImpl[3];        
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
    public FinalLLImpl[] translateAll(CoreeObject core, boolean onlineprocess) {
        ////////System.out.println("Query Word:"+expquery);
        FinalLLImpl[] ll = new FinalLLImpl[3];
        String trans_out = "";
        String transoutForIclIof = "";
        try {
            String actualQueryword = core.getTamilTranslatedConcepts();// to get the actual Query word from QueryExpansion output
            //////System.out.println("Actual Query Word:"+actualQueryword);            
            trans_out = qt.enconvert(actualQueryword, onlineprocess);//to enconvert Actual Query Word.            	    
            ////System.out.println("Output:"+trans_out);   	
            //transoutForIclIof = trans_out.substring(trans_out.indexOf("[w]#") + 4, trans_out.indexOf("#[/w]#")); // transoutForIclIof to process the enconverted 			output for ICL IOF Combination.
            //////System.out.println("Output transoutForIclIof:"+transoutForIclIof);   	
            ////////System.out.println("Transout :"+trans_out.substring(trans_out.indexOf("[w]#")+4,trans_out.indexOf("#[/w]#")));

            //to enconver multiword to single concept	
            String multiword = "";
            if (core.getMultiwords().size() > 0) {
                for (String mwword : core.getMultiwords()) {
                    //////System.out.println("Multiword:"+mwword);
                    String getSingleCon[] = mwword.split(" ");// split multiwords to single concepts
                    for (String getSWord : getSingleCon) { // enconvert the single concept
                        //////System.out.println("getSWord:"+getSWord);
                        if (getSWord.length() > 0) {
                            trans_out = trans_out + "&" + qt.enconvert(getSWord, onlineprocess); // concordinate both actual query word with each concepts in multiword concept.
                        }
                    }
                }
            }
           // //System.out.println(">>|"+ trans_out);
            	/*
             * if(core.getMultiwords().size() > 0){ for(String mwword :
             * core.getMultiwords()){ multiword += mwword+"%"; }
             * //////System.out.println("multiword:"+multiword); String
             * multiwordlist[] = multiword.split("%"); for (String getMWord :
             * multiwordlist) { String getSingleCon[] = getMWord.split(" ");//
             * split multiwords to single concepts for (String getSWord :
             * getSingleCon) { // enconvert the single concept if
             * (getSWord.length() > 0) { trans_out = trans_out + "&" +
             * qt.enconvert(getSWord); // concordinate both actual query word
             * with each concepts in multiword concept. } } } }
             */



            ll[0] = constructActualQryGraph(trans_out, core);// populate the all the Actual Query word concepts into the multilist[0]
            //////System.out.println(">>>>>>>"+ll[0]);
	  /*
             * ArrayList getAllExpansion = new ArrayList(); for(String id :
             * core.getUwconceptid()){ //////System.out.println("****"+id);
             * getAllExpansion = new QueryExpansion_DB().getQueryExpansion(id);
             * }
             */

            //ll[1] = constructExpansionGraph(getAllExpansion, 1, core.getTamilConcepts());//graph construct for Expansion in multilist[1]
            //graphconstruct(trans_out);            
            //debug tool
            // to get the single word tamil concept from Query Expansion Output    	          
            //String singleWord = expquery.substring(expquery.indexOf("[") + 1, expquery.indexOf("]"));


            //String singleWord = core.getTamilTranslatedConcepts();            
            //String singleWordArry[] = singleWord.split(" ");
            //////System.out.println("Single Concept:"+ singleWord.toString());
            //ArrayList<CRC> getAllExpansion = new ArrayList<CRC>(); // to store all the Expansion for each Query Word.            	    
            /*
             * for(String getSWord : core.getTamilConcepts()){ //for (String
             * getSWord : singleWordArry) { //////System.out.println("Expansion
             * word"+ getSWord); //ArrayList<CRC> temp = new
             * QueryExpansion_DB().getQueryexpansion(getSWord); ArrayList<CRC>
             * temp = new
             * QueryExpansion_DB().getQueryexpansion(getSWord,session,transaction);
             * getAllExpansion.addAll(temp); }//for	*
             *
             * /**
             * To Filter the Expanded Query Word based on Tourism Domine
             */
            /*
             * ArrayList getNounEntity = new ArrayList(); for (Object o :
             * getAllExpansion) { CRC c = (CRC) o; // to get the tourism
             * oriented concepts. if (!c.getC1().contains("icl>action") &&
             * !c.getC1().contains("agt>") && !c.getC1().contains("obj>") &&
             * !c.getC1().contains("mod>") && !c.getC1().contains("aoj>") &&
             * !c.getC1().contains("mod<")) { if
             * (!c.getC2().contains("icl>action") && !c.getC2().contains("agt>")
             * && !c.getC2().contains("obj>") && !c.getC2().contains("mod>") &&
             * !c.getC2().contains("aoj>") && !c.getC2().contains("mod<")) {
             * getNounEntity.add(c); ////////System.out.println("Noun Test"+c.c1 +
             * "*" + c.tam1 + "*" + c.c2 + "*" + c.tam2); } } }
             */

            /*
             * for(Object ob:getNounEntity){ CRC c=(CRC)ob;
             * //////System.out.println("***"+ c.c1 + "-" + c.tam1 + "-" + c.c2 +
             * "-" + c.tam2); }
             */

            //ArrayList sortedArylis = freqsrt.getObjArray(getNounEntity);// sorting based on frequeny count across the document           
            //Collections.sort(getAllExpansion);
            //ll[1] = constructExpansionGraph(getAllExpansion, 1, core.getTamilConcepts());//graph construct for Expansion in multilist[1]
            //constructExpansionGraph(sortedArylis, 1, core.getTamilConcepts());//graph construct for Expansion in multilist[1]

            //////System.out.println(":"+transoutForIclIof);
            //ArrayList getIclIofExpansion = CombinationIclIof(transoutForIclIof,"");// to check UWConcept has iof + icl or One & Only Icl            
            //constructExpansionGraph(getIclIofExpansion, 2, singleWordConcept);//graph construct for IclIof Expansion in multilist[2]      

            for (int i = 0; i < 1; i++) {
                HeadNode h1 = new HeadNode();
                h1 = ll[i].head;
                ConceptNode c11 = new ConceptNode();
                c11 = h1.colnext;
                ConceptToNode c22 = new ConceptToNode();
                while (c11 != null) {
                    c22 = c11.rownext;
                    //  //System.out.println("concept in Miltilist[" + i + "] :" + c11.gn_word + "\t" + c11.uwconcept + "\t" + c11.queryTag+"\t"+c11.con_uid+"\t"+c11.tam_uid);
                    if (c22 != null) {
                        while (c22 != null) {
                            //   //System.out.println("rownext id: "+c22.uwtoconcept+"\t"+ c22.relnlabel);
                            c22 = c22.rownext;
                        }
                    }
                    //////System.out.println("\n");
                    c11 = c11.colnext;
                }
            }

        } catch (Exception e) {
            //////System.out.println("Exception in appln" + e);
            e.printStackTrace();
        }
        return (ll);
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
            ////////System.out.println("Not Multiword"+MWtag_Qw);
        }
        return MWtag_Qw;
    }

    /**
     * To populate the Actual Query Word into the Multiword
     */
    private FinalLLImpl constructActualQryGraph(String translatedOP, CoreeObject core) {
        //////System.out.println(">>>>||"+translatedOP.toString());
        //////System.out.println(">>>>|"+core.toString());
        //String transOutx = "[s]#[w]#ஷாஜகான்;shajahan;icl>name                        ;Entity;1#தாஜ்மஹால்;tajmahal;icl>building                        ;Entity;2#[/w]#[r]#2    pos    1#[/r]#[/s]# & [s]#[w]#எகிப்து;egypt;iof>country                        ;Entity;1#[/w]#[r]#[/r]#[/s]# & [s]#[w]#தனுஷ்கோடி;dhanushkodi;iof>place                        ;Entity;1#[/w]#[r]#[/r]#[/s]# ";
        /*
         * try { ll[0] = new org.apache.nutch.analysis.unl.ta.FinalLLImpl(); }
         * catch (Exception e) { }
         */
        ArrayList<String> uwconceptid = new ArrayList<String>();
        FinalLLImpl ll = null;

        ArrayList<Integer> importantConC = new ArrayList<Integer>();
        String relnGraph = "";
        try {
            ll = new FinalLLImpl();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String splitEncovOP[] = translatedOP.split("&");//        
        for (String transOut : splitEncovOP) {
            //System.out.println("--->"+transOut);
            int position = 1;
            ArrayList<String> getConcept = new ArrayList<String>();
            TreeSet<String> getReln = new TreeSet<String>();

            String splitTransOut[] = transOut.split("#");
            //System.out.println(Arrays.asList(splitTransOut));
            while (splitTransOut.length > position) {
                ////System.out.println("split length :"+splitTransOut.length);
                if (splitTransOut[position].contains("[w]")) {                        //to get the concepts in the translated Output.                    
                    while (!splitTransOut[++position].contains("[/w]")) {
                        getConcept.add(splitTransOut[position]);
                    }
                    //System.out.println(getConcept);
                } else if (splitTransOut[position].contains("[r]")) {                  //to get the relation in the translated Output.                                                        
                    while (!splitTransOut[++position].contains("[/r]")) {
                        getReln.add(splitTransOut[position]);

                        int conid = 0;
                        String relationid_str = splitTransOut[position].substring(0, 1);
                        if (relationid_str.equals(" ") && relationid_str.equals("None")) {
                            conid = Integer.parseInt(relationid_str);
                        }

                        if (!importantConC.contains(conid)) {
                            importantConC.add(conid);
                        }
                    }
                }
                position++;
            }

            /*
             * for(String str: getReln){ ////System.out.println(">>>|
             * "+str.substring(0,)); }
             */

            // if there is any relation between the concepts below code to add huristic relation
            if (transOut.contains("2#") && getReln.size() == 0) {                                            //to check for more than 1 concept.
                //if (getReln.size() == 0) {                    
                for (int j = 1; j <= getConcept.size() - 1; j++) {                  //Array list started from 0, set Size -1 and start from 0
                    int dupJval = j;
                    if (getConcept.get(j).contains("mod<thing")) {
                        //relnGraph += (dupJval + 1) + "	" + "mod" + "	" + (dupJval) + "#";     // to set mod relation huristically
                        relnGraph = (dupJval + 1) + "	" + "mod" + "	" + (dupJval) + "#";     // to set mod relation huristically
                        getReln.add(relnGraph);
                       // //System.out.println("if "+getConcept.get(j));
                    } else {
                        //relnGraph += (dupJval + 1) + "	" + "pos" + "	" + (dupJval) + "#";     // to set mod relation huristically
                        relnGraph = (dupJval + 1) + "	" + "pos" + "	" + (dupJval) + "#";     // to set mod relation huristically
                        getReln.add(relnGraph);
                        ////System.out.println("else "+getConcept.get(j));
                    }
                }
            }

            //////System.out.println("Relation array "+getReln);
            ArrayList tamilCon = new ArrayList();   // to avoid duplicates
            ////////System.out.println("Size -->"+getConcept.size());


            int sentenceId = 0;
            for (String conInfo : getConcept) {
                String[] getConInfo = conInfo.split(";");
                //////System.out.println("-->"+getConInfo[0].toString()+"\t"+getConInfo[1].toString()+"\t"+getConInfo[2].toString()+"\t"+getConInfo[3].toString()+"\t"+getConInfo[4].toString()+"\t"+getConInfo[5].toString()+"\t"+getConInfo[6].toString());

                if (getConInfo[3].equals("Entity") || getConInfo[3].equals("Noun")) {
                    uwconceptid.add(getConInfo[1] + "(" + getConInfo[2] + ")");
                }
                //////System.out.println("-->"+getConInfo.length);
                if (!tamilCon.contains(getConInfo[0])) {
                    //String conceptid = Integer.toString(++sentenceId);                    
                    String querytag = getQueryTag(getConInfo[0], core);
                    String sentenceid = String.valueOf(++sentenceId);
                    //////System.out.println(querytag +"\t"+sentenceid);

                    if (querytag.contains("QW") && importantConC.contains(sentenceId)) {
                        //////System.out.println("Success !!!");
                        querytag = "CONC";
                    }
                    ll.addConcept(getConInfo[0], getConInfo[1] + "(" + getConInfo[2] + ")", sentenceid, "1", "1", getConInfo[3], "", "", "", getConInfo[4], getConInfo[5], querytag, chkMultiword(getConInfo[0])); // to add concepts in to the multilist
                  // //System.out.println("-->"+getConInfo[0]+";"+  getQueryTag(getConInfo[0],core));
                    ////System.out.println("-->"+getConInfo[0]+"   "+ getConInfo[1] +" "+"(" + getConInfo[2] + ")"+"   "+ String.valueOf(sentenceId)+"   "+ getConInfo[3] +" "+"QW"+"   *** "+ chkMultiword(getConInfo[0]) +"\t"+ getConInfo[4]+"\t"+ getConInfo[5]);
                    tamilCon.add(getConInfo[0]);
                }
            }
            if (getReln.size() > 0) {                                           //to add reln into the ll list
                for (String relId : getReln) {
                    ////////System.out.println("relation id"+relId);
                    relId = relId.replaceAll("#", "");
                    ////////System.out.println("relation id"+relId);

                    String[] relnInfo = relId.split("	");
                    //////System.out.println("Size:"+getReln.size()+"  "+ relId +"\t single concept "+relnInfo[0]);

                    ll.addRelation(relnInfo[1]);
                    //////System.out.println("----->"+relnInfo[0]+" "+relnInfo[2]+" "+ relnInfo[1]);

                    ConceptToNode cn = ll.addCT_Concept(relnInfo[0], relnInfo[2], relnInfo[1], "1", "1");
                    ll.addCT_Relation(cn);
                }
            }
        }
        core.setUwconceptid(uwconceptid);
        return ll;
    }

    /**
     * to get the Query tage whether Tag is AQW or MW
     *
     * @param word
     * @return
     */
    private String getQueryTag(String word, CoreeObject core) {
        //////System.out.println("Core"+core.getAndConcepts_ta());
        //////System.out.println("Core"+core.getDoubleQuotesConcepts_ta());
        String[] temp = word.split(" ");
        if (temp.length > 1) {
            return "MW";
        } else if (core.getAndConcepts_ta().toString().contains(word)) {
            int i = 1;
            String tag = "";
            for (String str : core.getAndConcepts_ta()) {
                //////System.out.println(">>"+str +"Word:"+word);
                if (str.contains(word)) {
                    tag = "AND" + i;
                }
                i++;
            }
            //////System.out.println(">>"+tag);
            return tag;
        } else if (core.getAndConcepts_en().toString().contains(word)) {
            int i = 1;
            String tag = "";
            for (String str : core.getAndConcepts_en()) {
                //////System.out.println(">>"+str +"Word:"+word);
                if (str.contains(word)) {
                    tag = "AND" + i;
                }
                i++;
            }
            //////System.out.println(">>"+tag);
            return tag;
        } else if (core.getDoubleQuotesConcepts_ta().toString().contains(word)) {
            int i = 1;
            String tag = "";
            for (String str : core.getDoubleQuotesConcepts_ta()) {
                //////System.out.println(">>"+str +"Word:"+word);
                if (str.contains(word)) {
                    tag = "QTS" + i;
                }
                i++;
            }
            //////System.out.println(">>"+tag);
            return tag;
            //return "QTS";
        } else if (core.getDoubleQuotesConcepts_en().toString().contains(word)) {
            int i = 1;
            String tag = "";
            for (String str : core.getDoubleQuotesConcepts_en()) {
                //////System.out.println(">>"+str +"Word:"+word);
                if (str.contains(word)) {
                    tag = "QTS" + i;
                }
                i++;
            }
            //////System.out.println(">>"+tag);
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
    private FinalLLImpl constructExpansionGraph(ArrayList Expansion, int ls, ArrayList singleWordConcept) throws Exception {
        //////System.out.println("Single wordL:"+singleWordConcept.toString());
        ArrayList getTamilCon = new ArrayList();
        String multiwordTag = "";
        int conceptcount = 0;
        ArrayList toconcept = new ArrayList();
        ArrayList conceptids = new ArrayList();
        String frmconceptid = "";
        String toconceptid = "";
        FinalLLImpl ll = null;
        int flag = 0;
        try {
            ll = new FinalLLImpl();
            //ll[ls] = new org.apache.nutch.analysis.unl.ta.FinalLLImpl();

            int expansioncnt = 0;
            for (Object getExpansionObj : Expansion) {
                CRC c = (CRC) getExpansionObj;
                getTamilCon.add((String) c.getTam1());
                multiwordTag = chkMultiword((String) c.getTam1());

                conceptcount++;
                frmconceptid = String.valueOf(conceptcount);

                if (singleWordConcept.contains((String) c.getTam1())) {
                    ll.addConcept((String) c.getTam1(), (String) c.getC1(), frmconceptid, "1", "1", (String) c.getPos1(), "", "", "", "", String.valueOf(c.getUwconceptid()), "QW", multiwordTag);//tagmw,MWtag_Qw
                } else {
                    ll.addConcept((String) c.getTam1(), (String) c.getC1(), frmconceptid, "1", "1", (String) c.getPos1(), "", "", "", "", "", "EQW", multiwordTag);//tagmw,MWtag_Qw
                }


                multiwordTag = chkMultiword((String) c.getTam2());   // multiword check

                if (!toconcept.contains((String) c.getTam2())) {
                    conceptcount++;
                    toconceptid = String.valueOf(conceptcount);

                    if (singleWordConcept.contains((String) c.getTam2())) {
                        ll.addConcept((String) c.getTam2(), (String) c.getC2(), toconceptid, "1", "1", (String) c.getPos2(), "", "", "", "", String.valueOf(c.getUwconceptid()), "QW", multiwordTag);//tagsw+EQWCnt,""
                    } else {
                        ll.addConcept((String) c.getTam2(), (String) c.getC2(), toconceptid, "1", "1", (String) c.getPos2(), "", "", "", "", String.valueOf(c.getUwconceptid()), "CQW", multiwordTag);//tagsw+EQWCnt,""
                    }
                    toconcept.add((String) c.getTam2());
                    conceptids.add(toconceptid);
                    flag = 1;
                }


                if (flag == 1) {
                    int ind = toconcept.indexOf((String) c.getTam2());
                    String tocon = conceptids.get(ind).toString();
                    String conceptfrm = frmconceptid;
                    ConceptToNode cn = new ConceptToNode();
                    cn = ll.addCT_Concept(conceptfrm, tocon, (String) c.getRel(), "1", "1");
                    ll.addCT_Relation(cn);
                } else {
                    String conceptfrm = frmconceptid;
                    String conceptto = toconceptid;
                    ConceptToNode cn = new ConceptToNode();
                    cn = ll.addCT_Concept(conceptfrm, conceptto, (String) c.getRel(), "1", "1");
                    ll.addCT_Relation(cn);
                }


                //to limit the Expansion to top 20
                ++expansioncnt;
                if (expansioncnt == 20) {
                    break;
                }

            }//for
        } catch (Exception e) {
        }
        return ll;
    }

    //old version
    /*
     * private ArrayList CombinationIclIof(String translatedOP, String version )
     * {
     *
     * String[] getTransCon = translatedOP.split("#");
     * //////System.out.println("length combination ioficl"); ArrayList<String>
     * iofExpansion = new ArrayList<String>(); ArrayList getIofExpansion = new
     * ArrayList();
     *
     *
     * for (int i = 0; i < getTransCon.length; i++) { iof = new
     * QueryExpansion_iof_ta().instance(); if (getTransCon.length > 1 && ((i +
     * 1) < getTransCon.length)) {
     * //கன்னியாகுமரி;kanniyakumari;iof>place;Entity;1 if
     * (getTransCon[i].contains("iof>") && getTransCon[i + 1].contains("icl>"))
     * { String[] toConceptInfo = getTransCon[i + 1].split(";"); String
     * getSuperClasConcept = toConceptInfo[1].toString().trim(); String
     * iofConstrain = "(iof>" + getSuperClasConcept + ")";
     * //////System.out.println("constraint:"+iofConstrain);
     *
     * // to get the IOF information (idly etc ...) if(!version.equals("DB")){
     * iofExpansion = iof.processSuper(iofConstrain.trim()); if
     * (iofExpansion.size() <= 0) { String getSuperClasConcept1 =
     * toConceptInfo[2].substring(toConceptInfo[2].indexOf(">") + 1,
     * toConceptInfo[2].length()); iofConstrain = "(iof>" + getSuperClasConcept1
     * + ")"; iofExpansion = iof.processSuper(iofConstrain.trim()); }
     *
     * for (String frmconcept : iofExpansion) { String Temp_iof1[] =
     * getTransCon[i].split(";"); String Temp_iof2[] = frmconcept.split(";");
     *
     * crc = new CRC(); crc.tam1 = Temp_iof2[0]; crc.c1 = Temp_iof2[1] + "(" +
     * Temp_iof2[2] + ")"; crc.pos1 = Temp_iof2[3]; crc.rel = "None"; crc.tam2 =
     * Temp_iof1[0]; crc.c2 = Temp_iof1[1] + "(" + Temp_iof1[2] + ")"; crc.pos2
     * = Temp_iof1[3]; getIofExpansion.add(crc); }//for
     */
    /*
     * }else{ String[] temp = getTransCon[i].split(";"); String tamil = temp[0];
     * String headword= temp[1]; String constraint = temp[2]; String pos =
     * temp[3]; String uw = headword +"("+constraint+")"; String
     * searchconstraint = constraint.replace("iof", "icl");
     * getIofExpansion.addAll(QueryExpansion_DB.instanceOf().getQueryexpansion_hierarchy(tamil,uw,pos,searchconstraint));
     * } }
     *
     * } else { ////////System.out.println("i'm in else part " + getTransCon[i] +
     * " " + getTransCon.length); if (getTransCon[i].contains("icl>") &&
     * getTransCon.length == 1) { //////System.out.println("Chennai i'm here");
     * String[] toConceptInfo = getTransCon[i].split(";"); String
     * getSuperClasConcept = toConceptInfo[1].toString().trim(); String
     * iofConstrain = "(iof>" + getSuperClasConcept + ")";
     * //////System.out.println("iofConstrain " + iofConstrain);
     *
     * if(!version.equals("DB")){ iofExpansion =
     * iof.processSuper(iofConstrain.trim()); if (iofExpansion.size() <= 0) {
     * String getSuperClasConcept1 =
     * toConceptInfo[2].substring(toConceptInfo[2].indexOf(">") + 1,
     * toConceptInfo[2].length()); iofConstrain = "(iof>" + getSuperClasConcept1
     * + ")"; iofExpansion = iof.processSuper(iofConstrain.trim()); }
     *
     * for (String frmconcept : iofExpansion) { String Temp_iof1[] =
     * getTransCon[i].split(";"); String Temp_iof2[] = frmconcept.split(";");
     * crc = new CRC(); crc.tam1 = Temp_iof2[0]; crc.c1 = Temp_iof2[1] + "(" +
     * Temp_iof2[2] + ")"; crc.pos1 = Temp_iof2[3]; crc.rel = "None"; crc.tam2 =
     * Temp_iof1[0]; crc.c2 = Temp_iof1[1] + "(" + Temp_iof1[2] + ")"; crc.pos2
     * = Temp_iof1[3]; getIofExpansion.add(crc); } }else{ String[] temp =
     * getTransCon[i].split(";"); String tamil = temp[0]; String headword=
     * temp[1]; String constraint = temp[2]; String pos = temp[3]; String uw =
     * headword +"("+constraint+")"; String searchconstraint =
     * constraint.replace("iof", "icl");
     * getIofExpansion.addAll(QueryExpansion_DB.instanceOf().getQueryexpansion_hierarchy(tamil,uw,pos,searchconstraint));
     * } }else if (getTransCon[i].contains("iof>") && getTransCon.length == 1) {
     * //////System.out.println("I'm Here..."); String[] toConceptInfo =
     * getTransCon[i].split(";"); String getSuperClasConcept =
     * toConceptInfo[1].toString().trim(); String iofConstrain = "(icl>" +
     * getSuperClasConcept + ")"; // ////System.out.println("===> " +
     * getTransCon[i]);
     *
     * if(!version.equals("DB")){ iofExpansion =
     * iof.processSuper(iofConstrain.trim()); if (iofExpansion.size() <= 0) {
     * String getSuperClasConcept1 =
     * toConceptInfo[2].substring(toConceptInfo[2].indexOf(">") + 1,
     * toConceptInfo[2].length()); iofConstrain = "(iof>" + getSuperClasConcept1
     * + ")"; iofExpansion = iof.processSuper(iofConstrain.trim()); }
     *
     * for (String frmconcept : iofExpansion) { String Temp_iof1[] =
     * getTransCon[i].split(";"); String Temp_iof2[] = frmconcept.split(";");
     * crc = new CRC(); crc.tam1 = Temp_iof2[0]; crc.c1 = Temp_iof2[1] + "(" +
     * Temp_iof2[2] + ")"; crc.pos1 = Temp_iof2[3]; crc.rel = "None"; crc.tam2 =
     * Temp_iof1[0]; crc.c2 = Temp_iof1[1] + "(" + Temp_iof1[2] + ")"; crc.pos2
     * = Temp_iof1[3]; getIofExpansion.add(crc); } }else{ String[] temp =
     * getTransCon[i].split(";"); String tamil = temp[0]; String headword=
     * temp[1]; String constraint = temp[2]; String pos = temp[3]; String uw =
     * headword +"("+constraint+")"; String searchconstraint =
     * constraint.replace("iof", "icl");
     * getIofExpansion.addAll(QueryExpansion_DB.instanceOf().getQueryexpansion_hierarchy(tamil,uw,pos,searchconstraint));
     * }
     *
     *
     * }
     * }//else// if len > i+1 }// for
     *
     * return getIofExpansion; }
     */
    /*
     * public ArrayList CombinationIclIof(String translatedOP) {
     * ////////System.out.println("ICLIOF:"+translatedOP); String[] getTransCon =
     * translatedOP.split("#"); ////////System.out.println("length :" +
     * getTransCon.length); ArrayList<String> iofExpansion = new
     * ArrayList<String>(); ArrayList getIofExpansion = new ArrayList();
     *
     *
     * try { for (int i = 0; i < getTransCon.length; i++) { iof = new
     * QueryExpansion_iof_ta().instance(); if (getTransCon.length > 1) {
     * //கன்னியாகுமரி;kanniyakumari;iof>place;Entity;1
     *
     * if (getTransCon[i].contains("iof>") && ((i + 1) > getTransCon.length)) {
     * if (getTransCon[i + 1].contains("icl>")) { String[] toConceptInfo =
     * getTransCon[i + 1].split(";"); String getSuperClasConcept =
     * toConceptInfo[1].toString().trim(); String iofConstrain = "(iof>" +
     * getSuperClasConcept + ")";
     * ////System.out.println("IOFCONSTARTINT:"+iofConstrain);
     *
     * // to get the IOF information (idly etc ...) iofExpansion =
     * iof.processSuper(iofConstrain.trim());
     *
     * if (iofExpansion.size() <= 0) { String getSuperClasConcept1 =
     * toConceptInfo[2].substring(toConceptInfo[2].indexOf(">") + 1,
     * toConceptInfo[2].length()); iofConstrain = "(iof>" + getSuperClasConcept1
     * + ")"; iofExpansion = iof.processSuper(iofConstrain.trim()); }
     *
     * for (String frmconcept : iofExpansion) { String Temp_iof1[] =
     * getTransCon[i].split(";"); String Temp_iof2[] = frmconcept.split(";");
     *
     * crc = new CRC(); crc.tam1 = Temp_iof2[0]; crc.c1 = Temp_iof2[1] + "(" +
     * Temp_iof2[2] + ")"; crc.pos1 = Temp_iof2[3]; crc.rel = "None"; crc.tam2 =
     * Temp_iof1[0]; crc.c2 = Temp_iof1[1] + "(" + Temp_iof1[2] + ")"; crc.pos2
     * = Temp_iof1[3]; getIofExpansion.add(crc); }//for } } } else {
     * ////System.out.println("i'm in else part " + getTransCon[i] + " " +
     * getTransCon.length); if (getTransCon[i].contains("icl>") &&
     * getTransCon.length == 1) {
     *
     * String[] toConceptInfo = getTransCon[i].split(";"); String
     * getSuperClasConcept = toConceptInfo[1].toString().trim(); String
     * iofConstrain = "(iof>" + getSuperClasConcept + ")";
     * //////System.out.println("iofConstrain " + iofConstrain);
     *
     * iofExpansion = iof.processSuper(iofConstrain.trim()); if
     * (iofExpansion.size() <= 0) { String getSuperClasConcept1 =
     * toConceptInfo[2].substring(toConceptInfo[2].indexOf(">") + 1,
     * toConceptInfo[2].length()); iofConstrain = "(iof>" + getSuperClasConcept1
     * + ")"; iofExpansion = iof.processSuper(iofConstrain.trim()); }
     *
     * for (String frmconcept : iofExpansion) { String Temp_iof1[] =
     * getTransCon[i].split(";"); String Temp_iof2[] = frmconcept.split(";");
     * crc = new CRC(); crc.tam1 = Temp_iof2[0]; crc.c1 = Temp_iof2[1] + "(" +
     * Temp_iof2[2] + ")"; crc.pos1 = Temp_iof2[3]; crc.rel = "None"; crc.tam2 =
     * Temp_iof1[0]; crc.c2 = Temp_iof1[1] + "(" + Temp_iof1[2] + ")"; crc.pos2
     * = Temp_iof1[3]; getIofExpansion.add(crc); } } }//else// if len > i+1 }//
     * for } catch (Exception e) { e.printStackTrace(); // TODO: handle
     * exception }
     *
     * return getIofExpansion; }
     */
    /**
     * @return multilist ll[0] ll[1] and ll[2] to search
     */
    /*
     * public static FinalLLImpl[] multilist_UNLQuery() { return (ll); }
     */
    public static void main(String args[]) {
        //ஆமெரிக்கா செட்டிநாடு உணவுவகைகள் அகத்தியர் அருவி ஆப்பிள் செட்டிநாடு உணவுவகைகள்
        CoreeObject core = new CoreeObject();
        //core.setActualQuery("ஆமெரிக்கா தமிழ்நாட்டு கவுதம் மாவட்டம் கவுதம்  உணவு செட்டிநாடு உணவுவகைகள் அகத்தியர் அருவி ஆப்பிள் செட்டிநாடு உணவுவகைகள்");
        core.setActualQuery("கன்னியாகுமரியில் பார்க்க வேண்டிய இடங்கள் ");
        core.setLanguage("ta");
        //Session session = HibernateUtil.getSessionFactory().openSession();
        //Transaction transaction = session.beginTransaction();
        //core.setTamilTranslatedConcepts("ஆமெரிக்கா தமிழ்நாட்டு கவுதம் மாவட்டம் கவுதம்  மாவட்டம்   உணவு செட்டிநாடு உணவுவகைகள் அகத்தியர் அருவி ஆப்பிள் செட்டிநாடு உணவுவகைகள்");
        new TamilQueryGraph().translateAll(core, true);
    }
}
