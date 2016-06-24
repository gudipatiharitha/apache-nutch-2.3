package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.enconversion.unl.ta;

import org.apache.nutch.util.NutchConfiguration;
import org.apache.hadoop.conf.Configuration;

import java.io.*;
import java.lang.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.nutch.analysis.unl.ta.Integrated.Jumbo;

public class UWDict {

    public Hashtable uwdict;
    public LinkedList entry;
//    public BST bst;
//    public BSTNode bstnode;

    public UWDict() {
        uwdict = new Hashtable();
        entry = new LinkedList();
//        bst = new BST();
//        bstnode = new BSTNode();
        loadDic();
    }

    public void loadDic() {
        Configuration conf = NutchConfiguration.create();
        String path = conf.get("unl_resource_dir");

        String conentry;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(Jumbo.getCLIAHome() + "/resource/unl/uwdict.txt"), "UTF8"));

            while ((conentry = in.readLine()) != null) {
                //System.out.println(conentry);
                LinkedList new_entry = new LinkedList();
                StringTokenizer tok = new StringTokenizer(conentry.trim(), "/");
                String lex = tok.nextToken().trim();
                String hc = new String(Base64.encodeBase64(("" + lex).getBytes())); // Modified
                String hw = tok.nextToken().trim();
                String cl = tok.nextToken().trim();
                String t_uid = tok.nextToken().trim();
                String c_uid = tok.nextToken().trim();
                //bst.insert(hc, lex, hw, cl, t_uid, c_uid);
                new_entry.add(0, lex);
                new_entry.add(1, hw);
                new_entry.add(2, cl);
                new_entry.add(3, t_uid);
                new_entry.add(4, c_uid);
                if (uwdict.isEmpty()) {
                    entry.add(new_entry);
                    uwdict.put(hc, entry);
                } else if (uwdict.containsKey(hc)) {
                    LinkedList temp_ll = (LinkedList) uwdict.get(hc);
                    temp_ll.addLast(new_entry);
                    uwdict.remove(hc);
                    uwdict.put(hc, temp_ll);
                } else {
                    entry = new LinkedList();
                    entry.add(new_entry);
                    uwdict.put(hc, entry);
                }

            }
            //System.out.println("Size of UWDict"+bst.Conceptsize());
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("Exception in loadDic"+e);
        }

    }

//    public BST get_bst() {
//        return bst;
//    }
//
//    public void traverse() {
//        bst.inorder();
//        //System.out.println(mwbst.Conceptsize());
//    }
    public static void main(String args[]) {
        UWDict b = new UWDict();
    }
}
