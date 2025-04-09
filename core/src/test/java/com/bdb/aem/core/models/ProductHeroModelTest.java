package com.bdb.aem.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.lenient;

import java.text.ParseException;
import java.util.Iterator;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class HeroModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ProductHeroModelTest {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductHeroModelTest.class);

	/** The aem context. */
	AemContext aemContext = new AemContext();

	/** The hero model. */
	@InjectMocks
	ProductHeroModel productHeroModel;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The request path. */
	@Mock
	RequestPathInfo requestPath;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** Sling setting service . */
	@Mock
	SlingSettingsService slingSettingsService;

	/** The query builder. */
	@Mock
	QueryBuilder queryBuilder;

	/** The predicates. */
	@Mock
	PredicateGroup predicates;

	/** The session. */
	@Mock
	Session session;

	/** The query. */
	@Mock
	Query query;

	/** The search result. */
	@Mock
	SearchResult searchResult;

	/** The resources. */
	@Mock
	Iterator<Resource> resources;

	/** The next resource. */
	@Mock
	Resource nextResource;

	/** The hp node resource. */
	@Mock
	Resource hpNodeResource, hpNodeResource1, hpNodeResource2, toolsResource, varientResource,parentRes,childRes,resource;

	/** The varient value map. */
	@Mock
	ValueMap varientValueMap, toolsMap;

	/** The hero obj. */
	@Mock
	Object heroObj, CompanionObject, toolsObj;

	/** The current page. */
	@Mock
	Page currentPage;

	/** Mock ResourceResolverFactory. */
	@Mock
	ExternalizerService externalizerService;

	/** The solr search service. */
	@Mock
	SolrSearchService solrSearchService;
	@Mock
	Asset asset;
	/** The Companion json. */
	String CompanionJson = "{\r\n" + "      \"productsList\": [\r\n" + "      {\r\n"
			+ "        \"image\": \"companion-1.jpg\",\r\n" + "        \"imgAtlText\": \"alt text\",\r\n"
			+ "        \"ctaTarget\": \"_new\",\r\n"
			+ "        \"productTitle\": \"PE Rat Anti-Mouse CD4 RM4-5 (also known as RM4.5) RUO\",\r\n"
			+ "        \"size\": \"0.2 mg\",\r\n" + "        \"catNo\": \"553049\"\r\n" + "      }\r\n" + "    ]\r\n"
			+ "  }";

	String conjugateData = "[{\"tdsCloneName\":\"145-2C11\",\"dyeName\":\"PerCP\"}]";

	String jsonString = "{\"DYENAME\":\"http://localhost:4502/content/dam/bdb/products/global/reagents/immunoassay-reagents/cba/cba-kits/561509_base/PerCP.png\",\"GALLERY\":[{\"imagePath\":\"http://localhost:4502/content/dam/bdb/products/global/reagents/immunoassay-reagents/cba/cba-kits/561509_base/561509_image1.png\",\"imageTitle\":\"BD™ Cytometric Bead Array (CBA) Human IL-1β Enhanced Sensitivity Flex Set | ImageKeywords\",\"caption\":\"\\\"Figure 1. Example BD CBA Human IL-1â Enhanced Sensitivity Flex Set standard curve. Data acquired on a BD FACSArray bioanalyzer and analyzed using FCAP Array Software.\\\"\",\"imageAltText\":\"Human IL-1β Enhanced Sensitivity Flex Set\"}]}";

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(productHeroModel, "formatsLabel", "viewAllformatsLabel");
		PrivateAccessor.setField(productHeroModel, "cloneLabel", "clone");
		PrivateAccessor.setField(productHeroModel, "showLessCta", "showLess");
		PrivateAccessor.setField(productHeroModel, "showMoreCta", "showMore");
		PrivateAccessor.setField(productHeroModel, "linesCount", "linesCount");
		PrivateAccessor.setField(productHeroModel, "productTitle", "productTitle");
		PrivateAccessor.setField(productHeroModel, "productClone", "productClone");

		lenient().when(request.getAttribute("productVarHPPath"))
				.thenReturn("var/commerce/products/bdb/product/variant/hp");
		lenient().when(resourceResolver.getResource("var/commerce/products/bdb/product/variant/hp"))
				.thenReturn(varientResource);
		lenient().when(varientResource.getParent()).thenReturn(parentRes);
		lenient().when(parentRes.getChild("340336")).thenReturn(childRes);
		lenient().when(varientResource.getValueMap()).thenReturn(varientValueMap);
		PrivateAccessor.setField(productHeroModel, "request", request);
		PrivateAccessor.setField(productHeroModel, "dyeNameExcelPath", "content/bdb/abc.xlsx");
		PrivateAccessor.setField(productHeroModel, "displaySpectrumViewerTool", false);
		final String[] input2 = new String[] { "myString1", "340336" };
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPath);
		lenient().when(requestPath.getSelectors()).thenReturn(input2);
		lenient().when(request.getAttribute("catalogNumber")).thenReturn("340336");
	}

	/**
	 * Test all getters.
	 */
	@Test
	void testAllGetters() {
		assertNotNull(productHeroModel.getProductTitle());
		assertNotNull(productHeroModel.getProductClone());
		assertNotNull(productHeroModel.getCloneLabel());
		assertNotNull(productHeroModel.getShowLessCta());
		assertNotNull(productHeroModel.getShowMoreCta());
		assertNotNull(productHeroModel.getFormatsLabel());

	}

	/**
	 * Test get format detail json.
	 */
	@Test
	void testGetFormatDetailJson() {
		String jsonString = "{\r\n" + "	\"dyeName\": \"bicton\"\r\n" + "}";
		JsonElement formatDetailsElement = new JsonParser().parse(jsonString).getAsJsonObject();
		productHeroModel.getFormatDetailJson(formatDetailsElement);
		productHeroModel.getLinesCount();
		productHeroModel.getBrand();
		productHeroModel.getAbSeqBrand();
		productHeroModel.getRegulatoryStatus();
		productHeroModel.getIcon1();
		//productHeroModel.getDisplaySpectrumViewerTool();
		//productHeroModel.getBeanList();
	}

	/**
	 * Test get vairient resource.
	 *
	 * @throws LoginException the login exception
	 * @throws RepositoryException 
	 * @throws ParseException 
	 */
