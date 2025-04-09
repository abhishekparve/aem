package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.impl.BDBApiEndpointServiceImpl;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The Class OrderInquiryModelImplTest.
 */
@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class OrderInquiryModelImplTest {

	/**
	 * The value page title.
	 */
	String VALUE_PAGE_TITLE = "Dashboard Test";

	/**
	 * The value page resource type.
	 */
	String VALUE_PAGE_RESOURCE_TYPE = "bdb/components/structure/page";

	/**
	 * The dashboard banner model test impl.
	 */
	@InjectMocks
	private OrderInquiryModelImpl orderInquiryModel;

	/** The bdb api endpoint service. */
	@InjectMocks
	BDBApiEndpointService bdbApiEndpointService = new BDBApiEndpointServiceImpl();


	/** The current page. */
	@Mock
	Page currentPage;

	/**
	 * The context.
	 */
	private AemContext context;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		context.addModelsForClasses(DashboardBannerModelImplTest.class);
		Map<String, String> pageProperties = new HashMap<>();
		pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
		pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
		pageProperties.put("country", "US");
		pageProperties.put("region", "US");
		pageProperties.put("hybrisSiteId", "bdbUS");

		Map<String, String> grantsProperties = new HashMap<>();
		grantsProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE, "bdb-aem/components/content/accountmanagement/v1/accountmanagement");

		orderInquiryModel = new OrderInquiryModelImpl();

		PrivateAccessor.setField(orderInquiryModel, "contactUsLabel", "contactUsLabel");

		PrivateAccessor.setField(bdbApiEndpointService, "bdbHybrisDomain", "https://api.domain.com");
		PrivateAccessor.setField(bdbApiEndpointService, "getOrderInquiryDetailEndpoint", "/occ/{{site}}/getOrderInquiryDetailEndpoint");
		PrivateAccessor.setField(bdbApiEndpointService, "postOrderInquiryDetailsEndpoint", "/occ/{{site}}/postOrderInquiryDetailsEndpoint");

		PrivateAccessor.setField(orderInquiryModel, "bdbApiEndpointService", bdbApiEndpointService);

		orderInquiryModel.init();
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		orderInquiryModel.init();
	}

	/**
	 * Gets the user Order Inquiry labels.
	 *
	 * @return the user Order Inquiry labels
	 */
	@Test
	void getOrderInquiryLabels() {
		assertNotNull(orderInquiryModel.getOrderInquiryLabels());
	}

	/**
	 * Gets the user Order Inquiry config.
	 *
	 * @return the user Order Inquiry config
	 */
	@Test
	void getOrderInquiryConfig() {
		assertNotNull(orderInquiryModel.getOrderInquiryConfig());

	}
}
