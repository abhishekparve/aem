
package com.bdb.aem.core.models;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jsoup.Jsoup;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.Externalizer;
import com.day.cq.dam.api.Asset;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import lombok.Getter;

/**
 * Model to set variant hp path value in the session for a PDP page.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PDPSessionModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(PDPSessionModel.class);

	/** The current page. */
	@Inject
	Page currentPage;

	/** The request. */
	@Inject
	SlingHttpServletRequest request;
	
	/** The Response. */
	@Inject
	SlingHttpServletResponse response;

	/** The resolver factory. */
	@Inject
	ResourceResolverFactory resolverFactory;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** The resource resolver. */
	ResourceResolver resourceResolver;
	
	/** The bdb api endpoint service. */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;

	/** The externalizer. */
	@Inject
	Externalizer externalizer;
	
	/** The thumbnailImagePath. */
	private String thumbnailImagePath;
	
	/** The completeThumbnailImagePath with the domain name. */
	private String completeThumbnailImagePath;

	/** The product name. */
	private String productName; 
	
	/** The product Description. */
	@Getter
	private String description;
	
	/** The product Description without html tags. */
	@Getter
	private String plainTextDescription;
	
	/** The product keywords. */
	@Getter
	private String keywords;
	
	@Inject
	CatalogStructureUpdateService catalogStructureUpdateService;
	
	@Inject
	BDBSearchEndpointService solrConfig;
	
	private String regulatoryStatus;

	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		long startTime = System.currentTimeMillis();
		logger.debug("PDPSessionModel - Init method started");
		try {
			resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory);
			String rummode = CommonHelper.getRunMode(bdbApiEndpointService);
			String catalogNumber = CommonHelper.getSelectorDetails(request);
			String country = CommonHelper.getCountry(currentPage);
			request.setAttribute("catalogNumber", catalogNumber);
			Resource baseHpResource = null;
			Resource variantRes= null;
			String currentPageName = currentPage.getName();
			if (null != catalogNumber && null!=resourceResolver ) {
				variantRes = catalogStructureUpdateService.getProductFromLookUp(resourceResolver, catalogNumber,
						CommonConstants.MATERIAL_NUMBER);
				baseHpResource = CommonHelper.getBaseHpResourceFromLookUp(variantRes);
					if (null != baseHpResource && CommonHelper.getProductAvailabilityInRegion(catalogNumber, country,
							resourceResolver, catalogStructureUpdateService)) {
						ValueMap baseHpResourceVM = baseHpResource.adaptTo(ValueMap.class);
						getMetaDataAttributes(baseHpResourceVM);
						
						JsonObject assetPathObj = new JsonObject();
						request.setAttribute(CommonConstants.PRODUCT_VAR_HP_PATH, baseHpResource.getPath());
						thumbnailImagePath = CommonHelper.getGlobalThumbnailImage(resourceResolver, variantRes.getChild(CommonConstants.HP),
								externalizerService, bdbApiEndpointService);
						String productUrl = CommonHelper.getPdpProductUrl(variantRes.getChild(CommonConstants.HP));
						request.setAttribute("thumbnailImagePath", thumbnailImagePath);
						request.setAttribute("productUrl", productUrl);
						logger.debug("PDPSessionModel - hpPath from session: {}",
								request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH));
						Resource baseProductResource = baseHpResource.getParent();
						String videoId = StringUtils.EMPTY;
						Resource variantHpResource = variantRes.getChild(CommonConstants.HP);
						Resource marketingResource = variantHpResource
								.getChild("marketing" + CommonConstants.SINGLE_SLASH + country);
						if (null != marketingResource) {
							ValueMap valueMapMarketing = marketingResource.adaptTo(ValueMap.class);
							videoId = valueMapMarketing.get("videoId", String.class);
						}
						if (StringUtils.isNotEmpty(videoId)) {
							assetPathObj.addProperty("videoId", videoId);
						}
						assetPathObj = getAssetJson(resourceResolver, baseProductResource, assetPathObj,
								variantHpResource, baseHpResourceVM);
						request.setAttribute("assetPaths", assetPathObj);
						if (assetPathObj.size() > 0) {
							JsonElement assetObjArray = new JsonParser().parse(assetPathObj.toString());
							logger.debug("PDPSession model Asset Response {}", assetObjArray);
						}
						if (null != catalogNumber && null != baseHpResource.getParent().getChild(catalogNumber)) {
							regulatoryStatus = CommonHelper.getRuoFromRegionDetails(baseHpResource, country, catalogNumber);
						}
						
						request.setAttribute("regulatoryStatus", regulatoryStatus);
					}else {
						if (StringUtils.isNotEmpty(rummode) && rummode.equalsIgnoreCase(CommonConstants.PUBLISH) && StringUtils.isNotEmpty(currentPageName)  && currentPageName.equalsIgnoreCase("pdp") ) {
							response.sendError(404);
						}
					}	
			}
			
		} catch (LoginException e) {
			logger.error("LoginException {}", e.getMessage());
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException {}", e.getMessage());
		} catch (RepositoryException e) {
			logger.error("RepositoryException {}", e.getMessage());
		} catch (IOException e) {
			logger.error("IOException	 {}", e.getMessage());
		} finally {
			CommonHelper.closeResourceResolver(resourceResolver);
		}
		logger.debug("PDPSessionModel - Init method Exit");
		long endTime = System.currentTimeMillis();
		logger.debug("PDPSessionModel TIME - {}" ,endTime-startTime);

	}

	public void getMetaDataAttributes(ValueMap baseHpResourceVM) {
		productName = baseHpResourceVM.get(CommonConstants.LABEL_DESCRIPTION, String.class);
		request.setAttribute("productName", productName);
		description = baseHpResourceVM.get(CommonConstants.TDS_DESCRIPTION, String.class);
		keywords = baseHpResourceVM.get(CommonConstants.KEYWORDS, String.class);
		if (StringUtils.isNotEmpty(description)) {
			Pattern regex = Pattern.compile("<p>(.*?)</p>");
			Matcher matcher = regex.matcher(description);
			while (matcher.find()) {
				if (!matcher.group(1).trim().isEmpty()) {
					description = matcher.group(1).trim();
				}
			}
		}
	}

	/**
	 * Gets the asset json.
	 *
	 * @param resourceResolver the resource resolver
	 * @param baseProductResource the base product resource
	 * @param assetPathObj the asset path obj
	 * @param hpResource the hp resource
	 * @param hpResourceVM 
	 * @return the asset json
	 */
	public JsonObject getAssetJson(ResourceResolver resourceResolver, Resource baseProductResource,
			JsonObject assetPathObj, Resource hpResource, ValueMap baseHpResourceVM) {
		String region = CommonHelper.getRegion(currentPage);
		String damRegionPath = baseProductResource.getPath()
				.replace(CommonConstants.VAR_COMMERCE + CommonConstants.SINGLE_SLASH + CommonConstants.PRODUCTS,
						CommonConstants.CONTENT_DAM)
				.replace(CommonConstants.PRODUCTS, CommonConstants.PRODUCTS + CommonConstants.SINGLE_SLASH + region);
		String damGlobalPath = baseProductResource.getPath()
				.replace(CommonConstants.VAR_COMMERCE + CommonConstants.SINGLE_SLASH + CommonConstants.PRODUCTS,
						CommonConstants.CONTENT_DAM)
				.replace(CommonConstants.PRODUCTS, CommonConstants.PRODUCTS + CommonConstants.SINGLE_SLASH + "global");
		logger.debug("Dam Global Path {}",damGlobalPath);
		assetPathObj = getAssetPaths(resourceResolver, damGlobalPath, assetPathObj, hpResource, baseHpResourceVM);
		if(null != resourceResolver.getResource(damRegionPath)) {
			assetPathObj = getAssetPaths(resourceResolver, damRegionPath, assetPathObj, hpResource, baseHpResourceVM);
		}		
		return assetPathObj;
	}

	/**
	 * Gets the asset paths.
	 *
	 * @param resourceResolver the resource resolver
	 * @param damRegionPath the dam region path
	 * @param assetPathObj the asset path obj
	 * @param hpResource the hp resource
	 * @return the asset paths
	 */
	public JsonObject getAssetPaths(ResourceResolver resourceResolver, String damRegionPath, JsonObject assetPathObj,
			Resource hpResource,ValueMap baseHpResourceVM) {
		logger.debug("Entered Into getAssetPaths Method ");
		JsonArray galleryJsonArray = new JsonArray();
		JsonArray heroJsonArray = new JsonArray();
		JsonArray vialJsonArray = new JsonArray();
		JsonArray ruoJsonArray = new JsonArray();
		JsonArray otherVialArray = new JsonArray();
		JsonArray otherFormat = new JsonArray();
		Resource baseDamResource = resourceResolver.getResource(damRegionPath);
		List<Asset> assetTagWithSku = new ArrayList<>();
		String materialNumber =  Objects.requireNonNull(hpResource.getParent()).getName();
		if (null != baseDamResource) {
			Iterator<Resource> children = baseDamResource.listChildren();
			while (children.hasNext()) {
				Resource child = children.next();
				Asset asset = child.adaptTo(Asset.class);
				String imagePath = CommonHelper.getImageBasedOnProductSku(resourceResolver, asset, child, materialNumber);
				if (null != asset && null != asset.getMetadata(CommonConstants.CQ_TAGS)) {
					Object[] tags = (Object[]) asset.getMetadata(CommonConstants.CQ_TAGS);
					List<Object> tagList = Arrays.asList(tags);
					if(CollectionUtils.isNotEmpty(tagList) && !tagList.contains(CommonConstants.CLINICAL_TAG) && StringUtils.isBlank(imagePath)) {
						getAssetMetaData(resourceResolver, assetPathObj, galleryJsonArray, heroJsonArray, child, asset,
								tags);
					} else if(StringUtils.isNotBlank(imagePath)){
						assetTagWithSku.add(asset);
					}
				}
			}
		}

		/* To get the Images on PDP based on Product SKU */
		getImagesWithProductSkuTag(assetTagWithSku, ruoJsonArray);
		
		getImagesWithoutTags(galleryJsonArray, hpResource, baseDamResource, vialJsonArray, ruoJsonArray,
					otherVialArray,otherFormat,baseHpResourceVM );
		assignGalleryArray(resourceResolver, assetPathObj, galleryJsonArray,
				heroJsonArray, vialJsonArray);
		if (otherVialArray.size() > 0) {
			assetPathObj.add("OTHER_VIAL", otherVialArray);
		}
		if (ruoJsonArray.size() > 0) {
			assetPathObj.add("RUO_VIAL", ruoJsonArray);
		} 
		if ((assetPathObj.has("videoId") && assetPathObj.size() == 1) || (assetPathObj.size() == 0)) {
			logger.debug("Entered Into Placeholder Image");
			getPlaceholderImage(resourceResolver, assetPathObj, galleryJsonArray);
		}
		if(otherFormat.size()>0) {
			assetPathObj.add("OTHER_FORMAT", otherFormat);
		}
		return assetPathObj;
	}

	/**
	 * Gets the Images with Product SKU Tags
	 * @param assetTagWithSku
	 * @param ruoJsonArray
	 */
	private void getImagesWithProductSkuTag(List<Asset> assetTagWithSku, JsonArray ruoJsonArray) {
		if(null != assetTagWithSku && assetTagWithSku.size() > 0) {
			for (Asset asset : assetTagWithSku) {
				JsonObject ruoVialObj = new JsonObject();
				if (null != asset) {
					ruoVialObj.addProperty(CommonConstants.IMAGE_PATH, asset.getPath());
					CommonHelper.getMetaDataAttributes(ruoJsonArray,ruoVialObj,asset);
				}
			}
		}
	}

	/**
	 * Gets the images without tags.
	 *
	 * @param resourceResolver the resource resolver
	 * @param hpResource the hp resource
	 * @param baseProductResource the base product resource
	 * @param vialJsonArray the vial json array
	 * @param ruoJsonArray the ruo json array
	 * @param otherVialArray the other vial array
	 * @param hpResourceVM 
	 * @param asset the asset
	 * @param externalizerService2 
	 * @return the images without tags
	 */
	public void getImagesWithoutTags(JsonArray galleryJsonArray, Resource hpResource,
			Resource baseDamResource, JsonArray vialJsonArray, JsonArray ruoJsonArray, JsonArray otherVialArray, JsonArray formattedArray, ValueMap baseHpResourceVM) {
		    logger.debug("Entered Vial Image Section");
		    JsonObject assetVialObj = new JsonObject();
		    if(null != hpResource) {
		    	 String materialNumber = hpResource.getParent().getName();
		    	 ValueMap variantValueMap = hpResource.adaptTo(ValueMap.class);
					// To get ruo,bottle,unique images
					String tagResourcePath = CommonHelper.getProductTagPath(hpResource);
					List<Resource> resourceList = CommonHelper.getOtherVialImages(tagResourcePath, baseHpResourceVM, resourceResolver,
							bdbApiEndpointService.getVialImagesBasePath());
					logger.debug("Query Result {}", resourceList.size());
					logger.debug("Query Result Data {}", resourceList);
					CommonHelper.getVialImages(resourceResolver, otherVialArray, materialNumber, resourceList);
					if (ruoJsonArray.size() == 0 && (null != baseHpResourceVM.get(CommonConstants.REGULATORY_STATUS)
							&& baseHpResourceVM.get(CommonConstants.REGULATORY_STATUS, String.class).equals(CommonConstants.RUO))
							&& (baseHpResourceVM.get(CommonConstants.PDP_TEMPLATE, String.class).equals(CommonConstants.SFA)
									|| baseHpResourceVM.get(CommonConstants.PDP_TEMPLATE, String.class).equals(CommonConstants.OTHER))) {
					CommonHelper.getRuoVial(resourceResolver, ruoJsonArray, baseHpResourceVM, resourceList,bdbApiEndpointService, variantValueMap);
					}
					// to get clinical vial images
					if (vialJsonArray.size() == 0) {
						getClinicalVialObj(galleryJsonArray, baseDamResource, vialJsonArray, assetVialObj, materialNumber);
					}
					
					if(!resourceList.isEmpty()) {
						logger.debug("Entered Formated Image Section");
						for(Resource res : resourceList) {
							if(res.getPath().contains(bdbApiEndpointService.getFormatImagesBasePath())) {
								logger.debug("Formated Image Path {}", res.getPath());
								formattedArray.add(res.getPath());
								break;
							}
						}
					}
		    }
		   
		
	}

	/**
	 * Gets the clinical vial obj.
	 *
	 * @param resourceResolver the resource resolver
	 * @param baseProductResource the base product resource
	 * @param vialJsonArray the vial json array
	 * @param asset the asset
	 * @param assetVialObj the asset vial obj
	 * @param materialNumber the material number
	 * @return the clinical vial obj
	 */
	public void getClinicalVialObj(JsonArray galleryJsonArray, Resource baseDamResource,
			JsonArray vialJsonArray, JsonObject assetVialObj, String materialNumber) {
		logger.debug("Entered Clinical Vial Image Section");
		String clinicalVialImageName = materialNumber.concat("_01.png");
		logger.debug("clinicalVialImageName {}", clinicalVialImageName);
		if(null != baseDamResource) {
			logger.debug("Dam Base Resource {}", baseDamResource.getPath());
			String vialImgPath = baseDamResource.getPath() + CommonConstants.SINGLE_SLASH + clinicalVialImageName;
			Resource imageMetadataResource = resourceResolver.getResource(vialImgPath + CommonConstants.METADATAPATH);
			if (null != imageMetadataResource) {
				logger.debug("Clinical Vial Image Resource Path {}", imageMetadataResource.getPath());
				/* Getting the value map of the imageResource resource */
				ValueMap property = imageMetadataResource.adaptTo(ValueMap.class);
				if(null != property) {
					if(!property.containsKey(CommonConstants.SKU_NUMBER)){
						Asset asset = imageMetadataResource.adaptTo(Asset.class);
						assetVialObj.addProperty(CommonConstants.IMAGE_PATH, vialImgPath);
						String title = getMetaDataAttribute(asset, CommonConstants.DC_TITLE);
						String[] titleArray = title.split("\\|");
						assetVialObj.addProperty(CommonConstants.IMAGE_TITLE, titleArray[0]);
						String caption = removeDoubleQuotesFromCaption(getMetaDataAttribute(asset, CommonConstants.DC_CAPTION));
						assetVialObj.addProperty(CommonConstants.IMAGE_CAPTION, caption);
						String altText = getMetaDataAttribute(asset, CommonConstants.DC_IMAGE_ALT);
						assetVialObj.addProperty(CommonConstants.IMAGE_ALT_TEXT, altText);
						if (!galleryJsonArray.toString().contains(clinicalVialImageName)) {
							vialJsonArray.add(assetVialObj);
						}
					} else {
						logger.debug("Resource not found for Clinical Vial {}", vialImgPath);
					}
				}
			}
		}
	}


	
	/**
	 * Assign gallery array.
	 *
	 * @param resourceResolver the resource resolver
	 * @param assetPathObj the asset path obj
	 * @param hpResource the hp resource
	 * @param galleryJsonArray the gallery json array
	 * @param heroJsonArray the hero json array
	 * @param vialJsonArray the vial json array
	 */
	public void assignGalleryArray(ResourceResolver resourceResolver, JsonObject assetPathObj,
			JsonArray galleryJsonArray, JsonArray heroJsonArray, JsonArray vialJsonArray) {
		if (heroJsonArray.size() > 0) {
			assetPathObj.add("HERO", heroJsonArray);
		}
		if (galleryJsonArray.size() > 0 ) {
			assetPathObj.add(CommonConstants.GALLERY, galleryJsonArray);
		}
		if(vialJsonArray.size() > 0){
			assetPathObj.add("VIAL_ARRAY", vialJsonArray);
		}
	}

	/**
	 * Gets the placeholder image.
	 *
	 * @param resourceResolver the resource resolver
	 * @param assetPathObj the asset path obj
	 * @param galleryJsonArray the gallery json array
	 * @return the placeholder image
	 */
	public void getPlaceholderImage(ResourceResolver resourceResolver, JsonObject assetPathObj,
			JsonArray galleryJsonArray) {
		JsonObject assetPlaceholder = new JsonObject();
		assetPlaceholder.addProperty(CommonConstants.IMAGE_PATH, "/content/dam/bdb/general/placeholder/placeholder.png");
		assetPlaceholder.addProperty(CommonConstants.IMAGE_ALT_TEXT, "No Image");
		galleryJsonArray.add(assetPlaceholder);
		assetPathObj.add(CommonConstants.GALLERY, galleryJsonArray);
	}

	/**
	 * Gets the asset meta data.
	 *
	 * @param resourceResolver the resource resolver
	 * @param assetPathObj the asset path obj
	 * @param galleryJsonArray the gallery json array
	 * @param heroJsonArray the hero json array
	 * @param child the child
	 * @param asset the asset
	 * @param tags the tags
	 * @return the asset meta data
	 */
	public void getAssetMetaData(ResourceResolver resourceResolver, JsonObject assetPathObj, JsonArray galleryJsonArray,
			JsonArray heroJsonArray, Resource child, Asset asset, Object[] tags) {
		logger.debug("Entered Gallery and Primary images Section");
		ArrayList<String> tagList = new ArrayList<>();
		TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
		tagList.add(CommonConstants.PRIMARY);
		tagList.add(CommonConstants.THUMBNAIL);
		tagList.add(CommonConstants.GALLERY);
		tagList.add(CommonConstants.COMPARE);
		tagList.add(CommonConstants.DYENAME);
		tagList.add(CommonConstants.HERO);
		for (Object tagObject : tags) {
			JsonObject assetMetaObj = new JsonObject();
			if (null != tagObject) {
				Tag tag = tagManager.resolve(tagObject.toString());
				if (null != tag && tagList.contains(tag.getTitle())) {
					if (tag.getTitle().equals(CommonConstants.HERO) || tag.getTitle().equals(CommonConstants.PRIMARY)) {
						getImageProperties(resourceResolver, heroJsonArray, child, asset, assetMetaObj);
					} else if (tag.getTitle().equals(CommonConstants.GALLERY)) {
						getImageProperties(resourceResolver, galleryJsonArray, child, asset, assetMetaObj);
					} else { 
						assetPathObj.addProperty(tag.getTitle(), child.getPath());
					}
				}
			}
		}
	}

	
	/**
	 * Gets the image properties.
	 *
	 * @param resourceResolver the resource resolver
	 * @param galleryJsonArray the gallery json array
	 * @param child the child
	 * @param asset the asset
	 * @param assetMetaObj the asset meta obj
	 * @return the image properties
	 */
	public void getImageProperties(ResourceResolver resourceResolver, JsonArray galleryJsonArray, Resource child,
			Asset asset, JsonObject assetMetaObj) {
		assetMetaObj.addProperty(CommonConstants.IMAGE_PATH, child.getPath());
		String title = getMetaDataAttribute(asset, CommonConstants.DC_TITLE);
		String[] titleArray = title.split("\\|");
		assetMetaObj.addProperty(CommonConstants.IMAGE_TITLE, titleArray[0]);
		String caption = removeDoubleQuotesFromCaption(getMetaDataAttribute(asset, CommonConstants.DC_CAPTION));
		assetMetaObj.addProperty(CommonConstants.IMAGE_CAPTION, caption);
		String altText = getMetaDataAttribute(asset, CommonConstants.DC_IMAGE_ALT);
		assetMetaObj.addProperty(CommonConstants.IMAGE_ALT_TEXT, altText);
		galleryJsonArray.add(assetMetaObj);
	}

	/**
	 * Gets the meta data attribute.
	 *
	 * @param asset the asset
	 * @param property the property
	 * @return the meta data attribute
	 */
	public String getMetaDataAttribute(Asset asset, String property) {
		String title =  StringUtils.EMPTY;
		if (null != asset && null != asset.getMetadataValue(property)) {
			title = asset.getMetadataValue(property);
		}
		return title;
	}
	
	
	/**
	 * gets the plainTextDescription 
	 * It rremoves the html from the description 
	 * and gets it as a plain text.
	 */
	public String getPlainTextDescription() {
		if (!StringUtils.isEmpty(description)) {
			plainTextDescription = description;
			return Jsoup.parse(plainTextDescription).text();
		}
		return plainTextDescription;
	}
	
	/**
	 * Sets the plainTextDescription.
	 */
	public void setPlainTextDescription(String plainDescription) {
		this.plainTextDescription = plainDescription;
	}

	/**
	 * Gets the completeThumbnailImagePath along with the domain name.
	 *
	 * @return the completeThumbnailImagePath
	 * @throws LoginException 
	 */
	public String getCompleteThumbnailImagePath() throws LoginException {
		resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory);
		if (!StringUtils.isEmpty(thumbnailImagePath) && null != externalizerService && null != resourceResolver) {
			completeThumbnailImagePath = thumbnailImagePath;
			completeThumbnailImagePath = externalizerService.getFormattedUrl(completeThumbnailImagePath, resourceResolver);
			return completeThumbnailImagePath;
		}
		return completeThumbnailImagePath;
	}
	
	/**
	 * Sets the completeThumbnailImagePath.
	 */
	public void setCompleteThumbnailImagePath(String completeThumbnailImagePath) {
		this.completeThumbnailImagePath = completeThumbnailImagePath;
	}

	/**
	 * Removes the double quotes from caption.
	 *
	 * @param caption the caption
	 * @return the string
	 */
	public String removeDoubleQuotesFromCaption(String caption) {
		if(null != caption && caption.startsWith("\"") && caption.endsWith("\"")) {
			StringBuilder sb = new StringBuilder(caption);
			sb.deleteCharAt(0);
			sb.deleteCharAt(sb.length()-1);
			caption = sb.toString();
			caption = caption.replaceAll("\\<.*?\\>", "");
			caption = caption.trim();
		}
		return caption;
	}
	
	public String getProductName() {
		return productName;
	}
}
