package com.bdb.aem.core.services.solr;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.solr.client.solrj.SolrServerException;

import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.google.gson.JsonArray;

import java.io.IOException;

import javax.jcr.RepositoryException;

public interface FetchingToolsService {
    /**
     *
     * @param baseId
     * @param solrConfig
     * @param solrSearchService
     * @param panelTypeId 
     * @param type 
     * @param catalogStructureUpdateService 
     * @param resourceResolver 
     * @param reactivity 
     * @return Related Clones Details
     * @throws IOException
     * @throws SolrServerException
     * @throws RepositoryException 
     */
	 String getCellTypeData(String country, SolrSearchService solrSearchService, JsonArray requestCellType, String type, CatalogStructureUpdateService catalogStructureUpdateService, ResourceResolver resourceResolver, String reactivity) throws IOException, SolrServerException, RepositoryException;

}
