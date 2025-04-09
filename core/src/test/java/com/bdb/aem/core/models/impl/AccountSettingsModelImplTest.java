package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.GlobalErrorMessagesModel;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.i18n.I18n;
import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The class AccountSettingsModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class AccountSettingsModelImplTest {

	/**
	 * The Value page path.
	 */
	String VALUE_PAGE_PATH = "/content/bdb/testPage";

	/**
	 * The Value page resource type.
	 */
	String VALUE_PAGE_RESOURCE_TYPE = "bdb/components/structure/page";
	/**
	 * The Value template.
	 */
	String VALUE_TEMPLATE = "/conf/bdb/settings/wcm/templates/content-page";
	/**
	 * The Value areoffocus resource type.
	 */
	String VALUE_AREOFFOCUS_RESOURCE_TYPE = "bdb-aem/proxy/components/content/areaoffocus";
	/**
	 * The Value page title.
	 */
	String VALUE_PAGE_TITLE = "SAMPLE PAGE TITLE";
	/**
	 * The Value aof page path.
	 */
	String VALUE_AOF_PAGE_PATH = "/content/testpage/aof";

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The account settings model. */
	@InjectMocks
	private AccountSettingsModelImpl accountSettingsModel;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The current page. */
	@Mock
	Page currentPage;

	/** The bundle. */
	@Mock
	ResourceBundle bundle;

	/** The error model. */
	@Mock
	GlobalErrorMessagesModel errorModel;

	/** The page locale. */
	Locale pageLocale = new Locale("en", "US");

	/** The i 18 n. */
	@Mock
	I18n i18n;

	/** The children. */
	

	/** The in VM. */
	InheritanceValueMap inVM;

	/** The page. */
	private Page page;
	
	/** The pwd page. */
	private Page pwdPage;
	
	/** The context. */
	private AemContext context;
	
	/** The resource area of focus. */
	private Resource resourceAreaOfFocus;
	
	/** The resource sign up. */
	private Resource resourceSignUp;
	
	/** The resource global error. */
	private Resource resourceGlobalError;
	
	/** The resource. */
	@Mock
	Resource resource;

	/**
	 * Sets .
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	public void setup() throws NoSuchFieldException {

		Map<String, String> passwordRulesPageProperties = new HashMap<>();
		passwordRulesPageProperties.put(JcrConstants.JCR_TITLE, "Account Management");
		passwordRulesPageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY,
				"bdb-aem/proxy/components/structure/account-management-page");

		context.addModelsForClasses(AccountSettingsModelImpl.class);

		Map<String, String> pageProperties = new HashMap<>();
		pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
		pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
		page = context.create().page(VALUE_PAGE_PATH, VALUE_TEMPLATE, pageProperties);
		Map<String, String> resourceProperties = new HashMap<>();
		resourceProperties.put("industryTitle", "Industry TItle");
		resourceProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE, "bdb-aem/components/content/accountmanagement/v1/aeraoffocus");
		Map<String, String> signUpProperties = new HashMap<>();
		signUpProperties.put("aofPagePath", "/content/testpage/aof");
		signUpProperties.put(CommonConstants.ERROR_PAGE_PATH, "ErrorPage");
		signUpProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE,
				"bdb-aem/proxy/components/content/globalerror");
		resourceAreaOfFocus = context.create().resource(page.getContentResource().getPath() + "/root/aof",
				resourceProperties);
		resourceSignUp = context.create().resource(page.getContentResource().getPath() + "/root/aof/accountmanagement",
				signUpProperties);
		
		
		PrivateAccessor.setField(accountSettingsModel, "aofPagePath", "/content/testpage/aof");

	}

	/**
	 * Test init.
	 */
	@Test
	void testInit() {
		accountSettingsModel.init();
	}
	
	/**
	 * Test init error page.
	 */
	@Test
	void testInitErrorPage() {
		when(currentPage.getContentResource()).thenReturn(resourceSignUp);
		when(resourceResolver.getResource("ErrorPage".concat(CommonConstants.JCR_ROOT))).thenReturn(resource);
		Iterator<Resource> children=Mockito.mock(Iterator.class);
		Resource resource1=Mockito.mock(Resource.class);
		
		when(resource1.isResourceType("bdb-aem/proxy/components/content/globalerror")).thenReturn(true);
		when(resource1.getPath()).thenReturn("errorpage/path");
		when(resource.listChildren()).thenReturn(children);
		when(children.hasNext()).thenReturn(true).thenReturn(false);
		when(children.next()).thenReturn(resource1);
		when(resourceResolver.getResource("errorpage/path")).thenReturn(resource);
		when(resource.adaptTo(GlobalErrorMessagesModel.class)).thenReturn(errorModel);
		
		accountSettingsModel.init();
	}
	
	/**
	 * Test getters.
	 */
	@Test
	public void testGetters() {
		assertNull(accountSettingsModel.getAccountSettingConfig());
		assertNull(accountSettingsModel.getAccountSettingLabels());
		assertNull(accountSettingsModel.getAreaFocusConfig());
		assertNull(accountSettingsModel.getAreaFocusLabels());
	}

}
