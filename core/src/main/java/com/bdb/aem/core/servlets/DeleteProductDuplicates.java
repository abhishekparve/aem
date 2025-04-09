package com.bdb.aem.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map.Entry;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Servlet to update catalog structure with hp and hybris data.
 */
@SuppressWarnings("CQRules:CQBP-75")
@Component(service = Servlet.class, immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE,
		property = {Constants.SERVICE_DESCRIPTION + "=" + "Delete Duplicates",
				ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
				ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + DeleteProductDuplicates.RESOURCE_TYPE
		})
public class DeleteProductDuplicates extends BaseServlet {
	public static final String RESOURCE_TYPE = "bdb/delete-duplicate-products";
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	public static final Logger logger = LoggerFactory.getLogger(DeleteProductDuplicates.class);
	
	/** The Constant DUPLICATE. */
	private static final String DUPLICATE = "Duplicate";
	
	/** The Constant LOOK_UP_PATH. */
	private static final String LOOK_UP_PATH = "LookupPath";

	/** The catalog structure update service. */
	@Reference
	private transient CatalogStructureUpdateService catalogStructureUpdateService;
	
	@Reference 
	private transient Replicator replicator;
	
	/**
	 * Do post.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		long startTime = System.currentTimeMillis();
		ResourceResolver resourceResolver= null;
		JsonObject responseJson = new JsonObject();
		try {

			JsonObject requestObject =  getRequestObject(request);
			resourceResolver = request.getResourceResolver();
			Session session = resourceResolver.adaptTo(Session.class);
			
			JsonObject variantJsonArray = requestObject.get("variant").getAsJsonObject();
			JsonObject baseProductJsonArray = requestObject.get("baseProduct").getAsJsonObject();
			
			deleteDuplicateProducts(variantJsonArray, resourceResolver);
			session.save();
			deleteDuplicateProducts(baseProductJsonArray, resourceResolver);
			session.save();
			
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(responseJson);
			out.flush();

		} catch (JsonProcessingException | RepositoryException e) {
			logger.error("Failed to delete duplicates {}", e.getMessage());
			response.getWriter().write("Failed to delete duplicates");
		}
		long endTime = System.currentTimeMillis();
		logger.debug("TOTAL SERVLET TIME - {}" ,endTime-startTime);

	}

	/**
	 * Delete duplicate products.
	 *
	 * @param productObject the product object
	 * @param resourceResolver the resource resolver
	 */
	public void deleteDuplicateProducts(JsonObject productObject, ResourceResolver resourceResolver) {
		TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
		Session session = resourceResolver.adaptTo(Session.class);
		for (Entry<String, JsonElement> productEntry : productObject.entrySet()) {
			JsonObject productJson = productEntry.getValue().getAsJsonObject();
			String lookUpPath = productJson.get(LOOK_UP_PATH).getAsString();
			JsonArray duplicateArray = productJson.get(DUPLICATE).getAsJsonArray();
			for (JsonElement duplicateElement : duplicateArray) {
				try {
					String duplicatePath = duplicateElement.getAsString();
					if(!duplicatePath.equals(lookUpPath)) {
						Resource productResource = resourceResolver.getResource(duplicatePath);
						if(null != productResource) {
							Node productNode = productResource.adaptTo(Node.class);
							replicator.replicate(session,ReplicationActionType.DEACTIVATE,productNode.getPath()); 
							productNode.remove();
							Tag tag = tagManager.resolve(getTagPath(duplicatePath));
							if(null != tag) {
								replicator.replicate(session,ReplicationActionType.DEACTIVATE,getTagPath(duplicatePath)); 
								tagManager.deleteTag(tag);
							}
						}
					}
				} catch (RepositoryException | ReplicationException e) {
					logger.error("Failed to delete duplicate element: {}", duplicateElement);
				}
			}
		}
	}
	
	/**
	 * Gets the tag path.
	 *
	 * @param catalogPath the catalog path
	 * @return the tag path
	 */
	public String getTagPath(String catalogPath) {
        return catalogPath.replace(SolrSearchConstants.CATALOG_CONTENT_HIERARCHY_PATH,
                CommonConstants.CATALOG_TAG_HIERARCHY_PATH);
    }

}