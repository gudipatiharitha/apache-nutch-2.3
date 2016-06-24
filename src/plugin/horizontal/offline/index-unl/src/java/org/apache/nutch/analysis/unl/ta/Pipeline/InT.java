package org.apache.nutch.analysis.unl.ta.Pipeline;

import org.apache.nutch.analysis.unl.ta.Integrated.MyIndex;
import org.apache.nutch.analysis.unl.ta.Integrated.CPack;
import org.apache.nutch.analysis.unl.ta.Integrated.CRCPack;
import java.util.*;
import java.io.*;
import org.apache.nutch.analysis.unl.ta.*;
import org.apache.nutch.analysis.unl.ta.Integrated.IntegratedGeneral;
import org.apache.nutch.analysis.unl.ta.Integrated.Jumbo;

public class InT extends Thread {

    private boolean sf = true;
    public Q inQ = new Q();
    public InTI inti;
//

    public InT(InteT i) {
        inti = i;
        inQ = new Q();
    }
//

    public synchronized void report(String s) {
        inQ.EnQ(s);
    }
//

    public void run() {
        System.out.println("Index Thread Started....");
        while (sf) {
            if (inQ.isEmpty()) {
//System.out.println("Index Empty...");
                try {
                    sleep(500);
                } catch (Exception e) {
                }
            } else {
                try {
                    long l1 = System.currentTimeMillis();
                    String fn = inQ.DeQ();
                    FinalLLImpl ll_new = IntegratedGeneral.readObject(fn);
                    int i = 0;
                    String id = IntegratedGeneral.fetchID(fn);
                    MyIndex mi = new MyIndex();
                    mi._index(ll_new, id);

                   /* ArrayList<CPack>[] HTc = mi._splitC(mi.cnl);
                    ArrayList<CRCPack>[] HTcrc = mi._splitCRC(mi.ctnl);
                    for (i = 0; i < HTc.length; i++) {
                        new File(Jumbo.getBasePath() + "Lucene-C-Index/" + i + "/").mkdirs();
                        mi._indexC(Jumbo.getBasePath() + "Lucene-C-Index/" + i + "/", HTc[i]);
                    }
                    for (i = 0; i < HTcrc.length; i++) {
                        new File(Jumbo.getBasePath() + "Lucene-CRC-Index/" + i + "/").mkdirs();
                        mi._indexCRC(Jumbo.getBasePath() + "Lucene-CRC-Index/" + i + "/", HTcrc[i]);
                    }

                    mi._indexC(Jumbo.getCPath(id), mi.cnl);
                    mi._indexCRC(Jumbo.getCRCPath(id), mi.ctnl);*/
                    long l2 = System.currentTimeMillis();
                    System.out.println("Indx Finished.[" + id + "] in " + (l2 - l1) + " ms");

                    inti._do3(fn);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }
        System.out.println("Index Thread Ended....");
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
