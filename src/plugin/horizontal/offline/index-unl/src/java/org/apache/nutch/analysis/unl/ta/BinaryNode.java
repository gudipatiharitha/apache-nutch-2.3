package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.index.unl;

import java.io.*;

/**
 * version history**
 *version number:1.1
The package   INDEX_BST  consists of Structures(nodes) required to build the application BianrySearchtree 
 * The class BinaryNode consists of Constructors 
 * The BinaryNode is created to store the concept with the help of its hash code
 * The BinaryNodes are Linked in a Bianry Search Tree fashion
 * The Dissimilar concepts having same hash code are stored in BinaryNextNode using the pointer linknext
 * Description of variables
 * -----------------------
 *  asciival-ascivalue of the concept(hash code )which is used to store the concept in BinarySearchTree
 * con-source concept
 * rel-connecting relation
 * tocon-destination concept
 * ind_tamil-the actual tamil word of the concept
 * linknext-pointer to the BinaryNextNode
 * docid-Document id of the concept
 * left-pointer to BinaryNode which stores the concepts having smaller hash code than the root concept
 * right-pointer to BinaryNode which stores the concepts having greater hash code than the root concept
 * docnext-pointer to the next DocNode :*/
public class BinaryNode implements Serializable {
    // Constructors

    public BinaryNode() {
        asciival = null;
        con = null;
        rel = null;
        tocon = null;
        ind_tamil = null;
        docid = 0;
        senid = null;
        weight = 0;
        frequency = 0;
        vbcheck = null;
        tovbcheck = null;
        tamcon = null;
crc_count=0;
        left = null;
        right = null;
        docnext = null;
        linknext = null;
    }

    public BinaryNode(Comparable theElement, String concept_name, String reln, String Tocon, String Stamil, int i, String sen, int weig, String vb, int freq, String tamcon, String topos,int crccount) {
        asciival = theElement;
        con = concept_name;
        rel = reln;
        tocon = Tocon;
        ind_tamil = Stamil;
        docid = i;
        senid = sen;
        weight = weig;
        frequency = freq;
        vbcheck = vb;
        tovbcheck = topos;
        tamcon = tamcon;
crc_count=crccount;
        left = null;
        right = null;
        docnext = null;
        linknext = null;
    }

    public BinaryNode(Comparable theElement, String concept_name, String reln, String Tocon, String Stamil, String vb, String tamcon, String topos,int crccount) {
        asciival = theElement;
        con = concept_name;
        rel = reln;
        tocon = Tocon;
        ind_tamil = Stamil;
        docid = 0;
        senid = null;
        weight = 0;
        frequency = 0;
        vbcheck = vb;
        tovbcheck = topos;
        tamcon = tamcon;
    crc_count=crccount;
        left = null;
        right = null;
        docnext = null;
        linknext = null;
    }
    public Comparable asciival;//hashcode of the universalword1.
    public String con;//The universal word1
    public String rel;//the UNL relation
    public String tocon;//The universal word2 which is connected to universal word1 throuh the UNL relation rel
    public String ind_tamil;//tamil word of the universal word1.
    public int docid;//The document identifier of the UNL graphin which the universal word1 is present.
    public String senid;//The sentence identifier of the UNL graphin which the universal word1 is present.
    public String tamcon;//tamil word of the universal word2.
    public int weight;//weight of the universal word1 calculated in forward indexing using its sentence position and it's frequency in the UNL graph
    public int frequency;//The frequency of the tamil word which is calculated and passed form enconversion module.
    public String vbcheck;//POS tag of the universal word1.
    public String tovbcheck;//POS tag of universal word2.
    public int crc_count	; //No fo crcs attached to a c
    public DocNode docnext;//pointer to the  DocNode in which the document identifiers are  stored
    public BinaryNextNode linknext;//pointer to the BinaryNextNode in which the document identifiers are  stored
    public BinaryNode left;// pointer to the left child.
    public BinaryNode right;// pointer to the rightchild.
}
