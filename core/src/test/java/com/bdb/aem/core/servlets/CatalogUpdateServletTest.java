package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.google.gson.JsonObject;

@ExtendWith(MockitoExtension.class)
public class CatalogUpdateServletTest {

	/** The CatalogUpdateServlet servlet. */
	@InjectMocks
	CatalogUpdateServlet catlogUpdateServlet;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The response. */
	@Mock
	SlingHttpServletResponse response;

	/** The reader. */
	@Mock
	BufferedReader reader;
	
	/** The print writer. */
	@Mock
	PrintWriter printWriter;

	/** The catalog structure update service. */
	@Mock
	CatalogStructureUpdateService catalogStructureUpdateService;

	@Mock
	ResourceResolver resourceResolver;

	@Mock
	Session session;

	@Mock
	RequestPathInfo requestPathInfo;
	
	/** The request object. */
	private JsonObject requestObject;

	/** The request 1. */
	private String request1;

	/** The api response. */
	private String apiResponse;

	/** The request 2. */
	private String request2;

	@BeforeEach
	void setUp() throws IOException {
		when(request.getReader()).thenReturn(reader);
		request1 = "{\"test\":Test}";
		apiResponse = "Testing";
		requestObject = new JsonObject();
		requestObject.addProperty("test", "Test");
		request2 = null;
		when(reader.readLine()).thenReturn(request1, request2);
		when(request.getResourceResolver()).thenReturn(resourceResolver);
		//when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(response.getWriter()).thenReturn(printWriter);
	}

	@Test
	public void doPostSAPTest() throws IOException {

		String[] selectors = new String[1];
		selectors[0] = "sap";
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		lenient().when(requestPathInfo.getSelectors()).thenReturn(selectors);
		when(response.getWriter()).thenReturn(printWriter);
		catlogUpdateServlet.doPost(request, response);
	}
	
	@Test
	public void doPostHPTest() throws IOException {

		String[] selectors = new String[1];
		selectors[0] = "hp";
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		lenient().when(requestPathInfo.getSelectors()).thenReturn(selectors);
		when(response.getWriter()).thenReturn(printWriter);
		catlogUpdateServlet.doPost(request, response);
	}
	
	@Test
	public void doPostIndexAndReplicateTest() throws IOException {

		String[] selectors = new String[1];
		selectors[0] = "indexAndReplicate";
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		lenient().when(requestPathInfo.getSelectors()).thenReturn(selectors);
		when(response.getWriter()).thenReturn(printWriter);
		catlogUpdateServlet.doPost(request, response);
	}

}