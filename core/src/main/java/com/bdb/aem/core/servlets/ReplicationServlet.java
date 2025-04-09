package com.bdb.aem.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ReplicationService;
import com.bdb.aem.core.servlets.solr.FetchingDataFromSolr;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.replication.ReplicationException;
import com.google.gson.JsonObject;

/**
 * The Class BeadLotStructureUpdateServlet.
 */
@Component(immediate = true, service = Servlet.class, configurationPolicy = ConfigurationPolicy.REQUIRE, property = {
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + ReplicationServlet.RESOURCE_TYPE,
		ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_GET,
		ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_POST})
public class ReplicationServlet extends BaseServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant LOGGER. */
	public static final Logger LOGGER = LoggerFactory.getLogger(ReplicationServlet.class);

	/** The replication service. */
	@Reference
	private transient ReplicationService replicationService;
	
	public static final String RESOURCE_TYPE = "bdb/replicate";

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
		PrintWriter writer = null;
		ResourceResolver resourceResolver = null;
		Session session = null;
		String responseJson = StringUtils.EMPTY;
		try {
			long startTime = System.currentTimeMillis();
			LOGGER.debug("Servlet starting time {}", startTime);
			JsonObject jsonObject = getRequestObject(request);
			resourceResolver = request.getResourceResolver();
			session = resourceResolver.adaptTo(Session.class);
			responseJson = replicationService.replicate(jsonObject, resourceResolver, session);
			long endTime = System.currentTimeMillis();
			LOGGER.debug("Servlet End time - {}", endTime);
			LOGGER.debug("Total time taken for Servlet - {}", endTime - startTime);
		} catch (RepositoryException | ReplicationException e) {
			LOGGER.error("Exception {}", e.getMessage());
			responseJson = "Exception occured while replicating - " + e.getMessage();
		} finally {
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
}