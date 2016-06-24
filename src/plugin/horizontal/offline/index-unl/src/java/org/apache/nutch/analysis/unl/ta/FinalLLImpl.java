package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.enconversion.unl.ta;

import java.io.*;
import java.lang.*;
import java.util.*;

public class FinalLLImpl implements Serializable {
    // reference to the head node.

    public HeadNode head;
    public ConceptNode concept;
    public ConceptToNode destination;
    public RelationNode relation;
    public int ConceptCount;
    public int RelationCount;
    //
    public String DocID = "";
    public String URL = "";
    public ArrayList<String> SEList = new ArrayList<String>();
    //
//	PrintStream ugt;
    // LinkedList constructor

    public FinalLLImpl() throws Exception {
        // this is an empty list, so the reference to the head node
        // is set to a new node with no data
        head = new HeadNode();
        ConceptCount = 0;
        RelationCount = 0;

    }

    public void addConcept(String w, String uw, String cid, String did, String sid, String posck, String freqcount, String con_freq, String con_weight, String tamil_uid, String con_uid, String queryTag, String MWtag_Qw) // post: appends the specified element to the end of this list.
    {
        //System.out.println("Add Concept:"+w+" "+uw+" "+cid+" "+did+" "+sid+" "+posck+" "+freqcount+" "+queryTag+" "+MWtag_Qw);
        ConceptNode temp = new ConceptNode(w, uw, cid, did, sid, posck, freqcount, con_freq, con_weight, tamil_uid, con_uid, queryTag, MWtag_Qw);
        if (head.colnext == null) {
            head.setColNext(temp);
        } else {
            ConceptNode current = head.colnext;
            // starting at the head node, crawl to the end of the list
            while (current.getColNext() != null) {
                current = current.getColNext();
            }
            current.setColNext(temp);
        }
        ConceptCount++;// increment the number of elements variable
    }

    public ConceptNode getConcept(int index) // post: returns the element at the specified position in this list.
    {
        // index must be 1 or higher
        if (index <= 0) {
            return null;
        }

        ConceptNode current = head.getColNext();
        for (int i = 1; i < index; i++) {
            if (current.getColNext() == null) {
                return null;
            }

            current = current.getColNext();
        }
        return current;
    }

    public int Conceptsize() {
        return ConceptCount;
    }

    public int Relationsize() {
        return RelationCount;
    }

    public ConceptToNode addCT_Concept(String cid, String cto, String rl, String did, String sid) {

        ConceptToNode temp = new ConceptToNode(cid, cto, rl, did, sid);

        ConceptNode current = head.colnext;
        ConceptToNode current1;

        while (current != null) {

            if ((current.conceptid.equals(cid)) && (current.docid.equals(did)) && (current.sentid.equals(sid))) {
                //////ugt.println("Given concept matches");

                if (current.getRowNext() == null) {
                    current.setRowNext(temp);
                } else {

                    current1 = current.getRowNext();
                    while (current1.getRowNext() != null) {
                        //	////ugt.println("IW");
                        current1 = current1.getRowNext();
                    }

                    current1.setRowNext(temp);
                }
                break;
            }
            current = current.getColNext();
        }

        return (temp);
    }

    public void getCT_Concept() // post: returns the element at the specified position in this list.
    {
        // index must be 1 or higher
        try {
            //	ugt  = new PrintStream(new FileOutputStream(new File("./graphtrav.txt"),true));	
            ConceptNode current = head.colnext;
            ConceptToNode current1;

            while (current != null) {
                //////ugt.println("Inside Outer While");

                //ugt.println();
                //ugt.print(current.gn_word+'\t');
                //ugt.print(current.uwconcept+'\t');
                //ugt.print(current.conceptid+'\t');
                //ugt.print(current.docid+'\t');
                //ugt.print(current.sentid+'\t');
                //ugt.print(current.freq_count+'\t');
                //ugt.print(current.poscheck+'\t');
                //ugt.print(current.queryTag+'\t');
                //ugt.print(current.MWtag_Qw+'\t');

                if (current.getRowNext() != null) {
                    current1 = current.getRowNext();
                    //ugt.print(current1.uwfrmconcept+'\t');
                    //ugt.print(current1.uwtoconcept+'\t');
                    //ugt.print(current1.relnlabel+'\t');
                    //ugt.print(current1.docid+'\t');
                    //ugt.println(current1.sentid+'\t');


                    while (current1.getRowNext() != null) {
                        //ugt.print("------>");
                        current1 = current1.getRowNext();
                        //ugt.print(current1.uwfrmconcept+'\t');
                        //ugt.print(current1.uwtoconcept+'\t');
                        //ugt.print(current1.relnlabel+'\t');
                        //ugt.print(current.docid+'\t');
                        //ugt.println(current.sentid+'\t');										
                    }
                }
                current = current.getColNext();

            }
            ////ugt.close();
        } catch (Exception e) {
        }


    }

    public void addRelation(String rl) {
        if (checkRelationExist(rl) == 0) {
            RelationNode temp = new RelationNode(rl);

            if (head.rownext == null) {
                head.setRowNext(temp);
            } else {
                RelationNode current = head.rownext;

                while (current.getRowNext() != null) {
                    current = current.getRowNext();
                }

                current.setRowNext(temp);
            }
            RelationCount++;// increment the number of elements variable
        }
    }

