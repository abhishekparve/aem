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
 * The Class AccountManagementOrdersModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class AccountManagementOrdersModelImplTest {
	
	/** The value page title. */
	String VALUE_PAGE_TITLE = "Orders Test";
	
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
	
	/** The orders model test impl. */
	@InjectMocks
	private AccountManagementOrdersModelImpl ordersModelTestImpl;
	
	/** The context. */
	private AemContext context;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		context.addModelsForClasses(AccountManagementOrdersModelImplTest.class);
		Map<String, String> pageProperties = new HashMap<>();
		pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
		pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
		pageProperties.put("country", "US");
		pageProperties.put("region", "US");
		pageProperties.put("hybrisSiteId", "bdbUS");

		Map<String, String> ordersProperties = new HashMap<>();
		ordersProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE, "bdb-aem/components/content/accountmanagement/v1/accountmanagement");

		ordersModelTestImpl = new AccountManagementOrdersModelImpl();
		
		PrivateAccessor.setField(bdbApiEndpointService, "bdbHybrisDomain", "https://api.domain.com");
		PrivateAccessor.setField(bdbApiEndpointService, "getUserOrdersListEndpoint", "/hybris/getUserOrdersListEndpoint");
		PrivateAccessor.setField(bdbApiEndpointService, "getOrderDetailsEndpoint", "/hybris/getOrderDetailsEndpoint");
		PrivateAccessor.setField(bdbApiEndpointService, "getOrderPackingListEndpoint", "/hybris/getOrderPackingListEndpoint");
		PrivateAccessor.setField(bdbApiEndpointService, "cancelOrderEndpoint", "/hybris/cancelOrderEndpoint");
		PrivateAccessor.setField(ordersModelTestImpl, "currentPage", currentPage);
		PrivateAccessor.setField(ordersModelTestImpl, "request", request);
		PrivateAccessor.setField(ordersModelTestImpl, "bdbApiEndpointService", bdbApiEndpointService);
		ordersModelTestImpl.init();
		PrivateAccessor.setField(ordersModelTestImpl, "hybrisSiteId", "bdbUS");
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		ordersModelTestImpl.init();
	}
	
	/**
	 * Gets the user orders list labels.
	 *
	 * @return the user orders list labels
	 */
	@Test
	void getUserOrdersListLabels() {
		assertNotNull(ordersModelTestImpl.getUserOrdersListLabels());
	}

	/**
	 * Gets the user orders config.
	 *
	 * @return the user orders config
	 */
	@Test
	void getUserOrdersConfig() {
		assertNotNull(ordersModelTestImpl.getUserOrdersConfig());
	}
	
	/**
	 * Gets the hybris site id.
	 *
	 * @return the hybris site id
	 */
	@Test
	void getHybrisSiteId() {
		assertNotNull(ordersModelTestImpl.getHybrisSiteId());
	}

}
