package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import junitx.util.PrivateAccessor;


/**
 * The Class BDBProfileMenuMutlifieldModelTest.
 */
@ExtendWith({ MockitoExtension.class })
class BDBProfileMenuMutlifieldModelTest {

	/** The bdb profile menu mutlifield model. */
	@InjectMocks
	BDBProfileMenuMutlifieldModel bdbProfileMenuMutlifieldModel;

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

	/**
	 * Test get redirect URL.
	 *
	 * @throws LoginException the login exception
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void TestGetRedirectURL() throws LoginException, NoSuchFieldException {
		PrivateAccessor.setField(bdbProfileMenuMutlifieldModel, "redirectURL", "redirectURL");
		assertEquals("redirectURL", bdbProfileMenuMutlifieldModel.getRedirectURL());
		bdbProfileMenuMutlifieldModel.getAltText();
		bdbProfileMenuMutlifieldModel.getId();
		bdbProfileMenuMutlifieldModel.getLabel();
		bdbProfileMenuMutlifieldModel.getImgSrc();
		bdbProfileMenuMutlifieldModel.toString();
	}

}
