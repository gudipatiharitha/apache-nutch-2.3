/**
 * 
 */
package org.apache.nutch.analysis.unl.ta;


import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author ubuntu
 * 
 */
public class UNL_Attributes {
	
	Acqusition_of_Patterns aop = new Acqusition_of_Patterns();

	ArrayList store_Tokens;
	ArrayList word_features;
	ArrayList morph_features;
	ArrayList m_feature_set;// = new ArrayList();
	ArrayList w_feature_set;// = new ArrayList();
	ArrayList words_list;
	ArrayList pos_list;
	ArrayList result_arr;
	ArrayList unl_attr_List;

	String m_word;
	String m_pos;
	String prev;
	String next;
	String curr_word;

	String result = "";
	String features = "";

	public void init() {

		prev = "";
		next = "";
		m_word = "";
		m_pos = "";

		store_Tokens = new ArrayList();
		// word_features = new ArrayList();
		// morph_features = new ArrayList();
		m_feature_set = new ArrayList();
		w_feature_set = new ArrayList();
		pos_list = new ArrayList();
		words_list = new ArrayList();
		result_arr = new ArrayList();
		unl_attr_List = new ArrayList();
	}

	public ArrayList process_Analysed_Features(ArrayList anal) {
		// System.out.println("anal:"+anal);
		init();
		int i = 0;
		int tot_cnt = anal.size();

		while (i < tot_cnt) {
			// word_features.clear();
			// morph_features.clear();
			word_features = new ArrayList();
			morph_features = new ArrayList();
			String anal_value = anal.get(i).toString();
			StringTokenizer strToken1 = new StringTokenizer(anal_value, ">");

			while (strToken1.hasMoreTokens()) {
				String getNext = strToken1.nextToken();
				// features = getNext;
				// System.out.println("getNext:" + features);
				if ((getNext.contains("<")) && (getNext.contains("&"))) {
					StringTokenizer strToken4 = new StringTokenizer(getNext,
							"<&");
					String first_word = strToken4.nextToken("<");
					String next_word = strToken4.nextToken("&");
					// System.out.println("First Word:"+first_word);
					// System.out.println("Next Word:"+next_word);
					first_word = first_word.replace(":", "");
					next_word = next_word.replace("<", "");
					word_features.add(first_word.trim());
					morph_features.add(next_word.trim());
				}
			}
			m_feature_set.add(morph_features);
			w_feature_set.add(word_features);

			System.out.println("Word_Features:------>"
					+ word_features.toString());
			System.out.println("Morph_Features:------>"
					+ morph_features.toString());

			// word_features.clear();
			// morph_features.clear();
			i++;
		}

		// System.out.println("Word_Feature Set:------>"+w_feature_set);
		// System.out.println("Morph_Feature Set:------>"+m_feature_set);

		/**
		 * for(Iterator m_iterate = m_feature_set.iterator();
		 * m_iterate.hasNext();){ ArrayList morph = (ArrayList)m_iterate.next();
		 * System.out.println("Morph:==================>>>>>>>:"+morph);
		 * for(Iterator m1_iterate = morph.iterator();m1_iterate.hasNext();){
		 * ArrayList morph1 = (ArrayList)m1_iterate.next();
		 * System.out.println("Morph1:==================>>>>>>>:"+morph1); } }
		 */
		POS_Disambiguation();

		System.out.println("After Disambiguation Word_Feature Set:------>"
				+ w_feature_set);
		System.out.println("After Disambiguation Morph_Feature Set:------>"
				+ m_feature_set);
		// attribute_Rules();
	//	aop.pattern_Acqusition(w_feature_set,m_feature_set);
		find_Multiword();

		
		System.out.println("Words List:------>" + words_list);
		System.out.println("POS list:------>" + pos_list);

		return result_arr;
	}

	public void POS_Disambiguation() {
		for (int i = 0; i < m_feature_set.size(); i++) {
			ArrayList morph = (ArrayList) m_feature_set.get(i);
			ArrayList word = (ArrayList) w_feature_set.get(i);
			// System.out.println("MORPH SIZE:"+morph);
			// if(morph.size()==1){

			// }else{
			// String get_morph = morph.get(i).toString();
			// String get_word = word.get(i).toString();
			if ((morph.contains("Verb")) && (morph.contains("Noun"))) {
				// int j = i-1;
				// System.out.println("MORPH--------------------->:"+morph);
				if (i != 0) {
					ArrayList morph1 = (ArrayList) m_feature_set.get(i - 1);
					prev = morph1.get(0).toString();
				}
				ArrayList morph2 = (ArrayList) m_feature_set.get(i + 1);
				next = morph2.get(0).toString();
				ArrayList word_list1 = (ArrayList) w_feature_set.get(i);
				curr_word = word_list1.get(0).toString();
				if ((prev.equals("Noun")) && (next.equals("Noun"))) {
					// System.out.println("PREV:=============>>:"+prev);
					// String res_morph = "[" + prev + "]";
					// String res_word = word1 + "]";
					m_feature_set.remove(i);
					morph.clear();
					morph.add(prev);
					m_feature_set.add(i, morph);
					w_feature_set.remove(i);
					word.clear();
					word.add(curr_word);
					w_feature_set.add(i, word);
					// w_feature_set.add(i, res_word);
				}
			} else {
				// System.out.println("ELSE MORPH===========>>>>>>>"+morph);
			}
			// }
		}
	}

