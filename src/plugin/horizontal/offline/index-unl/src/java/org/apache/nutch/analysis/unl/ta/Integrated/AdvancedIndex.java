package org.apache.nutch.analysis.unl.ta.Integrated;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang.math.RandomUtils;
//import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.nutch.analysis.unl.ta.SolrIndx.SolrActions;
import org.apache.solr.common.SolrInputDocument;


/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author root
 */
public class AdvancedIndex {
    
    public static void main(String[] s) throws Exception {
        // System.out.println(computeStoragePath("/opt/Index/", "a"));
        //System.out.println(Jumbo.getCPath("abcd"));
       // _indexDict(Jumbo.getUWDictPath(), Jumbo.getMWDictPath());
    }
    //

    public static synchronized void _indexRecNoToURL(String IndexDir, String docid, String url) throws Exception {
        
      /*  File indexDir = new File(IndexDir);
        indexDir.mkdirs();
        //IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_CURRENT, new SimpleAnalyzer());
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir), new SimpleAnalyzer(), IndexWriter.MaxFieldLength.UNLIMITED);
        indexWriter.setUseCompoundFile(false);
        
        long l1 = System.currentTimeMillis();
        
        Document doc = new Document();
        doc.add(new Field("docid", docid, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
        doc.add(new Field("url", url, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
        indexWriter.addDocument(doc);*/
        //
//            is.close();
//        indexWriter.optimize();
  //      indexWriter.close();

        //
        SolrInputDocument sdoc = new SolrInputDocument();
        sdoc.addField("AUCEG.RecNoToURL.docid", docid);
        sdoc.addField("AUCEG.RecNoToURL.url", url);
        sdoc.addField("id", "AUCEG.RecNoToURL."+docid+(RandomUtils.nextInt()));
        SolrActions._putDoc(sdoc);
        //
        long l2 = System.currentTimeMillis();

//
    }
//

/*    public static void _indexDict(String IndexDirUW, String IndexDirMW) throws Exception {
        
        File indexDir = new File(IndexDirUW);
        indexDir.mkdirs();
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir), new SimpleAnalyzer(), IndexWriter.MaxFieldLength.UNLIMITED);
        indexWriter.setUseCompoundFile(false);
        //

        BufferedReader br = new BufferedReader(new FileReader(org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome() + "resource/unl/uwdict.txt"));
        String SS = "";
        while ((SS = br.readLine()) != null) {
            //
            String s1[] = SS.split("/");
            String synonym, uwconcept, tamilwordid, uw;
            synonym = s1[0];
            uwconcept = s1[1] + "(" + s1[2] + ")";
            tamilwordid = s1[3];
            uw = s1[4];
            Document doc = new Document();
            doc.add(new Field("tamilword", synonym + "", Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            doc.add(new Field("uwconcept", "#" + uwconcept, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            doc.add(new Field("tamilwordid", tamilwordid + "", Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            doc.add(new Field("uwconceptid", uw + "", Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            indexWriter.addDocument(doc);
        }
        br.close();
        //            is.close();
        indexWriter.optimize();
        indexWriter.close();
        
        indexDir = new File(IndexDirMW);
        indexDir.mkdirs();
        indexWriter = new IndexWriter(FSDirectory.open(indexDir), new SimpleAnalyzer(), IndexWriter.MaxFieldLength.UNLIMITED);
        indexWriter.setUseCompoundFile(false);
        
        br = new BufferedReader(new FileReader(org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome() + "resource/unl/multiwords.txt"));
        while ((SS = br.readLine()) != null) {
            String s1[] = SS.split("/");
            String tamilword, concept, noofwrds, firstword, tamid, cid;
            tamilword = s1[0];
            concept = s1[1] + "(" + s1[2] + ")";
            noofwrds = s1[3];
            firstword = s1[4];
            tamid = s1[5];
            cid = s1[6];
            Document doc = new Document();
            doc.add(new Field("tamilword", tamilword + "", Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            doc.add(new Field("uwconcept", "#" + concept + "", Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            doc.add(new Field("tamilwordid", tamid + "", Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            doc.add(new Field("uwconceptid", cid + "", Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            doc.add(new Field("noofwords", noofwrds + "", Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            doc.add(new Field("firstword", firstword + "", Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            indexWriter.addDocument(doc);
        }
        br.close();
        indexWriter.optimize();
        indexWriter.close();

//
    }*/
    
/*    public static synchronized void _indexSnippets(String IndexDir, ArrayList<String> cn) throws Exception {
        if (cn == null || cn.size() < 1) {
            return;
        }
        
        File indexDir = new File(IndexDir);
        indexDir.mkdirs();
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir), new SimpleAnalyzer(), IndexWriter.MaxFieldLength.UNLIMITED);
        indexWriter.setUseCompoundFile(false);
        
        long l1 = System.currentTimeMillis();
        
        for (int i = 0; i < cn.size(); i++) {
            //
            Document doc = new Document();
            doc.add(new Field("sentenceid", (i + 1) + "", Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            doc.add(new Field("sentence", "#" + cn.get(i).replace("#", "").replace(".", "") + "", Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
            indexWriter.addDocument(doc);
        }
        //
//            is.close();
        indexWriter.optimize();
        indexWriter.close();
        
        long l2 = System.currentTimeMillis();

//
    }*/
    
/*    public static synchronized void _indexTamilSummary(String IndexDir, String docid, HashMap<String, String> tsum) throws Exception {
        if (tsum == null || tsum.size() < 1) {
            return;
        }
        
        File indexDir = new File(IndexDir);
        indexDir.mkdirs();
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir), new SimpleAnalyzer(), IndexWriter.MaxFieldLength.UNLIMITED);
        indexWriter.setUseCompoundFile(false);
        
        long l1 = System.currentTimeMillis();
        
        Document doc = new Document();
        doc.add(new Field("documentid", docid + "", Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
        doc.add(new Field("tamilsummary", tsum.get(docid) + "", Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
        indexWriter.addDocument(doc);
        
        indexWriter.optimize();
        indexWriter.close();
        
        long l2 = System.currentTimeMillis();
    }*/
    
/*    public static synchronized void _indexEnglishSummary(String IndexDir, String docid, HashMap<String, String> esum) throws Exception {
        
        if (esum == null || esum.size() < 1) {
            return;
        }
        File indexDir = new File(IndexDir);
        indexDir.mkdirs();
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir), new SimpleAnalyzer(), IndexWriter.MaxFieldLength.UNLIMITED);
        indexWriter.setUseCompoundFile(false);
        
        long l1 = System.currentTimeMillis();
        
        Document doc = new Document();
        doc.add(new Field("documentid", docid + "", Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
        doc.add(new Field("englishsummary", esum.get(docid) + "", Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
        indexWriter.addDocument(doc);
        
        indexWriter.optimize();
        indexWriter.close();
        
        long l2 = System.currentTimeMillis();
    }*/
    
    public static String computeStoragePath(String baseP, String docid) throws Exception {
        String P = baseP;
        if (docid == null || docid == "" || docid.length() < 1) {
            return P + "waste/" + docid + "/";
        } else {
            for (int i = 0, j = 0; i < docid.length() && j < 3; i++, j++) {
                P += ("" + docid.charAt(i) + "/");
            }
            return P + docid + "/";
        }
    }
}
