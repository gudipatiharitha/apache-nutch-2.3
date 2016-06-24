package net.iiit.siel.analysis.domain;

// Commons Logging imports
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// Nutch imports
import org.apache.nutch.metadata.Metadata;
import org.apache.nutch.parse.HTMLMetaTags;
import org.apache.nutch.parse.Parse;
//import org.apache.nutch.parse.HtmlParseFilter;
//import org.apache.nutch.parse.ParseResult;
import org.apache.nutch.protocol.Content;

// Hadoop imports
import org.apache.hadoop.conf.Configuration;

// DOM imports
import org.w3c.dom.DocumentFragment;
//Additional
import org.apache.avro.util.Utf8;
import java.nio.ByteBuffer;
import org.apache.nutch.storage.WebPage.Field;
import java.util.*;
import org.apache.nutch.storage.WebPage;
import org.apache.nutch.parse.ParseFilter;


public class DomainParserFilter implements ParseFilter {
  
	public static final Log LOG = LogFactory.getLog(DomainParserFilter.class);
	private Configuration conf;

	//private Configuration conf;
  	private static final Collection<WebPage.Field> FIELDS = new HashSet<WebPage.Field>();
	public Parse filter(String url, WebPage page, Parse parse, HTMLMetaTags metaTags,
      DocumentFragment doc){
		return parse;
	}
 
	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public Configuration getConf() {
		return this.conf;
	}
	public Collection<Field> getFields() {
    	return FIELDS;
  	}
}
