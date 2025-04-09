package com.bdb.aem.core.services.impl;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFactory;

import org.apache.pdfbox.rendering.PDFRenderer;
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

import com.adobe.granite.workflow.WorkflowException;
import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.dam.api.Rendition;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.InvalidTagFormatException;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.tagging.TagManager.FindResults;

@ExtendWith(MockitoExtension.class)
class ProductImgMigrationServiceImplTest {
	
	/** Image name value */
    private static final String IMAGE_NAME ="imageName";

	/** ProductImgMigration Object */
	@InjectMocks
	ProductImgMigrationServiceImpl productImgMigrationServiceImpl;

	/** The workflow helper service. */
	@Mock
	private WorkflowHelperService workflowHelperService;

	/** The ResourceResolver */
	@Mock
	ResourceResolver resourceResolver;
	/** Mock ResourceResolverFactory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The Tag Manager */
	@Mock
	TagManager tagMan;

	/** The AssetManager */
	@Mock
	AssetManager assetMan;

	/** The AssetManager */
	@Mock
	com.adobe.granite.asset.api.AssetManager removeAssetMan;

	/** The Asset */
	@Mock
	Asset currentAsset;
	
	/** The Tag Manager */
	//@Mock
	//TagManager tagMan;

	/** The Resource */
	@Mock
	Resource assetResource;
	
	@Mock
	Resource variantResource;

	/** The Session */
	@Mock
	Session wfSession;

	/** The asset binary. */
	@Mock
	private Binary assetBinary;

	/** The value factory. */
	@Mock
	private ValueFactory valueFactory;

	/** The InputStream */
	@Mock
	InputStream assetStream;

	/** The Asset Rendition */
	@Mock
	Rendition rendition;

	/** The Image Resource */
	@Mock
	Resource imageRes;
	
	/** The Image Resource */
	@Mock
	FindResults tagResult;
	
	/** The parent Resource */
	@Mock
	Resource parentRes;

	/** The Node */
	@Mock
	Node currentNode;

	/** The ValueMap */
	@Mock
	ValueMap vMap;

	/** The TAG */
	@Mock
	Tag tag;

	@Mock
	Resource imgRes, hitRes, clinicalRes, imgResource;

	@Mock
	SolrSearchService solrSearchService;
	@Mock
	CatalogStructureUpdateService catalogStructureUpdateService;
	@Mock
	BDBWorkflowConfigService bDBWorkflowConfigService;
	@Mock
	SearchResult result;
	/** The query. */
	@Mock
	Query query;
	
	/** The query builder. */
	@Mock
	QueryBuilder queryBuilder;
	
	/** The session. */
	@Mock
	Session session;
	
	/** The predicates. */
	@Mock
	PredicateGroup predicates;
	/** The hit. */
	@Mock
	Hit hit;
	
	/** The Resource */
	@Mock
	Resource productResource;

	/** name of medias property in hp node */
	private static String medias = "[{\"imageName\":\"610383Image1\",\"caption\":\"Western blot analysis of Adaptin.Expression was visualized with HRP Goat Anti-Mouse Ig (Cat. No. 554002), and expressed as a 106 kDa band.\",\"mime\":\"image/jpeg\",\"imageMetadata\":\"ImageTitle~BD Trucount™ Tubes|ImageKeywords~Anti-Mouse|imageDesc~HRP Goat Anti-Mouse|imageLang~EN|imageRegion~US\",\"imageType\":\"PRIMARY\"}]";
	String assetPath = "/content/dam/bdb/temp-assets/images/610383Image1.jpeg";
	private static final String SLASH = "/";
	String productTagName = "productTagName";
	@Mock
	BufferedImage img;
	@Mock
	FindResults value;
	@Mock
	Object objValue;
	@Mock
	PDFRenderer pdfRenderer;
	@Mock
	Property property;
	private static final String LABEL_DESC = "labelDescription";
	/** Image caption property name */
	private static final String CAPTION = "caption";
	/** relative base path of the cq tags folder */
	private static final String CQ_TAG_BASE_PATH = "bdb:assets/images/";
	/** relative base path of the cq tags folder */
	private static final String CQ_TAG_VIAL = "bdb:assets/images/clinical-vial";
	/** relative base path of the cq tags folder */
	private static final String CQ_TAG_GALLERY = "bdb:assets/images/gallery";
	private static final String CLINICAL_VIAL_IMAGES = "clinical-vial-images";
	ArrayList<Resource> assetResList = new ArrayList<>();
	int height = 100;
	int width = 50;
	private Long resultCount = 1L;

