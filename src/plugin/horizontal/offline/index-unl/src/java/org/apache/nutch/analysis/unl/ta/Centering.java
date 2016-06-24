package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.enconversion.unl.ta;

//import java.io.*;
//import java.lang.*;
import java.util.*;

/**
 * This Class describes about the Centering approach for Pronoun Resolution
 * along with the use of Semantics This class solves the Problem of
 * Demonstrative Adjectives, Adverbs and Pronouns Based on Centering approach,
 * contraindexes are filtered With the classification of Pronouns - The Rules
 * are derived and Pronouns are identified
 * 
 * @author Balaji J
 * 
 */

public class Centering {

	ArrayList<String> fCenter = new ArrayList<String>();
	ArrayList<String> bCenter;
	ArrayList<String> pCenter;
	ArrayList<String> adverbPlace;
	ArrayList<String> getPlace;
	ArrayList<String> concatList;

	Stack cindexList;
	Stack eventsList;
	Stack personList;
	Stack recencyStack;
	Stack entityStack;

	Stack cIndexStack;
	Stack adverbStack;
	Stack stack2;

	String fAnchor;
	String bAnchor;
	String pAnchor;

	String pword;
	String aword;
	String analword;
	String wordCase1;
	String wordCase2;
	String resWord;

	String dadj = null;
	String dword = null;
	String nouncheck = null;
	String nounword = null;

	public Centering() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Initialize all the Variables and ArrayLists
	 */
	public void init() {

		bCenter = new ArrayList<String>();
		pCenter = new ArrayList<String>();
		adverbPlace = new ArrayList<String>();
		concatList = new ArrayList<String>();
		getPlace = new ArrayList<String>();

		cindexList = new Stack();
		eventsList = new Stack();
		personList = new Stack();
		recencyStack = new Stack();
		entityStack = new Stack();

		fAnchor = null;
		bAnchor = null;
		pAnchor = null;

		pword = null;
		aword = null;
		analword = null;
		wordCase1 = null;
		wordCase2 = null;
		resWord = null;
	}

	/**
	 * This method handles the adverbs for places (ingu, inguLLa, ingirundhu,
	 * inguthaan) DataStructure used is Stack ForwardList contains words having
	 * POS as Entity and/or Semantics as Place, Temple etc... Performing the
	 * stack operations, exact word for adverbs are identified and replaced
	 * 
	 * @param forwardList
	 *            - list of words as places
	 * @param pronounword
	 *            - the pronoun like ingu, inguLLa etc..
	 * @return inguPlace - word for the adverb
	 */
	public String centeringProcessforAdverb(ArrayList<String> forwardList,
			String pronounword) {
		init();
		adverbPlace = getEntityplace(forwardList);
		Stack adverbStack = new Stack();
		String inguPlace = null;
		for (int i = 0; i < adverbPlace.size(); i++) {
			String notAdv = adverbPlace.get(i).toString();
			if (!(notAdv.contains("person")) && !(notAdv.contains("god"))
					&& !(notAdv.contains("information"))
					&& !(notAdv.contains("goddess"))
					&& !(notAdv.contains("automobile"))
					&& !(notAdv.contains("flower")) && !(notAdv.contains("king")) && !(notAdv.contains("area")) ) {
				adverbStack.push(notAdv);
			}
		}
	//	System.out.println("adverbStack:" + adverbStack + ":"				+ adverbStack.size());
		for (int k = 0; k < adverbStack.size(); k++) {
			String adverbTerm = adverbStack.peek().toString();
		//	System.out.println("adverbTerm inside FOR:" + adverbTerm);
			if ((adverbTerm.contains("Entity"))
					&& ((adverbTerm.contains("iof>place"))
							|| (adverbTerm.contains("icl>temple")) || (adverbTerm
							.contains("iof>temple")))) {
				inguPlace = adverbStack.peek().toString();
		//		System.out.println("adverbTerm inside IF:" + inguPlace);
				break;
			} else {
				adverbStack.pop();
		//		System.out.println("Popped adverbTerm:" + adverbTerm);
			}
		}
		return inguPlace;
	}

