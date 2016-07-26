package com.beehyv.nutch.parse;

import com.beehyv.holmes.ETenant;
import com.beehyv.holmes.Utils;
import org.apache.nutch.parse.Parse;
import org.apache.nutch.storage.WebPage;

/**
 * Created by haritha on 22/7/16.
 */
public class RelianceParser extends ProductParser {

    private ETenant tenant = ETenant.RELIANCE;
    public RelianceParser() {
        super();
        tenantName = tenant.getName();

    }


    public Parse getParse(String url, WebPage page) {
        productId = Utils.getProductIdFromUrl(url,tenant);
        type = ProductConstants.PAGE_TYPE_TENANT_PRODUCT;
        return super.getParse(url, page);
    }
}