	/**
	 * The Setup method.
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		/*
		 * Tag[] tagArray= {}; FindResults findResult = new FindResults();
		 * findResult.tags=tagArray;
		 * lenient().when(resourceResolver.adaptTo(AssetManager.class)).thenReturn(
		 * assetMan);
		 * lenient().when(resourceResolver.adaptTo(com.adobe.granite.asset.api.
		 * AssetManager.class)) .thenReturn(removeAssetMan);
		 * lenient().when(resourceResolver.getResource(
		 * "/content/dam/bdb/temp-assets/images/610383Image1.jpeg")).thenReturn(
		 * assetResource);
		 * lenient().when(assetResource.adaptTo(Asset.class)).thenReturn(currentAsset);
		 * ArrayList<Resource> list = new ArrayList<Resource>(); list.add(imgRes);
		 * lenient().when(solrSearchService.getHpNodeResource("332774", "us",
		 * resourceResolver)).thenReturn(imgRes);
		 * lenient().when(solrSearchService.getMatchingImagesHpRes("610383Image1",
		 * resourceResolver)).thenReturn(list);
		 * lenient().when(currentAsset.getOriginal()).thenReturn(rendition);
		 * lenient().when(rendition.getStream()).thenReturn(null);
		 * lenient().when(imgRes.getPath()).thenReturn(
		 * "/content/commerce/products/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies/644444_base/644444/hp"
		 * ); lenient() .when(resourceResolver .getResource(
		 * "/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies/644444_base/610383Image1.png/jcr:content/metadata"
		 * )) .thenReturn(imageRes);
		 * lenient().when(imageRes.adaptTo(Node.class)).thenReturn(currentNode);
		 * lenient().when(imgRes.adaptTo(ValueMap.class)).thenReturn(vMap);
		 * lenient().when(vMap.get("medias")).thenReturn(medias);
		 * lenient().when(vMap.get("labelDescription")).thenReturn("labelDescription");
		 * lenient().when(resourceResolver.adaptTo(TagManager.class)).thenReturn(
		 * tagMan);
		 * 
		 * lenient().when(tagMan.findByTitle("PRIMARY")).thenReturn(findResult);
		 * lenient().when(tagMan.createTag("bdb:assets/images/primary", "PRIMARY",
		 * "")).thenReturn(tag);
		 * lenient().when(wfSession.getValueFactory()).thenReturn(valueFactory);
		 * lenient().when(valueFactory.createBinary(assetStream)).thenReturn(assetBinary
		 * );
		 */

	}

	@Test
	void testCreateAsset() throws LoginException, WorkflowException, RepositoryException, AccessControlException,
			InvalidTagFormatException, IOException, IllegalArgumentException {
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(wfSession.getValueFactory()).thenReturn(valueFactory);
		lenient().when(valueFactory.createBinary(assetStream)).thenReturn(assetBinary);
		productImgMigrationServiceImpl.createAsset(resourceResolver, assetMan, assetStream, assetPath, wfSession,
				removeAssetMan);

	}

	@Test
	void testProcessProductImage() throws LoginException, WorkflowException, RepositoryException,
			AccessControlException, InvalidTagFormatException, IOException, IllegalArgumentException {
		ArrayList<Resource> assetResList = new ArrayList<>();
		ArrayList<Rendition> renditionList = new ArrayList<>();
		renditionList.add(rendition);
		assetResList.add(hitRes);
		Tag[] tagArray= {tag};
		lenient().when(bDBWorkflowConfigService.getProductImageBasePath()).thenReturn("/content/dam/bdb/temp-assets/products/images/661310.png");
		lenient().when(rendition.getStream()).thenReturn(assetStream);
		lenient().when(assetStream.read(Mockito.any())).thenReturn(-1);
		
		lenient().when(resourceResolver.getResource("/content/dam/bdb/temp-assets/images/610383Image1.jpeg"))
				.thenReturn(assetResource);
		lenient().when(currentAsset.getMetadata(DamConstants.DC_FORMAT))
		.thenReturn("image/jpeg");
		lenient().when(currentAsset.getRenditions()).thenReturn(renditionList);
		lenient().when(rendition.getMimeType()).thenReturn("image/png");
		lenient().when(rendition.getSize()).thenReturn(1L);
		
		
		lenient().when(solrSearchService.getMatchingImagesHpRes(IMAGE_NAME.concat("\\\":\\\"").concat("610383Image1"), resourceResolver))
		.thenReturn(assetResList);
		
		lenient().when(hitRes.getPath())
		.thenReturn("/content/commerce/products/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies/644444_base/644444/hp");
		lenient().when(resourceResolver.getResource(
				"/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies/644444_base/610383Image1.png/jcr:content/metadata"))
				.thenReturn(imageRes);
		lenient().when(resourceResolver.adaptTo(AssetManager.class)).thenReturn(assetMan);
		lenient().when(resourceResolver.adaptTo(com.adobe.granite.asset.api.AssetManager.class))
				.thenReturn(removeAssetMan);
		lenient().when(assetResource.adaptTo(Asset.class)).thenReturn(currentAsset);
		lenient().when(currentNode.getPath()).thenReturn("/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies/644444_base/610383Image1.png/jcr:content/metadata");
		lenient().when(currentAsset.getOriginal()).thenReturn(rendition);
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(wfSession.getValueFactory()).thenReturn(valueFactory);
		lenient().when(valueFactory.createBinary(assetStream)).thenReturn(assetBinary);
		lenient().when(imageRes.adaptTo(Node.class)).thenReturn(currentNode);
		lenient().when(hitRes.adaptTo(ValueMap.class)).thenReturn(vMap);
		
		lenient().when(vMap.containsKey("medias")).thenReturn(true);
		lenient().when(vMap.get("medias")).thenReturn(medias);
		lenient().when(vMap.get("labelDescription")).thenReturn("labelDescription");
		
		FindResults findResult = new FindResults();
		findResult.tags=tagArray;
		lenient().when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagMan);
		lenient().when(tagMan.findByTitle("644444")).thenReturn(findResult);
		//lenient().when(tagResult.tags).thenReturn(tagArray);
		lenient().when(tag.getTagID()).thenReturn("bdb:products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies/644444_base/644444");
		
		lenient().when(currentNode.getPath()).thenReturn("/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies/644444_base/610383Image1.png/jcr:content/metadata");
		lenient().when(hitRes.getParent()).thenReturn(parentRes);
		lenient().when(parentRes.getName()).thenReturn("644444");
		lenient().when(tagMan.findByTitle("PRIMARY")).thenReturn(findResult);
		lenient().when(tagMan.createTag("bdb:assets/images/primary", "PRIMARY","")).thenReturn(tag);
		
		lenient().when(imageRes.adaptTo(Node.class)).thenReturn(currentNode);
		productImgMigrationServiceImpl.processProductImage("/content/dam/bdb/temp-assets/images/610383Image1.jpeg",
				resourceResolver, wfSession);

	}
	
	@Test
	void testProcessProductTifImage() throws LoginException, WorkflowException, RepositoryException,
			AccessControlException, InvalidTagFormatException, IOException, IllegalArgumentException {
		lenient().when(bDBWorkflowConfigService.getProductImageBasePath()).thenReturn("/content/dam/bdb/temp-assets/products/images/661310.png");
		lenient().when(rendition.getStream()).thenReturn(assetStream);
		lenient().when(resourceResolver.getResource("/content/dam/bdb/temp-assets/images/610383Image1.tif"))
				.thenReturn(assetResource);
		lenient().when(resourceResolver.getResource(
				"/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies/644444_base/610383Image1.png/jcr:content/metadata"))
				.thenReturn(imageRes);
		lenient().when(resourceResolver.adaptTo(AssetManager.class)).thenReturn(assetMan);
		lenient().when(resourceResolver.adaptTo(com.adobe.granite.asset.api.AssetManager.class))
				.thenReturn(removeAssetMan);
		lenient().when(assetResource.adaptTo(Asset.class)).thenReturn(currentAsset);
		lenient().when(currentAsset.getOriginal()).thenReturn(rendition);
		productImgMigrationServiceImpl.processProductImage("/content/dam/bdb/temp-assets/images/610383Image1.tif",
				resourceResolver, wfSession);

	}
	
	@Test
	void testProcessFlatFileImage() throws LoginException, WorkflowException, RepositoryException,
			AccessControlException, InvalidTagFormatException, IOException, IllegalArgumentException {
		lenient().when(bDBWorkflowConfigService.getProductImageBasePath()).thenReturn("/content/dam/bdb/temp-assets/products/images/661310.png");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("path", "/var/bdb/products/images/unprocessed-records");
		map.put("property", "imageName");
		map.put("property.value", "661310_123.png");
		map.put("type", "nt:base");
		lenient().when(resourceResolver.getResource("/content/dam/bdb/temp-assets/products/images/661310.png"))
				.thenReturn(assetResource);
		ArrayList<Rendition> renditionList = new ArrayList<>();
		renditionList.add(rendition);
		lenient().when(currentAsset.getMetadata(DamConstants.DC_FORMAT))
		.thenReturn("image/jpeg");
		lenient().when(currentAsset.getRenditions()).thenReturn(renditionList);
		lenient().when(rendition.getMimeType()).thenReturn("image/png");
		lenient().when(rendition.getSize()).thenReturn(1L);
		lenient().when(rendition.getStream()).thenReturn(assetStream);
		lenient().when(assetStream.read(Mockito.any())).thenReturn(-1);
		lenient().when(wfSession.getValueFactory()).thenReturn(valueFactory);
		lenient().when(valueFactory.createBinary(assetStream)).thenReturn(assetBinary);
		lenient().when(imageRes.adaptTo(Node.class)).thenReturn(currentNode);
		lenient().when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);
		lenient().when(query.getResult()).thenReturn(result);
		lenient().when(result.getTotalMatches()).thenReturn(resultCount);
		lenient().when(resourceResolver.adaptTo(AssetManager.class)).thenReturn(assetMan);
		lenient().when(resourceResolver.adaptTo(com.adobe.granite.asset.api.AssetManager.class))
				.thenReturn(removeAssetMan);
		lenient().when(assetResource.adaptTo(Asset.class)).thenReturn(currentAsset);
		lenient().when(currentAsset.getOriginal()).thenReturn(rendition);
		List<Hit> hitList = new ArrayList<>();
		hitList.add(hit);
		lenient().when(result.getHits()).thenReturn(hitList);
		lenient().when(hit.getPath()).thenReturn("/var/bdb/products/images/unprocessed-records/09-26-2024/1727341884006/record-1/661310");
		lenient().when(resourceResolver.getResource(hit.getPath())).thenReturn(productResource);
		lenient().when(productResource.getName()).thenReturn("661310");
		lenient().when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver, "332774",
				CommonConstants.MATERIAL_NUMBER)).thenReturn(variantResource);
		productImgMigrationServiceImpl.processProductImage("/content/dam/bdb/temp-assets/products/images/661310.png",
				resourceResolver, wfSession);

	}

	@Test
	void testProcessProductImageClinical()
			throws LoginException, WorkflowException, RepositoryException, InvalidTagFormatException {
		lenient().when(bDBWorkflowConfigService.getProductImageBasePath()).thenReturn("/content/dam/bdb/temp-assets/products/images/661310.png");
		lenient()
				.when(resourceResolver.getResource(
						"/content/dam/bdb/temp-assets/images/packaging-images/clinical-vial-images/332774_01.png"))
				.thenReturn(assetResource);
		lenient().when(resourceResolver.getResource(
				"/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies/644444_base/332774_01.png/jcr:content/metadata"))
				.thenReturn(imageRes);
		lenient().when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver, "332774",
				CommonConstants.MATERIAL_NUMBER)).thenReturn(assetResource);
		// lenient().when(solrSearchService.getMatchingImagesHpRes("332774_01",
		// resourceResolver)).thenReturn(Mockito.any());
		lenient().when(assetResource.adaptTo(Asset.class)).thenReturn(currentAsset);
		lenient().when(currentAsset.getOriginal()).thenReturn(rendition);
		lenient().when(currentAsset.getMetadata(DamConstants.DC_FORMAT)).thenReturn("image/png");
		productImgMigrationServiceImpl.processProductImage(
				"/content/dam/bdb/temp-assets/images/packaging-images/clinical-vial-images/332774_01.png",
				resourceResolver, wfSession);
	}
	
	@Test
	void testProcessProductImageClinicalPdf()
			throws LoginException, WorkflowException, RepositoryException, InvalidTagFormatException {
		lenient().when(bDBWorkflowConfigService.getProductImageBasePath()).thenReturn("/content/dam/bdb/temp-assets/products/images/661310.png");
		lenient()
				.when(resourceResolver.getResource(
						"/content/dam/bdb/temp-assets/images/packaging-images/clinical-vial-images/332774_01.pdf"))
				.thenReturn(assetResource);
		lenient().when(resourceResolver.getResource(
				"/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies/644444_base/332774_01.png/jcr:content/metadata"))
				.thenReturn(imageRes);
		lenient().when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver, "332774",
				CommonConstants.MATERIAL_NUMBER)).thenReturn(assetResource);
		// lenient().when(solrSearchService.getMatchingImagesHpRes("332774_01",
		// resourceResolver)).thenReturn(Mockito.any());
		lenient().when(assetResource.adaptTo(Asset.class)).thenReturn(currentAsset);
		lenient().when(currentAsset.getOriginal()).thenReturn(rendition);
		lenient().when(currentAsset.getMetadata(DamConstants.DC_FORMAT)).thenReturn("application/pdf");
		productImgMigrationServiceImpl.processProductImage(
				"/content/dam/bdb/temp-assets/images/packaging-images/clinical-vial-images/332774_01.pdf",
				resourceResolver, wfSession);
	}

	@Test
	void testBufferedImage() throws LoginException, WorkflowException, RepositoryException, AccessControlException,
			InvalidTagFormatException, IOException, IllegalArgumentException {
		lenient().when(img.getHeight()).thenReturn(height);
		lenient().when(img.getWidth()).thenReturn(width);
		productImgMigrationServiceImpl.getTrimmedImage(img);
	}
}
