package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.index.unl;

import java.io.*;
import java.util.*;
import java.util.ArrayList;
//import org.apache.nutch.enconversion.unl.ta.*;
//import org.apache.nutch.analysis.unl.ta.*;
import org.apache.nutch.util.NutchConfiguration;
import org.apache.hadoop.conf.Configuration;

public class InstanceOF implements Serializable {

    public BinaryNode root;
    public static ArrayList<String> SuperConcept = new ArrayList<String>();
    public static BinarySearchTree t = new BinarySearchTree();
    public static boolean check_Tree = true;
    public  String unlResourceDir = null;
    /*public  Configuration conf;



        public String setConf(Configuration conf,String property)
        {
              this.conf = conf;
              unlResourceDir = conf.get(property);
              return unlResourceDir;
        }
        public Configuration getConf()
        {
                return this.conf;
        }*/
    /**
     * To load the c from c into the Binary Search Tree
     * @return root node
     */
   
    public ArrayList processSuper(String Constrain) {
        
        ArrayList<String> Temp_super = new ArrayList<String>();        
        try {
            if (check_Tree==true) {
                long Stime=System.currentTimeMillis();
                //System.out.println("Start Time"+Stime);
                //conf = NutchConfiguration.create();
                //BufferedReader br = new BufferedReader(new FileReader(setConf(conf,"UNLCrawl")+"C_IOFTree.txt"));//("./crawl-unl/C_IOFTree.txt"));//
		try{
                BufferedReader br = new BufferedReader(new FileReader("./crawl-unl/C_IOFTree.txt"));
                String str = "";
                while ((str = br.readLine()) != null) {
		    //System.out.println("***"+str);	
                    SuperConcept.add(str);                     		    	
                }
		}catch(Exception e){}
                //System.out.println("Superconcept:"+SuperConcept.size());
                long Etime=System.currentTimeMillis();
                //System.out.println("End Time"+Etime);
                long totTime=Etime-Stime;
                //System.out.println("Total Time in milli sec"+totTime);
                long insec = totTime/1000;
                //System.out.println("Total Time"+insec);
                check_Tree = false;
            }            
            for (String tmp : SuperConcept) {
		//System.out.println("-->"+tmp);
		//System.out.println("Constrain"+Constrain+"/"+tmp.contains(Constrain));

                if (tmp.contains(Constrain) && Temp_super.size() < 10) {
                    String[] sample= tmp.split(";");
                    String temp = sample[1].substring(sample[1].indexOf("(")+1,sample[1].indexOf(")"));
		    //System.out.println("temp"+temp);	
                    String temp1=sample[1].substring(0,sample[1].indexOf("("));
		    //System.out.println("temp"+temp1);	
                    tmp=sample[0]+";"+temp1+";"+temp+";"+sample[2];
                    Temp_super.add(tmp);
                    //System.out.println("? " + tmp);
                }
            }


        } catch (Exception e) {
        }
        return Temp_super;
    }

    public static void main(String args[]) throws Exception {
        try {
            InstanceOF Q = new InstanceOF();
            Q.processSuper("icl>person");           
        } catch (Exception e) {
        }
    }
}