	/**
	 * 
	 * @param pnounList
	 * @param pwordlist
	 * @param posword
	 * @param analysed
	 * @return
	 */
	public String centeringProcessforPronoun(ArrayList<String> pnounList,
			ArrayList<String> pwordlist, String posword, String analysed) {
		init();
		bCenter.addAll(pnounList);
	//	System.out.println("bCenter:"+bCenter);
		pword = posword;
		analword = analysed;
		for (int i = 0; i < pwordlist.size(); i++) {
			concatList.add(pwordlist.get(i).toString());
		}
		filteringProcess();
		pronounRules();
		concatList.clear();
		aword = resWord;
		return aword;
	}
	/** Third Person Pronouns and Proximate Pronouns are handled in this method
	 *  Building ContraIndices for Persons and for Events and Places spearately
	 *  Getting the caseMarkers associated with the pronouns
	 *  Pronouns indicating Persons with Plural Suffix are handled separately
	 *  Pronouns indicating Events and Places are handled separately
	 *  From the Stack identify the appropriate expression with the help of Semantics
	 *  checking for 'group' or 'person' for the person pronouns with plural suffix
	 *  Other than plural suffix, the cases like accusative, genetive, Dative, Locative and clitic are considered
	 *  Recency checking is needed for co-specifiers and the use of semantics
	 */
	public void pronounRules() {			
		/**Check for pronouns that represents persons**/
		
		if ((analword.contains("இவர்")) || (analword.contains("அவர்"))
				|| (analword.contains("அவன்")) || (analword.contains("இவன்"))) {
		//	System.out.println("Inside Persons");
			contraIndexforPerson();	
			getCaseMarkers();		
			if (analword.contains("கள்")) {			
				for (int k = 0; k < personList.size(); k++) {	
					String chkPerson1 = personList.peek().toString();
					if ((chkPerson1.contains("group"))
							|| (chkPerson1.contains("person"))) { 
						String[] prResult = chkPerson1.split(",");
						if ((prResult[1].contains("icl>group"))
								|| (prResult[1].contains("icl>person"))) {
							resWord = prResult[1].toString().trim();
						} else {
							resWord = prResult[2].toString().trim();
						}
					} else {
						personList.pop();
					}

				}
			} else if ((analword.contains("500")) || (analword.contains("502"))
					|| (analword.contains("504")) || (analword.contains("506"))
					|| (analword.contains("Clitic"))) {   
				recencyStack.addAll(bCenter);	
		//		System.out.println("recencyStack Inside Else IF:"+recencyStack);
				for (int i = 0; i < recencyStack.size(); i++) {
					String recWord1 = recencyStack.peek().toString();
					if (recWord1.contains("person")) {	
						resWord = recWord1;
					} else {
						recencyStack.pop();
					}
				}
			} else {
				if (bCenter.size() == 0) {
					for (int k = 0; k < personList.size(); k++) {
						String chkPerson2 = personList.peek().toString();
						if (chkPerson2.contains("person")) {
							String[] prResult1 = chkPerson2.split(",");
							if (prResult1[1].contains("person")) {
								resWord = prResult1[1].toString().trim();
							} else {
								resWord = prResult1[2].toString().trim();
							}
						} else {
							personList.pop();
						}
					}
				} else {
					for (int l = 0; l < bCenter.size(); l++) {
						String recWord2 = bCenter.get(l).toString();
						if (recWord2.contains("person")) {
							resWord = recWord2;
							break;
						}
					}
				}
			}
		} else if ((analword.contains("அது")) || (analword.contains("இது"))) {
		//	System.out.println("Inside அது  இது");
			contraIndexforEventsandPlaces();
			getCaseMarkers();
			processCoSpecifiers();
	
		}
		personList.clear();
		eventsList.clear();
	}
	public void processCoSpecifiers(){
		if ((analword.contains("500")) || (analword.contains("502"))
				|| (analword.contains("504")) || (analword.contains("506"))
				|| (analword.contains("Clitic"))) {
			if (bCenter.size() != 0) {
				for (int l = 0; l < bCenter.size(); l++) {
					String recWord4 = bCenter.get(l).toString();
					if ((recWord4.contains("iof>place"))
							|| (recWord4.contains("icl>temple"))
							|| (recWord4.contains("iof>river"))
							|| (recWord4.contains("icl>facilities"))) {
						resWord = recWord4;
						break;
					}
				}
			} else {
	//			System.out.println("recencyStack Inside else:"+recencyStack);
				for (int l = 0; l < recencyStack.size(); l++) {
					String recWord3 = recencyStack.peek().toString();
					if ((recWord3.contains("iof>place"))
							|| (recWord3.contains("icl>temple"))
							|| (recWord3.contains("iof>river"))
							|| (recWord3.contains("icl>facilities"))) {
						resWord = recWord3;
					} else {
						recencyStack.pop();
					}
				}
			}
		}
	}

