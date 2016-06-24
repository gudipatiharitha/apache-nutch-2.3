package org.iitkgp.nutch.scoring.content;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import org.apache.avro.util.Utf8;
import org.apache.nutch.crawl.CrawlDatum;
//import org.apache.nutch.crawl.Inlinks;
import org.apache.nutch.parse.Parse;
import org.apache.nutch.storage.WebPage;
import org.apache.nutch.util.Bytes;

public class CollectPageAttributes {

	public static PageAttributes collectPageAttributes(WebPage page,
			String decodedPagelUrl, String host, Map<CharSequence,CharSequence> inlinks, String[] nEs, String[] mWEs) {

		PageAttributes pageAttributes = new PageAttributes();

		pageAttributes.setUrl(decodedPagelUrl);
		pageAttributes.setSite(host);
		pageAttributes.setTitle(page.getMetadata().get(new Utf8("title"))!=null ? Bytes.toString(page.getMetadata().get(new Utf8("title")).array()) + "" : "");
		pageAttributes.setDescription(page.getMetadata().get(new Utf8("description"))!=null ? Bytes.toString(page.getMetadata().get(new Utf8("description")).array()) + "" : "");
		pageAttributes.setContent(page.getMetadata().get(new Utf8("content"))!=null ? Bytes.toString(page.getMetadata().get(new Utf8("content")).array()) + "" : "");
		pageAttributes.setLang(page.getMetadata().get(new Utf8("lang"))!=null ? Bytes.toString(page.getMetadata().get(new Utf8("lang")).array()) + "" : "");
		pageAttributes.setEntities(getEntities(nEs, mWEs));
		// Collecting in-links and in-linked anchor texts
		String[] anchors = null;
		if (inlinks != null) {
			try{
				ArrayList<String> anchors_list= new ArrayList<String>();
				for(CharSequence key: inlinks.keySet()){
					anchors_list.add(inlinks.get(key).toString());
				}
				anchors = anchors_list.toArray(new String[anchors_list.size()]);
			pageAttributes.setAnchorIN(anchors);
			}catch(Exception e){
				System.out.println("Exception encountred while extracting inlinks : " + e.getMessage());
			}
			pageAttributes.setN_extraDomian_Inlinks(getExtraDomainLinks(
					inlinks, host).size());
			/*System.out.println("NO OF EXTRADOMAIN LINKS :"
					+ getExtraDomainLinks(inlinks, host).size());*/
			pageAttributes.setN_intraDomian_Inlinks(getIntraDomainLinks(
					inlinks, host).size());
		   /*	System.out.println("NO OF INTRAADOMAIN LINKS :"
					+ getIntraDomainLinks(inlinks, host).size()); */

		}
            
		return pageAttributes;
	}

	public static ArrayList<String> getExtraDomainLinks(Map<CharSequence,CharSequence> inlinks,
			String host) {
		ArrayList<String> links = new ArrayList<String>();
		String urlText = inlinks.toString();
		String[] urlLines = urlText.split("\n");
		for (String line : urlLines) {

			String url = extractLink(line);
			if (url != null) {
				String site = getSiteFromUrl(url);
				if (!host.equals(site)) {
					links.add(site);
				}

			}
		}

		return links;

	}

	public static ArrayList<String> getIntraDomainLinks(Map<CharSequence,CharSequence> inlinks,
			String host) {
		ArrayList<String> links = new ArrayList<String>();
		String urlText = inlinks.toString();
		String[] urlLines = urlText.split("\n");
		for (String line : urlLines) {
			String url = extractLink(line);
			if (url != null) {
				String site = getSiteFromUrl(url);
				if (host.equals(site)) {
					links.add(site);
				}
			}
		}

		return links;
	}

	public static String getSiteFromUrl(String url) {

		String site = null;
		try {
			URL u = new URL(url.toString().trim());
			site = u.getHost();
		} catch (MalformedURLException e) {
			System.out.println("ERROR in Indexer!!");
		}

		return site;
	}

	public static String extractLink(String inlinks) {

		String url = null;
		if (inlinks.contains("http") || inlinks.contains("anchor")) {
			url = inlinks.substring(inlinks.indexOf("http"),
					inlinks.indexOf("anchor"));

		}
		return url;
	}

	public static String[] getEntities(String[] nEs, String[] mWEs) {
		// Collecting the Entities of the page
		String[] Entities = null;
		ArrayList<String> EntityList = new ArrayList<String>();

		if (nEs != null) {
			for (String entity : nEs) {
				if(!EntityList.contains(entity))
				      EntityList.add(entity);
			}
		}

		if (mWEs != null) {
			for (String entity : mWEs) {
				if(!EntityList.contains(entity))
				      EntityList.add(entity);
			}
		}

		if (EntityList.size() > 0) {
			Entities = new String[EntityList.size()];
			for (int i = 0; i < Entities.length; i++) {
				Entities[i] = EntityList.get(i);
			}

		}
		return Entities;
	}

}
