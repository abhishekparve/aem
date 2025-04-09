package com.bdb.aem.core.services;

import org.apache.sling.api.resource.ResourceResolver;

public interface CreateSynonymsService {
	
	public void createSynonyms(String filePath, ResourceResolver resolver);


	
}
