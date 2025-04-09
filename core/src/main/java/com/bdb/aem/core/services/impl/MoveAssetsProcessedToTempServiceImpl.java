package com.bdb.aem.core.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.MoveAssetsProcessedToTempService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.search.Query;

/**
 * The Class MoveAssetsProcessedToTempServiceImpl.
 */
@Component(service = MoveAssetsProcessedToTempService.class, immediate = true)
public class MoveAssetsProcessedToTempServiceImpl implements MoveAssetsProcessedToTempService {

	/** The workflow helper service. */
	@Reference
	WorkflowHelperService workflowHelperService;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MoveAssetsProcessedToTempServiceImpl.class);

	/**
	 * Move assets.
	 *
	 * @param session          the session
	 * @param resourceResolver the resource resolver
	 * @param queryParam       the query param
	 * @return the string
	 * @throws RepositoryException the repository exception
	 */
	@Override
	public String moveAssets(Session session, ResourceResolver resourceResolver, Map<String, String> queryParam)
			throws RepositoryException {
		LOGGER.debug("Entry moveAssets method of MoveAssetsProcessedToTempServiceImpl");
		String response;
		String sourcePath = queryParam.get("source");
		String targetPath = queryParam.get("target");
		int batchSize = CommonHelper.stringToInteger(queryParam.get("batchSize"));
		if (batchSize == 0) {
			LOGGER.debug("Setting default batch size of 100");
			batchSize = 100;
		}

		if (StringUtils.isNotBlank(sourcePath) && StringUtils.isNotBlank(targetPath)
				&& session.nodeExists(sourcePath)) {
			getAllResources(sourcePath, targetPath, resourceResolver, session, batchSize);
			LOGGER.debug("Completed successfully MoveAssetsProcessedToTempServiceImpl");
			response = "Success";
		} else {
			response = "Please send all the query params and check nodes persists in system";
		}
		LOGGER.debug("Exit moveAssets method of MoveAssetsProcessedToTempServiceImpl");
		return response;
	}

	/**
	 * Gets the all resources.
	 *
	 * @param sourcePath       the source path
	 * @param targetPath       the target path
	 * @param resourceResolver the resource resolver
	 * @param session          the session
	 * @return the all resources
	 * @throws RepositoryException the repository exception
	 */
	public void getAllResources(String sourcePath, String targetPath, ResourceResolver resourceResolver,
			Session session, int batchSize) throws RepositoryException {
		LOGGER.debug("Entry getAllResources method of MoveAssetsProcessedToTempServiceImpl");
		Query query = CommonHelper.getQuery(resourceResolver,
				getQueryMapForAsset(sourcePath, DamConstants.NT_DAM_ASSET));
		List<String> paths = workflowHelperService.getResourcesFromQuery(query);
		processResources(sourcePath, targetPath, paths, resourceResolver, session, batchSize);
		LOGGER.debug("Exit getAllResources method of MoveAssetsProcessedToTempServiceImpl");
	}

	/**
	 * Process resources.
	 *
	 * @param sourcePath       the source path
	 * @param targetPath       the target path
	 * @param paths            the paths
	 * @param resourceResolver the resource resolver
	 * @param session          the session
	 * @throws RepositoryException the repository exception
	 */
	public void processResources(String sourcePath, String targetPath, List<String> paths,
			ResourceResolver resourceResolver, Session session, int batchSize) throws RepositoryException {
		LOGGER.debug("Entry processResources method of MoveAssetsProcessedToTempServiceImpl");
		int i = 0;
		if (!session.nodeExists(targetPath)) {
			workflowHelperService.createNode(targetPath, resourceResolver, session,
					JcrResourceConstants.NT_SLING_FOLDER, JcrResourceConstants.NT_SLING_FOLDER);
			session.save();
		}
		for (String path : paths) {
			moveAssetToTempAssetFolder(path, sourcePath, targetPath, resourceResolver, session);
			i++;
			if (i == batchSize) {
				session.save();
				i = 0;
			}
		}
		session.save();
		LOGGER.debug("Exit processResources method of MoveAssetsProcessedToTempServiceImpl");
	}

	/**
	 * Move asset to temp asset folder.
	 *
	 * @param path             the path
	 * @param sourcePath       the source path
	 * @param targetPath       the target path
	 * @param resourceResolver the resource resolver
	 * @param session          the session
	 * @throws RepositoryException the repository exception
	 */
	public void moveAssetToTempAssetFolder(String path, String sourcePath, String targetPath,
			ResourceResolver resourceResolver, Session session) throws RepositoryException {
		String absFinalPath = path.replace(sourcePath, targetPath);
		if (!StringUtils.equals(path, absFinalPath) && !session.nodeExists(absFinalPath)) {
			com.adobe.granite.asset.api.AssetManager moveAssetMan = resourceResolver
					.adaptTo(com.adobe.granite.asset.api.AssetManager.class);
			LOGGER.debug("moving resource from : {} To : {}", path, absFinalPath);
			moveAssetMan.moveAsset(path, absFinalPath);
		}
	}

	/**
	 * Gets the query map for asset.
	 *
	 * @param pathValue the path value
	 * @param typeValue the type value
	 * @return the query map for asset
	 */
	private Map<String, String> getQueryMapForAsset(String pathValue, String typeValue) {
		Map<String, String> map = new HashMap<>();
		LOGGER.debug("Entry getQueryMapForAsset method of MoveAssetsProcessedToTempServiceImpl");
		map.put(CommonConstants.PATH, pathValue);
		map.put("path.flat", "true");
		map.put(CommonConstants.TYPE, typeValue);
		map.put(CommonConstants.LIMIT, CommonConstants.LIMIT_MINUS_ONE);
		LOGGER.debug("Exit getQueryMapForAsset method of MoveAssetsProcessedToTempServiceImpl");
		return map;
	}
}