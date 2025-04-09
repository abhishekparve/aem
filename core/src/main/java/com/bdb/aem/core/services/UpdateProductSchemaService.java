package com.bdb.aem.core.services;

import java.io.IOException;
import java.util.List;

import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.solr.client.solrj.SolrServerException;

import com.bdb.aem.core.services.solr.SolrSearchService;
import com.day.cq.tagging.Tag;
import com.google.gson.JsonObject;

/**
 * The Interface UpdateProductSchemaService.
 */
public interface UpdateProductSchemaService {

	
	/**
	 * Gets the product announcement.
	 *
	 * @param marketingResource the marketing resource
	 * @param request the request
	 * @param productJson the product json
	 * @param resourceResolver the resource resolver
	 * @param currentPage 
	 * @param externalizerService 
	 * @param product 
	 * @return the product announcement
	 * @throws RepositoryException 
	 */
	public JsonObject getProductAnnouncement(Resource marketingResource, SlingHttpServletRequest request, JsonObject productJson,ResourceResolver resourceResolver, ExternalizerService externalizerService, String product, SolrSearchService solrSearchService) throws RepositoryException;
	
	
	/**
	 * Sets the product announce.
	 *
	 * @param request the request
	 * @param resourceResolver the resource resolver
	 * @param marketingResource the marketing resource
	 * @return the string
	 */
	public void setProductAnnounce(SlingHttpServletRequest request, ResourceResolver resourceResolver, Resource marketingResource);
	
	/**
	 * Gets the marketing resource.
	 *
	 * @param request the request
	 * @param resourceResolver the resource resolver
	 * @return the marketing resource
	 */
	public Resource getMarketingResource(SlingHttpServletRequest request, ResourceResolver resourceResolver, SolrSearchService solrSearchService);

	/**
	 * validate the date for product announcement
	 * @param startDate and endDate
	 * @return true if dates are valid
	 */
	public Boolean checkProductAnnouncementDateRange(String startDate, String endDate);

	/**
	 * Gets the tag.
	 *
	 * @param resourceResolver the resource resolver
	 * @param region the region
	 * @return the tag
	 */
	public Tag getTag(ResourceResolver resourceResolver, String region);


	public JsonObject getProductSKUDetails(Resource varientResource, SlingHttpServletRequest request,
			JsonObject productJson, ResourceResolver resourceResolver, ExternalizerService externalizerService,
			String product, SolrSearchService solrSearchService, Resource baseHpResource, String country) throws IOException, SolrServerException;


	public List<Resource> getEmeaRegionMarketingReources(SlingHttpServletRequest request,
			ResourceResolver resourceResolver, SolrSearchService solrSearchService);
}
