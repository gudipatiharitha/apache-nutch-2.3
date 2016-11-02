package com.beehyv.nutch.parse;

import com.beehyv.holmes.enums.PageTypeEnum;
import com.beehyv.nectar.extractor.HtmlExtractor;
import com.beehyv.nectar.models.information.InfoNode;
import com.beehyv.nutch.parse.rules.IngestionRuleModel;
import com.beehyv.nutch.parse.rules.PropertyValueParser;
import com.beehyv.nutch.parse.rules.RuleConstants;
import com.beehyv.nutch.parse.rules.RuleFilter;
import org.apache.geronimo.mail.util.StringBufferOutputStream;
import org.apache.nutch.parse.Outlink;
import org.apache.nutch.parse.Parse;
import org.apache.nutch.parse.html.HtmlParser;
import org.apache.nutch.storage.WebPage;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haritha on 20/7/16.
 */
public class BeehyvParser extends HtmlParser {

    public static final Logger LOG = LoggerFactory
            .getLogger("com.beehyv.nutch.parse");

    private RuleFilter ruleFilter = new RuleFilter();

    @Override
    public Parse getParse(String url, WebPage page) {
        Parse parse = super.getParse(url, page);
        // we dont want any outlinks
        parse.setOutlinks(new Outlink[0]);

        Map<String, String> metadata = new HashMap<>();
        List<IngestionRuleModel> ingestionRuleModelList = ruleFilter.filterRules(url);
        PageTypeEnum pageType = PageTypeEnum.DEFAULT;
        int domainId = ingestionRuleModelList.get(0).getDomainId();
        int tenantId = ingestionRuleModelList.get(0).getTenantId();
        metadata.put(RuleConstants.SOURCE_URL, url);
        metadata.put(RuleConstants.DOMAIN_ID, String.valueOf(domainId));
        metadata.put(RuleConstants.TENANT_ID, String.valueOf(tenantId));
        page.getMetadata().put(RuleConstants.DOMAIN_ID, ByteBuffer.allocate(4).putInt(domainId));
        page.getMetadata().put(RuleConstants.TENANT_ID, ByteBuffer.allocate(4).putInt(tenantId));
        for (IngestionRuleModel ruleModel: ingestionRuleModelList) {
            if (ruleModel.getProperty().equals(RuleConstants.PAGE_TYPE_ENUM)) {
                pageType = PageTypeEnum.valueOf(PropertyValueParser.getPropertyValue(url, ruleModel.getValue(), ruleModel.getValueType()));
            } else {
                // properties example: productId and pagetype enum for ecomm
                page.getMetadata().put(ruleModel.getProperty(),
                        ByteBuffer.wrap(PropertyValueParser.getPropertyValue(url, ruleModel.getValue(), ruleModel.getValueType()).getBytes()));
            }
            metadata.put(ruleModel.getProperty(), PropertyValueParser.getPropertyValue(url, ruleModel.getValue(), ruleModel.getValueType()));
        }

        HtmlExtractor ext = new HtmlExtractor();
        ByteBuffer rawContent = page.getContent();

        InfoNode content = ext.extract(new ByteArrayInputStream(rawContent.array()), pageType);
        content.getMetadata().putAll(metadata);

        ObjectMapper mapper = new ObjectMapper();
        StringBuffer out = new StringBuffer("");

        try {
            mapper.writeValue(new StringBufferOutputStream(out), content);
        } catch(IOException e) {
            LOG.error(e.getMessage(),e);

        }
        page.getMetadata().put("json", ByteBuffer.wrap(out.toString().getBytes()));
        return parse;
    }

 }
