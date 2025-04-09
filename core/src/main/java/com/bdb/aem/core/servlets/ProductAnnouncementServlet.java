package com.bdb.aem.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.UpdateProductSchemaService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class ProductAnnouncementServlet.
 */
@Component(name = "ProductAnnouncementServlet", service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "ProductAnnouncementServlet",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + ProductAnnouncementServlet.RESOURCE_TYPE
})
public class ProductAnnouncementServlet extends SlingAllMethodsServlet  {
	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "bdb/get-product-announcements";

	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductAnnouncementServlet.class);

	/**
	 * The resource resolver factory.
	 */
	@Reference
	private transient ResourceResolverFactory resourceResolverFactory;

	/** The update product schema service. */
	@Reference
	private transient UpdateProductSchemaService updateProductSchemaService;


	/** The externalizer service. */
	@Reference
	private transient ExternalizerService externalizerService;

	/** The solr search service. */
	@Reference
	transient SolrSearchService solrSearchService;
	
	@Reference
	private transient CatalogStructureUpdateService catalogStructureUpdateService;

	/**
	 * Do get.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		ResourceResolver resourceResolver = null;
		JsonObject productJsonObject = new JsonObject();
		JsonArray products = new JsonArray();
		BufferedReader reader = request.getReader();
		StringBuilder jb = new StringBuilder();
		try {
			String line = null;
			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
			resourceResolver = request.getResourceResolver();
			String jsonString = jb.toString();
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(jsonString);
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			String country = getJsonProperty(jsonObject, "country");
			if(null != country && !("global").equalsIgnoreCase(country)) {
				country = country.toLowerCase();
			}
			JsonArray productsArray = jsonObject.getAsJsonArray("productID");
			for (JsonElement product : productsArray) {
				JsonObject productJson = new JsonObject();
				String productID = product.getAsString();
				products = createProductJsonObject(request, resourceResolver, products, country, productID,
						productJson, externalizerService);
			}
			if(products.size() >0) {
				productJsonObject.add("products", products);
			}			
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException {}", e.getMessage());
		}  

		response.setContentType(CommonConstants.APPLICATION_JSON);
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		if(!productJsonObject.entrySet().isEmpty()) {
			writer.write(productJsonObject.toString());	
		}else {
			writer.write("This Product Does Not Have Announcements");
		}
		
		writer.flush();
	}

	/**
	 * Creates the product json object.
	 *
	 * @param request the request
	 * @param resourceResolver the resource resolver
	 * @param products the products
	 * @param country the country
	 * @param product the product
	 * @param productJson the product json
	 * @param externalizerService the externalizer service
	 * @return the json array
	 * @throws RepositoryException 
	 */
	public JsonArray createProductJsonObject(SlingHttpServletRequest request, ResourceResolver resourceResolver,
											 JsonArray products, String country, String product, JsonObject productJson,
											 ExternalizerService externalizerService) throws RepositoryException {
		String productID = product.toLowerCase();
		Resource variantResource = catalogStructureUpdateService.getProductFromLookUp(resourceResolver, productID, CommonConstants.MATERIAL_NUMBER);
		if (null != variantResource && CommonHelper.getProductAvailabilityInRegion(productID, country,
				resourceResolver, catalogStructureUpdateService)) {
			Resource baseHpResource = CommonHelper.getBaseHpResourceFromLookUp(variantResource);
			Resource variantHpResource = SolrUtils.getVariantHpResource(baseHpResource, productID);
			Resource parentResource = variantHpResource.getParent();
			if (null != country && null != parentResource) {
				Resource marketingResource = parentResource.getChild("marketing");
				if(null != marketingResource && marketingResource.hasChildren()) {
					Iterable<Resource> countryListResource = marketingResource.getChildren();
					for(Resource countryResource : countryListResource) {
						Resource countryRes = null;
						String regionNode = countryResource.getName();
						if(StringUtils.isNotEmpty(regionNode) && regionNode.equalsIgnoreCase(CommonConstants.GLOBAL)) {
							countryRes = marketingResource.getChild(CommonConstants.GLOBAL);
							productJson = updateProductSchemaService.getProductAnnouncement(countryResource, request, productJson,
									resourceResolver, externalizerService, product, solrSearchService);
						} else {
							if(StringUtils.isNotEmpty(regionNode) && regionNode.equalsIgnoreCase(CommonConstants.EMEA)) {
								if(CommonConstants.EMEA_COUNTRIES_LIST.contains(country)) {
									countryRes = marketingResource.getChild(CommonConstants.EMEA);
									productJson = updateProductSchemaService.getProductAnnouncement(countryRes, request, productJson,
											resourceResolver, externalizerService, product, solrSearchService);
								}
							} else if(StringUtils.isNotEmpty(regionNode) && regionNode.equalsIgnoreCase(CommonConstants.APAC)) {
								if(CommonConstants.APAC_COUNTRIES_LIST.contains(country)) {
									countryRes = marketingResource.getChild(CommonConstants.APAC);
									productJson = updateProductSchemaService.getProductAnnouncement(countryRes, request, productJson,
											resourceResolver, externalizerService, product, solrSearchService);
								}
							} else if(StringUtils.isNotEmpty(regionNode) && regionNode.equalsIgnoreCase(CommonConstants.APAC_EN)) {
								if(CommonConstants.APAC_EN_COUNTRIES_LIST.contains(country)) {
									countryRes = marketingResource.getChild(CommonConstants.APAC_EN);
									productJson = updateProductSchemaService.getProductAnnouncement(countryRes, request, productJson,
											resourceResolver, externalizerService, product, solrSearchService);
								}
							} else {
								countryRes = marketingResource.getChild(country);
								productJson = updateProductSchemaService.getProductAnnouncement(countryRes, request, productJson,
										resourceResolver, externalizerService, product, solrSearchService);
							}
						}
					}
				}
				if(productJson.size() > 0) {
					productJson.addProperty("productID", product);
					products.add(productJson);
				}
				
			}
		}
		return products;

	}

	/**
	 * Gets the json property.
	 *
	 * @param assetObj the asset obj
	 * @param myParam the my param
	 * @return the json property
	 */
	private String getJsonProperty(JsonObject assetObj, String myParam) {
		String local = null;
		if (null != assetObj.get(myParam)) {
			local = assetObj.get(myParam).getAsString();
		}
		return local;
	}
}
