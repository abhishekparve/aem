package com.bdb.aem.core.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.AccountManagementQuotesModel;
import com.bdb.aem.core.pojo.AccountQuotesLabels;
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

/**
 * The Class AccountManagementQuotesModelImpl.
 */
@Model( adaptables = { SlingHttpServletRequest.class, Resource.class }, 
		adapters = { AccountManagementQuotesModel.class }, 
		resourceType = { AccountManagementQuotesModelImpl.RESOURCE_TYPE },
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AccountManagementQuotesModelImpl implements AccountManagementQuotesModel {
	
	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/accountmanagement/v1/accountmanagement";

	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(AccountManagementQuotesModelImpl.class);
	
	/** The current page. */
	@Inject
	private Page currentPage;

	/** The quotes heading. */
	@Inject
	@Via("resource")
	private String quotesHeading;
	
	/** The quotes search place holder. */
	@Inject
	@Via("resource")
	private String quotesSearchPlaceHolder;
	
	/** The search and filter text. */
	@Inject
	@Via("resource")
	private String searchAndFilterText;
	
	/** The view quote label. */
	@Inject
	@Via("resource")
	private String viewQuoteLabel;
	
	/** The search by label. */
	@Inject
	@Via("resource")
	private String searchByLabel;
	
	/** The search button label. */
	@Inject
	@Via("resource")
	private String searchButtonLabel;

	/** The No Quotes Found Label. */
	@Inject
	@Via("resource")
	private String noQuotesFoundLabel;

	/** The Quotes Num Search Label. */
	@Inject
	@Via("resource")
	private String quotesNumSearchLabel;

	/** The Product Num Search Label. */
	@Inject
	@Via("resource")
	private String productNumSearchLabel;

	/** The Product Name Search Label. */
	@Inject
	@Via("resource")
	private String productNameSearchLabel;

	/** The emptyQuoteImage. */
	@Inject
	@Via("resource")
	private String emptyQuoteImage;

	/** The PemptyQuotedesc */
	@Inject
	@Via("resource")
	private String emptyQuotedesc;

	/** The emptyQuotelabel. */
	@Inject
	@Via("resource")
	private String emptyQuotelabel;

	/** The noQuotesData. */
	@Inject
	@Via("resource")
	private String noQuotesData;

	/** The noQuotesFoundInfo. */
	@Inject
	@Via("resource")
	private String noQuotesFoundInfo;

	/** The emptyQuoteImgAltText. */
	@Inject
	@Via("resource")
	private String emptyQuoteImgAltText;
	
	/** The bdb api endpoint service. */
	@Inject
	private BDBApiEndpointService bdbApiEndpointService;

	/** The quotes labels. */
	private String quotesLabels;
	
	/** The quotes config. */
	private String quotesConfig;
	
	/** The hybris site id. */
	private String hybrisSiteId;

	/**
	 * Creates and Initializes the model with the Labels and Configs.
	 */
	@PostConstruct
	void init() {
		log.debug("Inside Quotes Init Method");
		hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		ExcludeUtil excludeUtilObject = new ExcludeUtil();
		setQuotesLabels(excludeUtilObject);
		setQuotesConfig(excludeUtilObject);
	}

	/**
	 * Sets the quotes labels.
	 *
	 * @param excludeUtilObject the new quotes labels
	 */
	private void setQuotesLabels(ExcludeUtil excludeUtilObject) {
		quotesLabels = StringUtils.EMPTY;
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		
		String quoteNumberText = CommonHelper.getLabel(CommonConstants.QUOTE_NUMBER_LABEL, currentPage);
		String quoteDateText = CommonHelper.getLabel(CommonConstants.QUOTE_DATE_LABEL, currentPage);
		String quoteStatusText = CommonHelper.getLabel(CommonConstants.QUOTE_STATUS_LABEL, currentPage);
		String dateRangeText = CommonHelper.getLabel(CommonConstants.DATE_RANGE_LABEL, currentPage);
		String fromPlaceholderText = CommonHelper.getLabel(CommonConstants.FROM_PLACEHOLDER_LABEL, currentPage);	
		String toPlaceholderText = CommonHelper.getLabel(CommonConstants.TO_PLACEHOLDER_LABEL, currentPage);
		String distributorNameText = CommonHelper.getLabel(CommonConstants.DISTRIBUTOR_NAME_LABEL, currentPage);
		String distributorEmailText = CommonHelper.getLabel(CommonConstants.DISTRIBUTOR_EMAIL_LABEL, currentPage);
		String distributorPhoneNumberText = CommonHelper.getLabel(CommonConstants.DISTRIBUTOR_PHONE_NUMBER_LABEL, currentPage);
		String shipToAddressText = CommonHelper.getLabel(CommonConstants.SHIP_TO_ADDRESS_LABEL, currentPage);
		String shipToNumberLabel = CommonHelper.getLabel(CommonConstants.SHIP_TO_NUMBER_LABEL, currentPage);
		String accMgrNameText = CommonHelper.getLabel(CommonConstants.ACCOUNT_MANAGER_NAME_LABEL, currentPage);
		String accMgrEmailText = CommonHelper.getLabel(CommonConstants.ACCOUNT_MANAGER_EMAIL_LABEL, currentPage);
		String accMgrPhoneNumber = CommonHelper.getLabel(CommonConstants.ACCOUNT_MANAGER_PHONE_NUMBER_LABEL, currentPage);
		String billToAddressText = CommonHelper.getLabel(CommonConstants.BILL_TO_ADDRESS, currentPage);
		String billToNumberLabel = CommonHelper.getLabel(CommonConstants.BILL_TO_NUMBER_LABEL, currentPage);
		String pendingLabel = CommonHelper.getLabel(CommonConstants.PENDING_LABEL, currentPage);
		String payerAddressLabel = CommonHelper.getLabel(CommonConstants.PAYER_ADDRESS_LABEL, currentPage);
		String payToNumberLabel = CommonHelper.getLabel(CommonConstants.PAY_TO_NUMBER_LABEL, currentPage);
		AccountQuotesLabels quoteDetails = new AccountQuotesLabels();
		quoteDetails.setDistributorNameText(distributorNameText);
		quoteDetails.setDistributorEmailText(distributorEmailText);
		quoteDetails.setDistributorPhoneNumberText(distributorPhoneNumberText);
		quoteDetails.setShipToAddressText(shipToAddressText);
		quoteDetails.setShipToNumberLabel(shipToNumberLabel);
		quoteDetails.setAccMgrNameText(accMgrNameText);
		quoteDetails.setAccMgrEmailText(accMgrEmailText);
		quoteDetails.setAccMgrPhoneNumber(accMgrPhoneNumber);
		quoteDetails.setBillToAddressText(billToAddressText);
		quoteDetails.setBillToNumberLabel(billToNumberLabel);
		quoteDetails.setPendingLabel(pendingLabel);
		quoteDetails.setPayerAddressLabel(payerAddressLabel);
		quoteDetails.setPayToNumberLabel(payToNumberLabel);
		
		JsonElement quoteDetailsLabel = json.toJsonTree(quoteDetails);
		
		AccountQuotesLabels accountQuotesLabels = new AccountQuotesLabels();
		accountQuotesLabels.setQuotesHeading(quotesHeading);
		accountQuotesLabels.setQuotesSearchPlaceHolder(quotesSearchPlaceHolder);
		accountQuotesLabels.setSearchAndFilterText(searchAndFilterText);
		accountQuotesLabels.setViewQuoteLabel(viewQuoteLabel);
		accountQuotesLabels.setSearchByLabel(searchByLabel);
		accountQuotesLabels.setSearchButtonLabel(searchButtonLabel);
		accountQuotesLabels.setQuoteNumberText(quoteNumberText);
		accountQuotesLabels.setQuoteDateText(quoteDateText);
		accountQuotesLabels.setQuoteStatusText(quoteStatusText);
		accountQuotesLabels.setDateRangeText(dateRangeText);
		accountQuotesLabels.setFromPlaceholderText(fromPlaceholderText);
		accountQuotesLabels.setToPlaceholderText(toPlaceholderText);
		accountQuotesLabels.setQuoteDetailsLabel(quoteDetailsLabel);
		accountQuotesLabels.setNoQuotesFoundLabel(noQuotesFoundLabel);
		accountQuotesLabels.setQuotesNumSearchLabel(quotesNumSearchLabel);
		accountQuotesLabels.setProductNumSearchLabel(productNumSearchLabel);
		accountQuotesLabels.setProductNameSearchLabel(productNameSearchLabel);
		accountQuotesLabels.setEmptyQuotelabel(emptyQuotelabel);
		accountQuotesLabels.setEmptyQuotedesc(emptyQuotedesc);
		accountQuotesLabels.setEmptyQuoteImage(emptyQuoteImage);
		accountQuotesLabels.setNoQuotesData(noQuotesData);
		accountQuotesLabels.setNoQuotesFoundInfo(noQuotesFoundInfo);
		accountQuotesLabels.setEmptyQuoteImgAltText(emptyQuoteImgAltText);
		
		quotesLabels = json.toJson(accountQuotesLabels);
	}
	
	/**
	 * Sets the quotes config.
	 *
	 * @param excludeUtilObject the new quotes config
	 */
	private void setQuotesConfig(ExcludeUtil excludeUtilObject) {
		quotesConfig = StringUtils.EMPTY;
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		if (bdbApiEndpointService != null) {
			String getPaymentsEndpoint = StringUtils.replace(
					bdbApiEndpointService.getGetQuotesEndpoint(),
					CommonConstants.HYBRIS_SITE_LITERAL, hybrisSiteId);
			Payload getPaymentsPayload = new Payload(
					bdbApiEndpointService.getBDBHybrisDomain() + getPaymentsEndpoint,
					HttpConstants.METHOD_GET);
			
			PayloadConfig getPaymentsPayloadConfig = new PayloadConfig(getPaymentsPayload);
			quotesConfig = json.toJson(getPaymentsPayloadConfig);
		}
	}
	
	/**
	 * Gets the quotes labels.
	 *
	 * @return the quotes labels
	 */
	@Override
	public String getQuotesLabels() {
		return quotesLabels;
	}

	/**
	 * Gets the quotes config.
	 *
	 * @return the quotes config
	 */
	@Override
	public String getQuotesConfig() {
		return quotesConfig;
	}
}
