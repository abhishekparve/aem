package com.bdb.aem.core.services;

import java.util.Set;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;

import com.bdb.aem.core.bean.ErrorSKU;
import com.day.cq.replication.ReplicationException;
import com.google.gson.JsonObject;

/**
 * The Interface BeadLotStructureUpdateService.
 */
public interface BeadLotStructureUpdateService {

	/**
	 * Creates the beadlot structure.
	 *
	 * @param resourceResolver the resource resolver
	 * @param jsonObject the json object
	 * @param session the session
	 * @return the string
	 * @throws RepositoryException the repository exception
	 * @throws ReplicationException the replication exception
	 */
	public String createBeadlotStructure(ResourceResolver resourceResolver, JsonObject jsonObject, Session session)
			throws RepositoryException, ReplicationException;

	/**
	 * Creates the response.
	 *
	 * @param status the status
	 * @param statusMessage the status message
	 * @param errorSKUs the error SK us
	 * @return the string
	 */
	String createResponse(String status, String statusMessage, Set<ErrorSKU> errorSKUs);
}
