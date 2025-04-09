package com.bdb.aem.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.api.client.RestClient;
import com.bdb.aem.core.api.client.impl.RestClientImpl;
import com.bdb.aem.core.api.response.BaseResponse;
import com.bdb.aem.core.api.response.DataResponse;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.FetchBearerTokenService;
import com.google.gson.JsonObject;

import junitx.util.PrivateAccessor;

/**
 * The Class EligibleProductsApiServiceImplTest.
 */
@ExtendWith({ MockitoExtension.class })
class EligibleProductsApiServiceImplTest {

	/** The eligible products api service impl. */
	@InjectMocks
	EligibleProductsApiServiceImpl eligibleProductsApiServiceImpl;

	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The fetch bearer token service. */
	@Mock
	FetchBearerTokenService fetchBearerTokenService;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The response. */
	@Mock
	SlingHttpServletResponse response;

	/** The base response. */
	@Mock
	BaseResponse baseResponse;

	/** The http client. */
	@Mock
	CloseableHttpClient httpClient;

	/**
	 * Test fetch promo details.
	 *
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testFetchPromoDetails() throws AemInternalServerErrorException, NoSuchFieldException {

		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("name", "BDB");
		RestClientImpl restClient = Mockito.mock(RestClientImpl.class);

		PrivateAccessor.setField(eligibleProductsApiServiceImpl, "restClient", restClient);
		PrivateAccessor.setField(restClient, "httpClient", httpClient);

		when(fetchBearerTokenService.fetchAuthToken(request)).thenReturn("accessToken");
		when(request.getHeader("siteId")).thenReturn("header");
		when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn("hybrisDomain");
		when(bdbApiEndpointService.getPromoProductsListEndpoint()).thenReturn("listEndpoint");

		assertNotNull(eligibleProductsApiServiceImpl.fetchPromoDetails(request, requestObject, response, "promocode"));
	}

	/**
	 * Test fetch promo details null to access token.
	 *
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 */
	@Test
	void testFetchPromoDetailsNullToAccessToken() throws AemInternalServerErrorException {
		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("name", "BDB");

		when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn("hybrisDomain");
		when(bdbApiEndpointService.getPromoProductsListEndpoint()).thenReturn("listEndpoint");

		assertNotNull(eligibleProductsApiServiceImpl.fetchPromoDetails(request, requestObject, response, "promocode"));
	}

	/**
	 * Test fetch auth token.
	 *
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testFetchAuthToken() throws AemInternalServerErrorException, NoSuchFieldException {
		DataResponse dataResponse = Mockito.mock(DataResponse.class);
		String jsonResponse = "{\r\n" + "	\"parentPagePath\": \"page/path\",\r\n" + "	\"type\": \"event\",\r\n"
				+ "	\"access_token\": \"accessToken\",\r\n" + "	\"isEnable\": true\r\n" + "}";
		RestClient restClient = Mockito.mock(RestClient.class);
		PrivateAccessor.setField(eligibleProductsApiServiceImpl, "restClient", restClient);

		when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn("hybrisDomain");
		when(bdbApiEndpointService.getFetchAuthTokenEndpoint()).thenReturn("tokenEndpoint");
		when(bdbApiEndpointService.getFetchAuthTokenEndpointClientId()).thenReturn("endpointClientId");
		when(bdbApiEndpointService.getFetchAuthTokenEndpointGrantType()).thenReturn("endpointGrantType");
		when(bdbApiEndpointService.getFetchAuthTokenEndpointClientSecret()).thenReturn("endpointClientSecret");
		when(bdbApiEndpointService.getAnonymousUserIdPlaceholder()).thenReturn("userIdPlaceholder");
		when(restClient.sendRequest(Mockito.any(), Mockito.any())).thenReturn(baseResponse);
		when(baseResponse.hasError()).thenReturn(false);
		when(baseResponse.getResponseData()).thenReturn(dataResponse);
		when(dataResponse.getData()).thenReturn(jsonResponse);

		assertNotNull(eligibleProductsApiServiceImpl.fetchAuthToken(request));
	}

}
