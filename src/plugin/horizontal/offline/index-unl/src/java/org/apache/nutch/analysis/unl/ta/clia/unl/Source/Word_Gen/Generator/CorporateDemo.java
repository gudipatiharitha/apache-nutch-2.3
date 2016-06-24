
package org.apache.nutch.analysis.unl.ta.clia.unl.Source.Word_Gen.Generator;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
/** This is the main class which acts as the interface for the user*/
public class CorporateDemo
	{

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Font f;
		JComboBox sub_jcb,sub1_jcb,adj1_jcb,noun1_jcb,case1_jcb,post_pos1_jcb,adj2_jcb,noun2_jcb,case2_jcb,post_pos2_jcb,adv_jcb,verb_jcb,aux1_jcb,aux2_jcb,aux3_jcb,aux4_jcb,tense_jcb;
		JButton ok_button,cancel_button,exit_button;
		JRadioButton sing1 = new JRadioButton("ஒருமை",true);
		JRadioButton plur1 = new JRadioButton("பன்மை");
		ButtonGroup bg1 = new ButtonGroup();
		JRadioButton sing2 = new JRadioButton("ஒருமை",true);
		JRadioButton plur2 = new JRadioButton("பன்மை");
		ButtonGroup bg2 = new ButtonGroup();

		JTextArea resultarea = new JTextArea(10,5);
		JScrollPane textpane = new JScrollPane(resultarea);



		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		JPanel p5 = new JPanel();
		JPanel p6 = new JPanel();
		JPanel p7 = new JPanel();
		JPanel p8 = new JPanel();
		JPanel p9 = new JPanel();

		static String[] sub_str = {"எழுவாய்","நான்","நாம்","நாங்கள்","நீ","நீங்கள்","நீவீர்","அவன்","அவள்","அவர்","அவர்கள்","அது","அவை","அவைகள்"};
		static String[]sub1_str={"ஆண்பால்","பெண்பால்","பலர்பால்","ஒன்றன்பால்","பலவின்பால்"};
		static String[] adj_str = {"பெயரடை","உறுதி","அழகு","கருப்பு","உயரம்","பயங்கரம்","கோரம்","தூய்மை","ஒரு","இரு","மூன்று","நான்கு","ஐந்து","ஆறு","ஏழு","எட்டு","ஒன்பது","பத்து","நல்ல"};
		static String[] case_str = {"வேற்றுமைகள்","ஐ","ஆல்","கண்","உக்கு","இல்","இடம்","ஓடு","உடைய","உடன்","அது","இருந்து","இன்","உக்காக","ஆக","இலிருந்து","இடமிருந்து","இனது"};
		static String[] pps_str = {"பின்நிலை","விட","விடவும்","போல","போல்","போன்று","கொண்டு","நோக்கி","பற்றி","குறித்து","சுற்றி","சுற்றிலும்","விட்டு","தவிர","முன்னிட்டு","வேண்டி","ஒட்டி","பொறுத்து","பொறுத்தவரை","ஆக","என்று","முன்","பின்","உள்","இடையே","நடுவே","மத்தியில்","வௌதயே",
"மேல்","கீழ்","எதிரில்","பக்கத்தில்","அருகில்","பதில்","மாறாக","நேராக","உரிய","உள்ள","தகுந்த","வாயிலாக","மூலமாக","வழியாக","பேரில்"
,"பொருட்டு","உடன்","கூட","உடைய","வசம்","இடம்","வரை","தோறும்","ஆர"};
		static String[] adv_str = {"வினையடை","வேகம்","விரைவு","அழகு","அடிக்கடி","இனிமேல்","இனியும்","மறுபடியும்","மீண்டும்","மெல்ல","உள்","எதிர்","பின்","வௌத",
"கூச்சல்","பச்சை","மஞ்சள்","போல","போல்","போன்று","கொண்டு","நோக்கி","பற்றி","குறித்து","சுற்றி","சுற்றிலும்","தவிர","முன்னிட்டு","வண்டி","ஒட்டி","பொறுத்து","ஆக","முன்","இடையே","நடுவே","மத்தியில்","மேல்","கீழ்","எதிரில்","பக்கத்தில்","அருகில்","பதில்","மாறாக",
"நேராக","வாயிலாக","மூலமாக","வழியாக","பேரில்","பொருட்டு","கூட","உடைய","வசம்","இடம்","வரை"};
		static String[] ver_str = {"வினைச்சொல்","சாப்பிடு","செய்","வெட்டு","விழு","போ","படி","நட","உண்","தூங்கு","போடு","பெறு","செல்","சொல்", 
"கல்","காண்","வா"};
		static String[] aux_str = {"துணை","கொள்","இரு","விடு","பார்","தொலை","அழு","கொடு","கிட","கிழி","தள்ளு","தீர்","போடு","காட்டு","மாட்டு","படு","போ","வா",
"செய்","வை","முடி","ஆயிற்று","வேண்டும்","வேண்டாம்","கூடும்","கூடாது","இல்லை","தொலைவு"};
		static String[] noun_str = {"பெயர்ச்சொல்","மாம்பழம்","கிண்ணம்","கட்டில்","கட்டை","பொம்மை","மரம்","தாள்","கத்தி","பூ","புத்தகம்","ஆயுதம்","ஈ","கோட்டூர்புரம்","மதுரை"};
		static String[] tense_str = {"காலங்கள்","நிகழ்காலம்","இறந்தகாலம்","எதிர்காலம்"};



/** This is the constructor in which the components are
  * added in the layout*/
/*public CorporateDemo()
	{
		setTitle("Generator version 1.0");
		setFont(f);

		ImageIcon i = new ImageIcon(getClass().getResource("GIF.GIF"));
		setIconImage(i.getImage());

		try
		{
			InputStream inputStream = getClass().getResourceAsStream("TABANNA.TTF");
			Font f1 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			f = f1.deriveFont(Font.PLAIN, 14);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

        sing1.setFont(f);
        plur1.setFont(f);
		bg1.add(sing1);
		bg1.add(plur1);
		p4.setLayout(new BorderLayout());
		p4.add(sing1,"North");
        p4.add(plur1,"South");

		sing2.setFont(f);
        plur2.setFont(f);
		bg2.add(sing2);
		bg2.add(plur2);
		p5.setLayout(new BorderLayout());
		p5.add(sing2,"North");
        p5.add(plur2,"South");

		p6.setLayout( new FlowLayout(FlowLayout.LEFT));
        sub_jcb   = addComboBox(p6,sub_str);
        sub1_jcb   = addComboBox(p6,sub1_str);

		adj1_jcb  = addComboBox(p1,adj_str);
		adj1_jcb.setEditable(true);

		noun1_jcb = addComboBox(p1,noun_str);
		noun1_jcb.setEditable(true);

		p1.add(p4);

		case1_jcb = addComboBox(p1,case_str);
		post_pos1_jcb = addComboBox(p1,pps_str);
		post_pos1_jcb.addActionListener(this);

		adj2_jcb  = addComboBox(p2,adj_str);
		adj2_jcb.setEditable(true);

		noun2_jcb = addComboBox(p2,noun_str);
		noun2_jcb.setEditable(true);

		p2.add(p5);

		case2_jcb = addComboBox(p2,case_str);
		post_pos2_jcb = addComboBox(p2,pps_str);
		post_pos2_jcb.addActionListener(this);

		adv_jcb = addComboBox(p3,adv_str);


		verb_jcb = addComboBox(p3,ver_str);
		verb_jcb.setEditable(true);
		aux1_jcb = addComboBox(p3,aux_str);
		aux2_jcb = addComboBox(p3,aux_str);
		aux3_jcb = addComboBox(p3,aux_str);
		aux4_jcb = addComboBox(p3,aux_str);


		tense_jcb = addComboBox(p7,tense_str);
		ok_button = addButton(p7,"உருவாக்கு");
		cancel_button = addButton(p7,"அழி");
		exit_button = addButton(p7,"வௌதயேறு");


		p8.setLayout(new BorderLayout());
		p8.add(p2,"North");
		p8.add(p3,"Center");
		p8.add(p7,"South");
		p9.setLayout(new BorderLayout());
		p9.add(p6,"North");
		p9.add(p1,"Center");


		getContentPane().add(p9,"North");
		getContentPane().add(p8,"Center");
		getContentPane().add(textpane,"South");
		resultarea.setFont(f);



		addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent we){System.exit(0);}});

		Dimension framesize = this.getPreferredSize();

		setLocation(dim.width/2 - (framesize.width/2),
                    dim.height/2 - (framesize.height/2));
		setResizable(false);
		setVisible(true);
		pack();
	}

/** In this method the items are added in the Jcombobox
  * the string array is passed
  * return type is Jcombobox*/
/*public JComboBox addComboBox(JPanel p,String str[])
	{
		JComboBox jcb = new JComboBox();
		for(int i=0;i<str.length;i++)
			jcb.addItem(str[i]);
		jcb.setFont(f);
		p.add(jcb);
		return jcb;
	}
/** In this method names are added in the buttons
	* the string is passed
    * return type is Jbutton*/

/*public JButton addButton(JPanel pa,String name)
	{
		JButton but = new JButton(name);
		but.addActionListener(this);
		but.setFont(f);
		pa.add(but);
		return but;
	}
/** In this method the actions
  * of various buttons are captured*/
/*public void actionPerformed(ActionEvent ae)
	{

		String giv_adj1="",giv_noun1 ="",giv_adj2="",giv_noun2 ="" ;
		if(ae.getSource()==ok_button)
		{
			if(!(sub_str[0].equals((String)sub_jcb.getSelectedItem())))
			{
				if(!(ver_str[0].equals((String)verb_jcb.getSelectedItem())))
				{
					if(!(tense_str[0].equals((String)tense_jcb.getSelectedItem())))
					{
						String giv_sub = (String)sub_jcb.getSelectedItem();//
							  giv_adj1 = (String)adj1_jcb.getSelectedItem();
							 giv_noun1 = (String)noun1_jcb.getSelectedItem();
						String giv_case1 = (String)case1_jcb.getSelectedItem();//
						String giv_post1 = (String)post_pos1_jcb.getSelectedItem();//

								giv_adj2 = (String)adj2_jcb.getSelectedItem();
							   giv_noun2 = (String)noun2_jcb.getSelectedItem();
						String giv_case2 = (String)case2_jcb.getSelectedItem();//
						String giv_post2 = (String)post_pos2_jcb.getSelectedItem();//
						String giv_adv = (String)adv_jcb.getSelectedItem();//

						String giv_verb = (String)verb_jcb.getSelectedItem();//
						String giv_aux1 = (String)aux1_jcb.getSelectedItem();//
						String giv_aux2 = (String)aux2_jcb.getSelectedItem();//
						String giv_aux3 = (String)aux3_jcb.getSelectedItem();//
						String giv_aux4 = (String)aux4_jcb.getSelectedItem();//
						String giv_tense = (String)tense_jcb.getSelectedItem();//

						boolean isSingular1 = true;
						boolean isSingular2 = true;

						if(plur1.isSelected())
						   isSingular1 = false;
						if(plur2.isSelected())
					       isSingular2 = false;

					try{	String result[] = generateSentences(giv_sub,giv_adj1,giv_noun1,giv_case1,isSingular1,giv_post1,giv_adj2,giv_noun2,giv_case2,isSingular2,giv_post2,giv_adv,giv_verb,giv_aux1,giv_aux2,giv_aux3,giv_aux4,giv_tense);


						for(int i=0;i<1&& result[i]!=null;i++)
						  resultarea.append(result[i]+"\n");}catch(Exception e){
							  e.printStackTrace();
							  }


					}
					else
						resultarea.setText("you must select a tense");
				}
				else
					resultarea.setText("you must select an verb");
			}
			else
				resultarea.setText("you must select a subject");
		}
		if(ae.getSource()==cancel_button)
			{

				reset();

			}
		if(ae.getSource()==exit_button)
			System.exit(0);
		if(ae.getSource()==post_pos1_jcb)
			if(!(pps_str[0].equals((String)post_pos1_jcb.getSelectedItem())))
			    case1_jcb.setSelectedItem(case_str[0]);
		if(ae.getSource()==post_pos2_jcb)
			if(!(pps_str[0].equals((String)post_pos2_jcb.getSelectedItem())))
			    case2_jcb.setSelectedItem(case_str[0]);
	} 

/** This method generates sentences for the various inputs
  * inputs are the subject,adjective,verb,postpositions
  * auxillary verbs etc	*/
/*public static String[] generateSentences(String subject,String adjective1,String noun1,String case1,boolean issingular1,String post1,String adjective2,String noun2,String case2,boolean issingular2,String post2,String adverb,String verb,String auxilary1,String auxilary2,String auxilary3,String auxilary4,String tense)
 {
	 VerbMethods verbobj = new VerbMethods();
	 CaseMethods caseobj = new CaseMethods();
	 PostPositionMethods postobj = new PostPositionMethods();
	 AdjectiveMethods adjobj = new AdjectiveMethods();

     String post1result="",post2result="",case1result="",case2result="",adj1result="",adj2result="";

     String aux[] ={auxilary1,auxilary2,auxilary3,auxilary4};
	 String[] adv_verb_aux_tense=null;
	 adv_verb_aux_tense =verbobj.callAll(subject,verb,aux,tense,"",adverb);


	 if(!(post1.equals(pps_str[0])))
	 	post1result = " "+postobj.addPostPositions(noun1,post1,1,issingular1);
	 else if(!(case1.equals(case_str[0])))
	 	case1result = " "+caseobj.addCase(noun1,case1,"",issingular1);
	 else if((!(case1.equals(case_str[0])))&&(!(post1.equals(pps_str[0]))))
	 	case1result = " "+noun1;

	 if(!(post2.equals(pps_str[0])))
	 	 	post2result = " "+postobj.addPostPositions(noun2,post2,1,issingular2);
	 	 else if(!(case2.equals(case_str[0])))
	 	case2result = " "+caseobj.addCase(noun2,case2,"",issingular2);

	 if(!(adjective1.equals(adj_str[0])))
	 	adj1result = " "+adjobj.addAdjective(adjective1);
	 if(!(adjective2.equals(adj_str[0])))
	 	adj2result = " "+adjobj.addAdjective(adjective2);



	 for(int count=0;count<adv_verb_aux_tense.length && adv_verb_aux_tense[count]!=null;count++)
	    adv_verb_aux_tense[count]=subject + adj1result + case1result + post1result + adj2result + case2result + post2result + " " + adv_verb_aux_tense[count];

	 return adv_verb_aux_tense;
 }*/


public static String[] generateSentences(String subject,String adjective1,String noun1,String case1,boolean issingular1,String post1,String adjective2,String noun2,String case2,boolean issingular2,String post2,String adverb,String verb,String auxilary1,String auxilary2,String auxilary3,String auxilary4,String tense)throws IOException
{
    System.out.println("Sub :" + subject);
	  System.out.println("Adj1 :" + adjective1);
	 System.out.println("Noun1 :" + noun1);
	 System.out.println("Ca2 :" + case1);
	 System.out.println("Sin1 :" + issingular1);
	 System.out.println("Pos1 :" + post1);
	  System.out.println("Adj2 :" + adjective2);
	 System.out.println("N2 :" + noun2);
	 System.out.println("Cs2 :" + case2);
	  System.out.println("Sin2 :" + issingular2);
	 System.out.println("Po2 :" + post2);
	 System.out.println("Adv :" + adverb);
	 System.out.println("Ver :" + verb);
	 System.out.println("Aux1 :" + auxilary1);
	 System.out.println("Ax2 :" + auxilary2);
	 System.out.println("Ax3 :" + auxilary3);
	 System.out.println("Ax4 :" + auxilary4);
	 System.out.println("Tense :" + tense);

	 VerbMethods verbobj = new VerbMethods();
	 CaseMethods caseobj = new CaseMethods();
	 PostPositionMethods postobj = new PostPositionMethods();
	 AdjectiveMethods adjobj = new AdjectiveMethods();

    String post1result="",post2result="",case1result="",case2result="",adj1result="",adj2result="";

    String aux[] ={auxilary1,auxilary2,auxilary3,auxilary4};
	 String[] adv_verb_aux_tense=null;
	 System.out.println("Generated" + subject+noun1+verb+tense);
 //   adv_verb_aux_tense =verbobj.callAll(subject,verb,aux,tense,"",adverb,voice,verb);
    adv_verb_aux_tense =verbobj.callAll(subject,verb,aux,tense,"",adverb);

	 if(!(post1.equals(pps_str[0])))
	 	post1result = " "+postobj.addPostPositions(noun1,post1,1,issingular1);
	 else if(!(case1.equals(case_str[0])))
	 	case1result = " "+caseobj.addCase(noun1,case1,"",issingular1);

	 if(!(post2.equals(pps_str[0])))
	 	 	post2result = " "+postobj.addPostPositions(noun2,post2,1,issingular2);
	 else if(!(case2.equals(case_str[0])))
	 	 {
	 	  System.out.println("case2 entered");
	 	 case2result = " "+caseobj.addCase(noun2,case2,"",issingular2);
	     }
	 System.out.println("case2="+case2);
	 if(!(adjective1.equals(adj_str[0])))
	 	adj1result = " "+adjobj.addAdjective(adjective1);
	 if(!(adjective2.equals(adj_str[0])))
	 	adj2result = " "+adjobj.addAdjective(adjective2);


	System.out.println(adv_verb_aux_tense[0]);
System.out.println("subject + adj1result + case1result + post1result + adj2result + case2result + post2result "+
                            subject +"\n"+ adj1result +"\n"+ case1result +"\n"+ post1result +"\n"+ adj2result +"\n"+ case2result +"\n"+ post2result+"\n");
for(int count=0;count<adv_verb_aux_tense.length && adv_verb_aux_tense[count]!=null;count++)
	    adv_verb_aux_tense[count]=subject + adj1result + case1result + post1result + adj2result + case2result + post2result + " " + adv_verb_aux_tense[count];

	 System.out.println( "adv_verb_aux_tense ------>"+adv_verb_aux_tense[0]);
	 return adv_verb_aux_tense;
}
/** This is the main method*/
public static void main(String ar[])
	{
		new CorporateDemo();
	}
/** thos method resets all the buttons*/
public void reset()
{
					sub_jcb.setSelectedItem("எழுவாய்");
					adj1_jcb.setSelectedItem("பெயரடை");
					noun1_jcb.setSelectedItem("பெயர்ச்சொல்");
					case1_jcb.setSelectedItem("வேற்றுமைகள்");
					post_pos1_jcb.setSelectedItem("பின்நிலை");
					adj2_jcb.setSelectedItem("பெயரடை");
					noun2_jcb.setSelectedItem("பெயர்ச்சொல்");
					case2_jcb.setSelectedItem("வேற்றுமைகள்");
					post_pos2_jcb.setSelectedItem("பின்நிலை");
					adv_jcb.setSelectedItem("வினையடை");
					verb_jcb.setSelectedItem("வினைச்சொற்கள்");
					aux1_jcb.setSelectedItem("துணை");
					aux2_jcb.setSelectedItem("துணை");
					aux3_jcb.setSelectedItem("துணை");
					aux4_jcb.setSelectedItem("துணை");
					tense_jcb.setSelectedItem("காலங்கள்");
					resultarea.setText("");
					sing1.setSelected(true);
					sing2.setSelected(true);

}
}
