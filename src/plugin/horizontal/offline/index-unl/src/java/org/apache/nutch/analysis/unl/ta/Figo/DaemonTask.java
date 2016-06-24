/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.nutch.analysis.unl.ta.Figo;

import java.io.File;
import java.util.ArrayList;
//import org.apache.nutch.analysis.unl.ta.Classify.CIndex;
import org.apache.nutch.analysis.unl.ta.FinalLLImpl;
import org.apache.nutch.analysis.unl.ta.Integrated.*;

/**
 *
 * @author Admin
 */
public class DaemonTask {

    public static String SentExtrForExistingDoc(String path) throws Exception {
        String SE = "";
        String fc = IntegratedGeneral.readF(path);
        String id = IntegratedGeneral.fetchID(path);//path.substring(path.lastIndexOf(File.pathSeparator)).replaceAll("[^\\p{Nd}]", "");
        ArrayList<String> al = new ArrayList<String>();
        long l1 = System.currentTimeMillis();
        IntegratedSentExtr IE = new IntegratedSentExtr();
	System.out.println("****************inside sentrnce extraction");
        al = IE._impSentExtr(fc, "" + id);
        SE = IE.CNT;
        //
        IntegratedGeneral.writeF(fc, Jumbo.getCorePath() + "Input." + id);
        IntegratedGeneral.writeF(fc, Jumbo.getLamppPath(id));
        IntegratedGeneral.writeF(SE, Jumbo.getCorePath() + "SentExtr." + id);
        IntegratedGeneral.writeList(Jumbo.getCorePath() + "SEList." + id, al);
        //
        long l2 = System.currentTimeMillis();
        System.out.println("SE Finished.[" + id + "] in " + (l2 - l1) + " ms");

        return Jumbo.getCorePath() + "SentExtr." + id;
    }

    public static String SentExtrNewDoc(String path) throws Exception {
        String SE = "";
        String fc = IntegratedGeneral.readF(path);
        String id = IntegratedGeneral._generateDocID();
        ArrayList<String> al = new ArrayList<String>();
        long l1 = System.currentTimeMillis();
        IntegratedSentExtr IE = new IntegratedSentExtr();
        al = IE._impSentExtr(fc, "" + id);
        SE = IE.CNT;
        //
        IntegratedGeneral.writeF(fc, Jumbo.getCorePath() + "Input." + id);
        IntegratedGeneral.writeF(fc, Jumbo.getLamppPath(id));
        IntegratedGeneral.writeF(SE, Jumbo.getCorePath() + "SentExtr." + id);
        IntegratedGeneral.writeList(Jumbo.getCorePath() + "SEList." + id, al);
        //
        long l2 = System.currentTimeMillis();
        System.out.println("SE Finished.[" + id + "] in " + (l2 - l1) + " ms");

        return Jumbo.getCorePath() + "SentExtr." + id;
    }

    public static String Enc(String path, String url) throws Exception {
        long l1 = System.currentTimeMillis();
        String id = IntegratedGeneral.fetchID(path);
        IntegratedFinalAppln ifa = new IntegratedFinalAppln();
        String enc = ifa.start(path, id);
        IntegratedGeneral.writeF(enc, Jumbo.getCorePath() + "Enc." + id);
        FinalLLImpl ll_new = ifa.graphconstruct("" + id);
        //ArrayList<String> al = IntegratedGeneral.readList(Jumbo.getCorePath() + "SEList." + id);
        //
        ll_new.DocID = id;
        //ll_new.SEList = al;
        ll_new.URL = url;//Bingo._getRecNoToURL(Jumbo.getRecnoToURLPath(), id);
        if ("".equals(ll_new.URL) || ll_new.URL == null) {
            ll_new.URL = url;
        } else {
            ll_new.URL = path;
        }
        //
        IntegratedGeneral.writeinObject("Graph." + id, ll_new, Jumbo.getCorePath());
        //CIndex._indexCGyro(id, enc);
        //
        long l2 = System.currentTimeMillis();
        System.out.println("ENC Finished.[" + id + "] in " + (l2 - l1) + " ms");
        return Jumbo.getCorePath() + "Graph." + id;
    }

