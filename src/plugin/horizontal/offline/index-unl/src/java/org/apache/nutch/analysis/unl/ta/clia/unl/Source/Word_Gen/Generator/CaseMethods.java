
package org.apache.nutch.analysis.unl.ta.clia.unl.Source.Word_Gen.Generator;
import java.io.*;

/** This class defines methods for different cases*/
public class CaseMethods{//class starts

ByteMethods bm = new ByteMethods();
TabMethods tm = new TabMethods();
sandhimethods2 sm=new sandhimethods2();

byte[] kal={14,1,29};byte[] kkal={14,14,1,29};byte[] ukal={5,14,1,29};byte[] e={3};byte[] a={1};byte[] ai={9};
byte[] markal={23,2,25,14,1,29};byte[] mar={23,2,25};

public String addCase(String nounstr,String case1str,String case2str,boolean number)throws IOException
	{//function starts
//PrintStream ps = new PrintStream(new FileOutputStream(new File("/usr/output/case.txt")));
	//ps.println("noun in caes method"+nounstr.trim());
	//ps.println("case1"+case1str.trim());
	//ps.println("ncase2"+case2str.trim());
	//ps.println("number"+number);
	byte[] nounbyte  = tm.convert(nounstr);
	//ps.println("the converted noun string in case methods "+tm.revert(nounbyte));
//String il;
//String case1str=il;
	byte[] case1 = tm.convert(case1str.trim());
		//System.out.println(nounstr);
		byte[] case2 = tm.convert(case2str.trim());
		byte[] result=null;//byte[] result1=null;
		byte[] tempo=null;
		byte[] temp = new byte[nounbyte.length+1];
		String tan="தன்";String[] exceptions={"அவன்","இவன்","எவன்","அவர்","இவர்","எவர்","இவர்","தன்","என்","உன்","நம்","உம்","எம்"};
		String en="என்";String un="உன்";String nam="நம்";String em="எம்";String um="உம்";
		String avan="அவன்";String ivan="இவன்";//String ivar="இவர்";
		//String avar="அவர்";
		//String ivan="இவன்";
		String ivar="இவர்";String avar="அவர்";
		String ival="இவள்";	String evar="எவர்";String evan="எவன் ";

		 byte[] pala=tm.convert("பல"); byte[] sila=tm.convert("சில");
		String itamirunthu="இடமிருந்து";
		String ilirunthu="இலிருந்து";
		String ai="ஐ";
		String udaiya="உடைய";
		String aal="ஆல்";
		String itam="இடம்";
		String il="இல்";
		String kaN="கண்";
		String Odu="ஓடு";
		String udan="உடன்";
		byte [] akkaka=tm.convert("அக்காக");
		byte [] akku=tm.convert("அக்கு");
		//byte [] athu=tm.convert("அது");
		byte [] kku=tm.convert("க்கு");
		byte [] kkaka=tm.convert("க்காக");
		byte [] ukku=tm.convert("உக்கு");
		byte [] ukkaka=tm.convert("உக்காக");
		byte[] ku={14,5};byte[] in={3,31};
		byte[] iRku={3,30,14,5};
//byte [] error=tm.convert(" <௢௹஢௸ ஫௵௸஢ு஬௱ ௠஼ஹ ௵௳ணஶ>");
int flag=1;
		byte [] error=tm.convert("<This case is not applicable>");
		byte[] aRku={1,30,14,5};byte[] ithu={3,20,5};byte[] ethu={7,20,5};byte[] athu={1,20,5};
String[] cases ={"இடமிருந்து","இலிருந்து","ஐ","உக்காக","அக்காக","க்காக","கு","க்கு","உக்கு","அக்கு","அது","உடைய","ஆல்","இடம்","இல்","கண்","ஓடு","உடன்"};
		if(number)
			{//start
//ps.println("i am here");
				//System.out.println("entered the singular number");
				if((case1.length==1 && case1[0]==0)||(case1.length==16))
					result=nounbyte;
				else
					{//start else
					//System.out.println("entered the start else singular number");
//ps.println("no i am here");

							//for(int i=0;i<cases.length;i++){
                                        if(flag==1)
					{
							//if(case2.length==1 && case2[0]==0)
							//{//start if case

							if((bm.isequal(case1,ku))||(bm.isequal(case2,ku)))
							{
								if((nounstr.equals(en))||(nounstr.equals(nam))||(nounstr.equals(un))||(nounstr.equals(um))||(nounstr.equals(tan)))
								//result=bm.addarray(bm.addarray(tm.convert("<"),case1),error);
								result=error;
//}
							////System.out.println("entered casesshfkh"+cases[6]);
							else
							{
							case1=iRku;
							case2=iRku;
							result=sm.sandhimeth(nounbyte,case1);}
							}
							else if(((case1str.equals(ethu))||(case1str.equals(ithu))||(case1str.equals(athu)))||((case2str.equals(ethu))||(case2str.equals(ithu))||(case2str.equals(athu))))
							{
							case1=aRku;
							case2=aRku;
							result=sm.sandhimeth(nounbyte,case1);
							}
							else if(((nounstr.equals(en))||(nounstr.equals(un))||(nounstr.equals(nam))||(nounstr.equals(em))||(nounstr.equals(tan))||(nounstr.equals(um)))&&((bm.isequal(case1,akkaka))||(bm.isequal(case1,akku))||(bm.isequal(case1,athu))||(bm.isequal(case2,akkaka))||(bm.isequal(case2,akku))||(bm.isequal(case2,athu))))
							{
							result=sm.sandhimeth(nounbyte,case1);
							}

							else if((((nounbyte[nounbyte.length-1])==5)||((nounbyte[nounbyte.length-1])==2)||((nounbyte[nounbyte.length-1])==6)||((nounbyte[nounbyte.length-1])==11)||((nounbyte[nounbyte.length-1])==28)||((nounbyte[nounbyte.length-1])==25)||((nounbyte[nounbyte.length-1])==29)||((nounbyte[nounbyte.length-1])==31)||((nounbyte[nounbyte.length-1])==26)||((nounbyte[nounbyte.length-1])==19)||((nounbyte[nounbyte.length-1])==18)||((nounbyte[nounbyte.length-1])==1))&&((bm.isequal(case1,ukku))||(bm.isequal(case1,ukkaka))||(bm.isequal(case2,ukku))||(bm.isequal(case2,ukkaka))))
							{
								if((nounstr.equals(en))||(nounstr.equals(un))||(nounstr.equals(tan)))
								result=error;
								//result=bm.addarray(bm.addarray(tm.convert("<"),case1),error);

								else
								result=sm.sandhimeth(nounbyte,case1);

							}
							else if((((nounbyte[nounbyte.length-1])==9)||((nounbyte[nounbyte.length-1])==3)||((nounbyte[nounbyte.length-1])==4)||((nounbyte[nounbyte.length-1])==8)||((nounbyte[nounbyte.length-1])==12)||((nounbyte[nounbyte.length-1])==24))&&((bm.isequal(case1,kkaka))||(bm.isequal(case1,kku))||(bm.isequal(case2,kkaka))||(bm.isequal(case2,kku))))
							{
							//System.out.println("nounbyte   "+(nounbyte[nounbyte.length-1]));
							//System.out.println("case1   "+case1str);
							result=sm.sandhimeth(nounbyte,case1);
							//System.out.println("result   "+tm.revert(result));
							}
							else if(case1str.equals(kaN))
							{
							/* for(int i=0;i<exceptions.length;i++)
							  {*/

								if((nounstr.equals(en))||(nounstr.equals(nam))||(nounstr.equals(un))||(nounstr.equals(um))||(nounstr.equals(tan))||(nounstr.equals(avan))
								||(nounstr.equals(ivan))||(nounstr.equals(ivar))||(nounstr.equals(avar))||(nounstr.equals(ival))||(nounstr.equals(evar))||(nounstr.equals(evan)))
								{
								//System.out.println("Entered the kan loop nounstr"+nounstr);
								result=bm.addarray(nounbyte,case1);
								//System.out.println("after Entering kan loop"+result);
								}
								else
								{
								//System.out.println("Entered the else kan loop nounstr"+nounstr);
								//tempo=bm.addarray(nounbyte,in);
								result=sm.sandhimeth(nounbyte,case1);
								}
							  //}
							}
                                                     else if (flag==1)
							{

							 if((case1str.equals(itamirunthu))||(case1str.equals(ilirunthu))||(case1str.equals(ai))||(case1str.equals(udaiya))||(case1str.equals(aal))||(case1str.equals(itam))||(case1str.equals(il))||(case1str.equals(Odu))||(case1str.equals(udan)))
							 {
								System.out.println("nounbyte   "+(nounbyte[nounbyte.length-1]));
                                                              //  ps.println("no i am hereeeeeeeeeee");
								//System.out.println("case1   "+case1str);
								//ps.println("casestr"+case1str);
								//ps.println("nounbyte"+nounbyte);
								result=sm.sandhimeth(nounbyte,case1);
								//System.out.println("the result"+result);
								//ps.println("result   "+tm.revert(result));
					//ps.println("here1");
				return tm.revert(result);

							}
							}
							else
							{
								//ps.println("no i am hereeeeeeeeeee");
							}
							result=error;
							//result=bm.addarray(bm.addarray(tm.convert("<"),case1),error);
							////System.out.println("case methods error"+tm.revert(case1));
							 }//end if case
//}
					}//end else
			}//end

		if(!number)
			{//if plural starts
				//System.out.println("entered the number");

				if(((case1.length==1 && case1[0]==0)||(case1.length==16)))
				{//start if
				//System.out.println("hgccbghjasgcjhasgdjkadh");
					//if((bm.isequal(case1,kku))||(bm.isequal(case1,kkaka))||(bm.isequal(case1,akkaka))||(bm.isequal(case1,ku))||(bm.isequal(case1,akku)))
					//		result=error;
					String [] noun1={"முஸ்லிம்"};
					//else if((bm.isequal(case1,itamirunthu))||(bm.isequal(case1,ilirunthu))||(bm.isequal(case1,ai))||(bm.isequal(case1,ukkaka))||(bm.isequal(case1,ukku))||(bm.isequal(case1,athu))||(bm.isequal(case1,udaiya))
					//||(bm.isequal(case1,aal))||(bm.isequal(case1,itam))||(bm.isequal(case1,il))||(bm.isequal(case1,kaN))||(bm.isequal(case1,Odu))||(bm.isequal(case1,utan)))
					//{//start big else
//if m ending change to ng
					if(nounbyte[nounbyte.length-1]==23)
						{
							for(int i=0;i<noun1.length;i++){
							if(nounstr.equals(noun1[i]))
							{
							//System.out.println("Entered the loop");
							result = bm.addarray1(nounbyte,kal);
							}
							else
							{
							nounbyte[nounbyte.length-1]=15;
							result = bm.addarray1(nounbyte,kal);
							}}
						}
//if relations add mar/markal/kal or kkal
					else if((nounstr.equals("அப்பா"))||(nounstr.equals("அம்மா"))||(nounstr.equals("தாத்தா"))||(nounstr.equals("பாட்டி"))||(nounstr.equals("அத்தை"))||(nounstr.equals("அக்கா"))||(nounstr.equals("அண்ணன்"))||(nounstr.equals("தங்கை"))||(nounstr.equals("மாமா"))||(nounstr.equals("மாமி"))||(nounstr.equals("குழந்தை"))||(nounstr.equals("தந்தை"))||(nounstr.equals("தாய்")))
						{
							byte[] result1=null;byte[] result2=null;byte[] result3=null;byte[] result4=null;byte[] res5=null;
							byte[] space=tm.convert("/ "+nounstr);
							result1=bm.addarray1(nounbyte,markal);
							result3=bm.addarray1(space,mar);
							res5=bm.addarray1(result1,result3);
							//System.out.println("entered the if loop");
							if((nounstr.equals("தாய்"))||(nounstr.equals("தந்தை"))||(nounstr.equals("குழந்தை"))||(nounstr.equals("பாட்டி"))||(nounstr.equals("அத்தை"))||(nounstr.equals("அண்ணன்"))||(nounstr.equals("மாமி")))
							{
								result2=bm.addarray1(space,kal);
								result=bm.addarray1(res5,result2);
							}
							else
							{
								result4=bm.addarray1(space,kkal);
								result=bm.addarray1(res5,result4);
							}
						}
//if endswith ee,uu,aa add kkal
					else if((nounbyte[nounbyte.length-1]==4)||(nounbyte[nounbyte.length-1]==6)||(nounbyte[nounbyte.length-1]==2))
						{
							result = bm.addarray1(nounbyte,kkal);
						}
//if endswith u	and -2 nounbyte is r add kkal
					else if((nounbyte[nounbyte.length-1]==5)&&(nounbyte[nounbyte.length-2]==25))
						{
						result = bm.addarray1(nounbyte,kkal);
						}
// if endswith k and t doubling the k or t and add ukal
					else if((nounbyte[nounbyte.length-1]==14)||(nounbyte[nounbyte.length-1]==18))
						{
							String nounstrdoub=nounstr+nounstr.charAt(nounstr.length()-1);
							byte[] nounstrbyte=tm.convert(nounstrdoub);
							result = bm.addarray1(nounstrbyte,ukal);
						}
//if endswith ng add kukal
					else if(nounbyte[nounbyte.length-1]==15)
						{
							byte[] kukal={14,5,14,1,29};
							result = bm.addarray1(nounbyte,kukal);
						}
//if endswith L add change L to t and add kal gives two outputs
					else if(nounbyte[nounbyte.length-1]==29)
						{
							byte[] space=tm.convert("/"+nounstr);byte[] result1=null;byte[] result2=null;
							byte[] nounbyte1=tm.convert(nounstr.trim());
							nounbyte[nounbyte.length-1]=18;
							result1=bm.addarray1(nounbyte,kal);
							result2=bm.addarray1(space,kal);
							result=bm.addarray1(result1,result2);
						}
//if endswith l and for certain words such as cel just add kal
// or else change l to R and add kal.
					else if(nounbyte[nounbyte.length-1]==26)
						{
							String[] noun2={"செல்"};
							for(int i=0;i<noun2.length;i++){
							if(nounstr.equals(noun2[i]))
							{
							//System.out.println("Entered the loop");
							result = bm.addarray1(nounbyte,kal);
							}
							else
							{nounbyte[nounbyte.length-1]=30;
							result = bm.addarray1(nounbyte,kal);}
							}
						}
//else just add kal
					else
						{
							result = bm.addarray1(nounbyte,kal);
						}
				//}// big else ends

					}//if ends

//else for other cases that is case2string ==ai and case2string
				else
					{//else starts

						//System.out.println("entered the else plural number");
						if(case2.length==1 && case2[case2.length-1]==0)
							{//if starts for case2
							if((case1str.equals(itamirunthu))||(case1str.equals(ilirunthu))||(case1str.equals(ai))||(case1str.equals(udaiya))||(case1str.equals(aal))||(case1str.equals(itam))||(case1str.equals(il))||(case1str.equals(Odu))||(case1str.equals(udan))||
				(bm.isequal(case1,ukkaka))||(bm.isequal(case1,ukku)))
							{
								//System.out.println("entered the  first else number");


						String [] noun1={"முஸ்லிம்"};
											if(nounbyte[nounbyte.length-1]==23)
												{
													//System.out.println("entered the second else number");
													for(int i=0;i<noun1.length;i++){
													if(nounstr.equals(noun1[i]))
													{
													//System.out.println("Entered the loop");
													result=bm.addarray(bm.addarray(nounbyte,kal),case1);
													//result = bm.addarray1(nounbyte,kal);
													}
													else
													{
													nounbyte[nounbyte.length-1]=15;
													//result = bm.addarray1(nounbyte,kal);
													result=bm.addarray(bm.addarray(nounbyte,kal),case1);
													}}
												}
											else if((nounstr.equals("அப்பா"))||(nounstr.equals("அம்மா"))||(nounstr.equals("தாத்தா"))||(nounstr.equals("பாட்டி"))||(nounstr.equals("அத்தை"))||(nounstr.equals("அக்கா"))||(nounstr.equals("அண்ணன்"))||(nounstr.equals("தங்கை"))||(nounstr.equals("மாமா"))||(nounstr.equals("மாமி"))||(nounstr.equals("குழந்தை"))||(nounstr.equals("தந்தை"))||(nounstr.equals("தாய்")))
												{
													//System.out.println("entered the nounstr number");
													byte[] result1=null;byte[] result2=null;byte[] result3=null;byte[] result4=null;byte[] res5=null;
													byte[] space=tm.convert("/ "+nounstr);
													result1=bm.addarray1(nounbyte,markal);
													result3=bm.addarray1(space,mar);
													res5=bm.addarray1(result1,result3);
													//System.out.println("entered the if loop");
													if((nounstr.equals("தாய்"))||(nounstr.equals("தந்தை"))||(nounstr.equals("குழந்தை"))||(nounstr.equals("பாட்டி"))||(nounstr.equals("அத்தை"))||(nounstr.equals("அண்ணன்"))||(nounstr.equals("மாமி")))
													{
														result2=bm.addarray1(space,kal);

														//result=bm.addarray1(res5,result2);
														result=bm.addarray(bm.addarray(res5,result2),case1);
													}
													else
													{
														//System.out.println("entered the kkal number");
														result4=bm.addarray1(space,kkal);
														//result=bm.addarray1(res5,result4);
														result=bm.addarray(bm.addarray(res5,result4),case1);
													}
												}
											else if((nounbyte[nounbyte.length-1]==4)||(nounbyte[nounbyte.length-1]==6)||(nounbyte[nounbyte.length-1]==2))
												{
													//System.out.println("entered the nounbyte number");
													//result = bm.addarray1(nounbyte,kkal);
													result=bm.addarray(bm.addarray(nounbyte,kkal),case1);
												}
											else if((nounbyte[nounbyte.length-1]==5)&&(nounbyte[nounbyte.length-2]==25))
												{
													//System.out.println("entered the else nounbyte number");
													result=bm.addarray(bm.addarray(nounbyte,kkal),case1);
												//result = bm.addarray1(nounbyte,kkal);
												}
											else if((nounbyte[nounbyte.length-1]==14)||(nounbyte[nounbyte.length-1]==18))
												{
													//System.out.println("entered the  14 883 else number");
													String nounstrdoub=nounstr+nounstr.charAt(nounstr.length()-1);
													byte[] nounstrbyte=tm.convert(nounstrdoub);
													//result = bm.addarray1(nounstrbyte,ukal);
													result=bm.addarray(bm.addarray(nounstrbyte,ukal),case1);
												}
											else if(nounbyte[nounbyte.length-1]==15)
												{
													//System.out.println("entered the kkukkal else number");
													byte[] kukal={14,5,14,1,29};
													//result = bm.addarray1(nounbyte,kukal);
													result=bm.addarray(bm.addarray(nounbyte,kukal),case1);
												}
											else if(nounbyte[nounbyte.length-1]==29)
												{
													byte[] result3=null;
													//System.out.println("entered the 29 900 number");
													byte[] space=tm.convert("/"+nounstr);byte[] result1=null;byte[] result2=null;
													byte[] nounbyte1=tm.convert(nounstr.trim());
													nounbyte[nounbyte.length-1]=18;
													result1=bm.addarray1(nounbyte,kal);
													result3=bm.addarray1(result1,case1);
													result2=bm.addarray1(space,kal);
													//result=bm.addarray1(result1,result2);
													result=bm.addarray(bm.addarray(result3,result2),case1);
												}
											else if(nounbyte[nounbyte.length-1]==26)
												{
													//System.out.println("entered the 26 else number");
													String[] noun2={"செல்"};
													for(int i=0;i<noun2.length;i++){
													if(nounstr.equals(noun2[i]))
													{
													//System.out.println("Entered the loop");
													//result = bm.addarray1(nounbyte,kal);
													result=bm.addarray(bm.addarray(nounbyte,kal),case1);
													}
													else
													{nounbyte[nounbyte.length-1]=30;
													//result = bm.addarray1(nounbyte,kal);
													result=bm.addarray(bm.addarray(nounbyte,kal),case1);}
													}
												}

											else
												{
													//System.out.println("entered thejkhjkhjkehjkehrjkrh else number");
													//result = bm.addarray1(nounbyte,kal);
													result=bm.addarray(bm.addarray(nounbyte,kal),case1);
												}
										}

										else
										result=error;

											}//if ends for case2
								}//else ends




					}// ends plural
//					System.out.println("reultgjgfjkaf"+result);
                   
		//return tm.revert(result);
                  return "";
	}//function ends
}//class ends

