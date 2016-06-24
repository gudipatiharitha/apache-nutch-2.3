package org.apache.nutch.analysis.unl.ta.Integrated;

import java.io.*;
import java.util.*;
import java.util.Map.*;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.nutch.analysis.unl.ta.*;
//import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.nutch.analysis.unl.ta.SolrIndx.SolrActions;
import org.apache.solr.common.SolrInputDocument;

public class MyIndex {

    public ArrayList<CPack> cnl = new ArrayList<CPack>();
    public ArrayList<CRCPack> ctnl = new ArrayList<CRCPack>();
    public int SkipC = 0, SkipCRC = 0;

    public MyIndex() {
        cnl = new ArrayList<CPack>();
        ctnl = new ArrayList<CRCPack>();
        SkipC = 0;
        SkipCRC = 0;
    }

    public int process_BitPattern(int k, String sid) {
        int bit_pat = 0;
        try {
            int initial = k;
            initial = addSententenceId(initial, sid);
            bit_pat = initial;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bit_pat;
    }

    public int powprocess(int pow, int value) {
        int math = 1;
        if (value == 0) {
            return 1;
        } else {
            math = (math * pow) * powprocess(pow, value - 1);
        }
        return math;
    }

    public int addSententenceId(int bitPattern, String sentenceId) {
        int newSentenceId = Integer.parseInt(sentenceId.substring(1));
        if (newSentenceId < 32) {
            int getPattern = powprocess(2, newSentenceId - 1);
            bitPattern = bitPattern | getPattern;
        }
        return bitPattern;

    }

    public void _index(FinalLLImpl finalImp, String id) throws Exception {
        //
        cnl.clear();
        ctnl.clear();
        ConceptNode conceptnode = new ConceptNode();
        HeadNode headnode = new HeadNode();
        if (finalImp != null) {
            headnode = finalImp.head;
            conceptnode = headnode.colnext;
            while (conceptnode != null) {
                ConceptNode cn = conceptnode;
//
                //String gn_word;
                //String uwconcept;
                //String conceptid;
                //String docid;
                String sentid;
                //String poscheck;
                //int rowct;
                String freq_count;
                String con_freq;
                String con_weight;
                //String tam_uid;
                //String con_uid;
                //String queryTag;
                //String MWtag_Qw;

                //gn_word = cn.gn_word;
                //uwconcept = cn.uwconcept;
                //conceptid = cn.conceptid;
                //docid = cn.docid;
                int b_rep = process_BitPattern(0, cn.sentid);
                sentid = b_rep + "";
                //poscheck = cn.poscheck;
                //rowct = cn.rowct;
                freq_count = cn.freq_count;
                con_freq = cn.con_freq;
                con_weight = cn.con_weight;
                //tam_uid = cn.tam_uid;
                //con_uid = cn.con_uid;
                //queryTag = cn.queryTag;
                //MWtag_Qw = cn.MWtag_Qw;
//
                int sen_weight = 0, weight_c = 0;
                SkipC = 0;
                SkipCRC = 0;
                if (sentid.equals("s1")) {
                    sen_weight = 100;
                    int count_c = Integer.parseInt(con_freq.toString());
                    weight_c = (count_c * 5) + sen_weight;

                } else if (sentid.equals("s2")) {
                    sen_weight = 75;
                    int count_c = Integer.parseInt(con_freq.toString());
                    weight_c = (count_c * 5) + sen_weight;

                } else if (sentid.equals("s3") || (sentid.equals("s4"))
                        || (sentid.equals("s5"))) {
                    sen_weight = 50;
                    int count_c = Integer.parseInt(con_freq.toString());
                    weight_c = (count_c * 5) + sen_weight;

                } else {
                    sen_weight = 0;
                    int count_c = Integer.parseInt(con_freq.toString());
                    weight_c = (count_c) + sen_weight;

                }
                con_weight = weight_c + "";
                cn.con_weight = weight_c + "";
                if (!(cn.poscheck.contains("Verb"))) {
                    int tfc = Integer.parseInt((String) freq_count);
                    int cfc = Integer.parseInt((String) con_freq);
                    int cw = Integer.parseInt((String) con_weight);
                    //
                    CPack cp = new CPack();
                    cp.sentenceid = sentid;
                    cp.sid = cn.sentid;
                    cp.termfrequency = freq_count;
                    cp.conceptfrequency = con_freq;
                    cp.weight = con_weight;
                    cp.tamilwordid = cn.tam_uid;
                    cp.documentid = cn.docid;
                    cp.conceptid = cn.con_uid;
                    cp.synonym = cn.gn_word;
                    cp.uwconcept = cn.uwconcept;
//
                    cp.poscheck = cn.poscheck;
                    cp.rowct = cn.rowct + "";
                    cp.tam_uid = cn.tam_uid;
                    cp.con_uid = cn.conceptid;
                    cp.queryTag = cn.queryTag;
                    cp.MWtag_Qw = cn.MWtag_Qw;
                    //
//                    if ((tfc == 1) && (cfc > 1)) {
                    cnl.add(cp);
//                    } else if ((tfc == 1) && (cfc == 1) && ((cw >= 50) || (cn.poscheck.contains("Entity")) || (cn.poscheck.contains("Noun")))) {
//                        cnl.add(cp);
//                    } else {
//                        SkipC++;
//                    }
                }
//
                ConceptToNode ctn = conceptnode.getRowNext();
                while (ctn != null) {
//
                    String uwfrmconcept;
                    String uwtoconcept;
                    String relnlabel;
                    String docid1;
                    String sentid1;

                    uwfrmconcept = ctn.uwfrmconcept;
                    uwtoconcept = ctn.uwtoconcept;
                    relnlabel = ctn.relnlabel;
                    docid1 = ctn.docid;
                    //int b_rep1 = process_BitPattern(0, ctn.sentid);
                    sentid1 = b_rep + "";
//
//                    if ((!relnlabel.equals("qua")) && (!relnlabel.equals("man")) && (!relnlabel.equals("seq")) && (!relnlabel.equals("cao")) && (!relnlabel.equals("aoj")) && (!relnlabel.equals("pur"))) {
                    String to_Tamilword = finalImp.getconcept_vs_ToConcept(uwtoconcept, cn.sentid);

                    if (to_Tamilword != null && to_Tamilword != "") {
                        StringTokenizer strToken = new StringTokenizer(to_Tamilword, "$");
                        String to_tw = strToken.nextToken();
                        String to_pos = strToken.nextToken();
                        String to_uw = strToken.nextToken();
                        String to_cf = strToken.nextToken();
                        String to_cw = strToken.nextToken();
                        String to_cuid = strToken.nextToken();

                        //String crc = cn.gn_word + "\t" + cn.poscheck + "\t" + cn.uwconcept + "\t" + relnlabel + "\t" + to_tw + "\t" + to_pos + "\t" + to_uw + "\t" + to_cuid;

                        int toc_cf = Integer.parseInt(to_cf.toString());
                        int toc_cw = Integer.parseInt(to_cw.toString());
                        //
                        CRCPack crp = new CRCPack();
                        crp.crc = cn.uwconcept + to_uw;
                        crp.conceptid = to_cuid;
                        crp.fromuwconcept = cn.uwconcept;
                        crp.touwconcept = to_uw;
                        crp.fromtamilconcept = cn.gn_word;
                        crp.relation = relnlabel;
                        crp.frompos = cn.poscheck;
                        crp.topos = to_pos;
                        crp.totamilconcept = to_tw;
                        crp.documentid = docid1;
                        crp.sentenceid = sentid1;
                        crp.sid = ctn.sentid;
                        crp.termfrequency = to_cf;
                        crp.conceptfrequency = to_cf;
                        crp.weight = to_cw;
                        //
//                            if (toc_cf > 1) {
                        ctnl.add(crp);
//                            } else if ((toc_cf == 1) && (toc_cw >= 50)) {
//                                ctnl.add(crp);
//                            } else {
//                                SkipCRC++;
//                            }

                    }
//                    }
//
                    ctn = ctn.getRowNext();
                }
                conceptnode = conceptnode.getColNext();
            }
            System.out.println("Added: [C:" + cnl.size() + "][CRC:" + ctnl.size() + "] for " + id);
            //System.out.println("Filtered: [C:" + SkipC + "][CRC:" + SkipCRC + "] for " + id);
        }
    }

    //
    public void _indexSolrCRC(ArrayList<CRCPack> ctn) throws Exception {
        if (ctn == null || ctn.size() < 1) {
            return;
        }
        for (int i = 0; i < ctn.size(); i++) {
            SolrInputDocument sdoc = new SolrInputDocument();
            sdoc.addField("AUCEG.CRC-Index.crc", ctn.get(i).crc);
            sdoc.addField("AUCEG.CRC-Index.conceptid", ctn.get(i).conceptid);
            sdoc.addField("AUCEG.CRC-Index.fromuwconcept", ctn.get(i).fromuwconcept);
            sdoc.addField("AUCEG.CRC-Index.touwconcept", ctn.get(i).touwconcept);
            sdoc.addField("AUCEG.CRC-Index.fromtamilconcept", ctn.get(i).fromtamilconcept);//1
            sdoc.addField("AUCEG.CRC-Index.relation", ctn.get(i).relation);//2
            sdoc.addField("AUCEG.CRC-Index.frompos", ctn.get(i).frompos);//3
            sdoc.addField("AUCEG.CRC-Index.topos", ctn.get(i).topos);//4
            sdoc.addField("AUCEG.CRC-Index.totamilconcept", ctn.get(i).totamilconcept);//5
            sdoc.addField("AUCEG.CRC-Index.documentid", ctn.get(i).documentid);//
            sdoc.addField("AUCEG.CRC-Index.sentenceid", ctn.get(i).sentenceid);//
            sdoc.addField("AUCEG.CRC-Index.sid", ctn.get(i).sid);//
            sdoc.addField("AUCEG.CRC-Index.termfrequency", ctn.get(i).termfrequency);//
            sdoc.addField("AUCEG.CRC-Index.conceptfrequency", ctn.get(i).conceptfrequency);//
            sdoc.addField("AUCEG.CRC-Index.weight", ctn.get(i).weight);//
            sdoc.addField("id","AUCEG.CRC-Index."+ ctn.get(i).documentid+(RandomUtils.nextInt()));
            SolrActions._putDoc(sdoc);
        }

    }
//

    public void _indexSolrC(ArrayList<CPack> cn) throws Exception {
        if (cn == null || cn.size() < 1) {
            return;
        }
        for (int i = 0; i < cn.size(); i++) {
            //
            SolrInputDocument sdoc = new SolrInputDocument();
            sdoc.addField("AUCEG.C-Index.sentenceid", cn.get(i).sentenceid);
            sdoc.addField("AUCEG.C-Index.sid", cn.get(i).sid);
            sdoc.addField("AUCEG.C-Index.termfrequency", cn.get(i).termfrequency);
            sdoc.addField("AUCEG.C-Index.conceptfrequency", cn.get(i).conceptfrequency);
            sdoc.addField("AUCEG.C-Index.weight", cn.get(i).weight);//1
            sdoc.addField("AUCEG.C-Index.tamilwordid", cn.get(i).tamilwordid);//2
            sdoc.addField("AUCEG.C-Index.documentid", cn.get(i).documentid);//3
            sdoc.addField("AUCEG.C-Index.conceptid", cn.get(i).conceptid);//3
            sdoc.addField("AUCEG.C-Index.tamilword", cn.get(i).synonym);//2
            sdoc.addField("AUCEG.C-Index.uwconcept", cn.get(i).uwconcept);//3

            sdoc.addField("AUCEG.C-Index.poscheck", cn.get(i).poscheck);//3
            sdoc.addField("AUCEG.C-Index.rowct", cn.get(i).rowct);//3
            sdoc.addField("AUCEG.C-Index.tam_uid", cn.get(i).tam_uid);//3
            sdoc.addField("AUCEG.C-Index.con_uid", cn.get(i).con_uid);//3
            sdoc.addField("AUCEG.C-Index.queryTag", cn.get(i).queryTag);//3
            sdoc.addField("AUCEG.C-Index.MWtag_Qw", cn.get(i).MWtag_Qw);//3
            sdoc.addField("id", "AUCEG.C-Index."+cn.get(i).documentid+(RandomUtils.nextInt()));
            SolrActions._putDoc(sdoc);
        }

//
    }
//

    //
 /*   public void _indexCRC(String IndexDir, ArrayList<CRCPack> ctn) throws Exception {
        if (ctn == null || ctn.size() < 1) {
            return;
        }
        File indexDir = new File(IndexDir);
        indexDir.mkdirs();
        //IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_CURRENT, new SimpleAnalyzer());
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir), new SimpleAnalyzer(), IndexWriter.MaxFieldLength.UNLIMITED);
        indexWriter.setUseCompoundFile(false);

        int D = 1;
        long l1 = System.currentTimeMillis();

        for (int i = 0; i < ctn.size(); i++) {
            Document doc = new Document();
            //String CRC = ctn.get(i).uwfrmconcept + uwtoconcept;

            doc.add(new Field("crc", ctn.get(i).crc, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            doc.add(new Field("conceptid", ctn.get(i).conceptid, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            doc.add(new Field("fromuwconcept", ctn.get(i).fromuwconcept, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            doc.add(new Field("touwconcept", ctn.get(i).touwconcept, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            doc.add(new Field("fromtamilconcept", ctn.get(i).fromtamilconcept, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//1
            doc.add(new Field("relation", ctn.get(i).relation, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//2
            doc.add(new Field("frompos", ctn.get(i).frompos, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//3
            doc.add(new Field("topos", ctn.get(i).topos, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//4
            doc.add(new Field("totamilconcept", ctn.get(i).totamilconcept, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//5
            doc.add(new Field("documentid", ctn.get(i).documentid, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//
            doc.add(new Field("sentenceid", ctn.get(i).sentenceid, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//
            doc.add(new Field("sid", ctn.get(i).sid, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//
            doc.add(new Field("termfrequency", ctn.get(i).termfrequency, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//
            doc.add(new Field("conceptfrequency", ctn.get(i).conceptfrequency, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//
            doc.add(new Field("weight", ctn.get(i).weight, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//

            indexWriter.addDocument(doc);
        }
        //
        //indexWriter.optimize();
        indexWriter.close();

        long l2 = System.currentTimeMillis();
        //////////System.out.println("Indexing Time:" + (l2 - l1));
//
    }*/
//

   /* public void _indexC(String IndexDir, ArrayList<CPack> cn) throws Exception {
        if (cn == null || cn.size() < 1) {
            return;
        }
        File indexDir = new File(IndexDir);
        indexDir.mkdirs();
        //IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_CURRENT, new SimpleAnalyzer());
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir), new SimpleAnalyzer(), IndexWriter.MaxFieldLength.UNLIMITED);
        indexWriter.setUseCompoundFile(false);

        int D = 1;
        long l1 = System.currentTimeMillis();

        for (int i = 0; i < cn.size(); i++) {
            //
            Document doc = new Document();
            doc.add(new Field("sentenceid", cn.get(i).sentenceid, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            doc.add(new Field("sid", cn.get(i).sid, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            doc.add(new Field("termfrequency", cn.get(i).termfrequency, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            doc.add(new Field("conceptfrequency", cn.get(i).conceptfrequency, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            doc.add(new Field("weight", cn.get(i).weight, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//1
            doc.add(new Field("tamilwordid", cn.get(i).tamilwordid, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//2
            doc.add(new Field("documentid", cn.get(i).documentid, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//3
            doc.add(new Field("conceptid", cn.get(i).conceptid, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//3
            doc.add(new Field("tamilword", cn.get(i).synonym, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//2
            doc.add(new Field("uwconcept", cn.get(i).uwconcept, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//3

            doc.add(new Field("poscheck", cn.get(i).poscheck, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//3
            doc.add(new Field("rowct", cn.get(i).rowct, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//3
            doc.add(new Field("tam_uid", cn.get(i).tam_uid, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//3
            doc.add(new Field("con_uid", cn.get(i).con_uid, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//3
            doc.add(new Field("queryTag", cn.get(i).queryTag, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//3
            doc.add(new Field("MWtag_Qw", cn.get(i).MWtag_Qw, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));//3
            indexWriter.addDocument(doc);
        }
        //
//            is.close();
        // indexWriter.optimize();
        indexWriter.close();

        long l2 = System.currentTimeMillis();

//
    }*/
//

    public Hashtable<String, String> _DictGet() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome() + "resource/unl/uwdict.txt"));
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ArrayList<String> arr = new ArrayList<String>();
        String SS = "";
        while ((SS = br.readLine()) != null) {
            //
            String s1[] = SS.split("/");
            String synonym, uwconcept, tamilwordid, uw;
            //synonym = s1[0];
            //uwconcept = s1[1] + "(" + s1[2] + ")";
            tamilwordid = s1[3];
            //uw = s1[4];
            ht.put(tamilwordid, SS);
        }
        br.close();
        br = new BufferedReader(new FileReader(org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome() + "resource/unl/multiwords.txt"));
        while ((SS = br.readLine()) != null) {
            String s1[] = SS.split("/");
            String tamilword, concept, noofwrds, firstword, tamid, cid;
            //tamilword = s1[0];
            //concept = s1[1] + "(" + s1[2] + ")";
            //noofwrds = s1[3];
            //firstword = s1[4];
            tamid = s1[5];
            //cid = s1[6];
            ht.put(tamid, SS);
        }
        br.close();
        return ht;
