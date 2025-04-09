package com.bdb.aem.core.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.AccountManagementGrantsModel;
import com.bdb.aem.core.pojo.AccountManagementGrantsConfig;
import com.bdb.aem.core.pojo.Payload;
import com.bdb.aem.core.pojo.PayloadConfig;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.ExcludeUtil;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The Class AccountManagementGrantsModelImpl.
 */
@Model( adaptables = { SlingHttpServletRequest.class, Resource.class }, 
		adapters = { AccountManagementGrantsModel.class },
		resourceType = { AccountManagementGrantsModelImpl.RESOURCE_TYPE },
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AccountManagementGrantsModelImpl implements AccountManagementGrantsModel {
	
	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/accountmanagement/v1/accountmanagement";
	
	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(AccountManagementGrantsModelImpl.class);
	
	/** The current page. */
	@Inject
	private Page currentPage;

	/** The request. */
	@Self
	private SlingHttpServletRequest request;
	
	/** The grants header. */
	@Inject
	@Via("resource")
	@SerializedName("title")
	private String grantsHeader;
	
	/** The remaining grants label. */
	@Inject
	@Via("resource")
	@SerializedName("grantsLeft")
	private String remainingGrantsLabel;
	
	/** The ship to number label. */
	@Inject
	@Via("resource")
	@SerializedName("shipToNumber")
	private String shipToNumberLabel;
	
	/** The starting from label. */
	@Inject
	@Via("resource")
	@SerializedName("startingFrom")
	private String startingFromLabel;
	
	/** The valid until label. */
	@Inject
	@Via("resource")
	@SerializedName("validUntil")
	private String validUntilLabel;
	
	/** The out of label. */
	@Inject
	@Via("resource")
	@SerializedName("outOf")
	private String outOfLabel;
	
	/** The more details label. */
	@Inject
	@Via("resource")
	@SerializedName("moreDetails")
	private String moreDetailsLabel;
	
	/** The order number label. */
	@Inject
	@SerializedName("orderNumber")
	private String orderNumberLabel;
	
	/** The po date label. */
	@Inject
	@SerializedName("poDate")
	private String poDateLabel;
	
	/** The grants used label. */
	@Inject
	@Via("resource")
	@SerializedName("grantsUsed")
	private String grantsUsedLabel;
	
	/** The order status. */
	@Inject
	@SerializedName("orderStatus")
	private String orderStatus;
	
	/** The email id label. */
	@Inject
	@Via("resource")
	@SerializedName("emailId")
	private String emailIdLabel;
	
	/** The view details label. */
	@Inject
	@Via("resource")
	@SerializedName("viewDetails")
	private String viewDetailsLabel;
	
	/** The plus icon alt text. */
	@Inject
	@Via("resource")
	@SerializedName("plusIconAltText")
	private String plusIconAltText;
	
	/** The minus icon alt text. */
	@Inject
	@Via("resource")
	@SerializedName("minusIconAltText")
	private String minusIconAltText;
		
	/** The bdb api endpoint service. */
	@Inject
	private BDBApiEndpointService bdbApiEndpointService;
	
	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;
	
	/** The user grants labels. */
	private String userGrantsLabels; 	
	
	/** The user grants config. */
	private String userGrantsConfig;
	
	/** The hybris site id. */
	private String hybrisSiteId;
	
	/**
	 * Creates and Initializes the model with the Labels and Configs.
	 */
	@PostConstruct
	void init() {
		log.debug("Inside Grants Init Method");
		hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);

		ExcludeUtil excludeUtilObject = new ExcludeUtil();			
		setGrantsLabels(excludeUtilObject);
		setGrantsConfig(excludeUtilObject);
	}

	/**
	 * Sets the grants labels.
	 *
	 * @param excludeUtilObject the new grants labels
	 */
	private void setGrantsLabels(ExcludeUtil excludeUtilObject) {
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		orderNumberLabel = CommonHelper.getLabel(CommonConstants.ORDER_NUMBER_LABEL, currentPage); 
		poDateLabel = CommonHelper.getLabel(CommonConstants.PO_DATE_LABEL, currentPage);
		orderStatus = CommonHelper.getLabel(CommonConstants.ORDER_STATUS_LABEL, currentPage);
		userGrantsLabels = json.toJson(this);
	}

	/**
	 * Sets the grants config.
	 *
	 * @param excludeUtilObject the new grants config
	 */
	private void setGrantsConfig(ExcludeUtil excludeUtilObject) {
		JsonElement getGrantsForCustomersJson;
		JsonElement orderHistoryForGrantsJson;
		
		if (bdbApiEndpointService != null) {
			String getGrantsForCustomerEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getGrantsForCustomerEndpoint(), CommonConstants.HYBRIS_SITE_LITERAL, hybrisSiteId);
			String orderHistoryForGrantsEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.orderHistoryForGrantsEndpoint() , CommonConstants.HYBRIS_SITE_LITERAL, hybrisSiteId);
			
			Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
					.addSerializationExclusionStrategy(excludeUtilObject).create();
			
			Payload getGrantsForCustomerPayload =  new Payload(getGrantsForCustomerEndpoint , HttpConstants.METHOD_GET);
			Payload orderHistoryForGrantsPayload = new Payload(orderHistoryForGrantsEndpoint, HttpConstants.METHOD_GET);
			
			PayloadConfig getGrantsForCustomerPayloadConfig = new PayloadConfig(getGrantsForCustomerPayload);
			getGrantsForCustomersJson = json.toJsonTree(getGrantsForCustomerPayloadConfig);
			PayloadConfig orderHistoryForGrantsPayloadConfig = new PayloadConfig(orderHistoryForGrantsPayload);
			orderHistoryForGrantsJson = json.toJsonTree(orderHistoryForGrantsPayloadConfig);
			
			AccountManagementGrantsConfig accountManagementGrantsConfig = new AccountManagementGrantsConfig(getGrantsForCustomersJson, orderHistoryForGrantsJson);
			userGrantsConfig = json.toJson(accountManagementGrantsConfig);
		}
	}

	
	/**
	 * Gets the user grants labels.
	 *
	 * @return the user grants labels
	 */
	public String getUserGrantsLabels() {
		return userGrantsLabels;
	}
	
	/**
	 * Gets the user grants config.
	 *
	 * @return the user grants config
	 */
	public String getUserGrantsConfig() {
		return userGrantsConfig;
	}

	/**
	 * Gets the hybris site id.
	 *
	 * @return the hybris site id
	 */
	public String getHybrisSiteId() {
		return hybrisSiteId;
	}
}
