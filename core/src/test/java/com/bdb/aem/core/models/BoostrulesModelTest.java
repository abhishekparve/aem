package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

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
import com.bdb.aem.core.services.impl.EventDetailsCarouselServiceImpl;
import com.bdb.aem.core.util.CommonHelper;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The test class Boost rules Model Test.
 * 
 */

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class BoostrulesModelTest {

	@InjectMocks
	private BoostrulesModel boostrulesModel;

    
    /** The boostRules */
    @Mock
    private Resource boostRules;


	/**
	 * Sets up.
	 * 
	 * @throws NoSuchFieldException
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {

		PrivateAccessor.setField(boostrulesModel, "boostRules", boostRules);
		PrivateAccessor.setField(boostrulesModel, "preferenceOrder", 0);
		PrivateAccessor.setField(boostrulesModel, "facetAttributes", "facetAttributes");
 
	}

	/**
	 * Test init.
	 * 
	 * @throws LoginException
	 */
	@Test
	void testInit() throws LoginException {
		
		boostrulesModel.init();
	}
	
	/**
	 * Tests all getters.
	 * 
	 */
	@Test
	void testAllGetters() {
		assertNotNull(boostrulesModel.getBoostRules());
		assertNotNull(boostrulesModel.getFacetAttributes());
		assertNotNull(boostrulesModel.getPreferenceOrder());
		
	}
	
}
