/**
 * 
 */
package iitb.cfilt.parse.wiki;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.iiit.siel.analysis.domain.Domain;
import net.iiit.siel.analysis.domain.DomainIdentifier;
import net.iiit.siel.analysis.domain.UnsupportedDomainException;
import net.iiit.siel.analysis.lang.Language;
import net.iiit.siel.analysis.lang.LanguageIdentifier;
import net.iiit.siel.analysis.lang.UnsupportedLanguageException;

import org.apache.hadoop.conf.Configuration;
import org.apache.html.dom.HTMLDocumentImpl;
import org.apache.nutch.metadata.Metadata;
import org.apache.nutch.metadata.Nutch;
import org.apache.nutch.net.protocols.Response;
import org.apache.nutch.parse.HTMLMetaTags;
//import org.apache.nutch.parse.HtmlParseFilters;
import org.apache.nutch.parse.Outlink;
import org.apache.nutch.parse.Parse;
import org.apache.nutch.parse.Parser;
//import org.apache.nutch.parse.ParseData;
//import org.apache.nutch.parse.ParseImpl;
//import org.apache.nutch.parse.ParseResult;
//import org.apache.nutch.parse.ParseStatus;
import org.apache.nutch.protocol.Content;
import org.apache.nutch.storage.WebPage;
import org.apache.nutch.storage.WebPage.Field;
import org.apache.nutch.util.EncodingDetector;
//import org.apache.nutch.util.LogUtil;
import org.apache.nutch.util.NutchConfiguration;
import org.cyberneko.html.parsers.DOMFragmentParser;
import org.htmlcleaner.TagNode;
import org.iitkgp.cel.parse.html.CmlNoiseRemover;
import org.iitkgp.cel.parse.html.CmlParser;
import org.iitkgp.cel.parse.html.DOMBuilder;
import org.iitkgp.cel.parse.html.DOMContentUtils;
import org.iitkgp.cel.parse.html.ExtractSegments;
import org.iitkgp.cel.parse.html.FontTranscoderIL;
import org.iitkgp.cel.parse.html.HTMLMetaProcessor;
import org.iitkgp.cel.parse.html.MetaData;
import org.iitkgp.cel.parse.html.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentFragment;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;

/**
 * @author arjun
 *
 */
public class WikiParser implements Parser{

	public static final Logger LOG = LoggerFactory
			.getLogger("iitb.cfilt.parse.wiki");
	
	public static Configuration conf; 
	
	@Override
	public Parse getParse(String url, WebPage page) {
		// TODO Auto-generated method stub
		return null;
	}
	/** Prints the TIME difference */
	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public Configuration getConf() {
		return this.conf;
	}

	public static void main(String[] args) throws Exception {
		
	}

	@Override
	public Collection<Field> getFields() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
