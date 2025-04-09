package com.bdb.aem.core.services;

import org.apache.sling.api.resource.Resource;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;

/**
 * This service is used to create image metadata node under /var/bdb/images/unprocessed-assets/{Processing-date}/{Processing-time}/{record}/.. path
 * for images that are not pushed from source/Half pipe.
 */
public interface CreateProductImagesNodeService {
    /**
     * This method is used to create image metadata node under /var/bdb/images/unprocessed-assets/{Processing-date}/{Processing-time}/{record}/.. path
     * for images that are not pushed from source/Half pipe based on Excel uploaded under file path.
     *
     * @param resource
     * @param session
     * @throws IOException
     * @throws RepositoryException
     */
    void createProductImageNodes(Resource resource, Session session) throws IOException, RepositoryException;
}