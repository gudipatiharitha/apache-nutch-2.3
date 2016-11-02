package com.beehyv.nutch.parse;

import com.beehyv.holmes.enums.PageTypeEnum;
import com.beehyv.nectar.extractor.AbstractExtractor;
import com.beehyv.nectar.extractor.DocExtractor;
import com.beehyv.nectar.extractor.DocxExtractor;
import com.beehyv.nectar.models.information.InfoNode;
import org.apache.geronimo.mail.util.StringBufferOutputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.parse.*;
import org.apache.nutch.storage.ParseStatus;
import org.apache.nutch.storage.WebPage;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by kapil on 26/9/16.
 */
public class BeehyvDocxParser implements Parser {

    public static final Logger LOG = LoggerFactory.getLogger(BeehyvDocxParser.class);

    private static Collection<WebPage.Field> FIELDS = new HashSet<WebPage.Field>();

    static {
        FIELDS.add(WebPage.Field.BASE_URL);
        FIELDS.add(WebPage.Field.CONTENT_TYPE);
    }

    private Configuration conf;
    private final String DOC_TYPE = "application/msword";
    private final String DOCX_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    @Override
    public Parse getParse(String url, WebPage page) {
        String mimeType = page.getContentType().toString();
        LOG.debug("mimetype is: " + mimeType);

        ByteBuffer raw = page.getContent();
        ByteArrayInputStream bis = new ByteArrayInputStream(raw.array());
        InfoNode documentContent = null;
        AbstractExtractor extractor = null;

        switch (mimeType) {
            case DOC_TYPE:
                // uses docExtractor
                extractor = new DocExtractor();
                break;
            case DOCX_TYPE:
                // uses docxExtractor
                extractor = new DocxExtractor();
                break;
            default:
                extractor = new DocxExtractor();
                break;
        }

        documentContent = extractor.extract(bis, PageTypeEnum.DEFAULT);

        ObjectMapper mapper = new ObjectMapper();
        StringBuffer out = new StringBuffer("");
        try {
            mapper.writeValue(new StringBufferOutputStream(out), documentContent);
        } catch(IOException e) {
            LOG.error(e.getMessage(),e);
        }
        page.getMetadata().put("json", ByteBuffer.wrap(out.toString().getBytes()));
        // we do not want any outlinks
        Outlink[] outlinks = new Outlink[0];
//        if (!TextUtils.isEmpty(documentContent.getRawContent()))
//            outlinks = OutlinkExtractor.getOutlinks(documentContent.getRawContent(), getConf());
//        else outlinks = new Outlink[0];
        ParseStatus status = ParseStatusUtils.STATUS_SUCCESS;
        return new Parse(documentContent.getContent(), documentContent.getMetadata().get("title"), outlinks, status);
    }

    @Override
    public void setConf(Configuration configuration) {
        this.conf = configuration;
    }

    @Override
    public Configuration getConf() {
        return this.conf;
    }

    @Override
    public Collection<WebPage.Field> getFields() {
        return FIELDS;
    }
}
