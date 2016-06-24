package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.template.unl;

//import org.apache.nutch.enconversion.unl.ta.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.apache.nutch.util.NutchConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.analysis.unl.ta.clia.unl.Source.Word_Gen.Generator.word_noun;

/**
 * @author karthikeyan.S
 * @version 2.0
 * @since AUCEG
 */
public class OfflineSummary {

    public HashMap<String, String[]> Templates;
    public static boolean isTemplateLoaded = false;
    public HashMap<String, String> Temple, God, Animal, Food, Facilities, Transport, Place, summary, placeList;
    public HashSet<String> docid, EmptySummary;
    public int Tempwordcount = 0;
    public static Configuration configuration = NutchConfiguration.create();
    public static String templateHome = configuration.get("UNLCrawl");
    public static String EnconversionHome = configuration.get("Template");

    /**
     * This method define for call the all methods in Summary
     */
    public void callFunctions() {
        TemplateLoaded();
        unlgraphRead();
        AddTemplatewords();
        hashTest();
        summaryWrite();
        EmptySummaryFileWrite();
        placeLisstWrite();
    }

    /**
     * This method defien for Load the Templates
     */
    public void TemplateLoaded() {
        // if (isTemplateLoaded) {
        //     return;
        // }
        Templates = new HashMap<String, String[]>();
        String[] templearray = {"இவ்விடத்தில்  காணப்படும்  கோவில்கள், ", "இவ்விடத்தில் பிரசித்தி  பெற்ற   கோவில்கள் ", "இங்கு  காணப்படும் வழிபாட்டுத் தலங்கள்,", " இங்கு உள்ள பழமை வாய்ந்த கோவில்கள்", "இங்கு  காணப்படும் வரலாற்றுச் சிறப்புமிக்க கோவில்கள் ,"};
        String[] placearray = {" பற்றி எடுத்துரைக்கிறது.", " பற்றிய செய்திகளை எடுத்துரைக்கிறது.", " பற்றிய முக்கிய விவரங்களை உள்ளடக்கியுள்ளது.", " குறித்த   தகவல்களை விவரிக்கிறது ", " பற்றி  விவரிக்கிறது  "};
        String[] godarray = {" போன்ற  தெய்வங்களை மக்கள் வணங்குகிறார்கள", " போன்ற  தெய்வங்களை மக்கள் துதிக்கின்றனர்.", " போன்ற  தெய்வங்களை வழிபடுகிறார்கள். ", " போன்ற  தெய்வங்களை பூஜிக்கின்றனர்.", "போன்ற தெய்வங்களை மக்கள் மிக சிறந்த தெய்வமாக வழிபடுகிறார்கள் "};
        String[] animalarray = {" போன்ற உயிரினங்கள் காணப்படுகின்றன.", " போன்ற  உயிரினங்களை  காணலாம்.", " ஆகிய உயிரினங்கள் உள்ளன.", " ஆகிய உயிரினங்கள் பெரும்பாலும் காணப்படுகின்றன. ", " போன்றவை முக்கிய உயிரினங்கள் ஆகும்"};
        String[] foodarray = {" போன்ற உணவு வகைகள் கிடைக்கும்.", " போன்ற உணவு வகைகள் மிகுதியாக கிடைக்கும். ", " ஆகிய உணவுப் பண்டங்கள கிடைக்கும்.", " ஆகிய உணவுப் பொருட்கள கிடைக்கும.", "போன்ற ஆகாரங்கள் கிடைக்கும்.."};
        Templates.put("Temple", templearray);
        Templates.put("Place", placearray);
        Templates.put("Animal", animalarray);
        Templates.put("God", godarray);
        Templates.put("Food", foodarray);
        intialize();
        //  isTemplateLoaded = true;
    }

