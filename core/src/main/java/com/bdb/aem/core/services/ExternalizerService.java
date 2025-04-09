package com.bdb.aem.core.services;

import org.apache.sling.api.resource.ResourceResolver;

public interface ExternalizerService {

	/**
	 * Method returns Externalized/Formatted url
	 */
	String getFormattedUrl(String url, ResourceResolver resourceResolver);
	String getFormattedUrlForPublish(String url, ResourceResolver resourceResolver);
	String externalizedUrl(String url, ResourceResolver resourceResolver);
}
