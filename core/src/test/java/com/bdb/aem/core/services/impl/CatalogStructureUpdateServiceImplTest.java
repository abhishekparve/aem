
package com.bdb.aem.core.services.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.Map.Entry;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.event.jobs.JobManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.bean.SAPProductVariantPropertyBean;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.tagging.InvalidTagFormatException;
import com.day.cq.tagging.TagManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * Junit for CatalogStructureUpdateServiceImpl
 *
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CatalogStructureUpdateServiceImplTest {

	@InjectMocks
	CatalogStructureUpdateServiceImpl catalogStructureUpdateServiceImpl;

	/** Mock ResourceResolverFactory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** Mock ResourceResolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** Mock Resource */
	@Mock
	Resource resource, lookUpResource;;

	@Mock
	Resource varientRes;
	@Mock
	Resource varientLookUpResource;
	@Mock
	Node varientNode;
	
	@Mock
	Node variantNode, node;;

	CommonHelper commonHelper;

	@Mock
	BDBSearchEndpointService searchConfig;
	/** The job manager. */
	@Mock
	JobManager jobManager;

	/** The workflow helper service. */
	@Mock
	WorkflowHelperService workflowHelperService;

	JsonObject variantObj = new JsonObject();

	JsonObject productObject = new JsonObject();
	/** The session. */
	@Mock
	Session session;
	@Mock
	SolrSearchService solrSearchService;
	@Mock
	ValueMap valueMap;
	@Mock
	Node varNode;
	@Mock
	Node sapNode;
	@Mock
	Node regionDetailsNode;
	
	@Mock
	ValueMap varientVM;
	
	@Mock
	ValueMap lookUpVM;
	@Mock
	ValueMap varientLookUpVM;
	String path = "/content/bdb";
	String productId = "1234";
	String productType = "productType";
	String MATERIAL_NUMBER = "MATERIAL_NUMBER";
	String BASE_MATERIAL_NUMBER = "BASE_MATERIAL_NUMBER";
	String CATALOG_PATH = "catalogPath";
	@Mock
	ArrayList<String> variantList;
	@Mock
	Property property;
	@Mock
	Property varientProp;
	@Mock
	Property lookUpProp;
	@Mock
	Property varientLookUpProp;
	@Mock
	SAPProductVariantPropertyBean productVariantPropertyBean;
	String[] jsonString = { "jsonStrings" };
	@Mock
	TagManager tagManager;
	JsonArray errorSKUs;
	@Mock
	JsonElement jsonelement;
	Entry<String, JsonElement> baseProductEntry;
	@Mock
	Replicator replicator;

	@Mock
	PrefixedProductDataResolverImpl pdResolver;

	/**
	 * Set up.
	 *
	 * @throws Exception the exception
	 */

	@BeforeEach
	void setUp() throws Exception {

		// commonHelper = mock(CommonHelper.class);
	}

	@Test
	void testVariantExistsInTaxonomy()
			throws LoginException, RepositoryException, JsonProcessingException, InvalidTagFormatException {
		ArrayList<String> variantList = new ArrayList<String>();
		variantList.add("variantList");
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(node.getPath()).thenReturn(path);
		lenient().when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(lookUpResource);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		catalogStructureUpdateServiceImpl.checkIfVariantExistsInTaxonomy(node, resourceResolver, "variantName");
		assertNotNull(catalogStructureUpdateServiceImpl.addVariantsInTaxonomy(node, resourceResolver, "variantName"));
		catalogStructureUpdateServiceImpl.replicateVariants(resourceResolver, variantList);
		catalogStructureUpdateServiceImpl.indexAndReplicateProducts(resourceResolver, "{\"products\":[{\"baseProduct\":{\"code\":\"545454\",\"variants\":[{\"code\":\"454545\",\"globalWebAvailable\":true,\"grossWeight\":123,\"netWeight\":25,\"researchOnly\":true,\"marketAvailability\":[{\"baseStore\":{\"uid\":\"us\"}}]}]}}]}");
		
	}
	@Test
	void testIterateOverJsonForVariantList()
	{
		assertNotNull(catalogStructureUpdateServiceImpl.iterateOverJsonForVariantList(resourceResolver, "{\"products\":[{\"code\":\"545454\",\"variants\":[{\"materialNumber\":\"454545\",\"globalWebAvailable\":true,\"grossWeight\":123,\"netWeight\":25,\"researchOnly\":true,\"marketAvailability\":[{\"baseStore\":{\"uid\":\"us\"}}]}]}]}"));
	}
	@Test
	void testCreateProductNodeStructure()
			throws LoginException, RepositoryException, JsonProcessingException, ReplicationException {
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(variantNode.hasNode(CommonConstants.HP_NODE)).thenReturn(false);
		lenient().when(variantNode.hasNode(CommonConstants.SAP_CC_NODENAME)).thenReturn(false);
		lenient().when(variantNode.hasNode(CommonConstants.REGION_DETAILS_NODE_NAME)).thenReturn(false);
		lenient().when(variantNode.getNode(CommonConstants.SAP_CC_NODENAME)).thenReturn(node);
		lenient().when(searchConfig.getCatalogStructureNodeType()).thenReturn(Mockito.anyString());
		catalogStructureUpdateServiceImpl.createProductNodeStructure(variantNode);
	}

	@Test
	void testNull() throws LoginException, RepositoryException, JsonProcessingException, InvalidTagFormatException {
		lenient().when(searchConfig.getAllowSolrIndexing()).thenReturn("false");
		catalogStructureUpdateServiceImpl.setHPNodeProperty(resourceResolver, "{products : []}");
		catalogStructureUpdateServiceImpl.addHpStructuretoVariants(productObject, node, resourceResolver, variantList,
				errorSKUs, true, true, variantList);
		catalogStructureUpdateServiceImpl.setNodeProperty(productObject, path, node);
		assertNotNull(catalogStructureUpdateServiceImpl.iterateOverJsonForVariantList(resourceResolver, "{products : []}"));

	}

	@Test
	void testGetters() throws LoginException, RepositoryException, JsonProcessingException, InvalidTagFormatException {
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		catalogStructureUpdateServiceImpl.getCatalogPath(path);
		catalogStructureUpdateServiceImpl.getTagPath(path);
		catalogStructureUpdateServiceImpl.getTagPathFromLookupForBaseProduct(productObject, resourceResolver, productId,
				productType);
		catalogStructureUpdateServiceImpl.getLookUpPathFromProductId(productId, "baseMaterialNumber");
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(lookUpResource);
		lenient().when(lookUpResource.adaptTo(ValueMap.class)).thenReturn(valueMap);
		lenient().when(valueMap.containsKey(CATALOG_PATH)).thenReturn(true);
		lenient().when(valueMap.get(CATALOG_PATH)).thenReturn(valueMap);
		assertNotNull(catalogStructureUpdateServiceImpl.getProductFromLookUp(resourceResolver, productId, productType));

	}

	/**
	 * Test create variant node structure.
	 * 
	 * @throws RepositoryException
	 */
	@Test
	void testSetHpNodeProperties() throws RepositoryException, LoginException {
		lenient().when(resourceResolver.adaptTo(Node.class)).thenReturn(node);
		JsonObject productObject = new JsonObject();
		productObject.addProperty(CommonConstants.SUPER_CATEGORY, "SUPER_CATEGORY");
		lenient().when(node.hasProperty("primarySuperCategory")).thenReturn(false);
		catalogStructureUpdateServiceImpl.setHpNodeProperties(productObject, node, "baseMaterialNumber");
	}

	@Test
	void testSetHpNodeMaterialNumber() throws RepositoryException, LoginException {
		lenient().when(resourceResolver.adaptTo(Node.class)).thenReturn(node);
		JsonObject productObject = new JsonObject();
		productObject.addProperty(CommonConstants.SUPER_CATEGORY, "SUPER_CATEGORY");
		lenient().when(node.hasProperty("primarySuperCategory")).thenReturn(false);
		lenient().when(node.getParent()).thenReturn(node);
		lenient().when(node.getNode(CommonConstants.HP_NODE)).thenReturn(node);
		lenient().when(node.getProperty("primarySuperCategory")).thenReturn(property);
		catalogStructureUpdateServiceImpl.setHpNodeProperties(productObject, node, "materialNumber");
	}

	@Test
	void testSetSAPProperties() throws RepositoryException, JsonProcessingException {

		lenient().when(searchConfig.getAllowSolrIndexing()).thenReturn("false");
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(searchConfig.getAdminPagePath()).thenReturn(path);
		lenient().when(resourceResolver.getResource(path)).thenReturn(resource);
		lenient().when(resource.adaptTo(ValueMap.class)).thenReturn(valueMap);
		lenient().when(valueMap.containsKey("slingJobEnabler")).thenReturn(true);
		assertNotNull(catalogStructureUpdateServiceImpl.setSAPNodeProperty(resourceResolver, "{\"products\":[{\"baseProduct\":{\"code\":\"545454\"}},{\"baseProduct\":{\"code\":\"454545\"}}]}"));

	}

	@Test
	void testSetSAPVariantProperties() throws RepositoryException, JsonProcessingException, InvalidTagFormatException {
		JsonObject productObject = new JsonObject();
		lenient().when(productVariantPropertyBean.getGlobalWebAvailable()).thenReturn(true);
		lenient().when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		productObject.addProperty(CommonConstants.SUPER_CATEGORY, CommonConstants.SUPER_CATEGORY);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(lookUpResource);
		lenient().when(lookUpResource.adaptTo(Node.class)).thenReturn(node);
		lenient().when(node.hasProperty(CATALOG_PATH)).thenReturn(true);
		lenient().when(node.getProperty(CATALOG_PATH)).thenReturn(property);
		lenient().when(property.getString()).thenReturn("/content");
		lenient().when(pdResolver.resolve(anyString())).thenReturn("/content/cq:tags/bdb/supercategory/545454");
		catalogStructureUpdateServiceImpl.createProductTaxonomy(resourceResolver, productObject, productId);
		catalogStructureUpdateServiceImpl.getTagPathFromLookupForVariants(node, resourceResolver, productId);
		assertNotNull(catalogStructureUpdateServiceImpl.getTagPathFromLookupForBaseProduct(productObject, resourceResolver, productId,
				productType));

	}
	
	@Test
	void testSetSAPProperty() throws RepositoryException, JsonProcessingException {

		lenient().when(searchConfig.getAllowSolrIndexing()).thenReturn("false");
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(searchConfig.getAdminPagePath()).thenReturn(path);
		lenient().when(resourceResolver.getResource(path)).thenReturn(resource);
		lenient().when(resource.adaptTo(ValueMap.class)).thenReturn(valueMap);
		lenient().when(valueMap.containsKey("slingJobEnabler")).thenReturn(true);
		lenient().when(valueMap.containsKey("catalogPath")).thenReturn(true);
		lenient().when(valueMap.get("catalogPath")).thenReturn(property);
		lenient().when(property.getString()).thenReturn("/content/commerce/products/545454");
		lenient().when(resourceResolver.getResource("/content/commerce/lookup/baseProduct/545454")).thenReturn(resource);
		lenient().when(resourceResolver.getResource("/content/commerce/products/545454")).thenReturn(resource);
		lenient().when(resource.adaptTo(Node.class)).thenReturn(variantNode);
		
		lenient().when(resourceResolver.getResource("/content/commerce/lookup/baseProduct/545f/545454")).thenReturn(lookUpResource);
		lenient().when(resourceResolver.getResource("/content/commerce/lookup/variant/454545")).thenReturn(varientRes);
		lenient().when(varientRes.adaptTo(ValueMap.class)).thenReturn(varientVM);
		lenient().when(lookUpResource.adaptTo(ValueMap.class)).thenReturn(lookUpVM);
		lenient().when(varientRes.adaptTo(Node.class)).thenReturn(varNode);
		lenient().when(varNode.getNode("sap-cc")).thenReturn(sapNode);
		lenient().when(sapNode.getNode("regionDetailsNode")).thenReturn(regionDetailsNode);
		lenient().when(varientVM.containsKey("slingJobEnabler")).thenReturn(true);
		lenient().when(lookUpVM.containsKey("catalogPath")).thenReturn(true);
		lenient().when(lookUpVM.get("catalogPath")).thenReturn(lookUpProp);
		lenient().when(lookUpProp.toString()).thenReturn("/content/commerce/products/545454");
		lenient().when(varientProp.getString()).thenReturn("/content/commerce/products/545454/454545");
		lenient().when(resourceResolver.getResource("/content/commerce/products/545454/454545")).thenReturn(varientRes);
		lenient().when(varientRes.getPath()).thenReturn("/content/commerce/products/545454/454545");
		lenient().when(searchConfig.getAllowProductReplication()).thenReturn("true");
		
		assertNotNull(catalogStructureUpdateServiceImpl.setSAPNodeProperty(resourceResolver, "{\"products\":[{\"baseProduct\":{\"code\":\"545454\",\"variants\":[{\"code\":\"454545\"}]}}]}"));

	}
	@Test
	void testSetHPNodeProperty() throws RepositoryException, JsonProcessingException, InvalidTagFormatException, LoginException {

		lenient().when(searchConfig.getAllowSolrIndexing()).thenReturn("false");
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(searchConfig.getAdminPagePath()).thenReturn(path);
		lenient().when(resourceResolver.getResource(path)).thenReturn(resource);
		lenient().when(resource.adaptTo(ValueMap.class)).thenReturn(valueMap);
		lenient().when(valueMap.containsKey("slingJobEnabler")).thenReturn(true);
		lenient().when(valueMap.containsKey("catalogPath")).thenReturn(true);
		lenient().when(valueMap.get("catalogPath")).thenReturn(property);
		lenient().when(property.getString()).thenReturn("/content/commerce/products/545454");
		lenient().when(resourceResolver.getResource("/content/commerce/lookup/baseProduct/545454")).thenReturn(resource);
		lenient().when(resourceResolver.getResource("/content/commerce/products/545454")).thenReturn(resource);
		lenient().when(resource.adaptTo(Node.class)).thenReturn(variantNode);
		
		lenient().when(resourceResolver.getResource("/content/commerce/lookup/variant/454545")).thenReturn(varientRes);
		lenient().when(varientRes.adaptTo(ValueMap.class)).thenReturn(varientVM);
		lenient().when(varientRes.adaptTo(Node.class)).thenReturn(varNode);
		lenient().when(varNode.getNode("sap-cc")).thenReturn(sapNode);
		lenient().when(sapNode.getNode("regionDetailsNode")).thenReturn(regionDetailsNode);
		lenient().when(varientVM.containsKey("slingJobEnabler")).thenReturn(true);
		lenient().when(varientVM.containsKey("catalogPath")).thenReturn(true);
		lenient().when(varientVM.get("catalogPath")).thenReturn(varientProp);
		lenient().when(varientProp.getString()).thenReturn("/content/commerce/products/545454/454545");
		lenient().when(resourceResolver.getResource("/content/commerce/products/545454/454545")).thenReturn(varientRes);
		lenient().when(varientRes.getPath()).thenReturn("/content/commerce/products/545454/454545");
		lenient().when(searchConfig.getAllowProductReplication()).thenReturn("true");
		
		lenient().when(searchConfig.getAllowProductReplication()).thenReturn("true");
		assertNotNull(catalogStructureUpdateServiceImpl.setHPNodeProperty(resourceResolver, "{\"products\":[{\"baseProduct\":{\"code\":\"545454\",\"variants\":[{\"code\":\"454545\"}]},\"baseMaterialNumber\":\"545454\",\"isVariantOnly\":\"Yes\"}]}"));

	}
	@Test
	void testElseSetHPNodeProperty() throws RepositoryException, JsonProcessingException, InvalidTagFormatException, LoginException {

		lenient().when(searchConfig.getAllowSolrIndexing()).thenReturn("false");
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(searchConfig.getAdminPagePath()).thenReturn(path);
		lenient().when(resourceResolver.getResource(path)).thenReturn(resource);
		lenient().when(resource.adaptTo(ValueMap.class)).thenReturn(valueMap);
		lenient().when(valueMap.containsKey("slingJobEnabler")).thenReturn(true);
		lenient().when(valueMap.containsKey("catalogPath")).thenReturn(true);
		lenient().when(valueMap.get("catalogPath")).thenReturn(property);
		lenient().when(property.getString()).thenReturn("/content/commerce/products/545454");
		lenient().when(resourceResolver.getResource("/content/commerce/lookup/baseProduct/545454")).thenReturn(resource);
		lenient().when(resourceResolver.getResource("/content/commerce/products/545454")).thenReturn(resource);
		lenient().when(resource.adaptTo(Node.class)).thenReturn(variantNode);
		
		lenient().when(resourceResolver.getResource("/content/commerce/products/bdb/supercategory/545454")).thenReturn(varientRes);
		lenient().when(varientRes.adaptTo(ValueMap.class)).thenReturn(varientVM);
		lenient().when(varientRes.adaptTo(Node.class)).thenReturn(varNode);
		lenient().when(varNode.getNode("sap-cc")).thenReturn(sapNode);
		lenient().when(sapNode.getNode("regionDetailsNode")).thenReturn(regionDetailsNode);
		lenient().when(varientVM.containsKey("slingJobEnabler")).thenReturn(true);
		lenient().when(varientVM.containsKey("catalogPath")).thenReturn(true);
		lenient().when(varientVM.get("catalogPath")).thenReturn(varientProp);
		lenient().when(varientProp.getString()).thenReturn("/content/commerce/products/bdb/catalogPath");
		lenient().when(resourceResolver.getResource("/content/commerce/products/545454/454545")).thenReturn(varientRes);
		lenient().when(varientRes.getPath()).thenReturn("/content/commerce/products/545454/454545");
		lenient().when(searchConfig.getAllowProductReplication()).thenReturn("true");
		
		lenient().when(resourceResolver.getResource("/content/commerce/lookup/baseProduct/545f/545454")).thenReturn(resource);
		lenient().when(variantNode.hasProperty("variantNode")).thenReturn(true);
		lenient().when(variantNode.getProperty("variantNode")).thenReturn(varientProp);
		lenient().when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		lenient().when(varNode.getNode(CommonConstants.HP_NODE)).thenReturn(node);
		lenient().when(pdResolver.resolve(anyString())).thenReturn("/content/cq:tags/bdb/supercategory/545454");
		lenient().when(node.getParent()).thenReturn(node);
		lenient().when(node.getNode(CommonConstants.HP_NODE)).thenReturn(node);
		lenient().when(node.getProperty("primarySuperCategory")).thenReturn(property);
		lenient().when(searchConfig.getCatalogStructureNodeType()).thenReturn(CommonConstants.OAK_UNSTRUCTURED);
		//assertNotNull(catalogStructureUpdateServiceImpl.setHPNodeProperty(resourceResolver, "{\"products\":[{\"baseProduct\":{\"code\":\"545454\",\"variants\":[{\"code\":\"454545\"}]},\"baseMaterialNumber\":\"545454\",\"isVariantOnly\":\"No\",\"superCategory\":\"superCategory\"}]}"));

	}
	@Test
	void getVariantsOfProductTest() throws JsonProcessingException, RepositoryException
	{
		lenient().when(searchConfig.getAllowSolrIndexing()).thenReturn("false");
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(searchConfig.getAdminPagePath()).thenReturn(path);
		lenient().when(resourceResolver.getResource(path)).thenReturn(resource);
		lenient().when(resource.adaptTo(ValueMap.class)).thenReturn(valueMap);
		lenient().when(valueMap.containsKey("slingJobEnabler")).thenReturn(true);
		lenient().when(valueMap.containsKey("catalogPath")).thenReturn(true);
		lenient().when(valueMap.get("catalogPath")).thenReturn(property);
		lenient().when(property.getString()).thenReturn("/content/commerce/products/545454");
		lenient().when(resourceResolver.getResource("/content/commerce/lookup/baseProduct/545454")).thenReturn(resource);
		lenient().when(resourceResolver.getResource("/content/commerce/products/545454")).thenReturn(resource);
		lenient().when(resource.adaptTo(Node.class)).thenReturn(variantNode);
		
		lenient().when(resourceResolver.getResource("/content/commerce/lookup/baseProduct/545f/545454")).thenReturn(lookUpResource);
		lenient().when(resourceResolver.getResource("/content/commerce/lookup/variant/454f/454545")).thenReturn(varientLookUpResource);
		lenient().when(resourceResolver.getResource("/content/commerce/lookup/variant/454545")).thenReturn(varientRes);
		lenient().when(varientRes.adaptTo(ValueMap.class)).thenReturn(varientVM);
		lenient().when(lookUpResource.adaptTo(ValueMap.class)).thenReturn(lookUpVM);
		lenient().when(varientLookUpResource.adaptTo(ValueMap.class)).thenReturn(varientLookUpVM);
		lenient().when(varientRes.adaptTo(Node.class)).thenReturn(varNode);
		lenient().when(varNode.getNode("sap-cc")).thenReturn(sapNode);
		lenient().when(sapNode.getNode("region-details")).thenReturn(regionDetailsNode);
		lenient().when(varientVM.containsKey("slingJobEnabler")).thenReturn(true);
		lenient().when(lookUpVM.containsKey("catalogPath")).thenReturn(true);
		lenient().when(lookUpVM.get("catalogPath")).thenReturn(lookUpProp);
		lenient().when(lookUpProp.toString()).thenReturn("/content/commerce/products/545454");
		lenient().when(varientLookUpVM.containsKey("catalogPath")).thenReturn(true);
		lenient().when(varientLookUpVM.get("catalogPath")).thenReturn(varientLookUpProp);
		lenient().when(varientLookUpProp.toString()).thenReturn("/content/commerce/products/545454/454545");
		lenient().when(varientProp.getString()).thenReturn("/content/commerce/products/545454/454545");
		lenient().when(resourceResolver.getResource("/content/commerce/products/545454/454545")).thenReturn(varientRes);
		lenient().when(varientRes.getPath()).thenReturn("/content/commerce/products/545454/454545");
		lenient().when(searchConfig.getAllowProductReplication()).thenReturn("true");
		
		assertNotNull(catalogStructureUpdateServiceImpl.setSAPNodeProperty(resourceResolver, "{\"products\":[{\"baseProduct\":{\"code\":\"545454\",\"variants\":[{\"code\":\"454545\",\"globalWebAvailable\":true,\"grossWeight\":123,\"netWeight\":25,\"researchOnly\":true,\"marketAvailability\":[{\"baseStore\":{\"uid\":\"us\"}}]}]}}]}"));
	}
}
