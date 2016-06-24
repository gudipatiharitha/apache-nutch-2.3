package org.iitkgp.nutch.scoring.content;

/**
 * @author parnab 
 *
 */

public class EntityScoringFeatures {
	private int inUrl; // Presence in url
	private int inAnchorINTexts; // Presence in inlinked anchor texts
	private int intitle; // presence in title
	private int indescription; // Presence in Description
	private int inEmphasisedTexts; // Presence in Emphasised Texts in Web pages
	private double fraction_sentences;  // fraction of Sentences containing the Entity
	private double position_measure;  // Position measure of the entity in the sentences
	private double top_measure;  // Presence of the entity towards the beginign of the document
	private int nNEs;           // frequency of the  entity in the document
	
	
	
	public int getInUrl() {
		return inUrl;
	}
	public void setInUrl(int inUrl) {
		this.inUrl = inUrl;
	}
	public int getInAnchorINTexts() {
		return inAnchorINTexts;
	}
	public void setInAnchorINTexts(int inAnchorINTexts) {
		this.inAnchorINTexts = inAnchorINTexts;
	}
	public int getIntitle() {
		return intitle;
	}
	public void setIntitle(int intitle) {
		this.intitle = intitle;
	}
	public int getIndescription() {
		return indescription;
	}
	public void setIndescription(int indescription) {
		this.indescription = indescription;
	}
	public int getInEmphasisedTexts() {
		return inEmphasisedTexts;
	}
	public void setInEmphasisedTexts(int inEmphasisedTexts) {
		this.inEmphasisedTexts = inEmphasisedTexts;
	}
	public double getFraction_sentences() {
		return fraction_sentences;
	}
	public void setFraction_sentences(double fraction_sentences) {
		this.fraction_sentences = fraction_sentences;
	}
	public double getPosition_measure() {
		return position_measure;
	}
	public void setPosition_measure(double position_measure) {
		this.position_measure = position_measure;
	}
	public double getTop_measure() {
		return top_measure;
	}
	public void setTop_measure(double top_measure) {
		this.top_measure = top_measure;
	}
	public int getnNEs() {
		return nNEs;
	}
	public void setnNEs(int nNEs) {
		this.nNEs = nNEs;
	}
	
	
	

}
