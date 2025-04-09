package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.GlobalErrorMessagesModel;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.impl.BDBApiEndpointServiceImpl;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class ResetPasswordImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ResetPasswordImplTest {
	
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

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;
	
	/** The error model. */
	@Mock
	GlobalErrorMessagesModel errorModel;
	
	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The bdb api endpoint service. */
	@InjectMocks
	BDBApiEndpointService bdbApiEndpointService = new BDBApiEndpointServiceImpl();

	/** The reset password test model. */
	@InjectMocks
	ResetPasswordImpl resetPasswordTestModel;
	
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
		context.addModelsForClasses(ResetPasswordImpl.class);
		context.addModelsForClasses(GlobalErrorMessagesModel.class);
		Map<String, String> pageProperties = new HashMap<>();
		pageProperties.put(JcrConstants.JCR_TITLE, VALUE_PAGE_TITLE);
		pageProperties.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, VALUE_PAGE_RESOURCE_TYPE);
		page = context.create().page(VALUE_PAGE_PATH, VALUE_TEMPLATE, pageProperties);

		Map<String, String> purchaseProperties = new HashMap<>();
		purchaseProperties.put(SlingConstants.PROPERTY_RESOURCE_TYPE,
				"bdb-aem/components/content/resetpassword/v1/resetpassword");
		
		resourceResetPassword = context.create()
				.resource(page.getContentResource().getPath() + "/root/resetpassword", purchaseProperties);

		resetPasswordTestModel = new ResetPasswordImpl();
		
		resourceResolver = context.currentResource(resourceResetPassword).getResourceResolver();
		
		PrivateAccessor.setField(bdbApiEndpointService, "bdbHybrisDomain", "https://api.domain.com");
		PrivateAccessor.setField(bdbApiEndpointService, "hybrisResetPasswordEndpoint", "/occ/{{site}}/updatePassword");
		PrivateAccessor.setField(bdbApiEndpointService, "hybrisValidatePasswordEndpoint", "/occ/{{site}}/validatepasswordtoken");
		PrivateAccessor.setField(resetPasswordTestModel, "resourceResolver", resourceResolver); 
		PrivateAccessor.setField(resetPasswordTestModel, "bdbApiEndpointService", bdbApiEndpointService);
		resetPasswordTestModel.init();
	}

	/**
	 * Test init.
	 */
	@Test
	void testInit() {
		resetPasswordTestModel.init();
	}
	
//	/**
//	 * Creates the reset password model labels.
//	 *
//	 * @throws NoSuchFieldException the no such field exception
//	 */
//	@Test
//	void createResetPasswordModelLabels() throws NoSuchFieldException {
//		errorModel = new GlobalErrorMessagesModel();
//		PrivateAccessor.setField(errorModel, "rulesHeading", "rulesHeading"); 
//		resetPasswordTestModel.createResetPasswordModelLabels(errorModel);
//	}

	/**
	 * Test reset password labels and config.
	 */
	@Test
	void testResetPasswordLabelsAndConfig() {
		assertNotNull(resetPasswordTestModel.getResetPasswordLabels());
		assertNotNull(resetPasswordTestModel.getResetPasswordConfig());
	}

}
