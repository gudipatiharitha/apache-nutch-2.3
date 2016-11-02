package com.beehyv.nutch.parse.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Based on the url regex, we will get the properties to be fetched
 * along with their values
 *
 * Created by kapil on 26/10/16.
 */
public class RuleFilter {

    private static List<IngestionRuleModel> rulesList;

    /**
     * Returns a list of rules to be used for the current url
     * @param url Url for which rules have to be fetched
     * @return list of rules that will be used to fetch properties for this url
     */
    public List<IngestionRuleModel> filterRules(String url) {
        if(rulesList == null) {
            rulesList = new CsvRuleBuilder().buildRules();
        }
        return filterRules(url, rulesList);
    }

    private List<IngestionRuleModel> filterRules(String url, List<IngestionRuleModel> allRulesList) {
        List<IngestionRuleModel> ruleModelList = new ArrayList<>();

        for (IngestionRuleModel ruleModel: allRulesList) {
            if (matchUrlPattern(ruleModel.getUrlRegex(), url)) {
                ruleModelList.add(ruleModel);
            }
        }
        return ruleModelList;
    }

    private boolean matchUrlPattern(String regex, String url) {
        // Create a Pattern object
        Pattern r = Pattern.compile(regex);

        // Now create matcher object.
        Matcher m = r.matcher(url);
        return m.matches();
    }
}
