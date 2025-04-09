package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.lenient;

import com.bdb.aem.core.services.BDBApiEndpointService;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.util.CommonHelper;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class CheckoutModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CheckoutModelTest {

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The image text model. */
	@InjectMocks
	CheckoutModel checkoutModel;

	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	private String hybrisSiteId="siteId";

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(checkoutModel, "checkoutLabels", "checkoutLabels");
		PrivateAccessor.setField(checkoutModel,"hybrisSiteId",hybrisSiteId);
		PrivateAccessor.setField(checkoutModel,"checkOutOtConsentDataPayload","{\"key\":\"value\"}");
	}

	/**
	 * Test all getters.
	 */
	@Test
	void testAllGetters() {
		assertNotNull(checkoutModel.getCheckoutLabels());
	}

	/**
	 * Test init.
	 * 
	 * @throws LoginException
	 */
	@Test
	void testInit() throws LoginException {
		checkoutModel.init();
		checkoutModel.getCheckoutConfig();
		checkoutModel.getCheckoutLabels();
		checkoutModel.getAddAddressLabels();
		assertNotNull(checkoutModel.getAddAddressLabels());
	}

}
