
package org.apache.nutch.analysis.unl.ta.clia.unl.Source.Word_Gen.Generator;

import java.io.FileInputStream;
import java.io.*;
public class VerbMethods
{
		ByteMethods bm = new ByteMethods();
		TabMethods tm = new TabMethods();
		FileRead file = new FileRead();

		final int PRESENT		=	2;
		final int PAST			=	3;
		final int FUTURE		=	4;
		final int FUTURE_NEUTER		=	5;
		final int INFINITIVE 		=	6;
		final int VERBALPARTICIPLE 	=	7;
		final int PRESENT_NEUTER 	=	8;

	public VerbMethods()
	{
	}
	/** This method returns the generated string using
	  *  auxillaryverb,tense,verb and adverb*/
	public String[] callAll(String subject,String verb,String aux[],String tense,String aaoo,String adverb)throws IOException
	{
	//	PrintStream ps = new PrintStream(new FileOutputStream(new File("/usr/output/callall.txt")));

		String result[] =new String[16];

		result[0]="விடையை கணிகக முடிய முடியவில்லை";

		byte[][] b=new byte[4][];
		//ps.println("tm.revert(b)  "+tm.revert(b));
		byte[] b1=tm.convert(verb);
		//ps.println("verb"+verb);
		//ps.println("the revert"+tm.revert(b1));
		byte[] b2=tm.convert(subject);
		//System.out.println("subject"+subject);
		byte[] b3=tm.convert(tense);
		//System.out.println("tense"+tense);
		byte[][] a1=null;
		int auxlen=aux.length;

		b=addTense(b1,b2,b3);
		//,b2

		byte[][] auxi=new byte[4][];

		for(int i=0;i<4;i++)
		{
			if(aux[i].equals("துணை"))
				auxlen--;
			auxi[i]=tm.convert(aux[i]);
		}
byte[] c=genderEndings(b2,b3,b[1]);
/*System.out.println("genderendings c  "+tm.revert(c));
System.out.println("genderendings b2   "+tm.revert(b2));
System.out.println("genderendings b3   "+tm.revert(b3));
System.out.println("genderendings b[1] "+tm.revert(b[1]));
System.out.println("genderendings b[2] "+tm.revert(b[2]));*/
		String s3="";
		byte[] z=null;
		z=bm.addarray(b[2],c);
//System.out.println("gender z  "+tm.revert(z));
		String adverb_string ="";

		if("வினையடை".equals(adverb)); ///
		else
			adverb_string = tm.revert(addAdverb(adverb))+" ";

		try
		{
			if(auxlen==0)
			{
				s3=tm.revert(bm.addarray1(b[0],z));
				result[0]=" "+adverb_string+" "+s3+"\n";
			}
			else
			{
				a1=addAuxi(b[3],b[1],auxi,b3,b2,auxlen);
				for(int i=0;i<a1.length;i++)
					result[i]=" "+adverb_string+" "+tm.revert(a1[i])+"\n";
			}
		}catch(Exception e){
			//e.printStackTrace();
			System.out.println("method callall");
			}
//ps.println("the result"+result.toString());
		return result;
		
	}

