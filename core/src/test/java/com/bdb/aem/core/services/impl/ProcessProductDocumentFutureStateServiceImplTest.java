
  package com.bdb.aem.core.services.impl;
  
  import static org.mockito.Mockito.lenient;
import static
  org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import
  org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import
  org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.adobe.dam.print.ids.StringConstants;
import
  com.adobe.granite.workflow.WorkflowException;
import
  com.adobe.granite.workflow.metadata.MetaDataMap;
import
  com.bdb.aem.core.services.BDBWorkflowConfigService;
import
  com.bdb.aem.core.services.WorkflowHelperService;
import
  com.bdb.aem.core.services.solr.SolrSearchService;
import
  com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import
  com.day.cq.dam.api.Rendition;
import
  com.day.cq.replication.ReplicationException;
import
  com.day.cq.replication.Replicator;
import
  com.day.cq.tagging.InvalidTagFormatException;
import
  com.day.cq.tagging.TagManager;
  
 /**
	 * The Class ProcessProductDocumentFutureStateServiceImplTest.
	 */

  @ExtendWith(MockitoExtension.class) 
  @MockitoSettings(strictness = Strictness.LENIENT) class
  ProcessProductDocumentFutureStateServiceImplTest {
  
 /** The process product document. */

  @InjectMocks ProcessProductDocumentFutureStateServiceImpl
  processProductDocumentFutureStateServiceImpl;
  
 /** The workflow helper service. */

  @Mock private WorkflowHelperService workflowHelperService;
  
 /** The resource resolver factory. */

  @Mock ResourceResolverFactory resourceResolverFactory;
  
 /** The bDB workflow config service. */

  @Mock private BDBWorkflowConfigService bDBWorkflowConfigService;
  
 /** The resource resolver. */

  @Mock private ResourceResolver resourceResolver;
  
 /** The tag manager. */

  @Mock private TagManager tagManager;
  
 /** The session. */

  @Mock private Session session;
  
 /** The current node. */

  @Mock private Node currentNode;
  
 /** The rendition. */

  @Mock private Rendition rendition;
  
 /** The args. */

  @Mock private MetaDataMap args;
  
 /** The asset man. */

  @Mock private AssetManager assetMan;
  
 /** The remove asset man. */

  @Mock private com.adobe.granite.asset.api.AssetManager removeAssetMan;
  
 /** The current asset. */

  @Mock private Asset currentAsset;
  
 /** The final asset. */

  @Mock private Asset finalAsset;
  
 /** The asset stream. */

  @Mock private InputStream assetStream;
  
 /** The properties. */

  @Mock private ValueMap properties;
  
 /** The properties. */

  @Mock private ModifiableValueMap oldProperties;  
  
 /** The resource. */

  @Mock private Resource resource;
  
 /** The value factory. */

  @Mock private ValueFactory valueFactory;
  
 /** The asset binary. */

  @Mock private Binary assetBinary;
  
 /** The resources. */

  List<Resource> resources = new ArrayList<>();
  
 /** The replicator. */

  @Mock Replicator replicator;
  
 /** The solr search service. */

  @Mock SolrSearchService solrSearchService;
  
 /** The Constant assetPath. */

  private static final String assetPath = "/content/dam/pankaj.pdf";
  
 /** The Constant DOC_PART_ID. */

  private static final String DOC_PART_ID = "docPartId";
  
 /** The Constant DOC_TYPE. */

  private static final String DOC_TYPE = "docType";
  
 /** The Constant HP_NODE_PATH. */

  private static final String HP_NODE_PATH =
  "/var/commerce/products/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies/644444_base/644444/hp";
  
 /** The Constant DAM_COMMERCE_BASE. */

  private static final String DAM_COMMERCE_BASE = "dam/products/global/";
  
 /** The Constant VAR_COMMERCE_BASE. */

  private static final String VAR_COMMERCE_BASE =
  "/var/commerce/products/bdb/products/";
  
 /** The Constant DOC_TITLE. */

  private static final String DOC_TITLE = "docTitle";
  
 /** The Constant PDFX_DOC_TITLE. */

  private static final String PDFX_DOC_TITLE = "pdfx:docTitle";
  
 /** The Constant PDFX_DOC_REGION. */

  private static final String PDFX_DOC_REGION = "pdfx:docRegion";
  
 /** The Constant DOC_LANG. */

  private static final String DOC_LANG = "docLang";
  
 /** The Constant PDFX_DOC_LANG. */

  private static final String PDFX_DOC_LANG = "pdfx:docLang";
  
 /** The Constant DOC_SKU. */

  private static final String DOC_SKU = "docSKU";
  
 /** The Constant PDFX_DOC_SKU. */

  private static final String PDFX_DOC_SKU = "pdfx:docSKU";
  
 /** The Constant DOC_DESC. */

  private static final String DOC_DESC = "docDesc";
  
 /** The Constant PDFX_DOC_DESC. */

  private static final String PDFX_DOC_DESC = "pdfx:docDesc";
  
 /** The Constant DOC_EXPIRY_DATE. */

  private static final String DOC_EXPIRY_DATE = "docExpiryDate";
  
 /** The Constant PDFX_DOC_EXPIRY_DATE. */

  private static final String PDFX_DOC_EXPIRY_DATE = "pdfx:docExpiryDate";
  
 /** The Constant DOC_REG_STATUS. */

  private static final String DOC_REG_STATUS = "docRegStatus";
  
 /** The Constant PDFX_DOC_REG_STATUS. */

  private static final String PDFX_DOC_REG_STATUS = "pdfx:docRegStatus";
  
 /** The Constant DOC_REV. */

  private static final String DOC_REV = "docRevision";
  
 /** The Constant PDFX_DOC_REV. */

  private static final String PDFX_DOC_REV = "pdfx:docRevision";
  
 /** The Constant DOC_OWNER. */

  private static final String DOC_OWNER = "docOwner";
  
 /** The Constant PDFX_DOC_OWNER. */

  private static final String PDFX_DOC_OWNER = "pdfx:docOwner";
  
 /** The Constant DEFAULT_DOC_LANG. */

  private static final String DEFAULT_DOC_LANG = "EN";
  
 /** The Constant DEFAULT_DOC_OWNER. */

  private static final String DEFAULT_DOC_OWNER = "PT";
  
 /** The Constant DOC_PROD_NAME. */

  private static final String DOC_PROD_NAME = "productName";
  
 /** The Constant PDFX_DOC_PROD_NAME. */

  private static final String PDFX_DOC_PROD_NAME = "pdfx:productName";
  
 /** The Constant DOC_KEYWORDS. */

  private static final String DOC_KEYWORDS = "docKeywords";
  
 /** The Constant PDFX_DOC_KEYWORDS. */

  private static final String PDFX_DOC_KEYWORDS = "pdfx:docKeywords";
  
 /** The Constant PDFX_DOC_PART_ID. */

  private static final String PDFX_DOC_PART_ID = "pdfx:docPartId";
  
 /** The Constant PDFX_DOC_TYPE. */

  private static final String PDFX_DOC_TYPE = "pdfx:docType";
  
 /**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */

 
  @BeforeEach void setUp() throws Exception { resources.add(resource); }
  
  
  
 /**
	 * Execute test.
	 *
	 * @throws WorkflowException         the workflow exception
	 * @throws LoginException            the login exception
	 * @throws RepositoryException       the repository exception
	 * @throws InvalidTagFormatException the invalid tag format exception
	 * @throws ReplicationException      the replication exception
	 */
  
		  @Test void executeTest() throws WorkflowException, LoginException,
		  RepositoryException, InvalidTagFormatException, ReplicationException {
		  when(resourceResolver.getResource(Mockito.anyString())).thenReturn(resource);
		  when(resource.adaptTo(ValueMap.class)).thenReturn(properties);		
		  lenient().when(resource.adaptTo(org.apache.sling.api.resource.ModifiableValueMap.class)).thenReturn(oldProperties);
		  when(properties.get(PDFX_DOC_TITLE,
		  StringUtils.EMPTY)).thenReturn(DOC_TITLE); when(properties.get(PDFX_DOC_TYPE,
		  StringUtils.EMPTY)).thenReturn(DOC_TYPE);
		  when(properties.get(PDFX_DOC_REGION, StringUtils.EMPTY)).thenReturn("US,EU");
		  when(properties.get(PDFX_DOC_LANG, DEFAULT_DOC_LANG)).thenReturn(DOC_LANG);
		  when(properties.get(PDFX_DOC_SKU, StringUtils.EMPTY)).thenReturn(DOC_SKU);
		  when(properties.get(PDFX_DOC_DESC, StringUtils.EMPTY)).thenReturn("");
		  when(properties.get(StringConstants.METADATA_DESC,
		  StringUtils.EMPTY)).thenReturn(DOC_DESC);
		  when(properties.get(PDFX_DOC_EXPIRY_DATE,
		  StringUtils.EMPTY)).thenReturn(DOC_EXPIRY_DATE);
		  when(properties.get(PDFX_DOC_REG_STATUS,
		  StringUtils.EMPTY)).thenReturn(DOC_REG_STATUS);
		  when(properties.get(PDFX_DOC_REV, StringUtils.EMPTY)).thenReturn(DOC_REV);
		  when(properties.get(PDFX_DOC_OWNER,
		  DEFAULT_DOC_OWNER)).thenReturn(DOC_OWNER);
		  when(properties.get(PDFX_DOC_PROD_NAME,
		  StringUtils.EMPTY)).thenReturn(DOC_PROD_NAME);
		  when(properties.get(PDFX_DOC_KEYWORDS,
		  StringUtils.EMPTY)).thenReturn(DOC_KEYWORDS);
		  when(properties.get(PDFX_DOC_PART_ID,
		  StringUtils.EMPTY)).thenReturn(DOC_PART_ID);
		  when(solrSearchService.getHpNodeResources(Mockito.anyString(),
		  Mockito.anyString(), Mockito.any())) .thenReturn(resources);
		  when(bDBWorkflowConfigService.getVarCommerceBasePath()).thenReturn(
		  VAR_COMMERCE_BASE);
		  when(bDBWorkflowConfigService.getDamAssetBasePath()).thenReturn(
		  DAM_COMMERCE_BASE); when(resource.getPath()).thenReturn(HP_NODE_PATH);
		  when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		  when(resourceResolver.adaptTo(AssetManager.class)).thenReturn(assetMan);
		  when(resourceResolver.getResource(Mockito.anyString())).thenReturn(resource);
		
		  when(resource.adaptTo(Asset.class)).thenReturn(currentAsset);
		  when(currentAsset.getOriginal()).thenReturn(rendition);
		  when(rendition.getStream()).thenReturn(assetStream);
		  when(session.getValueFactory()).thenReturn(valueFactory);
		  when(valueFactory.createBinary(assetStream)).thenReturn(assetBinary);
		  when(assetMan.createOrUpdateAsset(Mockito.anyString(), Mockito.any(),
		  Mockito.anyString(), Mockito.anyBoolean())).thenReturn(finalAsset);
		  when(resource.adaptTo(Node.class)).thenReturn(currentNode);
		  when(currentNode.getPath()).thenReturn("content/dam/pankaj.pdf");
		  lenient().when(resourceResolver.adaptTo(com.adobe.granite.asset.api.
		  AssetManager.class)).thenReturn(removeAssetMan);
		  processProductDocumentFutureStateServiceImpl.processDocuments(assetPath,
		  resourceResolver, session); } }
		 