    public int checkRelationExist(String relnLab) {
        RelationNode current = head.rownext;
        int flag = 0;
        while (current != null) {
            if (current.relnlabel.equals(relnLab)) {
                flag++;
            }
            current = current.getRowNext();
        }
        if (flag == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public RelationNode getRelation(int index) // post: returns the element at the specified position in this list.
    {

        ////ugt.println("inside get"+index);
        // index must be 1 or higher
        if (index <= 0) {
            return null;
        }

        RelationNode current = head.getRowNext();
        for (int i = 1; i < index; i++) {

            if (current.getRowNext() == null) {

                return null;
            }

            current = current.getRowNext();
        }
        return current;
    }

    public void addCT_Relation(ConceptToNode cn) {

        RelationNode current = head.rownext;
        ConceptToNode current1;

        while (current != null) {
            //////ugt.println("In W");



            if (current.relnlabel.equals(cn.relnlabel)) {
                //////ugt.println("Given Relation matches");

                if (current.getColNext() == null) {
                    current.setColNext(cn);
                } else {

                    current1 = current.getColNext();
                    while (current1.getColNext() != null) {
                        //////ugt.println("IW");
                        current1 = current1.getColNext();
                    }

                    current1.setColNext(cn);
                }
                break;
            }
            current = current.getRowNext();
        }


    }

    public void getCT_Relation() {

        RelationNode current = head.rownext;
        ConceptToNode current1;

        while (current != null) {
            //////ugt.println("Inside Outer While");


            //ugt.println(current.relnlabel+'\t');

            if (current.getColNext() != null) {
                current1 = current.getColNext();
                //ugt.print(current1.uwfrmconcept+'\t');
                //ugt.print(current1.uwtoconcept+'\t');
                //ugt.print(current1.relnlabel+'\t');
                //ugt.print(current1.docid+'\t');
                //ugt.println(current1.sentid+'\t');	


                while (current1.getColNext() != null) {
                    //ugt.print("------>");
                    current1 = current1.getColNext();
                    //ugt.print(current1.uwfrmconcept+'\t');
                    //ugt.print(current1.uwtoconcept+'\t');
                    //ugt.print(current1.relnlabel+'\t');
                    //ugt.print(current1.docid+'\t');
                    //ugt.println(current1.sentid+'\t');	


                }
            } else {
                //////ugt.println("HAI");
            }
            current = current.getRowNext();
        }



    }

    public String getconcept_vs_conceptid(String cs, String se) {

        ConceptNode temp1;
        temp1 = concept;
        temp1 = head.colnext;
        if (temp1 != null && temp1.uwconcept != null) {
            while (temp1 != null) {
                if ((cs.equals(temp1.conceptid)) && (se.equals(temp1.sentid))) {

                    break;

                }
                temp1 = temp1.getColNext();

            }//while


            return temp1.uwconcept;

        } else {
            return "No Concept";
        }
    }

    public String getconcept_vs_conceptid_query(String cs) {
        ConceptNode temp1;
        temp1 = concept;
        temp1 = head.colnext;
        if (temp1 != null) {
            while (temp1 != null) {
                if (cs.equals(temp1.conceptid)) {

                    break;
                }
                temp1 = temp1.colnext;
            }

            return temp1.uwconcept + "&" + temp1.con_uid + "&" + temp1.tam_uid;
        }
        return "NoQueryWord";



    }

    public String gettamilword(String cs) {
        ConceptNode temp1;
        temp1 = concept;
        temp1 = head.colnext;
        while (temp1 != null) {
            if (cs.equals(temp1.uwconcept)) {

                break;
            }
            temp1 = temp1.colnext;
        }
        return temp1.gn_word;

    }

    public String getconcept_vs_ToConcept(String cs, String se) {
        String to_set = "";

        ConceptNode temp1;
        temp1 = concept;
        temp1 = head.colnext;
        try {
            while (temp1 != null) {
                //	 System.out.println("STR-------->"+cs+":"+se);
                //	 if(!temp1.conceptid.equals("None")){
                if ((cs.equals(temp1.conceptid)) && (se.equals(temp1.sentid))) {
                    //	 System.out.println("STR-------->"+cs+":"+se);
                    break;

                }
                //	 }
                temp1 = temp1.getColNext();
            }
            if (temp1 != null) {
                to_set = temp1.gn_word + "$" + temp1.poscheck + "$" + temp1.uwconcept + "$" + temp1.con_freq + "$" + temp1.con_weight + "$" + temp1.con_uid;
            }
        } catch (Exception e) {
            //System.err.println(temp1.gn_word+"$"+temp1.poscheck+"$"+temp1.uwconcept+"$"+temp1.con_freq+"$"+temp1.con_weight+"$"+temp1.con_uid);
            e.printStackTrace();
        }
        return to_set;

    }

    public String gettagvalue(String cs) {
        ConceptNode temp1;
        temp1 = concept;
        temp1 = head.colnext;
        while (temp1 != null) {
            if (cs.equals(temp1.uwconcept)) {

                break;
            }
            temp1 = temp1.colnext;
        }
        return temp1.queryTag;

    }

    public String getentityofuw(String cs) {
        ConceptNode temp1;
        temp1 = concept;
        temp1 = head.colnext;
        while (temp1 != null) {
            if (cs.equals(temp1.uwconcept)) {

                break;
            }
            temp1 = temp1.colnext;
        }
        return temp1.poscheck;

    }
}
