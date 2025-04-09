package com.bdb.aem.core.services;

import java.io.IOException;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

/**
 * The Interface SolrSearchService.
 */
public interface UpdateProductAnnouncementService {

    /**
	 * Index all Scientific Resource Thumbnail Images to solr.
     * @param resourceResolver 
	 *
	 * @param resolver the resolver
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws SAXException   the SAX exception
	 * @throws TikaException  the tika exception
	 * @throws LoginException the login exception
	 */
    public void updateProductAnnouncement(String filePath, ResourceResolver resourceResolver);
    
}