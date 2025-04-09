package com.bdb.aem.core.models;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

/**
 * The Class AmountSummaryModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class AmountSummaryModelTest {

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	
	@Mock
	InheritanceValueMap pageProperties;

	/** The image text model. */
	@InjectMocks
	AmountSummaryModel amountSummaryModel;

	/** The BDB Api Endpoint Service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	/** The current page. */
	@Mock
	Page currentPage;

	@Mock
	Resource resource;
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(amountSummaryModel, "amountSummary", "amountSummary");
		PrivateAccessor.setField(amountSummaryModel, "myCartAmountSummaryConfig", "myCartAmountSummaryConfig");
		PrivateAccessor.setField(amountSummaryModel, "shipTo", "shipTo");
		PrivateAccessor.setField(amountSummaryModel, "shipToConfig", "shipToConfig");
	}

	/**
	 * Test all getters.
	 */
	@Test
	void testAllGetters() throws LoginException ,Exception{
		assertNotNull(amountSummaryModel.getAmountSummary());
		assertNotNull(amountSummaryModel.getMyCartAmountSummaryConfig());
		assertNotNull(amountSummaryModel.getShipTo());
		assertNotNull(amountSummaryModel.getShipToConfig());
		amountSummaryModel.getShipToAddressLabels();
		amountSummaryModel.getAmountSummaryForCheckout();
		amountSummaryModel.amountSummaryLabels();
		amountSummaryModel.amountSummaryCheckoutLabels();
		amountSummaryModel.getOrderConfirmationAmountSummary();
	}

	/**
	 * Test init.
	 * 
	 * @throws LoginException
	 */
	@Test
	void testInit() throws LoginException ,Exception{
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(resource.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn("https://api-qa1.author.com");
		lenient().when(bdbApiEndpointService.getUpdateVATExemptStatus())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		lenient().when(bdbApiEndpointService.getValidateMyCart())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		lenient().when(bdbApiEndpointService.getShipToAddressConfig())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		lenient().when(bdbApiEndpointService.getPunchoutTransmitRequestEndpoint())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/cxml/requestRequisition");
		lenient().when(bdbApiEndpointService.getPunchoutCancelRequestEndpoint())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/cxml/cancelRequisition");
		lenient().when(bdbApiEndpointService.getUpdateAddressConfig())
		.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}/addresses/{{addressType}}/{{addressId}}");
		PrivateAccessor.setField(amountSummaryModel, "termsOfUse", "termsOfUse");
		lenient().when(currentPage.getAbsoluteParent(4)).thenReturn(currentPage);
		lenient().when(resource.getPath()).thenReturn("/content/eu");
		lenient().when(currentPage.getProperties()).thenReturn(pageProperties);
		amountSummaryModel.init();
	}

	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	//@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		amountSummaryModel.init();
	}

}
