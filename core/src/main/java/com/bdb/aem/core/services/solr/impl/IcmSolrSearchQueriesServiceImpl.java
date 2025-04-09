package com.bdb.aem.core.services.solr.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import com.bdb.aem.core.services.BDBApiEndpointService;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.solr.IcmSolrSearchQueriesService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Component(service = IcmSolrSearchQueriesService.class, immediate = true)
public class IcmSolrSearchQueriesServiceImpl implements IcmSolrSearchQueriesService {
	private static final Logger LOG = LoggerFactory.getLogger(IcmSolrSearchQueriesServiceImpl.class);
	JsonObject response = new JsonObject();
	
	/** The bdbApiEndpointService. */
	@Reference
	private BDBApiEndpointService bdbApiEndpointService;
	
	/** The solr config. */
	@Reference
	BDBSearchEndpointService solrConfig;

	/**
     * 
     * @param country
     * @param solrConfig
     * @param solrSearchService
     * @param cellType
     * @param cellID
	 * @return 
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
	@Override
	public  JsonArray getCellsData(String country, BDBSearchEndpointService solrConfig, SolrSearchService solrSearchService, String reactivity, String cellID)
			throws IOException, SolrServerException {
		JsonArray cellsList = null;
		HttpSolrClient server = solrSearchService.solrClient(country);
		if(null!=reactivity || null!=cellID)
		{
			QueryResponse sitesQueryResponse = server.query(fetchCellDataQuery(reactivity, cellID));
			SolrDocumentList sitesSolrDocs = sitesQueryResponse.getResults();
			cellsList = getResultsJson(sitesSolrDocs);
		}
		return cellsList;
	}
	
	private JsonArray getResultsJson(SolrDocumentList sitesSolrDocs) {
		Iterator<SolrDocument> iterator = sitesSolrDocs.iterator();
		JsonArray results = new JsonArray();
		while (iterator.hasNext()) {
			SolrDocument solrDocument = iterator.next();
			Iterator<Map.Entry<String, Object>> itr = solrDocument.iterator();
			JsonObject facetObject = new JsonObject();
			while (itr.hasNext()) {
				Map.Entry<String, Object> map = itr.next();
				facetObject.add(map.getKey(), new Gson().toJsonTree(map.getValue()));
			}

			results.add(facetObject);
		}

		return results;
	}
	/**
	 * 
	 * @param cellType
	 * @param cellID
	 * @return
	 */
	private SolrQuery fetchCellDataQuery(String reactivity, String cellID) {
		LOG.debug(":: Fetching the Cell Data from Solr ::");
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.addFilterQuery("doctype:\""+"cellType\"");
		solrQuery.addFilterQuery("reactivity:"+reactivity);
		if(StringUtils.isNotEmpty(cellID)) 
			solrQuery.addFilterQuery("cellId:("+cellID+")");
		solrQuery.setQuery("*:*");
		solrQuery.setRows(solrConfig.cellTypeQueryLimit());
		solrQuery.setStart(0);
		LOG.debug("full Solr Query :: {}",solrQuery);
		return solrQuery;
	}

}
