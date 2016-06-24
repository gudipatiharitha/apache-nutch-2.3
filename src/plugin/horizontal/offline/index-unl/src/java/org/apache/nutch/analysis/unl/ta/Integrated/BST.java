package org.apache.nutch.analysis.unl.ta.Integrated;

import java.io.PrintStream;

/**
 *
 * @author root
 *
 */
class BST {

    public BSTNode root = null;
    PrintStream concept;
    int ConceptCount;
    public String tamil_word = null;
    boolean uwflag;
    String tamilword;
    String multiword;

    public BST() {
        try {
            ConceptCount = 0;
        } catch (Exception e) {
        }
    }

    /**
     * The Binary Search Tree is arranged in the Inorder fashion.
     */
    public String inorder1(String word) {
        boolean flag = false;
        String tamil = inorder_check(root, word);
        return tamil;
    }

    public void clear() {
        root = null;
    }

    /**
     * @return boolean value is true if the root of the tree is empty otherwise
     * false.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * The Binary Search Tree is arranged in the Inorder fashion.
     */
    public void inorder() {
        inorder(root);
    }

    /**
     * Visiting the BST nodes
     *
     * @param p element in the node of the BST Tree.
     */
    public static String visit1(BSTNode p, String word) {
        String tamilword = null;
        while (p != null) {
            if (p.headword.equals(word)) {
                tamilword = p.lexeme;
                break;
            }
            p = p.getNext();
        }
        return tamilword;
    }

    public static String visit_andcheckbst(BSTNode p) {
        String lex = "";
        if (p.getNext() != null) {

            lex = p.lexeme;

            visit_andcheckbst(p.getNext());
        }
        return lex;
    }

    /**
     * Inorder Operation is performed.
     */
    public void inorder(BSTNode p) {
        if (p != null) {
            inorder(p.left);
            // visit(p);
            // System.out.println("tamil word"+p.lexeme);
            inorder(p.right);
        }
    }

    public String inorder_check(BSTNode p, String word) {
        boolean flag = false;
        String tamilword = null;
        if (p != null && uwflag == false) {

            if (uwflag == false) {
                inorder_check(p.left, word);
            }

            if (p != null && uwflag == false) {

                if (uwflag == false) {
                    tamilword = visit1(p, word);
                }

                System.out.println("tamil word" + tamilword);
                if (tamilword != null) {
                    flag = true;
                    root = null;
                    p = null;
                    //System.out.println("tamil word" + tamilword);
                    //System.out.println("flag" + flag);
                    //System.out.println("root p" + p);
                    uwflag = true;
                    return tamilword;
                }
                //System.out.println("Visit Ends");
            }

            //System.out.println("p:" + p);

            if (uwflag == false) {
                //System.out.println("p in right" + p);
                inorder_check(p.right, word);
            }
        }

        return tamilword;
    }

    public String find_tamil_word(BSTNode p, String UNL) {
        String tamilword = null;
        tamilword = p.lexeme;
        return tamilword;
    }

    public String retrive_tamilword(String UNLword) {
        tamil_word = null;
        //System.out.println(">>>|"+UNLword);
        String tamil = retrive_tamilword(root, UNLword);
        return tamil;
    }

    public String retrive_tamilword(BSTNode p, String UNL) {
        int flag = 0;
        if (p != null) {
            retrive_tamilword(p.left, UNL);
            if ((p.headword.equals(UNL))) {
                // //System.out.println("eeeeeeeeeeeeeeqqqqqqqqqqqqqqqqq");
                // //System.out.println("unnnnnnnnlllll"+p.headword);
                // //System.out.println("the passsssss"+UNL);
                // //System.out.println("the taaaaaaaam"+p.lexeme);
                // tamil_word= find_tamil_word(p,UNL);
                tamil_word = p.lexeme;
                flag = 1;
                uwflag = true;
                // break;
            }
            // //System.out.println("the unl"+UNL);
            // //System.out.println("the lex"+p.lexeme);
            // //System.out.println("the head"+p.headword);
            // //System.out.println("the constraint"+p.constraint);
            if (flag != 1) {
                retrive_tamilword(p.right, UNL);
            }
        }
        //System.out.println("Output:"+tamil_word);
        return tamil_word;

    }

    public boolean get_uwflag() {
        return uwflag;
    }

    public String retrive_tamilmultiword(String UNLword) {
        String tamil = retrive_tamilmultiword(root, UNLword);
        return tamil;
    }

    public String retrive_tamilmultiword(BSTNode p, String UNL) {
        if (p != null) {
            int flag = 0;
            retrive_tamilmultiword(p.left, UNL);
            // //System.out.println(p.headword+ "  "+ UNL);
            if ((p.headword).equals(UNL)) {
                // //System.out.println("went inside" );
                multiword = p.lexeme;
                // //System.out.println("MultiWord "+ multiword);
                flag = 1;
            }
            if (flag != 1) {
                retrive_tamilmultiword(p.right, UNL);
            }
        }
        //System.out.println("Output:"+multiword);
        return multiword;
    }

    /**
     * To search for a string.
     *
     * @param el searching for an element.
     * @return root node in the tree.
     */
    public BSTNode search(int el) {
        return search(root, el);
    }

    public BSTNode search(BSTNode p, int el) {
        // //System.out.println("Hashcode "+p.el);

        if (p == null) {
            return null;
        } else if (el < (p.el)) {
            return search(p.left, el);
        } else if (el > (p.el)) {
            return search(p.right, el);
        } else {
            return p;
        }

    }

    public BSTNode search_in_next(int el) {
        BSTNode bn1 = new BSTNode();
        BSTNode bn2 = new BSTNode();
        bn1 = search(el);
        if ((bn1 != null) && bn1.el != el) {
            bn2 = bn1.next;
            while (bn2 != null) {
                if (bn2.next.el == el) {
                    break;
                }
                bn2 = bn2.getNext();
            }
            return bn2;
        } else {
            return bn1;
        }
    }

    /**
     * to insert new string. to find a place for inserting new node.
     *
     * @param el element to be searched, lex lexeme. hw headword. CL Constraint
     * list.
     */
    public void insert(int el, String lex, String hw, String CL) {
        BSTNode p = root, prev = null;
        while (p != null) {
            // find a place for inserting new node;
            prev = p;
            if (el < (p.el)) {
                p = p.left;
            } else if (el > (p.el)) {
                p = p.right;

            } else {
                break;
            }

        }
        if (root == null) // tree is empty;
        {
            root = new BSTNode(el, lex, hw, CL);
        } else if (el < (prev.el)) {
            prev.left = new BSTNode(el, lex, hw, CL);
        } else if (el > (prev.el)) {
            prev.right = new BSTNode(el, lex, hw, CL);
        } else {
            prev.next = new BSTNode(el, lex, hw, CL);
        }
        ConceptCount++;
    }

    /**
     * to know the size of the concepts.
     *
     * @return returns the no. of concepts in the given sentence.
     */
    public int Conceptsize() {
        return ConceptCount;
    }

    public BSTNode get_root() {
        return root;
    }
}
