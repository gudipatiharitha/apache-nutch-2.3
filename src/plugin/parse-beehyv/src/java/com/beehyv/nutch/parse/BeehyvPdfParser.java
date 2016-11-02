package com.beehyv.nutch.parse;

import com.beehyv.holmes.enums.PageTypeEnum;
import com.beehyv.nectar.extractor.PdfExtractor;
import com.beehyv.nectar.models.information.InfoNode;
import org.apache.avro.util.Utf8;
import org.apache.geronimo.mail.util.StringBufferOutputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.http.util.TextUtils;
import org.apache.nutch.metadata.Metadata;
import org.apache.nutch.parse.*;
import org.apache.nutch.storage.ParseStatus;
import org.apache.nutch.storage.WebPage;
import org.apache.nutch.util.Bytes;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by kapil on 23/9/16.
 */
public class BeehyvPdfParser implements Parser {

    public static final Logger LOG = LoggerFactory.getLogger(BeehyvPdfParser.class);

    private static Collection<WebPage.Field> FIELDS = new HashSet<WebPage.Field>();

    static {
        FIELDS.add(WebPage.Field.BASE_URL);
        FIELDS.add(WebPage.Field.CONTENT_TYPE);
    }

    private Configuration conf;
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Parse getParse(String url, WebPage page) {
        String pdfText, title;
        PdfExtractor extractor = PdfExtractor.getInstance(url);

        String mimeType = page.getContentType().toString();
        LOG.debug("mimetype is: " + mimeType);
        Metadata metadata = new Metadata();
        metadata.set(Metadata.CONTENT_TYPE, mimeType);

        ByteBuffer raw = page.getContent();
        ByteArrayInputStream bis = new ByteArrayInputStream(raw.array());
        InfoNode pdfDoc = extractor.extract(bis, PageTypeEnum.DEFAULT);

        pdfText = pdfDoc.getContent();
        title = pdfDoc.getMetadata().get("title");
        Outlink[] outlinks = OutlinkExtractor.getOutlinks(pdfText, getConf());
//        metadata.add(Metadata.DESCRIPTION, "No of pages: " + String.valueOf(pdfDoc.getNoOfPages()));
        metadata.add(Metadata.TITLE, title);

        ParseStatus status = ParseStatusUtils.STATUS_SUCCESS;
        Parse parse = new Parse(pdfText, title, outlinks, status);
        // populate Nutch metadata with our gathered metadata
        String[] pdfMDNames = metadata.names();
        for (String pdfMDName : pdfMDNames) {
            if (!TextUtils.isEmpty(metadata.get(pdfMDName)))
                page.getMetadata().put(new Utf8(pdfMDName),
                        ByteBuffer.wrap(Bytes.toBytes(metadata.get(pdfMDName))));
        }

        StringBuffer out = new StringBuffer("");
        try {
            mapper.writeValue(new StringBufferOutputStream(out), pdfDoc);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        page.getMetadata().put("json", ByteBuffer.wrap(out.toString().getBytes()));

        return parse;
    }

    @Override
    public void setConf(Configuration configuration) {
        this.conf = configuration;
    }

    @Override
    public Configuration getConf() {
        return conf;
    }

    @Override
    public Collection<WebPage.Field> getFields() {
        return FIELDS;
    }
}
