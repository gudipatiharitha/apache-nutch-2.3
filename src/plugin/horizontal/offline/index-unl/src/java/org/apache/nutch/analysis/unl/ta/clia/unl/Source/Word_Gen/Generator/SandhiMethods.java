

package org.apache.nutch.analysis.unl.ta.clia.unl.Source.Word_Gen.Generator;

public class SandhiMethods{
static TabMethods tm =new TabMethods();
static ByteMethods bm = new ByteMethods();
static byte[] nounbyte=tm.convert("அவை");
static byte[] pala=tm.convert("பல");static byte[] sila=tm.convert("சில");
static byte[] avai=tm.convert("அவை");static byte[] ivai=tm.convert("இவை");
static byte[] evai=tm.convert("எவை");static byte[] ellam=tm.convert("எல்லாம்");
static byte[] c=tm.convert("கள்");static byte[] aRRu=tm.convert("அற்று");
static byte[] resultsandhi=null;static byte [] ai={9};
static String[] aaaa={"பல","சில","அவை","இவை","எவை","எல்லாம்"};
static byte[] kan=tm.convert("கண்");
static byte[] avan=tm.convert("அவன்");

public static void main(String a[])
  {
    checksandhiarray(nounbyte,c);
   }

public static void checksandhiarray(byte nounbyte[],byte[] c)
	{
		int x=nounbyte[nounbyte.length-2];
		//System.out.println("x"+x);

		if((nounbyte[nounbyte.length-1])==29)
		{
			nounbyte[nounbyte.length-1]=18;
			resultsandhi=bm.addarray1(nounbyte,c);
			//System.out.println("resultsandhi"+tm.revert(resultsandhi));
		}
		else if((nounbyte[nounbyte.length-1])==23)
		{
			//(((nounbyte[nounbyte.length-1])==23)&&((nounbyte[nounbyte.length-1])==23))
			nounbyte[nounbyte.length-1]=15;
			resultsandhi=bm.addarray1(nounbyte,c);
			//System.out.println("resultsandhi"+tm.revert(resultsandhi));
		}
		else if(((nounbyte[nounbyte.length-1])==9)&&((nounbyte[nounbyte.length-1])==27))
		{
			//System.out.println("entered");
			//nounbyte[nounbyte.length-1]=27;
			resultsandhi=bm.addarray1(nounbyte,aRRu);
			//System.out.println("resultsandhi"+tm.revert(resultsandhi));

		}
		//(bm.isequal(nounbyte,avai))
		else if(((nounbyte[nounbyte.length-2])==27)&&((nounbyte[nounbyte.length-1])==9))
		{
			//nounbyte=nounbyte[nounbyte.length-1];
			//System.out.println("nounbyte1"+tm.revert(nounbyte));
			//nounbyte=bm.removevowel(ai,nounbyte);
				//System.out.println("kabhi kabhi mera dhil me kayal aata hein"+tm.revert(bm.addarray1(kan,avan)));
			resultsandhi=bm.addarray1(nounbyte,aRRu);
			//System.out.println("resultsandhi"+tm.revert(resultsandhi));
			//System.out.println("nounbyte"+tm.revert(nounbyte));
		}

		/*else if((bm.isequal(nounbyte,pala))||(bm.isequal(nounbyte,sila))||(bm.isequal(nounbyte,avai))||(bm.isequal(nounbyte,ivai))||(bm.isequal(nounbyte,evai))||(bm.isequal(nounbyte,ellam)))
		{
			for(int i=0;i<aaaa.length;i++){
			resultsandhi=bm.addarray1(tm.convert(aaaa[i]),aRRu);
			//byte[] resultsandhi1=bm.addarray1(resultsandhi,tm.convert("ஐ ));
			//System.out.println("resultsandhi"+tm.revert(resultsandhi));}
		}*/

	}
}
