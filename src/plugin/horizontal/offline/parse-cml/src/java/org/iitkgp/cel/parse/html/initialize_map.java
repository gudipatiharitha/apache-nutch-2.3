package org.iitkgp.cel.parse.html;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class initialize_map {
	public static void main(String[] args0){
		Map<String, String> ldapContent = new HashMap<String, String>();
		ldapContent.put("key", "value");
		Properties properties = new Properties();

		for (Map.Entry<String,String> entry : ldapContent.entrySet()) {
		    properties.put(entry.getKey(), entry.getValue());
		}

		try {
			properties.store(new FileOutputStream("data.properties"), null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
