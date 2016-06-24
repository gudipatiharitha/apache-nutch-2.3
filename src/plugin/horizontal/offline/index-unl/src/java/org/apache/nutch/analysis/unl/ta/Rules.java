package org.apache.nutch.analysis.unl.ta;

import org.apache.nutch.util.NutchConfiguration;
import org.apache.hadoop.conf.Configuration;

import java.lang.*;
import java.util.*;
import java.io.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.nutch.analysis.unl.ta.snippet.Snippet;

/**
 * VERSION 3 - Dated: 18.09.2012 - Author : Balaji J
 */
/**
 * Rules class consists of a set of Rules to enconvert the tamil documents into
 * UNL Graphs There are 53 Rules based on Morphological Endings, POS, Semantics,
 * Co-occurences These 53 Rules are Categorized into 5 Categories: Morphological
 * Endings, POS, Semantics, Co-occurences and Connective This Class also
 * includes Word Sense Disambiguation consists of 8 Rules Finally the Output
 * returned by this class is a set of UNL Graphs for a set of documents. UW List
 * and MW list are used to retrieve the UW concepts of a Tamil word
 *
 */
public class Rules {

    MWDict mwdict;
    UWDict uwdict;
    Centering centering;
    NestedGraph nestedunl;
    UNL_Attributes unl_attr;
    WordSenseDisambiguation wsd;
    MultiWordExtraction MWE;
    Snippet s_g;
    ArrayList<String> anal;
    ArrayList<String> anal_attr;
    ArrayList<String> wordAL;
    ArrayList<String> root;
    ArrayList<String> rootnew;
    ArrayList<String> pos;
    ArrayList<String> verbList;
    ArrayList<LinkedList> ambiguousList;
    ArrayList rootrel;
    ArrayList<String> abbwords = new ArrayList<String>();
    ArrayList<String> expwords = new ArrayList<String>();
    ArrayList<String> tempcmtw;
    ArrayList<String> tempcmew;
    ArrayList<String> tempcmuw;
    ArrayList<String> mwlist;
    ArrayList<String> tempcmtuid;
    ArrayList<String> tempcmcuid;
    ArrayList<String> hw;
    ArrayList<String> uw;
    ArrayList<String> tuid;
    ArrayList<String> cuid;
    ArrayList no;
    ArrayList scpno;
    ArrayList<String> conceptfrm;
    ArrayList<String> relnlabel;
    ArrayList<String> conceptto;
    ArrayList compdid;
    int aojindex;
    int plfindex;
    int insindex;
    int cobindex;
    int timindex;
    int cwindex;
    int verbcount;
    String verb1;
    String verb2;
    String verb3;
    boolean bolverb1;
    boolean bolverb2;
    boolean bolverb3;
    boolean num_flag;
    boolean t_flag;
    String verbno1;
    String verbno2;
    String verbno3;
    String verbno;
    boolean chk_suffix;
    boolean pr_res;
    String pr;
    int pr_index;
    String firstword = "";
    String firstwordpss = "";
    String nestedresult = "";
    String sni_did = "";
    int noofcw;
    int position;
    int ind_obj;
    int j = 0, total;
    int cnt1 = 0, cnt2 = 0, cnt3 = 0, cnt4 = 0, cnt5 = 0;
    String analysed;
    String tempword;
    String rel_label;
    String h_word = " ";
    String u_word = " ";
    String unknown = "";
    String com_list = "";
    String result;
    String mwword = "";
    public String rootentry = null;
    public String uwentry = null;
    public String hwentry = null;
    public String prevanal = null;
    public String curranal = null;
    public String nextanal = null;
    public String prevhw = null;
    public String nexthw = null;
    public String prevuw = null;
    public String nextuw = null;
    public String prevconcept = null;
    public String nextconcept = null;
    ArrayList<String> tne = new ArrayList<String>();
    ArrayList<String> uwlist;
    ArrayList<String> hwlist;
    ArrayList<String> rootlist;
    ArrayList<String> tuid_list;
    ArrayList<String> cuid_list;
    ArrayList<String> fentry;
    ArrayList<String> sense;
    ArrayList<String> UnknownList = new ArrayList<String>();
    ArrayList<String> splitword;
    ArrayList<String> splitpos;
    ArrayList<String> splitanal;
    ArrayList<String> multisplit;
    ArrayList<String> unl_attributes;
    ArrayList<String> Tags;
    //ArrayList<String> mwlist;
    LinkedList wsd_ll;
    ArrayList<String> RefExp;
    // ArrayList<String> RefExpWord = new ArrayList<String>();
    ArrayList<String> personUW;
    ArrayList<String> pnounuwList = new ArrayList<String>();
    Hashtable freqTable = new Hashtable();
    // clia.unl.wordsense.WordSense ws;
    public String sword = "";
    public String sente = "";
    Configuration conf = NutchConfiguration.create();
    String path = conf.get("EncInput");

    public Rules() {
        try {
//            dict = new BST();
//            dict_mw = new MWBST();

            mwdict = new MWDict();
//            mwdict.mwbst.inorder();

            uwdict = new UWDict();
//            uwdict.bst.inorder();

            // ws = new WordSense();

//            dict = uwdict.bst;
//            dict_mw = mwdict.mwbst;
//
//            bn_mw = new MWBSTNode();

            centering = new Centering();

            nestedunl = new NestedGraph();

            unl_attr = new UNL_Attributes();

            wsd = new WordSenseDisambiguation();

            MWE = new MultiWordExtraction();

            s_g = new Snippet();

        } catch (Exception e) {
        }

    }

    public void init() {
        try {

            anal = new ArrayList<String>();
            anal_attr = new ArrayList<String>();
            wordAL = new ArrayList<String>();
            root = new ArrayList<String>();
            rootnew = new ArrayList<String>();
            pos = new ArrayList<String>();
            verbList = new ArrayList<String>();
            ambiguousList = new ArrayList<LinkedList>();
            rootrel = new ArrayList();
            Tags = new ArrayList<String>();

            tempcmtw = new ArrayList<String>();
            tempcmew = new ArrayList<String>();
            tempcmuw = new ArrayList<String>();
            tempcmtuid = new ArrayList<String>();
            tempcmcuid = new ArrayList<String>();

            hw = new ArrayList<String>();
            uw = new ArrayList<String>();
            tuid_list = new ArrayList();
            cuid_list = new ArrayList();
            tuid = new ArrayList();
            cuid = new ArrayList();
            no = new ArrayList();
            scpno = new ArrayList();

            wsd_ll = new LinkedList();

            conceptfrm = new ArrayList<String>();
            relnlabel = new ArrayList<String>();
            conceptto = new ArrayList<String>();
            compdid = new ArrayList();

            uwlist = new ArrayList<String>();
            hwlist = new ArrayList<String>();
            rootlist = new ArrayList<String>();
            fentry = new ArrayList<String>();
            sense = new ArrayList<String>();
            unl_attributes = new ArrayList<String>();

            splitword = new ArrayList<String>();
            splitpos = new ArrayList<String>();
            splitanal = new ArrayList<String>();
            multisplit = new ArrayList<String>();

            RefExp = new ArrayList<String>();
            personUW = new ArrayList<String>();
            // recencyList = new ArrayList<String>();

            aojindex = 0;
            insindex = 0;
            plfindex = 0;
            timindex = 0;
            cobindex = 0;

            cwindex = 0;
            noofcw = 0;
            ind_obj = 0;

            bolverb1 = false;
            bolverb2 = false;
            bolverb3 = false;

            t_flag = false;

            num_flag = false;
            chk_suffix = false;
            pr_res = false;
            pr = "";
            pr_index = -1;

            verb1 = "";
            verb2 = "";
            verb3 = "";

            // //attribute = "";

            verbcount = 0;

            verbno1 = "";
            verbno = "";

            position = 0;
            result = "[s]#";
            nestedresult = "";

        } catch (Exception e) {
        }
    }

    public String enconvert(String st, boolean online) {
        return enconvert(st);
    }

