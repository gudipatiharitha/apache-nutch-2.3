package org.apache.nutch.analysis.unl.ta.Pipeline;

import org.apache.nutch.analysis.unl.ta.*;
import org.apache.nutch.analysis.unl.ta.Integrated.IntegratedGeneral;
import org.apache.nutch.analysis.unl.ta.Integrated.IntegratedOfflineSummary;

public class SuT extends Thread {

    private boolean sf = true;
    public Q suQ = new Q();
    public SuTI suti;
//
    public SuT(InteT s) {
        suti = s;
        suQ = new Q();
    }
//
    public synchronized void report(String s) {
        suQ.EnQ(s);
    }
//
    public void run() {
        System.out.println("Summary Thread Started....");
        while (sf) {
            if (suQ.isEmpty()) {
//System.out.println("SuT Empty...");
                try {
                    sleep(500);
                } catch (Exception e) {
                }
            } else {
                try {
                    long l1 = System.currentTimeMillis();
                    String fn = suQ.DeQ();
                    String id = IntegratedGeneral.fetchID(fn);
                    FinalLLImpl ll_new = IntegratedGeneral.readObject(fn);
                    IntegratedOfflineSummary os = new IntegratedOfflineSummary();//._main_for_integration(id,ll_new,headnode);
                    os.FN = fn;
                    os.ID = id;
                    os.IDD = id;
                    FinalLLImpl lll = ll_new;
                    os._doSummary(lll);

                    long l2 = System.currentTimeMillis();
                    System.out.println("TamilSummary Finished.[" + id + "] in " + (l2 - l1) + " ms");
                    /*
                     * l1 = System.currentTimeMillis();
                     *
                     * IntegratedOfflineSummaryEng os1 = new
                     * IntegratedOfflineSummaryEng();//._main_for_integration(id,ll_new,headnode);
                     * os1.FN = fn; os1.ID = id; os1.IDD = id;
                     * os1._doSummary(ll_new); os1=null; l2 =
                     * System.currentTimeMillis();
                     * System.out.println("EngSummary Finished.["+id+"] in
                     * "+(l2-l1)+" ms");
                     */
                    suti._do4(id);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }
        System.out.println("Summary Thread Ended....");
    }
//
    public synchronized void _start() {
        sf = true;
        start();
    }
//
    public synchronized void _stop() {
        sf = false;
    }
}
