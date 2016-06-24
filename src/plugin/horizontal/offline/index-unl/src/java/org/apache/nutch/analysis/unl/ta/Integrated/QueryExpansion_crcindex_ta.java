package org.apache.nutch.analysis.unl.ta.Integrated;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import org.apache.nutch.analysis.unl.ta.*;

/**
 * Index Query Expansion
 * 
* Version :1.0
 * 
* Date	:9 July 2009
 * 
*
 * Description :Query Expansion UNL is to retrieve the CRC for the user query
 * from the crc_query.txt
 * 
* Module Functionality :First create the binary tree and loads the CRC from the
 * crc_query.text
 * 
* Get the QueryWord from the Query Translation module and lookup the Mwdict
 * (Multiword Dictionary) and UWDict to get the english concepts for the user
 * query word.
 * 
* Retrieve the crc for the English concept Based on the English concept from
 * Binary search tree
 * 
* Store all the retrieved crc in the array list and return back to the Query
 * Translation.
 * 
* ------------------------------------------------------------------------------
 * Version History
 * ------------------------------------------------------------------------------
 * Date :9 July 2009 Description:getBinarySearchTree() - create the Binary tree
 * and load the CRC from the crc_query.txt
 * ------------------------------------------------------------------------------
 * Date :9 July 2009 Description:visittree() is traverse the tree based on the
 * hashcode of English concept if available then return the entire binary
 * node.first get all the CRC values (term
 * word(From),concept(from),VBchech(POS), rel(Relation),term
 * word(To),concept(To),VBchech(POS)) from the node, next moves to binary next
 * node to get the CRC
 * ------------------------------------------------------------------------------
 * Date :9 July 2009 Description:EnconvertUNL() is to lookup the UWdict and
 * MWdict to get the English Concept for the Query word and it returns the hash
 * code for the English Concept.
 * ------------------------------------------------------------------------------
 * Date :9 July 2009 Description:processUNLMW() is to load the Binary tree once
 * and get the CRC for the user query and return the CRC to the Query
 * Translation
 * ------------------------------------------------------------------------------
 * 
*/
public class QueryExpansion_crcindex_ta implements Serializable {

    public BinaryNode root;
    ArrayList<String> GetMW = new ArrayList<String>();
    ArrayList ArlCon = new ArrayList();
    ArrayList arltemp = new ArrayList();
    int EQW_Cnt;
    public static int cnt = 0;
    public static BinarySearchTree t = new BinarySearchTree();
    public static boolean check_Tree = true;
    public static boolean chkflag = true;
    public static BinarySearchTree bst = new BinarySearchTree();
    public static org.apache.nutch.analysis.unl.ta.Rules rs;
    public static boolean chek = true;
    public static MWDict mwdict;
    public static UWDict uwdict;
//    public static org.apache.nutch.analysis.unl.ta.BSTNode bn;
//    public static org.apache.nutch.analysis.unl.ta.BST dict;
//    public static MWBST dict_mw;
//    public static MWBSTNode bn_mw;
    public static boolean chk_load = true;

    /**
     * Constructor
     */
    public QueryExpansion_crcindex_ta() {
        EQW_Cnt = 0;
    }
    private static QueryExpansion_crcindex_ta expansion_ta = null;

    /**
     * Singleton class
     *
     * @return
     */
    public static QueryExpansion_crcindex_ta instanceOf() {
        if (expansion_ta == null) {
            expansion_ta = new QueryExpansion_crcindex_ta();
        }
        return expansion_ta;
    }

