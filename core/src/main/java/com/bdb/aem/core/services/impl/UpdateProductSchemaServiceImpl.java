package com.bdb.aem.core.services.impl;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.UpdateProductSchemaService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagConstants;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.solr.client.solrj.SolrServerException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


/**
 * The Class UpdateProductSchemaServiceImpl.
 */
@Component(service = UpdateProductSchemaService.class, immediate = true)
public class UpdateProductSchemaServiceImpl implements UpdateProductSchemaService {

	
	/** The Constant LOGGER. */
	private static final Logger logger = LoggerFactory.getLogger(UpdateProductSchemaServiceImpl.class);

    /** formatter for the target output format (yyyy-mm-dd) */
    private static  final SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");

    /** The bdb api endpoint service. */
	@Reference
	BDBApiEndpointService bdbApiEndpointService;
	
	/** The externalizer service. */
	@Reference
	ExternalizerService externalizerService;
	
	@Reference
	CatalogStructureUpdateService catalogStructureUpdateService;
	
	/** The solr config. */
	@Reference
	BDBSearchEndpointService solrConfig;
	
	/**
	 * Gets the product announcement.
	 *
	 * @param marketingResource the marketing resource
	 * @param request           the request
	 * @param productJson       the product json
	 * @param resourceResolver  the resource resolver
	 * @param externalizerService the externalizer service
	 * @param catalogNumber the catalog number
	 * @param solrSearchService the solr search service
	 * @return the product announcement
	 * @throws RepositoryException 
	 */
	@Override
	public JsonObject getProductAnnouncement(Resource marketingResource, SlingHttpServletRequest request,
			JsonObject productJson, ResourceResolver resourceResolver,ExternalizerService externalizerService,String catalogNumber, SolrSearchService solrSearchService) throws RepositoryException {
		logger.debug("Get getProductAnnouncement configuration");
		String neutralizationActivity = StringUtils.EMPTY;
		String targetMW= StringUtils.EMPTY;
		String assayRange= StringUtils.EMPTY;
		if (null != marketingResource && StringUtils.isEmpty(request.getParameter(CommonConstants.FORM_DATA))) {
			logger.debug("Reading region specific data under marketing node");
			logger.info("Reading region specific data under marketing node");
			ValueMap varientValueMap = marketingResource.getValueMap();
			String productAnnouncement = getValueMapProperty(varientValueMap, CommonConstants.PRODUCT_ANNOUNCEMENT);
			String paDescription = getValueMapProperty(varientValueMap, CommonConstants.PA_DESCRIPTION);
			String startDate = getValueMapProperty(varientValueMap,CommonConstants.START_DATE);
            String endDate = getValueMapProperty(varientValueMap,CommonConstants.END_DATE);
			String paFAQTitle = getValueMapProperty(varientValueMap, CommonConstants.FAQ_TITLE);
			String regionalDisclaimers = getValueMapProperty(varientValueMap, CommonConstants.REGIONAL_DISCLAIMER);
			String paViewMoreCTA = getValueMapProperty(varientValueMap, CommonConstants.PA_VIEWMORE_CTA);
			String openNewTab = getValueMapProperty(varientValueMap, CommonConstants.OPEN_NEW_TAB);
			String disclaimerStatus = getValueMapProperty(varientValueMap, CommonConstants.DISCLAIMER_STATUS);
			String moreInfoLink = getValueMapProperty(varientValueMap, CommonConstants.MORE_INFO_LINK);
			String viewMoreFaqLabel = getValueMapProperty(varientValueMap, CommonConstants.VIEW_MORE_LABEL);
			String viewMoreFaqLink = getValueMapProperty(varientValueMap, CommonConstants.VIEW_MORE_LINK);
			String smessage = getValueMapProperty(varientValueMap, CommonConstants.SPECIAL_MESSAGE);
			String smessageStatus = getValueMapProperty(varientValueMap, CommonConstants.SPECIAL_MESSAGE_STATUS);
			String altText = getValueMapProperty(varientValueMap, CommonConstants.ALT_TEXT);
			String replacedProductId = getValueMapProperty(varientValueMap, CommonConstants.REPLACED_PRODUCT_ID);
			String displayBulkReagentQuote = getValueMapProperty(varientValueMap, CommonConstants.DISPLAY_BULK_REAGENT_QUOTE);
			String testimonialId=getValueMapProperty(varientValueMap,CommonConstants.TESTIMONIAL_ID);
			String videoId = getValueMapProperty(varientValueMap,CommonConstants.VIDEO_ID);
			Resource marketingParentResource = marketingResource.getParent();
            Resource productResource = marketingParentResource.getParent();
            Resource baseHpResource = CommonHelper.getBaseHpResourceFromLookUp(productResource);
            
            if(null!=catalogNumber) {
				/* return true if dates are valid */
	            if(!(checkProductAnnouncementDateRange(startDate,endDate))) {
	            	return productJson;
	            }
            }
            		
            if (null != baseHpResource ) {
				ValueMap baseValueMapAttributes = baseHpResource.getValueMap();
				String thumbnailImagePath = CommonHelper.getGlobalThumbnailImage(resourceResolver, productResource.getChild(CommonConstants.HP),
						externalizerService, bdbApiEndpointService);
				productJson.addProperty(CommonConstants.PRODUCT_IMAGE_HREF, thumbnailImagePath);
				if (null != replacedProductId) {
					Resource replacementVariantResource = catalogStructureUpdateService
							.getProductFromLookUp(resourceResolver, replacedProductId, CommonConstants.MATERIAL_NUMBER);
					if (null != replacementVariantResource) {
						String productUrl = CommonHelper
								.getPdpProductUrl(replacementVariantResource.getChild(CommonConstants.HP));
						if (StringUtils.isNotBlank(productUrl)) {
							String rummode = CommonHelper.getRunMode(bdbApiEndpointService);
							if (StringUtils.isNotEmpty(rummode) && rummode.equalsIgnoreCase(CommonConstants.AUTHOR)) {
								productUrl = CommonConstants.CONTENT + CommonConstants.SINGLE_SLASH
										+ CommonConstants.BDB + CommonConstants.SINGLE_SLASH + "{region}"
										+ CommonConstants.SINGLE_SLASH + "{country}" + CommonConstants.SINGLE_SLASH
										+ "{language-country}" + productUrl + ".html";
							} else if (StringUtils.isNotEmpty(rummode)
									&& rummode.equalsIgnoreCase(CommonConstants.PUBLISH)) {
								productUrl = CommonConstants.SINGLE_SLASH + "{language-country}"
										+ productUrl.replace("pdp.", "");
							}
							productJson.addProperty(CommonConstants.PRODUCT_CATALOG_HREF, productUrl);
						}
					}
				}

				neutralizationActivity = getValueMapProperty(baseValueMapAttributes,
						CommonConstants.NEUTRALIZATION_ACTIVITY);
				targetMW = getValueMapProperty(baseValueMapAttributes, CommonConstants.TARGET_MW);
				assayRange = getValueMapProperty(baseValueMapAttributes, CommonConstants.ASSAY_RANGE);
			}
           
			
			String[] faqProperties = varientValueMap.get(CommonConstants.FAQ_PROPERTY, String[].class);
			JsonArray jsonArray = new JsonArray();
			JsonObject productInfoJson = new JsonObject();
			getFaqProperties(faqProperties, jsonArray);
			String cqTag = getCqTagProperty(request, resourceResolver);
			Tag tagValue = getTag(resourceResolver, cqTag);
			if(null != tagValue) {
				String tagName = tagValue.getName();
				String tagTitle = tagValue.getTitle();

				productJson.addProperty(CommonConstants.PRODUCT_SKU, tagName);
				productJson.addProperty(CommonConstants.PRODUCT_NAME, tagTitle);
			}
			
			productJson.addProperty(CommonConstants.PRODUCT_ANNOUNCEMENT, productAnnouncement);
			productJson.addProperty(CommonConstants.START_DATE, startDate);
			productJson.addProperty(CommonConstants.END_DATE, endDate);
			productJson.addProperty(CommonConstants.PA_DESCRIPTION, paDescription);
			productJson.addProperty(CommonConstants.FAQ_TITLE, paFAQTitle);
			productJson.addProperty(CommonConstants.REGIONAL_DISCLAIMER, regionalDisclaimers);
			productJson.addProperty(CommonConstants.PA_VIEWMORE_CTA, paViewMoreCTA);
			productJson.addProperty(CommonConstants.OPEN_NEW_TAB, openNewTab);
			productJson.addProperty(CommonConstants.DISCLAIMER_STATUS, disclaimerStatus);
			productJson.addProperty(CommonConstants.MORE_INFO_LINK, moreInfoLink);
			productJson.addProperty(CommonConstants.VIEW_MORE_LABEL, viewMoreFaqLabel);
			productJson.addProperty(CommonConstants.VIEW_MORE_LINK, viewMoreFaqLink);
			productJson.addProperty(CommonConstants.SPECIAL_MESSAGE_STATUS, smessageStatus);
			productJson.addProperty(CommonConstants.NEUTRALIZATION_ACTIVITY, neutralizationActivity);
            productJson.addProperty(CommonConstants.TARGET_MW, targetMW);
            productJson.addProperty(CommonConstants.ASSAY_RANGE, assayRange);
			productJson.addProperty(CommonConstants.TESTIMONIAL_ID,testimonialId);
			productJson.addProperty(CommonConstants.DISPLAY_BULK_REAGENT_QUOTE, displayBulkReagentQuote);
			productJson.addProperty(CommonConstants.VIDEO_ID,videoId);
			if(CommonConstants.TRUE.equals(smessageStatus)) {
                productInfoJson.addProperty(CommonConstants.SPECIAL_MESSAGE, smessage);
                productInfoJson.addProperty(CommonConstants.ALT_TEXT, altText);
            }
            productJson.add(CommonConstants.SURCHARGE_DISCLAIMER, productInfoJson);
            productJson.addProperty(CommonConstants.REPLACED_PRODUCT_ID, replacedProductId);
			productJson.add(CommonConstants.FAQ_MAP, jsonArray);

		}
		return productJson;
	}

