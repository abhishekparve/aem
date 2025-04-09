package com.bdb.aem.core.models;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.ResourceResolver;
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
 * The Class ListFlowcytometryNewsModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ListFlowcytometryNewsModelTest {

	/** The externalizer service. */
	@Mock
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;

    /** The context. */
    @Mock
    ComponentContext context;
    
    /** The flowcytometry model. */
    @InjectMocks
    ListFlowcytometryNewsModel flowcytometryModel;
    
    /** The ret path. */
    public static String RET_PATH = "http://domain.test.com";
    
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(flowcytometryModel, "publicationDate", "publicationDate");
		PrivateAccessor.setField(flowcytometryModel, "newsTitle", "newsTitle");
		PrivateAccessor.setField(flowcytometryModel, "newsLink", "newsLink");
		PrivateAccessor.setField(flowcytometryModel, "readMoreLabel", "readMoreLabel");
		PrivateAccessor.setField(flowcytometryModel, "readMoreLink", "readMoreLink");
	}

	/**
	 * Test all getters.
	 */
	@Test
    void testAllGetters() {
        lenient().when(externalizerService.getFormattedUrl("newsLink", resourceResolver)).thenReturn(RET_PATH);
        lenient().when(externalizerService.getFormattedUrl("readMoreLink", resourceResolver)).thenReturn(RET_PATH);

        assertEquals("publicationDate", flowcytometryModel.getPublicationDate());
        assertEquals("newsTitle", flowcytometryModel.getNewsTitle());
        assertEquals("newsLink", flowcytometryModel.getNewsLink());
        assertEquals("readMoreLabel", flowcytometryModel.getReadMoreLabel());
        assertEquals("readMoreLink", flowcytometryModel.getReadMoreLink());
    }
    
    /**
     * Test init.
     */
    @Test
    void testInit(){
    	flowcytometryModel.init();
    }
}
