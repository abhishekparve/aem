package com.bdb.aem.core.services;

import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;

/**
 * The Interface ProductImageProcessingService.
 */
public interface ProductImageProcessingService {

	public void processProductImages(ResourceResolver resourceResolver, Session session, String assetsPathListInDAM, String assetsReferencePathListInVar, String failedRecordsPathInVar, long retryLimit);
}
