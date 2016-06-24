package org.apache.nutch.analysis.unl.ta.Integrated;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Translate_en_to_ta {

    public static BST bst;
    public BSTNode bstnode;
    private static Translate_en_to_ta objQueryTranslateDict = null;
    public static boolean loadIndexFlag = false;

    public Translate_en_to_ta() {
        bst = new BST();
        bstnode = new BSTNode();
    }

    public BST get_bst() {
        if (loadIndexFlag == false) {
            loadDic();
            loadIndexFlag = true;
        }
        return bst;
    }

    public static BST get_bst1(BST bst1) {
        return bst1;
    }

    public void loadDic() {
        //System.out.println("Load UWLIST for Tamil Traslation Index");
        // Configuration conf = NutchConfiguration.create();
        // String path=conf.get("unl_resource_dir");
        String path = org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome()+"resource/unl";
        String conentry;
        try {// //System.out.println("entered befor");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(path + "/uwdict.txt"), "UTF8"));

            while ((conentry = in.readLine()) != null) {

                StringTokenizer tok = new StringTokenizer(conentry.trim(), "/");
                String lex = tok.nextToken().trim();
                ////System.out.println("lex:"+lex);
                int hc = lex.hashCode();
                String hw = tok.nextToken().trim();
                // //System.out.println("head:"+hw);
                String cl = tok.nextToken().trim();
                bst.insert(hc, lex, hw, cl);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("Exception in loadDic" + e);
        }

    }

    public static Translate_en_to_ta getInstance() {
        if (objQueryTranslateDict == null) {
            objQueryTranslateDict = new Translate_en_to_ta();
        }
        return objQueryTranslateDict;
    }

    public static void main(String args[]) {
        Translate_en_to_ta b = new Translate_en_to_ta();
        BST bs1 = new BST();
        bs1 = b.get_bst();
        // b.traverse(bs);		
        String str = "go to madurai";
        //tamilword=b.traverse(bs,str.trim());
        String[] temp = str.split(" ");
        for (String tem : temp) {
            bs1 = b.get_bst();
            //System.out.println("Result:"+ tem +"\t" + bs1.retrive_tamilword(tem.trim()));		
        }

        //System.out.println("------");
		/*
         * str = "go to madurai"; //tamilword=b.traverse(bs,str.trim()); temp =
         * str.split(" "); for(String tem : temp){
         * //System.out.println("Result:" + bs1.retrive_tamilword(tem.trim()));
         * }
         */

        //str = "apple tsunami";
        // tamilword=b.traverse(bs,str.trim());
        ////System.out.println("Result:" + bs.retrive_tamilword(str.trim()));
    }
}