	/**
	 * Get string date in string format
	 *
	 * @param string dates
	 * @return the boolean value according to range.
	 */
	public Boolean checkProductAnnouncementDateRange(String startDate, String endDate)  {
		Date currentDate, startingDate, endingDate;
		try {
			currentDate = getDateWithoutTimeUsingFormat();
            startingDate = convertStringDateInDateFormat(startDate);
            endingDate = convertStringDateInDateFormat(endDate);
		} catch (ParseException e) {
			logger.error("Parse Exception, Invalid date format, {}",e.getMessage());
			return false;
		}

        if(null == startingDate && null == endingDate) {
        	return true;
        }
        if(null == endingDate && (currentDate.after(startingDate) || currentDate.equals(startingDate))) {
        	return true;
        }
        return (currentDate.after(startingDate) || currentDate.equals(startingDate)) && (currentDate.before(endingDate))
                || currentDate.equals(endingDate);
    }

	/**
	 * Gets the string date in date format.
	 * @return the string date in date format
     */
    private static Date convertStringDateInDateFormat(String inputDate) throws ParseException {
        if(null == inputDate || inputDate.trim().isEmpty()) return null;
		try{
			/* returning the date if it is in correct format */
            return outputFormatter.parse(inputDate);
        } catch (ParseException e) {
			/* try to parse with (MM/dd/yyyy) format */
            SimpleDateFormat inputFormatter = new SimpleDateFormat("MM/dd/yyyy");
			/* ensuring strict parsing */
			inputFormatter.setLenient(false);
            return inputFormatter.parse(inputDate);
        }
    }
	
