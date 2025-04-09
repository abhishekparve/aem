package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

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

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class InteractiveCarouselIconModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class InteractiveCarouselIconModelTest {

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
    
    /** The path. */
    private final String PATH = "PATH";
    
    /** The ret path. */
    private final String RET_PATH = "RET_PATH";
	
	/** The icon model. */
    @InjectMocks
    InteractiveCarouselIconModel iconModel;
    
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(iconModel, "iconPath", PATH);
		PrivateAccessor.setField(iconModel, "altText", "altText");
		PrivateAccessor.setField(iconModel, "subHeading", "subHeading");
		PrivateAccessor.setField(iconModel, "subDescription", "subDescription");
	}
	
	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		lenient().when(externalizerService.getFormattedUrl(PATH, resourceResolver)).thenReturn(RET_PATH);
		assertEquals(PATH, iconModel.getIconPath());
		assertEquals("altText", iconModel.getAltText());
		assertEquals("subHeading", iconModel.getSubHeading());
		assertEquals("subDescription", iconModel.getSubDescription());
	}

	/**
	 * Test init.
	 */
	@Test
    void testInit() {
        iconModel.init();
    }
}
