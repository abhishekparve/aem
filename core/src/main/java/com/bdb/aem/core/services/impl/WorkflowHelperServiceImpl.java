package com.bdb.aem.core.services.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFactory;

import com.day.cq.commons.jcr.JcrConstants;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.event.jobs.JobManager;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.jcr.JcrUtil;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.dam.commons.util.DamUtil;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.search.Query;
import com.day.cq.search.result.SearchResult;

/**
 * The Class WorkflowHelperServiceImpl.
 */
@Component(service = WorkflowHelperService.class, immediate = true)
public class WorkflowHelperServiceImpl implements WorkflowHelperService {

	/** The bdb workflow config service. */
	@Reference
	BDBWorkflowConfigService bdbWorkflowConfigService;
	
	/** The bdb workflow config service. */
	@Reference
	BDBApiEndpointService bdbApiEndpointService;

	/** The replicator. */
	@Reference
	Replicator replicator;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowHelperServiceImpl.class);

	/**
	 * Creates the node.
	 *
	 * @param path             the path
	 * @param resourceResolver the resource resolver
	 * @param session          the session
	 * @param nodeType         the node type
	 * @return the node
	 * @throws RepositoryException the repository exception
	 */
	@Override
	public Node createNode(String path, ResourceResolver resourceResolver, Session session, String nodeType)
			throws RepositoryException {
		LOGGER.debug("Entry createNode method of WorkflowHelperServiceImpl");
		return JcrUtil.createPath(path, nodeType, session);
	}

	/**
	 * Creates the node.
	 *
	 * @param path                 the path
	 * @param resourceResolver     the resource resolver
	 * @param session              the session
	 * @param nodeType             the node type
	 * @param intermediateNodeType the intermediate node type
	 * @throws RepositoryException the repository exception
	 */
	@Override
	public void createNode(String path, ResourceResolver resourceResolver, Session session, String nodeType,
			String intermediateNodeType) throws RepositoryException {
		LOGGER.debug("Entry createNode method of WorkflowHelperServiceImpl");
		JcrUtil.createPath(path, intermediateNodeType, nodeType, session, true);
	}

	/**
	 * Creates the folder structure based on the destPath before creating an asset.
	 *
	 * @param destPath        the dest path
	 * @param session         the session
	 * @param serviceResolver the service resolver
	 * @throws RepositoryException the repository exception
	 */
	@Override
	public void createFolderStructure(String destPath, Session session, ResourceResolver serviceResolver)
			throws RepositoryException {
		LOGGER.debug("Entry createFolderStructure method of WorkflowHelperServiceImpl");
		String folderName = destPath.substring(0, destPath.lastIndexOf(CommonConstants.SINGLE_SLASH));

		// Creating all the folders using the createNode method
		if (!session.nodeExists(folderName)) {
			createNode(folderName, serviceResolver, session, JcrResourceConstants.NT_SLING_FOLDER,
					JcrResourceConstants.NT_SLING_FOLDER);
			/** Replacing the first occurence of "/content/dam/bdb" from the folderName 
			* in order to avoid iterating the content, dam, and bdb folders
			*  if they are created in future under the "BDB_DAM_ROOT_PATH" path */ 
			folderName = folderName.replaceFirst(CommonConstants.CONST_BDB_DAM_ROOT_PATH, "");
			String [] folders = folderName.split("/");
			
			// Creating jcr:content node for all the folders under the BDB_DAM_ROOT_PATH
			Node currentNode = session.getNode(CommonConstants.CONST_BDB_DAM_ROOT_PATH);
			for (String nodeName : folders) {
				if (null != nodeName && !nodeName.isEmpty()) {
					if (currentNode.hasNode(nodeName)) {
						currentNode = currentNode.getNode(nodeName);
						if (!currentNode.hasNode(CommonConstants.JCR_CONTENT_NODE)) {
							createJcrNode(currentNode);
						}
					}
				}
			}	
		}

		LOGGER.debug("Exit createFolderStructure method of WorkflowHelperServiceImpl");
	}

	/**
	 * Explicitly creating jcr:content Node.
	 *
	 * @param node  the node under which jcr:content needs to be created
	 * @throws RepositoryException the repository exception
	 *
	 **/
	public static void createJcrNode(Node node) throws RepositoryException {
		LOGGER.debug("Entry createJcrNode method of WorkflowHelperServiceImpl");
		if (!node.hasNode(JcrConstants.JCR_CONTENT)) {
			try {
				node.addNode(JcrConstants.JCR_CONTENT, JcrConstants.NT_UNSTRUCTURED);
			} catch (RepositoryException e) {
				LOGGER.warn("Error while creating intermediate node", e);
				node.refresh(false);
			}
		}
		LOGGER.debug("Exit createJcrNode method of WorkflowHelperServiceImpl");
	}

	/**
	 * Gets the resources.
	 *
	 * @param payloadPath     the payload path
	 * @param serviceResolver the service resolver
	 * @param session         the session
	 * @return the resources
	 */
	@Override
	public List<String> getPDFResources(String payloadPath, ResourceResolver serviceResolver, Session session) {
		Map<String, String> queryMapForProductNode = CommonHelper.getQueryMapForProductNode(payloadPath,
				DamConstants.NT_DAM_ASSET, "jcr:content/metadata/dc:format", "application/pdf");
		LOGGER.debug("Entry getPDFResources method of WorkflowHelperServiceImpl");
		LOGGER.debug("QueryMap for PDF Resources Node : {}", queryMapForProductNode);
		Query query = CommonHelper.getQuery(serviceResolver, queryMapForProductNode);
		LOGGER.debug("Query for PDF Resources Node : {}", query);
		LOGGER.debug("Exit getPDFResources method of WorkflowHelperServiceImpl");
		return getResourcesFromQuery(query);
	}

	/**
	 * Gets the query map for clinical only.
	 *
	 * @param pathValue the path value
	 * @param typeValue the type value
	 * @return the query map for clinical only
	 */
	public static Map<String, String> getQueryMapForClinicalOnly(String pathValue, String typeValue) {
		Map<String, String> map = new HashMap<>();
		LOGGER.debug("Entry getQueryMapForClinicalOnly method of WorkflowHelperServiceImpl");
		map.put(CommonConstants.PATH, pathValue);
		map.put(CommonConstants.TYPE, typeValue);
		map.put(CommonConstants.LIMIT, CommonConstants.LIMIT_MINUS_ONE);
		LOGGER.debug("Exit getQueryMapForClinicalOnly method of WorkflowHelperServiceImpl");
		return map;
	}

	/**
	 * Gets the query map for all images.
	 *
	 * @param imagePath         the image path
	 * @param clinicalImagePath the clinical image path
	 * @return the query map for all images
	 */
	public static Map<String, String> getQueryMapForAllImages(String imagePath, String clinicalImagePath) {
		LOGGER.debug("Entry getQueryMapForAllImages method of WorkflowHelperServiceImpl");
		Map<String, String> map = new HashMap<>();
		map.put(CommonConstants.TYPE, DamConstants.NT_DAM_ASSET);
		map.put("group.p.or", "true");
		map.put("group.1_path", imagePath);
		map.put("group.1_path.flat", "true");
		map.put("group.2_path", clinicalImagePath);
		map.put("group.2_path.flat", "true");
		map.put(CommonConstants.LIMIT, CommonConstants.LIMIT_MINUS_ONE);
		LOGGER.debug("Exit getQueryMapForAllImages method of WorkflowHelperServiceImpl");
		return map;
	}

	/**
	 * Gets the image resources.
	 *
	 * @param payloadPath     the payload path
	 * @param serviceResolver the service resolver
	 * @param session         the session
	 * @return the image resources
	 */
	@Override
	public List<String> getImageResources(String payloadPath, ResourceResolver serviceResolver, Session session) {
		LOGGER.debug("Entry getExcelNodeResources method of getImageResources");
		Map<String, String> queryMapForProductNode = getQueryMapBasedOnPath(payloadPath);
		if (MapUtils.isNotEmpty(queryMapForProductNode)) {
			LOGGER.debug("QueryMap for Excel Node : {}", queryMapForProductNode);
			Query query = CommonHelper.getQuery(serviceResolver, queryMapForProductNode);
			LOGGER.debug("Query for Excel Node : {}", query);
			LOGGER.debug("Exit getExcelNodeResources method of getImageResources");
			return getResourcesFromQuery(query);
		} else {
			return Collections.emptyList();
		}
	}

	/**
	 * Gets the query map based on path.
	 *
	 * @param payloadPath the payload path
	 * @return the query map based on path
	 */
	private Map<String, String> getQueryMapBasedOnPath(String payloadPath) {
		LOGGER.debug("Entry getQueryMapBasedOnPath method of WorkflowHelperServiceImpl");
		Map<String, String> queryMapForAssets;
		if (StringUtils.equalsIgnoreCase(payloadPath, bdbWorkflowConfigService.getImageBasePath())) {
			queryMapForAssets = getQueryMapForAllImages(bdbWorkflowConfigService.getImageBasePath(),
					bdbWorkflowConfigService.getClinicalImagePath());
		} else if (StringUtils.equalsIgnoreCase(payloadPath, bdbWorkflowConfigService.getClinicalImagePath())) {
			queryMapForAssets = getQueryMapForClinicalOnly(bdbWorkflowConfigService.getClinicalImagePath(),
					DamConstants.NT_DAM_ASSET);
		} else if (StringUtils.equalsIgnoreCase(payloadPath, bdbWorkflowConfigService.getProcessedImageBasePath())) {
			queryMapForAssets = getQueryMapForAllImages(bdbWorkflowConfigService.getProcessedImageBasePath(),
					bdbWorkflowConfigService.getProcessedClinicalImagePath());
		} else if (StringUtils.equalsIgnoreCase(payloadPath, bdbWorkflowConfigService.getProcessedClinicalImagePath())) {
			queryMapForAssets = getQueryMapForClinicalOnly(bdbWorkflowConfigService.getProcessedClinicalImagePath(),
					DamConstants.NT_DAM_ASSET);
		} else if (StringUtils.equalsIgnoreCase(payloadPath, bdbWorkflowConfigService.getProductImageBasePath())) {
			queryMapForAssets = getQueryMapForClinicalOnly(bdbWorkflowConfigService.getProductImageBasePath(),
					DamConstants.NT_DAM_ASSET);
		}
		else {
			queryMapForAssets = new HashMap<>();
		}
		LOGGER.debug("Exit getQueryMapBasedOnPath method of WorkflowHelperServiceImpl");
		return queryMapForAssets;
	}

	/**
	 * Gets the resources from query.
	 *
	 * @param query the query
	 * @return the resources from query
	 */
	@Override
	public List<String> getResourcesFromQuery(Query query) {
		LOGGER.debug("Entry getResourcesFromQuery method of WorkflowHelperServiceImpl");
		List<String> resourceList = new ArrayList<>();
		if (null != query) {
			SearchResult result = query.getResult();
			LOGGER.debug("Query Result Size {} ", result.getTotalMatches());
			Iterator<Resource> resources = result.getResources();
			if (null != resources) {
				while (resources.hasNext()) {
					resourceList.add(resources.next().getPath());
				}
			} else {
				LOGGER.debug("Excel Resource Not Found");
			}
		} else {
			LOGGER.debug("Could not create query from the params {}", query);
		}
		LOGGER.debug("Exit getResourcesFromQuery method of WorkflowHelperServiceImpl");
		return resourceList;
	}

	/**
	 * Gets the sub lists.
	 *
	 * @param list   the list
	 * @param length the length
	 * @return the sub lists
	 */
	@Override
	public List<List<String>> getSubLists(List<String> list, final int length) {
		LOGGER.debug("Entry getSubLists method of WorkflowHelperServiceImpl");
		List<List<String>> parts = new ArrayList<>();
		final int N = list.size();
		for (int i = 0; i < N; i += length) {
			parts.add(new ArrayList<>(list.subList(i, Math.min(N, i + length))));
		}
		LOGGER.debug("Exit getSubLists method of WorkflowHelperServiceImpl");
		return parts;
	}

	/**
	 * Adds the job.
	 *
	 * @param assetPaths the asset paths
	 * @param jobManager the job manager
	 * @param jobType    the job type
	 */
	@Override
	public void addJob(List<String> assetPaths, JobManager jobManager, String jobType) {
		LOGGER.debug("job added for the paths : {}", assetPaths);
		Map<String, Object> jobProperties = new HashMap<>();
		jobProperties.put(CommonConstants.PAYLOAD_PATHS, assetPaths);
		jobManager.addJob(jobType, jobProperties);
		LOGGER.debug("job completed for the paths : {}", assetPaths);
	}
	
	/**
	 * Adds the job for replication.
	 *
	 * @param assetPaths the asset paths
	 * @param jobManager the job manager
	 * @param jobType the job type
	 * @param batchSize the batch size
	 * @param mode the mode
	 */
	@Override
	public void addJobForReplication(List<String> assetPaths, JobManager jobManager, String jobType, int batchSize, String mode) {
		LOGGER.debug("Replication job added for the paths : {}", assetPaths);
		Map<String, Object> jobProperties = new HashMap<>();
		jobProperties.put(CommonConstants.PAYLOAD_PATHS, assetPaths);
		jobProperties.put(BDBDistributionServiceImpl.KEY_CHUNK_SIZE, batchSize);
		jobProperties.put(BDBDistributionServiceImpl.KEY_MODE, mode);
		jobManager.addJob(jobType, jobProperties);
		LOGGER.debug("Replication job completed for the paths : {}", assetPaths);
	}
	
	/**
	 * Adds the job for indexing.
	 *
	 * @param assetPaths the asset paths
	 * @param jobManager the job manager
	 * @param jobType the job type
	 * @param assetType the asset type
	 */
	@Override
	public void addJobForIndexing(List<String> assetPaths, JobManager jobManager, String jobType, String assetType) {
		LOGGER.debug("Indexing job added for the paths : {}", assetPaths);
		Map<String, Object> jobProperties = new HashMap<>();
		jobProperties.put(CommonConstants.PAYLOAD_PATHS, assetPaths);
		jobProperties.put(BDBIndexingJob.ASSET_TYPE, assetType);
		jobManager.addJob(jobType, jobProperties);
		LOGGER.debug("Indexing job completed for the paths : {}", assetPaths);
	}
	
	@Override
	public void addJobForIndexingOnDemandTDS(List<String> assetPaths, JobManager jobManager, String jobType, String assetType) {
		LOGGER.debug("Indexing job added for the paths : {}", assetPaths);
		Map<String, Object> jobProperties = new HashMap<>();
		jobProperties.put(CommonConstants.PAYLOAD_PATHS, assetPaths);
		jobProperties.put(BDBIndexingJobForOnDemandTDS.ASSET_TYPE, assetType);
		jobManager.addJob(jobType, jobProperties);
		LOGGER.debug("Indexing job completed for the paths : {}", assetPaths);
	}

	/**
	 * Adds the job for AkamaiPurge.
	 *
	 * @param assetPaths the asset paths
	 * @param jobManager the job manager
	 * @param jobType the job type
	 */
	@Override
	public void addJobForAkamaiPurge(List<String> assetPaths, JobManager jobManager, String jobType) {
		LOGGER.debug("AkamaiPurge job added for the paths : {}", assetPaths);
		Map<String, Object> jobProperties = new HashMap<>();
		jobProperties.put(CommonConstants.PAYLOAD_PATHS, assetPaths);
		jobManager.addJob(jobType, jobProperties);
		LOGGER.debug("AkamaiPurge job completed for the paths : {}", assetPaths);
	}

	/**
	 * Gets the list of assets.
	 *
	 * @param path            the path
	 * @param serviceResolver the service resolver
	 * @param session         the session
	 * @return the list of assets
	 */
	@Override
	public List<String> getListOfPDFAssets(String path, ResourceResolver serviceResolver, Session session) {
		LOGGER.debug("Entry getListOfPDFAssets method of WorkflowHelperServiceImpl");
		List<String> list = new ArrayList<>();
		Resource resource = serviceResolver.getResource(path);
		if (DamUtil.isAsset(resource)) {
			if (StringUtils.containsIgnoreCase(path, CommonConstants.DOT_PDF)) {
				list.add(path);
			}
			return list;
		} else {
			list = getPDFResources(path, serviceResolver, session);
			return list;
		}
	}

	/**
	 * Gets the list of image assets.
	 *
	 * @param path            the path
	 * @param serviceResolver the service resolver
	 * @param session         the session
	 * @return the list of image assets
	 */
	@Override
	public List<String> getListOfImageAssets(String path, ResourceResolver serviceResolver, Session session) {
		LOGGER.debug("Entry getListOfImageAssets method of WorkflowHelperServiceImpl");
		List<String> list = new ArrayList<>();
		Resource resource = serviceResolver.getResource(path);
		if (DamUtil.isAsset(resource)) {
			list.add(path);
		} else {
			list = getImageResources(path, serviceResolver, session);
		}
		LOGGER.debug("Exit getListOfImageAssets method of WorkflowHelperServiceImpl");
		return list;
	}

	/**
	 * Assign job in batches.
	 *
	 * @param assetPaths the asset paths
	 * @param jobManager the job manager
	 * @param jobType    the job type
	 */
	@Override
	public void assignJobInBatches(List<String> assetPaths, JobManager jobManager, String jobType) {
		LOGGER.debug("Assigning Job for : {} in batches", jobType);
		int size = bdbWorkflowConfigService.getBatchSize();
		if (assetPaths.size() < size) {
			addJob(assetPaths, jobManager, jobType);
		} else {
			List<List<String>> lists = getSubLists(assetPaths, size);
			for (List<String> list : lists) {
				addJob(list, jobManager, jobType);
			}
		}
		LOGGER.debug("Exit assignJobInBatches method of WorkflowHelperServiceImpl");
	}

	/**
	 * Should process.
	 *
	 * @param path the path
	 * @param basePath the base path
	 * @return true, if successful
	 */
	@Override
	public boolean canProcessPath(String path, String basePath) {
		return StringUtils.containsIgnoreCase(path, basePath);
	}

	/**
	 * Replicate resource to publish.
	 *
	 * @param session            the session
	 * @param damPathToReplicate the dam path to replicate
	 * @throws ReplicationException the replication exception
	 */
	@Override
	public void replicateResourceToPublish(Session session, String damPathToReplicate) throws ReplicationException {
		LOGGER.debug("Entry replicateResourceToPublish method of WorkflowHelperServiceImpl");
		replicator.replicate(session, ReplicationActionType.ACTIVATE, damPathToReplicate);
		LOGGER.debug("Exit replicateResourceToPublish method of WorkflowHelperServiceImpl");
	}
	
	
	/**
	 * Move asset from temp asset folder.
	 *
	 * @param currentPath the current path
	 * @param serviceResolver the service resolver
	 * @param session the session
	 * @throws RepositoryException the repository exception
	 */
	@Override
	public void moveAssetFromTempAssetFolder(String currentPath, ResourceResolver serviceResolver, Session session)
			throws RepositoryException {
		LOGGER.debug("Entry moveAssetFromTempAssetFolder method of WorkflowHelperServiceImpl");
		String absFinalPath = "";
		if (!StringUtils.containsIgnoreCase(currentPath, bdbWorkflowConfigService.getProcessedAssetBasePath())) {
			if(currentPath.startsWith(bdbWorkflowConfigService.getProductImageBasePath())) {
				absFinalPath = currentPath.replace(bdbWorkflowConfigService.getProductImageBasePath(),
						bdbWorkflowConfigService.getProcessedAssetBasePath());
			} else {
				absFinalPath = currentPath.replace(bdbWorkflowConfigService.getTempAssetBasePath(),
						bdbWorkflowConfigService.getProcessedAssetBasePath());
			}
			createFolderStructure(absFinalPath, session, serviceResolver);
			com.adobe.granite.asset.api.AssetManager moveAssetMan = serviceResolver
					.adaptTo(com.adobe.granite.asset.api.AssetManager.class);
			LOGGER.debug("moving resource from : {} To : {}", currentPath, absFinalPath);
			if(session.nodeExists(absFinalPath)) {
				moveAssetMan.removeAsset(absFinalPath);
			}
			if(session.nodeExists(currentPath)) {
				moveAssetMan.moveAsset(currentPath, absFinalPath);
			}
			session.save();
		}
		LOGGER.debug("Exit moveAssetFromTempAssetFolder method of WorkflowHelperServiceImpl");
	}
	
	/**
	 * Gets the asset type.
	 *
	 * @param payload the payload
	 * @return the asset type
	 */
	@Override
	public String getAssetType(String payload) {
		if (StringUtils.contains(payload, "/pdf/")) {
			return CommonConstants.TYPE_PDF;
		}else if (StringUtils.contains(payload, bdbApiEndpointService.getScientificResourceFolder())) {
			return CommonConstants.TYPE_VIDEO_THUMBNAIL;
		} else {
			return CommonConstants.TYPE_IMAGE;
		}
		
	}
	
	/**
	 * Creates the asset.
	 *
	 * @param assetMan the asset man
	 * @param assetInputStream the asset input stream
	 * @param destPath the dest path
	 * @param assetType the asset type
	 * @param serviceResolver the service resolver
	 * @param session the session
	 * @return the asset
	 * @throws RepositoryException the repository exception
	 */
	@Override
	public Asset createAsset(AssetManager assetMan, InputStream assetInputStream, String destPath, String assetType,
			ResourceResolver serviceResolver, Session session) throws RepositoryException {
		LOGGER.debug("Entry CreateAsset method of WorkflowHelperServiceImpl");
		ValueFactory factory;
		Asset asset = null;
		createFolderStructure(destPath, session, serviceResolver);
		if (session.nodeExists(destPath)) {
			deleteAssetFromDamFolder(destPath, serviceResolver, session);
		}
		factory = session.getValueFactory();
		Binary assetBinary = factory.createBinary(assetInputStream);
		asset = assetMan.createOrUpdateAsset(destPath, assetBinary, assetType, true);
		LOGGER.debug("Exit CreateAsset method of WorkflowHelperServiceImpl");
		return asset;
	}
	
	/**
	 * Delete asset from dam folder.
	 *
	 * @param assetPath the asset path
	 * @param serviceResolver the service resolver
	 * @param session the session
	 * @throws RepositoryException the repository exception
	 */
	@Override
	public void deleteAssetFromDamFolder(String assetPath, ResourceResolver serviceResolver, Session session)
			throws RepositoryException {
		LOGGER.debug("Entry deleteAssetFromTempFolder method of WorkflowHelperServiceImpl");
		com.adobe.granite.asset.api.AssetManager removeAssetMan = serviceResolver
				.adaptTo(com.adobe.granite.asset.api.AssetManager.class);
		LOGGER.debug("Removing resource : {}", assetPath);
		removeAssetMan.removeAsset(assetPath);
		session.save();
		LOGGER.debug("Exit deleteAssetFromTempFolder method of WorkflowHelperServiceImpl");
	}
}