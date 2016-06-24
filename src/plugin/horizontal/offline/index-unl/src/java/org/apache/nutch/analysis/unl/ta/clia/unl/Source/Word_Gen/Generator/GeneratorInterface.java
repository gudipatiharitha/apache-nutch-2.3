
package org.apache.nutch.analysis.unl.ta.clia.unl.Source.Word_Gen.Generator;



/**
 * APPLICATION For Morphological Generator
 * 
 * 
 * 
 */

import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;
import java.util.*;
import java.lang.Math.*;


/**
  * This class contains the main class. The interface is designed here and on
  * click of various buttons the actions are performed  by calling the relevant
  * meth_scompods declared above.
  */

public class GeneratorInterface  extends JFrame
{
	/** input file path */
	public String infile;
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	/** contains input file paths */
	Vector ifile=new Vector(100,1);
	public JRadioButton jrb_threshold;
    Container container = getContentPane();
    static Font f = new Font("Latha",4,12);
     //static Font f1 = new Font("TAB-Anna",4,12);
    Vector generatedvec;
    JComboBox jcbrule;String jcbselected="";String jcbselected1="";
    String jcbverbselected="";String jcbverbselected1="";
    JComboBox jcbrule1;
    JComboBox jcbverbrule;
    JComboBox jcbverbrule1;
    word_verb verbwg = new word_verb();
    file_verb verbfile = new file_verb();
    file_noun ncm=new file_noun();
    word_noun ncmword=new word_noun();
    StringBuffer sb=new StringBuffer();
    static String[] str = {"பெயர்ச்சொல் விதிகள்","பெயர்ச்சொல்+வேற்றுமை உருபு",
		"பெயர்ச்சொல்+பன்மை",
		"பெயர்ச்சொல்+ஒட்டு",
		"பெயர்ச்சொல்+பன்மை+ஒட்டு",
		"பெயர்ச்சொல்+பன்மை+வேற்றுமை உருபு",
	//	"பெயர்ச்சொல்+வேற்றுமை உ      ருபு+ஒட்டு",
		"பெயர்ச்சொல்+வேற்றுமை உருபு+சொல்லுருபு"};
//	"பெயர்ச்சொல்+வேற்றுமை உருபு+சொல்லுருபு+ஒட்டு"};
	//"பெயர்ச்சொல்+உரிச்சொல் ஈற்றசை"};
  //  static String[] str = {"Noun Rules","Clitics","Pl+Clitic","N1+Plural","Pl+CM","CM+Clitic","PP+Clitic","CM+PP","N1+CM","N1+PP","N1+Clitic","N1+AdjectivalSuffix"};
    //static String[] str1 ={"Verb Rules","V+TM+PNG","V+TM+Aux1+PNG","V+TM+Aux1+Aux2+PNG","V+TM+Aux1+Aux2+Aux3+PNG"};
    String[] str1 ={"வினைச்சொல் விதிகள்","வினை+காலம்+பால்/இட/எண்","வினை+காலம்+துணை1+பால்/இட/எண்",
"வினை+காலம்+துணை1+துணை2+பால்/இட/எண்","வினை+காலம்+துணை1+துணை2+துணை3+பால்/இட/எண்","வினை+காலம்+து.வி1+து.வி2+து.வி3+து.வி4+பால்/இட/எண்",
"வினை+உம்+பெயரெச்ச பின்னொட்டு","வினை+இறந்தகாலம்+நிபந்தனை விகுதி","வினை+எச்சம்+ஈற்றசை",
"வினை+எச்சம்+நிலை+(பால்/இட/எண்)/(தொ.பெ.விகுதி)/(+வே.உ)","ஏ.மு.ஒ.விகுதி(ISM) / ஏ.மு.ப.விகுதி(IPM)",
"வினை+எச்சம்+செ.பாட்டு+காலம்+பா/இ/எண்","வினை+எச்சம்+செ.பாட்டு+காலம்+தொ.பெ.விகுதி / (+வே.உ)",
"வினை+காலம்+பெ.எச்சம்+தொ.பெ","வினை+காலம்+பெ.எச்சம்+பெயரெச்ச பின்னொட்டு",
"வினை+எச்சம்+எதிர்மறை"};


	/** to count the number of segments of a file */
	public int count=0;

	/** Main Method */
	public static void main(String args[])throws IOException
	{
		/* Information about frame */
		JFrame framemain = new GeneratorInterface();

		framemain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		framemain.pack();
		framemain.setTitle("Word Generator version 1.1");
		framemain.setBounds(10,10,750,400);
		framemain.setVisible(true);
	}//main ends

