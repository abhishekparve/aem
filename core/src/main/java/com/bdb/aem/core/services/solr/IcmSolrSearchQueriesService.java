package com.bdb.aem.core.services.solr;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.google.gson.JsonArray;

import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

public interface IcmSolrSearchQueriesService {
    /**
     * 
     * @param country
     * @param solrConfig
     * @param solrSearchService
     * @param cellType
     * @param cellID
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
	JsonArray getCellsData(String country, BDBSearchEndpointService solrConfig, SolrSearchService solrSearchService, String cellType, String cellID) throws IOException, SolrServerException;

}