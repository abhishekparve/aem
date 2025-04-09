package com.bdb.aem.core.servlets;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.UpdateProductSchemaService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;

import java.io.IOException;
import java.io.PrintWriter;


/**
 * The Class UpdateProductSchemaServlet.
 */
@Component(immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE, service = Servlet.class, property = {
		ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + UpdateProductSchemaServlet.RESOURCE_TYPE,
		ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_GET,
		ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_POST})
public class UpdateProductSchemaServlet extends SlingAllMethodsServlet  {
	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "bdb/update-product-schema";

	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateProductSchemaServlet.class);

	/**
	 * Get Method.
	 *
	 */

	/**
	 * The resource resolver factory.
	 */
	@Reference
	private transient ResourceResolverFactory resourceResolverFactory;

	/** The update product schema service. */
	@Reference
	private transient UpdateProductSchemaService updateProductSchemaService;
	
	/** The replicator. */
	@Reference
	transient  Replicator replicator;
	
	
	/** The externalizer service. */
	@Reference
	private transient ExternalizerService externalizerService;
	
	@Reference
	transient SolrSearchService solrSearchService;

	/**
	 * Do get.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		ResourceResolver resourceResolver = null;
		JsonObject productJson = new JsonObject();

		try {
			resourceResolver = CommonHelper.getServiceResolverReplication(resourceResolverFactory);
			Resource marketingResource = updateProductSchemaService.getMarketingResource(request, resourceResolver, solrSearchService);
			productJson = updateProductSchemaService.getProductAnnouncement(marketingResource, request, productJson,resourceResolver, externalizerService, null, solrSearchService);
			updateProductSchemaService.setProductAnnounce(request, resourceResolver, marketingResource);
			if(null != request.getParameter("activate") && null != marketingResource) {
				Session session = resourceResolver.adaptTo(Session.class);
				Node marketingNode = marketingResource.adaptTo(Node.class);
				replicator.replicate(session,ReplicationActionType.ACTIVATE,marketingNode.getPath());
				Resource marketingParentResource = marketingResource.getParent();
                Resource productResource = marketingParentResource.getParent();
                Resource hpResource = productResource.getChild("hp");
                Node hpNode = hpResource.adaptTo(Node.class);
                if(null != request.getParameter("noReplicate")) {
                	if(request.getParameter("noReplicate").equals("false")) {
                		LOGGER.debug("Replication From API Endpoint");
                		replicator.replicate(session,ReplicationActionType.ACTIVATE,hpNode.getPath());	
                	}else {
                		LOGGER.debug("noReplicate is set to TRUE from API");
                	}
                }else {
                	LOGGER.debug("Replication From PDP Admin Page");
                	replicator.replicate(session,ReplicationActionType.ACTIVATE,hpNode.getPath());	
                }
			}
		} catch (LoginException e) {
			LOGGER.error("LoginException {}", e.getMessage());
		} catch (ReplicationException e) {
			LOGGER.error("ReplicationException {}", e.getMessage());
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException {}", e.getMessage());
		} finally {
			CommonHelper.closeResourceResolver(resourceResolver);
		}

		response.setContentType(CommonConstants.APPLICATION_JSON);
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(productJson.toString());
		writer.flush();
	}

	/**
	 * Do post.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		LOGGER.debug("Inside doPOSt of UpdateProductSchemaServlet");
		doGet(request, response);
	}

}