    public String enconvert(String st) {
        try {
            int cnt = 1;
            String templex = "";
            String mwe = "";
            int flag = 0;
            int now = 0;
            String dAdjective = null;
            String adverbWord = null;
            String pronounWord = null;
            String[] demonAdj;
            String[] adverbStr = null;
            String[] pronounStr;

            init();
            //	System.out.println("Sentence:"+st);
            // Identify words delimiter

            if (st.contains("s")) {
                st = st.replaceAll("s", "");
            } else {
                st = st;
            }
            StringTokenizer inp = new StringTokenizer(st, ",; ");
            sente = st;
            int noofwords = inp.countTokens(); // No. of words in the given
            // input
            int ctnoofwords = 0; // Count no. of words is initialized to zero

            // store the words in inp array List and root words in root array
            // List
            // //System.out.println("Start RefExp:"+RefExp);
            while (ctnoofwords < noofwords) // Check the condition
            {

                String word = inp.nextToken().trim(); // Remove the blank

                String pss = "";
                String word2 = "";
                String word1 = "";
                String prev, next;

                // to add root word in wordAL arrayList


                // analyze the given word
                num_flag = isNumeric(word.trim());
                if (num_flag == false) {
                    wordAL.add(word.trim());
                    num_flag = true;
                    //	System.out.println("WORD TO ANALYSER:"+word);
                    if (word.startsWith("<a>")) {
                        word = word.replace("<a>", "");
                        t_flag = true;
                    } else {
                        word = word;
                    }
                    if (t_flag == true) {
                        Tags.add("ANC");
                    } else {
                        Tags.add("None");
                    }
                    if (word.endsWith("</a>")) {
                        //Tags.add("ANC");
                        word = word.replace("</a>", "");
                        t_flag = false;
                    } else {
                        word = word;
                    }

                    String analysed = org.apache.nutch.analysis.unl.ta.Analyser.analyseF(word.trim(), true);
                    anal_attr.add(analysed);
                    //	System.out.println("analysed1:" + analysed1);
                /*
                     * String spt[] = analysed1.split("#"); String category =
                     * spt[0].toString(); //
                     * System.out.println("category:"+category); analysed =
                     * spt[1].toString();
                     */
                    //	System.out.println("analysed:" + analysed);
                    StringTokenizer strToken2 = new StringTokenizer(analysed,
                            ":\n██—<>,=+»←→↓↑•*;:?-.'“\"&", false);

                    int firstindex = analysed.indexOf('<', analysed.indexOf('>'));
                    String analmod = analysed.substring(firstindex);
                    // to store analyzer output
                    anal.add(analmod);
                    // to get root word and POS from analyzer output
                    if ((analysed.indexOf("unknown") != -1)
                            || (analysed.indexOf("count=4") != -1)) {
                        word1 = strToken2.nextToken().trim();
                        word2 = word1;
                        pos.add("None");
                        root.add(word2);
                        if (RefExp.isEmpty()) {
                            RefExp.add(word2);
                        } else if (RefExp.contains(word2)) {
                            int k = RefExp.indexOf(word2);
                            RefExp.remove(k);
                            if (RefExp.size() != 0) {
                                RefExp.add(RefExp.size() - 1, word2);
                            }
                        } else {
                            RefExp.add(word2);
                        }
                    } else if ((analysed.indexOf("unknown") != -1)
                            || (analysed.indexOf("count=5") != -1)) {
                        word1 = strToken2.nextToken().trim();
                        word2 = word1;
                        pos.add("None");
                        root.add(word2);
                    } else if ((analysed.indexOf("Demonstrative Adjective") != -1)
                            && (!analysed.contains("அதன்"))) {
                        prev = strToken2.nextToken().trim();
                        word1 = prev;
                        next = strToken2.nextToken().trim();

                        dAdjective = centering.centeringProcessforDAdjective(next,
                                analysed, pnounuwList);
                        if (dAdjective != null) {
                            demonAdj = dAdjective.split("/");
                            root.add(demonAdj[0]);
                            pos.add(demonAdj[1]);

                        } else {
                            root.add(next);
                            pos.add("None");
                        }
                    } else if ((analysed.indexOf("Adjectival Noun") != -1)
                            || (analysed.indexOf("Noun") != -1)
                            || (analysed.indexOf("Interrogative") != -1)
                            || (analysed.indexOf("Non Tamil Noun") != -1)
                            || (analysed.indexOf("Entity") != -1)
                            || (analysed.indexOf("Adverb") != -1)
                            || (analysed.indexOf("Numbers") != -1)
                            || (analysed.indexOf("charNumbers") != -1)
                            || (analysed.indexOf("DateTime") != -1)
                            || (analysed.indexOf("Postposition") != -1)
                            || ((analysed.indexOf("Verb") != -1) && (analysed.indexOf("Finite Verb") == -1))
                            || (analysed.indexOf("Particle") != -1)
                            || (analysed.indexOf("Adjective") != -1)) {
                        prev = strToken2.nextToken().trim();
                        word1 = prev;
                        next = strToken2.nextToken().trim();
                        //	System.out.println("word1:"+word1+"====="+next);
                        if ((analysed.contains("Entity"))
                                || (analysed.contains("Noun"))) {
                            if (RefExp.isEmpty()) {
                                RefExp.add(next);
                            } else if (RefExp.contains(next)) {
                                int k = RefExp.indexOf(next);
                                RefExp.remove(k);
                                if (RefExp.size() != 0) {
                                    RefExp.add(RefExp.size() - 1, next);
                                }
                            } else {
                                RefExp.add(next);
                            }
                        }
                        while (strToken2.hasMoreTokens()) {
                            if ((!(next.equals("Adjectival Noun")))
                                    && (!(next.equals("Noun")))
                                    && (!(next.equals("Interrogative Adjective")))
                                    && (!(next.equals("Interrogative Noun")))
                                    && (!(next.equals("Non Tamil Noun")))
                                    && (!(next.equals("Entity")))
                                    && (!(next.equals("Adverb")))
                                    && (!(next.equals("Numbers")))
                                    && (!(next.equals("charNumbers")))
                                    && (!(next.equals("DateTime")))
                                    && (!(next.equals("Postposition")))
                                    && (!(next.equals("Verb")))
                                    && (!(next.equals("Finite Verb")))
                                    && (!(next.equals("Adjective")))
                                    && ((!(next.equals("Particle"))))) {
                                prev = next;
                                next = strToken2.nextToken().trim();
                            } else {
                                break;
                            }
                        }
                        word2 = prev;
                        pss = next;
                        if (word1.indexOf("இங்கு") != -1) {
                            addMorphology(analmod);
                            // //System.out.println("Inside Adverb:"+word1);
                            adverbWord = centering.centeringProcessforAdverb(
                                    pnounuwList, word1);
                            if (adverbWord != null) {
                                adverbStr = adverbWord.split("/");
                                word2 = adverbStr[0];
                                pss = adverbStr[1];

                                root.add(word2);
                                pos.add(pss);
                                // pnounuwList.clear();
                            } else {
                                root.add(word2);
                                pos.add(pss);
                            }
                        } else {
                            root.add(word2);
                            pos.add(pss);
                        }
                    } else {
                        if (strToken2.hasMoreTokens()) {
                            word1 = strToken2.nextToken().trim();
                            word2 = strToken2.nextToken().trim();
                            String s1 = "";
                            if (strToken2.hasMoreTokens()) {
                                s1 = strToken2.nextToken();
                                pss = s1;
                            }
                        } else {
                        }
                        if ((analysed.contains("Pronoun"))
                                || (word1.indexOf("இதன்") != -1)) // ithan, ithu
                        {
                            // //System.out.println("Inside Pronoun:"+word1 +
                            // ":"+analysed);
                            addMorphology(analmod);
                            chkIfPerson();
                            pronounWord = centering.centeringProcessforPronoun(
                                    personUW, pnounuwList, word1, analysed);

                            if (pronounWord != null) {
                                pronounStr = pronounWord.split("/");
                                word2 = pronounStr[0];
                                pss = pronounStr[1];
                                // //System.out.println("After Processing Pronoun:"+word2
                                // + pss);
                                root.add(word2);
                                pos.add(pss);
                                // pnounuwList.clear();
                            } else {
                                root.add(word2);
                                pos.add(pss);
                            }
                        } else {
                            root.add(word2);
                            pos.add(pss);
                        }
                    } // end of else
                    if ((pss.equals("Verb")) || (pss.equals("Finite Verb"))) {
                        verbcount++;
                        verbList.add(word2);
                        flag = 0;
                        if (bolverb1 == false) {
                            bolverb1 = true;
                            verb1 = word2;
                            // //System.out.println("Verb1:"+verb1);
                        } else if ((bolverb1 == true) && (bolverb2 == false)) {
                            bolverb2 = true;
                            verb2 = word2;
                            // //System.out.println("Verb2:"+verb2);
                        } else if ((bolverb1 == true) && (bolverb2 == true)
                                && (bolverb3 == false)) {
                            bolverb3 = true;
                            verb3 = word2;
                            // //System.out.println("Verb3:"+verb3);
                        }
                    } // end of if				
                } else {
                    //	System.out.println("word:"+word);
                }

                //	System.out.println("WORD:"+word2);
                sword = word2;
                splitword.add(word2);
                splitpos.add(pss);
                ctnoofwords++;
            } // end of while
            //	System.out.println("TAGS:"+Tags);
            //	System.out.println("ROOT:----->"+root);
            for (int i = 0; i < pos.size(); i++) {
                String s = pos.get(i).toString();
            }
            findUW();

            for (int f = 0; f < verbList.size(); f++) {
                String get_verb = verbList.get(f).toString();
                position = root.indexOf(get_verb);
                // //System.out.println("Position:"+position);
                if (!(anal.get(position).toString().contains("900"))
                        && !(anal.get(position).toString().contains("911"))) {
                    verbno1 = findverbnumber(get_verb);
                    break;
                }
            }
            if (bolverb2 == true) {
                verbno2 = findverbnumber(verb2);
            }

            findrelnindex();// Commented for Snippet Generation**************

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(analysed);
        }
        return result;
    }

    public void clearList() {
        //	s_g.SnippetWriteinFile();
        //	s_g.clearSnippet_hash();
        //	s_g=null;
        //	s_g=new Snippet();
        pnounuwList.clear();
        UnknownList.clear();
        sni_did = "";
    }

    public void getDocid(String did) {
        sni_did = did;
    }

    public void writeintofile(String docid) {
        try {
            Writer output = null;
            File fn = new File("./resource/unl/UWUnknown/" + docid + ".txt");
            fn.createNewFile();
            output = new BufferedWriter(new FileWriter(fn, false));
            String un_result = UnknownList.toString();
            un_result = un_result.replace(",", "\n");
            un_result = un_result.replace(" ", "");
            un_result = un_result.replace("[", "");
            un_result = un_result.replace("]", "");
            output.write(un_result);
            output.close();
        } catch (Exception e) {
        }
    }

    public static boolean isNumeric(String str) {
        try {
            int d = Integer.parseInt(str.trim());
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void loadequ() {
        String equentry = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(path
                    + "/equ.txt"));
            while ((equentry = in.readLine()) != null) {
                StringTokenizer tok = new StringTokenizer(equentry, "/");
                abbwords.add(tok.nextToken());
                expwords.add(tok.nextToken());
            }
            in.close();
        } catch (Exception e) {
        }

    }

