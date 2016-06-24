package org.apache.nutch.analysis.unl.ta;

import org.apache.nutch.analysis.unl.ta.Analyser;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.FileOutputStream;


public class Analyse_UNL {

	/**
	 * @param args
	 */
	Hashtable tam_head = new Hashtable();
	Hashtable tam_entry = new Hashtable();
	int count = 0;
	int cnt1 = 0;
	ArrayList al = new ArrayList();
	ArrayList filelist = new ArrayList();
	Hashtable hash = new Hashtable();
	String result = "";
	String output = "";
	String prev = "";
	String word1 = "";
	String next = "";
	String get_file = "";
	PrintStream  ps;
	PrintStream  ps1;
	PrintStream  ps2;
	PrintStream  ps3;
	PrintStream  true_output;
	PrintStream  false_output;
	public String get_Words(String st){
		try{
			
		//	st = st.replaceAll("[^0-9.,]+","");
			StringTokenizer strToken1 = new StringTokenizer(st, " ");
			int count = strToken1.countTokens();
			int initial = 0;
			while(initial < count){
				String word = strToken1.nextToken().trim();
			/**	if(word.endsWith(".")){
					word = word.replaceAll(".","");
				}	*/			
			//	if(!word.isEmpty()){
				String analysed = org.apache.nutch.analysis.unl.ta.Analyser.analyseF(word.trim(), true);
			//	System.out.println("analysed:" + analysed);
				StringTokenizer strToken2 = new StringTokenizer(analysed,":\n██—<>,=+»←→↓↑•*;:?-.'“\"&", false);

				int firstindex = analysed.indexOf('<', analysed.indexOf('>'));
				if( (analysed.contains("Unknown")) || (analysed.contains("count=4")) || (analysed.contains("count=3")) ){
					next = word;
				}else{
					prev = strToken2.nextToken().trim();
					word1 = prev;
					next = strToken2.nextToken().trim();
				}
				if(tam_head.containsKey(next)){
					
				}else if(tam_entry.containsKey(next)){
					
				}else{	
				if(hash.isEmpty()){
					int cnt1 = 1;
					hash.put(next, cnt1);
					if(next.contains(".")){
						next = next.replace(".", "");
						if(next != null){
							ps1.append(next+"\n");
						}	
					}else{
						if(next!= null){
							ps1.append(next+"\n");
						}	
					}					
				}else if(hash.containsKey(next)){
					int cnt2 = Integer.parseInt(hash.get(next).toString());
					cnt2++;
					hash.remove(next);
					hash.put(next, cnt2);
				}else{
					int cnt3 = 1;
					hash.put(next, cnt3);
					if(next.contains(".")){
						next = next.replace(".", "");
						if(next != null){
							ps1.append(next+"\n");
						}	
					}else{
						if(next != null){
							ps1.append(next+"\n");
						}	
					}
				}
			}
				initial++;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public void readFiles() {
        try {
            File fn = new File("./resource/unl/ta/SentenceExtraction/Input/content/");
            File[] list = fn.listFiles();
            for (int i = 0; i < list.length; i++) {
                String filename = list[i].getName();
                al.add(filename);
            }
            //    System.out.println("Files:"+al+al.size());
        } catch (Exception e) {
        }
    }
	public void get_docs(){
		try{	
			read_FileList();
			get_Duplicates();
			ps = new PrintStream(new FileOutputStream(new File("./resource/unl/ta/invalidFiles.txt"), true));
			ps1 = new PrintStream(new FileOutputStream(new File("./resource/unl/ta/UniqueWords.txt"), true));
			readFiles();
			get_UNL();			
			for(int i = 0; i < al.size(); i++){
				get_file = al.get(i).toString();	
				if(filelist.contains(get_file)){
					
				}else{
					try{
					String fName = "./resource/unl/ta/SentenceExtraction/Input/content/" + get_file;
					File file = new File(fName);
					ps.append(get_file+"\n");
				//	if(file.length() > 50){
						Scanner scanner = new Scanner(file).useDelimiter("\\Z");
						String contents = scanner.next();
						//System.out.println(contents);
						output = get_Words(contents);
						scanner.close();
				//	}	
					}catch(Exception e){
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage()+get_file);
		}
	}
	public void read_FileList(){
		try{
			String comp_file = "";
			BufferedReader in_buff = new BufferedReader(new FileReader("./resource/unl/ta/invalidFiles.txt"));
			while( (comp_file = in_buff.readLine())!=null){
				filelist.add(comp_file);
			}
		}catch(Exception e){
			
		}
	}
	public void get_UNL(){
		try{	
			String value = "1";		
				String uwentry = "";
				BufferedReader buffer = new BufferedReader(new FileReader("./resource/unl/uwdict.txt"));
				while ((uwentry = buffer.readLine()) != null) {
				//	uw.add(uwentry);
					StringTokenizer strTok1 = new StringTokenizer(uwentry,"/");
					cnt1 = strTok1.countTokens();
					//	System.out.println(cnt1);
					String t_word = strTok1.nextToken();
					String h_word = strTok1.nextToken();
					String u_word = strTok1.nextToken(); 			
						count++;
						String t_W_h = t_word+"/"+h_word;
						if(tam_head.isEmpty()){
							tam_head.put(t_word, u_word);
							//System.out.println(t_W_h+"/"+u_word);
							//ps3.println(t_W_h+"/"+u_word);
						}else if(tam_head.containsKey(t_word)){
						//	System.out.println(t_W_h+"/"+u_word);
							//ps4.println(t_W_h+"/"+u_word);
						}else{
							tam_head.put(t_word, u_word);						
						}					
					}					
		}catch(Exception e){			
		}	
	}	
	public void get_Duplicates(){
		try{	
			boolean isTamil = true;
			ps2 = new PrintStream(new FileOutputStream(new File("./resource/unl/ta/WithoutDupEntry.txt"), false));
			ps3 = new PrintStream(new FileOutputStream(new File("./resource/unl/ta/NonTamilEntry.txt"), false));
			String value = "1";		
				String entry = "";
				BufferedReader dup_buffer = new BufferedReader(new FileReader("./resource/unl/ta/UniqueWords.txt"));
				while ((entry = dup_buffer.readLine()) != null) {
					String t_word = entry.trim();
					for (int i = 0; i < t_word.length(); i++)
					{
						int j = t_word.charAt(i);
					
						if((j>57) && (j<=127))
						{
							isTamil = false;
						//	return 0;
						} 
					}
				//	uw.add(uwentry);
					if(isTamil == true){
						
						if(tam_entry.isEmpty()){
							tam_entry.put(t_word, "1");
							ps2.println(t_word);
							//ps3.println(t_W_h+"/"+u_word);
						}else if(tam_entry.containsKey(t_word)){
						//	System.out.println(t_W_h+"/"+u_word);
							//ps4.println(t_W_h+"/"+u_word);
						}else{
							tam_entry.put(t_word, "1");	
							ps2.println(t_word);
						}					
					}else{
						ps3.println(t_word);
						isTamil = true;
					}
				}
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	public void Testing(){
		try{
			true_output = new PrintStream(new FileOutputStream(new File("./resource/unl/ta/true_analysis.txt"), true));
			false_output = new PrintStream(new FileOutputStream(new File("./resource/unl/ta/false_analysis.txt"), true));
			String fName = "./resource/unl/ta/AnalyserUnknown_Unique.txt";
			File file = new File(fName);
				Scanner scanner = new Scanner(file).useDelimiter("\\Z");
				String contents = scanner.next();
			//	System.out.println("Contents:"+contents);
				StringTokenizer test_tok = new StringTokenizer(contents, "\n");
				int tok_count = test_tok.countTokens();
				int tok_i = 0;
				while(tok_i<tok_count){
					String token = test_tok.nextToken().trim();
					String analysed = org.apache.nutch.analysis.unl.ta.Analyser.analyseF(token.trim(), true);
				//	System.out.println("analysed:" + analysed);
					if((analysed.contains("count=0")) || (analysed.contains("count=1")) ){
						true_output.println(token);
					}else{
						false_output.println(token);
					}
					tok_i++;
				}
				true_output.close();
				false_output.close();
				
		}catch(Exception e){			
		}
	}
	public void final_uniqueUnknown_FromAnalyser(){
		try{
			Hashtable final_hash = new Hashtable();
			String get_entry = "";
			BufferedReader buffer_final = new BufferedReader(new FileReader("./resource/unl/ta/Analyser_Unknown.txt"));
			while((get_entry = buffer_final.readLine())!=null){
				if(final_hash.isEmpty()){
					final_hash.put(get_entry, "1");
					System.out.println(get_entry);
				}else if(final_hash.containsKey(get_entry)){
					
				}else{
					final_hash.put(get_entry, "1");
					System.out.println(get_entry);
				}
			}
		}catch(Exception e){
			
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Analyse_UNL unl = new Analyse_UNL();
		unl.get_docs();
	//	unl.get_Duplicates();
		//unl.Testing();
	//	unl.final_uniqueUnknown_FromAnalyser();
	}

}
