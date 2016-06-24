package org.apache.nutch.analysis.unl.ta.Pipeline;

import java.util.*;
import org.apache.nutch.analysis.unl.ta.*;
import org.apache.nutch.analysis.unl.ta.Integrated.IntegratedGeneral;
import org.apache.nutch.analysis.unl.ta.Integrated.IntegratedSnippet;
import org.apache.nutch.analysis.unl.ta.Integrated.Jumbo;

public class SnP extends Thread {

    private boolean sf = true;
    public Q enQ = new Q();
    public SnPI SnPi;
//

    public SnP(InteT e) {
        SnPi = e;
        enQ = new Q();
    }
//

    public synchronized void report(String s) {
        enQ.EnQ(s);
    }
//

    public void run() {
        System.out.println("Snippet Thread Started....");
        while (sf) {
            if (enQ.isEmpty()) {
//System.out.println("SnP Empty...");
                try {
                    sleep(500);
                } catch (Exception e) {
                }
            } else {
                try {
                    long l1 = System.currentTimeMillis();
                    String fn = enQ.DeQ();
//
                    String id = IntegratedGeneral.fetchID(fn);
                    IntegratedSnippet IS = new IntegratedSnippet();
                    IS.globalDocid = "" + id;
                    FinalLLImpl ll_new = IntegratedGeneral.readObject(fn);
                    ArrayList<String> al = IntegratedGeneral.readList(Jumbo.getCorePath() + "SEList." + id);
                    IS.__doSnippet(ll_new, al);
//###########################
                    long l2 = System.currentTimeMillis();
                    System.out.println("Snippet Exec Time: " + (l2 - l1) + "ms");
                    SnPi._do6(Jumbo.getCorePath() + "d" + id);
//}
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Snippet Thread Ended....");
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
