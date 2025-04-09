package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.EligibleProductsApiService;
import com.google.gson.JsonObject;

/**
 * @author knarayansingh
 *
 */
@ExtendWith(MockitoExtension.class)
public class EligibleProductApiServletTest {

	/** The EligibleProductApiServlet servlet object. */
	@InjectMocks
	EligibleProductApiServlet eligibleProductApiServlet;

	/** The EligibleProductsApiService. */
	@Mock
	EligibleProductsApiService eligibleProductsApiService;

	/** The SlingHttpServletRequest. */
	@Mock
	SlingHttpServletRequest request;

	/** The SlingHttpServletResponse. */
	@Mock
	SlingHttpServletResponse response;

	/** The PrintWriter. */
	@Mock
	PrintWriter printWriter;

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
		when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		String[] selector = new String[1];
		selector[0] = "selector";
		when(requestPathInfo.getSelectors()).thenReturn(selector);
		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("name", "BDB");
		when(eligibleProductsApiService.fetchPromoDetails(request, requestObject, response, "demo")).thenReturn("test");
		when(response.getWriter()).thenReturn(printWriter);
		eligibleProductApiServlet.doPost(request, response);
	}

	/**
	 * @throws IOException
	 * @throws AemInternalServerErrorException
	 */
	@Test
	public void aemInternalServerErrorExceptionTest() throws IOException, AemInternalServerErrorException {
		when(request.getReader()).thenReturn(reader);
		when(reader.readLine()).thenReturn("{ \"name\":\"BDB\"}", null);
		when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		String[] selector = new String[1];
		selector[0] = "selector";
		when(requestPathInfo.getSelectors()).thenReturn(selector);
		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("name", "BDB");
		when(eligibleProductsApiService.fetchPromoDetails(request, requestObject, response, "demo"))
				.thenThrow(AemInternalServerErrorException.class);
		when(response.getWriter()).thenReturn(null);
		eligibleProductApiServlet.doPost(request, response);
	}

}
