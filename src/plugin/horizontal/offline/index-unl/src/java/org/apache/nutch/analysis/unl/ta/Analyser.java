package org.apache.nutch.analysis.unl.ta;

import java.io.*;
import java.util.Stack;
import java.util.Set;
import java.util.HashSet;
import java.util.Vector;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.*;

import org.apache.nutch.analysis.unl.ta.Tag.*;

public class Analyser {
    

    static boolean analysePart = true;
    static boolean print;
    
    //public static ResourceBundle Tags = ResourceBundle.getBundle(Tag.ENGLISH_EXPAND,new Locale("en","US"));
    public static ResourceBundle Tags = ResourceBundle.getBundle("org/apache/nutch/analysis/unl/ta/Tag/" + Tag.ENGLISH_EXPAND, new Locale("en", "US"));

    public static String analyseF(String input, boolean analysePart) {

        String output = "<" + input + ">" + ":\n";
        try {
            Stack allStk = new Stack();
            int count = 0;
            //BufferedWriter out = new Buffere7dWriter(new FileWriter("array.txt",true));
            //BufferedWriter out1 = new BufferedWriter(new FileWriter("partsarray.txt",true));
            // for dic ref count
            // output += "- " + Method.analyse(input,allStk);
            //  //////System.out.println("before method call");
            //out.write("& "+input);
            //out.write("\n");
            //out1.write("& "+input);
            //out1.write("\n");
            Method.analyse(input, allStk, analysePart);
            //	//////System.out.println("after method call");
            int size = allStk.size();
            //////System.out.println("size of stack  "+ size);
            if (size == 0) {
                output += "<Error>\n";
                return output;
            }
            //	output +=" Size="+ size;
            for (int i = 0; i < size; i++) {
                Stack outputStack = (Stack) allStk.get(i);
                String part = "<";
                boolean flag = false;
                String stag = "~";
                while (!outputStack.empty()) {
                    Entry entry = (Entry) outputStack.pop();
                    String s = UnicodeConverter.revert(entry.getPart());
                    int tag = entry.getTag();
                    //System.out.println("s value = " + s);
                    //if(!s.startsWith("கள்")){
                    if (tag != -1) {
                        flag = true;
                        if (part.length() == 1) {
                            // //System.out.println("output of s in analyser s if part" + s);

                            // if (s.startsWith("கள்")) {
                            // s="";
                            part += s;
                            //   }

                        } else {
                            ////System.out.println("output of s in analyser s in else " + s);
                            part += "+" + s;
                        }
                        // }

                        // if (!s.startsWith("கள்")) {
                        s += " < ";
                        // if(!s.startsWith("Plural Suffix")){
//			System.out.println("----------Tags:"+Tags);
                        try {
                            s += Tags.getString(String.valueOf(tag));//}
                        } catch (Exception ee) {
                            // System.out.println("AnalyserErr:" + s + "-" + tag);
                        }
                        /*
                         * if(!s.startsWith("Plural Suffix")){ s
                         * +=Tags.getString(String.valueOf(tag)); }else{ s
                         * +=Tags.getString(String.valueOf(tag)); }
                         */

                        // //System.out.println("output of s in analyser s after tags strung" + s);
                        s += " " + "&" + " ";
                        stag = stag + "\t" + String.valueOf(tag);
                        s += String.valueOf(tag);
                        ////System.out.println("output of sttring tag" + String.valueOf(tag));
                        // //System.out.println("\t");
                        // //System.out.println("output of s in analyser s" + s);
                        // //System.out.println("\t");
                        s += " > ";
                        // }
                    } else {
                        count++;
                        s += "<0>";
                        break;
                    }
                    //if(!s.("Plural Suffix"))
                    output += s;
                    //if(output.startsWith("கள்")){
                    //output=output.replaceAll("கள்","");
                    // //System.out.println("analyser value of output in two " + output);
                    // }
                }
                //out.write("\n");
                if (!stag.equals("~")) //out1.write("\n"+stag+"\n");
                {
                    if (allStk.size() != 1 && flag == true) {
                        output += Utils.newLineStr + part + '>' + Utils.newLineStr;
                    }
                }

            }
            output += " count=" + count + "\n";
            if (count == (size - 1) && size == 4) {
                if (output.startsWith("<#>:")) {
                    output = "<<s>\n";
                    //System.out.println("analyser value of output in two if " + output);
                } else {
                    output += "<unknown>\n";
                    //out.write("<unknown>\n");
                    //out1.write("<unknown>\n");
                }
            }
            //out.write("@\n");
            //out.clo		se();
            //out1.write("@\n");
            //out1.close();
        } catch (Exception e) {
            System.err.println("Analyser analyseF: " + e);
            e.printStackTrace();
            return input;
        }
        //////System.out.println( "output =" + output);
        output += "<<wc>\n";

        return output;
    }

