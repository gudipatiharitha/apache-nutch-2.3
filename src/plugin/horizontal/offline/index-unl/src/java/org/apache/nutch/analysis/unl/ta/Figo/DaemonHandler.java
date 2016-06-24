/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.nutch.analysis.unl.ta.Figo;

import java.io.File;
import org.apache.nutch.analysis.unl.ta.Integrated.Jumbo;

/**
 *
 * @author Admin
 */
public class DaemonHandler implements DaemonInterface {
//

    public Q TQ = new Q();

    public synchronized Entry getNextEntry() {
        return TQ.DeQ();
    }

    public synchronized boolean isEmpty() {
        return TQ.isEmpty();
    }

    public synchronized void putNextEntry(Entry E) {
        TQ.EnQ(E.TID, E.TData);
    }

//
    //
    public void _doProcess(int threadcount, String taskid, String path, String pattern, boolean useDocIdFromPath) throws Exception {
        String[] flist = new File(path).list();
        if (useDocIdFromPath) {
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].contains(pattern)) {
                    TQ.EnQ(taskid, path + flist[i]);
                }
            }
        } else {
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].contains(pattern)) {
                    TQ.EnQ("SEN", path + flist[i]);
                }
            }
        }

        if (threadcount > 25) {
            threadcount = 25;
        }
        //
        Jumbo._init();
        //
        Daemon[] D = new Daemon[threadcount];
        System.out.println("Daemons Created...." + TQ.size());
        for (int i = 0; i < D.length; i++) {
            D[i] = new Daemon(this);
        }
    }

    public static void main(String[] args) throws Exception {
        new DaemonHandler()._doProcess(1, "SEE", "C:/opt/core/", "b", true);
    }
}
