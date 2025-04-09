package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.lenient;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.service.component.annotations.Reference;

import com.bdb.aem.core.services.SpectrumViewerConfigService;

/**
 * The Class AnonymousTokenToFETest.
 */
@ExtendWith(MockitoExtension.class)
public class SvgIconsDynamicDropDownServletTest {
	/** Servlet sso login. */
	@InjectMocks
	SvgIconsDynamicDropDownServlet svgIconsDynamicDropDownServlet;

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
		lenient().when(spectrumViewerConfigService.getSpectrumViewerIconsPath()).thenReturn("/path");
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(response.getWriter()).thenReturn(printWriter);
		svgIconsDynamicDropDownServlet.doGet(request, response);
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
		svgIconsDynamicDropDownServlet.doGet(request, response);
	}
}
	
	
