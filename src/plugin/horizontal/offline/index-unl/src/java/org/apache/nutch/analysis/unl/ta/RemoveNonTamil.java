package org.apache.nutch.analysis.unl.ta;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Hashtable;
import java.io.*;
import java.util.*;

public class RemoveNonTamil{
	ArrayList al = new ArrayList();
        public static Hashtable fileList = null;
        Hashtable RecnoToURL = new Hashtable();
	int count = 30000;
	public void readFiles(){
		try{
			String file = "";
			BufferedReader buffer = new BufferedReader(new FileReader(org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome()+"resource/unl/ta/SentenceExtraction/Output/nonemptyfiles.txt"));
			while((file=buffer.readLine())!=null){
				al.add(file);
			}
			buffer.close();
		}catch(Exception e){

		}
	}
	public void process_Files(){
		try{
			Hashtable urlfileList2=loadserfiles("./crawl-unl2/RecnoToURL.ser");
			String str = "";
			readFiles();
			for(int i=0;i<al.size();i++){
                                count++;
				String getFile = al.get(i).toString();
				String fn = org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome()+"resource/unl/ta/SentenceExtraction/Output/"+getFile;
				//System.out.println("get_File====================>");
				Scanner scanner = new Scanner(new File(fn)).useDelimiter("\\Z");
				String contents = scanner.next();
				//if(contents.startsWith("<t>")){
				//	contents = contents.replace("<t>","");
					if( (contents.trim().charAt(0)>=2949) && (contents.trim().charAt(0)<=2997) ){
						System.out.println(getFile);
					/**	File file = new File(getFile);
                                                String get_fname = file.getName();
                                             //   System.out.println("get_fName:"+get_fname);
						//String[] get_id = get_fname.split(".");
						//String docid = get_id[0].toString().trim();
                                                String docid = get_fname.replace(".txt", "");
                                              //  System.out.println("docid:"+docid);
						String get_inpfile = org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome()+"resource/unl/ta/SentenceExtraction/Input/"+getFile;
						Scanner scan = new Scanner(new File(get_inpfile)).useDelimiter("\\Z");
						String inp_content = scan.next();
						writeintofile(count, inp_content);						
						if(urlfileList2.containsKey(docid)){
                                                        String value = urlfileList2.get(docid).toString();
							RecnoToURL.put("d"+count, value);
						}
                                                scan.close();*/
					}                               
                                scanner.close();
			}
               /*       ObjectOutputStream RecnotoURLOutputStream = IOHelper.getObjectOutputStream("./crawl-unl3/RecnotoURL.ser");
                        IOHelper.writeObjectToOutputStream(RecnotoURLOutputStream, RecnoToURL);
                        IOHelper.closeObjectOutputStream(RecnotoURLOutputStream);   */
		}catch(Exception e){
                    e.printStackTrace();
		}
	}
	
	public Hashtable loadserfiles(String urlsfilepath)
	{
		try
	    	{   	
			FileInputStream fis=new FileInputStream(urlsfilepath);
			
			ObjectInputStream ois=new ObjectInputStream(fis);				
			fileList=(Hashtable)ois.readObject();
			ois.close();//closing object stream 
			fis.close();//closing file stream
			
			
		}
		catch(Exception e)
		{
			fileList = new Hashtable();	
			
			e.printStackTrace();//To print the run time exception			
		} 
		return fileList;
		
	}	
	public void writeintofile(int cnt, String content){
		try{
			Writer output = null;			
			File fn = new File(org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome()+"resource/unl/ta/SentenceExtraction/newInput/content/"+"d"+cnt+".txt");
			fn.createNewFile();
			output = new BufferedWriter(new FileWriter(fn, false));
			output.write(content);
			output.close();
		}catch(Exception e){

		}
	}
	public static void main(String args[]){
		RemoveNonTamil RNT = new RemoveNonTamil();
		RNT.process_Files();
	}
}
