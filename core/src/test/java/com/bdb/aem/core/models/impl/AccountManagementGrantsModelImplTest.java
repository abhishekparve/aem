package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.SlingHttpServletRequest;
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
 * The Class AccountManagementGrantsModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class AccountManagementGrantsModelImplTest {

	/** The value page title. */
	String VALUE_PAGE_TITLE = "Grants Test";
	
	/** The value page resource type. */
	String VALUE_PAGE_RESOURCE_TYPE = "bdb/components/structure/page";

	/** The current page. */
	@Mock
	Page currentPage;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;
	
	/** The bdb api endpoint service. */
	@InjectMocks
	BDBApiEndpointService bdbApiEndpointService = new BDBApiEndpointServiceImpl();
	
	/** The grants model test impl. */
	@InjectMocks
	private AccountManagementGrantsModelImpl grantsModelTestImpl;
	
	/** The context. */
	private AemContext context;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		context.addModelsForClasses(AccountManagementGrantsModelImplTest.class);
		Map<String, String> pageProperties = new HashMap<>();
		pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
		pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
		pageProperties.put("country", "US");
		pageProperties.put("region", "US");
		pageProperties.put("hybrisSiteId", "bdbUS");

		Map<String, String> grantsProperties = new HashMap<>();
		grantsProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE, "bdb-aem/components/content/accountmanagement/v1/accountmanagement");

		grantsModelTestImpl = new AccountManagementGrantsModelImpl();

		PrivateAccessor.setField(grantsModelTestImpl, "grantsHeader", "grantsHeader");
		PrivateAccessor.setField(grantsModelTestImpl, "shipToNumberLabel", "shipToNumberLabel");
		
		PrivateAccessor.setField(bdbApiEndpointService, "bdbHybrisDomain", "https://api.domain.com");
		PrivateAccessor.setField(bdbApiEndpointService, "getGrantsForCustomerEndpoint","/occ/{{site}}/getGrantsForCustomer");
		PrivateAccessor.setField(bdbApiEndpointService, "orderHistoryForGrantsEndpoint","/occ/{{site}}/orderHistoryForGrants");
		PrivateAccessor.setField(grantsModelTestImpl, "currentPage", currentPage);
		PrivateAccessor.setField(grantsModelTestImpl, "request", request);
		PrivateAccessor.setField(grantsModelTestImpl, "bdbApiEndpointService", bdbApiEndpointService);
		grantsModelTestImpl.init();
		PrivateAccessor.setField(grantsModelTestImpl, "hybrisSiteId", "bdbUS");
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		grantsModelTestImpl.init();
	}

	/**
	 * Gets the user grants labels.
	 *
	 * @return the user grants labels
	 */
	@Test
	void getUserGrantsLabels() {
		assertNotNull(grantsModelTestImpl.getUserGrantsLabels());
	}
	
	/**
	 * Gets the user grants config.
	 *
	 * @return the user grants config
	 */
	@Test
	void getUserGrantsConfig() {
		assertNotNull(grantsModelTestImpl.getUserGrantsConfig());
	}
	
	/**
	 * Gets the hybris site id.
	 *
	 * @return the hybris site id
	 */
	@Test
	void getHybrisSiteId() {
		assertNotNull(grantsModelTestImpl.getHybrisSiteId());
	}
}
