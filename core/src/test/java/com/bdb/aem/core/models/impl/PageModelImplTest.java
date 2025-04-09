package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.models.ApiErrorMessagesModel;
import com.bdb.aem.core.models.GlobalErrorMessagesModel;
import com.bdb.aem.core.models.MasterDataMessagesModel;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.impl.BDBApiEndpointServiceImpl;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The Class PageModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class PageModelImplTest {

	/**
	 * The value page path.
	 */
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

	/**
	 * The title.
	 */
	String title = "title";

	/**
	 * The resource resolver factory.
	 */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/**
	 * The resource resolver.
	 */
	@Mock
	ResourceResolver resourceResolver;

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/**
	 * The bdb api endpoint service.
	 */
	/*
	 * @InjectMocks BDBApiEndpointService bdbApiEndpointService = new
	 * BDBApiEndpointServiceImpl();
	 */
	@Mock
	private BDBApiEndpointService bdbApiEndpointService;
	/** The page model. */
	@InjectMocks
	PageModelImpl pageModel;

	/** The error model. */
	@Mock
	GlobalErrorMessagesModel errorModel;

	/** The current page. */
	@Mock
	Page currentPage;

	/** The error messages model. */
	@Mock
	ApiErrorMessagesModel errorMessagesModel;

	/** The master data messages model. */
	@Mock
	MasterDataMessagesModel masterDataMessagesModel;
	SlingSettingsService slingSettingsService;

	/** The page. */
	private Page page;

	/** The context. */
	private AemContext context;

	/** The resource reset password. */
	private Resource resourceResetPassword;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		context.addModelsForClasses(PageModelImpl.class);

		Map<String, String> pageProperties = new HashMap<>();
		pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
		pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
		page = context.create().page(VALUE_PAGE_PATH, VALUE_TEMPLATE, pageProperties);

		Map<String, String> purchaseProperties = new HashMap<>();
		purchaseProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE, "bdb-aem/components/structure/page/v1/page");
		purchaseProperties.put(CommonConstants.ERROR_PAGE_PATH, "ErrorPage");
		purchaseProperties.put(CommonConstants.HOME_PAGE_PATH, "homePage");
		purchaseProperties.put(CommonConstants.MASTER_DATA_PAGE_PATH, "masterDataPage");

		resourceResetPassword = context.create().resource(page.getContentResource().getPath() + "/root/page",
				purchaseProperties);

		pageModel = new PageModelImpl();

		/*
		 * PrivateAccessor.setField(bdbApiEndpointService,
		 * "countriesFromRegionServletPath",
		 * "/bin/getCountriesFromRegionPathServlet.json");
		 * PrivateAccessor.setField(bdbApiEndpointService, "bdbHybrisDomain",
		 * "https://api.domain.com"); PrivateAccessor.setField(bdbApiEndpointService,
		 * "statesFromCountryServletPath", "/bin/getStatesFromCountryPathServlet.json");
		 * PrivateAccessor.setField(bdbApiEndpointService, "mergeAnonymousCartEndpoint",
		 * "/{{site}}/users/{userId}/merge/{anonymousCartGuid}");
		 */
		PrivateAccessor.setField(pageModel, "getCountriesPayload", "getCountriesPayload");
		PrivateAccessor.setField(pageModel, "getStatesPayload", "getStatesPayload");
		PrivateAccessor.setField(pageModel, "orderApprovalPromotions", "orderApprovalPromotions");
		PrivateAccessor.setField(pageModel, "certificationsType", "certificationsType");
		PrivateAccessor.setField(pageModel, "mergeAnonymousCart", "mergeAnonymousCart");
		PrivateAccessor.setField(pageModel, "bdbApiEndpointService", bdbApiEndpointService);
		PrivateAccessor.setField(pageModel, "resourceResolver", resourceResolver);
		PrivateAccessor.setField(pageModel, "externalizerService", externalizerService);
		PrivateAccessor.setField(pageModel, "currentPage", currentPage);
		PrivateAccessor.setField(pageModel, "errorMessages", "errorMessages");
		PrivateAccessor.setField(pageModel, "homePageUrl", "homePageUrl");
		PrivateAccessor.setField(pageModel, "certificationsStatus", "certificationsStatus");
		PrivateAccessor.setField(pageModel, "orderSource", "orderSource");
		PrivateAccessor.setField(pageModel, "orderStatus", "orderStatus");
		PrivateAccessor.setField(pageModel, "quoteStatus", "quoteStatus");
		PrivateAccessor.setField(pageModel, "anonymousTokenUrl", "anonymousTokenUrl");
		PrivateAccessor.setField(pageModel, "saveToQuoteList", "saveToQuoteList");
		PrivateAccessor.setField(pageModel, "addToQuote", "addToQuote");
		PrivateAccessor.setField(pageModel, "yesterday", "yesterday");
		PrivateAccessor.setField(pageModel, "today", "today");
		PrivateAccessor.setField(pageModel, "deliveryOption", "deliveryOption");
		PrivateAccessor.setField(pageModel, "signInUrl", "signInUrl");
		PrivateAccessor.setField(pageModel, "idleTime", 300);
		PrivateAccessor.setField(pageModel, "logOutUrl", "logOutUrl");
		PrivateAccessor.setField(pageModel, "refreshToken", "refreshToken");
		PrivateAccessor.setField(pageModel, "standard", "standard");
		PrivateAccessor.setField(pageModel, "region", "region");
		PrivateAccessor.setField(pageModel, "country", "country");
		PrivateAccessor.setField(pageModel, "language", "language");
		PrivateAccessor.setField(pageModel, "hybrisSiteId", "hybrisSiteId");
		PrivateAccessor.setField(pageModel, "orderInquiryTypes", "orderInquiryTypes");
		PrivateAccessor.setField(pageModel, "defaultServerError", "defaultServerError");
		PrivateAccessor.setField(pageModel, "contactUsUrl", "contactUsUrl");
	}

	/**
	 * Test init.
	 */
	@Test
	void testInit() {
		lenient().when(bdbApiEndpointService.ssoLoginPingIdDomain()).thenReturn("ssoLoginPingIdDomain");
		lenient().when(bdbApiEndpointService.ssoLoginPingIdEndpoint()).thenReturn("ssoLoginPingIdEndpoint");
		lenient().when(bdbApiEndpointService.ssoLoginResponseType()).thenReturn("ssoLoginResponseType");
		lenient().when(bdbApiEndpointService.ssoLoginClientId()).thenReturn("ssoLoginClientId");
		lenient().when(bdbApiEndpointService.ssoLoginScope()).thenReturn("ssoLoginScope");
		lenient().when(bdbApiEndpointService.ssoCustomerLoginService()).thenReturn("ssoCustomerLoginService");
		lenient().when(bdbApiEndpointService.environmentType()).thenReturn("\"stage\"");
		Resource pageContentResource = Mockito.mock(Resource.class);

		Iterator<Resource> children = Mockito.mock(Iterator.class);
		List<ApiErrorMessagesModel> listArrorMessagesModel = new ArrayList<>();
		listArrorMessagesModel.add(errorMessagesModel);
		Resource resource1 = Mockito.mock(Resource.class);

		when(resource1.isResourceType("bdb-aem/proxy/components/content/globalerror")).thenReturn(true);
		when(resource1.isResourceType("bdb-aem/proxy/components/content/masterdata")).thenReturn(true);
		when(resource1.getPath()).thenReturn("errorpage/path");
		when(currentPage.getContentResource()).thenReturn(resourceResetPassword);
		when(resourceResolver.getResource("ErrorPage".concat(CommonConstants.JCR_ROOT)))
				.thenReturn(pageContentResource);
		when(pageContentResource.listChildren()).thenReturn(children);
		when(children.hasNext()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true)
				.thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		when(children.next()).thenReturn(resource1);
		when(resourceResolver.getResource("errorpage/path")).thenReturn(pageContentResource);
		when(pageContentResource.adaptTo(GlobalErrorMessagesModel.class)).thenReturn(errorModel);
		when(pageContentResource.adaptTo(MasterDataMessagesModel.class)).thenReturn(masterDataMessagesModel);
		when(errorModel.getErrorMessages()).thenReturn(listArrorMessagesModel);
		when(masterDataMessagesModel.getCertificationMessages()).thenReturn(listArrorMessagesModel);
		when(masterDataMessagesModel.getOrderSourceMessages()).thenReturn(listArrorMessagesModel);
		when(masterDataMessagesModel.getOrderStatusMessages()).thenReturn(listArrorMessagesModel);
		when(masterDataMessagesModel.getDeliverOptionMessages()).thenReturn(listArrorMessagesModel);
		when(masterDataMessagesModel.getQuoteStatusMessages()).thenReturn(listArrorMessagesModel);
		when(masterDataMessagesModel.getOrderInquiryTypesOptions()).thenReturn(listArrorMessagesModel);
		when(masterDataMessagesModel.getCertificationsType()).thenReturn(listArrorMessagesModel);
		when(masterDataMessagesModel.getPromotionsCodeDescription()).thenReturn(listArrorMessagesModel);
		when(errorMessagesModel.getErrorCode()).thenReturn("errorCode");
		when(errorMessagesModel.getErrorMessage()).thenReturn("errormessage");
		when(resourceResolver.getResource("masterDataPage".concat(CommonConstants.JCR_ROOT)))
				.thenReturn(pageContentResource);

		pageModel.init();
	}

	/**
	 * Test getGetCountriesPayload.
	 */
	@Test
	void testGetNotNullMethods() {
		assertNotNull(pageModel.getGetCountriesPayload());
		assertNotNull(pageModel.getGetStatesPayload());
		assertNotNull(pageModel.getOrderApprovalPromotions());
		assertNotNull(pageModel.getCertificationsType());
		assertNotNull(pageModel.getMergeAnonymousCart());
		assertNotNull(pageModel.getErrorMessages());
		assertNotNull(pageModel.getHomePageUrl());
		assertNotNull(pageModel.getCertificationsStatus());
		assertNotNull(pageModel.getOrderSource());
		assertNotNull(pageModel.getOrderStatus());
		assertNotNull(pageModel.getQuoteStatus());
		assertNotNull(pageModel.getAnonymousTokenUrl());
		assertNotNull(pageModel.getSaveToQuoteList());
		assertNotNull(pageModel.getAddToQuote());
		assertNotNull(pageModel.getYesterday());
		assertNotNull(pageModel.getToday());
		assertNotNull(pageModel.getDeliveryOption());
		assertNotNull(pageModel.getSignInUrl());
		assertNotNull(pageModel.getIdleTime());
		assertNotNull(pageModel.getLogOutUrl());
		assertNotNull(pageModel.getRefreshToken());
		assertNotNull(pageModel.getStandard());
		assertNotNull(pageModel.getRegion());
		assertNotNull(pageModel.getCountry());
		assertNotNull(pageModel.getLanguage());
		assertNotNull(pageModel.getHybrisSiteId());
		assertNotNull(pageModel.getOrderInquiryTypes());
		assertNotNull(pageModel.getDefaultServerError());
		assertNotNull(pageModel.getContactUsUrl());
	}

}