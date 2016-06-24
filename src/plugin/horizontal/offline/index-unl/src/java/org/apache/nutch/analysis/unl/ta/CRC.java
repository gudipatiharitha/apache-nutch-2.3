
/**
 * Class Name  : CRC
 *
 * Date        : 10 June 2009
 * 
 * Description :
 * 
 */
package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.index.unl;
public class CRC {
   //tam1 is Concept1 Tamil term
   public String tam1;
   //c1 is Concept1 english concept
   public String c1;
   //pos1 as concept1's parts of speech
   public String pos1;
   //QWTag1 says Concept1 is QW or EQW
   public String QWTag1;
   //MWTag1 says Concept1 is MW or Not
   public String MWTag1;
   // rel shows the re4lation between two concepts
   public String rel;
   //tam2 is Concept1 Tamil term
   public String tam2;
   //c2 is Concept2 english concept
   public String c2;
   //pos2 as concept2's parts of speech
   public String pos2;
   //QWTag2 says Concept2 is QW or EQW
   public String QWTag2;
   //MWTag2 says Concept2 is MW or Not
   public String MWTag2;
public int crcFreqCnt;
   /*public static void main(String[] args) {

   ArrayList al = new ArrayList();
   int i =0;
   while(i < 3) {
       CRC crc = new CRC();
       crc.c1 = "che" + i;
       i++;
       al.add(crc);
   }
   for (Object o : al) {
       CRC c = (CRC)o;
       System.out.println("the value stored is" +c.c1);
   }

}*/
}
