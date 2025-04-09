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
 * The Class AccountManagementOrdersApprovalModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class AccountManagementOrdersApprovalModelImplTest {
	
	/** The value page title. */
	String VALUE_PAGE_TITLE = "Orders Approval Test";
	
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
	
	/** The orders approval model test impl. */
	@InjectMocks
	private AccountManagementOrdersApprovalModelImpl ordersApprovalModelTestImpl;
	
	/** The context. */
	private AemContext context;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		context.addModelsForClasses(AccountManagementOrdersApprovalModelImplTest.class);
		Map<String, String> pageProperties = new HashMap<>();
		pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
		pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
		pageProperties.put("country", "US");
		pageProperties.put("region", "US");
		pageProperties.put("hybrisSiteId", "bdbUS");

		Map<String, String> grantsProperties = new HashMap<>();
		grantsProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE, "bdb-aem/components/content/accountmanagement/v1/accountmanagement");

		ordersApprovalModelTestImpl = new AccountManagementOrdersApprovalModelImpl();
		
		PrivateAccessor.setField(bdbApiEndpointService, "bdbHybrisDomain", "https://api.domain.com");
		PrivateAccessor.setField(bdbApiEndpointService, "orderDetailsApproverEndpoint", "/hybris/orderDetailsApproverEndpoint");
		PrivateAccessor.setField(bdbApiEndpointService, "orderListApproverEndpoint", "/hybris/orderListApproverEndpoint");
		PrivateAccessor.setField(bdbApiEndpointService, "orderApprovalDecisionEndpoint", "/hybris/orderApprovalDecisionEndpoint");
		PrivateAccessor.setField(bdbApiEndpointService, "updatePoNumberEndpoint", "/hybris/updatePoNumberEndpoint");
		PrivateAccessor.setField(ordersApprovalModelTestImpl, "currentPage", currentPage);
		PrivateAccessor.setField(ordersApprovalModelTestImpl, "request", request);
		PrivateAccessor.setField(ordersApprovalModelTestImpl, "bdbApiEndpointService", bdbApiEndpointService);
		ordersApprovalModelTestImpl.init();
		PrivateAccessor.setField(ordersApprovalModelTestImpl, "hybrisSiteId", "bdbUS");
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		ordersApprovalModelTestImpl.init();
	}
	
	/**
	 * Gets the orders approval list labels.
	 *
	 * @return the orders approval list labels
	 */
	@Test
	void getOrdersApprovalListLabels() {
		assertNotNull(ordersApprovalModelTestImpl.getOrdersApprovalListLabels());
	}
	
	/**
	 * Gets the order approval config.
	 *
	 * @return the order approval config
	 */
	@Test
	void getOrderApprovalConfig() {
		assertNotNull(ordersApprovalModelTestImpl.getOrderApprovalConfig());
	}
	
	/**
	 * Gets the hybris site id.
	 *
	 * @return the hybris site id
	 */
	@Test
	void getHybrisSiteId() {
		assertNotNull(ordersApprovalModelTestImpl.getHybrisSiteId());
	}
}
