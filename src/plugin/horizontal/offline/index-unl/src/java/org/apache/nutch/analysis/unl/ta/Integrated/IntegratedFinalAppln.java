package org.apache.nutch.analysis.unl.ta.Integrated;

import org.apache.nutch.analysis.unl.ta.*;

import java.io.*;
import java.util.*;
//import org.apache.nutch.analysis.unl.ta.Classify.CIndex;

public class IntegratedFinalAppln extends FinalAppln {

    public FinalLLImpl lll = null;
    public String ID = "";
    public Hashtable _al = null, _con_count = null;

    public IntegratedFinalAppln() {
    }

    public String start_(String fc, String id) throws Exception {
        ID = id;
        encon_out = "";
        String s = "";
        String docid1 = "";
        ArrayList<String> alnew = new ArrayList<String>();

        r.loadequ();

        String sent = "", recv = "", token = "", temp = "";
        int temp1 = 0, j = 0;
        //docid = id;
        //initialdocid = id;
        r.getDocid(docid + "");

        int incrementalIter = 0;

        StringBuffer docbuff = new StringBuffer();
        encon_out += "[d]#";
        //String splitid[] = s.split("/");
        //docid1 = splitid[1].replace(".txt", "").toString();
        docid1 = id + "";
        alnew.add(id + "");
        StringReader fr = new StringReader(fc);
        StreamTokenizer st = new StreamTokenizer(fr);

        while (st.nextToken() != st.TT_EOF) {
            temp = "";
            temp1 = 0;
            if (st.ttype == st.TT_WORD) {
                temp = st.sval;
            } else if (st.ttype == st.TT_NUMBER) {
                temp1 = (int) st.nval;
                temp = Integer.toString(temp1);
            }
            if (temp != null && temp != "") {
                docbuff.append(temp + " ");
            }
        }
        StringTokenizer sentToken1 = new StringTokenizer(docbuff.toString().trim(), ".", false);
        j = 0;
        int jj = 1;
        while (sentToken1.hasMoreTokens()) {
            sent = sentToken1.nextToken();
            recv = r.enconvert(sent);
            encon_out += recv;
            //System.out.println(sent + " <-> " + recv);
            try {
                org.apache.nutch.analysis.unl.ta.snippet.Snippet._indexSnippets(id, recv, sent, jj + "");
               // CIndex._indexCSentGyro(id, recv, sent, jj + "");
            } catch (Exception e) {
                e.printStackTrace();
            }

            jj++;
        }
        encon_out += "[/d]#";
        r.writeintofile(docid1);
        r.clearList();
        fr.close();
//super.no_doc=id;
        //System.out.println("Docid:" + no_doc);

        _freq_count(alnew);

        return encon_out;
    }
//

