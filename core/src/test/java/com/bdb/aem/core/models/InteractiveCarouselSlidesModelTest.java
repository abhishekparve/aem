package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class InteractiveCarouselSlidesModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class InteractiveCarouselSlidesModelTest {

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
    
    /** The aem context. */
    private AemContext aemContext;
	
    /** The interactive carousel slides model. */
    @InjectMocks
    InteractiveCarouselSlidesModel interactiveCarouselSlidesModel;
    
    /** The label. */
    private final String LABEL = "label";
	
	/** The path. */
	private final String PATH = "PATH";
	
	/** The ret path. */
	private final String RET_PATH = "http://domain.test.com";
	
	/** The icons multifield. */
	private List<Resource> iconsMultifield;
	
	/** The icons resource. */
	private Resource iconsResource;
    
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		Map<String, String> iconsProperty = new HashMap<>();		
		iconsProperty.put("iconPath", PATH);
		iconsProperty.put("altText", LABEL);
		iconsProperty.put("subHeading", LABEL);
		iconsProperty.put("subDescription", LABEL);
		iconsResource = aemContext.create().resource("/root/icons", iconsProperty);
		iconsMultifield = Arrays.asList(iconsResource);
		
		PrivateAccessor.setField(interactiveCarouselSlidesModel, "title", LABEL);
		PrivateAccessor.setField(interactiveCarouselSlidesModel, "subTitle", LABEL);
		PrivateAccessor.setField(interactiveCarouselSlidesModel, "sliderTitle", LABEL);
		PrivateAccessor.setField(interactiveCarouselSlidesModel, "sliderIconPath", PATH);
		PrivateAccessor.setField(interactiveCarouselSlidesModel, "sliderIconAltText", LABEL);
		PrivateAccessor.setField(interactiveCarouselSlidesModel, "ctaLabel", LABEL);
		PrivateAccessor.setField(interactiveCarouselSlidesModel, "ctaPath", PATH);
		PrivateAccessor.setField(interactiveCarouselSlidesModel, "secondaryCtaLabel", LABEL);
		PrivateAccessor.setField(interactiveCarouselSlidesModel, "secondaryCtaPath", PATH);
		PrivateAccessor.setField(interactiveCarouselSlidesModel, "iconsMultifield", iconsMultifield);
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		lenient().when(externalizerService.getFormattedUrl(PATH, resourceResolver)).thenReturn(RET_PATH);
		assertEquals(interactiveCarouselSlidesModel.getTitle(), LABEL);
		assertEquals(interactiveCarouselSlidesModel.getSubTitle(), LABEL);
		assertEquals(interactiveCarouselSlidesModel.getSliderTitle(), LABEL);
		assertEquals(interactiveCarouselSlidesModel.getSliderIconPath(), PATH);
		assertEquals(interactiveCarouselSlidesModel.getSliderIconAltText(), LABEL);
		assertEquals(interactiveCarouselSlidesModel.getCtaLabel(), LABEL);
		assertEquals(interactiveCarouselSlidesModel.getCtaPath(), PATH);
		assertEquals(interactiveCarouselSlidesModel.getSecondaryCtaLabel(), LABEL);
		assertEquals(interactiveCarouselSlidesModel.getSecondaryCtaPath(), PATH);
		assertNotNull(interactiveCarouselSlidesModel.getIconsList());
	}
	
	/**
	 * Test init.
	 */
	@Test
    void testInit() {
        interactiveCarouselSlidesModel.init();
    }
}
