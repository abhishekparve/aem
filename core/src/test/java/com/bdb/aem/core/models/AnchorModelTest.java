package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class AnchorModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class AnchorModelTest {

	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;;

	/** The context. */
	@Mock
	ComponentContext context;

	/** The anchor model. */
	@InjectMocks
	AnchorModel anchorModel;

	/** The current resource. */
	@Mock
	Resource currentResource;
	
	/** The current page. */
	@Mock
	Page currentPage;

	/** The resource. */
	@Mock
	Resource resource;

	/** The resource tag. */
	Resource resourceTag;

	/** The children. */
	@Mock
	Iterator<Resource> children;

	/** The aem context. */
	private AemContext aemContext;

	/** The tag manager. */
	@Mock
	TagManager tagManager;

	/** The tag. */
	@Mock
	Tag tag;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		Map<String, String> signUpProperties = new HashMap<>();
		signUpProperties.put("productCategoryTags", "pcTag");
		signUpProperties.put("searchResultPagePath", "searchPage");
		resourceTag = aemContext.create().resource("/root/aof/accountmanagement", signUpProperties);

		PrivateAccessor.setField(anchorModel, "categorySelect", "anchor");
		PrivateAccessor.setField(anchorModel, "anchorPlaceholder", "anchorPlaceholder");
		PrivateAccessor.setField(anchorModel, "searchPagePath", "searchPagePath");
		PrivateAccessor.setField(anchorModel, "searchPlaceholder", "searchPlaceholder");
		PrivateAccessor.setField(anchorModel, "pageTabVariarion", "false");
		PrivateAccessor.setField(anchorModel, "title", "title");
	}

	/**
	 * Test all getters.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testAllGetters() throws NoSuchFieldException {
		List<Map<String, String>> anchorList = new ArrayList<Map<String,String>>();
		PrivateAccessor.setField(anchorModel, "anchorList", anchorList);
		assertNotNull(anchorModel.getCategorySelect());
		assertNotNull(anchorModel.getAnchorList());
		assertNotNull(anchorModel.getAnchorPlaceholder());
		assertNotNull(anchorModel.getSearchPlaceholder());
		assertNotNull(anchorModel.getSearchPagePath());
		assertNotNull(anchorModel.getTitle());
	}

	
	/**
	 * Test init.
	 *
	 * @throws LoginException the login exception
	 * @throws RepositoryException the repository exception
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testInit() throws LoginException, RepositoryException, NoSuchFieldException {
		Map<String, Object> writeServiceAuth = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE,
				"readService");
		Node firstNode = Mockito.mock(Node.class);
		Resource resource1 = Mockito.mock(Resource.class);
		Property sectionTitle = Mockito.mock(Property.class);
		ValueMap valueMap=Mockito.mock(ValueMap.class);

		when(resource1.getValueMap()).thenReturn(valueMap);
		when(valueMap.get("enableAnchorIdentifier")).thenReturn("true");
		when(resolverFactory.getServiceResourceResolver(writeServiceAuth)).thenReturn(resourceResolver);
		when(currentPage.getPath()).thenReturn("content/page/sample");
		when(resourceResolver.getResource("content/page/sample".concat(CommonConstants.JCR_ROOT))).thenReturn(resource);
		when(resource.listChildren()).thenReturn(children);
		when(children.hasNext()).thenReturn(true).thenReturn(false);
		when(children.next()).thenReturn(resource1);
		when(resource1.adaptTo(Node.class)).thenReturn(firstNode);
		when(firstNode.hasProperty(CommonConstants.SECTION_TITLE)).thenReturn(true);
		when(firstNode.getProperty(CommonConstants.SECTION_TITLE)).thenReturn(sectionTitle);
		when(sectionTitle.getString()).thenReturn("key");
		when(currentResource.getParent()).thenReturn(resource1);
		when(resource1.getPath()).thenReturn("/page/item0");
		when(resource1.getName()).thenReturn("anchorResourceLink");
		when(resource1.getParent()).thenReturn(resource1);

		when(currentPage.getContentResource()).thenReturn(resourceTag);
		when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		when(tagManager.resolve("pcTag")).thenReturn(tag);
		when(tag.getTitle()).thenReturn("pcTag");
		when(externalizerService.getFormattedUrl("searchPage", resourceResolver)).thenReturn("something");
		anchorModel.init();
	}
	
	/**
	 * Test else if condition.
	 *
	 * @throws LoginException the login exception
	 * @throws RepositoryException the repository exception
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testElseIfCondition() throws LoginException, RepositoryException, NoSuchFieldException {
		Map<String, Object> writeServiceAuth = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE,
				"readService");
		Node firstNode = Mockito.mock(Node.class);
		Resource resource1 = Mockito.mock(Resource.class);
		Property title = Mockito.mock(Property.class);
		ValueMap valueMap=Mockito.mock(ValueMap.class);

		when(resource1.getValueMap()).thenReturn(valueMap);
		when(valueMap.get("enableAnchorIdentifier")).thenReturn("true");
		when(resolverFactory.getServiceResourceResolver(writeServiceAuth)).thenReturn(resourceResolver);
		
		when(currentPage.getPath()).thenReturn("content/page/sample");
		when(resourceResolver.getResource("content/page/sample".concat(CommonConstants.JCR_ROOT))).thenReturn(resource);
		when(resource.listChildren()).thenReturn(children);
		when(children.hasNext()).thenReturn(true).thenReturn(false);
		when(children.next()).thenReturn(resource1);
		when(resource1.adaptTo(Node.class)).thenReturn(firstNode);
		when(firstNode.hasProperty(CommonConstants.SECTION_TITLE)).thenReturn(false);
		when(firstNode.hasProperty(CommonConstants.TITLE_LABEL)).thenReturn(true);
		when(firstNode.getProperty(CommonConstants.TITLE_LABEL)).thenReturn(title);
		when(title.getString()).thenReturn(CommonConstants.SECTION_TITLE);
		when(currentResource.getParent()).thenReturn(resource1);
		when(resource1.getPath()).thenReturn("/page/item0");
		when(resource1.getName()).thenReturn("anchorResourceLink");
		when(resource1.getParent()).thenReturn(resource1);

		when(currentPage.getContentResource()).thenReturn(resourceTag);
		when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		when(tagManager.resolve("pcTag")).thenReturn(tag);
		when(tag.getTitle()).thenReturn("pcTag");
		when(externalizerService.getFormattedUrl("searchPage", resourceResolver)).thenReturn("something");
		anchorModel.init();
	}
	
	
	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenThrow(LoginException.class);
		anchorModel.init();
	}
}