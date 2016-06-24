package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.index.unl;

import java.io.*;
import java.util.*;
import java.util.ArrayList;
//import org.apache.nutch.enconversion.unl.ta.*;
//import org.apache.nutch.analysis.unl.ta.*;

public class SuperofConstrain implements Serializable {

    public BinaryNode root;
    public static ArrayList<String> SuperConcept = new ArrayList<String>();
    public static BinarySearchTree t = new BinarySearchTree();
    public static boolean check_Tree = true;

    /**
     * To load the c from c into the Binary Search Tree
     * @return root node
     */
   
    public ArrayList processSuper(String Constrain) {
        ArrayList<String> Temp_super = new ArrayList<String>();        
        try {
            if (check_Tree) {
                long Stime=System.currentTimeMillis();
                //System.out.println("Start Time"+Stime);
                BufferedReader br = new BufferedReader(new FileReader("./crawl-unl/C_ICLTree.txt"));
                String str = "";
                while ((str = br.readLine()) != null) {
                    SuperConcept.add(str);
                }
                long Etime=System.currentTimeMillis();
                //System.out.println("Start Time"+Etime);
                long totTime=Etime-Stime;
                //System.out.println("Total Time in milli sec"+totTime);
                long insec = totTime/1000;
                //System.out.println("Total Time"+insec);
                check_Tree = false;
            }            
            for (String tmp : SuperConcept) {
                if (tmp.contains(Constrain) && Temp_super.size() < 5) {
                    Temp_super.add(tmp);
                    //System.out.println("? " + tmp);
                }
            }


        } catch (Exception e) {
        }
        return Temp_super;
    }

    public static void main(String args[]) throws Exception {
        try {
            SuperofConstrain Q = new SuperofConstrain();
            Q.processSuper("icl>action");           
        } catch (Exception e) {
        }
    }
}


