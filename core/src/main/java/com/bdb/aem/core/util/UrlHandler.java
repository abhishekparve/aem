package com.bdb.aem.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;

public class UrlHandler {
    /**
     *
     * @param resourceResolver 
     * @param url
     * @return the modified url based on either internal or external link
     */

    public static String getModifiedUrl(String url,ResourceResolver resourceResolver) {
        String modifiedUrl = StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(url) && null != resourceResolver) {
            if (url.startsWith(CommonConstants.CONTENT)) {
                modifiedUrl = resourceResolver.map(url).concat(CommonConstants.HTML);
            } else {
                modifiedUrl = url;
            }
        }
        return modifiedUrl;
    }
}
