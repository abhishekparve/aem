package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.bean.CompanionProductsBean;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * CompanionProductsModelTest
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class CompanionProductsModelTest {

	/** The aem context. */
	AemContext aemContext = new AemContext();

	/** The CompanionProductsModel. */
	@InjectMocks
	CompanionProductsModel companionProductsModel;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The request path. */
	@Mock
	RequestPathInfo requestPath;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** Resourceresolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** Mock ResourceResolverFactory. */
	@Mock
	ExternalizerService externalizerService;

	/** The query builder. */
	@Mock
	QueryBuilder queryBuilder;

	/** The predicates. */
	@Mock
	PredicateGroup predicates;

	/** The session. */
	@Mock
	Session session;

	/** The varientResource. */
	@Mock
	Resource varientResource;

	/** The varientValueMap. */
	@Mock
	ValueMap varientValueMap;

	/** The Resource. */
	@Mock
	Resource hpResource;

	/** The Query. */
	@Mock
	Query query;

	/** The SearchResult. */
	@Mock
	SearchResult result;

	/** The Iterator<Resource>. */
	@Mock
	Iterator<Resource> resources;

	/** The nextResource. */
	@Mock
	Resource nextResource;

	/** The varientResourceParent. */
	@Mock
	Resource baseHpResource, varientResourceParent, parent, paResource, variantRes;

	/** The varientResourceParentParent. */
	@Mock
	Resource varientResourceParentParent, baseProductDamResource;

	/** The ValueMap. */
	@Mock
	ValueMap valueMap, hpValueMap;

	@Mock
	CommonHelper helper;

	/** The Companion json. */
	String CompanionJson = "{\r\n" + "      \"productsList\": [\r\n" + "      {\r\n"
			+ "        \"image\": \"companion-1.jpg\",\r\n" + "        \"imgAtlText\": \"alt text\",\r\n"
			+ "        \"ctaTarget\": \"_new\",\r\n"
			+ "        \"productTitle\": \"PE Rat Anti-Mouse CD4 RM4-5 (also known as RM4.5) RUO\",\r\n"
			+ "        \"size\": \"0.2 mg\",\r\n" + "        \"catNo\": \"553049\"\r\n" + "      }\r\n" + "    ]\r\n"
			+ "  }";

	@Mock
	SolrSearchService solrSearchService;

	@Mock
	JsonElement element;

	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	@Mock
	CompanionProductsBean companionProductsBean;

	@Mock
	SlingSettingsService slingSettingsService;
	@Mock
	CatalogStructureUpdateService catalogStructureUpdateService;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {

		PrivateAccessor.setField(companionProductsModel, "productsLabel", "productsLabel");
		PrivateAccessor.setField(companionProductsModel, "sizeLabel", "sizeLabel");
		PrivateAccessor.setField(companionProductsModel, "catNoLabel", "catNoLabel");
		PrivateAccessor.setField(companionProductsModel, "viewDetailsLabel", "viewDetailsLabel");
		PrivateAccessor.setField(companionProductsModel, "viewMoreCTALink", "viewMoreCTALink");
		final String[] input2 = new String[] { "myString1", "340336" };
		lenient().when(request.getAttribute("productVarHPPath"))
				.thenReturn("var/commerce/products/bdb/product/variant/hp");
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPath);
		lenient().when(requestPath.getSelectors()).thenReturn(input2);
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		// when(request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH)).thenReturn("hpPath");
		// when(resourceResolver.getResource("hpPath")).thenReturn(baseHpResource);

	}

	/**
	 * Test all getters.
	 */
	@Test
	void testAllGetters() {

		assertNotNull(companionProductsModel.getCatNoLabel());
		assertNotNull(companionProductsModel.getViewDetailsLabel());
		assertNotNull(companionProductsModel.getSizeLabel());
		assertNotNull(companionProductsModel.getProductsLabel());
		assertNotNull(companionProductsModel.getNumberOfProducts());
		assertNotNull(companionProductsModel.getViewMoreCTALink());

	}

	/**
	 * Test init.
	 * 
	 * @throws LoginException
	 */
	@Test
	void testInit() throws LoginException {

		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(resourceResolver.getResource("var/commerce/products/bdb/product/variant/hp"))
				.thenReturn(varientResource);
		lenient().when(varientResource.getValueMap()).thenReturn(varientValueMap);
		companionProductsModel.init();

	}

	/**
	 * Test init login exception.
	 *
	 * @throws LoginException
	 */
	@Test
	void testInitLoginException() throws LoginException {

		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		companionProductsModel.init();

	}

	/**
	 * Test getVarientHPDetails().
	 */
	// @Test
	void testGetVarientHPDetails() {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("path", "/content/commerce/products/bdb");
		map.put("nodename", "skuid");
		map.put("p.limit", "-1");
		map.put("type", "nt:unstructured");
		lenient().when(solrSearchService.getHpNodeResource("skuid", "", resourceResolver)).thenReturn(hpResource);
		lenient().when(hpResource.getValueMap()).thenReturn(hpValueMap);
		lenient().when(hpValueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class)).thenReturn("label");
		lenient().when(hpValueMap.get(CommonConstants.REGULATORY_STATUS, String.class)).thenReturn("ruo");
		lenient().when(hpValueMap.get(CommonConstants.SIZE_QTY, String.class)).thenReturn("label");
		lenient().when(hpValueMap.get(CommonConstants.SIZE_UOM, String.class)).thenReturn("label");
		lenient().when(hpResource.getParent()).thenReturn(parent);
		lenient().when(parent.getPath()).thenReturn("/content/commerce/products/bdb/products");
		lenient().when(parent.getParent()).thenReturn(paResource);
		lenient().when(paResource.getPath()).thenReturn("path/to/base");
		lenient().when(hpResource.adaptTo(ValueMap.class)).thenReturn(hpValueMap);
		lenient().when(hpValueMap.get(CommonConstants.PRIMARY_SUPER_CATEGORY, String.class)).thenReturn("super");
		lenient().when(hpValueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class)).thenReturn("super");

		lenient().when(bdbApiEndpointService.getVialImagesBasePath()).thenReturn("vial/images/base/path");

		lenient().when(queryBuilder.createQuery(PredicateGroup.create(map), session)).thenReturn(query);
		lenient().when(query.getResult()).thenReturn(result);
		lenient().when(result.getResources()).thenReturn(resources);
		lenient().when(resources.hasNext()).thenReturn(true, true, true, false);
		lenient().when(resources.hasNext()).thenReturn(true, true, false);
		lenient().when(resources.next()).thenReturn(nextResource);
		lenient().when(nextResource.hasChildren()).thenReturn(true, false);
		lenient().when(nextResource.getChild(CommonConstants.HP)).thenReturn(varientResource);
		lenient().when(varientResource.getValueMap()).thenReturn(varientValueMap);
		lenient().when(varientValueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class)).thenReturn("LabelDesc");
		lenient().when(varientValueMap.get(CommonConstants.SIZE_QTY, String.class)).thenReturn("sizQty");
		lenient().when(varientResource.getParent()).thenReturn(varientResourceParent);
		lenient().when(varientResourceParent.getParent()).thenReturn(varientResourceParentParent);
		lenient().when(varientResourceParentParent.getPath()).thenReturn("/content/commerce/products/bdb");
		lenient().when(varientResource.adaptTo(ValueMap.class)).thenReturn(valueMap);
		lenient().when(valueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class)).thenReturn("LabelDesc");
