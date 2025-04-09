package com.bdb.aem.core.services.solr.impl;

import static org.mockito.Mockito.lenient;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.solr.SolrSearchService;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class IcmSolrSearchQueriesServiceImplTest {

	@InjectMocks
	IcmSolrSearchQueriesServiceImpl icmSolrSearchQueriesServiceImpl;

	@Mock
	SolrSearchService solrSearchService;

	@Mock
	BDBSearchEndpointService solrConfig;

	@Mock
	HttpSolrClient httpSolrClient;
	
	@Mock
	QueryResponse queryResponse;
	
	@Mock
	SolrDocumentList solrDocumentList;
	
	@Mock
	SolrDocument solrDocument;
	
	@Mock
	Iterator<SolrDocument> solrDocumentItr;
	
	@Mock
	Iterator<Entry<String, Object>> solrDocItr;
	
	@Mock
	Entry<String, Object> map;
	
	@Mock
	SolrQuery query;

	@Mock
	ResourceResolver resourceResolver;

	@Test
	void testICMSolrSearchQuery() throws IOException, SolrServerException {
		
		lenient().when(solrConfig.cellTypeQueryLimit()).thenReturn(10);
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.addFilterQuery("doctype:\""+"cellType\"");
		solrQuery.addFilterQuery("cellType:("+"cellType"+")");
		solrQuery.setQuery("*:*");
		solrQuery.setRows(solrConfig.cellTypeQueryLimit());
		solrQuery.setStart(0);
		
		lenient().when(solrSearchService.solrClient("us")).thenReturn(httpSolrClient);
		lenient().when(httpSolrClient.query(Mockito.any())).thenReturn(queryResponse);
		lenient().when(queryResponse.getResults()).thenReturn(solrDocumentList);
		solrDocumentList.add(solrDocument);
		lenient().when(solrDocumentList.iterator()).thenReturn(solrDocumentItr);
		lenient().when(solrDocumentItr.hasNext()).thenReturn(true).thenReturn(false);
		lenient().when(solrDocumentItr.next()).thenReturn(solrDocument);
		
		lenient().when(solrDocument.iterator()).thenReturn(solrDocItr);
		lenient().when(solrDocItr.hasNext()).thenReturn(true).thenReturn(false);
		lenient().when(solrDocItr.next()).thenReturn(map);
		lenient().when(map.getKey()).thenReturn("key");
		lenient().when(map.getValue()).thenReturn("value");
		
		icmSolrSearchQueriesServiceImpl.getCellsData("us", solrConfig, solrSearchService, "cellType", "cellId");
		
	}

}
