package com.beehyv.nutch.parse;

import com.beehyv.nectar.models.DocumentContent;
import com.beehyv.nectar.models.Paragraph;
import org.apache.avro.util.Utf8;
import org.apache.commons.io.FileUtils;
import org.apache.geronimo.mail.util.StringBufferOutputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.http.util.TextUtils;
import org.apache.nutch.metadata.Metadata;
import org.apache.nutch.parse.*;
import org.apache.nutch.storage.ParseStatus;
import org.apache.nutch.storage.WebPage;
import org.apache.nutch.util.Bytes;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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
        String mimeType = page.getContentType().toString();
        LOG.debug("mimetype is: " + mimeType);
        Metadata metadata = new Metadata();
        metadata.set(Metadata.CONTENT_TYPE, mimeType);
        ByteBuffer raw = page.getContent();
        PDDocument pdDoc;
        COSDocument cosDoc;

        PDFParser parser;
        PDFTextStripper pdfStripper;
        String pdfText, title;
        Parse parse = null;

        try {
            // todo: use PdfExtractor class here
            parser = new PDFParser(new RandomAccessFile(bytes2file(raw.array()), "r"));
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            // let's extract the entire text first
            // optional setting to add formatting to the output text
            pdfStripper.setAddMoreFormatting(true);

            pdfStripper.setStartPage(0);
            pdfStripper.setEndPage(pdDoc.getNumberOfPages());

            pdfText = pdfStripper.getText(pdDoc);
            // do we really need the outlinks here?
            // will this outlink extractor work for pdfs?
            Outlink[] outlinks = OutlinkExtractor.getOutlinks(pdfText, getConf());
            // collect title
            PDDocumentInformation info = pdDoc.getDocumentInformation();
            title = info.getTitle();
            // more useful info, currently not used. please keep them for future use.
            metadata.add(Metadata.DESCRIPTION, "No of pages: " + String.valueOf(pdDoc.getNumberOfPages()));
            metadata.add(Metadata.FEED_AUTHOR, info.getAuthor());
            metadata.add(Metadata.CREATOR, info.getCreator());
            metadata.add(Metadata.PUBLISHER, info.getProducer());

            List<Paragraph> paras = getParas(pdfText);
            DocumentContent documentContent = new DocumentContent();
            documentContent.setNoOfPages(pdDoc.getNumberOfPages());
            documentContent.setTitle(pdDoc.getDocumentInformation().getTitle());
            documentContent.setSourceURL(url);
            documentContent.setParagraphs(paras);

            pdDoc.close();
            ParseStatus status = ParseStatusUtils.STATUS_SUCCESS;
            parse = new Parse(pdfText, title, outlinks, status);
            // populate Nutch metadata with our gathered metadata
            String[] pdfMDNames = metadata.names();
            for (String pdfMDName : pdfMDNames) {
                if (!TextUtils.isEmpty(metadata.get(pdfMDName)))
                page.getMetadata().put(new Utf8(pdfMDName),
                        ByteBuffer.wrap(Bytes.toBytes(metadata.get(pdfMDName))));
            }
            StringBuffer out = new StringBuffer("");
            mapper.writeValue(new StringBufferOutputStream(out), documentContent);
            page.getMetadata().put("json", ByteBuffer.wrap(out.toString().getBytes()));
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }

        return parse;
    }

    protected List<Paragraph> getParas(String text) {
        List<Paragraph> paragraphs = new ArrayList<>();
        // we assume that paragraphs are separated by 2 newlines
        String[] paras = text.split("\\n\\n");
        for (String content: paras) {
            Paragraph paragraph = new Paragraph();
            paragraph.setContent(content);
            paragraphs.add(paragraph);
        }
        return paragraphs;
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

    private static File bytes2file(byte[] input) throws IOException {
        final String PREFIX = "bytes2file";
        final String SUFFIX = ".tmp";
        final File tempFile = File.createTempFile(PREFIX, SUFFIX);
        tempFile.deleteOnExit();
        FileUtils.writeByteArrayToFile(tempFile, input);
        return tempFile;
    }

}
