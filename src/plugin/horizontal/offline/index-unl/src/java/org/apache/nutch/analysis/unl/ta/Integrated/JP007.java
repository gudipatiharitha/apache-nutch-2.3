/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.nutch.analysis.unl.ta.Integrated;

/**
 *
 * @author root
 */
public class JP007 {

    public String ID = "0";
    public String FC = "A";

    public JP007(String fc) {
        try {
            FC = fc;
            //System.out.println(Analyser.Tags.toString());
            ID = Integrated.integrateAPI(FC);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String _SE(String ID) throws Exception {
        return IntegratedGeneral.readF_API(Jumbo.getCorePath() + "SentExtr." + ID);
    }

    public String _ENC(String ID) throws Exception {
        return IntegratedGeneral.readF_API(Jumbo.getCorePath() + "Enc." + ID);
    }

    public String _ESUM(String ID) throws Exception {
        return IntegratedGeneral.readSummary(Jumbo.getBasePath() + "Summary/summaryeng" + ID + ".ser").get(ID);
    }

    public String _TSUM(String ID) throws Exception {
        return IntegratedGeneral.readSummary(Jumbo.getBasePath() + "Summary/summary" + ID + ".ser").get(ID);
    }

    public static void main(String[] args) throws Exception {
        JP007 jp = new JP007(IntegratedGeneral.readF("/in/3.txt"));
        System.out.flush();
        //System.out.println(jp._SE(jp.ID));
        // System.out.println(jp._ENC(jp.ID));
        System.out.println(jp._TSUM(jp.ID));
        System.out.println(jp._ESUM(jp.ID));
    }
}
