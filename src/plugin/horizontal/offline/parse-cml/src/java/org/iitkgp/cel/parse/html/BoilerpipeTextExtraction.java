package org.iitkgp.cel.parse.html;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.xml.sax.SAXException;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLFetcher;

public class BoilerpipeTextExtraction {
	public static void main(String[] args) {
		HTMLDocument htmlDoc = null;
		try {
			htmlDoc = HTMLFetcher.fetch(new URL("https://www.kaggle.com"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TextDocument doc = null;
		try {
			doc = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
		} catch (BoilerpipeProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String content = null;
		try {
			content = CommonExtractors.ARTICLE_EXTRACTOR.getText(doc);
		} catch (BoilerpipeProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(content);
	}
}