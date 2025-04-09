package com.bdb.aem.core.services.impl;

import java.io.IOException;

import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.solr.client.solrj.SolrServerException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.FetchRequiredCompanionProduct;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component(service = FetchRequiredCompanionProduct.class, immediate = true)
public class FetchRequiredCompanionProductImpl implements FetchRequiredCompanionProduct {

	@Reference
	ExternalizerService externalizerService;
	
	@Reference
	BDBApiEndpointService bdbApiEndpointService;
	
	/** The solr config. */
	@Reference
	BDBSearchEndpointService solrConfig;

	@Override
	public String getRequriedCompanionProduct(String materialNum, String country, BDBSearchEndpointService solrConfig,
			SolrSearchService solrSearchService, ResourceResolver resourceResolver, CatalogStructureUpdateService catalogStructureUpdateService)
			throws IOException, SolrServerException, RepositoryException {
		Resource variantResource= catalogStructureUpdateService.getProductFromLookUp(resourceResolver, materialNum, CommonConstants.MATERIAL_NUMBER);
		JsonArray list = null;
		Resource baseHpResource = CommonHelper.getBaseHpResourceFromLookUp(variantResource);
		if(null != baseHpResource) {
				ValueMap varientValueMap = baseHpResource.getValueMap();
				if (null != varientValueMap.get(CommonConstants.COMPANION_PRODUCTS)) {
					JsonArray jsonObject = CommonHelper.getJsonObject(varientValueMap, CommonConstants.COMPANION_PRODUCTS);
					list = getCompanionList(jsonObject, resourceResolver, solrSearchService, country, catalogStructureUpdateService);
				}
		}
		if(null != list)
			return list.toString();
		else 
			return StringUtils.EMPTY;
	}

	/**
	 * Gets the companion list.
	 *
	 * @param jsonObject the json object
	 * @param catalogStructureUpdateService 
	 * @param hpResource 
	 * @return the companion list
	 * @throws RepositoryException 
	 * @throws JsonProcessingException the json processing exception
	 */
	private JsonArray getCompanionList(JsonArray jsonObject, ResourceResolver resourceResolver,
			SolrSearchService solrSearchService, String country, CatalogStructureUpdateService catalogStructureUpdateService) throws RepositoryException {
		JsonArray responseArray = new JsonArray();
		if (null != jsonObject) {
			JsonArray productsArray = jsonObject.getAsJsonArray();
			for (JsonElement product : productsArray) {
				JsonObject productObj = product.getAsJsonObject();
				String companionType = CommonHelper.getJsonProperty(productObj, CommonConstants.COMPANION_TYPE);

				if (null != companionType && companionType.trim().equals("Required")) {
					String companionMaterialNumber = CommonHelper.getJsonProperty(productObj,
							CommonConstants.COMPANION_MATERIAL_NUMBER);
					JsonObject productDetails = getVarientHPDetails(companionMaterialNumber, resourceResolver,
							solrSearchService, country, catalogStructureUpdateService);
					if(null != productDetails) {
						responseArray.add(productDetails);
					}
					
				}

			}
		}
		return responseArray;
	}

