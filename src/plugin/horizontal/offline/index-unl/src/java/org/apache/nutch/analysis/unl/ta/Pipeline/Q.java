package org.apache.nutch.analysis.unl.ta.Pipeline;

import java.util.*;

public class Q {

    public LinkedList<String> L = new LinkedList<String>();

    public Q() {
        L = new LinkedList<String>();
    }

    public synchronized void EnQ(String c) {
        L.addLast(c);
    }

    public synchronized String DeQ() {
        if (L.peekFirst() != null) {
            return L.removeFirst();
        } else {
            return "";
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
