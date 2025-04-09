package com.bdb.aem.core.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import com.bdb.aem.core.services.UpdateProductSchemaService;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.bean.ProductAnnouncementBean;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrUtils;
import com.day.cq.wcm.api.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;


/**
 * The Class ProductAnnouncementModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductAnnouncementModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(ProductAnnouncementModel.class);

	/** The request. */
	@SlingObject
	private SlingHttpServletRequest request;

	/** The resolver factory. */
	@Inject
	ResourceResolverFactory resolverFactory;

	/** The product announcement bean. */
	@Inject
	ProductAnnouncementBean productAnnouncementBean;

	/** The current page. */
	@Inject
	Page currentPage;

	/** The product announcement. */
	private String productAnnouncement;

	/** The pa description. */
	private String paDescription;

	/** The pa FAQ title. */
	private String paFAQTitle;

	/** The regional disclaimers. */
	private String regionalDisclaimers;

	/** The pa view more CTA. */
	private String paViewMoreCTA;

	/** The open new tab. */
	private String openNewTab;

	/** The disclaimer status. */
	private String disclaimerStatus;

	/** The more info link. */
	private String moreInfoLink;

	/** The view more faq label. */
	private String viewMoreFaqLabel;

	/** The startDate label */
	private String startDate;

	/** The endDate label */
	private String endDate;

	/** The faq list. */
	private List<ProductAnnouncementBean> faqList;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;
	
	@Inject
	BDBApiEndpointService bdbApiEndpointService;
	
	@Inject
	CatalogStructureUpdateService catalogStructureUpdateService;

	@Inject
	UpdateProductSchemaService updateProductSchemaService;
	
	/**
	 * Inits the.
	 * @throws RepositoryException 
	 */
	@PostConstruct
	protected void init() throws RepositoryException {
		logger.debug("Inside ProductAnnouncementModel Init");
		String productVarient = CommonHelper.getSelectorDetails(request);
		try (ResourceResolver resourceResolver = CommonHelper.getServiceResolver(resolverFactory)){
			String country = CommonHelper.getCountry(currentPage);
			Resource hpBaseResource = null;
			if (null != request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH)) {
				String productVarHPPath = request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH).toString();
				hpBaseResource = resourceResolver.getResource(productVarHPPath);
				
			}
			if(null != hpBaseResource && null != country) {
				Resource variantHpResource = SolrUtils.getVariantHpResource(hpBaseResource, productVarient);
				getVarientResource(resourceResolver, variantHpResource, country);
			}
			

		} catch (LoginException e) {
			logger.error("LoginException {}", e.getMessage());
		} 
		logger.debug("End of ProductAnnouncementModel Init");
	}

	/**
	 * Gets the varient resource.
	 *
	 * @param resourceResolver the resource resolver
	 * @param varientResource the varient resource
	 * @param region the region
	 * @return the varient resource
	 * @throws RepositoryException the repository exception
	 */
	public void getVarientResource(ResourceResolver resourceResolver, Resource varientResource, String country)
			throws RepositoryException {
		if (null != varientResource) {
			Resource parentResource = varientResource.getParent();
			if (null != parentResource.getChild(CommonConstants.MARKETING_NODE)) {
				Resource marketingResource = parentResource.getChild(CommonConstants.MARKETING_NODE);
				if (null != marketingResource.getChild(country)) {
					Resource regionRes = marketingResource.getChild(country);
					logger.debug("Get Properties from regionRes {}", regionRes);
					ValueMap varientRegionValueMap = regionRes.getValueMap();
					if (null != varientRegionValueMap.get(CommonConstants.PRODUCT_ANNOUNCEMENT)) {
						getValueMapProperties(resourceResolver, varientRegionValueMap, country);
					} else {
						ValueMap globalVarientValueMap = getGlobalMap(marketingResource,resourceResolver);
						getValueMapProperties(resourceResolver, globalVarientValueMap, country);
					}

				} else if ((null != marketingResource.getChild(CommonConstants.EMEA)) && CommonConstants.EMEA_COUNTRIES_LIST.contains(country)) {
					Resource emeaRegionRes = marketingResource.getChild(CommonConstants.EMEA);
					ValueMap emeaVarientRegionValueMap = emeaRegionRes.getValueMap();
					if (null != emeaVarientRegionValueMap.get(CommonConstants.PRODUCT_ANNOUNCEMENT)) {
						getValueMapProperties(resourceResolver, emeaVarientRegionValueMap, country);
					} 
				} else {
					ValueMap globalVarientValueMap = getGlobalMap(marketingResource,resourceResolver);
					getValueMapProperties(resourceResolver, globalVarientValueMap, country);
				}

			}
		}
	}

	/**
	 * Gets the global map.
	 *
	 * @param marketingResource the marketing resource
	 * @param resourceResolver the resource resolver
	 * @return the global map
	 * @throws RepositoryException the repository exception
	 */
	public ValueMap getGlobalMap(Resource marketingResource, ResourceResolver resourceResolver) throws RepositoryException {
		ValueMap globalVarientValueMap = null;
		if(null == marketingResource.getChild(CommonConstants.GLOBAL)) {
			Session session = resourceResolver.adaptTo(Session.class);
			Node marketingNode = marketingResource.adaptTo(Node.class);
			marketingNode.addNode(CommonConstants.GLOBAL);
			session.save();
		}
		if(null != marketingResource.getChild(CommonConstants.GLOBAL)) {
			Resource global = marketingResource.getChild(CommonConstants.GLOBAL);
			globalVarientValueMap = global.getValueMap();
		}

		return globalVarientValueMap;
	}

	/**
	 * Gets the value map properties.
	 *
	 * @param resourceResolver the resource resolver
	 * @param varientValueMap  the varient value map
	 * @return the value map properties
	 */
	public void getValueMapProperties(ResourceResolver resourceResolver, ValueMap varientValueMap, String country) {
		try {
			String startDate = getValueMapProperty(varientValueMap,CommonConstants.START_DATE);
			String endDate = getValueMapProperty(varientValueMap,CommonConstants.END_DATE);
			if(updateProductSchemaService.checkProductAnnouncementDateRange(startDate, endDate)) {
				productAnnouncement = getValueMapProperty(varientValueMap, CommonConstants.PRODUCT_ANNOUNCEMENT);
				paDescription = getValueMapProperty(varientValueMap, CommonConstants.PA_DESCRIPTION);
				String replacedProductId = getValueMapProperty(varientValueMap, "replacedProductId");
				if (null != replacedProductId) {
					Resource variantResource = catalogStructureUpdateService.getProductFromLookUp(resourceResolver, replacedProductId, CommonConstants.MATERIAL_NUMBER);
					if (null != variantResource) {
						String productUrl = CommonHelper.getPdpProductUrl(variantResource.getChild(CommonConstants.HP));
						String language = CommonHelper.getLanguage(currentPage) + "-" + CommonHelper.getCountry(currentPage);
						productUrl = CommonConstants.CONTENT + CommonConstants.SINGLE_SLASH + CommonConstants.BDB + CommonConstants.SINGLE_SLASH + CommonHelper.getRegion(currentPage) + CommonConstants.SINGLE_SLASH + CommonHelper.getCountry(currentPage) + CommonConstants.SINGLE_SLASH + language + productUrl;
						productUrl = externalizerService.getFormattedUrl(productUrl, resourceResolver);
						if (paDescription.contains("[" + replacedProductId + "]")) {
							paDescription = paDescription.replace("[" + replacedProductId + "]",
									"<a href=\"" + productUrl + "\">" + "[" + replacedProductId + "]" + "</a>");
						}
					}
				}
				paFAQTitle = getValueMapProperty(varientValueMap, CommonConstants.FAQ_TITLE);
				regionalDisclaimers = getValueMapProperty(varientValueMap, CommonConstants.REGIONAL_DISCLAIMER);
				paViewMoreCTA = getValueMapProperty(varientValueMap, CommonConstants.PA_VIEWMORE_CTA);
				openNewTab = getValueMapProperty(varientValueMap, CommonConstants.OPEN_NEW_TAB);
				disclaimerStatus = getValueMapProperty(varientValueMap, CommonConstants.DISCLAIMER_STATUS);
				String moreInfoUrl = getValueMapProperty(varientValueMap, CommonConstants.MORE_INFO_LINK);
				String language = CommonConstants.SINGLE_SLASH.concat(CommonHelper.getLanguage(currentPage) + CommonConstants.HYPHEN
						+ CommonHelper.getCountry(currentPage));
				if (null != moreInfoUrl && moreInfoUrl.contains(CommonConstants.LANGUAGE_PLACEHOLDER)) {
					String moreInfoLangUrl = moreInfoUrl.replace(CommonConstants.LANGUAGE_PLACEHOLDER, language);
					String rummode = CommonHelper.getRunMode(bdbApiEndpointService);
					if (StringUtils.isNotEmpty(rummode) && rummode.equalsIgnoreCase(CommonConstants.AUTHOR)) {
						moreInfoLink = CommonConstants.CONTENT + CommonConstants.SINGLE_SLASH + CommonConstants.BDB
								+ CommonConstants.SINGLE_SLASH
								+ CommonHelper.getRegion(currentPage).concat(CommonConstants.SINGLE_SLASH)
								.concat(CommonHelper.getCountry(currentPage)).concat(moreInfoLangUrl);
						/* Check if Url has QueryParameters */
						checkIfQueryParameter();
					} else if (StringUtils.isNotEmpty(rummode) && rummode.equalsIgnoreCase(CommonConstants.PUBLISH)) {
						moreInfoLink = moreInfoLangUrl;
					}
				} else {
					moreInfoLink = getValueMapProperty(varientValueMap, CommonConstants.MORE_INFO_LINK);
				}
				viewMoreFaqLabel = getValueMapProperty(varientValueMap, CommonConstants.VIEW_MORE_LABEL);
				if (null != varientValueMap && null != varientValueMap.get(CommonConstants.FAQ_PROPERTY)) {
					String[] faqProperties = varientValueMap.get(CommonConstants.FAQ_PROPERTY, String[].class);
					JsonObject faqJson = new JsonObject();
					getFaqProperties(faqProperties, faqJson);
				}
			}
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException {}", e.getMessage());
		} catch (JsonProcessingException e) {
			logger.error("JsonProcessingException {}", e.getMessage());
		} catch (RepositoryException e) {
			logger.error("RepositoryException {}", e.getMessage());
		}
			 
	}

	public void checkIfQueryParameter() {
		if(moreInfoLink.contains("?")) {
			String[] urlArray = moreInfoLink.split("\\?");
			moreInfoLink = urlArray[0].concat(CommonConstants.DOT_HTML+"?").concat(urlArray[1]);	
			}else {
				moreInfoLink = moreInfoLink.concat(CommonConstants.DOT_HTML);
			}
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
		if (null !=varientValueMap &&  null != varientValueMap.get(property)) {
			propertyValue = varientValueMap.get(property, String.class);
		}
		return propertyValue;
	}

	/**
	 * Gets the faq properties.
	 *
	 * @param faqProperties the faq properties
	 * @param faqJson       the faq json
	 * @return the faq properties
	 * @throws JsonProcessingException the json processing exception
	 */
	public void getFaqProperties(String[] faqProperties, JsonObject faqJson) throws JsonProcessingException {
		faqList = new ArrayList<>();
		for (String faqData : faqProperties) {
			String[] faqProperty = faqData.split("&");
			String question = faqProperty[0];
			String answer = faqProperty[1];
			faqJson.addProperty(CommonConstants.QUESTION, question);
			faqJson.addProperty(CommonConstants.ANSWER, answer);
			ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);

			ProductAnnouncementBean productAnnouncementObj = objectMapper.readValue(faqJson.toString(),
					ProductAnnouncementBean.class);
			faqList.add(productAnnouncementObj);
		}
	}

	/**
	 * Gets the product announcement.
	 *
	 * @return the product announcement
	 */
	public String getProductAnnouncement() {
		return productAnnouncement;
	}

	/**
	 * Gets the pa description.
	 *
	 * @return the pa description
	 */
	public String getPaDescription() {
		return paDescription;
	}

	/**
	 * Gets the pa FAQ title.
	 *
	 * @return the pa FAQ title
	 */
	public String getPaFAQTitle() {
		return paFAQTitle;
	}

	/**
	 * Gets the regional disclaimers.
	 *
	 * @return the regional disclaimers
	 */
	public String getRegionalDisclaimers() {
		return regionalDisclaimers;
	}

	/**
	 * Gets the pa view more CTA.
	 *
	 * @return the pa view more CTA
	 */
	public String getPaViewMoreCTA() {
		return paViewMoreCTA;
	}

	/**
	 * Gets the open new tab.
	 *
	 * @return the open new tab
	 */
	public String getOpenNewTab() {
		return openNewTab;
	}

	/**
	 * Gets the disclaimer status.
	 *
	 * @return the disclaimer status
	 */
	public String getDisclaimerStatus() {
		return disclaimerStatus;
	}

	/**
	 * Gets the more info link.
	 *
	 * @return the more info link
	 */
	public String getMoreInfoLink() {
		return moreInfoLink;
	}

	/**
	 * Gets the view more faq label.
	 *
	 * @return the view more faq label
	 */
	public String getViewMoreFaqLabel() {
		return viewMoreFaqLabel;
	}

	/**
	 * Gets the faq list.
	 *
	 * @return the faq list
	 */
	public List<ProductAnnouncementBean> getFaqList() {
		if (null != faqList) {
			return new ArrayList<>(faqList);
		} else {
			return Collections.emptyList();
		}
	}

}
