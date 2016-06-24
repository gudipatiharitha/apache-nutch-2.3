
package org.apache.nutch.analysis.unl.ta.clia.unl.Source.Word_Gen.Generator;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.Properties;
import java.io.FileInputStream;
public class verbinfinitive{//start pgm
	TabMethods tm =new TabMethods();
	ByteMethods bm = new ByteMethods();
	sandhimethods1 sm = new sandhimethods1();
	FileRead file = new FileRead();
public String verbgetinfi(String verb)
{
	int cat = 0;String verbinfinitive="";
	byte[] a={1};byte[] ka={14,1};byte[] kka={14,14,1};
	byte[] tka={18,14,1};byte[] Rka={30,14,1};byte[] ara={1,25,1};
	byte[] verbbyte=tm.convert(verb);
	try
		{
			cat = file.getVerbCat(verb,"VerbCategory.txt");
			if((cat==1)||(cat==3)||(cat==6)||(cat==7)||(cat==8)||(cat==9)
			||(cat==10)||(cat==12)||(cat==14)||(cat==15)||(cat==16)||(cat==17))
			{
				if((cat==14)||(cat==15)||(cat==1))
				{
				 String verbstrdoub=verb+verb.charAt(verb.length()-1);
				 verbbyte=tm.convert(verbstrdoub);
				 verbinfinitive=tm.revert(bm.verbaddarray(verbbyte,a));
				}
				else if(cat==17)
				{
					verbbyte=bm.subarray(verbbyte,0,verbbyte.length-1);
					verbinfinitive=tm.revert(bm.verbaddarray(verbbyte,ara));
				}
				else if ((cat==3)||(cat==6)||(cat==7)||(cat==8)||(cat==9)
				||(cat==10)||(cat==12)||(cat==16))
				{
				verbinfinitive=tm.revert(bm.verbaddarray(verbbyte,a));
				//System.out.println("verbinfinitive  "+verbinfinitive);
				}
				else
				verbinfinitive=tm.revert(verbbyte);
			}
			else if((cat==11)||(cat==13)||(cat==4)||(cat==18))
			{
			if(cat==11)
				{
				//	System.out.println("entered the 11 condition");
					verbbyte=bm.subarray(verbbyte,0,verbbyte.length-1);
					verbinfinitive=tm.revert(bm.verbaddarray(verbbyte,tka));
				//	System.out.println("verbinfinitive  "+verbinfinitive);
				}
			else if(cat==13)
				{
				//	System.out.println("entered the 13 condition");
					verbbyte=bm.subarray(verbbyte,0,verbbyte.length-1);
					verbinfinitive=tm.revert(bm.verbaddarray(verbbyte,Rka));
				//	System.out.println("verbinfinitive  "+verbinfinitive);
				}
				else
				{
						verbinfinitive=tm.revert(bm.verbaddarray(verbbyte,ka));
				//		System.out.println("verbinfinitive  "+verbinfinitive);
				}
			}
			else if((cat==2)||(cat==5))
			{
			//System.out.println("entered kka");
			verbinfinitive=tm.revert(bm.verbaddarray(verbbyte,kka));
			//System.out.println("verbinfinitive  "+verbinfinitive);
			}
		}
	catch(Exception ex)
	{
		//System.out.println("exception");
	}
	return verbinfinitive;
}
}
