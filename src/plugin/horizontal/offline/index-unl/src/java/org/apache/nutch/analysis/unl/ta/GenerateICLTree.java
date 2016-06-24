package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.index.unl;

import java.io.*;
import java.util.*;
import java.util.ArrayList;
//import org.apache.nutch.enconversion.unl.ta.*;
//import org.apache.nutch.analysis.unl.ta.*;

public class GenerateICLTree implements Serializable {
    public BinaryNode root;
    public ArrayList<String> SuperConcept = new ArrayList<String>();
    public static BinarySearchTree t = new BinarySearchTree();
    public static boolean check_Tree = true;
    public static CRC crc= new CRC();

    /**
     * To load the c from c into the Binary Search Tree
     * @return root node
     */

    public BinarySearchTree getBinarySearchTree() {
        try {
            //System.out.println("Reading c.txt");
            long startT = System.currentTimeMillis();
            //System.out.println("Start Time" + startT);
            FileInputStream fis2 = new FileInputStream("./crawl-unl/c.txt");
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            String str = "";
            //binaryTree.root=binaryTree.tree_FileRead1(binaryTree.root, ois2,str);
            t.root = t.tree_FileRead(t.root, ois2, str);
            ois2.close();
            fis2.close();
            long stopT = System.currentTimeMillis();
            //System.out.println("Stop Time" + stopT);

            Long totalT = stopT - startT;
            Long Timeinsec = totalT / 1000;
            //System.out.println("Time in Sec." + Timeinsec);
        } catch (Exception e) {
            t = new BinarySearchTree();
            return t;
        }
        return t;
    }

    /**
     * @return Tree
     * @throws java.io.IOException
     */
    public BinarySearchTree printquerytree() throws IOException {
        try {
            //System.out.println("Loading it in to Binary Tree");
            t = getBinarySearchTree();
        } catch (Exception e) {
        }
        return t;
    }

    /**
     * Get the CRC for the User Query
     * @param t is root node
     * @param QryHC is hash code for the User Query
     * @return ArrayList of Objects for CRC
     * @throws java.lang.Exception
     */
    /**
     * It Load the BST once
     * @param queryHC --hash code for the user query
     * @return ArrayList(getExpQry) of objects
     */
    public ArrayList visitTree(BinaryNode t) throws IOException {
        //System.out.println("Visit tree" + t + "\t" + s);
        int flagQry = 0;
        String stng = "";
        try {
            
                if (t != null) {
                    //System.out.println("Concept                    " + t.con);
                    if (t.con.contains("(icl>")) {
                        //System.out.println("Hai  " + t.con + "-" + t.vbcheck + "-" + t.ind_tamil);
                        //crc.tam1=t.ind_tamil;
                        //crc.c1=t.con;
                        //crc.pos1=t.vbcheck;
                        //SuperConcept.add(crc);
                        SuperConcept.add(t.ind_tamil + ";" + t.con + ";" + t.vbcheck);
                        BinaryNextNode b = new BinaryNextNode();
                        b = t.linknext;
                        while (b != null) {
                            //System.out.println("the connected concepts" + b.tamcon);
                            b = b.linknext;
                        }
                    }
                    visitTree(t.left);
                    //System.out.println("Left over...");
                    visitTree(t.right);
                    //System.out.println("Right over..."); SuperConcept = new ArrayList;
                }
            
        } catch (Exception e) {
        }
        //System.out.println("stng" + stng);
        return SuperConcept;
    }

    //public ArrayList processSuper(String Constrain) {
    public void processSuper() {
        try{
        t = printquerytree();
        }catch(Exception e){}
        // System.out.println("Constrain");

        ArrayList Temp_super = new ArrayList();
        SuperConcept = new ArrayList();
        try {
             FileWriter fw = new FileWriter("./crawl-unl/C_ICLTree.txt");
            Temp_super=visitTree(t.root);
            for(String o:SuperConcept){
                //System.out.println(">>"+o);
               fw.write(o+"\n");
            }
            //System.out.println("Final Method" + Temp_super);
        } catch (Exception e) {
        }
        //return Temp_super;
    }

    public static void main(String args[]) throws Exception {
        try {
            GenerateICLTree Q = new GenerateICLTree();
            Q.processSuper();

            /*String x="Idli(icl>food)";
            String y="icl>food";

            if(y.contains(x)){
            System.out.println("Available");
            }else{
            System.out.println("Not Available");
            }*/



        } catch (Exception e) {
        }
    }

}


