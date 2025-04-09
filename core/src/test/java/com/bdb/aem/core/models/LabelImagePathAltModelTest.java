package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
 * The Class LabelImagePathAltModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class LabelImagePathAltModelTest {

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
	
    /** The label image path alt model. */
    @InjectMocks
    LabelImagePathAltModel labelImagePathAltModel;
    
    /** The label. */
    private final String LABEL = "label";
	
	/** The path. */
	private final String PATH = "PATH";
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(labelImagePathAltModel, "label", LABEL);
		PrivateAccessor.setField(labelImagePathAltModel, "path", PATH);
		PrivateAccessor.setField(labelImagePathAltModel, "altText", LABEL);
	}
	
	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		lenient().when(externalizerService.getFormattedUrl(PATH, resourceResolver)).thenReturn("https://www.testurl.com");
		assertEquals(labelImagePathAltModel.getLabel(), LABEL);
		assertNotNull(labelImagePathAltModel.getPath());
		assertEquals(labelImagePathAltModel.getAltText(), LABEL);
	}
	
	/**
	 * Test init.
	 */
	@Test
    void testInit(){
        labelImagePathAltModel.init();
    }
}
