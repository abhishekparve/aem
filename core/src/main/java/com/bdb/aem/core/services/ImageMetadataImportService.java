package com.bdb.aem.core.services;

import com.day.cq.tagging.InvalidTagFormatException;
import org.apache.sling.api.resource.ResourceResolver;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * This service is used to add metadata to product images moved to product dam base folder
 */

public interface ImageMetadataImportService {
    /**
     * Process product image.
     *
     * @param assetPath        the asset path
     * @param resourceResolver the resource resolver
     * @param session          the session
     * @throws RepositoryException the repository exception
     */
    void processProductImage(String assetPath, ResourceResolver resourceResolver, Session session)
            throws RepositoryException, InvalidTagFormatException;
}
