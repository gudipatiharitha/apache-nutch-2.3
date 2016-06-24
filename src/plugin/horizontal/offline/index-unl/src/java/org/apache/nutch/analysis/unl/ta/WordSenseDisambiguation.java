package org.apache.nutch.analysis.unl.ta;

import java.io.*;
import java.lang.*;
import java.util.*;

public class WordSenseDisambiguation {

	// NounDisambiguation wsdNoun;
	// VerbDisambiguation wsdVerb;

	ArrayList<String> rootnew;
	ArrayList<String> posnew;
	ArrayList<String> hwnew;
	ArrayList<String> uwnew;
	ArrayList<String> analnew;
	ArrayList<String> tuidnew;
	ArrayList<String> cuidnew;
	ArrayList<LinkedList> sense_List;

	LinkedList sense_ll;// = new LinkedList();

	String prev_tw;
	String prev_anal;
	String prev_pos;
	String prev_hw;
	String prev_uw;

	String next_anal;
	String next_pos;
	String next_hw;
	String next_uw;

	public WordSenseDisambiguation() {
		// wsdNoun = new NounDisambiguation();
		// wsdVerb = new VerbDisambiguation();
	}

	public void init() {
		try {
			rootnew = new ArrayList<String>();
			posnew = new ArrayList<String>();
			hwnew = new ArrayList<String>();
			uwnew = new ArrayList<String>();
			analnew = new ArrayList<String>();
			tuidnew = new ArrayList<String>();
			cuidnew = new ArrayList<String>();
			sense_List = new ArrayList<LinkedList>();

			sense_ll = new LinkedList();

			prev_tw = "";
			prev_anal = "";
			prev_pos = "";
			prev_hw = "";
			prev_uw = "";

			next_anal = "";
			next_pos = "";
			next_hw = "";
			next_uw = "";
		} catch (Exception e) {

		}
	}

	public LinkedList processWSD(ArrayList<String> anal,
			ArrayList<String> root, ArrayList<String> pos,
			ArrayList<String> hw, ArrayList<String> uw, ArrayList<String> tuid, ArrayList<String> cuid,
			ArrayList<LinkedList> senseList) {
		LinkedList ll_wsd = new LinkedList();
		try {
			init();
			rootnew.addAll(root);
			posnew.addAll(pos);
			hwnew.addAll(hw);
			uwnew.addAll(uw);
			tuidnew.addAll(tuid);
			cuidnew.addAll(cuid);
			analnew.addAll(anal);
			sense_List.addAll(senseList);
			// System.out.println("Inside WSD:"+root+"\n"+pos+"\n"+hw+"\n"+uw+"\n"+senseList);
			getMorph();
			// getSenses();
			disambiguationProcess();
			ll_wsd = sense_ll;
		} catch (Exception e) {

		}
		// System.out.println("ll_wsd^^^^^^^^^^^^^:"+ll_wsd);
		return ll_wsd;
	}

	public void getMorph() {
		try {
			// System.out.println("Inside getMorph:" + analnew);
		} catch (Exception e) {

		}
	}

	public LinkedList check_SenseList(LinkedList get_ll) {
		LinkedList result_ll = new LinkedList();
		try {
			// int inc = 0;
			// Iterator iter = get_ll.iterator();
			for (int inc = 0; inc < get_ll.size(); inc++) {
				// inc++;
				LinkedList temp_ll = (LinkedList) get_ll.get(inc);
				if (temp_ll.contains("icl>person")) {
					// System.out.println("INSIDE IF********get_ll*******");
					// System.out.println("CORRECT SENSE PERSON:----------->"+temp_ll);
					result_ll.addAll(temp_ll);
					break;
				} else if (temp_ll.contains("icl>number")) {
					// System.out.println("CORRECT SENSE RIVER:----------->"+temp_ll);
					result_ll.addAll(temp_ll);
					break;
				} else if (temp_ll.contains("river")) {
					// System.out.println("CORRECT SENSE RIVER:----------->"+temp_ll);
					result_ll.addAll(temp_ll);
					break;
				} else if ((temp_ll.contains("food"))
						|| (temp_ll.contains("fruit"))) {
					result_ll.addAll(temp_ll);
					break;
				} else if ((temp_ll.contains("god"))
						|| (temp_ll.contains("goddess"))) {
					result_ll.addAll(temp_ll);
					break;
				} else if (temp_ll.contains("metal")) {
					result_ll.addAll(temp_ll);
					break;
				} else if (temp_ll.contains("color")) {
					result_ll.addAll(temp_ll);
					break;
				} else if ((temp_ll.contains("date"))
						|| (temp_ll.contains("time"))
						|| (temp_ll.contains("month"))
						|| (temp_ll.contains("period"))) {
					result_ll.addAll(temp_ll);
					break;
				} else if ((temp_ll.contains("place"))
						|| (temp_ll.contains("city"))
						|| (temp_ll.contains("district"))
						|| (temp_ll.contains("area"))) {
					result_ll.addAll(temp_ll);
					break;
				}
			}
		} catch (Exception e) {

		}
		return result_ll;
	}

	public LinkedList returnFirstHit(LinkedList get_ll) {
		LinkedList fh_ll = new LinkedList();
		try {
			fh_ll = (LinkedList) get_ll.get(0);
		} catch (Exception e) {

		}
		// System.out.println("FIRSTHIT:"+fh_ll);
		return fh_ll;
	}

