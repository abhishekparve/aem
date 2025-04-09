package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
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
 * The Class AccountManagementCommunicationSettingsModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class AccountManagementCommunicationSettingsModelImplTest {
	
	/** The value page title. */
	String VALUE_PAGE_TITLE = "Communications Settings Test";
	
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
	
	/** The communication model test impl. */
	@InjectMocks
	private AccountManagementCommunicationSettingsModelImpl communicationModelTestImpl;
	
	/** The communications list multi field. */
	private List<Resource> communicationsListMultiField;
	
	/** The communications list resource. */
	private Resource communicationsListResource;
	
	/** The context. */
	private AemContext context;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		context.addModelsForClasses(AccountManagementCommunicationSettingsModelImplTest.class);
		Map<String, String> pageProperties = new HashMap<>();
		pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
		pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
		pageProperties.put("country", "US");
		pageProperties.put("region", "US");
		pageProperties.put("hybrisSiteId", "bdbUS");
		
		Map<String, String> communicationListProperty = new HashMap<>();		
		communicationListProperty.put("id", "TEST_CODE");
		communicationListProperty.put("label", "Test");
		communicationsListResource = context.create().resource("/root/account/certificate", communicationListProperty);
		communicationsListMultiField = Arrays.asList(communicationsListResource);

		Map<String, String> communicationProperties = new HashMap<>();
		communicationProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE, "bdb-aem/components/content/accountmanagement/v1/accountmanagement");

		communicationModelTestImpl = new AccountManagementCommunicationSettingsModelImpl();
		
		PrivateAccessor.setField(bdbApiEndpointService, "bdbHybrisDomain", "https://api.domain.com");
		PrivateAccessor.setField(bdbApiEndpointService, "notificationEndpoint", "/hybris/{{baseSiteId}}/notificationEndpoint");
		PrivateAccessor.setField(communicationModelTestImpl, "currentPage", currentPage);
		PrivateAccessor.setField(communicationModelTestImpl, "request", request);
		PrivateAccessor.setField(communicationModelTestImpl, "bdbApiEndpointService", bdbApiEndpointService);
		communicationModelTestImpl.init();
		PrivateAccessor.setField(communicationModelTestImpl, "hybrisSiteId", "bdbUS");
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		communicationModelTestImpl.init();
	}
	
	/**
	 * Test get communication settings labels.
	 */
	@Test
	void testGetCommunicationSettingsLabels() {
		assertNotNull(communicationModelTestImpl.getCommunicationSettingsLabels());
	}

	/**
	 * Test get communication settings config.
	 */
	@Test
	void testGetCommunicationSettingsConfig() {
		assertNotNull(communicationModelTestImpl.getCommunicationSettingsConfig());
	}
	
	/**
	 * Gets the hybris site id.
	 *
	 * @return the hybris site id
	 */
	@Test
	void getHybrisSiteId() {
		assertNotNull(communicationModelTestImpl.getHybrisSiteId());
	}
}