	private JsonObject getVarientHPDetails(String skuId, ResourceResolver resourceResolver,
			SolrSearchService solrSearchService, String country, CatalogStructureUpdateService catalogStructureUpdateService) throws RepositoryException {
		String labelDescription = StringUtils.EMPTY;
		String size = StringUtils.EMPTY;
		JsonObject companionProdDataJson = null;
		Resource varaiantResource =  catalogStructureUpdateService.getProductFromLookUp(resourceResolver, skuId, CommonConstants.MATERIAL_NUMBER);
		Resource baseHpResource	= CommonHelper.getBaseHpResourceFromLookUp(varaiantResource);
		if (null != varaiantResource) {
			companionProdDataJson = new JsonObject();
			ValueMap baseHpValueMap = baseHpResource.getValueMap();
			Resource variantHpResource = varaiantResource.getChild(CommonConstants.HP);
			if (null != variantHpResource) {
				ValueMap variantValueMap = variantHpResource.adaptTo(ValueMap.class);
				if (null != variantValueMap.get(CommonConstants.SIZE_QTY, String.class)) {
					size = variantValueMap.get(CommonConstants.SIZE_QTY, String.class) + CommonConstants.SPACE
							+ variantValueMap.get(CommonConstants.SIZE_UOM, String.class);
				}
			}
			if (null != baseHpValueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class)) {
				labelDescription = baseHpValueMap.get(CommonConstants.LABEL_DESCRIPTION, String.class);
			}

			String status = CommonHelper.getRuoFromRegionDetails(baseHpResource, country, skuId);
			if(StringUtils.isNotBlank(status) && country.equalsIgnoreCase("jp") && status.equals("IVD")) {
				status = CommonHelper.getTranslatedRegulatoryStatus("JP_IVD", resourceResolver, solrConfig);
			}

			JsonParser jsonParser = new JsonParser();
			String cloneArrayString = baseHpValueMap.get(CommonConstants.CLONE, String.class);
			if(!StringUtils.isEmpty(cloneArrayString)) {
				JsonArray cloneArray = (JsonArray) jsonParser.parse(cloneArrayString);
				if (cloneArray == null || cloneArray.size() <= 0) {
					String conjugateArrayString = baseHpValueMap.get(CommonConstants.CONJUGATE, String.class);
					cloneArray = (JsonArray) jsonParser.parse(conjugateArrayString);
				}
				String clone = getCloneName(cloneArray);
				
				String imagePath = CommonHelper.getGlobalThumbnailImage(resourceResolver, variantHpResource,
						externalizerService, bdbApiEndpointService);
				companionProdDataJson.addProperty("imageUrl", imagePath);
				String productUrl = CommonHelper.getPdpProductUrl(variantHpResource);
				String url = StringUtils.EMPTY;
				url = getProductUrl(productUrl, url);
				companionProdDataJson.addProperty(CommonConstants.PRODUCT_URL, url);
				companionProdDataJson.addProperty(CommonConstants.PRODUCT_TITLE_LABEL, labelDescription);
				companionProdDataJson.addProperty(CommonConstants.SIZE_LABEL, size);
				companionProdDataJson.addProperty(CommonConstants.CATALOG_NUMBER_KEY, skuId);
				companionProdDataJson.addProperty("cloneId", clone);
				companionProdDataJson.addProperty(CommonConstants.STATUS, status);
			}
			

		

			

		}
				
		
		return companionProdDataJson;
	}

	public String getProductUrl(String productUrl, String url) {
		if (StringUtils.isNotBlank(productUrl)) {
			String rummode = CommonHelper.getRunMode(bdbApiEndpointService);
			if (StringUtils.isNotEmpty(rummode) && rummode.equalsIgnoreCase(CommonConstants.AUTHOR)) {
				url = CommonConstants.CONTENT + CommonConstants.SINGLE_SLASH + CommonConstants.BDB
						+ CommonConstants.SINGLE_SLASH + "{region}" + CommonConstants.SINGLE_SLASH + "{country}"
						+ CommonConstants.SINGLE_SLASH + "{language-country}" + productUrl+ ".html";
			} else if (StringUtils.isNotEmpty(rummode) && rummode.equalsIgnoreCase(CommonConstants.PUBLISH)) {
				url = CommonConstants.SINGLE_SLASH + "{language-country}" + productUrl.replace("pdp.", "") +".html";
			}
		}
		return url;
	}

	/**
	 * Gets the clone name.
	 *
	 * @param jsonArray the json array
	 * @return the clone name
	 */
	private String getCloneName(JsonArray jsonArray) {
		String cloneName = StringUtils.EMPTY;
		if (null != jsonArray && jsonArray.size() > 0) {
			JsonObject cloneObj = jsonArray.get(0).getAsJsonObject();
			if (cloneObj.has(CommonConstants.TDS_CLONE_NAME))
				cloneName = cloneObj.get(CommonConstants.TDS_CLONE_NAME).getAsString();
		}
		return cloneName;
	}

}
