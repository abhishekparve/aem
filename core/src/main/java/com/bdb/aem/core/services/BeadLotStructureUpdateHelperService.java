package com.bdb.aem.core.services;

import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;

import com.bdb.aem.core.bean.BeadlotJsonBean;
import com.google.gson.JsonObject;

/**
 * The Interface BeadLotStructureUpdateHelperService.
 */
public interface BeadLotStructureUpdateHelperService {

	/**
	 * Creates the node.
	 *
	 * @param path             the path
	 * @param resourceResolver the resource resolver
	 * @param session          the session
	 * @param nodeType         the node type
	 * @return the node
	 * @throws RepositoryException the repository exception
	 */
	public Node createNode(String path, ResourceResolver resourceResolver, Session session, String nodeType)
			throws RepositoryException;

	/**
	 * Gets the beadlot json as list.
	 *
	 * @param jsonObject the json object
	 * @return the beadlot json as list
	 */
	public List<BeadlotJsonBean> getBeadlotJsonAsList(JsonObject jsonObject);

	/**
	 * Removes the first last quotes.
	 *
	 * @param input the input
	 * @return the string
	 */
	public String removeFirstLastQuotes(String input);
}
