
package org.apache.nutch.analysis.unl.ta.clia.unl.Source.Word_Gen.Generator;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.Properties;
import java.io.FileInputStream;
public class word_verb {//start pgm
	TabMethods tm =new TabMethods();
	ByteMethods bm = new ByteMethods();
	AdjectiveMethods1 am=new AdjectiveMethods1();
	verbinfinitive vf=new verbinfinitive();
	verbalnoun vn=new verbalnoun();
	StringBuffer sb=new StringBuffer();
	clitics cl= new clitics();
	Vector generatedwordverbvec =new Vector(40,1);
					String[] gensentences1=null;
					String[] gensentences=null;
					String ruleinput="";String fileinput="";String out="";
					String s = "";
					String[] rule={"1","2","3","4","6","7","8"};
					String verbinput="";
				    String giv_sub = "எழுவாய்";
					String giv_adj1 = "பெயரடை";
					String giv_noun1 = "பெயர்ச்சொல்";
					String giv_case1 = "வேற்றுமைகள்";
					String giv_clitic1="மிதவை ஒட்டு";
					String giv_post1 = "பின்நிலை";

					String giv_adj2 = "பெயரடை";
					String giv_noun2 = "பெயர்ச்சொல்";
					String giv_case2 = "வேற்றுமைகள்";
					String giv_clitic2="மிதவை ஒட்டு";
					String giv_post2 = "பின்நிலை";
					String giv_adv = "வினையடை";

					String giv_verb = "வினைச்சொல்";
					String giv_aux1 = "துணை";
					String giv_aux2 = "துணை";
					String giv_aux3 = "துணை";
					String giv_aux4 = "துணை";
					String giv_tense = "காலங்கள்";

