package org.apache.nutch.analysis.unl.ta;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.FileReader;
import org.apache.hadoop.conf.Configuration;

public class Acqusition_of_Patterns {
	String result = "";
//	Hashtable hash = new Hashtable();
	ArrayList hash = new ArrayList();
	ArrayList value_inc = new ArrayList();
	int count = 0;
	public String pattern_Acqusition(ArrayList words_set, ArrayList pos_set) {
		// int i = 0;
		String get_pos = "";
		String next_pos = "";
		String get_fw = "";
		String get_nw = "";
		int tot_cnt = pos_set.size();
		// System.out.println("SET:"+pos_set);
		for (int i = tot_cnt; i > 0; i--) {
			// System.out.println("INSIDE FOR LOOP 1");
			ArrayList word = (ArrayList) words_set.get(i-1);
			ArrayList morph = (ArrayList) pos_set.get(i-1);
			get_pos = morph.get(0).toString();
			if (!(get_pos.isEmpty())) {
				get_fw = words_set.get(i - 1).toString();
			//}
				for (int j = i - 1; j > 0; j--) {
					// System.out.println("INSIDE FOR LOOP 2");
					ArrayList next_morph = (ArrayList) pos_set.get(j-1);
					next_pos = next_morph.get(0).toString();
					if (!(next_pos.isEmpty())) {
						get_nw = words_set.get(j - 1).toString();
						System.out.println(get_fw + " " + get_nw);
					}
				}
			}
		}
		/**
		 * while(i < tot_cnt){ ArrayList word = (ArrayList)words_set.get(i);
		 * ArrayList morph = (ArrayList)pos_set.get(i); //
		 * if(!(morph.isEmpty())){ // if(morph.size()==1){ get_pos =
		 * morph.get(0).toString(); if(get_pos != null){
		 * if(get_pos.equals("Verb")){ // if( (get_pos.contains("Entity")) ||
		 * (get_pos.contains("Noun")) ){
		 * System.out.println("AOP ======= morph:"+morph+"----->"+i); }else{
		 * 
		 * } }
		 * 
		 * // }else if (get_pos.equals("Verb")){
		 * 
		 * // } // } i++; }
		 */
		return result;
	}
	
	public void readFile(){
		try{
			String str = "";
			BufferedReader buff = new BufferedReader(new FileReader(org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome()+"resource/unl/BWA_Prob/PATTERNS/Input_Patterns.txt"));
			while((str=buff.readLine())!=null){
		//		System.out.println("get str");
			//	loadintoHash(str);
				
				if(hash.isEmpty()){
					int cnt = 1;
					hash.add(str);
					value_inc.add(Integer.toString(cnt));
				}else if(hash.contains(str)){
					int ind = hash.indexOf(str);
					int inc = Integer.parseInt(value_inc.get(ind).toString());
					inc++;
					value_inc.add(ind,Integer.toString(inc));
				}else{
					int cnt = 1;
					hash.add(str);
					value_inc.add(Integer.toString(cnt));
				}				
			}
			for(int i = 0; i < hash.size(); i++){
				String key = hash.get(i).toString();
				String value = value_inc.get(i).toString();
				System.out.println(key + "\t"+value);
			}
		/**	String res = hash.toString();
			res = res.replace(", (", "\n(");
			System.out.println(res); */
		}catch(Exception e){
			
		}
	}

	/**public void loadintoHash(String st){
		try{
			int cnt = 1;
			if(hash.isEmpty()){
				hash.put(st, cnt);
			}else if(hash.contains(st)){
				int inc = Integer.parseInt(hash.get(st).toString());
				inc++;
				hash.put(st, inc);
			}else{
				hash.put(st, cnt);
			}
			
		}catch(Exception e){
			
		}
		
		//return result;
	}	*/
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Acqusition_of_Patterns aop = new Acqusition_of_Patterns();
		aop.readFile();
	}

}