	public void getCaseMarkers() {
		StringTokenizer strToken1 = new StringTokenizer(analword, " ");
		while (strToken1.hasMoreTokens()) {
			String wrd1 = strToken1.nextToken(">");
			if ((wrd1.contains("500")) || (wrd1.contains("502"))
					|| (wrd1.contains("504")) || (wrd1.contains("506")) || (wrd1.contains("Clitic")) ) {
				StringTokenizer strToken2 = new StringTokenizer(wrd1, "<");
				wordCase1 = strToken2.nextToken().trim();
				wordCase2 = strToken2.nextToken().trim();
			}
		//	System.out.println("wordcase1:"+wordCase1);
		//	System.out.println("wordcase2:"+wordCase2);
		}
	}

	public void forwardCenter() {
		if (fCenter.isEmpty()) {
			fCenter.add(fAnchor);
		} else if (!fCenter.contains(fAnchor)) {
			fCenter.add(fAnchor);
		}
	}

	public void preferredCenter() {
		pCenter.add(fAnchor);
	}

	public void backwardCenter() {
		bCenter.add(fAnchor);
	}

	/**
	 * This method describes about the ContraIndexes for Persons Some pronouns
	 * may points to both events and places (eg: "adhu","idhu" etc.. )
	 * ContraIndex - If more than one pronoun occur in the same sentence then it
	 * is said to be contraIndexed For Example:- "He liked him", Here "he" and
	 * "him" are contraindexed. A List of contra index Elements are created and
	 * filtered the through the filtering Process
	 */
	public void contraIndexforPerson() {
		String contraword = null;
		for (int i = 0; i < concatList.size(); i++) {
			String cel1 = concatList.get(i).toString();
			if ((cel1.contains("icl>person")) || (cel1.contains("icl>group"))
					|| (cel1.contains("iof>person"))
					|| (cel1.contains("icl>quantity"))) {
				for (int j = i + 1; j < concatList.size(); j++) {
					String cel2 = concatList.get(j).toString();
					if ((cel2.contains("icl>person"))
							|| (cel2.contains("icl>group"))
							|| (cel2.contains("iof>person"))
							|| (cel2.contains("icl>quantity"))) {
						contraword = pword + "," + cel1 + "," + cel2;
						personList.add(contraword);
					}
				} // end of for j
			}
		} // end of for i
	}

	/**
	 * This method describes about the ContraIndexes for Events and Places Some
	 * pronouns may points to both events and places (eg: "adhu","idhu" etc.. )
	 * ContraIndex - If more than one pronoun occur in the same sentence then it
	 * is said to be contraIndexed For Example:- "He liked him", here "he" and
	 * "him" are contraindexed A List of contra index Elements are created and
	 * filtered the through the filtering Process
	 */
	public void contraIndexforEventsandPlaces() {
		String contraTerm = null;
		for (int i = 0; i < concatList.size(); i++) {
			String contraevent1 = concatList.get(i).toString();
			if ((contraevent1.contains("iof>place"))
					|| (contraevent1.contains("icl>temple"))
					|| (contraevent1.contains("iof>river"))
					|| (contraevent1.contains("icl>facilities"))) {
				for (int j = i + 1; j < concatList.size(); j++) {
					String contraevent2 = concatList.get(j).toString();
					if ((contraevent2.contains("iof>place"))
							|| (contraevent2.contains("icl>temple"))
							|| (contraevent2.contains("iof>river"))) {
						contraTerm = pword + "," + contraevent1 + ","
								+ contraevent2;
						cindexList.add(contraTerm);
					}
				}// end of for j
			}
		}// end of for i
	}

