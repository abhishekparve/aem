package com.bdb.aem.core.servlets;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;

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
import org.osgi.service.component.annotations.Reference;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.FetchRequiredCompanionProduct;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;

/**
* The Class equiredCompanionProductServlet.
*/
@ExtendWith(MockitoExtension.class)
public class RequiredCompanionProductServletTest {
	
	/** The update product schema servlet. */
	@InjectMocks
	RequiredCompanionProductServlet requiredCompanionProductServlet;
	
	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The response. */
	@Mock
	SlingHttpServletResponse response;

	/** The print writer. */
	@Mock
	PrintWriter printWriter;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;
	
	@Mock
    BDBSearchEndpointService solrConfig;
	@Mock
    SolrSearchService solrSearchService;
	@Mock
    FetchRequiredCompanionProduct fetchingService;
	@Mock
	Resource hpResource;
	@Mock
	ValueMap varientValueMap;
	/**
	 * Inits the.
	 *
	 * @throws LoginException the login exception
	 * @throws IOException 
	 */
	@BeforeEach
	public void init() throws LoginException, IOException {
		when(request.getParameter("catalogNo")).thenReturn("940510");
		when(request.getParameter(CommonConstants.COUNTRY)).thenReturn("us");
		when(request.getResourceResolver()).thenReturn(resourceResolver);
		when(response.getWriter()).thenReturn(printWriter);
	}

	/**
	 * Testdo GET.
	 *
	 * @throws.
	 */
	@Test
	public void testdoGET() throws IOException {
		requiredCompanionProductServlet.doGet(request, response);
	}

}
