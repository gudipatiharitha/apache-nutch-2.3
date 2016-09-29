package com.beehyv.nutch.parse;

import com.beehyv.nectar.extractor.HtmlExtractor;
import com.beehyv.nectar.models.DocumentContent;
import com.beehyv.nectar.models.PageTypeEnum;
import com.beehyv.nectar.models.json.JSONDocumentContent;
import com.beehyv.nectar.utils.DocumentContentMapper;
import com.beehyv.nectar.utils.PageTypeDeterminer;
import org.apache.geronimo.mail.util.StringBufferOutputStream;
import org.apache.nutch.parse.Parse;
import org.apache.nutch.parse.html.HtmlParser;
import org.apache.nutch.storage.WebPage;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by haritha on 20/7/16.
 */
public class BeehyvParser extends HtmlParser {

    public static final Logger LOG = LoggerFactory
            .getLogger("com.beehyv.nutch.parse");

    public Parse getParse(String url, WebPage page) {
        Parse parse = super.getParse(url, page);

        HtmlExtractor ext = new HtmlExtractor();
        ByteBuffer rawContent = page.getContent();
        // this logic (determining PageTypeEnum) is currently inside document-ingestor
        // and could/should be moved to beehyv-parse module instead
        PageTypeEnum pageType = PageTypeDeterminer.getPageType(url);
        DocumentContent content = ext.extract(new ByteArrayInputStream(rawContent.array()), pageType);
        content.setSourceURL(url);
        JSONDocumentContent docContent = DocumentContentMapper.getJSONMap(content);
        ObjectMapper mapper = new ObjectMapper();
        StringBuffer out = new StringBuffer("");

        try {
            mapper.writeValue(new StringBufferOutputStream(out), docContent);
        } catch(IOException e) {
            LOG.error(e.getMessage(),e);

        }
        page.getMetadata().put("json", ByteBuffer.wrap(out.toString().getBytes()));
        return parse;
    }
 }
