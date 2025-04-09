package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.Iterator;

import javax.jcr.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
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
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class BeadlotLinkOnPdpModelImpl.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class BeadlotLinkOnPdpModelImplTest {

	/**
	 * The resource resolver.
	 */
	@Mock
	ResourceResolver resourceResolver;

	/**
	 * The resolverFactory.
	 */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The page model. */
	@InjectMocks
	BeadlotLinkOnPdpModelImpl beadlotLinkOnPdpModelImpl;

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The request path. */
	@Mock
	RequestPathInfo requestPath;

	/** The query builder. */
	@Mock
	QueryBuilder queryBuilder;

	/** The query. */
	@Mock
	private Query query;

	/** The search result. */
	@Mock
	private SearchResult searchResult;

	/** The result count. */
	private Long resultCount = 1L;

	/** The session. */
	@Mock
	private Session session;

	/** The resources. */
	@Mock
	Iterator<Resource> resources;

	/** The resource. */
	@Mock
	Resource resource;

	/** The properties. */
	@Mock
	ValueMap properties;

	/** The SolrSearchService. */
	@Mock
	SolrSearchService solrSearchService;
		
	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	
	@Mock
	Object obj;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		// when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		PrivateAccessor.setField(beadlotLinkOnPdpModelImpl, "headerText", "headerText");
		PrivateAccessor.setField(beadlotLinkOnPdpModelImpl, "noBeadlotText", "noBeadlotText");
		PrivateAccessor.setField(beadlotLinkOnPdpModelImpl, "landingPageUrl", "landingPageUrl");
		PrivateAccessor.setField(beadlotLinkOnPdpModelImpl, "titleOfBeadlotFile", "titleOfBeadlotFile");
		PrivateAccessor.setField(beadlotLinkOnPdpModelImpl, "hasBeadlot", true);
		PrivateAccessor.setField(beadlotLinkOnPdpModelImpl, "viewAllText", "viewAllText");

	}

	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitSuccess() throws LoginException {

		String[] a = { "a", "b" };
		
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(bdbApiEndpointService.getCustomRunMode()).thenReturn("author");
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPath);
		lenient().when(requestPath.getSelectors()).thenReturn(a);
		lenient().when(solrSearchService.getHpNodeResource("b", "us", resourceResolver)).thenReturn(resource);
		lenient().when(resource.adaptTo(ValueMap.class)).thenReturn(properties);
		lenient().when(properties.get("hasBeadlotFiles", StringUtils.EMPTY)).thenReturn("test");
		lenient().when(properties.get("beadlotTitle", StringUtils.EMPTY)).thenReturn("test1");
		lenient().when(request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH)).thenReturn(obj);
		lenient().when(request.getAttribute("catalogNumber")).thenReturn(obj);
		lenient().when(obj.toString()).thenReturn("/content/bdb");
		// when(resource.getParent()).thenReturn(resource);
		// when(resource.getChild("/content/bdb")).thenReturn(resource);
		beadlotLinkOnPdpModelImpl.setTitleOfBeadlotFile(resource);
		beadlotLinkOnPdpModelImpl.init();

	}

	/**
	 * Test init exception.
	 *
	 * @throws LoginException the login exception
	 */
	// @Test
	void testInitException() throws LoginException {
		/*
		 * String[] a = { "a", "b" };
		 * when(CommonHelper.getServiceResolver(resolverFactory)).thenThrow(new
		 * LoginException());
		 * when(request.getRequestPathInfo()).thenReturn(requestPath);
		 * when(requestPath.getSelectors()).thenReturn(a);
		 * beadlotLinkOnPdpModelImpl.init();
		 */
	}

	/**
	 * Test get not null methods.
	 */
	@Test
	void testGetNotNullMethods() {
		assertNotNull(beadlotLinkOnPdpModelImpl.getHeaderText());
		assertNotNull(beadlotLinkOnPdpModelImpl.getNoBeadlotText());
		assertNotNull(beadlotLinkOnPdpModelImpl.getLandingPageUrl());
		assertNotNull(beadlotLinkOnPdpModelImpl.getHasBeadlot());
		assertNotNull(beadlotLinkOnPdpModelImpl.getTitleOfBeadlotFile());
		assertNotNull(beadlotLinkOnPdpModelImpl.getViewAllText());
	}

	// @Test
	void testGeftNotNullMethods() {
		// beadlotLinkOnPdpModelImpl
	}

	@Test
	void testgetLandingPageUrlElse() throws NoSuchFieldException {
		/*
		 * PrivateAccessor.setField(beadlotLinkOnPdpModelImpl, "hasBeadlot", false);
		 * assertNotNull(beadlotLinkOnPdpModelImpl.getLandingPageUrl());
		 */
	}
}