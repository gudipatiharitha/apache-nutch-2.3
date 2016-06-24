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
public class NamedEntityRecognizer {
	private static NamedEntityRecognizer neRecognizer;
	private HashMap<String, HashSet<String>> neMap;

	private NamedEntityRecognizer() {
		neMap = new HashMap<String, HashSet<String>>();

	}

	public static synchronized NamedEntityRecognizer getInstance() {

		if (neRecognizer == null) {
			neRecognizer = new NamedEntityRecognizer();
		}
		// System.out.println("get instance of mweR called");
		return neRecognizer;
	}

	/**
	 * @param lang
	 *            Language of String in content
	 * @param conf
	 *            Nutch configuration object
	 * @param content
	 *            The string in which named entities have to be identified
	 * @return Array of named Entities found out with a window of 5
	 */
	public String[] recognizeNamedEntities(String lang, Configuration conf,
			String content) {
		HashSet<String> namedEntitiesIdentified = new HashSet<String>();
		HashSet<String> neList = neMap.get(lang);
		if (neList == null) {
			// System.out.println("******loading " + lang + "_mwe.txt *******");
			neList = readNEs(conf.get("NER", "") + lang + "_ne.txt");
			System.out.println(neList.size());
			neMap.put(lang, neList);
		}
		String[] terms = content.split(" ");

		for (int i = 0; i < terms.length; i++) {
			if (neList.contains((terms[i]).trim())) {
				namedEntitiesIdentified.add((terms[i]).trim());
			}
			if (i >= 1) {
				if (neList.contains((terms[i - 1] + " " + terms[i]).trim())) {
					namedEntitiesIdentified.add((terms[i - 1] + " " + terms[i])
							.trim());
				}
			}
			if (i >= 2) {
				if (neList
						.contains((terms[i - 2] + " " + terms[i - 1] + " " + terms[i])
								.trim())) {
					namedEntitiesIdentified.add((terms[i - 2] + " "
							+ terms[i - 1] + " " + terms[i]).trim());
				}
			}
			if (i >= 3) {
				if (neList.contains((terms[i - 3] + " " + terms[i - 2] + " "
						+ terms[i - 1] + " " + terms[i]).trim())) {
					namedEntitiesIdentified.add((terms[i - 1] + " " + terms[i])
							.trim());
				}
			}
			if (i >= 4) {
				if (neList.contains((terms[i - 4] + " " + terms[i - 3] + " "
						+ terms[i - 2] + " " + terms[i - 1] + " " + terms[i])
						.trim())) {
					namedEntitiesIdentified.add((terms[i - 4] + " "
							+ terms[i - 3] + " " + terms[i - 2] + " "
							+ terms[i - 1] + " " + terms[i]).trim());
				}
			}
		}
		String[] nesIdentified = new String[namedEntitiesIdentified.size()];
		nesIdentified = namedEntitiesIdentified.toArray(nesIdentified);
		return nesIdentified;
	}

	public HashSet<String> readNEs(String fileName) {
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