	/**
	 * @return the date in date format without time.
	 */
	private static Date getDateWithoutTimeUsingFormat() throws ParseException{
		return outputFormatter.parse(outputFormatter.format(new Date()));
	}

	/**
	 * Gets the product SKU details.
	 *
	 * @param variantResource the variant resource
	 * @param request the request
	 * @param productJson the product json
	 * @param resourceResolver the resource resolver
	 * @param externalizerService the externalizer service
	 * @param product the product
	 * @param solrSearchService the solr search service
	 * @return the product SKU details
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SolrServerException the solr server exception
	 */
	@Override
	public JsonObject getProductSKUDetails(Resource variantResource, SlingHttpServletRequest request,
			JsonObject productJson, ResourceResolver resourceResolver, ExternalizerService externalizerService,
			String product, SolrSearchService solrSearchService, Resource baseHpResource, String country) throws IOException, SolrServerException {
		logger.debug("Get getProductSKUDetails configuration");
		ValueMap baseHpValueMap = baseHpResource.getValueMap();
		String name = getValueMapProperty(baseHpValueMap, CommonConstants.LABEL_DESCRIPTION);
		String cloneName = CommonHelper.getTdsCloneIdFromHp(baseHpValueMap);
		String brand = getValueMapProperty(baseHpValueMap, CommonConstants.BRAND_KEY);
		String alternativeName = getValueMapProperty(baseHpValueMap, CommonConstants.ALTERNATIVE_NAME);
		String volPerTest = getValueMapProperty(baseHpValueMap, CommonConstants.VOL_PER_TEST);
		String isoType = getIsoType(baseHpValueMap);
		String reactivity = getReactivity(baseHpValueMap);
		String application = getApplication(baseHpValueMap);
		String immunogen = getImmunogen(baseHpValueMap);
		String workshopNumber = getWorkShopNumber(baseHpValueMap);
		String entrezGeneId = getValueMapProperty(baseHpValueMap, CommonConstants.ENTREZ_GENE_ID);
		String storageBuffer = getValueMapProperty(baseHpValueMap, CommonConstants.STORAGE_BUFFER);
		String regulatoryStatus = CommonHelper.getRuoFromRegionDetails(baseHpResource, country, product);
		if(StringUtils.isNotBlank(regulatoryStatus) && country.equalsIgnoreCase("jp") && regulatoryStatus.equals("IVD")) {
		 regulatoryStatus = CommonHelper.getTranslatedRegulatoryStatus("JP_IVD", resourceResolver,solrConfig);
		}
		String displayBulkReagentQuote = getValueMapProperty(baseHpValueMap, CommonConstants.DISPLAY_BULK_REAGENT_QUOTE);
		
		JsonArray options = getVariantsArray(variantResource);

		getProductUrlAndImageUrl(request, productJson, resourceResolver, externalizerService, variantResource,
				solrSearchService);

		productJson.addProperty("name_t", name);
		productJson.addProperty(CommonConstants.CLONE, cloneName);
		productJson.addProperty(CommonConstants.BRAND, brand);
		productJson.addProperty(CommonConstants.ALTERNATIVE_NAME, alternativeName);
		productJson.addProperty(CommonConstants.VOL_PER_TEST, volPerTest);
		productJson.addProperty(CommonConstants.ISOTYPE, isoType);
		productJson.addProperty(CommonConstants.REACTIVITY, reactivity);
		productJson.addProperty(CommonConstants.APPLICATION, application);
		productJson.addProperty(CommonConstants.IMMUNOGEN, immunogen);
		productJson.addProperty(CommonConstants.WORKSHOP_NUMBER, workshopNumber);
		productJson.addProperty(CommonConstants.ENTREZ_GENE_ID, entrezGeneId);
		productJson.addProperty(CommonConstants.STORAGE_BUFFER, storageBuffer);
		productJson.addProperty(CommonConstants.REGULATORY_STATUS, regulatoryStatus);
		productJson.addProperty(CommonConstants.DISPLAY_BULK_REAGENT_QUOTE, displayBulkReagentQuote);

		if (null != baseHpValueMap.get(CommonConstants.BD_FORMAT)) {
			JsonArray formatDetailsJsonArray = new JsonParser()
					.parse(baseHpValueMap.get(CommonConstants.BD_FORMAT).toString()).getAsJsonArray();
			JsonObject formatDetailsJson = null;
			for (JsonElement formatDetailsElement : formatDetailsJsonArray)
				formatDetailsJson = formatDetailsElement.getAsJsonObject();
			if (null != formatDetailsJson && formatDetailsJson.has(CommonConstants.EMMISSION_MAX)) {
				String emmisionmax = formatDetailsJson.get(CommonConstants.EMMISSION_MAX).getAsString().concat(CommonConstants.SPACE).concat(CommonConstants.NM);
				productJson.addProperty(CommonConstants.EMISSION_MAX, emmisionmax);
			}
			if (null != formatDetailsJson && formatDetailsJson.has(CommonConstants.EXCITATION_MAX)) {
				String excitationmax = formatDetailsJson.get(CommonConstants.EXCITATION_MAX).getAsString().concat(CommonConstants.SPACE).concat(CommonConstants.NM);
				excitationmax = excitationmax.replace(CommonConstants.COMMA, CommonConstants.SPACE + CommonConstants.NM + CommonConstants.COMMA);
				productJson.addProperty(CommonConstants.EXCITATION_MAX_CP, excitationmax);
			}
			if (null != formatDetailsJson && formatDetailsJson.has(CommonConstants.DYE_NAME)) {
				String dyeName = formatDetailsJson.get(CommonConstants.DYE_NAME).getAsString();
				productJson.addProperty("currentFormat", dyeName);
			}
			if (null != formatDetailsJson  && formatDetailsJson.has(CommonConstants.LASER_COLOR) && formatDetailsJson.has(CommonConstants.LASER_WAVELENGTH)) {
				String excitationSource = formatDetailsJson.get(CommonConstants.LASER_COLOR).getAsString() + " "
						+ formatDetailsJson.get(CommonConstants.LASER_WAVELENGTH).getAsString().concat(CommonConstants.SPACE).concat(CommonConstants.NM);
				excitationSource = excitationSource.replace(CommonConstants.COMMA, CommonConstants.SPACE + CommonConstants.NM + CommonConstants.COMMA);
				productJson.addProperty(CommonConstants.EXCITATION_SOURCE, excitationSource);
			}
		}
		JsonObject variantsJson = new JsonObject();
		if(options.size() > 0) {
			variantsJson.add("options", options);
			productJson.add("variants", variantsJson);
		}
		return productJson;
	}
    
