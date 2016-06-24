package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.template.unl;

import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

/**
 *
 * @author karthikeyan.S
 * @version 2.0
 * @since AUCEG
 */
public class SummaryTest {

    public static boolean isSummaryLoaded = false;
   public static HashMap<String, String> summary;
//public static Hashtable summary;
    /**
     * This method define for load the place Object only first time
     */
    public void loadSummary() {
        if (isSummaryLoaded) {
            return;
        }
        ObjectInputStream summaryReader = IOHelper.getObjectInputStream("./crawl-unl/newsummary/summaryeng.ser");
        summary = (HashMap<String, String>) IOHelper.readObjectFromInputStream(summaryReader);
        isSummaryLoaded = true;
    }

    /**
     *This method define for get the place of document
     */
    public void getTestSummary() {
        loadSummary();
        Set key = summary.keySet();
        for (Object docids : key) {
            System.out.println("Docid" + docids.toString());
            System.out.println("Summarys" + summary.get(docids.toString()));
        }
	System.out.println(summary.size());
    }

    public static void main(String[] args) {
        SummaryTest summaryTest = new SummaryTest();
        summaryTest.getTestSummary();
    }
}
