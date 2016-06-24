
package org.apache.nutch.analysis.unl.ta.clia.unl.Source.Word_Gen.Generator;
import java.io.*;


/** This class adds case markers to the noun according
  *  to the postposition input given*/
public class PostPositionMethods
	{
		 TabMethods tm = new TabMethods();
		 ByteMethods bm = new ByteMethods();
		 sandhimethods1 sm=new sandhimethods1();
		 byte[] ukku = {5,14,14,5};
		 byte[] kku = {14,14,5};
		 byte[] rrku = {3,30,14,5};
		 byte[] ku = {14,5};
		 byte[] ai = {9};
    	 byte[] in = {3,31};
    	 /** This method adds the case markers according to the
    	   *  postposition input given as well as it checks for the
    	   *  singular or not
    	   *  Return type is string */
		public String addPostPositions(String noun,String postposition,int num,boolean isSingular)throws IOException
			{

			   String return_string = "";
			   //System.out.println("num            "+num);
               if(isSingular)
                              {
				if(noun.endsWith("ம்"))
                  noun=noun.substring(0,noun.length()-2).concat("த்த்");
				if((postposition.equals("விட"))||(postposition.equals("விடவும்"))||(postposition.equals("போல"))||(postposition.equals("போல்"))||(postposition.equals("போன்று"))||(postposition.equals("கொண்டு"))||(postposition.equals("நோக்கி"))||(postposition.equals("பற்றி"))||(postposition.equals("குறித்து"))||(postposition.equals("சுற்றி"))||(postposition.equals("சுற்றிலும்"))||(postposition.equals("விட்டு")) || (postposition.equals("தவிர")) || (postposition.equals("முன்னிட்டு")) || (postposition.equals("வேண்டி")) || (postposition.equals("ஒட்டி")) || (postposition.equals("பொறுத்து")) || (postposition.equals("பொறுத்தவரை")))
					{
						return_string=tm.revert(bm.addarray(tm.convert(noun),bm.addarray(ai,tm.convert(postposition))));
					}
				else if(postposition.equals("உடன் ") || postposition.equals("கூட") || postposition.equals("உடைய") ||postposition.equals("வசம்") || postposition.equals("இடம்") || postposition.equals("வரை") || postposition.equals("தோறும்") ||postposition.equals("ஆர"))
					{
						return_string = tm.revert(bm.addarray(tm.convert(noun),tm.convert(postposition)));
					}
				else if(postposition.equals("பதிலாக") ||postposition.equals("பதில்") ||postposition.equals("மாறாக") || postposition.equals("நேராக") || postposition.equals("உரிய") || postposition.equals("உள்ள") || postposition.equals("தகுந்த"))
					{
						if(num==1)
							return_string = tm.revert(bm.addarray(tm.convert(noun),bm.addarray(ukku,tm.convert(postposition))));
						else if(num==2)
						 	return_string = tm.revert(bm.addarray(tm.convert(noun),bm.addarray(kku,tm.convert(postposition))));
						else if(num==3)
						 	return_string = tm.revert(bm.addarray(tm.convert(noun),bm.addarray(rrku,tm.convert(postposition))));
						else if(num==4)
						 	return_string = tm.revert(bm.addarray(tm.convert(noun),bm.addarray(ku,tm.convert(postposition))));
					}
				else if(postposition.equals("பிறகு") ||postposition.equals("ஆக") || postposition.equals("என்று"))
				     return_string = tm.revert(bm.addarray(bm.addarray(tm.convert(noun),bm.addarray(ukku,tm.convert(postposition))),bm.addarray(tm.convert(" / "+noun),tm.convert(postposition))) );
			    else if(postposition.equals("வாயிலாக") || postposition.equals("மூலமாக") || postposition.equals("வழியாக") || postposition.equals("பேரில்") || postposition.equals("பொருட்டு") )
				     {
				    	 //if(num==1)
				    	   return_string = tm.revert(bm.addarray(tm.convert(noun),bm.addarray(in,tm.convert(postposition))));
				    	 //else if(num==2)
				    	 //  return_string = tm.revert(bm.addarray(tm.convert(noun),tm.convert(postposition)));
				    }
				else if(postposition.equals("என்று") || postposition.equals("முன்") ||postposition.equals("பின்") || postposition.equals("உள்") || postposition.equals("இடையே") || postposition.equals("நடுவே") ||postposition.equals("மத்தியில்") ||postposition.equals("வௌதயே") ||postposition.equals("மேல்") ||postposition.equals("கீழ்") || postposition.equals("எதிரில்") || postposition.equals("பக்கத்தில்") || postposition.equals("அருகில்")|| postposition.equals("குறுக்கே"))
				{
					if(num==1)
                    	return_string = tm.revert(bm.addarray( tm.convert(noun) , bm.addarray( ukku, tm.convert(postposition))));
                    else if(num==2)
                        return_string = tm.revert(bm.addarray( tm.convert(noun) , bm.addarray( rrku, tm.convert(postposition))));
                    else if(num==3)
                    	return_string = tm.revert(bm.addarray( tm.convert(noun) , bm.addarray(kku, tm.convert(postposition))));
                    else if(num==4)
                    	return_string = tm.revert(bm.addarray( tm.convert(noun) , bm.addarray( ku, tm.convert(postposition))));
                    else if(num==5)
                    	return_string = tm.revert(bm.addarray(tm.convert(noun),tm.convert(postposition)));
                    else if(num==6)
                    	return_string = tm.revert(bm.addarray(tm.convert(noun),bm.addarray(in,tm.convert(postposition))));
				}
			}
			else
			{
				noun = singularToPlural(noun);
				 			if(noun.endsWith("ம்"))
				                  noun=noun.substring(0,noun.length()-2).concat("த்த்");

								if((postposition.equals("விட"))||(postposition.equals("விடவும்"))||(postposition.equals("போல"))||(postposition.equals("போல்"))||(postposition.equals("போன்று"))||(postposition.equals("கொண்டு"))||(postposition.equals("நோக்கி"))||(postposition.equals("பற்றி"))||(postposition.equals("குறித்து"))||(postposition.equals("சுற்றி"))||(postposition.equals("சுற்றிலும்"))||(postposition.equals("விட்டு")) || (postposition.equals("தவிர")) || (postposition.equals("முன்னிட்டு")) || (postposition.equals("வேண்டி")) || (postposition.equals("ஒட்டி")) || (postposition.equals("பொறுத்து")) || (postposition.equals("பொறுத்தவரை")))
									{
										return_string=tm.revert(bm.addarray(tm.convert(noun),bm.addarray(ai,tm.convert(postposition))));
									}
			else if(postposition.equals("உடன்") || postposition.equals("கூட") || postposition.equals("உடைய") ||postposition.equals("வசம்") || postposition.equals("இடம்") || postposition.equals("வரை") || postposition.equals("தோறும்") ||postposition.equals("ஆர"))
									{
										return_string = tm.revert(bm.addarray(tm.convert(noun),tm.convert(postposition)));
									}
								else if(postposition.equals("பதிலாக") ||postposition.equals("பதில்") ||postposition.equals("மாறாக") || postposition.equals("நேராக") || postposition.equals("உரிய") || postposition.equals("உள்ள") || postposition.equals("தகுந்த"))
									{
										if(num==1)
											return_string = tm.revert(bm.addarray(tm.convert(noun),bm.addarray(ukku,tm.convert(postposition))));
										else if(num==2)
										 	return_string = tm.revert(bm.addarray(tm.convert(noun),bm.addarray(kku,tm.convert(postposition))));
										else if(num==3)
										 	return_string = tm.revert(bm.addarray(tm.convert(noun),bm.addarray(rrku,tm.convert(postposition))));
										else if(num==4)
										 	return_string = tm.revert(bm.addarray(tm.convert(noun),bm.addarray(ku,tm.convert(postposition))));
									}
								else if(postposition.equals("பிறகு") ||postposition.equals("ஆக ") || postposition.equals("என்று"))
								     return_string = tm.revert( bm.addarray(bm.addarray(tm.convert(noun),bm.addarray(ukku,tm.convert(postposition))),bm.addarray(tm.convert(" / "+noun),tm.convert(postposition))) );
							    else if(postposition.equals("வாயிலாக") || postposition.equals("மூலமாக") || postposition.equals("வழியாக") || postposition.equals("பேரில்") || postposition.equals("பொருட்டு") )
								     {
								    	 //if(num==1)
								    	   return_string = tm.revert(bm.addarray(tm.convert(noun),bm.addarray(in,tm.convert(postposition))));
								    	 //else if(num==2)
								    	 //  return_string = tm.revert(bm.addarray(tm.convert(noun),tm.convert(postposition)));
								    }
								else if(postposition.equals("என்று") || postposition.equals("முன்") ||postposition.equals("பின்") || postposition.equals("உள்") || postposition.equals("இடையே") || postposition.equals("நடுவே") ||postposition.equals("மத்தியில்") ||postposition.equals("வௌதயே") ||postposition.equals("மேல்") ||postposition.equals("கீழ்") || postposition.equals("எதிரில்") || postposition.equals("பக்கத்தில்") || postposition.equals("அருகில்")|| postposition.equals("குறுக்கே"))
								{
									if(num==1)
				                    	return_string = tm.revert(bm.addarray( tm.convert(noun) , bm.addarray( ukku, tm.convert(postposition))));
				                    else if(num==2)
				                        return_string = tm.revert(bm.addarray( tm.convert(noun) , bm.addarray( rrku, tm.convert(postposition))));
				                    else if(num==3)
				                    	return_string = tm.revert(bm.addarray( tm.convert(noun) , bm.addarray(kku, tm.convert(postposition))));
				                    else if(num==4)
				                    	return_string = tm.revert(bm.addarray( tm.convert(noun) , bm.addarray( ku, tm.convert(postposition))));
				                    else if(num==5)
				                    	return_string = tm.revert(bm.addarray(tm.convert(noun),tm.convert(postposition)));
				                    else if(num==6)
				                    	return_string = tm.revert(bm.addarray(tm.convert(noun),bm.addarray(in,tm.convert(postposition))));
								}

			}
			  return return_string;
			}
/** This  method converts sinular to plural
  * for the given noun input*/
public String singularToPlural(String noun)throws IOException
	{
		byte[] kal = {14,1,29} ;
		byte[] kkal = {14,14,1,29} ;
	    byte[] nounbyte = tm.convert(noun);

		if(nounbyte[nounbyte.length-1]==23)
			{
				nounbyte[nounbyte.length-1]=15;
				nounbyte = bm.addarray1(nounbyte,kal);
				noun = tm.revert(nounbyte);
			}
		else if((nounbyte[nounbyte.length-1]==4)||(nounbyte[nounbyte.length-1]==6))
			{
				nounbyte = bm.addarray1(nounbyte,kkal);
				noun = tm.revert(nounbyte);
			}
		else
			{
				nounbyte = bm.addarray1(nounbyte,kal);
				noun = tm.revert(nounbyte);
			}
		return noun;
	}
}
