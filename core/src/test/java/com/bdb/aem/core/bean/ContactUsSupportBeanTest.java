package com.bdb.aem.core.bean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

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

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
* Class ContactUsSupportBeanTest
* @author phanindra
*/
@ExtendWith({AemContextExtension.class, MockitoExtension.class })
class ContactUsSupportBeanTest {

	/**Class ContactUsSupportBean*/
	@InjectMocks
	ContactUsSupportBean contactUsSupportBean;
	
	/**Resource of multi-field*/
	@Mock
	Resource resource;
	
	/**Mocking ExternalizerService*/
	@Mock
	ExternalizerService externalizerService;
	
	/**Mocking Resource Resolver Factory*/
	@Mock
	ResourceResolverFactory resolverFactory;
	
	/**Mocking Resource Resolver*/
	@Mock
	ResourceResolver resourceResolver;
	
	/**AEM Context*/
	@Mock
	ComponentContext context;
	
	@BeforeEach
	void init() throws LoginException, NoSuchFieldException
	{
		
		PrivateAccessor.setField(contactUsSupportBean, "titleText", "Title Text");
		PrivateAccessor.setField(contactUsSupportBean, "subText", "sub Text");
		PrivateAccessor.setField(contactUsSupportBean, "address", "Address");
		PrivateAccessor.setField(contactUsSupportBean, "description", "Description");
		PrivateAccessor.setField(contactUsSupportBean, "emailLabel", "Email Label");
		PrivateAccessor.setField(contactUsSupportBean, "emailUrl", "/content/email");
		PrivateAccessor.setField(contactUsSupportBean, "courseLabel", "Course Label");
		PrivateAccessor.setField(contactUsSupportBean, "courseUrl", "/content/course");
		
	}
	/**
     * Test Is Null for all the variables.
	 * testGetters() 
     */
	@Test
	void testGetters() {
		lenient().when(externalizerService.getFormattedUrl("/content/email", resourceResolver)).thenReturn("/content/email");
		lenient().when(externalizerService.getFormattedUrl("/content/course", resourceResolver)).thenReturn("/content/course");
		assertNotNull(contactUsSupportBean.getTitleText());
		assertNotNull(contactUsSupportBean.getSubText());
		assertNotNull(contactUsSupportBean.getAddress());
		assertNotNull(contactUsSupportBean.getDescription());
		assertNotNull(contactUsSupportBean.getEmailLabel());
		assertNotNull(contactUsSupportBean.getEmailUrl());
		assertNotNull(contactUsSupportBean.getCourseLabel());
		assertNotNull(contactUsSupportBean.getCourseUrl());
	}
	/**
     * Test with given mock values.
	 * testFields()
     */
	@Test
	void testFields() {
		lenient().when(externalizerService.getFormattedUrl("/content/email", resourceResolver)).thenReturn("/content/email");
		lenient().when(externalizerService.getFormattedUrl("/content/course", resourceResolver)).thenReturn("/content/course");
		assertEquals("Title Text",contactUsSupportBean.getTitleText());
		assertEquals("sub Text",contactUsSupportBean.getSubText());
		assertEquals("Address",contactUsSupportBean.getAddress());
		assertEquals("Description",contactUsSupportBean.getDescription());
		assertEquals("Email Label",contactUsSupportBean.getEmailLabel());
		assertEquals("Course Label",contactUsSupportBean.getCourseLabel());
		assertEquals("/content/email",contactUsSupportBean.getEmailUrl());
		assertEquals("/content/course",contactUsSupportBean.getCourseUrl());
	}
	/**
     * Test init.
	 * @throws LoginException 
     */
    @Test
    void testInit() throws LoginException{
    	contactUsSupportBean.init();
    }
}
