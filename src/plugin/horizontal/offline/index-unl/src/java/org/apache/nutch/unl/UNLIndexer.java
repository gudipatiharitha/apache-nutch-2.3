package org.apache.nutch.unl;

// JDK import
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Hashtable;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.nutch.analysis.unl.ta.Integrated.Integrated;
import org.apache.nutch.crawl.CrawlDatum;
//import org.apache.nutch.crawl.Inlinks;
import org.apache.nutch.indexer.IndexingException;
import org.apache.nutch.indexer.IndexingFilter;
import org.apache.nutch.indexer.IndexingFilters;
import org.apache.nutch.indexer.NutchDocument;
import org.apache.nutch.parse.Parse;
import org.apache.nutch.storage.WebPage;
import org.apache.nutch.storage.WebPage.Field;

public class UNLIndexer implements IndexingFilter {
	public static final Log LOG = LogFactory.getLog(UNLIndexer.class.getName());
	private int MAX_TITLE_LENGTH;
	private Configuration conf;
	private Hashtable<String, String> URLtoRecno = new Hashtable<String, String>();
	private Hashtable<String, String> RecnotoURL = new Hashtable<String, String>();
	private int documentId;

	public UNLIndexer() {
	}
	
	@Override
	public NutchDocument filter(NutchDocument doc, String url, WebPage page)
			throws IndexingException {
		System.out.println("inside unl indexer ");
		return doc;
	}
	
	@Override
	public Collection<Field> getFields() {
		// TODO Auto-generated method stub
		return null;
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
	
}
