package org.apache.nutch.analysis.unl.ta;

//package clia.unl.sentextract;

import java.lang.*;
import java.util.*;
import java.io.*;

public class ExtractInput
{
	public void ExtractList()
	{
		String dirname="/opt/unl-graph/";
		File f1=new File(dirname);
		try
		{			
			PrintStream ps=new PrintStream(new FileOutputStream(new File("./graphfiles.txt")));
			BufferedReader br=new BufferedReader(new FileReader("./graphfiles.txt"));
		
			if(f1.isDirectory())
			{
				////System.out.println(dirname);
			//	ps.println(dirname);
				String s[]=f1.list();
				for(int i=0;i<s.length;i++)
				{
					File f2=new File(dirname+'/'+s[i]);
					if(f2.isDirectory())
					{
						String s1[]=f2.list();
						for(int j=0;j<s1.length;j++)
						{
							//System.out.println(s[i]+'/'+s1[j]);
							ps.println('/'+s[i]+'/'+s1[j]);
						}
					}
					else
					{
						////System.out.println(s[i]);
						ps.println(s[i]);
					}	
				}
			}
			br.close();
			ps.close();
		}	
		catch (Exception e)
		{
			//System.out.println(e);
		}
	}
	public static void main(String args[]){
		ExtractInput ei = new ExtractInput();
		ei.ExtractList();
	}	
}
