package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

	
/**
 * The Class FeaturesAndSpecsDetailsModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class FeaturesAndSpecsDetailsModelTest {

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

    /** The features and specs details model. */
    @InjectMocks
    FeaturesAndSpecsDetailsModel featuresAndSpecsDetailsModel;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
    	PrivateAccessor.setField(featuresAndSpecsDetailsModel, "imagePath", "imagePath");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "imagePathMobile", "imagePathMobile");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "description", "description");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "sectionTitle", "sectionTitle");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "subTitle", "subTitle");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "slideTitle", "slideTitle");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "altText", "altText");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "borderEnable", "borderEnable");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "backgroundColorImage", "backgroundColorImage");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "isLargeImage", "isLargeImage");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "modalImgFlag", "modalImgFlag");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "imageEnlargeSize", "imageEnlargeSize");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "enlargedImagePath", "enlargedImagePath");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "enlargedImageAltText", "enlargedImageAltText");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "imageTitle", "imageTitle");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "imageDescription", "imageDescription");

    }

    /**
     * Test all getters.
     */
    @Test
    void testAllGetters() {
    	lenient().when(externalizerService.getFormattedUrl("imagePath", resourceResolver)).thenReturn("/content/dam/image.png");
    	lenient().when(externalizerService.getFormattedUrl("imagePathMobile", resourceResolver)).thenReturn("/content/dam/image.png");
    	lenient().when(externalizerService.getFormattedUrl("enlargedImagePath", resourceResolver)).thenReturn("/content/dam/image.png");
    
    	assertNotNull(featuresAndSpecsDetailsModel.getImagePath());
        assertNotNull(featuresAndSpecsDetailsModel.getImagePathMobile());
        assertNotNull(featuresAndSpecsDetailsModel.getDescription());
        assertNotNull(featuresAndSpecsDetailsModel.getSectionTitle());
        assertNotNull(featuresAndSpecsDetailsModel.getSubTitle());
        assertNotNull(featuresAndSpecsDetailsModel.getSlideTitle());
        assertNotNull(featuresAndSpecsDetailsModel.getAltText());
        assertNotNull(featuresAndSpecsDetailsModel.getBorderEnable());
        assertNotNull(featuresAndSpecsDetailsModel.getBackgroundColorImage());
        assertNotNull(featuresAndSpecsDetailsModel.getIsLargeImage());
        assertNotNull(featuresAndSpecsDetailsModel.getModalImgFlag());
        assertNotNull(featuresAndSpecsDetailsModel.getImageEnlargeSize());
        assertNotNull(featuresAndSpecsDetailsModel.getEnlargedImagePath());
        assertNotNull(featuresAndSpecsDetailsModel.getEnlargedImageAltText());
        assertNotNull(featuresAndSpecsDetailsModel.getImageTitle());
        assertNotNull(featuresAndSpecsDetailsModel.getImageDescription());
    }
    
    /**
     * Test init.
     *
     * @throws LoginException the login exception
     */
    @Test
    void testInit() throws LoginException {
    	featuresAndSpecsDetailsModel.init();
    }
    
}