	/**
	 * Gets the product url and image url.
	 *
	 * @param request the request
	 * @param productJson the product json
	 * @param resourceResolver the resource resolver
	 * @param externalizerService the externalizer service
	 * @param product the product
	 * @param solrSearchService the solr search service
	 * @return the product url and image url
	 */
	public void getProductUrlAndImageUrl(SlingHttpServletRequest request, JsonObject productJson,
			ResourceResolver resourceResolver, ExternalizerService externalizerService, Resource variantResource,
			SolrSearchService solrSearchService) {
		if (null != variantResource) {
			String thumbnailImagePath = CommonHelper.getGlobalThumbnailImage(resourceResolver,variantResource,externalizerService,bdbApiEndpointService); 
			String productUrl = CommonHelper.getPdpProductUrl(variantResource);
			if (StringUtils.isNotBlank(productUrl)) {
				String rummode = CommonHelper.getRunMode(bdbApiEndpointService);
				if (StringUtils.isNotEmpty(rummode) && rummode.equalsIgnoreCase(CommonConstants.AUTHOR)) {
					productUrl = CommonConstants.CONTENT + CommonConstants.SINGLE_SLASH + CommonConstants.BDB + CommonConstants.SINGLE_SLASH + "{region}" + CommonConstants.SINGLE_SLASH +"{country}" + CommonConstants.SINGLE_SLASH + "{language-country}"
							+ productUrl;
				}else if (StringUtils.isNotEmpty(rummode) && rummode.equalsIgnoreCase(CommonConstants.PUBLISH)) {
					productUrl = CommonConstants.SINGLE_SLASH + "{language-country}"
							+ productUrl.replace("pdp.", "");
				}
				productUrl = productUrl + ".html";
			}
			//Logic for data image comparison
			String noDataAvailablePath = "/content/dam/bdb/general/placeholder/placeholder.png";
			String tempThumbnailImagePath = noDataAvailablePath;
			Resource thumbnailImagePathRes=  resourceResolver.getResource(thumbnailImagePath + "/jcr:content/metadata");
			TagManager tagManager =resourceResolver.adaptTo(TagManager.class);
			Tag [] tag=	 tagManager.getTags(thumbnailImagePathRes);
			
			if(tag!=null && tag.length >0) {
				for (int i = 0; i <= tag.length - 1; i++) {
					if(thumbnailImagePath.contains("_base") && (tag[i].getTitle().equals(CommonConstants.THUMBNAIL) || tag[i].getTitle().equals(CommonConstants.PRIMARY))) {
						tempThumbnailImagePath = thumbnailImagePath;
						break;
					}
				}
			}
			
			productJson.addProperty("imageUrl", tempThumbnailImagePath);
			productJson.addProperty("thumbnailImageUrl", tempThumbnailImagePath);
			productJson.addProperty("productUrl", productUrl);
			
		}
	}
	
	/**
	 * Gets the variants array.
	 *
	 * @param varientValueMap the varient value map
	 * @return the variants array
	 */
	private JsonArray getVariantsArray(Resource hpResource) {
		JsonArray sizeJsonArray = new JsonArray();
		Iterator<Resource> iterator = hpResource.getParent().listChildren();
		while (iterator.hasNext()) {
			Resource nextRes = iterator.next();
			if(null != nextRes && nextRes.getName().equals(CommonConstants.HP)) {
					ValueMap variantValueMap = nextRes.adaptTo(ValueMap.class);
					JsonObject sizeJsontObject = new JsonObject();
					sizeJsontObject.addProperty("label",
							variantValueMap.get(CommonConstants.SIZE_QTY).toString()
									+ variantValueMap.get(CommonConstants.SIZE_UOM).toString());
					sizeJsontObject.addProperty("id",
							variantValueMap.get(CommonConstants.MATERIAL_NUMBER).toString());
					sizeJsonArray.add(sizeJsontObject);
			}
		}
		return sizeJsonArray;
	}
	
	/**
	 * Gets the iso type.
	 *
	 * @param varientValueMap the varient value map
	 * @return the iso type
	 */
	public String getIsoType(ValueMap varientValueMap) {
		StringBuilder isotypeStringBuilder = new StringBuilder();
		if (null != varientValueMap.get(CommonConstants.CLONE)) {
			String clone = varientValueMap.get(CommonConstants.CLONE).toString();
			JsonArray cloneArray = new JsonParser().parse(clone).getAsJsonArray();

			for (JsonElement cloneElement : cloneArray) {
				JsonObject cloneJsonObject = cloneElement.getAsJsonObject();
				isotypeStringBuilder
						.append(cloneJsonObject.get(CommonConstants.HOST_SPECIES_KEY).getAsString() + CommonConstants.SPACE);
				isotypeStringBuilder
						.append(cloneJsonObject.get(CommonConstants.HOST_STRAIN_KEY).getAsString() + CommonConstants.SPACE);
				isotypeStringBuilder
						.append(cloneJsonObject.get(CommonConstants.ISOTYPE_KEY).getAsString());
			}
		}
		return isotypeStringBuilder.toString();
	}
	
