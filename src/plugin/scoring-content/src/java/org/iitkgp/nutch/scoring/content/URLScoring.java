package org.iitkgp.nutch.scoring.content;

public class URLScoring {
		
	    private String   site;
		private String   url;
		private double   SITE_REPUTATION;
		private Boolean  NE_URL=false;
		private String[] entities;
		
		private double URL_SCORE;
		
		

		public double getURL_SCORE() {
			return URL_SCORE;
		}

		public void setURL_SCORE(double uRLSCORE) {
			URL_SCORE = uRLSCORE;
		}

        
		public URLScoring(PageAttributes pageAttributes) {
			this.site=pageAttributes.getSite();
			this.url=pageAttributes.getUrl();
			this.entities=pageAttributes.getEntities();
			
			computeSiteReputation();
			checkNEURL();
		}
		
		
		public void computeSiteReputation(){
			double reputation =1;// Has to be changed . Need to be queried from Index
			this.SITE_REPUTATION=reputation;
		}
		
		
		public void checkNEURL(){
			String[] Entities=this.entities;
			if(Entities!=null){
				for(String NE: Entities){
					if(this.url.contains(NE)){
						this.NE_URL=true;
						break;
					}
				}
			}
		}
		
		public void computeURLScore(){
			if(this.NE_URL==true){
				this.URL_SCORE= Math.pow((this.SITE_REPUTATION + 1),2);
			}else{
				this.URL_SCORE= Math.pow(this.SITE_REPUTATION ,2);
			}
			
		}
	     
	

	
	
	
	
}
