package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

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
 * The Class RegionMultifieldModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class RegionMultifieldModelTest {
	
	
	 /**  The ComponentContext context *. */
    @Mock
    ComponentContext context;
    
    /** The externalizer service. */
    @Mock
    ExternalizerService externalizerService;
    
    /** The resource resolver. */
    @Mock
    private ResourceResolver resourceResolver;

    /** The resource resolver factory. */
    @Mock
    private ResourceResolverFactory resourceResolverFactory;
	
	
	/** The region multifield model. */
	@InjectMocks
	RegionMultifieldModel regionMultifieldModel;

	
	/**
	 * Sets things up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		lenient().when(externalizerService.getFormattedUrl("/root/country", resourceResolver)).thenReturn("/root/country");
        
		CountryMultifieldModel countryMultifieldModel = new CountryMultifieldModel();
		PrivateAccessor.setField(countryMultifieldModel,"country","Spain");
		PrivateAccessor.setField(countryMultifieldModel,"urlCountry","/root/country");
		List<CountryMultifieldModel> countryList = new ArrayList<CountryMultifieldModel>();
		countryList.add(countryMultifieldModel);
		PrivateAccessor.setField(regionMultifieldModel,"countrymultifield",countryList);
		PrivateAccessor.setField(regionMultifieldModel,"region","Europe");
		PrivateAccessor.setField(regionMultifieldModel,"compareCountry","france(french)");
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertNotNull(regionMultifieldModel.getCountrymultifield());
		assertNotNull(regionMultifieldModel.getRegion());
		assertNotNull(regionMultifieldModel.getCompareCountry());
	}
	
	/**
	 * Test field values.
	 */
	@Test
	void testFields() {
		
		assertEquals("france(french)",regionMultifieldModel.getCompareCountry());
		assertEquals("Europe",regionMultifieldModel.getRegion());
		assertEquals("Spain",regionMultifieldModel.getCountrymultifield().get(0).getCountry());
	}
	
	/**
     * Test init.
	 * @throws LoginException 
     */
    @Test
    void testInit() throws LoginException{
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		regionMultifieldModel.init();
    }
    
    /**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		regionMultifieldModel.init();
	}
}
