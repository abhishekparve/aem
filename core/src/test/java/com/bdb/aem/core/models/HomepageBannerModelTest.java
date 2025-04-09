package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class HomepageBannerModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class HomepageBannerModelTest {

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
    
    /** The bdb api endpoint service. */
    @Mock
    BDBApiEndpointService bdbApiEndpointService;

    /** The homepage banner model. */
    @InjectMocks
    HomepageBannerModel homepageBannerModel;

    /**
     * Sets the up.
     *
     * @throws NoSuchFieldException the no such field exception
     */
    @BeforeEach
    void setUp() throws NoSuchFieldException{
    	PrivateAccessor.setField(homepageBannerModel, "imagePath", "imagePath");
		PrivateAccessor.setField(homepageBannerModel, "imagePathMobile", "imagePathMobile");
		PrivateAccessor.setField(homepageBannerModel, "ctaUrl", "ctaUrl");
		PrivateAccessor.setField(homepageBannerModel, "videoEnabled", "yes");
		PrivateAccessor.setField(homepageBannerModel, "playVideoLabel", "playVideoLabel");
		PrivateAccessor.setField(homepageBannerModel, "thumbnail", "thumbnail");
		PrivateAccessor.setField(homepageBannerModel, "brightcoveVideoId", "brightcoveVideoId");
		PrivateAccessor.setField(homepageBannerModel, "urlVideo", "urlVideo");
		PrivateAccessor.setField(homepageBannerModel, "source", "source");
		PrivateAccessor.setField(homepageBannerModel, "darkMode", "darkMode");
		PrivateAccessor.setField(homepageBannerModel, "fontColor", "fontColor");
		PrivateAccessor.setField(homepageBannerModel, "buttonURL", "buttonURL");
    	
    }
    
    /**
     * Test all getters.
     *
     * @throws NoSuchFieldException the no such field exception
     */
    @Test
    void testAllGetters() throws NoSuchFieldException {
    	PrivateAccessor.setField(homepageBannerModel, "ctaVariation", "{\r\n" + "\"test\": \"test\"\r\n" +"}");

        lenient().when(externalizerService.getFormattedUrl("imagePath", resourceResolver)).thenReturn("/content/dam/image.png");
    	lenient().when(externalizerService.getFormattedUrl("imagePathMobile", resourceResolver)).thenReturn("/content/dam/image-mob.png");
    	lenient().when(externalizerService.getFormattedUrl("ctaUrl", resourceResolver)).thenReturn("www.examplepath.com");
    	lenient().when(externalizerService.getFormattedUrl("urlVideo", resourceResolver)).thenReturn("www.examplepath.com");
    	lenient().when(externalizerService.getFormattedUrl("thumbnail", resourceResolver)).thenReturn("/content/dam/image-mob.png");
    	
        assertNotNull(homepageBannerModel.getImagePath());
        assertNotNull(homepageBannerModel.getImagePathMobile());
        assertNotNull(homepageBannerModel.getCtaUrl());
        assertNotNull(homepageBannerModel.getVideoEnabled());
        assertNotNull(homepageBannerModel.getPlayVideoLabel());
        assertNotNull(homepageBannerModel.getThumbnail());
        assertNotNull(homepageBannerModel.getBrightcoveVideoId());
        assertNotNull(homepageBannerModel.getUrlVideo());
        assertNotNull(homepageBannerModel.getSource());
        assertNotNull(homepageBannerModel.getDarkMode());
        assertNotNull(homepageBannerModel.getFontColor());
        
        assertNotNull(homepageBannerModel.getCtaVariation());
    	assertNotNull(homepageBannerModel.getCtaTxtColor());
    	assertNotNull(homepageBannerModel.getCtaBgColor());
    }
    
    /**
     * Test cta variation.
     *
     * @throws NoSuchFieldException the no such field exception
     */
    @Test
    void testCtaVariation() throws NoSuchFieldException {
    	PrivateAccessor.setField(homepageBannerModel, "ctaVariation", null);
    	assertNull(homepageBannerModel.getCtaVariation());
    }
    
    /**
     * Test brightcove ids.
     */
    @Test
    void testBrightcoveIds() {
    	lenient().when(bdbApiEndpointService.brightcovePlayerId()).thenReturn("/content/dam/image-mob.png");
    	lenient().when(bdbApiEndpointService.brightcoveAccountId()).thenReturn("/content/dam/image-mob.png");
    	
        assertNotNull(homepageBannerModel.getBrightcoveAccountId());
        assertNotNull(homepageBannerModel.getBrightcovePlayerId());
    }

    /**
     * Test init.
     *
     * @throws LoginException the login exception
     */
    @Test
    void testInit() {
    	homepageBannerModel.init();
    }
}
