package com.bdb.aem.core.services;

import java.io.InputStream;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.event.jobs.JobManager;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.day.cq.replication.ReplicationException;
import com.day.cq.search.Query;

/**
 * The Interface WorkflowHelperService.
 */
public interface WorkflowHelperService {

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
	public Node createNode(String path, ResourceResolver resourceResolver, Session session, String nodeType)
			throws RepositoryException;

	/**
	 * Gets the resources.
	 *
	 * @param payloadPath     the payload path
	 * @param serviceResolver the service resolver
	 * @param session         the session
	 * @return the resources
	 */
	List<String> getPDFResources(String payloadPath, ResourceResolver serviceResolver, Session session);

	/**
	 * Gets the resources from query.
	 *
	 * @param query the query
	 * @return the resources from query
	 */
	List<String> getResourcesFromQuery(Query query);

	/**
	 * Gets the sub lists.
	 *
	 * @param list the list
	 * @param L    the l
	 * @return the sub lists
	 */
	List<List<String>> getSubLists(List<String> list, int length);

	/**
	 * Adds the job.
	 *
	 * @param assetPaths the asset paths
	 * @param jobType    the job type
	 */
	void addJob(List<String> assetPaths, JobManager jobManager, String jobType);

	/**
	 * Assign job in batches.
	 *
	 * @param assetPaths the asset paths
	 */
	void assignJobInBatches(List<String> assetPaths, JobManager jobManager, String jobType);

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
	void createNode(String path, ResourceResolver resourceResolver, Session session, String nodeType,
			String intermediateNodeType) throws RepositoryException;

	/**
	 * Creates the folder structure.
	 *
	 * @param destPath        the dest path
	 * @param session         the session
	 * @param serviceResolver the service resolver
	 * @throws RepositoryException the repository exception
	 */
	void createFolderStructure(String destPath, Session session, ResourceResolver serviceResolver)
			throws RepositoryException;

	/**
	 * Replicate resource to publish.
	 *
	 * @param session            the session
	 * @param damPathToReplicate the dam path to replicate
	 * @throws ReplicationException the replication exception
	 */
	void replicateResourceToPublish(Session session, String damPathToReplicate) throws ReplicationException;

	/**
	 * Gets the image resources.
	 *
	 * @param payloadPath the payload path
	 * @param serviceResolver the service resolver
	 * @param session the session
	 * @return the image resources
	 */
	List<String> getImageResources(String payloadPath, ResourceResolver serviceResolver, Session session);

	/**
	 * Gets the list of image assets.
	 *
	 * @param path the path
	 * @param serviceResolver the service resolver
	 * @param session the session
	 * @return the list of image assets
	 */
	List<String> getListOfImageAssets(String path, ResourceResolver serviceResolver, Session session);

	/**
	 * Gets the list of assets.
	 *
	 * @param path            the path
	 * @param serviceResolver the service resolver
	 * @param session         the session
	 * @return the list of assets
	 */
	List<String> getListOfPDFAssets(String path, ResourceResolver serviceResolver, Session session);

	/**
	 * Should process.
	 *
	 * @param path the path
	 * @return true, if successful
	 */
	boolean canProcessPath(String path, String basePath);
	
	/**
	 * Move asset from temp asset folder.
	 *
	 * @param currentPath the current path
	 * @param newPath the new path
	 * @param serviceResolver the service resolver
	 * @param session the session
	 * @throws RepositoryException the repository exception
	 */
	void moveAssetFromTempAssetFolder(String currentPath, ResourceResolver serviceResolver, Session session) throws RepositoryException;

	
	/**
	 * Adds the job for replication.
	 *
	 * @param assetPaths the asset paths
	 * @param jobManager the job manager
	 * @param jobType the job type
	 * @param batchSize the batch size
	 * @param mode the mode
	 */
	void addJobForReplication(List<String> assetPaths, JobManager jobManager, String jobType, int batchSize,
			String mode);

	/**
	 * Adds the job for indexing.
	 *
	 * @param assetPaths the asset paths
	 * @param jobManager the job manager
	 * @param jobType the job type
	 * @param assetType the asset type
	 */
	void addJobForIndexing(List<String> assetPaths, JobManager jobManager, String jobType, String assetType);
	
	/**
	 * Adds the job for indexing.
	 *
	 * @param assetPaths the asset paths
	 * @param jobManager the job manager
	 * @param jobType the job type
	 * @param assetType the asset type
	 */
	void addJobForIndexingOnDemandTDS(List<String> assetPaths, JobManager jobManager, String jobType, String assetType);

	/**
	 * Adds the job for AkamaiPurge.
	 *
	 * @param assetPaths the asset paths
	 * @param jobManager the job manager
	 * @param jobType the job type
	 */
	void addJobForAkamaiPurge(List<String> assetPaths, JobManager jobManager, String jobType);
	/**
	 * Gets the asset type.
	 *
	 * @param payload the payload
	 * @return the asset type
	 */
	String getAssetType(String payload);

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
	Asset createAsset(AssetManager assetMan, InputStream assetInputStream, String destPath, String assetType,
			ResourceResolver serviceResolver, Session session) throws RepositoryException;

	/**
	 * Delete asset from dam folder.
	 *
	 * @param assetPath the asset path
	 * @param serviceResolver the service resolver
	 * @param session the session
	 * @throws RepositoryException the repository exception
	 */
	void deleteAssetFromDamFolder(String assetPath, ResourceResolver serviceResolver, Session session)
			throws RepositoryException;
}
