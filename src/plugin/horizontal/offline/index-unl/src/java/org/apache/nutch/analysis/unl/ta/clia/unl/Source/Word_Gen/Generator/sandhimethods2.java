

package org.apache.nutch.analysis.unl.ta.clia.unl.Source.Word_Gen.Generator;
import java.io.*;

public class sandhimethods2{
	TabMethods tm =new TabMethods();
	ByteMethods bm = new ByteMethods();
public byte[] sandhimeth(byte nounbyte[],byte[] nountemp)throws IOException
    {
//	System.out.println("the retrieved n"+tm.revert(nountemp));
//	System.out.println("the retrieved n1"+tm.revert(nounbyte));
	 byte[] aRRu=tm.convert("அற்று");byte[] vaRRu=tm.convert("வற்று");
		 byte[] pala=tm.convert("பல"); byte[] sila=tm.convert("சில");
	 byte[] avai=tm.convert("அவை"); byte[] ivai=tm.convert("இவை");
		 byte[] evai=tm.convert("எவை"); byte[] ellam=tm.convert("எல்லாம்");
		 byte[] a ={1};byte[] y ={24};byte[] k ={14};byte[] in={3,31};byte[] kaN={14,1,19};
		// System.out.println("the length in bytes"+nounbyte[nounbyte.length-1]);
		String tan="தன்";
		String en="என்";
		String un="உன்";
		String nam="நம்";
		String em="எம்";
		String um="உம்";
		String nounstr=tm.revert(nounbyte);
////System.out.println("entered the sandhimethods");
        byte[] temp=null; byte[] output=null;
         int x=nounbyte.length-1;
       //System.out.println("x      " +x);
         int len=nounbyte[nounbyte.length-1];
         for(int w=0;w<nounbyte.length;w++)
         {
        	 System.out.println("inside for loop"+nounbyte[w]);
        //	 System.out.println("the retrieved n1"+tm.revert(nounbyte[w]));
         }
         System.out.println("nbl"+nounbyte.length);
         System.out.println("the lenght"+len);
   //     System.out.println("the length in bytes 2"+nounbyte[nounbyte.length-2]);
   //    System.out.println("the length in bytes 3"+nounbyte[nounbyte.length-3]);
       // System.out.println("the length in bytes 4"+nounbyte[nounbyte.length-4]);
     	 // System.out.println("the length in bytes4"+nounbyte[nounbyte.length-4]);
        if(nounbyte.length==1 && nounbyte[0]==0)
          temp=nountemp;
        else if(nountemp.length==1 && nountemp[0]==0)
        temp=nounbyte;

  								else if(nounbyte[nounbyte.length-1]==23)
  									{//start
  									////System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmm");
  										if((nounstr.equals("îñ¢"))||(nounstr.equals("ïñ¢"))||(nounstr.equals("¸ñ¢"))||(nounstr.equals("âñ¢"))||(nounstr.equals("àñ¢")))
  										{
  											
  											 String nounstrdoub=nounstr+nounstr.charAt(nounstr.length()-1);
  								 			 temp=tm.convert(nounstrdoub);
  								 			 if(bm.isequal(nountemp,kaN))
											 temp=bm.addarray(temp,in);
  								 			  output=bm.addarray(temp,nountemp);

  										}
  										else if(bm.isequal(nounbyte,ellam))
										{
  											System.out.println("am i here 1");
											temp =bm.addarray1(bm.subarray(nounbyte,0,4),vaRRu);
											if(bm.isequal(nountemp,kaN))
											 temp=bm.addarray(temp,in);
											output=bm.addarray(temp,nountemp);
											////System.out.println("from sm"+tm.revert(output));

										}
  										else
  										{
  											System.out.println("am i here 2");
											byte[] thth={20,20};
											temp=bm.addarray(bm.subarray(nounbyte,0,nounbyte.length-1),thth);
											if(bm.isequal(nountemp,kaN))
											 temp=bm.addarray(temp,in);
											output=bm.addarray(temp,nountemp);
											////System.out.println("from sm"+tm.revert(output));
  										}
  									}//end
  								/*if ends with u*/
        
  								 else if((nounbyte[nounbyte.length-1])==5)
  								 {//start
  								 ////System.out.println("uuuuuuuuuuuuuuuuuu");
  									System.out.println("am i here 3");
  								 System.out.println("the length in bytes"+nounbyte[nounbyte.length-1]);
  								 	System.out.println("the retrieved n"+tm.revert(nountemp));  								 	
									System.out.println("the retrieved string inside 3"+tm.revert(nounbyte));
							//		System.out.println("the lenght in 3"+nounbyte[nounbyte.length-3]);
  							  if(((((nounbyte[nounbyte.length-3])==2) || (((nounbyte[nounbyte.length-3])==4)||((nounbyte[nounbyte.length-3])==6)||((nounbyte[nounbyte.length-3])==8) )||((nounbyte[nounbyte.length-3])==11))&&(((nounbyte[nounbyte.length-2])==18))))
  										 {
  								  
  								  
  							//	 System.out.println("the length in bytes 2"+nounbyte[nounbyte.length-2]);
  							//      System.out.println("the length in bytes 3"+nounbyte[nounbyte.length-3]);
  							//     System.out.println("the length in bytes 4"+nounbyte[nounbyte.length-4]);
  							//     	 System.out.println("the length in bytes4"+nounbyte[nounbyte.length-4]);
  								  
  								  
  								  
  								       /*        String n=tm.revert(nounbyte);
  								               System.out.println("the string before sub array"+n);
  								               if(nounbyte.length>=5)
  								               {
  								            	   System.out.println("thiruuuuuuu");
  								                   if(nounbyte.length-5!=0)
  								               {
  								        if((nounbyte[nounbyte.length-5])==1)
  								               {
  								            	 nounstr =tm.revert(bm.subarray(nounbyte,0,7));
  								               }
  								        else         if((nounbyte[nounbyte.length-5])==28)
  								               {
  								            	 nounstr =tm.revert(bm.subarray(nounbyte,0,8));
  								               }
  								             else     if((nounbyte[nounbyte.length-5])==30||(nounbyte[nounbyte.length-5])==29)
								               {
  								            	 
  								            	 if(nounbyte.length-1==5)
  								            	 {
  								            		 nounstr =tm.revert(bm.subarray(nounbyte,0,5));
  								            	 }
  								            	 
  								            	 else	 if((nounbyte[nounbyte.length-4])==14 ||(nounbyte[nounbyte.length-4])==29)
  								            	 {
  								            		nounstr =tm.revert(bm.subarray(nounbyte,0,10));
  								            	 }
  								            	
  								            	 else 
								            	 nounstr =tm.revert(bm.subarray(nounbyte,0,5));
								               }
  								           else     if((nounbyte[nounbyte.length-5])==15 ||(nounbyte[nounbyte.length-4])==14)
							               {
  								        	 System.out.println("thiruchengodu");
  								        	  if(nounbyte.length>5 &&  nounbyte.length<9)
  								          	  {
  								        	 	 nounstr =tm.revert(bm.subarray(nounbyte,0,6));
  								        		  
  								        	  }
  								        	  else
  								        	 
							            	 nounstr =tm.revert(bm.subarray(nounbyte,0,11));
							               }
  								     else     if((nounbyte[nounbyte.length-5])==9)
							               {
							            	 nounstr =tm.revert(bm.subarray(nounbyte,0,9));
							             }
  										//	 System.out.println("inside 3"+nounstr);
  								            //   }
  								               }
  								               }
  										else
  											{
  												// System.out.println("thiruchengodu");
  										byte[] thth={20,20};
									 	temp=bm.addarray(bm.subarray(nounbyte,0,nounbyte.length-1),thth);
  											//	 nounstr =tm.revert(bm.subarray(nounbyte,0,3));
  									 }
  												
  										 	 String nounstrdoub=nounstr+nounstr.charAt(nounstr.length()-1);
  								 		     temp=tm.convert(nounstrdoub);
  								 		     if(bm.isequal(nountemp,kaN))
											 temp=bm.addarray(temp,in);
  											  output=bm.addarray(temp,nountemp);*/
  								  				byte[] itt={18};
  								  				temp=bm.addarray(bm.subarray(nounbyte,0,nounbyte.length-1),itt);
  										 
  								//	temp=bm.subarray(nounbyte,0,nounbyte.length-1);
  										if(bm.isequal(nountemp,kaN))
										 temp=bm.addarray(temp,in);
  											output=bm.addarray(temp,nountemp);
  								 		 }
  							  else if((((nounbyte[nounbyte.length-3])==2)|| ((nounbyte[nounbyte.length-3])==11) )&& ((nounbyte[nounbyte.length-2])==30))
							 {
  							//	 System.out.println("the length in bytes 2"+nounbyte[nounbyte.length-2]);
 							  //    System.out.println("the length in bytes 3"+nounbyte[nounbyte.length-3]);
										byte[] irr={30};
						  				temp=bm.addarray(bm.subarray(nounbyte,0,nounbyte.length-1),irr);
						  				System.out.println("suba");
				//	temp=bm.subarray(nounbyte,0,nounbyte.length-1);
					  					if(bm.isequal(nountemp,kaN))
										temp=bm.addarray(temp,in);
					  					output=bm.addarray(temp,nountemp);
							 }
  								 
									else	  if((((nounbyte[nounbyte.length-3])==1)||((nounbyte[nounbyte.length-3])==3)||((nounbyte[nounbyte.length-3])==5)||((nounbyte[nounbyte.length-3])==7)||((nounbyte[nounbyte.length-3])==9))&&(((nounbyte[nounbyte.length-2])==18)||((nounbyte[nounbyte.length-2])==20)))
  								 	 	{
  								 		System.out.println("am i here 4");
  										byte[] v={27};
  									 	temp=bm.addarray1(nounbyte,v);
  									 	if(bm.isequal(nountemp,kaN))
											 temp=bm.addarray(temp,in);
  										output=bm.addarray(temp,nountemp);
  										}
  									else if(((nounbyte[nounbyte.length-2])==25)||((nounbyte[nounbyte.length-2])==28)||((nounbyte[nounbyte.length-2])==20)||((nounbyte[nounbyte.length-2])==29)||((nounbyte[nounbyte.length-2])==19)||((nounbyte[nounbyte.length-2])==31))
  										{
  										System.out.println("am i here 5");
  										byte[] v={27};
  										temp=bm.addarray1(nounbyte,v);
  										if(bm.isequal(nountemp,kaN))
											 temp=bm.addarray(temp,in);
  										output=bm.addarray(temp,nountemp);
  									 	}
  								else if(((nounbyte[nounbyte.length-2])==14)||((nounbyte[nounbyte.length-2])==16)||((nounbyte[nounbyte.length-2])==18)||((nounbyte[nounbyte.length-2])==22)||((nounbyte[nounbyte.length-2])==27)||((nounbyte[nounbyte.length-2])==20)||((nounbyte[nounbyte.length-2])==30))
  										{ 
  										System.out.println("am i here 6");
  											temp=bm.subarray(nounbyte,0,nounbyte.length-1);
  											if(bm.isequal(nountemp,kaN))
											 temp=bm.addarray(temp,in);
  											output=bm.addarray(temp,nountemp);
  										}
  									else
  										{
  										System.out.println("am i here 7");
  										temp=nounbyte;
  										if(bm.isequal(nountemp,kaN))
											 temp=bm.addarray(temp,in);
  										output=bm.addarray(temp,nountemp);
  										}
  								 }//end
  								 else if((bm.isequal(nounbyte,avai))||(bm.isequal(nounbyte,evai))||(bm.isequal(nounbyte,ivai)))

								 		{
  									System.out.println("am i here 8");
								 			temp=bm.addarray(bm.subarray(nounbyte,0,2),aRRu);
								 			if(bm.isequal(nountemp,kaN))
											 temp=bm.addarray(temp,in);
								 			output=bm.addarray(temp,nountemp);

										}
								 else if((bm.isequal(nounbyte,sila))||(bm.isequal(nounbyte,pala)))

										{
										System.out.println("am i here 9");
											temp=bm.addarray(nounbyte,aRRu);
											if(bm.isequal(nountemp,kaN))
											 temp=bm.addarray(temp,in);
											output=bm.addarray(temp,nountemp);

										}

	 /*if ends with n,N,y,l.L and the nounbyte length<3*/
	 else if(((nounbyte[nounbyte.length-1])==19)||((nounbyte[nounbyte.length-1])==31)||((nounbyte[nounbyte.length-1])==24)||((nounbyte[nounbyte.length-1])==26)||((nounbyte[nounbyte.length-1])==29))
	{//start//99999
			System.out.println("am i here 10");
	 int no=nounbyte.length;

	 if(((nounbyte.length)<3)||((nounbyte.length)==3)||(((nounbyte.length)>3)&&((nounbyte[nounbyte.length-4]==14)||(nounbyte[nounbyte.length-4]==15)||(nounbyte[nounbyte.length-4]==16)
	 ||(nounbyte[nounbyte.length-4]==17)||(nounbyte[nounbyte.length-4]==18)||(nounbyte[nounbyte.length-4]==19)||(nounbyte[nounbyte.length-4]==20)||(nounbyte[nounbyte.length-4]==21)||(nounbyte[nounbyte.length-4]==22)||(nounbyte[nounbyte.length-4]==23)||(nounbyte[nounbyte.length-4]==24)
	 ||(nounbyte[nounbyte.length-4]==25)||(nounbyte[nounbyte.length-4]==26)||(nounbyte[nounbyte.length-4]==27)||(nounbyte[nounbyte.length-4]==28)||(nounbyte[nounbyte.length-4]==29)||(nounbyte[nounbyte.length-4]==31)||(nounbyte[nounbyte.length-4]==31))))/*||((nounbyte[nounbyte.length-5]==14)||(nounbyte[nounbyte.length-5]==15)||(nounbyte[nounbyte.length-5]==16)
	 ||(nounbyte[nounbyte.length-5]==17)||(nounbyte[nounbyte.length-5]==18)||(nounbyte[nounbyte.length-5]==19)||(nounbyte[nounbyte.length-5]==20)||(nounbyte[nounbyte.length-5]==21)||(nounbyte[nounbyte.length-5]==22)||(nounbyte[nounbyte.length-5]==23)||(nounbyte[nounbyte.length-5]==24)
	 ||(nounbyte[nounbyte.length-5]==25)||(nounbyte[nounbyte.length-5]==26)||(nounbyte[nounbyte.length-5]==27)||(nounbyte[nounbyte.length-5]==28)||(nounbyte[nounbyte.length-5]==29)||(nounbyte[nounbyte.length-5]==31)||(nounbyte[nounbyte.length-5]==31))))*/
		{//start for doubling
			  if(((nounstr.equals(en))||(nounstr.equals(un))||(nounstr.equals(nam))||(nounstr.equals(em))||(nounstr.equals(tan))||(nounstr.equals(um)))&&((bm.startswith(nountemp,a))))
				{//start1
				temp=nounbyte;
				if(bm.isequal(nountemp,kaN))
				temp=bm.addarray(temp,in);
				output=bm.addarray(temp,nountemp);
				}//end1
			 else if(((nounbyte[nounbyte.length-2])==1)||((nounbyte[nounbyte.length-2])==3)||((nounbyte[nounbyte.length-2])==5)||((nounbyte[nounbyte.length-2])==7)||((nounbyte[nounbyte.length-2])==9)||((nounbyte[nounbyte.length-2])==10)||((nounbyte[nounbyte.length-2])==12))
				{//start2
					String nounstrdoub=nounstr+nounstr.charAt(nounstr.length()-1);
					temp=tm.convert(nounstrdoub);

					if(((temp[temp.length-1]==14)||(temp[temp.length-1]==15)||(temp[temp.length-1]==16)
					 ||(temp[temp.length-1]==17)||(temp[temp.length-1]==18)||(temp[temp.length-1]==19)||(temp[temp.length-1]==20)||(temp[temp.length-1]==21)||(temp[temp.length-1]==22)||(temp[temp.length-1]==23)||(temp[temp.length-1]==24)
					 ||(temp[temp.length-1]==25)||(temp[temp.length-1]==26)||(temp[temp.length-1]==27)||(temp[temp.length-1]==28)||(temp[temp.length-1]==29)||(temp[temp.length-1]==31)||(temp[temp.length-1]==31))&&(bm.startswith(nountemp,k)))
					 {//2a
						temp=bm.subarray(temp,0,temp.length-1);
						if(bm.isequal(nountemp,kaN))
						{//2a1
						nounstrdoub=nounstr+nounstr.charAt(nounstr.length()-1);
						temp=tm.convert(nounstrdoub);
						temp=bm.addarray(temp,in);
						output=bm.addarray(temp,nountemp);
						}//2a1
					 }//2a
					else
						{//2b
						if(bm.isequal(nountemp,kaN))
						temp=bm.addarray(temp,in);
						output=bm.addarray(temp,nountemp);
						}//2b
				}//end2
			  else
				{//start3
				temp=nounbyte;
				if(bm.isequal(nountemp,kaN))
				temp=bm.addarray(temp,in);
				output=bm.addarray(temp,nountemp);
				}//end3
			}//end for doubling
		else
			{// start else for doubling
			temp=nounbyte;
			if(bm.isequal(nountemp,kaN))
			temp=bm.addarray(temp,in);
			output=bm.addarray(temp,nountemp);
			}// end else for doubling
	}//end 99999
 else
	{// start final else
		System.out.println("am i here subalalitha");
		String n=tm.revert(nounbyte);
		System.out.println("kodungalur"+n);
	temp=nounbyte;
	if(bm.isequal(nountemp,kaN))
	temp=bm.addarray(temp,in);
	output=bm.addarray(temp,nountemp);
	System.out.println("the sandhi returns"+tm.revert(output));
	}// end final else
System.out.println("the sandhi method returns "+output.toString());
  return output;
	}
}
