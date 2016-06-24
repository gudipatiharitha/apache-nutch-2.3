package org.apache.nutch.parse.cml;

// JDK import
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.avro.util.Utf8;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.nutch.crawl.CrawlDatum;
//import org.apache.nutch.crawl.CrawlDatum;
//import org.apache.nutch.crawl.Inlinks;
import org.apache.nutch.indexer.IndexingException;
import org.apache.nutch.indexer.IndexingFilter;
import org.apache.nutch.indexer.IndexingFilters;
import org.apache.nutch.indexer.NutchDocument;
import org.apache.nutch.parse.Parse;
import org.apache.nutch.parse.cml.MultiWordRecognizer;
import org.apache.nutch.parse.cml.NamedEntityRecognizer;
import org.apache.nutch.storage.WebPage;
import org.apache.nutch.storage.WebPage.Field;
import org.apache.nutch.util.Bytes;
import org.apache.nutch.util.TableUtil;
import org.iitkgp.nutch.scoring.content.EntityScore;
import org.iitkgp.nutch.scoring.content.EntityScoringWrapper;
import org.iitkgp.nutch.scoring.content.Utilities;

public class CmlIndexer implements IndexingFilter {
	public static final Log LOG = LogFactory.getLog(CmlIndexer.class.getName());
	private int MAX_TITLE_LENGTH;
	private Configuration conf;