    /**
     * To load the CRC from crc_query.txt into the Binary Search Tree
     *
     * @return root node
     */
    public BinarySearchTree getBinarySearchTree() {
        try {
            FileInputStream fis2 = new FileInputStream("./crawl-unl/crc_query.txt");
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            String str = "";
            //binaryTree.root=binaryTree.tree_FileRead1(binaryTree.root, ois2,str);
            t.root = t.tree_FileRead(t.root, ois2, str);
            ois2.close();
            fis2.close();
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
        t = getBinarySearchTree();
        return t;
    }

    /**
     * Multiword Checker
     *
     * @param g_word
     * @return MW if g_word is Multiword else return empty string(" ")
     */
    public String CheckMultiword(String g_word) {
        String MWtag_Qw = "";
        String[] QW_MWCheck = g_word.split(" ");
        int QW_MWCheckCnt = QW_MWCheck.length;
        if (QW_MWCheckCnt >= 2) {
            MWtag_Qw = "MW";
        } else {
            MWtag_Qw = "NMW ";
            ////System.out.println("Not Multiword"+MWtag_Qw);
        }
        return MWtag_Qw;
    }

    /**
     * Get the CRC for the User Query
     *
     * @param t is root node
     * @param QryHC is hash code for the User Query
     * @return ArrayList of Objects for CRC
     * @throws java.lang.Exception
     */
    public ArrayList TraverseTree(BinaryNode bn) throws Exception {
        String MWTag = "";
        ArrayList GetIndx = new ArrayList();
        ////System.out.println();
        BinaryNextNode bt = new BinaryNextNode();
        if (bn != null) {
            CRC crc = new CRC();
            EQW_Cnt++;
            crc.tam1 = bn.ind_tamil;
            crc.c1 = bn.con;
            crc.pos1 = bn.vbcheck;
            crc.QWTag1 = "EQW" + EQW_Cnt;
            crc.MWTag1 = "";
            crc.rel = bn.rel;

            crc.tam2 = bn.tamcon;
            MWTag = CheckMultiword(bn.tamcon);//Multiword check
            crc.c2 = bn.tocon;
            crc.pos2 = bn.tovbcheck;
            crc.QWTag2 = "QW";
            crc.MWTag2 = MWTag;
            GetIndx.add(crc);
            //ArlCon= new ArrayList();
            ////System.out.println("Traverse Binary Node:"+bn.ind_tamil+" "+bn.con+" "+bn.vbcheck+" "+bn.rel+" "+bn.tamcon+" "+bn.tamcon+" "+bn.tocon+"\t\t Hash Code "+bn.asciival+"\n");

            /*
             * bt=bn.linknext; while (bt!=null){ crc = new CRC(); EQW_Cnt++;
             * crc.tam1= bt.ind_tamil; crc.c1=bt.con; crc.pos1=bt.vbcheck;
             * crc.QWTag1= "EQW"+EQW_Cnt; crc.MWTag1=""; crc.rel=bt.rel;
             * crc.tam2=bt.tamcon; MWTag=CheckMultiword(bt.tamcon);//Multiword
             * check crc.c2=bt.tocon; crc.pos2=bt.tovbcheck; crc.QWTag2="QW";
             * crc.MWTag2=MWTag; GetIndx.add(crc);
             * ////System.out.println("Traverse Binary Next
             * Node:"+bt.ind_tamil+" "+bt.con+" "+bt.vbcheck+" "+bt.rel+"
             * "+bt.tamcon+" "+bt.tamcon+" "+bt.tocon+"\t\t Hash
             * Code"+bn.asciival+"\n"); bt=bt.linknext; }
             */
        }
        return GetIndx;
    }

    /**
     * Get the CRC for the User Query
     *
     * @param t is root node
     * @param QryHC is hash code for the User Query
     * @return ArrayList of Objects for CRC
     * @throws java.lang.Exception
     */
    public ArrayList visitTree(BinarySearchTree t, Comparable QryHC) throws Exception {
        String MWTag = "";
        //t.display_count1();
        ArrayList GetIndx = new ArrayList();
        ArlCon.clear();
        BinaryNode bn = t.find(QryHC);
        //System.out.println("*:"+QryHC+"\t*:"+bn);
        ////System.out.println();
        BinaryNextNode bt = new BinaryNextNode();
        if (bn != null) {
            CRC crc = new CRC();
            EQW_Cnt++;
            crc.tam1 = bn.ind_tamil;
            //System.out.println("*"+bn.ind_tamil);
            crc.c1 = bn.con;
            crc.pos1 = bn.vbcheck;
            crc.QWTag1 = "EQW" + EQW_Cnt;
            crc.MWTag1 = "";
            crc.rel = bn.rel;

            crc.tam2 = bn.tamcon;
            //System.out.println("*"+bn.tamcon);
            MWTag = CheckMultiword(bn.tamcon);//Multiword check
            crc.c2 = bn.tocon;
            crc.pos2 = bn.tovbcheck;
            crc.QWTag2 = "QW";
            crc.MWTag2 = MWTag;
            GetIndx.add(crc);
            //ArlCon= new ArrayList();
            //System.out.println("Traverse Binary Node:"+bn.ind_tamil+" "+bn.con+" "+bn.vbcheck+" "+bn.rel+" "+bn.tamcon+" "+bn.tamcon+" "+bn.tocon+"\t\t Hash Code "+bn.asciival+"\n");

            bt = bn.linknext;
            while (bt != null) {
                crc = new CRC();
                EQW_Cnt++;
                crc.tam1 = bt.ind_tamil;
                crc.c1 = bt.con;
                crc.pos1 = bt.vbcheck;
                crc.QWTag1 = "EQW" + EQW_Cnt;
                crc.MWTag1 = "";
                crc.rel = bt.rel;
                crc.tam2 = bt.tamcon;
                MWTag = CheckMultiword(bt.tamcon);//Multiword check
                crc.c2 = bt.tocon;
                crc.pos2 = bt.tovbcheck;
                crc.QWTag2 = "QW";
                crc.MWTag2 = MWTag;
                GetIndx.add(crc);
                //System.out.println("Traverse Binary Next Node:"+bt.ind_tamil+" "+bt.con+" "+bt.vbcheck+" "+bt.rel+" "+bt.tamcon+" "+bt.tamcon+" "+bt.tocon+"\t\t Hash Code"+bn.asciival+"\n");
                bt = bt.linknext;
            }
        }
        return GetIndx;
    }

    /**
     * To load and Lookup the UWDict and MWDict and get the hash code for the
     * English UWConcept
     *
     * @param str
     * @return
     * @throws java.lang.Exception
     */
    //   public String EnconvertUNL(String str)throws Exception
//    public int EnconvertUNL(String str) throws Exception {
//        //System.out.println("Concept"+str);
//        int IndxHc = 0;
//        int hc = 0;
//        if (chk_load) {
//            mwdict = new MWDict();
//            uwdict = new UWDict();
//            dict_mw = new MWBST();
//            bn_mw = new MWBSTNode();
//            bn = new org.apache.nutch.analysis.unl.ta.BSTNode();
//            dict = new org.apache.nutch.analysis.unl.ta.BST();
//            chk_load = false;
//        }
//
//        try {
//            dict_mw = mwdict.get_mwbst();
//            dict = uwdict.get_bst();
//            String temp[] = str.split(" ");
//            String Final = new String();
//            if (temp.length > 1) {
//                //System.out.println("Multiword word check");
//                hc = temp[0].hashCode();
//                bn_mw = dict_mw.search(hc);
//                if (bn_mw != null) {
//                    MWBSTNode bn_mw1 = new MWBSTNode();
//                    while (bn_mw != null) {
//                        bn_mw1 = bn_mw;
//                        if (bn_mw.lexeme.equals(str)) {
//                            break;
//                        }
//                        bn_mw = bn_mw.getNext();
//                    }
//                    Final = bn_mw1.headword + "(" + bn_mw1.constraint.trim() + ")";
//                    //System.out.println("Multiword word check Word"+Final);
//                    IndxHc = Final.trim().hashCode();
//                    //System.out.println("Multiword word check Hashcode"+IndxHc);
//                }
//            } else {
//                ////System.out.println("Single word check:"+str);
//                hc = str.hashCode();
//                //System.out.println("Single word check hc:"+hc);
//                bn = dict.search(hc);
//                if (bn != null) {
//                    Final = bn.headword + "(" + bn.constraint.trim() + ")";
//                    //System.out.println("Single word Final"+Final);
//                    IndxHc = Final.trim().hashCode();
//                    // //System.out.println("Dict Word"+bn.lexeme +"\t"+ bn.headword+"\n");
//                }
//            }
//            //System.out.println("Enconvert method Final"+Final);
//            //System.out.println("Enconvert method Hash Value"+IndxHc);
//            ////System.out.println("Enconvert method Hash Value"+hc);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //return hc;
//        return IndxHc;
//    }
//
//    //   public String EnconvertUNL(String str)throws Exception
//    public String EnconvertUNL1(String str) throws Exception {
//
//        if (chk_load) {
//            mwdict = new MWDict();
//            uwdict = new UWDict();
//            dict_mw = new MWBST();
//            bn_mw = new MWBSTNode();
//            bn = new org.apache.nutch.analysis.unl.ta.BSTNode();
//            dict = new org.apache.nutch.analysis.unl.ta.BST();
//            chk_load = false;
//        }
//
//        dict_mw = mwdict.get_mwbst();
//        dict = uwdict.get_bst();
//
//
//        int IndxHc = 0;
//        int hc;
//        String temp[] = str.split(" ");
//        String Final = new String();
//        if (temp.length > 1) {
//            hc = temp[0].hashCode();
//            bn_mw = dict_mw.search(hc);
//            if (bn_mw != null) {
//                MWBSTNode bn_mw1 = new MWBSTNode();
//                while (bn_mw != null) {
//                    bn_mw1 = bn_mw;
//                    if (bn_mw.lexeme.equals(str)) {
//                        break;
//                    }
//                    bn_mw = bn_mw.getNext();
//                }
//                Final = bn_mw1.headword + "(" + bn_mw1.constraint.trim() + ")";
//                IndxHc = Final.trim().hashCode();
//            }
//        } else {
//            hc = str.hashCode();
//            bn = dict.search(hc);
//            if (bn != null) {
//                Final = bn.headword + "(" + bn.constraint.trim() + ")";
//                ////System.out.println("Final"+Final);
//                IndxHc = Final.trim().hashCode();
//                ////System.out.println("Dict Word"+bn.lexeme +"\t"+ bn.headword+"\n");
//            }
//        }
//        ////System.out.println("Hash Value"+IndxHc);
//        //System.out.println("Final"+Final);
//        return Final;
//    }
    /**
     * It Load the BST once
     *
     * @param queryHC --hash code for the user query
     * @return ArrayList(getExpQry) of objects
     */
    public ArrayList processUNLMW(Comparable queryHC) {
        ArrayList temp = new ArrayList();
        try {
            if (check_Tree) {
                t = printquerytree();
                check_Tree = false;
            }
            ArrayList getExpQry = visitTree(t, queryHC);
            if (getExpQry != null) {
                temp = getExpQry;
                //System.out.println("Temp "+temp);	
            } else {
                temp = null;
            }
        } catch (Exception e) {
        }
        return temp;
    }

    public static void main(String args[]) {
//        QueryExpansion_crcindex_ta Q = new QueryExpansion_crcindex_ta();
//        int i = 0;
//        try {
//            i = Q.EnconvertUNL("உருட்டு");
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        ////System.out.println("Hash Value"+ i);
//        ArrayList arl = Q.processUNLMW(new Integer(i));
//        System.out.println("ArrayList" + arl);
//
//        System.out.println("Hashcode of waterfalls(icl>naturalworld):" + ("india(iof>country)").hashCode());
    }
}