    public static String Snippet(String path) throws Exception {
        long l1 = System.currentTimeMillis();
        String id = IntegratedGeneral.fetchID(path);
        IntegratedSnippet IS = new IntegratedSnippet();
        IS.globalDocid = "" + id;
        FinalLLImpl ll_new = IntegratedGeneral.readObject(path);
        ArrayList<String> al = IntegratedGeneral.readList(Jumbo.getCorePath() + "SEList." + id);
        IS.__doSnippet(ll_new, al);
        long l2 = System.currentTimeMillis();
        System.out.println("Snippet Exec Time: " + (l2 - l1) + "ms");
        return "Nothing";
    }

    public static synchronized String Index(String path) throws Exception {
        System.out.println(path);
        try {
            long l1 = System.currentTimeMillis();
            String fn = path;
            FinalLLImpl ll_new = IntegratedGeneral.readObject(fn);
            int i = 0;
            String id = IntegratedGeneral.fetchID(fn);
            MyIndex mi = new MyIndex();
            mi._index(ll_new, id);

          /* ArrayList<CPack>[] HTc = mi._splitC(mi.cnl);
            ArrayList<CRCPack>[] HTcrc = mi._splitCRC(mi.ctnl);
            for (i = 0; i < HTc.length; i++) {
                new File(Jumbo.getBasePath() + "Lucene-C-Index/" + i + "/").mkdirs();
                mi._indexC(Jumbo.getBasePath() + "Lucene-C-Index/" + i + "/", HTc[i]);
            }
            for (i = 0; i < HTcrc.length; i++) {
                new File(Jumbo.getBasePath() + "Lucene-CRC-Index/" + i + "/").mkdirs();
                mi._indexCRC(Jumbo.getBasePath() + "Lucene-CRC-Index/" + i + "/", HTcrc[i]);
            }
            //
            mi._indexC(Jumbo.getCPath(id), mi.cnl);
            mi._indexCRC(Jumbo.getCRCPath(id), mi.ctnl);*/
            //
            mi._indexSolrC(mi.cnl);
            mi._indexSolrCRC(mi.ctnl);
            //
            //CIndex._indexCategories(Jumbo.getCatIndexPath(), id);
            //
            long l2 = System.currentTimeMillis();
            System.out.println("Indx Finished.[" + id + "] in " + (l2 - l1) + " ms");
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return "Nothing";
    }

    public static String TamilSummary(String path) throws Exception {
        long l1 = System.currentTimeMillis();
        String id = IntegratedGeneral.fetchID(path);
        FinalLLImpl ll_new = IntegratedGeneral.readObject(path);
        IntegratedOfflineSummary os = new IntegratedOfflineSummary();//._main_for_integration(id,ll_new,headnode);
        os.FN = path;
        os.ID = id;
        os.IDD = id;
        FinalLLImpl lll = ll_new;
        os._doSummary(lll);
        long l2 = System.currentTimeMillis();
        System.out.println("TamilSummary Finished.[" + id + "] in " + (l2 - l1) + " ms");
        return "Nothing";
    }

    public static String EnglishSummary(String path) throws Exception {
        long l1 = System.currentTimeMillis();
        String id = IntegratedGeneral.fetchID(path);
        FinalLLImpl ll_new = IntegratedGeneral.readObject(path);
        IntegratedOfflineSummaryEng os1 = new IntegratedOfflineSummaryEng();//._main_for_integration(id,ll_new,headnode);
        os1.FN = path;
        os1.ID = id;
        os1.IDD = id;
        os1._doSummary(ll_new);
        os1 = null;
        long l2 = System.currentTimeMillis();
        System.out.println("EngSummary Finished.[" + id + "] in " + (l2 - l1) + " ms");
        return "Nothing";
    }
}
