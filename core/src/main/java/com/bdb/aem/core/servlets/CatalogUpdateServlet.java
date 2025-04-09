package com.bdb.aem.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.solr.client.solrj.SolrServerException;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.tagging.InvalidTagFormatException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * Servlet to update catalog structure with hp and hybris data.
 */
@SuppressWarnings("CQRules:CQBP-75")
@Component(service = Servlet.class, configurationPolicy = ConfigurationPolicy.REQUIRE, property = { "sling.servlet.extensions=html",
		"sling.servlet.paths=/bin/catalogUpdatePathServlet", "sling.servlet.methods=post",
		"sling.servlet.selectors=" + "hp", "sling.servlet.selectors=" + "sap" })
public class CatalogUpdateServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	/** The logger. */
	public static final Logger logger = LoggerFactory.getLogger(CatalogUpdateServlet.class);

	public static final String RESOURCE_TYPE = "bdb/catalog-update";

	/** The catalog structure update service. */
	@Reference
	private transient CatalogStructureUpdateService catalogStructureUpdateService;

	/**
	 * Do post.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		long startTime = System.currentTimeMillis();
		ResourceResolver resourceResolver = null;
		JsonObject responseJson = new JsonObject();
		try {

			JsonObject jsonObject = getRequestObject(request);
			String jsonString = jsonObject.toString();
			logger.info("jsonString before parsing ***** {}", jsonString);
			if (StringUtils.isNoneBlank(jsonString) && !jsonString.equals("{}")) {
				resourceResolver = request.getResourceResolver();
				String[] selectors = request.getRequestPathInfo().getSelectors();
				if (selectors.length > 0) {
					if (selectors[0].contentEquals(CommonConstants.SAP)) {
						responseJson = catalogStructureUpdateService.setSAPNodeProperty(resourceResolver, jsonString);
					} else if (selectors[0].contentEquals(CommonConstants.HP)) {
						responseJson = catalogStructureUpdateService.setHPNodeProperty(resourceResolver, jsonString);
					} else if (selectors[0].contentEquals("indexAndReplicate")) {
						catalogStructureUpdateService.indexAndReplicateProducts(resourceResolver, jsonString);
					}

				} 
			} else {
				responseJson.addProperty("ERROR", "Json is improper");
				logger.info("***** Json is improper ****** {}", jsonString);
			}

		} catch (JsonProcessingException | RepositoryException | InvalidTagFormatException | SolrServerException
				| LoginException e) {
			logger.error("Exception {}", e.getMessage());
			response.getWriter().write("Catalog Structure failed to be updated");
		} catch (JsonSyntaxException e) {
			responseJson.addProperty("ERROR", "Json is improper");
			logger.error("Json is improper ", e);
		}
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(responseJson);
		out.flush();

		long endTime = System.currentTimeMillis();
		logger.debug("TOTAL SERVLET TIME - {}", endTime - startTime);

	}

}