package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.Iterator;

import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;

import junitx.util.PrivateAccessor;

/**
 * The Class PDPTemplateModelTest.
 */
@ExtendWith({ MockitoExtension.class })
class PDPTemplateModelTest {

	/** The pdp template model. */
	@InjectMocks
	PDPTemplateModel pdpTemplateModel;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The query. */
	@Mock
	Query query;

	/** The session. */
	@Mock
	Session session;

	/** The query builder. */
	@Mock
	QueryBuilder queryBuilder;

	/** The request path info. */
	@Mock
	RequestPathInfo requestPathInfo;
	
	/** The search result. */
	@Mock
	SearchResult searchResult;

	/** The hp node resource. */
	@Mock
	Resource nextResource, hpNodeResource;
	/** The resources. */
	@Mock
	Iterator<Resource> resources;

	/** The value map. */
	@Mock
	ValueMap valueMap;

	/** The hp resource. */
	@Mock
	Resource hpResource;

	/** The solr search service. */
	@Mock
	SolrSearchService solrSearchService;

	/** The selectors. */
	String[] selectors = { "test", "2524525" };
	
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		
		PrivateAccessor.setField(pdpTemplateModel, "templateId","SFA");
	}
		

	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInit() throws LoginException {
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		lenient().when(requestPathInfo.getSelectors()).thenReturn(selectors);
		lenient().when(request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH)).thenReturn("/content/commerce");
		lenient().when(resourceResolver.getResource("/content/commerce")).thenReturn(hpNodeResource);
		assertNotNull(hpNodeResource);
		lenient().when(hpNodeResource.getValueMap()).thenReturn(valueMap);
		lenient().when(valueMap.get("pdpTemplate", StringUtils.EMPTY)).thenReturn("SFA");
		pdpTemplateModel.init();

	}
	
	/**
	 * Test all getters.
	 */
	@Test
    void testAllGetters() {
		 assertNotNull(pdpTemplateModel.getTemplateId());
	}

}
