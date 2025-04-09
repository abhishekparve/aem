package com.bdb.aem.core.models.impl;

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

import com.bdb.aem.core.bean.ContactUsSupportBean;
import com.bdb.aem.core.bean.ContactUsSupportCategoriesBean;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
* Class ContactUsSupportModelImplTest
* @author phanindra
*/
@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class ContactUsSupportModelImplTest {

	/** Resource Resolver */
	@Mock
	ResourceResolver resourceResolver;
	
	@InjectMocks
	ContactUsSupportModelImpl contactUsSupportModelImpl;
	
	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;
	
	/** The context. */
    @Mock
    ComponentContext context;
    
    /** The Resource. */
	@Mock
	Resource resource;
	
	/**List holds the multi-field mock data*/
	List<Resource> fieldMultifield;
	
	List<Resource> supportCategories = new ArrayList<Resource>();
	
	@BeforeEach
	void setUp() throws NoSuchFieldException, LoginException
	{
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		supportCategories.add(resource);
		ContactUsSupportCategoriesBean contactUsCatBean = new ContactUsSupportCategoriesBean();
		ContactUsSupportBean cusBean = new ContactUsSupportBean();
		PrivateAccessor.setField(cusBean, "titleText", "Title Text");
		PrivateAccessor.setField(cusBean, "subText", "sub Text");
		PrivateAccessor.setField(cusBean, "address", "Address");
		PrivateAccessor.setField(cusBean, "description", "Description");
		PrivateAccessor.setField(cusBean, "emailLabel", "Email Label");
		PrivateAccessor.setField(cusBean, "emailUrl", "/content/email");
		PrivateAccessor.setField(cusBean, "courseLabel", "Course Label");
		PrivateAccessor.setField(cusBean, "courseUrl", "/content/course");
		ArrayList<ContactUsSupportBean> contactBean = new ArrayList<>();
		contactBean.add(cusBean);	
		PrivateAccessor.setField(contactUsCatBean, "categoryTitle", "Category Title");
		PrivateAccessor.setField(contactUsCatBean, "fields", contactBean);
		PrivateAccessor.setField(contactUsCatBean, "fieldMultifield", fieldMultifield);
		lenient().when(resource.adaptTo(ContactUsSupportBean.class)).thenReturn(cusBean);
		ArrayList<ContactUsSupportCategoriesBean> contactCatBean = new ArrayList<>();
		contactCatBean.add(contactUsCatBean);
		PrivateAccessor.setField(contactUsSupportModelImpl, "secTitle", "Sec Title");
		PrivateAccessor.setField(contactUsSupportModelImpl, "subTitle", "Sub Title");
		PrivateAccessor.setField(contactUsSupportModelImpl, "secDesc", "Sec Description");
		PrivateAccessor.setField(contactUsSupportModelImpl, "supportCategories", supportCategories);
		PrivateAccessor.setField(contactUsSupportModelImpl, "supportFields", contactCatBean);
		lenient().when(resource.adaptTo(ContactUsSupportCategoriesBean.class)).thenReturn(contactUsCatBean);
		
	}
	 /**
     * Test Not Null for the Variables.
	 *  testGetters()
     */
	@Test
	void testGetters() {
		assertNotNull(contactUsSupportModelImpl.getSecTitle());
		assertNotNull(contactUsSupportModelImpl.getSubTitle());
		assertNotNull(contactUsSupportModelImpl.getSecDesc());
		assertNotNull(contactUsSupportModelImpl.getSupportFields().get(0));
	}
	/**
     * Test Variables with the mock data.
	 *  testFields()
     */
	@Test
	void testFields() {
		lenient().when(externalizerService.getFormattedUrl("/content/email", resourceResolver)).thenReturn("/content/email");
		lenient().when(externalizerService.getFormattedUrl("/content/course", resourceResolver)).thenReturn("/content/course");
		assertEquals("Sec Title",contactUsSupportModelImpl.getSecTitle());
		assertEquals("Sub Title",contactUsSupportModelImpl.getSubTitle());
		assertEquals("Sec Description",contactUsSupportModelImpl.getSecDesc());
		assertEquals("Category Title",contactUsSupportModelImpl.getSupportFields().get(0).getCategoryTitle());
		assertEquals("Title Text",contactUsSupportModelImpl.getSupportFields().get(0).getFields().get(0).getTitleText());
		assertEquals("sub Text",contactUsSupportModelImpl.getSupportFields().get(0).getFields().get(0).getSubText());
		assertEquals("Address",contactUsSupportModelImpl.getSupportFields().get(0).getFields().get(0).getAddress());
		assertEquals("Description",contactUsSupportModelImpl.getSupportFields().get(0).getFields().get(0).getDescription());
		assertEquals("Email Label",contactUsSupportModelImpl.getSupportFields().get(0).getFields().get(0).getEmailLabel());
		assertEquals("Course Label",contactUsSupportModelImpl.getSupportFields().get(0).getFields().get(0).getCourseLabel());
	}
	
    /**
     * Test init.
	 * @throws LoginException 
     */
    @Test
    void testInit() throws LoginException{
    	contactUsSupportModelImpl.init();
    }
}
