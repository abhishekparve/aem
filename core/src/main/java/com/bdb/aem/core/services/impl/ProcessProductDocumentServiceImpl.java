package com.bdb.aem.core.services.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.bean.ExcelNodeBean;
import com.bdb.aem.core.bean.PdfMetaDataBean;
import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.ProcessProductDocumentService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.day.cq.replication.ReplicationException;
import com.day.cq.search.Query;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.InvalidTagFormatException;
import com.day.cq.tagging.TagManager;

/**
 * The Class ProcessProductDocumentServiceImpl.
 */
@Component(service = ProcessProductDocumentService.class, immediate = true)
public class ProcessProductDocumentServiceImpl implements ProcessProductDocumentService {

	/** The Constant LOG. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessProductDocumentServiceImpl.class);

	/** The Constant DOC_TITLE. */
	private static final String DOC_TITLE = "docTitle";

	/** The Constant DOC_REGION. */
	private static final String DOC_REGION = "docRegion";

	/** The Constant DOC_LANG. */
	private static final String DOC_LANG = "docLang";

	/** The Constant DOC_SKU. */
	private static final String DOC_SKU = "docSKU";

	/** The Constant DOC_DESC. */
	private static final String DOC_DESC = "docDesc";

	/** The Constant DOC_EXPIRY_DATE. */
	private static final String DOC_EXPIRY_DATE = "docExpiryDate";

	/** The Constant DOC_REG_STATUS. */
	private static final String DOC_REG_STATUS = "docRegStatus";

	/** The Constant DOC_REV. */
	private static final String DOC_REV = "docRevision";

	/** The Constant DOC_OWNER. */
	private static final String DOC_OWNER = "docOwner";

	/** The Constant ASSET_TYPE. */
	private static final String ASSET_TYPE = "application/pdf";

	/** The Constant BASE_FILE_NAME. */
	private static final String BASE_FILE_NAME = "baseFileName";

	/** The Constant MATARIAL_NUM. */
	private static final String MAT_NUM = "matNumber";

	/** The Constant DOC_PART_ID. */
	private static final String DOC_PART_ID = "docPartId";

	/** The Constant DOC_TYPE. */
	private static final String DOC_TYPE = "docType";

	/** The Constant REGION. */
	private static final String REGION = "region";

	/** The Constant WEB_NAME. */
	private static final String WEB_NAME = "webName";

	/** The Constant LABEL_DESC. */
	private static final String LABEL_DESC = "labelDescription";

	/** The Constant MATERIAL_NUMBER. */
	private static final String MATERIAL_NUMBER = "materialNumber";

	/** The Constant REGION_TAG_NAMESPACE. */
	private static final String REGION_TAG_NAMESPACE = "/content/cq:tags/bdb/regions";

	/** The Constant REGION_TAG_ID. */
	private static final String REGION_TAG_ID = "bdb:regions/";

	/** The bDB workflow config service. */
	@Reference
	private BDBWorkflowConfigService bDBWorkflowConfigService;

	/** The solr search service. */
	@Reference
	private SolrSearchService solrSearchService;

	/** The workflow helper service. */
	@Reference
	private WorkflowHelperService workflowHelperService;

	/**
	 * Process documents.
	 *
	 * @param payloadPath     the payload path
	 * @param serviceResolver the service resolver
	 * @param session         the session
	 * @throws ReplicationException      the replication exception
	 * @throws RepositoryException       the repository exception
	 * @throws InvalidTagFormatException the invalid tag format exception
	 */
	@Override
	public void processDocuments(String payloadPath, ResourceResolver serviceResolver, Session session)
			throws ReplicationException, RepositoryException, InvalidTagFormatException {
		LOGGER.debug("Entry processDocuments method of ProcessProductDocument");
		LOGGER.debug("Processing asset {}", payloadPath);
		String assetName = CommonHelper.getAssetNameFromPath(payloadPath);
		if (StringUtils.isNotEmpty(assetName)) {
			List<Resource> excelResource = getExcelNodeResources(serviceResolver, assetName);
			if (CollectionUtils.isNotEmpty(excelResource)) {
				List<ExcelNodeBean> excelNodeBeanList = getExcelBeanListFromNode(excelResource);
				LOGGER.debug("Excel node bean : {}", excelNodeBeanList);
				processAssets(excelNodeBeanList, payloadPath, assetName, serviceResolver, session);
			} else {
				LOGGER.error("Asset not found in the Excel nodes");
			}
		} else {
			LOGGER.error("Asset name is not in correct format");
		}
		LOGGER.debug("Exit processDocuments method of ProcessProductDocument");
	}

