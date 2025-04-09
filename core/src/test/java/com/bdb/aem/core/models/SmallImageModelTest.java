package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.*;
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
 * The Class SmallImageModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class SmallImageModelTest {
	
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
	
    /** The small image model. */
    @InjectMocks
    SmallImageModel smallImageModel;

    /** The label. */
    private final String LABEL = "label";
	
	/** The path. */
	private final String PATH = "PATH";
	
	/** The ret path. */
	private final String RET_PATH = "https://www.testurl.com";
    
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(smallImageModel, "label", LABEL);
		PrivateAccessor.setField(smallImageModel, "path", PATH);
		PrivateAccessor.setField(smallImageModel, "enlargedImagePath", PATH);
		PrivateAccessor.setField(smallImageModel, "imageEnlargeSize", "large");
		PrivateAccessor.setField(smallImageModel, "smallImageTitle", "smallImageTitle");
		PrivateAccessor.setField(smallImageModel, "smallImageCaption", "smallImageCaption");
		PrivateAccessor.setField(smallImageModel, "smallImageDescription", "smallImageDescription");
		PrivateAccessor.setField(smallImageModel, "backgroundColorSmallImage", "backgroundColorSmallImage");
	}
	
	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		lenient().when(externalizerService.getFormattedUrl(PATH, resourceResolver)).thenReturn(RET_PATH);
		assertEquals(LABEL, smallImageModel.getLabel());
		assertEquals(PATH, smallImageModel.getPath());
		assertEquals(PATH, smallImageModel.getEnlargedImagePath());
		assertEquals("large", smallImageModel.getImageEnlargeSize());
		assertEquals("smallImageTitle", smallImageModel.getSmallImageTitle());
		assertEquals("smallImageCaption", smallImageModel.getSmallImageCaption());
		assertEquals("smallImageDescription", smallImageModel.getSmallImageDescription());
		assertEquals("backgroundColorSmallImage", smallImageModel.getBackgroundColorSmallImage());
	}
	
	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
    void testInit() throws LoginException{
        smallImageModel.init();
    }

    /**
     * Test init login exception.
     *
     * @throws LoginException the login exception
     */
    @Test
	void testInitLoginException() throws LoginException {
		smallImageModel.init();
	}
}
