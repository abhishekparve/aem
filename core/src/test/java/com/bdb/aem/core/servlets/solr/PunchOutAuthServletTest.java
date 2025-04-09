package com.bdb.aem.core.servlets.solr;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
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
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

/**
 * The Class PunchOutAuthServletTest.
 */
@ExtendWith({ MockitoExtension.class })
class PunchOutAuthServletTest {

	/** The punch out auth servlet. */
	@InjectMocks
	PunchOutAuthServlet punchOutAuthServlet;

	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/** The page manager. */
	@Mock
	PageManager pageManager;
	@Mock
	Resource resource;
	/** The base response. */
	@Mock
	BaseResponse baseResponse;

	/** The base request. */
	@Mock
	BaseRequest baseRequest;

	/** The http response. */
	@Mock
	HttpResponse httpResponse;

	/** The post method. */
	@Mock
	HttpPost postMethod;

	/** The rest client. */
	@Mock
	RestClient restClient;

	/** The response data. */
	@Mock
	private DataResponse responseData;

	/** The SlingHttpServletRequest. */
	@Mock
	SlingHttpServletRequest request;

	/** The SlingHttpServletResponse. */
	@Mock
	SlingHttpServletResponse response;

	/** The PrintWriter. */
	@Mock
	PrintWriter writer;

	/** The HttpSolrClient. */
	@Mock
	HttpSolrClient server;

	/** The write service auth. */
	Map<String, Object> writeServiceAuth;

	/** The parameter map. */
	Map<String, String[]> parameterMap;
@Mock
Page page;
	/**
	 * Setup.
	 */
	@BeforeEach
	void setup() {

		String[] parameterMapValue = { "Value" };
		writeServiceAuth = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, "writeService");
		parameterMap = new HashMap<>();
		parameterMap.put(CommonConstants.KEY, parameterMapValue);
		parameterMap.put(CommonConstants.SID, parameterMapValue);
		parameterMap.put(CommonConstants.CART_ID, parameterMapValue);
		parameterMap.put(CommonConstants.OPERATION, parameterMapValue);
		parameterMap.put(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.SID), parameterMapValue);
		parameterMap.put(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.CART_ID), parameterMapValue);
		parameterMap.put(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.OPERATION), parameterMapValue);
		parameterMap.put(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.REGION), parameterMapValue);
		parameterMap.put(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.COUNTRY), parameterMapValue);
		parameterMap.put(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.LANGUAGE), parameterMapValue);
		parameterMap.put(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.OPERATION), parameterMapValue);
		parameterMap.put(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.DISPLAY_MY_ORDERS), parameterMapValue);
		parameterMap.put(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.PRODUCT_CODE_KEYWORD), parameterMapValue);
		parameterMap.put(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.DISPLAY_SHIPTO), parameterMapValue);
		parameterMap.put(CommonConstants.URL_KEYWORD_AMP.concat(CommonConstants.ADDRESS_ID), parameterMapValue);


	}

	/**
	 * Test do get.
	 *
	 * @throws IOException                     Signals that an I/O exception has
	 *                                         occurred.
	 * @throws LoginException                  the login exception
	 * @throws AemInternalServerErrorException the aem internal server error
	 *                                         exception
	 * @throws NoSuchFieldException            the no such field exception
	 */
	@Test
	void testDoGet() throws IOException, LoginException, AemInternalServerErrorException, NoSuchFieldException {
		String responseString = "{\r\n" + "  \"operation\": \"create\",\r\n"
				+ "  \"hybrisToken\": \"2a62827e-83e2-4837-9b70-f60e03e01bdf\",\r\n" + "  \"uid\": \"bearer\",\r\n"
				+ "  \"cartID\": 1409805998,\r\n" + "  \"ssoToken\": \"f60e03e01bdf\",\r\n"
				+ "  \"grantEnabled\": \"enable\",\r\n" + "  \"isSmartCartUser\": \"enable\"\r\n" + "}";
		lenient().when(restClient.sendRequest(Mockito.any(), Mockito.any())).thenReturn(baseResponse);
		lenient().when(baseResponse.getResponseData()).thenReturn(responseData);
		lenient().when(responseData.getData()).thenReturn(responseString);

		lenient().when(request.getParameterMap()).thenReturn(parameterMap);
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(response.getWriter()).thenReturn(writer);
		lenient().when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn("HybrisProduction");
		lenient().when(bdbApiEndpointService.getPunchOutEndpoint()).thenReturn("PunchOutEndpoint");
		lenient().when(bdbApiEndpointService.getCookieExpiryTime()).thenReturn(500);
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(resource);
		lenient().when(resource.adaptTo(Page.class)).thenReturn(page);
		punchOutAuthServlet.doGet(request, response);
	}

}
