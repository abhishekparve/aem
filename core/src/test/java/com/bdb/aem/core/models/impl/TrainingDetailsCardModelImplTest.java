package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class TrainingDetailsCardModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class TrainingDetailsCardModelImplTest {
	
	/** The externalizer service. */
	@Mock
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;
    
    /** The context. */
    @Mock
    ComponentContext context;
    
    /** The context aem. */
    private AemContext contextAem;
    
    /** The training details model. */
    @InjectMocks
    TrainingDetailsCardModelImpl trainingDetailsModel;
    
    /** The description multi field. */
    private List<Resource> descriptionMultiField;
    
    /** The description list resource. */
    private Resource descriptionListResource;

    /** The path. */
    private final String PATH = "/dummy/test";
    
    /** The ret path. */
    private final String RET_PATH = "https://www.testurl.com/dummy/test";
    
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		Map<String, String> descriptionsProperty = new HashMap<>();
		descriptionsProperty.put("errorCode", "Title");
		descriptionsProperty.put("errorMessage", "Long Description");
		descriptionListResource = contextAem.create().resource("/root/master/training", descriptionsProperty);
		
		descriptionMultiField = Arrays.asList(descriptionListResource);

		PrivateAccessor.setField(trainingDetailsModel, "title", "title");
		PrivateAccessor.setField(trainingDetailsModel, "descriptionMultiField", descriptionMultiField);
		PrivateAccessor.setField(trainingDetailsModel, "videoEnabled", true);
		PrivateAccessor.setField(trainingDetailsModel, "brightcoveVideoId", "12345");
		PrivateAccessor.setField(trainingDetailsModel, "imagePath", PATH);
		PrivateAccessor.setField(trainingDetailsModel, "imageVideoAltText", "imageVideoAltText");
		PrivateAccessor.setField(trainingDetailsModel, "captionUnderImage", "captionUnderImage");
		PrivateAccessor.setField(trainingDetailsModel, "primaryCtaLabel", "primaryCtaLabel");
		PrivateAccessor.setField(trainingDetailsModel, "primaryCtaPath", PATH);
		PrivateAccessor.setField(trainingDetailsModel, "optionalCtaLabel", "optionalCtaLabel");
		PrivateAccessor.setField(trainingDetailsModel, "optionalCtaPath", PATH);
		PrivateAccessor.setField(trainingDetailsModel, "miniThumbnailImage", PATH);
		PrivateAccessor.setField(trainingDetailsModel, "miniThumbnailAltText", "miniThumbnailAltText");
		PrivateAccessor.setField(trainingDetailsModel, "isFeatured", true);
		PrivateAccessor.setField(trainingDetailsModel, "sectionAlign", "isAlignedLeft");
	}

	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInit() throws LoginException {
		trainingDetailsModel.init();
	}
	
	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		lenient().when(externalizerService.getFormattedUrl(PATH, resourceResolver)).thenReturn(RET_PATH);
		
		assertEquals("title", trainingDetailsModel.getTitle());
		assertNotNull(trainingDetailsModel.getDescriptionList());
		assertEquals(true, trainingDetailsModel.isVideoEnabled());
		assertEquals("12345", trainingDetailsModel.getBrightcoveVideoId());
		assertEquals(PATH, trainingDetailsModel.getImagePath());
		assertEquals("imageVideoAltText", trainingDetailsModel.getImageVideoAltText());
		assertEquals("captionUnderImage", trainingDetailsModel.getCaptionUnderImage());
		assertEquals("primaryCtaLabel", trainingDetailsModel.getPrimaryCtaLabel());
		assertEquals(PATH, trainingDetailsModel.getPrimaryCtaPath());
		assertEquals("optionalCtaLabel", trainingDetailsModel.getOptionalCtaLabel());
		assertEquals(PATH, trainingDetailsModel.getOptionalCtaPath());
		assertEquals(PATH, trainingDetailsModel.getMiniThumbnailImage());
		assertEquals("miniThumbnailAltText", trainingDetailsModel.getMiniThumbnailAltText());
		assertEquals(true, trainingDetailsModel.isFeatured());
		assertEquals("isAlignedLeft", trainingDetailsModel.getSectionAlign());
	}

	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		trainingDetailsModel.init();
	}
}
