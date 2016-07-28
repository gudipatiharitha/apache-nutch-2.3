package com.beehyv.nutch.parse;

import com.beehyv.nectar.extractor.HtmlExtractor;
import com.beehyv.nectar.models.DocumentContent;
import com.beehyv.nectar.models.json.JSONDocumentContent;
import com.beehyv.nectar.utils.DocumentContentMapper;
import org.apache.geronimo.mail.util.StringBufferOutputStream;
import org.apache.nutch.parse.Parse;
import org.apache.nutch.parse.html.HtmlParser;
import org.apache.nutch.storage.WebPage;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;


/**
 * Created by haritha on 20/7/16.
 */
public class BeehyvParser extends HtmlParser {

    public static final Logger LOG = LoggerFactory
            .getLogger("com.beehyv.nutch.parse");

    public Parse getParse(String url, WebPage page) {
        Parse parse = super.getParse(url,page);

        //TODO call boiler pipe here and get the JSON
        HtmlExtractor ext = new HtmlExtractor();
        DocumentContent content = ext.extractUrl(page.getBaseUrl().toString());
        JSONDocumentContent docContent = DocumentContentMapper.getJSONMap(content);
        ObjectMapper mapper = new ObjectMapper();
        StringBuffer out = new StringBuffer("");


//        page.setType("tenant_product_page");
//        page.setTenant(tenant);
//        page.setProductId(productId);
//        page.setProductTypeId(productType.getName());
//        page.setSourceUrl(content.getSourceURL());
/*
        String tenant = "";
        String type = "";
        String productId = "";
        String productTypeId = "";
        String sourceUrl = "";

        page.getMetadata().put("type", ByteBuffer.wrap(type.getBytes()));
        page.getMetadata().put("tenant", ByteBuffer.wrap(tenant.getBytes()));
        page.getMetadata().put("productId", ByteBuffer.wrap(productId.getBytes()));
        page.getMetadata().put("productTypeId", ByteBuffer.wrap(productTypeId.getBytes()));
        page.getMetadata().put("sourceUrl", ByteBuffer.wrap(sourceUrl.getBytes()));*/

        try {
            mapper.writeValue(new StringBufferOutputStream(out), docContent);
        } catch(IOException e) {
            LOG.error(e.getMessage(),e);

        }
        page.getMetadata().put("json", ByteBuffer.wrap(out.toString().getBytes()));
        return parse;
    }
 }
