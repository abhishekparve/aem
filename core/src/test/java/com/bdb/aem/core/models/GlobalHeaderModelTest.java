package com.bdb.aem.core.models;

import static org.mockito.Mockito.lenient;

import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.impl.BDBApiEndpointServiceImpl;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class GlobalHeaderModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class GlobalHeaderModelTest {

	/** The global header model. */
	@InjectMocks
	GlobalHeaderModel globalHeaderModel;

	/** Mock ResourceResolverFactory. */
	@Mock
	ExternalizerService externalizerService;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The ComponentContext context *. */
	@Mock
	ComponentContext context;

	/** The bdb api endpoint service. */
	@InjectMocks
	BDBApiEndpointService bdbApiEndpointService = new BDBApiEndpointServiceImpl();
	
	@Mock
	Page currentPage;

	
	
	/**
	 * Sets things up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {

		PrivateAccessor.setField(bdbApiEndpointService, "ssoLoginPingIdDomain", "/test/ssoLoginPingIdDomain");
		PrivateAccessor.setField(bdbApiEndpointService, "ssoLoginPingIdEndpoint", "/test/ssoLoginPingIdEndpoint");
		PrivateAccessor.setField(bdbApiEndpointService, "ssoLoginResponseType", "/test/ssoLoginResponseType");
		PrivateAccessor.setField(bdbApiEndpointService, "ssoLoginClientId", "/test/ssoLoginClientId");
		PrivateAccessor.setField(bdbApiEndpointService, "ssoCustomerLoginService", "/test/ssoCustomerLoginService");
		PrivateAccessor.setField(bdbApiEndpointService, "ssoLoginScope", "/test/ssoLoginScope");
		PrivateAccessor.setField(globalHeaderModel, "orderLookupIconAltText", "orderLookupIconAltText");
		PrivateAccessor.setField(globalHeaderModel, "countryDetectionTitle", "countryDetectionTitle");
		PrivateAccessor.setField(globalHeaderModel, "countryDetectionDescription", "countryDetectionDescription");
		PrivateAccessor.setField(globalHeaderModel, "redirectButton", "redirectButton");
		PrivateAccessor.setField(globalHeaderModel, "stayHereButton", "stayHereButton");
		PrivateAccessor.setField(globalHeaderModel, "modalTitle", "modalTitle");
		PrivateAccessor.setField(globalHeaderModel, "modalDescription", "modalDescription");
		PrivateAccessor.setField(globalHeaderModel, "cancelCTALabel", "cancelCTALabel");
		PrivateAccessor.setField(globalHeaderModel, "reactivateCTALabel", "reactivateCTALabel");
		PrivateAccessor.setField(globalHeaderModel, "reactivationURL", "reactivationURL");
		PrivateAccessor.setField(globalHeaderModel, "continueShoppingModalTitle", "continueShoppingModalTitle");
		PrivateAccessor.setField(globalHeaderModel, "continueShoppingModalDescription", "continueShoppingModalDescription");
		PrivateAccessor.setField(globalHeaderModel, "proceedToCheckoutBtnLabel", "proceedToCheckoutBtnLabel");
		PrivateAccessor.setField(globalHeaderModel, "closeModalBtnLabel", "closeModalBtnLabel");
		PrivateAccessor.setField(globalHeaderModel, "expiredQuoteModalTitle", "expiredQuoteModalTitle");
		PrivateAccessor.setField(globalHeaderModel, "expiredQuoteModalDescription", "expiredQuoteModalDescription");
		PrivateAccessor.setField(globalHeaderModel, "clearCartBtnLabel", "clearCartBtnLabel");
		PrivateAccessor.setField(globalHeaderModel, "orderLookupIconUrl", "orderLookupIconUrl");
		PrivateAccessor.setField(globalHeaderModel, "orderLookupLabel", "orderLookupLabel");
		PrivateAccessor.setField(globalHeaderModel, "orderLookupUrl", "orderLookupUrl");
		PrivateAccessor.setField(globalHeaderModel, "canonicalUrl", "canonicalUrl");
		//PrivateAccessor.setField(globalHeaderModel, "currentPage", "currentPage");
		PrivateAccessor.setField(globalHeaderModel, "bdbApiEndpointService", bdbApiEndpointService);

		lenient().when(externalizerService.getFormattedUrl("urlLogoAction", resourceResolver))
				.thenReturn("/content/urlLogoAction.html");
		lenient().when(externalizerService.getFormattedUrl("urlLogo", resourceResolver))
				.thenReturn("/content/dam/logo.png");
		lenient().when(externalizerService.getFormattedUrl("urlCartAction", resourceResolver))
				.thenReturn("/content/urlCartAction.html");
		lenient().when(externalizerService.getFormattedUrl("urlCart", resourceResolver))
				.thenReturn("/content/dam/cart.png");
		lenient().when(externalizerService.getFormattedUrl("urlCountry", resourceResolver))
				.thenReturn("/content/urlCountry.html");
		lenient().when(externalizerService.getFormattedUrl("urlIcon", resourceResolver))
				.thenReturn("/content/dam/icon.png");
		lenient().when(externalizerService.getFormattedUrl("signInUrl", resourceResolver))
				.thenReturn("/content/signInUrl.html");
		lenient().when(externalizerService.getFormattedUrl("regUrl", resourceResolver))
				.thenReturn("/content/regUrl.html");
		lenient().when(externalizerService.getFormattedUrl("orderLookupIconUrl", resourceResolver))
				.thenReturn("orderLookupIconUrl.html");
		lenient().when(externalizerService.getFormattedUrl("orderLookupUrl", resourceResolver))
				.thenReturn("orderLookupUrl.html");
			/*
			 * lenient().when(externalizerService.getFormattedUrl("canonicalUrl",
			 * resourceResolver)) .thenReturn("canonicalUrl.html");
			 */
		 

	}

	/**
	 * Test init.
	 */
	@Test
	void testInit() {
		lenient().when(externalizerService.getFormattedUrl("content/bdb/test", resourceResolver))
				.thenReturn("/content/bdb");
		globalHeaderModel.getExternalizedUrl("content/bdb/test.html", resourceResolver);
		//globalHeaderModel.init();
	}
	@Test
	void testInitElse() throws Exception {
		PrivateAccessor.setField(globalHeaderModel, "urlLogoAction", "urlLogoAction");
		PrivateAccessor.setField(globalHeaderModel, "urlCartAction", "urlCartAction");
		PrivateAccessor.setField(globalHeaderModel, "urlLogoSticky", "urlLogoSticky");
		lenient().when(externalizerService.getFormattedUrl("content/bdb/test", resourceResolver))
				.thenReturn("/content/bdb");
		globalHeaderModel.getExternalizedUrl("content/bdb/test.html", resourceResolver);
		//globalHeaderModel.init();
	}

	/**
	 * Test order lookup getters.
	 */
	@Test
	void testOrderLookupGetters() {
		globalHeaderModel.getOrderLookupIconAltText();
		globalHeaderModel.getCountryDetectionTitle();
		globalHeaderModel.getCountryDetectionDescription();
		globalHeaderModel.getRedirectButton();
		globalHeaderModel.getStayHereButton();
		globalHeaderModel.getModalTitle();
		globalHeaderModel.getModalDescription();
		globalHeaderModel.getCancelCTALabel();
		globalHeaderModel.getReactivateCTALabel();
		globalHeaderModel.getReactivationURL();
		globalHeaderModel.getContinueShoppingModalTitle();
		globalHeaderModel.getContinueShoppingModalDescription();
		globalHeaderModel.getProceedToCheckoutBtnLabel();
		globalHeaderModel.getCloseModalBtnLabel();
		globalHeaderModel.getExpiredQuoteModalTitle();
		globalHeaderModel.getExpiredQuoteModalDescription();
		globalHeaderModel.getClearCartBtnLabel();
		globalHeaderModel.getOrderLookupIconUrl();
		globalHeaderModel.getOrderLookupLabel();
		globalHeaderModel.getOrderLookupUrl();
		globalHeaderModel.getUrlLogoAction();
		globalHeaderModel.getUrlLogo();
		globalHeaderModel.getUrlCart();
		globalHeaderModel.getUrlCartAction();
		globalHeaderModel.getUrlCountry();
		globalHeaderModel.getChangeLabelUrl();
		globalHeaderModel.getUrlIcon();
		globalHeaderModel.getSignInUrl();
		globalHeaderModel.getRegUrl();
		globalHeaderModel.getProfileMenu();
		globalHeaderModel.getUrlLogoSticky();
		//globalHeaderModel.getCanonicalUrl();
	}
}
