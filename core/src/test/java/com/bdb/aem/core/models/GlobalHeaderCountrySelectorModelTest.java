package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.impl.BDBApiEndpointServiceImpl;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class GlobalHeaderModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class GlobalHeaderCountrySelectorModelTest {
	
	
	/** The global header model. */
	@InjectMocks
	GlobalHeaderCountrySelectorModel globalHeaderCountrySelectorModel;

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
    
    /** The bdb api endpoint service. */
    @InjectMocks
	BDBApiEndpointService bdbApiEndpointService = new BDBApiEndpointServiceImpl();


	/**
	 * Sets things up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		RegionMultifieldModel regionMultifieldModel = new RegionMultifieldModel();
		PrivateAccessor.setField(regionMultifieldModel,"region","Europe");
		
		ArrayList<RegionMultifieldModel> regionmultifield = new ArrayList<>();
		regionmultifield.add(regionMultifieldModel);
		
		PrivateAccessor.setField(globalHeaderCountrySelectorModel,"regionmultifield",regionmultifield);
	}

	
	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertNotNull(globalHeaderCountrySelectorModel.getRegionmultifield());
		assertNotNull(globalHeaderCountrySelectorModel.getRegionmultifield().get(0));
		//assertNull();
	}
	
	/**
	 * Test fields.
	 */
	@Test
	void testFields() {
		assertEquals("Europe",globalHeaderCountrySelectorModel.getRegionmultifield().get(0).getRegion());
	}
    
    /**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
	}
}
