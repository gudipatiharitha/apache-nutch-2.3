package org.iitkgp.nutch.scoring.content;


public class PageAttributes {
	private  String url;
	private  String site;
	private  String title;
	private  String description;
	private  String content;
	private  String emphasisedtext;
	private  String lang;
	private  String [] entities;
	private  double datumScore;
	private  String[] anchorIN;
	private  int n_extraDomian_Inlinks=0;
	private  int n_intraDomian_Inlinks=0;
	
	
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public String[] getEntities() {
		return entities;
	}
	public void setEntities(String[] entities) {
		this.entities = entities;
	}
	public double getDatumScore() {
		return datumScore;
	}
	public void setDatumScore(double datumScore) {
		this.datumScore = datumScore;
	}
	public String[] getAnchorIN() {
		return anchorIN;
	}
	public void setAnchorIN(String[] anchorIN) {
		this.anchorIN = anchorIN;
	}
	public int getN_extraDomian_Inlinks() {
		return n_extraDomian_Inlinks;
	}
	public void setN_extraDomian_Inlinks(int n_extraDomian_Inlinks) {
		this.n_extraDomian_Inlinks = n_extraDomian_Inlinks;
	}
	public int getN_intraDomian_Inlinks() {
		return n_intraDomian_Inlinks;
	}
	public void setN_intraDomian_Inlinks(int n_intraDomian_Inlinks) {
		this.n_intraDomian_Inlinks = n_intraDomian_Inlinks;
	}
	public String getEmphasisedtext() {
		return emphasisedtext;
	}
	public void setEmphasisedtext(String emphasisedtext) {
		this.emphasisedtext = emphasisedtext;
	}
	
	
	
	
    
	

}
