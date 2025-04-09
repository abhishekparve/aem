package com.bdb.aem.core.services.solr.impl;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import org.apache.sling.settings.SlingSettingsService;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

import javax.jcr.RepositoryException;

import static org.mockito.Mockito.*;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class FetchingCloneServiceImplTest {
	@InjectMocks
	FetchingCloneServiceImpl fetchingCloneServiceImpl;
	@Mock
	SolrSearchService solrSearchService;
	@Mock
	BDBSearchEndpointService solrConfig;
	@Mock
	HttpSolrClient server;
	@Mock
	QueryResponse solrQueryResponse;
	@Mock
	SolrDocument solrDocument;
	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	@BeforeEach
	void setUp() {

	}

	@Test
	void testClonesData() throws IOException, SolrServerException {
		SolrDocumentList docList = new SolrDocumentList();
		List<SolrDocument> solrList = new ArrayList<>();
		LinkedHashMap<String, Object> m = new LinkedHashMap<String, Object>();
		String[] specificity = { "TCR VÎ±7.2" };
		m.put("specificity", specificity);
		String[] cloneName = { "cloneName" };
		m.put("tdsCloneName", cloneName);
		String[] dyeName = { "BV421" };
		m.put("dyeName", dyeName);
		m.put("id", "/content/bd-aem");

		solrList.add(solrDocument);
		SolrDocument solrDoc = new SolrDocument(m);
		docList.add(solrDoc);
		lenient().when(solrSearchService.solrClient("us")).thenReturn(server);
		lenient().when(server.query(Mockito.any())).thenReturn(solrQueryResponse);
		when(solrQueryResponse.getResults()).thenReturn(docList);
		when(bdbApiEndpointService.getCustomRunMode()).thenReturn("author");
		fetchingCloneServiceImpl.getClonesData("us",solrConfig,solrSearchService,"TCR VÎ±7.2","BV421","cloneName");
	}

	@Test
	void testFormatsData() throws IOException, SolrServerException, RepositoryException {
		SolrDocumentList docList = new SolrDocumentList();
		List<SolrDocument> solrList = new ArrayList<>();
		LinkedHashMap<String, Object> m = new LinkedHashMap<String, Object>();
		String[] specificity = { "TCR VÎ±7.2" };
		m.put("specificity", specificity);
		String[] cloneName = { "cloneName" };
		m.put("tdsCloneName", cloneName);
		String[] dyeName = { "BV421" };
		m.put("dyeName", dyeName);
		m.put("id", "/content/bd-aem");

		solrList.add(solrDocument);
		SolrDocument solrDoc = new SolrDocument(m);
		docList.add(solrDoc);
		lenient().when(solrSearchService.solrClient("us")).thenReturn(server);
		lenient().when(server.query(Mockito.any())).thenReturn(solrQueryResponse);
		when(solrQueryResponse.getResults()).thenReturn(docList);
		when(bdbApiEndpointService.getCustomRunMode()).thenReturn("author");
		fetchingCloneServiceImpl.getFormatsData("us",solrConfig,solrSearchService,"BV421","cloneName");
	}
	
	@Test
	void testSpecificityData() throws IOException, SolrServerException, RepositoryException {
		SolrDocumentList docList = new SolrDocumentList();
		List<SolrDocument> solrList = new ArrayList<>();
		LinkedHashMap<String, Object> m = new LinkedHashMap<String, Object>();
		String[] specificity = { "TCR VÎ±7.2" };
		m.put("specificity", specificity);
		String[] cloneName = { "cloneName" };
		m.put("tdsCloneName", cloneName);
		String[] dyeName = { "BV421" };
		m.put("dyeName", dyeName);
		m.put("id", "/content/bd-aem");

		solrList.add(solrDocument);
		SolrDocument solrDoc = new SolrDocument(m);
		docList.add(solrDoc);
		lenient().when(solrSearchService.solrClient("us")).thenReturn(server);
		lenient().when(server.query(Mockito.any())).thenReturn(solrQueryResponse);
		when(solrQueryResponse.getResults()).thenReturn(docList);
		when(bdbApiEndpointService.getCustomRunMode()).thenReturn("author");
		fetchingCloneServiceImpl.getSpecificityData("us",solrConfig,solrSearchService,"BV421","TCR VÎ±7.2");
	}

}
