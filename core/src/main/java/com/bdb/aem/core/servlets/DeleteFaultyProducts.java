package com.bdb.aem.core.servlets;

import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.AccessDeniedException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;
import javax.servlet.Servlet;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

/**
 * Servlet to update catalog structure with hp and hybris data.
 */
@SuppressWarnings("CQRules:CQBP-75")
@Component(immediate = true, service = Servlet.class, configurationPolicy = ConfigurationPolicy.REQUIRE, property = {
		ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + DeleteFaultyProducts.RESOURCE_TYPE,
		ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_POST,
		ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "base",
		ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "variant",
		ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + "solr",
		ServletResolverConstants.SLING_SERVLET_EXTENSIONS + CommonConstants.EQUAL + "html" })
public class DeleteFaultyProducts extends SlingAllMethodsServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

    public static final String RESOURCE_TYPE = "bdb/delete-faulty-products";
    
	/** The logger. */
	public static final Logger logger = LoggerFactory.getLogger(DeleteFaultyProducts.class);

	/** The catalog structure update service. */
	@Reference
	private transient CatalogStructureUpdateService catalogStructureUpdateService;

	@Reference
	private transient Replicator replicator;
	
	@Reference
	transient SolrSearchService solrSearchService;

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
		InputStream stream = request.getInputStream();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {

			String line = null;
			ArrayList<String> productList = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				productList.add(line);
			}
			resourceResolver = request.getResourceResolver();
			Session session = resourceResolver.adaptTo(Session.class);
			String[] selectors = request.getRequestPathInfo().getSelectors();
			String deleteDamFolder = request.getParameter("deleteDamFolder");
			if (selectors.length > 0) {
				if (selectors[0].contentEquals("variant")) {
					deleteVariants(productList, resourceResolver, session);
				} else if (selectors[0].contentEquals("base")) {
					responseJson = deleteBaseProducts(productList, resourceResolver, deleteDamFolder, session);
				} else if (selectors[0].contentEquals("solr")) {
					deleteSolrIndexes(productList);
				}
			}

			session.save();

			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(responseJson);
			out.flush();

		} catch (JsonProcessingException | RepositoryException e) {
			logger.error("Failed to delete faulty products {}", e.getMessage());
			response.getWriter().write("Failed to delete duplicates");
		}
		long endTime = System.currentTimeMillis();
		logger.debug("TOTAL SERVLET TIME - {}", endTime - startTime);

	}

	public void deleteVariants(ArrayList<String> variantList, ResourceResolver resourceResolver, Session session) {
		TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
		for (String variant : variantList) {
			try {
				Resource variantRes = catalogStructureUpdateService.getProductFromLookUp(resourceResolver, variant,
						CommonConstants.MATERIAL_NUMBER);
				if (null != variantRes) {
					replicator.replicate(session, ReplicationActionType.DEACTIVATE, variantRes.getPath());
					variantRes.adaptTo(Node.class).remove();
					String variantTagPath = getTagPath(variantRes.getPath());
					Tag tag = tagManager.resolve(variantTagPath);
					deleteLookupPath(variant, CommonConstants.MATERIAL_NUMBER, resourceResolver, session);
					if (null != tag) {
						replicator.replicate(session, ReplicationActionType.DEACTIVATE, variantTagPath);
						tagManager.deleteTag(tag);
					}
				}

			} catch (RepositoryException | ReplicationException e) {
				logger.error("Exception in deleting Faulty Product {}", e.getMessage());
			}
		}
	}

	public JsonObject deleteBaseProducts(ArrayList<String> baseProductList, ResourceResolver resourceResolver,
			String deleteDamFolder, Session session) {
		TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
		JsonObject responseJson = new JsonObject();
		for (String baseProduct : baseProductList) {
			try {
				Resource baseProductRes = catalogStructureUpdateService.getProductFromLookUp(resourceResolver,
						baseProduct + "_base", CommonConstants.BASE_MATERIAL_NUMBER);
				if (null != baseProductRes && baseProductDeleteConditions(baseProductRes)) {

					replicator.replicate(session, ReplicationActionType.DEACTIVATE, baseProductRes.getPath());
					baseProductRes.adaptTo(Node.class).remove();
					String baseProductTagPath = getTagPath(baseProductRes.getPath());
					Tag tag = tagManager.resolve(baseProductTagPath);
					deleteLookupPath(baseProduct + "_base", CommonConstants.BASE_MATERIAL_NUMBER, resourceResolver, session);
					if (null != tag) {
						replicator.replicate(session, ReplicationActionType.DEACTIVATE, baseProductTagPath);
						tagManager.deleteTag(tag);
					}
					deleteLookupPath(baseProduct, CommonConstants.MATERIAL_NUMBER, resourceResolver, session);
					if(Boolean.valueOf(deleteDamFolder)) {
						deleteDamFolder(baseProductRes, session, resourceResolver);
					}
				} else {
					responseJson.addProperty(baseProduct, "Failure");
				}

			} catch (RepositoryException | ReplicationException e) {
				responseJson.addProperty(baseProduct, "Exception");
				logger.error("Exception in deleting Faulty Product {}", e.getMessage());
			}
		}
		logger.debug("Response Json for Faulty Products {}", responseJson);
		return responseJson;
	}
	
	/**
	 * Deletes the DAM Folder for {@code baseProductRes}.
	 * 
	 * @param baseProductRes the base product resource
	 * @param session        the session
	 * @param resolver       the resource resolver
	 */
	private void deleteDamFolder(Resource baseProductRes, Session session, ResourceResolver resolver) {
		String existingPath = "/content/commerce/products/bdb/products";
		String replacedBy = "/content/dam/bdb/products/global";
		Optional.ofNullable(baseProductRes).map(r -> r.getPath()).ifPresent(p -> {
			String u = p.startsWith(existingPath) ? p.replace(existingPath, replacedBy) : StringUtils.EMPTY;
			Resource r = resolver.getResource(u);
			if (null != r) {
				Node n = r != null ? r.adaptTo(Node.class) : null;

				try {
					if (n != null) {
						replicator.replicate(session, ReplicationActionType.DEACTIVATE, u);
						n.remove();
					}
				} catch (AccessDeniedException e) {
					logger.error("Access Denied Exception {}", e);
				} catch (VersionException e) {
					logger.error("Version Exception {}", e);
				} catch (LockException e) {
					logger.error("Lock Exception {}", e);
				} catch (ConstraintViolationException e) {
					logger.error("Constraint Violation Exception {}", e);
				} catch (RepositoryException e) {
					logger.error("Repository Exception {}", e);
				} catch (ReplicationException e) {
					logger.error("Replication Exception {}", e);
				}
			}

		});

	}

	public void deleteSolrIndexes(ArrayList<String> productList) {
		try {
			ArrayList<HttpSolrClient> solrClients = (ArrayList<HttpSolrClient>) solrSearchService.getAllSolrClients();
			for (HttpSolrClient server : solrClients) {
				for(String product : productList) {
					server.deleteById(product);
				}
				server.commit();
				server.close();
			}
		} catch (SolrServerException | LoginException | IOException e) {
			logger.error("Exception due to", e);
		}

	}

	public boolean baseProductDeleteConditions(Resource baseProductRes) {
		Iterator<Resource> children = baseProductRes.listChildren();
		String productName = baseProductRes.getName().replace("_base", "");
		while (children.hasNext()) {
			Resource child = children.next();
			String childNodeName = child.getName();
			if (!childNodeName.equals(CommonConstants.HP_NODE) && !childNodeName.equals(CommonConstants.SAP_CC_NODENAME)
					&& !childNodeName.equals(productName)) {
				return false;
			} else {
				
			}
		}
		return true;
	}

	public String checkProductInLookup(ResourceResolver resourceResolver, String productId, String productType)
			throws RepositoryException {
		Resource productResource = catalogStructureUpdateService.getProductFromLookUp(resourceResolver, productId,
				productType);
		if (null != productResource) {
			return productResource.getPath();
		} else {
			return "Product path from lookup structure doesnot exist";
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

	public void deleteLookupPath(String productId, String productType, ResourceResolver resourceResolver,
			Session session) throws RepositoryException, ReplicationException {
		String lookUpPath = CommonHelper.getLookUpPathFromProductId(productId, productType);
		Resource lookUpPathResource = resourceResolver.getResource(lookUpPath);
		if(null != lookUpPathResource) {
			Node node = lookUpPathResource.adaptTo(Node.class);
			replicator.replicate(session, ReplicationActionType.DEACTIVATE, lookUpPath);
			node.remove();
		}
	}

}