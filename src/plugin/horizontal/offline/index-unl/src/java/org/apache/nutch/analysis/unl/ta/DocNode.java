package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.index.unl;

/**
 * version history**
 *version number:1.1
@author subalalitha
 */
import java.io.*;

/**The package   INDEX_BST  consists of Structures(nodes) required to build the application BianrySearchtree 
 * The DocNode is created to store the document ids of the of the similar concepts having same hash code
 * The DocNodes are linked in a linked list fashion from the the main BianryNode
 * Each BinaryNode and BinaryNext will have DocNodes
 * The DocNode are used in BinaryNextNode in the same way as of BinaryNode
 * Description of variables
 * -----------------------
 * docid-Document id of the concept
 * docnext-pointer to the next DocNode :
 */
public class DocNode implements Serializable {

    public DocNode() {
        docid = 0;
        weight = 0;
        senid = null;
        gn_word = null;
        vbcheck = null;
        tovbcheck = null;
        frequency = 0;
        tamcon = null;
        docnext = null;

    }
    public int docid;
    public String senid;
    public int weight;
    public DocNode docnext;
    public String gn_word;
    public String vbcheck;
    public String tovbcheck;
    public int frequency;
    public String tamcon;
}
