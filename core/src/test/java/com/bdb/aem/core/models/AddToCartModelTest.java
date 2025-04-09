package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class ImageTextModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class AddToCartModelTest {

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	@Mock
	ExternalizerService externalizerService;

	@Mock
	InheritanceValueMap pageProperties;

	/** The image text model. */
	@InjectMocks
	AddToCartModel addToCartModel;

	/** The BDB Api Endpoint Service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	@Mock
	Page currentPage;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(addToCartModel, "myCartLabels", "myCartLabels");
		PrivateAccessor.setField(addToCartModel, "myCartConfig", "myCartConfig");
	}

	/**
	 * Test all getters.
	 */
	@Test
	void testAllGetters() {
		assertNotNull(addToCartModel.getMyCartConfig());
		assertNotNull(addToCartModel.getMyCartLabels());
	}
	@Test
	void testGetters() {
		addToCartModel.getOutOfStock();
		addToCartModel.getInStock();
		addToCartModel.getDistributorDeliveryDate();
		addToCartModel.getMyCartPrintPDFLabels();
		addToCartModel.getMyQuoteLabels();
		addToCartModel.getEnableAddToQuoteCheckBox();
		addToCartModel.getMyQuoteConfig();
		addToCartModel.createMyQuoteLabels();
	}

	/**
	 * Test init.
	 * 
	 * @throws LoginException
	 */
	@Test
	void testInit() throws LoginException {
//		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn("https://api-qa1.author.com");
		lenient().when(bdbApiEndpointService.getCartWithIdentifier())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		lenient().when(bdbApiEndpointService.getDeleteEntryFromCart())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		lenient().when(bdbApiEndpointService.getSaveForLater())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		lenient().when(bdbApiEndpointService.getCreateSaveForLaterCart())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		lenient().when(bdbApiEndpointService.getAddToSaveForLater())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		lenient().when(bdbApiEndpointService.getAddToCartFromSaveToLater())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		lenient().when(bdbApiEndpointService.getDeleteSaveForLater())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		lenient().when(bdbApiEndpointService.getUpdateCartQuantity())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		lenient().when(bdbApiEndpointService.getUpdateLotIndicator())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		lenient().when(bdbApiEndpointService.getUpdatesCartVATExemptStatus())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		lenient().when(bdbApiEndpointService.getApplyCoupon())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		lenient().when(bdbApiEndpointService.getQuoteReference())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		lenient().when(bdbApiEndpointService.getGetQuote())
				.thenReturn("/occ/v2/{{siteId}}/users/{{userId}}/quote/getquotes?applicationID={{applicationId}}&quoteRefNum={{quoteNumber}}&custPurchaseOrderNumber={{custPONumber}}");
		lenient().when(bdbApiEndpointService.getQuoteToCart())
				.thenReturn("/occ/v2/{{siteId}}/users/{{userId}}/quote/quoteToCart");
		lenient().when(bdbApiEndpointService.getReplaceSaveForLaterEntry())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		lenient().when(bdbApiEndpointService.getReplaceCartEntry())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		lenient().when(bdbApiEndpointService.getDeleteCoupon())
		.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		lenient().when(bdbApiEndpointService.sendEmail())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		lenient().when(bdbApiEndpointService.getClearCart()).thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
		addToCartModel.init();
	}
}
