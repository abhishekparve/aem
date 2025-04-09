package com.bdb.aem.core.models.impl;

import java.util.ArrayList;
import java.util.List;

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

import com.bdb.aem.core.bean.MessageAndCodeBean;
import com.bdb.aem.core.models.AccountManagementCommunicationSettingsModel;
import com.bdb.aem.core.models.MasterDataMessagesModel;
import com.bdb.aem.core.pojo.AccountManagementCommunicationSettingsConfig;
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
 * The Class AccountManagementCommunicationSettingsModelImpl.
 */
@Model( adaptables = { SlingHttpServletRequest.class, Resource.class }, 
		adapters = { AccountManagementCommunicationSettingsModel.class },
		resourceType = { AccountManagementCommunicationSettingsModelImpl.RESOURCE_TYPE },
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AccountManagementCommunicationSettingsModelImpl implements AccountManagementCommunicationSettingsModel {
	
/** The Constant RESOURCE_TYPE. */
protected static final String RESOURCE_TYPE = "bdb-aem/components/content/accountmanagement/v1/accountmanagement";

/** The Constant MASTER_DATA_RESOURCE_TYPE. */
protected static final String MASTER_DATA_RESOURCE_TYPE = "bdb-aem/proxy/components/content/masterdata";
	
	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(AccountManagementCommunicationSettingsModelImpl.class);
	
	/** The current page. */
	@Inject
	private Page currentPage;

	/** The request. */
	@Self
	private SlingHttpServletRequest request;
	
	/** The bdb api endpoint service. */
	@Inject
	private BDBApiEndpointService bdbApiEndpointService;
	
	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;
	
	/** The communication settings header. */
	@Inject
	@Via("resource")
	@SerializedName("title")
	private String communicationSettingsHeader;
	
	/** The notify msg. */
	@Inject
	@Via("resource")
	@SerializedName("infoLine")
	private String notifyMsg;
	
	/** The save Button Label. */
	@Inject
	@Via("resource")
	@SerializedName("saveButtonLabel")
	private String saveButtonLabel;
	
	/** The save Notification Message. */
	@Inject
	@Via("resource")
	@SerializedName("saveNotificationMessage")
	private String saveNotificationMessage;

	/** The email label. */
	@SerializedName("emailLabel")
	private String emailLabel;
	
	/** The communication settings labels. */
	private String communicationSettingsLabels;
	
	/** The communication settings config. */
	private String communicationSettingsConfig;
	
	/** The communications list. */
	@SerializedName("commsList")
	List<MessageAndCodeBean> communicationsList = new ArrayList<>();
	
	/** The hybris site id. */
	private String hybrisSiteId;
	
	/**
	 * Inits the.
	 */
	@PostConstruct
	void init() {
		log.debug("Inside Communications Settings Init Method");

		hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		ExcludeUtil excludeUtilObject = new ExcludeUtil();
	
		populateCommunicationsList();
		setCommunicationSettingsLabels(excludeUtilObject);
		setCommunicationSettingsConfig(excludeUtilObject);
	}
	
	/**
	 * Sets the communication settings labels.
	 *
	 * @param excludeUtilObject the new communication settings labels
	 */
	private void setCommunicationSettingsLabels(ExcludeUtil excludeUtilObject) {
		communicationSettingsLabels = StringUtils.EMPTY;
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		emailLabel = CommonHelper.getLabel(CommonConstants.EMAIL, currentPage);
		
		communicationSettingsLabels = json.toJson(this);
	}

	/**
	 * Sets the communication settings config.
	 *
	 * @param excludeUtilObject the new communication settings config
	 */
	private void setCommunicationSettingsConfig(ExcludeUtil excludeUtilObject) {
		communicationSettingsConfig = StringUtils.EMPTY;
		JsonElement getNotificationsJson;
		JsonElement updateNotificationJson;
		
		if (bdbApiEndpointService != null) {
			Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
					.addSerializationExclusionStrategy(excludeUtilObject).create();
			String notificationEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.notificationEndpoint(), CommonConstants.BASE_SITE_ID, hybrisSiteId);
			
			Payload getNotificationsPayload = new Payload(notificationEndpoint, HttpConstants.METHOD_GET);
			Payload updateNotificationPayload = new Payload(notificationEndpoint, HttpConstants.METHOD_POST);
			
			PayloadConfig getNotificationsPayloadConfig = new PayloadConfig(getNotificationsPayload);
			getNotificationsJson = json.toJsonTree(getNotificationsPayloadConfig);
			PayloadConfig updateNotificationPayloadConfig = new PayloadConfig(updateNotificationPayload);
			updateNotificationJson = json.toJsonTree(updateNotificationPayloadConfig);
			
			AccountManagementCommunicationSettingsConfig communicationSettingsConfigJson = new AccountManagementCommunicationSettingsConfig(getNotificationsJson, updateNotificationJson);
			communicationSettingsConfig = json.toJson(communicationSettingsConfigJson);
		}
	}

	/**
	 * Populate communications list.
	 */
	private void populateCommunicationsList() {
		String masterDataPagePath = CommonHelper.getMasterDataPagePath(currentPage);
		MasterDataMessagesModel masterDataModel = CommonHelper.getMasterDataMessageModel(
				masterDataPagePath, resourceResolver, MASTER_DATA_RESOURCE_TYPE);
		
		String abandonedCartLabel = StringUtils.EMPTY;
		// String blogLabel = StringUtils.EMPTY;
		// String newsLetterLabel = StringUtils.EMPTY;
		// String tutorialLabel = StringUtils.EMPTY;
		// String webinarLabel = StringUtils.EMPTY;
	    
		String abandonedCartCode = CommonConstants.ABANDONED_CART_CODE;
		// String blogCode = CommonConstants.BLOG_CODE;
		// String newsLetterCode = CommonConstants.NEWSLETTER_CODE;
		// String tutorialCode = CommonConstants.TUTORIAL_CODE;
		// String webinarCode = CommonConstants.WEBINAR_CODE;
		
		if (masterDataModel != null) {			
			abandonedCartLabel = masterDataModel.getAbandonedCartLabel();
			// blogLabel = masterDataModel.getBlogLabel();
			// newsLetterLabel = masterDataModel.getNewsLetterLabel();
			// tutorialLabel = masterDataModel.getTutorialLabel();
			// webinarLabel = masterDataModel.getWebinarLabel();
		}
		
		MessageAndCodeBean abandonedCartResource = new MessageAndCodeBean(abandonedCartCode, abandonedCartLabel) ;
		communicationsList.add(abandonedCartResource);
		// MessageAndCodeBean blogResource =  new MessageAndCodeBean(blogCode, blogLabel);
		// communicationsList.add(blogResource);
		// MessageAndCodeBean newsletterResource =  new MessageAndCodeBean(newsLetterCode, newsLetterLabel);
		// communicationsList.add(newsletterResource);
		// MessageAndCodeBean tutorialResource =  new MessageAndCodeBean(tutorialCode, tutorialLabel);
		// communicationsList.add(tutorialResource);
		// MessageAndCodeBean webinarResource =  new MessageAndCodeBean(webinarCode, webinarLabel);
		// communicationsList.add(webinarResource);
	}

	/**
	 * Gets the communication settings labels.
	 *
	 * @return the communication settings labels
	 */
	@Override
	public String getCommunicationSettingsLabels() {
		return communicationSettingsLabels;
	}

	/**
	 * Gets the communication settings config.
	 *
	 * @return the communication settings config
	 */
	@Override
	public String getCommunicationSettingsConfig() {
		return communicationSettingsConfig;
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