	/** constructor with no parameter */
GeneratorInterface()throws IOException
	{

        /** file_select contains the input file */
		final Vector file_select = new Vector(100,1);
		/** dimension of a button */
	    Dimension tdim = new Dimension(50,25);
		Dimension butdim=new Dimension(200,100);
		final JFileChooser filechooser = new JFileChooser(".");
		Dimension buttondim = new Dimension(150,25);
		Dimension textdim = new Dimension(300,25);

		/**For word generation these text field and text area**/
		final JTextArea jta1= new JTextArea(10,40);
		jta1.setText("");
		jta1.setEditable(true);
		jta1.setLineWrap(true);
		jta1.setWrapStyleWord(true);
		JScrollPane jsp1 = new JScrollPane(jta1);
		jta1.setFont(f);

		final JTextField intext1 = new JTextField();
		intext1.setMaximumSize(textdim);
		intext1.setMinimumSize(textdim);
		intext1.setFont(f);
		intext1.setText("யிக்ஷெந்ரூ");


        /* text area in the main screen where the input files will be displayed
		 * by clicking add button described below
		 */
		final JTextArea jta= new JTextArea(10,40);
		jta.setText("");
		jta.setEditable(true);
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);
    	JScrollPane jsp = new JScrollPane(jta);
    	jsp.setFont(f);

		/** intext is the provision that displays the chosen file */
		final JTextField intext = new JTextField();
		intext.setMaximumSize(textdim);
		intext.setMinimumSize(textdim);
		intext.setFont(f);



