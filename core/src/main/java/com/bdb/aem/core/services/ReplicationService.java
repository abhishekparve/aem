package com.bdb.aem.core.services;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.replication.ReplicationException;
import com.google.gson.JsonObject;

/**
 * The Interface ReplicationService.
 */
public interface ReplicationService {

	/**
	 * Replicate.
	 *
	 * @param jsonObject       the json object
	 * @param resourceResolver the resource resolver
	 * @param session          the session
	 * @return the string
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException the replication exception
	 */
	public String replicate(JsonObject jsonObject, ResourceResolver resourceResolver, Session session)
			throws RepositoryException, ReplicationException;
}
