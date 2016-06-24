/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.nutch.analysis.unl.ta.SolrIndx;

import java.net.URL;

import org.apache.nutch.analysis.unl.ta.Integrated.*;
import org.apache.nutch.indexer.solr.SolrUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

/**
 *
 * @author Admin
 */
public class SolrActions {
    /*
     * private static HashMap<String, String> summary = new HashMap<String,
     * String>(); private static HashMap<String, String> snippet = new
     * HashMap<String, String>(); private static HashMap<String, String> c_index
     * = new HashMap<String, String>(); private static HashMap<String, String>
     * crc_index = new HashMap<String, String>(); private static HashMap<String,
     * String> recnotourl = new HashMap<String, String>();
     *
     * public static void _init() { summary = new HashMap<String, String>();
     * snippet = new HashMap<String, String>(); c_index = new HashMap<String,
     * String>(); crc_index = new HashMap<String, String>(); recnotourl = new
     * HashMap<String, String>(); }
     *
     * public static synchronized void _putSummary(String fieldName, String
     * fieldValue) throws Exception { summary.put(fieldName, fieldValue); }
     *
     * public static synchronized void _putSnippet(String fieldName, String
     * fieldValue) throws Exception { snippet.put(fieldName, fieldValue); }
     *
     * public static synchronized void _putCIndex(String fieldName, String
     * fieldValue) throws Exception { c_index.put(fieldName, fieldValue); }
     *
     * public static synchronized void _putCRCIndex(String fieldName, String
     * fieldValue) throws Exception { crc_index.put(fieldName, fieldValue); }
     *
     * public static synchronized void _putRecNoToUrl(String fieldName, String
     * fieldValue) throws Exception { recnotourl.put(fieldName, fieldValue); }
     *
     * public static SolrInputDocument _getDoc(HashMap<String, String> solrinfo,
     * String docid, String url) throws Exception { SolrInputDocument doc = new
     * SolrInputDocument(); Iterator<Map.Entry<String, String>> en =
     * solrinfo.entrySet().iterator(); while (en.hasNext()) {
     * doc.addField(en.next().getKey(), en.next().getValue()); } return doc; }
     */

    public static synchronized void _putDoc(SolrInputDocument doc) throws Exception {
        SolrServer solr = new HttpSolrServer(new String(Jumbo.getSolrUrl()));
        solr.add(doc);
        solr.commit();
    }

    public static void main(String[] args) {
        // TODO code application logic here
    }
}
