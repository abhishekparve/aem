package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.Date;
import com.bdb.aem.core.services.BDBApiEndpointService;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

	
/**
 * The Class PromoDetailsModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class PromoDetailsModelTest {

    /** Mock ResourceResolverFactory. */
    @Mock
    ExternalizerService externalizerService;


    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;

    /**  The ComponentContext context *. */
    @Mock
    ComponentContext context;

	/** The sling settings service. */
	@Mock
	SlingSettingsService slingSettingsService;


    /** The promo details model. */
    @InjectMocks
    PromoDetailsModel promoDetailsModel;

    /** The bdb api endpoint service. */
    @Mock
    BDBApiEndpointService bdbApiEndpointService;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
        PrivateAccessor.setField(promoDetailsModel,"expirationDate",new Date());
    	PrivateAccessor.setField(promoDetailsModel, "imagePath", "imagePath");
		PrivateAccessor.setField(promoDetailsModel, "altText", "altText");   
		PrivateAccessor.setField(promoDetailsModel, "title", "title");    
		PrivateAccessor.setField(promoDetailsModel, "shortDescription", "shortDescription");
		PrivateAccessor.setField(promoDetailsModel, "expirationLabel", "expirationLabel");   
		PrivateAccessor.setField(promoDetailsModel, "promoLabel", "promoLabel");
		PrivateAccessor.setField(promoDetailsModel, "promoCodeValue", "promoCodeValue");    	
		PrivateAccessor.setField(promoDetailsModel, "promoDescription", "promoDescription");
		PrivateAccessor.setField(promoDetailsModel, "labelCta", "labelCta");
    	PrivateAccessor.setField(promoDetailsModel, "urlCta", "urlCta");
		PrivateAccessor.setField(promoDetailsModel, "longDescription", "longDescription");
        PrivateAccessor.setField(promoDetailsModel, "promoId", "12345");
    }

    /**
     * Test all getters.
     */
    @Test
    void testAllGetters() {
    	lenient().when(externalizerService.getFormattedUrl("imagePath", resourceResolver)).thenReturn("/content/dam/image.png");
    	lenient().when(externalizerService.getFormattedUrl("urlCta", resourceResolver)).thenReturn("www.examplepath.com");
        assertNotNull(promoDetailsModel.getImagePath());
        assertNotNull(promoDetailsModel.getAltText());
        assertNotNull(promoDetailsModel.getExpirationDate());
        assertNotNull(promoDetailsModel.getExpirationLabel());
        assertNotNull(promoDetailsModel.getLabelCta());
        assertNotNull(promoDetailsModel.getLongDescription());
        assertNotNull(promoDetailsModel.getShortDescription());
        assertNotNull(promoDetailsModel.getPromoDescription());
        assertNotNull(promoDetailsModel.getTitle());
        assertNotNull(promoDetailsModel.getPromoCodeValue());
        assertNotNull(promoDetailsModel.getPromoLabel());
        assertNotNull(promoDetailsModel.getUrlCta());
        assertNotNull(promoDetailsModel.getPromoId());
        
        
    }

    /**
     * Test init.
     * @throws NoSuchFieldException 
     */
    @Test
    void testInit() throws NoSuchFieldException{
    	lenient().when(externalizerService.getFormattedUrl("urlCta", resourceResolver)).thenReturn("www.examplepath.com");
        when(bdbApiEndpointService.getCustomRunMode()).thenReturn("author");
    	promoDetailsModel.init();
    }
}
