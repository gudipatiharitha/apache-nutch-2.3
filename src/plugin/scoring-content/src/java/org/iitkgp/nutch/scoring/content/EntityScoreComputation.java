package org.iitkgp.nutch.scoring.content;

public class EntityScoreComputation {

	private double score;

	public EntityScoreComputation(PageAttributes pageAttributes, String entity) {
		ComputeEntityFeatures CEF = new ComputeEntityFeatures(pageAttributes,
				entity);

		double fractionalMeasure = CEF.getFractionalMeasure();
		double positionMeasure = CEF.getPositionMeasure();
		double topnessMeasure = CEF.getTopnessMeasure();
		double titleMeasure = CEF.getTitleMeasure();
		double descriptionMeasure = CEF.getDescriptionMeasure();
		double urlMeasure = CEF.getUrlMeasure();
		double inAnchorMeasure = CEF.getInAnchorMeasure();
		double entityFrequencyMeasure = CEF.getEntityFrequencyMeasure();
		double entityEmphasisMeasure = CEF.getEntityEmphasisMeasure();

		// the below combination is just for test
		/*double score = 3 * titleMeasure + descriptionMeasure + 2 * urlMeasure
				+ 4 * inAnchorMeasure
				+ (positionMeasure + topnessMeasure + fractionalMeasure)* Math.log(entityFrequencyMeasure + 1) 
				+ 3* entityEmphasisMeasure;*/
		
		double score = 3 * titleMeasure + descriptionMeasure + 2 * urlMeasure
				+ 4 * inAnchorMeasure
				+ (positionMeasure + topnessMeasure + fractionalMeasure)* (entityFrequencyMeasure + 1) 
				+ 3* entityEmphasisMeasure;

		this.score = score;

	}

	public double getScore() {
		return this.score;
	}

}
