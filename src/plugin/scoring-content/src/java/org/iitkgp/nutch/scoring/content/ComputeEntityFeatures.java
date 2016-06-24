package org.iitkgp.nutch.scoring.content;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

public class ComputeEntityFeatures {
	private PageAttributes pageAttributes;
	private String[] sentences;
	private String entity;
	private double fractionalMeasure;
	private double positionMeasure;
	private double topnessMeasure;
	private double titleMeasure;
	private double descriptionMeasure;
	private double urlMeasure;
	private double inAnchorMeasure;
	private double entityFrequencyMeasure;
	private double entityEmphasisMeasure;

	public ComputeEntityFeatures(PageAttributes pageAttr, String entity) {
		this.pageAttributes = pageAttr;
		this.entity = entity;
		extractSentences();
		computeFractionalMeasure();
		computePositionMeasure();
		topnessMeasure();
		titleMeasure();
		urlMeasure();
		inAnchorTextMeasure();
		frequencyMeasure();
		emphasisMeasure();
		descriptionMeasure();

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

		String delimeter = delimeter_map.get(this.pageAttributes.getLang()
				.trim());
		String content = this.pageAttributes.getContent();

		// System.out.println("PAGE LANG : " + this.pageAttributes.getLang() +
		// " DELIMETER : " + delimeter);

		String[] sentences = StringUtils.split(content, delimeter);
		this.sentences = sentences;
	}

	public void computeFractionalMeasure() {
		if (this.sentences == null) {
			return;
		}
		double numSentences = this.sentences.length;

		double count = 0;
		for (String sentence : this.sentences) {
			if (StringUtils.contains(sentence, this.entity)) {
				count++;
			}
		}

		if (numSentences != 0) {
			this.fractionalMeasure = (count / numSentences);
		}
	}

	public void computePositionMeasure() {
		double measure = 0;int count=0;
		for (String sentence : this.sentences) {
			count++;
			if (sentence.contains(this.entity)) {
				int index = StringUtils.indexOf(sentence, this.entity);
				measure = measure + (sentence.length() - index)/(double)sentence.length();
				
				

			}
		}
		this.positionMeasure = measure/(double)count;
	}

	public void topnessMeasure() {
		if (this.sentences == null) {
			return;
		}
		double measure = 0.0;
		int found = 0;
		for (int i = 0; i < sentences.length; i++) {
			if (sentences[i].contains(this.entity)) {
				found++;
				measure = measure + (found / (double) (i + 1));

			}
		}
		this.topnessMeasure = measure/(double)sentences.length;
	}

	public void titleMeasure() {
		String title = this.pageAttributes.getTitle();
		double measure = 0.0;
		measure = StringUtils.countMatches(title, this.entity);
		this.titleMeasure = measure;
	}

	public void urlMeasure() {
		String url = this.pageAttributes.getUrl();
		double measure = 0.0;
		measure = StringUtils.countMatches(url, this.entity);
		this.urlMeasure = measure;
	}

	public void inAnchorTextMeasure() {
		String[] inAnchors = this.pageAttributes.getAnchorIN();
		if (inAnchors == null) {
			return;
		}
		double measure = 0.0;
		for (String anchor : inAnchors) {

			if (anchor.trim().contains(this.entity)) {
				measure++;
			}

		}
		this.inAnchorMeasure = measure;
	}

	public void frequencyMeasure() {
		double measure = 0.0;
		measure = StringUtils.countMatches(this.pageAttributes.getContent(),
				this.entity);
		this.entityFrequencyMeasure = measure;
	}

	public void emphasisMeasure() {
		double measure = 0.0;
		measure = StringUtils.countMatches(
				this.pageAttributes.getEmphasisedtext(), this.entity);
		this.entityEmphasisMeasure = measure;
	}

	public void descriptionMeasure() {
		double measure = 0.0;
		measure = StringUtils.countMatches(
				this.pageAttributes.getDescription(), this.entity);
		this.descriptionMeasure = measure;
	}

	public PageAttributes getPageAttributes() {
		return pageAttributes;
	}

	public String[] getSentences() {
		return sentences;
	}

	public String getEntity() {
		return entity;
	}

	public double getFractionalMeasure() {
		return fractionalMeasure;
	}

	public double getPositionMeasure() {
		return positionMeasure;
	}

	public double getTopnessMeasure() {
		return topnessMeasure;
	}

	public double getTitleMeasure() {
		return titleMeasure;
	}

	public double getDescriptionMeasure() {
		return descriptionMeasure;
	}

	public double getUrlMeasure() {
		return urlMeasure;
	}

	public double getInAnchorMeasure() {
		return inAnchorMeasure;
	}

	public double getEntityFrequencyMeasure() {
		return entityFrequencyMeasure;
	}

	public double getEntityEmphasisMeasure() {
		return entityEmphasisMeasure;
	}

}
