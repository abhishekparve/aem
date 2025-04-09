package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.LoginException;
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
 * The Class ProtocolTextLinkModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ProtocolTextLinkModelTest {
	
	/** The externalizer service. */
	@Mock
    ExternalizerService externalizerService;


    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;
    
    /** The context. */
    @Mock
    ComponentContext context;
	
    /** The protocol text link test model. */
    @InjectMocks
    ProtocolTextLinkModel protocolTextLinkTestModel;
	
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
		PrivateAccessor.setField(protocolTextLinkTestModel, "label", LABEL);
		PrivateAccessor.setField(protocolTextLinkTestModel, "path", PATH);
		PrivateAccessor.setField(protocolTextLinkTestModel, "useAsAsset", "true");
	}
	
	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		lenient().when(externalizerService.getFormattedUrl(PATH, resourceResolver)).thenReturn("https://www.testurl.com");
		assertEquals(protocolTextLinkTestModel.getLabel(), LABEL);
		assertEquals(protocolTextLinkTestModel.getUseAsAsset(), "true");
		assertNotNull(protocolTextLinkTestModel.getPath());
	}
	
	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
    void testInit() throws LoginException{
        protocolTextLinkTestModel.init();
    }
}
