package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;

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
 * The Class BlogDetailsModelTest.
 */

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class BlogDetailsModelTest {

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
    
	/** The blog details model. */
	@InjectMocks
	private BlogDetailsModel blogDetailsModel;
	
	
	/**
	 * Sets the up.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {
		
		PrivateAccessor.setField(blogDetailsModel, "bannerImageBlog", "bannerImageBlog");
		PrivateAccessor.setField(blogDetailsModel, "bannerThumbnailImageBlog", "bannerThumbnailImageBlog");
		PrivateAccessor.setField(blogDetailsModel,"blogDate","2020-10-21T10:00:00.000+05:30");
		
		PrivateAccessor.setField(blogDetailsModel, "blogUrlCta", "/some/url");
		PrivateAccessor.setField(blogDetailsModel, "blogUrlCtaAdd", "/some/url");
		
		lenient().when(externalizerService.getFormattedUrl("/some/url", resourceResolver)).thenReturn("http://localhost:4502/some/url");
	}
	
	/**
	 * Test all getters.
	 */
	@Test
	void testAllGetters() {

		lenient().when(externalizerService.getFormattedUrl("bannerImageBlog", resourceResolver)).thenReturn("/content/dam/image-mob.png");
    	lenient().when(externalizerService.getFormattedUrl("bannerThumbnailImageBlog", resourceResolver)).thenReturn("/content/dam/image-mob.png");
		
    	assertNotNull(blogDetailsModel.getBannerImageBlog());
    	assertNotNull(blogDetailsModel.getBannerThumbnailImageBlog());
		assertNotNull(blogDetailsModel.getBlogDate());
		assertNotNull(blogDetailsModel.getBlogUrlCta());
		assertNotNull(blogDetailsModel.getBlogUrlCtaAdd());
	}
	
	
	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 */
    @Test
    void testInit() throws LoginException{
        lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
        blogDetailsModel.init();
    }
    
	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		blogDetailsModel.init();
	}
}
