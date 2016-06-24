package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.index.unl;

/**
 * version history** version number:1.1 (indicates developments done after 5th
 * PRSG Meet)
 *
 * @author subalalitha
 */
/*
 * The package INDEX_BST consists of Structures(nodes) required to build the
 * application BianrySearchtree The class BinarySearchTree is created to store
 * and retrieve the indices effectively The Traditional BinarySearchTree is been
 * modified here to avoid collision and suit the search process Used for
 * Inverted indexing
 *
 */
import java.io.IOException;
import java.lang.*;
import java.io.*;
//import org.apache.nutch.search.unl.*;

public class BinarySearchTree implements Serializable {

    int count = 0;
    int count1 = 0;
    int count_crc = 0;
    int counter = 0;
    BinaryNode searchResult = new BinaryNode();
    //public static int tot_indices_cnt=0;	

    /**
     * empty constructor
     */
    public BinarySearchTree() {
        root = null;
    }
//The function tree_FileWrite is used to write the BinarySearch Tree into a binary file mainly used for searching

    public static void tree_FileWrite(
            BinaryNode r,
            ObjectOutputStream output) throws IOException {
        if (r != null) {
            output.writeObject("BINARY NODE EXISTS");
            output.writeObject(r.asciival);
            output.writeObject(r.con);
            output.writeObject(r.rel);
            output.writeObject(r.tocon);
            output.writeObject(r.ind_tamil);
            output.writeObject(r.docid);
            output.writeObject(r.senid);
            output.writeObject(r.weight);
            output.writeObject(r.vbcheck);
            output.writeObject(r.frequency);
            output.writeObject(r.tamcon);
            output.writeObject(r.tovbcheck);
            output.writeObject(r.crc_count);

            DocNode d = new DocNode();
            d = r.docnext;
            while (d != null) {
                output.writeObject("DOC NODE EXISTS");
                output.writeObject(d.docid);
                output.writeObject(d.weight);
                output.writeObject(d.senid);
                output.writeObject(d.frequency);
                output.writeObject(d.gn_word);
                output.writeObject(d.vbcheck);
                output.writeObject(d.tovbcheck);
                output.writeObject(d.tamcon);
                d = d.docnext;
            }
            output.writeObject("NO DOC NODE");

            BinaryNextNode bnode = new BinaryNextNode();
            bnode = r.linknext;
            while (bnode != null) {
                output.writeObject("Binary NEXT NODE EXISTS");
                output.writeObject(bnode.con);
                output.writeObject(bnode.rel);
                output.writeObject(bnode.tocon);
                output.writeObject(bnode.ind_tamil);
                output.writeObject(bnode.docid);
                output.writeObject(bnode.senid);
                output.writeObject(bnode.weight);
                output.writeObject(bnode.vbcheck);
                output.writeObject(bnode.frequency);
                output.writeObject(bnode.tamcon);
                output.writeObject(bnode.tovbcheck);
                DocNode dw = new DocNode();
                dw = bnode.docnext;
                while (dw != null) {
                    output.writeObject("DOC NODE EXISTS");
                    output.writeObject(dw.docid);
                    output.writeObject(dw.weight);
                    output.writeObject(dw.senid);
                    output.writeObject(dw.frequency);
                    output.writeObject(dw.gn_word);
                    output.writeObject(dw.vbcheck);
                    output.writeObject(dw.tovbcheck);
                    output.writeObject(dw.tamcon);
                    dw = dw.docnext;
                }
                output.writeObject("NO DOC NODE");
                bnode = bnode.linknext;
            }
            output.writeObject("NO BNODE");
            output.writeObject("LEFT BNODE");

            tree_FileWrite(r.left, output);
            output.writeObject("RIGHT BNODE");

            tree_FileWrite(r.right, output);
        } else {
            output.writeObject("BINARY NODE DOES NOT EXISTS");
            return;
        }
    }
//The function tree_FileRead is used to read the BinarySearch Tree from  a binary file 