	/**
	 * Process assets.
	 *
	 * @param excelNodeBeanList the excel node bean list
	 * @param payloadPath       the payload path
	 * @param assetName         the asset name
	 * @param serviceResolver   the service resolver
	 * @param session           the session
	 * @return the list
	 * @throws ReplicationException      the replication exception
	 * @throws RepositoryException       the repository exception
	 * @throws InvalidTagFormatException the invalid tag format exception
	 */
	public void processAssets(List<ExcelNodeBean> excelNodeBeanList, String payloadPath, String assetName,
			ResourceResolver serviceResolver, Session session)
			throws ReplicationException, RepositoryException, InvalidTagFormatException {
		LOGGER.debug("Entry processAssets method of ProcessProductDocument");
		List<Resource> resourceFromQuery = null;
		boolean isMoved = false;
		for (ExcelNodeBean excelNodeBean : excelNodeBeanList) {
			resourceFromQuery = solrSearchService.getHpNodeResources(CommonConstants.UNIQUE_IDENTIFIER, excelNodeBean.getMatNumber(),
					serviceResolver);
			if (CollectionUtils.isNotEmpty(resourceFromQuery)) {
				moveResourceToDam(excelNodeBean, resourceFromQuery, payloadPath, assetName, serviceResolver, session);
				isMoved = true;
			} else {
				LOGGER.error("No product found in product catalogue corresponding to Excel node bean : {}",
						excelNodeBeanList);
			}
		}
		if (isMoved) {
			//deleteAssetFromDamFolder(payloadPath, serviceResolver, session);
			workflowHelperService.moveAssetFromTempAssetFolder(payloadPath, serviceResolver, session);
		}
		LOGGER.debug("Exit processAssets method of ProcessProductDocument");
	}

	/**
	 * Delete asset from dam folder.
	 *
	 * @param assetPath       the asset path
	 * @param serviceResolver the service resolver
	 * @param session         the session
	 * @throws RepositoryException the repository exception
	 */
	public void deleteAssetFromDamFolder(String assetPath, ResourceResolver serviceResolver, Session session)
			throws RepositoryException {
		LOGGER.debug("Entry deleteAssetFromTempFolder method of ProcessProductDocument");
		com.adobe.granite.asset.api.AssetManager removeAssetMan = serviceResolver
				.adaptTo(com.adobe.granite.asset.api.AssetManager.class);
		LOGGER.debug("Removing resource : {}", assetPath);
		removeAssetMan.removeAsset(assetPath);
		session.save();
		LOGGER.debug("Exit deleteAssetFromTempFolder method of ProcessProductDocument");
	}

	/**
	 * Move resource to dam.
	 *
	 * @param excelNodeBean     the excel node bean
	 * @param resourceFromQuery the resource from query
	 * @param payloadPath       the payload path
	 * @param assetName         the asset name
	 * @param serviceResolver   the service resolver
	 * @param session           the session
	 * @return the list
	 * @throws ReplicationException      the replication exception
	 * @throws RepositoryException       the repository exception
	 * @throws InvalidTagFormatException the invalid tag format exception
	 */
	public List<Resource> moveResourceToDam(ExcelNodeBean excelNodeBean, List<Resource> resourceFromQuery,
			String payloadPath, String assetName, ResourceResolver serviceResolver, Session session)
			throws ReplicationException, RepositoryException, InvalidTagFormatException {
		LOGGER.debug("Entry moveResourceToDam method of ProcessProductDocument");
		ValueMap properties;
		PdfMetaDataBean pdfMetaDataBean = null;
		for (Resource resource : resourceFromQuery) {
			properties = resource.getParent().getParent().getChild(CommonConstants.HP).adaptTo(ValueMap.class);
			if (MapUtils.isNotEmpty(properties)) {
				pdfMetaDataBean = new PdfMetaDataBean();
				pdfMetaDataBean.setDocTitle(properties.get(WEB_NAME, StringUtils.EMPTY));
				pdfMetaDataBean.setDocDesc(properties.get(LABEL_DESC, StringUtils.EMPTY));
				pdfMetaDataBean.setDocType(excelNodeBean.getDocType());
				pdfMetaDataBean.setDocSku(excelNodeBean.getMatNumber());
				pdfMetaDataBean.setDocRegion(excelNodeBean.getRegion());
				pdfMetaDataBean.setDocPartId(excelNodeBean.getDocPartId());
				pdfMetaDataBean.setTempFolderFilePath(payloadPath);
				pdfMetaDataBean.setTempFolderFileName(assetName);
				pdfMetaDataBean.setDamFolderFilePath(CommonHelper.getDamPathFromVarComResource(bDBWorkflowConfigService,
						resource.getPath(), assetName));
				pdfMetaDataBean.setDamFolderFileName(assetName);
			}
			if (null != pdfMetaDataBean) {
				LOGGER.debug("Process Asset related to Product bean : {}", pdfMetaDataBean);
				createResourceInDam(pdfMetaDataBean, serviceResolver, session);
			}

		}
		LOGGER.debug("Entry moveResourceToDam method of ProcessProductDocument");
		return resourceFromQuery;
	}