    public void findUW() {

        int count = 1;
        int scpcnt = 1;
        int cnt = 0;
        String pnentry = null;
        String rword = "";
        ArrayList<String> te = new ArrayList<String>();
        // StringBuffer sb = new StringBuffer();
        StringBuffer resultuw = new StringBuffer();
        int j = 0, total;
//		System.out.println("SIZE OF ROOT IN FINDUW:"+root);
        try {
            total = root.size();
            while (j < total) {

//                bn = new BSTNode();

                rword = root.get(j).toString().trim();

                //int hc = rword.hashCode();
                String hc = new String(Base64.encodeBase64(("" + rword).getBytes())); // Modified
                //	System.out.println("RWORD:"+rword +"---->"+j+"===="+hc);
                //	int cindex = tempcmtw.indexOf(rword);
                if (uwdict.uwdict.containsKey(hc)) {
                    LinkedList ll = (LinkedList) uwdict.uwdict.get(hc);
                    //	System.out.println("ll>>>>>>>>>>>>"+rword+"|"+hc +"\t"+ll.size());
                    if (ll.size() == 1) {
                        LinkedList ll_entry = (LinkedList) ll.getLast();
                        // ambiguousList.add("None");
                        // System.out.println("ll_entry:"+ll_entry.get(0).toString()+"\t"+ll_entry.get(1).toString()+"\t"+ll_entry.get(2).toString());
                        hw.add(ll_entry.get(1).toString());
                        uw.add(ll_entry.get(2).toString());
                        tuid.add(ll_entry.get(3).toString());
                        cuid.add(ll_entry.get(4).toString());
                        no.add(new Integer(count++));
                        rootnew.add(ll_entry.get(0).toString());
                        //		System.out.println("Entries:"+hw+"\t"+uw+"\t"+rootnew);
                    } else {
                        /**
                         * ********* WORD SENSE DISAMBIGUATION ***********
                         */
                        ambiguousList.add(ll);
                        hw.add("$$$");
                        uw.add("$$$");
                        tuid.add("$$$");
                        cuid.add("$$$");
                        no.add(new Integer(count++));
                        rootnew.add(rword);
                    }
                } else if (pos.get(j).equals("Numbers")) {

                    hw.add(rword);
                    uw.add("icl>number");
                    tuid.add("None");
                    cuid.add("None");
                    no.add(new Integer(count++));
                    //scpno.add(":0" + new Integer(scpcnt++));
                    rootnew.add(rword);
                } else {
                    if (UnknownList.isEmpty()) {
                        UnknownList.add(rword.trim());
                    } else if (UnknownList.contains(rword.trim())) {
                    } else {
                        UnknownList.add(rword.trim());
                    }
                    rootnew.add("None");
                    hw.add("None");
                    uw.add("None");
                    tuid.add("None");
                    cuid.add("None");
                    no.add("None");
                    //scpno.add(":00");
                }
                // //System.out.println("ROOT+POS:"+rootnew.get(j).toString()+"/"+pos.get(j).toString());
                pnentry = rootnew.get(j).toString() + "/"
                        + pos.get(j).toString() + "/" + uw.get(j).toString();
                // //System.out.println("PNENTRY:"+pnentry);
                if ((pnentry.contains("Entity")) || (pnentry.contains("Noun"))) {
                    if (pnounuwList.isEmpty()) {
                        pnounuwList.add(pnentry);
                    } else if (pnounuwList.contains(pnentry)) {
                        int k = pnounuwList.indexOf(pnentry);
                        // //System.out.println("Index:"+k);
                        pnounuwList.remove(k);
                        if (pnounuwList.size() < 1) {
                            pnounuwList.add(0, pnentry);
                        } else {
                            pnounuwList.add(pnounuwList.size() - 1, pnentry);
                        }
                    } else {
                        pnounuwList.add(pnentry);
                    }
                }
                // freqCount(rword);
                // //System.out.println("J inside while:"+j);
                j++;
            }// end of while
            /**
             * ***** WORD SENSE DISAMBIGUATION ******
             */
            Process_For_Disambiguation();
//			int flag=0;
            for (int l = 0; l < wsd_ll.size(); l++) {
                LinkedList get_all = (LinkedList) wsd_ll.get(l);
                String wsd_tw = get_all.get(0).toString();
                String wsd_hw = get_all.get(1).toString();
                String wsd_uw = get_all.get(2).toString();
                String wsd_tuid = get_all.get(3).toString();
                String wsd_cuid = get_all.get(4).toString();
                for (int m = 0; m < root.size(); m++) {
                    if (hw.get(m).toString().contains("$$$")) {
                        int index = hw.indexOf("$$$");
                        String tw = root.get(index).toString();
                        if (tw.equals(wsd_tw)) {
                            hw.remove(index);
                            hw.add(index, wsd_hw);
                            uw.remove(index);
                            uw.add(index, wsd_uw);
                            tuid.remove(index);
                            tuid.add(index, wsd_tuid);
                            cuid.remove(index);
                            cuid.add(index, wsd_cuid);
                            break;
                        } else {
                            //int index = hw.indexOf("$$$");
                            LinkedList ll2 = returnFirstHit(tw);
                            rootnew.remove(index);
                            rootnew.add(ll2.get(0).toString());
                            hw.remove(index);
                            hw.add(index, ll2.get(1).toString());
                            uw.remove(index);
                            uw.add(index, ll2.get(2).toString());
                            tuid.remove(index);
                            tuid.add(index, ll2.get(3).toString());
                            cuid.remove(index);
                            cuid.add(index, ll2.get(4).toString());
                            no.add(new Integer(count++));

                            //flag=-1;
                            break;
                        }
                    }
                }
//				if(flag==-1){break;}
            }
            wsd_ll.clear();
            /**
             * ***** MULTIWORD EXTRACTION ******
             */
            Process_For_MWExtraction();
            if (mwlist != null && !mwlist.isEmpty()) {
                for (int len = 0; len < mwlist.size(); len++) {
                    String mword = mwlist.get(len).toString();
                    StringTokenizer strToken6 = new StringTokenizer(mword, "/");
                    String mwe_tw = strToken6.nextToken();
                    String mwe_hw = strToken6.nextToken();
                    String mwe_uw = strToken6.nextToken();
                    String mwe_lc = strToken6.nextToken();
                    String mwe_tuid = strToken6.nextToken();
                    String mwe_cuid = strToken6.nextToken();
                    String mwe_anal = strToken6.nextToken();
                    String new_mwpos = "Entity";
                    rootnew.add(mwe_tw);
                    hw.add(mwe_hw);
                    uw.add(mwe_uw);
                    pos.add(new_mwpos);
                    tuid.add(mwe_tuid);
                    cuid.add(mwe_cuid);
                    anal.add(mwe_anal);
                    no.add(new Integer(count++));
                    //	System.out.println("MWE:------->"+mwe_tw);
                }
            }
            /**
             * *******SNIPPET GENERATION************
             */
            int lineNumber = 0;
            //s_g.globalDocid = sni_did;
            //System.out.println("Size:"+root.size()+"|"+wordAL.size());	
            //System.out.println(wordAL+ "\n"+root);
		/*
             * for(int si=0;si<root.size();si++){ if(
             * (pos.get(si).toString().contains("Entity")) ||
             * pos.get(si).toString().contains("Noun") ){ String uw_con =
             * hw.get(si).toString()+"("+uw.get(si).toString()+")"; String
             * tamilword = wordAL.get(si).toString(); String rootword =
             * root.get(si).toString(); String
             * line=s_g.SnippetArrangement(sente, tamilword, rootword); String[]
             * output = s_g.Linesplit(line, tamilword, rootword, lineNumber); //
             * System.out.println("Snippet
             * Output:"+"\n"+rootword+"\n"+lineNumber+"\n"+line); s_g.snippets =
             * s_g.Snippetput(s_g.snippets, rootword + uw_con, output); } }
             */

            // to append all concepts

            for (int k = 0; k < hw.size(); k++) {
                if (!(rootnew.get(k).equals("None"))) {
                    resultuw.append(rootnew.get(k).toString() + ';');
                    resultuw.append(hw.get(k).toString() + ';');
                    resultuw.append(uw.get(k).toString() + ';');
                    //resultuw.append(unl_attributes.get(k).toString() + ';');
                    resultuw.append(pos.get(k).toString() + ';');
                    resultuw.append(tuid.get(k).toString() + ';');
                    resultuw.append(cuid.get(k).toString() + ';');
                    resultuw.append(no.get(k).toString() + '#');
                    //resultuw.append(scpno.get(k).toString() + '#');
                    // resultuw.append(fcount.get(k).toString()+';');
                }
            }
            result += "[w]#";
            result += resultuw;
            result += "[/w]#";
            //	System.out.println("ResultUW------------>:"+resultuw);
            // //System.out.println("Result ++++++++++++++++:"+result);
        } catch (Exception e) {
            e.printStackTrace();
            //System.err.println(rootnew.size()+"|"+hw.size()+"|"+uw.size()+"|"+pos.size()+"|"+tuid.size()+"|"+cuid.size()+"|"+no.size()+"|");
//System.err.print(rootnew+"\n"+pos);
        }
        // //System.out.println("Resultuw:"+result);
    }