	public void find_Multiword() {
		for (int i = 0; i < m_feature_set.size(); i++) {
			ArrayList morph = (ArrayList) m_feature_set.get(i);
			ArrayList word = (ArrayList) w_feature_set.get(i);
			// System.out.println("INSIDE MULTIWORD:"+m_feature_set);
			// System.out
			// .println("morph------==========---------->>>><<<<<<<<>>>>>>>>>"
			// + morph);
			if (morph.isEmpty()) {

			} else {
				String get_morph = morph.get(0).toString().trim();
				String get_mword = word.get(0).toString().trim();
				if (morph.size() == 1) {
					if (get_morph.equals("Noun")) {
						m_word += get_mword + " ";
						m_pos += get_morph + " ";
					} else if (get_morph.equals("Entity")) {
						if (m_pos.isEmpty()) {
							m_word += get_mword + " ";
							m_pos += get_morph + " ";
						} else if (m_pos.contains("Noun")) {
							words_list.add(m_word.trim());
							// words_list.add(get_mword.trim());
							pos_list.add(m_pos.trim());
							// pos_list.add(get_morph.trim());
							m_word = "";
							m_pos = "";
							m_word += get_mword + " ";
							m_pos += get_mword + " ";

						} else if (m_pos.contains("Entity")) {
							m_word += get_mword + " ";
							m_pos += get_morph + " ";
						} else {
							words_list.add(m_word.trim());
							words_list.add(get_mword.trim());
							pos_list.add(m_pos.trim());
							pos_list.add(get_morph.trim());
							m_word = "";
							m_pos = "";
						}
					} else {
						words_list.add(m_word.trim());
						words_list.add(get_mword.trim());
						pos_list.add(m_pos.trim());
						pos_list.add(get_morph.trim());
						m_word = "";
						m_pos = "";
					}
				} else {
					if (m_pos.contains("Noun")) {
						if (get_morph.contains("Noun")) {
							m_word += get_mword + " "
									+ word.subList(1, word.size());
							m_pos += get_morph + " "
									+ morph.subList(1, morph.size());

							words_list.add(m_word);
							// words_list.add(get_mword);
							pos_list.add(m_pos);
							// pos_list.add(get_morph);
							m_word = "";
							m_pos = "";
						} else {
							words_list.add(m_word);
							words_list.add(word);
							pos_list.add(m_pos);
							pos_list.add(morph);
							m_word = "";
							m_pos = "";
						}
					} else if (m_pos.contains("Entity")) {
						if ((get_morph.contains("Entity"))
								|| (get_morph.contains("Noun"))) {
							m_word += get_mword + " ";
							m_pos += get_morph + " ";

							words_list.add(m_word);
							// words_list.add(get_mword);
							pos_list.add(m_pos);
							// pos_list.add(get_morph);
							m_word = "";
							m_pos = "";
						} else {
							words_list.add(m_word);
							words_list.add(word);
							pos_list.add(m_pos);
							pos_list.add(morph);
							m_word = "";
							m_pos = "";
						}
					} else {
						words_list.add(m_word);
						words_list.add(word);
						pos_list.add(m_pos);
						pos_list.add(morph);
						m_word = "";
						m_pos = "";
					}
				}
			}
		}
	}

