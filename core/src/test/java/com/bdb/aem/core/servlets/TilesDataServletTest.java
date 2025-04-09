package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.servlethelpers.MockRequestPathInfo;
import org.apache.sling.servlethelpers.MockSlingHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.EventBlogModel;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.google.common.collect.ImmutableMap;

/**
 * The Class TilesDataServletTest.
 */
@ExtendWith({ MockitoExtension.class })
class TilesDataServletTest {

	/** The tiles data servlet. */
	@InjectMocks
	TilesDataServlet tilesDataServlet;

	/** The request. */
	MockSlingHttpServletRequest request;

	/** The SlingHttpServletResponse. */
	@Mock
	SlingHttpServletResponse response;

	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The resource. */
	@Mock
	Resource resource;

	/** The items list. */
	@Mock
	Iterator<Resource> itemsList;

	/** The print writer. */
	@Mock
	PrintWriter printWriter;

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/** The event blog model. */
	@Mock
	EventBlogModel eventBlogModel;

	/** The write service auth. */
	Map<String, Object> writeServiceAuth;
	
	/** The PagemManager. */
	@Mock
	PageManager pageManager;
	
	/** The Page. */
	@Mock
	Page page;
	
	@Mock
	ValueMap parentPageValueMap;
	
	
	@Mock
	Iterator<Page> rootPageIterator;

