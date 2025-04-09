package com.bdb.aem.core.models;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.TDSService;
import com.bdb.aem.core.services.SDSDocumentSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.*;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * The Class DataSheetsModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DataSheetsModel {

	/** The logger. */
	static Logger logger = LoggerFactory.getLogger(DataSheetsModel.class);


	/** Reduce Top Padding. */
	@Inject
	@Via("resource")
	@Default(values = "false")
	private Boolean togglePaddingTop;
	
	/** Reduce Bottom Padding. */
	@Inject
	@Via("resource")
	@Default(values = "false")
	private Boolean togglePaddingBottom;
	
	/** The heading. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String heading;

	/** The download alt text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String downloadAltText;

	/** The view more. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String viewMore;

	/** The view less. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String viewLess;

	/** The up arrow alt text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String upArrowAltText;

	/** The down arrow alt text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String downArrowAltText;
	
	/** The popover title. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String popoverTitle;

	/** The right arrow alt text. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String rightArrowAltText;

	/** The count. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String count;

	/** The doc types. */
	@Inject
	@Via("resource")
	public Resource docTypes;

	/** The data sheets labels. */
	private String dataSheetsLabels;

	/** The data sheets configs. */
	private String dataSheetsConfigs;
	
	/** The On Demand TDS*/
	private static final String ON_DEMAND_TDS = "ON_DEMAND_TDS";

	/** The Published TDS*/
	private static final String PUBLISHED_TDS = "PUBLISHED_TDS";

	/** The request. */
	@Inject
	private SlingHttpServletRequest request;

	/** The current page. */
	@Inject
	private Page currentPage;

	/** The resolver factory. */
	@Inject
	ResourceResolverFactory resolverFactory;

	/** The bdb api endpoint service. */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** The sds document service. */
	@Inject
	SDSDocumentSearchService sdsDocumentService;
	
	/** GetPublishedTDSpdf */
	@Inject
	TDSService tDSService;

	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("DataSheetsModel - Init method started");
		JsonObject dataSheetsConfigObj = new JsonObject();
		JsonObject tdsConfigObj = new JsonObject();

		String productVariant = CommonHelper.getSelectorDetails(request);
		String countryCode = CommonHelper.getCountry(currentPage);

		try (ResourceResolver resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory)) {
			/* To get other PDF from content/dam base resource */
			Resource hpBaseResource = null;
			if (null != request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH)) {
				String productVarHPPath = request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH).toString();
				hpBaseResource = resourceResolver.getResource(productVarHPPath);
			}

			if (null != hpBaseResource) {
				JsonObject dataSheetsJsonLabel = new JsonObject();
				JsonObject labelsObject = new JsonObject();
				
				logger.debug("HPResource {}", hpBaseResource);
				ValueMap hpBaseValueMap = hpBaseResource.adaptTo(ValueMap.class);
				getLabelJson(resourceResolver,hpBaseResource, hpBaseValueMap, dataSheetsConfigObj, tdsConfigObj, countryCode,
						productVariant,dataSheetsJsonLabel,labelsObject);

				/* To get OTHER DOCUMENTS */
				String damGlobalPath = hpBaseResource.getParent().getPath()
						.replace(CommonConstants.VAR_COMMERCE + CommonConstants.SINGLE_SLASH + CommonConstants.PRODUCTS,
								CommonConstants.CONTENT_DAM)
						.replace(CommonConstants.PRODUCTS,
								CommonConstants.PRODUCTS + CommonConstants.SINGLE_SLASH + "global");
				Resource baseProductDamResource = resourceResolver.getResource(damGlobalPath);
				if (null != baseProductDamResource) {
					getPdfPathsBasedOnType(
							baseProductDamResource.getPath() + CommonConstants.SINGLE_SLASH + CommonConstants.PDF,
							resourceResolver, countryCode.toLowerCase(), docTypes, dataSheetsConfigObj,
							externalizerService, productVariant,labelsObject,currentPage);
				}
				dataSheetsJsonLabel.add(CommonConstants.DOC_TYPE_LABELS, labelsObject);
				dataSheetsLabels = dataSheetsJsonLabel.toString();
			}
		} catch (LoginException e) {
			logger.error("LoginException", e);
		} finally {
			dataSheetsConfigs = dataSheetsConfigObj.toString();
		}
		logger.debug("Exit DataSheet Model Activate");
	}

	/**
	 * Gets the pdf paths based on type.
	 *
	 * @param basePdfPath         the base pdf path
	 * @param resolver            the resolver
	 * @param locale              the locale
	 * @param docTypes            the doc types
	 * @param dataSheetsConfigObj the data sheets config obj
	 * @param externalizerService the externalizer service
	 * @return the pdf paths based on type
	 */
	public static String getPdfPathsBasedOnType(String basePdfPath, ResourceResolver resolver, String locale,
			Resource docTypes, JsonObject dataSheetsConfigObj, ExternalizerService externalizerService, String productVariant, JsonObject labelsObject,Page pdpPage) {
		Date dt = new Date();
		String country = CommonConstants.REGION_TAG + locale;
		if (null != resolver.getResource(basePdfPath)) {
			Resource baseRes = resolver.getResource(basePdfPath);
			Iterator<Resource> listPdf = baseRes.listChildren();
			while (listPdf.hasNext()) {
				Resource pdfRes = listPdf.next();
				String path = pdfRes.getPath() + CommonConstants.METADATAPATH;
				Resource res = resolver.getResource(path);
				if (null != res) {
					ValueMap vm = res.adaptTo(ValueMap.class);
					getOtherFiles(docTypes, dataSheetsConfigObj, country, res, vm, resolver, externalizerService, productVariant,  labelsObject,pdpPage);
				}
			}
		}
		logger.debug("Time take to get the pdf path based on type : {}",new Date().getTime()-dt.getTime());
		return dataSheetsConfigObj.toString();
	}

	/**
	 * Gets the other files.
	 *
	 * @param docTypes             the doc types
	 * @param dataSheetsConfigObj  the data sheets config obj
	 * @param country              the country
	 * @param res                  the res
	 * @param vm                   the vm
	 * @param resolver             the resolver
	 * @param externalizerService2 the externalizer service 2
	 * @return the other files
	 */
	public static void getOtherFiles(Resource docTypes, JsonObject dataSheetsConfigObj, String country, Resource res,
			ValueMap vm, ResourceResolver resolver, ExternalizerService externalizerService2, String productVariant, JsonObject labelsObject, Page pdpPage) {
		Date dt = new Date();
		if(country.equalsIgnoreCase("bdb:regions/eu")){
			country = "bdb:regions/gb";
		}
		if (vm.containsKey(CommonConstants.PDF_DOC_REGION) && vm.containsKey(CommonConstants.PDF_DOC_TYPE)) {
			String[] docRegion = vm.get(CommonConstants.PDF_DOC_REGION, String[].class);

			for (String region : docRegion) {
				if (region.equalsIgnoreCase(country) || region.equals("bdb:regions/global")){	
					String docType = vm.get(CommonConstants.DOC_TYPE).toString();
					if (null != docTypes) {
						Iterator<Resource> docIteratior = docTypes.listChildren();
						getPdfMatchedWithDocType(dataSheetsConfigObj, res, resolver, externalizerService2, docType,
								docIteratior,productVariant, labelsObject, pdpPage);
					}

				}
				if(CommonConstants.EMEA_REGION_TAG.equals(region)) {
					Resource countryListResource = resolver.getResource(CommonConstants.EMEA_COUNTRIES_LIST_PATH);
					if(null != countryListResource) {
			            Iterator<Resource> items = countryListResource.listChildren();
			            while (items.hasNext()) {
			                Resource countryResource = items.next();
			                if (null != countryResource) {
			                    ValueMap properties = countryResource.getValueMap();
			                    String countryId = CommonHelper.getPropertyValue(properties, "value").toLowerCase();
			                    String countryTagName = "bdb:regions/"+countryId;
			                    if (StringUtils.isNotBlank(countryId) && StringUtils.isNotBlank(countryTagName)) {
			                    	if (countryTagName.equalsIgnoreCase(country)){ 
										String docType = vm.get(CommonConstants.DOC_TYPE).toString();
										if (null != docTypes) {
											Iterator<Resource> docIteratior = docTypes.listChildren();
											getPdfMatchedWithDocType(dataSheetsConfigObj, res, resolver, externalizerService2, docType,
													docIteratior,productVariant,labelsObject,pdpPage);
										}

									}
			                    }
			                }
			            }
					}
				}
				if(CommonConstants.APAC_REGION_TAG.equals(region) || CommonConstants.APAC_EN_REGION_TAG.equals(region)) {
					ArrayList<String>apacCountriesList = CommonHelper.getApacTagRegions(resolver,region);
                    if(!apacCountriesList.isEmpty()) {
                    	for(String countryCode:apacCountriesList) {
                    		String countryTagName = CommonConstants.REGION_TAG + countryCode;
	                    	if (StringUtils.isNotBlank(countryCode)) {
		                    	if (countryTagName.equalsIgnoreCase(country)) {
		                    		String docType = vm.get(CommonConstants.DOC_TYPE).toString();
									if (null != docTypes) {
										Iterator<Resource> docIteratior = docTypes.listChildren();
										getPdfMatchedWithDocType(dataSheetsConfigObj, res, resolver, externalizerService2, docType,
												docIteratior,productVariant,labelsObject,pdpPage);
									}
		        				}
		                    }
	                    }
                    }
				}
			}
		}
		logger.debug("Time take to get other files : {}",new Date().getTime()-dt.getTime());
	}

	/**
	 * Gets the pdf matched with doc type.
	 *
	 * @param dataSheetsConfigObj  the data sheets config obj
	 * @param res                  the res
	 * @param resolver             the resolver
	 * @param externalizerService2 the externalizer service 2
	 * @param docType              the doc type
	 * @param docIteratior         the doc iteratior
	 * @return the pdf matched with doc type
	 */
	public static void getPdfMatchedWithDocType(JsonObject dataSheetsConfigObj, Resource res, ResourceResolver resolver,
			ExternalizerService externalizerService2, String docType, Iterator<Resource> docIteratior, String productVariant,JsonObject labelsObject,Page pdpPage) {
		Date dt = new Date();
		while (docIteratior.hasNext()) {
			Resource docTypeVal = docIteratior.next();
			ValueMap docTypeItemMap = docTypeVal.adaptTo(ValueMap.class);
			if (null != docTypeItemMap.get(CommonConstants.DOCUMENT_TYPE)
					&& StringUtils.isNotEmpty(docTypeItemMap.get(CommonConstants.DOCUMENT_TYPE, String.class))) {
				String documentType = docTypeItemMap.get(CommonConstants.DOCUMENT_TYPE, String.class);

				if (null != docType && docType.equalsIgnoreCase(documentType) && !CommonConstants.DATA_TDS.equalsIgnoreCase(docType) ) {
					JsonObject otherDocObj = new JsonObject();
					otherDocObj.addProperty(CommonConstants.FILE_PATH,
							externalizerService2.getFormattedUrl(res.getParent().getParent().getPath(), resolver));
					if (null != docTypeItemMap.get(CommonConstants.DOCUMENT_LABEL) && StringUtils
							.isNotEmpty(docTypeItemMap.get(CommonConstants.DOCUMENT_LABEL, String.class))) {
						String documentLabel = docTypeItemMap.get(CommonConstants.DOCUMENT_LABEL, String.class);
						String docName = res.getParent().getParent().getName();
						
						String tdsDisplayOnPDPConfig = CommonHelper.tdsDisplayOnPDPConfig(pdpPage);
						
						
						if(tdsDisplayOnPDPConfig.equals("showAllTds") || tdsDisplayOnPDPConfig.isEmpty()) {
						if(docName.startsWith(productVariant + "_")) {
							int underscoreIndex = docName.lastIndexOf("_") +1;
							int dotIndex = docName.lastIndexOf(".");
							String languageName = docName.substring(underscoreIndex, dotIndex);
							
							dataSheetsConfigObj.add(documentLabel.toUpperCase()+ " - "+languageName.toUpperCase(), otherDocObj);
							labelsObject.addProperty(documentLabel.toUpperCase() + " - "+languageName.toUpperCase(), documentLabel + " - " +languageName);
						}else {
						dataSheetsConfigObj.add(documentLabel.toUpperCase(), otherDocObj);
						}
						}else if(tdsDisplayOnPDPConfig.equals("showCountrySpecificTdsOnly")) {
							if(docName.startsWith(productVariant + "_")) {
								int underscoreIndex = docName.lastIndexOf("_") +1;
								int dotIndex = docName.lastIndexOf(".");
								String languageName = docName.substring(underscoreIndex, dotIndex);
								
								dataSheetsConfigObj.add(documentLabel.toUpperCase()+ " - "+languageName.toUpperCase(), otherDocObj);
								labelsObject.addProperty(documentLabel.toUpperCase() + " - "+languageName.toUpperCase(), documentLabel + " - " +languageName);
							}else {
								//do nothing for the documents of english language
								logger.error("Not showing any English TDS as per configured tdsDisplayonPDPConfig : {}",tdsDisplayOnPDPConfig);
							}
						}else if(tdsDisplayOnPDPConfig.equals("showEnglishTdsOnly")) {
							if(docName.startsWith(productVariant + "_")) {
								//do nothing for the documents of Multi language
								logger.error("Not showing any Multi Language TDS as per configured tdsDisplayonPDPConfig : {}",tdsDisplayOnPDPConfig);
							}else {
								dataSheetsConfigObj.add(documentLabel.toUpperCase(), otherDocObj);
							}
						}
					}

				}
			}
		}
		logger.debug("Time take to get Pdf Matched With DocType : {}",new Date().getTime()-dt.getTime());
	}

	/**
	 * Gets the config.
	 *
	 * @param sdsArray            the response builder
	 * @param dataSheetsConfigObj the data sheets config obj
	 * @param documentTypeTitle   the document type title
	 * @return the config
	 */
	private String getConfig(JsonArray sdsArray, JsonObject dataSheetsConfigObj, String documentTypeTitle) {
		JsonObject sdsConfigObj = new JsonObject();
		if (sdsArray.size() > 0) {
			sdsConfigObj.addProperty(CommonConstants.FILE_PATH, bdbApiEndpointService.getSdsEndpoint());
			sdsConfigObj.add(CommonConstants.RESPONSE, sdsArray);
			dataSheetsConfigObj.add(documentTypeTitle.toUpperCase(), sdsConfigObj);
		}
		return dataSheetsConfigObj.toString();
	}

	/**
	 * Gets the label json.
	 *
	 * @param hpBaseValueMap      the hp base value map
	 * @param dataSheetsConfigObj the data sheets config obj
	 * @param tdsConfigObj        the tds config obj
	 * @param countryCode         the country code
	 * @param productVariant      the product variant
	 * @return the label json
	 */
	public String getLabelJson(ResourceResolver resourceResolver,Resource hpBaseResource, ValueMap hpBaseValueMap, JsonObject dataSheetsConfigObj, JsonObject tdsConfigObj,
			String countryCode, String productVariant,JsonObject dataSheetsJsonLabel, JsonObject labelsObject) {
		Date dt = new Date();
		dataSheetsJsonLabel.addProperty(CommonConstants.TOGGLE_PADDING_TOP,togglePaddingTop );
		dataSheetsJsonLabel.addProperty(CommonConstants.TOGGLE_PADDING_BOTTOM,togglePaddingBottom );
		dataSheetsJsonLabel.addProperty(CommonConstants.HEADING, heading);
		dataSheetsJsonLabel.addProperty(CommonConstants.VIEW_MORE, viewMore);
		dataSheetsJsonLabel.addProperty(CommonConstants.DOWNLAOD_ALT_TEXT, downloadAltText);
		dataSheetsJsonLabel.addProperty(CommonConstants.VIEW_LESS, viewLess);
		dataSheetsJsonLabel.addProperty(CommonConstants.UP_ARROW_ALT_TEXT, upArrowAltText);
		dataSheetsJsonLabel.addProperty(CommonConstants.DOWN_ARROW_ALT_TEXT, downArrowAltText);
		dataSheetsJsonLabel.addProperty(CommonConstants.POP_OVER_TITLE, popoverTitle);
		dataSheetsJsonLabel.addProperty(CommonConstants.RIGHT_ARROW_ALT_TEXT, rightArrowAltText);
		dataSheetsJsonLabel.addProperty(CommonConstants.COUNT, count);
		if (null != docTypes) {
			Iterator<Resource> docIteratior = docTypes.listChildren();
			while (docIteratior.hasNext()) {
				Resource docTypeVal = docIteratior.next();
				ValueMap docTypeItemMap = docTypeVal.adaptTo(ValueMap.class);
				if (null != docTypeItemMap.get(CommonConstants.DOCUMENT_LABEL)
						&& StringUtils.isNotEmpty(docTypeItemMap.get(CommonConstants.DOCUMENT_LABEL, String.class))) {
					String documentTypeTitle = docTypeItemMap.get(CommonConstants.DOCUMENT_LABEL, String.class);
					labelsObject.addProperty(documentTypeTitle.toUpperCase(), documentTypeTitle);

					if (null != docTypeItemMap.get(CommonConstants.DOCUMENT_TYPE) && StringUtils
							.isNotEmpty(docTypeItemMap.get(CommonConstants.DOCUMENT_TYPE, String.class))) {
						String documentType = docTypeItemMap.get(CommonConstants.DOCUMENT_TYPE, String.class);
						/* To get dynamic TDS 
						 * Added productVariant to get TDS name*/
						getTdsConfig(resourceResolver,hpBaseValueMap,hpBaseResource, dataSheetsConfigObj, tdsConfigObj, documentType,
								documentTypeTitle,countryCode, productVariant,labelsObject);

						/* To get dynamic SDS */
						getSdsConfig(dataSheetsConfigObj, countryCode, productVariant, documentType, documentTypeTitle);
					}
				}
			}
		}
		logger.debug("Time take to get the Json Labels : {}",new Date().getTime()-dt.getTime());
		return dataSheetsJsonLabel.toString();
	}

	/**
	 * Gets the sds config.
	 *
	 * @param dataSheetsConfigObj the data sheets config obj
	 * @param countryCode         the country code
	 * @param productVariant      the product variant
	 * @param documentTypeTitle   the document type title
	 * @param documentTypeTitle2
	 * @return the sds config
	 */
	public void getSdsConfig(JsonObject dataSheetsConfigObj, String countryCode, String productVariant,
			String documentType, String documentTypeTitle) {
		Date dt = new Date();
		if (StringUtils.isNotEmpty(documentType) && (documentType.contains(CommonConstants.DATA_SDS))) {
			JsonArray sdsArray = sdsDocumentService.searchSDSDocument(countryCode, productVariant);
			getConfig(sdsArray, dataSheetsConfigObj, documentTypeTitle);
		}
		logger.debug("Time take for sds config : {}",new Date().getTime()-dt.getTime());
	}

	/**
	 * Gets the tds config.
	 *
	 * @param hpBaseValueMap      the hp base value map
	 * @param dataSheetsConfigObj the data sheets config obj
	 * @param tdsConfigObj        the tds config obj
	 * @param documentTypeTitle   the document type title
	 * @param documentTypeTitle2
	 * @return the tds config
	 */
	public void getTdsConfig(ResourceResolver resourceResolver, ValueMap hpBaseValueMap, Resource hpBaseResource, JsonObject dataSheetsConfigObj, JsonObject tdsConfigObj,
			String documentType, String documentTypeTitle, String countryCode, String productVariant,JsonObject labelsObject) {
		Date dt = new Date();
		
		String tdsDocType = hpBaseValueMap.containsKey(CommonConstants.TDS_DOCUMENT_TYPE)
				? hpBaseValueMap.get(CommonConstants.TDS_DOCUMENT_TYPE, String.class)
				: StringUtils.EMPTY;
		if(ON_DEMAND_TDS.equals(tdsDocType))
		{
			String basePdfPath = hpBaseResource.getParent().getPath().replace(CommonConstants.COMMERCE_PRODUCT_PATH, CommonConstants.DAM_PRODUCT_PATH);
			if (null != resourceResolver.getResource(basePdfPath)) {
				Resource baseRes = resourceResolver.getResource(basePdfPath);
				if(baseRes.hasChildren()) {
					Resource basePdfRes = baseRes.getChild(CommonConstants.PDF);
					if(null!=basePdfRes) {	
						String pdfName = productVariant + CommonConstants.DOT_PDF;
						Resource pdfRes = basePdfRes.getChild(pdfName);
						if(null!=pdfRes) {
							tdsConfigObj.addProperty(CommonConstants.FILE_PATH, externalizerService.getFormattedUrl(pdfRes.getPath(), resourceResolver));
							if (StringUtils.isNotEmpty(documentType) && (documentType.contains(CommonConstants.DATA_TDS))) {
								dataSheetsConfigObj.add(documentTypeTitle.toUpperCase(), tdsConfigObj);
							}
						}
					}
				}

			}
		}
		else if(PUBLISHED_TDS.equals(tdsDocType))
		{
			
			String tdsDisplayOnPDPConfig = CommonHelper.tdsDisplayOnPDPConfig(currentPage);
			
			if(tdsDisplayOnPDPConfig.equals("showAllTds")  || tdsDisplayOnPDPConfig.isEmpty()) {
				processEnglishTds(hpBaseResource,hpBaseValueMap, resourceResolver, countryCode, productVariant, tdsConfigObj, dataSheetsConfigObj, documentType, documentTypeTitle);
				processCountrySpecificTds(hpBaseResource, hpBaseValueMap, resourceResolver, countryCode, productVariant,tdsConfigObj, dataSheetsConfigObj, documentType, documentTypeTitle, labelsObject);
			}else if(tdsDisplayOnPDPConfig.equals("showCountrySpecificTdsOnly") ) {
				processCountrySpecificTds(hpBaseResource, hpBaseValueMap, resourceResolver, countryCode, productVariant,tdsConfigObj, dataSheetsConfigObj, documentType, documentTypeTitle, labelsObject);
			}else if(tdsDisplayOnPDPConfig.equals("showEnglishTdsOnly")) {
				processEnglishTds(hpBaseResource,hpBaseValueMap, resourceResolver, countryCode, productVariant, tdsConfigObj, dataSheetsConfigObj, documentType, documentTypeTitle);
			}
			
			
		}else
		{
			logger.debug("product is not On-Demand and is not Published Tds or pdf is not available : {}", tdsDocType);
		}
		logger.debug("Time take for tds config : {}",new Date().getTime()-dt.getTime());
	}

	/**
	 * Process the English TDS.
	 *
	 * @param hpBaseValueMap      the hp base value map
	 * @param dataSheetsConfigObj the data sheets config obj
	 * @param tdsConfigObj        the tds config obj
	 * @param documentType		  the document type
	 * @param documentTypeTitle   the document type title
	 */
	public void processEnglishTds(Resource hpBaseResource, ValueMap hpBaseValueMap, ResourceResolver resourceResolver, String countryCode, String productVariant,JsonObject tdsConfigObj,JsonObject dataSheetsConfigObj,String documentType,String documentTypeTitle){
		Resource pdfRes= tDSService.getPublishedTdsPdf(hpBaseResource,hpBaseValueMap, resourceResolver, countryCode, productVariant);
		if(null!=pdfRes)
		{
			tdsConfigObj.addProperty(CommonConstants.FILE_PATH, externalizerService.getFormattedUrl(pdfRes.getPath(), resourceResolver));
			if (StringUtils.isNotEmpty(documentType) && (documentType.contains(CommonConstants.DATA_TDS))) {
				dataSheetsConfigObj.add(documentTypeTitle.toUpperCase(), tdsConfigObj);
			}
		}
	}
	

	/**
	 * Process the Country Specific TDS.
	 *
	 * @param hpBaseValueMap      the hp base value map
	 * @param dataSheetsConfigObj the data sheets config obj
	 * @param tdsConfigObj        the tds config obj
	 * @param documentType		  the document type
	 * @param documentTypeTitle   the document type title
	 */
	public void processCountrySpecificTds(Resource hpBaseResource, ValueMap hpBaseValueMap, ResourceResolver resourceResolver, String countryCode, String productVariant,JsonObject tdsConfigObj,JsonObject dataSheetsConfigObj,String documentType,String documentTypeTitle,JsonObject labelsObject){
		//adding the Multi lang TDS to data sheet
		List<Resource> pathListMultiLangPdf = tDSService.getMultiLanguagePublishedTdsPdf(hpBaseResource, hpBaseValueMap, resourceResolver, countryCode, productVariant);
		if(pathListMultiLangPdf != null && !pathListMultiLangPdf.isEmpty()) {
			for( Resource multiLangPdfRes : pathListMultiLangPdf) {
				
				if(null!=multiLangPdfRes)
				{
					String docName = multiLangPdfRes.getName();
					int underscoreIndex = docName.lastIndexOf("_") +1;
					int dotIndex = docName.lastIndexOf(".");
					String languageName = docName.substring(underscoreIndex, dotIndex);
					
					JsonObject tdsConfigObjLang = new JsonObject();
					tdsConfigObjLang.addProperty(CommonConstants.FILE_PATH, externalizerService.getFormattedUrl(multiLangPdfRes.getPath(), resourceResolver));
					if (StringUtils.isNotEmpty(documentType) && (documentType.contains(CommonConstants.DATA_TDS))) {
						dataSheetsConfigObj.add(documentTypeTitle.toUpperCase() + " - "+languageName.toUpperCase(), tdsConfigObjLang);
						labelsObject.addProperty(documentTypeTitle.toUpperCase() + " - "+languageName.toUpperCase(), documentTypeTitle + " - " +languageName);
					}
				}
				
			}
		}
	}
	
	/**
	 * Gets the data sheets labels.
	 *
	 * @return the data sheets labels
	 */
	public String getDataSheetsLabels() {
		return dataSheetsLabels;
	}

	/**
	 * Gets the data sheets configs.
	 *
	 * @return the data sheets configs
	 */
	public String getDataSheetsConfigs() {
		return dataSheetsConfigs;
	}

}
