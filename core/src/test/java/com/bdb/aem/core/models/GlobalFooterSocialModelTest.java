package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class GlobalFooterSocialModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class GlobalFooterSocialModelTest {

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The context. */
	@Mock
	ComponentContext context;

	/** The global footer social model. */
	@InjectMocks
	GlobalFooterSocialModel globalFooterSocialModel;

	/**
	 * Sets the up.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {
		PrivateAccessor.setField(globalFooterSocialModel, "socialAlt", "socialAlt");
		PrivateAccessor.setField(globalFooterSocialModel, "socialURL", "socialURL");
		PrivateAccessor.setField(globalFooterSocialModel, "socialIcon", "socialIcon");

	}

	/**
	 * Test all getters.
	 */
	@Test
	void testAllGetters() {
		lenient().when(externalizerService.getFormattedUrl("socialURL", resourceResolver))
				.thenReturn("https://www.google.co.in");
		lenient().when(externalizerService.getFormattedUrl("socialIcon", resourceResolver))
				.thenReturn("https://www.google.co.in");

		assertNotNull(globalFooterSocialModel.getSocialURL());
		assertNotNull(globalFooterSocialModel.getSocialIcon());
		assertNotNull(globalFooterSocialModel.getSocialAlt());
	}

	/**
	 * Test init.
	 */
	@Test
	void testInit(){
		globalFooterSocialModel.init();
	}

	/**
	 * Test null values.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testNullValues() throws NoSuchFieldException {
		PrivateAccessor.setField(globalFooterSocialModel, "socialAlt", "");
		PrivateAccessor.setField(globalFooterSocialModel, "socialURL", "");
		PrivateAccessor.setField(globalFooterSocialModel, "socialIcon", "");
		
		assertNotNull(globalFooterSocialModel.getSocialURL());
		assertNotNull(globalFooterSocialModel.getSocialIcon());
		assertNotNull(globalFooterSocialModel.getSocialAlt());
	}
}