					boolean isSingular1 = true;
					boolean isSingular2 = true;
					BufferedReader f1;
					String temp="";int ctr=0;

public StringBuffer verbgeneration(String filein,String ruleselected)throws Exception
	{//start function
	byte[] a={1};
	String[] cases ={"இடமிருந்து","இலிருந்து","ஐ","உக்காக","உக்கு","அது","உடைய","ஆல்","இடம்","இல்","கண்","ஓடு","உடன்"};
	 //String[] str1 ={"Verb Rules","V+TM+PNG","V+TM+Aux1+PNG","V+TM+Aux1+Aux2+PNG","V+TM+Aux1+Aux2+Aux3+PNG"};
	 String[] str1 ={"வினைச்சொல் விதிகள்","வினை+காலம்+பால்/இட/எண்","வினை+காலம்+துனை1+பால்/இட/எண்",
"வினை+காலம்+துனை1+துனை2+பால்/இட/எண்","வினை+காலம்+துனை1+துனை2+துனை3+பால்/இட/எண்",
"வினை+காலம்+து.வி1+து.வி2+து.வி3+து.வி4+பால்/இட/எண்",
"வினை+உம்+பெயரெச்ச பின்னொட்டு","வினை+இறந்தகாலம்+நிபந்தனை விகுதி","வினை+எச்சம்+ஈற்றசை"
,"வினை+எச்சம்+நிலை+(பால்/இட/எண்)/(தொ.பெ.விகுதி)/(+வே.உ)","ஏ.மு.ஒ.விகுதி(ISM) / ஏ.மு.ப.விகுத¤(IPM)",
"வினை+எச்சம்+செ.பாட்டு+காலம்+பா/இ/எண்","வினை+எச்சம்+செ.பாட்டு+காலம்+தொ.பெ.விகுதி / (+வே.உ)",
"வினை+காலம்+பெ.எச்சம்+தொ.பெ","வினை+காலம்+பெ.எச்சம்+பெயரெச்ச பின்னொட்டு",
"வினை+எச்சம்+எதிர்மறை"};
	String[] subject = {"நான்","நாம்","நீ","அவன்","அவள்","அது","அவைகள்","அவர்கள்","அவர்","நீவீர்"};
	String[] subject1 = {"நான்"};
	String[] tenses = {"நிகழ்காலம்","இறந்தகாலம்","எதிர்காலம்"};
	String[] tenses2 = {"நிகழ்காலம்","இறந்தகாலம்"};
	String[] tenses1 = {"நிகழ்காலம்"};
	//String[] auxstr1 ={"கொள்","இரு","விடு","பார்","தொலை","அழு","கொடு","கிட","கிழி","தள்ளு","தீர்","போடு","காட்டு","மாட்டு","படு","போ","வா","செய்","
//வை","முடி","ஆயிற்று"};
	String[] auxstr = {"கொள்","இரு","விடு","பார்","தொலை","அழு","கொடு","கிட","கிழி","தள்ளு","தீர்","போடு","காட்டு","மாட்டு","படு","போ","வா","செய்","வை","முடி","ஆயிற்று","வேண்டும்","வேண்டாம்","கூடும்","கூடாது","இல்லை"};
	String[] advparticle={"ஆறு","படி","படியால்","படியே","பொழுது","போதிலும்","போது","வண்ணம்","வண்ணமாக"};
	String[] adv_par_full={"அப்ப","அப்புறம்","ஆறு","உடன்","உடனே","உடன்தான்","படி","படியால்","படியே","பிறகு","பிறகும்","பின்","பின்னர்","பொழுது","போதிலும்","போது","போதெல்லாம்","வண்ணம்","வண்ணமாக","அன்று","வரைப்போல்","மாதிர¤"};
	String[] suffixes={"லாம்","ட்டும்","விட்டால்","ஆவிட்டால்","ஆமல்","ஆமலும்","ஆதே","ஆதீர்கள்","ஆது","ஆவிடில்","மாட்டு"};
	String[] gender={"ஏன்","ஓம்","ஆய்","ஈர்","ஈர்கள்","ஆன்","ஆள்","அது","அன","ஆர்கள்","ஆர்"};
	String[] nilai={"வேண்டி","வேண்டிய","வல்ல","கூடிய","கூடாது"};
	String[] ethirmarai={"ஆது","ஆவிடில்","ஆமம்"};
	String[] pronominal={"அவன்","அவள்","அவர்","அவர்கள்","அது","அவை","அவைகள்"};
	String[] pronominal1={"அன்","அள்","அர்","அர்கள்","அது","அன","ஐ"};
//	String[] ismipm={"டா","டி","ம்மா","ப்பா","ய்யா","ங்கள்","உங்கள¢"};
	//String[] ismipm={"டா","டி","ம்மா","ப்பா","ய்யா","ங்கள¢"};
	String[] ismipm={"டா","டி","ம்மா","ப்பா","ய்யா","உடா","உடி","உம்மா","உப்பா","உய்யா",
	"ங்கள்","உங்கள்"};

	//System.out.println("Input word "+filein);
	//System.out.println("Ruleselected " + ruleselected);
	verbinput=filein;

	for(int c1=0;c1<str1.length;c1++)
		{

			switch(c1)
			{
				case 1:
						if(ruleselected.equals("வினை+காலம்+பால்/இட/எண்"))
						{
						s="";
						giv_noun1 = "பெயர்ச்சொல்";
						giv_case1 = "வேற்றுமைகள்";
						giv_post1 = "பின்நிலை";
						giv_aux1  = "துணை";
						giv_aux2  = "துணை";
						giv_aux3  = "துணை";
						giv_aux4  = "துணை";
						//System.out.println("entered the verbgeneratebut");
					for(int k=0;k<subject.length;k++)
					{
						for(int l=0;l<tenses.length;l++)
						{
							giv_sub=subject[k];
							giv_verb=verbinput;
							giv_tense=tenses[l];
							gensentences = CorporateDemo1.generateSentences(giv_sub,giv_adj1,giv_noun1,giv_case1,giv_clitic1,isSingular1,giv_post1,giv_adj2,giv_noun2,giv_case2,giv_clitic2,isSingular2,giv_post2,giv_adv,giv_verb,giv_aux1,giv_aux2,giv_aux3,giv_aux4,giv_tense);
							for(int j=0;j<1 && gensentences[j]!=null;j++)
							{
								try{
										sb.append(gensentences[j].trim()+"\n");
								   }
								catch(Exception ex)
								{

									//System.out.println("Word Verb" + ex);
								}
							}
						}
					}
				}
			break;
			case 2:
					if(ruleselected.equals("வினை+காலம்+துணை1+பால்/இட/எண்"))
					{
					s="";
					giv_noun1 = "பெயர்ச்சொல்";
					giv_case1 = "வேற்றுமைகள்";
					giv_post1 = "பின்நிலை";
					giv_aux1  = "துணை";
					giv_aux2  = "துணை";
					giv_aux3  = "துணை";
					giv_aux4  = "துணை";

				for(int k=0;k<subject1.length;k++)
				{
					for(int l=0;l<tenses1.length;l++)
					{
						for(int a1=0;a1<auxstr.length;a1++)
						{
						giv_sub=subject1[k];
						giv_verb=verbinput;
						giv_tense=tenses1[l];
						giv_aux1=auxstr[a1];
						gensentences = CorporateDemo1.generateSentences(giv_sub,giv_adj1,giv_noun1,giv_case1,giv_clitic1,isSingular1,giv_post1,giv_adj2,giv_noun2,giv_case2,giv_clitic2,isSingular2,giv_post2,giv_adv,giv_verb,giv_aux1,giv_aux2,giv_aux3,giv_aux4,giv_tense);
						for(int j=0;j<1 && gensentences[j]!=null;j++)
						{
							try{
								sb.append(gensentences[j].trim()+"\n");
							   }
							catch(Exception ex)
							{
								//System.out.println("Word VErb" + ex);
							}
						}
					  }
					}
				}
			}
			break;
			case 3:
				if(ruleselected.equals("வினை+காலம்+துணை1+துணை2+பால்/இட/எண¢"))
				{
				s="";
				giv_noun1 = "பெயர்ச்சொல்";
				giv_case1 = "வேற்றுமைகள்";
				giv_post1 = "பின்நிலை";
				giv_aux1  = "துணை";
				giv_aux2  = "துணை";
				giv_aux3  = "துணை";
				giv_aux4  = "துணை";
				/*System.out.println("entered the verbgenerate but");
				System.out.println("subject: "+subject1.length);
				System.out.println("auxstr1: "+auxstr.length);
				System.out.println("auxstr: "+auxstr.length);*/



			for(int k=0;k<subject1.length;k++)
			{
				for(int l=0;l<tenses1.length;l++)
				{
					for(int a1=0;a1<auxstr.length;a1++)
					{
						for(int a2=0;a2<auxstr.length;a2++)
						 {

					giv_sub=subject1[k];
					giv_verb=verbinput;
					giv_tense=tenses1[l];
					giv_aux1=auxstr[a1];
					giv_aux2=auxstr[a2];

					gensentences = CorporateDemo1.generateSentences(giv_sub,giv_adj1,giv_noun1,giv_case1,giv_clitic1,isSingular1,giv_post1,giv_adj2,giv_noun2,giv_case2,giv_clitic2,isSingular2,giv_post2,giv_adv,giv_verb,giv_aux1,giv_aux2,giv_aux3,giv_aux4,giv_tense);
					int j;
					for(j=0;j<1 && gensentences[j]!=null;j++)
					{
						try{

								sb.append(gensentences[j].trim()+"\n");
						   }
						catch(Exception ex)
						{
							//System.out.println("Word Verb" + ex);
						}
					 }
		           }
				}
			}
		}
	}
 break;
  case 4:
  			if(ruleselected.equals("வினை+காலம்+துணை1+துணை2+துணை3+பால்/இட/எண்"))
  				{
  				s="";
  				giv_noun1 = "பெயர்ச்சொல்";
  				giv_case1 = "வேற்றுமைகள்";
  				giv_post1 = "பின்நிலை";
  				giv_aux1  = "துணை";
				giv_aux2  = "துணை";
				giv_aux3  = "துணை";
				giv_aux4  = "துணை";
  				/*System.out.println("entered the verbgenerate but");
  				System.out.println("subject1: "+subject1.length);
  				System.out.println("auxstr1: "+auxstr.length);
  				System.out.println("auxstr: "+auxstr.length);*/
  				for(int k=0;k<subject1.length;k++)
				{
				for(int l=0;l<tenses1.length;l++)
					{
  					for(int a1=0;a1<auxstr.length;a1++)
  						{
  						for(int a2=0;a2<auxstr.length;a2++)
  						 {
							 for(int a3=0;a3<auxstr.length;a3++)
							 	{
  					giv_sub=subject1[k];
  					giv_verb=verbinput;
  					giv_tense=tenses1[l];
  					giv_aux1=auxstr[a1];
  					giv_aux2=auxstr[a2];
  					giv_aux3=auxstr[a3];
  					gensentences = CorporateDemo1.generateSentences(giv_sub,giv_adj1,giv_noun1,giv_case1,giv_clitic1,isSingular1,giv_post1,giv_adj2,giv_noun2,giv_case2,giv_clitic2,isSingular2,giv_post2,giv_adv,giv_verb,giv_aux1,giv_aux2,giv_aux3,giv_aux4,giv_tense);
  					int j;
  					for(j=0;j<1 && gensentences[j]!=null;j++)
  					{
  						try{
  							sb.append(gensentences[j].trim()+"\n");
  						   }
  						catch(Exception ex)
  						{
  							//System.out.println("Word Verb" + ex);
  						}
  					 }
  		           }
			   }
		   }
		}
  	}
  }
    break;
case 5:
  	if(ruleselected.equals("வினை+காலம்+து.வி1+து.வி2+து.வி3+து.வி4+பால்/இட/எண¢"))
  				{
  				s="";
  				giv_noun1 = "பெயர்ச்சொல்";
  				giv_case1 = "வேற்றுமைகள்";
  				giv_post1 = "பின்நிலை";
  				giv_aux1  = "துணை";
				giv_aux2  = "துணை";
				giv_aux3  = "துணை";
				giv_aux4  = "துணை";
  				/*System.out.println("entered the verbgenerate but");
  				System.out.println("subject: "+subject1.length);
  				System.out.println("auxstr1: "+auxstr.length);
  				System.out.println("auxstr: "+auxstr.length);*/
  				for(int k=0;k<subject1.length;k++)
				{
				for(int l=0;l<tenses1.length;l++)
					{
  					for(int a1=0;a1<auxstr.length;a1++)
  						{
  						for(int a2=0;a2<auxstr.length;a2++)
  						 {
							 for(int a3=0;a3<auxstr.length;a3++)
							 	{
									for(int a4=0;a4<auxstr.length;a4++)
							 		{
  					giv_sub=subject1[k];
  					giv_verb=verbinput;
  					giv_tense=tenses1[l];
  					giv_aux1=auxstr[a1];
  					giv_aux2=auxstr[a2];
  					giv_aux3=auxstr[a3];
  					giv_aux4=auxstr[a4];
  					gensentences = CorporateDemo1.generateSentences(giv_sub,giv_adj1,giv_noun1,giv_case1,giv_clitic1,isSingular1,giv_post1,giv_adj2,giv_noun2,giv_case2,giv_clitic2,isSingular2,giv_post2,giv_adv,giv_verb,giv_aux1,giv_aux2,giv_aux3,giv_aux4,giv_tense);
  					int j;
  					for(j=0;j<1 && gensentences[j]!=null;j++)
  					{
  						try{
  							sb.append(gensentences[j].trim()+"\n");
  						   }
  						catch(Exception ex)
  						{
  							//System.out.println("Word Verb" + ex);
  						}
  					 }
  		           }
			   }
		   }
		}
	}
  }
}
 break;
case 6:
	if(ruleselected.equals("வினை+உம்+பெயரெச்ச பின்னொட்டு"))
  		{
			giv_sub="அது";
			giv_tense="எதிர்காலம்";
			giv_verb=verbinput;
			giv_noun1 = "பெயர்ச்சொல்";
			giv_case1 = "வேற்றுமைகள்";
			giv_post1 = "பின்நிலை";
			giv_aux1  = "துணை";
			giv_aux2  = "துணை";
			giv_aux3  = "துணை";
			giv_aux4  = "துணை";String umout="";
			gensentences = CorporateDemo1.generateSentences(giv_sub,giv_adj1,giv_noun1,giv_case1,giv_clitic1,isSingular1,giv_post1,giv_adj2,giv_noun2,giv_case2,giv_clitic2,isSingular2,giv_post2,giv_adv,giv_verb,giv_aux1,giv_aux2,giv_aux3,giv_aux4,giv_tense);
			for(int j=0;j<1 && gensentences[j]!=null;j++)
			{
				try{
					umout=gensentences[j].trim();

				   }
				catch(Exception ex)
				{
				//	System.out.println("Word Verb" + ex);
				}
  			 }
			for(int ad=0;ad<advparticle.length;ad++)
			{
				String out=tm.revert(bm.addarray(tm.convert(umout),tm.convert(advparticle[ad])));
				sb.append(out.trim()+"\n");
			}

		}
break;
case 7:
	if(ruleselected.equals("வினை+இறந்தகாலம்+நிபந்தனை விகுதி"))
  		{
			giv_sub="நிபந்தனை";
			giv_tense="இறந்தகாலம்";
			giv_verb=verbinput;
			giv_noun1 = "பெயர்ச்சொல்";
			giv_case1 = "வேற்றுமைகள்";
			giv_post1 = "பின்நிலை";
			giv_aux1  = "துணை";
			giv_aux2  = "துணை";
			giv_aux3  = "துணை";
			giv_aux4  = "துணை";String umout="";
			gensentences = CorporateDemo1.generateSentences(giv_sub,giv_adj1,giv_noun1,giv_case1,giv_clitic1,isSingular1,giv_post1,giv_adj2,giv_noun2,giv_case2,giv_clitic2,isSingular2,giv_post2,giv_adv,giv_verb,giv_aux1,giv_aux2,giv_aux3,giv_aux4,giv_tense);
			for(int j=0;j<1 && gensentences[j]!=null;j++)
			{
				try{
					umout=gensentences[j].trim();
					sb.append(umout.trim()+"\n");
				   }
				catch(Exception ex)
				{
					//System.out.println("Word Verb" + ex);
				}
  			 }

		}
break;
case 8:
	if(ruleselected.equals("வினை+எச்சம்+ஈற்றசை"))
  		{
			giv_sub="எழுவாய்";
			giv_tense = "காலங்கள்";
			giv_verb=verbinput;
			giv_noun1 = "பெயர்ச்சொல்";
			giv_case1 = "வேற்றுமைகள்";
			giv_post1 = "பின்நிலை";
			giv_aux1  = "துணை";
			giv_aux2  = "துணை";
			giv_aux3  = "துணை";
			giv_aux4  = "துணை";String umout="";
			umout=vf.verbgetinfi(giv_verb);
			System.out.println("umout  "+umout);
			for(int i=0;i<suffixes.length;i++)
			{
				if(suffixes[i]=="மாட்ட்")
				{
					for(int k=0;k<gender.length;k++)
					{
						String suff=tm.revert(bm.addarray(bm.addarray(tm.convert(umout),tm.convert("மாட்ட¢")),tm.convert(gender[k])));
						sb.append(suff.trim()+"\n");
					}
				}
			else{
			String suff=tm.revert(bm.verbaddarray(tm.convert(umout),tm.convert(suffixes[i])));
			sb.append(suff.trim()+"\n");
			}
				}
	 	 }
	break;

case 9:
	if(ruleselected.equals("வினை+எச்சம்+நிலை+(பால்/இட/எண¢)/(தொ.பெ.விகுதி)/(+வே.உ)"))
  		{
			giv_sub="எழுவாய்";
			giv_tense = "காலங்கள்";
			giv_verb=verbinput;
			giv_noun1 = "பெயர்ச்சொல்";
			giv_case1 = "வேற்றுமைகள்";
			giv_post1 = "பின்நிலை";
			giv_aux1  = "துணை";
			giv_aux2  = "துணை";
			giv_aux3  = "துணை";
			giv_aux4  = "துணை";String umout="";
			umout=vf.verbgetinfi(giv_verb);
			//System.out.println("umout  "+umout);
			for(int i=0;i<nilai.length;i++)
			{
				if((nilai[i]=="வல்ல")||(nilai[i]=="வேண்டி")||(nilai[i]=="கூடிய")||(nilai[i]=="கூடாது"))
				{
					for(int p=0;p<pronominal.length;p++)
						{
						for(int c=0;c<cases.length;c++)
							{
							String suff=tm.revert(bm.addarray(bm.addarray(tm.convert(umout),tm.convert(nilai[i])),tm.convert(pronominal[p])));
							String suff2=tm.revert(bm.addarray(tm.convert(suff),tm.convert(cases[c])));
							sb.append(suff2.trim()+"\n");
							}
						//sb.append(suff+"\n");
						}
					if(nilai[i]=="வல்ல")
					{
					for(int k=0;k<gender.length;k++)
						{
						String suff=tm.revert(bm.addarray(tm.convert(umout),tm.convert(nilai[i])));
						String suff1=tm.revert(bm.verbaddarray(tm.convert(suff),tm.convert(gender[k])));
						sb.append(suff1.trim()+"\n");
						}
					}
				}

			else{
			String suff=tm.revert(bm.verbaddarray(tm.convert(umout),tm.convert(nilai[i])));
			sb.append(suff.trim()+"\n");
			}
				}
	 	 }
	break;
	case 10:
		if(ruleselected.equals("ஏ.மு.ஒ.விகுதி(ISM) / ஏ.மு.ப.விகுத¤(IPM)"))
	  		{
				giv_sub="எழுவாய்";
				giv_tense = "காலங்கள்";
				giv_verb=verbinput;
				giv_noun1 = "பெயர்ச்சொல்";
				giv_case1 = "வேற்றுமைகள்";
				giv_post1 = "பின்நிலை";
				giv_aux1  = "துணை";
				giv_aux2  = "துணை";
				giv_aux3  = "துணை";
				giv_aux4  = "துணை";
				for(int i=0;i<ismipm.length;i++)
				{
							String ipm=tm.revert(bm.addarray(tm.convert(giv_verb),tm.convert(ismipm[i])));
							sb.append(ipm.trim()+"\n");
				}

		 	 }
		break;
case 11:
	if(ruleselected.equals("வினை+எச்சம்+செ.பாட்டு+காலம்+பா/இ/எண்"))
  		{
			giv_sub="எழுவாய்";
			giv_tense = "காலங்கள்";
			giv_verb=verbinput;
			giv_noun1 = "பெயர்ச்சொல்";
			giv_case1 = "வேற்றுமைகள்";
			giv_post1 = "பின்நிலை";
			giv_aux1  = "துணை";
			giv_aux2  = "துணை";
			giv_aux3  = "துணை";
			giv_aux4  = "துணை";String umout="";
			umout=vf.verbgetinfi(giv_verb);
			//System.out.println("umout  "+umout);

				for(int k=0;k<subject.length;k++)
					{
						for(int l=0;l<tenses.length;l++)
							{

										giv_sub=subject[k];
										giv_verb="ð´";
										giv_tense=tenses[l];
										gensentences = CorporateDemo1.generateSentences(giv_sub,giv_adj1,giv_noun1,giv_case1,giv_clitic1,isSingular1,giv_post1,giv_adj2,giv_noun2,giv_case2,giv_clitic2,isSingular2,giv_post2,giv_adv,giv_verb,giv_aux1,giv_aux2,giv_aux3,giv_aux4,giv_tense);
										for(int j=0;j<1 && gensentences[j]!=null;j++)
										{
											try{
												String padu=gensentences[j].trim();
												String out=tm.revert(bm.addarray(tm.convert(umout),tm.convert(padu)));
												sb.append(out.trim()+"\n");
											   }
											catch(Exception ex)
											{

											//	System.out.println("Word Verb" + ex);
											}
										}
									}
					}

	 	 }
	break;
	case 12:
		if(ruleselected.equals("வினை+எச்சம்+செ.பாட்டு+காலம்+தொ.பெ.விகுதி / (+வே.உ)"))
	  		{
				giv_sub="எழுவாய்";
				giv_tense = "காலங்கள்";
				giv_verb=verbinput;
				giv_noun1 = "பெயர்ச்சொல்";
				giv_case1 = "வேற்றுமைகள்";
				giv_post1 = "பின்நிலை";
				giv_aux1  = "துணை";
				giv_aux2  = "துணை";
				giv_aux3  = "துணை";
				giv_aux4  = "துணை";String umout="";
				umout=vf.verbgetinfi(giv_verb);
			//	System.out.println("umout  "+umout);
						for(int k=0;k<pronominal.length;k++)
						{
							for(int l=0;l<tenses.length;l++)
								{
									giv_sub="ஆன்";
									giv_verb="ð´";
									giv_tense=tenses[l];
									gensentences = CorporateDemo1.generateSentences(giv_sub,giv_adj1,giv_noun1,giv_case1,giv_clitic1,isSingular1,giv_post1,giv_adj2,giv_noun2,giv_case2,giv_clitic2,isSingular2,giv_post2,giv_adv,giv_verb,giv_aux1,giv_aux2,giv_aux3,giv_aux4,giv_tense);
									for(int j=0;j<1 && gensentences[j]!=null;j++)
										{
											try{
												String padu=gensentences[j].trim();
												String out1=tm.revert(bm.addarray(tm.convert(umout),tm.convert(padu)));
												String out=tm.revert(bm.verbaddarray(tm.convert(out1),tm.convert(pronominal[k])));
												sb.append(out.trim()+"\n");
											   }
											catch(Exception ex)
											{
												//System.out.println("Word Verb" + ex);
											}
										}
									}
							 	 }
						 }
		break;
	case 13:
		if(ruleselected.equals("வினை+காலம்+பெ.எச்சம்+தொ.பெ"))
		  		{
					giv_sub="எழுவாய்";
					giv_tense = "காலங்கள்";
					giv_verb=verbinput;
					giv_noun1 = "பெயர்ச்சொல்";
					giv_case1 = "வேற்றுமைகள்";
					giv_post1 = "பின்நிலை";
					giv_aux1  = "துணை";
					giv_aux2  = "துணை";
					giv_aux3  = "துணை";
					giv_aux4  = "துணை";String umout="",umout1="";
					umout="";
					String aatha="ஆத";
					umout=vf.verbgetinfi(giv_verb);
					umout1=tm.revert(bm.addarray(tm.convert(umout),tm.convert(aatha)));
					for(int k=0;k<pronominal1.length;k++)
					{
					sb.append(tm.revert(bm.addarray(tm.convert(umout1),tm.convert(pronominal1[k])))+"\n");
					}
				for(int k=0;k< pronominal1.length;k++)
				{
					for(int l=0;l<tenses.length;l++)
						{
								giv_sub="ஆன்";
								giv_verb=verbinput;
								giv_tense=tenses[l];
							String an="அன்";
							if(tenses[l]=="எதிர்காலம்")
							{
								 out=vn.getverbalnoun(giv_verb);
							}
							else{
								gensentences = CorporateDemo1.generateSentences(giv_sub,giv_adj1,giv_noun1,giv_case1,giv_clitic1,isSingular1,giv_post1,giv_adj2,giv_noun2,giv_case2,giv_clitic2,isSingular2,giv_post2,giv_adv,giv_verb,giv_aux1,giv_aux2,giv_aux3,giv_aux4,giv_tense);
								for(int j=0;j<1 && gensentences[j]!=null;j++)
								{
									try{
										String padu=gensentences[j].trim();
										out=tm.revert(bm.addarray(tm.convert(padu),a));
										String out1=tm.revert(bm.addarray(tm.convert(out),tm.convert(pronominal1[k])));
										sb.append(out1.trim()+"\n");

									   }
									catch(Exception ex)
									{

										//System.out.println("Word Verb" + ex);
									}

								}
							}
						}
					}
				}

	break;

case 14:
		if(ruleselected.equals("வினை+காலம்+பெ.எச்சம்+பெயரெச்ச பின்னொட்டு"))
		  		{
					giv_sub="எழுவாய்";
					giv_tense = "காலங்கள்";
					giv_verb=verbinput;
					giv_noun1 = "பெயர்ச்சொல்";
					giv_case1 = "வேற்றுமைகள்";
					giv_post1 = "பின்நிலை";
					giv_aux1  = "துணை";
					giv_aux2  = "துணை";
					giv_aux3  = "துணை";
					giv_aux4  = "துணை";String umout="",umout1="";
					umout="";
					String aatha="ஆத";
					umout=vf.verbgetinfi(giv_verb);
					umout1=tm.revert(bm.addarray(tm.convert(umout),tm.convert(aatha)));
					for(int a1=0;a1<adv_par_full.length;a1++){
					sb.append(tm.revert(bm.addarray(tm.convert(umout1),tm.convert(adv_par_full[a1])))+"\n");
					for(int l=0;l<tenses2.length;l++)
						{
								giv_sub="ஆன்";
								giv_verb=verbinput;
								giv_tense=tenses[l];
								String an="அன்";
								gensentences = CorporateDemo1.generateSentences(giv_sub,giv_adj1,giv_noun1,giv_case1,giv_clitic1,isSingular1,giv_post1,giv_adj2,giv_noun2,giv_case2,giv_clitic2,isSingular2,giv_post2,giv_adv,giv_verb,giv_aux1,giv_aux2,giv_aux3,giv_aux4,giv_tense);
								for(int j=0;j<1 && gensentences[j]!=null;j++)
								{
									try{
										String padu=gensentences[j].trim();
										String out=tm.revert(bm.addarray(tm.convert(padu),a));
										String out1=tm.revert(bm.addarray(tm.convert(out),tm.convert(adv_par_full[a1])));
										sb.append(out1.trim()+"\n");

									   }
									catch(Exception ex)
									{

										//System.out.println("Word Verb" + ex);
									}

								}
							}
					giv_sub="அது";
					giv_tense="எதிர்காலம்";
					gensentences = CorporateDemo1.generateSentences(giv_sub,giv_adj1,giv_noun1,giv_case1,giv_clitic1,isSingular1,giv_post1,giv_adj2,giv_noun2,giv_case2,giv_clitic2,isSingular2,giv_post2,giv_adv,giv_verb,giv_aux1,giv_aux2,giv_aux3,giv_aux4,giv_tense);
					for(int j=0;j<1 && gensentences[j]!=null;j++)
						{
						try{
							umout=gensentences[j].trim();
							      sb.append(tm.revert(bm.addarray(tm.convert(umout),tm.convert(adv_par_full[a1]))).trim()+"\n");
						   }
						catch(Exception ex)
							{
							//System.out.println("Word Verb" + ex);
							}
  			 			}
					}
				}



	break;
case 15:
		if(ruleselected.equals("வினை+எச்சம்+எதிர்மறை"))
		  		{
					giv_sub="எழுவாய்";
					giv_tense = "காலங்கள்";
					giv_verb=verbinput;
					giv_noun1 = "பெயர்ச்சொல்";
					giv_case1 = "வேற்றுமைகள்";
					giv_post1 = "பின்நிலை";
					giv_aux1  = "துணை";
					giv_aux2  = "துணை";
					giv_aux3  = "துணை";
					giv_aux4  = "துணை";
					String umout="";
					umout=vf.verbgetinfi(giv_verb);
					for(int e=0;e<ethirmarai.length;e++)
					{
						sb.append(tm.revert(bm.verbaddarray(tm.convert(umout),tm.convert(ethirmarai[e]))).trim()+"\n");
					}
				}
		break;




	}//switch
}//for
	//System.out.println("output for 2");

	return sb;
}//close function

}//close pgm

