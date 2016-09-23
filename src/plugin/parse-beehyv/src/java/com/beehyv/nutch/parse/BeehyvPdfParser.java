package com.beehyv.nutch.parse;

import com.beehyv.nectar.models.DocumentContent;
import com.beehyv.nectar.models.Paragraph;
import org.apache.avro.util.Utf8;
import org.apache.commons.io.FileUtils;
import org.apache.geronimo.mail.util.StringBufferOutputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.http.util.TextUtils;
import org.apache.nutch.metadata.Metadata;
import org.apache.nutch.metadata.Nutch;
import org.apache.nutch.parse.*;
import org.apache.nutch.storage.ParseStatus;
import org.apache.nutch.storage.WebPage;
import org.apache.nutch.util.Bytes;
import org.apache.nutch.util.MimeUtil;
import org.apache.nutch.util.NutchConfiguration;
import org.apache.nutch.util.TableUtil;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.*;

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
    private String cachingPolicy;
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
        this.cachingPolicy = getConf().get("parser.caching.forbidden.policy",
                Nutch.CACHING_FORBIDDEN_CONTENT);
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

    /*public static void main(String[] args) throws Exception {
        String name = args[0];
        String url = "file:" + name;
        File file = new File(name);
        byte[] bytes = new byte[(int) file.length()];
        @SuppressWarnings("resource")
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        in.readFully(bytes);
        Configuration conf = NutchConfiguration.create();
        // TikaParser parser = new TikaParser();
        // parser.setConf(conf);
        WebPage page = WebPage.newBuilder().build();
        page.setBaseUrl(new Utf8(url));
        page.setContent(ByteBuffer.wrap(bytes));
        MimeUtil mimeutil = new MimeUtil(conf);
        String mtype = mimeutil.getMimeType(file);
        page.setContentType(new Utf8(mtype));
        // Parse parse = parser.getParse(url, page);

        Parse parse = new ParseUtil(conf).parse(url, page);

        System.out.println("content type: " + mtype);
        System.out.println("title: " + parse.getTitle());
        System.out.println("text: " + parse.getText());
        System.out.println("outlinks: " + Arrays.toString(parse.getOutlinks()));
    }*/
}