	/** This method returns the byte array,
	  * after adding kiru,kindru etc according
	  * to the tense selected*/
	public byte[][] addTense(byte[] verbbyte,byte[] subbyte,byte[] tenbyte)throws IOException
		{
               //  PrintStream ps = new PrintStream(new FileOutputStream(new File("/usr/output/callall.txt")));
		byte[][] b = new byte[4][];
		int cat=0;
		String verb=tm.revert(verbbyte);
		//ps.println("verb "+ verb);

		try
		{
			cat = file.getVerbCat(verb,"verbcat.txt");
			//ps.println("verbcat  "+cat);
			switch(cat)
			{
				case 1:
						b[3]=verbbyte;
						b[0]=verbbyte;             //verb
						b[1] = Constants.th;
						if(bm.isequal(Constants.prebyte,tenbyte))
						{
							if(bm.isequal(Constants.avai,subbyte) || bm.isequal(Constants.avaikal,subbyte))
								b[2] = Constants.kindru;
							else
								b[2] = Constants.kiru;
						}
						if(bm.isequal(Constants.pasbyte,tenbyte))
							b[2] = Constants.th;
						if(bm.isequal(Constants.futbyte,tenbyte))
						{
							if(bm.isequal(subbyte,Constants.avai)|| bm.isequal(subbyte,Constants.avaikal) || bm.isequal(subbyte,Constants.athu))
								b[2] = Constants.zero;
							else
								b[2] = Constants.v;
						}
						break;

				case 2:
						b[3]=verbbyte;
						b[0]=verbbyte;             //verb
						b[1] = Constants.nth;
						if(bm.isequal(Constants.prebyte,tenbyte))
						{
							if(bm.isequal(Constants.avai,subbyte) || bm.isequal(Constants.avaikal,subbyte))
								b[2] = Constants.kkindru;
							else
								b[2] = Constants.kkiru;
						}
						if(bm.isequal(Constants.pasbyte,tenbyte))
							b[2] = Constants.nth;
						if(bm.isequal(Constants.futbyte,tenbyte))
						{
							if(bm.isequal(subbyte,Constants.avai)|| bm.isequal(subbyte,Constants.avaikal) || bm.isequal(subbyte,Constants.athu))
								b[2] = Constants.kk;
							else
								b[2] = Constants.pp;
						}
						break;

				case 3:
						b[3] = verbbyte;
						b[0]=verbbyte;             //verb
						b[1] = Constants.nth;
						if(bm.isequal(Constants.prebyte,tenbyte))
						{
							if(bm.isequal(Constants.avai,subbyte) || bm.isequal(Constants.avaikal,subbyte))
								b[2] = Constants.kindru;
							else
								b[2] = Constants.kiru;
						}
						if (bm.isequal(Constants.pasbyte,tenbyte))
						{
							if(bm.isequal(Constants.avai,subbyte) || bm.isequal(Constants.avaikal,subbyte))
								b[2] = Constants.nth;
							else
								b[2] = Constants.nth;
						}
						if(bm.isequal(Constants.futbyte,tenbyte))
						{
							if(bm.isequal(subbyte,Constants.avai)|| bm.isequal(subbyte,Constants.avaikal) || bm.isequal(subbyte,Constants.athu))
								b[2] = Constants.zero;
							else
								b[2] = Constants.v;
						}
						break;

				case 4:
						b[3]=verbbyte;
						b[0]=verbbyte;             //verb
						b[1]  = Constants.n;
						if(bm.isequal(Constants.prebyte,tenbyte))
						{
							if(bm.isequal(Constants.avai,subbyte) || bm.isequal(Constants.avaikal,subbyte))
								b[2] = Constants.kindru;
							else
								b[2] = Constants.kiru;
						}
						if(bm.isequal(Constants.pasbyte,tenbyte))
							b[2] = Constants.n;
						if(bm.isequal(Constants.futbyte,tenbyte))
						{
							if(bm.isequal(subbyte,Constants.avai)|| bm.isequal(subbyte,Constants.avaikal) || bm.isequal(subbyte,Constants.athu))
								b[2] = Constants.k;
							else
								b[2] = Constants.v;
						}
						break;
				case 5:
						b[3]=verbbyte;
						b[0]=verbbyte;             //verb
						b[1] = Constants.thth;
						if(bm.isequal(Constants.prebyte,tenbyte))
						{
							if(bm.isequal(Constants.avai,subbyte) || bm.isequal(Constants.avaikal,subbyte))
								b[2] = Constants.kkindru;
							else
								b[2] = Constants.kkiru;
						}
						if(bm.isequal(Constants.pasbyte,tenbyte))
							b[2] = Constants.thth;
						if(bm.isequal(Constants.futbyte,tenbyte))
						{
							if(bm.isequal(subbyte,Constants.avai)|| bm.isequal(subbyte,Constants.avaikal) || bm.isequal(subbyte,Constants.athu))
								b[2] = Constants.kk;
							else
								b[2] = Constants.pp;
						}
						break;
				case 6:
						b[3]=verbbyte;
						b[0]=verbbyte;             //verb
						b[1] = Constants.t;
						if(bm.isequal(Constants.prebyte,tenbyte))
						{
							if(bm.isequal(Constants.avai,subbyte) || bm.isequal(Constants.avaikal,subbyte))
								b[2] = Constants.kindru;
							else
								b[2] = Constants.kiru;
						}
						if(bm.isequal(Constants.pasbyte,tenbyte))
							b[2] = Constants.t;
						if(bm.isequal(Constants.futbyte,tenbyte))
						{
							if(bm.isequal(subbyte,Constants.avai)|| bm.isequal(subbyte,Constants.avaikal) || bm.isequal(subbyte,Constants.athu))
								b[2] = Constants.inn;
							else
								b[2] = Constants.p;
						}
						break;
				case 7:
						b[3] = verbbyte;
						b[0] = verbbyte;             //verb
						b[1] = Constants.rr;
						if(bm.isequal(Constants.prebyte,tenbyte))
						{
							if(bm.isequal(Constants.avai,subbyte) || bm.isequal(Constants.avaikal,subbyte))
								b[2] = Constants.kindru;
							else
								b[2] = Constants.kiru;
						}
						if(bm.isequal(Constants.pasbyte,tenbyte))
							b[2] = Constants.rr;
						if(bm.isequal(Constants.futbyte,tenbyte))
						{
							if(bm.isequal(subbyte,Constants.avai)|| bm.isequal(subbyte,Constants.avaikal) || bm.isequal(subbyte,Constants.athu))
                        b[2] = Constants.n;
                     else
                        b[2]= Constants.p;
                    }
                  break;
    case 8:
                  b[3]=bm.subarray(verbbyte,0,verbbyte.length-1);
	 	      	  b[0]=verbbyte;             //verb
                  b[1] = Constants.in;
                  if(bm.isequal(Constants.prebyte,tenbyte))
                   {
                   if(bm.isequal(Constants.avai,subbyte) || bm.isequal(Constants.avaikal,subbyte))
                     b[2] = Constants.kindru;
                   else
                     b[2] = Constants.kiru;
                    }
                  if(bm.isequal(Constants.pasbyte,tenbyte))
                   {
                    b[0]=bm.subarray(b[0],0,b[0].length-1);
                    b[2] = Constants.in;
                    }
                  if(bm.isequal(Constants.futbyte,tenbyte))
                    {
                     if(bm.isequal(subbyte,Constants.avai)|| bm.isequal(subbyte,Constants.avaikal) || bm.isequal(subbyte,Constants.athu))
                        b[2] = Constants.zero;
                     else
                        b[2] = Constants.v;
                    }
                  break;
    case 9:
                  b[3]=bm.subarray(verbbyte,0,verbbyte.length-1);
	 	      	  b[0]=verbbyte;             //verb
                  b[1] = Constants.t;
                  if(bm.isequal(Constants.prebyte,tenbyte))
                   {
                   if(bm.isequal(Constants.avai,subbyte) || bm.isequal(Constants.avaikal,subbyte))
                     b[2] = Constants.kindru;
                   else
                     b[2] = Constants.kiru;
                    }
                  if(bm.isequal(Constants.pasbyte,tenbyte))
                   {
                    b[0]=bm.subarray(b[0],0,b[0].length-1);
                    b[2] = Constants.t;
                    }
                  if(bm.isequal(Constants.futbyte,tenbyte))
                    {
                     if(bm.isequal(subbyte,Constants.avai)|| bm.isequal(subbyte,Constants.avaikal) || bm.isequal(subbyte,Constants.athu))
                        b[2] = Constants.zero;
                     else
                        b[2] = Constants.v;
                    }
                  break;
   case 10:
                  b[3] = bm.subarray(verbbyte,0,verbbyte.length-1);
	 	      	  b[0] = verbbyte;             //verb
                  b[1] = Constants.rr;
                  if(bm.isequal(Constants.prebyte,tenbyte))
                   {
                   if(bm.isequal(Constants.avai,subbyte) || bm.isequal(Constants.avaikal,subbyte))
                     b[2] = Constants.kindru;
                   else
                     b[2] = Constants.kiru;
                    }
                  if(bm.isequal(Constants.pasbyte,tenbyte))
                   {
                    b[0] = bm.subarray(b[0],0,b[0].length-1);
                    b[2] = Constants.rr;
                    }
                  if(bm.isequal(Constants.futbyte,tenbyte))
                    {
                     if(bm.isequal(subbyte,Constants.avai)|| bm.isequal(subbyte,Constants.avaikal) || bm.isequal(subbyte,Constants.athu))
                        b[2] = Constants.zero;
                     else
                        b[2] = Constants.v;
                    }
                  break;
    case 11:
                  b[3] = verbbyte;
	 	      	  b[0] = verbbyte;             //verb
                  b[1] = Constants.tt;
                  if(bm.isequal(Constants.prebyte,tenbyte))
                   {
                   if(bm.isequal(Constants.avai,subbyte) || bm.isequal(Constants.avaikal,subbyte))
                   	{
                   	 b[0]=bm.subarray(b[0],0,b[0].length-1);
                     b[2] = bm.addarray(Constants.t,Constants.kindru);
                     }
                   else
                   	{
					 b[0]=bm.subarray(b[0],0,b[0].length-1);
	                 b[2] = bm.addarray(Constants.t,Constants.kiru);
				 	}
                    }
                  if(bm.isequal(Constants.pasbyte,tenbyte))
                   {
                    b[0]=bm.subarray(b[0],0,b[0].length-1);
                    b[2] = Constants.tt;  ///////////
                    }
                  if(bm.isequal(Constants.futbyte,tenbyte))
                    {
                     if(bm.isequal(subbyte,Constants.avai)|| bm.isequal(subbyte,Constants.avaikal) || bm.isequal(subbyte,Constants.athu))
                     {
                     	b[0]=bm.subarray(b[0],0,b[0].length-1);
                        b[2] = bm.addarray(Constants.t,Constants.k);
					}
                     else
                     	{
                     	b[0]=bm.subarray(b[0],0,b[0].length-1);
                        b[2] = bm.addarray(Constants.t,Constants.p);
						}
                    }
                  break;
     case 12:
                  b[3] = verbbyte;
	 	      	  b[0] = verbbyte;             //verb
                  b[1] = Constants.innt;
                  if(bm.isequal(Constants.prebyte,tenbyte))
                   {
                   if(bm.isequal(Constants.avai,subbyte) || bm.isequal(Constants.avaikal,subbyte))
                     b[2] = Constants.kindru;
                   else
                     b[2] = Constants.kiru;
                    }
                  if(bm.isequal(Constants.pasbyte,tenbyte))
                   {
                    b[0] = bm.subarray(b[0],0,b[0].length-1);
                    b[2] = Constants.innt;
                    }
                  if(bm.isequal(Constants.futbyte,tenbyte))
                    {
                     if(bm.isequal(subbyte,Constants.avai)|| bm.isequal(subbyte,Constants.avaikal) || bm.isequal(subbyte,Constants.athu))
                        b[2] = Constants.ll;
                     else
                        b[2] = Constants.v;
                    }
                    break;
       case 13:
       				 //System.out.println("entered the category 13");
	                  b[3] = verbbyte;
	  	 	      	  b[0] = verbbyte;             //verb
	                  b[1] = Constants.rrrr;
	                    if(bm.isequal(Constants.prebyte,tenbyte))
	                     {
	                     if(bm.isequal(Constants.avai,subbyte) || bm.isequal(Constants.avaikal,subbyte))
	                     {
						   b[0] = bm.subarray(b[0],0,b[0].length-1);
	                       b[2] = bm.addarray(Constants.rr,Constants.kindru);
					   	 }
	                     else
	                     {
	                       b[0] = bm.subarray(b[0],0,b[0].length-1);
	                       b[2] = bm.addarray(Constants.rr,Constants.kiru);
	                        }
	                      }
	                    if(bm.isequal(Constants.pasbyte,tenbyte))
	                     {
	                      b[0] = bm.subarray(b[0],0,b[0].length-1);
	                      b[2] = Constants.rrrr;
	                      }
	                    if(bm.isequal(Constants.futbyte,tenbyte))
	                      {
	                       if(bm.isequal(subbyte,Constants.avai)|| bm.isequal(subbyte,Constants.avaikal) || bm.isequal(subbyte,Constants.athu))
	                       {
	                      	  b[0] = bm.subarray(b[0],0,b[0].length-1);
	                          b[2] = bm.addarray(Constants.rr,Constants.k);
						  }
	                       else
	                       {

	                      b[0] = bm.subarray(b[0],0,b[0].length-1);
	                          b[2] = bm.addarray(Constants.rr,Constants.p);
						  }
	                      }
                    break;
   case 14:
                  b[3] = bm.subarray(verbbyte,0,verbbyte.length-1);
	 	      	  b[0] = verbbyte;            //verb
                  b[1] = Constants.nrr;
                  if(bm.isequal(Constants.prebyte,tenbyte))
                   {
                   if(bm.isequal(Constants.avai,subbyte) || bm.isequal(Constants.avaikal,subbyte))
                     b[2] = Constants.kindru;
                   else
                     b[2] = Constants.kiru;
                    }
                  if(bm.isequal(Constants.pasbyte,tenbyte))
                   {
                    b[0] = bm.subarray(b[0],0,b[0].length-1);
                    b[2] = Constants.nrr;
                    }
                  if(bm.isequal(Constants.futbyte,tenbyte))
                    {
                     if(bm.isequal(subbyte,Constants.avai)|| bm.isequal(subbyte,Constants.avaikal) || bm.isequal(subbyte,Constants.athu))
                        b[2] = Constants.l;
                     else
                        b[2] = Constants.v;
                    }
                  break;
  case 15:
                  b[3] = bm.subarray(verbbyte,0,verbbyte.length-1);
	 	      	  b[0] = verbbyte;             //verb
                  b[1] = Constants.inin;
                  if(bm.isequal(Constants.prebyte,tenbyte))
                   {
                    if(bm.isequal(Constants.avai,subbyte) || bm.isequal(Constants.avaikal,subbyte))
                     b[2] = Constants.kindru;
                   else
                     b[2] = Constants.kiru;
                    }
                  if(bm.isequal(Constants.pasbyte,tenbyte))
                   {
                    b[0] = bm.subarray(b[0],0,b[0].length-1);
                    b[2] = Constants.inin;
                    }
                  if(bm.isequal(Constants.futbyte,tenbyte))
                    {

                    if(bm.isequal(subbyte,Constants.avai)|| bm.isequal(subbyte,Constants.avaikal) || bm.isequal(subbyte,Constants.athu))
                    	{
						String verbstrdoub=verb+verb.charAt(verb.length()-1);
				 		b[0]=tm.convert(verbstrdoub);
                        b[2] = Constants.zero;
						}
                     else
                        b[2] = Constants.v;
                    }
                  break;
  case 16:

				  b[3]= new byte[verbbyte.length];
                  for(int i=0;i<verbbyte.length;i++)
                     b[3][i]=verbbyte[i];
	              b[3][1]=1;
                  b[0]=verbbyte;             //verb
                  b[1] = Constants.t;
                  if(bm.isequal(Constants.prebyte,tenbyte))
                   {
                   if(bm.isequal(Constants.avai,subbyte) || bm.isequal(Constants.avaikal,subbyte))
                     b[2] = Constants.kindru;
                   else
                     b[2] = Constants.kiru;

                    }
                  if(bm.isequal(Constants.pasbyte,tenbyte))
                   {
                    b[0][1]=1;//bm.subarray(b[0],0,b[0].length-1);
                    b[2] = Constants.t;
                    }
                  if(bm.isequal(Constants.futbyte,tenbyte))
                    {
                    if(bm.isequal(subbyte,Constants.avai)|| bm.isequal(subbyte,Constants.avaikal) || bm.isequal(subbyte,Constants.athu))
                        b[2] = Constants.zero;
                     else
                        b[2] = Constants.p;
                    }
                  break;
  case 17:
                  b[3] = verbbyte;
	 	      	  b[0] = verbbyte;             //verb
                  b[1] = Constants.nth;
                  if(bm.isequal(Constants.prebyte,tenbyte))
                   {
                    b[0][1]=1;
                   if(bm.isequal(Constants.avai,subbyte) || bm.isequal(Constants.avaikal,subbyte))
                     b[2] = Constants.rukinru;
                   else
                     b[2] = Constants.rukiru;
                    }
                  if(bm.isequal(Constants.pasbyte,tenbyte))
                   {
                    b[0][1]=1;//bm.subarray(b[0],0,b[0].length-1);
                    b[2] = Constants.nth;
                    }
                  if(bm.isequal(Constants.futbyte,tenbyte))
                    {
                     b[0][1]=1;
                    if(bm.isequal(subbyte,Constants.avai)|| bm.isequal(subbyte,Constants.avaikal) || bm.isequal(subbyte,Constants.athu))
                        b[2] = Constants.ru;
                     else
                        b[2] = Constants.ruv;
                    }
                  break;
   case 18:
   						b[3]=verbbyte;
   						b[0]=verbbyte;             //verb
   						b[1] = Constants.thth;
   						if(bm.isequal(Constants.prebyte,tenbyte))
   						{
   							if(bm.isequal(Constants.avai,subbyte) || bm.isequal(Constants.avaikal,subbyte))
   								b[2] = Constants.kindru;
   							else
   								b[2] = Constants.kiru;
   						}
   						if(bm.isequal(Constants.pasbyte,tenbyte))
   						{
   						byte[] se ={7};
   						b[0]=bm.subarray(b[0],0,b[0].length-1);
						b[0]=bm.verbaddarray(b[0],se);
   						b[2] = Constants.thth;
						}
   						if(bm.isequal(Constants.futbyte,tenbyte))
   						{
   							if(bm.isequal(subbyte,Constants.avai)|| bm.isequal(subbyte,Constants.avaikal) || bm.isequal(subbyte,Constants.athu))
   								b[2] = Constants.k;
   							else
   								b[2] = Constants.v;
   						}
						break;


      }

          //System.out.println(cat+"");
          }
         catch(Exception e){
			 //e.printStackTrace();
			 System.out.println("From the verbcate exception");
			 }

         return b;

      }

/**This method generates gender endings.*/
public byte[] genderEndings(byte[] sub,byte[] tense,byte[] pasttensemarker)
  {
	byte[] b = new byte[10];

       String s="";
        try
        {
         String subject=tm.revert(sub);
	     s = file.getGenderEnding(subject,"genderendings.txt");
         b=tm.convert(s);
        if( bm.isequal(tense,Constants.pasbyte) && bm.isequal(Constants.in,pasttensemarker))
          if(bm.isequal(sub,Constants.avai)|| bm.isequal(sub,Constants.avaikal))
             b = Constants.a;
        if(bm.isequal(tense,Constants.futbyte))
         if(bm.isequal(sub,Constants.avai)|| bm.isequal(sub,Constants.avaikal) || bm.isequal(sub,Constants.athu))
          b = Constants.um;
	  }

      catch(Exception e){
		 // e.printStackTrace();
		 System.out.println("From the gender ending exception");
		  }
      return b ;
}
/** This method adds the auxillary */
public byte[][] addAuxi( byte[] verb, byte[] ptm, byte[][] auxi,byte[] tense,byte[] subject,int auxlen)throws IOException
  {
    int cat=0;
    int word=0;
    int inc=0;
    byte[][] b=new byte[16][];
    int cla=0;
    int nextcla=0;
    String ten=tm.revert(tense);

    int tense_variable = 0;

    if(ten.equals("நிகழ்காலம்"))
    	tense_variable = PRESENT;
    else if(ten.equals("இறந்தகாலம்"))
    	tense_variable = PAST;
    else if(ten.equals("எதிர்காலம்"))
    	tense_variable = FUTURE;

    String aux1="";
    String aux2="";
    String auxil[]=new String[4];
    for(int i=0;i<auxi.length;i++)
     auxil[i]=tm.revert(auxi[i]);
    byte temp[][] =new byte[16][];
    byte[][] resaux=new byte[16][];
    byte[] space ={0,32,0};
    byte[] subver=bm.addarray(space,verb);
    try
      {
       word=1;

       // for more than one auxilary
       for(int i=0;i<auxlen-1;i++)
        {

	     cla = file.getAuxCat(auxil[i],"auxiliary.txt");

		nextcla = file.getAuxCat(auxil[i+1],"auxiliary.txt");
          if(i==0)
            {
            if(cla==1)
              {
               if(bm.isequal(ptm,Constants.e))
                 resaux[0] = ptm;
               //else if(bm.isequal(ptm,thth))
                 //resaux[0]=kka;
               else
                resaux[0]=bm.addarray(ptm,Constants.u);
                }
            if(cla==2)
             {
               if(bm.isequal(ptm,Constants.thth))
			      resaux[0] = Constants.kka;
			   else if(bm.isequal(ptm,Constants.nth))
			      resaux[0] = Constants.kka;
			   else if(bm.isequal(ptm,Constants.rrrr))
			      resaux[0] = Constants.rrka;
			   else
			       {
			   		if(bm.isequal(ptm,Constants.nrr))
			   		  resaux[0] = Constants.lla;
			   		else
			   		   {
			   		     resaux[0] = Constants.a;
			   		   }
					}
		     }
            if(cla==3)
             {
                if(bm.isequal(ptm,Constants.e))
                  resaux[0] = ptm;
                else
                  resaux[0] = bm.addarray(ptm,Constants.u);

                  if(bm.isequal(ptm,Constants.thth))
				     resaux[1] = Constants.kka;
				  else if(bm.isequal(ptm,Constants.nth))
				     resaux[1] = Constants.kka;
				  else if(bm.isequal(ptm,Constants.rrrr))
				     resaux[1] = Constants.rrka;
				  else
				     {
				    	if(bm.isequal(ptm,Constants.nrr))
				  	 	   resaux[1] = Constants.lla;
				  	 	else
				  	 	   {
				     	     resaux[1] = Constants.a;
				  	       }
				 	 }
               word*=2;
             }
            }
           if(nextcla==1 || nextcla==4)
            {

              aux1 = file.getAuxString(auxil[i],"auxiliary.txt",VERBALPARTICIPLE);

              for(int x=0;x<word;x++)
              resaux[x]=bm.addarray1(resaux[x],tm.convert(aux1));
            }
         if(nextcla==2 || nextcla==5 || nextcla==6)
            {

             aux1 = file.getAuxString(auxil[i],"auxiliary.txt",INFINITIVE);

            for(int x=0;x<word;x++)
              resaux[x]=bm.addarray1(resaux[x],tm.convert(aux1));
            }
         if(nextcla==3)
            {
             word*=2;
              aux1 = file.getAuxString(auxil[i],"auxiliary.txt",VERBALPARTICIPLE);


	           aux2 = file.getAuxString(auxil[i],"auxiliary.txt",INFINITIVE);

            for(int x=0;x<word/2;x++)
             {
              resaux[x]=bm.addarray1(resaux[x],tm.convert(aux1));
              resaux[word/2+x]=bm.addarray1(resaux[x],tm.convert(aux2));
             }
            }

           }


           // for the last auxilary
        cla = file.getAuxCat(auxil[auxlen-1],"auxiliary.txt");
      if(cla==1 || cla==2 || cla==3)
       {
         if(cla==1)
          {
            if(auxlen==1)
             {
             if(bm.isequal(ptm,Constants.e))
               resaux[0]=ptm;
             else if(bm.isequal(ptm,Constants.nn))
               resaux[0] = Constants.e;
             else if(bm.isequal(ptm,Constants.n))
               resaux[0] = Constants.yi;
             else
              resaux[0]=bm.addarray1(ptm,Constants.u);
               }
           }//

          else if(cla==2)
           {
          if(auxlen==1)
             {
			  if(bm.isequal(ptm,Constants.thth))
			     resaux[0] = Constants.kka;
			  else if(bm.isequal(ptm,Constants.nth))
			     resaux[0] = Constants.kka;
			   else if(bm.isequal(ptm,Constants.rrrr))
			     resaux[0] = Constants.rrka;
			  else
			     {
					 if(bm.isequal(ptm,Constants.nrr))
					   resaux[0] = Constants.lla;
					 else
					 resaux[0] = Constants.a;
				 }
		    }
           }

    else if(cla==3)
        {
           if(auxlen==1)
             {
               if(bm.isequal(ptm,Constants.thth))
                 resaux[1] = Constants.kka;
               else if(bm.isequal(ptm,Constants.nth))
			     resaux[1] = Constants.kka;
			   else if(bm.isequal(ptm,Constants.rrrr))
			     resaux[1] = Constants.rrka;
			   else
                 {
				 	if(bm.isequal(ptm,Constants.nrr))
				 	   resaux[1] = Constants.lla;
				 	else
				 	   {
					     resaux[1] = Constants.a;
				       }
				 }
                if(bm.isequal(ptm,Constants.e))
                  resaux[0] = ptm;
			  	else if(bm.isequal(ptm,Constants.nn))
                  resaux[0]= Constants.e;
                else
                  resaux[0]=bm.addarray1(ptm,Constants.u);
               word*=2;
              }
         }
         for(int x=0;x<word;x++)
           b[x]=bm.addarray(subver,resaux[x]);
           if((bm.isequal(subject,Constants.avai)|| bm.isequal(subject,Constants.avaikal))&& bm.isequal(tense,Constants.prebyte))
            {
              aux1 = file.getAuxString(auxil[auxlen-1],"auxiliary.txt",PRESENT_NEUTER);

            }
           else if((bm.isequal(subject,Constants.athu)||bm.isequal(subject,Constants.avai)|| bm.isequal(subject,Constants.avaikal))&& bm.isequal(tense,Constants.futbyte))
            {
              aux1 = file.getAuxString(auxil[auxlen-1],"auxiliary.txt",FUTURE_NEUTER);

          }
          else
           {
              aux1 = file.getAuxString(auxil[auxlen-1],"auxiliary.txt",tense_variable);

           }
         for(int x=0;x<word;x++)
           b[x]=bm.addarray1(bm.addarray(b[x],tm.convert(aux1)),genderEndings(subject,tense,ptm));
         }
    else if(cla==4)
        {
         if(auxlen==1)
           if(bm.isequal(ptm,Constants.e))
             resaux[0]=ptm;
           if(bm.isequal(ptm,Constants.nn))
             resaux[0] = Constants.e;
           else
             resaux[0] = bm.addarray1(ptm,Constants.u);
        for(int x=0;x<word;x++)
         b[x]=bm.addarray(subver,resaux[x]);
        aux1=auxil[auxlen-1];
        for(int x=0;x<word;x++)
         b[x]=bm.addarray1(b[x],tm.convert(aux1));
        return b;
        }
      else if(cla==5)
       {
       if(auxlen==1)
          {
		    if(bm.isequal(ptm,Constants.thth))
		      resaux[0] = Constants.kka;
		    else if(bm.isequal(ptm,Constants.nth))
		      resaux[0] = Constants.kka;
		    else if(bm.isequal(ptm,Constants.rrrr))
		      resaux[0] = Constants.rrka;
		    else
				{
				 if(bm.isequal(ptm,Constants.nrr))
				   resaux[0] = Constants.lla;
				 else
				   resaux[0] = Constants.a;
				 }
	  	  }
       for(int x=0;x<word;x++)
       b[x]=bm.addarray(subver,resaux[x]);
       aux1=auxil[auxlen-1];
       for(int x=0;x<word;x++)
        b[x]=bm.addarray(b[x],tm.convert(aux1));
       return b;
        }
       else if(cla==6)
        {
          if(auxlen==1)
           {
            if(bm.isequal(ptm,Constants.thth))
               resaux[0] = Constants.kka;
            else if(bm.isequal(ptm,Constants.nth))
               resaux[0] = Constants.kka;
            else if(bm.isequal(ptm,Constants.rrrr))
               resaux[0] = Constants.rrka;
            else
              {
				  if(bm.isequal(ptm,Constants.nrr))
				     resaux[0] = Constants.lla;
				   else
				     resaux[0] = Constants.a;
		       }
		    }
        for(int x=0;x<word;x++)
          b[x]=bm.addarray(subver,resaux[x]);
        aux1=auxil[auxlen-1];
        for(int x=0;x<word;x++)
          b[x]=bm.addarray(bm.addarray1(b[x],tm.convert(aux1)),genderEndings(subject,tense,ptm));
       return b;
        }
if((bm.isequal(subject,Constants.avai)|| bm.isequal(subject,Constants.avaikal))&& bm.isequal(tense,Constants.pasbyte)&& bm.isequal(ptm,Constants.in))
   for(int x=0;x<word;x++)
     b[x]=bm.addarray(b[x],Constants.na);

     }catch(Exception e){
		// e.printStackTrace();
		System.out.println("In the last auxilary exception");
		 }
    return b;
 }


public byte[] addAdverb(String given_adverb)
 {
  byte[] adverb_result=null;
  byte[] given_adverb_byte=tm.convert(given_adverb);
  byte[] L={29};
  byte[] n={31};
  //bm.printarray(given_adverb_byte);
  String []adverbtype1 = {"வேகம்","விரைவு","அழகு"};
  String []adverbtype2 = {"அடிக்கடி","இனிமேல்","இனியும்","மறுபடியும்","மீண்டும்","மெல்ல"};
  String []adverbtype3 = {"உள்","எதிர்","பின்","வௌத","போல","போன்று","கொண்டு","நோக்கி","பற்றி","குறித்து","சுற்றி","விட்டு","தவிர","முன்னிட்டு","வேண்டி","ஒட்டி","பொறுத்து","பொறுத்தவரை","என்று","முன்","சுற்றிலும்","ஆக","மேல்","கீழ்","எதிரில்","பக்கத்தில்","அருகில்","மாறாக","நேராக","வாயிலாக","மூலமாக","வழியாக","பேரில்","பொருட்டு","கூட","வசம்","இடம்","வலம்"};
  String []adverbtype4 = {"கூச்சல்","பச்சை","மஞ்சள்"};
//"விட","விடவும்","போல்",,"பின்","உள்","இடையே","நடுவே","மத்தியில்","வௌதயே",,"பதில்","உரிய","உள்ள","தகுந்த","உடைய","தோறும்","ஆர"
  for(int i=0;i<adverbtype1.length;i++)
    if(given_adverb.equals(adverbtype1[i]))
          adverb_result=bm.addarray(given_adverb_byte,Constants.aaka);
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
