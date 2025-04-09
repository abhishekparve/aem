package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.ProtocolTextLinkModel;
import com.bdb.aem.core.services.ExternalizerService;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class ProtocolModelImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ProtocolModelImplTest {
	
	/** The externalizer service. */
	@Mock
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;
    
    /** The context. */
    @Mock
    ComponentContext context;
    
    /** The context aem. */
    private AemContext contextAem;
    
    /** The protocol test model. */
    @InjectMocks
    ProtocolModelImpl protocolTestModel;
	
	/** The label. */
	private final String LABEL = "Testlabel";
	
	/** The path. */
	private final String PATH = "/dummy/test";
	
	/** The ret path. */
	private final String RET_PATH = "https://www.testurl.com/dummy/test";
	@Mock
    Resource resource;
	@Mock
	ProtocolTextLinkModel protocolTextLinkModel;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		Map<String, String> protocolProperty = new HashMap<>();		
		protocolProperty.put("label", LABEL);
		protocolProperty.put("path", PATH);
		protocolProperty.put("useAsAsset", "true");
		//protocolResource = contextAem.create().resource("/root/protocol", protocolProperty);
		//protocolMultifield = Arrays.asList(protocolResource);
		List<Resource> protocolMultifield = new ArrayList<>();
		protocolMultifield.add(resource);
		contextAem.addModelsForClasses(ProtocolModelImpl.class);
		PrivateAccessor.setField(protocolTestModel, "protocolTitle", "protocolTitle");
		PrivateAccessor.setField(protocolTestModel, "isIncluded", "true");
		PrivateAccessor.setField(protocolTestModel, "description", "description");
		PrivateAccessor.setField(protocolTestModel, "videoEnabled", false);
		PrivateAccessor.setField(protocolTestModel, "brightcoveVideoId", "12345");		
		PrivateAccessor.setField(protocolTestModel, "imagePath", PATH);
		PrivateAccessor.setField(protocolTestModel, "imageAltText", "imageAltText");
		PrivateAccessor.setField(protocolTestModel, "viewMoreLabel", "viewMoreLabel");
		PrivateAccessor.setField(protocolTestModel, "viewLessLabel", "viewLessLabel");
		PrivateAccessor.setField(protocolTestModel, "protocolMultifield", protocolMultifield);
	}

	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInit() throws LoginException {
		lenient().when(resource.adaptTo(ProtocolTextLinkModel.class)).thenReturn(protocolTextLinkModel);
		lenient().when(protocolTextLinkModel.getLabel()).thenReturn(LABEL);
		protocolTestModel.init();
	}
	
	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		lenient().when(externalizerService.getFormattedUrl(PATH, resourceResolver)).thenReturn(RET_PATH);
		assertEquals("protocolTitle", protocolTestModel.getProtocolTitle());
		assertEquals("true", protocolTestModel.isIncluded());
		assertEquals("description", protocolTestModel.getDescription());
		assertEquals(PATH, protocolTestModel.getImagePath());
		assertEquals(false, protocolTestModel.isVideoEnabled());
		assertEquals("12345", protocolTestModel.getBrightcoveVideoId());
		assertEquals("imageAltText", protocolTestModel.getImageAltText());
		assertEquals("viewMoreLabel", protocolTestModel.getViewMoreLabel());
		assertEquals("viewLessLabel", protocolTestModel.getViewLessLabel());
		assertEquals(0, protocolTestModel.getProtocolCount());
		assertNotNull(protocolTestModel.getProtocolsList());
	}
}
