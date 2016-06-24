package org.apache.nutch.analysis.unl.ta.Integrated;

import java.io.File;
import org.apache.nutch.analysis.unl.ta.Figo.DaemonTask;

public class Integrated {

    public static String integrateAPI(String doccnt) throws Exception {
        return integrate(doccnt, null);
    }

    public static String integrate(String path) throws Exception {
        return integrate(path, null, false);
    }

    public static String integrate(String doccnt, String URL) throws Exception {
        return integrate(doccnt, URL, true);
    }

    public static String integrate(String doccnt, String URL, boolean genid) throws Exception {
        String fc = "";
        String id = "";
        String DT = "";
        if (genid) {
		System.out.println("........inside if....");
            id = IntegratedGeneral._generateDocID();
            fc = doccnt;
            IntegratedGeneral.writeF(fc, Jumbo.getCorePath() + "Input." + id);
        } else {
		System.out.println("..............inside true url........");
            id = IntegratedGeneral.fetchID(doccnt);
            fc = IntegratedGeneral.readF_API(doccnt);
            IntegratedGeneral.writeF(fc, Jumbo.getCorePath() + "Input." + id);
        }
        fc = "";
        //System.err.println(fc);

        Jumbo._init();
        try {
            //
       /*     if (URL != null && !"".equals(URL)) {
                AdvancedIndex._indexRecNoToURL(Jumbo.getRecnoToURLPath(), id, URL);
            }*/
            //

// Sentence Extraction
//###########################
            try {
		System.out.println(".............inside sentence ectraction");
                DT = DaemonTask.SentExtrForExistingDoc(Jumbo.getCorePath() + "Input." + id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //System.gc();
// Enconversion
//###########################
            try {
                if (URL != null && !"".equals(URL)) {
                    DT = DaemonTask.Enc(DT, null);
                } else {
                    DT = DaemonTask.Enc(DT, URL);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

// Indexing
//###########################
            try {
                DaemonTask.Index(DT);
            } catch (Exception e) {
                e.printStackTrace();
            }

// Snippet
//###########################
            try {
                DaemonTask.Snippet(DT);
            } catch (Exception e) {
                e.printStackTrace();
            }

// Summary
//###########################
            try {
                DaemonTask.TamilSummary(DT);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //System.gc();
            try {
                DaemonTask.EnglishSummary(DT);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //System.gc();
//###########################
        } catch (Exception eeee) {
            eeee.printStackTrace();
        }
        //System.gc();
        return id;
    }

    public static void main(String args[]) throws Exception {
        //    integrate(IntegratedGeneral.readF("/opt/I/1.txt"), "http://1.txt");
        //   integrate(IntegratedGeneral.readF("/opt/I/2.txt"), "http://2.txt");
        //  integrate(IntegratedGeneral.readF("/opt/I/3.txt"), "http://3.txt");
        // String path = Jumbo.getCorePath();
        String path = "C:/opt/core/";
        String[] flist = new File(path).list();
        for (int ii = 0; ii < flist.length; ii++) {
            if (flist[ii].contains("b")) {
                //
                System.out.println("Processing: [" + (ii + 1) + " of " + flist.length + "]: " + path + flist[ii]);
                new Integrated().integrate((path + flist[ii]));
                System.gc();
            }
        }
    }
}