	/**
	 * Gets the reactivity.
	 *
	 * @param varientValueMap the varient value map
	 * @return the reactivity
	 */
	private String getReactivity(ValueMap varientValueMap) {
		StringBuilder reactivityStringBuilder = new StringBuilder();
		if(null != varientValueMap.get(CommonConstants.SPECIES_REACTIVITY_KEY) && StringUtils.isNotEmpty(varientValueMap.get(CommonConstants.SPECIES_REACTIVITY_KEY).toString())) {
			String reactivity = varientValueMap.get(CommonConstants.SPECIES_REACTIVITY_KEY).toString();
			JsonArray reactivityArray = new JsonParser().parse(reactivity).getAsJsonArray();
			int index = 0;
			for (JsonElement reactivityElement : reactivityArray) {
				index = index + 1;
				JsonObject reactivityJsontObject = reactivityElement.getAsJsonObject();
				reactivityStringBuilder.append(reactivityJsontObject.get(CommonConstants.SPECIES_DESC).getAsString()
						+ CommonConstants.SPACE	);
					
				String statusDesc = reactivityJsontObject.get(CommonConstants.REACTIVITY_STATUS_DES).getAsString();
				if( StringUtils.isNotBlank(statusDesc) ) {
					reactivityStringBuilder.append( "(" + statusDesc + ")");
				}
				if (index < reactivityArray.size()) {
					reactivityStringBuilder.append(", ");
				}
			}	
		}
		
		return reactivityStringBuilder.toString();
	}
	
	/**
	 * Gets the application.
	 *
	 * @param varientValueMap the varient value map
	 * @return the application
	 */
	private String getApplication(ValueMap varientValueMap) {
		String application = varientValueMap.get(CommonConstants.APPLICAATION_KEY).toString();
		JsonArray applicationArray = new JsonParser().parse(application).getAsJsonArray();
		StringBuilder applicationStringBuilder = new StringBuilder();
		int index = 0;
		for (JsonElement applicationElement : applicationArray) {
			index = index + 1;
			JsonObject applicationJsontObject = applicationElement.getAsJsonObject();
			applicationStringBuilder
					.append(applicationJsontObject.get(CommonConstants.APPLICATION_DESC).getAsString() + CommonConstants.SPACE );
			
			String appDesc = applicationJsontObject.get(CommonConstants.APPLICATION_STATUS_DESC).getAsString();
			if( StringUtils.isNotBlank(appDesc)) {
				applicationStringBuilder
						.append( "(" + appDesc + ")");
			}
			
			if (index < applicationArray.size()) {
				applicationStringBuilder.append(", ");
			}
		}
		return applicationStringBuilder.toString();
	}
	
	/**
	 * Gets the immunogen.
	 *
	 * @param varientValueMap the varient value map
	 * @return the immunogen
	 */
	private String getImmunogen(ValueMap varientValueMap) {
		String clone = varientValueMap.get(CommonConstants.CLONE).toString();
		JsonArray cloneArray = new JsonParser().parse(clone).getAsJsonArray();

		StringBuilder immunogenStringBuilder = new StringBuilder();
		for (JsonElement cloneElement : cloneArray) {
			JsonObject cloneJsontObject = cloneElement.getAsJsonObject();
			immunogenStringBuilder.append(cloneJsontObject.get(CommonConstants.IMMUNOGEN_KEY).getAsString());
		}
		return immunogenStringBuilder.toString();
	}
	
	/**
	 * Gets the work shop number.
	 *
	 * @param varientValueMap the varient value map
	 * @return the work shop number
	 */
	private String getWorkShopNumber(ValueMap varientValueMap) {
		String clone = varientValueMap.get(CommonConstants.CLONE).toString();
		JsonArray cloneArray = new JsonParser().parse(clone).getAsJsonArray();

		StringBuilder workShopNumberBuilder = new StringBuilder();
		for (JsonElement cloneElement : cloneArray) {
			JsonObject cloneJsontObject = cloneElement.getAsJsonObject();
			workShopNumberBuilder.append(cloneJsontObject.get(CommonConstants.WORKSHOP_NUMBER).getAsString());
		}
		return workShopNumberBuilder.toString();
	}
	
