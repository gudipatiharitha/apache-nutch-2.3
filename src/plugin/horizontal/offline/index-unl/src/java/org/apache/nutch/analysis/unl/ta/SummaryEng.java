package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.template.unl;

//import org.apache.nutch.enconversion.unl.ta.*;
//import org.apache.nutch.analysis.unl.ta.*;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import org.apache.nutch.util.NutchConfiguration;
import org.apache.hadoop.conf.Configuration;

/**
 * @author karthikeyan.S
 * @version 2.0
 * @since AUCEG
 */
public class SummaryEng {

    public HashMap<String, String[]> Templates;
    public static boolean isTemplateLoaded = false;
    public HashMap<String, String> Summary = new HashMap<String, String>();
    ;
   // public HashSet<String> docid, EmptySummary;
   // public int Tempwordcount = 0;
    public static Configuration configuration = NutchConfiguration.create();
    public static String templateHome = configuration.get("UNLCrawl");
    public static String EnconversionHome = configuration.get("Template");
    public Hashtable templepl = new Hashtable();
    public Hashtable templesing = new Hashtable();
    public Hashtable godpl = new Hashtable();
    public Hashtable godsing = new Hashtable();
    public Hashtable animalpl = new Hashtable();
    public Hashtable animalsing = new Hashtable();
    public Hashtable foodpl = new Hashtable();
    public Hashtable foodsing = new Hashtable();
    public Hashtable facilitypl = new Hashtable();
    public Hashtable facilitysing = new Hashtable();
    public Hashtable transportpl = new Hashtable();
    public Hashtable transportsing = new Hashtable();
    public Hashtable placepl = new Hashtable();
    public Hashtable placesing = new Hashtable();
    int patternindex = 0;

    /**
     * This method define for call the all methods in Summary
     */
    public void callFunctions() {
        TemplateLoaded();
        unlgraphRead();
        summaryWrite();
        //  AddTemplatewords();
        //  hashTest();
        // summaryWrite();
        //  EmptySummaryFileWrite();
        //placeLisstWrite();
    }

