package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;

import junitx.util.PrivateAccessor;

/**
 * The Class CategoryCaseStudyModelTest.
 */
@ExtendWith({ MockitoExtension.class })
class CategoryCaseStudyModelTest {

	/** The category case study model. */
	@InjectMocks
	CategoryCaseStudyModel categoryCaseStudyModel;

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	
	 @Mock
	 Resource currentResource;

	/** The test value. */
	private String TEST_VALUE = "test";

	/**
	 * Setup.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setup() throws NoSuchFieldException {
		PrivateAccessor.setField(categoryCaseStudyModel, "title", TEST_VALUE);
		PrivateAccessor.setField(categoryCaseStudyModel, "subTitle", TEST_VALUE);
		PrivateAccessor.setField(categoryCaseStudyModel, "description", TEST_VALUE);
		PrivateAccessor.setField(categoryCaseStudyModel, "ctaLabel", TEST_VALUE);
		PrivateAccessor.setField(categoryCaseStudyModel, "ctaLink", TEST_VALUE);
		PrivateAccessor.setField(categoryCaseStudyModel, "additionalCtaLabel", TEST_VALUE);
		PrivateAccessor.setField(categoryCaseStudyModel, "additionalCtaLink", TEST_VALUE);
		PrivateAccessor.setField(categoryCaseStudyModel, "sectionAlignment", TEST_VALUE);
		PrivateAccessor.setField(categoryCaseStudyModel, "sectionTitle", TEST_VALUE);
		PrivateAccessor.setField(categoryCaseStudyModel, "backgroundImage", TEST_VALUE);
		PrivateAccessor.setField(categoryCaseStudyModel, "foregroundImage", TEST_VALUE);
		PrivateAccessor.setField(categoryCaseStudyModel, "backgroundOverlay", TEST_VALUE);
		lenient().when(currentResource.getName()).thenReturn("categoryCaseStudyModel");
		lenient().when(currentResource.getParent()).thenReturn(currentResource);
	}

	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInit() throws LoginException {
		when(externalizerService.getFormattedUrl(TEST_VALUE, resourceResolver)).thenReturn(TEST_VALUE);
		categoryCaseStudyModel.init();
	}

	/**
	 * Test all getter.
	 */
	@Test
	void testAllGetter() {
		assertEquals(TEST_VALUE, categoryCaseStudyModel.getTitle());
		assertEquals(TEST_VALUE, categoryCaseStudyModel.getSubTitle());
		assertEquals(TEST_VALUE, categoryCaseStudyModel.getDescription());
		assertEquals(TEST_VALUE, categoryCaseStudyModel.getCtaLabel());
		assertEquals(TEST_VALUE, categoryCaseStudyModel.getCtaLink());
		assertEquals(TEST_VALUE, categoryCaseStudyModel.getAdditionalCtaLabel());
		assertEquals(TEST_VALUE, categoryCaseStudyModel.getAdditionalCtaLink());
		assertEquals(TEST_VALUE, categoryCaseStudyModel.getSectionAlignment());
		assertEquals(TEST_VALUE, categoryCaseStudyModel.getSectionTitle());
		assertEquals(TEST_VALUE, categoryCaseStudyModel.getBackgroundImage());
		assertEquals(TEST_VALUE, categoryCaseStudyModel.getBackgroundOverlay());
		assertEquals(TEST_VALUE, categoryCaseStudyModel.getForegroundImage());
		assertNotNull(categoryCaseStudyModel.getArticleId());
	}

}
