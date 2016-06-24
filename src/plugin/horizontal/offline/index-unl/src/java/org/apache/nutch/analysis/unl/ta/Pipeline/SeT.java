package org.apache.nutch.analysis.unl.ta.Pipeline;

import java.util.*;
import org.apache.nutch.analysis.unl.ta.Integrated.AdvancedIndex;
import org.apache.nutch.analysis.unl.ta.Integrated.IntegratedGeneral;
import org.apache.nutch.analysis.unl.ta.Integrated.IntegratedSentExtr;
import org.apache.nutch.analysis.unl.ta.Integrated.Jumbo;

public class SeT extends Thread {
    
    private boolean sf = true;
    public Q seQ = new Q();
    public SeTI seti;
//

    public SeT(InteT s) {
        seti = s;
        seQ = new Q();
    }
//

    public synchronized void report(String s) {
        seQ.EnQ(s);
    }
//

    public void run() {
        System.out.println("SE Thread Started....");
        while (sf) {
            if (seQ.isEmpty()) {
//System.out.println("SeT Empty...");
                try {
                    sleep(500);
                } catch (Exception e) {
                }
            } else {
                try {
                    long l1 = System.currentTimeMillis();
                    IntegratedSentExtr IE = new IntegratedSentExtr();
                    String fn = seQ.DeQ();
//if(!(new File(fn).exists())){
                    String id = IntegratedGeneral.fetchID(fn);
                    String fc = IntegratedGeneral.readF(fn);
                    IntegratedGeneral.writeF(fc, Jumbo.getCorePath() + "Input." + id);
                    IntegratedGeneral.writeF(fc, Jumbo.getLamppPath(id));
                    ArrayList<String> arr = IE._impSentExtr(fc, "" + id);
                    IntegratedGeneral.writeF(IE.CNT, Jumbo.getCorePath() + "SentExtr." + id);
                    IntegratedGeneral.writeList(Jumbo.getCorePath() + "SEList." + id, arr);
                  //  AdvancedIndex._indexSnippets(Jumbo.getSniPPath(id), arr);
//Q.getQ().enQ.EnQ("/tmp/core/b"+id);
                    long l2 = System.currentTimeMillis();
                    System.out.println("SE Finished.[" + id + "] in " + (l2 - l1) + " ms");
                    
                    seti._do1(Jumbo.getCorePath() + "SentExtr." + id, id);
//}
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }
        System.out.println("SE Thread Ended....");
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
