package com.bdb.aem.core.bean;

import static org.junit.jupiter.api.Assertions.*;
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

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
/**
* Class ContactUsSupportCategoriesBeanTest
* @author phanindra
*/

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ContactUsSupportCategoriesBeanTest {

	/**Class ContactUsSupportCategoriesBean*/
	@InjectMocks
	ContactUsSupportCategoriesBean contactUsSupportCategoriesBean;
	
	/**Resource of multi field*/
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
	
	/**fieldMultifield mock value for multifield*/
	List<Resource> fieldMultifield= new ArrayList<Resource>();
	@BeforeEach
	void init() throws LoginException, NoSuchFieldException
	{
		
		fieldMultifield.add(resource);
		
		ContactUsSupportBean cusBean = new ContactUsSupportBean();
		List<ContactUsSupportBean> fields = new ArrayList<>();
		PrivateAccessor.setField(contactUsSupportCategoriesBean, "categoryTitle", "Category Title");
		PrivateAccessor.setField(cusBean, "titleText", "Title Text");
		PrivateAccessor.setField(cusBean, "subText", "sub Text");
		PrivateAccessor.setField(cusBean, "address", "Address");
		PrivateAccessor.setField(cusBean, "description", "Description");
		PrivateAccessor.setField(cusBean, "emailLabel", "Email Label");
		PrivateAccessor.setField(cusBean, "emailUrl", "/content/email");
		PrivateAccessor.setField(cusBean, "courseLabel", "Course Label");
		PrivateAccessor.setField(cusBean, "courseUrl", "/content/course");
		fields.add(cusBean);
		lenient().when(resource.adaptTo(ContactUsSupportBean.class)).thenReturn(cusBean);
		PrivateAccessor.setField(contactUsSupportCategoriesBean, "fields", fields);
		PrivateAccessor.setField(contactUsSupportCategoriesBean, "fieldMultifield", fieldMultifield);
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		
	}
	/**
     * Test Not Null for variables.
	 * testGetters()
     */
	@Test
	void testGetters() {
		assertNotNull(contactUsSupportCategoriesBean.getCategoryTitle());
		assertNotNull(contactUsSupportCategoriesBean.getFields().get(0));
	}
	/**
     * Test variables with mock values.
	 * testFields()
     */
	@Test
	void testFields() {
		lenient().when(externalizerService.getFormattedUrl("/content/email", resourceResolver)).thenReturn("/content/email");
		lenient().when(externalizerService.getFormattedUrl("/content/course", resourceResolver)).thenReturn("/content/course");
		assertEquals("Category Title",contactUsSupportCategoriesBean.getCategoryTitle());
		assertEquals("Title Text",contactUsSupportCategoriesBean.getFields().get(0).getTitleText());
		assertEquals("sub Text",contactUsSupportCategoriesBean.getFields().get(0).getSubText());
		assertEquals("Address",contactUsSupportCategoriesBean.getFields().get(0).getAddress());
		assertEquals("Description",contactUsSupportCategoriesBean.getFields().get(0).getDescription());
		assertEquals("Email Label",contactUsSupportCategoriesBean.getFields().get(0).getEmailLabel());
		assertEquals("Course Label",contactUsSupportCategoriesBean.getFields().get(0).getCourseLabel());
	}
	/**
     * Test init.
	 * @throws LoginException 
     */
    @Test
    void testInit() throws LoginException{
    	contactUsSupportCategoriesBean.init();
    }

}
