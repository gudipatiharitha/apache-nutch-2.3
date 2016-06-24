/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.nutch.analysis.unl.ta.Figo;

/**
 *
 * @author Admin
 */
public class Daemon extends Thread {

    private DaemonInterface DI = null;

    public Daemon(DaemonHandler di) {
        DI = di;
        start();
    }

    @Override
    public void run() {
        System.out.println("Started Daemon..." + this.getId());
        while (!DI.isEmpty()) {
            Entry E = DI.getNextEntry();
            //
            String tdata = "";
            try {
                if (E.TID.contains("SEE")) {
                    tdata = DaemonTask.SentExtrForExistingDoc(E.TData);
                    DI.putNextEntry(new Entry("ENC", tdata));
                } else if (E.TID.contains("SEN")) {
                    tdata = DaemonTask.SentExtrNewDoc(E.TData);
                    DI.putNextEntry(new Entry("ENC", tdata));
                } else if (E.TID.contains("ENC")) {
                    tdata = DaemonTask.Enc(E.TData, null);
                    DI.putNextEntry(new Entry("INDX", tdata));
                    //DI.putNextEntry(new Entry("SNP", tdata));
                    DI.putNextEntry(new Entry("TSUM", tdata));
                    DI.putNextEntry(new Entry("ESUM", tdata));
                } else if (E.TID.contains("INDX")) {
                    DaemonTask.Index(E.TData);
                } else if (E.TID.contains("SNP")) {
                    DaemonTask.Snippet(E.TData);
                } else if (E.TID.contains("TSUM")) {
                    DaemonTask.TamilSummary(E.TData);
                } else if (E.TID.contains("ESUM")) {
                    DaemonTask.EnglishSummary(E.TData);
                }
                //
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Ended Daemon..." + this.getId());
    }
}
