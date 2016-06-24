package org.iitkgp.nutch.scoring.content;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Vector;

import org.apache.hadoop.conf.Configuration;

public class MultiWordIdentification {
	
	private Configuration conf;
	
	
	
	public MultiWordIdentification(Configuration conf) {
		this.conf=conf;
	}
	
	public String[] getIdentifiedMWEs(String pageContent, String pageLang) {
		
		String propertyName = null;
		if (pageLang == null || pageLang.equalsIgnoreCase("un"))
			return null;
		propertyName = "MWE_" + pageLang;
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
				
				return null;
			}
		} catch (FileNotFoundException e) {
			
			return null;
		}
		HashMap<String, Integer> list_content = new HashMap<String, Integer>();
		String[] wordlist = pageContent.split(" ");
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
		String MWE = null;
		Vector<String> namedList = new Vector<String>();
		try {
			while ((MWE = br.readLine()) != null) {

				MWE = MWE.trim();

				Integer offset = list_content.get(MWE);
				if (offset != null) {
					namedList.add(MWE);
				}
			}
			br.close();
		} catch (IOException e) {
		}
		String[] finalList = null;
		if (namedList == null)
			return null;
		finalList = namedList.toArray(new String[namedList.size()]);
		return finalList;
	}

}