    /**
     * This method defien for Load the Templates
     */
    public void TemplateLoaded() {
        //    if (isTemplateLoaded) {
        //        return;
        //     }
        Templates = new HashMap<String, String[]>();
        String[] templearraysingular = {"The only temple mentioned in this page is #insert", "The page provides information on #insert", "The page talsk about the only temple #insert", "You can find some interseting info on #insert", "#insert is an ancient temple abouth which this document describes"};
        String[] templearrayplural = {"Temples found in this location are #insert , ", "Popular temples mentioned here are #insert", "Famous religious sites mentioned here are #insert", "Ancient temples mentioned here are #insert", "Historical temples in this location are #insert ,"};
        String[] placearraysingular = {"The only location mentioned in this document is #insert", "#insert is one location this document mentions", " This document provides information about #insert", "This document does not talk much about many location. The only location mentioned in this document is #insert", " Information about #insert can be found in this page"};
        String[] placearrayplural = {"This document talks about places like #insert", "Places like #insert are mentioned in this document", "Information about #insert like places are provided in this page", "Multiple locations such as #insert are mentioned in this page", "This document mentions locations such as #insert"};
        String[] transportarraysingular = {"The only transport facility  mentioned in this page is  #insert", "The page talks about the #insert transport facility", "Transport facility like #insert is mentioned in this page", "#insert is the only transport facility mentioned in this page", "transport facility like #insert is available in this tourist place"};
        String[] transportarrayplural = {"The transport facilities mentioned in this page are  #insert", "Transport facilities like #insert are available to reach this tourist spot", "One can reach this tourist spot by #insert", "Transport facilities like #insert are mentioned in this page", "The page mentions about transport facilities like #insert"};
        String[] animalarraysingular = {"#insert is the only animal mentioned in this document", " Not many animals other than #insert are mentioned in this page", " #insert can be spotted in this region", " This page provides some information on #insert", "This document briefly mentions presence of #insert in this region"};
        String[] animalarrayplural = {" Animals such as #insert are mentioned in this document ", "This document talks about animals like #insert", "This page provides information about animals such as #insert found in this region.", "#insert are a few animals that can be spotted in this region.", "Quite a few animals such as #insert are mentioned in this region"};
        String[] facilityarraysingular = {"The only boarding  facility mentioned in this page is #insert ", "Details regarding #insert is mentioned in this page", "Boarding facility like #insert is described in this page", "#insert facility is mentioned in this page", "Information about #insert is mentioned in this page"};
        String[] facilityarrayplural = {"The  boarding  facilities mentioned in this page are #insert", "Details regarding #insert boarding facilities are mentioned in this page", "Boarding facilities like #insert are described in this page", "#insert facilities are mentioned in this page", "Information about #insert are mentioned in this page"};
        String[] foodarraysingular = {"The only food  mentioned in this page is #insert", "#insert is described in this page", "Informatin about #insert is mentioned in this page", "About #insert is mentioned in this page", "The page mentions about #insert"};
        String[] foodarrayplural = {"The  food information   mentioned in this page are  #insert", "#insert are described in this page", "Informatin about #insert are  mentioned in this page", "About #insert are  mentioned in this page", "The page mentions about #insert"};
        String[] godarraysingular = {"Information about #insert deity is mentioned in this page", " This page does not talk about many deities.The page mentions about #insert", "#insert is the only deity mentioned in this document", "This page talks about #insert deity", " This page has some religious content mentioning #deity"};
        String[] godarrayplural = {"Information about deities like #insert is provided in this document", "This document talks about some religious sites and deities such as #insert", " #insert are a few deities mentioned in this document", " Popular deities such as #insert are mentioned in this page", " This page mentions about deities like #insert"};



        /*
         * Templates.put("Temple", templearray); Templates.put("Place",
         * placearray); Templates.put("Animal", animalarray);
         * Templates.put("God", godarray); Templates.put("Food", foodarray);
        intialize();
         */
        templepl.put("Temple", templearrayplural);
        templesing.put("Temple", templearraysingular);
        godpl.put("God", godarrayplural);
        godsing.put("God", godarraysingular);
        animalpl.put("Animal,temple", animalarrayplural);
        animalsing.put("Animal", animalarraysingular);
        placepl.put("Place", placearrayplural);
        placesing.put("Place", placearraysingular);
        foodpl.put("Food", foodarrayplural);
        foodsing.put("Food", foodarraysingular);
        transportpl.put("Transport", transportarrayplural);
        transportsing.put("Transport", transportarraysingular);
        facilitypl.put("Facility", facilityarrayplural);
        facilitysing.put("Facility", facilityarraysingular);
        //templepl.put("Temple",templearrayplural);
        //templesing.put("Temple",templearrayplural);  
        //  isTemplateLoaded = true;
    }

    /**
     * This method define for Intialize the all variable for Summary
     */
    /*
     * public void intialize() { Temple = new HashMap<String, String>(); God =
     * new HashMap<String, String>(); Animal = new HashMap<String, String>();
     * Food = new HashMap<String, String>(); Facilities = new HashMap<String,
     * String>(); Transport = new HashMap<String, String>(); Place = new
     * HashMap<String, String>(); docid = new HashSet<String>(); summary = new
     * HashMap<String, String>(); EmptySummary = new HashSet<String>();
     * placeList = new HashMap<String, String>();
    }
     */
    /**
     * This method define for Read unlgraph Object files
     */
    public void unlgraphRead() {
        ArrayList FilenameList = unlgraphGetFile();
        for (Object fileName : FilenameList) {
            ObjectInputStream unlgraph = IOHelper.getObjectInputStream(fileName.toString());
            FinalLLImpl[] finalllImp = (FinalLLImpl[]) IOHelper.readObjectFromInputStream(unlgraph);
            Traverselnode(finalllImp);
        }
    }

    /**
     * This method define for How many unlgraph(input-files) stored in one
     * ArrayList name as FileNameList
     *
     * @return a ArrayList name as FileNameList. This List contains a How
     * many(input-files) unl-graph files in unl-graph directory
     */
    public ArrayList unlgraphGetFile() {
        ArrayList FileNameList = new ArrayList();
        File f = new File(EnconversionHome);
        File[] filename = f.listFiles();
        for (Object filenameIterate : filename) {
            if (filenameIterate.toString().contains("unlgraph")) {
                FileNameList.add(filenameIterate.toString());
            }
        }
        return FileNameList;
    }

