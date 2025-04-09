package com.bdb.aem.core.services;

import java.io.IOException;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.solr.client.solrj.SolrServerException;

import com.bdb.aem.core.services.solr.SolrSearchService;

public interface FetchRequiredCompanionProduct {

	String getRequriedCompanionProduct(String baseId, String country, BDBSearchEndpointService solrConfig,
			SolrSearchService solrSearchService, ResourceResolver resourceResolver, CatalogStructureUpdateService catalogStructureUpdateService) throws IOException, SolrServerException, RepositoryException;
}
