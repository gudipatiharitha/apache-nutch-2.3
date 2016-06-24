package org.apache.nutch.analysis.unl.ta;

//import org.apache.nutch.unl.utils.*;
import java.util.*;


public class Method
{
	static String x = "";
	static String y = "";
	static UnicodeConverter TC;
	static ADictionary dictionary;
	static Stack originalStack;
	static Stack originalStack1;

	public static int analyse(String inputString, Stack allStk, boolean analysePart)
	{
		try
		{
			////////System.out.println("inside mehtod class Word: " + inputString);
			// not necessary now
			int c = 0;
			boolean checkNum;
			boolean isNoun;
	                boolean isRoot;
        	        boolean isVerb;
			boolean isTourism;
			boolean isTamil=true;
			boolean isNumber=false;
			// new. not completed.
			/* eg:
			 100: < Noun>
			 10-Þô¢: <Unknown>
			 */
		

			for (int i = 0; i < inputString.length(); i++)
			{
				int j = inputString.charAt(i);
										// this for loop is for remove the special character and unwanted character and english letter.
				if((j>57) && (j<=127))
				{
					isTamil = false;
					return 0;
				} 
			}
		
			
				byte[] topElmt=UnicodeConverter.convert(inputString);
				////////System.out.println("hi"+topElmt.length);
                                //byte[] topElmt = inputString.getBytes();
                               // //System.out.println("tope "+UnicodeConverter.revert(topElmt)+"top "+topElmt.length);
				checkNum=NounMisc.checkNumber(allStk, inputString);
				if (checkNum)
				{	
					////System.out.println("NounMisc Check Number True");
					return c;
				}
				////////System.out.println("CHECK NUm is.."+checkNum);
				
				/*if(ADictionary.doubleNoun(allStk, topElmt)){
					//////System.out.println("Double word True");
					return c;
				}*/
				
				// root word

				  int stkSize=allStk.size();
				  isRoot = ADictionary.check(allStk, topElmt);
		    		  if(isRoot)
				  {
				    	return c;
				  }
				
				  c = 17;
				
				isTourism=ADictionary.tourismDomain(allStk, topElmt);
				if(isTourism)
				{
					return c;
				}
				// prefix
				boolean chkPrefix=Noun.checkPrefix(allStk, topElmt, analysePart);
				if(chkPrefix)
				{
					//return c;
				}
				//////System.out.println("chkPrefix is :"+chkPrefix);
			
			
				// noun analysis
				isNoun = Noun.check(allStk, topElmt, analysePart);
				//System.out.println("isNoun "+isNoun);
				if (isNoun)
				{
				
				 return c;
				}
			
				////////System.out.println("NOUNstack size is.."+allStk.size());
			
				// verb analysis
				isVerb = Verb.check(allStk, topElmt, analysePart);
		   		//System.out.println("isVerb"+isVerb);
				// mARRu
				if (isVerb)
				{
					return c;
				}
						
				if(!(isNoun||isVerb))
				{
					////////System.out.println("enter into DW");
					////////System.out.println("allStk"+allStk.size());
					if(Doubleword.check(allStk, topElmt, analysePart)){
					//System.out.println("Double word True");
					return c;
					}
				}
			
            return 0;
		} catch (Exception ex)
		{
			String xxx = "";

			////////System.out.println(xxx + "Error while analysing input string: " + inputString);
			ex.printStackTrace();
			return 0;
		}

	}
}
