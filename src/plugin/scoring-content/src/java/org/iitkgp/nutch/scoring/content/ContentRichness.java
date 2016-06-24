package org.iitkgp.nutch.scoring.content;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class ContentRichness {

	private int nNEs; //number of Entities in document
	private int nNEsContent; //number of Entities in content
	private int nSentenceNE; // number of sentences having entities
	private double positionMeasureNE; //position measure of entities in sentences
	private int nNEsTitle;//number of entities in title
	private int nNEsDescpription;//number of entities in description
	private String[] sentences; //sentences from the page content
	private int nSentences=1;// number of sentences

	private String title;// page title
	private String description;// page description
	private String content;// page content
	private String lang; // page lang
	private String[] entities;//Entities  in the page

	private double SCORErichness = 1.0; // content richness measure w.r.t to entities

	public ContentRichness(PageAttributes PA) {

		this.title = PA.getTitle();
		this.description = PA.getDescription();
		this.content = PA.getContent();
		this.lang = PA.getLang();
		this.entities = PA.getEntities();
		
		System.out.println("THIS ENTITIES : " + this.entities);

		if (this.entities != null) {
			extractSentences();
			calculateNentities();
			calculateNEsContent();
			calculateNEsDescription();
			calculateNEsSentence();
			calculateNEsTitle();
			calculatePositionMeasure();
		}

	}
     
	public double getSCORErichness() {
		return SCORErichness;
	}

	public void setSCORErichness(double sCORErichness) {
		SCORErichness = sCORErichness;
	}
	
	
	// Calculate Number of Entities
	public void calculateNentities() {
		this.nNEs = this.entities.length;
		System.out.println("NO of NES " + this.nNEs);
	}

	
	// Calculate Number of Entities in Content
	public void calculateNEsContent() {
		int NECOUNT = 0;
		for (String NE : this.entities) {
			NECOUNT += StringUtils.countMatches(this.content, NE.trim());
		}
		this.nNEsContent = NECOUNT;
		System.out.println("NO of NES in content " + this.nNEsContent);
	}

	//Calculate number Of Entities in Title
	public void calculateNEsTitle() {
		int NECOUNT = 0;
		for (String NE : this.entities) {
			NECOUNT += StringUtils.countMatches(this.title, NE.trim());
		}
		this.nNEsTitle = NECOUNT;
		System.out.println("NO of NES in title " + this.nNEsTitle);
	}

	// Calculate number of Entities in Description
	public void calculateNEsDescription() {
		int NECOUNT = 0;
		for (String NE : this.entities) {
			NECOUNT += StringUtils.countMatches(this.description, NE.trim());
		}
		this.nNEsDescpription = NECOUNT;
		System.out.println("NO of NES in description " + this.nNEsDescpription);
	}

	//Calculate Number of Sentences Containing Entities
	public void calculateNEsSentence() {
		int count = 0;
		for (String sentence : this.sentences) {
			int temp = 0;
			for (String NE : this.entities) {
				if (sentence.contains(NE)) {
					temp++;
				}
			}

			if (temp > 0) {
				count++;
			}
		}

		this.nSentenceNE = count;
		System.out.println("No of Sentences having NE " + this.nSentenceNE);

	}

	// Calculate Position Measure of Entities in Sentences
	public void calculatePositionMeasure() {
		double measure = 0;
		for (String sentence : this.sentences) {
			int temp = 0;
			for (String NE : this.entities) {
				if (sentence.contains(NE)) {
					int index = StringUtils.indexOf(sentence, NE);
					measure = measure + (sentence.length() - index);
					// System.out.println("SENTENCE "+ sentence+ " NE " + NE+
					// " INDEX "+(sentence.length()-index));
				}
			}

		}

		this.positionMeasureNE = measure;
		System.out.println("NE Position Measure : " + this.positionMeasureNE);

	}

	public void extractSentences() {
		HashMap<String, String> delimeter_map = new HashMap<String, String>();

		delimeter_map.put("bn", "।");
		delimeter_map.put("en", ".");
		delimeter_map.put("hi", "।");
		delimeter_map.put("ta", ".");
		delimeter_map.put("te", ".");
		delimeter_map.put("mr", "।");
		delimeter_map.put("pa", "।");
		delimeter_map.put("gu", "।");
		delimeter_map.put("as", "।");
		delimeter_map.put("or", "।");

		String delimeter = delimeter_map.get(this.lang.trim());
		String content = this.content;
		
		System.out.println("PAGE LANG : " + this.lang + " DELIMETER : " + delimeter);

		String[] sentences = StringUtils.split(content, delimeter);
		this.sentences = sentences;
		//displaySentences();
		if(sentences!=null){
		this.nSentences = sentences.length;
		}
		System.out.println("No of sentences : " + this.nSentences);

	}

	public void computeRichness() {
          double richness=1;
          if(this.entities!=null){
          richness = (this.nNEs+this.nNEsContent)/2 + 
          				this.nNEsTitle + 
          				this.nNEsDescpription + 
          				this.nSentenceNE
                      + Math.log(this.positionMeasureNE==0.0?1:this.positionMeasureNE) ;
          }
        //  System.out.println("richness : " + richness);
          this.SCORErichness=richness;                    
	}
	
	void displaySentences(){
		System.out.println("SENTENCES :  ");
		if(this.sentences!=null){
			for(String sentences : this.sentences){
				System.out.println(sentences);
			}
		}
	}

}