	public ArrayList attribute_Rules(ArrayList anal, ArrayList root) {
		ArrayList unl_attr = new ArrayList();		
		try {
			
			String attribute = "";
			for (int i = 0; i < anal.size(); i++) {
				String analysed = anal.get(i).toString().trim();
				String tempword = root.get(i).toString().trim();
			//	String uword = uw.get(j).toString();
				if ( (analysed.contains("Noun")) || (analysed.contains("Entity")) ){
					attribute += ".@Noun";
				}
				if (analysed.contains("Verb")) {
					attribute += ".@Verb";
				}
				if (analysed.contains("Adjective")) {
					attribute += ".@Adjective";
				}
				if (analysed.contains("Adverb")) {
					attribute += ".@Adverb";
				}
				if ((analysed.indexOf("இல்") != -1)
						|| (analysed.indexOf("ஆக") != -1)
						|| (analysed.indexOf("இடம்") != -1)) {
					if ((analysed.indexOf("இருந்து") != -1)) {
						attribute += ".@from";
					}
					if (analysed.indexOf("உள்ள") != -1) {

					}
					if (analysed.indexOf("உம்") != -1) {

					} else {
						attribute += ".@in.@at";
					}
				}
				if ((analysed.indexOf("படு") != -1)
						&& (analysed.indexOf("அது") != -1)) {
					attribute += ".@passive";
				}
				if (analysed.indexOf("ஆல்") != -1) {
					attribute += ".@by";
				}
				if ((analysed.indexOf("உடன்") != -1)
						|| (analysed.indexOf("ஓடு") != -1)) {
					attribute += ".@with";
				}
				if (analysed.indexOf("இன்") != -1) {
					attribute += ".@of";
				}
				if ((analysed.indexOf("உடைய") != -1)
						|| (analysed.indexOf("அது") != -1)) {
					attribute += ".@of";
				}
				if ((tempword.equals("மட்டுமல்லாமல்"))
						|| (tempword.equals("மற்றும்"))
						|| (tempword.equals("மேலும்"))) {
					attribute += ".@and";
				}
				if (tempword.equals("வழியாக")) {
					attribute += ".@through.@via";
				}
				if ((tempword.equals("காட்டிலும்")) || (tempword.equals("விட"))) {
					attribute += ".@than";
				}
				if ((tempword.equals("முதல்")) || (tempword.equals("இருந்து"))) {
					attribute += ".@from.@since";
				}
				if ((tempword.equals("ஆகிய")) || (tempword.equals("போன்ற"))
						|| (tempword.equals("முதலிய"))) {
					attribute += ".@as.@like";
				}
				if ((tempword.equals("அப்புறம்"))
						|| (tempword.equals("முன்னால்"))
						|| (tempword.equals("பின்னால்"))
						|| (tempword.equals("பின்னர்"))
						|| (tempword.equals("பின்பு"))) {
					attribute += ".@after.@behind";
				}
				if ((tempword.equals("அல்லாத"))
						|| (tempword.equals("நீங்கலாக"))) {
					attribute += ".@apart_from.@barring.@except.(@except.@if)";
				}
				if ((tempword.equals("பொழுது")) || (tempword.equals("போது"))) {
					attribute = ".@during.@while";
				}
				if (tempword.equals("வரை")) {
					attribute += ".@to";
				}
				if (tempword.equals("அல்லது")) {
					attribute += ".@or";
				}
				if (tempword.equals("நிறைய")) {
					attribute += ".@qua";
				}
				if ((tempword.equals("உள்ளிட்ட")) || (tempword.equals("உள்பட"))
						|| (tempword.equals("உட்பட"))) {
					attribute += ".@including";
					// unl_attributes.add(attribute);

				}
				if ((tempword.equals("இடையில்")) || (tempword.equals("இடையே"))) {
					attribute += ".@amid.@among.@between";
					// unl_attributes.add(attribute);

				}
				if (tempword.equals("போதிலும்")) {
					attribute += ".@despite";
				}
				if( (tempword.equals("முடியும்")) || (tempword.equals("கூடும்")) ){
					if(anal.get(i-1).toString().contains("Relative Participle Suffix") ){
						attribute += ".@ability";
					}
					if(anal.get(i-1).toString().contains("உம்") ){
						attribute += ".@possibility";
					}		
					if(analysed.contains("Clitic")){
						attribute += ".@request";
					}
				}
				if( (tempword.equals("ஆகலாம்")) || (tempword.equals("முடியலாம்")) ){
					attribute += ".@probability";
				}
				if(tempword.equals("வேண்டும்")){
					attribute += ".@deduction";
				}
		/*		if(uword.contains("person")){
					attribute += ".@3";
				} */
				if( (analysed.contains("ஆன்")) || (analysed.contains("ஆர்")) ){
					attribute += ".@3.@male";
				}
				if(analysed.contains("ஆள்")){
					attribute += ".@3.@female";
				}
				if(analysed.contains("First Person Singular")){
					attribute += ".@1";
				}
				if(analysed.contains("First Person Plural")){
					attribute += ".@1.@pl";
				}
				if(analysed.contains("Second Person Singular")){
					attribute += ".@2";
				}
				if(analysed.contains("Second Person Plural")){
					attribute += ".@2.@pl";
				}
				unl_attr.add(i, attribute);
				attribute = "";
			}
			
		} catch (Exception e) {

		}
		return unl_attr;
	}
}
