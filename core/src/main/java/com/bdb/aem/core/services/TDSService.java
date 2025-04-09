package com.bdb.aem.core.services;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;

/**
 * 
 * @author phphanindra
 * @
 */
public interface TDSService  {

	/**
	 * 
	 * @param skuRes
	 * @param resourceResolver
	 * @param countryCode
	 * @return pdfRes
	 */
	public Resource getPublishedTdsPdf(Resource skuRes, ValueMap skuBaseVM, ResourceResolver resourceResolver, String countryCode, String catalogNumber);
	/**
	 * 
	 * @param skuRes
	 * @param resourceResolver
	 * @param countryCode
	 * @return pdfRes
	 */
	public List<Resource> getMultiLanguagePublishedTdsPdf(Resource skuRes, ValueMap skuBaseVM, ResourceResolver resourceResolver, String countryCode, String catalogNumber);
}

