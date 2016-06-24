/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.nutch.analysis.unl.ta.Figo;

/**
 *
 * @author Admin
 */
public interface DaemonInterface {

    public boolean isEmpty();

    public void putNextEntry(Entry E);

    public Entry getNextEntry();
}
