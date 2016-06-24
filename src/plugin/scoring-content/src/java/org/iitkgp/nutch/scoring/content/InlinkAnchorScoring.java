package org.iitkgp.nutch.scoring.content;

public class InlinkAnchorScoring {

	private String[] inAnchors;
	private String[] entities;

	private int INLINK_ANCHOR_SCORE=0;

	public InlinkAnchorScoring(PageAttributes pageAttributes) {
		this.inAnchors = pageAttributes.getAnchorIN();
		this.entities = pageAttributes.getEntities();
	}
	
	

	public int getINLINK_ANCHOR_SCORE() {
		return INLINK_ANCHOR_SCORE;
	}



	public void setINLINK_ANCHOR_SCORE(int iNLINKANCHORSCORE) {
		INLINK_ANCHOR_SCORE = iNLINKANCHORSCORE;
	}



	public void computeInlinkAnchorScore() {
		int score = 0;
		
		if(this.entities==null || this.inAnchors==null){
			return;
		}
		
		for (String anchors : this.inAnchors) {
			for (String entities : this.entities) {
				if (entities.trim().equals(anchors.trim())) {
					score++;
				}
			}
		}
		this.INLINK_ANCHOR_SCORE = score;
	}

}
