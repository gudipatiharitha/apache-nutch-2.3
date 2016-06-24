/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.nutch.analysis.unl.ta.Integrated;

import java.io.*;
import org.apache.hadoop.conf.Configuration;
//import org.apache.nutch.indexer.solr.SolrIndexer;
import org.apache.nutch.util.NutchConfiguration;

/**
 *
 * @author root
 */
public class Jumbo {

    private static final String basePath = "/home/arjun/unldump/";
    private static final String lamppPath = "/home/arjun/unldump/lampp/htdocs/core/";
    public static final Configuration conf = NutchConfiguration.create();

    public static void _init() throws Exception {
        new File(Jumbo.getBasePath() + "Snippets/").mkdirs();
        new File(Jumbo.getBasePath() + "Summary/").mkdirs();
    }

    public static String getBasePath() throws Exception {
        new File(basePath).mkdirs();
        return basePath;
    }

    public static String getQryPath() throws Exception {
        new File(basePath + "Qry/").mkdirs();
        return basePath + "Qry/";
    }

    public static String getLamppPath(String id) throws Exception {
        new File(lamppPath).mkdirs();
        return lamppPath + id + ".html";
    }

    public static String getLamppURL(String id) throws Exception {
        return "http://localhost/core/" + id + ".html";
    }

    public static String getUWUnknownPath() throws Exception {
        new File(basePath + "UWUnknown/").mkdirs();
        return basePath + "UWUnknown/";
    }

    public static String getTreePath() throws Exception {
        new File(basePath + "Tree/").mkdirs();
        return basePath + "Tree/";
    }

    public static String getUWDictPath() throws Exception {
        new File(basePath + "UWDict/").mkdirs();
        return basePath + "UWDict/";
    }

    public static String getMWDictPath() throws Exception {
        new File(basePath + "MWDict/").mkdirs();
        return basePath + "MWDict/";
    }

    public static String getCorePath() throws Exception {
        new File(basePath + "core/").mkdirs();
        return basePath + "core/";
    }

    public static String getRecnoToURLPath() throws Exception {
        new File(basePath + "RecnoToURL/").mkdirs();
        return basePath + "RecnoToURL/";
    }

    public static String getCGyroPath() throws Exception {
        new File(basePath + "CGyro/").mkdirs();
        return basePath + "CGyro/";
    }

    public static String getCSentGyroPath() throws Exception {
        new File(basePath + "CSentGyro/").mkdirs();
        return basePath + "CSentGyro/";
    }

    public static String getGyroPath() throws Exception {
        new File(basePath + "Gyro/").mkdirs();
        return basePath + "Gyro/";
    }

    public static String getSentGyroPath() throws Exception {
        new File(basePath + "SentGyro/").mkdirs();
        return basePath + "SentGyro/";
    }

    public static String getCatIndexPath() throws Exception {
        new File(basePath + "CatIndex/").mkdirs();
        return basePath + "CatIndex/";
    }

    public static String getCPath(String id) throws Exception {
        new File(AdvancedIndex.computeStoragePath(getTreePath(), id) + "C/").mkdirs();
        return AdvancedIndex.computeStoragePath(getTreePath(), id) + "C/";
    }

    public static String getCRCPath(String id) throws Exception {
        new File(AdvancedIndex.computeStoragePath(getTreePath(), id) + "CRC/").mkdirs();
        return AdvancedIndex.computeStoragePath(getTreePath(), id) + "CRC/";
    }

    public static String getSumTPath(String id) throws Exception {
        new File(AdvancedIndex.computeStoragePath(getTreePath(), id) + "TamilSummary/").mkdirs();
        return AdvancedIndex.computeStoragePath(getTreePath(), id) + "TamilSummary/";
    }

    public static String getSumEPath(String id) throws Exception {
        new File(AdvancedIndex.computeStoragePath(getTreePath(), id) + "EnglishSummary/").mkdirs();
        return AdvancedIndex.computeStoragePath(getTreePath(), id) + "EnglishSummary/";
    }

    public static String getSniPPath(String id) throws Exception {
        new File(AdvancedIndex.computeStoragePath(getTreePath(), id) + "Snippet/").mkdirs();
        return AdvancedIndex.computeStoragePath(getTreePath(), id) + "Snippet/";
    }

    public static String getSnippetPath(String id) throws Exception {
        new File(Jumbo.getBasePath() + "Snippets/" + id + "/").mkdirs();
        return Jumbo.getBasePath() + "Snippets/" + id + "/";
    }
    //

    public static String getCLIAHome() {
        return conf.get("CLIA_HOME", "c:/CliaIITKGP/");
    }
    //

    public static String getSolrUrl() {
        /*
         * <property> <name>SOLR_SERVER_URL</name>
         * <value>http://localhost:8983/solr/</value> <description>Solr Server
         * URL</description> </property>
         */

        return conf.get("SolrUNLUrl");
    }
}