    public static BinaryNode tree_FileRead(BinaryNode r, ObjectInputStream input, String str) throws IOException, ClassNotFoundException {
        try {


            str = (String) input.readObject();
            if (str.equalsIgnoreCase("BINARY NODE EXISTS")) {

                r = new BinaryNode();

                r.asciival = (Integer) input.readObject();
                r.con = (String) input.readObject();
                r.rel = (String) input.readObject();
                r.tocon = (String) input.readObject();
                r.ind_tamil = (String) input.readObject();
                r.docid = ((Integer) input.readObject()).intValue();
                r.senid = (String) input.readObject();
                r.weight = ((Integer) input.readObject()).intValue();
                r.vbcheck = (String) input.readObject();
                r.frequency = ((Integer) input.readObject()).intValue();
                r.tamcon = (String) input.readObject();
                r.tovbcheck = (String) input.readObject();
                r.crc_count = (Integer) input.readObject();
                str = (String) input.readObject();

                if (str.equalsIgnoreCase("DOC NODE EXISTS")) {
                    DocNode dr = new DocNode();
                    dr.docid = ((Integer) input.readObject()).intValue();
                    dr.weight = ((Integer) input.readObject()).intValue();
                    dr.senid = (String) input.readObject();
                    dr.frequency = ((Integer) input.readObject()).intValue();
                    dr.gn_word = (String) input.readObject();
                    dr.vbcheck = (String) input.readObject();
                    dr.tovbcheck = (String) input.readObject();
                    dr.tamcon = (String) input.readObject();
                    r.docnext = dr;
                    str = (String) input.readObject();
                    while (str.equalsIgnoreCase("DOC NODE EXISTS")) {
                        DocNode dr1 = new DocNode();
                        dr1.docid = ((Integer) input.readObject()).intValue();
                        dr1.weight = ((Integer) input.readObject()).intValue();
                        dr1.senid = (String) input.readObject();
                        dr1.frequency = ((Integer) input.readObject()).intValue();
                        dr1.gn_word = (String) input.readObject();
                        dr1.vbcheck = (String) input.readObject();
                        dr1.tovbcheck = (String) input.readObject();
                        dr1.tamcon = (String) input.readObject();
                        dr.docnext = dr1;
                        dr = dr1;
                        str = (String) input.readObject();
                    }

                }
                str = (String) input.readObject();
                if (str.equalsIgnoreCase("BINARY NEXT NODE EXISTS")) {

                    BinaryNextNode bnode = new BinaryNextNode();
                    bnode.con = (String) input.readObject();
                    bnode.rel = (String) input.readObject();
                    bnode.tocon = (String) input.readObject();
                    bnode.ind_tamil = (String) input.readObject();
                    bnode.docid = ((Integer) input.readObject()).intValue();
                    bnode.senid = (String) input.readObject();
                    bnode.weight = ((Integer) input.readObject()).intValue();
                    bnode.vbcheck = (String) input.readObject();
                    bnode.frequency = ((Integer) input.readObject()).intValue();
                    bnode.tamcon = (String) input.readObject();
                    bnode.tovbcheck = (String) input.readObject();
                    r.linknext = bnode;
                    //node=r.linknext;//debugging

                    while (str.equalsIgnoreCase("BINARY NEXT NODE EXISTS")) {
                        str = (String) input.readObject();
                        if (str.equalsIgnoreCase("DOC NODE EXISTS")) {
                            DocNode dw = new DocNode();
                            dw.docid = ((Integer) input.readObject()).intValue();
                            dw.weight = ((Integer) input.readObject()).intValue();
                            dw.senid = (String) input.readObject();
                            dw.frequency = ((Integer) input.readObject()).intValue();
                            dw.gn_word = (String) input.readObject();
                            dw.vbcheck = (String) input.readObject();
                            dw.tovbcheck = (String) input.readObject();
                            dw.tamcon = (String) input.readObject();
                            bnode.docnext = dw;
                            DocNode dw1 = new DocNode();
                            str = (String) input.readObject();
                            while (str.equalsIgnoreCase("DOC NODE EXISTS")) {
                                dw1.docid = ((Integer) input.readObject()).intValue();
                                dw1.weight = ((Integer) input.readObject()).intValue();
                                dw1.senid = (String) input.readObject();
                                dw1.frequency = ((Integer) input.readObject()).intValue();
                                dw1.gn_word = (String) input.readObject();
                                dw1.vbcheck = (String) input.readObject();
                                dw1.tovbcheck = (String) input.readObject();
                                dw1.tamcon = (String) input.readObject();
                                dw.docnext = dw1;
                                dw = dw.docnext;
                                dw1 = new DocNode();
                                str = (String) input.readObject();

                            }
                        }
                        str = (String) input.readObject();
                        if (str.equalsIgnoreCase("BINARY NEXT NODE EXISTS")) {
                            BinaryNextNode bnode1 = new BinaryNextNode();
                            bnode1.con = (String) input.readObject();
                            bnode1.rel = (String) input.readObject();
                            bnode1.tocon = (String) input.readObject();
                            bnode1.ind_tamil = (String) input.readObject();
                            bnode1.docid = ((Integer) input.readObject()).intValue();
                            bnode1.senid = (String) input.readObject();
                            bnode1.weight = ((Integer) input.readObject()).intValue();
                            bnode1.vbcheck = (String) input.readObject();
                            bnode1.frequency = ((Integer) input.readObject()).intValue();
                            bnode1.tamcon = (String) input.readObject();
                            bnode1.tovbcheck = (String) input.readObject();
                            bnode.linknext = bnode1;
                            bnode = bnode.linknext;
                        }
                    }
                }
                //display_connectedconcepts(r);//debugging
                str = (String) input.readObject();

                if (str.equalsIgnoreCase("LEFT BNODE")) {
                    r.left = tree_FileRead(r.left, input, str);
                }

                str = (String) input.readObject();


                if (str.equalsIgnoreCase("RIGHT BNODE")) {
                    r.right = tree_FileRead(r.right, input, str);
                }

            }
        } catch (EOFException e) {
        }
        return r;


    }

    /**
     * Method insert loads the concepts,relation,connected concepts ,actual
     * tamil word ,document id using the hash code of the concepts
     */
    public void insert(Comparable asciiv, String concept_name, String reln, String Tocon, String Stamil, int i, String sen, int weight, String vb, int freq, String tamcon, String topos) {
        //chezhiyank
        int bitPattern = 0;
        bitPattern = addSententenceId(bitPattern, sen);
        root = insert(asciiv, concept_name, reln, Tocon, Stamil, i, String.valueOf(bitPattern), weight, vb, freq, tamcon, topos, root);



        // root = insert(asciiv, concept_name, reln, Tocon, Stamil, i, sen, weight, vb, freq, tamcon, topos, root);
    }

    public void insert(Comparable asciiv, String concept_name, String reln, String Tocon, String Stamil, String vb, String tamcon, String topos) {
        root = insert(asciiv, concept_name, reln, Tocon, Stamil, vb, tamcon, topos, root);
    }