    public String start(String fn, String id) throws Exception {
        ID = id;
        encon_out = "";
        String s = "";
        String docid1 = "";
        ArrayList<String> alnew = new ArrayList<String>();

        r.loadequ();

        String sent = "", recv = "", token = "", temp = "";
        int temp1 = 0, j = 0;
        //docid = id;
        //initialdocid = id;
        r.getDocid(docid + "");

        int incrementalIter = 0;

        StringBuffer docbuff = new StringBuffer();
        encon_out += "[d]#";
        //String splitid[] = s.split("/");
        //docid1 = splitid[1].replace(".txt", "").toString();
        docid1 = id + "";
        alnew.add(id + "");
        FileReader fr = new FileReader(fn);
        StreamTokenizer st = new StreamTokenizer(fr);

        while (st.nextToken() != st.TT_EOF) {
            temp = "";
            temp1 = 0;
            if (st.ttype == st.TT_WORD) {
                temp = st.sval;
            } else if (st.ttype == st.TT_NUMBER) {
                temp1 = (int) st.nval;
                temp = Integer.toString(temp1);
            }
            if (temp != null && temp != "") {
                docbuff.append(temp + " ");
            }
        }
        StringTokenizer sentToken1 = new StringTokenizer(docbuff.toString().trim(), ".", false);
        j = 0;
        int jj = 1;
        while (sentToken1.hasMoreTokens()) {
            sent = sentToken1.nextToken();
            recv = r.enconvert(sent);
            encon_out += recv;
            //System.out.println(sent + " <-> " + recv);
            try {
                org.apache.nutch.analysis.unl.ta.snippet.Snippet._indexSnippets(id, recv, sent, jj + "");
               // CIndex._indexCSentGyro(id, recv, sent, jj + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            jj++;
        }
        encon_out += "[/d]#";
        r.writeintofile(docid1);
        r.clearList();
        fr.close();
//super.no_doc=id;
        //System.out.println("Docid:" + no_doc);

        _freq_count(alnew);

        return encon_out;
    }
//

    public void _freq_count(ArrayList<String> alnew) {
        String word = "";
        String g_word = "";
        String conentry = "";
        String relnentry = "";
        String docid1 = "";
        Hashtable freq_table = null;
        Hashtable concept_table = null;
        int l = 0;
        try {
            StringTokenizer strToken = new StringTokenizer(encon_out, "#");
            while (strToken.hasMoreTokens()) {
                try {
                    word = strToken.nextToken().trim();
                    if (word.equals("[d]")) {
                        freq_table = new Hashtable();
                        concept_table = new Hashtable();
                        docid1 = alnew.get(l).toString();
                        // //System.out.println("Inside Tag [d]:"+docid1+":"+l);
                        l++;
                    } else if (word.equals("[s]")) {
                    } else if (word.equals("[/d]")) {
                        //al.put(docid1, freq_table);
                        _al = freq_table;
                        _con_count = concept_table;
                        //con_count.put(docid1, concept_table);
                        // //System.out.println("new_Freq:"+al);
                        // freq_table.clear();
                    } else if (word.equals("[w]")) {

                        conentry = strToken.nextToken().trim();
                        // //System.out.println("conentry:"+conentry);
                        while (!(conentry.equals("[/w]"))) {
                            StringTokenizer strToken1 = new StringTokenizer(
                                    conentry, ";");
                            if (strToken1.hasMoreElements()) {
                                g_word = strToken1.nextToken().trim();
                                // //System.out.println("g_word:"+g_word);
                                int count = 1;
                                String frequency = "";
                                if (freq_table.isEmpty()) {
                                    frequency = Integer.toString(count).toString();
                                    freq_table.put(g_word, frequency);
                                } else if (freq_table.containsKey(g_word)) {
                                    String freq_count = freq_table.get(g_word).toString();
                                    int f_cnt = Integer.parseInt(freq_count);
                                    f_cnt++;
                                    frequency = Integer.toString(f_cnt).toString();
                                    freq_table.put(g_word, frequency);
                                } else {
                                    frequency = Integer.toString(count).toString();
                                    freq_table.put(g_word, frequency);
                                }

                            }
                            if (strToken1.hasMoreElements()) {
                                String uwconcept = strToken1.nextToken().trim()
                                        + '(' + strToken1.nextToken().trim()
                                        + ')';
                                int count = 1;
                                String con_frequency = "";
                                if (concept_table.isEmpty()) {
                                    con_frequency = Integer.toString(count);
                                    concept_table.put(uwconcept, con_frequency);
                                } else if (concept_table.containsKey(uwconcept)) {
                                    String freq_count = concept_table.get(
                                            uwconcept).toString();
                                    int con_cnt = Integer.parseInt(freq_count);
                                    con_cnt++;
                                    con_frequency = Integer.toString(con_cnt).toString();
                                    concept_table.remove(uwconcept);
                                    concept_table.put(uwconcept, con_frequency);
                                } else {
                                    con_frequency = Integer.toString(count).toString();
                                    concept_table.put(uwconcept, con_frequency);
                                }
                            }
                            if (strToken1.hasMoreElements()) {
                            }
                            if (strToken1.hasMoreElements()) {
                            }
                            if (strToken1.hasMoreElements()) {
                            }
                            if (strToken1.hasMoreElements()) {
                            }
                            conentry = strToken.nextToken().trim();
                        }
                    }
                    /**
                     * else if (word.equals("[r]")) { while
                     * (!(relnentry.equals("[/r]"))) { } }else{ }
                     */
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //	
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

//
    public FinalLLImpl graphconstruct(String FD) throws Exception {
        String g_word = "", concept = "", conceptto = "", sid = "", did = "", relnid = "", frmscpid = "", toscpid = "", conceptfrm = "", ConceptToNode = "";
        String verbword = "";
        String conentry = "";
        String relnentry = "";
        String frmscopeid = "";
        String toscopeid = "";
        String tamil_uid = "";
        String concept_uid = "";
        String word = "";
        int totcon_sent = 0;
        int totrel_sent = 0;

        String fd = "";
        String fd1 = "";
        String fldoc = "";
        FinalLLImpl ll_new = new FinalLLImpl();
        try {
            // //System.out.println("Encon_Out:"+encon_out);
            StringTokenizer strToken = new StringTokenizer(encon_out, "#");
            String conceptid = " ";
            int iter = 1;
            while (strToken.hasMoreTokens()) {
                try {
                    word = strToken.nextToken().trim();
                    if (word.equals("[d]")) {

                        //fldoc = list.get(docid).toString();
                        //StringTokenizer strTok = new StringTokenizer(fldoc, "/");
                        //fd1 = strTok.nextToken();
                        //fd = strTok.nextToken();
                        fd = FD;//fd.replace(".txt", "");
                        //System.out.println("fd:" + fd);
                        docid++;
                        get_count = _al;//(Hashtable) al.get(fd);
                        get_concount = _con_count;//(Hashtable) con_count.get(fd);
                        // //System.out.println("enconout Inside Graph Construct:"+fd+"\n"+encon_out);

                    } else if (word.equals("[s]")) {
                        totcon_sent = 0;
                        totrel_sent = 0;
                        sentid++;

                    } else if (word.equals("[/d]")) {
                        sentid = 0;
                        iter++;
                    } else if (word.equals("[w]")) {

                        conentry = strToken.nextToken().trim();
                        while (!(conentry.equals("[/w]"))) {
                            StringTokenizer strToken1 = new StringTokenizer(
                                    conentry, ";");
                            if (strToken1.hasMoreElements()) {
                                g_word = strToken1.nextToken().trim();
                            }
                            if (strToken1.hasMoreElements()) {
                                concept = strToken1.nextToken().trim() + '('
                                        + strToken1.nextToken().trim() + ')';
                            }
                            if (strToken1.hasMoreElements()) {
                                verbword = strToken1.nextToken().trim();
                            }
                            if (strToken1.hasMoreElements()) {
                                tamil_uid = strToken1.nextToken().trim();
                            }
                            if (strToken1.hasMoreElements()) {
                                concept_uid = strToken1.nextToken().trim();
                            }
                            totcon_sent++;
                            if (strToken1.hasMoreElements()) {
                                conceptid = strToken1.nextToken().trim();
                            }

                            sid = ("s" + sentid);
                            int sen_weight = 0;
                            // did=("d"+docid);
                            Object str = null;
                            Object con_freq = null;
                            Object con_weight = null;
                            did = fd.trim();
                            if (g_word != null) {
                                str = get_count.get(g_word);
                            }
                            if (concept != null) {
                                con_freq = get_concount.get(concept);
                            }
                            if (sid.equals("s1")) {
                                sen_weight = 100;
                            } else if (sid.equals("s2")) {
                                sen_weight = 75;
                            } else if (sid.equals("s3") || (sid.equals("s4"))
                                    || (sid.equals("s5"))) {
                                sen_weight = 50;
                            } else {
                                sen_weight = 0;
                            }
                            int count_c = Integer.parseInt(con_freq.toString());
                            int weight_c = (count_c * 5) + sen_weight;
                            con_weight = (Object) weight_c;
                            // //System.out.println("G_WORD:"+g_word+":"+sid+":"+conceptid+":"+did);
                            // ll[dociditer-1].addConcept(g_word,concept,conceptid,did,sid,verbword);
                            if (str != null) {
                                ll_new.addConcept(g_word, concept,
                                        conceptid, did, sid, verbword, str.toString(), con_freq.toString(), con_weight.toString(), tamil_uid, concept_uid, "", "");
                            } else {
                                ll_new.addConcept(g_word, concept,
                                        conceptid, did, sid, verbword, tamil_uid, concept_uid, "", "", "", "", "");
                            }

                            conentry = strToken.nextToken().trim();

                        }

                    } else if (word.equals("[r]")) {
                        relnentry = strToken.nextToken().trim();
                        // //System.out.println("RELN:"+relnentry);
                        while (!(relnentry.equals("[/r]"))) {

                            StringTokenizer strToken2 = new StringTokenizer(
                                    relnentry);
                            /**
                             * if (strToken2.hasMoreElements()) {
                             *
                             * frmscopeid = strToken2.nextToken().trim(); //
                             * //System.out.println("frmSCP:"+frmscopeid);
                             * if(!(frmscopeid.equals("scp1"))){ frmscpid =
                             * frmscopeid; }else{ frmscpid = "None"; } }
                             */
                            if (strToken2.hasMoreElements()) {

                                conceptfrm = strToken2.nextToken().trim();
                                // //System.out.println("frmconceptid:"+conceptfrm);
                            }
                            if (strToken2.hasMoreElements()) {

                                relnid = strToken2.nextToken().trim();
                                // //System.out.println("relnid:"+relnid);
                            }
                            if (strToken2.hasMoreElements()) {
                                conceptto = strToken2.nextToken().trim();
                                // //System.out.println("toconceptid:"+conceptto);
                            }
                            /**
                             * if (strToken2.hasMoreElements()) {
                             *
                             * toscopeid = strToken2.nextToken().trim(); //
                             * //System.out.println("toscpid:"+toscopeid);
                             * if(!(toscopeid.equals("scp2"))){ toscpid =
                             * toscopeid; }else{ toscpid = "None"; } }
                             */
                            sid = ("s" + sentid);
                            // did=("d"+(FinalAppln.initialdocid+iter));
                            did = fd.trim();

                            totrel_sent++;

                            if ((!(conceptfrm.equals("None"))) && (!(conceptto.equals("None")))) {

                                ll_new.addRelation(relnid);
                                ConceptToNode cn = ll_new.addCT_Concept(conceptfrm, conceptto, relnid, did, sid);
                                ll_new.addCT_Relation(cn);
                                // System.out.println(conceptfrm + "-" + conceptto + "-" + relnid + "-" + did + "-" + sid);
                            }

                            if (conceptto.equals("None")) {
                            }

                            relnentry = strToken.nextToken().trim();

                        }

                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // System.out.println("Document is Empty");
                    //no_doc++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ll_new;
    }
}
