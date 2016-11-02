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

import com.beehyv.holmes.EsUtil;
import com.beehyv.holmes.GenericDocumentInsertor;
import com.beehyv.nectar.models.information.InfoNode;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.indexer.IndexWriter;
import org.apache.nutch.indexer.NutchDocument;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

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
        defaultIndex = job.get(BeehyvElasticConstants.INDEX, "elastic.index");
        LOG.info("defaultIndex" + defaultIndex);
        maxBulkDocs = job.getInt(BeehyvElasticConstants.MAX_BULK_DOCS,
                DEFAULT_MAX_BULK_DOCS);
        maxBulkLength = job.getInt(BeehyvElasticConstants.MAX_BULK_LENGTH,
                DEFAULT_MAX_BULK_LENGTH);
        LOG.info("Checking for Index");
        if (!EsUtil.checkIfIndexExists(client, defaultIndex)) {
            EsUtil.createIndexWithSettingsAndMappings(client, defaultIndex, EsUtil.ANALYZER_SETTINGS, EsUtil.getMappings());
            LOG.info("Created the Index with Mappings");
        }
    }

    @Override
    public void write(NutchDocument doc) throws IOException {
        LOG.info("BeehyvElasticIndexWriter : write");
//        Metadata metadata = doc.getDocumentMeta();

        // first add index request for the parent node with ID as it source Url (something unique)
        // TODO: do we want to change this ID to something else bases on tenant id
        // TODO: int tenantId = doc.getDocumentMeta().get("tenantId"); and then
        // TODO: based on this tenant id we can set the unique id for the parent node
//        String id = metadata.get(BeehyvElasticConstants.FLD_SOURCE_URL);
        String jsonContent = doc.getFieldValue("json");
        InfoNode content = mapper.readValue(jsonContent, InfoNode.class);
        String id = content.getMetadata().get(BeehyvElasticConstants.FLD_SOURCE_URL);
        GenericDocumentInsertor documentInsertor = new GenericDocumentInsertor(client);
        for (IndexRequestBuilder indexingRequest: documentInsertor.insertGenericDocuments(defaultIndex, id, content)) {
            bulk.add(indexingRequest);
            indexedDocs++;
            bulkDocs++;
        }
//        Map<String, String> esMetadata = parentNode.getMetadata();
//        // Loop through all the metadata fields of this doc and
//        // add more metadata that was fetched during parse
//        for (String meta: metadata.names()) {
//            esMetadata.put(meta, metadata.get(meta));
//            bulkLength += metadata.get(meta).length();
//        }
//        parentNodeRequest.setSource(documentNode);
//        bulk.add(parentNodeRequest);
        bulkLength += jsonContent.length();

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
