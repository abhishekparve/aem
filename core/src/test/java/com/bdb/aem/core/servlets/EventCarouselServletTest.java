package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.lenient;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.servlethelpers.MockRequestPathInfo;
import org.apache.sling.servlethelpers.MockSlingHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.util.CommonHelper;
import com.google.common.collect.ImmutableMap;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

// TODO: Auto-generated Javadoc
/**
 * The Class EventCarouselServletTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class EventCarouselServletTest {

	/** The value page resource type. */
	String VALUE_PAGE_RESOURCE_TYPE = "bdb/components/structure/page";

	/** The value template. */
	String VALUE_TEMPLATE = "/conf/bdb/settings/wcm/templates/content-page";

	/** The value page title. */
	String VALUE_PAGE_TITLE = "SAMPLE PAGE TITLE";

	/** The event carousel servlet. */
	@InjectMocks
	private EventCarouselServlet eventCarouselServlet;

	/** The request. */
	MockSlingHttpServletRequest request;

	/** The response. */
	@Mock
	SlingHttpServletResponse response;
	
	@Mock
	SlingHttpServletRequest request1;

	/** The print writer. */
	@Mock
	PrintWriter printWriter;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The resource. */
	@Mock
	Resource resource;

	/** The it. */
	@Mock
	Iterator<Resource> it;

	/**
	 * Setup.
	 */
	@BeforeEach
	void setup() {

	}

	/**
	 * Test do get blog.
	 *
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws LoginException the login exception
	 */
	@Test
	void testDoGetBlog() throws IOException, LoginException {
		request = new MockSlingHttpServletRequest(resourceResolver);
		MockRequestPathInfo requestPathInfo = (MockRequestPathInfo) request.getRequestPathInfo();
		requestPathInfo.setSelectorString("selector1.selector2");
		requestPathInfo.setExtension("html");
		requestPathInfo.setResourcePath("/content/sample");
		request.setQueryString("parentPagePath=parent/Page/Path&type=child");
		request.setParameterMap(ImmutableMap.<String, Object>builder().put("parentPagePath", "parent/Page/Path")
				.put("type", "blog").put("ctaLabel", "CTA").build());

		request.setResource(resourceResolver.getResource("/content/sample"));
		request.setAuthType("tockenId");
		request.setAttribute("attr1", "value1");
		request.addHeader("header1", "value1");
		request.setPathInfo(requestPathInfo.toString());
		lenient().when(response.getWriter()).thenReturn(printWriter);
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(resourceResolver.getResource("" + "/jcr:content/root")).thenReturn(resource);
		lenient().when(resource.listChildren()).thenReturn(it);
		lenient().when(it.hasNext()).thenReturn(true, true, false);
		lenient().when(it.next()).thenReturn(resource);
		lenient().when(resource.getResourceType()).thenReturn("bdb-aem/proxy/components/content/eventblogDetails");
		eventCarouselServlet.doGet(request, response);
	}

	/**
	 * Test do get event.
	 *
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws LoginException the login exception
	 */
	@Test
	void testDoGetEvent() throws IOException, LoginException {
		request = new MockSlingHttpServletRequest(resourceResolver);
		MockRequestPathInfo requestPathInfo = (MockRequestPathInfo) request.getRequestPathInfo();
		requestPathInfo.setSelectorString("selector1.selector2");
		requestPathInfo.setExtension("html");
		requestPathInfo.setResourcePath("/content/sample");
		request.setQueryString("parentPagePath=parent/Page/Path&type=child");
		request.setParameterMap(ImmutableMap.<String, Object>builder().put("parentPagePath", "parent/Page/Path")
				.put("type", "event").put("ctaLabel", "CTA").build());

		request.setResource(resourceResolver.getResource("/content/sample"));
		request.setAuthType("tockenId");
		request.setAttribute("attr1", "value1");
		request.addHeader("header1", "value1");
		request.setPathInfo(requestPathInfo.toString());
		lenient().when(response.getWriter()).thenReturn(printWriter);
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(resourceResolver.getResource("" + "/jcr:content/root")).thenReturn(resource);
		lenient().when(resource.listChildren()).thenReturn(it);
		lenient().when(it.hasNext()).thenReturn(true, true, false);
		lenient().when(it.next()).thenReturn(resource);
		lenient().when(resource.getResourceType()).thenReturn("bdb-aem/proxy/components/content/eventblogDetails");
		lenient().when(resource.getChild("multifieldSection")).thenReturn(resource);
		lenient().when(resource.listChildren()).thenReturn(it);
		lenient().when(it.hasNext()).thenReturn(true);
		eventCarouselServlet.doGet(request, response);
	}


	/**
	 * Test do get blog null.
	 *
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws LoginException the login exception
	 */
	@Test
	void testDoGetBlogNull() throws IOException, LoginException {
		request = new MockSlingHttpServletRequest(resourceResolver);
		MockRequestPathInfo requestPathInfo = (MockRequestPathInfo) request.getRequestPathInfo();
		requestPathInfo.setSelectorString("selector1.selector2");
		requestPathInfo.setExtension("html");
		requestPathInfo.setResourcePath("/content/sample");
		request.setQueryString("parentPagePath=parent/Page/Path&type=child");
		request.setParameterMap(ImmutableMap.<String, Object>builder().put("parentPagePath", "parent/Page/Path")
				.put("type", "blog").put("ctaLabel", "CTA").build());

		request.setResource(resourceResolver.getResource("/content/sample"));
		request.setAuthType("tockenId");
		request.setAttribute("attr1", "value1");
		request.addHeader("header1", "value1");
		request.setPathInfo(requestPathInfo.toString());
		lenient().when(response.getWriter()).thenReturn(printWriter);
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(null);
		eventCarouselServlet.doGet(request, response);
	}
}
