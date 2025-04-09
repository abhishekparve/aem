package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
 * The Class CountryMultifieldModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class CountryMultifieldModelTest {

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

   
    /** The country multifield model. */
    @InjectMocks
    CountryMultifieldModel countryMultifieldModel;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
    	lenient().when(externalizerService.getFormattedUrl("/root/country", resourceResolver)).thenReturn("/root/country");
        
    	PrivateAccessor.setField(countryMultifieldModel,"country","Spain");
		PrivateAccessor.setField(countryMultifieldModel,"urlCountry","/root/country");

    }

    /**
     * Test all getters.
     */
    @Test
    void testAllGetters() {
    	assertNotNull(countryMultifieldModel.getCountry());
    	assertNotNull(countryMultifieldModel.getUrlCountry());
    }
    
    /**
	 * Test field values.
	 */
	@Test
	void testFields() {
		
		assertEquals("Spain",countryMultifieldModel.getCountry());
		assertEquals("/root/country",countryMultifieldModel.getUrlCountry());
	}
   
    /**
     * Test init.
     *
     * @throws LoginException the login exception
     */
    @Test
    void testInit() throws LoginException{
        lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
        countryMultifieldModel.init();
    }
    
	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		countryMultifieldModel.init();
	}
}
