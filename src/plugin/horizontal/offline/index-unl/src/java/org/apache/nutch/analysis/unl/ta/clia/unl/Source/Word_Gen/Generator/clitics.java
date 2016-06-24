
package org.apache.nutch.analysis.unl.ta.clia.unl.Source.Word_Gen.Generator;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
public class clitics
{

	 ByteMethods bm = new ByteMethods();
	 TabMethods tm = new TabMethods();
	public static final char[] WORD_SEPARATORS = {' ','+','\t', '\n', ';', '?', '_',
					'\r', '\f', '.', ',', ':', '-', '(', ')', '[', ']', '{',
					'}', '<', '>', '/', '|', '\\', '\'', '\"'};


	 String [][] c ={{"ஆ","ஏ","ஓ"},{"தான¢"}, {"மட்டும்","மாத்திரம்"},
							   {"என்னும்","ஆகிலும்","ஆயினும்"},
							   {"கூட"},
							   {"உம்"},
							   {"ஆவது"},
							   {"அடா","அடி","அம்மா","அப்பா","அய்யா"} };

	 String[] rule={"1","2","3","4","5","6","7","8"};
	 /*,"7+1+8",
"7+1","7+2+1+8","7+2+8","7+2+1","7+2","7+3+2+1+8","7+3+8",	"7+3+2","7+3+1","7+3+1+8","7+3+2+1","7+3","6+1+8",
"6+1","6+2+1+8","6+2+8","6+2+1","6+2","6+5+2+1","6+5+2","6+5+1","6+5","5+1+8","5+1","5+2+1+8","5+2+8","5+2+1",
"5+2","5+3","5+3+1","5+6","5+6+1","5+7","5+7+1","5+8","4+1",
"3+1+8","3+8","3+1","3+2+1+8","3+2+1","3+2+8","3+2",
"3+4+1+8","3+4+8","3+4+1","3+4","3+5+1","3+5","3+5+2","3+5+2+1","3+5+3","3+5+3+1",	"3+5+6","3+5+6+1","3+5+7",
"3+5+7+1","3+5+8","3+7+1+8","3+7+8","3+7+1","3+7","3+8","2+1+8","2+1","2+8","1+2", "1+3","1+8"};*/








/*
	 "2+1","2+1+8","2+8",
	"3+1","3+8","3+1+8","3+2","3+2+1","3+2+1+8","3+2+8","3+4","3+4+1","3+4+1+8",
	"3+4+8","3+5","3+5+1","3+7","3+7+1","3+7+1+8","3+7+8","3+8","4+1","5+1",
	"5+1+8","5+2","5+2+1","5+2+1+8","5+2+8","5+3","5+3+1","5+3+1+8","5+3+8",
	"6+1","6+1+8","6+2","6+2+1","6+2+1+8","6+2+8","6+5","6+5+1","6+5+2","6+5+2+1","7+1",
	"7+2+1","7+1+8","7+2","7+2+1+8","7+2+8","7+3","7+3+1","7+3+1+8","7+3+2","7+3+2+1",
	"7+3+2+1+8","7+3+8"};*/

	byte sp []=tm.convert(" ");
byte[] resultcliticstring=null;	String[] cliticresult=null;String[] result=null;

public Vector cliticsadd()throws IOException
{
	Vector v=new Vector(10,1);String[] clivect=null;

	int st=0;String ans="";
	for(int i=0;i<rule.length;i++)
	{
		////System.out.println("New Result ");

		String ruleinput = rule[i];
		String[] rules = tokenize(ruleinput);

		 v = ruleadd(rules);

		for(int m=0; m<v.size(); m++)
		{

			String clivect1=String.valueOf(v.get(m));
			//for(int l=0;l<clivect.length;l++){
				//clivect[m]=clivect1;
			////System.out.println("clivect"+clivect1);
		}
	}
return v;
	}


public  String[] tokenize(String string)
{
	int i=0;
	String wordSeparators = new String(WORD_SEPARATORS);
	StringTokenizer stringTokens = new StringTokenizer(string,wordSeparators);
	String[] result = new String[stringTokens.countTokens()];
	while(stringTokens.hasMoreTokens())
	result[i++] = stringTokens.nextToken();
	return result;
}


public Vector ruleadd(String[] rulesend)throws IOException
{
	//for(int i=0;i<rulesend.length;i++)
	////System.out.println("rulesend"+rulesend[i]);

	Vector v = new Vector(10,1);

	v.add("");

	for(int i=0; i<rulesend.length; i++)
	{
		int n1= Integer.parseInt(rulesend[i]);

		String s1 [] = new String[v.size()];
		v.toArray(s1);

		String s2 [] = new String[c[n1-1].length];

		for(int m=0; m<c[n1-1].length; m++)
		{
			s2[m]=c[n1-1][m];

		}

		v=Compute(s1,s2);
	}
	return v;
}


public Vector Compute(String s1[], String s2[])throws IOException
{
	Vector v = new Vector(10,1);
	for(int i=0; i<s1.length; i++)
	{
		for(int j=0; j<s2.length; j++)
		{
			byte[] b = bm.addarray1(tm.convert(s1[i]),tm.convert(s2[j]));
			v.add((tm.revert(b)).trim());

			/*String s=s1[i]+s2[j];
			v.add(s.trim());*/

		}

	}
	return v;
}

}

