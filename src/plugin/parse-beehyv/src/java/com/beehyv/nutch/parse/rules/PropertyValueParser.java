package com.beehyv.nutch.parse.rules;

import com.beehyv.nectar.extractor.HtmlExtractor;
import org.jsoup.nodes.Document;

/**
 * Created by kapil on 1/11/16.
 *
 * Gets the value of a property based on the value type
 */
public class PropertyValueParser {

    /**
     * Returns string repr of value of a property
     * @param url url of this page
     * @param value how this value should be calculated
     * @param valueType type of this value
     * @return value
     */
    public static String getPropertyValue(String url, String value, String valueType) {
        String returnValue = "";
        switch (valueType) {
            case "selectorPath":
                // need Jsoup document here
                Document document = new HtmlExtractor().extractDoc(url);
                returnValue = document.select(value).attr("value");
                break;
            case "xPath":
                // convert to Jsoup query and execute
                break;
            case "text":
                // value is the returnValue
                returnValue = value;
                break;
        }
        return returnValue;

    }
}
