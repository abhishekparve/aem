package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.security.auth.login.LoginException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.api.client.RestClient;
import com.bdb.aem.core.api.request.BaseRequest;
import com.bdb.aem.core.api.response.BaseResponse;
import com.bdb.aem.core.api.response.DataResponse;
import com.bdb.aem.core.api.response.ErrorResponse;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.FetchBearerTokenService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SearchConstants;


/**
 * The Class SearchAdminPageServletTest.
 */
@ExtendWith(MockitoExtension.class)
class SearchAdminPageServletTest {

	/** The search admin page servlet. */
	@InjectMocks
	SearchAdminPageServlet searchAdminPageServlet;

	/** The bdb search endpoint service. */
	@Mock
	BDBSearchEndpointService bdbSearchEndpointService;

	/** The SlingHttpServletRequest. */
	@Mock
	SlingHttpServletRequest request;

	/** The SlingHttpServletResponse. */
	@Mock
	SlingHttpServletResponse response;

	/** The request parameter. */
	@Mock
	RequestParameter requestParameter;

	/** The rest client. */
	@Mock
	RestClient restClient;

	/** The base request. */
	@Mock
	BaseRequest baseRequest;

	/** The base response. */
	@Mock
	BaseResponse baseResponse;

	/** The writer. */
	@Mock
	PrintWriter writer;

	/** The reader. */
	@Mock
	BufferedReader reader;
	
	/** The mock iterator. */
	@Mock
	Iterator mockIterator;

	/** The response data. */
	@Mock
	private DataResponse responseData;

	/** The fetch bearer token service. */
	@Mock
	FetchBearerTokenService fetchBearerTokenService;

	/** The solr url. */
	private String SOLR_URL = "/content/bd/language-masters/en/products";

	/** The synonym results. */
	private String SYNONYM_RESULTS = "y";
	
	@Mock
	ArrayList<String> countryList;

	/** The error response. */
	@Mock
	ErrorResponse errorResponse;
	
	@Mock
	ResourceResolverFactory resolverFactory;
	
	@Mock
	ResourceResolver resolver;
	
	@Mock
	SolrSearchService solrSearchService;