    /**
     * This method define for Travese in unlgraph
     *
     * @param finalImp is a unlgraph Objects. This object used for Traverse in
     * unl-graph
     */
    public void Traverselnode(FinalLLImpl[] finalImp) {
        //System.out.println("Welcome to Traverse in unlgraph");

        for (int i = 0; i < finalImp.length; i++) {
            //for (int i = 0; i < 100; i++) {
            ArrayList con = new ArrayList();
            String docid = "";
            if (patternindex <= 3 || patternindex == 0) {//&& patternindex!=0){
                patternindex++;
            } else if (patternindex == 4) {
                patternindex = 0;
            }

            if (finalImp[i] != null) {

                ConceptNode conceptnode = new ConceptNode();
                HeadNode headnode = new HeadNode();
                headnode = finalImp[i].head;
                conceptnode = headnode.colnext;

                while (conceptnode != null) {
                    // docid.add(conceptnode.docid);   
                    //  System.out.println("UW IN GRAPH:"+conceptnode.uwconcept);    
                    // con.put(conceptnode.docid,conceptnode.uwconcept);
                    con.add(conceptnode.uwconcept);
                    docid = conceptnode.docid;
                    conceptnode = conceptnode.getColNext();
                }


            }
            checkTemplates(con, docid);
//System.out.println("***********INPUT DOCUMENT OVER************************");
        }
    }

    /**
     * This method define for Stor the summary in particular Templats
     *
     * @param constraint is a String. This String has contains a Constraints for
     * words
     * @param tamilWord is a String. This String has contains a tamilword
     * @param docid is a String. This String has contains a file name of
     * Documents
     */
    /*
     * public void store(String constraints, String concepts, String docid) { if
     * (constraints.contains(">temple)")) { //Templates(concepts, docid,
     * Temple);
     *
     * insertSummary(concepts, docid, temple); }
     *
     * /* if (constraints.contains(">god)")) { Templates(tamilwords, docid,
     * God); } if (constraints.contains(">animal)") ||
     * constraints.contains(">mammal)") || constraints.contains(">snake)") ||
     * constraints.contains(">reptile)") || constraints.contains(">insect") ||
     * constraints.contains(">bird)")) { Templates(tamilwords, docid, Animal); }
     * if (constraints.contains(">food)")) { Templates(tamilwords, docid, Food);
     * } if (constraints.contains(">place)") || constraints.contains(">city)")
     * || constraints.contains(">state)") || constraints.contains(">continent)")
     * || constraints.contains(">district)") ||
     * constraints.contains(">country")) { place(tamilwords, docid,
     * constraints); } if (constraints.contains(">hotel)") ||
     * constraints.contains(">lodge)")) { Templates(tamilwords, docid,
     * Facilities); } if (constraints.contains(">train)") ||
     * constraints.contains(">bus)")) { Templates(tamilwords, docid, Transport);
        }
     */
    //  }
    public void checkTemplates(ArrayList concepts, String docid) {

        ArrayList temple = new ArrayList();
        ArrayList god = new ArrayList();
        ArrayList animals = new ArrayList();
        ArrayList food = new ArrayList();
        ArrayList place = new ArrayList();
        ArrayList facility = new ArrayList();
        ArrayList transport = new ArrayList();

        String key = docid;

        for (int i = 0; i < concepts.size(); i++) {
            // key=(String)e.nextElement();
            String constraints = (String) concepts.get(i);
            // System.out.println("UW:"+constraints);
            if (constraints.contains("iof>place)") || constraints.contains("iof>city)") || constraints.contains("iof>state)") || constraints.contains("iof>continent)") || constraints.contains("iof>district)") || constraints.contains("iof>country")) {
                //System.out.println("UW:"+uw);
                //   boolean flag=checkDuplicates(place,constraints);    
                if (!place.contains(constraints)) {
                    place.add(constraints);
                }
            }
            if (constraints.contains(">god)") || constraints.contains(">goddess)")) {
                if (!god.contains(constraints)) {
                    god.add(constraints);
                }
            }
            if (constraints.contains(">food)")) {
                if (!food.contains(constraints)) {
                    food.add(constraints);
                }
            }
            if (constraints.contains(">animal)") || constraints.contains(">mammal)") || constraints.contains(">snake)") || constraints.contains(">reptile)") || constraints.contains(">insect") || constraints.contains(">bird)")) {
                if (!animals.contains(constraints)) {
                    animals.add(constraints);
                }
            }
            if (constraints.contains(">train)") || constraints.contains(">bus)")) {
                if (!transport.contains(constraints)) {
                    transport.add(constraints);
                }
            }
            if (constraints.contains(">hotel)") || constraints.contains(">lodge)")) {
                if (!facility.contains(constraints)) {
                    facility.add(constraints);
                }
            }


        }
        StringBuffer God = insertGod(god, key);
        StringBuffer Temple = insertTemple(temple, key);
        StringBuffer Place = insertPlace(place, key);
        StringBuffer Animal = insertAnimal(animals, key);
        StringBuffer Transport = insertTransport(transport, key);
        StringBuffer Facility = insertFacility(facility, key);
        StringBuffer Food = insertFood(food, key);
        StringBuffer Sum = extractSummary(God, Temple, Place, Animal, Transport, Facility, Food);
        summaryput(key, Sum.toString());
        //  return Summary;
    }

