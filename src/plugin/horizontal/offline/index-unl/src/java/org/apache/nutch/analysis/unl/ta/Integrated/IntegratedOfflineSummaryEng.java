package org.apache.nutch.analysis.unl.ta.Integrated;

import org.apache.nutch.analysis.unl.ta.*;
import java.io.*;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.nutch.util.NutchConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.analysis.unl.ta.SolrIndx.SolrActions;
import org.apache.solr.common.SolrInputDocument;

public class IntegratedOfflineSummaryEng extends SummaryEng {

    public String FN = "";
    public String ID = "";
    public String IDD = "";
    public static Configuration configuration = NutchConfiguration.create();
    public static String templateHome = configuration.get("UNLCrawl");
    public static String EnconversionHome = configuration.get("unl-Graph");

    public void _doSummary(FinalLLImpl lll) throws Exception {
        TemplateLoaded();
        if (lll != null) {
            FinalLLImpl[] ll_new = new FinalLLImpl[1];
            ll_new[0] = lll;
            Traverselnode(ll_new);
            //AddTemplatewords();
            summaryWrite(IDD);
            // EmptySummaryFileWrite(IDD);
            // placeLisstWrite(IDD);
            //  System.out.println(summary+"");
        }
//
    }

    public void summaryWrite(String id) throws Exception { //this for Integration
        ObjectOutputStream objectOutputStream = IOHelper.getObjectOutputStream(Jumbo.getBasePath() + "Summary/summaryeng" + id + ".ser");
        IOHelper.writeObjectToOutputStream(objectOutputStream, Summary);
        IOHelper.closeObjectOutputStream(objectOutputStream);
        //System.out.println("Writing completed. [" + (templateHome + "newsummary/summary-" + id + ".ser") + "]");
        System.gc();

        String s = Summary.get(id);
        SolrInputDocument sdoc = new SolrInputDocument();
        sdoc.addField("AUCEG.Summary.english.docid", id);
        sdoc.addField("id", "AUCEG.Summary.english."+id+(RandomUtils.nextInt()));

        if (s != null || !"".equals(s)) {
            sdoc.addField("AUCEG.Summary.english.summary", s);
        } else {
            sdoc.addField("AUCEG.Summary.english.summary", "No Tamil Summary Avaliable!");
        }
        SolrActions._putDoc(sdoc);
        //


        //System.out.println(summary + "");
        // AdvancedIndex._indexEnglishSummary(Jumbo.getSumEPath(id), id, Summary);
    }
}
