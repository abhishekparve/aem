package com.bdb.aem.core.servlets.solr;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.solr.SolrSearchService;

/**
 * @author knarayansingh
 *
 */
@ExtendWith(MockitoExtension.class)
public class PopularSearchedTest {

	/**
	 * PopularSearched Object.
	 */
	@InjectMocks
	PopularSearched popularSearched;

	/**
	 * The SolrSearchService
	 */
	@Mock
	SolrSearchService searchService;;

	/**
	 * The SlingHttpServletRequest
	 */
	@Mock
	SlingHttpServletRequest request;

	/**
	 * The SlingHttpServletResponse
	 */
	@Mock
	SlingHttpServletResponse response;

	/**
	 * The PrintWriter
	 */
	@Mock
	PrintWriter writer;

	/**
	 * The HttpSolrClient
	 */
	@Mock
	HttpSolrClient server;

	/**
	 * The QueryResponse
	 */
	@Mock
	QueryResponse queryResponse;

	/**
	 * The SolrQuery
	 */
	@Mock
	SolrQuery solrQuery;

	/**
	 * @throws IOException
	 * @throws SolrServerException
	 */
	@Test
	public void testDoGet() throws IOException, SolrServerException {
		when(searchService.solrClientPopSearch()).thenReturn(server);
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameter("country")).thenReturn("country");
		popularSearched.doGet(request, response);
	}

	/**
	 * @throws IOException
	 * @throws SolrServerException
	 */
	@Test
	public void testDoGetTestIOException() throws IOException, SolrServerException {
		when(searchService.solrClientPopSearch()).thenReturn(server);
		when(response.getWriter()).thenThrow(IOException.class);
		popularSearched.doGet(request, response);
	}

	/**
	 * @throws IOException
	 */
	@Test
	public void testDoGetSolrDown() throws IOException {
		when(searchService.solrClientPopSearch()).thenReturn(null);
		when(response.getWriter()).thenReturn(writer);
		popularSearched.doGet(request, response);
	}

}
