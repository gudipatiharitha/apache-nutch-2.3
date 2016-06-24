package org.iitkgp.cel.parse.html;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.metadata.Metadata;
import org.apache.nutch.protocol.Content;
import org.apache.nutch.util.NutchConfiguration;

import org.iitkgp.cel.parse.html.TextUtils;

public class FontTranscoderIL {
    public static final Log LOG = LogFactory.getLog("Font Transcoder for Indian Languages");
    public static int count;

    public String fontnames = "";
    public HashMap fontfamilies = new HashMap();

    private Configuration conf;
    public String ftResourceDir;

    public FontTranscoderIL(Configuration conf) {
        setConf(conf);
    }
    
    public String getContentInUTF(String content) {
        StringBuilder sb = new StringBuilder();
                
        try {
            String proprietaryEncoding = null;
            byte[] contentInOctets;
            contentInOctets = content.getBytes();
            proprietaryEncoding = checkForKnownFonts(contentInOctets);
           
            if(proprietaryEncoding != null) {
            	 System.out.print(proprietaryEncoding+"I am the count"+(++count) + " start time font transcoder" + System.currentTimeMillis());
    //            System.out.println("FROM CONF: " + ftResourceDir);
                String fname = ftResourceDir+"/temp.html";
                File cfile = new File(fname);
                TextUtils.WriteToFile(cfile.getAbsolutePath(), content);
                String cmd = "php "+ftResourceDir+"/cmd_convert.php5 \"\" " + cfile.getAbsolutePath();
    //            System.out.println(cmd);
                Process p1 = Runtime.getRuntime().exec(cmd);
                BufferedReader input = new BufferedReader(new InputStreamReader(p1.getInputStream()));

                String line = "";
                while ((line = input.readLine()) != null) {
                    sb.append(line).append(" ");
                }
                p1.destroy();
                input.close();
                cfile.delete();
                System.out.print("end time font transcoder" + System.currentTimeMillis());
            } else {
                sb.setLength(0);
                sb.append(content);
            }
        } catch (Exception e) {
        }
        
        return sb.toString();
    }
        
    public void setConf(Configuration conf) {
        this.conf = conf;
        this.fontnames = conf.get("parser.fontnames", "AAADurga::AAADurga, AAADurgax::AAADurgax, AAADurgaxx::AAADurgaxx, AabpBengali::AabpBengali, AabpBengalix::AabpBengalix, AabpBengalixx::AabpBengalixx, Amudham::Amudham, Anu::Anu, BEJA::BEJA, Bhaskar::Bhaskar, BWRevathi::BWRevathi, Chanakya::Chanakya, DrChatrik::DrChatrik, DV_TTGanesh::DV_TTGanesh, DVW_TTGanesh::DVW_TTGanesh, Eenadu::Eenadu, ElangoTmlPanchali::ElangoTmlPanchali, EPatrika::EPatrika, Gopika::Gopika, Hemalatha::Hemalatha, HTChanakya::HTChanakya, Jagran::Jagran, Kairali::Kairali, Kalakaumudi::Kalakaumudi, Karthika::Karthika, Kiran::Kiran, Krutidev::Krutidev, Kumudam::Kumudam, Manjusha::Manjusha, Manorama::Manorama, Matweb::Matweb, MillenniumVarun::MillenniumVarun, MillenniumVarunWeb::MillenniumVarunWeb, Mithi::Mithi, Nandi::Nandi, Panchami::Panchami,  Pudhari::Pudhari, Revathi::Revathi, Shivaji01::Shivaji, Shivaji::Shivaji, Shree_0908W::Shree_0908W, Shree_Dev_0714::Shree_Dev_0714, Shree_Kan_0850::Shree_Kan_0850, SHREE_MAL_0501::SHREE_MAL_0501, ShreeTam0802::ShreeTam0802, Shree_Tel_0900::Shree_Tel_0900, Shree_Tel_0902::Shree_Tel_0902, Shusha::Shusha, Subak::Subak, SuriTln::SuriTln, TAB::TAB, TAM_LFS_Kamban::TAM_LFS_Kamban, TAM::TAM, TCSMith::TCSMith, TeluguFont::TeluguFont, TeluguLipi::TeluguLipi, Thoolika::Thoolika, Tikkana::Tikkana, TL_Hemalatha::TL_Hemalatha, TSCII_Fonts::TSCII_Fonts, TSCII::TSCII, Ujala::Ujala, Vaartha::Vaartha, VaarthaText::VaarthaText, Vakil01::Vakil01, Vakil::Vakil, VikaasWebFont::VikaasWebFont, Vikatan::Vikatan") + ",";
        this.ftResourceDir = conf.get("Font_Transcoder_Dir");

        Pattern p = Pattern.compile("([^,:]+)::([^,:]+),");
        Matcher m = p.matcher(this.fontnames);
        while(m.find()) {
            this.fontfamilies.put(m.group(2).toLowerCase().trim(), m.group(1).toLowerCase().trim());
        }
    }

    public Configuration getConf() {
        return this.conf;
    }
  
    private String checkForKnownFonts(byte[] content) {
        Object keys[] = fontfamilies.keySet().toArray();
        for (int i=0; i<keys.length; i++) {
            String fontname = (String)keys[i];
            if(fontname.trim().length() > 3 && (new String(content)).toLowerCase().indexOf(fontname.toLowerCase()) >= 0) {
                if (LOG.isInfoEnabled()) {
                    LOG.info("Font name (in if) - " + (String)fontfamilies.get(fontname));
                }
                return (String)fontfamilies.get(fontname) /*"iso-8859-1"*/;
            } else if(fontname.trim().length()<=3 && (new String(content)).toLowerCase().indexOf("\"" + fontname.toLowerCase() + "\"") >= 0) {
                if (LOG.isInfoEnabled()) {
                    LOG.info("Font name (in else) - " + (String)fontfamilies.get(fontname));
                }
                return (String)fontfamilies.get(fontname);
            }
        }
        return null;
    }
  
    public static String GetContents(String file) {
        return GetContents(new File(file));
    }
    
    public static String GetContents(File file) {
        StringBuilder text = new StringBuilder();
        text.setLength(0);
        try {
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ( (line = br.readLine()) != null ) {
                text.append(line).append(" ");
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text.toString();
    }
    
    
    public static void main(String[] args) throws Exception {
     String name = args[0];
     String rawHTMLContent = "", encodedContent = "";

     Configuration conf = NutchConfiguration.create();
     FontTranscoderIL ft = new FontTranscoderIL(conf);
     ft.setConf(conf);

     try {
         rawHTMLContent = ft.GetContents(new File(name));
         System.out.println("RAW: " + rawHTMLContent);
         encodedContent = ft.getContentInUTF(rawHTMLContent);
         System.out.println("ENCODED: " + encodedContent);
     } catch (Exception e) {
         e.printStackTrace();
     }

//     System.out.println("data: "+rawHTMLContent.trim());
//     System.out.println("text: "+encodedContent.toString());

    }

}