//
    }
//

/*    public ArrayList<CPack>[] _splitC(ArrayList<CPack> cnl) throws Exception {
        int i;
        ArrayList<CPack>[] HT = (ArrayList<CPack>[]) new ArrayList[20];
        for (i = 0; i < HT.length; i++) {
            HT[i] = new ArrayList<CPack>();
        }
        for (i = 0; i < cnl.size(); i++) {
            if (!"".equals(cnl.get(i).conceptid.replaceAll("[^\\p{Nd}]", "")) && cnl.get(i).conceptid != null && !"".equals(cnl.get(i).conceptid) && !cnl.get(i).conceptid.contains("None")) {
                int I = Integer.parseInt(cnl.get(i).conceptid);
                I = I / 10000;
                HT[I].add(cnl.get(i));
            }
        }
        return HT;
    }*/
//
//

   /* public ArrayList<CRCPack>[] _splitCRC(ArrayList<CRCPack> cnl) throws Exception {
        int i;
        ArrayList<CRCPack>[] HT = (ArrayList<CRCPack>[]) new ArrayList[20];
        for (i = 0; i < HT.length; i++) {
            HT[i] = new ArrayList<CRCPack>();
        }
        for (i = 0; i < cnl.size(); i++) {
            if (!"".equals(cnl.get(i).conceptid.replaceAll("[^\\p{Nd}]", "")) && cnl.get(i).conceptid != null && !"".equals(cnl.get(i).conceptid) && !cnl.get(i).conceptid.contains("None")) {
                int I = Integer.parseInt(cnl.get(i).conceptid);
                I = I / 10000;
                HT[I].add(cnl.get(i));
            }
        }
        return HT;
    }*/
//

    public static void main(String[] s) throws Exception {
        FinalLLImpl ll_new = IntegratedGeneral.readObject("/classify/Graph/Graph.1051423112193756337");
        int i = 0;
        String id = IntegratedGeneral.fetchID("/classify/Graph/Graph.1051423112193756337");
        MyIndex mi = new MyIndex();
        mi._index(ll_new, id);
      /*  ArrayList<CPack>[] HTc = mi._splitC(mi.cnl);
        ArrayList<CRCPack>[] HTcrc = mi._splitCRC(mi.ctnl);
        for (i = 0; i < HTc.length; i++) {
            mi._indexC("/root/Desktop/CMI/" + i + "/", HTc[i]);
        }
        for (i = 0; i < HTcrc.length; i++) {
            mi._indexCRC("/root/Desktop/CRCMI/" + i + "/", HTcrc[i]);
        }*/
    }
//
}