	/**
	 * Sets the product announce.
	 *
	 * @param request           the request
	 * @param resourceResolver  the resource resolver
	 * @param marketingResource the marketing resource
	 */
	@Override
	public void setProductAnnounce(SlingHttpServletRequest request, ResourceResolver resourceResolver,
			Resource marketingResource) {
		try {
			logger.debug("Set ProductAnnouncement configuration from admin page to catalog structure");
			Node schemaNode = null;
			if (null != request.getParameter("form_data") && null != resourceResolver) {				
				Session session = resourceResolver.adaptTo(Session.class);
				String formData = request.getParameter(CommonConstants.FORM_DATA);
				JsonParser parser = new JsonParser();
				JsonObject formJsonObject = null;
				formJsonObject = getFormObject(formData, parser, formJsonObject);
				String announcement = getJsonProperty(formJsonObject, CommonConstants.PRODUCT_ANNOUNCEMENT);
				String description = getJsonProperty(formJsonObject, CommonConstants.PA_DESCRIPTION);
				String startDate = getJsonProperty(formJsonObject, CommonConstants.START_DATE);
				String endDate = getJsonProperty(formJsonObject, CommonConstants.END_DATE);
				String faqTitle = getJsonProperty(formJsonObject, CommonConstants.FAQ_TITLE);
				String moreInformationLable = getJsonProperty(formJsonObject, CommonConstants.PA_VIEWMORE_CTA);
				String regionalDisclaimer = getJsonProperty(formJsonObject, CommonConstants.REGIONAL_DISCLAIMER);
				String moreInfoLink = getJsonProperty(formJsonObject, CommonConstants.MORE_INFO_LINK);
				String opeNewTab = getJsonProperty(formJsonObject, CommonConstants.OPEN_NEW_TAB);
				String disclaimerStatus = getJsonProperty(formJsonObject, CommonConstants.DISCLAIMER_STATUS);
				String viewMoreFaqLabel = getJsonProperty(formJsonObject, CommonConstants.VIEW_MORE_LABEL);
				String viewMoreFaqLink = getJsonProperty(formJsonObject, CommonConstants.VIEW_MORE_LINK);
				
				String smessageStatus = getJsonProperty(formJsonObject, CommonConstants.SPECIAL_MESSAGE_STATUS);
				String smessage = getJsonProperty(formJsonObject, CommonConstants.SPECIAL_MESSAGE);
				String altText = getJsonProperty(formJsonObject, CommonConstants.ALT_TEXT);
				String replacedProductId = getJsonProperty(formJsonObject, CommonConstants.REPLACED_PRODUCT_ID);
				String neutralizationActivity = getJsonProperty(formJsonObject, CommonConstants.NEUTRALIZATION_ACTIVITY);
                String targetMW = getJsonProperty(formJsonObject, CommonConstants.TARGET_MW);
                String assayRange = getJsonProperty(formJsonObject, CommonConstants.ASSAY_RANGE);
                String pdpAttributesStatus = getJsonProperty(formJsonObject, CommonConstants.PDP_ATTRIBUTES_STATUS);
        		String displayBulkReagent= getJsonProperty(formJsonObject, CommonConstants.DISPLAY_BULK_REAGENT_QUOTE);
				String testimonialId=getJsonProperty(formJsonObject,CommonConstants.TESTIMONIAL_ID);
				String videoId=getJsonProperty(formJsonObject,CommonConstants.VIDEO_ID);
				
				JsonArray faqListArray = formJsonObject.getAsJsonArray(CommonConstants.FAQ_LIST);
				List<String> list = getFaqList(faqListArray);
				Resource marketingParentResource = marketingResource.getParent();
                setAssayProcedure(neutralizationActivity, targetMW, assayRange, pdpAttributesStatus,
						marketingParentResource);
				if (null != marketingResource) {
					schemaNode = marketingResource.adaptTo(Node.class);
					setPropertyToNode(announcement, schemaNode, CommonConstants.PRODUCT_ANNOUNCEMENT);
					setPropertyToNode(description, schemaNode, CommonConstants.PA_DESCRIPTION);
					setPropertyToNode(startDate, schemaNode, CommonConstants.START_DATE);
					setPropertyToNode(endDate, schemaNode, CommonConstants.END_DATE);
					setPropertyToNode(faqTitle, schemaNode, CommonConstants.FAQ_TITLE);
					setPropertyToNode(moreInformationLable, schemaNode, CommonConstants.PA_VIEWMORE_CTA);
					setPropertyToNode(regionalDisclaimer, schemaNode, CommonConstants.REGIONAL_DISCLAIMER);
					setPropertyToNode(moreInfoLink, schemaNode, CommonConstants.MORE_INFO_LINK);
					setPropertyToNode(opeNewTab, schemaNode, CommonConstants.OPEN_NEW_TAB);
					setPropertyToNode(disclaimerStatus, schemaNode, CommonConstants.DISCLAIMER_STATUS);
					setPropertyToNode(viewMoreFaqLabel, schemaNode, CommonConstants.VIEW_MORE_LABEL);
					setPropertyToNode(viewMoreFaqLink, schemaNode, CommonConstants.VIEW_MORE_LINK);
					
					setPropertyToNode(smessageStatus, schemaNode, CommonConstants.SPECIAL_MESSAGE_STATUS);
					setPropertyToNode(smessage, schemaNode, CommonConstants.SPECIAL_MESSAGE);
					setPropertyToNode(altText, schemaNode, CommonConstants.ALT_TEXT);
					setPropertyToNode(replacedProductId, schemaNode, CommonConstants.REPLACED_PRODUCT_ID);
					setPropertyToNode(displayBulkReagent, schemaNode, CommonConstants.DISPLAY_BULK_REAGENT_QUOTE);
					setPropertyToNode(testimonialId,schemaNode,CommonConstants.TESTIMONIAL_ID);
					setPropertyToNode(videoId,schemaNode,CommonConstants.VIDEO_ID);
					String[] str = getStringArray(list);
					schemaNode.setProperty("faqProperty", str);
				}
				session.save();
				logger.debug("Data saved to node {}", schemaNode);
			}
		} catch (ValueFormatException e) {
			logger.error("ValueFormatException {}", e.getMessage());
		} catch (VersionException e) {
			logger.error("VersionException {}", e.getMessage());
		} catch (LockException e) {
			logger.error("LockException {}", e.getMessage());
		} catch (ConstraintViolationException e) {
			logger.error("ConstraintViolationException {}", e.getMessage());
		} catch (RepositoryException e) {
			logger.error("RepositoryException {}", e.getMessage());
		}

	}

	/**
	 * Gets the form object.
	 *
	 * @param formData the form data
	 * @param parser the parser
	 * @param formJsonObject the form json object
	 * @return the form object
	 */
	public JsonObject getFormObject(String formData, JsonParser parser, JsonObject formJsonObject) {
		if (null != formData) {
			JsonElement jsonElement = parser.parse(formData);
			formJsonObject = jsonElement.getAsJsonObject();
		}
		return formJsonObject;
	}

	/**
	 * Sets the assay procedure.
	 *
	 * @param neutralizationActivity the neutralization activity
	 * @param targetMW the target MW
	 * @param assayRange the assay range
	 * @param pdpAttributesStatus the pdp attributes status
	 * @param marketingParentResource the marketing parent resource
	 * @throws RepositoryException the repository exception
	 */
	public void setAssayProcedure(String neutralizationActivity, String targetMW, String assayRange,
			String pdpAttributesStatus, Resource marketingParentResource) throws RepositoryException {
		if(null != marketingParentResource && CommonConstants.TRUE.equals(pdpAttributesStatus) ) {
		    Resource productResource = marketingParentResource.getParent();
		    Resource hpResource = productResource.getChild("hp");
		    Node hpNode = hpResource.adaptTo(Node.class);
		    setPropertyToNode(neutralizationActivity, hpNode, CommonConstants.NEUTRALIZATION_ACTIVITY);
		    setPropertyToNode(targetMW, hpNode, CommonConstants.TARGET_MW);
		    setPropertyToNode(assayRange, hpNode, CommonConstants.ASSAY_RANGE);
		}
	}