		/* To Choose a file */
     	JButton inbut = new JButton("கோப்பை தேர்வு செய்");
     	inbut.setFont(f);
				//inbut.setMnemonic('i');
				//inbut.setFont(f);
				inbut.setMaximumSize(buttondim);
			//	inbut.setColor(black);
				inbut.addActionListener
				(
					new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							int retval = filechooser.showOpenDialog(GeneratorInterface.this);
							if(retval == JFileChooser.APPROVE_OPTION)
							{
								File file = filechooser.getSelectedFile();
								infile = file.getPath();
								intext.setText(infile);
								//System.out.println("filein"+intext.getText());
							}
						}
					}
		);
         //jtf_number is to specify the number of indexwords should be taken
		final JTextField jtf_number = new JTextField();
		jtf_number.setMaximumSize(tdim);
		jtf_number.setMinimumSize(tdim);

		//tsub: label
		JLabel tsub = new JLabel("அடிச்சொல்லை உள்ளீடு செய்: ");
		tsub.setFont(f);
		JLabel tcomb = new JLabel("விதியை தேர்வு செய்: ");
		tcomb.setFont(f);
		JLabel tcomb1 = new JLabel("விதியை தேர்வு செய்: ");
		tcomb1.setFont(f);
		jcbrule=new JComboBox();
		jcbrule.setFont(f);
		for(int i=0;i<str.length;i++)
			jcbrule.addItem(str[i]);

		jcbrule.addActionListener
		(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						jcbselected=String.valueOf(jcbrule.getSelectedItem());
						//System.out.println("jcbruleselected"+jcbselected);


					}
				}
			);
			jcbverbrule=new JComboBox();
			jcbverbrule.setFont(f);
					for(int i=0;i<str1.length;i++)
						jcbverbrule.addItem(str1[i]);

					jcbverbrule.addActionListener
					(
							new ActionListener()
							{
								public void actionPerformed(ActionEvent e)
								{
									jcbverbselected=String.valueOf(jcbverbrule.getSelectedItem());
									//System.out.println("jcbruleselected"+jcbverbselected);


								}
							}
			);
		jcbrule1=new JComboBox();
		jcbrule1.setFont(f);
		for(int i=0;i<str.length;i++)
			jcbrule1.addItem(str[i]);
		jcbrule1.addActionListener
		(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						jcbselected1=String.valueOf(jcbrule1.getSelectedItem());
						//System.out.println("jcbruleselected"+jcbselected1);


					}
				}
			);
			jcbverbrule1=new JComboBox();
			jcbverbrule1.setFont(f);
								for(int i=0;i<str1.length;i++)
									jcbverbrule1.addItem(str1[i]);

								jcbverbrule1.addActionListener
								(
										new ActionListener()
										{
											public void actionPerformed(ActionEvent e)
											{
												jcbverbselected1=String.valueOf(jcbverbrule1.getSelectedItem());

											}
										}
			);



		jcbverbrule1.setSelectedIndex(0);
		JLabel tsub1 = new JLabel("உருபனியியல் உருவாக்கியின் வௌதயீட்டு முடிவுகள்: ");
		tsub1.setFont(f);

		JLabel toutlabel = new JLabel("உருபனியியல் உருவாக்கியின் வௌதயீட்டு முடிவுகள்: ");
		toutlabel.setFont(f);


		//to add a input document
		JButton jb_add_docu = new JButton("Add");
		jb_add_docu.setMnemonic('a');
		jb_add_docu.setMaximumSize(buttondim);
		jb_add_docu.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{

				}
			}
		);


	  	JButton jb_sub=new JButton("பெயர்ச்சொற்களை உருவாக்கு");
	  	jb_sub.setFont(f);
		//jb_sub.setMnemonic('N');
		jb_sub.setMaximumSize(butdim);
		jb_sub.addActionListener

		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
				sb.replace(0,sb.length(),"");
				sb=ncm.NounCMGen(intext.getText(),jcbselected);

				}


			}

	);
		JButton jb_verb=new JButton("வினைச்சொற்களை உருவாக்கு");
		jb_verb.setFont(f);
			//jb_verb.setMnemonic('V');
			jb_verb.setMaximumSize(butdim);
			jb_verb.addActionListener

			(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						sb.replace(0,sb.length(),"");
						sb=verbfile.verbgeneration(intext.getText(),jcbverbselected);
						sb.append("\n");

					}


				}

	);
				JButton jb_wordsub=new JButton("பெயர்ச்சொற்களை உருவாக்கு");
				jb_wordsub.setFont(f);
				//jb_wordsub.setMnemonic('N');
				jb_wordsub.setMaximumSize(butdim);
				jb_wordsub.addActionListener

				(
					new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
						sb.replace(0,sb.length(),"");
						//sb=ncmword.NounCMGen1(intext1.getText(),jcbselected1);
						//jta1.setText(sb.toString()+"\n");

					}

					}

	);

			JButton jb_verbsub=new JButton("வினைச்சொற்களை உருவாக்கு");
			jb_verbsub.setFont(f);
			//jb_verbsub.setMnemonic('V');
			jb_verbsub.setMaximumSize(butdim);
			jb_verbsub.addActionListener

			(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
					sb.replace(0,sb.length(),"");
					//sb=verbwg.verbgeneration(intext1.getText(),jcbverbselected1);
	//				jta1.setText(sb.toString()+"\n");
						}
					}

				);

		/** second button*/
		  	JButton jb_sub1=new JButton("சேமி");
		  	jb_sub1.setFont(f);
		  	//jb_sub.setMnemonic('S');
			jb_sub1.setMaximumSize(butdim);
			jb_sub1.addActionListener

			(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						int retval = filechooser.showSaveDialog(GeneratorInterface.this);
						if(retval == JFileChooser.APPROVE_OPTION)
						{
						File file = filechooser.getSelectedFile();
						infile = file.getPath();
						try
						  {

						FileWriter f=new FileWriter(file.getPath(),true);
						BufferedWriter bf=new BufferedWriter(f);
						bf.write(sb.toString());
						bf.newLine();
						bf.close();
						f.close();

						}catch(Exception ex){
//							System.out.println("N_CM2" + ex);
							}

							}

					}


				}

		);

	/** third button*/
	  	JButton jb_sub2=new JButton("அழி");
	  	jb_sub2.setFont(f);
	  	//jb_sub.setMnemonic('C');
		jb_sub2.setMaximumSize(butdim);
		jb_sub2.addActionListener

		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					jcbrule.setSelectedItem("Noun Rules");
					jcbverbrule.setSelectedItem("வினைச்சொல் விதிகள்");
					intext.setText("");
				}


			}

	);

		JButton jb_clear=new JButton("அழி");
		jb_clear.setFont(f);
	  	//jb_clear.setMnemonic('C');
		jb_clear.setMaximumSize(butdim);
		jb_clear.addActionListener

		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
				jcbrule1.setSelectedItem("Noun Rules");
				jcbverbrule1.setSelectedItem("வினைச்சொல் விதிகள்");
				intext1.setText("");
				jta1.setText("");
				}


			}

	);




		JButton jb_reset=new JButton("வௌதயேறு");
		jb_reset.setFont(f);
		//jb_reset.setMnemonic('x');
		jb_reset.setMaximumSize(butdim);
		jb_reset.addActionListener
		(
	 new ActionListener()
	  {
	  	public void actionPerformed(ActionEvent e)
	  	{

			System.exit(0);
	  	}
	  }
	   );

	   JButton jb_reset1=new JButton("வௌதயேறு");
	   jb_reset1.setFont(f);
	   		//jb_reset1.setMnemonic('x');
	   		jb_reset1.setMaximumSize(butdim);
	   		jb_reset1.addActionListener
	   		(
	   	 new ActionListener()
	   	  {
	   	  	public void actionPerformed(ActionEvent e)
	   	  	{

	   			System.exit(0);
	   	  	}
	   	  }
	   );


		JPanel tsubpanel = new JPanel();
		tsubpanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
		tsubpanel.setLayout(new BoxLayout(tsubpanel, BoxLayout.X_AXIS));
		tsubpanel.add(tcomb);
		tsubpanel.add(jcbrule);
		tsubpanel.add(jcbverbrule);

		JPanel wgsubpanel = new JPanel();
		wgsubpanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
		wgsubpanel.setLayout(new BoxLayout(wgsubpanel, BoxLayout.X_AXIS));
		wgsubpanel.add(tcomb1);
		wgsubpanel.add(jcbrule1);
		wgsubpanel.add(jcbverbrule1);



		JPanel tsubpanel1 = new JPanel();
		tsubpanel1.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
		tsubpanel1.setLayout(new BoxLayout(tsubpanel1, BoxLayout.X_AXIS));
		tsubpanel1.add(tsub1);

		JPanel tadd_panel = new JPanel();
		tadd_panel.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
		tadd_panel.setLayout(new BoxLayout(tsubpanel, BoxLayout.X_AXIS));
		tadd_panel.add(jb_add_docu);


		JPanel tcoll_panel = new JPanel();
		tcoll_panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		tcoll_panel.setLayout(new BoxLayout(tcoll_panel, BoxLayout.X_AXIS));
		tcoll_panel.add(inbut);
		tcoll_panel.add(intext);

		JPanel tcoll_panel1 = new JPanel();
		tcoll_panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		tcoll_panel1.setLayout(new BoxLayout(tcoll_panel1, BoxLayout.X_AXIS));
		tcoll_panel1.add(tsub);
		tcoll_panel1.add(intext1);

		JPanel tsubpanel2 = new JPanel();
		tsubpanel2.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
		tsubpanel2.setLayout(new BoxLayout(tsubpanel2, BoxLayout.X_AXIS));
		tsubpanel2.add(toutlabel);



		JPanel butpanel=new JPanel();
		butpanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	    butpanel.setLayout(new BoxLayout(butpanel,BoxLayout.X_AXIS));
		//butpanel.add(jb_add_docu);
		butpanel.add(jb_sub);
		butpanel.add(jb_verb);
		butpanel.add(jb_sub1);
		butpanel.add(jb_sub2);
		butpanel.add(jb_reset);

		JPanel butpanel1=new JPanel();
		butpanel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		butpanel1.setLayout(new BoxLayout(butpanel1,BoxLayout.X_AXIS));
		//butpanel.add(jb_add_docu);
		butpanel1.add(jb_wordsub);
		butpanel1.add(jb_verbsub);
		butpanel1.add(jb_clear);
		butpanel1.add(jb_reset1);


		final JPanel fpanel = new JPanel();
		fpanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		fpanel.setLayout(new BoxLayout(fpanel, BoxLayout.Y_AXIS));
		fpanel.add(tsubpanel);
		fpanel.add(tcoll_panel);
		fpanel.add(tsubpanel1);
		fpanel.add(jsp);
		fpanel.add(butpanel);

		final JPanel wordpanel = new JPanel();
		wordpanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		wordpanel.setLayout(new BoxLayout(wordpanel, BoxLayout.Y_AXIS));
		wordpanel.add(wgsubpanel);
		wordpanel.add(tcoll_panel1);
		wordpanel.add(tsubpanel2);
		wordpanel.add(jsp1);
		wordpanel.add(butpanel1);


		 JTabbedPane uitab = new JTabbedPane();
		uitab.addTab("கோப்பு உருவாக்கி",fpanel);
		 uitab.addTab("சொல் உருவாக்கி",wordpanel);
		 uitab.setSelectedIndex(1);
		 container.add(uitab);
		 uitab.setFont(f);

		addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent we){System.exit(0);}});
		//Dimension framesize = this.getPreferredSize();
		//setLocation(dim.width/2 - (framesize.width/2),
		//dim.height/2 - (framesize.height/2));
		//setResizable(true);
		//setVisible(true);
		//pack();

	}
}