	/**
	 * Creates the resource in dam.
	 *
	 * @param pdfMetaDataBean the pdf meta data bean
	 * @param serviceResolver the service resolver
	 * @param session         the session
	 * @throws RepositoryException       the repository exception
	 * @throws ReplicationException      the replication exception
	 * @throws InvalidTagFormatException the invalid tag format exception
	 */
	public void createResourceInDam(PdfMetaDataBean pdfMetaDataBean, ResourceResolver serviceResolver, Session session)
			throws RepositoryException, ReplicationException, InvalidTagFormatException {
		LOGGER.debug("Entry createResourceInDam method of ProcessProductDocument");
		createTagsForAsset(pdfMetaDataBean, serviceResolver, session);
		createResource(pdfMetaDataBean, serviceResolver, session);
		session.save();
		LOGGER.debug("Exit createResourceInDam method of ProcessProductDocument");
	}

	/**
	 * Creates the tags for asset.
	 *
	 * @param pdfMetaDataBean the pdf meta data bean
	 * @param serviceResolver the service resolver
	 * @param session the session
	 * @throws InvalidTagFormatException the invalid tag format exception
	 * @throws ReplicationException the replication exception
	 */
	public void createTagsForAsset(PdfMetaDataBean pdfMetaDataBean, ResourceResolver serviceResolver, Session session)
			throws InvalidTagFormatException, ReplicationException {
		LOGGER.debug("Entry createTagsForAsset method of ProcessProductDocument");
		TagManager tagManager = serviceResolver.adaptTo(TagManager.class);
		String[] regions = pdfMetaDataBean.getDocRegion();
		if (null != tagManager && null != regions && regions.length > 0) {
			tagManager.createTag(REGION_TAG_NAMESPACE, REGION, "regions tag for pdf regions");
			for (String region : regions) {
				tagManager.createTag(REGION_TAG_NAMESPACE + CommonConstants.SINGLE_SLASH + region.trim().toLowerCase(),
						region.trim().toUpperCase(), StringUtils.EMPTY);
				/*workflowHelperService.replicateResourceToPublish(session,
						REGION_TAG_NAMESPACE + CommonConstants.SINGLE_SLASH + region.trim().toLowerCase());*/
				LOGGER.debug("Tags created successfully for region {}", region);
			}
		}
		LOGGER.debug("Exit createTagsForAsset method of ProcessProductDocument");
	}

