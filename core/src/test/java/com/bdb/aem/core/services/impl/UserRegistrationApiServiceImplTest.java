
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
import com.google.gson.JsonObject;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * Junit for UserRegistrationApiServiceImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class UserRegistrationApiServiceImplTest {

	/** The user registration api service impl. */
	@InjectMocks
	UserRegistrationApiServiceImpl userRegistrationApiServiceImpl;
	
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
	
	/** The request object. */
	private JsonObject requestObject;
	
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
		responseString = "{\r\n" + 
				"  \"access_token\": \"2a62827e-83e2-4837-9b70-f60e03e01bdf\",\r\n" + 
				"  \"token_type\": \"bearer\",\r\n" + 
				"  \"expires_in\": 1409805998,\r\n" + 
				"  \"scope\": \"client_credentials openid\"\r\n" + 
				"}";
		requestObject = new JsonObject();
		when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn("A");
		when(bdbApiEndpointService.getFetchAuthTokenEndpoint()).thenReturn("A");
		when(bdbApiEndpointService.getFetchAuthTokenEndpointClientId()).thenReturn("A");
		when(bdbApiEndpointService.getFetchAuthTokenEndpointGrantType()).thenReturn("A");
		when(bdbApiEndpointService.getFetchAuthTokenEndpointClientSecret()).thenReturn("A");
		when(bdbApiEndpointService.getAnonymousUserIdPlaceholder()).thenReturn("A");
		when(restClient.sendRequest(Mockito.any(), Mockito.any())).thenReturn(baseResponse);
		when(baseResponse.hasError()).thenReturn(false);
		when(baseResponse.getResponseData()).thenReturn(responseData);
		when(responseData.getData()).thenReturn(responseString);
	}
	
	/**
	 * Fetch sign up details test.
	 * @throws AemInternalServerErrorException 
	 */
	@Test
	void fetchSignUpDetailsTest() throws AemInternalServerErrorException {
		when(bdbApiEndpointService.getHybrisSignUpEndpoint()).thenReturn("A");
		userRegistrationApiServiceImpl.fetchSignUpDetails(request, requestObject, response);
	}
	
	/**
	 * Area of focus test.
	 * @throws AemInternalServerErrorException 
	 */
	@Test
	void areaOfFocusTest() throws AemInternalServerErrorException {
		
		when(bdbApiEndpointService.getHybrisSignUpPreferenceEndpoint()).thenReturn("A");
		userRegistrationApiServiceImpl.areaOfFocus(request, requestObject, response);
	}
	
	/**
	 * Reset password test.
	 * @throws AemInternalServerErrorException 
	 */
	@Test
	void resetPasswordTest() throws AemInternalServerErrorException {
		when(bdbApiEndpointService.getHybrisResetPasswordEndpoint()).thenReturn("A");
		userRegistrationApiServiceImpl.resetPassword(request, requestObject, response);
	}
	
	/**
	 * Purchase account test.
	 * @throws AemInternalServerErrorException 
	 */
	@Test
	void purchaseAccountTest() throws AemInternalServerErrorException {
		when(bdbApiEndpointService.getPurchasingAccountRegistrationEndpoint()).thenReturn("A");
		userRegistrationApiServiceImpl.purchaseAccount(request, requestObject, response);
	}
}
