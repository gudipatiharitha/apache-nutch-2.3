package org.iitkgp.nutch.scoring.content;

import java.util.ArrayList;
import java.util.Map;

import org.apache.nutch.crawl.CrawlDatum;
//import org.apache.nutch.crawl.Inlinks;
import org.apache.nutch.parse.Parse;
import org.apache.nutch.storage.WebPage;

public class EntityScoringWrapper {
	private ArrayList<EntityScore> EntityScoreList;
	private PageAttributes pageAttributes;

	public EntityScoringWrapper(WebPage page, String decodedPagelUrl,
			String host, Map<CharSequence,CharSequence> inlinks, String[] nEs,
			String[] mWEs) {

		this.pageAttributes = CollectPageAttributes.collectPageAttributes(
				page, decodedPagelUrl, host, inlinks, nEs, mWEs);

		String[] entities = this.pageAttributes.getEntities();
		if (entities != null) {
			this.EntityScoreList = new ArrayList<EntityScore>();
			for (String entity : entities) {
				if (entity != null) {
					EntityScoreComputation ESC = new EntityScoreComputation(
							this.pageAttributes, entity);
					EntityScore ES = new EntityScore();
					ES.setEntity(entity);
					ES.setScore(ESC.getScore());
					this.EntityScoreList.add(ES);
					ESC = null;// help garbage collector
				}
			}
		}

	}

	public ArrayList<EntityScore> getEntityScoreList() {
		return EntityScoreList;
	}

}