    /*
     * public boolean checkDuplicate(ArrayList a, String uw){ boolean flag=true;
     * if(!a.contains(uw)){
     *
     * flag=flase\;
     *
     * }
     *
     * return flag=
}
     */
    public StringBuffer insertTemple(ArrayList con, String docid) {

        StringBuffer summ = new StringBuffer();
        if (con.size() >= 2) {
            StringBuffer sum = new StringBuffer();
            //System.out.println("in if ");
            String sumtemp1[] = (String[]) (templepl.get("Temple"));
            String s1 = sumtemp1[patternindex];
            String s2 = "#insert";

            //   sum.append(sumtemp1[0]); 
            for (int i = 0; i < con.size(); i++) {
                String uw = con.get(i).toString();
                int index = uw.indexOf("(");
                String uw1 = uw.substring(0, index);
                if (i == con.size() - 1) {
                    sum.append("and ");
                    sum.append(uw1);//.append("."); 
                    sum.append("#");
                } else {
                    sum.append(uw1).append(", ");
                }
            }

            s1 = s1.replaceAll("#insert", sum.toString());
            summ.append(s1).append(".");
        } else if (con.size() == 1) {
            StringBuffer sum = new StringBuffer();
            // System.out.println("in  else if ");
            String sumtemp1[] = (String[]) (templesing.get("Temple"));
            String s1 = sumtemp1[patternindex];
            String s2 = "#insert";
            //sum.append(sumtemp1[0]);
            String uw = con.get(0).toString();
            int index = uw.indexOf("(");
            String uw1 = uw.substring(0, index);
            sum.append(uw1);
//sum.append(".");   
            sum.append("#");
            s1 = s1.replaceAll("#insert", sum.toString());
            summ.append(s1).append(".");
        }

        //sum.append(con.toString());

        //System.out.println("Summary temple:"+sum.toString());
        return summ;
    }

    public StringBuffer insertGod(ArrayList con, String docid) {

//System.out.println("CON"+con.toString());
//System.out.println("CON SIZE"+con.size());

        StringBuffer summ = new StringBuffer();
        if (con.size() >= 2) {
            //System.out.println("in  if ");
            String sumtemp1[] = (String[]) (godpl.get("God"));
            String s1 = sumtemp1[patternindex];
            String s2 = "#insert";
            StringBuffer sum = new StringBuffer();
            //   sum.append(sumtemp1[0]); 
            for (int i = 0; i < con.size(); i++) {
                String uw = con.get(i).toString();
                int index = uw.indexOf("(");
                String uw1 = uw.substring(0, index);
                if (i == con.size() - 1) {
                    sum.append("and ");
                    sum.append(uw1);//.append("."); 
                    sum.append("#");
                } else {
                    sum.append(uw1).append(", ");
                }
            }
            s1 = s1.replaceAll("#insert", sum.toString());
            summ.append(s1).append(".");
        } else if (con.size() == 1) {
            StringBuffer sum = new StringBuffer();
            //System.out.println("in  else if ");
            String sumtemp1[] = (String[]) (godsing.get("God"));
            String s1 = sumtemp1[patternindex];
            String s2 = "#insert";
            //sum.append(sumtemp1[0]);
            String uw = con.get(0).toString();
            int index = uw.indexOf("(");
            String uw1 = uw.substring(0, index);
            sum.append(uw1);
            // sum.append(".");   
            sum.append("#");
            s1 = s1.replaceAll("#insert", sum.toString());
            summ.append(s1).append(".");
        }


        //sum.append(con.toString());

        //System.out.println("Summary temple:"+sum.toString());
        return summ;
    }