    public static String analyseW(String input, boolean analysePart) {
        String output = input + ":";

        try {
            Stack allStk = new Stack();

            // for dic ref count
            // output += "- " + Method.analyse(input,allStk);

            Method.analyse(input, allStk, analysePart);
            int size = allStk.size();

            if (size == 0) {
                output += " Unknown";
                return output;
            }
            for (int i = 0; i < size; i++) {
                Stack outputStack = (Stack) allStk.get(i);

                while (!outputStack.empty()) {
                    Entry entry = (Entry) outputStack.pop();
                    String s = UnicodeConverter.revert(entry.getPart());

                    output += Utils.newLineStr_OS;
                    output += s;
                    int tag = entry.getTag();

                    if (tag != -1) {
                        s = " < ";
                        s += Tags.getString(String.valueOf(tag));
                        s += " > ";
                    } else {
                        s = "";
                    }
                    output += s;
                }
                if (allStk.size() != 1) {
                    output += Utils.newLineStr_OS + "***";
                }
            }
        } catch (Exception e) {
            System.err.println("Analyser analyseW: " + e);
            e.printStackTrace();
            return input;
        }
        return output;
    }

    public static Vector analyse(String input, boolean analysePart) {
        Vector output = new Vector();

        try {
            Stack allStk = new Stack();

            Method.analyse(input, allStk, analysePart);
            for (int i = 0; i < allStk.size(); i++) {
                Stack outputStack = (Stack) allStk.get(i);
                Vector part = new Vector();
                Vector tag = new Vector();

                while (!outputStack.empty()) {
                    Entry entry = (Entry) outputStack.pop();
                    String s = UnicodeConverter.revert(entry.getPart());

                    part.add(s);
                    int tagInt = entry.getTag();

                    tag.add(new Integer(tagInt));
                }
                output.add(new Element(part, tag));
            }
        } catch (Exception e) {
            System.err.println("Analyser analyse: " + e);
            e.printStackTrace();
            return null;
        }

        return output;
    }

    public static Vector getVector(Stack inputStk) {
        Vector output = new Vector();

        try {
            for (int i = 0; i < inputStk.size(); i++) {
                Stack outputStack = (Stack) inputStk.get(i);
                Vector part = new Vector();
                Vector tag = new Vector();

                while (!outputStack.empty()) {
                    Entry entry = (Entry) outputStack.pop();
                    String s = UnicodeConverter.revert(entry.getPart());

                    part.add(s);
                    int tagInt = entry.getTag();

                    if (tagInt != -1) {
                        s = String.valueOf(tagInt);
                    } else {
                        s = "";
                    }
                    tag.add(s);
                }
                output.add(new Element(part, tag));
            }
        } catch (Exception e) {
            System.err.println("Analyser analyse: " + e);
            e.printStackTrace();
            return null;
        }
        return output;
    }

    // call this before analysing a large document
    // load btree into memory
    public static void init() {
        analyse(UnicodeConverter.revert(Constant.vel), analysePart);
    }

