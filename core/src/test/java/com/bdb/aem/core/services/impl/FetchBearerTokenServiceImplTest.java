
package com.bdb.aem.core.services.impl;

import static org.mockito.Mockito.when;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
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

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * Junit for FetchBearerTokenServiceImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class FetchBearerTokenServiceImplTest {

	/** The Fetch Bearer Token Service Impl. */
	@InjectMocks
	FetchBearerTokenServiceImpl fetchBearerTokenServiceImpl;

	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The data. */
	@Mock
	private DataResponse responseData;

	/** The rest client. */
	@Mock
	private RestClient restClient;

	/** The request. */
	@Mock
	private SlingHttpServletRequest request;

	/** The base request. */
	@Mock
	private BaseRequest baseRequest;

	/** The response. */
	@Mock
	private SlingHttpServletResponse response;

	/** The base response. */
	@Mock
	private BaseResponse baseResponse;

	/** The response string. */
	private String responseString;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */

	@BeforeEach
	void setUp() throws Exception {
		responseString = "{\r\n" + "  \"access_token\": \"2a62827e-83e2-4837-9b70-f60e03e01bdf\",\r\n"
				+ "  \"token_type\": \"bearer\",\r\n" + "  \"expires_in\": 1409805998,\r\n"
				+ "  \"scope\": \"client_credentials openid\"\r\n" + "}";
		when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn("Test");
		when(bdbApiEndpointService.getFetchAuthTokenEndpoint()).thenReturn("Test");
		when(bdbApiEndpointService.getFetchAuthTokenEndpointClientId()).thenReturn("Test");
		when(bdbApiEndpointService.getFetchAuthTokenEndpointGrantType()).thenReturn("Test");
		when(bdbApiEndpointService.getFetchAuthTokenEndpointClientSecret()).thenReturn("Test");
		when(bdbApiEndpointService.getAnonymousUserIdPlaceholder()).thenReturn("Test");
		when(restClient.sendRequest(Mockito.any(), Mockito.any())).thenReturn(baseResponse);
		when(baseResponse.hasError()).thenReturn(false);
		when(baseResponse.getResponseData()).thenReturn(responseData);
		when(responseData.getData()).thenReturn(responseString);
	}

	/**
	 * Fetch auth token test.
	 *
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 */
	@Test
	void fetchAuthTokenTest() throws AemInternalServerErrorException {
		fetchBearerTokenServiceImpl.fetchAuthToken(request);
	}
}
