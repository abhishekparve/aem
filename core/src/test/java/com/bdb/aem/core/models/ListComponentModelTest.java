package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import com.bdb.aem.core.bean.ListBlogsBean;
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

import com.bdb.aem.core.bean.ListEventsBean;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.components.ComponentContext;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class ListComponentModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class ListComponentModelTest {

	public static final String EVENTBLOG_DETAIL_COMPONENT_CONTENT_PATH = "/content/bdb/language-masters/en/learn/science-thought-leadership/events/isev-2021-international-society-of-extracellular-vesicles/jcr:content/root/eventblogdetails_cop";
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
    
    /** The query builder. */
    @Mock
    QueryBuilder queryBuilder;
    
    /** The query. */
    @Mock
    Query query;
	
	/** The list component model. */
	@InjectMocks
	ListComponentModel listComponentModel;
	
	/** The session. */
	@Mock
	Session session;
	
	/** The search result. */
	@Mock
	SearchResult result;
	
	/** The resources. */
	@Mock
	Iterator<Resource> rootresList;
	
	/** The hit. */
	@Mock
	Hit hit;
	
	/** The root res item. */
	@Mock
	Resource eventPage,eventResource , eventChild, rootRsrc, rootResItem;
	
	/** The value map. */
	@Mock
	ValueMap valueMap;
	
	/** The event blog model. */
	@Mock
	EventBlogModel eventBlogModel;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
        lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		
		ListResourcesModel listResourcesModel = new ListResourcesModel();
		PrivateAccessor.setField(listResourcesModel,"rowTitle","Resources Title");
		ArrayList<ListResourcesModel> resourcesList = new ArrayList<>();
		resourcesList.add(listResourcesModel);
		PrivateAccessor.setField(listComponentModel,"resourcesRows",resourcesList);
		
		ListPopularToolsModel listPopularToolsModel = new ListPopularToolsModel();
		PrivateAccessor.setField(listPopularToolsModel,"rowTitle","Popular Tools Title");
		ArrayList<ListPopularToolsModel> popularToolsList = new ArrayList<>();
		popularToolsList.add(listPopularToolsModel);
		PrivateAccessor.setField(listComponentModel,"popularToolsRows",popularToolsList);
		
		ListSupportModel listSupportModel = new ListSupportModel();
		PrivateAccessor.setField(listSupportModel,"listRowTitle","Support Title");
		ArrayList<ListSupportModel> supportList = new ArrayList<>();
		supportList.add(listSupportModel);		
		PrivateAccessor.setField(listComponentModel,"supportRows",supportList);
		
		ListFlowcytometryNewsModel listFlowcytometryNewsModel  = new ListFlowcytometryNewsModel();
		PrivateAccessor.setField(listFlowcytometryNewsModel ,"newsTitle","Flowcytometry Title");		
		ArrayList<ListFlowcytometryNewsModel> flowcytometryList = new ArrayList<>();
		flowcytometryList.add(listFlowcytometryNewsModel );		
		PrivateAccessor.setField(listComponentModel,"flowcytometryRows",flowcytometryList);
		
		ListEventsBean listEventsBean  = new ListEventsBean();
		PrivateAccessor.setField(listEventsBean ,"title","Event Title");		
		ArrayList<ListEventsBean> eventsList = new ArrayList<>();
		eventsList.add(listEventsBean );		
		PrivateAccessor.setField(listComponentModel,"listOfEvents",eventsList);
		
		PrivateAccessor.setField(listComponentModel, "eventParentPagePath", "eventParentPagePath");
		PrivateAccessor.setField(listComponentModel, "numberLimit", "1");
        PrivateAccessor.setField(listComponentModel, "listSelection", "Events");


		ListBlogsBean listBlogsBean = new ListBlogsBean();
		PrivateAccessor.setField(listBlogsBean,"title","Blog Title");
		ArrayList<ListBlogsBean> blogsList = new ArrayList<>();
		blogsList.add(listBlogsBean);
		PrivateAccessor.setField(listComponentModel, "listSelection", "Blogs");
		PrivateAccessor.setField(listComponentModel,"listOfBlogs",blogsList);
		PrivateAccessor.setField(listComponentModel, "blogParentPagePath", "blogParentPagePath");
		PrivateAccessor.setField(listComponentModel,"blogsnumberLimit","1");
	}
	
	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertNotNull(listComponentModel.getResourcesRows());
		assertNotNull(listComponentModel.getResourcesRows().get(0));
		assertNotNull(listComponentModel.getSupportRows());
		assertNotNull(listComponentModel.getSupportRows().get(0));
		assertNotNull(listComponentModel.getPopularToolsRows());
		assertNotNull(listComponentModel.getPopularToolsRows().get(0));
		assertNotNull(listComponentModel.getFlowcytometryRows());
		assertNotNull(listComponentModel.getFlowcytometryRows().get(0));
		
		assertNotNull(listComponentModel.getListOfEvents());
		assertNotNull(listComponentModel.getListOfEvents().get(0));
		
		assertNotNull(listComponentModel.getNumberLimit());
		assertNotNull(listComponentModel.getEventParentPagePath());
		
        lenient().when(externalizerService.getFormattedUrl("icon", resourceResolver)).thenReturn("/content/us/en/img.png");
        lenient().when(externalizerService.getFormattedUrl("viewAllURL", resourceResolver)).thenReturn("https://www.google.co.in");

		assertNull(listComponentModel.getTitle());
		assertNull(listComponentModel.getIcon());
		assertNull(listComponentModel.getHasWhiteBackground());
		assertNull(listComponentModel.getThoughtLeadershipVariation());
		assertNull(listComponentModel.getViewAllLabel());
		assertNull(listComponentModel.getViewAllURL());
		assertNull(listComponentModel.getIsPlacedBelow());
	}
	
	/**
	 * Test fields.
	 */
	@Test
	void testListsAndFields() {
		assertEquals("Resources Title",listComponentModel.getResourcesRows().get(0).getRowTitle());
		assertEquals("Support Title",listComponentModel.getSupportRows().get(0).getListRowTitle());
		assertEquals("Popular Tools Title",listComponentModel.getPopularToolsRows().get(0).getRowTitle());
		assertEquals("Flowcytometry Title",listComponentModel.getFlowcytometryRows().get(0).getNewsTitle());
		assertEquals("Event Title",listComponentModel.getListOfEvents().get(0).getTitle());
		assertEquals("1",listComponentModel.getNumberLimit());
		assertEquals("eventParentPagePath",listComponentModel.getEventParentPagePath());
	}
	
	
    /**
     * Test init.
     *
     * @throws LoginException the login exception
     * @throws RepositoryException the repository exception
     */
    @Test
    void testInit() throws LoginException, RepositoryException{
    	
    	Map<String, String> params = new HashMap<>();
    	params.put("path", "path");
    	params.put("p.limit", "1");
		params.put("property", "addToHomePage");
		params.put("property.value", "true");
    	
        when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);

		lenient().when(query.getResult()).thenReturn(result);
		lenient().when(result.getTotalMatches()).thenReturn(1L);
		
    	List<Hit> hitList = new ArrayList<>();
		hitList.add(hit);

		lenient().when(result.getHits()).thenReturn(hitList);
		lenient().when(hit.getPath()).thenReturn("hitPath");
		lenient().when(resourceResolver.getResource("hitPath")).thenReturn(eventPage);
		//when(eventPage.getValueMap()).thenReturn(valueMap);
		//when(valueMap.get(CommonConstants.JCR_PRIMARY_TYPE)).thenReturn("cq:Page");
		when(eventPage.getPath()).thenReturn(EVENTBLOG_DETAIL_COMPONENT_CONTENT_PATH);
		lenient().when(resourceResolver.getResource(eventPage.getPath().split(JcrConstants.JCR_CONTENT)[0])).thenReturn(eventResource);
		lenient().when(eventResource.getChild(JcrConstants.JCR_CONTENT)).thenReturn(eventChild);
		lenient().when(eventChild.getChild("root")).thenReturn(rootRsrc);

		lenient().when(rootRsrc.listChildren()).thenReturn(rootresList);
		lenient().when(rootresList.hasNext()).thenReturn(true).thenReturn(false);
		lenient().when(rootresList.next()).thenReturn(rootResItem);
		lenient().when(rootResItem.getResourceType()).thenReturn("bdb-aem/proxy/components/content/eventblogDetails");

		lenient().when(rootResItem.adaptTo(EventBlogModel.class)).thenReturn(eventBlogModel);
		lenient().when(eventBlogModel.getBannerTitle()).thenReturn("");
		lenient().when(eventBlogModel.getEventLocation()).thenReturn("");
		lenient().when(eventPage.getPath()).thenReturn("resourcesURL");

			
     lenient().when(eventBlogModel.getEventStartDate()).thenReturn("2020-12-14T13:26:36.480+05:30");

    	listComponentModel.init();
    }
    
    /**
     * Test event details exception.
     *
     * @throws LoginException the login exception
     * @throws RepositoryException 
     */
    @Test
	void testEventDetailsException() throws LoginException, RepositoryException {
    	
    	lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
    	when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
    	when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
    	when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);
    	when(query.getResult()).thenReturn(result);
    	when(result.getTotalMatches()).thenReturn(1L);
    	List<Hit> hitList = new ArrayList<>();
		hitList.add(hit);
		when(result.getHits()).thenReturn(hitList);
		lenient().when(hit.getPath()).thenThrow(RepositoryException.class);
		listComponentModel.getEventsDetails(resourceResolver,"Events", false );
	}
    
	/**
	 * Test init login exception.
	 *
	 * @throws LoginException the login exception
	 */
	@Test
	void testInitLoginException() throws LoginException {
		lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		listComponentModel.init();
	}
}
