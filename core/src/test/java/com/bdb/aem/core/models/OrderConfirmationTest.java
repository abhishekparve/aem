package com.bdb.aem.core.models;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.day.cq.wcm.api.Page;

/**
 * The Class OrderConfirmationTest.
 */
@ExtendWith({ MockitoExtension.class })
public class OrderConfirmationTest {

	/** The order confirmation model. */
	@InjectMocks
	OrderConfirmationModel orderConfirmationModel;

	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The current page. */
	@Mock
	Page currentPage;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInit() throws LoginException {
		orderConfirmationModel.init();
	}

	/**
	 * Test getter.
	 */
	@Test
	void testGetter() {
		orderConfirmationModel.getAddressDetails();
		orderConfirmationModel.getInfoIcon();
		orderConfirmationModel.getMessage();
		orderConfirmationModel.getProducts();
		orderConfirmationModel.getOrderconfirmationConfig();
		orderConfirmationModel.getOrderConfirmationLabel();
	}
}
