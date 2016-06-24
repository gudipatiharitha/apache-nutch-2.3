package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.index.unl;

/**
 * version history**
 *version number:1.1
@author subalalitha
 *

 *this class is used  to re build the CRC tree in such a way that the tree is built with c2 as hash code.
 * this aids query expansion using CRC indices
 */
import java.io.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class toconcepttree implements Serializable {

    BinarySearchTree t1 = new BinarySearchTree();
    ArrayList a = new ArrayList();
    int count = 0;

    /**expandQuery reads CRC tree from binary file .
     *calls printTree ,writeTree and readTree methods sequentially
     */
    public void expandQuery() {
        try {
            BinarySearchTree t = new BinarySearchTree();
            BinarySearchTree t2 = new BinarySearchTree();

            BinaryNode n = new BinaryNode();
            FileInputStream fis2 = new FileInputStream("./crawl-unl/crc.txt");
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            String str = "";
            t.root = t.tree_FileRead(t.root, ois2, str);
            printTree(t.root);
            //System.out.println("root" + t1);
            //printTree1(t1.root);
            writeTree(t1.root);
            readTree();
        } catch (Exception e) {
        }

    }

    /**loads the new CRC tree whose key is the hash code of toconcept
     *does this recursively in a inorder fashion
     */
    public void printTree(BinaryNode t) throws IOException {

        try {
            if (t != null) {
                printTree(t.left);
                loadQueryTree(t);
                //  printEntireTree(t);                
                printTree(t.right);
//System.out.println("the size"+a.size());
            }
        } catch (Exception e) {
        }


    }
    /*public void printEntireTree(BinaryNode t){

    }*/

//loadQueryTree gets the binary node as input and updates the binary next nodes whose hash code are same as that of binary node
    public void loadQueryTree(BinaryNode t) {

        try {
            BinaryNode b = new BinaryNode();
            BinaryNextNode bt = new BinaryNextNode();
            b = t;
            String str = b.tocon;
            int ascii = str.hashCode();

//  System.out.println("the cocept"+b.con);
            //  System.out.println("the concept inside if loop"+b.con);
            if (a.contains(new Integer(ascii))) {
                t1.insert_BinaryToNode(new Integer(ascii), b.con, b.rel, b.tocon, b.ind_tamil, b.vbcheck, b.tamcon, b.tovbcheck);
            } else {
                t1.insert(new Integer(ascii), b.con, b.rel, b.tocon, b.ind_tamil, b.vbcheck, b.tamcon, b.tovbcheck);
                a.add(new Integer(ascii));
            }

            if (b.linknext != null) {

                BinaryNextNode bt1 = new BinaryNextNode();
                //   b=t;
                bt1 = b.linknext;

                while (bt1 != null) {
                    String str1 = bt1.tocon;
                    int ascii1 = str1.hashCode();

//System.out.println("the concept inside else  loop"+bt1.con);
                    // System.out.println("str"+str+"count"+count);
                    if (a.contains(new Integer(ascii1))) {
                        t1.insert_BinaryToNode(new Integer(ascii1), bt1.con, bt1.rel, bt1.tocon, bt1.ind_tamil, bt1.vbcheck, bt1.tamcon, bt1.tovbcheck);
                    } else {
                        t1.insert(new Integer(ascii1), bt1.con, bt1.rel, bt1.tocon, bt1.ind_tamil, bt1.vbcheck, bt1.tamcon, bt1.tovbcheck);
                        a.add(new Integer(ascii1));
                    }

                    //count++;
                    bt1 = bt1.linknext;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(t.con);
        }


    }

    public void printTree1(BinaryNode b) throws IOException {
        try {
            PrintStream con = new PrintStream(new FileOutputStream(("query1.txt"), true));
            //BinaryNode b=new BinaryNode();
            //b=t.root;
            if (b != null) {
                printTree1(b.left);
            }
            {
                BinaryNextNode bt = new BinaryNextNode();
                bt = b.linknext;
                con.println(b.tamcon + "-" + b.rel + "-" + b.ind_tamil);
                while (bt != null) {
                    //con.println("linknext crcs");
                    con.println(bt.tamcon + "-" + bt.rel + "-" + bt.ind_tamil);
                    bt = bt.linknext;
                }
                printTree1(b.right);
            }
            con.close();
        } catch (Exception e) {
        }

    }

//writes the newly built tree into a binary file named crc_query
    public void writeTree(BinaryNode t) {
        try {
//S/ystem.out.println("have i co]===============e here"+t);
            FileOutputStream fos3 = new FileOutputStream("./crawl-unl/crc_query.txt");
//System.out.println("have i crossed"+t);

            ObjectOutputStream oos3 = new ObjectOutputStream(fos3);
//System.out.println("have i crossed1"+t);
            BinarySearchTree bst = new BinarySearchTree();
//System.out.println("have i crossed2"+t);
            bst.tree_FileWrite(t, oos3);
            oos3.close();
            fos3.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**reads the crc_query tree from binary file and loads to memory
     *calls visit tree which loads the tree into buffer writer

     */
    public void readTree() {
        try {
            BinarySearchTree t = new BinarySearchTree();
            FileInputStream fis2 = new FileInputStream("./crawl-unl/crc_query.txt");
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            String str = "";
            t.root = t.tree_FileRead(t.root, ois2, str);
//System.out.println("t value in readtree"+t.root);
            visitTree(t.root);
        } catch (Exception e) {
        }

    }
// visit tree traverses the crc_query tree into a buffer writer by invoking writeinbuffer method

    public void visitTree(BinaryNode t) {
        try {

            System.out.println("t value in visit tree" + t);
            if (t != null) {
                visitTree(t.left);
                writeinbuffer(t);
                visitTree(t.right);


            }
        } catch (Exception e) {
        }

    }
//writes the binarynode that is passed and also its linked binary next nodes into a buffered writer

    public void writeinbuffer(BinaryNode t) {
        try {
            BufferedWriter buff = new BufferedWriter(new FileWriter("Treeeeee.txt", true));
//buff.append("\n");
            buff.append(t.tamcon + "-" + t.rel + "-" + t.ind_tamil + "\n");

            while (t.linknext != null) {

                buff.append(t.linknext.tamcon + "-" + t.linknext.rel + "-" + t.linknext.ind_tamil + "\n");
//buff.append("\n");
                t.linknext = t.linknext.linknext;

            }

            buff.close();
        } catch (Exception e) {
        }

    }

    public BinaryNode calculateFrequencyforQuery(BinaryNode t) throws IOException {
        int counter = 0;

        int flag = 1;
        DocNode d = new DocNode();
        d = t.docnext;
        while (d != null) {
            counter++;

            if (counter >= 1) {
//System.out.println("have i come here");
                flag = 1;
                break;
            }
            d = d.docnext;
        }
        if (flag == 1) {
//System.out.println("inga vanthurukana"+t.tamcon);
            return t;
        } else {
            return null;
        }
    }

    public static void main(String args[]) throws IOException {
        toconcepttree q = new toconcepttree();
        q.expandQuery();

    }
}
