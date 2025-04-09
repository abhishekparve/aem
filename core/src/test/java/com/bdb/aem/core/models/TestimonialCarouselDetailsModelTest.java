package com.bdb.aem.core.models;

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

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class TestimonialCarouselDetailsModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class TestimonialCarouselDetailsModelTest {

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

    /** The testimonial carousel details model. */
    @InjectMocks
    TestimonialCarouselDetailsModel testimonialCarouselDetailsModel;

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
    	PrivateAccessor.setField(testimonialCarouselDetailsModel, "imagePath", "imagePath");
		PrivateAccessor.setField(testimonialCarouselDetailsModel, "altImage", "altImage");
		PrivateAccessor.setField(testimonialCarouselDetailsModel, "description", "description");
		PrivateAccessor.setField(testimonialCarouselDetailsModel, "pname", "pname");
		PrivateAccessor.setField(testimonialCarouselDetailsModel, "department", "department");
		PrivateAccessor.setField(testimonialCarouselDetailsModel, "university", "university");
		PrivateAccessor.setField(testimonialCarouselDetailsModel, "ctaLabel", "ctaLabel");
		PrivateAccessor.setField(testimonialCarouselDetailsModel, "ctaUrl", "ctaUrl");
		PrivateAccessor.setField(testimonialCarouselDetailsModel, "videoEnabled", "videoEnabled");
		PrivateAccessor.setField(testimonialCarouselDetailsModel, "playVideoLabel", "playVideoLabel");
		PrivateAccessor.setField(testimonialCarouselDetailsModel, "brightcoveVideoId", "brightcoveVideoId");
		PrivateAccessor.setField(testimonialCarouselDetailsModel, "fontStyle", "fontStyle");
		PrivateAccessor.setField(testimonialCarouselDetailsModel, "bgColor", "bgColor");
		PrivateAccessor.setField(testimonialCarouselDetailsModel, "fontColor", "fontColor");
		PrivateAccessor.setField(testimonialCarouselDetailsModel, "ctaTxtColor", "White");
		PrivateAccessor.setField(testimonialCarouselDetailsModel, "ctaBgColor", "Black");
		PrivateAccessor.setField(testimonialCarouselDetailsModel, "extraCtaLabel", "extraCtaLabel");
		PrivateAccessor.setField(testimonialCarouselDetailsModel, "extraCtaUrl", "extraCtaUrl");

    }

    /**
     * Test all getters.
     */
    @Test
    void testAllGetters() {
    	lenient().when(externalizerService.getFormattedUrl("imagePath", resourceResolver)).thenReturn("/content/dam/image.png");
    	lenient().when(externalizerService.getFormattedUrl("ctaUrl", resourceResolver)).thenReturn("www.examplepath.com");
    	lenient().when(externalizerService.getFormattedUrl("extraCtaUrl", resourceResolver)).thenReturn("www.examplepath.com");

    	assertNotNull(testimonialCarouselDetailsModel.getAltImage());
        assertNotNull(testimonialCarouselDetailsModel.getCtaLabel());
        assertNotNull(testimonialCarouselDetailsModel.getCtaUrl());
        assertNotNull(testimonialCarouselDetailsModel.getDepartment());
        assertNotNull(testimonialCarouselDetailsModel.getDescription());
        assertNotNull(testimonialCarouselDetailsModel.getPname());
        assertNotNull(testimonialCarouselDetailsModel.getImagePath());
        assertNotNull(testimonialCarouselDetailsModel.getUniversity());
        assertNotNull(testimonialCarouselDetailsModel.isVideoEnabled());
        assertNotNull(testimonialCarouselDetailsModel.getPlayVideoLabel());
        assertNotNull(testimonialCarouselDetailsModel.getBrightcoveVideoId());
        assertNotNull(testimonialCarouselDetailsModel.getFontStyle());
        assertNotNull(testimonialCarouselDetailsModel.getBgColor());
        assertNotNull(testimonialCarouselDetailsModel.getFontColor());
        assertNotNull(testimonialCarouselDetailsModel.getCtaTxtColor());
        assertNotNull(testimonialCarouselDetailsModel.getCtaBgColor());
        assertNotNull(testimonialCarouselDetailsModel.getExtraCtaLabel());
    	assertNotNull(testimonialCarouselDetailsModel.getExtraCtaUrl());

    }
    
    /**
     * Test null.
     *
     * @throws NoSuchFieldException the no such field exception
     */
    @Test
    void testFontStyleNull() throws NoSuchFieldException {
		PrivateAccessor.setField(testimonialCarouselDetailsModel, "fontStyle", "");
        assertNotNull(testimonialCarouselDetailsModel.getFontStyle());
    }

    /**
     * Test brightcove ids.
     */
    @Test
    void testBrightcoveIds() {
    	lenient().when(bdbApiEndpointService.brightcovePlayerId()).thenReturn("/content/dam/image-mob.png");
    	lenient().when(bdbApiEndpointService.brightcoveAccountId()).thenReturn("/content/dam/image-mob.png");
    	
        assertNotNull(testimonialCarouselDetailsModel.getBrightcoveAccountId());
        assertNotNull(testimonialCarouselDetailsModel.getBrightcovePlayerId());
    }

    /**
     * Test init.
     *
     * @throws LoginException the login exception
     */
    @Test
    void testInit() throws LoginException{
    	testimonialCarouselDetailsModel.init();
    }
}
