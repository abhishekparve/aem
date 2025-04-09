package com.bdb.aem.core.services;

import java.io.IOException;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;

/**
 * The Interface CreateExcelNodeService.
 */
public interface CreateExcelNodeService {

	/**
	 * Process doc metadata excel.
	 *
	 * @param session          the session
	 * @param resourceResolver the resource resolver
	 * @param queryParam       the query param
	 * @return the string
	 * @throws RepositoryException the repository exception
	 * @throws IOException 
	 */
	public String processDocMetadataExcel(Session session, ResourceResolver resourceResolver,
			Map<String, String> queryParam) throws RepositoryException, IOException;

}
