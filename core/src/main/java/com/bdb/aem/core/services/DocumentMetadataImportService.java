package com.bdb.aem.core.services;

import org.apache.sling.api.resource.ResourceResolver;

public interface DocumentMetadataImportService {
	public void processPdfAssets(String payload, ResourceResolver serviceResolver);
}