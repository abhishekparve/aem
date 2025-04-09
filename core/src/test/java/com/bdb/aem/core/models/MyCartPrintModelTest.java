package com.bdb.aem.core.models;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.inherit.InheritanceValueMap;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.apache.sling.api.resource.LoginException;
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
 * The Class MyCartPrintModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class MyCartPrintModelTest {

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	@Mock
	InheritanceValueMap pageProperties;

	/** The MyCartPrintModel. */
	@InjectMocks
	MyCartPrintModel myCartPrintModel;

	/** The BDB Api Endpoint Service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(myCartPrintModel, "myCartPrintPageLabels", "myCartPrintPageLabels");
		PrivateAccessor.setField(myCartPrintModel, "myCartPrintPageConfig", "myCartPrintPageConfig");
		PrivateAccessor.setField(myCartPrintModel, "iconsList", "iconsList");
	}

	/**
	 * Test all getters.
	 */
	@Test
	void testAllGetters() {
		assertNotNull(myCartPrintModel.getMyCartPrintPageLabels());
		assertNotNull(myCartPrintModel.getMyCartPrintPageConfig());
		assertNotNull(myCartPrintModel.getIconsList());
	}

	/**
	 * Test init.
	 * 
	 * 
	 */
	@Test
	void testInit() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn("https://api-qa1.author.com");
		lenient().when(bdbApiEndpointService.getCartWithIdentifier())
				.thenReturn("/occ/v2/{{baseSiteId}}/users/{{userId}}/carts/{{cartId}}");
	
		myCartPrintModel.init();
	}

	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	//@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		myCartPrintModel.init();
	}

}
