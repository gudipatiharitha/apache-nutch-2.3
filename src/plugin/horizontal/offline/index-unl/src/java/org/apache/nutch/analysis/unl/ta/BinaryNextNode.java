package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.index.unl;

import java.io.*;

/**
 * version history**
 *version number:1.1
@author subalalitha
 *The package   INDEX_BST  consists of Structures(nodes) required to build the application BianrySearchtree
 * The class BinaryNextNode consists of Constructors 
 * BinaryNextNode is created 
to avoid collision of concepts having same hashcode
 * * The  dissimilar concepts having same hash code are stored in BianryNextNode in a linked list fashion from the main BinaryNode
 *  Each BinaryNode will have BinaryNextNodes  
 * Description of variables
 * -----------------------
 * con-source concept
 * rel-connecting relation
 * tocon-destination concept
 * ind_tamil-the actual tamil word of the concept
 * linknext-pointer to the BinaryNextNode
 * docid-Document id of the concept
 * docnext-pointer to the next DocNode :
 *  
 */
public class BinaryNextNode implements Serializable {

    public BinaryNextNode() {
        con = null;
        rel = null;
        tocon = null;
        ind_tamil = null;
        linknext = null;
        docid = 0;
        senid = null;
        weight = 0;
        frequency = 0;
        tamcon = null;
        vbcheck = null;
        tovbcheck = null;
        docnext = null;
    }

    public BinaryNextNode(Comparable theElement, String concept_name, String reln, String Tocon, String Stamil, int i, String sen, int weigh, String vb, int freq, String tam, String topos) {

        con = concept_name;
        rel = reln;
        tocon = Tocon;
        ind_tamil = Stamil;
        docid = i;
        senid = sen;
        weight = weigh;
        frequency = freq;
        vbcheck = vb;
        tovbcheck = topos;
        tamcon = tam;
        docnext = null;
        linknext = null;
    }

    public BinaryNextNode(Comparable theElement, String concept_name, String reln, String Tocon, String Stamil, String vb, String tam, String topos) {

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
        tamcon = tam;
        docnext = null;
        linknext = null;
    }
    public String con;//The universal word1
    public String rel;//the UNL relation
    public String tocon;//The universal word2 which is connected to universal word1 throuh the UNL relation rel
    public BinaryNextNode linknext;//pointer to the next Avl_linknextNode.
    public String ind_tamil;//tamil word of the universal word1.
    public int docid;//The document identifier of the UNL graphin which the universal word1 is present.
    public String senid;//The sentence identifier of the UNL graphin which the universal word1 is present.
    public String tamcon;//tamil word of the universal word2.
    public int weight;//weight of the universal word1 calculated in forward indexing using its sentence position and it's frequency in the UNL graph
    public int frequency;//The frequency of the tamil word which is calculated and passed form enconversion module.
    public String vbcheck;//POS tag of the universal word1.
    public String tovbcheck;//POS tag of universal word2.
    public DocNode docnext;//pointer to the next DocNode in which the document identifiers are  stored
}
