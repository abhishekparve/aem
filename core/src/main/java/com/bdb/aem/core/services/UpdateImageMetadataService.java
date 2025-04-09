package com.bdb.aem.core.services;

import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.search.result.SearchResult;

/**
 * The Interface ProductImageProcessingService.
 */
public interface UpdateImageMetadataService {

	public void updateFlatFileImageMetadata(ResourceResolver resourceResolver, Session session, Resource imageResource, SearchResult result);
	
	public SearchResult getMatchingFlatFileImageNode(ResourceResolver resourceResolver, String payload, SearchResult result);
}
