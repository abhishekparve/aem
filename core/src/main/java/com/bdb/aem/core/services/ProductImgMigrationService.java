package com.bdb.aem.core.services;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;

/**
 * The Interface ProductImgMigrationService.
 */
public interface ProductImgMigrationService {

	/**
	 * Process product image.
	 *
	 * @param assetPath the asset path
	 * @param resourceResolver the resource resolver
	 * @param session the session
	 * @throws RepositoryException the repository exception
	 */
	public void processProductImage(String assetPath, ResourceResolver resourceResolver, Session session)
			throws RepositoryException;

}
