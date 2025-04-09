package com.bdb.aem.core.models;

/**
 * Href Tag Model
 */
public class HrefTagModel {
    /**
     * href Lang URL
     */
    private String hrefLangUrl;
    /**
     * href Lang Code
     */
    private String hrefLangCode;

    /**
     * Get the Href Lang URL
     * @return
     */
    public String getHrefLangUrl() {
    	if(hrefLangUrl.endsWith("home-page"))
    		hrefLangUrl = hrefLangUrl.substring(0, hrefLangUrl.lastIndexOf("/"));
        return hrefLangUrl;
    }

    /**
     * Set the Href Lang URL
     * @param hrefLangUrl
     */
    public void setHrefLangUrl(String hrefLangUrl) {
        this.hrefLangUrl = hrefLangUrl;
    }

    /**
     * Get Href Lang Code
     * @return
     */
    public String getHrefLangCode() {
        return hrefLangCode;
    }

    /**
     * Set the Href Lang COde
     * @param hrefLangCode
     */
    public void setHrefLangCode(String hrefLangCode) {
        this.hrefLangCode = hrefLangCode;
    }
}
