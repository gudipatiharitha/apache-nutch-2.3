package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.snippetgeneration.unl.ta;
import java.util.*;
import java.io.*;
public class Summary{
   public static boolean isSummaryLoaded = false;
   public static HashMap<String,String> summary;
   public void loadSummary(){
    if(isSummaryLoaded)
    {
       return;
    }
    ObjectInputStream summaryReader = IOHelper.getObjectInputStream("./crawl-unl/summary.ser");
    summary = (HashMap<String,String>) IOHelper.readObjectFromInputStream(summaryReader);
    isSummaryLoaded = true;
   }
    
   public String getSummaryunl(String docid){
    loadSummary();
    String summarys="";
    Set key=summary.keySet();
    if(!key.contains(docid))
      return "NoSummaryAvailable";
    else
       summarys=summary.get(docid).toString();
    summarys="<a href=\"javascript:showhide('div"+docid+"');\" onmouseover=\"javascript:showhide('div"+docid+"');\" onmouseout=\"javascript:showhide('div"+docid+"');\" style=\"color:green\">குறுந்தொகுப்பு</a>"+"<div id=\"div"+docid+"\" style=\"display:none;border:ridge;margin-right:25%;color:green\">"+"<b> இப்பக்கத்தில் சுற்றுலா பற்றிய குறிப்பு</b><br/>"+summarys+"</div>";
    return summarys;
   }
}
