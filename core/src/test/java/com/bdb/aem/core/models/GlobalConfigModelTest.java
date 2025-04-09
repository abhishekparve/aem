package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.CookieNameService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class GlobalConfigModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class GlobalConfigModelTest {

	/** The global config model. */
	@InjectMocks
	GlobalConfigModel globalConfigModel;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The current page. */
	@Mock
	Page currentPage;

	/** The cookie name service. */
	@Mock
	CookieNameService cookieNameService;

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The context. */
	private AemContext context;

	/** The page. */
	private Page page;

	/** The resource. */
	private Resource resource;

	/** The value page path. */
	String VALUE_PAGE_PATH = "/content/bdb/testPage";

	/**
	 * The value page resource type.
	 */
	String VALUE_PAGE_RESOURCE_TYPE = "bdb/components/structure/page";

	/**
	 * The value template.
	 */
	String VALUE_TEMPLATE = "/conf/bdb/settings/wcm/templates/content-page";

	/**
	 * The value page title.
	 */
	String VALUE_PAGE_TITLE = "SAMPLE PAGE TITLE";

	/** The test value. */
	String TEST_VALUE = "test";

	/**
	 * Sets the up.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {
		context.addModelsForClasses(GlobalConfigModel.class);
		Map<String, String> pageProperties = new HashMap<>();
		pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
		pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
		page = context.create().page(VALUE_PAGE_PATH, VALUE_TEMPLATE, pageProperties);

		Map<String, String> purchaseProperties = new HashMap<>();
		purchaseProperties.put(CommonConstants.MY_CART_PAGE, "mycartPagePath");
		purchaseProperties.put(CommonConstants.CHECKOUT_PAGE_PATH, "checkoutPagePath");
		purchaseProperties.put(CommonConstants.ORDER_CONFIRMATION_PAGE_PATH, "orderConfirmationPagePath");
		purchaseProperties.put(CommonConstants.MY_QUOTE_PAGE_PATH, "myQuotePagePath");
		purchaseProperties.put(CommonConstants.SIGNIN_PATH, "signInUrl");
		purchaseProperties.put(CommonConstants.DASHBOARD_PAGE_PATH, "dashBoardUrl");
		purchaseProperties.put("quoteConfirmationPagePath", "quoteConfirmationPagePath");

		resource = context.create().resource(page.getContentResource().getPath() + "/root/page", purchaseProperties);

		PrivateAccessor.setField(globalConfigModel, "currentPage", currentPage);
		PrivateAccessor.setField(globalConfigModel, "myCartPagePath", TEST_VALUE);
		PrivateAccessor.setField(globalConfigModel, "cookiesJson", TEST_VALUE);
		PrivateAccessor.setField(globalConfigModel, "requestPayloadObj", TEST_VALUE);
	}

	/**
	 * Test.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void test() throws LoginException {
		when(currentPage.getContentResource()).thenReturn(resource);
		when(externalizerService.getFormattedUrl("mycartPagePath", resourceResolver)).thenReturn("https://bdb.com");
		when(externalizerService.getFormattedUrl("checkoutPagePath", resourceResolver)).thenReturn("content/page/bdb");
		when(externalizerService.getFormattedUrl("orderConfirmationPagePath", resourceResolver))
				.thenReturn("content/page/orderConfirmed");
		when(externalizerService.getFormattedUrl("quoteConfirmationPagePath", resourceResolver))
		.thenReturn("content/page/quoteConfirmed");
		when(externalizerService.getFormattedUrl("myQuotePagePath", resourceResolver))
				.thenReturn("content/page/QuotePage");
		
		when(externalizerService.getFormattedUrl("dashBoardUrl", resourceResolver)).thenReturn("bdb.dashBoard.com");

		globalConfigModel.init();

	}

	/**
	 * Test get methods.
	 */
	@Test
	void testGetMethods() {
		assertEquals(TEST_VALUE, globalConfigModel.getMyCartPagePath());
		assertEquals(TEST_VALUE, globalConfigModel.getCookiesJson());
		assertEquals(TEST_VALUE, globalConfigModel.getRequestPayloadObj());
	}

}
