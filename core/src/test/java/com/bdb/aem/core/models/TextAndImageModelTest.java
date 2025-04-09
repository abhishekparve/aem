package com.bdb.aem.core.models;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
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
 * The Class TextAndImageModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class TextAndImageModelTest {

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

    /** The text and image model. */
    @InjectMocks
    TextAndImageModel textAndImageModel ;

	@Mock
	Resource currentResource;

    
    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
		PrivateAccessor.setField(textAndImageModel, "largeEnlargedImagePath", "largeEnlargedImagePath");
		PrivateAccessor.setField(textAndImageModel, "title", "title");
		PrivateAccessor.setField(textAndImageModel, "sectionTitle", "sectionTitle");
		PrivateAccessor.setField(textAndImageModel, "disclaimerFontSize", "disclaimerFontSize");
		PrivateAccessor.setField(textAndImageModel, "description", "description");
		lenient().when(currentResource.getName()).thenReturn("TextandImage");
		lenient().when(currentResource.getParent()).thenReturn(currentResource);
		
    }
    
    /**
     * Test getters.
     */
    @Test
    void testGetters() throws Exception{
    	SmallImageModel smallImageModel = new SmallImageModel();
		PrivateAccessor.setField(smallImageModel, "label", "label");
		List<SmallImageModel> sections = new ArrayList<>();
		sections.add(smallImageModel);
		PrivateAccessor.setField(textAndImageModel,"imageList",sections);
		PrivateAccessor.setField(textAndImageModel, "playVideoIcon", "playVideoIcon");
		PrivateAccessor.setField(textAndImageModel, "largeUrl", "largeUrl");
        lenient().when(externalizerService.getFormattedUrl("playVideoIcon", resourceResolver)).thenReturn("/content/dam/image");
        lenient().when(externalizerService.getFormattedUrl("largeUrl", resourceResolver)).thenReturn("www.google.com");
        lenient().when(externalizerService.getFormattedUrl("largeEnlargedImagePath", resourceResolver)).thenReturn("www.google.com");

//        assertNotNull(textAndImageModel.getArticleId());
        assertNotNull(textAndImageModel.getPlayVideoIcon());
        assertNotNull(textAndImageModel.getLargeUrl());
        assertNotNull(textAndImageModel.getLargeEnlargedImagePath());
        assertNotNull(textAndImageModel.getImageList());
		assertNotNull(textAndImageModel.getImageList().get(0));
		assertEquals("disclaimerFontSize", textAndImageModel.getDisclaimerFontSize());
		assertNotNull(textAndImageModel.getDescription());
    }
    
   
    /**
     * Test brightcove ids.
     */
    @Test
    void testBrightcoveIds() {
    	lenient().when(bdbApiEndpointService.brightcovePlayerId()).thenReturn("/content/dam/image-mob.png");
    	lenient().when(bdbApiEndpointService.brightcoveAccountId()).thenReturn("/content/dam/image-mob.png");
        assertNotNull(textAndImageModel.getBrightcoveAccountId());
        assertNotNull(textAndImageModel.getBrightcovePlayerId());
    }
    
    /**
     * Test null.
     *
     * @throws NoSuchFieldException the no such field exception
     */
    @Test
    void testFontStyleNull() throws NoSuchFieldException {
		PrivateAccessor.setField(textAndImageModel, "playVideoIcon", "");
		PrivateAccessor.setField(textAndImageModel, "sectionTitle", "");
		PrivateAccessor.setField(textAndImageModel, "largeUrl", "");
		PrivateAccessor.setField(textAndImageModel, "largeEnlargedImagePath", "");  
        assertNotNull(textAndImageModel.getPlayVideoIcon());
        assertNotNull(textAndImageModel.getLargeUrl());
//        assertNotNull(textAndImageModel.getArticleId());
        assertNotNull(textAndImageModel.getLargeEnlargedImagePath());
        textAndImageModel.getImageList();
        textAndImageModel.init();
    }
    
    
    /**
     * Test init.
     *
     * @throws LoginException the login exception
     */
    @Test
    void testInit() throws LoginException,Exception{
    	PrivateAccessor.setField(textAndImageModel, "playVideoIcon", "playVideoIcon");
		PrivateAccessor.setField(textAndImageModel, "largeUrl", "largeUrl");
        textAndImageModel.init();
    }
}
