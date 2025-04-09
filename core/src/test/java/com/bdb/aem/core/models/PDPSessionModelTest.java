package com.bdb.aem.core.models;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.Externalizer;
import com.day.cq.dam.api.Asset;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.*;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.util.Iterator;

import static org.mockito.Mockito.lenient;

/**
 * The Class PDPSessionModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class PDPSessionModelTest {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PDPSessionModelTest.class);

	/** The aem context. */
	AemContext aemContext = new AemContext();

	/** The PDP Session model. */
	@InjectMocks
	PDPSessionModel pdpSessionModel;

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
	Resource hpNodeResource, toolsResource;

	/** The varient value map. */
	@Mock
	ValueMap varientValueMap, toolsMap, valueMapMarketing;

	/** The hero obj. */
	@Mock
	Object heroObj, CompanionObject, toolsObj;

	/** The current page. */
	@Mock
	Page currentPage;

	/** Mock ResourceResolverFactory. */
	@Mock
	ExternalizerService externalizerService;

	/** The base product mock resource. */
	@Mock
	Resource baseProductMockResourceParent, baseProductMockResourceSuperParent, baseRes, parentRes, hpResource,
			baseProductResource, clinicalRes, imageResource;

	/** The child. */
	@Mock
	Resource child;

	/** The children. */
	@Mock
	Iterator<Resource> children;

	@Mock
	TagManager tagManager;

	@Mock
	Tag tagg, taggg, tagggg;

	@Mock
	private SolrSearchService solrSearchService;

	/** The asset path obj. */
	JsonObject assetPathObj = new JsonObject();
	@Mock
	Asset asset, imgAsset;
	/** Mock BDBApiEndpointService. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	@Mock
	ValueMap hpResourceVM, hpValueMap;

	private String materialNumber = "12345";

	private String damRegionPath = "/content";
	JsonArray jsonArray = new JsonArray();
	JsonArray pdpJsonArray = new JsonArray();
	@Mock
	CatalogStructureUpdateService catalogStructureUpdateService;
	@Mock
	Externalizer externalizer;
	@Mock
	Object obj;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		final String[] input2 = new String[] { "myString1", "940150" };
		final String[] tag = new String[] { "tag1", "tag2" };
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPath);
		lenient().when(requestPath.getSelectors()).thenReturn(input2);
		lenient().when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		lenient().when(solrSearchService.getHpNodeResource("940150", "us", resourceResolver))
				.thenReturn(hpNodeResource);
		lenient().when(hpNodeResource.adaptTo(ValueMap.class)).thenReturn(hpValueMap);
		lenient().when(hpNodeResource.getPath()).thenReturn(
				"/content/commerce/products/bdb/products/reagents/single-cell-multinomics-reagents/abseq/940150_base/940150/hp");
		lenient().when(hpNodeResource.getParent()).thenReturn(baseProductMockResourceParent);
		lenient().when(hpNodeResource.getParent().getParent()).thenReturn(baseProductMockResourceSuperParent);
		lenient().when(baseProductMockResourceParent.getChild("marketing" + CommonConstants.SINGLE_SLASH + "us"))
				.thenReturn(child);
		lenient().when(child.adaptTo(ValueMap.class)).thenReturn(valueMapMarketing);
		lenient().when(baseProductMockResourceSuperParent.getPath()).thenReturn(
				"/content/commerce/products/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-(ruo)/553141_base");
		lenient().when(resourceResolver.getResource(
				"/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-(ruo)/553141_base"))
				.thenReturn(baseRes);
		lenient().when(baseRes.listChildren()).thenReturn(children);
		lenient().when(children.hasNext()).thenReturn(true, false);
		lenient().when(children.next()).thenReturn(nextResource);
		lenient().when(child.adaptTo(Asset.class)).thenReturn(asset);
		lenient().when(asset.getMetadata(CommonConstants.CQ_TAGS)).thenReturn(tag);
		lenient().when(baseProductMockResourceParent.getName()).thenReturn("assetName");
		lenient().when(baseRes.getPath()).thenReturn("/content/dam/bdb/assets");
		lenient().when(resourceResolver.getResource("/content/dam/bdb/assets/assetName_01.png"))
				.thenReturn(toolsResource);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);

	}

	@Test
	void testClinicalVialObj() throws Exception {
		lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(clinicalRes);
		pdpSessionModel.getClinicalVialObj(pdpJsonArray, clinicalRes, jsonArray, assetPathObj, materialNumber);
	}

	@Test
	void testGetAssetJson() {
		lenient().when(baseProductResource.getPath()).thenReturn(damRegionPath);
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(baseRes);
		lenient().when(baseRes.listChildren()).thenReturn(children);
		lenient().when(hpResource.adaptTo(ValueMap.class)).thenReturn(hpValueMap);
		lenient().when(hpResource.getParent()).thenReturn(baseRes);
		lenient().when(hpResource.getParent().getName()).thenReturn(damRegionPath);

		lenient().when(nextResource.adaptTo(Asset.class)).thenReturn(asset);
		JsonArray galleryJsonArray = new JsonArray();
		JsonArray heroJsonArray = new JsonArray();
		final String[] tag = new String[] { "tag1", "tag2", "tag3" };

		lenient().when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		lenient().when(tagManager.resolve("tag1")).thenReturn(tagg);
		lenient().when(tagManager.resolve("tag2")).thenReturn(taggg);
		lenient().when(tagManager.resolve("tag3")).thenReturn(tagggg);
		lenient().when(tagg.getTitle()).thenReturn("HERO");
		lenient().when(taggg.getTitle()).thenReturn("GALLERY");
		lenient().when(tagggg.getTitle()).thenReturn("test");
		lenient().when(child.getPath()).thenReturn("/content/cq:tag/bdb/tags");
		pdpSessionModel.getAssetJson(resourceResolver, baseProductResource, assetPathObj, hpResource, hpResourceVM);
	}

	@Test
	void testImagesWithoutTags() throws Exception {
		JsonArray galleryJsonArray = new JsonArray();
		lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(hpResource);
		lenient().when(hpResource.adaptTo(ValueMap.class)).thenReturn(hpValueMap);
		lenient().when(hpResource.getParent()).thenReturn(baseRes);
		lenient().when(hpResource.getParent().getName()).thenReturn(damRegionPath);
		lenient().when(hpResourceVM.get(CommonConstants.REGULATORY_STATUS)).thenReturn(obj);
		lenient().when(obj.toString()).thenReturn("ruo");
		lenient().when(hpResourceVM.get(CommonConstants.REGULATORY_STATUS, String.class)).thenReturn("RUO");
		lenient().when(hpResourceVM.get(CommonConstants.PDP_TEMPLATE, String.class)).thenReturn("SFA");
		pdpSessionModel.getImagesWithoutTags(galleryJsonArray, hpResource, hpResource, galleryJsonArray,
				galleryJsonArray, galleryJsonArray, galleryJsonArray, hpResourceVM);

	}

	@Test
	void testGetters() {
		lenient().when(hpResourceVM.get(CommonConstants.TDS_DESCRIPTION, String.class)).thenReturn("TDS_DESCRIPTION");
		pdpSessionModel.getMetaDataAttributes(hpResourceVM);
		pdpSessionModel.getPlaceholderImage(resourceResolver, assetPathObj, jsonArray);
		pdpSessionModel.getMetaDataAttribute(imgAsset, "property");
		pdpSessionModel.getImageProperties(resourceResolver, jsonArray, child, imgAsset, assetPathObj);
		pdpSessionModel.getProductName();
		pdpSessionModel.getDescription();
		pdpSessionModel.getKeywords();
	}

	/**
	 * Test get asset paths.
	 */
	// @Test
	void testGetAssetPaths() {
		lenient().when(resourceResolver.getResource("/content/dam/bdb/assets/assetName_01.png")).thenReturn(null);
		lenient().when(hpNodeResource.adaptTo(ValueMap.class)).thenReturn(hpResourceVM);
		lenient().when(hpResourceVM.get(CommonConstants.LABEL_DESCRIPTION, String.class)).thenReturn("testProductName");
		pdpSessionModel.init();

	}

	/**
	 * Test get asset paths.
	 */
	@Test
	void testGetAssetMetaData() {
		JsonArray galleryJsonArray = new JsonArray();
		JsonArray heroJsonArray = new JsonArray();
		final String[] tag = new String[] { "tag1", "tag2", "tag3" };

		lenient().when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		lenient().when(tagManager.resolve("tag1")).thenReturn(tagg);
		lenient().when(tagManager.resolve("tag2")).thenReturn(taggg);
		lenient().when(tagManager.resolve("tag3")).thenReturn(tagggg);
		lenient().when(tagg.getTitle()).thenReturn("HERO");
		lenient().when(taggg.getTitle()).thenReturn("GALLERY");
		lenient().when(tagggg.getTitle()).thenReturn("test");
		lenient().when(child.getPath()).thenReturn("/content/cq:tag/bdb/tags");
		pdpSessionModel.getAssetMetaData(resourceResolver, assetPathObj, galleryJsonArray, heroJsonArray, child, asset,
				tag);

	}

	@Test
	void testInit() throws LoginException,Exception {
		lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver, "940150",CommonConstants.MATERIAL_NUMBER)).thenReturn(baseRes);
		lenient().when(bdbApiEndpointService.getCustomRunMode()).thenReturn("author");
		lenient().when(baseRes.getParent()).thenReturn(clinicalRes);
		lenient().when(clinicalRes.getChild(CommonConstants.HP)).thenReturn(hpResource);
		lenient().when(baseRes.getChild(CommonConstants.SAP_CC_NODENAME)).thenReturn(hpResource);
		lenient().when(hpResource.getChild(CommonConstants.REGION_DETAILS_NODE_NAME)).thenReturn(baseProductResource);
		lenient().when(baseProductResource.adaptTo(ValueMap.class)).thenReturn(hpValueMap);
		pdpSessionModel.init();

	}

	@Test
	void testLoginException() throws LoginException {
		lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenThrow(LoginException.class);
		pdpSessionModel.init();
	}

}