	/**
	 * Sets the up.
	 *
	 * @throws LoginException the login exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@BeforeEach
	void setUp() throws LoginException, IOException {
		request = new MockSlingHttpServletRequest(resourceResolver);
		MockRequestPathInfo requestPathInfo = (MockRequestPathInfo) request.getRequestPathInfo();
		requestPathInfo.setSelectorString("selector1.selector2");
		requestPathInfo.setExtension("html");
		requestPathInfo.setResourcePath("/content/sample");
		request.setQueryString("parentPagePath=parent/Page/Path&type=child");
		request.setParameterMap(ImmutableMap.<String, Object>builder().put("parentPagePath", "parent/Page/Path")
				.put("type", "child").put("ctaLabel", "CTA").build());

		request.setResource(resourceResolver.getResource("/content/sample"));
		request.setAuthType("tockenId");
		request.setAttribute("attr1", "value1");
		request.addHeader("header1", "value1");
		request.setPathInfo(requestPathInfo.toString());

		writeServiceAuth = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, "readService");
		ValueMap valueMap = Mockito.mock(ValueMap.class);
	
		when(resolverFactory.getServiceResourceResolver(writeServiceAuth)).thenReturn(resourceResolver);
		when(response.getWriter()).thenReturn(printWriter);
		when(resourceResolver.getResource(Mockito.anyString())).thenReturn(resource);
		//when(resourceResolver.getResource("page/path")).thenReturn(resource);
		when(resource.listChildren()).thenReturn(itemsList);
		when(itemsList.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(itemsList.next()).thenReturn(resource);
		when(resource.getValueMap()).thenReturn(valueMap);
		//when(valueMap.get("jcr:primaryType")).thenReturn("cq:Page");
		when(valueMap.get(Mockito.anyString())).thenReturn("cq:Page");
		when(resource.getChild("jcr:content")).thenReturn(resource);
		when(resource.getChild("root")).thenReturn(resource);
		when(resource.getResourceType()).thenReturn("bdb-aem/proxy/components/content/eventblogDetails");
		
		//when(resource.adaptTo(EventBlogModel.class)).thenReturn(eventBlogModel);
		
		when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		when(pageManager.getPage(Mockito.anyString())).thenReturn(page);
		when(page.listChildren(null, true)).thenReturn(rootPageIterator);
		when(rootPageIterator.hasNext()).thenReturn(true).thenReturn(false);
		when(rootPageIterator.next()).thenReturn(page);
		when(page.getPath()).thenReturn("childPagePath");

	}

	/**
	 * Test with blog.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//@Test
	void testWithBlog() throws IOException {
		String content = "{\r\n" + "	\"facets\": [{\r\n" + "		\"value\": \"events\"\r\n" + "	}],\r\n"
				+ "	\"parentPagePath\": \"page/path\",\r\n" + "	\"type\": \"blog\",\r\n"
				+ "	\"deactivationDate\": \"2018-01-09T11:11:02.0+03:00\",\r\n" + "	\"ctaLabel\": \"ctaLabel\",\r\n"
				+ "	\"timeout\": 5000,\r\n" + "	\"isEnable\": true\r\n" + "}";
		request.setContent(content.getBytes());

		when(eventBlogModel.getSelection()).thenReturn("blog");
		when(eventBlogModel.getBlogTopic1()).thenReturn("topic1");
		when(eventBlogModel.getBlogTopic2()).thenReturn("topic2");
		when(eventBlogModel.getBlogTopic3()).thenReturn("topic3");
		when(eventBlogModel.getBannerThumbnailImageBlog()).thenReturn("urlimage");
		when(eventBlogModel.getBannerImageAltBlog()).thenReturn("altText");
		when(eventBlogModel.getBannerTitleBlog()).thenReturn("heading");
		when(eventBlogModel.getBannerSubTitle()).thenReturn("subHeading");
		when(eventBlogModel.getBlogDate()).thenReturn("2020-11-09T11:11:02.0+03:00");
		tilesDataServlet.doPost(request, response);
	}

	/**
	 * Test with event.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testWithEvent() throws IOException {
		String content = "{\r\n" + "	\"facets\": [{\r\n" + "		\"value\": \"topics\",\r\n"
				+ "		\"label\": \"code\"\r\n" + "	}],\r\n" + "	\"parentPagePath\": \"page/path\",\r\n"
				+ "	\"type\": \"event\",\r\n" + "	\"deactivationDate\": \"2018-01-09T11:11:02.0+03:00\",\r\n"
				+ "	\"tagList\": \"home-page\",\r\n"
				+ "	\"ctaLabel\": \"ctaLabel\",\r\n" + "	\"timeout\": 5000,\r\n" + "	\"isEnable\": true\r\n" + "}";
		request.setContent(content.getBytes());

		/*
		 * when(eventBlogModel.getSelection()).thenReturn("event");
		 * when(eventBlogModel.getEventTopic()).thenReturn("eventTopic");
		 * when(eventBlogModel.getEventType()).thenReturn("public");
		 * when(eventBlogModel.getBannerThumbnailImage()).thenReturn("urlimage");
		 * when(eventBlogModel.getBannerImageAlt()).thenReturn("altText");
		 * when(eventBlogModel.getBannerTitle()).thenReturn("heading");
		 * when(eventBlogModel.getSubHeading()).thenReturn("subHeading");
		 * when(eventBlogModel.getEventStartDate()).thenReturn(
		 * "2021-02-09T11:11:02.0+03:00");
		 * when(eventBlogModel.getEventEndDate()).thenReturn(
		 * "2021-11-09T11:11:02.0+03:00");
		 */
		when(resourceResolver.getResource("page/path")).thenReturn(resource);
		when(resource.getResourceType()).thenReturn("bdb-aem/proxy/components/content/tilescomponent");
	//	when(parentPageValueMap.get(Mockito.anyString())).thenReturn("checkboxvalue");
		
					
		tilesDataServlet.doPost(request, response);
	}

	/**
	 * Test constructing facet jsons for date.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//@Test
	void testConstructingFacetJsonsForDate() throws IOException {
		String content = "{\r\n" + "	\"facets\": [{\r\n" + "		\"value\": \"dates\",\r\n"
				+ "		\"label\": \"code\"\r\n" + "	}],\r\n" + "	\"parentPagePath\": \"page/path\",\r\n"
				+ "	\"type\": \"event\",\r\n" + "	\"deactivationDate\": \"2018-01-09T11:11:02.0+03:00\",\r\n"
				+ "	\"tagList\": \"non-home-page\",\r\n"
				+ "	\"ctaLabel\": \"ctaLabel\",\r\n" + "	\"timeout\": 5000,\r\n" + "	\"isEnable\": true\r\n" + "}";
		request.setContent(content.getBytes());
		when(eventBlogModel.getSelection()).thenReturn("blog");

		tilesDataServlet.doPost(request, response);
	}
}
