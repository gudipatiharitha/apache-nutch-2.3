package org.apache.nutch.analysis.unl.ta.Integrated;

import org.apache.nutch.analysis.unl.ta.*;
import java.lang.*;
import java.io.*;
import java.net.InetAddress;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * This class contains some methods which required for "Integrated.java" Class
 */
public class IntegratedGeneral {

    public static String putSolrURL(String solr_Server_url) throws Exception {
        String s = File.createTempFile("solr", "url").getAbsolutePath();
        writeF(solr_Server_url, s);
        return s;
    }

    public static String getSolrURL(String tmpFile) throws Exception {
        String s = readF_API(tmpFile);
        if (s != null || !"".equals(s)) {
            return s;
        } else {
            return "http://localhost:8983/solr/";
        }
    }

    public static void writeinObject(String filename, FinalLLImpl ll_new, String graph_path) {
        try {
            FileOutputStream fostream = new FileOutputStream(graph_path + filename);
            ObjectOutput oostream = new ObjectOutputStream(fostream);
            oostream.writeObject(ll_new);
            //System.out.println("Writing Completed");
            oostream.close();
            fostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FinalLLImpl readObject(String fn) {
        FinalLLImpl ll_new = null;
        try {
            FileInputStream fis = new FileInputStream(fn);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ll_new = (FinalLLImpl) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ll_new;
    }

    public static FinalLLImpl[] readObjectArr(String fn) {
        FinalLLImpl[] ll_new = null;
        try {
            FileInputStream fis = new FileInputStream(fn);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ll_new = (FinalLLImpl[]) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ll_new;
    }

    public static HashMap<String, String> readSummary(String fn) {
        HashMap<String, String> ll_new = null;
        try {
            FileInputStream fis = new FileInputStream(fn);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ll_new = (HashMap<String, String>) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ll_new;
    }

    public static String readF(String fn) throws Exception {
        if (new File(fn).exists()) {
            BufferedReader fr = new BufferedReader(new FileReader(fn));
            String fc = "", tmp = "";
            while ((tmp = fr.readLine()) != null) {
                fc += tmp;
            }
            //
            Pattern p = Pattern.compile("<title>(.*?)</title>", Pattern.DOTALL);
            Matcher m = p.matcher(fc);
            String tle = "";
            while (m.find()) {
                tle = m.group(1);
            }
            tle = StringEscapeUtils.unescapeHtml(tle);
            tle = tle.replaceAll("[^\\p{InTamil}.]", " ");
            tle = tle + ".\n";

            //
            fc = StringEscapeUtils.unescapeHtml(fc);
            fc = fc.replaceAll("[^\\p{InTamil}.]", " ");
            fc = fc.replaceAll("[\t]", " ");
            fc = fc.replaceAll("[ ]{2,}", " ");
            fc = fc.replaceAll("[.]{2,}", ".");
            fc = fc.replaceAll("[. ]{2,}", ".");
            fc = fc.replaceAll("[ .]{2,}", ".");
            fc = fc.replaceAll("[.]", ".\n");

            return tle + fc;
        } else {
            return "";
        }
    }

    public static String readFF(String fn) throws Exception {
        String s = "~¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ";
        if (new File(fn).exists()) {
            BufferedReader fr = new BufferedReader(new FileReader(fn));
            String fc = "", tmp = "";
            while ((tmp = fr.readLine()) != null) {
                for (int jj = 0; jj < s.length(); jj++) {
                    tmp = tmp.replace(s.charAt(jj) + "", "");
                }
                fc += tmp;
            }
            return fc;
        } else {
            return "";
        }
    }

    public static String readF_API(String fn) throws Exception {
        if (new File(fn).exists()) {
            BufferedReader fr = new BufferedReader(new FileReader(fn));
            String fc = "", tmp = "";
            while ((tmp = fr.readLine()) != null) {
                fc += tmp;
            }
            return fc;
        } else {
            return "";
        }
    }

    /*
     *
     * ~¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ
     *
     */
    public static void writeF(String fc, String fn) throws Exception {
        PrintStream out = new PrintStream(fn);
        out.print(fc);
        out.close();
    }

    public static String fetchID(String path) throws Exception {
        //return Integer.parseInt(path.replaceAll("[\\W]", "").replaceAll("[a-zA-Z]", ""));
        int ii = path.lastIndexOf(File.separator);
        if (ii >= 0) {
            path = path.substring(path.lastIndexOf(File.separator));
        }
        path = path.replaceAll("[^\\p{Nd}]", "");
        return path;
    }

    public static void writeList(String filename, ArrayList<String> ll_new) {
        try {
            FileOutputStream fostream = new FileOutputStream(filename);
            ObjectOutput oostream = new ObjectOutputStream(fostream);
            oostream.writeObject(ll_new);
            //System.out.println("Writing Completed");
            oostream.close();
            fostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> readList(String fn) {
        ArrayList<String> ll_new = null;
        try {
            FileInputStream fis = new FileInputStream(fn);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ll_new = (ArrayList<String>) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ll_new;
    }

    public static String _generateDocID() throws Exception {
        InetAddress thisIp = InetAddress.getLocalHost();
        String res = thisIp.getHostAddress().replaceAll("[^\\p{Nd}]", "");
        Date dd = new Date();
        String r = res + "" + dd.getDate() + "" + dd.getHours() + "" + dd.getMinutes() + "" + dd.getSeconds();
        r = r.replaceAll("[^\\p{Nd}]", "");
        if (r.length() < 20) {
            Random R = new Random();
            int len = (20 - r.length());
            String c = "";
            for (int ii = 0; ii < len; ii++) {
                c += "9";
            }
            int l = Integer.parseInt(c);
            int a = R.nextInt(l);
            r += "" + a;
        }

        return r;
    }
}