	public void disambiguationProcess() {
		try {
			LinkedList polysemy_ll = new LinkedList();
			int indx = 0;
			String searchStr = "$$$";
			String currItem = "";
			// Iterator iter = hwnew.iterator();
			for (int i = 0; i < hwnew.size(); i++) {
				indx++;
				currItem = hwnew.get(i).toString();
				if (currItem.equals(searchStr)) {
					LinkedList get_ll = new LinkedList();
					try {
						// System.out.println("SENSES:------->" + sense_List +
						// "\t"
						// + sense_List.size());
						// int in = hwnew.indexOf(currItem);
						Iterator iter1 = sense_List.iterator();
						while (iter1.hasNext()) {
							get_ll = (LinkedList) iter1.next();
							// sense_ll.add(get_ll);
							polysemy_ll = Rules(i, get_ll);
							// System.out.println("POLYSEMY:"+polysemy_ll);
							sense_ll.add(polysemy_ll);
							// System.out.println("Sense_ll:--------->"+sense_ll);
						}
					} catch (Exception e) {

					}
				}
			}
		} catch (Exception e) {

		}
		// return sense_ll;
	}

	public void cooccurrence(int indx) {
		try {
			int prev = indx - 1;
			int next = indx + 1;
			// if (indx > 0) {
			prev_tw = rootnew.get(prev).toString();
			prev_anal = analnew.get(prev).toString();
			prev_pos = posnew.get(prev).toString();
			prev_hw = hwnew.get(prev).toString();
			prev_uw = uwnew.get(prev).toString();
			// }
			// if (indx < rootnew.size() - 1) {
			next_anal = analnew.get(next).toString();
			next_pos = posnew.get(next).toString();
			next_hw = hwnew.get(next).toString();
			next_uw = uwnew.get(next).toString();
			// }
		} catch (Exception e) {

		}
	}

	public void clearList() {
		prev_tw = "";
		prev_anal = "";
		prev_pos = "";
		prev_hw = "";
		prev_uw = "";

		next_anal = "";
		next_pos = "";
		next_hw = "";
		next_uw = "";
	}

	public LinkedList Rules(int indx, LinkedList get_ll) {
		String rword = rootnew.get(indx).toString();
		String curranal = analnew.get(indx).toString();
		String currpos = posnew.get(indx).toString();
		cooccurrence(indx);
		LinkedList ll = new LinkedList();// = getSenses();
		try {
			if (prev_pos.contains("Entity")) {
				ll = check_SenseList(get_ll);
			} else if (prev_uw.contains("number")) {
				ll = check_SenseList(get_ll);
			} else if ((prev_uw.contains("person"))) {
				if (next_uw.contains("person")) {
					ll = check_SenseList(get_ll);
					// clearList();
				} else {
					ll = check_SenseList(get_ll);
				}
			} else if ((prev_uw.contains("god"))
					|| (prev_uw.contains("goddess"))) {
				if ((next_uw.contains("god")) || (next_uw.contains("goddess"))) {
					ll = check_SenseList(get_ll);
				} else {
					ll = check_SenseList(get_ll);
				}
			} else if ((prev_uw.contains("document"))
					|| (prev_uw.contains("book"))
					|| (prev_uw.contains("language"))) {
				ll = check_SenseList(get_ll);
			} else if (prev_anal.indexOf("ஆவது") != -1) {
				ll = check_SenseList(get_ll);
				// PositionSense(sword);
			} else if ((prev_uw.indexOf("food") != -1)
					|| (prev_uw.indexOf("fruit") != -1)
					|| (prev_hw.indexOf("fruit") != -1)
					|| (prev_hw.indexOf("food") != -1)) {
				if ((next_uw.indexOf("food") != -1)
						|| (next_uw.indexOf("fruit") != -1)
						|| (next_hw.indexOf("fruit") != -1)
						|| (next_hw.indexOf("food") != -1)) {
					ll = check_SenseList(get_ll);
				} else {
					ll = check_SenseList(get_ll);
				}
			} else if ((prev_uw.indexOf("metal") != -1)
					|| (prev_hw.indexOf("metal") != -1)) {
				if ((next_uw.indexOf("metal") != -1)
						|| (next_hw.indexOf("metal") != -1)) {
					ll = check_SenseList(get_ll);
				} else {
					ll = check_SenseList(get_ll);
				}
			} else if (prev_uw.indexOf("date") != -1) {
				ll = check_SenseList(get_ll);
			} else if ((prev_anal.indexOf("Numbers") != -1)
					|| (prev_anal.contains("charNumbers"))) {
				ll = check_SenseList(get_ll);
			} else if ((prev_anal.indexOf("Entity") != -1)
					|| (prev_uw.contains("number"))
					|| (prev_uw.contains("period"))
					|| (prev_uw.contains("building"))
					|| (prev_hw.contains("place"))) {
				ll = check_SenseList(get_ll);
			} else if ((prev_uw.contains("color"))) {
				if ((next_uw.contains("color"))) {
					ll = check_SenseList(get_ll);
				} else {
					ll = check_SenseList(get_ll);
				}
			} else if (prev_tw.equals("பெரிய")) {
				ll = check_SenseList(get_ll);
			} else if (next_anal != null) {
				if (next_anal.contains("Plural Suffix")) {
					ll = check_SenseList(get_ll);
					next_anal = "";
				}
			} else {
				ll = returnFirstHit(get_ll);
			}
			clearList();
		} catch (Exception e) {
			ll = returnFirstHit(get_ll);
		}
		if (ll.isEmpty()) {
			ll = returnFirstHit(get_ll);
		}
		return ll;
	}
}
