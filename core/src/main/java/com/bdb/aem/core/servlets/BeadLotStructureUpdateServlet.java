package com.bdb.aem.core.servlets;

import com.bdb.aem.core.services.BeadLotStructureUpdateService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.replication.ReplicationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
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

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

/**
 * The Class BeadLotStructureUpdateServlet.
 */
@Component(immediate = true, service = Servlet.class, configurationPolicy = ConfigurationPolicy.REQUIRE, property = {
		ServletResolverConstants.SLING_SERVLET_PATHS + CommonConstants.EQUAL + "/bin/beadlotstructureupdateservlet",
		ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_POST,
		ServletResolverConstants.SLING_SERVLET_EXTENSIONS + CommonConstants.EQUAL + CommonConstants.JSON })
public class BeadLotStructureUpdateServlet extends BaseServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "bdb/beadlots-structure-update";

	/** The Constant LOGGER. */
	public static final Logger LOGGER = LoggerFactory.getLogger(BeadLotStructureUpdateServlet.class);

	/** The bead lot structure update service. */
	@Reference
	private transient BeadLotStructureUpdateService beadLotStructureUpdateService;

	/**
	 * Do post.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		PrintWriter writer = null;
		ResourceResolver resourceResolver = null;
		Session session = null;
		String responseJson = StringUtils.EMPTY;
		try {
			JsonObject jsonObject = getRequestObject(request);
			resourceResolver = request.getResourceResolver();
			session = resourceResolver.adaptTo(Session.class);
			responseJson = beadLotStructureUpdateService.createBeadlotStructure(resourceResolver, jsonObject, session);
			session.save();
		} catch (JsonSyntaxException e) {
			LOGGER.error("Exception {}", e.getMessage());
			responseJson = beadLotStructureUpdateService.createResponse("Error",
							"Beadlot meta-data error occured while processing - Please send a valid json body",
							new HashSet<>());
		} catch (JsonProcessingException | RepositoryException | ReplicationException e) {
			LOGGER.error("Exception {}", e.getMessage());
			responseJson = beadLotStructureUpdateService.createResponse("Error",
							"Beadlot meta-data error occured while processing - " + e.getMessage(),
							new HashSet<>());
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