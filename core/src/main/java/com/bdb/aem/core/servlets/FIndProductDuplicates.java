package com.bdb.aem.core.servlets;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.servlets.solr.FetchingDataFromSolr;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Servlet to read duplicates from catalog structure
 */
@SuppressWarnings("CQRules:CQBP-75")
@Component(service = Servlet.class, immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE,
property = {Constants.SERVICE_DESCRIPTION + "=" + "Get duplicate products",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + FIndProductDuplicates.RESOURCE_TYPE
})
public class FIndProductDuplicates extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "bdb/get-duplicate-products";

	/** The logger. */
	public static final Logger logger = LoggerFactory.getLogger(FIndProductDuplicates.class);

	@Reference
	transient QueryBuilder queryBuilder;

	@Reference
	transient BDBSearchEndpointService searchConfig;

	@Reference
	transient CatalogStructureUpdateService catalogUpdateService;
	
	transient JsonObject variantJson = new JsonObject();
	transient JsonObject baseProductJson = new JsonObject();
	transient JsonObject variantDuplicateJson = new JsonObject();
	transient JsonObject baseProductDuplicateJson = new JsonObject();
	transient JsonObject responseJson = new JsonObject();
	
	/**
	 * Do post.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

		ResourceResolver resourceResolver = request.getResourceResolver();
		try {
			JsonObject responseJson = getDuplicateProducts(resourceResolver);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(responseJson);
			out.flush();
		} catch (RepositoryException e) {
			logger.error("Repository Exception", e);
		}

	}

	public JsonObject getDuplicateProducts(ResourceResolver resourceResolver) throws RepositoryException {
		long startTime = System.currentTimeMillis();
		logger.debug("Product creation Start time - {}", startTime);
		Map<String, String> params = new HashMap<>();
		params.put(SolrSearchConstants.QUERY_BUILDER_PATH, SolrSearchConstants.CATALOG_CONTENT_HIERARCHY_PATH);
		params.put(SolrSearchConstants.QUERY_BUILDER_TYPE, searchConfig.getCatalogStructureNodeType());
		params.put(SolrSearchConstants.QUERY_BUILDER_PROPERTY, SolrSearchConstants.IS_VARIANT);
		params.put(SolrSearchConstants.QUERY_BUILDER_PROPERTY_VALUE, "true");
		params.put(SolrSearchConstants.QUERY_BUILDER_LIMIT, "-1");

		JsonObject responseJson = new JsonObject();

		Session session = resourceResolver.adaptTo(Session.class);
		Query query = queryBuilder.createQuery(PredicateGroup.create(params), session);
		SearchResult searchResults = query.getResult();
		
		Iterator<Resource> iterator = searchResults.getResources();
		while (iterator.hasNext()) {
			Resource variantResource = iterator.next();
			String variant = variantResource.getName();
			Resource baseProductResource = variantResource.getParent();
			String baseProduct = baseProductResource.getName();
			// for variants 
			if (variantJson.has(variant)) {
				JsonArray variantDuplicateArray = new JsonArray();
				JsonObject variantInfoJson = new JsonObject();
				if (variantDuplicateJson.has(variant)) {
					variantDuplicateArray = variantDuplicateJson.get(variant).getAsJsonObject().get("Duplicate").getAsJsonArray();;
					variantDuplicateArray.add(variantResource.getPath());
					variantInfoJson.add("Duplicate", variantDuplicateArray);
					variantInfoJson.addProperty("LookupPath",
							checkProductInLookup(resourceResolver, variant, CommonConstants.MATERIAL_NUMBER));
					variantDuplicateJson.add(variant, variantInfoJson);
				} else {
					variantDuplicateArray.add(variantResource.getPath());
					variantDuplicateArray.add(variantJson.get(variant).getAsString());
					variantInfoJson.add("Duplicate", variantDuplicateArray);
					variantInfoJson.addProperty("LookupPath",
							checkProductInLookup(resourceResolver, variant, CommonConstants.MATERIAL_NUMBER));
					variantDuplicateJson.add(variant, variantInfoJson);
				}
			}
			variantJson.addProperty(variant, variantResource.getPath());

			// for base products
			if (baseProductJson.has(baseProduct)
					&& !baseProductJson.get(baseProduct).getAsString().equals(baseProductResource.getPath())) {
				JsonArray baseProductDuplicateArray = new JsonArray();
				JsonObject baseProductInfoJson = new JsonObject();
				if (baseProductDuplicateJson.has(baseProduct)) {
					baseProductDuplicateArray = baseProductDuplicateJson.get(baseProduct).getAsJsonObject().get("Duplicate").getAsJsonArray();
					baseProductDuplicateArray.add(baseProductResource.getPath());
					baseProductInfoJson.add("Duplicate", baseProductDuplicateArray);
					baseProductInfoJson.addProperty("LookupPath",
							checkProductInLookup(resourceResolver, baseProduct, CommonConstants.MATERIAL_NUMBER));
					baseProductDuplicateJson.add(baseProduct, baseProductInfoJson);
				} else {
					baseProductDuplicateArray.add(baseProductJson.get(baseProduct).getAsString());
					baseProductDuplicateArray.add(baseProductResource.getPath());
					baseProductInfoJson.add("Duplicate", baseProductDuplicateArray);
					baseProductInfoJson.addProperty("LookupPath",
							checkProductInLookup(resourceResolver, baseProduct, CommonConstants.BASE_MATERIAL_NUMBER));
					baseProductDuplicateJson.add(baseProduct, baseProductInfoJson);
				}
			}
			baseProductJson.addProperty(baseProduct, baseProductResource.getPath());
		}
		responseJson.add("baseProduct", baseProductDuplicateJson);
		responseJson.add("variant", variantDuplicateJson);
		long endTime = System.currentTimeMillis();
		logger.debug("Product creation End time - {}", endTime);
		logger.debug("Total time taken for checking duplicates - {}", endTime - startTime);
		variantJson = new JsonObject();
		baseProductJson = new JsonObject();
		variantDuplicateJson = new JsonObject();
		baseProductDuplicateJson = new JsonObject();
		return responseJson;
	}

	public String checkProductInLookup(ResourceResolver resourceResolver, String productId, String productType)
			throws RepositoryException {
		Resource productResource = catalogUpdateService.getProductFromLookUp(resourceResolver, productId, productType);
		if (null != productResource) {
			return productResource.getPath();
		} else {
			return "Product path from lookup structure doesnot exist";
		}
	}

}