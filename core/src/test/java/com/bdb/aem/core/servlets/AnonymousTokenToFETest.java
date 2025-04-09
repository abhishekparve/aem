package com.bdb.aem.core.servlets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ValueMap;
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
import com.bdb.aem.core.services.FetchBearerTokenService;

/**
 * The Class AnonymousTokenToFETest.
 */
@ExtendWith(MockitoExtension.class)
public class AnonymousTokenToFETest {

	/** The get countries from region servlet. */
	@InjectMocks
	AnonymousTokenToFE anonymousTokenToFE;
	
	/** The Fetch Bearer Token Service Impl. */
	@Mock
	FetchBearerTokenService fetchBearerTokenService;

	/** The restClient. */
	@Mock
	RestClient restClient;
	
	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The response. */
	@Mock
	SlingHttpServletResponse response;

	/** The properties. */
	@Mock
	ValueMap properties;

	/** The print writer. */
	@Mock
	PrintWriter printWriter;

	/** The bdbApiEndpointService Service*/
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	
	/** The base request*/
	@Mock
	BaseRequest baseRequest;
	
	/** The base response. */
	@Mock
	private BaseResponse baseResponse;
	
	/** The data. */
	@Mock
	private DataResponse responseData;
	
	/**
	 * Inits the.
	 * 
	 * @throws LoginException
	 * @throws IOException
	 * @throws AemInternalServerErrorException
	 */
	@BeforeEach
	public void init() throws IOException, LoginException , AemInternalServerErrorException {
		
		String responseString = "{\r\n" + "  \"access_token\": \"2a62827e-83e2-4837-9b70-f60e03e01bdf\",\r\n"
				+ "  \"token_type\": \"bearer\",\r\n" + "  \"expires_in\": 1409805998,\r\n"
				+ "  \"scope\": \"client_credentials openid\"\r\n" + "}";
		when(fetchBearerTokenService.getAuthTokenApiEndpoint(request)).thenReturn("https://api-qa1.bdbiosciences.com/authorizationserver/oauth/token?client_id=bdb&grant_type=client_credentials&client_secret=password&siteID=bdbUS");
		when(restClient.sendRequest(Mockito.any(), Mockito.any())).thenReturn(baseResponse);
		when(baseResponse.hasError()).thenReturn(false);
		when(baseResponse.getResponseData()).thenReturn(responseData);
		when(responseData.getData()).thenReturn(responseString);
	}

	/**
	 * TestdoGet step value
	 *
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws LoginException
	 */
	@Test
	public void testdoGet() throws IOException, LoginException {
		anonymousTokenToFE.doGet(request, response);
	}
}
