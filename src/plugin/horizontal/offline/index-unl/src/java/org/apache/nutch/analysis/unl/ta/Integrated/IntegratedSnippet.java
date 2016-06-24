package org.apache.nutch.analysis.unl.ta.Integrated;

import java.io.*;
import java.util.*;
import org.apache.nutch.analysis.unl.ta.*;
import org.apache.nutch.util.NutchConfiguration;
import org.apache.hadoop.conf.Configuration;

/**
 * @author karthikeyan
 * @version 2.0
 * @since AUCEG
 */
public class IntegratedSnippet {

    public HashMap<String, String[]> snippets = new HashMap<String, String[]>();
    public ArrayList<String> arr = new ArrayList<String>();
    public String globalDocid = "start";
    public Configuration configuration = NutchConfiguration.create();
    public String SummaryHome = configuration.get("UNLCrawl");
    public String EnconversionHome = configuration.get("unl-Graph");
    public String sentenceExtractionHome = configuration.get("SentenceExtraction");
    public String FN = "/tmp/aa.txt";

    public IntegratedSnippet() {
    }

    public void __doSnippet(FinalLLImpl ll_new, ArrayList<String> fn) throws Exception {
        //_fetchAllSent(fn);
        arr = fn;
        try {
            snippets = new HashMap<String, String[]>();
            ConceptNode conceptnode = new ConceptNode();
            HeadNode headnode = new HeadNode();
            headnode = ll_new.head;
            conceptnode = headnode.colnext;
            while (conceptnode != null) {
		if((conceptnode.poscheck.toString().contains("Entity")) || conceptnode.poscheck.toString().contains("Noun")){
                	getSnippet(conceptnode.docid, conceptnode.uwconcept.toString(), conceptnode.gn_word.toString(), conceptnode.sentid.toString());
		}
                conceptnode = conceptnode.getColNext();
            }
            SnippetWriteinFile();
        } catch (Exception eee1) {
            eee1.printStackTrace();
        }

    }
    //
    //

    public void getSnippet(String docid, String constraint, String tamilword, String sentid) {
        int lineNumber = Integer.parseInt(sentid.substring(1, sentid.length()));
        globalDocid = docid;
        String snippetLine = arr.get(lineNumber - 1);
        if (snippetLine.contains(tamilword)) {
            String[] output = Linesplit(snippetLine, tamilword, lineNumber);
            if (!snippets.containsKey((tamilword + constraint))) {
                snippets.put((tamilword + constraint), output);
                //System.out.println((tamilword + constraint) + " - " + output[0] + "\t" + output[1] + "\t" + output[2]);
            }
        }

    }
//

    public void _print(HashMap<String, String[]> hash) {
        Set hashkey = hash.keySet();
        for (Object constrains : hashkey) {
            System.out.print("\t" + constrains.toString() + "->");
            String[] output = (String[]) hash.get(constrains.toString());
            System.out.println("\t" + output[0] + "\t" + output[1] + "\t" + output[2] + "\n");
        }

    }

    public void SnippetWriteinFile() throws Exception {
        // System.out.println("SnippetFileWriting----->" + globalDocid);
        ObjectOutputStream objectOutputStream = IOHelper.getObjectOutputStream(Jumbo.getBasePath() + "Snippets/" + globalDocid + ".ser");
        IOHelper.writeObjectToOutputStream(objectOutputStream, snippets);
        IOHelper.closeObjectOutputStream(objectOutputStream);
        // _print(snippets);
    }

    public void Snippetput(String key, String[] value) {
        if (!snippets.containsKey(key)) {
            snippets.put(key, value);
        }
    }

    public String SnippetArrangement(String line, String tamilWord) {
        String[] words = line.split(" ");
        if (words.length < 10) {
            return line;
        }
        int startIndex = 0;
        int startSnippet = 0;
        int endSnippet = words.length;

        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(tamilWord)) {
                startIndex = i;
            }
        }
        if (startIndex - 5 > 0) {
            startSnippet = startIndex - 5;
        }
        if ((startSnippet + 15) < words.length) {
            endSnippet = startSnippet + 15;
        }
        String snippet = "";
        for (int i = startSnippet; i < endSnippet; i++) {
            snippet = snippet + " " + words[i];
        }
        return snippet.trim();
    }

    public String[] Linesplit(String line, String tamilwords, int lineNumber) {
        String[] output = new String[3];
        String head = "", body = "";
        String[] linespt = line.split(" ");
        for (int i = 0; i < linespt.length; i++) {
            if (i == 4) {
                break;
            }
            head = head + linespt[i] + " ";
        }
        if (head.length() < line.length()) {
            body = line.substring(head.length() - 1, line.length() - 1);
        } else {
            body = head;
        }
        head = Highlightwords(head, tamilwords);
        body = Highlightwords(body, tamilwords);
        output[0] = head;
        output[1] = body;
        output[2] = String.valueOf(lineNumber);
        return output;
    }

    public String Highlightwords(String input, String highlightwords) {
        String[] spt = highlightwords.split(" ");
        for (String s : spt) {
            input = input.replace(s, "<b><font color=\"green\">" + s + "</font></b>");
        }
        return input;
    }
}
