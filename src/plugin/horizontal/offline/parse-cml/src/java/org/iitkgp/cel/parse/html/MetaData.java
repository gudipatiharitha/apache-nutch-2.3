/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iitkgp.cel.parse.html;

/**
 *
 * @author drrprasath
 */
public class MetaData {
    private String keywords = "";
    private String description = "";
    private String contype = "";
    private String charset = "";

    public MetaData() {
    }
    
    /**
     * @return the keywords
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * @param keywords the keywords to set
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the contype
     */
    public String getContype() {
        return contype;
    }

    /**
     * @param contype the contype to set
     */
    public void setContype(String contype) {
        this.contype = contype;
    }

    /**
     * @return the charset
     */
    public String getCharset() {
        return charset;
    }

    /**
     * @param charset the charset to set
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    
}
