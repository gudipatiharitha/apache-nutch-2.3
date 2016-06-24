/**
 * To Sort the Expanded Word Based on frequency
 */
package org.apache.nutch.analysis.unl.ta.Integrated;

import java.util.*;

public class FreqSort {

    public static CRC crc;
    ArrayList<Integer> arl = new ArrayList<Integer>();

    public FreqSort() {
        crc = new CRC();
    }

    /**
     * getObjArray method is used to get the Frequency count of Expanded Query
     * Word
     *
     * @param ObjAry List of Expanded Query Word
     * @return List of Expanded Query Word with its frequeny Count
     */
    public ArrayList getObjArray(ArrayList ObjAry) {
        ArrayList<String> toConKey = new ArrayList<String>();
        Hashtable<String, Object> ConObj = new Hashtable<String, Object>();
        ArrayList<Integer> ConFreq = new ArrayList<Integer>();
        ArrayList<String> Con = new ArrayList<String>();
        ArrayList SortedObj = new ArrayList();

        try {
            for (Object O : ObjAry) {
                CRC c = (CRC) O;
                if (!toConKey.contains(c.tam1)) {
                    toConKey.add(c.tam1);
                    //to Key as tam1 and Values O as object(CRC)
                    ConObj.put(c.tam1, O);
                }
                Con.add(c.tam1);
                ConFreq.add(c.crcFreqCnt);
            }


            ArrayList<String> Sortedlst = sort(Con, ConFreq);
            int counter = 0;
            for (String str : Sortedlst) {
                if (counter < 20) {
                    SortedObj.add(ConObj.get(str));
                    counter++;
                } else {
                    break;
                }
            }


            /*
             * int i=0; for(Object O : SortedObj){ CRC c=(CRC)O;
             * System.out.println(c.tam1+" :"+c.rel+" :"+c.tam2+"
             * :"+ConFreq.get(i)); i++; }
             */

        } catch (Exception e) {
        }
        return SortedObj;
    }

    /**
     * sort Method is to sort the Expanded Query Word Based on the frequency.
     *
     * @param Con Expanded English UW Concept
     * @param ConFreq Frequency count of Expanded Query Word
     * @return sorted ArrayList
     */
    public ArrayList<String> sort(ArrayList<String> Con, ArrayList<Integer> ConFreq) {

        for (int i = 0; i < ConFreq.size(); i++) {
            for (int j = 0; j < ConFreq.size(); j++) {
                if (ConFreq.get(i) > ConFreq.get(j)) {
                    //System.out.println("[arl 0] "+arl);

                    int ival = ConFreq.get(i);
                    String icon = Con.get(i);

                    int jval = ConFreq.get(j);
                    String jcon = Con.get(j);

                    ConFreq.remove(i);
                    Con.remove(i);
                    ConFreq.add(i, jval);
                    Con.add(i, jcon);

                    //System.out.println("[arl 0] "+arl);

                    ConFreq.remove(j);
                    Con.remove(j);
                    ConFreq.add(j, ival);
                    Con.add(j, icon);
                } else {
                    continue;
                }
            }
        }
        return Con;
    }

    /*
     * Main Method for Debugging
     */
    public static void main(String args[]) {
        FreqSort fq = new FreqSort();
        //fq.loadInp();
    }
}