	/**
	 * Gets the faq list.
	 *
	 * @param faqListArray the faq list array
	 * @return the faq list
	 */
	public List<String> getFaqList(JsonArray faqListArray) {
		List<String> list = new ArrayList<>();
		for (JsonElement faqList : faqListArray) {
			JsonObject faqObj = faqList.getAsJsonObject();
			String question = getJsonProperty(faqObj, CommonConstants.QUESTION);
			String answer = getJsonProperty(faqObj, CommonConstants.ANSWER);
			String faqItem = question + "&" + answer;
			list.add(faqItem);
		}
		return list;
	}

	/**
	 * Gets the marketing resource.
	 *
	 * @param request          the request
	 * @param resourceResolver the resource resolver
	 * @param solrSearchService the solr search service
	 * @return the marketing resource
	 */
	@Override
	public Resource getMarketingResource(SlingHttpServletRequest request, ResourceResolver resourceResolver, SolrSearchService solrSearchService) {
		Resource usRegionNode = null;
		String tagName = StringUtils.EMPTY;
		try {
			if (null != resourceResolver) {
				Session session = resourceResolver.adaptTo(Session.class);
				if(null != request.getParameter("skuNum")) {
					logger.debug("SkuNumber from API Endpoint");
					tagName = request.getParameter("skuNum");
				}else {
					String cqTagProperty = getCqTagProperty(request, resourceResolver);
					logger.debug("Get marketing resource from Tag selector resource");
					Tag tag = getTag(resourceResolver, cqTagProperty);
					tagName = tag.getName();
				}
				Resource varientResource = catalogStructureUpdateService.getProductFromLookUp(resourceResolver, tagName, CommonConstants.MATERIAL_NUMBER);	
				logger.debug("varientResource {}",varientResource.getPath());
				if(null != varientResource && null != request.getParameter("region")) {
					String region = request.getParameter("region");
					Tag countryTag = getTag(resourceResolver, region);
					usRegionNode = getCountryNode(usRegionNode, session, varientResource, countryTag);
				}
			}
			logger.debug("RegionNode {}", usRegionNode);
		} catch (ItemExistsException e) {
			logger.error("ItemExistsException {}", e.getMessage());
		} catch (PathNotFoundException e) {
			logger.error("PathNotFoundException {}", e.getMessage());
		} catch (VersionException e) {
			logger.error("VersionException {}", e.getMessage());
		} catch (ConstraintViolationException e) {
			logger.error("ConstraintViolationException {}", e.getMessage());
		} catch (LockException e) {
			logger.error("LockException {}", e.getMessage());
		} catch (RepositoryException e) {
			logger.error("RepositoryException {}", e.getMessage());
		}
		return usRegionNode;

	}
	
	/**
	 * Gets the marketing resource.
	 *
	 * @param request          the request
	 * @param resourceResolver the resource resolver
	 * @param solrSearchService the solr search service
	 * @return the marketing resource list
	 */
	@Override
	public List<Resource> getEmeaRegionMarketingReources(SlingHttpServletRequest request,
			ResourceResolver resourceResolver, SolrSearchService solrSearchService) {
		
		List<Resource> emeaRegions = new ArrayList<>(); 
		try {
			if (null != resourceResolver) {
				Session session = resourceResolver.adaptTo(Session.class);
				String emeaCountriesListPath = "/etc/acs-commons/lists/bdb/region-country-mapping/eu/jcr:content/list";
		    	Resource countryListResource = resourceResolver.getResource(emeaCountriesListPath);
				if(null != countryListResource) {
					
		            Iterator<Resource> items = countryListResource.listChildren();
		            while (items.hasNext()) {
		            	Resource emeaRegionNode = null;
		                Resource countryResource = items.next();
		                if (null != countryResource) {
		                    ValueMap properties = countryResource.getValueMap();
		                    String countryId = CommonHelper.getPropertyValue(properties, "value").toLowerCase();
		                    
		                    String cqTagProperty = getCqTagProperty(request, resourceResolver);
		    				logger.debug("Get marketing resource from Tag selector resource");
		    				Tag productTag = getTag(resourceResolver, cqTagProperty);
		    				String tagName = productTag.getName();
		    				
		    				Resource varientResource = catalogStructureUpdateService.getProductFromLookUp(resourceResolver, tagName, CommonConstants.MATERIAL_NUMBER);	
		    				logger.debug("varientResource {}",varientResource.getPath());
		    				if(null != varientResource && null != request.getParameter("region")) {
		    					String regionTag = "bdb:regions/"+countryId;
		    					Tag countryTag = getTag(resourceResolver, regionTag);
		    					emeaRegionNode = getCountryNode(emeaRegionNode, session, varientResource, countryTag);
		    				}
		                }
		                if(emeaRegionNode != null) {
		                	emeaRegions.add(emeaRegionNode);
		                }
		            }
				}
			}
		}
		catch (ItemExistsException e) {
			logger.error("ItemExistsException {}", e.getMessage());
		} catch (PathNotFoundException e) {
			logger.error("PathNotFoundException {}", e.getMessage());
		} catch (VersionException e) {
			logger.error("VersionException {}", e.getMessage());
		} catch (ConstraintViolationException e) {
			logger.error("ConstraintViolationException {}", e.getMessage());
		} catch (LockException e) {
			logger.error("LockException {}", e.getMessage());
		} catch (RepositoryException e) {
			logger.error("RepositoryException {}", e.getMessage());
		}
		return emeaRegions;
	}
	