	/**
	 * Creates the resource.
	 *
	 * @param pdfMetaDataBean the pdf meta data bean
	 * @param serviceResolver the service resolver
	 * @param session         the session
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException the replication exception
	 */
	public void createResource(PdfMetaDataBean pdfMetaDataBean, ResourceResolver serviceResolver, Session session)
			throws RepositoryException, ReplicationException {
		LOGGER.debug("Entry createResource method of ProcessProductDocument");
		AssetManager assetMan = serviceResolver.adaptTo(AssetManager.class);
		Asset currentAsset = serviceResolver.getResource(pdfMetaDataBean.getTempFolderFilePath()).adaptTo(Asset.class);
		InputStream assetStream = currentAsset.getOriginal().getStream();
		Asset asset = createAsset(assetMan, assetStream, pdfMetaDataBean.getDamFolderFilePath(), ASSET_TYPE,
				serviceResolver, session);
		if (null != asset) {
			LOGGER.debug("Asset has been created at DAM location : {}", asset.getPath());
			setMetadataForAsset(pdfMetaDataBean, serviceResolver);
		} else {
			LOGGER.error("Could not create asset at the DAM location : {}", pdfMetaDataBean.getDamFolderFilePath());
		}
		LOGGER.debug("Exit createResource method of ProcessProductDocument");
	}

	/**
	 * Sets the metadata for asset.
	 *
	 * @param pdfMetaDataBean the pdf meta data bean
	 * @param serviceResolver the service resolver
	 * @throws RepositoryException the repository exception
	 */
	public void setMetadataForAsset(PdfMetaDataBean pdfMetaDataBean, ResourceResolver serviceResolver)
			throws RepositoryException {
		LOGGER.debug("Entry setMetadataForAsset method of ProcessProductDocument");
		Resource pdfMetadataResource = serviceResolver
				.getResource(pdfMetaDataBean.getDamFolderFilePath() + CommonConstants.METADATAPATH);
		if (null != pdfMetadataResource) {
			Node currentNode = pdfMetadataResource.adaptTo(Node.class);
			LOGGER.debug("Working on Dam node : {}", currentNode.getPath());
			placeMetadata(pdfMetaDataBean, currentNode);
		}
		LOGGER.debug("Exit setMetadataForAsset method of ProcessProductDocument");
	}

	/**
	 * Place metadata.
	 *
	 * @param pdfMetaDataBean the pdf meta data bean
	 * @param currentNode     the current node
	 * @throws RepositoryException the repository exception
	 */
	public void placeMetadata(PdfMetaDataBean pdfMetaDataBean, Node currentNode) throws RepositoryException {
		LOGGER.debug("Entry placeMetadata method of ProcessProductDocument");
		currentNode.setProperty(DOC_TITLE, pdfMetaDataBean.getDocTitle());
		currentNode.setProperty(DOC_TYPE, pdfMetaDataBean.getDocType());
		currentNode.setProperty(DOC_REGION, getAsTags(pdfMetaDataBean.getDocRegion()));
		currentNode.setProperty(DOC_LANG, pdfMetaDataBean.getDocLang());
		currentNode.setProperty(DOC_SKU, pdfMetaDataBean.getDocSku());
		currentNode.setProperty(DOC_DESC, pdfMetaDataBean.getDocDesc());
		currentNode.setProperty(DOC_EXPIRY_DATE, pdfMetaDataBean.getDocExpiryDate());
		currentNode.setProperty(DOC_REG_STATUS, pdfMetaDataBean.getDocRegStatus());
		currentNode.setProperty(DOC_REV, pdfMetaDataBean.getDocRevision());
		currentNode.setProperty(DOC_OWNER, pdfMetaDataBean.getDocOwner());
		currentNode.setProperty(DOC_PART_ID, pdfMetaDataBean.getDocPartId());
		LOGGER.debug("Exit try of placeMetadata method of ProcessProductDocument");
	}

	/**
	 * Gets the excel bean list from node.
	 *
	 * @param excelResourceList the excel resource list
	 * @return the excel bean list from node
	 */
	public List<ExcelNodeBean> getExcelBeanListFromNode(List<Resource> excelResourceList) {
		LOGGER.debug("Entry getExcelBeanListFromNode method of ProcessProductDocument");
		List<ExcelNodeBean> excelNodeBeanList = new ArrayList<>();
		ExcelNodeBean excelNodeBean;
		ValueMap properties;
		for (Resource resource : excelResourceList) {
			properties = resource.adaptTo(ValueMap.class);
			excelNodeBean = new ExcelNodeBean();
			if (MapUtils.isNotEmpty(properties)) {
				String[] region = properties.get(REGION, String[].class);
				if (null != region && region.length > 0) {
					excelNodeBean.setRegion(region);
				} else {
					excelNodeBean.setRegion(new String[0]);
				}
				excelNodeBean.setBaseFileName(properties.get(BASE_FILE_NAME, StringUtils.EMPTY));
				excelNodeBean.setMatNumber(properties.get(MAT_NUM, StringUtils.EMPTY));
				excelNodeBean.setDocPartId(properties.get(DOC_PART_ID, StringUtils.EMPTY));
				excelNodeBean.setDocType(properties.get(DOC_TYPE, StringUtils.EMPTY));
			}
			LOGGER.debug("Excel node bean : {}", excelNodeBean);
			excelNodeBeanList.add(excelNodeBean);
		}
		LOGGER.debug("Exit getExcelBeanListFromNode method of ProcessProductDocument");
		return excelNodeBeanList;
	}