    /**
     * This method define for Intialize the all variable for Summary
     */
    public void intialize() {
        Temple = new HashMap<String, String>();
        God = new HashMap<String, String>();
        Animal = new HashMap<String, String>();
        Food = new HashMap<String, String>();
        Facilities = new HashMap<String, String>();
        Transport = new HashMap<String, String>();
        Place = new HashMap<String, String>();
        docid = new HashSet<String>();
        summary = new HashMap<String, String>();
        EmptySummary = new HashSet<String>();
        placeList = new HashMap<String, String>();
    }

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
        ConceptNode conceptnode = new ConceptNode();
        HeadNode headnode = new HeadNode();
        for (int i = 0; i < finalImp.length; i++) {
            if (finalImp[i] != null) {
                headnode = finalImp[i].head;
                conceptnode = headnode.colnext;


                while (conceptnode != null) {
                    docid.add(conceptnode.docid);
//                    System.err.println(docid+"\t"+conceptnode.docid);
                    store(conceptnode.uwconcept, conceptnode.gn_word, conceptnode.docid);
                    //TraverseltoConcpet(conceptnode.rownext, finalImp, conceptnode, i, headnode);
                    conceptnode = conceptnode.getColNext();
                }
            }
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
    public void store(String constraints, String tamilwords, String docid) {
        if (constraints.contains(">temple)")) {
            Templates(tamilwords, docid, Temple);
        }
        if (constraints.contains(">god)")) {
            Templates(tamilwords, docid, God);
        }
        if (constraints.contains("icl>animal)") || constraints.contains("iof>animal)") || constraints.contains(">mammal)") || constraints.contains(">snake)") || constraints.contains(">reptile)") || constraints.contains(">insect") || constraints.contains(">bird)")) {
            Templates(tamilwords, docid, Animal);
        }
        if (constraints.contains(">food)")) {
            Templates(tamilwords, docid, Food);
        }
        if (constraints.contains("iof>place)") || constraints.contains("iof>city)") || constraints.contains("iof>state)") || constraints.contains("iof>continent)") || constraints.contains("iof>district)") || constraints.contains("iof>country")) {
            place(tamilwords, docid, constraints);
        }
        if (constraints.contains(">hotel)") || constraints.contains(">lodge)")) {
            Templates(tamilwords, docid, Facilities);
        }
        if (constraints.contains(">train)") || constraints.contains(">bus)")) {
            Templates(tamilwords, docid, Transport);
        }
    }

    /**
     * This method defien for Add more details in particular Templates
     *
     * @param tamilWord is a String. This String has contains a tamilword
     * @param docid is a String. This String has contains a file name of
     * Documents
     * @param templates is HasMap. This HashMap has contains a document-id and
     * summary's
     */
    public void Templates(String tamilwords, String docid, HashMap<String, String> templates) {
        // System.err.println(templates.size());
        if (templates.size() == 0) {
            templates.put(docid, tamilwords);
        } else if ((templates.size() <= 5)) {
            if (templates.containsKey(docid)) {
                String value = templates.get(docid).toString();
                if (!value.contains(tamilwords)) {
                    value = value + " " + tamilwords;
                    templates.put(docid, value);
                }
            } else {
                templates.put(docid, tamilwords);
            }
        }
    }

    /**
     * This method define for getting places from documents
     *
     * @param tamilWord is a String. This String has contains a tamilword
     * @param docid is a String. This String has contains a file name of
     * Documents
     * @param constraint is a String. This String has contains a Constraints for
     * words
     */
    public void place(String tamilwords, String docid, String constraints) {
        if (!tamilwords.contains("அண்மை") && !tamilwords.contains("கீழ்") && !tamilwords.contains("பகுதி") && !tamilwords.contains("மென்மை") && !tamilwords.contains("கடைசி") && !tamilwords.contains("கீழே")) {
            Templates(tamilwords, docid, Place);
            if (constraints.contains("iof")) {
                placeListProcess(constraints, docid);
            }
        }
    }

    /**
     * This method define for put the places in PlaceLists
     *
     * @param constraint is a String. This String has contains a Constraints for
     * words
     * @param docid is a String. This String has contains a file name of
     * Documents
     */
    public void placeListProcess(String constraints, String docid) {
        int Endindex = constraints.indexOf("(");
        constraints = constraints.substring(0, Endindex) + " ";
        Templates(constraints, docid, placeList);
    }

    /**
     * This method define for Traverse the Toconcpet in unl-graph
     *
     * @param conceptToNode is a Object. This Object has contains ConceptToNode
     * details
     * @param finalImp is a object.This Object has contains a Object of
     * unl-graph
     * @param conceptnode is a Object. This Object has contains Conceptnode
     * details
     * @param i is Integer. This integer has contains which unl-graph count
     * @param headNode is also one of the object. This Object has contains
     * HeadNode Deatails
     */
    public void TraverseltoConcpet(ConceptToNode conceptToNode, FinalLLImpl[] finalImp, ConceptNode conceptnode, int i, HeadNode headNode) {
        while (conceptToNode != null && conceptnode != null) {
            try {
                String ToConcept = finalImp[i].getconcept_vs_conceptid(conceptToNode.uwtoconcept, conceptnode.sentid);
                if (conceptToNode.relnlabel.equals("plt") || conceptToNode.relnlabel.equals("plf")) {
                    String tamilwords = finalImp[i].getconcept_vs_ToConcept(ToConcept, conceptnode.sentid);
                    if (ToConcept.contains("(icl>place)") || ToConcept.contains("(icl>facility)")) {
                        ConceptNode DistanceNode = Distance(conceptnode.sentid, headNode);
                        if (DistanceNode != null) {
                            ConceptNode UnitNode = Unit(DistanceNode, headNode);
                            String words = callgenerator(tamilwords, "பெயர்ச்சொல்+வேற்றுமைஉருபு", "இலிருந்து");
                            //System.out.println("Generator Words" + words);
                        }
                    }
                    store(conceptToNode.uwtoconcept, tamilwords.toString(), conceptnode.docid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            conceptToNode = conceptToNode.getRowNext();
        }
    }

    /**
     * This method define for found the Distance
     *
     * @param sentid is a String. This string has contains the word occur the
     * which sentence
     * @param headNode is also one of the object. This Object has contains
     * HeadNode Deatails
     * @return values is a object. This object has contanins Concpetnode
     */
    public ConceptNode Distance(String sentid, HeadNode head) {
        ConceptNode conceptNode = new ConceptNode();
        conceptNode = head.colnext;
        while (conceptNode != null) {
            if (conceptNode.sentid.equals(sentid)) {
                if (conceptNode.uwconcept.contains("(icl>distance unit)")) {
                    break;
                }
            }
            conceptNode = conceptNode.getColNext();
        }
        return conceptNode;
    }

    /**
     * This method define for found the unit in Conceptnode
     *
     * @param conceptnode is a Object. This Object has contains Conceptnode
     * details
     * @param headNode is also one of the object. This Object has contains
     * HeadNode Deatails
     * @return values is a object. This object has contanins Concpetnode
     */
    public ConceptNode Unit(ConceptNode conceptNode, HeadNode head) {
        ConceptNode traverseNode = new ConceptNode();
        traverseNode = head.colnext;
        while (traverseNode != null) {
            if (traverseNode == conceptNode) {
                ////System.out.println("Welcome");
                break;
            }
            traverseNode = traverseNode.getColNext();
        }
        return conceptNode;
    }

    /**
     * This method define for call the generator
     *
     * @param words is a String. This string has contains word
     * @param rule is a String. This string has contains rule
     * meand(பெயர்ச்சொல்+வேற்றுமைஉருபு)
     * @param caseend is String. This string has contains a word as இலிருந்து
     * @return
     */
    public String callgenerator(String words, String rule, String caseend) {
        String returnwords = "";
        try {
            word_noun noun = new word_noun();
            StringBuffer genwords = noun.NounCMGen1(words, rule, caseend);
            returnwords = genwords.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnwords;
    }

    /**
     * This method define for Add The templates for each documents
     */
    public void AddTemplatewords() {
        for (Object docids : docid) {
            String summarywords = "";
            if (Temple.get(docids.toString()) != null) {
                String[] TemplateWords = (String[]) Templates.get("Temple");
                summarywords = summarywords + " " + TemplateWords[Tempwordcount] + " " + Temple.get(docids.toString()) + ".  ";
            }
            if (God.get(docids.toString()) != null) {
                String[] TemplateWords = (String[]) Templates.get("God");
                summarywords = summarywords + " இவ்விடத்தில் " + God.get(docids.toString()) + " " + TemplateWords[Tempwordcount] + ".  ";
            }
            if (Animal.get(docids.toString()) != null) {
                String[] TemplateWords = (String[]) Templates.get("Animal");
                summarywords = summarywords + " இங்கு " + Animal.get(docids.toString()) + " " + TemplateWords[Tempwordcount] + ".  ";
            }
            if (Food.get(docids.toString()) != null) {
                String[] TemplateWords = (String[]) Templates.get("Food");
                summarywords = summarywords + " இங்கு " + Food.get(docids.toString()) + " " + TemplateWords[Tempwordcount] + ".  ";
            }
            if (Place.get(docids.toString()) != null) {
                String[] TemplateWords = (String[]) Templates.get("Place");
                summarywords = summarywords + " இந்தப்   பக்கம் " + Place.get(docids.toString()) + " " + TemplateWords[Tempwordcount] + ".  ";
            }
            if (Facilities.get(docids.toString()) != null) {
                summarywords = summarywords + " இவ்விடத்தில் " + Facilities.get(docids.toString()) + " வசதி   உண்டு .";
            }
            if (Transport.get(docids.toString()) != null) {
                summarywords = summarywords + " இவ்விடத்திற்க்கு " + Transport.get(docids.toString()) + " மூலம்  செல்லலாம் .";
            }
            Tempwordcount = wordCountcheck(Tempwordcount);
            Tempwordcount++;
            summaryput(docids.toString(), summarywords);
        }
    }

    /**
     * This method define for wordCount for Templates
     *
     * @param count is a integer. This integer has contais templateword counts
     * @return value is integer. This integer value has contains count
     */
    public int wordCountcheck(int count) {
        if (count == 4) {
            count = 0;
        }
        return count;
    }

    /**
     * This method define for put the summary for each documents
     *
     * @param docid is a String. This String has contains a file name of
     * Documents
     * @param summarywords is a String. This string has contaisn summary words
     * for document
     */
    public void summaryput(String docids, String summarywords) {
        if (summarywords.length() != 0) {
            summary.put(docids, summarywords);
        } else {
            EmptySummary.add(docids);
        }
    }

    /**
     * This method defien for Summary Testing
     */
    public void hashTest() {
        Set key = summary.keySet();
        for (Object docids : key) {
            // //System.out.println("Docid" + docids.toString());
            ////System.out.println("Summarys" + summary.get(docids.toString()));
        }
    }

    /**
     * This method define for write the summary to Serialize file
     */
    public void summaryWrite() {
        File file = new File(templateHome + "newsummary");
        file.mkdir();
        ObjectOutputStream objectOutputStream = IOHelper.getObjectOutputStream(templateHome + "newsummary/summary.ser");
        IOHelper.writeObjectToOutputStream(objectOutputStream, summary);
        IOHelper.closeObjectOutputStream(objectOutputStream);
    }

    /**
     * This method define for write the placeList to Serialize file
     */
    public void placeLisstWrite() {
        ObjectOutputStream objectOutputStream = IOHelper.getObjectOutputStream(templateHome + "newsummary/placeList.ser");
        IOHelper.writeObjectToOutputStream(objectOutputStream, placeList);
        IOHelper.closeObjectOutputStream(objectOutputStream);
    }

    /**
     * This method define for write Empty Summar document to text file
     */
    public void EmptySummaryFileWrite() {
        BufferedWriter bufferedWriter = IOHelper.getBufferedWriter(templateHome + "newsummary/summaryEmpty.txt");
        for (Object s : EmptySummary) {
            IOHelper.writeLineToBufferedWriter(bufferedWriter, s.toString());
        }
        IOHelper.closeBufferedWriter(bufferedWriter);
    }

    public static void main(String args[]) {
        OfflineSummary summary = new OfflineSummary();
        summary.callFunctions();
    }
}
