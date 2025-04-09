package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.models.ApiErrorMessagesModel;
import com.bdb.aem.core.models.GlobalErrorMessagesModel;
import com.bdb.aem.core.models.MasterDataMessagesModel;
import com.bdb.aem.core.models.PageModel;
import com.bdb.aem.core.pojo.Payload;
import com.bdb.aem.core.pojo.PayloadConfig;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.ExcludeUtil;
import com.bdb.aem.core.util.GlobalErrorMessagesModelUtil;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * The Class PageModelImpl.
 */
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class }, adapters = { PageModel.class }, resourceType = {
		PageModelImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class PageModelImpl implements PageModel {

	/**
	 * The bdb api endpoint service.
	 */
	@Inject
	private BDBApiEndpointService bdbApiEndpointService;
	/**
	 * CurrentPage.
	 */
	@Inject
	private Page currentPage;

	/**
	 * The resource resolver.
	 */
	@SlingObject
	private ResourceResolver resourceResolver;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;
	
	private boolean feLog;

	/**
	 * The Constant RESOURCE_TYPE.
	 */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/structure/page/v1/page";

	/**
	 * The Constant log.
	 */
	protected static final Logger log = LoggerFactory.getLogger(PageModelImpl.class);
	/**
	 * The Constant GLOBAL_ERROR_RESOURCE_TYPE.
	 */
	protected static final String GLOBAL_ERROR_RESOURCE_TYPE = "bdb-aem/proxy/components/content/globalerror";

	/** The Constant MASTER_DATA_RESOURCE_TYPE. */
	protected static final String MASTER_DATA_RESOURCE_TYPE = "bdb-aem/proxy/components/content/masterdata";

	/** The get states payload. */
	private String getStatesPayload;

	/** The get countries payload. */
	private String getCountriesPayload;

	/** The error messages. */
	private String errorMessages;

	/** The certifications status. */
	private String certificationsStatus;

	/** The order source. */
	private String orderSource;

	/** The order status. */
	private String orderStatus;
	
	/** The order status. */
	private String deliveryOption;

	/** The quote status. */
	private String quoteStatus;

	/** The quote status. */
	private String orderInquiryTypes;

	/** The home page path. */
	private String homePageUrl;

	/** The yesterday. */
	private String yesterday;

	/** The today. */
	private String today;

	/** The certifications type. */
	private String certificationsType;

	/** The Sign In Url. */
	private String signInUrl;

	/** The idle Time. */
	private int idleTime;

	/** The log Out Url. */
	private String logOutUrl;

	/** The order approval promotions. */
	private String orderApprovalPromotions;

	/** The merge anonymous cart. */
	private String mergeAnonymousCart;

	/** The Sign In Url. */
	private String anonymousTokenUrl;

	/** The Save to Quote List. */
	private String saveToQuoteList;

	/** The Add to Quote List. */
	private String addToQuote;
	
	/** The refresh token. */
	private String refreshToken;
	
	/** The standard. */
	private String standard;
	
	/** The Default Server error. */
	private String defaultServerError;
	
	/** The contact us url. */
	private String contactUsUrl;
	
	private String productInquireUrl;

	private String region;

	private String country;

	private String language;

	private String hybrisSiteId;
	
	private String marektoSubmitCta;
	
	private String loginErrorAlertLabels;
	
	/** The public email domains. */
	private String publicEmailDomains;

	/**
	 * The init method.
	 */
	@PostConstruct
	protected void init() {
		language = CommonHelper.getLanguage(currentPage);
		country = CommonHelper.getCountry(currentPage);
		region = CommonHelper.getRegion(currentPage);
		hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		feLog =  null!=CommonHelper.getLabel("feLog", currentPage) && CommonHelper.getLabel("feLog", currentPage).equalsIgnoreCase("true")?true:false;
		publicEmailDomains = CommonHelper.getEmailDomainList(currentPage, resourceResolver);
		if (bdbApiEndpointService != null) {
			ExcludeUtil excludeUtilObject = new ExcludeUtil();
			Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
					.addSerializationExclusionStrategy(excludeUtilObject).create();
			setGetStatesPayload(gson);
			setGetCountriesPayload(gson);
			setErrorMessages();
			setHomePageUrl();
			setGetCertificationsStatus();
			setGetOrderSource();
			setGetOrderStatus();
			setGetDeliveryOption();
			setGetQuoteStatus();
			setGetOrderInquiryTypes();
			setYesterdayAndTodayLabels();
			setCertificationsType();
			setSignInUrl();
			setIdleTime();
			setLogOutUrl();
			setOrderApprovalPromotions();
			setMergeAnonymousCart();
			setAnonymousTokenUrl();
			setAddToQuoteList();
			setAddToQuote();
			setStandard();
			setRefreshToken(gson);
			setDefaultServerError();
			setContactUsUrl();
			setProductInquireUrl();
			setLoginAlert();
		}
	}

	private void setLoginAlert() {
		JsonObject loginErrorAlert = new JsonObject();
		loginErrorAlert.addProperty("alertTitle", CommonHelper.getLabel("loginErrorAlertTitle", currentPage));
		loginErrorAlert.addProperty("alertMsg", CommonHelper.getLabel("loginErrorAlertMsg", currentPage));
		loginErrorAlert.addProperty("alertCta", CommonHelper.getLabel("loginErrorAlerCTA", currentPage));
		loginErrorAlertLabels = loginErrorAlert.toString();
		
	}

	/**
	 * Gets the gets the states payload.
	 *
	 * @return the gets the states payload
	 */
	@Override
	public String getGetStatesPayload() {
		return getStatesPayload;
	}

	/**
	 * Gets the gets the countries payload.
	 *
	 * @return the gets the countries payload
	 */
	@Override
	public String getGetCountriesPayload() {
		return getCountriesPayload;
	}

	/**
	 * Gets the error messages.
	 *
	 * @return the error messages
	 */
	@Override
	public String getErrorMessages() {
		return errorMessages;
	}

	/**
	 * Gets the home page url.
	 *
	 * @return the home page url
	 */
	@Override
	public String getHomePageUrl() {
		return homePageUrl;
	}

	/**
	 * Gets the certifications status.
	 *
	 * @return the certifications status
	 */
	@Override
	public String getCertificationsStatus() {
		return certificationsStatus;
	}

	/**
	 * Gets the order source.
	 *
	 * @return the order source
	 */
	@Override
	public String getOrderSource() {
		return orderSource;
	}

	/**
	 * Gets the order status.
	 *
	 * @return the order status
	 */
	@Override
	public String getOrderStatus() {
		return orderStatus;
	}


	/**
	 * Gets the quote status.
	 *
	 * @return the quote status
	 */
	@Override
	public String getQuoteStatus() {
		return quoteStatus;
	}

	/**
	 * Gets the anonymousTokenUrl
	 *
	 * @return the anonymousTokenUrl
	 */
	@Override
	public String getAnonymousTokenUrl() {
		return anonymousTokenUrl;
	}

	/**
	 * Return saveToQuoteList labels.
	 * 
	 * @return
	 */
	@Override
	public String getSaveToQuoteList() {
		return saveToQuoteList;
	}

	/**
	 * Return addToQuote labels.
	 * 
	 * @return
	 */
	@Override
	public String getAddToQuote() {
		return addToQuote;
	}

	/**
	 * Set addToQuote label.
	 */
	private void setAddToQuote() {
		addToQuote = CommonHelper.getLabel("addToQuote", currentPage);

	}

	@Override
	public String getRegion() {
		return region.toUpperCase();
	}
	@Override
	public String getCountry() {
		return country.toUpperCase();
	}
	@Override
	public String getLanguage() {
		return language.toUpperCase();
	}
	@Override
	public String getHybrisSiteId() {
		return hybrisSiteId;
	}

	/**
	 * Set saveToQuoteList label.
	 */
	private void setAddToQuoteList() {
		saveToQuoteList = CommonHelper.getLabel("saveToQuoteList", currentPage);

	}

	/**
	 * Sets the gets the states payload.
	 *
	 * @param gson the new gets the states payload
	 */
	private void setGetStatesPayload(Gson gson) {
		Payload getStatesPayloadObj = new Payload(bdbApiEndpointService.getStatesFromCountryServletPath(),
				HttpConstants.METHOD_POST);
		PayloadConfig getStatesPayloadConfig = new PayloadConfig(getStatesPayloadObj);
		getStatesPayload = gson.toJson(getStatesPayloadConfig);
	}

	/**
	 * Sets the gets the countries payload.
	 *
	 * @param gson the new gets the countries payload
	 */
	private void setGetCountriesPayload(Gson gson) {
		Payload getCountriesPayloadObj = new Payload(bdbApiEndpointService.getCountriesFromRegionServletPath(),
				HttpConstants.METHOD_GET);
		PayloadConfig getCountriesPayloadConfig = new PayloadConfig(getCountriesPayloadObj);
		getCountriesPayload = gson.toJson(getCountriesPayloadConfig);
	}
	
	/**
	 * Sets the refresh token.
	 *
	 * @param gson the new refresh token
	 */
	private void setRefreshToken(Gson gson) {
		Payload getRefreshTokenPayload = new Payload(bdbApiEndpointService.getRefreshTokenEndpoint(),
				HttpConstants.METHOD_POST);
		PayloadConfig getRefreshTokenPayloadConfig = new PayloadConfig(getRefreshTokenPayload);
		refreshToken = gson.toJson(getRefreshTokenPayloadConfig);
	}

	/**
	 * Sets the error messages.
	 */
	private void setErrorMessages() {
		String errorPagePath = CommonHelper.getErrorPagePath(currentPage);
		GlobalErrorMessagesModel errorModel = GlobalErrorMessagesModelUtil.getErrorMessageModel(errorPagePath,
				resourceResolver, GLOBAL_ERROR_RESOURCE_TYPE);

		if (null != errorModel) {
			JsonObject errorMessageJson = new JsonObject();
			for (ApiErrorMessagesModel errorMessagesModel : errorModel.getErrorMessages()) {
				errorMessageJson.addProperty(errorMessagesModel.getErrorCode(), errorMessagesModel.getErrorMessage());
			}
			errorMessages = errorMessageJson.toString();
		}

	}

	/**
	 * Sets the home page url.
	 */
	private void setHomePageUrl() {
		String homePage = CommonHelper.getHomePageUrl(currentPage);
		if (!StringUtil.isEmpty(homePage) && null != externalizerService) {
			homePageUrl = externalizerService.getFormattedUrl(homePage, resourceResolver);
		}
	}

	/**
	 * Sets the get certifications status.
	 */
	private void setGetCertificationsStatus() {
		String masterDataPagePath = CommonHelper.getMasterDataPagePath(currentPage);
		MasterDataMessagesModel masterDataModel = CommonHelper.getMasterDataMessageModel(masterDataPagePath,
				resourceResolver, MASTER_DATA_RESOURCE_TYPE);
		if (null != masterDataModel) {
			JsonObject masterDataMessagesJson = new JsonObject();
			for (ApiErrorMessagesModel messagesModel : masterDataModel.getCertificationMessages()) {
				if (messagesModel.getErrorCode() != null && messagesModel.getErrorMessage() != null) {
					masterDataMessagesJson.addProperty(messagesModel.getErrorCode(), messagesModel.getErrorMessage());
				}
			}
			certificationsStatus = masterDataMessagesJson.toString();
		}
	}

	/**
	 * Sets the get order source.
	 */
	private void setGetOrderSource() {
		String masterDataPagePath = CommonHelper.getMasterDataPagePath(currentPage);
		MasterDataMessagesModel masterDataModel = CommonHelper.getMasterDataMessageModel(masterDataPagePath,
				resourceResolver, MASTER_DATA_RESOURCE_TYPE);
		if (null != masterDataModel) {
			JsonObject masterDataMessagesJson = new JsonObject();
			for (ApiErrorMessagesModel messagesModel : masterDataModel.getOrderSourceMessages()) {
				if (messagesModel.getErrorCode() != null && messagesModel.getErrorMessage() != null) {
					masterDataMessagesJson.addProperty(messagesModel.getErrorCode(), messagesModel.getErrorMessage());
				}
			}
			orderSource = masterDataMessagesJson.toString();
		}
	}

	/**
	 * Sets the get order status.
	 */
	private void setGetOrderStatus() {
		String masterDataPagePath = CommonHelper.getMasterDataPagePath(currentPage);
		MasterDataMessagesModel masterDataModel = CommonHelper.getMasterDataMessageModel(masterDataPagePath,
				resourceResolver, MASTER_DATA_RESOURCE_TYPE);
		if (null != masterDataModel) {
			JsonObject masterDataMessagesJson = new JsonObject();
			for (ApiErrorMessagesModel messagesModel : masterDataModel.getOrderStatusMessages()) {
				if (messagesModel.getErrorCode() != null && messagesModel.getErrorMessage() != null) {
					masterDataMessagesJson.addProperty(messagesModel.getErrorCode(), messagesModel.getErrorMessage());
				}
			}
			orderStatus = masterDataMessagesJson.toString();
		}
	}
	
	private void setGetDeliveryOption() {
		String masterDataPagePath = CommonHelper.getMasterDataPagePath(currentPage);
		MasterDataMessagesModel masterDataModel = CommonHelper.getMasterDataMessageModel(masterDataPagePath,
				resourceResolver, MASTER_DATA_RESOURCE_TYPE);
		if (null != masterDataModel) {
			JsonObject masterDataMessagesJson = new JsonObject();
			for (ApiErrorMessagesModel messagesModel : masterDataModel.getDeliverOptionMessages()) {
				if (messagesModel.getErrorCode() != null && messagesModel.getErrorMessage() != null) {
					masterDataMessagesJson.addProperty(messagesModel.getErrorCode(), messagesModel.getErrorMessage());
				}
			}
			deliveryOption = masterDataMessagesJson.toString();
		}
		
	}

	/**
	 * Sets the get quote status.
	 */
	private void setGetQuoteStatus() {
		String masterDataPagePath = CommonHelper.getMasterDataPagePath(currentPage);
		MasterDataMessagesModel masterDataModel = CommonHelper.getMasterDataMessageModel(masterDataPagePath,
				resourceResolver, MASTER_DATA_RESOURCE_TYPE);
		if (null != masterDataModel) {
			JsonObject masterDataMessagesJson = new JsonObject();
			for (ApiErrorMessagesModel messagesModel : masterDataModel.getQuoteStatusMessages()) {
				if (messagesModel.getErrorCode() != null && messagesModel.getErrorMessage() != null) {
					masterDataMessagesJson.addProperty(messagesModel.getErrorCode(), messagesModel.getErrorMessage());
				}
			}
			quoteStatus = masterDataMessagesJson.toString();
		}
	}

	/**
	 * Sets the get order inquiry types.
	 */
	private void setGetOrderInquiryTypes() {
		String masterDataPagePath = CommonHelper.getMasterDataPagePath(currentPage);
		MasterDataMessagesModel masterDataModel = CommonHelper.getMasterDataMessageModel(masterDataPagePath,
				resourceResolver, MASTER_DATA_RESOURCE_TYPE);
		if (null != masterDataModel) {
			JsonObject masterDataMessagesJson = new JsonObject();
			for (ApiErrorMessagesModel messagesModel : masterDataModel.getOrderInquiryTypesOptions()) {
				if (messagesModel.getErrorCode() != null && messagesModel.getErrorMessage() != null) {
					masterDataMessagesJson.addProperty(messagesModel.getErrorCode(), messagesModel.getErrorMessage());
				}
			}
			orderInquiryTypes = masterDataMessagesJson.toString();
		}
	}

	/**
	 * Sets the yesterday and today labels.
	 */
	private void setYesterdayAndTodayLabels() {
		yesterday = CommonHelper.getLabel(CommonConstants.YESTERDAY_LABEL, currentPage);
		today = CommonHelper.getLabel(CommonConstants.TODAY_LABEL, currentPage);
	}
	
	/**
	 * Sets the error messages.
	 */
	private void setDefaultServerError() {
		defaultServerError = CommonHelper.getLabel(CommonConstants.DEFAULT_SERVER_ERROR, currentPage);
	}

	/**
	 * Gets the yesterday.
	 *
	 * @return the yesterday
	 */
	@Override
	public String getYesterday() {
		return yesterday;
	}

	/**
	 * Gets the today.
	 *
	 * @return the today
	 */
	@Override
	public String getToday() {
		return today;
	}
	/**
	 * Gets the deliveryOption.
	 *
	 * @return the deliveryOption
	 */
	
	@Override
	public String getDeliveryOption() {
		return deliveryOption;
	}

	/**
	 * Sets the certifications type.
	 */
	private void setCertificationsType() {
		certificationsType = StringUtils.EMPTY;
		String masterDataPagePath = CommonHelper.getMasterDataPagePath(currentPage);
		MasterDataMessagesModel masterDataModel = CommonHelper.getMasterDataMessageModel(masterDataPagePath,
				resourceResolver, MASTER_DATA_RESOURCE_TYPE);
		if (null != masterDataModel) {
			JsonObject masterDataMessagesJson = new JsonObject();
			for (ApiErrorMessagesModel messagesModel : masterDataModel.getCertificationsType()) {
				if (messagesModel.getErrorCode() != null && messagesModel.getErrorMessage() != null) {
					masterDataMessagesJson.addProperty(messagesModel.getErrorCode(), messagesModel.getErrorMessage());
				}
			}
			certificationsType = masterDataMessagesJson.toString();
		}
	}

	/**
	 * Gets the certifications type.
	 *
	 * @return the certifications type
	 */
	public String getCertificationsType() {
		return certificationsType;
	}

	/**
	 * Sets the signInUrl.
	 */
	private void setSignInUrl() {
		signInUrl = CommonHelper.getSignInUrl(bdbApiEndpointService, externalizerService, resourceResolver,
				currentPage);
	}

	/**
	 * Sets the idle time.
	 */
	private void setIdleTime() {
		idleTime = bdbApiEndpointService.getUserIdleTime();
	}

	/**
	 * Sets the log out url.
	 */
	private void setLogOutUrl() {
		logOutUrl = bdbApiEndpointService.getSignOutServletPath();
	}

	/**
	 * Gets the SignInUrl.
	 *
	 * @return the SignInUrl
	 */
	@Override
	public String getSignInUrl() {
		return signInUrl;
	}

	/**
	 * Gets the SignInUrl.
	 *
	 * @return the SignInUrl
	 */
	@Override
	public int getIdleTime() {
		return idleTime;
	}

	/**
	 * Gets the SignInUrl.
	 *
	 * @return the SignInUrl
	 */
	@Override
	public String getLogOutUrl() {
		return logOutUrl;
	}

	/**
	 * Sets the order approval promotions.
	 */
	private void setOrderApprovalPromotions() {
		orderApprovalPromotions = StringUtils.EMPTY;
		String masterDataPagePath = CommonHelper.getMasterDataPagePath(currentPage);
		MasterDataMessagesModel masterDataModel = CommonHelper.getMasterDataMessageModel(masterDataPagePath,
				resourceResolver, MASTER_DATA_RESOURCE_TYPE);
		if (null != masterDataModel) {
			JsonObject masterDataMessagesJson = new JsonObject();
			for (ApiErrorMessagesModel messagesModel : masterDataModel.getPromotionsCodeDescription()) {
				if (messagesModel.getErrorCode() != null && messagesModel.getErrorMessage() != null) {
					masterDataMessagesJson.addProperty(messagesModel.getErrorCode(), messagesModel.getErrorMessage());
				}
			}
			orderApprovalPromotions = masterDataMessagesJson.toString();
		}
	}

	/**
	 * Gets the order approval promotions.
	 *
	 * @return the order approval promotions
	 */
	@Override
	public String getOrderApprovalPromotions() {
		return orderApprovalPromotions;
	}

	/**
	 * Sets the merge anonymous cart.
	 */
	private void setMergeAnonymousCart() {
		mergeAnonymousCart = StringUtils.EMPTY;
		String hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		ExcludeUtil excludeUtilObject = new ExcludeUtil();
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		if (bdbApiEndpointService != null) {
			String mergeAnonymousCartEndpoint = StringUtils.replace(
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.mergeAnonymousCartEndpoint(),
					CommonConstants.HYBRIS_SITE_LITERAL, hybrisSiteId);
			Payload mergeAnonymousCartPayload = new Payload(mergeAnonymousCartEndpoint, CommonConstants.POST);
			PayloadConfig mergeAnonymousCartPayloadConfig = new PayloadConfig(mergeAnonymousCartPayload);
			mergeAnonymousCart = json.toJson(mergeAnonymousCartPayloadConfig);
		}
	}

	/**
	 * Gets the merge anonymous cart.
	 *
	 * @return the merge anonymous cart
	 */
	@Override
	public String getMergeAnonymousCart() {
		return mergeAnonymousCart;
	}
	
	/**
	 * Gets the refresh token.
	 *
	 * @return the refresh token
	 */
	@Override
	public String getRefreshToken() {
		return refreshToken;
	}

	private void setAnonymousTokenUrl() {
		if (bdbApiEndpointService != null) {
			anonymousTokenUrl = bdbApiEndpointService.getAnonymousTokenServletPath();
		}
	}

	/**
	 * Set Standard label
	 */
	private void setStandard() {
		standard = CommonHelper.getLabel("standard", currentPage);
		
	}
	
	/**
	 * @return standard.
	 */
	@Override
	public String getStandard() {
		return standard;
	}
	
	/**
	 * Gets the order inquiry types.
	 *
	 * @return the order inquiry types
	 */
	public String getOrderInquiryTypes() {
		return orderInquiryTypes;
	}

	@Override
	public String getDefaultServerError() {
		return defaultServerError;
	}

	/**
	 * Gets the contact us url.
	 *
	 * @return the contact us url
	 */
	public String getContactUsUrl() {
		return contactUsUrl;
	}

	/**
	 * Sets the contact us url.
	 */
	public void setContactUsUrl() {
		String url = CommonHelper.getLabel(CommonConstants.CONTACT_US_URL, currentPage);
		this.contactUsUrl = externalizerService.getFormattedUrl(url, resourceResolver);
	}
	
	@Override
	public boolean isFeLog() {
		return feLog;
	}
	
	@Override
	public String getLoginErrorAlertLabels() {
		return loginErrorAlertLabels;
	}
	
	@Override
	public String getMarektoSubmitCta() {
		marektoSubmitCta = CommonHelper.getLabel("marektoSubmitCta", currentPage);
		return marektoSubmitCta;
	}
	
	/**
	 * Gets the public email domains.
	 *
	 * @return the public email domains
	 */
	@Override
	public String getPublicEmailDomains() {
		return publicEmailDomains;
	}

	@Override
	public String getProductInquireUrl() {
		return productInquireUrl;
	}
	
	private void setProductInquireUrl() {
		String url = CommonHelper.getLabel("productInquireUrl", currentPage);
		this.productInquireUrl = externalizerService.getFormattedUrl(url, resourceResolver);
	}
}
