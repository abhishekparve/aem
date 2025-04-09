package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.lenient;

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
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class HeroCarouselMultifieldModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class HeroCarouselMultifieldModelTest {

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

    /** The hero carousel multifield model. */
    @InjectMocks
    HeroCarouselMultifieldModel heroCarouselMultifieldModel;

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
    	PrivateAccessor.setField(heroCarouselMultifieldModel, "imagePath", "imagePath");
		PrivateAccessor.setField(heroCarouselMultifieldModel, "mobileImagePath", "imagePathMobile");
		PrivateAccessor.setField(heroCarouselMultifieldModel, "altImage", "altImage");
		PrivateAccessor.setField(heroCarouselMultifieldModel, "ctaAlign", "ctaAlign");
		PrivateAccessor.setField(heroCarouselMultifieldModel, "heroTitle", "heroTitle");
		PrivateAccessor.setField(heroCarouselMultifieldModel, "description", "description");
		PrivateAccessor.setField(heroCarouselMultifieldModel, "labelCta", "labelCta");
		PrivateAccessor.setField(heroCarouselMultifieldModel, "urlCta", "urlCta");
		PrivateAccessor.setField(heroCarouselMultifieldModel, "labelExtra", "labelExtra");
		PrivateAccessor.setField(heroCarouselMultifieldModel, "urlExtra", "urlExtra");
		PrivateAccessor.setField(heroCarouselMultifieldModel, "carouselVideoEnabled", "carouselVideoEnabled");
		PrivateAccessor.setField(heroCarouselMultifieldModel, "carouselPlayVideoLabel", "carouselPlayVideoLabel");
		PrivateAccessor.setField(heroCarouselMultifieldModel, "thumbnail", "thumbnail");
		PrivateAccessor.setField(heroCarouselMultifieldModel, "carouselBrightcoveVideoId", "carouselBrightcoveVideoId");
		PrivateAccessor.setField(heroCarouselMultifieldModel, "allowAudio", "allowAudio");
		PrivateAccessor.setField(heroCarouselMultifieldModel, "urlVideo", "urlVideo");
		PrivateAccessor.setField(heroCarouselMultifieldModel, "source", "source");
		PrivateAccessor.setField(heroCarouselMultifieldModel, "darkMode", "darkMode");
		PrivateAccessor.setField(heroCarouselMultifieldModel, "ctaVariation", "ctaVariation");
    }

    /**
     * Test all getters.
     *
     * @throws NoSuchFieldException the no such field exception
     */
    @Test
    void testAllGetters() throws NoSuchFieldException {
    	PrivateAccessor.setField(heroCarouselMultifieldModel, "ctaVariation", "{\r\n" + "\"test\": \"test\"\r\n" +"}");
    	
    	lenient().when(externalizerService.getFormattedUrl("imagePath", resourceResolver)).thenReturn("/content/dam/image.png");
    	lenient().when(externalizerService.getFormattedUrl("mobileImagePath", resourceResolver)).thenReturn("/content/dam/image-mob.png");
    	lenient().when(externalizerService.getFormattedUrl("urlExtra", resourceResolver)).thenReturn("www.examplepathextra.com");
    	lenient().when(externalizerService.getFormattedUrl("urlCta", resourceResolver)).thenReturn("www.examplepath.com");
    	lenient().when(externalizerService.getFormattedUrl("urlVideo", resourceResolver)).thenReturn("www.examplepath.com");
    	lenient().when(externalizerService.getFormattedUrl("thumbnail", resourceResolver)).thenReturn("/content/dam/image-mob.png");

    	assertNotNull(heroCarouselMultifieldModel.getAltImage());
        assertNotNull(heroCarouselMultifieldModel.getCtaAlign());
        assertNotNull(heroCarouselMultifieldModel.getDescription());
        assertNotNull(heroCarouselMultifieldModel.getHeroTitle());
        assertNotNull(heroCarouselMultifieldModel.getImagePath());
        assertNotNull(heroCarouselMultifieldModel.getMobileImagePath());
        assertNotNull(heroCarouselMultifieldModel.getLabelCta());
        assertNotNull(heroCarouselMultifieldModel.getLabelExtra());
        assertNotNull(heroCarouselMultifieldModel.getUrlCta());
        assertNotNull(heroCarouselMultifieldModel.getUrlExtra());
        assertNotNull(heroCarouselMultifieldModel.getCarouselVideoEnabled());
        assertNotNull(heroCarouselMultifieldModel.getCarouselPlayVideoLabel());
        assertNotNull(heroCarouselMultifieldModel.getThumbnail());
        assertNotNull(heroCarouselMultifieldModel.getCarouselBrightcoveVideoId());
        assertNotNull(heroCarouselMultifieldModel.getAllowAudio());
        assertNotNull(heroCarouselMultifieldModel.getUrlVideo());
        assertNotNull(heroCarouselMultifieldModel.getSource());
        assertNotNull(heroCarouselMultifieldModel.getDarkMode());
        
        assertNotNull(heroCarouselMultifieldModel.getCtaVariation());
        assertNotNull(heroCarouselMultifieldModel.getCtaTxtColor());
        assertNotNull(heroCarouselMultifieldModel.getCtaBgColor());
    }
    
    /**
     * Test blank values.
     *
     * @throws NoSuchFieldException the no such field exception
     */
    @Test
    void testBlankValues() throws NoSuchFieldException {
    	PrivateAccessor.setField(heroCarouselMultifieldModel, "allowAudio", "");
    	PrivateAccessor.setField(heroCarouselMultifieldModel, "carouselVideoEnabled", "");
    	PrivateAccessor.setField(heroCarouselMultifieldModel, "carouselBrightcoveVideoId", "");
    	PrivateAccessor.setField(heroCarouselMultifieldModel, "carouselPlayVideoLabel", "");
    	
        assertNotNull(heroCarouselMultifieldModel.getAllowAudio());
        assertNotNull(heroCarouselMultifieldModel.getCarouselVideoEnabled());
        assertNotNull(heroCarouselMultifieldModel.getCarouselPlayVideoLabel());
        assertNotNull(heroCarouselMultifieldModel.getCarouselBrightcoveVideoId());
    }
    
    /**
     * Test video enabled yes.
     *
     * @throws NoSuchFieldException the no such field exception
     */
    @Test
    void testVideoEnabledYes() throws NoSuchFieldException {
    	PrivateAccessor.setField(heroCarouselMultifieldModel, "carouselVideoEnabled", "yes");
        assertNotNull(heroCarouselMultifieldModel.getCarouselVideoEnabled());
    }
    
    /**
     * Test cta variation.
     *
     * @throws NoSuchFieldException the no such field exception
     */
    @Test
    void testCtaVariation() throws NoSuchFieldException {
    	PrivateAccessor.setField(heroCarouselMultifieldModel, "ctaVariation", null);
    	assertNull(heroCarouselMultifieldModel.getCtaVariation());
    }
    
    /**
     * Test brightcove ids.
     */
    @Test
    void testBrightcoveIds() {
    	lenient().when(bdbApiEndpointService.brightcovePlayerId()).thenReturn("/content/dam/image-mob.png");
    	lenient().when(bdbApiEndpointService.brightcoveAccountId()).thenReturn("/content/dam/image-mob.png");
    	
        assertNotNull(heroCarouselMultifieldModel.getBrightcoveAccountId());
        assertNotNull(heroCarouselMultifieldModel.getBrightcovePlayerId());
    }

    /**
     * Test init.
     */
    @Test
    void testInit(){
    	heroCarouselMultifieldModel.init();
    }
}