    /**
     * The method find & retrieves the BinaryNode given its hash code(ascii value
     */
    public BinaryNode find(Comparable x) {
        return find(x, root);
    }

    public void find1(ConceptNode x) {
        find1(x, root);
    }

    /**
     * The method makeEmpty destroys the tree
     */
    public void makeEmpty() {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree() {
        try {

            if (isEmpty()) {
                //System.out.println("Empty tree");
            } else {
                printTree(root);
            }
        } catch (Exception e) {
        }
    }

    public void countCRCs() {
        try {

            if (isEmpty()) {
                //System.out.println("Empty tree");
            } else {
                countCRCs(root);
            }
        } catch (Exception e) {
        }
    }
//method written to cross check the print tree

    public void visitTree(String s) throws IOException {

        try {

            if (isEmpty()) {
                //System.out.println("Empty tree");
            } else {
                visitTree(root, s);
            }
        } catch (Exception e) {
        }


    }
//method written to cross check the print tree

    public void visitTree(BinaryNode t, String s) throws IOException {

        PrintStream con = new PrintStream(new FileOutputStream(("query.txt"), true));
        if (t != null) {
            visitTree(t.left, s);
            if (s.equals(t.tamcon)) {
                con.println(t.tamcon + "-" + t.rel + "-" + t.ind_tamil);
                BinaryNextNode b = new BinaryNextNode();
                b = t.linknext;
                while (b != null) {

                    con.println("the connected concepts" + b.tamcon);
                    b = b.linknext;
                }

            }
            visitTree(t.right, s);
        }


    }

    /**
     * Method insert loads the concepts,relation,connected concepts ,actual
     * tamil word ,document id using the hash code of the concepts
     */
    public BinaryNode insert(Comparable asciiv, String concept_name, String reln, String Tocon, String Stamil, int i, String sen, int weight, String vb, int freq, String tamcon, String topos, BinaryNode t) {
        //System.out.println("INSERT");


        if (t == null) {
            counter++;
            count++;
            t = new BinaryNode();
            t.asciival = asciiv;
            t.con = concept_name;
            t.tocon = Tocon;
            t.rel = reln;
            t.ind_tamil = Stamil;
            t.docid = i;
            t.senid = sen;
            t.weight = weight;
            t.frequency = freq;
            t.vbcheck = vb;
            t.tovbcheck = topos;
            t.tamcon = tamcon;

        } else if (asciiv.compareTo(t.asciival) < 0) {
            t.left = insert(asciiv, concept_name, reln, Tocon, Stamil, i, sen, weight, vb, freq, tamcon, topos, t.left);
        } else if (asciiv.compareTo(t.asciival) > 0) {
            t.right = insert(asciiv, concept_name, reln, Tocon, Stamil, i, sen, weight, vb, freq, tamcon, topos, t.right);

        } else;
        return t;
    }

    /**
     * Method insert loads the concepts,relation,connected concepts ,actual
     * tamil word ,document id using the hash code of the concepts for tree
     * build with hash code of C2 concept
     */
    public BinaryNode insert(Comparable asciiv, String concept_name, String reln, String Tocon, String Stamil, String vb, String tamcon, String topos, BinaryNode t) {

        if (t == null) {
            count++;
            t = new BinaryNode();
            t.asciival = asciiv;
            t.con = concept_name;
            t.tocon = Tocon;
            t.rel = reln;
            t.ind_tamil = Stamil;
            t.vbcheck = vb;
            t.tamcon = tamcon;
            t.tovbcheck = topos;

        } else if (asciiv.compareTo(t.asciival) < 0) {
            t.left = insert(asciiv, concept_name, reln, Tocon, Stamil, vb, tamcon, topos, t.left);
        } else if (asciiv.compareTo(t.asciival) > 0) {
            t.right = insert(asciiv, concept_name, reln, Tocon, Stamil, vb, tamcon, topos, t.right);

        } else;
        return t;
    }

    /**
     * The method insert_BinaryToNode inserts the dissimilar concepts having
     * same hash code This method finds the BinaryNode with the help of find()
     * method and inserts the concept using the BinaryNextNode This method is
     * used for the indices c-r-c
     */
    public void insert_BinaryToNode(Comparable asciiv, String concept_name, String reln, String Tocon, String Stamil, int i, String sen, int weight, String vb, int freq, String tam, String topos) {




        int bitPattern = 0;
        int bitpatternBinaryNextNode = 0;

        BinaryNode node = new BinaryNode();
        node = find(asciiv);
        BinaryNextNode bbnode = new BinaryNextNode();

        bitpatternBinaryNextNode = addSententenceId(0, sen);
        BinaryNextNode bnode = new BinaryNextNode(asciiv, concept_name, reln, Tocon, Stamil, i, String.valueOf(bitpatternBinaryNextNode), weight, vb, freq, tam, topos);
        DocNode dc1 = new DocNode();
        DocNode dc2 = new DocNode();
        BinaryNextNode node1 = new BinaryNextNode();
        BinaryNextNode bbnode1 = new BinaryNextNode();
        DocNode dc = new DocNode();
        DocNode dc3 = new DocNode();
        DocNode dc101 = new DocNode();
        DocNode dc4 = new DocNode();
        node1 = node.linknext;
        int flar = 0;
        if ((node.con + node.rel + node.tocon + node.ind_tamil).equals(concept_name + reln + Tocon + Stamil)) {
            if (node.docid == i) {
                //chezhiyank
                bitPattern = addSententenceId(Integer.parseInt(node.senid), sen);
                node.senid = String.valueOf(bitPattern);


            } else if (node.docnext == null) {
                count_crc++;
                node.docnext = dc;
                dc.docid = i;
                //dc.senid = sen;
                bitPattern = addSententenceId(0, sen);
                dc.senid = String.valueOf(bitPattern);
                dc.weight = weight;
                dc.frequency = freq;
                dc.gn_word = Stamil;
                dc.vbcheck = vb;
                dc.tovbcheck = topos;
                dc.tamcon = tam;
            } else {

                dc1 = node.docnext;
                while (dc1.docnext != null) {
                    if (dc1.docid == i) {
                        flar = 1;
                        break;
                    }
                    dc1 = dc1.docnext;
                }
                if (flar == 1) {
                    bitPattern = addSententenceId(Integer.parseInt(dc1.senid), sen);
                    dc1.senid = String.valueOf(bitPattern);

                }
                if (flar == 0) {
                    count_crc++;
                    dc1.docnext = dc;
                    dc.docid = i;
                    dc.weight = weight;
                    //dc.senid = sen;
                    bitPattern = addSententenceId(0, sen);
                    dc.senid = String.valueOf(bitPattern);
                    dc.frequency = freq;
                    dc.gn_word = Stamil;
                    dc.vbcheck = vb;
                    dc.tovbcheck = topos;
                    dc.tamcon = tam;
                }
            }
        } else if (!(node.con + node.rel + node.tocon + node.ind_tamil).equals(concept_name + reln + Tocon + Stamil)) {
            int flagg = 0;
            if (node.linknext == null) {

                node.linknext = bnode;
                counter++;
                count++;
            } else {


                while (node1 != null) {
                    bbnode1 = node1;
                    if ((bbnode1.con + bbnode1.rel + bbnode1.tocon + bbnode1.ind_tamil).equals(concept_name + reln + Tocon + Stamil)) {
                        flagg = 1;
                        break;
                    }

                    node1 = node1.linknext;
                }

                if (flagg == 0) {

                    bbnode1.linknext = bnode;
                    counter++;


                } else if (flagg == 1) {
                    if (bbnode1.docnext == null) {
                        dc3.docid = i;
                        dc3.weight = weight;
                        bitpatternBinaryNextNode = addSententenceId(0, sen);
                        dc3.senid = String.valueOf(bitpatternBinaryNextNode);
                        //dc3.senid = sen;
                        dc3.frequency = freq;
                        dc3.gn_word = Stamil;
                        dc3.vbcheck = vb;
                        dc3.tovbcheck = topos;
                        dc3.tamcon = tam;
                        bbnode1.docnext = dc3;
                        counter++;
                    } else if (bbnode1.docnext != null) {

                        dc4 = bbnode1.docnext;
                        while (dc4 != null) {
                            dc101 = dc4;

                            if (dc101.docid == i) {
                                flar = 1;
                                break;
                            }
                            dc4 = dc4.docnext;
                        }
                        if (flar == 1) {
                            bitPattern = addSententenceId(Integer.parseInt(dc101.senid), sen);
                            dc3.senid = String.valueOf(bitPattern);
                            //dc3.senid = dc3.senid + "," + sen;//dc101

                        } else if (flar == 0) {

                            dc3.docid = i;
                            dc3.weight = weight;
                            //dc3.senid = sen;
                            bitPattern = addSententenceId(0, sen);
                            dc3.senid = String.valueOf(bitPattern);
                            dc3.frequency = freq;
                            dc3.gn_word = Stamil;
                            dc3.vbcheck = vb;
                            dc3.tovbcheck = topos;
                            dc3.tamcon = tam;
                            dc101.docnext = dc3;
                            counter++;
                        }
                    }

                }
                count++;
            }
        }

    }

    /**
     * this method is used for populating the crc tree using C2's hash code The
     * method insert_BinaryToNode inserts the dissimilar concepts having same
     * hash code This method finds the BinaryNode with the help of find() method
     * and inserts the concept using the BinaryNextNode This method is used for
     * the indices c-r-c
     */
    public void insert_BinaryToNode(Comparable asciiv, String concept_name, String reln, String Tocon, String Stamil, String vb, String tam, String topos) {


        BinaryNode node = new BinaryNode();
        node = find(asciiv);
        BinaryNextNode bbnode = new BinaryNextNode();
        BinaryNextNode bbnode1 = new BinaryNextNode();
        BinaryNextNode bnode = new BinaryNextNode(asciiv, concept_name, reln, Tocon, Stamil, vb, tam, topos);
        BinaryNextNode node1 = new BinaryNextNode();
        bbnode = node.linknext;

        if (node.linknext == null) {
            node.linknext = bnode;
        } else {
            while (bbnode != null) {
                bbnode1 = bbnode;

                bbnode = bbnode.linknext;
            }
            bbnode1.linknext = bnode;
        }
    }

    /**
     * The method insert_BinaryToNode1 inserts the dissimilar concepts having
     * same hash code This method finds the BinaryNode with the help of find()
     * method and inserts the concept using the BinaryNextNode This method is
     * used for the indices c-r
     */
    public void insert_BinaryToNode1(Comparable asciiv, String concept_name, String reln, String Tocon, String Stamil, int i, String sen, int weigh, String vb, int freq, String tam, String topos) {

        int bitPattern = 0;
        int bitpatternBinaryNextNode = 0;
        BinaryNode node = new BinaryNode();
        node = find(asciiv);
        BinaryNextNode bbnode = new BinaryNextNode();

        bitpatternBinaryNextNode = addSententenceId(0, sen);

        BinaryNextNode bnode = new BinaryNextNode(asciiv, concept_name, reln, Tocon, Stamil, i, String.valueOf(bitpatternBinaryNextNode), weigh, vb, freq, tam, topos);
        DocNode dc1 = new DocNode();
        DocNode dc2 = new DocNode();
        BinaryNextNode node1 = new BinaryNextNode();
        BinaryNextNode bbnode1 = new BinaryNextNode();
        DocNode dc = new DocNode();
        node1 = node.linknext;
        DocNode dc3 = new DocNode();
        DocNode dc101 = new DocNode();
        DocNode dc4 = new DocNode();
        int flar = 0;
        if ((node.con + node.rel + node.ind_tamil).equals(concept_name + reln + Stamil)) {


            if (node.docid == i) {
                //chezhiyank
                bitPattern = addSententenceId(Integer.parseInt(node.senid), sen);
                node.senid = String.valueOf(bitPattern);

            } else if (node.docnext == null) {


                //count++;//debuggging
                node.docnext = dc;
                dc.docid = i;
                //dc.senid = sen;
                //chezhiyank
                bitPattern = addSententenceId(0, sen);
                dc.senid = String.valueOf(bitPattern);
                dc.weight = weigh;
                dc.frequency = freq;
                dc.gn_word = Stamil;
                dc.vbcheck = vb;
                dc.tamcon = tam;
                dc.tovbcheck = topos;

            } else {

                dc1 = node.docnext;
                while (dc1.docnext != null) {
                    if (dc1.docid == i) {
                        flar = 1;
                        break;
                    }
                    dc1 = dc1.docnext;
                }
                if (flar == 1) {
                    //chezhiyank
                    bitPattern = addSententenceId(Integer.parseInt(dc1.senid), sen);
                    dc1.senid = String.valueOf(bitPattern);

                }
                if (flar == 0) {
                    //count++;//debugging
                    dc1.docnext = dc;
                    dc.docid = i;
                    //dc.senid = sen;
                    //chezhiyank
                    bitPattern = addSententenceId(0, sen);
                    dc.senid = String.valueOf(bitPattern);
                    dc.weight = weigh;
                    dc.frequency = freq;
                    dc.gn_word = Stamil;
                    dc.vbcheck = vb;
                    dc.tamcon = tam;
                    dc.tovbcheck = topos;
                }

            }
        } else if (!(node.con + node.rel + node.tocon + node.ind_tamil).equals(concept_name + reln + Tocon + Stamil)) {
            int flagg = 0;
            if (node.linknext == null) {
                //out.println("in c insert 7"+concept_name+Stamil);//dbugging
                node.linknext = bnode;
                counter++;
                count++;
            } else {


                while (node1 != null) {
                    bbnode1 = node1;
                    if ((bbnode1.con + bbnode1.rel + bbnode1.tocon + bbnode1.ind_tamil).equals(concept_name + reln + Tocon + Stamil)) {
                        flagg = 1;
                        break;
                    }
                    //out.println("in c insert 9"+concept_name+Stamil);//debugging
                    node1 = node1.linknext;
                }
                //out.println("flagg"+flagg);//debugging
                if (flagg == 0) {

                    bbnode1.linknext = bnode;
                    counter++;


                } else if (flagg == 1) {
                    if (bbnode1.docnext == null) {
                        dc3.docid = i;
                        dc3.weight = weigh;
                        bitpatternBinaryNextNode = addSententenceId(0, sen);
                        dc3.senid = String.valueOf(bitpatternBinaryNextNode);
                        //dc3.senid = sen;
                        dc3.frequency = freq;
                        dc3.gn_word = Stamil;
                        dc3.vbcheck = vb;
                        dc3.tovbcheck = topos;
                        dc3.tamcon = tam;
                        bbnode1.docnext = dc3;
                        counter++;
                    } else if (bbnode1.docnext != null) {

                        dc4 = bbnode1.docnext;
                        while (dc4 != null) {
                            dc101 = dc4;

                            if (dc101.docid == i) {
                                flar = 1;
                                break;
                            }
                            dc4 = dc4.docnext;
                        }
                        if (flar == 1) {
                            //chezhiyank
                            bitPattern = addSententenceId(Integer.parseInt(dc101.senid), sen);
                            dc3.senid = String.valueOf(bitPattern);

                        } else if (flar == 0) {

                            dc3.docid = i;
                            dc3.weight = weigh;
                            //dc3.senid = sen;
                            //chezhiyank
                            bitPattern = addSententenceId(0, sen);
                            dc3.senid = String.valueOf(bitPattern);
                            dc3.frequency = freq;
                            dc3.gn_word = Stamil;
                            dc3.vbcheck = vb;
                            dc3.tovbcheck = topos;
                            dc3.tamcon = tam;
                            dc101.docnext = dc3;
                            counter++;
                        }
                    }

                }
                count++;
            }
        }

    }

    /**
     * This
     *
     * @param bitPattern
     * @param sentenceId
     * @return
     */
    private int powprocess(int pow, int value) {
        int math = 1;
        if (value == 0) {
            return 1;
        } else {
            math = (math * pow) * powprocess(pow, value - 1);
        }
        return math;
    }

    private int addSententenceId(int bitPattern, String sentenceId) {
        int newSentenceId = Integer.parseInt(sentenceId.substring(1));
        if (newSentenceId < 32) {
            int getPattern = powprocess(2, newSentenceId - 1);
            bitPattern = bitPattern | getPattern;
        }
        return bitPattern;

    }

    /**
     * The method insert_BinaryToNode2 inserts the dissimilar concepts having
     * same hash code This method finds the BinaryNode with the help of find()
     * method and inserts the concept using the BinaryNextNode This method is
     * used for the indices c
     */
    public void insert_BinaryToNode2(Comparable asciiv, String concept_name, String reln, String Tocon, String Stamil, int i, String sen, int weigh, String vb, int freq, String tamcon, String topos) {




        int bitPattern = 0;
        int bitpatternBinaryNextNode = 0;
        BinaryNode node = new BinaryNode();
        node = find(asciiv);
        BinaryNextNode bbnode = new BinaryNextNode();

        bitpatternBinaryNextNode = addSententenceId(0, sen);

        BinaryNextNode bnode = new BinaryNextNode(asciiv, concept_name, reln, Tocon, Stamil, i, String.valueOf(bitpatternBinaryNextNode), weigh, vb, freq, tamcon, topos);
        DocNode dc1 = new DocNode();
        DocNode dc2 = new DocNode();
        DocNode dc100 = new DocNode();
        BinaryNextNode node1 = new BinaryNextNode();
        BinaryNextNode bbnode1 = new BinaryNextNode();
        DocNode dc = new DocNode();
        DocNode dc3 = new DocNode();
        DocNode dc101 = new DocNode();
        DocNode dc4 = new DocNode();

        node1 = node.linknext;

        int flar = 0;

        //chezhiyank

        if ((node.con + node.ind_tamil).equals(concept_name + Stamil)) {
            if (node.docid == i) {
                //chezhiyank                
                //int bitPattern=0;
                bitPattern = addSententenceId(Integer.parseInt(node.senid), sen);
                node.senid = String.valueOf(bitPattern);

            } else if (node.docnext == null) {

                counter++;
                node.docnext = dc;
                dc.docid = i;
                dc.weight = weigh;
                bitPattern = addSententenceId(0, sen);
                dc.senid = String.valueOf(bitPattern);
                dc.frequency = freq;
                dc.gn_word = Stamil;
                dc.vbcheck = vb;
                dc.tamcon = tamcon;
                dc.tovbcheck = topos;
            } else {
                dc1 = node.docnext;
                //dc1=dc;//debugging
                while (dc1 != null) {
                    dc100 = dc1;
                    if (dc1.docid == i) {

                        flar = 1;
                        break;
                    }
                    dc1 = dc1.docnext;
                }
                if (flar == 1) {
                    //chezhiyank
                    //System.out.println("Both Binary Node Doc next Docid is available");
                    bitPattern = addSententenceId(Integer.parseInt(dc1.senid), sen);
                    dc1.senid = String.valueOf(bitPattern);


                } else if (flar == 0) {

                    dc100.docnext = dc;
                    dc.docid = i;
                    dc.weight = weigh;
                    //dc.senid = sen;
                    bitPattern = addSententenceId(0, sen);
                    dc.senid = String.valueOf(bitPattern);
                    dc.frequency = freq;
                    dc.gn_word = Stamil;
                    dc.vbcheck = vb;
                    dc.tovbcheck = topos;
                    dc.tamcon = tamcon;
                    counter++;
                }



            }
        } else if (!(node.con + node.ind_tamil).equals(concept_name + Stamil)) {

            int flagg = 0;

            if (node.linknext == null) {
                node.linknext = bnode;
                counter++;
                count++;
            } else {
                while (node1 != null) {
                    bbnode1 = node1;
                    if ((bbnode1.con + bbnode1.ind_tamil).equals(concept_name + Stamil)) {

                        flagg = 1;
                        break;
                    }

                    node1 = node1.linknext;
                }

                if (flagg == 0) {

                    bbnode1.linknext = bnode;
                    counter++;


                } else if (flagg == 1) {



                    if (bbnode1.docnext == null) {


                        if (bbnode1.ind_tamil.equals(Stamil)) {
                            dc3.docid = i;
                            dc3.weight = weigh;

                            bitpatternBinaryNextNode = addSententenceId(0, sen);
                            dc3.senid = String.valueOf(bitpatternBinaryNextNode);
                            //dc3.senid = sen;
                            dc3.frequency = freq;
                            dc3.gn_word = Stamil;
                            dc3.vbcheck = vb;
                            dc3.tovbcheck = topos;
                            dc3.tamcon = tamcon;
                            bbnode1.docnext = dc3;
                            counter++;
                        } else {
                            //System.out.println("not same***");
                        }
                    } else if (bbnode1.docnext != null) {

                        dc4 = bbnode1.docnext;
                        while (dc4 != null) {
                            dc101 = dc4;

                            if (dc101.docid == i) {
                                flar = 1;
                                break;
                            }
                            dc4 = dc4.docnext;
                        }
                        if (flar == 1) {
                            //chezhiyank last changes
                            bitPattern = addSententenceId(Integer.parseInt(dc101.senid), sen);
                            dc3.senid = String.valueOf(bitPattern);
                            //dc3.senid = dc3.senid + "," + sen;//dc101


                        } else if (flar == 0) {

                            dc3.docid = i;

                            dc3.weight = weigh;
                            //dc3.senid = sen;
                            bitPattern = addSententenceId(0, sen);
                            dc3.senid = String.valueOf(bitPattern);
                            dc3.frequency = freq;
                            dc3.gn_word = Stamil;
                            dc3.vbcheck = vb;
                            dc3.tovbcheck = topos;
                            dc3.tamcon = tamcon;
                            dc101.docnext = dc3;
                            counter++;
                        }
                    }

                }
                count++;
            }
        }

    }
//method used for debugging by counting the method calls

    public int get_counter() {

        System.out.println("the count" + counter);
        return counter;

    }
//given the key,locates the binary node 

    public BinaryNode find(Comparable x, BinaryNode t) {
        if (t == null) {
            return null;
        }
        if (x.compareTo(t.asciival) < 0) {
            return find(x, t.left);
        } else if (x.compareTo(t.asciival) > 0) {
            return find(x, t.right);
        } else {
            return t;
        }


    }
//written for cross checking the find method

    public void find1(ConceptNode x, BinaryNode t) {
        if (t != null) {
            find1(x, t.left);
            display_connectedconcepts(x, t);
            find1(x, t.right);
        }

    }
//prints the BST in an inorder fashion

    public void printTree(BinaryNode t) {
        if (t != null) {
            printTree(t.left);
            display_connectedconcepts1(t);
            printTree(t.right);
        }
    }

    public void countCRCs(BinaryNode t) {
        if (t != null) {
            countCRCs(t.left);
            t = updateCRCCount(t);
            countCRCs(t.right);
        }
    }

    public BinaryNode updateCRCCount(BinaryNode t) {
        BinaryNextNode bn = new BinaryNextNode();
        bn = t.linknext;
        int count = 0;
        while (bn != null) {
            System.out.println("Inside count loop");
            count++;
            bn = bn.linknext;
        }

        t.crc_count = count;
        return t;
    }

    /**
     * The method display_connectedconcepts displays the indices right form the
     * root . When displaying the content of the BinaryNode ,the respective
     * BinaryNextNodes ,DocNodes are also displayed
     */
    public void display_connectedconcepts(ConceptNode x, BinaryNode te) {



        try {
            if (te != null) {
                ConceptToNode ToCpt1 = x.rownext;
                if ((x.uwconcept).equals(te.con)) {

                    if ((ToCpt1.relnlabel).equals(te.rel)) {
                        searchResult = te;
                        display_connectedconcepts(te);
                    }

                }

                BinaryNextNode bnode = new BinaryNextNode();
                bnode = te.linknext;
                if (bnode != null) {
                    while (bnode != null) {
                        // System.out.println(x.uwconcept + "   " + bnode.con);//debugging
                        if ((x.uwconcept).equals(bnode.con)) {
                            // System.out.println(ToCpt1.relnlabel);
                            if ((ToCpt1.relnlabel).equals(bnode.rel)) {
                                searchResult = te;
                                display_connectedconcepts(te);
                            }

                        }
                        bnode = bnode.linknext;
                    }
                }
            }
        } catch (Exception e) {
        }

    }

    public BinaryNode get() {
        return searchResult;
    }

    /**
     * The method CharToASCII returns the hash code of the concept
     */
    public int CharToASCII(String str) throws IOException {
        int k = 0;
        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);

            int j = (int) c;
            k = j + k;

        }
        return k;
    }

    /**
     * The method display_connectedconcepts displays the indices right form the
     * root . When displaying the content of the BinaryNode ,the respective
     * BinaryNextNodes ,DocNodes are also displayed
     */
    public void display_connectedconcepts(BinaryNode te) {

        try {
            BufferedWriter buff = new BufferedWriter(new FileWriter("Tree.txt", true));
            //PrintStream buff.append = new PrintStream(new FileOutputStream(("printtree.txt"),true));//debugging
            //int count=0;//debugging

            DocNode dr = new DocNode();
            dr = te.docnext;
            if (te != null) {
                //  count1++;
                BinaryNextNode bnode = new BinaryNextNode();
                //System.out.println("te.con"+te.con);//debugging
                //System.out.println("te.tovbcheck"+te.tovbcheck);//debugging
                buff.append("\n");
                buff.append("the concept" + "-" + te.con + "\n");
                buff.append("the relation" + "-" + te.rel + "\n");
                buff.append("the toconcept" + "-" + te.tocon + "\n");
                buff.append("the document id" + "-" + te.docid + "\n");
                buff.append("the sentence id " + "-" + te.senid + "\n");
                buff.append("the weight of the index " + "-" + te.weight + "\n");
                buff.append("the ascii of the index " + "-" + te.asciival + "\n");
                buff.append("Freq count of actual tamil word " + "-" + te.frequency + "\n");
                buff.append("tamil word  from concept" + "-" + te.ind_tamil + "\n");
                buff.append("the tamil toconcept" + te.tamcon + "\n");
                buff.append("tamil word POS" + "-" + te.vbcheck + "\n");
                buff.append("tamil topos" + te.tovbcheck + "\n");
                buff.append("CRC count" + te.crc_count + "\n");

                while (dr != null) {
                    //  count1++;
                    // buff.append("yessssssssssssssssssssssssssss11111111111");//debugging
                    buff.append("the document id" + "-" + dr.docid + "\n");
                    buff.append("the weight " + dr.weight + "\n");
                    buff.append("the senid " + dr.senid + "\n");
                    buff.append("Freq count of actual tamil word " + "-" + dr.frequency + "\n");
                    buff.append("tamil word " + "-" + dr.gn_word + "\n");
                    buff.append("tamil word POS" + "-" + dr.vbcheck + "\n");
                    buff.append("tamil word topos" + "-" + dr.tovbcheck + "\n");
                    buff.append("tamil word totamil" + "-" + dr.tamcon + "\n");
                    dr = dr.docnext;
                }
                bnode = te.linknext;
                if (bnode != null) {
                    DocNode dw = new DocNode();

                    while (bnode != null) {
                        //  count1++;
                        buff.append("\n");
//buff.append("nooooooooooooooooooooooooooooooooooooo");   //debugging
                        buff.append("the concept" + "-" + bnode.con + "\n");
                        buff.append("the relation" + "-" + bnode.rel + "\n");
                        buff.append("the toconcept" + "-" + bnode.tocon + "\n");
                        buff.append("the document id" + "-" + bnode.docid + "\n");
                        buff.append("the sentence id" + "-" + bnode.senid + "\n");
                        buff.append("the weight of the index " + "-" + bnode.weight + "\n");
                        buff.append("Freq count of actual tamil word " + "-" + bnode.frequency + "\n");
                        buff.append("tamil from concept " + "-" + bnode.ind_tamil + "\n");
                        buff.append("the tamil toconcept" + bnode.tamcon + "\n");
                        buff.append("tamil word POS " + "-" + bnode.vbcheck + "\n");
                        buff.append("tamil word topos" + "-" + bnode.tovbcheck + "\n");
                        //buff.append("the ascii of the index "+"-"+te.asciival+"\n");//debugging
                        dw = bnode.docnext;
                        while (dw != null) {
                            //   count1++;
                            buff.append("yessssssssssssssssssssssssssss");//debugging
                            buff.append("the document id" + "-" + dw.docid + "\n");
                            buff.append("the weight " + dw.weight + "\n");
                            buff.append("the sentence id" + "-" + dw.senid + "\n");

                            buff.append("Freq count of actual tamil word " + "-" + dw.frequency + "\n");
                            buff.append("tamil word " + "-" + dw.gn_word + "\n");
                            buff.append("tamil word POS" + "-" + dw.vbcheck + "\n");
                            buff.append("tamil word to vbcheck" + dw.tovbcheck + "\n");
                            buff.append("tamil word totamil" + "-" + dw.tamcon + "\n");

                            dw = dw.docnext;
                        }
                        bnode = bnode.linknext;
                    }
                }
            }
            buff.close();
        } catch (Exception e) {
        }

    }

    /**
     * The method display_connectedconcepts1 displays the indices right form the
     * root . When displaying the content of the BinaryNode ,the respective
     * BinaryNextNodes ,DocNodes are also displayed
     */
    public void display_connectedconcepts1(BinaryNode te) {

        try {


            DocNode dr = new DocNode();
            dr = te.docnext;
            if (te != null) {
                count1++;
                BinaryNextNode bnode = new BinaryNextNode();

                System.out.println("\n");
                System.out.println("the concept" + "-" + te.con + "\n");
                System.out.println("the relation" + "-" + te.rel + "\n");
                System.out.println("the toconcept" + "-" + te.tocon + "\n");
                System.out.println("the document id" + "-" + te.docid + "\n");
                System.out.println("the sentence id " + "-" + te.senid + "\n");
                System.out.println("the weight of the index " + "-" + te.weight + "\n");
                System.out.println("the ascii of the index " + "-" + te.asciival + "\n");
                System.out.println("Freq count of actual tamil word " + "-" + te.frequency + "\n");
                System.out.println("tamil word  from concept" + "-" + te.ind_tamil + "\n");
                System.out.println("the tamil toconcept" + te.tamcon + "\n");
                System.out.println("tamil word POS" + "-" + te.vbcheck + "\n");
                System.out.println("tamil topos" + te.tovbcheck + "\n");
                System.out.println("CRC count" + te.crc_count + "\n");
                // buff.append("tamil word topos" + "-" + bnode.tovbcheck + "\n");

                while (dr != null) {
                    // count1++;
                    System.out.println("the document id" + "-" + dr.docid + "\n");
                    System.out.println("the sentence id " + "-" + dr.senid + "\n");

                    dr = dr.docnext;
                }
                bnode = te.linknext;
                if (bnode != null) {
                    DocNode dw = new DocNode();

                    while (bnode != null) {
                        count1++;
                        System.out.println("\n");

                        System.out.println("the concept" + "-" + bnode.con + "\n");
                        System.out.println("the relation" + "-" + bnode.rel + "\n");
                        System.out.println("the toconcept" + "-" + bnode.tocon + "\n");
                        System.out.println("the document id" + "-" + bnode.docid + "\n");
                        System.out.println("the sentence id" + "-" + bnode.senid + "\n");
                        System.out.println("the weight of the index " + "-" + bnode.weight + "\n");
                        System.out.println("Freq count of actual tamil word " + "-" + bnode.frequency + "\n");
                        System.out.println("tamil from concept " + "-" + bnode.ind_tamil + "\n");
                        System.out.println("the tamil toconcept" + bnode.tamcon + "\n");
                        System.out.println("tamil word POS " + "-" + bnode.vbcheck + "\n");
                        System.out.println("tamil word topos" + "-" + bnode.tovbcheck + "\n");

                        dw = bnode.docnext;
                        while (dw != null) {

                            //  count1++;
                            System.out.println("the document id" + "-" + dw.docid + "\n");
                            System.out.println("the sentence id " + "-" + dw.senid + "\n");



                            dw = dw.docnext;
                        }
                        bnode = bnode.linknext;
                    }
                }
            }

        } catch (Exception e) {
        }

    }

    /**
     * displays the count of indices by travesring the tree in order fashion
     */
    public int display_count() {
        System.out.println("the  indices  count is" + count);
        return count;
    }

    /**
     * displays the count of indices by travesring the tree in order fashion
     */
    public int display_count1() {

        printTree();
        System.out.println("why no count info");
        System.out.println("the  indices  count is" + count1);
        return count1;
    }
    public BinaryNode root;
}
