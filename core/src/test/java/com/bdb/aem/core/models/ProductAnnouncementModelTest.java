package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import com.bdb.aem.core.services.UpdateProductSchemaService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
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

import com.bdb.aem.core.bean.ProductAnnouncementBean;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class ProductAnnouncementModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class ProductAnnouncementModelTest {

	/** The value page resource type. */
	String VALUE_PAGE_RESOURCE_TYPE = "bdb/components/structure/page";

	/** The value template. */
	String VALUE_TEMPLATE = "/conf/bdb/settings/wcm/templates/content-page";

	/** The value page title. */
	String VALUE_PAGE_TITLE = "SAMPLE PAGE TITLE";

	/** The product announcement model. */
	@InjectMocks
	private ProductAnnouncementModel productAnnouncementModel;

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The product announcement bean. */
	@Mock
	ProductAnnouncementBean productAnnouncementBean;

	/** The current page. */
	@Mock
	Page currentPage;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/** The parent resource. */
	@Mock
	Resource parentResource;

	/** The varient value map. */
	@Mock
	ValueMap varientValueMap;

	/** The global varient value map. */
	@Mock
	ValueMap globalVarientValueMap;

	/** The object mapper. */
	@Mock
	ObjectMapper objectMapper;

	/** The session. */
	@Mock
	Session session;

	/** The marketing node. */
	@Mock
	Node marketingNode;

	/** The request path. */
	@Mock
	RequestPathInfo requestPath;

	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The query. */
	@Mock
	Query query;

	/** The region res. */
	@Mock
	Resource variantResource;

	/** The map. */
	@Mock
	Map<String, String> map;

	/** The query builder. */
	@Mock
	QueryBuilder queryBuilder;

	/** The result. */
	@Mock
	SearchResult result;

	/** The resources. */
	@Mock
	Iterator<Resource> resources;

	/** The Constant REPLACE_ID. */
	public static final String REPLACE_ID = "replacedProductId";

	@Mock
	ProductAnnouncementBean productAnnouncementObj;
	
	@Mock
	SolrSearchService solrSearchService;
	
	@Mock
	Resource resource;
	
	@Mock
	CommonHelper helper;
	
	@Mock
	RequestPathInfo requestPathInfo;
	
	@Mock
	ValueMap valueMap;
	
	@Mock
	Object obj;
	@Mock
	CatalogStructureUpdateService catalogStructureUpdateService;
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	@Mock
	UpdateProductSchemaService updateProductSchemaService;
	/**
	 * Setup.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setup() throws Exception {

		ProductAnnouncementBean productAnnouncementBean = new ProductAnnouncementBean();
		final ArrayList<ProductAnnouncementBean> productAnnouncementBeanList = new ArrayList<>();
		productAnnouncementBeanList.add(productAnnouncementBean);
		PrivateAccessor.setField(productAnnouncementModel, "productAnnouncement", "testProductAnnouncement");
		PrivateAccessor.setField(productAnnouncementModel, "paDescription", "testPaDescription");
		PrivateAccessor.setField(productAnnouncementModel, "paFAQTitle", "testPaFAQTitle");
		PrivateAccessor.setField(productAnnouncementModel, "regionalDisclaimers", "testRegionalDisclaimers");
		PrivateAccessor.setField(productAnnouncementModel, "paViewMoreCTA", "testPaViewMoreCTA");
		PrivateAccessor.setField(productAnnouncementModel, "openNewTab", "testOpenNewTab");
		PrivateAccessor.setField(productAnnouncementModel, "disclaimerStatus", "testDisclaimerStatus");
		PrivateAccessor.setField(productAnnouncementModel, "moreInfoLink", "testMoreInfoLink");
		PrivateAccessor.setField(productAnnouncementModel, "viewMoreFaqLabel", "testViewMoreFaqLabel");
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
	}

	/**
	 * Test init.
	 *
	 * @throws RepositoryException the repository exception
	 * @throws LoginException      the login exception
	 */
	@Test
	void testInit() throws RepositoryException, LoginException {
		String[] selectors = new String[] { "junit", "mock" };
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		lenient().when(requestPathInfo.getSelectors()).thenReturn(selectors);
		lenient().when(request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH)).thenReturn(obj);
		lenient().when(obj.toString()).thenReturn("/content/bdb");
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(resource);
		lenient().when(resource.getParent()).thenReturn(parentResource);
		lenient().when(parentResource.getChild("mock")).thenReturn(variantResource);
		lenient().when(variantResource.getChild(CommonConstants.HP_NODE)).thenReturn(resource);
		productAnnouncementModel.init(); 
		 
	}

	/**
	 * Test get varient resource.
	 *
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void testGetVarientResource() throws RepositoryException {

		Node marketingNode = Mockito.mock(Node.class);
		Resource marketingResource = Mockito.mock(Resource.class);
		Resource marketingChildResource = Mockito.mock(Resource.class);

		when(variantResource.getParent()).thenReturn(parentResource);
		when(parentResource.getChild(CommonConstants.MARKETING_NODE)).thenReturn(marketingResource);
		when(marketingResource.getChild("region")).thenReturn(marketingChildResource);
		when(marketingChildResource.getValueMap()).thenReturn(valueMap);
		when(valueMap.get(CommonConstants.PRODUCT_ANNOUNCEMENT)).thenReturn(null);
		when(marketingResource.getChild(CommonConstants.GLOBAL)).thenReturn(null).thenReturn(variantResource);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(marketingResource.adaptTo(Node.class)).thenReturn(marketingNode);
		when(variantResource.getValueMap()).thenReturn(varientValueMap);

		productAnnouncementModel.getVarientResource(resourceResolver, variantResource, "region");

	}
	@Test
	void testGetElseVarientResource() throws RepositoryException {

		Node marketingNode = Mockito.mock(Node.class);
		Resource marketingResource = Mockito.mock(Resource.class);
		Resource marketingChildResource = Mockito.mock(Resource.class);

		lenient().when(variantResource.getParent()).thenReturn(parentResource);
		lenient().when(parentResource.getChild(CommonConstants.MARKETING_NODE)).thenReturn(marketingResource);
		lenient().when(marketingResource.getChild("region")).thenReturn(marketingChildResource);
		lenient().when(marketingChildResource.getValueMap()).thenReturn(valueMap);
		lenient().when(valueMap.get(CommonConstants.PRODUCT_ANNOUNCEMENT)).thenReturn(true);
		lenient().when(marketingResource.getChild(CommonConstants.GLOBAL)).thenReturn(null).thenReturn(variantResource);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(marketingResource.adaptTo(Node.class)).thenReturn(marketingNode);
		lenient().when(variantResource.getValueMap()).thenReturn(varientValueMap);

		productAnnouncementModel.getVarientResource(resourceResolver, variantResource, "region");

	}
	@Test
	void testGetElseValueMapProperties() throws RepositoryException {
		String[] faqProperties = new String[] {"junit&mock"};
		lenient().when(varientValueMap.get("replacedProductId")).thenReturn(obj);
		lenient().when(varientValueMap.get("replacedProductId", String.class)).thenReturn("replacedProductId");
		lenient().when(varientValueMap.get(CommonConstants.FAQ_PROPERTY)).thenReturn(obj);
		lenient().when(varientValueMap.get(CommonConstants.FAQ_PROPERTY,String[].class)).thenReturn(faqProperties);
		productAnnouncementModel.getValueMapProperties(resourceResolver, varientValueMap, "country");
	}
	@Test
	void testGetValueMapProperties() throws RepositoryException {
		lenient().when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver, "replacedProductId", CommonConstants.MATERIAL_NUMBER)).thenReturn(parentResource);
		lenient().when(bdbApiEndpointService.getCustomRunMode()).thenReturn("author");
		lenient().when(varientValueMap.get(CommonConstants.MORE_INFO_LINK)).thenReturn(obj);
		lenient().when(varientValueMap.get(CommonConstants.MORE_INFO_LINK,String.class)).thenReturn(CommonConstants.LANGUAGE_PLACEHOLDER);
		productAnnouncementModel.getValueMapProperties(resourceResolver, varientValueMap, "country");
	}
	
	@Test
	void testGetFAQProperties() throws JsonProcessingException {
		String[] faqProperties = new String[] {"junit&mock"};
		JsonObject faqJson = new JsonObject();
		productAnnouncementModel.getFaqProperties(faqProperties, faqJson);
	}

	/**
	 * Test get product announcement.
	 */
	@Test
	void testGetProductAnnouncement() {
		assertNotNull(productAnnouncementModel.getProductAnnouncement());
	}

	/**
	 * Test get pa description.
	 */
	@Test
	void testGetPaDescription() {
		assertNotNull(productAnnouncementModel.getPaDescription());

	}

	/**
	 * Test get pa FAQ title.
	 */
	@Test
	void testGetPaFAQTitle() {
		assertNotNull(productAnnouncementModel.getPaFAQTitle());
	}

	/**
	 * Test get regional disclaimers.
	 */
	@Test
	void testGetRegionalDisclaimers() {
		assertNotNull(productAnnouncementModel.getRegionalDisclaimers()); 
	}

	/**
	 * Test get pa view more CTA.
	 */
	@Test
	void testGetPaViewMoreCTA() {
		assertNotNull(productAnnouncementModel.getPaViewMoreCTA()); 
	}

	/**
	 * Test get open new tab.
	 */
	@Test
	void testGetOpenNewTab() {
		assertNotNull(productAnnouncementModel.getOpenNewTab()); 
	}

	/**
	 * Test get disclaimer status.
	 */
	@Test
	void testGetDisclaimerStatus() {
		assertNotNull(productAnnouncementModel.getDisclaimerStatus()); 
	}

	/**
	 * Test get more info link.
	 */
	@Test
	void testGetMoreInfoLink() {
		assertNotNull(productAnnouncementModel.getMoreInfoLink());
	}

	/**
	 * Test get view more faq label.
	 */
	@Test
	void testGetViewMoreFaqLabel() {
		assertNotNull(productAnnouncementModel.getViewMoreFaqLabel());
	}

	/**
	 * Test get global map.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testGetGlobalMap() throws Exception {

		when(parentResource.getChild(CommonConstants.GLOBAL)).thenReturn(parentResource);
		productAnnouncementModel.getGlobalMap(parentResource, resourceResolver);

	}

	/**
	 * Test get null FAQ list.
	 */
	@Test
	public void testGetNullFAQList() {
		productAnnouncementModel.getFaqList();
	}

	/**
	 * Test get FAQ list.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	public void testGetFAQList() throws NoSuchFieldException {

		List<ProductAnnouncementBean> faqList = new ArrayList<>();
		PrivateAccessor.setField(productAnnouncementModel, "faqList", faqList);

		productAnnouncementModel.getFaqList();

	}
}
