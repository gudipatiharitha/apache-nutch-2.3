package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.enconversion.unl.ta;

import org.apache.nutch.util.NutchConfiguration;
import org.apache.hadoop.conf.Configuration;

import java.io.*;
import java.lang.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.nutch.analysis.unl.ta.Integrated.Jumbo;

public class MWDict {

    Hashtable mwdict;
    LinkedList mw_ll;

    public static void main(String args[]) {
        MWDict mw = new MWDict();
    }

    public MWDict() {
        mwdict = new Hashtable();
        mw_ll = new LinkedList();
        loadDic();
        //traverse();
    }

    public void loadDic() {
        Configuration conf = NutchConfiguration.create();
        String path = conf.get("unl_resource_dir");
        String conentry = "";
//	//System.out.println("Inside Load Multi Words Dictionary");
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(Jumbo.getCLIAHome() + "/resource/unl/multiwords.txt"), "UTF8"));
            while ((conentry = in.readLine()) != null) {
                //    System.out.println("Inside Concept While"+ conentry);
                LinkedList new_ll = new LinkedList();
                StringTokenizer tok = new StringTokenizer(conentry, "/");
                String lex = tok.nextToken();
                String hw = tok.nextToken();
                String cl = tok.nextToken();
                int nw = Integer.parseInt(tok.nextToken().trim());
                String fw = tok.nextToken();
                String hc = new String(Base64.encodeBase64(("" + fw).getBytes())); // Modified
                String t_uid = tok.nextToken().toString().trim();
                String c_uid = tok.nextToken().toString().trim();
                // mwbst.insert(hc, lex, hw, cl, nw, fw, t_uid, c_uid);
                new_ll.add(0, lex);
                new_ll.add(1, hw);
                new_ll.add(2, cl);
                new_ll.add(3, nw);
                new_ll.add(4, fw);
                new_ll.add(5, t_uid);
                new_ll.add(6, c_uid);

                if (mwdict.isEmpty()) {
                    mw_ll = new LinkedList();
                    mw_ll.add(new_ll);
                    mwdict.put(hc, mw_ll);
                } else if (mwdict.containsKey(hc)) {
                    LinkedList temp_ll = (LinkedList) mwdict.get(hc);
                    temp_ll.addLast(new_ll);
                    mwdict.remove(hc);
                    mwdict.put(hc, temp_ll);
                } else {
                    mw_ll = new LinkedList();
                    mw_ll.add(new_ll);
                    mwdict.put(hc, mw_ll);
                }
            }
            in.close();
            /*
             * for(Object key: mwdict.keySet()){ LinkedList ll_mw =
             * (LinkedList)mwdict.get(key);
             * System.out.println(key+"----->"+ll_mw); }
             */
        } catch (Exception e) {
            System.out.println(conentry);
            e.printStackTrace();

        }

    }
}
