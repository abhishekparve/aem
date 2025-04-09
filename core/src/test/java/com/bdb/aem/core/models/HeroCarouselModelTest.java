package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class HeroCarouselModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class HeroCarouselModelTest {

    
    
    /** The hero carousel model. */
    @InjectMocks
    HeroCarouselModel heroCarouselModel;
    
    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
	void setUp() throws Exception {
    	HeroCarouselMultifieldModel heroCarouselMultifieldModel = new HeroCarouselMultifieldModel();
		PrivateAccessor.setField(heroCarouselMultifieldModel,"heroTitle","testTitle");
		
		ArrayList<HeroCarouselMultifieldModel> heroCarouselModelList = new ArrayList<>();
		heroCarouselModelList.add(heroCarouselMultifieldModel);
		
		PrivateAccessor.setField(heroCarouselModel,"slides",heroCarouselModelList);
	}
   
    
    /**
     * Test getters.
     */
    @Test
	void testGetters() {
		assertNotNull(heroCarouselModel.getSlides());
		assertNotNull(heroCarouselModel.getSlides().get(0));
	}
    
    /**
     * Test fields.
     */
    @Test
	void testFields() {
		assertEquals("testTitle",heroCarouselModel.getSlides().get(0).getHeroTitle());
	}

}
