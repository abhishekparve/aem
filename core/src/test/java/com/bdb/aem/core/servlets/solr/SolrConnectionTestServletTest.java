package com.bdb.aem.core.servlets.solr;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bdb.aem.core.api.request.BaseRequest;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
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
import org.osgi.service.component.annotations.Reference;

import com.bdb.aem.core.api.client.RestClient;
import com.bdb.aem.core.api.response.BaseResponse;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;

@ExtendWith(MockitoExtension.class)
public class SolrConnectionTestServletTest {
	@InjectMocks
	SolrConnectionTestServlet solrConnectionTestServlet;

	/**
	 * The SolrSearchService
	 */
	@Mock
	SolrSearchService solrSearchService;

	@Mock
	ResourceResolver resourceResolver;

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
	@Mock
	Query query;
	@Mock
	QueryBuilder queryBuilder;
	/**
	 * The SolrQuery
	 */
	@Mock
	SolrQuery queryToBeExecuted;

	@Mock
	BaseRequest baseRequest;

	@Mock
	BaseResponse baseResponse;
	@Mock
	SolrDocument solrDocument;
	@Mock
	RestClient restClient;
	@Mock
	CloseableHttpClient httpClient;

	/**
	 * @throws IOException
	 * @throws SolrServerException
	 */
	// @Test
	public void testDoGet() throws IOException, AemInternalServerErrorException {
		final Map<String, String> requestHeaderMap = new HashMap<>();
		requestHeaderMap.put("key", "value");
		lenient().when(request.getParameter("case")).thenReturn("ip-with-proxy");
		lenient().when(request.getParameter("instance-id")).thenReturn("instance-id");
		lenient().when(restClient.sendRequest(baseRequest, requestHeaderMap)).thenReturn(baseResponse);
		lenient().when(baseResponse.hasError()).thenReturn(false);
		lenient().when(response.getWriter()).thenReturn(writer);
		solrConnectionTestServlet.doGet(request, response);
	}

	@Test
	public void testDoGetType() throws IOException, SolrServerException {

		lenient().when(restClient.getHttpClient()).thenReturn(httpClient);
		lenient().when(request.getParameter("case")).thenReturn("solr");
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		/*
		 * lenient().when(request.getParameter("case")).thenReturn("solr-with-proxy");
		 * lenient().when(solrSearchService.solrClient("us")).thenReturn(server);
		 * lenient().when(server.query(Mockito.any())).thenReturn(queryResponse);
		 */
		lenient().when(response.getWriter()).thenReturn(writer);
		solrConnectionTestServlet.doGet(request, response);
	}

}
