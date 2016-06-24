/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iitkgp.cel.parse.html;

import java.io.*;
import java.util.*;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.iitkgp.cel.parse.html.TextUtils;
import org.iitkgp.cel.parse.html.MetaData;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.HtmlNode;
import org.htmlcleaner.TagNode;

/**
 *
 * @author Dr Rajendra
 */
public class CmlNoiseRemover {
//public class CmlNoiseRemover implements TagNodeVisitor {

    /** Default Constructor */
    public CmlNoiseRemover() {
    }

    /**
     * This method filters rebuild the ill / semi structured given HTML tags 
     * into well structured structure
     * @param content String RAW HTML content
     * @return TagNode Well balanced HTML tree
     */
    public TagNode FilterNoisyContent(String content) {
        CleanerProperties cp = new CleanerProperties();
        cp.setOmitComments(true);
        cp.setOmitDoctypeDeclaration(true);
        cp.setOmitUnknownTags(true);
        cp.setOmitXmlDeclaration(true);
        cp.setTreatDeprecatedTagsAsContent(false);
        TagNode tagnode = null;
        HtmlCleaner htl = new HtmlCleaner(cp);
        content = content.replaceAll("&nbsp;", " ").replaceAll("&raquo;", "&#187;").replaceAll("&gt;", " ").replaceAll("&lt;", " ");
        content = content.replaceAll("&amp;", "	&#38;").replaceAll("&copy;", "&#169;");
        try {
            tagnode = htl.clean(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tagnode;
    }
    
    /**
     * Boolean function to check the presence of Specific tags in the DOM Tree
     * @param tagnode The given balanced TagNode object
     * @param findTag The HTML tag to be searched for.
     * @return boolean  true if findTag is present; false, otherwise
     */
    public boolean isTagAvailable(TagNode tagnode, String findTag) {
        return ((tagnode.getElementListByName(findTag, true).size() > 0) ? true : false);
    }

    /**
     * This method filters the text present in <TITLE> ... </TITLE> tag 
     * @param tagnode TagNode of the HTML content
     * @return String TITLE of the given HTML content via TagNode
     */
    public String getTitle(TagNode tagnode) {
        String title = "";
        try {
            title = tagnode.findElementByName("title", true).getText().toString();
            title = TextUtils.ReplaceEscapeChars(TextUtils.ConvertHexToUnicode(title));
        } catch (Exception e) {
            System.out.println("ERROR in Extracting: title");
        }
        return title+"";
    }

    /**
     * This method filters the data from META tags of the given HTML document
     * @param tagnode TagNode of the HTML content
     * @param meta MetaData Object having the extracted values of the META tags
     */
    public void getMetadata(TagNode tagnode, MetaData meta) {
        String keywords = "", description = "", contype = "", charset = "";
        boolean kflag = false, dflag = false, coflag = false, chflag = false;
        String retKeys = "", kwords = "";
        Map<String, String> mapMeta = new HashMap<String, String>();
        try {
            TagNode[] keywordNode = tagnode.getElementsByName("meta", true);
            if (keywordNode.length > 0) {
                for (int k = 0; k < keywordNode.length; k++) {
                    String attrib = keywordNode[k].getName();       // Prints "meta"

                    Map<String, String> mapAV = keywordNode[k].getAttributes();
                    Iterator<String> keys = mapAV.keySet().iterator();
                    while (keys.hasNext()) {
                        String attb = mapAV.get(keys.next()).toLowerCase().trim();
                        String attbValue = (keys.hasNext()) ? mapAV.get(keys.next()) : " ";
                        mapMeta.put(attb, attbValue);
                    }

                    if (mapMeta.keySet().contains("content-type")) {
                        String conType = "";
                        /** keyname = "keywords" => content-type = group(1) & charset(2) = group(3); */
                        Pattern httpEquip = Pattern.compile("([a-z]*/[a-z]*);\\s*(charset)=\\s*([a-z][_\\-0-9a-z]*)", Pattern.CASE_INSENSITIVE);
                        Matcher metaMatcher = httpEquip.matcher(mapMeta.get("content-type"));
                        if (metaMatcher.find()) {
                            contype = metaMatcher.group(1);
                            charset = metaMatcher.group(3);
                        }
                    }

                    if (mapMeta.keySet().contains("keywords")) {
                        retKeys = mapMeta.get("keywords").trim();
                        keywords = TextUtils.ReplaceEscapeChars(TextUtils.ConvertHexToUnicode(retKeys)) + "";
                    }
                    if (mapMeta.keySet().contains("description")) {
                        retKeys = mapMeta.get("description").trim();
                        description = TextUtils.ReplaceEscapeChars(TextUtils.ConvertHexToUnicode(retKeys)) + "";
                    }
                    kwords = " ";
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR in Extracting: Meta Data");
            e.printStackTrace();
        }

        meta.setKeywords(keywords);
        meta.setDescription(description);
        meta.setContype(contype);
        meta.setCharset(charset);
    }

    /** Single method to filter anchored links and texts as well */
    public static void getAnchorData(TagNode tagnode, Vector<String> links, Vector<String> texts) {
        try {
            TagNode[] linkNodes = tagnode.getElementsByName("a", true);
            for (int m = 0; m < linkNodes.length; m++) {
                String anchorText = TextUtils.EmptySpaceRemover(linkNodes[m].getText().toString().replaceAll("&nbsp;", " "));
                String hyperlink = linkNodes[m].getAttributeByName("href");
                Set values = linkNodes[m].getAttributes().keySet();
                if (values.size() > 0) {
                    Iterator iter = values.iterator();
                    while (iter.hasNext()) {
                        String key = iter.next().toString();
                        String keyName = linkNodes[m].getAttributeByName(key);
                        if (key.equalsIgnoreCase("href") && anchorText.trim().length() > 1 && !hyperlink.trim().startsWith("javascript")) {
                            links.add(hyperlink.trim());
                            String anchor = TextUtils.ReplaceEscapeChars(TextUtils.ConvertHexToUnicode(anchorText.trim())).replaceAll("#", "");
                            texts.add(anchor);
                        } else {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR in Extracting: links");
        }
    }

    /** Core CMLified Content Filtering Method. It uses node level nose to text ratio
     * for eliminating the NOISE in the given html document.
     * Also this method removes the repeated sentences using two levels:
     *      1) sentence level repeated terms elimination
     *      2) phrase level repeated terms elimination (here phrase length is <n> = <3> terms
     */
    
    public String getCMLifiedContent(TagNode tagnode) {
        Set store = new HashSet();
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        
        try {
            /** Filter out standard tags like script, image, input boxes, buttons, etc */
            String[] tags = {"script", "noscript", "style", "link", "br", "option", "input", "label", "img", "head"};
            for (int i = 0; i < tags.length; i++) {
                TagNode[] tno = tagnode.getElementsByName(tags[i].trim(), true);
                for (int l = 0; l< tno.length; l++) {
                    tno[l].removeFromTree();
                }
            }
            
            /** Processing TABLE tags */
            TagNode[] tagNodes = tagnode.getElementsByName("td", true);
            for ( int i = 0; i < tagNodes.length; i++ ) {
                String linkText = getAnchorTexts(tagNodes[i]).trim();
                String onlyText = TextUtils.WipeOutSpecials(getTextExcludeLinks(tagNodes[i])).trim();

                if (linkText.length() < onlyText.length()/2.0) {
                    String text = tagNodes[i].getText().toString()+" ";
                    if (store.add(text)) {
                        if ( sb.toString().contains(text) ){
                            text = TextUtils.FindLargerCommonSequence(sb.toString(), text);
                            sb.setLength(0);
                        }
                        sb.append(" ").append(text).append(" ");
                    }
                }
                tagNodes[i].removeFromTree();
            }
            
            /** Processing DIV tags */
            TagNode[] divNodes = tagnode.getElementsByName("div", true);
            for ( int i = 0; i < divNodes.length; i++ ) {
                String linkText = getAnchorTexts(divNodes[i]).trim();
                String onlyText = TextUtils.WipeOutSpecials(getTextExcludeLinks(divNodes[i])).trim();

                if (linkText.length() < onlyText.length()/2.0) {
                    String text = divNodes[i].getText().toString();
                    if (store.add(text)) {
                        if ( sb.toString().contains(text) ) {
                            text = TextUtils.FindLargerCommonSequence(sb.toString(), text);
                            sb.setLength(0);
                        }
                        sb.append(" ").append(text);
                    }
                }
                divNodes[i].removeFromTree();
            }
            
            /** If no TABLE or DIV tag exists then extract the text */
            String rest = TextUtils.ConvertHexToUnicode(tagnode.getText().toString());
            sb.append(" ").append(rest);

            String hextoUTF = TextUtils.ConvertHexToUnicode(rest);
	    String bufText = TextUtils.ConvertHexToUnicode(sb.toString());
            String rcont = TextUtils.WipeOutSpecials(TextUtils.FindLargerCommonSequence(bufText, hextoUTF));
            rcont = TextUtils.RemoveNumsSpaces(rcont.replaceAll("[»]+", ""));
            sb.setLength(0);
            sb.append(rcont);
        } catch (Exception e) {
            System.out.println("ERROR in Extracting: content");
        }
        store.clear();

        return TextUtils.EmptySpaceRemover(sb.toString());
    }

    /** To filter texts which are anchored using hyperlink references */
    public String getAnchorTexts(TagNode tagnode) {
//        System.out.println("Tags SET: "+tagnode.getAllElementsList(true).toString());
        StringBuilder anchText = new StringBuilder();
        try {
            TagNode[] linkNodes = tagnode.getElementsByName("a", true);
            for (int m = 0; m < linkNodes.length; m++) {
                String anchorText = TextUtils.EmptySpaceRemover(linkNodes[m].getText().toString().replaceAll("&nbsp;", "")).trim();
                String anchorLink = linkNodes[m].getAttributeByName("href");

                Set values = linkNodes[m].getAttributes().keySet();
                if (values.size() > 0) {
                    Iterator iter = values.iterator();
                    while (iter.hasNext()) {
                        String key = iter.next().toString();
                        String keyName = linkNodes[m].getAttributeByName(key);
                        if (key.equalsIgnoreCase("href") && anchorText.trim().length() > 1 
                                && !anchorLink.trim().startsWith("javascript")) {
                            anchText.append(anchorText.trim()).append(" ");
//                            System.out.println("Tag: "+key+" - Text: "+anchorText+" - Value: "+keyName);
                        } else {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR in Extracting: anchor text");
        }

        return anchText.toString();
    }

    /** To Filter Text by excluding anchored text from the HTML tree */
    public String getTextExcludeLinks(TagNode tagnode) {
        StringBuffer text = null;
        TagNode[] linkNode = tagnode.getElementsByName("a", true);
        for (int k = 0; k < linkNode.length; k++) {
            linkNode[k].removeFromTree();
        }
        text = tagnode.getText();
        return text.toString().trim();
    }


    /** PageScore - to estimate the weight of a page using specific fields */
    private float PageScore(String url, String keywords, String title, String body) {
        float pagescore = 0.0f;
        String fullText = (TextUtils.WipeOutSpecials(url).trim() + " " + keywords + " " + title + " " + body).toLowerCase();
        body = TextUtils.GetUniqueTerms(body);
        StringTokenizer stps = new StringTokenizer(fullText.trim());
        if (!fullText.trim().isEmpty()) {
            StringTokenizer stqry = new StringTokenizer(fullText);
            while (stqry.hasMoreTokens()) {
                String term = stqry.nextToken().trim();
                if (url.contains(term))
                    pagescore += 0.04 * ( ( NoOfOccurances(url, term) > 0 ) ? 1 : 0 );
                if (title.contains(term))
                    pagescore += 0.03 * ( ( NoOfOccurances(title, term) > 0 ) ? 1: 0 );
                if (keywords.contains(term))
                    pagescore += 0.02 * ( ( NoOfOccurances(keywords, term) > 0 ) ? 1: 0 );
                if (body.contains(term))
                    pagescore += 0.01 * ( ( NoOfOccurances(body, term) > 0 ) ? 1: 0 );
            }
        }
        return pagescore;
    }

    /** Counts the Number of occurances of the given term in the given text */
    private static int NoOfOccurances(String text, String term) {
        return StringUtils.countMatches(text.toLowerCase(), term.toLowerCase());
    }

    /** TO get the contents of the file */
    public String getContents(File aFile) throws FileNotFoundException, IOException {
        StringBuilder contents = new StringBuilder();
        try {
            BufferedReader input =  new BufferedReader(new FileReader(aFile));
            try {
                String line = null;
                while (( line = input.readLine()) != null){
                    contents.append(line).append(" ");
                }
            }
            finally {
                input.close();
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return contents.toString();
    }

    /** Method to form a fully qualified URL */
    public String getOualifiedUrl(String baseUrl, String brokenLink) {
        String validUrl = "";
        try {
            URI u = new URI(baseUrl);
            URI cur = new URI(u.getScheme(), u.getUserInfo(), u.getHost(), u.getPort(), u.getPath(), u.getQuery(), u.getFragment());
            validUrl = cur.resolve(brokenLink).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return validUrl;
    }

    /** Extracts <numToken> number of tokens in the given text starting from the first token */
    private String getKTokens(String giveText, int numToken) {
        StringTokenizer stkr = new StringTokenizer(giveText);
        int tok = stkr.countTokens();
        int maxCh = (tok < numToken) ? tok : numToken;
        String toRet = "";
        if ( tok > numToken ) {
            int count = 0;
            while (stkr.hasMoreTokens()) {
                if ( count < numToken ) {
                    toRet += stkr.nextToken()+" ";
                    count++;
                }
                else {
                    break;
                }
            }
        }
        else
            toRet = giveText+" ";
        return toRet;
    }

    /** Extracts <offset> number of tokens in the given text by skipping first <numtoken> tokens */
    private String getKTokens(String giveText, int numToken, int offset) {
        StringTokenizer stkr = new StringTokenizer(giveText.trim());
        String interText = "", toRet = "";
        int totalTokens = stkr.countTokens();
        if (totalTokens > numToken) {
            interText = giveText.substring(getKTokens(giveText, numToken).length());
            if (new StringTokenizer(interText).countTokens() > offset) {
                toRet = getKTokens(interText, offset);
            } else {
                toRet = interText.trim();
            }
        }
        return toRet.trim();
    }

    /** Returns an integer = "The number of terms" in the given text */
    private int CountTokensInText(String text) {
        return ((text.trim().isEmpty()) ? 0 : new StringTokenizer(text).countTokens());
    }

    /** This method eliminates repeating Pharases of length <offset> */
    private String GetSentence(String text, int offset) {
        Set set = new HashSet();
        StringBuilder sb = new StringBuilder();
        int ctrOne = 0, ctrTwo = 0, count = 0;
        int numterms = CountTokensInText(text);
        count = (numterms - (offset-1));
        String one = ""; int k = 0;
        String term = getKTokens(text, 0, 1).trim();
        for (int i = 0; i < count ; i++) {
            one = getKTokens(text, i, offset).trim();
            term = getKTokens(one, one.indexOf(one), 1).trim();
            if (set.add(one)) {
//                System.out.println("Added!    "+i+" "+one);
                if (i == k) {
                    sb.append(term).append(" ");
                    k++;
                }
            } else {
//                System.out.println("Repeated!! ");
                k = i + offset;
            }
        }
        term = getKTokens(one, (offset-(offset-1)), offset-1);
        sb.append(term).append(" ");
        return sb.toString();
    }
    
    
   public String getEmphasisTexts(TagNode tagnode){
    	
    	StringBuilder sb = new StringBuilder();
    	
    	String[] headerTags={"h1","h2","h3","h4","h5","em","strong","b"};
    	for(String headerTag:headerTags){
    	  TagNode[] linkNodes = tagnode.getElementsByName(headerTag, true);
    	   for(TagNode node:linkNodes){
    		   String text = node.getText().toString();
    		  sb.append(text+" ");
    	   }
    	}
    
    	String headerTexts = TextUtils.WipeOutSpecials(sb.toString());
    	return headerTexts;
    	
    }
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usuage: ./bin/nutch cmlifier " + CmlNoiseRemover.class.getName() + " <html File>");
            System.exit(1);
        }

        String inputHtml = args[0].trim();
        String baseUrl = "";
        try {
            CmlNoiseRemover cnf = new CmlNoiseRemover();
            String content = new String();
            content = cnf.getContents(new File(inputHtml));
            TagNode tagnode = cnf.FilterNoisyContent(content);

            String title = "";
            if (cnf.isTagAvailable(tagnode, "title")) {
                title = cnf.getTitle(tagnode).trim();
                tagnode.findElementByName("title", true).removeFromTree();
            }
            System.out.println("TITLE: "+title);

            String keywords = "", description = "", contype = "", charset = "";
            if (cnf.isTagAvailable(tagnode, "meta")) {
                MetaData meta = new MetaData();
                cnf.getMetadata(tagnode, meta);
                System.out.println("KEYWORDS: "+meta.getKeywords());
                System.out.println("DESCRIPTION: "+meta.getDescription());
                System.out.println("CONTENT-TYPE: "+meta.getContype());
                System.out.println("CHARSET: "+meta.getCharset()+ "\n");
            }

            TagNode[] metaNode = tagnode.getElementsByName("meta", true);
            for (int k = 0; k < metaNode.length; k++) {
                metaNode[k].removeFromTree();
            }

            Vector<String> links = new Vector<String>();
            Vector<String> texts = new Vector<String>();
            CmlNoiseRemover.getAnchorData(tagnode, links, texts);
         //   System.out.println("Anchor TEXTs: "+links.toString()+" - "+links.size());
         //   System.out.println("HyperLINKs: "+texts.toString()+" - "+texts.size());
            
//            for (int i = 0; i < links.size(); i++) {
//                String url = links.get(i);
//                System.out.println( texts.get(i) + " - " + url);
//            }
            
            /** Cleaned TEXT using CMLified Content Filtering Method */
            String filteredText = cnf.getCMLifiedContent(tagnode).trim()+"";
            System.out.println( "\nCLEANED TEXT: " + filteredText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
