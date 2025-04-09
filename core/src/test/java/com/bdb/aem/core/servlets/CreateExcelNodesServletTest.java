package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.service.component.annotations.Reference;

import com.bdb.aem.core.services.CreateExcelNodeService;

/**
 * The Class CreateExcelNodesServletTest.
 */
@ExtendWith(MockitoExtension.class)
class CreateExcelNodesServletTest {

	/** Servlet sso login. */
	@InjectMocks
	CreateExcelNodesServlet createExcelNodesServlet;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The response. */
	@Mock
	SlingHttpServletResponse response;

	/** The print writer. */
	@Mock
	PrintWriter printWriter;

	/** The sSO Login service. */
	@Mock
	CreateExcelNodeService createExcelNodeService;

	/** The resource resolver. */
	@Mock
	private ResourceResolver resourceResolver;

	/** The resource resolver. */
	@Mock
	private Session session;

	/** The resource resolver factory. */
	@Mock
	@Reference
	transient ResourceResolverFactory resourceResolverFactory;

	/**
	 * Inits the.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@BeforeEach
	public void init() throws IOException {
		HashMap<String, String[]> hashMap = new HashMap<>();
		String[] queryParams = { "testValue" };
		hashMap.put("testKey", queryParams);
		String[] queryParams1 = { "test1" };
		hashMap.put("key1", queryParams1);
		String[] queryParams2 = { "test2" };
		hashMap.put("key2", queryParams2);
		lenient().when(request.getParameterMap()).thenReturn(hashMap);

	}

	/**
	 * Test do get.
	 *
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws LoginException the login exception
	 */
	@Test
	public void testDoGet() throws IOException, LoginException {
		when(request.getResourceResolver()).thenReturn(resourceResolver);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(response.getWriter()).thenReturn(printWriter);
		createExcelNodesServlet.doPost(request, response);
	}

	/**
	 * Test login exception.
	 *
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws LoginException the login exception
	 */
	@Test
	public void testLoginException() throws IOException, LoginException {
		when(request.getResourceResolver()).thenReturn(resourceResolver);
		createExcelNodesServlet.doPost(request, response);
	}
}