    public StringBuffer insertPlace(ArrayList con, String docid) {


        StringBuffer summ = new StringBuffer();
        //System.out.println("pattern index"+patternindex);
        if (con.size() >= 2) {
            String sumtemp1[] = (String[]) (placepl.get("Place"));
            //   sum.append(sumtemp1[patternindex]);
            String s1 = sumtemp1[patternindex];
            String s2 = "#insert";


            // sum.delete(begin,end);
            StringBuffer sum = new StringBuffer();
            for (int i = 0; i < con.size(); i++) {

                //System.out.println("in  if ");
                String uw = con.get(i).toString();
                int index = uw.indexOf("(");
                String uw1 = uw.substring(0, index);
                //System.out.println("UW1:"+uw1);
                if (i == con.size() - 1) {
                    sum.append("and ");
                    sum.append(uw1);//.append(".");  
                    sum.append("#");
                } else {
                    sum.append(uw1).append(", ");
                }
            }
            s1 = s1.replaceAll("#insert", sum.toString());
            summ.append(s1).append(".");
        } else if (con.size() == 1) {
            StringBuffer sum = new StringBuffer();
            //System.out.println("in  else if ");
            String sumtemp1[] = (String[]) (placesing.get("Place"));
            //sum.append(sumtemp1[patternindex]);
            String s1 = sumtemp1[patternindex];
            String s2 = "#insert";
            String uw = con.get(0).toString();
            int index = uw.indexOf("(");
            String uw1 = uw.substring(0, index);
            sum.append(uw1);
            //  sum.append(".");   
            sum.append("#");
            s1 = s1.replaceAll("#insert", sum.toString());
            summ.append(s1).append(".");
        }

        //sum.append(con.toString());

        //System.out.println("Summary temple:"+sum.toString());
        return summ;
    }

    public StringBuffer insertAnimal(ArrayList con, String docid) {
        StringBuffer summ = new StringBuffer();
        try {

            if (con.size() >= 2) {
                StringBuffer sum = new StringBuffer();
                //System.out.println("in if ");
                String sumtemp1[] = (String[]) (animalpl.get("Animal"));
                String s1 = sumtemp1[patternindex];

                String s2 = "#insert";
                // sum.append(sumtemp1[0]); 
                for (int i = 0; i < con.size(); i++) {
                    String uw = con.get(i).toString();
                    int index = uw.indexOf("(");
                    String uw1 = uw.substring(0, index);
                    if (i == con.size() - 1) {
                        sum.append("and ");
                        sum.append(uw1);//.append(".");  
                        sum.append("#");
                    } else {
                        sum.append(uw1).append(", ");
                    }
                }
                s1 = s1.replaceAll("#insert", sum.toString());
                summ.append(s1).append(".");
            } else if (con.size() == 1) {
                StringBuffer sum = new StringBuffer();
                // System.out.println("in  else if ");
                String sumtemp1[] = (String[]) (animalsing.get("Animal"));
                String s1 = sumtemp1[patternindex];
                String s2 = "#insert";
                String uw = con.get(0).toString();
                int index = uw.indexOf("(");
                String uw1 = uw.substring(0, index);
                sum.append(uw1);
                //sum.append(sumtemp1[0]);
                //   sum.append(con.toString());
                // sum.append(".");   
                sum.append("#");
                s1 = s1.replaceAll("#insert", sum.toString());
                summ.append(s1).append(".");
            }
        } catch (Exception e) {
        }
        //sum.append(con.toString());

        //System.out.println("Summary temple:"+sum.toString());
        return summ;
    }

