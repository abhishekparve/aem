package com.bdb.aem.core.services.solr.impl;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.solr.SolrSearchService;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class GetDocumentsServiceImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class GetDocumentsServiceImplTest {
	
	/** The get documents service impl. */
	@InjectMocks
	GetDocumentsServiceImpl getDocumentsServiceImpl;
	
	/** The solr search service. */
	@Mock
	SolrSearchService solrSearchService;
	
	/** The solr config. */
	@Mock
	BDBSearchEndpointService solrConfig;
	
	/** The server. */
	@Mock
	HttpSolrClient server;
	
	/** The solr query response. */
	@Mock
	QueryResponse solrQueryResponse;
	
	/** The solr document. */
	@Mock
	SolrDocument solrDocument;
	
	/**
	 * Sets the up.
	 */
	@BeforeEach
	void setUp() {

	}
	
	/**
	 * Test clones data.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SolrServerException the solr server exception
	 */
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
		getDocumentsServiceImpl.getDocumentsByProductNameAndType("749494_base", "type", "us", solrSearchService);
	}

}
