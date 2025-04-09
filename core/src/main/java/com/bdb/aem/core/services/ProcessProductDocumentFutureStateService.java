package com.bdb.aem.core.services;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.replication.ReplicationException;
import com.day.cq.tagging.InvalidTagFormatException;

/**
 * The Interface ProcessProductDocumentFutureStateService.
 */
public interface ProcessProductDocumentFutureStateService {

	/**
	 * Process documents.
	 *
	 * @param payloadPath     the payload path
	 * @param serviceResolver the service resolver
	 * @param session         the session
	 * @throws RepositoryException       the repository exception
	 * @throws InvalidTagFormatException the invalid tag format exception
	 * @throws ReplicationException the replication exception
	 */
	public void processDocuments(String payloadPath, ResourceResolver serviceResolver, Session session)
			throws RepositoryException, InvalidTagFormatException, ReplicationException;
}