    public StringBuffer insertFood(ArrayList con, String docid) {

        StringBuffer summ = new StringBuffer();
        if (con.size() >= 2) {
            StringBuffer sum = new StringBuffer();
            //System.out.println("in   if ");
            String sumtemp1[] = (String[]) (foodpl.get("Food"));
            String s1 = sumtemp1[patternindex];
            String s2 = "#insert";
            //   sum.append(sumtemp1[0]);  
            for (int i = 0; i < con.size(); i++) {
                String uw = con.get(i).toString();
                int index = uw.indexOf("(");
                String uw1 = uw.substring(0, index);
                if (i == con.size() - 1) {
                    sum.append("and ");
                    sum.append(uw1);//.append(".");
                    sum.append("#");
                } else {
                    sum.append(uw1).append(", ");
                }
            }
            s1 = s1.replaceAll("#insert", sum.toString());
            summ.append(s1).append(".");
        } else if (con.size() == 1) {
            StringBuffer sum = new StringBuffer();
            //System.out.println("in  else if ");
            String sumtemp1[] = (String[]) (foodsing.get("Food"));
            String s1 = sumtemp1[patternindex];
            String s2 = "#insert";
            //sum.append(sumtemp1[0]);
            //   sum.append(con.toString());
            String uw = con.get(0).toString();
            int index = uw.indexOf("(");
            String uw1 = uw.substring(0, index);
            sum.append(uw1);
            //   sum.append(".");   
            sum.append("#");
            s1 = s1.replaceAll("#insert", sum.toString());
            summ.append(s1).append(".");
        }

        //sum.append(con.toString());

        //System.out.println("Summary temple:"+sum.toString());
        return summ;
    }

    public StringBuffer insertFacility(ArrayList con, String docid) {


        StringBuffer summ = new StringBuffer();
        if (con.size() >= 2) {
            StringBuffer sum = new StringBuffer();

            //System.out.println("in  if ");
            String sumtemp1[] = (String[]) (facilitypl.get("Facility"));
            String s1 = sumtemp1[patternindex];
            String s2 = "#insert";
            //sum.append(sumtemp1[0]);  
            for (int i = 0; i < con.size(); i++) {
                String uw = con.get(i).toString();
                int index = uw.indexOf("(");
                String uw1 = uw.substring(0, index);
                if (i == con.size() - 1) {
                    sum.append("and ");
                    sum.append(uw1);//.append(".");
                    sum.append("#");
                } else {
                    sum.append(uw1).append(", ");
                }
            }
            s1 = s1.replaceAll("#insert", sum.toString());
            summ.append(s1).append(".");
        } else if (con.size() == 1) {
            StringBuffer sum = new StringBuffer();
            //System.out.println("in  else if ");
            String sumtemp1[] = (String[]) (facilitysing.get("Facility"));
            String s1 = sumtemp1[patternindex];
            String s2 = "#insert";
            //sum.append(sumtemp1[0]);
            //   sum.append(con.toString());
            String uw = con.get(0).toString();
            int index = uw.indexOf("(");
            String uw1 = uw.substring(0, index);
            sum.append(uw1);
            //sum.append(sumtemp1[0]);
            // sum.append(con.toString());
//sum.append(".");   
            sum.append("#");
            s1 = s1.replaceAll("#insert", sum.toString());
            summ.append(s1).append(".");
        }

        //sum.append(con.toString());

        //System.out.println("Summary temple:"+sum.toString());
        return summ;
    }

    public StringBuffer insertTransport(ArrayList con, String docid) {


        StringBuffer summ = new StringBuffer();
        if (con.size() >= 1) {
            StringBuffer sum = new StringBuffer();
            //System.out.println("in  if ");
            String sumtemp1[] = (String[]) (transportpl.get("Transport"));
            String s1 = sumtemp1[patternindex];
            String s2 = "#insert";
            //sum.append(sumtemp1[0]); 
            for (int i = 0; i < con.size(); i++) {
                String uw = con.get(i).toString();
                int index = uw.indexOf("(");
                String uw1 = uw.substring(0, index);
                if (i == con.size() - 1) {
                    sum.append("and");
                    sum.append(uw1);//.append(".");
                    sum.append("#");

                } else {
                    sum.append(uw1).append(", ");
                }
            }
            s1 = s1.replaceAll("#insert", sum.toString());
            summ.append(s1).append(".");
        } else if (con.size() == 1) {
            StringBuffer sum = new StringBuffer();
            //System.out.println("in  else if ");
            String sumtemp1[] = (String[]) (transportsing.get("Transport"));
            String s1 = sumtemp1[patternindex];
            String s2 = "#insert";
            //sum.append(sumtemp1[0]);
            //   sum.append(con.toString());
            String uw = con.get(0).toString();
            int index = uw.indexOf("(");
            String uw1 = uw.substring(0, index);
            sum.append(uw1);
            //sum.append(sumtemp1[0]);
            //  sum.append(con.toString());
            // sum.append(".");    
            sum.append("#");
            s1 = s1.replaceAll("#insert", sum.toString());
            summ.append(s1).append(".");
        }

        //sum.append(con.toString());

        ///System.out.println("Summary temple:"+sum.toString());
        return summ;
    }

