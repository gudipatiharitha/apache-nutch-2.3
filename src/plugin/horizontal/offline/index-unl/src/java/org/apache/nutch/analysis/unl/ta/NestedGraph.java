package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.enconversion.unl.ta;

import org.apache.nutch.util.NutchConfiguration;
import org.apache.hadoop.conf.Configuration;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.regex.Pattern;
/**
 * 
 */

/**
 * @author Balaji Jagan
 *
 */
public class NestedGraph {
	ArrayList<String> nRoot;
	ArrayList<String> nAnal;
	ArrayList<String> nReln;
	ArrayList<String> nCRC;
	ArrayList nNum;
	
//	String getCRC="";
	String nResult;
	String concat;
	String nestresult;
	//String id1;
	//String id2;
	
	
	/**
	 * 
	 */
	public NestedGraph() {
		// TODO Auto-generated constructor stub
	}
	public void init(){
		nRoot = new ArrayList<String>();
		nAnal = new ArrayList<String>();
		nReln = new ArrayList<String>();
		nNum = new ArrayList();
		nCRC = new ArrayList<String>();
		
	//	getCRC = "";
		nResult = "";
		concat = "";
		nestresult="";
	//	id1 = "";
	//	id2 = "";
		
	}
	public String processGraph(String result){
	//	System.out.println("Inside Nested UNL");	
		init();
	//	nRoot.addAll(root);
	//	nAnal.addAll(anal);
	//	nReln.addAll(reln);
	//	System.out.println("Nested UNL:"+nRoot+"\n"+nReln+"\n"+num+"\n"+result+"\n");
	//	System.out.println("result:"+result);
		StringTokenizer strTok = new StringTokenizer(result,"#");
	//	int totcnt = strTok.countTokens();
		String crcEntry = "";
		String conEntry = "";
		String getCRC = "";
	//	int cnt = 0;
		while(strTok.hasMoreElements()){
			try{
			//	System.out.println("Inside While:");
				getCRC = strTok.nextToken();
			//	System.out.println("getCRC:"+getCRC);
				if(getCRC.equals("[s]")){
					
				}else if(getCRC.equals("[w]")){
					conEntry = strTok.nextToken().trim();
					while(!(conEntry.equals("[/w]"))){
					//	System.out.println(conEntry);
						conEntry = strTok.nextToken().trim();
					}
					
				}else if(getCRC.equals("[r]")){
					crcEntry = strTok.nextToken().trim();
					while(!(crcEntry.equals("[/r]")) ){
						nCRC.add(crcEntry);
						crcEntry = strTok.nextToken().trim();
					}
				}
			}catch(Exception e){
				
			}
		//	cnt++;
		//	System.out.println("cnt:"+cnt);
		}	
		addTags();
	//	nResult = result;
		compoundUWs();
		addToResult(result);
	 //	System.out.println("nCRC:"+nCRC);
		return nestresult;
	}	
	public void addTags(){
		int i = 0, totsize = nCRC.size();
		while(i < totsize){
			String getWord = nCRC.get(i).toString();
			int index = nCRC.indexOf(getWord);
			getWord = "scp1"+" "+getWord+" "+"scp2";
			nCRC.remove(index);
			nCRC.add(index, getWord);
			i++;
		}
	}
	public void compoundUWs(){
	//	System.out.println("CompoundUWs:");
		int i=0, totsize = nCRC.size();
		String scopeid = "";
		String id1 = "",id2="", id3="",id4="", id5="";
		int id = 1, sid;
		while(i<totsize){
			nResult = nCRC.get(i).toString();	
			int index = nCRC.indexOf(nResult);
			//if(nResult.contains("mod")){
			//	System.out.println("nResult:"+nResult);
				
			//		System.out.println("nResult inside Else:"+nResult);
					String pattern = "[,\\s]+";
					Pattern splitter = Pattern.compile(pattern);
					String[] getid = splitter.split(nResult);
					id1 = getid[0].toString();
				//	System.out.println("id1:"+id1);
					id2 = getid[1].toString();
				//	System.out.println("id2:"+id2);
					id3 = getid[2].toString();
				//	System.out.println("id3:"+id3);
							if( (id3.contains("mod")) || (id3.contains("pos")) || (id3.contains("and")) || (id3.contains("or")) ){
							//	System.out.println("Inside MOD:");
								sid = id++;
								scopeid = ":0"+Integer.toString(sid);
						//		System.out.println("SCOPEID:"+scopeid);	
								nResult = nResult.replace("scp1", scopeid);
								nCRC.remove(index);
								nCRC.add(index, nResult);
						//		System.out.println("nResult FFFFFFFFFFFF:"+nResult);								
							//	getScope(id1,scopeid);
							}else{
						/*		nResult = nResult.replace("scp1", "None");
								nCRC.remove(index);
								nCRC.add(index, nResult);	*/
						//		System.out.println("Else NRESULT:"+nResult);
							}
							
					//	}
				//	}	
			//	}
		//	}
			i++;
		}
		getScope();
	//	System.out.println("Final NCRC:"+nCRC);
	}	
	public void getScope(){
	//	System.out.println("Inside getScope:");
		String id1 = "",id2="", id3="",id4="", id5="";
		String nid1 = "",nid2="", nid3="",nid4="", nid5="";
		int totsize = nCRC.size();
		try{
			for(int i=0;i<totsize;i++){
				String sResult = nCRC.get(i).toString();
				String pattern = "[,\\s]+";
				Pattern splitter = Pattern.compile(pattern);
				String[] getid = splitter.split(sResult);
				id1 = getid[0].toString();
		//		System.out.println("id1:"+id1);
				id2 = getid[1].toString();
				for(int j = i+1;j<totsize;j++){
					String nextCRC = nCRC.get(j).toString();
					int index = nCRC.indexOf(nextCRC);
					String pattern1 = "[,\\s]+";
					Pattern splitter1 = Pattern.compile(pattern1);
					String[] getid1 = splitter1.split(nextCRC);
					nid1 = getid1[0].toString();
				//	System.out.println("nid1:"+nid1);
					nid2 = getid1[1].toString();
				//	System.out.println("nid2:"+nid2);
					nid3 = getid1[2].toString();
				//	System.out.println("nid3:"+nid3);
					nid4 = getid1[3].toString();
				//	System.out.println("nid4:"+nid4);
					nid5 = getid1[4].toString();
				//	System.out.println("nid5:"+nid5);
					
					if(!(id1.equals("scp1"))){
				//		System.out.println("Inside not scp1:");
						if(id2.equals(nid4)){
					//		System.out.println("nid4:"+id2+":"+nid4);
						//	nid5.replace(nid5, id1);
							String finalCRC = nid1+"\t"+nid2+"\t"+nid3+"\t"+nid4+"\t"+id1;
					//		System.out.println("finalCRC:"+finalCRC);
							nCRC.remove(index);
							nCRC.add(index, finalCRC);
							
						}
					}
				}		
			}
		}catch(Exception e){
			
		}
	}
	public void concatResult(){
		concat ="[r]#";
		for(int i = 0;i<nCRC.size();i++){
			concat +=nCRC.get(i).toString()+"#"; 
		}
	//	System.out.println("CONCAT:"+concat);
	}
	public void addToResult(String result){
		concatResult();
		int i = result.indexOf("[r]");
		int j = result.indexOf("[/r]", i);
	//	System.out.println("i and j:"+i+":"+j);
		String newRes = result.substring(i, j);
		
	//	System.out.println("newRes:"+newRes);
		nestresult =result.replace(newRes, concat);
	//	System.out.println("Final OUTPUT RESULT:"+nestresult);
	}
}
