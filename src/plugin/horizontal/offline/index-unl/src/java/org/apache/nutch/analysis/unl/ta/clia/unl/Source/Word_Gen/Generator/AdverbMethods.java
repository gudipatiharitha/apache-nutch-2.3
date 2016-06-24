
package org.apache.nutch.analysis.unl.ta.clia.unl.Source.Word_Gen.Generator;


public class AdverbMethods
{
	ByteMethods bm=new ByteMethods();
	TabMethods tm=new TabMethods();
public byte[] addAdverb(String given_adverb,String advsuffix)
 {
  byte[] adverb_result=null;
  byte[] given_adverb_byte=tm.convert(given_adverb);
  byte[] L={29};
  byte[] n={31};
  //bm.printarray(given_adverb_byte);
  String []adverbtype1 = {"வேகம்","விரைவு","அழகு"};
  String []adverbtype2 = {"அடிக்கடி","இனிமேல்","இனியும்","மறுபடியும்","மீண்டும்","மெல்ல"};
  String []adverbtype3 = {"உள்","எதிர்","பின்","வௌத","போல","போன்று","கொண்டு","நோக்கி","பற்றி","குறித்து","சுற்றி","விட்டு","தவிர","முன்னிட்டு","வேண்டி","ஒட்டி","பொறுத்து","பொறுத்தவரை","என்று","முன்","சுற்றிலும்","ஆக","மேல்","கீழ்","எதிரில்","பக்கத்தில்","அருகில்","மாறாக","நேராக","வாயிலாக","மூலமாக","வழியாக","பேரில்","பொருட்டு","கூட","வசம்","இடம்","வரை"};
  String []adverbtype4 = {"கூச்சல்","பச்சை","மஞ்சள¢"};
//"õ¤ì","õ¤ì¾ñ¢","«ð£ô¢",,"ð¤ù¢","à÷¢","Þ¬ì«ò","ï´«õ","ñî¢î¤ò¤ô¢","ªõ÷¤«ò",,"ðî¤ô¢","àó¤ò","à÷¢÷","î°ï¢î","à¬ìò","«î£Áñ¢","Ýó"

  for(int i=0;i<adverbtype1.length;i++)
    if(given_adverb.equals(adverbtype1[i]))
          adverb_result=bm.addarray(given_adverb_byte,tm.convert(advsuffix));
  for(int i=0;i<adverbtype2.length;i++)
     if(given_adverb.equals(adverbtype2[i]))
          adverb_result=given_adverb_byte;
  for(int i=0;i<adverbtype3.length;i++)
      if(given_adverb.equals(adverbtype3[i]))
        if(bm.endswith(given_adverb_byte,L))
           adverb_result=bm.addarray(bm.addarray(given_adverb_byte,L),Constants.ee);
        else if(bm.endswith(given_adverb_byte,n))
           adverb_result=bm.addarray(bm.addarray(given_adverb_byte,n),Constants.ee);
         else
           adverb_result=bm.addarray(given_adverb_byte,Constants.ee);
  for(int i=0;i<adverbtype4.length;i++)
      if(given_adverb.equals(adverbtype4[i]))
          adverb_result=bm.addarray(given_adverb_byte,Constants.aay);

  return adverb_result;
 }
}
