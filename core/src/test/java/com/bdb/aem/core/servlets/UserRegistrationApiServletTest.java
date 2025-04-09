package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.UserRegistrationApiService;
import com.google.gson.JsonObject;

/**
 * The Class UserRegistrationApiServletTest.
 */
@ExtendWith(MockitoExtension.class)
class UserRegistrationApiServletTest {

	/** Servlet for Api calls. */
	@InjectMocks
	UserRegistrationApiServlet userRegistrationApiServlet;

	/** The reader. */
	@Mock
	BufferedReader reader;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The response. */
	@Mock
	SlingHttpServletResponse response;

	/** The request path info mock. */
	@Mock
	RequestPathInfo requestPathInfo;

	/** The print writer. */
	@Mock
	PrintWriter printWriter;

	/** The user registration api service. */
	@Mock
	UserRegistrationApiService userRegistrationApiService;

	/** The request object. */
	private JsonObject requestObject;

	/** The line. */
	private String line;

	/** The api response. */
	private String apiResponse;

	/** The line 2. */
	private String line2;

	/**
	 * Inits the.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@BeforeEach
	public void init() throws IOException {
		when(request.getReader()).thenReturn(reader);
		line = "{\"test\":Test}";
		apiResponse = "Testing";
		requestObject = new JsonObject();
		requestObject.addProperty("test", "Test");
		line2 = null;
		when(reader.readLine()).thenReturn(line, line2);
		when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		when(response.getWriter()).thenReturn(printWriter);
	}

	/**
	 * Testdo post signup.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws AemInternalServerErrorException 
	 */
	@Test
	public void testdoPostSignup() throws IOException, AemInternalServerErrorException {
		String[] apiType = { "signupcall" };
		when(requestPathInfo.getSelectors()).thenReturn(apiType);
		when(userRegistrationApiService.fetchSignUpDetails(request, requestObject, response)).thenReturn(apiResponse);
		userRegistrationApiServlet.doPost(request, response);
	}

	/**
	 * Testdo post area of focus.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws AemInternalServerErrorException 
	 */
	@Test
	public void testdoPostAreaOfFocus() throws IOException, AemInternalServerErrorException {
		String[] apiType = { "areaoffocus" };
		when(requestPathInfo.getSelectors()).thenReturn(apiType);
		when(userRegistrationApiService.areaOfFocus(request, requestObject, response)).thenReturn(apiResponse);
		userRegistrationApiServlet.doPost(request, response);
	}

	/**
	 * Testdo post reset password.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws AemInternalServerErrorException 
	 */
	@Test
	public void testdoPostResetPassword() throws IOException, AemInternalServerErrorException {
		String[] apiType = { "resetpassword" };
		when(requestPathInfo.getSelectors()).thenReturn(apiType);
		when(userRegistrationApiService.resetPassword(request, requestObject, response)).thenReturn(apiResponse);
		userRegistrationApiServlet.doPost(request, response);
	}

	/**
	 * Testdo post purchase account.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws AemInternalServerErrorException 
	 */
	@Test
	public void testdoPostPurchaseAccount() throws IOException, AemInternalServerErrorException {
		String[] apiType = { "purchaseaccount" };
		when(requestPathInfo.getSelectors()).thenReturn(apiType);
		when(userRegistrationApiService.purchaseAccount(request, requestObject, response)).thenReturn(apiResponse);
		userRegistrationApiServlet.doPost(request, response);
	}

	/**
	 * Testdo post invalid request.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testdoPostInvalidRequest() throws IOException {
		String[] apiType = { "test" };
		when(requestPathInfo.getSelectors()).thenReturn(apiType);
		userRegistrationApiServlet.doPost(request, response);
	}

	/**
	 * Testdo post invalid request when selectors are not available.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testdoPostNoSelectors() throws IOException {
		String[] apiType = {};
		when(requestPathInfo.getSelectors()).thenReturn(apiType);
		userRegistrationApiServlet.doPost(request, response);
	}
	
	/**
	 * Test aem internal server error exception.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws AemInternalServerErrorException the aem internal server error exception
	 */
	@Test
	public void testAemInternalServerErrorException() throws IOException, AemInternalServerErrorException {
		String[] apiType = { "purchaseaccount" };
		when(requestPathInfo.getSelectors()).thenReturn(apiType);
		when(userRegistrationApiService.purchaseAccount(request, requestObject, response)).thenThrow(new AemInternalServerErrorException("Exception",new Throwable()));
		userRegistrationApiServlet.doPost(request, response);
	}
}
