package com.bdb.aem.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet to delete duplicate properties from varianthpnode.
 */
@SuppressWarnings("CQRules:CQBP-75")
@Component(service = Servlet.class, configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "Modify Catalog Structure",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + CatalogStructureModificationScript.RESOURCE_TYPE
})
public class CatalogStructureModificationScript extends SlingAllMethodsServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public static final String RESOURCE_TYPE = "bdb/modify-catalog-structure";
	
	/** The logger. */
	public static final Logger logger = LoggerFactory.getLogger(CatalogStructureModificationScript.class);

	@Reference
	private transient QueryBuilder queryBuilder;

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

		StringBuilder jb = new StringBuilder();
		JsonObject responseJson = new JsonObject();
		InputStream stream = request.getInputStream();
		Session session = null;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {

			String line = null;
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}

			String jsonString = jb.toString();
			JsonObject jsonObj = new JsonParser().parse(jsonString).getAsJsonObject();
			
			Map<String, String> params = new HashMap<>();
			// "/content/commerce/products/bdb"

			if (jsonObj.has("rootPath")) {

				params.put(SolrSearchConstants.QUERY_BUILDER_PATH, jsonObj.get("rootPath").getAsString());
				params.put(SolrSearchConstants.QUERY_BUILDER_TYPE, JcrResourceConstants.NT_SLING_FOLDER);
				params.put(SolrSearchConstants.QUERY_BUILDER_PROPERTY, SolrSearchConstants.IS_VARIANT);
				params.put(SolrSearchConstants.QUERY_BUILDER_PROPERTY_VALUE, "true");
				params.put(SolrSearchConstants.QUERY_BUILDER_LIMIT, "-1");

				ResourceResolver resourceResolver = request.getResourceResolver();
				session = resourceResolver.adaptTo(Session.class);
				Query query = queryBuilder.createQuery(PredicateGroup.create(params), session);
				SearchResult searchResults = query.getResult();
				logger.debug("Jcr Query Hits : {}", searchResults.getTotalMatches());
				ArrayList<String> acceptedProps = new ArrayList<String>();
				acceptedProps.add(CommonConstants.BASE_MATERIAL_NUMBER);
				acceptedProps.add(CommonConstants.MATERIAL_NUMBER);
				acceptedProps.add(CommonConstants.PRIMARY_SUPER_CATEGORY);
				acceptedProps.add(CommonConstants.SIZE_QTY);
				acceptedProps.add(CommonConstants.SIZE_UOM);
				acceptedProps.add(CommonConstants.TDS_REVISION_HP);
				acceptedProps.add(JcrConstants.JCR_CREATED_BY);
				acceptedProps.add(JcrConstants.JCR_CREATED);
				acceptedProps.add(JcrConstants.JCR_PRIMARYTYPE);
				for (Hit hit : searchResults.getHits()) {
					try {
						Resource variantHpResource = hit.getResource().getChild(CommonConstants.HP);
						Node variantHpNode = variantHpResource.adaptTo(Node.class);
						PropertyIterator propertyIterator = variantHpNode.getProperties();
						while (propertyIterator.hasNext()) {
							Property property = propertyIterator.nextProperty();
							if (!acceptedProps.contains(property.getName())) {
								property.remove();
							}
						}
					} catch (RepositoryException e) {
						logger.error("Error while deleting {}", e.getMessage());
					}
				}
				session.save();
				responseJson.addProperty("success", "success");
			}
		} catch (RepositoryException e) {
			logger.error("Error while saving session {}", e.getMessage());
			responseJson.addProperty("fail", "e.getMessage()");
		} finally {
			if (null != session && session.isLive()) {
				CommonHelper.closeSession(session);
			}
		}
		long endTime = System.currentTimeMillis();
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(responseJson);
		out.flush();
		logger.debug("TOTAL SERVLET TIME - {}", endTime - startTime);

	}

}