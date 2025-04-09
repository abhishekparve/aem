package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
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

import com.bdb.aem.core.services.SpectrumViewerConfigService;

/**
 * The Class AnonymousTokenToFETest.
 */
@ExtendWith(MockitoExtension.class)
public class CytometerDropDownServletTest {
	/** Servlet sso login. */
	@InjectMocks
	CytometerDropDownServlet cytometerDropDownServlet;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The response. */
	@Mock
	SlingHttpServletResponse response;

	/** The print writer. */
	@Mock
	PrintWriter printWriter;
	
	
	/** The items list. */
	@Mock
	List<Resource> resourceList;
	
	
	/** The items list. */
	@Mock
	Iterator<Resource> iterator;

	/** The resource resolver. */
	@Mock
	private ResourceResolver resourceResolver;

	/** The requestPathInfo. */
	@Mock
	RequestPathInfo requestPathInfo;

	/** The resource resolver. */
	@Mock
	private Session session;

	/** The resource resolver factory. */
	@Mock
	@Reference
	transient ResourceResolverFactory resourceResolverFactory;
	
	@Mock
	@Reference
	transient SpectrumViewerConfigService spectrumViewerConfigService;
	
	/** The resource. */
	@Mock
	Resource resource;

	
	@BeforeEach
	public void setUp() throws IOException {
		lenient().when(resourceResolver.getResource("page/path")).thenReturn(resource);
		lenient().when(resource.listChildren()).thenReturn(iterator);
		resourceList.add(resource);
	}

	/**
	 * Test do get.
	 *
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws LoginException the login exception
	 */
	@Test
	public void testDoGet() throws IOException, LoginException {
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(response.getWriter()).thenReturn(printWriter);
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(request.getResource()).thenReturn(resource);
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		lenient().when(request.getRequestPathInfo().getSuffix()).thenReturn("/content/path");
		when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		cytometerDropDownServlet.doGet(request, response);
	}

	/**
	 * Test login exception.
	 *
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws LoginException the login exception
	 */
	@Test
	public void testLoginException() throws IOException, LoginException {
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(request.getResource()).thenReturn(resource);
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		lenient().when(request.getRequestPathInfo().getSuffix()).thenReturn("/content/path");
		when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		cytometerDropDownServlet.doGet(request, response);
	}
}
	
	
