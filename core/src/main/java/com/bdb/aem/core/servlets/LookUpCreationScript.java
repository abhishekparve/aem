package com.bdb.aem.core.servlets;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.servlets.solr.FetchingDataFromSolr;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.day.cq.commons.jcr.JcrUtil;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet to update catalog structure with hp and hybris data.
 */
@SuppressWarnings("CQRules:CQBP-75")
@Component(service = Servlet.class, immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE,
property = {Constants.SERVICE_DESCRIPTION + "=" + "Fetch Data",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + LookUpCreationScript.RESOURCE_TYPE
})
public class LookUpCreationScript extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "bdb/lookup-creation";

	/** The logger. */
	public static final Logger logger = LoggerFactory.getLogger(LookUpCreationScript.class);

	@Reference
	transient QueryBuilder queryBuilder;

	@Reference
	transient BDBSearchEndpointService searchConfig;

	/**
	 * Do post.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		doPost(request, response);
	}
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		
		Session session = null;
		ResourceResolver resourceResolver = request.getResourceResolver();
		try {
			session = resourceResolver.adaptTo(Session.class);
			createLookUpStructure(session);
		} catch (RepositoryException e) {
			logger.error("RepositoryException {}", e.getMessage());
		} finally {
			CommonHelper.closeSession(session);
		}
	}

	public void createLookUpStructure(Session session)
			throws RepositoryException{
		long startTime = System.currentTimeMillis();
		logger.debug("Product creation Start time - {}" ,startTime);
		Map<String, String> params = new HashMap<>();
		params.put(SolrSearchConstants.QUERY_BUILDER_PATH, SolrSearchConstants.CATALOG_CONTENT_HIERARCHY_PATH);
		params.put(SolrSearchConstants.QUERY_BUILDER_TYPE, searchConfig.getCatalogStructureNodeType());
		params.put(SolrSearchConstants.QUERY_BUILDER_PROPERTY, SolrSearchConstants.IS_VARIANT);
		params.put(SolrSearchConstants.QUERY_BUILDER_PROPERTY_VALUE, "true");
		params.put(SolrSearchConstants.QUERY_BUILDER_LIMIT, "-1");

		Query query = queryBuilder.createQuery(PredicateGroup.create(params), session);
		SearchResult searchResults = query.getResult();
		for (Hit hit : searchResults.getHits()) {
			Resource variantResource = hit.getResource();
			try {
				Resource variantHpResource = variantResource.getChild(CommonConstants.HP_NODE);
				Resource baseProductResource = variantResource.getParent();
				ValueMap valueMap = variantHpResource.adaptTo(ValueMap.class);
				String materialNo = valueMap.get(CommonConstants.MATERIAL_NUMBER).toString();
				String baseMaterialNo = valueMap.get(CommonConstants.BASE_MATERIAL_NUMBER).toString();

				String baseMaterialNoLookUpPath = getLookUpPathFromProductId(baseMaterialNo, CommonConstants.BASE_MATERIAL_NUMBER);
				Node baseMaterialNoLookUpNode = JcrUtil.createPath(baseMaterialNoLookUpPath, searchConfig.getCatalogStructureNodeType(), session);
				baseMaterialNoLookUpNode.setProperty("catalogPath", baseProductResource.getPath());
				baseMaterialNoLookUpNode.setProperty("isBaseProductLookup", "true");

				String materialNoLookUpPath = getLookUpPathFromProductId(materialNo, CommonConstants.MATERIAL_NUMBER);
				Node materialNoLookUpNode = JcrUtil.createPath(materialNoLookUpPath, searchConfig.getCatalogStructureNodeType(), session);
				materialNoLookUpNode.setProperty("catalogPath", variantResource.getPath());
				materialNoLookUpNode.setProperty("isVariantLookup", "true");

			} catch (RepositoryException e) {
				logger.error("Excepyion while creating lookUp", e);
			}
		}
		session.save();
		long endTime = System.currentTimeMillis();
		logger.debug("Product creation End time - {}" ,endTime);
		logger.debug("Total time taken for Product creation - {}" ,endTime-startTime);
	}

	public String getLookUpPathFromProductId(String productId, String productType) {
		String basePath = StringUtils.EMPTY;
		if (productType.equals(CommonConstants.MATERIAL_NUMBER)) {
			basePath = "/content/commerce/lookup/variant/";
		} else if (productType.equals(CommonConstants.BASE_MATERIAL_NUMBER)) {
			basePath = "/content/commerce/lookup/baseProduct/";
			productId = productId.replace("_base", "");
		}
		return getLookupPath(productId, basePath);
	}

	private String getLookupPath(String productId, String basePath) {
		StringBuilder builder = new StringBuilder(basePath);
		String finalPath;
		String temp = productId;
		while (temp.length() > 0) {
			if (temp.length() > 2 && temp.length() != 3) {
				builder.append(temp.substring(0, 3)).append("f").append("/");
				temp = temp.substring(3);
			} else {
				temp = "";
			}
		}
		builder.append(productId);
		finalPath = builder.toString();
		return finalPath;
	}

}