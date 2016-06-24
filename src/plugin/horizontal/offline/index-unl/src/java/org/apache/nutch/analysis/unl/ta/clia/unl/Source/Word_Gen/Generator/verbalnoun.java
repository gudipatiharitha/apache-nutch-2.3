

package org.apache.nutch.analysis.unl.ta.clia.unl.Source.Word_Gen.Generator;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.Properties;
import java.io.FileInputStream;
public class verbalnoun{//start pgm
	TabMethods tm =new TabMethods();
	ByteMethods bm = new ByteMethods();
	sandhimethods1 sm = new sandhimethods1();
	FileRead file = new FileRead();
public String getverbalnoun(String verb)
{
	int cat = 0;String verbnoun="";
	byte[] p={22};byte[] pp={22,22};
	byte[] verbbyte=tm.convert(verb);
	try
		{
			cat = file.getVerbCat(verb,"VerbCategory.txt");
			if((cat==1)||(cat==3)||(cat==4)||(cat==6)||
			(cat==7)||(cat==8)||(cat==9)||(cat==10)||
			(cat==11)||(cat==12)||(cat==13)||(cat==14)||
			(cat==15)||(cat==16)||(cat==17)||(cat==18))
			{
				verbnoun=tm.revert(bm.addarray(verbbyte,p));
//				System.out.println("verbinfinitive  "+verbnoun);
			}
			else if((cat==2)||(cat==5))
			{
				verbnoun=tm.revert(bm.addarray(verbbyte,pp));
				////System.out.println("verbinfinitive  "+verbnoun);
			}

		}
	catch(Exception ex)
	{
		//System.out.println("exception");
	}
	return verbnoun;
}
}
