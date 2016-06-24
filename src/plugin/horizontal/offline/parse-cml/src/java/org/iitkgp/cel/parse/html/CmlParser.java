/**
 * File        :   CmlParser.java
 * Description :   Parses raw HTML file and gives parseText / parseData
 * Project     :   CLIA (Cross Lingual Information Access) System
 * Author      :   IIT Kharagpur(Dr Rajendra)
 * Modified    :   30 July 2011 05:30 hrs
 */

package org.iitkgp.cel.parse.html;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.*;
import java.util.regex.*;
import java.util.*;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;














// By IIIT-H: for language and domain identification
import net.iiit.siel.analysis.domain.Domain;
import net.iiit.siel.analysis.domain.DomainIdentifier;
import net.iiit.siel.analysis.domain.UnsupportedDomainException;
import net.iiit.siel.analysis.lang.Language;
import net.iiit.siel.analysis.lang.LanguageIdentifier;
import net.iiit.siel.analysis.lang.UnsupportedLanguageException;

import org.cyberneko.html.parsers.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import org.apache.html.dom.*;
import org.apache.avro.util.Utf8;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.apache.nutch.crawl.CrawlDatum;
import org.apache.nutch.metadata.Metadata;
import org.apache.nutch.metadata.Nutch;
import org.apache.nutch.net.protocols.Response;
import org.apache.nutch.protocol.Content;
import org.apache.hadoop.conf.*;
import org.apache.nutch.parse.*;
import org.apache.nutch.storage.ParseStatus;
import org.apache.nutch.storage.WebPage;
import org.apache.nutch.storage.WebPage.Field;
import org.apache.nutch.util.*;
import org.apache.commons.lang.StringUtils;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.iitkgp.cel.parse.html.CmlNoiseRemover;
import org.iitkgp.cel.parse.html.MetaData;
import org.iitkgp.cel.parse.html.FontTranscoderIL;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import de.l3s.boilerpipe.BoilerpipeDocumentSource;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.BoilerpipeHTMLParser;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLFetcher;

public class CmlParser implements Parser {
	public static final Logger LOG = LoggerFactory
			.getLogger("org.iitkgp.cel.parse.html");
	// I used 1000 bytes at first, but found that some documents have meta tag
	// well past the first 1000 bytes.

	private static final int CHUNK_SIZE = 2000;
	private static Pattern metaPattern = Pattern.compile(
			"<meta\\s+([^>]*http-equiv=\"?content-type\"?[^>]*)>",
			Pattern.CASE_INSENSITIVE);
	private static Pattern charsetPattern = Pattern.compile(
			"charset=\\s*([a-z][_\\-0-9a-z]*)", Pattern.CASE_INSENSITIVE);
	private static Collection<WebPage.Field> FIELDS = new HashSet<WebPage.Field>();

	static {
		FIELDS.add(WebPage.Field.BASE_URL);
	}
	private String parserImpl;

	public String fontnames = "";
	public HashMap fontfamilies = new HashMap();
	public String ftResourceDir;

	private static String sniffCharacterEncoding(byte[] content) {
		int length = content.length < CHUNK_SIZE ? content.length : CHUNK_SIZE;
		String str = new String(content, 0, 0, length);

		Matcher metaMatcher = metaPattern.matcher(str);
		String encoding = null;
		if (metaMatcher.find()) {
			Matcher charsetMatcher = charsetPattern.matcher(metaMatcher
					.group(1));
			if (charsetMatcher.find())
				encoding = new String(charsetMatcher.group(1));
			System.out.println("ENCODING: " + encoding);
		}

		return encoding;
	}

	private String defaultCharEncoding;
	private Configuration conf;
	private DOMContentUtils utils;
	private ParseFilters htmlParseFilters;
	private String cachingPolicy;
	