//	@Test
	void testGetVairientResource() throws LoginException, ParseException, RepositoryException {

		JsonParser parser = new JsonParser();
		JsonObject assetJson = (JsonObject) parser.parse(jsonString);
		lenient().when(varientValueMap.get("labelDescription", String.class)).thenReturn("productTitle");
		lenient().when(varientValueMap.get("conjugate", String.class)).thenReturn(conjugateData);
		lenient().when(request.getAttribute("assetPaths")).thenReturn(assetJson);
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(toolsResource);
		lenient().when(toolsResource.getValueMap()).thenReturn(toolsMap);
		lenient().when(toolsMap.containsKey(CommonConstants.PDP_TEMPLATE)).thenReturn(true);
		lenient().when(toolsMap.get(CommonConstants.PDP_TEMPLATE, String.class)).thenReturn("templateId");
		lenient().when(toolsResource.getParent()).thenReturn(toolsResource);
		lenient().when(toolsResource.getChild(Mockito.anyString())).thenReturn(hpNodeResource);
		lenient().when(hpNodeResource.getChild(Mockito.anyString())).thenReturn(hpNodeResource);
		lenient().when(hpNodeResource.adaptTo(ValueMap.class)).thenReturn(varientValueMap);
		lenient().when(toolsResource.adaptTo(ValueMap.class)).thenReturn(varientValueMap);
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
//		lenient().when(resource.adaptTo(ValueMap.class)).thenReturn(asset);
		lenient().when(resource.adaptTo(Asset.class)).thenReturn(asset);
		lenient().when(asset.getMetadataValueFromJcr(Mockito.anyString())).thenReturn("1");

		// lenient().when(toolsResource.adaptTo(Asset.class)).thenReturn(asset);
		// lenient().when(asset.getMimeType()).thenReturn("application/vnd.ms-excel");
		productHeroModel.init();
	}

	/**
	 * Test companion products.
	 * @throws RepositoryException 
	 * @throws ParseException 
	 */

	@Test
	void testCompanionProducts() throws ParseException, RepositoryException { // getVaientMap();
		lenient().when(varientValueMap.get("companionProducts")).thenReturn(CompanionObject);
		assertNotNull(CompanionObject);
		lenient().when(resourceResolver.getResource("/content/dam")).thenReturn(hpNodeResource);
		//lenient().when(hpNodeResource.getParent().getChild("hp")).thenReturn(varientResource2);
		lenient().when(hpNodeResource.getParent()).thenReturn(hpNodeResource);
		lenient().when(hpNodeResource.getParent().getParent()).thenReturn(hpNodeResource);
		lenient().when(hpNodeResource.getParent().getParent().getPath()).thenReturn("/content/dam");
		lenient().when(CommonHelper.getExternalizedImage("/var/commerce", resourceResolver, "companion-1.jpg",
				externalizerService)).thenReturn("/content/dam/companion-1.jpg");
		productHeroModel.init();
	}

	/**
	 * Test get technical tools.
	 * 
	 * 
	 * @Test void testGetTechnicalTools() {
	 *       lenient().when(currentPage.getPath()).thenReturn("/content");
	 *       //lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(toolsResource);
	 *       lenient().when(resourceResolver.getResource("/content/jcr:content/commercebox/technicalTools"))
	 *       .thenReturn(toolsResource);
	 *       lenient().when(toolsResource.getValueMap()).thenReturn(toolsMap);
	 *       String icon1 = "icon1"; assertNotNull(toolsMap);
	 *       lenient().when(toolsMap.get("icon1")).thenReturn(toolsObj);
	 *       lenient().when(toolsMap.get("icon1",
	 *       String.class)).thenReturn("icon1"); productHeroModel.init(); }
	 * 
	 *       /** Test get icon 1.
	 * 
	 *       /*
	 * 
	 * @Test public void testGetIcon1() { assertNull(productHeroModel.getIcon1()); }
	 * 
	 */
	/**
	 * Test get icon 2.
	 */
	// frm here uncomment

	@Test
	public void testGetIcon2() {
		assertNull(productHeroModel.getIcon2());
	}

	/**
	 * Test get icon 3.
	 */

	@Test
	public void testGetIcon3() {
		assertNull(productHeroModel.getIcon3());
	}

	/**
	 * Test get link 1.
	 */

	@Test
	public void testGetLink1() {
		assertNull(productHeroModel.getLink1());
	}

	/**
	 * Test get link 2.
	 */

	@Test
	public void testGetLink2() {
		assertNull(productHeroModel.getLink2());
	}

	/**
	 * Test get link 3.
	 */

	@Test
	public void testGetLink3() {
		assertNull(productHeroModel.getLink3());
	}

	/**
	 * Test get text 1.
	 */

	@Test
	public void testGetText1() {
		assertNull(productHeroModel.getText1());
	}

	/**
	 * Test get text 2.
	 */

	@Test
	public void testGetText2() {
		assertNull(productHeroModel.getText2());
	}

	/**
	 * Test get text 3.
	 */
	@Test
	public void testGetText3() {
		assertNull(productHeroModel.getText3());
	}

}
