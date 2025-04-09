package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
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
 * The Class TextAndDownloadModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class TextAndDownloadModelImplTest {

	/** The externalizer service. */
	@Mock
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;
    
    /** The context. */
    @Mock
    ComponentContext context;
	
    /** The text download test model. */
    @InjectMocks
    TextAndDownloadModelImpl textDownloadTestModel;
    
    @Mock
	Resource currentResource;
    
    /** The path. */
    private final String PATH = "/dummy/test";
    
    /** The ret path. */
    private final String RET_PATH = "https://www.testurl.com/dummy/test";
    
	/**
	 * Sets the Test Model.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(textDownloadTestModel, "linkLabel", "linkLabel");
		PrivateAccessor.setField(textDownloadTestModel, "linkPath", PATH);
		PrivateAccessor.setField(textDownloadTestModel, "primaryCtaLabel", "primaryCtaLabel");
		PrivateAccessor.setField(textDownloadTestModel, "primaryCtaPath", PATH);
		PrivateAccessor.setField(textDownloadTestModel, "optionalCtaLabel", "optionalCtaLabel");
		PrivateAccessor.setField(textDownloadTestModel, "optionalCtaPath", PATH);
		PrivateAccessor.setField(textDownloadTestModel, "caption", "caption");
		PrivateAccessor.setField(textDownloadTestModel, "imagePath", PATH);
		PrivateAccessor.setField(textDownloadTestModel, "sectionTitle", "sectionTitle");
		PrivateAccessor.setField(textDownloadTestModel, "title", "title");
		lenient().when(currentResource.getName()).thenReturn("textDownloadTestModel");
		lenient().when(currentResource.getParent()).thenReturn(currentResource);
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		lenient().when(externalizerService.getFormattedUrl(PATH, resourceResolver)).thenReturn(RET_PATH);
		assertEquals("linkLabel", textDownloadTestModel.getLinkLabel());
		assertEquals(PATH, textDownloadTestModel.getLinkPath());
		assertEquals("primaryCtaLabel", textDownloadTestModel.getPrimaryCtaLabel());
		assertEquals(PATH, textDownloadTestModel.getPrimaryCtaPath());
		assertEquals("optionalCtaLabel", textDownloadTestModel.getOptionalCtaLabel());
		assertEquals(PATH, textDownloadTestModel.getOptionalCtaPath());
		assertEquals("caption", textDownloadTestModel.getCaption());
		assertEquals(PATH, textDownloadTestModel.getImagePath());
		assertNotNull(textDownloadTestModel.getArticleId());
	}
	
	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInit() throws LoginException{
        textDownloadTestModel.init();
    }
}
