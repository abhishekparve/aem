package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.models.AccessLinksModel;
import com.bdb.aem.core.models.AreaOfFocusModel;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.impl.BDBApiEndpointServiceImpl;
import com.bdb.aem.core.services.impl.ExternalizerServiceImpl;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * The Class SignUpModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class SignUpModelImplTest {

	/** The sign up model impl. */
	@InjectMocks
	private SignUpModelImpl signUpModelImpl;

	/** The resource. */
	@Mock
	Resource resource, modelResource;

	/** The access link. */
	@Mock
	AccessLinksModel accessLink;

	/** The links multifield. */
	@Mock
	List<Resource> linksMultifield;

	/** The resource iterator. */
	@Mock
	Iterator<Resource> resourceIterator;

	/** The confg. */
	@Mock
	BDBApiEndpointServiceImpl.Configuration confg;

	/** The current page. */
	@Mock
	Page currentPage;

	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The externalizer service. */
	@InjectMocks
	ExternalizerService externalizerService = new ExternalizerServiceImpl();

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The area of focus model. */
	@Mock
	AreaOfFocusModel areaOfFocusModel;

	/** The in VM. */
	@Mock
	InheritanceValueMap inVM;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The page manager. */
	@Mock
	PageManager pageManager;

	/** The sling settings service. */
	@Mock
	SlingSettingsService slingSettingsService;

	/** The context. */
	private AemContext context;

	/** The aof page path. */
	private String aofPagePath = "/content/bd/language-masters/en/products";

	/** The value page path. */
	String VALUE_PAGE_PATH = "/content/bdb/testPage";

	/** The value page resource type. */
	String VALUE_PAGE_RESOURCE_TYPE = "bdb/components/structure/page";

	/** The value template. */
	String VALUE_TEMPLATE = "/conf/bdb/settings/wcm/templates/content-page";

	/** The value page title. */
	String VALUE_PAGE_TITLE = "SAMPLE PAGE TITLE";

	/** The title. */
	String title = "title";

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		linksMultifield.add(resource);
		signUpModelImpl.setAofPagePath(aofPagePath);

		context.addModelsForClasses(SignUpModelImpl.class);
		Map<String, String> pageProperties = new HashMap<>();
		pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
		pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
		pageProperties.put("country", "US");
		pageProperties.put("region", "US");
		pageProperties.put("hybrisSiteId", "bdbUS");

		Map<String, String> addressesProperties = new HashMap<>();
		addressesProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE,
				"bdb-aem/components/content/accountmanagement/v1/accountmanagement");

		/*
		 * PrivateAccessor.setField(bdbApiEndpointService, "bdbHybrisDomain",
		 * "https://api.domain.com"); PrivateAccessor.setField(bdbApiEndpointService,
		 * "hybrisSignUpPreferenceEndpoint",
		 * "/occ/v2/{{site}}/users/{userId}/lineofwork");
		 * PrivateAccessor.setField(bdbApiEndpointService,
		 * "updateFavoriteAddressEndpoint", "/occ/{{site}}/updateFavoriteAddress");
		 * PrivateAccessor.setField(bdbApiEndpointService, "hybrisSignUpEndpoint",
		 * "/occ/v2/{{site}}/users/{userId}/register");
		 * PrivateAccessor.setField(bdbApiEndpointService, "defaultAddressEndpoint",
		 * "/occ/{{site}}/defaultAddress");
		 * PrivateAccessor.setField(bdbApiEndpointService, "updateNicknameEndpoint",
		 * "/occ/{{site}}/updateNickname");
		 * PrivateAccessor.setField(bdbApiEndpointService, "ssoLoginPingIdDomain",
		 * "https://ssodev.bd.com"); PrivateAccessor.setField(bdbApiEndpointService,
		 * "ssoLoginPingIdEndpoint", "/as/authorization.oauth2");
		 * PrivateAccessor.setField(bdbApiEndpointService, "ssoLoginResponseType",
		 * "code"); PrivateAccessor.setField(bdbApiEndpointService, "ssoLoginClientId",
		 * "Galaxy_OIDC"); PrivateAccessor.setField(bdbApiEndpointService,
		 * "ssoCustomerLoginService", "/content/bdb/paths/sso-customer-login");
		 * PrivateAccessor.setField(bdbApiEndpointService, "ssoLoginScope",
		 * "openid+profile");
		 */

		PrivateAccessor.setField(signUpModelImpl, "currentPage", currentPage);
		PrivateAccessor.setField(signUpModelImpl, "request", request);
		PrivateAccessor.setField(signUpModelImpl, "bdbApiEndpointService", bdbApiEndpointService);
		PrivateAccessor.setField(signUpModelImpl, "externalizerService", externalizerService);
		PrivateAccessor.setField(signUpModelImpl, "hybrisSiteId", "bdbUS");
		PrivateAccessor.setField(signUpModelImpl, "confirmationLabels", "Confirm");
		PrivateAccessor.setField(signUpModelImpl, "areaFocusConfig", "areaFocusConfig");
		PrivateAccessor.setField(signUpModelImpl, "signUpLabels", "Sign Up");
		PrivateAccessor.setField(signUpModelImpl, "iriLabels", "Iri");
		PrivateAccessor.setField(signUpModelImpl, "passwordRuleLabels", "Password Rule");
		PrivateAccessor.setField(signUpModelImpl, "signUpConfig", "Signup Config");
		PrivateAccessor.setField(signUpModelImpl, "signUpOtConsentDataPayload", "{\"key\":\"value\"}");

	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		when(linksMultifield.iterator()).thenReturn(resourceIterator);
		when(resourceIterator.hasNext()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
		when(resourceIterator.next()).thenReturn(resource);
		when(resource.adaptTo(AccessLinksModel.class)).thenReturn(accessLink);
		when(resourceResolver.getResource(aofPagePath.concat("/jcr:content/root"))).thenReturn(resource);
		when(resource.listChildren()).thenReturn(resourceIterator);
		when(resource.isResourceType("bdb-aem/proxy/components/content/areaoffocus")).thenReturn(true)
				.thenReturn(false);
		when(resource.getPath()).thenReturn(aofPagePath);
		when(resourceResolver.getResource(aofPagePath)).thenReturn(resource);
		when(resource.adaptTo(AreaOfFocusModel.class)).thenReturn(areaOfFocusModel);
		when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		when(bdbApiEndpointService.getHybrisSignUpPreferenceEndpoint()).thenReturn("HybrisSignUpPreferenceEndpoint");
		when(bdbApiEndpointService.ssoCustomerLoginService()).thenReturn("ssoCustomerLoginService");
		when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn("BDBHybrisDomain");

		when(bdbApiEndpointService.getAnonymousUserIdPlaceholder()).thenReturn("AnonymousUserIdPlaceholder");
		when(bdbApiEndpointService.getHybrisSignUpEndpoint()).thenReturn("HybrisSignUpEndpoint");
		when(bdbApiEndpointService.environmentType()).thenReturn("environmentType");

		signUpModelImpl.init();
	}

	@Test
	void testGetAreaOfFocusLabel() {
		when(resourceResolver.getResource(aofPagePath.concat("/jcr:content/root"))).thenReturn(resource);
		when(resource.listChildren()).thenReturn(resourceIterator);
		when(resourceIterator.hasNext()).thenReturn(true, false);
		when(resourceIterator.next()).thenReturn(resource);
		when(resource.isResourceType("bdb/components/structure/page")).thenReturn(true);
		when(resource.getPath()).thenReturn(aofPagePath);
		when(resourceResolver.getResource("/content/bd/language-masters/en/products")).thenReturn(modelResource);
		when(modelResource.adaptTo(AreaOfFocusModel.class)).thenReturn(areaOfFocusModel);
		signUpModelImpl.getAreaOfFocusLabel(aofPagePath, resourceResolver, VALUE_PAGE_RESOURCE_TYPE);
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertNotNull(signUpModelImpl.getConfirmationLabels());
		assertNotNull(signUpModelImpl.getSignUpLabels());
		assertNotNull(signUpModelImpl.getIriLabels());
		assertNotNull(signUpModelImpl.getPasswordRuleLabels());
		assertNotNull(signUpModelImpl.getSignUpConfig());
		assertNotNull(signUpModelImpl.getAofPagePath());
		assertNotNull(signUpModelImpl.getHybrisSiteId());
		assertNotNull(signUpModelImpl.getAreaFocusConfig());
	}

}