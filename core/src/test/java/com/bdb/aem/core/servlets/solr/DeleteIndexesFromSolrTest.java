package com.bdb.aem.core.servlets.solr;

import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.google.gson.JsonObject;

/**
 * @author knarayansingh
 *
 */
@ExtendWith(MockitoExtension.class)
public class DeleteIndexesFromSolrTest {
	/**
	 * DeleteIndexesFromSolr Object.
	 */
	@InjectMocks
	DeleteIndexesFromSolr deleteIndexesFromSolr;

	/**
	 * The BDBSearchEndpointService
	 */
	@Mock
	BDBSearchEndpointService solrConfig;

	/**
	 * The SolrSearchService
	 */
	@Mock
	SolrSearchService SolrSearchService;

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
	
	/** The solr clients. */
	@Mock
	ArrayList<HttpSolrClient> solrClients;
	
	/** The solr client. */
	@Mock
	HttpSolrClient solrClient;
	
	/** The reader. */
	@Mock
	BufferedReader reader;
	
	/** The print writer. */
	@Mock
	PrintWriter printWriter;
	
	/** The request 1. */
	private String request1;

	/** The request 2. */
	private String request2;
	
	/**
	 * Sets the up.
	 * @throws IOException 
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setUp() throws IOException {
		solrClients = new ArrayList<>();
		solrClients.add(solrClient);
		when(request.getReader()).thenReturn(reader);
		when(reader.readLine()).thenReturn("web");
		when(response.getWriter()).thenReturn(printWriter);
	}

	/**
	 * @throws ServletException
	 * @throws IOException
	 * @throws LoginException 
	 */
	@Test
	public void testDoPost() throws ServletException, IOException, LoginException {
		when(SolrSearchService.getAllSolrClients()).thenReturn(solrClients);
		when(response.getWriter()).thenReturn(writer);
		deleteIndexesFromSolr.doPost(request, response);
	}
	
	/**
	 * Test do post login exception.
	 *
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws LoginException the login exception
	 */
	@Test
	public void testDoPostLoginException() throws ServletException, IOException, LoginException {
		when(SolrSearchService.getAllSolrClients()).thenThrow(new LoginException());
		deleteIndexesFromSolr.doPost(request, response);
	}
}
