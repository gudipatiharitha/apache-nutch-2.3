/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.nutch.analysis.unl.ta.snippet;

import java.io.File;
import java.util.ArrayList;
import org.apache.commons.lang.math.RandomUtils;
//import org.apache.lucene.analysis.KeywordAnalyzer;
//import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.nutch.analysis.unl.ta.Integrated.Jumbo;
import org.apache.nutch.analysis.unl.ta.SolrIndx.SolrActions;
import org.apache.solr.common.SolrInputDocument;

/**
 *
 * @author root
 */
public class Snippet {

    public static synchronized void _indexSnippets(String docid, String concept, String sentence, String sid) throws Exception {
/*File indexDir = new File(Jumbo.getSnippetPath(docid));
        indexDir.mkdirs();
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir), new SimpleAnalyzer(), IndexWriter.MaxFieldLength.UNLIMITED);
        indexWriter.setUseCompoundFile(false);
        Document doc = new Document();
        concept = concept.replaceAll(";", "#");
        concept = concept.replaceAll("\\[", "#");
        concept = concept.replaceAll("\\]", "#");
        doc.add(new Field("concept", concept.toLowerCase(), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
        doc.add(new Field("sentence", sentence.toLowerCase().replace(" 0 ", ".\n").replace("#", ""), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
        doc.add(new Field("sentenceid", sid, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
        indexWriter.addDocument(doc);
        indexWriter.close();*/
        //
        SolrInputDocument sdoc = new SolrInputDocument();
        sdoc.addField("id", "AUCEG.Snippet." + docid + (RandomUtils.nextInt()));
        sdoc.addField("AUCEG.Snippet.docid", docid);
        sdoc.addField("AUCEG.Snippet.concept", concept);
        sdoc.addField("AUCEG.Snippet.sentence", sentence);
        sdoc.addField("AUCEG.Snippet.sentenceid", sid);
        SolrActions._putDoc(sdoc);
        //
    }

   /* public static synchronized String _getSnippet(String id, String[] tamilWord) {
        String S = "";
        for (int i = 0; i < tamilWord.length; i++) {
            ArrayList<Document> tt1 = _getSnippet(id, tamilWord[i]);
            for (int j = 0; j < tt1.size() && j < 1; j++) {
                S += (tt1.get(j).get("sentence").replace("#", "")) + "... ";
            }
        }
        if (S.length() >= 500) {
            S = S.substring(0, 500) + "...";
        }
        for (int i = 0; i < tamilWord.length; i++) {
            S = S.replace(tamilWord[i], "<b><font color='green'>" + tamilWord[i] + "</font></b>");
        }
        if ("".equals(S)) {
            S = "No Snippet Available!";
        }
        return S;
    }*/

    /*public synchronized static ArrayList<Document> _getSnippet(String docid, String value) {
        ArrayList<Document> arr = new ArrayList<Document>();
        try {
            IndexSearcher is = new IndexSearcher(FSDirectory.open(new File(Jumbo.getSnippetPath(docid))));
            String ff = "concept";// br.readLine();
            String q = value.trim();//br.readLine();
            //q = QueryParser.escape(q).replace(" ", "?");
            q = "#*" + q + "*";
            QueryParser qp = new QueryParser(Version.LUCENE_CURRENT, ff.trim(), new KeywordAnalyzer());
            Query qq = qp.parse(q);
            TopDocs td;
            td = is.search(qq, 10);
            System.err.println("Snp Results Found:" + td.scoreDocs.length);
            for (int i = 0; i < td.scoreDocs.length; i++) {
                Document d = is.doc(td.scoreDocs[i].doc);
                arr.add(d);
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }*/

    public static void main(String[] args) throws Exception {
        //System.out.println("S:" + Snippet._getSnippet("10514231221894618187", new String[]{"அன்பு", "இரு", "ந்திய"}));
       // System.out.println(Snippet._getSnippet("10514231322365572481", "Adjective"));
    }
}
