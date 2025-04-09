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
import com.bdb.aem.core.util.CommonHelper;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class CompareProductsToolBarModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CompareProductsToolBarModelTest {

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	
	/** The compare products tool bar model. */
	@InjectMocks
	CompareProductsToolBarModel compareProductsToolBarModel;

	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	
	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;
	

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		lenient().when(externalizerService.getFormattedUrl("compareProductsLandingPage", resourceResolver)).thenReturn("compareProductsLandingPage");
  
		PrivateAccessor.setField(compareProductsToolBarModel, "compareToolBarLabels", "compareToolBarLabels");
		PrivateAccessor.setField(compareProductsToolBarModel,"compareToolBarConfig","compareToolBarConfig");
		PrivateAccessor.setField(compareProductsToolBarModel, "compareProductsLandingPage", "compareProductsLandingPage");
		
	}

	/**
	 * Test all getters.
	 */
	@Test
	void testAllGetters() {
		assertNotNull(compareProductsToolBarModel.getCompareToolBarLabels());
		assertNotNull(compareProductsToolBarModel.getCompareToolBarConfig());
	}

	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInit() throws LoginException {
		compareProductsToolBarModel.init();
	}

	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		compareProductsToolBarModel.init();
	}

}
