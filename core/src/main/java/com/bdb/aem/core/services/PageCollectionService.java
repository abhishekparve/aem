package com.bdb.aem.core.services;

import java.util.List;

import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;

public interface PageCollectionService {
	
	List<String> getReplicationPages(String resourcePath,ResourceResolver resourceResolver);

	List<String> listPagesUsingVariation(String variationPath, ResourceResolver resourceResolver);

	List<String> getPagesFromTemplateList(List<String> templatePathList, Session session);

	List<String> getPagesFromTemplateUsingXFVariation(String variationPath,
			ResourceResolver resourceResolver);

	List<String> getPagesFromContentUsingXFVariation(String variationPath,
			ResourceResolver resourceResolver);
	
	
	List<String> getShortUrls(List<String> pageList,ResourceResolver resourceResolver, boolean includeTrailingSlash, boolean includeDomain);

}
