package com.bdb.aem.core.servlets;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.UpdateProductSchemaService;
import com.bdb.aem.core.services.solr.FetchingCloneService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.servlets.solr.FetchingDataFromSolr;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.solr.client.solrj.SolrServerException;
import org.eclipse.jetty.util.StringUtil;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * The Class CompareProductsServlet.
 */
@Component(name = "CompareProductsServlet", service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "Compare Products",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + CompareProductsServlet.RESOURCE_TYPE
})
public class CompareProductsServlet extends SlingAllMethodsServlet  {
	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "bdb/compare-products";

	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CompareProductsServlet.class);

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

	/** The fetching clone service. */
	@Reference
	private transient FetchingCloneService fetchingCloneService;

	/** The solr config. */
	@Reference
	private transient BDBSearchEndpointService solrConfig;

	/** The solr search service. */
	@Reference
	private transient SolrSearchService solrSearchService;
	
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

			JsonArray productsArray = jsonObject.getAsJsonArray("productID");
			for (JsonElement product : productsArray) {
				JsonObject productJson = new JsonObject();
				String productID = product.getAsString();
				products = createProductJsonObject(request, resourceResolver, products, productID,
						productJson, externalizerService);
			}
			productJsonObject.add("products", products);
		} catch (SolrServerException e) {
			LOGGER.error("SolrServerException {}", e.getMessage());
		} catch (RepositoryException e) {
			LOGGER.error("RepositoryException {}", e.getMessage());
		}

		response.setContentType(CommonConstants.APPLICATION_JSON);
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(productJsonObject.toString());
		writer.flush();
	}

	/**
	 * Creates the product json object.
	 *
	 * @param request the request
	 * @param resourceResolver the resource resolver
	 * @param products the products
	 * @param product the product
	 * @param productJson the product json
	 * @param externalizerService the externalizer service
	 * @return the json array
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SolrServerException the solr server exception
	 * @throws RepositoryException 
	 */
	public JsonArray createProductJsonObject(SlingHttpServletRequest request, ResourceResolver resourceResolver,
											 JsonArray products, String product, JsonObject productJson, ExternalizerService externalizerService)
			throws IOException, SolrServerException, RepositoryException {
		
		if(null != request.getParameter(CommonConstants.COUNTRY)) {
			String country = request.getParameter(CommonConstants.COUNTRY).toLowerCase();
			
			Resource variantResource = catalogStructureUpdateService.getProductFromLookUp(resourceResolver, product, CommonConstants.MATERIAL_NUMBER);	
			Resource baseHpResource = CommonHelper.getBaseHpResourceFromLookUp(variantResource);
			if (null != baseHpResource && CommonHelper.getProductAvailabilityInRegion(product, country,
					resourceResolver, catalogStructureUpdateService))  {
				ValueMap baseHpMap = baseHpResource.adaptTo(ValueMap.class);
				String dyeName = CommonHelper.getDyeNameFromFormat(baseHpMap);
				String cloneId = CommonHelper.getTdsCloneIdFromHp(baseHpMap);
				JsonArray otherFormatsArray = new JsonArray();
				JsonArray red = new JsonArray();
				JsonArray yellow = new JsonArray();
				JsonArray blue = new JsonArray();
				JsonArray violet = new JsonArray();
				JsonArray ultraviolet = new JsonArray();
				JsonArray noColor = new JsonArray();
				productJson.addProperty(CommonConstants.CATALOG_NUMBER_KEY, product);
				if(null != variantResource && null != variantResource.getChild(CommonConstants.HP)) {
					Resource variantHpResource = variantResource.getChild(CommonConstants.HP);
					productJson = updateProductSchemaService.getProductSKUDetails(variantHpResource, request, productJson,
							resourceResolver, externalizerService, product, solrSearchService, baseHpResource,country);
				}				
				String output = fetchingCloneService.getFormatsData(country, solrConfig, solrSearchService,dyeName,cloneId);
				if (StringUtil.isNotBlank(output)) {
					getVariantsArray(productJson, output,red, yellow, blue, violet,ultraviolet, noColor, cloneId);
				}

				otherFormatFormattedResponse(red, yellow, blue, violet,ultraviolet, noColor, otherFormatsArray);
				productJson.add(CommonConstants.OTHER_FORMATS, otherFormatsArray);
				removeDuplicateFormats(productJson);
				products.add(productJson);
			}
		}
		
		return products;

	}
	
	/**
	 * Removes the duplicate formats from the productJson.
	 *
	 * @param productJson the product json
	 */
	private void removeDuplicateFormats(JsonObject productJson) {
		Set<String> formatIds = new HashSet<>();
		JsonArray uniqueFormats = new JsonArray();
		JsonArray otherFormats = productJson.getAsJsonArray(CommonConstants.OTHER_FORMATS);
		for (int i = 0; i < otherFormats.size(); i++) {
			JsonObject format = otherFormats.get(i).getAsJsonObject();
			String  formatId = format.get(CommonConstants.FORMAT_ID).getAsString();
			if (! formatIds.contains(formatId)) {
				formatIds.add(formatId);
				uniqueFormats.add(format);
			}
		}
		productJson.add(CommonConstants.OTHER_FORMATS, uniqueFormats);
	}

	private void otherFormatFormattedResponse(JsonArray red, JsonArray yellow, JsonArray blue, JsonArray violet,
											  JsonArray ultraviolet, JsonArray noColor, JsonArray otherFormatsArray) {
		if(red.size()>0)
			for(JsonElement obj :red) {
				otherFormatsArray.add(obj);
			}
		if(yellow.size()>0)
			for(JsonElement obj :yellow) {
				otherFormatsArray.add(obj);
			}
		if(blue.size()>0)
			for(JsonElement obj :blue) {
				otherFormatsArray.add(obj);
			}
		if(violet.size()>0)
			for(JsonElement obj :violet) {
				otherFormatsArray.add(obj);
			}
		if(ultraviolet.size()>0)
			for(JsonElement obj :ultraviolet) {
				otherFormatsArray.add(obj);
			}
		if(noColor.size()>0)
			for(JsonElement obj :noColor) {
				otherFormatsArray.add(obj);
			}

	}

	/**
	 * Gets the variants array.
	 *
	 * @param productJson the product json
	 * @param output the output
	 * @return the variants array
	 */
	public void getVariantsArray(JsonObject productJson, String output, JsonArray red, JsonArray yellow, JsonArray blue,JsonArray violet, JsonArray ultraviolet,JsonArray noColor, String cloneId) {
		JsonElement variantElement = new JsonParser().parse(output);
		if (null != variantElement && variantElement.isJsonArray()) {
			JsonArray variantArray = variantElement.getAsJsonArray();

			for (JsonElement variantObjElement : variantArray) {
				JsonObject otherFormatsJsonObj = new JsonObject();
				JsonObject variantJsontObject = variantObjElement.getAsJsonObject();
				if(null != variantJsontObject.get(CommonConstants.DYE_NAME) && !StringUtils.isEmpty(cloneId)) {
					String dyeName = variantJsontObject.get(CommonConstants.DYE_NAME).getAsString();
					String tdsCloneNameFacet = "&tdsCloneName_facet_s::"+cloneId+"="+cloneId;
					otherFormatsJsonObj.addProperty(CommonConstants.FORMAT_ID, dyeName);
					String productUrl = CommonConstants.PLP_URL.concat(dyeName).concat(tdsCloneNameFacet);
					otherFormatsJsonObj.addProperty(CommonConstants.URL, productUrl);
				}

				if(null != variantJsontObject.get(CommonConstants.LASER_COLOR)) {
					String colorValue = variantJsontObject.get(CommonConstants.LASER_COLOR).getAsString();
					if(colorValue.equalsIgnoreCase("red")) {
						red.add(otherFormatsJsonObj);
					}else if(colorValue.equalsIgnoreCase("yellow") || colorValue.equalsIgnoreCase("yellow-green")) {
						yellow.add(otherFormatsJsonObj);
					}else if(colorValue.equalsIgnoreCase("blue") ) {
						blue.add(otherFormatsJsonObj);
					}else if(colorValue.equalsIgnoreCase("violet")) {
						violet.add(otherFormatsJsonObj);
					}else if(colorValue.equalsIgnoreCase("ultraviolet")) {
						ultraviolet.add(otherFormatsJsonObj);
					}else {
						noColor.add(otherFormatsJsonObj);
					}

				}
			}
		}
	}

	/**
	 * Gets the value map property.
	 *
	 * @param varientValueMap the varient value map
	 * @param property the property
	 * @return the value map property
	 */
	public String getValueMapProperty(ValueMap varientValueMap, String property) {
		String propertyValue = null;
		if (null != varientValueMap.get(property)) {
			propertyValue = varientValueMap.get(property, String.class);
		}
		return propertyValue;
	}
}
