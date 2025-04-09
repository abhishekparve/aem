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

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class ApplicationsAndSolutionsImagesModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class ApplicationsAndSolutionsImagesModelTest {

    /** Mock ResourceResolverFactory. */
    @Mock
    ExternalizerService externalizerService;

    /** Resourceresolver factory. */
    @Mock
    ResourceResolverFactory resourceResolverFactory;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;

    /**  The ComponentContext context *. */
    @Mock
    ComponentContext context;

    /** The applications and solutions images. */
    @InjectMocks
    ApplicationsAndSolutionsImagesModel applicationsAndSolutionsImages;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
    	PrivateAccessor.setField(applicationsAndSolutionsImages, "altImage", "altImage");
		PrivateAccessor.setField(applicationsAndSolutionsImages, "thumbImagePath", "thumbImagePath");  
		PrivateAccessor.setField(applicationsAndSolutionsImages, "imagePath", "imagePath");  
		PrivateAccessor.setField(applicationsAndSolutionsImages, "imageTitle", "imageTitle");  
		PrivateAccessor.setField(applicationsAndSolutionsImages, "imageDescription", "imageDescription"); 
    }

    /**
     * Test all getters.
     */
    @Test
    void testAllGetters() {
    	lenient().when(externalizerService.getFormattedUrl("thumbImagePath", resourceResolver)).thenReturn("/content/dam/image.png");
    	lenient().when(externalizerService.getFormattedUrl("imagePath", resourceResolver)).thenReturn("/content/dam/image.png");
    	
        assertNotNull(applicationsAndSolutionsImages.getAltImage());
        assertNotNull(applicationsAndSolutionsImages.getImageDescription());
        assertNotNull(applicationsAndSolutionsImages.getImagePath());
        assertNotNull(applicationsAndSolutionsImages.getImageTitle());
        assertNotNull(applicationsAndSolutionsImages.getThumbImagePath());
               
    }

    /**
     * Test init.
     *
     * @throws LoginException the login exception
     */
    @Test
    void testInit() throws LoginException{
        lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
        applicationsAndSolutionsImages.init();
    }
    
	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		applicationsAndSolutionsImages.init();
	}
}
