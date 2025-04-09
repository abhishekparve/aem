package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.impl.BDBApiEndpointServiceImpl;
import com.day.cq.search.QueryBuilder;
import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

/**
 * The Class OrderLookupModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class OrderLookupModelImplTest {

	/** The order lookup model impl. */
	@InjectMocks
	private OrderLookupModelImpl orderLookupModelImpl;

	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The resource resolver. */
	@Mock
	private ResourceResolver resourceResolver;

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/** The sling settings service. */
	@Mock
	SlingSettingsService slingSettingsService;

	/** The current page. */
	@Mock
	Page currentPage;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(orderLookupModelImpl, "orderLookUpLabels", "orderLookUpLabels");
		PrivateAccessor.setField(orderLookupModelImpl, "orderLookUpConfig", "orderLookUpConfig");
	}

	/**
	 * Test init.
	 */
	@Test
	void testInit() {
		lenient().when(bdbApiEndpointService.environmentType()).thenReturn("stage");
		lenient().when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn("https://domain");
		orderLookupModelImpl.init();
	}

	/**
	 * Test get order lookup config.
	 */
	@Test
	void testGetOrderLookupConfig() {
		assertNotNull(orderLookupModelImpl.getOrderLookupConfig());
	}

	/**
	 * Test get order lookup labels.
	 */
	@Test
	void testGetOrderLookupLabels() {
		assertNotNull(orderLookupModelImpl.getOrderLookupLabels());
	}
}