//		companionProductsModel.getVarientHPDetails("skuid");

	}

	@Test
	void testGetVarientHPDetailsException() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		companionProductsModel.getVarientHPDetails("skuid", "baseMaterialNumber","340336");
	}

	@Test
	void testGetProductURL() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(bdbApiEndpointService.getCustomRunMode()).thenReturn("author");
		companionProductsModel.getProductURL("producturl", "externalizedUrl");
	}

	@Test
	void testGetVarientHPDetaildsExceptifon() throws LoginException {
		JsonObject companionProdDataJson = new JsonObject();
		companionProdDataJson.addProperty(CommonConstants.COMPANION_MATERIAL_NUMBER, "mock");
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(baseHpResource.getChild(CommonConstants.HP)).thenReturn(varientResource);
		lenient().when(varientResource.adaptTo(ValueMap.class)).thenReturn(varientValueMap);
		lenient().when(varientResource.getParent()).thenReturn(baseHpResource);
		lenient().when(baseHpResource.getChild("340336")).thenReturn(hpResource);
		lenient().when(varientValueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class)).thenReturn("LabelDesc");
		lenient().when(varientValueMap.get(CommonConstants.REGULATORY_STATUS, String.class))
				.thenReturn("regulatoryStatus");
		companionProductsModel.getLabelDescFromBaseHp(companionProdDataJson, baseHpResource, "LabelDesc","340336");
	}

	@Test
	void testGetVariantProperties() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		JsonObject companionProdDataJson = new JsonObject();
		companionProdDataJson.addProperty(CommonConstants.COMPANION_MATERIAL_NUMBER, "mock");
		// lenient().when(nextResource.getChild(CommonConstants.HP)).thenReturn(varientResource);
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(baseHpResource.getChild(CommonConstants.HP)).thenReturn(varientResource);
		lenient().when(varientResource.adaptTo(ValueMap.class)).thenReturn(varientValueMap);
		lenient().when(varientValueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class)).thenReturn("LabelDesc");
		lenient().when(varientValueMap.get(CommonConstants.REGULATORY_STATUS, String.class))
				.thenReturn("regulatoryStatus");
		lenient().when(nextResource.getChild(CommonConstants.HP)).thenReturn(varientResource);
		lenient().when(varientResource.adaptTo(ValueMap.class)).thenReturn(varientValueMap);
		lenient().when(varientValueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class)).thenReturn("LabelDesc");
		lenient().when(varientValueMap.get(CommonConstants.SIZE_QTY, String.class)).thenReturn("sizQty");
		lenient().when(varientValueMap.get(CommonConstants.REGULATORY_STATUS, String.class))
				.thenReturn("regulatoryStatus");
		lenient().when(varientResource.getValueMap()).thenReturn(varientValueMap);
		lenient().when(varientResource.getParent()).thenReturn(varientResourceParent);
		lenient().when(varientResourceParent.getParent()).thenReturn(varientResourceParentParent);
		lenient().when(varientResourceParentParent.getPath()).thenReturn("/content/commerce/products/bdb");
		lenient().when(varientResourceParentParent.getChild(CommonConstants.HP)).thenReturn(varientResource);
		lenient().when(varientResource.adaptTo(ValueMap.class)).thenReturn(valueMap);
		lenient().when(varientResourceParent.getPath()).thenReturn("/content/commerce/products/bdb");
		lenient().when(valueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class)).thenReturn("LabelDesc");
		companionProductsModel.getVariantProperties("skuId", "size", companionProdDataJson, nextResource,baseHpResource);
	}

	@Test
	void testGetCompanionList() throws JsonProcessingException {
		JsonArray jsonArray = new JsonArray();
		JsonObject obj = new JsonObject();
		obj.addProperty(CommonConstants.COMPANION_MATERIAL_NUMBER, "mock");
		jsonArray.add(element);
		lenient().when(element.getAsJsonObject()).thenReturn(obj);
		lenient().when(bdbApiEndpointService.getCustomRunMode()).thenReturn("author");
		companionProductsModel.getCompanionList(jsonArray);
	}

	@Test
	void testGetProductURLpublish() throws LoginException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(bdbApiEndpointService.getCustomRunMode()).thenReturn("publish");
		companionProductsModel.getProductURL("producturl", "externalizedUrl");
	}

	/**
	 * Test GetVarientHPDetails login exception.
	 *
	 * @throws LoginException
	 */
	// @Test
	void testIterateCompanionJsonObject() throws LoginException, JsonProcessingException, JsonMappingException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		JsonArray productsArray = new JsonArray();
		JsonObject obj = new JsonObject();
		obj.addProperty("companionTypes", "companionType");
		obj.addProperty("COMPANION_MATERIAL_NUMBER", "mock");
		obj.addProperty("BASE_MATERIAL_NUMBER", "baseMaterialNumber");
		productsArray.add(element);
		lenient().when(element.getAsJsonObject()).thenReturn(obj);
		companionProductsModel.iterateCompanionJsonObject("searchProductId", productsArray);

	}

	@Test
	void testGetBean() throws NoSuchFieldException {
		List<CompanionProductsBean> companionProductsBeanList = new ArrayList<>();
		companionProductsBeanList.add(companionProductsBean);
		PrivateAccessor.setField(companionProductsModel, "companionBeanList", companionProductsBeanList);
		assertNotNull(companionProductsModel.getCompanionBeanList());
	}

}
