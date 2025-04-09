package com.bdb.aem.core.servlets;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.jcr.JcrConstants;
//import org.springframework.test.util.ReflectionTestUtils;
import com.day.cq.wcm.api.Page;

/**
 * The Class GetStatesFromCountryServletTest.
 */
@ExtendWith(MockitoExtension.class)
class GetStatesFromCountryServletTest {
	
	/** The get countries from region servlet. */
	@InjectMocks
	GetStatesFromCountryServlet getStatesFromCountryServlet;


	/** The reader. */
	@Mock
	BufferedReader reader;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The response. */
	@Mock
	SlingHttpServletResponse response;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The jcr resource. */
	@Mock
	Resource countryListRes, listResource, resource, countryStateResource, childResource, countryListResource,
			stateResource, jcrResource;

	/** The child items. */
	@Mock
	Iterator<Resource> items, childItems;

	/** The properties. */
	@Mock
	ValueMap properties;

	/** The print writer. */
	@Mock
	PrintWriter printWriter;

	/** The child resources. */
	@Mock
	Iterable<Resource> childResources;

	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The country page. */
	@Mock
	Page countryPage;
	
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	

	/**
	 * Inits the.
	 * 
	 * @throws LoginException
	 */
	@BeforeEach
	public void init() throws LoginException {
		when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
	}


	/**
	 * Testdo post step value 1.
	 *
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws LoginException
	 */
	@Test
	public void testdoPost() throws IOException, LoginException {
		when(request.getReader()).thenReturn(reader);
		String line = "{\"countryId\":\"us-al\"}";
		String line2 = null;
		when(reader.readLine()).thenReturn(line, line2);
		when(bdbApiEndpointService.getCountryStateDropdownEndpoint())
			.thenReturn("/etc/acs-commons/lists/bdb/country-state-mapping");
		when(resourceResolver.getResource("/etc/acs-commons/lists/bdb/country-state-mapping"))
		.thenReturn(countryListRes);
		when(countryListRes.hasChildren()).thenReturn(true);
		when(countryListRes.getChildren()).thenReturn(childResources);
		when(childResources.iterator()).thenReturn(childItems);
//		when(childItems.hasNext()).thenReturn(true, false);
		when(childItems.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(childItems.next()).thenReturn(childResource);
		when(childResource.adaptTo(Page.class)).thenReturn(countryPage);
		when(countryPage.getPath()).thenReturn("countryPagePath");
		String jcrPath = "countryPagePath" + "/jcr:content";
		when(resourceResolver.getResource(jcrPath)).thenReturn(jcrResource);
		when(jcrResource.getValueMap()).thenReturn(properties);
		when(properties.containsKey("jcr:title")).thenReturn(true);
		when(properties.get("jcr:title", String.class)).thenReturn("jcr:title");
		when(properties.containsKey("value")).thenReturn(true);
		when(properties.get("value", String.class)).thenReturn("us-al");
		when(resourceResolver.getResource("countryPagePath/jcr:content/list"))
		.thenReturn(countryListRes);
		when(countryListRes.listChildren()).thenReturn(childItems);
		when(response.getWriter()).thenReturn(printWriter);
		getStatesFromCountryServlet.doPost(request, response);
	}
}