    public StringBuffer extractSummary(StringBuffer god, StringBuffer Temple, StringBuffer place, StringBuffer animal, StringBuffer transport, StringBuffer facility, StringBuffer food) {
        StringBuffer sum = new StringBuffer();
        try {




            boolean flag = false;
            if (god.toString().contains("#")) {
                int index = god.indexOf("#");
                god.deleteCharAt(index);
                sum.append(god.toString());
            }
            if (Temple.toString().contains("#")) {
                int index = Temple.indexOf("#");
                Temple.deleteCharAt(index);
                sum.append(Temple.toString());
            }
            if (place.toString().contains("#")) {
                int index = place.indexOf("#");
                place.deleteCharAt(index);
                sum.append(place.toString());
            }
            if (animal.toString().contains("#")) {
                int index = animal.indexOf("#");
                animal.deleteCharAt(index);
                sum.append(animal.toString());
            }
            if (transport.toString().contains("#")) {
                int index = transport.indexOf("#");
                transport.deleteCharAt(index);
                sum.append(transport.toString());
            }
            if (facility.toString().contains("#")) {
                int index = facility.indexOf("#");
                facility.deleteCharAt(index);
                sum.append(facility.toString());
            }
            if (food.toString().contains("#")) {
                int index = food.indexOf("#");
                food.deleteCharAt(index);
                sum.append(food.toString());

            }
            /*
             * sum.append(Temple.toString()); sum.append(place.toString());
             * sum.append(animal.toString()); sum.append(transport.toString());
             * sum.append(facility.toString()); sum.append(food.toString());
             * if(sum.toString().contains("#")){
        sum.replace(sum.length()-1);
             */
            //System.out.println("Summary for the document :"+sum.toString());
            // }
            //else{
            //   System.out.println("No summary is generated for the document");
            //}
            //System.out.println("******************************");  

        } catch (Exception e) {
        }
        return sum;


    }

    public void summaryput(String docids, String summarywords) {
        if (summarywords.length() != 0) {

            // System.out.println("DOCIDS"+docids);
           // System.out.println("SUMMARY" + summarywords);
            Summary.put(docids, summarywords);
        } else {
            //  EmptySummary.add(docids);
        }
    }

    public void summaryWrite() {
        File file = new File(templateHome + "newsummary");
        file.mkdir();
        ObjectOutputStream objectOutputStream = IOHelper.getObjectOutputStream(templateHome + "newsummary/summaryeng.ser");
        IOHelper.writeObjectToOutputStream(objectOutputStream, Summary);
        IOHelper.closeObjectOutputStream(objectOutputStream);
    }

    public static void main(String args[]) {
        SummaryEng summary = new SummaryEng();
        StringBuffer s = new StringBuffer();
        String[] placearraysingular = {"The only location mentioned in this document is #insert", "#insert is one location this document mentions", " This document provides information about #insert", "This document does not talk much about many location. The only location mentioned in this document is #insert.", " Information about #insert can be found in this page."};
        //s.append(placearraysingular[0]);  

        // String s1="#insert";
        // String s2=placearraysingular[0];
        // s2=s2.replaceAll("#insert","suba");

//int index=s2.lastindexOf(s1);   
// length=s1.length();
//System.out.println("Index"+index);
//s.replace(length,index,"suba");
//System.out.println("result"+s2.toString());

        summary.callFunctions();
    }
}
