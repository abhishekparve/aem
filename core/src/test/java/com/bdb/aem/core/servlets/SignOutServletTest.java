package com.bdb.aem.core.servlets;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.SignOutService;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

/**
 * @author knarayansingh
 *
 */
@ExtendWith(MockitoExtension.class)
public class SignOutServletTest {

	/** The SignOutServlet servlet object. */
	@InjectMocks
	SignOutServlet signOutServlet;

	/** The SlingHttpServletRequest. */
	@Mock
	SlingHttpServletRequest request;

	/** The SlingHttpServletResponse. */
	@Mock
	SlingHttpServletResponse response;

	/** The PrintWriter. */
	@Mock
	PrintWriter printWriter;

	/** The SignOutService. */
	@Mock
	SignOutService signOutService;;

	/** The BDBApiEndpointService. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The BufferedReader. */
	@Mock
	BufferedReader reader;

	/** The RequestPathInfo. */
	@Mock
	RequestPathInfo requestPathInfo;


	/**
	 * @throws IOException
	 * @throws AemInternalServerErrorException
	 */
	@Test
	public void doPostTest() throws IOException, AemInternalServerErrorException {

		when(request.getReader()).thenReturn(reader);
		when(reader.readLine()).thenReturn("{ \"name\":\"BDB\"}", null);
		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("name", "BDB");
		when(signOutService.fetchSignOutDetails(request, requestObject, response)).thenReturn(true);
		when(bdbApiEndpointService.ciamPingIdDomain()).thenReturn("redirect");
		when(bdbApiEndpointService.ciamSignOutEndpoint()).thenReturn("redirect");
		lenient().when(response.getWriter()).thenReturn(printWriter);
		signOutServlet.doPost(request, response);
		lenient().when(signOutService.fetchSignOutDetails(request, requestObject, response)).thenReturn(false);
		signOutServlet.doPost(request, response);

	}

	/**
	 * @throws IOException
	 * @throws AemInternalServerErrorException
	 */
	@Test
	public void aemInternalServerErrorExceptionTest() throws IOException, AemInternalServerErrorException {
		when(request.getReader()).thenReturn(reader);
		when(reader.readLine()).thenReturn("{ \"name\":\"BDB\"}", null);
		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("name", "BDB");
		lenient().when(response.getWriter()).thenReturn(printWriter);
		when(signOutService.fetchSignOutDetails(request, requestObject, response))
				.thenThrow(AemInternalServerErrorException.class);
		signOutServlet.doPost(request, response);
	}
	
	@Test
	public void prepareRedirectionObjTest () throws Exception {
		when(request.getReader()).thenReturn(reader);
		when(reader.readLine()).thenReturn("{ \"name\":\"BDB\"}", null);
		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("name", "BDB");
		lenient().when(signOutService.fetchSignOutDetails(request, requestObject, response)).thenReturn(true);
		lenient().when(request.getParameter("ui_locales")).thenReturn("/redirect/url");
		lenient().when(bdbApiEndpointService.getPingIdSsoSignOutDomain()).thenReturn("redirect");
		lenient().when(bdbApiEndpointService.devSsoSignOutEndpoint()).thenReturn("redirect");
		lenient().when(response.getWriter()).thenReturn(printWriter);
		signOutServlet.doPost(request, response);
	
	}

}
