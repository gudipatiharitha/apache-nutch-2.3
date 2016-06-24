package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.enconversion.unl.ta;


import java.lang.*;
import java.io.*;
import java.util.*;
//import org.apache.nutch.analysis.unl.ta.SentExtr;
public class SentAppln
{
		public int docid=0;
		
		int total_pos=0;
			
		
                public Hashtable ht=new Hashtable();
public SentAppln()
{
	start();
}		
public void start()
{
	
	try
	{
		long timestart  = System.currentTimeMillis();
		
		String s = "";
		StringBuffer docbuff  = new StringBuffer();		
		//ei.ExtractList();
		BufferedReader in = new BufferedReader(new FileReader(org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome()+"resource/unl/ta/SentenceExtraction/Input/nonemptyfiles.txt"));
		
		while((s=in.readLine())!=null)
		{ 
			SentExtr se = new SentExtr();
			docid++;
			//System.out.println("Str>?:"+s);
			String did=Integer.toString(docid);	
			//System.out.println("did:"+did);
						
			ht= se.impSentExtr(s,did);
		}	
		in.close();			
				long timeend = System.currentTimeMillis();
			
	}
	catch(Exception e)		
	{	
		e.printStackTrace();
	}

}
public static void main(String args[])throws IOException
{
	SentAppln ap=new SentAppln();	
}
}


