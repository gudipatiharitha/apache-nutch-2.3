package org.apache.nutch.analysis.unl.ta.Pipeline;

import java.io.*;
import java.util.*;
import java.util.Map.*;
import org.apache.nutch.util.NutchConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.analysis.unl.ta.Integrated.Jumbo;

public class InteT implements EnTI, InTI, SeTI, SuTI, SuTEI, SnPI {
//

    public static Configuration conf = NutchConfiguration.create();
    public static String input_path = conf.get("SentenceExtraction");
    public static String path = conf.get("unl_resource_dir");
    public static String graph_path = conf.get("unl-Graph");
    public static String templateHome = conf.get("UNLCrawl");
    SeT set = new SeT(this);
    EnT ent = new EnT(this);
    InT INT = new InT(this);
    SuT sut = new SuT(this);
    SuTE sute = new SuTE(this);
    SnP snp = new SnP(this);
    String idL = "";

    public String getIdL() {
        return idL;
    }

    public void _do1(String s, String idl) {
        idL = idl;
        ent.report(s);
    }
//

    public void _do2(String s) {
        snp.report(s);
        //INT.report(s);
        sut.report(s);
        sute.report(s);
    }
//

    public void _do3(String s) {
//System.out.println("INDX Finished - "+s);
    }
//

    public void _do4(String s) {
//System.out.println("SUMM Finished - "+s);
    }

    public void _do5(String s) {
//System.out.println("SUMM Finished - "+s);
    }

    public void _do6(String s) {
//System.out.println("SUMM Finished - "+s);
    }
//

    public static void main(String args[]) throws Exception {
        int s = 0, e = 0;
        if (args.length >= 2) {
            s = Integer.parseInt(args[0]);
            e = Integer.parseInt(args[1]);
            new InteT()._main(s, e);
        } else {
            System.out.println("Usage:");
            System.out.println("./bin/nutch plugin analysis-unl-ta org.apache.nutch.analysis.unl.ta.Integrated.InteT start-file end-file");
            System.out.println("Sample:\n./bin/nutch plugin analysis-unl-ta org.apache.nutch.analysis.unl.ta.Integrated.InteT 0 5000");
        }
    }
//

    public void _main(int START, int STOP) throws Exception {
        try {
            ArrayList<String> arr = new ArrayList<String>();
            String ss = "";
            long l1 = System.currentTimeMillis();
            long l2 = System.currentTimeMillis();
            long tT = 0;
            int NN = 1;
//            //
//            BufferedReader in = new BufferedReader(new FileReader(new File("/opt/core.2.list.txt")));
//            while ((ss = in.readLine()) != null) {
//                arr.add("/opt/C-DAC.core/core_129/core/" + ss);
//            }
//            in.close();
//            //
            arr.addAll(Arrays.asList(new File("/opt/core_96/").list()));
//
            new File(Jumbo.getBasePath() + "core/").mkdirs();
            new File(Jumbo.getBasePath() + "Snippets/").mkdirs();
            new File(Jumbo.getBasePath() + "Summary/").mkdirs();

            set = new SeT(this);
            ent = new EnT(this);
            INT = new InT(this);
            sut = new SuT(this);
            sute = new SuTE(this);
            snp = new SnP(this);

            set._start();
            ent._start();
            snp._start();
            INT._start();
            sut._start();
            sute._start();
//
            if (START == STOP) {
                START = 0;
                STOP = arr.size();
            }
            for (int i = START; i < arr.size() && i < STOP; i++, NN++) {
                System.gc();
                String doccnt = "", URL = "";
//doccnt=IntegratedGeneral.readF("/opt/content/"+arr.get(i));
//URL="file:///opt/content/"+arr.get(i);
//int newdocid=Integer.parseInt(arr.get(i).replace("d","").replace(".txt","").trim());
                idL = "";
                set.report("/opt/core_96/" + arr.get(i));
                System.out.println("/opt/core_96/" + arr.get(i));
                Thread.sleep(50);
            }
            boolean eF = true;
            while (true && eF) {
                File ff = new File("/root/Desktop/gmb.stop");
                if (ff.exists() && ff.length() > 2) {
                    boolean tf = (set.seQ.isEmpty()) && (ent.enQ.isEmpty()) && (INT.inQ.isEmpty()) && (sut.suQ.isEmpty()) && (sute.suQ.isEmpty());
                    if (tf) {
                        set._stop();
                        ent._stop();
                        snp._stop();
                        INT._stop();
                        sut._stop();
                        sute._stop();
                        eF = false;
                        System.exit(0);
                        break;
                    }
                }
                Thread.sleep(5000);
            }
        } catch (Exception ee) {
            System.out.println(ee + "");
        }

    }
}
