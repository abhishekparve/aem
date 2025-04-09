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
 * The Class InteractiveCarouselModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class InteractiveCarouselModelTest {
	
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
	
    /** The interactive carousel model. */
    @InjectMocks
    InteractiveCarouselModel interactiveCarouselModel;
    
    /** The label. */
    private final String LABEL = "LABEL";
	
	/** The path. */
	private final String PATH = "PATH";
	
	/** The ret path. */
	private final String RET_PATH = "http://domain.test.com";
	
	/** The slides multifield. */
	private List<Resource> slidesMultifield;
	
	/** The slides resource. */
	private Resource slidesResource;
	
	/** The context aem. */
	private AemContext contextAem;
    
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		Map<String, String> slidesProperty = new HashMap<>();		
		slidesProperty.put("title", LABEL);
		slidesProperty.put("subTitle", LABEL);
		slidesProperty.put("iconPath", PATH);
		slidesProperty.put("altText", LABEL);
		slidesProperty.put("sliderIconPath", PATH);
		slidesProperty.put("subHeading", LABEL);
		slidesProperty.put("subDescription", LABEL);
		slidesProperty.put("ctaLabel", LABEL);
		slidesProperty.put("ctaPath", PATH);
		slidesProperty.put("secondaryCtaLabel", LABEL);
		slidesProperty.put("secondaryCtaPath", PATH);
		slidesResource = contextAem.create().resource("/root/slides", slidesProperty);
		slidesMultifield = Arrays.asList(slidesResource);
		
		PrivateAccessor.setField(interactiveCarouselModel, "backgroundImage", PATH);
		PrivateAccessor.setField(interactiveCarouselModel, "slidesMultifield", slidesMultifield);
	}
	
	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		lenient().when(externalizerService.getFormattedUrl(PATH, resourceResolver)).thenReturn(RET_PATH);
		assertEquals(PATH, interactiveCarouselModel.getBackgroundImage());
		assertNotNull(interactiveCarouselModel.getSlidesList());
		interactiveCarouselModel.init();
		assertNotNull(interactiveCarouselModel.getSlideBarJson());
	}
	
	/**
	 * Test init.
	 *
	 */
	@Test
    void testInit() {
        interactiveCarouselModel.init();
    }
}