	public Parse getParse(String url,WebPage page){
		System.out.println("Parse-cml started.................");
		String[] args = {};
		    
		//BoilerpipeTextExtraction.main(args);
		long start, end;
		HTMLMetaTags metaTags = new HTMLMetaTags();

		//String url = content.getUrl().trim();
		// System.out.println(url + "\n" + text);
		
		String htmlContent = Bytes.toString(page.getContent().array());
		//System.out.println("...........");
		//System.out.println(htmlContent);
		if (htmlContent.contains("<htm")) {
			htmlContent = htmlContent.substring(htmlContent.indexOf("<htm"));
		}
		// else {
		// htmlContent =
		// "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head><body></body></html>";
		// if (LOG.isTraceEnabled()) {
		// LOG.trace(url + "[No HTML tags] (parse-cml)");
		// }
		// }

		start = System.currentTimeMillis();
		/** Call to the Font Transcoder - CDAC - Pune */
		// Configuration conf = NutchConfiguration.create();
		// TODO Wikipedia crawl change
		FontTranscoderIL ft = new FontTranscoderIL(conf);
		String encodedContent = htmlContent;
		try {
			encodedContent = ft.getContentInUTF(htmlContent);
		} catch (Exception e) {
			LOG.warn("Error in Font Transcoder");
		}
		// String encodedContent=htmlContent;
		// System.out.print( ") - " );
		end = System.currentTimeMillis();
		printTime(start, end);

		URL base = null;
		try {
			base = new URL(page.getBaseUrl().toString());
			System.out.println(".................");
			System.out.println(base.toString());
		} catch (MalformedURLException e) {
			//return new ParseStatus(e).getEmptyParseResult(base.toExternalForm(), getConf());
			return ParseStatusUtils.getEmptyParse(e, getConf());
		}

		String text = "", title = "", taggedText = "";
		String filteredText = "", keywords = "", description = "", contype = "", charset = "";
		String taggedPageSegments="";
		String focusedContent="";
		String emphasisText="";
		
		Outlink[] outlinks = new Outlink[0];
		Metadata metadata = new Metadata();

		// parse the content
		DocumentFragment root;
		try {
			byte[] contentInOctets = page.getContent().array();
			
			
			InputSource input = new InputSource(new ByteArrayInputStream(
					contentInOctets));
			InputSource input_for_bp = new InputSource(new ByteArrayInputStream(
					contentInOctets));
			System.out.println(".................");
			System.out.println(input.toString());
			
			
			//String contentType = content.getMetadata().get(Response.CONTENT_TYPE);
			String contentType = page.getContentType().toString();
			String encoding = EncodingDetector
					.parseCharacterEncoding(contentType);
			if ((encoding != null) && !("".equals(encoding))) {
				metadata.set(Metadata.ORIGINAL_CHAR_ENCODING, encoding);
				if ((encoding = EncodingDetector.resolveEncodingAlias(encoding)) != null) {
					metadata.set(Metadata.CHAR_ENCODING_FOR_CONVERSION,
							encoding);
					if (LOG.isTraceEnabled()) {
						LOG.trace(base + ": setting encoding to " + encoding);
					}
				}
			}

			// sniff out 'charset' value from the beginning of a document
			if ((encoding == null) || ("".equals(encoding))) {
				encoding = sniffCharacterEncoding(contentInOctets);
				if (encoding != null) {
					metadata.set(Metadata.ORIGINAL_CHAR_ENCODING, encoding);
					if ((encoding = EncodingDetector
							.resolveEncodingAlias(encoding)) != null) {
						metadata.set(Metadata.CHAR_ENCODING_FOR_CONVERSION,
								encoding);
						if (LOG.isTraceEnabled()) {
							LOG.trace(base + ": setting encoding to "
									+ encoding);
						}
					}
				}
			}

			if (encoding == null) {
				encoding = defaultCharEncoding;
				metadata.set(Metadata.CHAR_ENCODING_FOR_CONVERSION,
						defaultCharEncoding);
				if (LOG.isTraceEnabled()) {
					LOG.trace(base + ": falling back to " + defaultCharEncoding);
				}
			}
			input.setEncoding(encoding);
			input_for_bp.setEncoding(encoding);

			if (LOG.isTraceEnabled()) {
				LOG.trace("Parsing...");
			}
			try{

				//BoilerpipeSAXInput boilerPipeInput = new BoilerpipeSAXInput(input);	
				//System.out.println(boilerPipeInput.getTextDocument().getTitle());
				//TextDocument boilerTextDocument = boilerPipeInput.getTextDocument();
				//ExtractSegments extractSegments = new ExtractSegments(boilerTextDocument);
				//focusedContent=extractSegments.getFocusedText();
				//taggedPageSegments=extractSegments.getTaggedPageSegments();
				
				//new code
				BoilerpipeSAXInput boilerPipeInput = new BoilerpipeSAXInput(input);	
				TextDocument boilerTextDocument=null;
				try{
					final HTMLDocument htmlDoc = HTMLFetcher.fetch(new URL(url));
					System.out.println(htmlDoc.getData().toString());
				    boilerTextDocument = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
				}
				catch(Exception e){
					
				}
			
				ExtractSegments extractSegments = new ExtractSegments(boilerTextDocument);
				focusedContent=extractSegments.getFocusedText();
				taggedPageSegments=extractSegments.getTaggedPageSegments();
			}
			catch(Exception e){
				LOG.info("ERROR due to BOILERPIPE!!!!");
				//e.printStackTrace(LogUtil.getWarnStream(LOG));
				//return new ParseStatus(e).getEmptyParseResult(base.toExternalForm(), getConf());
			}
			
			
			
			root = parse(input_for_bp);
			
			
		} catch (IOException e) {
			//return new ParseStatus(e).getEmptyParseResult(base.toExternalForm(), getConf());
			return ParseStatusUtils.getEmptyParse(e, getConf());
		} catch (DOMException e) {
			//return new ParseStatus(e).getEmptyParseResult(base.toExternalForm(), getConf());
			return ParseStatusUtils.getEmptyParse(e, getConf());
		} catch (SAXException e) {
			//return new ParseStatus(e).getEmptyParseResult(base.toExternalForm(), getConf());
			return ParseStatusUtils.getEmptyParse(e, getConf());
		} catch (Exception e) {
			LOG.warn(e.getStackTrace().toString());
			//return new ParseStatus(e).getEmptyParseResult(base.toExternalForm(), getConf());
			return ParseStatusUtils.getEmptyParse(e, getConf());
		}

		// get meta directives
		HTMLMetaProcessor.getMetaTags(metaTags, root, base);
		if (LOG.isTraceEnabled()) {
			LOG.trace("Meta tags for " + base + ": " + metaTags.toString());
		}

		if (!metaTags.getNoFollow()) { // okay to follow links
			ArrayList l = new ArrayList(); // extract outlinks
			URL baseTag = utils.getBase(root);
			if (LOG.isTraceEnabled()) {
				LOG.trace("Getting links...");
			}
			utils.getOutlinks(baseTag != null ? baseTag : base, l, root);
			outlinks = (Outlink[]) l.toArray(new Outlink[l.size()]);
			System.out.println("found " + outlinks.length + " outlinks in "+ url);
			
			/*
			 * for(int i=0;i<outlinks.length;i++){	
				String tourl = outlinks[i].getToUrl();
				String anchor = outlinks[i].getAnchor();
				String modified_tourl="";
				int l1 = tourl.length();
				int x = tourl.indexOf("https");
				if(x<0){
					modified_tourl=tourl;
				}
				else{
					for(int j=0;j<=x+3;j++) modified_tourl += tourl.charAt(j);
					for(int j=x+5;j<l1;j++) modified_tourl += tourl.charAt(j);
				}
				
				Outlink temp=null;
				try {
					temp = new Outlink(modified_tourl , anchor);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(temp.toString());
				outlinks[i] = temp;
				System.out.println(outlinks[i].toString());
				
			}
			 */
			
			//new code
			Outlink[] outlinks2 = new Outlink[outlinks.length];
			for(int i=0;i<outlinks.length;i++){	
				String tourl = outlinks[i].getToUrl();
				String anchor = outlinks[i].getAnchor();
				String modified_tourl="";
				int l1 = tourl.length();
				int x = tourl.indexOf("https");
				if(x<0){
					modified_tourl=tourl;
				}
				else{
					for(int j=0;j<=x+3;j++) modified_tourl += tourl.charAt(j);
					for(int j=x+5;j<l1;j++) modified_tourl += tourl.charAt(j);
				}
				
				Outlink temp=null;
				try {
					temp = new Outlink(modified_tourl , anchor);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				outlinks2[i] = temp;
			}

		}

		ParseStatus status = ParseStatus.newBuilder().build();
		status.setMajorCode((int)ParseStatusCodes.SUCCESS);
		if (metaTags.getRefresh()) {
			status.setMinorCode((int)ParseStatusCodes.SUCCESS_REDIRECT);
			//status.setArgs(new String[] { metaTags.getRefreshHref()   .toString(),
			//		Integer.toString(metaTags.getRefreshTime()) });
			List<CharSequence> args_list = new ArrayList<CharSequence>();
			args_list.add(metaTags.getRefreshHref().toString());
			args_list.add(Integer.toString(metaTags.getRefreshTime()));
			status.setArgs(args_list);		
		}

		/**
		 * Content Extraction using CML Noise Remover - IIT Kharagpur
		 */
		try {
			CmlNoiseRemover cnf = new CmlNoiseRemover();
			TagNode tagnode = cnf.FilterNoisyContent(encodedContent);
			emphasisText = cnf.getEmphasisTexts(tagnode);
			//System.out.println(" EM TEXT : " + emphasisText);

			if (cnf.isTagAvailable(tagnode, "title")) {
				title = cnf.getTitle(tagnode).trim();
				tagnode.findElementByName("title", true).removeFromTree();
			}
			// System.out.println("TITLE (parse-cml): "+title);

			if (cnf.isTagAvailable(tagnode, "meta")) {
				MetaData meta = new MetaData();
				cnf.getMetadata(tagnode, meta);
				keywords = meta.getKeywords();
				description = meta.getDescription();
				charset = meta.getCharset();
				// System.out.println("DESCRIPTION (parse-cml): "+meta.getDescription());
			}

			TagNode[] metaNode = tagnode.getElementsByName("meta", true);
			for (int k = 0; k < metaNode.length; k++) {
				metaNode[k].removeFromTree();
			}

			// Vector<String> links = new Vector<String>();
			// Vector<String> texts = new Vector<String>();
			// cnf.getAnchorData(tagnode, links, texts);
			// System.out.println("TEXTs: "+links.toString()+" - "+links.size());
			// System.out.println("LINKs: "+texts.toString()+" - "+texts.size());

			/** Filtered Page Content */
			filteredText = cnf.getCMLifiedContent(tagnode).trim() + "";
			// System.out.println("CLEANED TEXT (parse-cml): "+filteredText);

			text = "<t>" + title + "</t><d>" + description + "</d><k>"
					+ keywords + "</k><c>" + filteredText + "</c>" +"<fc>"
					+focusedContent+"</fc>"
					+"<segments>"+taggedPageSegments+"</segments>";
			taggedText=text;

		} catch (Exception e) {
			e.printStackTrace();
		}

		//ParseData parseData = new ParseData(status, title, outlinks,content.getMetadata(), metadata);
		// parseData.setConf(this.conf);

		//Parse parse = new ParseImpl(text, parseData);
		//ParseResult parseResult = ParseResult.createParseResult(
		//		base.toExternalForm(), parse);
		// run filters on parse
		//parseResult = this.htmlParseFilters.filter(content, parseResult,metaTags, root);
		Parse parse = new Parse(text, title, outlinks, status);
	    parse = htmlParseFilters.filter(url, page, parse, metaTags, root);
	    if (metaTags.getNoCache()) { // not okay to cache
	        page.getMetadata().put(new Utf8(Nutch.CACHING_FORBIDDEN_KEY),ByteBuffer.wrap(Bytes.toBytes(cachingPolicy)));
	    } 
		/*if (metaTags.getNoCache()) { // not okay to cache
			parseResult.get(base.toExternalForm()).getData().getParseMeta()
					.set(Nutch.CACHING_FORBIDDEN_KEY, cachingPolicy);

		}*/

		String lang = null, domainStr = "misc";
		// String lang = "mr", domainStr = "misc";
		start = System.currentTimeMillis();
		System.out.print("\n" + url + " - Lang & Domain Identifier (");
		// TODO Wikipedia Crawl change (comment the below block)
		try {
			/* Call to the Language Identifier - IIIT Hyd */
			LanguageIdentifier indianLangIdentifier = LanguageIdentifier
					.getInstance(conf);
			Language language = indianLangIdentifier.getLanguage(url,
					filteredText);
			lang = language.toString().toLowerCase();

			/* Call to the Domain Identifier - IIIT Hyd */
			DomainIdentifier domainIdentifier = DomainIdentifier.getInstance(conf);
			Domain domain = domainIdentifier.getDomain(url, filteredText,
					language);
			domainStr = domain.toString().toLowerCase();
			// System.out.println("Identified Lang (domain): " + lang + "(" +
			// domainStr + ")");
		} catch (UnsupportedLanguageException e) {
			lang = "un";
			// if (LOG.isTraceEnabled()) {
			// LOG.trace("Unsupported Language by LANGUAGE IDENTIFIER(IIIT Hyd)");
			// }
		} catch (UnsupportedDomainException e) {
			// domainStr = "misc";
		} catch (Exception e) {
			e.printStackTrace();
		}
		// if (LOG.isTraceEnabled()) {
		// LOG.trace("Unsupported Domain by DOMAIN IDENTIFIER(IIIT Hyd)"); }
		// }
		end = System.currentTimeMillis();
		printTime(start, end);
		
		/*
		 * parseResult.get(base.toExternalForm()).getData().getParseMeta()
				.set("url", url);
		parseResult.get(base.toExternalForm()).getData().getParseMeta()
		.set("emphasisText", emphasisText);
		parseResult.get(base.toExternalForm()).getData().getParseMeta()
				.set("title", title);
		parseResult.get(base.toExternalForm()).getData().getParseMeta()
				.set("description", description);
		parseResult.get(base.toExternalForm()).getData().getParseMeta()
				.set("keywords", keywords);
		parseResult.get(base.toExternalForm()).getData().getParseMeta()
				.set("content", filteredText);
		parseResult.get(base.toExternalForm()).getData().getParseMeta()
		        .set("focusedContent", focusedContent);
		parseResult.get(base.toExternalForm()).getData().getParseMeta()
				.set("taggedContent", taggedText);

		int topK = 30;
		parseResult.get(base.toExternalForm()).getData().getParseMeta()
				.set("topKwords", TextUtils.getTopKterms(filteredText, topK));
		parseResult.get(base.toExternalForm()).getData().getParseMeta()
				.set("lang", lang);
		parseResult.get(base.toExternalForm()).getData().getParseMeta()
				.set("domain", domainStr);
		 */
		
		page.getMetadata().put(new Utf8("url"),ByteBuffer.wrap(Bytes.toBytes(url)));
		page.getMetadata().put(new Utf8("emphasisText"),ByteBuffer.wrap(Bytes.toBytes(emphasisText)));
		page.getMetadata().put(new Utf8("title"),ByteBuffer.wrap(Bytes.toBytes(title)));
		page.getMetadata().put(new Utf8("description"),ByteBuffer.wrap(Bytes.toBytes(description)));
		page.getMetadata().put(new Utf8("keywords"),ByteBuffer.wrap(Bytes.toBytes(keywords)));
		page.getMetadata().put(new Utf8("content"),ByteBuffer.wrap(Bytes.toBytes(filteredText)));
		page.getMetadata().put(new Utf8("focusedContent"),ByteBuffer.wrap(Bytes.toBytes(focusedContent)));
		page.getMetadata().put(new Utf8("taggedContent"),ByteBuffer.wrap(Bytes.toBytes(taggedText)));

		int topK = 30;
		page.getMetadata().put(new Utf8("topKwords"),ByteBuffer.wrap(Bytes.toBytes(TextUtils.getTopKterms(filteredText, topK))));
		page.getMetadata().put(new Utf8("lang"),ByteBuffer.wrap(Bytes.toBytes(lang)));
		page.getMetadata().put(new Utf8("domain"),ByteBuffer.wrap(Bytes.toBytes(domainStr)));

		/** Following lines are for testing purpose - By IIT Kharagpur */
		//LOG.info("DATA (parse-cml): " + parse.getData());

		//LOG.info("TEXT (parse-cml): " + parse.getText());
		return parse;
	}

	private String get_url(String tourl) {
		// TODO Auto-generated method stub
		return null;
	}

	/** Prints the TIME difference */
	public static void printTime(long starttime, long endtime) {
		long totaltime = (endtime - starttime);
		System.out.print(totaltime + " - [" + (totaltime / 1000) / 60 / 60
				+ ":" + (totaltime / 1000) / 60 % 60 + ":" + (totaltime / 1000)
				% 60 % 60 + "]");
	}

	private DocumentFragment parse(InputSource input) throws Exception {
		if (parserImpl.equalsIgnoreCase("tagsoup"))
			return parseTagSoup(input);
		else
			return parseNeko(input);
	}

	private DocumentFragment parseTagSoup(InputSource input) throws Exception {
		HTMLDocumentImpl doc = new HTMLDocumentImpl();
		DocumentFragment frag = doc.createDocumentFragment();
		DOMBuilder builder = new DOMBuilder(doc, frag);
		org.ccil.cowan.tagsoup.Parser reader = new org.ccil.cowan.tagsoup.Parser();
		reader.setContentHandler(builder);
		reader.setFeature(reader.ignoreBogonsFeature, true);
		reader.setFeature(reader.bogonsEmptyFeature, false);
		reader.setProperty("http://xml.org/sax/properties/lexical-handler",
				builder);
		reader.parse(input);
		return frag;
	}

	private DocumentFragment parseNeko(InputSource input) throws Exception {
		DOMFragmentParser parser = new DOMFragmentParser();
		// some plugins, e.g., creativecommons, need to examine html comments

		try {
			parser.setFeature(
					"http://apache.org/xml/features/include-comments", true);
			parser.setFeature("http://apache.org/xml/features/augmentations",
					true);
			parser.setFeature(
					"http://cyberneko.org/html/features/balance-tags/ignore-outside-content",
					false);
			parser.setFeature(
					"http://cyberneko.org/html/features/balance-tags/document-fragment",
					true);
			parser.setFeature(
					"http://cyberneko.org/html/features/report-errors", true);
		} catch (SAXException e) {
		}

		// convert Document to DocumentFragment
		HTMLDocumentImpl doc = new HTMLDocumentImpl();
		doc.setErrorChecking(false);
		DocumentFragment res = doc.createDocumentFragment();
		DocumentFragment frag = doc.createDocumentFragment();
		parser.parse(input, frag);
		res.appendChild(frag);

		try {
			while (true) {
				frag = doc.createDocumentFragment();
				parser.parse(input, frag);
				if (!frag.hasChildNodes())
					break;
				if (LOG.isInfoEnabled()) {
					LOG.info(" - new frag, " + frag.getChildNodes().getLength()
							+ " nodes.");
				}
				res.appendChild(frag);
			}
		} catch (Exception x) {
			LOG.error("Failed with the following Exception: ", x);
		}
		;
		return res;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
		this.htmlParseFilters = new ParseFilters(getConf());
		this.parserImpl = getConf().get("parser.html.impl", "neko");
		this.defaultCharEncoding = getConf().get(
				"parser.character.encoding.default", "windows-1252");
		this.utils = new DOMContentUtils(conf);
		this.cachingPolicy = getConf().get("parser.caching.forbidden.policy",
				Nutch.CACHING_FORBIDDEN_CONTENT);

		this.fontnames = getConf()
				.get("parser.fontnames",
						"AAADurga::AAADurga, AAADurgax::AAADurgax, AAADurgaxx::AAADurgaxx, AabpBengali::AabpBengali, AabpBengalix::AabpBengalix, AabpBengalixx::AabpBengalixx, Amudham::Amudham, Anu::Anu, BEJA::BEJA, Bhaskar::Bhaskar, BWRevathi::BWRevathi, Chanakya::Chanakya, DrChatrik::DrChatrik, DV_TTGanesh::DV_TTGanesh, DVW_TTGanesh::DVW_TTGanesh, Eenadu::Eenadu, ElangoTmlPanchali::ElangoTmlPanchali, EPatrika::EPatrika, Gopika::Gopika, Hemalatha::Hemalatha, HTChanakya::HTChanakya, Jagran::Jagran, Kairali::Kairali, Kalakaumudi::Kalakaumudi, Karthika::Karthika, Kiran::Kiran, Krutidev::Krutidev, Kumudam::Kumudam, Manjusha::Manjusha, Manorama::Manorama, Matweb::Matweb, MillenniumVarun::MillenniumVarun, MillenniumVarunWeb::MillenniumVarunWeb, Mithi::Mithi, Nandi::Nandi, Panchami::Panchami,  Pudhari::Pudhari, Revathi::Revathi, Shivaji01::Shivaji, Shivaji::Shivaji, Shree_0908W::Shree_0908W, Shree_Dev_0714::Shree_Dev_0714, Shree_Kan_0850::Shree_Kan_0850, SHREE_MAL_0501::SHREE_MAL_0501, ShreeTam0802::ShreeTam0802, Shree_Tel_0900::Shree_Tel_0900, Shree_Tel_0902::Shree_Tel_0902, Shusha::Shusha, Subak::Subak, SuriTln::SuriTln, TAB::TAB, TAM_LFS_Kamban::TAM_LFS_Kamban, TAM::TAM, TCSMith::TCSMith, TeluguFont::TeluguFont, TeluguLipi::TeluguLipi, Thoolika::Thoolika, Tikkana::Tikkana, TL_Hemalatha::TL_Hemalatha, TSCII_Fonts::TSCII_Fonts, TSCII::TSCII, Ujala::Ujala, Vaartha::Vaartha, VaarthaText::VaarthaText, Vakil01::Vakil01, Vakil::Vakil, VikaasWebFont::VikaasWebFont, Vikatan::Vikatan")
				+ ",";
		this.ftResourceDir = conf.get("Font_Transcoder_Dir");

		Pattern p = Pattern.compile("([^,:]+)::([^,:]+),");
		Matcher m = p.matcher(this.fontnames);
		while (m.find()) {
			this.fontfamilies.put(m.group(2).toLowerCase().trim(), m.group(1).toLowerCase().trim());
		}
	}

	public Configuration getConf() {
		return this.conf;

	}

	public static void main(String[] args) throws Exception {
		// LOG.setLevel(Level.FINE);
		BoilerpipeTextExtraction.main(args);
		String name = args[0];
		String url = "file:" + name;
		File file = new File(name);
		byte[] bytes = new byte[(int) file.length()];
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		in.readFully(bytes);

		Configuration conf = NutchConfiguration.create();
		CmlParser parser = new CmlParser();
		parser.setConf(conf);
		WebPage page = WebPage.newBuilder().build();
		page.setBaseUrl(new Utf8(url));
		page.setContent(ByteBuffer.wrap(bytes));
		page.setContentType(new Utf8("text/html"));
		Parse parse = parser.getParse(url, page);
		//ParseResult parse = parser.getParse(new Content(url, url, bytes,"text/html", new Metadata(), conf));

	}

	@Override
	public Collection<Field> getFields() {
		// TODO Auto-generated method stub
		return FIELDS;
	}

	
	
}
