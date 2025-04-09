package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;

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
 * The Class ApplicationsAndSolutionsContentModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class ApplicationsAndSolutionsContentModelTest {

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

  
    /** The applications and solutions content. */
    @InjectMocks
    ApplicationsAndSolutionsContentModel applicationsAndSolutionsContent;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
    	PrivateAccessor.setField(applicationsAndSolutionsContent, "contentTitle", "contentTitle");
		PrivateAccessor.setField(applicationsAndSolutionsContent, "contentDescription", "contentDescription");  
		PrivateAccessor.setField(applicationsAndSolutionsContent, "instructionText", "instructionText");  
		PrivateAccessor.setField(applicationsAndSolutionsContent, "ctaLabel", "ctaLabel");  
		PrivateAccessor.setField(applicationsAndSolutionsContent, "ctaUrl", "ctaUrl"); 
		PrivateAccessor.setField(applicationsAndSolutionsContent, "modalImgFlag", "modalImgFlag"); 
		
		ApplicationsAndSolutionsImagesModel imagesModel = new ApplicationsAndSolutionsImagesModel() ;
		PrivateAccessor.setField(imagesModel, "imageTitle", "imageTitle");
		PrivateAccessor.setField(imagesModel, "imageDescription", "imageDescription"); 
		ArrayList<ApplicationsAndSolutionsImagesModel> imagesList = new ArrayList<>();
		imagesList.add(imagesModel);
		PrivateAccessor.setField(applicationsAndSolutionsContent, "images",imagesList); 
    }

    /**
     * Test all getters.
     */
    @Test
    void testAllGetters() {
    	lenient().when(externalizerService.getFormattedUrl("ctaUrl", resourceResolver)).thenReturn("/content/page.html");
    	
        assertNotNull(applicationsAndSolutionsContent.getContentDescription());
        assertNotNull(applicationsAndSolutionsContent.getContentTitle());
        assertNotNull(applicationsAndSolutionsContent.getCtaLabel());
        assertNotNull(applicationsAndSolutionsContent.getCtaUrl());
        assertNotNull(applicationsAndSolutionsContent.getImages());
        assertNotNull(applicationsAndSolutionsContent.getInstructionText());
        assertNotNull(applicationsAndSolutionsContent.getModalImgFlag());
        applicationsAndSolutionsContent.getSelection();
        applicationsAndSolutionsContent.getSingleImagePath();
        applicationsAndSolutionsContent.getSingleEnlargedImagePath();
        applicationsAndSolutionsContent.getSingleAltText();
        applicationsAndSolutionsContent.getSingleImageEnlargeSize();
        applicationsAndSolutionsContent.getImageCaption();
        applicationsAndSolutionsContent.getSingleImageTitle();
        applicationsAndSolutionsContent.getSingleImageDescription();
        applicationsAndSolutionsContent.getBorderEnableSingle();
    }

    /**
     * Test init.
     *
     * @throws LoginException the login exception
     */
    @Test
    void testInit() throws LoginException{
        lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
        applicationsAndSolutionsContent.init();
    }
    
	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		applicationsAndSolutionsContent.init();
	}
}
