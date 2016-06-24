package org.iitkgp.nutch.scoring.content;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.hadoop.conf.Configuration;

public class NamedEntityIdentification {
private Configuration conf;
	
	
	public NamedEntityIdentification(Configuration conf) {
		this.conf=conf;
	}
	
	public String[] getIdentifiedNEs(String content, String lang) {
		String[] NEs = null;
		NEs = getNELookUp(NEs, content, lang);
		return NEs;
	}
	
	private String[] getNELookUp(String[] nEs, String content, String lang) {
		// TODO Auto-generated method stub
		String propertyName = null;
		if (lang == null || lang.equalsIgnoreCase("un")
				|| lang.equalsIgnoreCase("en"))
			return nEs;
		propertyName = "NER_" + lang;
		String fileName = conf.get(propertyName);
		if (fileName == null)
			return null;
		File file = new File(fileName);
		BufferedReader br = null;
		try {
			try {
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(file), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		}
		HashMap<String, Integer> list_content = new HashMap<String, Integer>();
		String[] wordlist = content.split(" ");
		for (int counter = 0; counter < wordlist.length; counter++) {
			list_content.put(wordlist[counter], counter);
			if (counter >= 1) {
				list_content.put(wordlist[counter - 1] + " "
						+ wordlist[counter], counter - 1);
			}
			if (counter >= 2) {
				list_content.put(wordlist[counter - 2] + " "
						+ wordlist[counter - 1] + " " + wordlist[counter],
						counter - 2);
			}
			if (counter >= 3) {
				list_content.put(wordlist[counter - 3] + " "
						+ wordlist[counter - 2] + " " + wordlist[counter - 1]
						+ " " + wordlist[counter], counter - 3);
			}
			if (counter >= 4) {
				list_content.put(
						wordlist[counter - 4] + " " + wordlist[counter - 3]
								+ " " + wordlist[counter - 2] + " "
								+ wordlist[counter - 1] + " "
								+ wordlist[counter], counter - 4);
			}
		}
		String NE = null;
		Vector<String> namedList = new Vector<String>();
		try {
			while ((NE = br.readLine()) != null) {

				NE = NE.trim();

				Integer offset = list_content.get(NE);
				if (offset != null) {
					namedList.add(NE);
				}
			}
			br.close();
		} catch (IOException e) {
		}

		String[] finalList = null;
		if (namedList == null)
			return nEs;
		finalList = namedList.toArray(new String[namedList.size()]);
		finalList = mergeStringArrays(nEs, finalList);
		return finalList;
	}
	
	/**
	 * This String utility or util method can be used to merge 2 arrays of
	 * string values. If the input arrays are like this array1 = {"a", "b" ,
	 * "c"} array2 = {"c", "d", "e"} Then the output array will have {"a", "b" ,
	 * "c", "d", "e"}
	 * 
	 * This takes care of eliminating duplicates and checks null values.
	 * 
	 * @param values
	 * @return
	 */
	public static String[] mergeStringArrays(String array1[], String array2[]) {
		if (array1 == null || array1.length == 0)
			return array2;
		if (array2 == null || array2.length == 0)
			return array1;
		List array1List = Arrays.asList(array1);
		List array2List = Arrays.asList(array2);
		List result = new ArrayList(array1List);
		List tmp = new ArrayList(array1List);
		tmp.retainAll(array2List);
		result.removeAll(tmp);
		result.addAll(array2List);
		return ((String[]) result.toArray(new String[result.size()]));
	}

}
