package org.apache.nutch.analysis.unl.ta;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Hashtable;
import java.io.*;
import java.util.*;
public class URLRemoval {
  public static void main(String[] args) throws FileNotFoundException {
    Hashtable hash = new Hashtable();
    Scanner scanner = new Scanner(new File("url.txt")).useDelimiter("\\Z");
    String contents = scanner.next();
   // System.out.println(contents);
    
    Scanner scanner1 = new Scanner(new File("url_Tamil.txt")).useDelimiter("\\Z");
    String contents1 = scanner1.next();
    StringTokenizer strToken1 = new StringTokenizer(contents1, "\n");
    while(strToken1.hasMoreTokens()){
	String get_did =  strToken1.nextToken();
        hash.put(get_did,"1");
    }
    StringTokenizer strToken2 =  new StringTokenizer(contents, "\n");
    while(strToken2.hasMoreTokens()){
	String get_did1 =  strToken2.nextToken();
        if(hash.containsKey(get_did1)){
		System.out.println("content/"+get_did1+".txt");
	}
    }
  //  System.out.println(contents);
    scanner1.close();
	scanner.close();

  }
}
