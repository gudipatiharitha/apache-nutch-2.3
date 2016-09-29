/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.beehyv.nutch.indexwriter.elastic;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

import com.beehyv.nectar.models.json.*;
import com.fasterxml.jackson.core.JsonGenerationException;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.http.util.TextUtils;
import org.apache.nutch.indexer.IndexWriter;
import org.apache.nutch.indexer.NutchDocument;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class BeehyvIndexWriter implements IndexWriter {
    public static Logger LOG = LoggerFactory.getLogger(BeehyvIndexWriter.class);

    private static final int DEFAULT_MAX_BULK_DOCS = 250;
    private static final int DEFAULT_MAX_BULK_LENGTH = 2500500;

    private Client client;
    private Node node;
    private String defaultIndex;

    private Configuration config;

    private BulkRequestBuilder bulk;
    private ListenableActionFuture<BulkResponse> execute;
    private int port = -1;
    private String host = null;
    private String clusterName = null;
    private int maxBulkDocs;
    private int maxBulkLength;
    private long indexedDocs = 0;
    private int bulkDocs = 0;
    private int bulkLength = 0;
    private boolean createNewBulk = false;
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void open(Configuration job) throws IOException {
        LOG.info("BeehyvIndexWriter: open");
        clusterName = job.get(BeehyvElasticConstants.CLUSTER);
        host = job.get(BeehyvElasticConstants.HOST);
        port = job.getInt(BeehyvElasticConstants.PORT, 9300);


        Builder settingsBuilder = Settings.settingsBuilder();

        BufferedReader reader = new BufferedReader(
                job.getConfResourceAsReader("elasticsearch.conf"));
        String line;
        String parts[];

        while ((line = reader.readLine()) != null) {
            if (StringUtils.isNotBlank(line) && !line.startsWith("#")) {
                line.trim();
                parts = line.split("=");

                if (parts.length == 2) {
                    settingsBuilder.put(parts[0].trim(), parts[1].trim());
                }
            }
        }

        if (StringUtils.isNotBlank(clusterName))
            settingsBuilder.put("cluster.name", clusterName);

        // Set the cluster name and build the settings
        Settings settings = settingsBuilder.build();

        // Prefer TransportClient
        if (host != null && port > 1) {
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
        } else if (clusterName != null) {
            node = nodeBuilder().settings(settings).client(true).node();
            client = node.client();
        }
        LOG.info("**************************");
        LOG.info("host:port:cluster" + host + ":" + port + ":" + clusterName);

        bulk = client.prepareBulk();
        defaultIndex = job.get(BeehyvElasticConstants.INDEX, "src/bin/nutch");
        LOG.info("defaultIndex" + defaultIndex);
        maxBulkDocs = job.getInt(BeehyvElasticConstants.MAX_BULK_DOCS,
                DEFAULT_MAX_BULK_DOCS);
        maxBulkLength = job.getInt(BeehyvElasticConstants.MAX_BULK_LENGTH,
                DEFAULT_MAX_BULK_LENGTH);
        LOG.info("Checking for Index");
        createIndexWithMappings(defaultIndex);
    }

    // todo: where should we check for index
    private void createIndexWithMappings(String index) throws IOException{
        IndicesAdminClient indicesAdminClient = client.admin().indices();
        boolean exists = indicesAdminClient
                .prepareExists(index)
                .get().isExists();
        if (!exists) {
            // create index
            indicesAdminClient.prepareCreate(index).get();
            // add mappings
            // not able to get from resources
            File mappingsJson = new File("/home/kapil/IdeaProjects/apache-nutch-2.3/src/plugin/indexer-beehyv/src/resources/mappings.json");
            Map<String, Object> map = mapper.readValue(
                    mappingsJson,
                    new TypeReference<Map<String, Object>>() {
                    });
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String type = entry.getKey();
                String source = null;
                try {
                    source = new JSONObject(entry.getValue().toString()).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                    LOG.info("Could not get json mappings for type: " + type);
                }
                indicesAdminClient.preparePutMapping(index)
                        .setType(type)
                        .setSource(source)
                        .get();
            }
            LOG.info("Created the Index with Mappings");
        }
    }

    private List<IndexRequestBuilder> insertAttributesAndSections(JSONDocumentContent content, String parentPageId) throws JsonMappingException, JsonGenerationException, IOException {
        List<IndexRequestBuilder> list = new ArrayList<IndexRequestBuilder>();
        List attributes = content.getAttributes();
        if (attributes != null) {
            Iterator sections = attributes.iterator();

            label52:
            while (true) {
                KeyValueRow currAttr;
                do {
                    if (!sections.hasNext()) {
                        LOG.debug("Inserted attributes for parent-id: " + parentPageId);
                        break label52;
                    }

                    currAttr = (KeyValueRow) sections.next();
                } while (TextUtils.isEmpty(currAttr.getKey()) && TextUtils.isEmpty(currAttr.getValue()));
                currAttr.setSourceUrl(content.getSourceURL());
                //this.client.insertElasticSearchDocWithParent("attribute", this.mapper.writeValueAsString(currAttr), parentPageId, "reliance");
                IndexRequestBuilder request = client.prepareIndex(defaultIndex, "attribute")
                        .setSource(this.mapper.writeValueAsString(currAttr)).setParent(parentPageId);

                list.add(request);
            }
        }

        List sections1 = content.getSections();
        if (sections1 != null) {
            Iterator currAttr1 = sections1.iterator();

            while (true) {
                Section currSec;
                ParaSection paraSection;
                label36:
                do {
                    while (currAttr1.hasNext()) {
                        currSec = (Section) currAttr1.next();
                        if (currSec instanceof ParaSection) {
                            paraSection = (ParaSection) currSec;
                            continue label36;
                        }

                        LOG.debug("ignored section which is not a para section");
                    }

                    LOG.debug("Inserted sections (paragraphs) for parent-id: " + parentPageId);
                    return list;
                } while (TextUtils.isEmpty(paraSection.getHeading()) && paraSection.getParagraphs() == null);
                currSec.setSourceUrl(content.getSourceURL());
                //this.client.insertElasticSearchDocWithParent("section", this.mapper.writeValueAsString(currSec), parentPageId, "reliance");
                IndexRequestBuilder request = client.prepareIndex(defaultIndex, "section")
                        .setSource(this.mapper.writeValueAsString(currSec)).setParent(parentPageId);
                list.add(request);
            }
        }
        return list;
    }

    @Override
    public void write(NutchDocument doc) throws IOException {
        LOG.info("BeehyvElasticIndexWriter : write");
        String id = (String) doc.getFieldValue("id");
        String type = doc.getDocumentMeta().get("type");
        if (type == null) type = "doc";
        IndexRequestBuilder request = client.prepareIndex(defaultIndex, type, id);

        Map<String, Object> source = new HashMap<String, Object>();

        // Loop through all fields of this doc
        for (String fieldName : doc.getFieldNames()) {
            if (doc.getFieldValues(fieldName).size() > 1) {
                source.put(fieldName, doc.getFieldValue(fieldName));
                // Loop through the values to keep track of the size of this document
                for (Object value : doc.getFieldValues(fieldName)) {
                    bulkLength += value.toString().length();
                }
            } else {
                if (fieldName.equals("json")) {
                    ObjectMapper mapper = new ObjectMapper();
                    //TODO deserialize and then put to ES index
                    String fieldValue = doc.getFieldValue(fieldName);

                    JSONDocumentContent content = (JSONDocumentContent) mapper.readValue(fieldValue, JSONDocumentContent.class);
                    List<IndexRequestBuilder> list = insertAttributesAndSections(content, id);
                    for (IndexRequestBuilder jsonrequest : list) {
                        // Add this indexing request to a bulk request
                        bulk.add(jsonrequest);
                        indexedDocs++;
                        bulkDocs++;
                    }
                } else {
                    source.put(fieldName, doc.getFieldValue(fieldName));
                }
                bulkLength += doc.getFieldValue(fieldName).toString().length();
            }
        }
        request.setSource(source);
        // Add this indexing request to a bulk request
        bulk.add(request);
        indexedDocs++;
        bulkDocs++;

        if (bulkDocs >= maxBulkDocs || bulkLength >= maxBulkLength) {
            LOG.info("Processing bulk request [docs = " + bulkDocs + ", length = "
                    + bulkLength + ", total docs = " + indexedDocs
                    + ", last doc in bulk = '" + id + "']");
            // Flush the bulk of indexing requests
            createNewBulk = true;
            commit();
        }
    }

    @Override
    public void delete(String key) throws IOException {
        try {
            DeleteRequestBuilder builder = client.prepareDelete();
            builder.setIndex(defaultIndex);
            builder.setType("doc");
            builder.setId(key);
            builder.execute().actionGet();
        } catch (ElasticsearchException e) {
            throw makeIOException(e);
        }
    }

    public static IOException makeIOException(ElasticsearchException e) {
        final IOException ioe = new IOException();
        ioe.initCause(e);
        return ioe;
    }

    @Override
    public void update(NutchDocument doc) throws IOException {
        write(doc);
    }

    @Override
    public void commit() throws IOException {
        LOG.info("start of commit" + execute);
        if (execute != null) {
            LOG.info("Execute is not null");
            // wait for previous to finish
            long beforeWait = System.currentTimeMillis();
            BulkResponse actionGet = execute.actionGet();
            if (actionGet.hasFailures()) {
                for (BulkItemResponse item : actionGet) {
                    if (item.isFailed()) {
                        throw new RuntimeException("First failure in bulk: "
                                + item.getFailureMessage());
                    }
                }
            }

            long msWaited = System.currentTimeMillis() - beforeWait;
            LOG.info("Previous took in ms " + actionGet.getTookInMillis()
                    + ", including wait " + msWaited);
            execute = null;
        }
        LOG.info("execute not null if block completed" + bulk);
        if (bulk != null) {
            LOG.info("no of bulk docs" + bulkDocs);
            if (bulkDocs > 0) {
                // start a flush, note that this is an asynchronous call
                execute = bulk.execute();
            }
            bulk = null;
        }
        LOG.info("b4 id create new bulk" + createNewBulk);
        if (createNewBulk) {
            // Prepare a new bulk request
            bulk = client.prepareBulk();
            bulkDocs = 0;
            bulkLength = 0;
        }
    }

    @Override
    public void close() throws IOException {
        // Flush pending requests
        LOG.info("Processing remaining requests [docs = " + bulkDocs
                + ", length = " + bulkLength + ", total docs = " + indexedDocs + "]");
        createNewBulk = false;
        commit();
        // flush one more time to finalize the last bulk
        LOG.info("Processing to finalize last execute");
        createNewBulk = false;
        commit();

        // Close
        client.close();
        if (node != null) {
            node.close();
        }
    }

    @Override
    public String describe() {
        StringBuffer sb = new StringBuffer("BeehyvIndexWriter\n");
        sb.append("\t").append(BeehyvElasticConstants.CLUSTER)
                .append(" : elastic prefix cluster\n");
        sb.append("\t").append(BeehyvElasticConstants.HOST).append(" : hostname\n");
        sb.append("\t").append(BeehyvElasticConstants.PORT)
                .append(" : port  (default 9300)\n");
        sb.append("\t").append(BeehyvElasticConstants.INDEX)
                .append(" : elastic index command \n");
        sb.append("\t").append(BeehyvElasticConstants.MAX_BULK_DOCS)
                .append(" : elastic bulk index doc counts. (default 250) \n");
        sb.append("\t").append(BeehyvElasticConstants.MAX_BULK_LENGTH)
                .append(" : elastic bulk index length. (default 2500500 ~2.5MB)\n");
        return sb.toString();
    }

    @Override
    public void setConf(Configuration conf) {
        config = conf;
        String cluster = conf.get(BeehyvElasticConstants.CLUSTER);
        String host = conf.get(BeehyvElasticConstants.HOST);

        if (StringUtils.isBlank(cluster) && StringUtils.isBlank(host)) {
            String message = "Missing elastic.cluster and elastic.host. At least one of them should be set in nutch-site.xml ";
            message += "\n" + describe();
            LOG.error(message);
            throw new RuntimeException(message);
        }
    }

    @Override
    public Configuration getConf() {
        return config;
    }
}