    public void Process_For_Disambiguation() {
        try {
            // System.out.println(ambiguousList+"\n"+rootnew+"\n"+pos+"\n"+hw+"\n"+uw+"\n"+anal);
            wsd_ll = (LinkedList) wsd.processWSD(anal, rootnew, pos, hw, uw, tuid, cuid, ambiguousList);
            // System.out.println("WSD RESULT:"+wsd_ll);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Process_For_MWExtraction() {
        try {
            //	System.out.println("Inside MWE");
            mwlist = MWE.processMW(anal, rootnew, pos);
            //	System.out.println("MWLIST*************:"+mwlist);
        } catch (Exception e) {
        }
    }
    //

    public LinkedList returnFirstHit(String tw_word) {
        LinkedList fh_ll = new LinkedList();
        try {
            int hc = tw_word.hashCode();
            if (uwdict.uwdict.containsKey(hc)) {
                LinkedList ll1 = (LinkedList) uwdict.uwdict.get(hc);
                fh_ll = (LinkedList) ll1.getFirst();
            }
        } catch (Exception e) {
        }
        // System.out.println("FIRSTHIT:"+fh_ll);
        return fh_ll;
    }

    //
    public String findverbnumber(String v) {
        int tempindex;
        int totalrw;

        int totrw = root.size();
        // //System.out.println("totrw:"+totrw);
        try {
            tempindex = root.indexOf(v.trim());
            // //System.out.println("tempindex:"+tempindex);
            if (tempindex != -1) {
                return (no.get(tempindex).toString().trim());
            }

        } catch (Exception e) {
        }
        return ("0");
    }

    public void chkIfPerson() {
        for (int i = 0; i < RefExp.size(); i++) {
            String chkWord = RefExp.get(i).toString();
            // String chkPos = pos.get(i).toString();
            //int hc = chkWord.hashCode();
            String hc = new String(Base64.encodeBase64(("" + chkWord).getBytes())); // Modified

//            if ((bn = dict.search(hc)) != null) {
//                if ((bn.constraint.contains("person"))
//                        || (bn.constraint.contains("place"))
//                        || (bn.constraint.contains("temple"))
//                        || (bn.constraint.contains("facilities"))) {
//                    personUW.add(bn.lexeme + "/Entity/" + bn.constraint);
//                }
//
//            }
            if (uwdict.uwdict.containsKey(hc)) {
                LinkedList ll = (LinkedList) uwdict.uwdict.get(hc);
                //	System.out.println("ll>>>>>>>>>>>>"+rword+"|"+hc +"\t"+ll.size());
                if (ll.size() == 1) {
                    LinkedList ll_entry = (LinkedList) ll.getLast();
                    personUW.add(ll_entry.get(0) + "/Entity/" + ll_entry.get(2));
                }
            }
        }
    }

    public void addMorphology(String analmod) {
        int k = anal.indexOf(analmod);
        String[] chkanal;// = "";
        String addPOS = "";
        // //System.out.println("k:"+k);
        if (analmod.contains("400")) {
            chkanal = analmod.split("400");
            // //System.out.println("chkanal for Adverb Ingu:"+chkanal[0]+":"+chkanal[1]);
            addPOS = "Entity & 50 >" + " " + "இல்" + " "
                    + "< Locative Case & 504" + chkanal[1];
        } else if (analmod.contains("101")) {
            chkanal = analmod.split("101");
            // //System.out.println("chkanal for Pronoun:"+chkanal[0]+":"+chkanal[1]);
            addPOS = "Entity & 50 " + chkanal[1];
        }
        anal.remove(k);
        anal.add(k, addPOS);
        // //System.out.println("anal:"+anal);
    }

    /**
     * Various Rules are defined for UNL Relations. Rule1 is defined for the UNL
     * relations: frm, plf, src, tmf. Based on the UNL Constraints the relations
     * will vary. These relations are obtained by applying the Rule: <"இல்",
     * "ஆக", "இடம்">-> <"இருந்து">
     */
    public void rule1() {

        if ((j + 1) != total) {
            String anal2 = anal.get(j + 1).toString().trim();

            if (anal2.indexOf("Relative Participle") != -1) {
                u_word = uw.get(j).toString();
                if ((u_word.indexOf("place") != -1)
                        || (u_word.indexOf("city") != -1)
                        || (u_word.indexOf("district") != -1)
                        || (u_word.indexOf("organization") != -1)
                        || (u_word.indexOf("temple") != -1)
                        || (u_word.indexOf("facilities") != -1)
                        || (u_word.indexOf("country") != -1)
                        || (u_word.indexOf("lake") != -1)
                        || (u_word.indexOf("vehicle") != -1)
                        || (u_word.indexOf("body") != -1)) {
                    rootrel.set(j, "frm");
                    rel_label = "frm";
                    findRelation_Category5(j + 1, j, rel_label);
                }
            } else {
                plfindex = j;
                rootrel.set(j, "plf" + "src");
                u_word = uw.get(j).toString();
                if ((u_word.indexOf("place") != -1)
                        || (u_word.indexOf("city") != -1)
                        || (u_word.indexOf("district") != -1)
                        || (u_word.indexOf("organization") != -1)
                        || (u_word.indexOf("temple") != -1)
                        || (u_word.indexOf("facilities") != -1)
                        || (u_word.indexOf("country") != -1)
                        || (u_word.indexOf("lake") != -1)) {
                    rel_label = "plf";
                } else if (u_word.indexOf("time") != -1) {
                    rel_label = "tmf";
                } else if (u_word.indexOf("event") != -1) {
                    rel_label = "scn";
                } else {
                    // rel_label = "src";
                }
                findRelation_Category2(j, rel_label);
            }
        } else {
            rootrel.set(j, "frm");
            rel_label = "frm";
            findRelation_Category2(j, rel_label);
        }
    }

    /**
     * Rule2 is defined for the UNL Relation: 'and'. The 'and' relation is
     * obtained by the following rules applied: "மட்டுமல்லாமல்", "மற்றும்",
     * "மேலும்", "உம்".
     *
     */
    public void rule2() // mattumallamal
    {
        // flag2=1;
        rootrel.set(j, "and");
        rel_label = "and";
        findRelation_Category1(j, rel_label);
    }

    /**
     * Rule3 is defined for the UNL Relation: ' via' The 'via'relation is
     * obtained by applying the Rule: "வழியாக"
     */
    public void rule3() // Valiyaga
    {
        rootrel.set(j, "via");
        // rel_label = "via";
        // findRelation_Category4(j, rel_label);
        u_word = uw.get(j - 1).toString();
        if ((u_word.indexOf("place") != -1) || (u_word.indexOf("city") != -1)
                || (u_word.indexOf("district") != -1)
                || (u_word.indexOf("organization") != -1)
                || (u_word.indexOf("temple") != -1)
                || (u_word.indexOf("facilities") != -1)
                || (u_word.indexOf("country") != -1)
                || (u_word.indexOf("lake") != -1)) {

            rel_label = "via";
            // rootrel.set(j, "via");
            findRelation_Category4(j, rel_label);
        }

    }

    /**
     * Rule4 is defined for the UNL Relation: 'and' 'ptn'. The 'and' and 'ptn'
     * relations are obtained by the following Rule: "உம்" and the position of
     * the analysed string.
     */
    public void rule4() {

        String pos1 = pos.get(j).toString();
        if ((j + 1) != total) {
            String pos2 = pos.get(j + 1).toString();
            String anal2 = anal.get(j + 1).toString().trim();
            if ((pos1.equals(pos2)) && (anal2.indexOf("உம்") != -1)) {
                rootrel.set(j, "and" + "ptn");
                rel_label = "ptn";
                findRelation_Category3(j + 1, rel_label);

                rel_label = "and";
                findRelation_Category5(j + 1, j, rel_label);
            }
        }
    }

    /**
     * Rule5 is defined for the UNL Realtion: 'agt'. The 'agt' relation is
     * obtained by checking the Instrumental case (i.e. 'ins'relation) The Rule
     * for 'agt' reltion is "படு","அது". The "insindex" Rule is "ஆல்".
     */
    public void rule5() {

        if (insindex == 1) {
            int i = rootrel.indexOf("ins");
            u_word = uw.get(i).toString();
            if ((u_word.indexOf("person") != -1)
                    || (u_word.indexOf("name") != -1)
                    || (u_word.indexOf("relative") != -1)) {
                rootrel.set(j, "agt");
                rel_label = "agt";
                findRelation_Category2(i, rel_label);
            }
        }
    }

    /**
     * Rule6 is defined for the UNL Relations: 'con', 'ins', 'met'. These
     * relations are obtained by applying the Rule: "ஆல்" The insindex is set
     * only when the concept is instrument, flower, vechicle etc.. The 'con'
     * relation is obtained by checking with the verb. And if the concept is a
     * person, name or relative then the 'met' relation is obtained.
     */
    public void rule6() {

        if (pos.get(j).toString().equals("Verb")) {
            rootrel.set(j, "con");
            rel_label = "con";
            findRelation_Category1(j, rel_label);
        } else {
            insindex = 1;
            rootrel.set(j, "ins");
            u_word = uw.get(j).toString();

            if ((u_word.indexOf("instrument") != -1)
                    || (u_word.indexOf("flower") != -1)
                    || (u_word.indexOf("vehicle") != -1)
                    || (u_word.indexOf("stationery") != -1)
                    || (u_word.indexOf("rock") != -1)) {
                rel_label = "ins";
                findRelation_Category2(j, rel_label);
            } else if ((u_word.indexOf("person") != -1)
                    || (u_word.indexOf("name") != -1)
                    || (u_word.indexOf("relative") != -1)) {
                rel_label = "met";
                findRelation_Category2(j, rel_label);
            }
        }
    }

    /**
     * Rule7 is defined for the UNL Relation: 'plt'. The 'plt' Relation is
     * obtained by applying the Rule: "க்கு"
     */
    public void rule7() {
        rootrel.set(j, "plt");
        rel_label = "plt";
        findRelation_Category1(j, rel_label);
    }

    /**
     * Rule8 is defined for the UNL Relation: 'bas'. The 'bas' relation is
     * obtained by applying the Rule: <"காட்டிலும்", "விட">
     */
    public void rule8() {

        rootrel.set(j, "bas");
        rel_label = "bas";
        findRelation_Category1(j, rel_label);
    }

    /**
     * Rule9 is defined for the UNL Relation: 'ben'. The 'ben' relation is
     * obtained by applying the Rule:"உக்கு", "ஆக" By Checking the UW word
     * constraint the relation is set to the corresponding concepts.
     */
    public void rule9() {

        rootrel.set(j, "ben");
        u_word = uw.get(j).toString();
        if ((u_word.indexOf("person") != -1) || (u_word.indexOf("place") != -1)
                || (u_word.indexOf("vehicle") != -1)
                || (u_word.indexOf("city") != -1)
                || (u_word.indexOf("district") != -1)
                || (u_word.indexOf("organization") != -1)
                || (u_word.indexOf("temple") != -1)
                || (u_word.indexOf("facilities") != -1)
                || (u_word.indexOf("country") != -1)
                || (u_word.indexOf("lake") != -1)
                || (u_word.indexOf("name") != -1)
                || (u_word.indexOf("relative") != -1)) {
            rel_label = "ben";
            findRelation_Category2(j, rel_label);
        }
    }

    /**
     * Rule10 is defined for the UNL Realtions: 'cag', 'cao', 'cob'. These
     * reltions are obtained by setting the codindex and By applying the Rule:
     * "உடன்", "ஓடு".
     */
    public void rule10() {

        // int cobindex;

        rootrel.set(j, "cag" + "cao" + "cob");
        int cobindex = 1;
        u_word = uw.get(j).toString();

        if ((u_word.indexOf("person") != -1) || (u_word.indexOf("place") != -1)
                || (u_word.indexOf("vehicle") != -1)
                || (u_word.indexOf("city") != -1)
                || (u_word.indexOf("district") != -1)
                || (u_word.indexOf("organization") != -1)
                || (u_word.indexOf("temple") != -1)
                || (u_word.indexOf("facilities") != -1)
                || (u_word.indexOf("country") != -1)
                || (u_word.indexOf("lake") != -1)
                || (u_word.indexOf("name") != -1)
                || (u_word.indexOf("relative") != -1)) {
            rootrel.set(j, "cag");
            rel_label = "cag";
            findRelation_Category3(j, rel_label);
        } else {
            rel_label = "cao";
            findRelation_Category5(j + 1, j, rel_label);
        }
    }

    /**
     * Rule11 is defined for the UNL Relation: 'cnt'. The 'cnt' Relation is
     * obtained by applying the Rule: "என்பது".
     */
    public void rule11() {

        rootrel.set(j, "cnt");
        rel_label = "cnt";
        findRelation_Category1(j, rel_label);
    }

    /**
     * Rule12 is defined for the UNL Relation: 'coo'. The 'coo' relation is
     * obtained by applying the Rule:<"கொண்டே", "அதேவேளை">
     */
    public void rule12() {

        rootrel.set(j, "coo");
        rel_label = "coo";
        findRelation_Category1(j, rel_label);
    }

    /**
     * Rule13 is defined for the UNL Relation: 'dur'. The 'dur' relation is
     * obtained by applying the Rule: <"பொழுது", "போது">
     */
    public void rule13() {
        rootrel.set(j, "dur");
        rel_label = "dur";
        findRelation_Category4(j, rel_label);
    }

    /**
     * Rule14 is defined for the UNL Realtion: 'equ'. The 'equ' relation is
     * obtained by applying the Rule: "என்னும்".
     */
    public void rule14() {
        rootrel.set(j, "equ");
        for (int i = 0; i < uw.size(); i++) {
            u_word = uw.get(i).toString();
            if (u_word.indexOf("iof>place") != -1) {
                rel_label = "equ";
                findRelation_Category5(j + 1, i, rel_label);
            }

        }
    }

    /**
     * Rule15 is defined for the UNL Relation: 'plf', 'tmf'. These Relations are
     * obtained by applying the following Rules: <"முதல்", "இருந்து">
     */
    public void rule15() {
        rootrel.set(j, "plf");
        for (int i = j - 1; i >= 0; i--) {
            u_word = uw.get(i).toString();
            if ((u_word.indexOf("place") != -1)
                    || (u_word.indexOf("city") != -1)
                    || (u_word.indexOf("district") != -1)
                    || (u_word.indexOf("organization") != -1)
                    || (u_word.indexOf("temple") != -1)
                    || (u_word.indexOf("facilities") != -1)
                    || (u_word.indexOf("country") != -1)
                    || (u_word.indexOf("lake") != -1)) {
                plfindex = i;
                rel_label = "plf";
                findRelation_Category2(i, rel_label);
                break;
            } else if ((u_word.indexOf("month") != -1)
                    || (u_word.indexOf("time") != -1)) {
                plfindex = i;
                rel_label = "tmf";
                findRelation_Category2(i, rel_label);
                break;
            }
        }
        /**
         * if(plfindex > 0) { rel_label = "fmt";
         * findRelation_Category5(plfindex,i,rel_label); }
         */
    }

    /**
     * Rule16 is defined for the UNL Relation: 'plt', 'gol', 'tmt'. These
     * relations are obtained by applying the Rules: "க்கு".
     */
    public void rule16() {

        rootrel.set(j, "plt" + "gol");
        u_word = uw.get(j).toString();
        if ((u_word.indexOf("place") != -1) || (u_word.indexOf("city") != -1)
                || (u_word.indexOf("district") != -1)
                || (u_word.indexOf("organization") != -1)
                || (u_word.indexOf("temple") != -1)
                || (u_word.indexOf("facilities") != -1)
                || (u_word.indexOf("country") != -1)
                || (u_word.indexOf("lake") != -1)) {
            rel_label = "plt";
        } else if ((u_word.indexOf("time") != -1)
                || (u_word.indexOf("month") != -1)) {
            rel_label = "tmt";
        } else if ((u_word.indexOf("person") != -1)
                || (u_word.indexOf("god") != -1)) {
            rel_label = "to";
        } else {
            rel_label = "gol";
            findRelation_Category5(j + 1, j, rel_label);
        }
        findRelation_Category2(j, rel_label);
    }

    /**
     * Rule17 is defined for the UNL Relation: 'plt' The 'plt' relation is
     * obtained by applying the Rule: "வரை"
     */
    public void rule17() {

        rootrel.set(j, "plt");

        for (int i = j - 1; i >= 0; i--) {
            u_word = uw.get(i).toString();
            if ((u_word.indexOf("place") != -1)
                    || (u_word.indexOf("city") != -1)
                    || (u_word.indexOf("district") != -1)
                    || (u_word.indexOf("organization") != -1)
                    || (u_word.indexOf("temple") != -1)
                    || (u_word.indexOf("facilities") != -1)
                    || (u_word.indexOf("country") != -1)
                    || (u_word.indexOf("lake") != -1)) {
                rel_label = "plt";
                findRelation_Category2(i, rel_label);
                break;
            } else if ((u_word.indexOf("time") != -1)
                    || (u_word.indexOf("month") != -1)) {
                rel_label = "tmt";
                findRelation_Category2(i, rel_label);
                break;
            }
        }
        /**
         * if(plfindex > 0) { rel_label = "fmt";
         * findRelation_Category5(plfindex,i,rel_label); }
         */
    }

    /**
     * Rule18 is defined for the UNL Relation: 'ins', 'obj'. These relations are
     * obtained by applying the Rule: "ஐ"
     */
    public void rule18() {
        rootrel.set(j, "ins" + "obj");
        u_word = uw.get(j).toString();
        if ((u_word.indexOf("instrument") != -1)
                || (u_word.indexOf("vehicle") != -1)
                || (u_word.indexOf("stationery") != -1)) {
            rel_label = "ins";
        } else {
            rel_label = "obj";
        }
        findRelation_Category2(j, rel_label);
        //break;
    }

    /**
     * Rule19 is defined for the UNL Relation: 'plc' The 'plc' relation is
     * obtained by applying the Rule: "இங்கு", "அங்கு" with the Pronoun Checking
     * is true.***
     */
    public void rule19() {
        rootrel.set(pr_index, "plc");
        rel_label = "plc";
        findRelation_Category4(j, rel_label);
    }

    /**
     * Rule20 is defined for the UNL Relation: 'pos' The 'pos' relation is
     * obtained by applying the Rule:"இன்"
     */
    public void rule20() {
        rootrel.set(j, "pos");
        rel_label = "pos";
        findRelation_Category5(j + 1, j, rel_label);
        // for(int i=j+1;i<total;i++)
        int i = j + 1;
        if (i != total) {
            u_word = uw.get(i).toString();
            if ((u_word.indexOf("place") != -1)
                    || (u_word.indexOf("city") != -1)
                    || (u_word.indexOf("district") != -1)
                    || (u_word.indexOf("organization") != -1)
                    || (u_word.indexOf("temple") != -1)
                    || (u_word.indexOf("facilities") != -1)
                    || (u_word.indexOf("country") != -1)
                    || (u_word.indexOf("lake") != -1)) {
                rootrel.set(i, pos);
                rel_label = "pos";
                findRelation_Category5(i, j, rel_label);
            } else {
                // break;
            }
        }
        i++;
    }

    /**
     * Rule21 is defined for the UNL Relation: 'ins', 'met'. These Relations are
     * obtained by applying the Rules:<"மூலம்","வைத்து","உபயோகி","கொண்டு">
     */
    public void rule21() {
        rootrel.set(j, "ins");
        u_word = uw.get(j - 1).toString();
        if ((u_word.indexOf("instrument") != -1)
                || (u_word.indexOf("flower") != -1)
                || (u_word.indexOf("vehicle") != -1)
                || (u_word.indexOf("stationery") != -1)) {
            rel_label = "ins";
        } else {
            rel_label = "met";
        }
        findRelation_Category4(j, rel_label);
    }

    /**
     * Rule22 is defined for the UNL Relation: 'int'. The 'int' relation is
     * obtained by applying the Rule: "பொது".
     */
    public void rule22() {

        rootrel.set(j, "int");
        rel_label = "int";
        findRelation_Category5(j - 1, j - 2, rel_label);
    }

    /**
     * Rule23 is defined for the UNL Relation: 'man'. The 'man' relation is
     * obtained by applying the Rule:"மிக"
     */
    public void rule23() {
        rootrel.set(j, "man");
        rel_label = "man";
        findRelation_Category5(j + 1, j, rel_label);
    }

    /**
     * Rule24 is defined for the UNL Relation: 'man' by appling the Rule:"இங்கு"
     * with the Adverb.
     */
    public void rule24() {
        rootrel.set(j, "man");
        rel_label = "man";
        findRelation_Category2(j, rel_label);
    }

    /**
     * Rule25 is defined for the UNL Relation: 'mod' by applying the Rule:
     * Adjective comes as a concept.
     */
    public void rule25() {
        rootrel.set(j, "mod");
        rel_label = "mod";
        findRelation_Category5(j + 1, j, rel_label);
    }

    /**
     * Rule26 is defined for the UNL Relation: 'nam' by applying the
     * Rules:<"ஆகிய","போன்ற","முதலிய"> and by checking the next word equals
     * <"இடம்","நாள்","நாடு">
     */
    public void rule26() {
        String w1 = root.get(j + 1).toString().trim();

        if ((w1.equals("இடம்")) || (w1.equals("நாள்")) || (w1.equals("நாடு"))) {
            rootrel.set(j, "nam");
            h_word = hw.get(j + 1).toString();
            // //////////System.out.println("h_word:1" + h_word);
            for (int i = 0; i < uw.size(); i++) {
                u_word = uw.get(i).toString();
                // //////////System.out.println("u_word:1" + u_word);
                if (u_word.indexOf(h_word) != -1) {
                    rel_label = "nam";
                    findRelation_Category5(j + 1, i, rel_label);
                }

            }
        }
    }

    /**
     * Rule27 is defined for the UNL Relation: 'or'. The Rules applied for the
     * 'OR' relation is:"அல்லது"
     */
    public void rule27() {
        rootrel.set(j, "or");
        rel_label = "or";
        findRelation_Category1(j, rel_label);
    }

    public void rule28() {
        rootrel.set(j, "per");
        rel_label = "per";
        findRelation_Category4(j, rel_label);
    }

    public void rule29() {
        if ((pos.get(j).toString().equals("Noun"))
                || (pos.get(j).toString().equals("Entity"))) {
            rootrel.set(j, "pos");
            rel_label = "pos";
            findRelation_Category5(j + 1, j, rel_label);
        } else {
            aojindex = j;
            rel_label = "aoj";
        }
    }

    public void rule30() {
        if (pos.get(j).toString().equals("Verb")) {
            rootrel.set(j, "pur");
            rel_label = "pur";
            findRelation_Category2(j, rel_label);
        }
    }

    public void rule31() {
        rootrel.set(j, "qua");
        rel_label = "qua";
        findRelation_Category5(j + 1, j, rel_label);
    }

    public void rule32() {
        rootrel.set(j, "rsn");
        rel_label = "rsn";
        findRelation_Category4(j, rel_label);
    }

    /**
     * rule33 is defined for the UNL Relation: 'seq'. The 'seq' relation is
     * obtained by applying the Rule:
     */
    public void rule33() {
        rootrel.set(j, "seq");
        rel_label = "seq";
        findRelation_Category1(j, rel_label);
    }

    public void rule34() {
        timindex = 1;
        rootrel.set(j - 1, "tim");
        u_word = uw.get(j - 1).toString();
        if (u_word.indexOf("number") != -1) {
            rel_label = "tim";
            findRelation_Category4(j, rel_label);
        }
    }

    public void rule35() {
        rootrel.set(j, "iof");
        h_word = hw.get(j).toString();
        for (int i = 0; i < uw.size(); i++) {
            u_word = uw.get(i).toString();
            if (u_word.indexOf(h_word) != -1) {
                rel_label = "iof";
                findRelation_Category5(j, i, rel_label);
            } else {
                break;
            }
        }
    }

    public void rule36() {
        rootrel.set(j, "pof");
        h_word = hw.get(j).toString();
        for (int i = 0; i < uw.size(); i++) {
            u_word = uw.get(i).toString();
            if (u_word.indexOf(h_word) != -1) {
                rel_label = "pof";
                findRelation_Category5(j, i, rel_label);
            }
        }
    }

    /**
     * Rule37 is defined for the UNL Relation: 'aoj'. The 'aoj' relation is
     * obtained by applying the Rules:
     * <"உள்ளது","இருக்கும்","உள்ளன","நிறைந்தது","ஆகும்","உண்டு", "பற்றி",
     * "பற்றிய">
     */
    public void rule37() {
        // flag3=1;
        aojindex = j;
        rootrel.set(j, "aoj");
        rel_label = "aoj";
        findRelation_Category4(j, rel_label);
    }

    public void rule38() {

        rootrel.set(j, "plc");
        u_word = uw.get(j).toString();
        if (u_word.indexOf("pof<body") != -1) {
            rel_label = "opl";
            findRelation_Category2(j, rel_label);
        } else if ((u_word.indexOf("place") != -1)
                || (u_word.indexOf("city") != -1)
                || (u_word.indexOf("district") != -1)
                || (u_word.indexOf("organization") != -1)
                || (u_word.indexOf("temple") != -1)
                || (u_word.indexOf("facilities") != -1)
                || (u_word.indexOf("country") != -1)
                || (u_word.indexOf("lake") != -1)
                || (u_word.indexOf("iof>state") != -1)) {
            rel_label = "plc";
            findRelation_Category2(j, rel_label);
        } else if (u_word.indexOf("time") != -1) {
            rel_label = "tim";
            findRelation_Category2(j, rel_label);
        }
    }

    /**
     * Rule39 is defined for the UNL Relation: 'neg'. The 'neg' relation is
     * obtained by applying the Rules:<"அல்லாத","நீங்கலாக">
     */
    public void rule39() {
        rootrel.set(j, "neg");
        rel_label = "neg";
        findRelation_Category4(j, rel_label);
    }

    /**
     * Rule40
     *
     */
    public void rule40() {
        rootrel.set(j, "agt");
        rel_label = "agt";
        findRelation_Category2(j, rel_label);
    }

    /**
     * Rule41 is defined for the UNL Relation: 'icl'. The 'icl' relation is
     * obtained by applying the Rule: <"உள்ளிட்ட","உள்பட","உட்பட">
     */
    public void rule41() {
        rootrel.set(j, "icl");
        rel_label = "icl";
        findRelation_Category4(j, rel_label);
    }

    /**
     * Rule42 is defined for the UNL Relation: 'obj'. The 'obj' relation is
     * obtained by applying the Rules:<"என","என்று">
     */
    public void rule42() {
        rootrel.set(j, "obj");
        rel_label = "obj";
        findRelation_Category4(j, rel_label);
    }

    /**
     * Rule43 is defined for the UNL Relation: 'int'. The 'int' relation is
     * obtained by applying the Rule:<"இடையில்","இடையே>
     */
    public void rule43() {
        rootrel.set(j, "int");
        rel_label = "int";
        findRelation_Category4(j, rel_label);
    }

    /**
     * Rule44 is defined for the UNL Relation: 'aoj'. The 'aoj' relation is
     * obtained by applying the Rule:
     */
    /**
     * public void Rule44() { rootrel.set(j, "aoj"); rel_label = "aoj";
     * findRelation_Category4(j, rel_label); }
     */
    public void rule45() {
        for (int i = j; i < root.size(); i++) {
            u_word = uw.get(i).toString();
            if ((u_word.indexOf("place") != -1)
                    || (u_word.indexOf("city") != -1)
                    || (u_word.indexOf("district") != -1)
                    || (u_word.indexOf("organization") != -1)
                    || (u_word.indexOf("temple") != -1)
                    || (u_word.indexOf("facilities") != -1)
                    || (u_word.indexOf("country") != -1)
                    || (u_word.indexOf("lake") != -1)) {
                // rootrel.set(i, "plt");
                rel_label = "plt";
                findRelation_Category5(i, j, rel_label);
                break;
            }
        }
    }

    /**
     * Rule46 is defined for the UNL Relation: 'iof'. The 'iof' relation is
     * obtained by applying the Rule:
     */
    /**
     * Rule47 is defined for the UNL Relation: 'coo'. The 'coo' relation is
     * obtained by applying the Rule: "போதிலும்".
     */
    public void rule47() {
        rootrel.set(j, "coo");
        rel_label = "coo";
        findRelation_Category1(j, rel_label);
    }

    public void rule48() {
        // //System.out.println("Inside RULE 48");
        check_CaseSuffix();
        if (chk_suffix == true) {
            // //System.out.println("Inside chk RULE 48");
            rel_label = "obj";
            findRelation_Category5(j, ind_obj, rel_label);
            chk_suffix = false;
            //break;
        }
    }

    /**
     * Rule49 is defined for the UNL Relation: 'plt'. The 'plt' relation is
     * obtained by checking the uword constraint: "place". Even if the 'plf'
     * will obtained by <"இல்","இருந்து">, its not necessary that when the 'plt'
     * relation will comes with <"க்கு">.
     */
    public void rule49() {

        u_word = uw.get(j).toString();
        if (u_word.indexOf("place") != -1) {
            rootrel.set(j, "plt");
            int i;
            if ((j + 1) != total) {
                if (analysed.indexOf("இல்") != -1) // il
                {
                    if ((analysed.indexOf("இருந்து") != -1)) // irundhu
                    {
                        rootrel.set(j, "plt");
                        rel_label = "plt";
                        findRelation_Category4(j, rel_label);
                    }
                }
            }
        }
    }

    /**
     * Rule50 is defined for the UNL Relation: 'pof'. The 'pof' relation is
     * obtained by applying the Rule:"number". If the number street no.,door
     * no.,etc. The condition is checked for the number not comes as a year,
     * date, etc..
     */
    public void rule50() {
        if ((j - 1) != total) {
            u_word = uw.get(j - 1).toString();
            if (u_word.indexOf("place") != -1) {
                tempword = root.get(j + 1).toString().trim();

                if (!((tempword.equals("ஆம்")) || (tempword.equals("இல்"))
                        || (tempword.equals("ம்")) || (tempword.equals("ல்")))) {
                    rootrel.set(j, "pof");
                    rel_label = "pof";
                    findRelation_Category5(j, j - 1, rel_label);
                }
            }
        }
        /**
         * if ((j - 2) != total) { tempword = root.get(j - 2).toString(); //
         * //////////System.out.println("BALA TEMPWORD TELEPHONE"); if
         * (!((tempword.equals("அறை")) || (tempword .equals("வண்டி")))) { //
         * //////////System.out.println("JI JI JI"); rootrel.set(j, "pos"); //
         * rel_label="pos"; // findRelation_Category5(j,j-2,rel_label); } }
         */
    }

    public void rule51() {
        for (int i = j + 1; i < root.size(); i++) {
            String posword = pos.get(i).toString();
            if ((posword.equals("Noun")) || (posword.equals("Entity"))) {
                rel_label = "mod";
                findRelation_Category5(j, i, rel_label);
                break;
            }
        }
    }

    /**
     *
     */
    public void rule52() {
        for (int i = j + 1; i < root.size(); i++) {
            String posword = pos.get(i).toString();
            if (posword.equals("Verb")) {
                rel_label = "seq";
                findRelation_Category5(i, j, rel_label);
                break;
            }
        }
    }

    /**
     * Rule53 is defined for the UNL Relation: 'pos'. The 'pos' relation is
     * obtained by applying the Rule: <"க்கு","உரிய">,"உள்ள".
     */
    public void rule53() {
        rootrel.set(j, "pos");
        rel_label = "pos";
        findRelation_Category5(j + 1, j, rel_label);
    }

    /**
     * Rule54 is defined for the UNL Relation: 'pof'. The 'pof' relation is
     * obtained by applying the Rule:"உட்பட்ட".
     */
    public void rule54() {
        rootrel.set(j, "pof");
        rel_label = "pof";
        findRelation_Category2(j, rel_label);
    }

    public void rule55() {
        rootrel.set(j, "mod");
        String uword1 = anal.get(j + 1).toString();
        if ((uword1.indexOf("person") != -1) || (uword1.indexOf("god") != -1)
                || (uword1.indexOf("goddess") != -1)) {
            rel_label = "mod";
            findRelation_Category5(j + 1, j, rel_label);
        }
    }

    public void rule56() {
        rootrel.set(j, "pos");
        String u_word1 = uw.get(j + 1).toString();
        if ((u_word1.indexOf("facilities") != -1)
                || (u_word1.indexOf("temple") != -1)) {
            rel_label = "pos";
            findRelation_Category5(j + 1, j, rel_label);
        }
    }

    public void rule57() {
        // for(int i=0;i<j;i++){
        String uwword = uw.get(j - 1).toString();
        if (((uwword.contains("person")) || (uwword.contains("god")))
                && (!anal.get(j - 1).toString().contains("502"))) {
            rel_label = "agt";
            findRelation_Category5(j, j - 1, rel_label);
            // break;
        }
        // }
    }

    public void rule58() {
        // //System.out.println("Inside Rule58");
        for (int i = j - 1; i > 0; i--) {
            String a_word = anal.get(i).toString();
            if (a_word.contains("Accusative")) {
                rel_label = "obj";
                findRelation_Category5(j, i, rel_label);
                break;
            }
            // }
        }
    }

    public void findCXCfood() {
        String u_word2 = uw.get(j + 1).toString();
        if (u_word2.contains("food")) {
            rootrel.set(j, "cxc");
            rel_label = "cxc";
            findRelation_Category5(j + 1, j, rel_label);
        }
    }

    public void findCXCentity() {
        String u_word2 = uw.get(j + 1).toString();
        if ((u_word2.contains("place")) || (u_word2.contains("food"))
                || (u_word2.contains("god")) || (u_word2.contains("goddess"))) {
            rootrel.set(j, "cxc");
            rel_label = "cxc";
            findRelation_Category5(j + 1, j, rel_label);
        }
    }

    public void check_CaseSuffix() {
        for (int i = j - 1; i > 0; i--) {
            String analword = anal.get(i).toString();
            String uword = uw.get(i).toString();
            if ((!(analword.contains("500"))) && (!(analword.contains("502")))
                    && (!(analword.contains("504")))
                    && (!(analword.contains("506")))
                    && (!(analword.contains("Clitic")))
                    && (!(uword.contains("person")))
                    && (!(uword.contains("place")))
                    && (!(uword.contains("city")))) {
                // if( (!(uword.contains("person"))) &&
                // (!(uword.contains("place"))) && (!(uword.contains("city")))
                // ){
                chk_suffix = true;
                // //System.out.println("chk_Suffix:" + chk_suffix);
                ind_obj = i;
                break;
                // }
            }
        }
    }

    public void findrelnindex() // throws Exception
    {

        j = 0;

        String analysed;
        String tempword;
        // //System.out.println("Anal inside Findrelnindex:");

        String rel_label;
        String h_word = " ";
        String u_word = " ";
        // String attribute = "";
        total = anal.size();
        result += "[r]#";
        // //System.out.println("RESULT:"+result);
        while (j < total) {
            try {
                // //////////System.out.println("j:"+j);
                rootrel.add("None");
                analysed = anal.get(j).toString().trim();
                tempword = root.get(j).toString().trim();
                u_word = uw.get(j).toString();
                if ((analysed.indexOf("இல்") != -1)
                        || (analysed.indexOf("ஆக") != -1)
                        || (analysed.indexOf("இடம்") != -1)) {
                    if ((analysed.indexOf("இருந்து") != -1)) {
                        // attribute = ".@from";
                        // unl_attributes.add(attribute);
                        rule1();
                        rule45();
                    }
                    if (analysed.indexOf("உள்ள") != -1) {
                        rule41();
                    }
                    if (analysed.indexOf("உம்") != -1) {
                        rule4();
                    } else {
                        // attribute = ".@in.@at";
                        // unl_attributes.add(attribute);
                        rule38();
                    }
                }
                if (analysed.indexOf("உம்") != -1) {
                    rule4();
                }
                if ((analysed.indexOf("படு") != -1)
                        && (analysed.indexOf("அது") != -1)) {
                    // attribute = ".@passive";
                    // unl_attributes.add(attribute);
                    rule5();
                }
                if (analysed.indexOf("ஆல்") != -1) {
                    // attribute = "@by";
                    // unl_attributes.add(attribute);
                    rule6();
                }
                if (analysed.indexOf("உக்கு") != -1) {
                    if (analysed.indexOf("ஆக") != -1) {
                        rule9();
                    }
                }
                if ((analysed.indexOf("உடன்") != -1)
                        || (analysed.indexOf("ஓடு") != -1)) {
                    // attribute = "@with";
                    // unl_attributes.add(attribute);
                    rule10();
                }
                if (analysed.indexOf("க்கு") != -1) {
                    if (analysed.indexOf("உரிய") != -1) {
                        rule53();
                    }
                    if (analysed.indexOf("உட்பட்ட") != -1) {
                        rule54();
                    }
                    if (analysed.indexOf("உம்") != -1) {
                        rule4();
                    } else {
                        rule16();
                    }
                }
                if (analysed.indexOf("ஐ") != -1) {
                    rule18();
                    //	rule42();
                    //	rule58();
                }
                if (analysed.indexOf("இன்") != -1) {
                    // attribute = "of";
                    // unl_attributes.add(attribute);
                    rule20();
                }
                if ((analysed.indexOf("உடைய") != -1)
                        || (analysed.indexOf("அது") != -1)) {
                    // attribute = "of";
                    // unl_attributes.add(attribute);
                    rule29();
                }
                if ((analysed.indexOf("ஆக") != -1)
                        || (analysed.indexOf("க்க") != -1)) {

                    rule30();
                }
                if (analysed.indexOf("ஆக") != -1) {
                    rule23();
                }
                if ((tempword.equals("உள்ளது"))
                        || (tempword.equals("இருக்கும்"))
                        || (tempword.equals("உள்ளன"))
                        || (tempword.equals("நிறைந்தது"))
                        || (tempword.equals("ஆகும்"))
                        || (tempword.equals("உண்டு"))
                        || (tempword.equals("பற்றி"))
                        || (tempword.equals("பற்றிய"))
                        || (analysed.indexOf("உள்ளது") != -1)
                        || (analysed.indexOf("இருக்கும்") != -1)
                        || (analysed.indexOf("உள்ளன") != -1)
                        || (analysed.indexOf("நிறைந்தது") != -1)
                        || (analysed.indexOf("ஆகும்") != -1)
                        || (analysed.indexOf("உண்டு") != -1)) {
                    rule37();
                }
                if ((analysed.indexOf("என") != -1) || (tempword.equals("என"))
                        || (tempword.equals("என்று"))) {
                    rule42();
                }
                if ((tempword.equals("மட்டுமல்லாமல்"))
                        || (tempword.equals("மற்றும்"))
                        || (tempword.equals("மேலும்"))) {
                    // attribute = "and";
                    // unl_attributes.add(attribute);
                    rule2();
                }
                if (tempword.equals("வழியாக")) {
                    // attribute = ".@through.@via";
                    // unl_attributes.add(attribute);
                    rule3();
                }
                if ((tempword.equals("காட்டிலும்")) || (tempword.equals("விட"))) {
                    // attribute = ".@than";
                    // unl_attributes.add(attribute);
                    rule8();
                }
                if (tempword.equals("என்னும்")) {
                    rule14();
                }
                if ((tempword.equals("முதல்")) || (tempword.equals("இருந்து"))) {
                    // attribute = ".@from.@since";
                    // unl_attributes.add(attribute);
                    rule15();
                }
                if ((tempword.equals("மூலம்")) || (tempword.equals("வைத்து"))
                        || (tempword.equals("உபயோகி"))
                        || (tempword.equals("கொண்டு"))) {
                    rule21();
                }
                if (tempword.equals("மிக")) {
                    rule23();
                }
                if ((tempword.equals("ஆகிய")) || (tempword.equals("போன்ற"))
                        || (tempword.equals("முதலிய"))) {
                    // attribute = ".@as.@like";
                    // unl_attributes.add(attribute);
                    rule26();
                }
                if (tempword.equals("அதனால்")) {
                    rule32();
                }
                if ((tempword.equals("அப்புறம்"))
                        || (tempword.equals("முன்னால்"))
                        || (tempword.equals("பின்னால்"))
                        || (tempword.equals("பின்னர்"))
                        || (tempword.equals("பின்பு"))) {
                    // attribute = ".@after.@behind";
                    // unl_attributes.add(attribute);
                    rule33();
                }
                if ((tempword.equals("ஆம்")) || (tempword.equals("இல்"))
                        || (tempword.equals("ம்")) || (tempword.equals("ல்"))) {
                    rule34();
                }
                if ((tempword.equals("அல்லாத"))
                        || (tempword.equals("நீங்கலாக"))) {
                    // attribute =
                    // ".@apart_from.@barring.@except.(@except.@if)";
                    // unl_attributes.add(attribute);
                    rule39();
                }
                if (tempword.equals("என்பது")) {
                    rule11();
                }
                if ((tempword.equals("கொண்டே")) || (tempword.equals("அதேவேளை"))) {
                    rule12();
                }
                if ((tempword.equals("பொழுது")) || (tempword.equals("போது"))) {
                    // attribute = ".@during.@while";
                    // unl_attributes.add(attribute);
                    rule13();
                }
                if (tempword.equals("வரை")) {
                    // attribute = ".@to";
                    // unl_attributes.add(attribute);
                    rule17();
                }
                if ((pr_res == true)
                        && ((pr.indexOf("இங்கு") != -1) || (pr.indexOf("அங்கு") != -1))) {
                    rule19();
                }
                if (tempword.indexOf("பொது") != -1) {
                    rule22();
                }
                if (((pr.indexOf("இங்கு") == -1))
                        && (analysed.indexOf("Adverb") != -1)) {
                    rule24();
                }
                if ((analysed.indexOf("Adjective") != -1)
                        || (analysed.indexOf("Adjectival Suffix") != -1)
                        || (analysed.indexOf("Adjectival Noun") != -1)) {
                    rule25();
                }
                if (tempword.equals("அல்லது")) {
                    // attribute = ".@or";
                    // unl_attributes.add(attribute);
                    rule27();
                }
                if (tempword.equals("முறை")) {
                    rule28();
                }
                if (tempword.equals("நிறைய")) {
                    // attribute = ".@qua";
                    // unl_attributes.add(attribute);
                    rule31();
                }
                if ((tempword.equals("நாடு")) || (tempword.equals("மாவட்டம்"))
                        || (tempword.equals("நகரம்"))
                        || (tempword.equals("மாநிலம்"))
                        || (tempword.equals("நதி")) || (tempword.equals("ஏரி"))
                        || (tempword.equals("பாலம்"))) {
                    rule35();
                }
                if ((tempword.equals("உடல்")) || (tempword.equals("மரம்"))) {
                    rule36();
                }
                if ((tempword.equals("உள்ளிட்ட")) || (tempword.equals("உள்பட"))
                        || (tempword.equals("உட்பட"))) {
                    // attribute = ".@including";
                    // unl_attributes.add(attribute);
                    rule41();
                }
                if ((tempword.equals("இடையில்")) || (tempword.equals("இடையே"))) {
                    // attribute = ".@amid.@among.@between";
                    // unl_attributes.add(attribute);
                    rule43();
                }
                if (tempword.equals("போதிலும்")) {
                    // attribute = ".@despite";
                    // unl_attributes.add(attribute);
                    rule47();
                }
                if (u_word.indexOf("number") != -1) {
                    rule50();
                    rule31();
                }
                if (tempword.equals("உள்ள")) {
                    rule53();
                }
                if ((tempword.equals("மட்டுமல்லாமல்"))
                        || (tempword.equals("அல்லாத"))) {
                    rule38();
                }
                if ((u_word.indexOf("city") != -1)
                        || (u_word.indexOf("state") != -1)
                        || (u_word.indexOf("place") != -1)
                        || (u_word.indexOf("country") != -1)
                        || (u_word.indexOf("region") != -1)
                        || (u_word.indexOf("facilities") != -1)
                        || (u_word.indexOf("temple") != -1)) {
                    rule55();
                }
                if ((u_word.indexOf("person") != -1)
                        || (u_word.indexOf("god") != -1)
                        || (u_word.indexOf("goddess") != -1)) {
                    rule56();
                }
                if (u_word.indexOf("food") != -1) {
                    findCXCfood();
                }
                if (u_word.indexOf("place") != -1) {
                    rule49();
                    findCXCentity();
                }
                if (u_word.indexOf("icl>action") != -1) {
                    // //System.out.println("Inside RPS IF");
                    //	rule48();
                }
                if (analysed.contains("Relative Participle Suffix")) {
                    // //System.out.println("Inside RPS");
                    rule51();
                    rule52();
                    rule57();
                    //	rule58();
                    if (u_word.indexOf("icl>action") != -1) {
                        //System.out.println("Inside RPS IF");
                        //	rule48();
                    }
                }
                if ((analysed.contains("Verbal Participle Suffix"))
                        || (analysed.contains("Particle"))) {
                    rule52();
                    rule57();
                    //	rule58();
                }
                if (((u_word.contains("person")) || (u_word.contains("god")))
                        && (!(analysed.contains("Accusative")))
                        && (!(analysed.contains("Dative")))) {
                    rule40();
                }
            } catch (Exception e) {
            }
            j++;
        }
        j = 0;
        // to find aoj,agt
        // lexical end none + aojindex - aoj
        // lexical end none + not aoj index - agt

        for (int i = 0; i < uw.size(); i++) {
            if ((rootrel != null) && (rootrel.size() > 0) && (i < rootrel.size() - 1)
                    && (rootrel.get(i).toString()).equals("None")) {
                u_word = uw.get(i).toString();
                if ((u_word.indexOf("person") != -1)
                        || (u_word.indexOf("food") != -1)
                        || (u_word.indexOf("vehicle") != -1)
                        || (u_word.indexOf("machine") != -1)
                        || (u_word.indexOf("body") != -1)
                        || (u_word.indexOf("god") != -1)
                        || (u_word.indexOf("place") != -1)) {
                    if (aojindex > 0) {
                        rel_label = "aoj";
                        findRelation_Category5(aojindex, i, rel_label);
                        break;
                    } else if (uw.indexOf("icl>occupation") != -1) {
                        rel_label = "aoj";
                        findRelation_Category5(uw.indexOf("icl>occupation"), i,
                                rel_label);
                        break;
                    } else {
                        rel_label = "agt";
                        findRelation_Category3(i, rel_label);
                        break;

                    }
                }
            }
        }
        result += "[/r]#[/s]#";
    }

    public void findRelation_Category1(int j, String rl) {
        String concept1 = " ";
        String concept2 = " ";
        try {
            cnt1++;
            int tot = hw.size();

            if ((rl.equals("cond")) || (rl.equals("rsn"))) {
            } else {
                int size = no.size();
                if (size != (j + 1)) {
                    concept1 = no.get(j + 1).toString();
                    if ((j - 1) > 0) {
                        concept2 = no.get(j - 1).toString();
                    } else {
                        concept2 = no.get(0).toString();
                    }
                    if (!concept1.equals(concept2)) {
                        result += concept1 + '\t' + rl + '\t' + concept2 + '#';
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findRelation_Category2(int j, String rl) {
        try {
            cnt2++;
            // //System.out.println("Inside Category2");

            String concept1 = " ";
            String concept2 = " ";

            if (j < Integer.parseInt(verbno1)) {
                concept1 = verbno1;
            } else {
                concept1 = verbno2;
            }

            // //System.out.println("Concept1:"+concept1);
            concept2 = no.get(j).toString();
            // //System.out.println("Concept2:"+concept2);
            if (!concept1.equals(concept2)) {
                result += concept1 + '\t' + rl + '\t' + concept2 + '#';
            }
            // //////System.out.println("Category2:"+cnt2);
        } catch (Exception e) {
        }
    }

    public void findRelation_Category3(int j, String rl) {
        // ////////////////System.out.println("Inside Category3");
        // ArrayList root= new ArrayList();;
        try {
            cnt3++;
            String concept1 = " ";
            String concept2 = " ";

            int temp_i = root.indexOf(verb1);

            // String uword = uw.get(no.indexOf(verbno1)).toString();

            String uword = uw.get(temp_i).toString();

            if ((uword.indexOf("icl>do") != -1)
                    || (uword.indexOf("agt<thing") != -1)) {

                if (j < Integer.parseInt(verbno1)) {
                    concept1 = verbno1;
                } else {
                    concept1 = verbno2;
                }
                concept2 = no.get(j).toString();
                if (!concept1.equals(concept2)) {
                    result += concept1 + '\t' + rl + '\t' + concept2 + '#';
                }
            } else if (cobindex == 1) {

                if (j < Integer.parseInt(verbno1)) {
                    concept1 = verbno1;
                } else {
                    concept1 = verbno2;
                }
                concept2 = no.get(j).toString();
                if (!concept1.equals(concept2)) {
                    result += concept1 + '\t' + "cob" + '\t' + concept2 + '#';
                }
            }// //////System.out.println("Category3:"+cnt3);
        } catch (Exception e) {
        }
    }

    public void findRelation_Category4(int j, String rl) {
        try {
            cnt4++;
            String concept1 = " ";
            String concept2 = " ";

            // concept1 = verbno1;
            if (j < Integer.parseInt(verbno1)) {
                concept1 = verbno1;
            } else {
                concept1 = verbno2;
            }
            // ////////////////System.out.println("Concept Value1"+concept1);
            if ((j - 1) > 0) {
                concept2 = no.get(j - 1).toString();
            } else {
                concept2 = no.get(0).toString();
            }
            // ////////////////System.out.println("Concept Value2"+concept2);
            if (!concept1.equals(concept2)) {
                result += concept1 + '\t' + rl + '\t' + concept2 + '#';
            }

            // //////System.out.println("Category4:"+cnt4);
        } catch (Exception e) {
        }
    }

    public void findRelation_Category5(int i, int j, String rl) {
        try {
            cnt5++;
            String concept1 = " ";
            String concept2 = " ";
            if (i > 0) {
                concept1 = no.get(i).toString();
            } else {
                concept1 = no.get(0).toString();
            }
            if (j >= 0) {
                concept2 = no.get(j).toString();
            } else {
                if (i < 0) {
                    concept2 = "";
                } else {
                    concept2 = no.get(0).toString();
                }
            }
            if (!concept1.equals(concept2)) {
                result += concept1 + '\t' + rl + '\t' + concept2 + '#';
            }

            // //////System.out.println("Category5:"+cnt5);
        } catch (Exception e) {
        }
    }
}
