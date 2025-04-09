package com.bdb.aem.core.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ReplicationService;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.ReplicationOptions;
import com.day.cq.replication.Replicator;
import com.day.cq.search.QueryBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * The Class ReplicationServiceImpl.
 */
@Component(service = ReplicationService.class, immediate = true)
public class ReplicationServiceImpl implements ReplicationService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ReplicationServiceImpl.class);

	/** The query builder. */
	@Reference
	private QueryBuilder queryBuilder;

	/** The replicator. */
	@Reference
	Replicator replicator;

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
	@Override
	public String replicate(JsonObject jsonObject, ResourceResolver resourceResolver, Session session)
			throws RepositoryException, ReplicationException {
		LOGGER.debug("Entry replicate method");
		List<String> pathsToReplicate = getListOfPathFromJson(jsonObject);
		process(pathsToReplicate, session);
		LOGGER.debug("Exit replicate method");
		return "Successful";
	}

	/**
	 * Gets the list of path from json.
	 *
	 * @param jsonObject the json object
	 * @return the list of path from json
	 */
	public List<String> getListOfPathFromJson(JsonObject jsonObject) {
		LOGGER.debug("Entry getListOfPathFromJson method");
		JsonArray hitsArray = jsonObject.getAsJsonArray("hits");
		List<String> listOfPath = new ArrayList<>();
		for (JsonElement element : hitsArray) {
			JsonObject asJsonObject = element.getAsJsonObject();
			listOfPath.add(removeQoutes(asJsonObject.get("path").toString()));
		}
		LOGGER.debug("Exit getListOfPathFromJson method");
		return listOfPath;
	}

	/**
	 * Removes the qoutes.
	 *
	 * @param path the path
	 * @return the string
	 */
	public static String removeQoutes(String path) {
		return path.substring(1, path.length() - 1);
	}

	/**
	 * Process.
	 *
	 * @param resourcesFromQuery the resources from query
	 * @param session            the session
	 * @throws ReplicationException the replication exception
	 */
	private void process(List<String> resourcesFromQuery, Session session) throws ReplicationException {
		LOGGER.debug("Entry process method");
		ReplicationOptions options = new ReplicationOptions();
//		options.setSynchronous(true);
		List<List<String>> subLists = getSubLists(resourcesFromQuery, 100);
		int i = 1;
		for (List<String> paths : subLists) {
			long startTime = System.currentTimeMillis();
			LOGGER.debug("Batch {} starting time {}", i, startTime);
			replicate(session, paths.toArray(new String[0]), options);
			long endTime = System.currentTimeMillis();
			LOGGER.debug("Batch End time - {}", endTime);
			LOGGER.debug("Total time taken for Batch - {} : {}", i, endTime - startTime);
			i++;
		}
		LOGGER.debug("Exit process method");
	}

	/**
	 * Gets the sub lists.
	 *
	 * @param list   the list
	 * @param length the length
	 * @return the sub lists
	 */
	public List<List<String>> getSubLists(List<String> list, final int length) {
		LOGGER.debug("Entry getSubLists method");
		List<List<String>> parts = new ArrayList<>();
		final int N = list.size();
		for (int i = 0; i < N; i += length) {
			parts.add(new ArrayList<>(list.subList(i, Math.min(N, i + length))));
		}
		LOGGER.debug("Exit getSubLists method");
		return parts;
	}

	/**
	 * Replicate.
	 *
	 * @param session the session
	 * @param paths   the paths
	 * @param options the options
	 * @throws ReplicationException the replication exception
	 */
	private void replicate(Session session, String[] paths, ReplicationOptions options) throws ReplicationException {
		LOGGER.debug("Entry replicateResourceToPublish method");
		replicator.replicate(session, ReplicationActionType.ACTIVATE, paths, options);
		LOGGER.debug("Exit replicateResourceToPublish method");
	}
}