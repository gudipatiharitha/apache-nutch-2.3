package org.apache.nutch.analysis.unl.ta.Integrated;

import org.apache.nutch.analysis.unl.ta.*;
import java.io.*;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.nutch.util.NutchConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.analysis.unl.ta.SolrIndx.SolrActions;
import org.apache.solr.common.SolrInputDocument;

public class IntegratedOfflineSummary extends OfflineSummary {

    public String FN = "";
    public String ID = "";
    public String IDD = "";
    public static Configuration configuration = NutchConfiguration.create();
    public static String templateHome = configuration.get("UNLCrawl");
    public static String EnconversionHome = configuration.get("unl-Graph");

    public void _doSummary(FinalLLImpl lll) throws Exception {
        intialize();
        TemplateLoaded();
        if (lll != null) {
            FinalLLImpl[] ll_new = new FinalLLImpl[1];
            ll_new[0] = lll;
            Traverselnode(ll_new);
            AddTemplatewords();
            summaryWrite(IDD);
            //EmptySummaryFileWrite(IDD);
            //placeLisstWrite(IDD);
            // System.out.println(summary+"");
        }
//
    }

    public void summaryWrite(String id) throws Exception { //this for Integration
        ObjectOutputStream objectOutputStream = IOHelper.getObjectOutputStream(Jumbo.getBasePath() + "Summary/summary" + id + ".ser");
        IOHelper.writeObjectToOutputStream(objectOutputStream, summary);
        IOHelper.closeObjectOutputStream(objectOutputStream);
        //System.out.println("Writing completed. [" + (templateHome + "newsummary/summary-" + id + ".ser") + "]");

        //

        String s = summary.get(id);
        SolrInputDocument sdoc = new SolrInputDocument();
        sdoc.addField("AUCEG.Summary.tamil.docid", docid);
        sdoc.addField("id",  "AUCEG.Summary.tamil."+docid+(RandomUtils.nextInt()));

        if (s != null || !"".equals(s)) {
            sdoc.addField("AUCEG.Summary.tamil.summary", s);
        } else {
            sdoc.addField("AUCEG.Summary.tamil.summary", "No Tamil Summary Avaliable!");
        }
        SolrActions._putDoc(sdoc);
        //

        System.gc();
        //System.out.println(summary + "");
        // AdvancedIndex._indexTamilSummary(Jumbo.getSumTPath(id), id, summary);
    }

    public void placeLisstWrite(String id) throws Exception {//this for Integration
        ObjectOutputStream objectOutputStream = IOHelper.getObjectOutputStream(Jumbo.getBasePath() + "Summary/placeList" + id + ".ser");
        IOHelper.writeObjectToOutputStream(objectOutputStream, placeList);
        IOHelper.closeObjectOutputStream(objectOutputStream);
        //System.out.println("Writing completed PlaceList.ser");
        System.gc();
    }

    public void EmptySummaryFileWrite(String id) throws Exception {
        BufferedWriter bufferedWriter = IOHelper.getBufferedWriter(Jumbo.getBasePath() + "Summary/summaryEmpty" + id + ".txt");
        for (Object s : EmptySummary) {
            IOHelper.writeLineToBufferedWriter(bufferedWriter, s.toString());
        }
        IOHelper.closeBufferedWriter(bufferedWriter);
        //System.out.println("Writing completed Summaryempty.ser");
        System.gc();
    }
}
