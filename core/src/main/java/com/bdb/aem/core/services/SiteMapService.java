package com.bdb.aem.core.services;

import javax.jcr.RepositoryException;
import javax.xml.stream.XMLStreamException;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;

public interface SiteMapService {
    /**
     * Creates the SiteMap for given content paths
     *
     * @param paths the content paths for SiteMaps
     * @param resolver the resource resolver object
     */
    void generateSiteMap(String[] paths, ResourceResolver resolver,final int P_DEFAULT_LIMIT) throws LoginException, RepositoryException, XMLStreamException;

	void generateMasterIndexSiteMap(String[] countryPaths, ResourceResolver resourceResolver) throws XMLStreamException, RepositoryException;
}
