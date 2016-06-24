
package org.apache.nutch.analysis.unl.ta.clia.unl.Source.Word_Gen.Generator;
import java.io.*;

public class verbsandhimeth{
	TabMethods tm =new TabMethods();
	ByteMethods bm = new ByteMethods();
public byte[] verbsandhi(byte verbbyte[],byte[] inflect)throws IOException
    {
		 byte[] aRRu=tm.convert("அற்று");byte[] vaRRu=tm.convert("வற்று");
		 byte[] pala=tm.convert("பல"); byte[] sila=tm.convert("சில");
		 byte[] avai=tm.convert("அவை"); byte[] ivai=tm.convert("இவை");
		 byte[] evai=tm.convert("எவை"); byte[] ellam=tm.convert("எல்லாம்");
		 byte[] a ={1};byte[] y ={24};byte[] k ={14};byte[] in={3,31};byte[] kaN={14,1,19};
		String tan="தன்";
		String en="என்";
		String un="உன்";
		String nam="நம்";
		String em="எம்";
		String um="உம்";
		String nounstr=tm.revert(verbbyte);
////System.out.println("entered the sandhimethods");
        byte[] temp=null; byte[] output=null;
         int x=verbbyte.length-1;
       // //System.out.println("x      " +x);
        if(verbbyte.length==1 && verbbyte[0]==0)
          temp=inflect;
        else if(inflect.length==1 && inflect[0]==0)
        temp=verbbyte;

  							/*if ends with u*/
  								 else if((verbbyte[verbbyte.length-1])==5)
  								 {//start
  								 ////System.out.println("uuuuuuuuuuuuuuuuuu");

  									  if((((verbbyte[verbbyte.length-3])==2)||((verbbyte[verbbyte.length-3])==4)||((verbbyte[verbbyte.length-3])==6)||((verbbyte[verbbyte.length-3])==8)||((verbbyte[verbbyte.length-3])==11))&&(((verbbyte[verbbyte.length-2])==18)||((verbbyte[verbbyte.length-2])==30)))
  										 {
  											 nounstr =tm.revert(bm.subarray(verbbyte,0,3));
  										  	 String nounstrdoub=nounstr+nounstr.charAt(nounstr.length()-1);
  								 		     temp=tm.convert(nounstrdoub);
  								 		     output=bm.addarray(temp,inflect);
  								 		 }
  								 	 else if((((verbbyte[verbbyte.length-3])==1)||((verbbyte[verbbyte.length-3])==3)||((verbbyte[verbbyte.length-3])==5)||((verbbyte[verbbyte.length-3])==7)||((verbbyte[verbbyte.length-3])==9))&&(((verbbyte[verbbyte.length-2])==18)||((verbbyte[verbbyte.length-2])==20)))
  								 	 	{

  										byte[] v={27};
  									 	temp=bm.addarray1(verbbyte,v);
  									 	output=bm.addarray(temp,inflect);
  										}
  									else if(((verbbyte[verbbyte.length-2])==25)||((verbbyte[verbbyte.length-2])==28)||((verbbyte[verbbyte.length-2])==20)||((verbbyte[verbbyte.length-2])==29)||((verbbyte[verbbyte.length-2])==19)||((verbbyte[verbbyte.length-2])==31))
  										{
  										byte[] v={27};
  										temp=bm.addarray1(verbbyte,v);
  										output=bm.addarray(temp,inflect);
  									 	}
  									else if(((verbbyte[verbbyte.length-2])==14)||((verbbyte[verbbyte.length-2])==16)||((verbbyte[verbbyte.length-2])==18)||((verbbyte[verbbyte.length-2])==22)||((verbbyte[verbbyte.length-2])==27)||((verbbyte[verbbyte.length-2])==20)||((verbbyte[verbbyte.length-2])==30))
  										{

  											temp=bm.subarray(verbbyte,0,verbbyte.length-1);
  											output=bm.addarray(temp,inflect);
  										}
  									else
  										{
  										temp=verbbyte;
  										output=bm.addarray(temp,inflect);
  										}
  								 }//end
  								 else if((bm.isequal(verbbyte,avai))||(bm.isequal(verbbyte,evai))||(bm.isequal(verbbyte,ivai)))

								 		{
								 			temp=bm.addarray(bm.subarray(verbbyte,0,2),aRRu);
								 			output=bm.addarray(temp,inflect);

										}
								 else if((bm.isequal(verbbyte,sila))||(bm.isequal(verbbyte,pala)))

										{
											temp=bm.addarray(verbbyte,aRRu);
											output=bm.addarray(temp,inflect);

										}

	 /*if ends with n,N,y,l.L and the verbbyte length<3*/
	 else if(((verbbyte[verbbyte.length-1])==19)||((verbbyte[verbbyte.length-1])==31)||((verbbyte[verbbyte.length-1])==24)||((verbbyte[verbbyte.length-1])==26)||((verbbyte[verbbyte.length-1])==29))
	{//start//99999
	 int no=verbbyte.length;

	 if(((verbbyte.length)<3)||((verbbyte.length)==3)||(((verbbyte.length)>3)&&((verbbyte[verbbyte.length-4]==14)||(verbbyte[verbbyte.length-4]==15)||(verbbyte[verbbyte.length-4]==16)
	 ||(verbbyte[verbbyte.length-4]==17)||(verbbyte[verbbyte.length-4]==18)||(verbbyte[verbbyte.length-4]==19)||(verbbyte[verbbyte.length-4]==20)||(verbbyte[verbbyte.length-4]==21)||(verbbyte[verbbyte.length-4]==22)||(verbbyte[verbbyte.length-4]==23)||(verbbyte[verbbyte.length-4]==24)
	 ||(verbbyte[verbbyte.length-4]==25)||(verbbyte[verbbyte.length-4]==26)||(verbbyte[verbbyte.length-4]==27)||(verbbyte[verbbyte.length-4]==28)||(verbbyte[verbbyte.length-4]==29)||(verbbyte[verbbyte.length-4]==31)||(verbbyte[verbbyte.length-4]==31))))/*||((verbbyte[verbbyte.length-5]==14)||(verbbyte[verbbyte.length-5]==15)||(verbbyte[verbbyte.length-5]==16)
	 ||(verbbyte[verbbyte.length-5]==17)||(verbbyte[verbbyte.length-5]==18)||(verbbyte[verbbyte.length-5]==19)||(verbbyte[verbbyte.length-5]==20)||(verbbyte[verbbyte.length-5]==21)||(verbbyte[verbbyte.length-5]==22)||(verbbyte[verbbyte.length-5]==23)||(verbbyte[verbbyte.length-5]==24)
	 ||(verbbyte[verbbyte.length-5]==25)||(verbbyte[verbbyte.length-5]==26)||(verbbyte[verbbyte.length-5]==27)||(verbbyte[verbbyte.length-5]==28)||(verbbyte[verbbyte.length-5]==29)||(verbbyte[verbbyte.length-5]==31)||(verbbyte[verbbyte.length-5]==31))))*/
		{//start for doubling
			  if(((nounstr.equals(en))||(nounstr.equals(un))||(nounstr.equals(nam))||(nounstr.equals(em))||(nounstr.equals(tan))||(nounstr.equals(um)))&&((bm.startswith(inflect,a))))
				{//start1
				temp=verbbyte;
				output=bm.addarray(temp,inflect);
				}//end1
			 else if(((verbbyte[verbbyte.length-2])==1)||((verbbyte[verbbyte.length-2])==3)||((verbbyte[verbbyte.length-2])==5)||((verbbyte[verbbyte.length-2])==7)||((verbbyte[verbbyte.length-2])==9)||((verbbyte[verbbyte.length-2])==10)||((verbbyte[verbbyte.length-2])==12))
				{//start2
					String nounstrdoub=nounstr+nounstr.charAt(nounstr.length()-1);
					temp=tm.convert(nounstrdoub);
					output=bm.addarray(temp,inflect);
				}//end2
			  else
				{//start3
				temp=verbbyte;
				output=bm.addarray(temp,inflect);
				}//end3
			}//end for doubling
		else
			{// start else for doubling
			temp=verbbyte;
			output=bm.addarray(temp,inflect);
			}// end else for doubling
	}//end 99999
 else
	{// start final else
	temp=verbbyte;
	output=bm.addarray(temp,inflect);
	}// end final else

  return output;
	}
}