    public static void main(String args[]) {
        try {
            Vector v = new Vector();
            String str = "";
            String analysed = "";
            BufferedReader inbuff = new BufferedReader(new FileReader("/root/NetBeansProjects/Analyser/in.txt"));
            BufferedWriter outbuff = new BufferedWriter(new FileWriter("/root/NetBeansProjects/Analyser/out.txt"));
            BufferedWriter unknownbuff = new BufferedWriter(new FileWriter("/root/NetBeansProjects/Analyser/unknown_next_new.txt"));
            BufferedWriter knownbuff = new BufferedWriter(new FileWriter("/root/NetBeansProjects/Analyser/known_next_new.txt"));
            //BufferedReader br = new BufferedReader(new FileReader("/root/NetBeansProjects/Analyser/docs.txt"));
            while ((str = inbuff.readLine()) != null) {
                if (str.indexOf("#") == -1) {
                    //ArrayList ar=new Analyser().onlineDictAnalyser(str);
                    //analysed=ar.get(2).toString();

                    String st = "";
                    String[] spt = str.split(" ");
                    //System.out.println("value of spt in str " + spt[0].toString());
                    for (int k = 0; k < spt.length; k++) {
                        //System.out.println("value of spt in str inside" + spt[k].toString());
                        st = spt[k];
                        analysed = Analyser.analyseF(st, true);

                        if ((analysed.contains("<unknown>")) || (analysed.contains("count=4")) || (analysed.contains("<Error>"))) {
                            //System.out.println(st);
                            unknownbuff.write(st + "\n");
                        } else {
                            outbuff.write(analysed + "\n");
                        }
                    }
                }

                //System.out.println("anf " + analysed);
            }



            /*
             * document work while ((str = br.readLine()) != null) { // String
             * contents = ""; String fn = "./Documents/" + str; //
             * //System.out.println("check:"+check); StringTokenizer strTok =
             * new StringTokenizer(str, "."); String docid = strTok.nextToken();
             * String other = strTok.nextToken(); //String str = ""; String
             * str_crt = ""; BufferedReader buffer = new BufferedReader(new
             * FileReader(fn)); while ((str = buffer.readLine()) != null) { if
             * (str.startsWith("[")) { str_crt = str.replace("[", ""); } else if
             * (str.endsWith("]")) { str_crt = str.replace("]", ""); } else {
             * str_crt = str; }
             *
             * analysed = Analyser.analyseF(str_crt.trim(), true);
             * //System.out.println("anf " + analysed); if
             * ((analysed.contains("<unknown>")) ||
             * (analysed.contains("count=4")) || (analysed.contains("<Error>"))
             * || (analysed.contains("count=5"))) { new
             * Analyser().writeintofileUnKnown(docid, analysed);
             * unknownbuff.write(str_crt.trim() + "\n"); } else { new
             * Analyser().writeintofileKnown(docid, analysed);
             * knownbuff.write(str_crt.trim() + "\n"); } } }
             */

            inbuff.close();
            outbuff.close();
            unknownbuff.close();
            knownbuff.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeintofileKnown(String docidk, String resultk) {
        try {
            Writer outputk = null;

            File fnk = new File("./Results/Known/" + docidk + ".txt");
            fnk.createNewFile();
            outputk = new BufferedWriter(new FileWriter(fnk, true));
            outputk.write(resultk + "\n");
            outputk.close();
        } catch (Exception e) {
        }
    }

    public void writeintofileUnKnown(String docidk, String resultk) {
        try {
            Writer outputk = null;

            File fnk = new File("./Results/Unknown/" + docidk + ".txt");
            fnk.createNewFile();
            outputk = new BufferedWriter(new FileWriter(fnk, true));
            outputk.write(resultk + "\n");
            outputk.close();
        } catch (Exception e) {
        }
    }
    // for procesing analyser output------------------
    static Set d = new HashSet();
    static String y = "";

    static {
        d.add(String.valueOf(Tag.Numbers));
        d.add(String.valueOf(Tag.Noun));
        d.add(String.valueOf(Tag.Pronoun));
        d.add(String.valueOf(Tag.InterrogativeNoun));
        d.add(String.valueOf(Tag.PronounOblique));
        d.add(String.valueOf(Tag.Verb));
        d.add(String.valueOf(Tag.FiniteVerb));
        d.add(String.valueOf(Tag.NegativeFiniteVerb));
        d.add(String.valueOf(Tag.Adjective));
        d.add(String.valueOf(Tag.DemonstrativeAdjective));
        d.add(String.valueOf(Tag.InterrogativeAdjective));
        d.add(String.valueOf(Tag.Adverb));
        d.add(String.valueOf(Tag.Interjection));
        d.add(String.valueOf(Tag.Conjunction));
        d.add(String.valueOf(Tag.Particle));
        d.add(String.valueOf(Tag.Intensifier));
        d.add(String.valueOf(Tag.Postposition));
    }

    public static boolean isNoun(Vector v) {
        for (int i = 0; i < v.size(); i++) {
            Element elmt = (Element) v.get(i);
            Vector tag = elmt.getTag();
            int index = elmt.containsTag(new Integer(Tag.Noun));

            if (index != -1) {
                return true;
            }
        }
        return false;
    }

    public static boolean is2320(Vector v) {
        for (int i = 0; i < v.size(); i++) {
            Element elmt = (Element) v.get(i);
            Vector tag = elmt.getTag();
            int index = elmt.containsTag(new Integer(2320));

            if (index != -1) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNoun(String word) {
        return isNoun(analyse(word, analysePart));
    }

    public static boolean isVerb(Vector v) {
        for (int i = 0; i < v.size(); i++) {
            Element elmt = (Element) v.get(i);
            Vector tag = elmt.getTag();
            int index = elmt.containsTag(new Integer(Tag.Verb));

            if (index != -1) {
                return true;
            }
        }
        return false;
    }

    public static boolean isVerb(String word) {
        return isVerb(analyse(word, analysePart));
    }

    public static String getRoot(Vector v) {
        for (int i = 0; i < v.size(); i++) {
            Element elmt = (Element) v.get(i);
            Vector partV = elmt.getPart();

            // String tag = null;

            if (partV.size() > 0) {
                return (String) partV.get(0);
            }
        }
        return null;
    }

    public static String getRoot(String word) {
        return getRoot(analyse(word, analysePart));
    }

    public static boolean isAnalysed(Vector v) {
        if (v.size() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isAnalysed(String input) {
        Vector v = analyse(input, analysePart);

        if (v.size() > 0) {
            return true;
        }
        return false;
    }

    public ArrayList onlineDictAnalyser(String input) {
        input = removeSymbols(input.trim());
        ArrayList arr = new ArrayList();
        input = analyseF(input.trim(), true);
        if (!input.contains("<Error>")) {
            arr.add(input);
            arr.add(getAanlyserFormat(input));
            input = getRootWord(input);
            //////////////System.out.println("--->>>" + input);

            if (input.length() > 1) {
                arr.add(input.trim());
                input = getTypes(arr.get(0).toString());
                arr.add(input.trim());
                arr.add(getTranslation(arr.get(1).toString(), arr.get(0).toString(), arr.get(2).toString()));
            } else {
                arr = new ArrayList();
                arr.add("Analyser Not Found");
                arr.add("Analyser Not Found");
                arr.add("Analyser Not Found");
                arr.add("none");
                arr.add("Analyser Not Found");
            }
        } else {
            arr.add("Analyser Not Found");
            arr.add("Analyser Not Found");
            arr.add("Analyser Not Found");
            arr.add("none");
            arr.add("Analyser Not Found");
        }
        return arr;
    }

    public String getAanlyserFormat(String input) {
        if (input.contains("Demonstrative Adjective")) {
            input = isDoubleRoot(input);
        } else {
            input = isSingleRoot(input);
        }
        return input;
    }

    public String isSingleRoot(String input) {
        String alsFormat = "";
        String[] spt = input.split("\n");
        for (int i = 0; i < spt.length; i++) {
            if (spt[i].contains(":") && spt.length > 1) {
                i++;
                while (spt[i].contains("<")) {
                    int start = spt[i].indexOf("<");
                    int end = 0;
                    if (spt[i].contains(">")) {
                        end = spt[i].indexOf(">");
                    } else {
                        end = spt[i].length() - 2;
                    }
                    if (end != spt[i].length() - 2) {
                        alsFormat += spt[i].substring(0, start) + "+";
                    } else {
                        alsFormat += checkIkkal(spt[i].substring(0, start));
                    }
                    spt[i] = spt[i].substring(end + 1, spt[i].length());
                }
                alsFormat += "<br/>";
            }
        }
        return alsFormat;
    }

    public String isDoubleRoot(String input) {
        String alsFormat = "";
        String[] spt = input.split("\n");
        while (spt[spt.length - 2].contains("<")) {
            int start = spt[spt.length - 2].indexOf("<");
            int end = 0;
            if (spt[spt.length - 2].contains(">")) {
                end = spt[spt.length - 2].indexOf(">");
            } else {
                end = spt[spt.length - 2].length() - 2;
            }
            if (end != spt[spt.length - 2].length() - 2) {
                alsFormat += spt[spt.length - 2].substring(0, start) + "+";
            } else {

                alsFormat += checkIkkal(spt[spt.length - 2].substring(0, start));
            }
            spt[spt.length - 2] = spt[spt.length - 2].substring(end + 1, spt[spt.length - 2].length());
        }
        alsFormat += "<br/>";
        return alsFormat;
    }

    public String checkIkkal(String input) {
        if (input.contains("க்கள்")) {
            input = input.replace("க்", "க் + ");
        }
        return input;
    }

    public String getRootWord(String input) {
        try {
            if (input.contains("Demonstrative Adjective")) {
                String[] spt = input.split("\n");
                int string = spt.length - 2;
                int end = spt[string].indexOf("<");
                input = spt[string].substring(0, end);
            } else {
                int start = input.indexOf(":");
                input = input.substring(start + 1, input.length() - 1);
                int end = input.indexOf("<");
                input = input.substring(1, end - 1);
            }
            return input;
        } catch (Exception e) {
            return "Word Not Found";
        }
    }

    public String getTypes(String input) {
        try {
            if (input.contains("Demonstrative Adjective")) {
                String[] spt = input.split("\n");
                int string = spt.length - 2;
                int start = spt[string].indexOf("<");
                int end = spt[string].indexOf(">");
                input = spt[string].substring(start + 1, end - 1);
            } else {
                int start = input.indexOf(":");
                input = input.substring(start + 1, input.length() - 1);
                start = input.indexOf("<");
                int end = input.indexOf(">");
                input = input.substring(start + 1, end - 1);
            }
            return input;
        } catch (Exception e) {
            return "none";
        }
    }

    public String removeSymbols(String input) {
        String[] symbols = {"!", "@", "#", "$", "%", "^", "&", "_", "{", "}", "=", "[", "]", "|", "/", "\\", "\"", ">", "<", ";", ":", "'", ",", ".", "~", "`", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "?", "+", "-", "*", "(", ")"};
        for (int i = 0; i < symbols.length; i++) {
            if (input.contains(symbols[i])) {
                input = input.replace(symbols[i], "");
            }
        }
        return input;
    }

    public String getTranslation(String getSplitedCon, String getAnalyser, String getRoot) {
        String transout = "";
        String Analyser = getSplitedCon.replaceAll("<br/>", "");
        String splitVal[] = Analyser.split("[+]");
        for (int i = 0; i < splitVal.length; i++) {
            ////////////System.out.println(splitVal[i].trim());
            if (i == 0) {
                transout = splitVal[i].trim() + "- root\n";
            } else {
                transout += splitVal[i].trim() + "-" + rules(splitVal[i].trim(), getAnalyser, getRoot) + "\n";
            }
        }
        return transout;
    }

    public String rules(String val, String outputVal, String root) {
        String transOp = "";
        if (val.equals("ஆக")) {
            transOp = "defines thing which is in a state or has an attribute [or] thing which restrict a focused thing.";
        } else if (val.equals("போல") || val.equals("விட") || val.equals("மிக") || val.equals("காட்டிலும்")) {
            transOp = "defines thing used as the basis for expressing degree.";
        } else if (val.equals("உக்காக") || val.equals("உக்கு")) {
            transOp = "defines not directly related beneficiary [or] purpose of an event.";
        } else if (val.equals("வ்")) {
            if (outputVal.contains("Verb")) {
                transOp = "future Tense";
            } else {
                transOp = "connectivity Character";
            }
        } else if (val.equals("ஓடு")) {
            transOp = "defines thing not in focus which initiates an implicit event which is done in parallel.";
        } else if (val.equals("என்பது") || val.equals("எனப்படுவது")) {
            transOp = "defines equivalent concept.";
        } else if (val.equals("உடன்") || val.equals("உம்")) {
            transOp = "defines thing that is directly affected by an implicit event done in parallel.";
        } else if (val.equals("கொண்டு")) {
            transOp = "defines co occured event or state for a focused event or state.";
        } else if (val.equals("பொது")) {
            transOp = "defines a period of time during an event occur.";
        } else if (val.equals("இல்")) {
            if (outputVal.contains("இருந்து")) {
                transOp = "defines range between two things.";
            } else {
                transOp = "defines place of an event occurs [or] place in focus where an event affects.";
            }
        } else if (val.equals("இடம்")) {
            transOp = "indicates a location.";
        } else if (outputVal.contains("இருந்து")) {
            transOp = "defines place from an event occur.";
        } else if (val.equals("முதல்")) {
            transOp = "defines place of an event begins.";
        } else if (val.equals("வரை")) {
            transOp = "defines place of an event ends.";
        } else if (val.equals("ஆல்")) {
            transOp = "defines instrument to carry out an event.";
        } else if (val.equals("ஆ") || val.equals("அ")) {
            transOp = "defines thing in focus which is directly affected by an event.";
        } else if (val.equals("மீது")) {
            transOp = "defines place in focus where an event affects.";
        } else if (val.equals("இன்") || val.equals("உடைய")) {
            transOp = "defines concept of which a focused thing is a part [or] Possessor of a thing.";
        } else if (val.equals("கள்")) {
            transOp = "plurals";
        } else if (val.equals("உள்") || val.equals("உள்ளே")) {
            transOp = "in";
        } else if (val.equals("ஏன்")) {
            transOp = "first person Singular";
        } else if (val.equals("ந்து") || val.equals("ந்த்")) {
            transOp = "past tense";
        } else if (val.equals("ஓடு")) {
            transOp = " ";
        } else if (val.equals("மீது") || val.equals("மேல்")) {
            transOp = "on";
        } else if (val.equals("க்கிறு") || val.equals("கிறு") || val.equals("கிற்") || val.equals("க்கிற்")) {
            transOp = "present tense";
        } else if (val.equals("உம்")) {
            if (outputVal.contains("Verb")) {
                transOp = "future tense";
            } else if (outputVal.contains("Noun")) {
                transOp = "even [or] also ";
            }
        } else if (val.equals("ஐ")) {
            transOp = "defines direct object";
        } else if (val.equals("இ")) {
            transOp = "past tense";
        } else if (val.equals("ப்ப்")) {
            transOp = "future tense";
        } else if (val.equals("த்") || val.equals("த்த்") || val.equals("ள்") || val.equals("உ")) {
            transOp = "future tense";
        } else if (val.equals("ஆன்")) {
            transOp = "masculine singular";
        } else if (val.equals("ஆள்")) {
            transOp = "feminine singular";
        } else if (val.equals("க்") || val.equals("ய்")) {
            transOp = "connective character";
        } else if (val.equals("க்கள்")) {
            transOp = "plural";
        } else {
            transOp = "";
        }
        return transOp;
    }
}//End of analyser from agaraadhi
//Added just to implement UNL.java interface, never used anywhere
  /*
 * public ArrayList process(String queryString) { ArrayList arr = null; return
 * arr; }
 */
