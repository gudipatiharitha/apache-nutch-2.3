package org.apache.nutch.analysis.unl.ta.Pipeline;

import org.apache.nutch.analysis.unl.ta.*;
import org.apache.nutch.analysis.unl.ta.Integrated.IntegratedFinalAppln;
import org.apache.nutch.analysis.unl.ta.Integrated.IntegratedGeneral;
import org.apache.nutch.analysis.unl.ta.Integrated.Jumbo;

public class EnT extends Thread {

    private boolean sf = true;
    public Q enQ = new Q();
    public EnTI enti;
//

    public EnT(InteT e) {
        enti = e;
        enQ = new Q();
    }
//

    public synchronized void report(String s) {
        enQ.EnQ(s);
    }
//

    public void run() {
        System.out.println("Enc Thread Started....");
        while (sf) {
            if (enQ.isEmpty()) {
//System.out.println("EnT Empty...");
                try {
                    sleep(500);
                } catch (Exception e) {
                }
            } else {
                try {
                    long l1 = System.currentTimeMillis();
                    IntegratedFinalAppln ifa = new IntegratedFinalAppln();
                    String fn = enQ.DeQ();
//if(!(new File(fn).exists())){

                    String id = IntegratedGeneral.fetchID(fn);

                    IntegratedGeneral.writeF(ifa.start(fn, id), Jumbo.getCorePath() + "Enc." + id);
                    FinalLLImpl ll_new = ifa.graphconstruct("" + id);
                    IntegratedGeneral.writeinObject("Graph." + id, ll_new, Jumbo.getCorePath());
                    //ArrayList<String> al = IntegratedGeneral.readList(Jumbo.getCorePath() + "l" + id);

                    //Bingo._indexGyro(Jumbo.getGyroPath(), id, Bingo.enGyro(ll_new), Bingo.enGyroTerm(ll_new));
                    //Bingo._indexSentGyro(Jumbo.getSentGyroPath(), id, Bingo.enSentGyro(ll_new), Bingo.enSentTermGyro(ll_new));
                    long l2 = System.currentTimeMillis();

                    System.out.println("ENC Finished.[" + id + "] in " + (l2 - l1) + " ms");
                    enti._do2(Jumbo.getCorePath() + "Graph." + id);
//}
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Enc Thread Ended....");
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
