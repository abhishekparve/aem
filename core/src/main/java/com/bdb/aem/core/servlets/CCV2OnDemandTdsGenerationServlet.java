package com.bdb.aem.core.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.AccessControlException;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

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
import org.xml.sax.SAXException;

import com.bdb.aem.core.services.CCV2OnDemandTdsGenerationService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.tagging.InvalidTagFormatException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * Servlet to update catalog structure with hp and hybris data.
 */
@SuppressWarnings("CQRules:CQBP-75")
@Component(service = Servlet.class, property = { "sling.servlet.extensions=html",
		"sling.servlet.paths=/bin/onDemandTdsGenerationServlet", "sling.servlet.methods=post",
		"sling.servlet.selectors=" + "aem"})
public class CCV2OnDemandTdsGenerationServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	/** The logger. */
	public static final Logger logger = LoggerFactory.getLogger(CCV2OnDemandTdsGenerationServlet.class);

	public static final String RESOURCE_TYPE = "bdb/generate-document";

	/** The catalog structure update service. */
	@Reference
	private transient CCV2OnDemandTdsGenerationService ccv2OnDemandTdsGenerationService;

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
		ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
		ResourceResolver resourceResolver = null;
		JsonObject responseJson = new JsonObject();
		String locale = StringUtils.EMPTY;
		try {

			JsonObject jsonObject = getRequestObject(request);
			String jsonString = jsonObject.toString();
			logger.info("jsonString before parsing ***** {}", jsonString);
			if (StringUtils.isNoneBlank(jsonString) && !jsonString.equals("{}")) {
				resourceResolver = request.getResourceResolver();
				String[] selectors = request.getRequestPathInfo().getSelectors();
				if (selectors.length > 0) {
					if (selectors[0].contentEquals("aem")) {
						JsonObject jsonObj = new JsonParser().parse(jsonString).getAsJsonObject();
				        JsonObject productsJson = jsonObj.getAsJsonObject("product");
				        if (null != productsJson ) {
				                if (productsJson.has(CommonConstants.MATERIAL_NUMBER) && null != productsJson.get(CommonConstants.MATERIAL_NUMBER).getAsString()) {
				                	String skuName = productsJson.get(CommonConstants.MATERIAL_NUMBER).getAsString().toLowerCase();
				                	logger.info("SKU Name : {}", skuName);
				                    logger.info("Locale : {}", locale);
				                	try {
										pdfStream = ccv2OnDemandTdsGenerationService.getPdfStream(jsonString, resourceResolver, skuName, locale);
									} catch (IOException | TransformerException | SAXException | RepositoryException
											| ParserConfigurationException e) {
										logger.error("Exception occured due to :", e);
									}
				                }
				        }
					} 
				} 
			} else {
				responseJson.addProperty("ERROR", "Json is improper");
				logger.info("***** Json is improper ****** {}", jsonString);
			}

		} catch (JsonProcessingException e) {
			logger.error("Exception {}", e.getMessage());
			response.getWriter().write("Catalog Structure failed to be updated");
		} catch (JsonSyntaxException e) {
			responseJson.addProperty("ERROR", "Json is improper");
			logger.error("Json is improper ", e);
		}
		response.setContentType("application/pdf");
		logger.debug("size of out file :: {}", pdfStream.size());
        response.setContentLength(pdfStream.size());
        response.getOutputStream().write(pdfStream.toByteArray());
        if (null != resourceResolver)
            resourceResolver.close();
        response.getOutputStream().flush();

		long endTime = System.currentTimeMillis();
		logger.debug("TOTAL SERVLET TIME - {}", endTime - startTime);

	}


}