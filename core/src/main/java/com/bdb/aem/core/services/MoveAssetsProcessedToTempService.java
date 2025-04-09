package com.bdb.aem.core.services;

import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;

/**
 * The Interface MoveAssetsProcessedToTempService.
 */
public interface MoveAssetsProcessedToTempService {

	/**
	 * Move assets.
	 *
	 * @param session          the session
	 * @param resourceResolver the resource resolver
	 * @param queryParam       the query param
	 * @return the string
	 * @throws RepositoryException the repository exception
	 */
	String moveAssets(Session session, ResourceResolver resourceResolver, Map<String, String> queryParam)
			throws RepositoryException;
}
