package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.SlingConstants;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.impl.BDBApiEndpointServiceImpl;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class AccountManagementQuotesModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class AccountManagementQuotesModelImplTest {
	
	/** The value page title. */
	String VALUE_PAGE_TITLE = "SAMPLE PAGE TITLE";
	
	/** The value page resource type. */
	String VALUE_PAGE_RESOURCE_TYPE = "bdb/components/structure/page";
	
	/** The quotes test model. */
	@InjectMocks
	AccountManagementQuotesModelImpl quotesTestModel;

	/** The bdb api endpoint service. */
	@InjectMocks
	BDBApiEndpointService bdbApiEndpointService = new BDBApiEndpointServiceImpl();
	
	/** The current page. */
	@Mock
	Page currentPage;
	
	/** The context. */
	private AemContext context;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		context.addModelsForClasses(AccountManagementQuotesModelImpl.class);
		Map<String, String> pageProperties = new HashMap<>();
		pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
		pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
		pageProperties.put("hybrisSiteId", "bdbUS");
		
		Map<String, String> quoteProperties = new HashMap<>();
		quoteProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE, "bdb-aem/components/content/accountmanagement/v1/accountmanagement");
		quotesTestModel = new AccountManagementQuotesModelImpl();
		
		PrivateAccessor.setField(bdbApiEndpointService, "bdbHybrisDomain", "https://api.domain.com");
		PrivateAccessor.setField(bdbApiEndpointService, "getPaymentsEndpoint", "/hybris/{{site}}/getPaymentsEndpoint");
		PrivateAccessor.setField(quotesTestModel, "bdbApiEndpointService", bdbApiEndpointService);
		quotesTestModel.init();
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		quotesTestModel.init();
	}
	
	/**
	 * Test get quotes labels.
	 */
	@Test
	void testGetQuotesLabels() {
		assertNotNull(quotesTestModel.getQuotesLabels());
	}
	
	/**
	 * Test get quotes config.
	 */
	@Test
	void testGetQuotesConfig() {
		assertNotNull(quotesTestModel.getQuotesConfig());
	}
}
