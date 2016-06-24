package org.apache.nutch.analysis.unl.ta.Figo;

import java.util.*;

class Entry {

    public String TID = "";
    public String TData = "";

    public Entry() {
        TID = "EXIT";
        TData = "EXIT";
    }

    public Entry(String taskid, String taskdata) {
        TID = taskid;
        TData = taskdata;
    }
}

public class Q {

    public LinkedList<Entry> L = new LinkedList<Entry>();

    public Q() {
        L = new LinkedList<Entry>();
    }

    public synchronized void EnQ(String taskid, String taskdata) {
        L.addLast(new Entry(taskid, taskdata));
    }

    public synchronized Entry DeQ() {
        if (L.peekFirst() != null) {
            return L.removeFirst();
        } else {
            return new Entry();
        }
    }

    public synchronized int size() {
        return L.size();
    }

    public synchronized boolean isEmpty() {
        if (L.peekFirst() == null) {
            return true;
        } else {
            return false;
        }
    }

    public synchronized void printQ() {
        System.out.print((L + "").replace(",", ",\n"));
    }
}
