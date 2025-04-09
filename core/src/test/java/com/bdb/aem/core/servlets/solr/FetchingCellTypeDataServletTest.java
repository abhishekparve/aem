package com.bdb.aem.core.servlets.solr;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.RepositoryException;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.solr.FetchingToolsService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class FetchingCellTypeDataServletTest.
 */
@ExtendWith(MockitoExtension.class)
class FetchingCellTypeDataServletTest {

	/** The cell type data. */
	@InjectMocks
	FetchingCellTypeDataServlet cellTypeData;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The response. */
	@Mock
	SlingHttpServletResponse response;

	/** The writer. */
	@Mock
	PrintWriter writer;

	/** The exception. */
	@Mock
	SolrServerException exception;

	/** The fetching tools service. */
	@Mock
	FetchingToolsService fetchingToolsService;

	/** The solr search service. */
	@Mock
	SolrSearchService solrSearchService;

	/** The catalog structure update service. */
	@Mock
	CatalogStructureUpdateService catalogStructureUpdateService;

	/** The reader. */
	@Mock
	BufferedReader reader;

	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;
	/** Mock ResourceResolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The request object. */
	private JsonObject requestObject;

	/**
	 * Inits the.
	 *
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws LoginException the login exception
	 */
	@BeforeEach
	public void setup() throws IOException, LoginException {
		MockitoAnnotations.initMocks(this);
		when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		when(request.getReader()).thenReturn(reader);
		requestObject = new JsonObject();
		String line = "{\"country\":[\"US\"],\"subSetId\":[\"e2486fc7-bd53-40a8-9d39-1f8b3a6d5e00\",\"2f925af3-6a7c-41f1-822a-70d229f35b4d\",\"fa6ab341-28b6-4b69-8cec-faf8884391ed\"],\"reactivity\":\"Human\"}";
		String line2 = null;
		when(reader.readLine()).thenReturn(line, line2);
		when(response.getWriter()).thenReturn(writer);

	}

	/**
	 * Test do get.
	 *
	 * @throws IOException         Signals that an I/O exception has occurred.
	 * @throws SolrServerException the solr server exception
	 * @throws RepositoryException the repository exception
	 * @throws LoginException      the login exception
	 */
	@Test
	public void testDoGet() throws IOException, SolrServerException, RepositoryException, LoginException {
		assertNotNull(request);
		getCellTypeResponseData();
		cellTypeData.doGet(request, response);
	}

	/**
	 * Test do post.
	 *
	 * @throws IOException         Signals that an I/O exception has occurred.
	 * @throws LoginException      the login exception
	 * @throws SolrServerException the solr server exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	public void testDoPost() throws IOException, LoginException, SolrServerException, RepositoryException {
		assertNotNull(request);
		getCellTypeResponseData();
		cellTypeData.doPost(request, response);
	}

	/**
	 * Gets the response data.
	 *
	 * @return the response data
	 * @throws LoginException      the login exception
	 * @throws IOException         Signals that an I/O exception has occurred.
	 * @throws SolrServerException the solr server exception
	 * @throws RepositoryException the repository exception
	 */
	public void getCellTypeResponseData() throws LoginException, IOException, SolrServerException, RepositoryException {
		when(request.getParameter(CommonConstants.ICM_TYPE)).thenReturn("cellType");
		String jsonString = "[\"e2486fc7-bd53-40a8-9d39-1f8b3a6d5e00\",\"2f925af3-6a7c-41f1-822a-70d229f35b4d\",\"fa6ab341-28b6-4b69-8cec-faf8884391ed\"]";
		JsonArray subSetArray = new JsonParser().parse(jsonString).getAsJsonArray();
		when(fetchingToolsService.getCellTypeData("us", solrSearchService, subSetArray, "cellType",
				catalogStructureUpdateService, resourceResolver, "Human")).thenReturn(StringUtils.EMPTY);
	}

	/**
	 * Gets the null cell type response data.
	 *
	 * @return the null cell type response data
	 * @throws LoginException the login exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SolrServerException the solr server exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	public void getNullCellTypeResponseData()
			throws LoginException, IOException, SolrServerException, RepositoryException {
		when(request.getParameter(CommonConstants.ICM_TYPE)).thenReturn("cellType");
		requestObject = new JsonObject();
		String line = "{}";
		String line2 = null;
		when(reader.readLine()).thenReturn(line, line2);
		when(fetchingToolsService.getCellTypeData("", solrSearchService, null, "cellType",
				catalogStructureUpdateService, resourceResolver, "")).thenReturn(StringUtils.EMPTY);
		cellTypeData.doGet(request, response);
	}

	/**
	 * Gets the panel type response data.
	 *
	 * @return the panel type response data
	 * @throws LoginException the login exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SolrServerException the solr server exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	public void getPanelTypeResponseData()
			throws LoginException, IOException, SolrServerException, RepositoryException {
		when(request.getParameter(CommonConstants.ICM_TYPE)).thenReturn("panelType");
		requestObject = new JsonObject();
		String line = "{\"country\":[\"US\"],\"panelName\":[\"TBMNK Backbone panel\",\"B cell subset panel - 3 Lasers\"]}";
		String line2 = null;
		when(reader.readLine()).thenReturn(line, line2);
		String jsonString = "[\"TBMNK Backbone panel\",\"B cell subset panel - 3 Lasers\"]";
		JsonArray subSetArray = new JsonParser().parse(jsonString).getAsJsonArray();
		when(fetchingToolsService.getCellTypeData("us", solrSearchService, subSetArray, "panelType",
				catalogStructureUpdateService, resourceResolver, "")).thenReturn(StringUtils.EMPTY);
		cellTypeData.doGet(request, response);
	}
	
	/**
	 * Test login exception.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws LoginException the login exception
	 */
	@Test
	void testLoginException() throws IOException, LoginException {
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenThrow(LoginException.class);
		cellTypeData.doGet(request, response);

	}

}