	public CmlIndexer() {
		// System.out.println("IN CML Indexer");
	}
	@Override
	public NutchDocument filter(NutchDocument doc,String url,
			WebPage page) throws IndexingException {
		/*try {
		if( IndexingFilters.bUseUNL==true) {
			return doc;
		}
		}catch(NoSuchFieldError e) {
			if(getConf().get("SolrUrl") == null) {
				return doc;
			}
		}
		*/
		LOG.info("Inside: CML Indexing Filter ... Now !!");
		
		/*
		 * String pageUrl = getUTF8String(url.toString()) + "";
		String pageTitle = parse.getData().getTitle();
		System.out.println(pageTitle);
		String pageKeywords = parse.getData().getMeta("keywords") + "";
		System.out.println(pageKeywords);
		String pageDesc = parse.getData().getMeta("description") + "";
		System.out.println(pageDesc);
		String pageTopKwords = parse.getData().getMeta("topKwords") + "";
		System.out.println(pageTopKwords);
		String pageContent = parse.getData().getMeta("content") + "";
		System.out.println(pageContent);
		String pageLang = parse.getData().getMeta("lang") + "";
		System.out.println(pageLang);
		String pageDomain = parse.getData().getMeta("domain") + "";
		 */
		
		String pageUrl = getUTF8String(url) + "";
		String pageTitle = page.getMetadata().get(new Utf8("title"))!=null ? Bytes.toString(page.getMetadata().get(new Utf8("title")).array()) + "" : "";
		//String pageTitle = page.getTitle() != null ? page.getTitle().toString() : "";
		System.out.println(pageTitle);
		String pageKeywords = page.getMetadata().get(new Utf8("keywords"))!=null ? Bytes.toString(page.getMetadata().get(new Utf8("keywords")).array()) + "" : "";
		String pageDesc = page.getMetadata().get(new Utf8("description"))!=null ? Bytes.toString(page.getMetadata().get(new Utf8("description")).array()) + "" : "";	
		String pageTopKwords = page.getMetadata().get(new Utf8("topKwords"))!=null ? Bytes.toString(page.getMetadata().get(new Utf8("topKwords")).array()) + "" : "";	
        String pageContent = page.getMetadata().get(new Utf8("content"))!=null ? Bytes.toString(page.getMetadata().get(new Utf8("content")).array()) + "" : "";     
		String pageLang = page.getMetadata().get(new Utf8("lang"))!=null ? Bytes.toString(page.getMetadata().get(new Utf8("lang")).array()) + "" : "";	
		String pageDomain = page.getMetadata().get(new Utf8("domain"))!=null ? Bytes.toString(page.getMetadata().get(new Utf8("domain")).array()) + "" : "";
	
		String host = null;
		try {
			URL u = new URL(url.toString());
			host = u.getHost();
		} catch (MalformedURLException e) {
			LOG.info("ERROR in Indexer!!");
		}

		/*******************************************************************
		 * Call to MWE Identification from the page Content
		 * 
		 *******************************************************************/
		MultiWordRecognizer MWR = MultiWordRecognizer.getInstance();
		String[] MWEs = null;
		MWEs = MWR.recognizeMultiwordEntities(pageLang, conf, pageContent + " "
				+ pageTitle + " " + pageDesc);
		MWR = null;// help garbage collector

		/******************************************************************
		 * Call to NamedEntity Identification from the page Content
		 * 
		 ******************************************************************/
		NamedEntityRecognizer NER = NamedEntityRecognizer.getInstance();

		String[] NEs = null;
		NEs = NER.recognizeNamedEntities(pageLang, conf, pageContent + " "
				+ pageTitle + " " + pageDesc);

		NER = null;// help garbage collector

		/*******************************************************************
		 * Decode ill-Formed Urls
		 * 
		 *******************************************************************/
		LOG.info("multi-word done ... ");
		String decodedPagelUrl = "";
		decodedPagelUrl = Utilities.DecodeURL(pageUrl);

		/*******************************************************************
		 * Call to Content-Scoring module
		 * 
		 *******************************************************************/
		Map<CharSequence,CharSequence> inlinks = page.getInlinks();
		System.out.println(page.getScore());
		EntityScoringWrapper ESC = new EntityScoringWrapper(page,decodedPagelUrl, host, inlinks, NEs, MWEs);
		ArrayList<EntityScore> EntityScoreList = ESC.getEntityScoreList();
		StringBuilder SB = new StringBuilder();
		if (EntityScoreList != null) {

			// System.out.println("-------------------------------------------------------");
			// System.out.println("        URL : " + decodedPagelUrl +
			// "                     ");
			for (EntityScore ES : EntityScoreList) {
				if (ES != null) {
					// System.out .println(ES.getEntity() + " ==> " +
					// ES.getScore());
					SB.append(" # " + ES.getEntity() + "$" + ES.getScore()
							+ " # ");
				}
			}
			// System.out
			// .println("-------------------------------------------------------");
		}

		// Store entity scores
		if (SB != null) {
			System.out.println(SB.toString());
			doc.add("entityscores", SB.toString());
		}

		//Add host
		if(host!=null){
			doc.add("site",host);
		}
		
		// Add the url of the page
		if (decodedPagelUrl != null) {
			doc.add("url", decodedPagelUrl);
		} else {
			doc.add("url", pageUrl);
		}

		// Store inlinked anchor text if available
		String[] anchors = null;
		inlinks = page.getInlinks();
		if (inlinks.size()!=0) {
			try {
				ArrayList<String> anchors_list= new ArrayList<String>();
				for(CharSequence key: inlinks.keySet()){
					anchors_list.add(inlinks.get(key).toString());
				}
				anchors = anchors_list.toArray(new String[anchors_list.size()]);
			} catch (Exception e) {
				System.out
						.println("Exception encountered while extracting inlinks anchor texts in cml indexer !!!");
			}
			if (anchors != null) {
				for (String anchor : anchors) {
					doc.add("anchor", anchor);
				}
			}
		}

		// Storing Named Entities if available
		if (NEs != null) {
			for (int i = 0; i < NEs.length; i++) {
				String NE = (NEs[i].split("/")[0].trim());
				doc.add("NER", NE);
			}
		}
		// Storing Multiwords if available
		if (MWEs != null) {
			for (int i = 0; i < MWEs.length; i++) {
				String MWE = MWEs[i].trim();
				doc.add("MWE", MWE);
			}
		}

		doc.add("title", pageTitle);
		doc.add("content", pageContent);
		doc.add("keywords", pageKeywords);
		doc.add("description", pageDesc);
		doc.add("topKwords", pageTopKwords);
		doc.add("lang", pageLang);
		doc.add("domain", pageDomain);
		return doc;
	}

	public float GetFloatFromString(String str) {
		float toRet = (NumberUtils.isNumber(str)) ? ((!str.trim().isEmpty()) ? new Float(
				str) : 0.0f)
				: 0.0f;
		return ((toRet < 0.0f) ? 0.0f : toRet);
	}

	/** Get UTF - 8 String from the Given Text */
	public String getUTF8String(String myText) {
		byte[] byteArray = null;
		try {
			byteArray = myText.getBytes("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new String(byteArray);
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
		this.MAX_TITLE_LENGTH = conf.getInt("indexer.max.title.length", 100);
	}

	public Configuration getConf() {
		return this.conf;
	}

	@Override
	public Collection<Field> getFields() {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	
}
