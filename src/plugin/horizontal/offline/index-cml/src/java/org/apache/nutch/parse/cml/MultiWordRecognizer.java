/**
 * 
 */
package org.apache.nutch.parse.cml;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.hadoop.conf.Configuration;

/**
 * @author swapnil
 * 
 */
public class MultiWordRecognizer {
	private static MultiWordRecognizer mweRecognizer;
	private HashMap<String, HashSet<String>> mweMap;

	private MultiWordRecognizer() {
		mweMap = new HashMap<String, HashSet<String>>();

	}

	public static synchronized MultiWordRecognizer getInstance() {

		if (mweRecognizer == null) {
			mweRecognizer = new MultiWordRecognizer();
		}
//		System.out.println("get instance of mweR called");
		return mweRecognizer;
	}
	
	
	/**
	 * @param lang Language of String in content
	 * @param conf Nutch configuration object
	 * @param content The string in which multi words have to be identified
	 * @return Array of multi words found out with a window of 5
	 */
	public String [] recognizeMultiwordEntities(String lang,
			Configuration conf, String content) {
		HashSet<String> multiwordsIdentified = new HashSet<String>();
		HashSet<String> mweList = mweMap.get(lang);
		if (mweList == null) {
//			System.out.println("******loading " + lang + "_mwe.txt *******");
			mweList = readMWEs(conf.get("MWE", "")+ lang + "_mwe.txt");
			System.out.println(mweList.size());
			mweMap.put(lang, mweList);
		}
		String[] terms = content.split(" ");
		
		for (int i = 0; i < terms.length; i++) {
			if (mweList.contains((terms[i]).trim())) {
				multiwordsIdentified.add((terms[i]).trim());
			}
			if(i >=1) {
				if (mweList.contains((terms[i-1]+" "+terms[i]).trim())) {
					multiwordsIdentified.add((terms[i-1]+" "+terms[i]).trim());
				}
			}
			if(i >=2) {
				if (mweList.contains((terms[i-2]+" "+terms[i-1]+" "+terms[i]).trim())) {
					multiwordsIdentified.add((terms[i-2]+" "+terms[i-1]+" "+terms[i]).trim());
				}
			}
			if(i >=3) {
				if (mweList.contains((terms[i-3]+" "+terms[i-2]+" "+terms[i-1]+" "+terms[i]).trim())) {
					multiwordsIdentified.add((terms[i-1]+" "+terms[i]).trim());
				}
			}
			if(i >=4) {
				if (mweList.contains((terms[i-4]+" "+terms[i-3]+" "+terms[i-2]+" "+terms[i-1]+" "+terms[i]).trim())) {
					multiwordsIdentified.add((terms[i-4]+" "+terms[i-3]+" "+terms[i-2]+" "+terms[i-1]+" "+terms[i]).trim());
				}
			}
		}
		String[] mwesIdentified = new String[multiwordsIdentified.size()];
		mwesIdentified = multiwordsIdentified.toArray(mwesIdentified);
		return mwesIdentified;
	}

	public HashSet<String> readMWEs(String fileName) {
		HashSet<String> set = new HashSet<String>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName), "UTF-8"));
			String line = reader.readLine();
			while (line != null) {
				line = line.trim();
				set.add(line);
				line = reader.readLine();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return set;
	}
}
