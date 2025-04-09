package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;

import org.apache.sling.api.resource.Resource;
import org.joda.time.chrono.LenientChronology;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import java_cup.runtime.lr_parser;
import junitx.util.PrivateAccessor;


/**
 * The Class FeaturesAndSpecsModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class FeaturesAndSpecsModelTest {

    
    /** The features and specs model. */
    @InjectMocks
    FeaturesAndSpecsModel featuresAndSpecsModel;
    
    @Mock
	Resource currentResource;
	
    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
	void setUp() throws Exception {
    	FeaturesAndSpecsDetailsModel featuresAndSpecsDetailsModel = new FeaturesAndSpecsDetailsModel();
    	PrivateAccessor.setField(featuresAndSpecsDetailsModel, "imagePath", "imagePath");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "imagePathMobile", "imagePathMobile");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "description", "description");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "sectionTitle", "sectionTitle");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "subTitle", "subTitle");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "slideTitle", "slideTitle");
		PrivateAccessor.setField(featuresAndSpecsDetailsModel, "altText", "altText");
		
		ArrayList<FeaturesAndSpecsDetailsModel> detailsModelList = new ArrayList<>();
		detailsModelList.add(featuresAndSpecsDetailsModel);
		PrivateAccessor.setField(featuresAndSpecsModel,"slides",detailsModelList);
		PrivateAccessor.setField(featuresAndSpecsModel,"sectionAlign","test-align");
		PrivateAccessor.setField(featuresAndSpecsModel,"sectionTitle","sectionTitle");
		lenient().when(currentResource.getName()).thenReturn("FeatureAndSpecs");
		lenient().when(currentResource.getParent()).thenReturn(currentResource);
		
	}
   
    
    /**
     * Test getters.
     */
    @Test
	void testGetters() {	
		assertNotNull(featuresAndSpecsModel.getSlides());
		assertNotNull(featuresAndSpecsModel.getSlides().get(0));
		assertNotNull(featuresAndSpecsModel.getArticleId());
	}
    
    /**
     * Test fields.
     */
    @Test
	void testFields() {
    	assertEquals("altText",featuresAndSpecsModel.getSlides().get(0).getAltText());
		assertEquals("subTitle",featuresAndSpecsModel.getSlides().get(0).getSubTitle());
		assertEquals("description",featuresAndSpecsModel.getSlides().get(0).getDescription());
		assertEquals("slideTitle",featuresAndSpecsModel.getSlides().get(0).getSlideTitle());
		assertEquals("sectionTitle",featuresAndSpecsModel.getSlides().get(0).getSectionTitle());
		assertEquals("test-align",featuresAndSpecsModel.getSectionAlign());
		assertEquals("sectionTitle",featuresAndSpecsModel.getSectionTitle());
	}
}
