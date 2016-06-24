
package org.apache.nutch.analysis.unl.ta.clia.unl.Source.Word_Gen.Generator;

import java.io.*;
import java.util.*;
/** This is the main class which is used for reading files
  * to get the auxillary verb,verb category & gender endings */
public class FileRead
{
	File inputFile = null;
  	  /**Constructor*/
	public FileRead()
	{

	}
	/** main method in which file read object is created*/
	public static void main(String args[])
	{
		FileRead fr = new FileRead();
		/*System.out.println(fr.getAuxString("கொள்","auxiliary.txt",2));
		System.out.println(fr.getAuxString("அது","genderendings.txt",1));

		System.out.println(fr.getVerbCat("படி","verbcat.txt"));
		System.out.println(fr.getAuxCat("கொள்","auxiliary.txt"));*/

	}

	/** This method gets the auxillary verb from the file
	  * when the file name, the root auxillary,
	  * and the record no is given*/
	public String getAuxString(String rootaux,String fileName,int no)
	{
		String auxinf = "";
String s="";
//String rootaux="﻿நாம்";
int flg=0;
//int no=3;
String tok="";
		try
		{
	//PrintStream ps = new PrintStream(new FileOutputStream(new File("/usr/output/auxstring.txt")));
	//ps.println("rootaux"+rootaux);
	//ps.println("no"+no);
//StreamTokenizer input = new StreamTokenizer(new BufferedReader(new InputStreamReader(("/usr/input"+fileName))));
//StreamTokenizer input = new StreamTokenizer(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileName))));
//getClass().getResourceAsStream
				//input.eolIsSignificant(true);
BufferedReader in = new BufferedReader(new FileReader(org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome()+"resource/unl/"+fileName));

				//int tokentype = 0;
while((s=in.readLine())!=null)
		{ 		 
		//ps.println("have i come here");
		//ps.println("next token"+s);
		  StringTokenizer strToken = new StringTokenizer(s);
                     
                 
		 while(strToken.hasMoreTokens())
		  {
			String  token = strToken.nextToken().toString();
	              // ps.println("token"+token);
                      // ps.println("rootaux"+rootaux);									
			if(!token.equals(rootaux))
			{
			   //  ps.println("rootaux inside"+rootaux);
						for(int i=0;i<no;i++)
						{
						      tok=strToken.nextToken();
                                                     //  ps.println("rtok"+tok);
							flg=1;
                                                       
                                                       
						}
			   //  ps.println("the token "+tok);
						}                      
		        }
				//ps.println("value of flg"+flg);
				if(flg==1)
				{
					//ps.println("have i reached this code");
					break;
				}
		
			/*	while((tokentype = input.nextToken()) != StreamTokenizer.TT_EOF)
				{
                                   ps.println("token"+input.nextToken());
					if(!input.sval.equals(rootaux))
					{

						do{
							tokentype = input.nextToken();

							if(tokentype == StreamTokenizer.TT_EOF)
							{
								//System.out.println("It is EOF");
								return null;
							}
							if(tokentype == StreamTokenizer.TT_EOL)
								break;
							else;

						  }while(!(tokentype == StreamTokenizer.TT_EOL));
					}
					else
					{
						for(int i=0;i<no;i++)
						{
							input.nextToken();
						}

						return input.sval;
					}*/
				if(flg==1)
				{
					//ps.println("have i reached this code");
					break;
				}

				}

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return tok;
		}

	/** This method gets the verb category for the given root auxillary*/
	public int getVerbCat(String rootaux,String fileName)throws IOException
	{
		//PrintStream ps = new PrintStream(new FileOutputStream(new File("/usr/output/verbcat.txt")));

		//ps.println("filein"+fileName);
		//ps.println("root"+rootaux);
		//int verbcat = 0;
  		String s="";
		
             	   int vbcat=0;
			int flg=0;
		//System.out.println("Entered From file read rootaux"+rootaux);
		//System.out.println("Entered From file read fileName"+fileName);
		try
		{
 
				//StreamTokenizer input = new StreamTokenizer(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileName))));
                                BufferedReader in = new BufferedReader(new FileReader(org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome()+"resource/unl/"+fileName));
				//input.eolIsSignificant(true);

				//int tokentype = 0;
				//boolean string = true;
			//ps.println("next token"+input.nextToken());

		while((s=in.readLine())!=null)
		{ 
		 
		///ps.println("have i come here");
		//ps.println("next token"+s);
		StringTokenizer strToken = new StringTokenizer(s);
                 
		 while(strToken.hasMoreElements())
		  {
	                       String  token = strToken.nextToken().toString();
				//ps.println("next token"+token);
					

						if(token.equals(rootaux))
						{			
						//input.nextToken();
						//System.out.println("Verb category from file read"+(int)input.nval);
					      //   ps.println("the val"+Integer.parseInt(strToken.nextElement().toString()));
                                                  flg=1;
                                               vbcat= Integer.parseInt(strToken.nextElement().toString());
				//ps.println("vbcat inside"+vbcat);
						break;		

						}

			
                       
		  }
//ps.println("value of flg"+flg);
		if(flg==1)
{
//ps.println("have i reached this code");
		break;
}
				/*while((tokentype = input.nextToken()) != StreamTokenizer.TT_EOF)
				{
				ps.println("inside if");
				   if(string)
				   {
					//System.out.println("hasjfg"+string);
					string = false;
					//System.out.println("input.sval  "+input.sval);
					if(input.sval.equals(rootaux))
					{
				
						input.nextToken();
						//System.out.println("Verb category from file read"+(int)input.nval);
					ps.println("the val"+(int)input.nval);
						return (int)input.nval;
					}

				   }*/
				//   else
				      //string = true;
			        }
//ps.println("have i come here");
in.close();
                
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
  // ps.println("the verb category"+vbcat);
		return vbcat;
		}

	/** This method gets the auxillary category when
	  * the root auxillary is given*/
	public int getAuxCat(String rootaux,String fileName)throws IOException

	{
//PrintStream ps = new PrintStream(new FileOutputStream(new File("/usr/output/auxcat.txt")));
		int auxcat = 0;
		try
		{

				StreamTokenizer input = new StreamTokenizer(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(org.apache.nutch.analysis.unl.ta.Integrated.Jumbo.getCLIAHome()+"resource/unl/"+fileName))));
				input.eolIsSignificant(true);

				int tokentype = 0;

				while((tokentype = input.nextToken()) != StreamTokenizer.TT_EOF)
				{

					if(!input.sval.equals(rootaux))
					{

						do{
							tokentype = input.nextToken();

							if(tokentype == StreamTokenizer.TT_EOF)
							{
								//System.out.println("It is EOF");
								return 0;
							}
							if(tokentype == StreamTokenizer.TT_EOL)
								break;
							else;

						  }while(!(tokentype == StreamTokenizer.TT_EOL));
					}
					else
					{
						input.nextToken();
                                             //   ps.println("the returned"+(int)input.nval);
						//return (int)input.nval;
					}

				}
                //  input.close();

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
			return auxcat;
		}

	/** This method generates the gender endings when the subject
	  * and the file is given*/
		public String getGenderEnding(String subject,String file)
		{
			return getAuxString(subject,file,1);
		}
}



