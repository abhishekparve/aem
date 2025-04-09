package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class TestimonialCarouselModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class TestimonialCarouselModelTest {

    
    /** The testimonial carousel model. */
    @InjectMocks
    TestimonialCarouselModel testimonialCarouselModel;
    
    @Mock
	Resource currentResource;
	
    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
	void setUp() throws Exception {
    	TestimonialCarouselDetailsModel testimonialCarouselDetailsModel = new TestimonialCarouselDetailsModel();
		PrivateAccessor.setField(testimonialCarouselDetailsModel,"department","test-department");
		
		ArrayList<TestimonialCarouselDetailsModel> testimonialCarouselList = new ArrayList<>();
		testimonialCarouselList.add(testimonialCarouselDetailsModel);
		PrivateAccessor.setField(testimonialCarouselModel,"slides",testimonialCarouselList);
		
		PrivateAccessor.setField(testimonialCarouselModel, "title", "title");
		when(currentResource.getName()).thenReturn("testimonialCarouselModel");
		when(currentResource.getParent()).thenReturn(currentResource);
	}
   
    
    /**
     * Test getters.
     */
    @Test
	void testGetters() {	
		assertNotNull(testimonialCarouselModel.getSlides());
		assertNotNull(testimonialCarouselModel.getSlides().get(0));
		
		assertNotNull(testimonialCarouselModel.getTitle());
        assertNotNull(testimonialCarouselModel.getArticleId());
	}
    
    /**
     * Test fields.
     * @throws NoSuchFieldException 
     */
    @Test
	void testFields() throws NoSuchFieldException {
    	PrivateAccessor.setField(testimonialCarouselModel, "title", "");
    	
		assertEquals("test-department",testimonialCarouselModel.getSlides().get(0).getDepartment());
		assertNotNull(testimonialCarouselModel.getTitle());
        assertNotNull(testimonialCarouselModel.getArticleId());
	}

}