	public void filteringProcess() {
		contraIndexforEventsandPlaces();

	}

	/**
	 * This method describes about handling of Demonstrative Adjectives This
	 * method uses the data structure as Stack for getting the recently occurred
	 * Entities, Nouns etc... The Tokenizer is used to tokenize the analyser
	 * output to check whether the Demonstrative Adjective contains noun, and
	 * based on the noun the checking of previous Strings are done. The String
	 * in the arraylist contains "tamilword/parts of speech/UNL Constraint"
	 * 
	 * @param processdadj
	 *            - Demonstrative Adjective as a String
	 * @param analysed
	 *            - analysed string that contains the grammatical informations
	 *            of a word
	 * @param dAdjList
	 *            - ArrayList contains the entities and nouns in the previous
	 *            sentence
	 * @return finalPlace -returns a String that is accepted as a final result
	 */

	public String centeringProcessforDAdjective(String processdadj,
			String analysed, ArrayList<String> dAdjList) {
		String finalPlace = null;
		String getplc = null;
		StringTokenizer strToken = new StringTokenizer(analysed, " ");
		while (strToken.hasMoreTokens()) {
			dadj = strToken.nextToken(">");
			if (dadj.contains("Noun")) {
				StringTokenizer strTok = new StringTokenizer(dadj, "<");
				nounword = strTok.nextToken().trim();
				nouncheck = strTok.nextToken().trim();
			}// end of IF
		}// End of While
		if (nounword != null) {
			if ((nounword.equals("நகரம்")) || (nounword.equals("இடம்"))
					|| (nounword.equals("ஊர்")) || (nounword.equals("பகுதி"))
					|| (nounword.equals("கோவில்"))) {
				getPlace = getEntityplace(dAdjList);
				for (int j = 0; j < getPlace.size(); j++) {
					getplc = getPlace.get(j).toString();
					entityStack.push(getplc);
				} // End of FOR
				dAdjList.clear();
		//		System.out.println("size:"+entityStack.size());
				for (int k = 0; k < entityStack.size(); k++) {
					
					String chkPlace = entityStack.peek().toString();
					if ((chkPlace.contains("Entity"))
							&& ((chkPlace.contains("iof>place"))
									|| (chkPlace.contains("iof>river")) || (chkPlace
									.contains("icl>temple"))|| (chkPlace
											.contains("iof>temple")) )) {
						finalPlace = entityStack.peek().toString();
			//			System.out.println("finalPlace:"+finalPlace);
						break;
						// entityStack.pop();
					} else {
						entityStack.pop();
					}
				}				
			}
		} else { // End of IF
			finalPlace = "None" + "/" + "None";
		} // End of ELSE
//		entityStack.clear();
		return finalPlace;
	}

	public ArrayList<String> getEntityplace(ArrayList<String> getDAdjList) {
		ArrayList<String> entityList = new ArrayList<String>();
		String getEntity = null;
		int j = getDAdjList.size();
		int i = 0;
		while (i < j) {
			getEntity = getDAdjList.get(i).toString();
			
			if ((getEntity.contains("Entity"))
					|| (getEntity.contains("iof>place")) ){
		//		System.out.println("getEntity:"+getEntity);
				if(entityList.isEmpty()){
					entityList.add(getEntity);
				}else if(entityList.contains(getEntity)){
					int k = entityList.indexOf(getEntity);
					entityList.remove(k);
					entityList.add(entityList.size()-1, getEntity);
				}else{
					entityList.add(getEntity);
				}
				//entityList.add(getEntity);
			}
			i++;
		}
	//	System.out.println("EntityList:"+entityList);
		return entityList;
	}
}
