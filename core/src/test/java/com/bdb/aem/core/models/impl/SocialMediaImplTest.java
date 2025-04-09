package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.SocialMediaMultiFieldModel;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class SocialMediaImplTest {
	
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
	
	/** The social media component model. */
	@InjectMocks
	SocialMediaImpl socialMediaImpl;
	
	@Mock
	AemContext ct;

	List<Resource> socialMediaDetails = new ArrayList<Resource>();
	
	
	@Mock
	Resource resource;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {		
		
        lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
        socialMediaDetails.add(resource);
        SocialMediaMultiFieldModel socialMediaMultiFieldModel = new SocialMediaMultiFieldModel();
		PrivateAccessor.setField(socialMediaMultiFieldModel,"icon","socialMediaIcon");		
		PrivateAccessor.setField(socialMediaMultiFieldModel,"url","/content/imageUrl");	
		PrivateAccessor.setField(socialMediaMultiFieldModel,"alt","socialMediaAlt");	
		ArrayList<SocialMediaMultiFieldModel> mediaList = new ArrayList<>();
		mediaList.add(socialMediaMultiFieldModel);		
		PrivateAccessor.setField(socialMediaImpl,"socialMediaTitle","Social Media Title");
		PrivateAccessor.setField(socialMediaImpl,"socialMediaAsList",mediaList);
		PrivateAccessor.setField(socialMediaImpl,"socialMediaDetails",socialMediaDetails);
		
		
		lenient().when(resource.adaptTo(SocialMediaMultiFieldModel.class)).thenReturn(socialMediaMultiFieldModel);
		
	}
	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertNotNull(socialMediaImpl.getSocialMediaTitle());
		assertNotNull(socialMediaImpl.getSocialMediaAsList());
		assertNotNull(socialMediaImpl.getSocialMediaAsList().get(0));
		
	}
	
	/**
	 * Test fields.
	 */
	@Test
	void testFields() {
		lenient().when(externalizerService.getFormattedUrl("/content/imageUrl", resourceResolver)).thenReturn("/content/imageUrl");
		assertEquals("Social Media Title",socialMediaImpl.getSocialMediaTitle());
		assertEquals("socialMediaIcon",socialMediaImpl.getSocialMediaAsList().get(0).getIcon());
		assertEquals("socialMediaAlt",socialMediaImpl.getSocialMediaAsList().get(0).getAlt());
	}
	

    /**
     * Test init.
     */
    @Test
    void testInit(){
    	socialMediaImpl.init();
    }

	
}
