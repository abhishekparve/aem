package com.bdb.aem.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.servlets.solr.FetchingDataFromSolr;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.ReplicationOptions;
import com.day.cq.replication.Replicator;

/**
 * The Class NodeStructureUpdateServlet.
 */
@Component(service = Servlet.class, immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE, 
property = {Constants.SERVICE_DESCRIPTION + "=" + "NodeStructureUpdateServlet",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + NodeStructureUpdateServlet.RESOURCE_TYPE
})
public class NodeStructureUpdateServlet extends BaseServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "bdb/update-node-structure";

	/** The Constant LOGGER. */
	public static final Logger LOGGER = LoggerFactory.getLogger(NodeStructureUpdateServlet.class);

	/** The batch. */
	static int batch;

	static Long batchCount;


	@Reference
	transient Replicator replicator;

	/** The resource resolver factory. */
	@Reference
	transient ResourceResolverFactory resourceResolverFactory;


	transient List<String> replicationList;

	static ReplicationOptions options = new ReplicationOptions();

	/**
	 * Do post.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		doGet(request, response);
	}

	/**
	 * Do post.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		batchCount = 0L;
		batch = 0;
		replicationList =  new ArrayList();
		PrintWriter writer = null;
		ResourceResolver resourceResolver = null;
		Session session = null;
		String responseJson = StringUtils.EMPTY;
		try {
			Map<String, String> queryParam = CommonHelper.getQueryParamsFromRequest(request);
			long startTime = System.currentTimeMillis();
			LOGGER.debug("Servlet starting time {}", startTime);
			resourceResolver = CommonHelper.getServiceResolver(resourceResolverFactory);
			session = resourceResolver.adaptTo(Session.class);
			responseJson = updateNodeType(session, queryParam);
			long endTime = System.currentTimeMillis();
			LOGGER.debug("Servlet End time - {}", endTime);
			String totalTime = totalTimeInMinutesSeconds(endTime - startTime);
			LOGGER.debug("Total time taken for Servlet - {}", totalTime);
			responseJson = responseJson + ", Total time taken : " + totalTime;
		} catch (RepositoryException | LoginException | ReplicationException e) {
			LOGGER.error("Exception {}", e.getMessage());
			responseJson = "Exception occured while changing type - " + e.getMessage();
		} finally {
			CommonHelper.closeResourceResolver(resourceResolver);
			CommonHelper.closeSession(session);
			response.setContentType(CommonConstants.APPLICATION_JSON);
			response.setCharacterEncoding("UTF-8");
			writer = response.getWriter();
			if (null != writer) {
				writer.print(responseJson);
				writer.flush();
				writer.close();
			}
		}
	}

	/**
	 * Update node type.
	 *
	 * @param session    the session
	 * @param queryParam the query param
	 * @return the string
	 * @throws RepositoryException the repository exception
	 */
	private String updateNodeType(Session session, Map<String, String> queryParam) throws RepositoryException, ReplicationException {
		String response;
		String path = queryParam.get("path");
		String currentNodeType = queryParam.get("currentNodeType");
		String finalNodeType = queryParam.get("finalNodeType");
		String doReplicate = queryParam.get("doReplicate");
		if (null == doReplicate) {
			doReplicate = StringUtils.EMPTY;
		}

		if (StringUtils.isNotBlank(path) && StringUtils.isNotBlank(currentNodeType)
				&& StringUtils.isNotBlank(finalNodeType)) {
			recursiveCall(session, path, currentNodeType, finalNodeType, doReplicate);
			response = "Success";
		} else {
			response = "Please send all the query params";
		}

		if(doReplicate.equalsIgnoreCase("true")) {
			replicator.replicate(session, ReplicationActionType.ACTIVATE, replicationList.toArray(new String[0]), options);
		}else {
			session.save();
		}
		return response;
	}

	/**
	 * Recursive call.
	 *
	 * @param session         the session
	 * @param path            the path
	 * @param currentNodeType the current node type
	 * @param finalNodeType   the final node type
	 * @throws RepositoryException the repository exception
	 */
	public void recursiveCall(Session session, String path, String currentNodeType, String finalNodeType, String doReplicate)
			throws RepositoryException, ReplicationException {
		Node tempNode;
		Node rootNode = session.getNode(path);
		NodeIterator nodes = rootNode.getNodes();
		while (nodes.hasNext()) {
			tempNode = nodes.nextNode();
			if (tempNode.hasNodes()) {
				recursiveCall(session, tempNode.getPath(), currentNodeType, finalNodeType, doReplicate);
			} else {
				if(doReplicate.equalsIgnoreCase("true")) {
					replicate(session, tempNode);
				}else {
					changeType(session, currentNodeType, finalNodeType, tempNode);
				}
			}
		}
		if(doReplicate.equalsIgnoreCase("true")) {
			replicate(session, rootNode);
		}else {
			changeType(session, currentNodeType, finalNodeType, rootNode);
		}
	}

	/**
	 * Change type.
	 *
	 * @param session         the session
	 * @param currentNodeType the current node type
	 * @param finalNodeType   the final node type
	 * @param tempNode        the temp node
	 * @throws RepositoryException the repository exception
	 */
	private static void changeType(Session session, String currentNodeType, String finalNodeType, Node tempNode)
			throws RepositoryException {
		if (tempNode.isNodeType(currentNodeType)) {
			tempNode.setPrimaryType(finalNodeType);
			batch++;

			if (batch == 999) {
				session.save();
				batchCount++;
				LOGGER.debug("Saved {} batch of thousand ******* ", batchCount);
				batch = 0;
			}
		}
	}

	public void replicate(Session session, Node tempNode)
			throws RepositoryException, ReplicationException {
		batch++;
		replicationList.add(tempNode.getPath());
		if (batch == 999) {
			replicator.replicate(session, ReplicationActionType.ACTIVATE, replicationList.toArray(new String[0]), options);
			batch = 0;
			replicationList = new ArrayList();
		}
	}

	/**
	 * Total time in minutes seconds.
	 *
	 * @param milliseconds the milliseconds
	 * @return the string
	 */
	private static String totalTimeInMinutesSeconds(long milliseconds) {
		long minutes = (milliseconds / 1000) / 60;
		long seconds = (milliseconds / 1000) % 60;
		return minutes + " minutes and " + seconds + " Seconds";
	}
}