	/**
	 * Inits the.
	 *
	 * @throws IOException                     Signals that an I/O exception has
	 *                                         occurred.
	 * @throws LoginException                  the login exception
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 * @throws org.apache.sling.api.resource.LoginException 
	 */
	@BeforeEach
	public void init() throws IOException, LoginException, AemInternalServerErrorException, org.apache.sling.api.resource.LoginException {
		countryList.add("us");
		countryList.add("ge");
		String responseString = "{\r\n" + "  \"access_token\": \"2a62827e-83e2-4837-9b70-f60e03e01bdf\",\r\n"
				+ "  \"token_type\": \"bearer\",\r\n" + "  \"expires_in\": 1409805998,\r\n"
				+ "  \"scope\": \"client_credentials openid\"\r\n" + "}";
		lenient().when(baseResponse.getError()).thenReturn(errorResponse);
		lenient().when(solrSearchService.getAllCountries(resolver)).thenReturn(countryList);
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resolver);
		lenient().when(restClient.sendRequest(Mockito.any(), Mockito.any())).thenReturn(baseResponse);
		lenient().when(baseResponse.getResponseData()).thenReturn(responseData);
		lenient().when(responseData.getData()).thenReturn(responseString);
		lenient().when(request.getRequestParameter(SearchConstants.SEARCH_PARAM)).thenReturn(requestParameter);
		lenient().when(request.getRequestParameter(SearchConstants.SEARCH_LANGUAGE_KEY_WORD)).thenReturn(requestParameter);
		lenient().when(request.getReader()).thenReturn(reader);
		lenient().when(reader.readLine()).thenReturn("{ \"name\":\"BDB\"}").thenReturn(null);
		lenient().when(bdbSearchEndpointService.getSolrUrl()).thenReturn(SOLR_URL);
		lenient().when(response.getWriter()).thenReturn(writer);
		lenient().when(baseResponse.getError()).thenReturn(errorResponse);
		lenient().when(countryList.iterator()).thenReturn(mockIterator);
	}

	/**
	 * Test do post search synonym.
	 *
	 * @throws IOException                     Signals that an I/O exception has
	 *                                         occurred.
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	@Test
	void testDoPostSearchSynonym() throws IOException, AemInternalServerErrorException {

		lenient().when(request.getRequestParameter(SearchConstants.SEARCH_PARAM).getString())
				.thenReturn(SearchConstants.SEARCH_SYNONYM_RESULTS);
		lenient().when(bdbSearchEndpointService.getSynonymResults()).thenReturn(SYNONYM_RESULTS);

		searchAdminPageServlet.doPost(request, response);
	}

	/**
	 * Test do post search delete.
	 *
	 * @throws IOException                     Signals that an I/O exception has
	 *                                         occurred.
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	@Test
	void testDoPostSearchDelete() throws IOException, AemInternalServerErrorException {
		lenient().when(request.getRequestParameter(SearchConstants.SEARCH_PARAM).getString())
				.thenReturn(SearchConstants.SEARCH_DELETE_RESULTS);
		lenient().when(request.getRequestParameter(SearchConstants.SEARCH_DELETE_KEY_WORD)).thenReturn(requestParameter);
		lenient().when(bdbSearchEndpointService.getDeleteSynonym()).thenReturn(SYNONYM_RESULTS);
		lenient().when(baseResponse.getError()).thenReturn(errorResponse);

		//searchAdminPageServlet.doPost(request, response);
	}

	/**
	 * Test do post search create synonym.
	 *
	 * @throws IOException                     Signals that an I/O exception has
	 *                                         occurred.
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */

	@Test
	void testDoPostSearchCreateSynonym() throws IOException, AemInternalServerErrorException,Exception {
		ArrayList<String> countriesList=new ArrayList<String>();
		countriesList.add("us");
		lenient().when(request.getRequestParameter(SearchConstants.SEARCH_PARAM).getString())
				.thenReturn(SearchConstants.SEARCH_CREATE_SYNONYM_RESULTS);
		lenient().when(bdbSearchEndpointService.getCreateSynonym()).thenReturn(SYNONYM_RESULTS);
		lenient().when(baseResponse.getError()).thenReturn(errorResponse);
		lenient().when(solrSearchService.getAllCountries(resolver)).thenReturn(countriesList);
		lenient().when(errorResponse.getMessage()).thenReturn("Blank");
		//searchAdminPageServlet.doPost(request, response);
	}
		/**
	 * Test do post search stopword.
	 *
	 * @throws IOException                     Signals that an I/O exception has
	 *                                         occurred.
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	@Test
	void testDoPostSearchStopword() throws IOException, AemInternalServerErrorException {
		lenient().when(request.getRequestParameter(SearchConstants.SEARCH_PARAM).getString())
				.thenReturn(SearchConstants.SEARCH_STOPWORD_RESULTS);
		lenient().when(bdbSearchEndpointService.getStopWordResults()).thenReturn(SYNONYM_RESULTS);
		lenient().when(baseResponse.getError()).thenReturn(errorResponse);

		searchAdminPageServlet.doPost(request, response);
	}

	/**
	 * Test do post search create stopword.
	 *
	 * @throws IOException                     Signals that an I/O exception has
	 *                                         occurred.
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	@Test
	void testDoPostSearchCreateStopword() throws IOException, AemInternalServerErrorException {
		lenient().when(request.getRequestParameter(SearchConstants.SEARCH_PARAM).getString())
				.thenReturn(SearchConstants.SEARCH_CREATE_STOPWORD_RESULTS);
		lenient().when(reader.readLine()).thenReturn("[ \"name\",\"BDB\"]").thenReturn(null);
		lenient().when(bdbSearchEndpointService.getCreateStopWord()).thenReturn(SYNONYM_RESULTS);
		lenient().when(baseResponse.getError()).thenReturn(errorResponse);

		//searchAdminPageServlet.doPost(request, response);
	}

	/**
	 * Test do post search remove stopword.
	 *
	 * @throws IOException                     Signals that an I/O exception has
	 *                                         occurred.
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	@Test
	void testDoPostSearchRemoveStopword() throws IOException, AemInternalServerErrorException ,Exception{
		ArrayList<String> countriesList=new ArrayList<String>();
		countriesList.add("us");
		lenient().when(solrSearchService.getAllCountries(resolver)).thenReturn(countriesList);
		lenient().when(errorResponse.getMessage()).thenReturn("Blank");
		lenient().when(request.getRequestParameter(SearchConstants.SEARCH_PARAM).getString())
				.thenReturn(SearchConstants.SEARCH_REMOVE_STOPWORD_RESULT);
		lenient().when(request.getRequestParameter(SearchConstants.SEARCH_REMOVE_KEY_WORD)).thenReturn(requestParameter);
		lenient().when(bdbSearchEndpointService.getRemoveSelectedStopWord()).thenReturn(SYNONYM_RESULTS);
		lenient().when(baseResponse.getError()).thenReturn(errorResponse);

		//searchAdminPageServlet.doPost(request, response);
	}

	/**
	 * Test do post search synonym error.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testDoPostSearchSynonymError() throws IOException {
		lenient().when(request.getRequestParameter(SearchConstants.SEARCH_PARAM).getString())
				.thenReturn(SearchConstants.SEARCH_SYNONYM_RESULTS);
		lenient().when(bdbSearchEndpointService.getSynonymResults()).thenReturn(SYNONYM_RESULTS);
		lenient().when(baseResponse.getError()).thenReturn(errorResponse);

		searchAdminPageServlet.doPost(request, response);
	}

	/**
	 * Test do post search delete error.
	 *
	 * @throws IOException                     Signals that an I/O exception has
	 *                                         occurred.
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	@Test
	void testDoPostSearchDeleteError() throws IOException, AemInternalServerErrorException,Exception {
		ArrayList<String> countriesList=new ArrayList<String>();
		countriesList.add("us");
		lenient().when(solrSearchService.getAllCountries(resolver)).thenReturn(countriesList);
		lenient().when(errorResponse.getMessage()).thenReturn("Blank");

		lenient().when(request.getRequestParameter(SearchConstants.SEARCH_PARAM).getString())
				.thenReturn(SearchConstants.SEARCH_DELETE_RESULTS);
		lenient().when(request.getRequestParameter(SearchConstants.SEARCH_DELETE_KEY_WORD)).thenReturn(requestParameter);
		lenient().when(bdbSearchEndpointService.getDeleteSynonym()).thenReturn(SYNONYM_RESULTS);
		lenient().when(baseResponse.getError()).thenReturn(errorResponse);

		//searchAdminPageServlet.doPost(request, response);
	}

	/**
	 * Test do post search create synonym error.
	 *
	 * @throws IOException                     Signals that an I/O exception has
	 *                                         occurred.
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	@Test
	void testDoPostSearchCreateSynonymError() throws IOException, AemInternalServerErrorException {
		lenient().when(request.getRequestParameter(SearchConstants.SEARCH_PARAM).getString())
				.thenReturn(SearchConstants.SEARCH_CREATE_SYNONYM_RESULTS);
		lenient().when(bdbSearchEndpointService.getCreateSynonym()).thenReturn(SYNONYM_RESULTS);
		lenient().when(baseResponse.getError()).thenReturn(errorResponse);

		//searchAdminPageServlet.doPost(request, response);
	}

	/**
	 * Test do post search stopword error.
	 *
	 * @throws IOException                     Signals that an I/O exception has
	 *                                         occurred.
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	@Test
	void testDoPostSearchStopwordError() throws IOException, AemInternalServerErrorException {
		lenient().when(request.getRequestParameter(SearchConstants.SEARCH_PARAM).getString())
				.thenReturn(SearchConstants.SEARCH_STOPWORD_RESULTS);
		lenient().when(bdbSearchEndpointService.getStopWordResults()).thenReturn(SYNONYM_RESULTS);
		lenient().when(baseResponse.getError()).thenReturn(errorResponse);
		searchAdminPageServlet.doPost(request, response);
	}

	/**
	 * Test do post search create stopword error.
	 *
	 * @throws IOException                     Signals that an I/O exception has
	 *                                         occurred.
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	@Test
	void testDoPostSearchCreateStopwordError() throws IOException, AemInternalServerErrorException,Exception {
		ArrayList<String> countriesList=new ArrayList<String>();
		countriesList.add("us");
		lenient().when(solrSearchService.getAllCountries(resolver)).thenReturn(countriesList);
		lenient().when(errorResponse.getMessage()).thenReturn("Blank");
		lenient().when(request.getRequestParameter(SearchConstants.SEARCH_PARAM).getString())
				.thenReturn(SearchConstants.SEARCH_CREATE_STOPWORD_RESULTS);
		lenient().when(reader.readLine()).thenReturn("[ \"name\",\"BDB\"]").thenReturn(null);
		lenient().when(bdbSearchEndpointService.getCreateStopWord()).thenReturn(SYNONYM_RESULTS);
		lenient().when(baseResponse.getError()).thenReturn(errorResponse);

		//searchAdminPageServlet.doPost(request, response);
	}

	/**
	 * Test do post search remove stopword error.
	 *
	 * @throws IOException                     Signals that an I/O exception has
	 *                                         occurred.
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 */
	@Test
	void testDoPostSearchRemoveStopwordError() throws IOException, AemInternalServerErrorException {
		lenient().when(request.getRequestParameter(SearchConstants.SEARCH_PARAM).getString())
				.thenReturn(SearchConstants.SEARCH_REMOVE_STOPWORD_RESULT);
		lenient().when(request.getRequestParameter(SearchConstants.SEARCH_REMOVE_KEY_WORD)).thenReturn(requestParameter);
		lenient().when(bdbSearchEndpointService.getRemoveSelectedStopWord()).thenReturn(SYNONYM_RESULTS);
		lenient().when(baseResponse.getError()).thenReturn(errorResponse);

		//searchAdminPageServlet.doPost(request, response);
	}

}
