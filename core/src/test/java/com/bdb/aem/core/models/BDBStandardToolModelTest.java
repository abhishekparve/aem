package com.bdb.aem.core.models;

import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;

/**
 * The Class BDBStandardToolModelTest.
 */
@ExtendWith({ MockitoExtension.class })
class BDBStandardToolModelTest {

	/** The bdb standard tool model. */
	@InjectMocks
	BDBStandardToolModel bdbStandardToolModel;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** Mock ResourceResolverFactory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The context. */
	@Mock
	ComponentContext context;

	/**
	 * Test.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void test() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		bdbStandardToolModel.getIcon();
		bdbStandardToolModel.getIconAltText();
		bdbStandardToolModel.getClassName();
		bdbStandardToolModel.toString();
	}

	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
	}

}