	/**
	 * Gets the country.
	 *
	 * @param request the request
	 * @param resourceResolver the resource resolver
	 * @return the country
	 */
	public String getCountry (SlingHttpServletRequest request, ResourceResolver resourceResolver) {
		String currentPagePath = request.getParameter(CommonConstants.CURRENT_PAGE);
		String country = "us";
		if(null != currentPagePath && null != resourceResolver.getResource(currentPagePath)) {
			Page currentPage = resourceResolver.getResource(currentPagePath).adaptTo(Page.class);
			country = CommonHelper.getCountry(currentPage);
		}
		return country;
	}

	/**
	 * Gets the country node.
	 *
	 * @param usRegionNode the us region node
	 * @param session the session
	 * @param parentResource the parent resource
	 * @param countryTag the country tag
	 * @return the country node
	 * @throws RepositoryException the repository exception
	 */
	public Resource getCountryNode(Resource usRegionNode, Session session, Resource varientResource, Tag countryTag) throws RepositoryException
			  {
		if (null != countryTag) {
			String country = countryTag.getName();
			// creating marketing node
			if (null != varientResource && null == varientResource.getChild(CommonConstants.MARKETING_NODE)) {
				Node varientResourceNode = varientResource.adaptTo(Node.class);
				varientResourceNode.addNode(CommonConstants.MARKETING_NODE).addNode(country);
				session.save();
				Resource marketingResource = varientResource.getChild(CommonConstants.MARKETING_NODE);
				usRegionNode = marketingResource.getChild(country);				
			} else if (null != varientResource && null != varientResource.getChild(CommonConstants.MARKETING_NODE)) {
				Resource marketingResource = varientResource.getChild(CommonConstants.MARKETING_NODE);
				Node marketingNode = marketingResource.adaptTo(Node.class);
				// creating region node

				if (null == marketingResource.getChild(country)) {
					marketingNode.addNode(country);
					session.save();
				} else {
					usRegionNode = marketingResource.getChild(country);
				}
			}
		}
		return usRegionNode;
	}

	/**
	 * Gets the tag.
	 *
	 * @param resourceResolver the resource resolver
	 * @param cqTagProperty    the cq tag property
	 * @return the tag
	 */
	public Tag getTag(ResourceResolver resourceResolver, String cqTagProperty) {
		Tag tag = null;
		if (null != cqTagProperty) {
			TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
			tag = tagManager.resolve(cqTagProperty);
			logger.debug("Tag selector resource Tag {}", tag);
		}
		return tag;
	}

	/**
	 * Gets the cq tag property.
	 *
	 * @param request          the request
	 * @param resourceResolver the resource resolver
	 * @return the cq tag property
	 */
	public String getCqTagProperty(SlingHttpServletRequest request, ResourceResolver resourceResolver) {
		String cqTagProperty = null;
		if (null != request.getParameter(CommonConstants.CURRENT_PAGE) && null != resourceResolver) {
			String curentPagePath = request.getParameter(CommonConstants.CURRENT_PAGE);
			String resourcePath = curentPagePath + "/jcr:content/root/producttagselector";
			logger.debug("Tag selector resource {}", resourcePath);
			if (null != resourceResolver.getResource(resourcePath)) {
				Resource productTagSelectorRes = resourceResolver.getResource(resourcePath);
				ValueMap productTagSelectorMap = productTagSelectorRes.getValueMap();
				if (null != productTagSelectorMap.get(TagConstants.PN_TAGS)) {
					cqTagProperty = productTagSelectorMap.get(TagConstants.PN_TAGS, String.class);
				}
			}
		}
		return cqTagProperty;

	}

	/**
	 * Gets the value map property.
	 *
	 * @param varientValueMap the varient value map
	 * @param property        the property
	 * @return the value map property
	 */
	public String getValueMapProperty(ValueMap varientValueMap, String property) {
		String propertyValue = null;
		if (null != varientValueMap && null != varientValueMap.get(property)) {
			propertyValue = varientValueMap.get(property, String.class);
		}
		return propertyValue;
	}

	/**
	 * Gets the faq properties.
	 *
	 * @param faqProperties the faq properties
	 * @param jsonArray     the json array
	 * @return the faq properties
	 */
	public void getFaqProperties(String[] faqProperties, JsonArray jsonArray) {
		if(null != faqProperties) {
			for (String faqData : faqProperties) {
				JsonObject faqMap = new JsonObject();
				String[] faqProperty = faqData.split("&");

				if (faqProperty.length != 0) {
					String question = faqProperty[0];
					faqMap.addProperty(CommonConstants.QUESTION, question);
					String answer = faqProperty[1];
					faqMap.addProperty(CommonConstants.ANSWER, answer);
				}
				jsonArray.add(faqMap);
			}
		}
	}

	/**
	 * Gets the json property.
	 *
	 * @param assetObj the asset obj
	 * @param property the property
	 * @return the json property
	 */
	private String getJsonProperty(JsonObject assetObj, String property) {
		if (null != assetObj && null != assetObj.get(property)) {
			property = assetObj.get(property).getAsString();
		}
		return property;
	}

	/**
	 * Sets the property to node.
	 *
	 * @param announcement the announcement
	 * @param schemaNode   the schema node
	 * @param propertyKey  the property key
	 * @throws RepositoryException the repository exception
	 */
	public void setPropertyToNode(String announcement, Node schemaNode, String propertyKey) throws RepositoryException {
		if (null != announcement) {
			schemaNode.setProperty(propertyKey, announcement);
		}
	}

	/**
	 * Gets the string array.
	 *
	 * @param arr the arr
	 * @return the string[]
	 */
	public static String[] getStringArray(List<String> arr) {
		String[] str = new String[arr.size()];
		for (int j = 0; j < arr.size(); j++) {
			str[j] = arr.get(j);
		}
		return str;
	}

}