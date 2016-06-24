package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.snippetgeneration.unl.ta;
import org.apache.nutch.util.NutchConfiguration;
import org.apache.hadoop.conf.Configuration;
import java.util.*;
import java.io.*;
import org.apache.nutch.analysis.unl.ta.Integrated.Jumbo;

public class SummaryGet {

    public static boolean isSummaryLoaded = false;
    public static boolean isSummaryLoadedeng = false;
    public static HashMap<String, String> summaryunl, summaryeng;
    public static Configuration conf = NutchConfiguration.create();
    //public static String summary_ta = conf.get("summary");
    //public static String summary_en = conf.get("summaryeng");

    public void loadSummary(String docid) throws Exception {

        ObjectInputStream summaryReader = IOHelper.getObjectInputStream(Jumbo.getBasePath() + "Summary/summary" + docid + ".ser");
        summaryunl = (HashMap<String, String>) IOHelper.readObjectFromInputStream(summaryReader);


    }

    public void loadEngSummary(String docid) throws Exception {

        ObjectInputStream summaryReader = IOHelper.getObjectInputStream(Jumbo.getBasePath() + "Summary/summaryeng" + docid + ".ser");
        summaryeng = (HashMap<String, String>) IOHelper.readObjectFromInputStream(summaryReader);

    }

    public String getSummaryunl(String docid) {
        String summarys = "";
        try {
            loadSummary(docid);

            Set key = summaryunl.keySet();

            if (!key.contains(docid)) {
                return "NoSummaryAvailable";
            } else {
                summarys = summaryunl.get(docid).toString();
            }

            //summarys="<a href=\"javascript:showhide('div"+docid+"');\" onmouseover=\"javascript:showhide('div"+docid+"');\" onmouseout=\"javascript:showhide('div"+docid+"');\" style=\"color:green\">குறுந்தொகுப்பு</a>"+"<div id=\"div"+docid+"\" style=\"display:none;border:ridge;margin-right:25%;color:green\">"+"<b> இப்பக்கத்தில் சுற்றுலா பற்றிய குறிப்பு</b><br/>"+summarys+"</div>";
            summarys = "<b> இப்பக்கத்தில் சுற்றுலா பற்றிய குறிப்பு</b><br/>" + summarys;

        } catch (Exception e) {
            e.printStackTrace();
            summarys = "NoSummaryAvailable";
        }

        return summarys;
    }

    public String getEngSummaryunl(String docid) {
        String summaryenglish = "";
        try {
            loadEngSummary(docid);

            Set key = summaryeng.keySet();

            if (!key.contains(docid)) {
                return "NoSummaryAvailable";
            } else {
                summaryenglish = summaryeng.get(docid).toString();
            }
            summaryenglish = "<b> Tourism information available in this page</b><br/>" + summaryenglish + "</div>";
        } catch (Exception e) {
            e.printStackTrace();
            summaryenglish = "NoSummaryAvailable";
        }
        return summaryenglish;
    }

    public static void main(String args[]) {
        SummaryGet s = new SummaryGet();

    }
}