	/**
	 * Gets the as tags.
	 *
	 * @param regions the regions
	 * @return the as tags
	 */
	public String[] getAsTags(String[] regions) {
		LOGGER.debug("Entry getAsTags method of ProcessProductDocument");
		String tagBasePathId = REGION_TAG_ID;
		String[] regionAsTag = new String[regions.length];
		for (int i = 0; i < regionAsTag.length; i++) {
			regionAsTag[i] = tagBasePathId + regions[i].trim().toLowerCase();
		}
		LOGGER.debug("Exit getAsTags method of ProcessProductDocument");
		return regionAsTag;
	}

	/**
	 * Gets the excel node resources.
	 *
	 * @param serviceResolver the service resolver
	 * @param assetName       the asset name
	 * @return the excel node resources
	 */
	public List<Resource> getExcelNodeResources(ResourceResolver serviceResolver, String assetName) {
		LOGGER.debug("Entry getExcelNodeResources method of ProcessProductDocument");
		Map<String, String> queryMap = CommonHelper.getQueryMapForExcelNode(assetName, MAT_NUM, DOC_PART_ID,
				bDBWorkflowConfigService.getExcelNodeBasePath());
		LOGGER.debug("QueryMap for Excel Node : {}", queryMap);
		Query query = CommonHelper.getQuery(serviceResolver, queryMap);
		LOGGER.debug("Query for Excel Node : {}", query);
		LOGGER.debug("Exit getExcelNodeResources method of ProcessProductDocument");
		return getResourcesFromQuery(query);
	}

	/**
	 * Gets the resources from query.
	 *
	 * @param query the query
	 * @return the resources from query
	 */
	private List<Resource> getResourcesFromQuery(Query query) {
		LOGGER.debug("Entry getResourcesFromQuery method of ProcessProductDocument");
		List<Resource> resourceList = new ArrayList<>();
		if (null != query) {
			SearchResult result = query.getResult();
			LOGGER.debug("Query Result Size {} ", result.getTotalMatches());
			Iterator<Resource> resources = result.getResources();
			if (null != resources) {
				while (resources.hasNext()) {
					resourceList.add(resources.next());
				}
			} else {
				LOGGER.debug("Excel Resource Not Found");
			}
		} else {
			LOGGER.debug("Could not create query from the params {}", query);
		}
		LOGGER.debug("Exit getResourcesFromQuery method of ProcessProductDocument");
		return resourceList;
	}

	/**
	 * Creates the asset.
	 *
	 * @param assetMan         the asset man
	 * @param assetInputStream the asset input stream
	 * @param destPath         the dest path
	 * @param assetType        the asset type
	 * @param serviceResolver  the service resolver
	 * @param session          the session
	 * @return the asset
	 * @throws RepositoryException the repository exception
	 */
	public Asset createAsset(AssetManager assetMan, InputStream assetInputStream, String destPath, String assetType,
			ResourceResolver serviceResolver, Session session) throws RepositoryException {
		LOGGER.debug("Entry CreateAsset method of ProcessProductDocument");
		ValueFactory factory;
		Asset asset = null;
		workflowHelperService.createFolderStructure(destPath, session, serviceResolver);
		if (session.nodeExists(destPath)) {
			deleteAssetFromDamFolder(destPath, serviceResolver, session);
		}
		factory = session.getValueFactory();
		Binary assetBinary = factory.createBinary(assetInputStream);
		asset = assetMan.createOrUpdateAsset(destPath, assetBinary, assetType, true);
		LOGGER.debug("Exit CreateAsset method of ProcessProductDocument");
		return asset;
	}
}