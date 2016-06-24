package org.iitkgp.nutch.scoring.content;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.nutch.crawl.CrawlDatum;
//import org.apache.nutch.crawl.Inlinks;
import org.apache.nutch.parse.Parse;
import org.apache.nutch.storage.WebPage;
import org.apache.nutch.util.Bytes;

public class PageScoring {
   
		
		private double CONTENT_RICHNESS_SCORE;
		private double URL_SCORE;
		private double DATUM_SCORE;
		private double INLINK_SCORE;
		private double INLINK_ANCHOR_SCORE;
		private double PAGE_SCORE;
		private Map<String,Double> BOOST_MAP;
		
		
		
		
	
	
	public static Map<String,Double> collectPageAttributes(WebPage page,
			String decodedPagelUrl, String host, CrawlDatum datum,
			Map<CharSequence,CharSequence> inlinks, String[] nEs, String[] mWEs) {
		
		    PageScoring PS = new PageScoring();   
		    PS.BOOST_MAP= new HashMap<String, Double>();
		
		    
		
			PageAttributes pageAttributes = new PageAttributes();
		
			pageAttributes.setUrl(decodedPagelUrl);
			pageAttributes.setSite(host);
			pageAttributes.setTitle(page.getTitle().toString());
			pageAttributes.setDescription(Bytes.toString(page.getMetadata().get("description").array()) + "");
			pageAttributes.setContent(Bytes.toString(page.getMetadata().get("content").array()) + "");
			pageAttributes.setLang(Bytes.toString(page.getMetadata().get("lang").array()) + "");
		
		//Collecting the Entities of the page
			String[] Entities=null;
		    ArrayList<String> EntityList=new ArrayList<String>();
		
		if(nEs!=null){
			for(String entity:nEs){
				EntityList.add(entity);
			}
		}
		
		if(mWEs!=null){
			for(String entity:mWEs){
				EntityList.add(entity);
			}
		}
		
		if(EntityList.size()>0){
			Entities=new String[EntityList.size()];
			for(int i=0;i<Entities.length;i++){
				Entities[i]=EntityList.get(i);
			}
			
		}
		
		pageAttributes.setEntities(Entities);
		
		
		// collecting the crawl DB score : score from OPIC ,LINK RANK(optional)
		if(datum!=null){
			pageAttributes.setDatumScore(datum.getScore());
		}
		
		//Collecting in-links and in-linked anchor texts
		String[] anchors = null;
		if(inlinks!=null){
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
			pageAttributes.setN_extraDomian_Inlinks(getExtraDomainLinks(inlinks,host).size());
			System.out.println( "NO OF EXTRADOMAIN LINKS :" + getExtraDomainLinks(inlinks,host).size() );
			pageAttributes.setN_intraDomian_Inlinks(getIntraDomainLinks(inlinks,host).size());
			System.out.println( "NO OF INTRAADOMAIN LINKS :" + getIntraDomainLinks(inlinks,host).size());
			
		}
		
			
		//Get Content Richness
		    ContentRichness CR = new ContentRichness(pageAttributes);
			CR.computeRichness();
			PS.CONTENT_RICHNESS_SCORE=CR.getSCORErichness();
			
			System.out.println("CONTENT RICHNESS : " + PS.CONTENT_RICHNESS_SCORE);
			PS.BOOST_MAP.put("content", PS.CONTENT_RICHNESS_SCORE);
			
	   //Compute URL Score
			URLScoring URS = new URLScoring(pageAttributes);
			URS.computeURLScore();
			PS.URL_SCORE=URS.getURL_SCORE();
			
			System.out.println("URL SCORE : " + PS.URL_SCORE);
			PS.BOOST_MAP.put("url", PS.URL_SCORE);
			
		
	  //Compute Datum Score
			if((Double)pageAttributes.getDatumScore()==null){
				PS.DATUM_SCORE=1.0;
			}
			else{
				PS.DATUM_SCORE=pageAttributes.getDatumScore();
			}
			
			System.out.println("DATUM SCORE : " + PS.DATUM_SCORE);
			
	  //Compute Inlink Score
			  
			PS.INLINK_SCORE=pageAttributes.getN_extraDomian_Inlinks()+
					         pageAttributes.getN_intraDomian_Inlinks();
			System.out.println("INLINK SCORE : " + PS.INLINK_SCORE);
			
			
	  //Compute Inlink Anchor Score		
		     	
		     InlinkAnchorScoring IAS = new InlinkAnchorScoring(pageAttributes);
		     IAS.computeInlinkAnchorScore();
		     PS.INLINK_ANCHOR_SCORE=IAS.getINLINK_ANCHOR_SCORE();
		     System.out.println("INLINK ANCHOR SCORE : " + PS.INLINK_ANCHOR_SCORE);
		     
	//Compute Final Page Score
		     
		     double score = Math.sqrt(PS.CONTENT_RICHNESS_SCORE) 
		                    + (PS.URL_SCORE+1)*(PS.DATUM_SCORE+1)
		                    +  (PS.INLINK_SCORE+1)*Math.pow(PS.INLINK_ANCHOR_SCORE, 2);
		     PS.PAGE_SCORE=score;
		     
		     PS.BOOST_MAP.put("doc", PS.PAGE_SCORE);
		     
		     return PS.BOOST_MAP;
		     
		
	}





	public static ArrayList<String> getExtraDomainLinks(Map<CharSequence,CharSequence> inlinks, String host){
		ArrayList<String> links= new ArrayList<String>();
		String urlText=inlinks.toString();
		String[] urlLines=urlText.split("\n");
		for(String line : urlLines){
		    		
			String url=extractLink(line);
			if(url!=null){
			String site = getSiteFromUrl(url);
			if(!host.equals(site)){
				links.add(site);
			}
			
		}
		}
				
		return links;
		
	}
	
	public static ArrayList<String>  getIntraDomainLinks(Map<CharSequence,CharSequence> inlinks, String host){
        ArrayList<String> links= new ArrayList<String>();
		String urlText=inlinks.toString();
		String[] urlLines=urlText.split("\n");
		for(String line : urlLines){
			String url=extractLink(line);
			if(url!=null){
			String site = getSiteFromUrl(url);
			if(host.equals(site)){
				links.add(site);
			}
		  }
		}
				
		return links;
	}
	
	public static String getSiteFromUrl(String url){
		
		String site=null;
		try {
			URL u = new URL(url.toString().trim());
			site = u.getHost();
		} catch (MalformedURLException e) {
			System.out.println("ERROR in Indexer!!");
		}
		
		return site;
	}
	
	
	public static String extractLink(String inlinks){
		
		String url=null;
		if(inlinks.contains("http") || inlinks.contains("anchor")){
		 url = inlinks.substring(inlinks.indexOf("http"),inlinks.indexOf("anchor"));
		 
		}
		return url;
	}

	






	
	
	

	

}
