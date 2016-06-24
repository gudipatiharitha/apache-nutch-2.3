/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.nutch.analysis.unl.ta.snippet;

import org.apache.nutch.analysis.unl.ta.SummaryGet;

/**
 *
 * @author root
 */
public class InvokeSnippet {

    public String snippetArrangements(String snippet, String url, String docid) {
        String output = "";
        SummaryGet summary1 = new SummaryGet();
        String[] urlsplit = url.split("/");
        String urlShort = "";
        try {
            urlShort = urlsplit[0] + "/" + urlsplit[1] + "/" + urlsplit[2] + "/";
        } catch (Exception ee) {
            urlShort = url;
        }
        if (snippet.length() > 50) {
            String titt = snippet.substring(0, 50);
            titt = titt.replace("<b style=\"color:green\">", "");
            titt = titt.replace("</b>", "");
            output = "<a style=\"color:black\" href=" + url + ">" + titt + "</a> <br/>" + "<a style=\"color:black\" href=" + url + ">" + urlShort + "</a> <br/>" + snippet + "</br>#" + summary1.getSummaryunl(docid) + "</br> #" + summary1.getEngSummaryunl(docid) + "</br>";
        } else if (snippet.length() < 50) {
            output = "<a style=\"color:black\" href=" + url + ">" + snippet + "</a> <br/>" + "<a style=\"color:black\" href=" + url + ">" + urlShort + "</a> <br/>" + snippet + "</br>#" + summary1.getSummaryunl(docid) + "</br> #" + summary1.getEngSummaryunl(docid) + "</br>";
        }
        return output;
    }

    public String[] snippet_generator(String constraints, String docid, String url, String tamilWord) {
        String[] snippet = new String[3];
        snippet[0] = "";
        snippet[1] = "";
        snippet[2] = "";
//        String sni = Snippet._getSnippet(docid, new String[]{tamilWord});
  //      snippet[0] = snippetArrangements(sni, url, docid);
        return snippet;
    }

    public String[] snippet_generator(String constraints, String docid, String url, String[] tamilWord) {
        String[] snippet = new String[3];
        snippet[0] = "";
        snippet[1] = "";
        snippet[2] = "";
      //  String sni = Snippet._getSnippet(docid, tamilWord);
       // snippet[0] = snippetArrangements(sni, url, docid);
        return snippet;
    }

    public static void main(String[] args) throws Exception {
        //System.out.println("S:" + Snippet._getSnippet("10514231221894618187", new String[]{"அன்பு", "இரு", "ந்திய"}));
        System.out.println(new InvokeSnippet().snippet_generator("", "1051423122175343714", "http://abc.google.com/abc/1.html", new String[]{"அன்பு", "இரு", "ந்திய"})[0]);
    }
}
