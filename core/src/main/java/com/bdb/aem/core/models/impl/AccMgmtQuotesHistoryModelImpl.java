package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.bean.AccMgmtQuotesHistoryModelLabelBean;
import com.bdb.aem.core.models.AccMgmtQuotesHistoryModel;
import com.bdb.aem.core.pojo.AccMgmtQuotesHistoryModelConfig;
import com.bdb.aem.core.pojo.AccMgmtQuotesHistoryModelLabel;
import com.bdb.aem.core.pojo.AccountQuoteListsConfig;
import com.bdb.aem.core.pojo.AccountQuoteListsLabels;
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
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class},
        adapters = {AccMgmtQuotesHistoryModel.class},
        resourceType = {AccMgmtQuotesHistoryModelImpl.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AccMgmtQuotesHistoryModelImpl implements AccMgmtQuotesHistoryModel {

    protected static final String RESOURCE_TYPE = "bdb-aem/components/content/accountmanagement/v1/accountmanagement";
    protected static final Logger log = LoggerFactory.getLogger(AccMgmtQuotesHistoryModelImpl.class);


    @Inject
    private Page currentPage;

    /**
     * The request.
     */
    @Self
    private SlingHttpServletRequest request;

    /**
     * The Quotes History header.
     */
    @Inject
    @Via("resource")
    @SerializedName("heading")
    private String quotesHistoryheading;
    
    /**
     * The Address Change Info Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("addressChangeInfo")
    private String addressChangeInfo;
    
    @Inject
    @Via("resource")
    @SerializedName("quoteInputLabel")
    private String quoteInputLabel;

    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    @SerializedName("validQuoteTabLabel")
    private String validQuoteTabLabel;
    
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    @SerializedName("expiredQuoteTabLabel")
    private String expiredQuoteTabLabel;
    
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    @SerializedName("allQuoteTabLabel")
    private String allQuoteTabLabel;
    
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    @SerializedName("serverErrorMsg")
    private String serverErrorMsg;
    
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    @SerializedName("quoteNumber")
    private String quoteNumber;

    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    @SerializedName("validFrom")
    private String validFrom;

    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    @SerializedName("validThrough")
    private String validThrough;

    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    @SerializedName("total")
    private String total;

    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    @SerializedName("viewDetailsBtn")
    private String viewDetailsBtn;

    @Inject
    @Via("resource")
    @SerializedName("noQuoteFound")
    private String noQuoteFound;

    @Inject
    @Via("resource")
    @SerializedName("noQuoteInfo")
    private String noQuoteInfo;

    @Inject
    @Via("resource")
    @SerializedName("clearSearch")
    private String clearSearch;
    
    @Inject
    @Via("resource")
    @SerializedName("noQuoteForDefaultShipTo")
    private String noQuoteForDefaultShipTo;
    
    @Inject
    @Via("resource")
    @SerializedName("noQuoteForDefaultShipToInfo")
    private String noQuoteForDefaultShipToInfo;
   
    @Inject
    @Via("resource")
    @SerializedName("quoteNumberHeading")
    private String detailsQuoteNumberHeading;

    @Inject
    @Via("resource")
    @SerializedName("quoteDetails")
    private String detailsQuotesDetails;

    @Inject
    @Via("resource")
    @SerializedName("quoteIssueText")
    private String detailsQuoteIssueText;

    @Inject
    @Via("resource")
    @SerializedName("contactUsCTA")
    private String detailsContactUsCTA;

    @Inject
    @Via("resource")
    @SerializedName("contactUsLink")
    private String detailsContactUsLink;

    @Inject
    @Via("resource")
    @SerializedName("cancelCTA")
    private String detailsCancelCTA;

    @Inject
    @Via("resource")
    @SerializedName("cancelLink")
    private String detailsCancelLink;

    @Inject
    @Via("resource")
    @SerializedName("quoteDetailsHeading")
    private String detailsQuoteDetailsHeading;

    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    @SerializedName("printQuoteBtn")
    private String printQuoteBtn;
    
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    @SerializedName("pricingInfoLabel")
    private String pricingInfoLabel;

    @Inject
    @Via("resource")
    @SerializedName("detailsQuoteNumber")
    private String detailsQuoteNumber;

    @Inject
    @Via("resource")
    @SerializedName("detailsValidFrom")
    private String detailsValidFrom;

    @Inject
    @Via("resource")
    @SerializedName("detailsValidThrough")
    private String detailsValidThrough;

    @Inject
    @Via("resource")
    @SerializedName("orderedBy")
    private String detailsOrderedBy;
    
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    @SerializedName("itemsTitle")
    private String itemsTitle;
    
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    @SerializedName("shipmentNumberLabel")
    private String detailsShipmentNumberLabel;
    
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    @SerializedName("pricePerUnit")
    private String pricePerUnit;
    
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    @SerializedName("yourPriceLabel")
    private String detailsYourPriceLabel;
    
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    @SerializedName("listPriceLabel")
    private String detailsListPriceLabel;
    
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    @SerializedName("totalPriceLabel")
    private String totalPriceLabel;
    
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    @SerializedName("quantityLabel")
    private String detailsQuantityLabel;

    @Inject
    @Via("resource")
    @SerializedName("addressDetailsHeading")
    private String addressDetailsHeading;

    @Inject
    @Via("resource")
    @SerializedName("shipToLabel")
    private String shipToLabel;

    @Inject
    @Via("resource")
    @SerializedName("billToLabel")
    private String billToLabel;

    @Inject
    @Via("resource")
    @SerializedName("soldToLabel")
    private String soldToLabel;

    @Inject
    @Via("resource")
    @SerializedName("shiptoNumberLabel")
    private String shiptoNumberLabel;

    @Inject
    @Via("resource")
    @SerializedName("billtoNumberLabel")
    private String billtoNumberLabel;

    @Inject
    @Via("resource")
    @SerializedName("soldtoNumberLabel")
    private String soldtoNumberLabel;

    @Inject
    @Via("resource")
    @SerializedName("productsHeading")
    private String productsHeading;

    @Inject
    @Via("resource")
    @SerializedName("estDeliveryDate")
    private String estDeliveryDate;

    @Inject
    @Via("resource")
    @SerializedName("productQuantityLabel")
    private String quantityLabel;

    @Inject
    @Via("resource")
    @SerializedName("defaultPRDImage")
    private String defaultPRDImage;

    @Inject
    @Via("resource")
    @SerializedName("defaultPRDImageAltText")
    private String defaultPRDImageAltText;

    @Inject
    @Via("resource")
    @SerializedName("specialInstructionsHeading")
    private String specialInstructionsHeading;
    
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    @SerializedName("nonPurchasableMessage")
    private String nonPurchasableMessage;
    
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    @SerializedName("nonPurchasableAltText")
    private String nonPurchasableAltText;

    @Inject
    @Via("resource")
    @SerializedName("grantAmountUsed")
    private String grantAmountUsed;

    @Inject
    @Via("resource")
    @SerializedName("amountSummaryHeading")
    private String amountSummaryHeading;

    @Inject
    @Via("resource")
    @SerializedName("src")
    private String iconSrc;

    @Inject
    @Via("resource")
    @SerializedName("altText")
    private String iconAltText;

    @Inject
    @Via("resource")
    @SerializedName("infoMessage")
    private String infoMessage;

    @Inject
    @Via("resource")
    @SerializedName("amountSubtotal")
    private String amountSubtotal;

    @Inject
    @Via("resource")
    @SerializedName("shippingAndHandling")
    private String shippingAndHandling;

    @Inject
    @Via("resource")
    @SerializedName("amountTaxes")
    private String amountTaxes;

    @Inject
    @Via("resource")
    @SerializedName("amountSurcharge")
    private String amountSurcharge;

    @Inject
    @Via("resource")
    @SerializedName("amountPromotion")
    private String amountPromotion;

    @Inject
    @Via("resource")
    @SerializedName("amountTotalPromoSavings")
    private String amountTotalPromoSavings;

    @Inject
    @Via("resource")
    @SerializedName("amountTotal")
    private String amountTotal;

    @Inject
    @Via("resource")
    @SerializedName("amountSurchargesToolTip")
    private String amountSurchargesToolTip;

    @Inject
    @Via("resource")
    @SerializedName("amountTaxToolTip")
    private String amountTaxToolTip;

    @Inject
    @Via("resource")
    @SerializedName("amountToolTipAltText")
    private String amountToolTipAltText;

    @Inject
    private BDBApiEndpointService bdbApiEndpointService;

    /**
     * The resource resolver.
     */
    @SlingObject
    private ResourceResolver resourceResolver;

    /**
     * The hybris site id.
     */
    private String hybrisSiteId;

    private String quoteHistoryLabels;
    
    private String userQuoteHistoryConfig;

    @PostConstruct
    void init() {
        log.debug("Inside Certifications Init Method");

        hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);

        ExcludeUtil excludeUtilObject = new ExcludeUtil();
        setQuoteHistoryLabels(excludeUtilObject);
        setUserQuoteHistoryConfig(excludeUtilObject);
    }

    @Override
    public String getQuoteHistoryLabels() {
        return quoteHistoryLabels;
    }
    
    @Override
    public String getUserQuoteHistoryConfig() {
        return userQuoteHistoryConfig;
    }

    private void setQuoteHistoryLabels(ExcludeUtil excludeUtilObject) {
        Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                .addSerializationExclusionStrategy(excludeUtilObject).create();
        AccMgmtQuotesHistoryModelLabelBean accMgmtQuotesHistoryModelLabelBean = new AccMgmtQuotesHistoryModelLabelBean();
        quoteHistoryLabels = StringUtils.EMPTY;

        accMgmtQuotesHistoryModelLabelBean.setQuotesHistoryheading(quotesHistoryheading);
        accMgmtQuotesHistoryModelLabelBean.setAddressChangeInfo(addressChangeInfo);
        accMgmtQuotesHistoryModelLabelBean.setQuoteInputLabel(quoteInputLabel);
        accMgmtQuotesHistoryModelLabelBean.setValidQuoteTabLabel(validQuoteTabLabel);
        accMgmtQuotesHistoryModelLabelBean.setExpiredQuoteTabLabel(expiredQuoteTabLabel);
        accMgmtQuotesHistoryModelLabelBean.setAllQuoteTabLabel(allQuoteTabLabel);
        accMgmtQuotesHistoryModelLabelBean.setServerErrorMsg(serverErrorMsg);
        
        AccMgmtQuotesHistoryModelLabelBean quoteListCardModelLabels = new AccMgmtQuotesHistoryModelLabelBean();
        quoteListCardModelLabels.setQuoteNumber(quoteNumber);
        quoteListCardModelLabels.setValidFrom(validFrom);
        quoteListCardModelLabels.setValidThrough(validThrough);
        quoteListCardModelLabels.setTotal(total);
        quoteListCardModelLabels.setViewDetailsBtn(viewDetailsBtn);
		JsonElement quoteListCardLabels = json.toJsonTree(quoteListCardModelLabels);
		
		AccMgmtQuotesHistoryModelLabelBean filtersModelLabels = new AccMgmtQuotesHistoryModelLabelBean();
		filtersModelLabels.setNoQuoteFound(noQuoteFound);
		filtersModelLabels.setNoQuoteInfo(noQuoteInfo);
		filtersModelLabels.setClearSearch(clearSearch);
		filtersModelLabels.setNoQuoteForDefaultShipTo(noQuoteForDefaultShipTo);
		filtersModelLabels.setNoQuoteForDefaultShipToInfo(noQuoteForDefaultShipToInfo);
		JsonElement filtersLabels = json.toJsonTree(filtersModelLabels);
		
		AccMgmtQuotesHistoryModelLabelBean iconLabel = new AccMgmtQuotesHistoryModelLabelBean();
		iconLabel.setIconSrc(iconSrc);
		iconLabel.setIconAltText(iconAltText);
		JsonElement icon = json.toJsonTree(iconLabel);
		
		AccMgmtQuotesHistoryModelLabelBean messageLabel = new AccMgmtQuotesHistoryModelLabelBean();
		messageLabel.setInfoMessage(infoMessage);
		JsonElement message = json.toJsonTree(messageLabel);
		
		AccMgmtQuotesHistoryModelLabelBean infoLabel = new AccMgmtQuotesHistoryModelLabelBean();
		infoLabel.setIcon(icon);
		infoLabel.setMessage(message);
		JsonElement info = json.toJsonTree(infoLabel);
		
		AccMgmtQuotesHistoryModelLabelBean amountSummaryLabel = new AccMgmtQuotesHistoryModelLabelBean();
		amountSummaryLabel.setGrantAmountUsed(grantAmountUsed);
		amountSummaryLabel.setAmountSummaryHeading(amountSummaryHeading);
		amountSummaryLabel.setAmountSubtotal(amountSubtotal);
		amountSummaryLabel.setShippingAndHandling(shippingAndHandling);
		amountSummaryLabel.setAmountTaxes(amountTaxes);
		amountSummaryLabel.setAmountSurcharge(amountSurcharge);
		amountSummaryLabel.setAmountPromotion(amountPromotion);
		amountSummaryLabel.setAmountTotalPromoSavings(amountTotalPromoSavings);
		amountSummaryLabel.setAmountTotal(amountTotal);
		amountSummaryLabel.setAmountSurchargesToolTip(amountSurchargesToolTip);
		amountSummaryLabel.setAmountTaxToolTip(amountTaxToolTip);
		amountSummaryLabel.setAmountToolTipAltText(amountToolTipAltText);
		amountSummaryLabel.setInfo(info);
		JsonElement amountSummary = json.toJsonTree(amountSummaryLabel);
		
		AccMgmtQuotesHistoryModelLabelBean addressDetailsModelLabel = new AccMgmtQuotesHistoryModelLabelBean();
		addressDetailsModelLabel.setAddressDetailsHeading(addressDetailsHeading);
		addressDetailsModelLabel.setShipToLabel(shipToLabel);
		addressDetailsModelLabel.setBillToLabel(billToLabel);
		addressDetailsModelLabel.setSoldToLabel(soldToLabel);
		addressDetailsModelLabel.setShiptoNumberLabel(shiptoNumberLabel);
		addressDetailsModelLabel.setBilltoNumberLabel(billtoNumberLabel);
		addressDetailsModelLabel.setSoldtoNumberLabel(soldtoNumberLabel);
		JsonElement addressDetailsLabel = json.toJsonTree(addressDetailsModelLabel);
		
		AccMgmtQuotesHistoryModelLabelBean specialInstructionsLabel = new AccMgmtQuotesHistoryModelLabelBean();
		specialInstructionsLabel.setSpecialInstructionsHeading(specialInstructionsHeading);
		JsonElement specialInstructions = json.toJsonTree(specialInstructionsLabel);
		
		AccMgmtQuotesHistoryModelLabelBean nonPurchasableLabel = new AccMgmtQuotesHistoryModelLabelBean();
		nonPurchasableLabel.setNonPurchasableMessage(nonPurchasableMessage);
		nonPurchasableLabel.setNonPurchasableAltText(nonPurchasableAltText);
		JsonElement nonPurchasable = json.toJsonTree(nonPurchasableLabel);
		
		AccMgmtQuotesHistoryModelLabelBean productsLabel = new AccMgmtQuotesHistoryModelLabelBean();
		productsLabel.setProductsHeading(productsHeading);
		productsLabel.setEstDeliveryDate(estDeliveryDate);
		productsLabel.setQuantityLabel(quantityLabel);
		productsLabel.setDefaultPRDImage(defaultPRDImage);
		productsLabel.setDefaultPRDImageAltText(defaultPRDImageAltText);
		JsonElement products = json.toJsonTree(productsLabel);
		
		AccMgmtQuotesHistoryModelLabelBean quoteDetailsModelLabels = new AccMgmtQuotesHistoryModelLabelBean();
		quoteDetailsModelLabels.setDetailsQuoteNumberHeading(detailsQuoteNumberHeading);
		quoteDetailsModelLabels.setDetailsQuotesDetails(detailsQuotesDetails);
		quoteDetailsModelLabels.setDetailsQuoteIssueText(detailsQuoteIssueText);
		quoteDetailsModelLabels.setDetailsContactUsCTA(detailsContactUsCTA);
		quoteDetailsModelLabels.setDetailsContactUsLink(detailsContactUsLink);
		quoteDetailsModelLabels.setDetailsCancelCTA(detailsCancelCTA);
		quoteDetailsModelLabels.setDetailsCancelLink(detailsCancelLink);
		quoteDetailsModelLabels.setDetailsQuoteDetailsHeading(detailsQuoteDetailsHeading);
		quoteDetailsModelLabels.setPrintQuoteBtn(printQuoteBtn);
		quoteDetailsModelLabels.setPricingInfoLabel(pricingInfoLabel);
		quoteDetailsModelLabels.setDetailsQuoteNumber(detailsQuoteNumber);
		quoteDetailsModelLabels.setDetailsValidFrom(detailsValidFrom);
		quoteDetailsModelLabels.setDetailsValidThrough(detailsValidThrough);
		quoteDetailsModelLabels.setDetailsOrderedBy(detailsOrderedBy);
		quoteDetailsModelLabels.setItemsTitle(itemsTitle);
		quoteDetailsModelLabels.setDetailsShipmentNumberLabel(detailsShipmentNumberLabel);
		quoteDetailsModelLabels.setPricePerUnit(pricePerUnit);
		quoteDetailsModelLabels.setDetailsYourPriceLabel(detailsYourPriceLabel);
		quoteDetailsModelLabels.setDetailsListPriceLabel(detailsListPriceLabel);
		quoteDetailsModelLabels.setTotalPriceLabel(totalPriceLabel);
		quoteDetailsModelLabels.setDetailsQuantityLabel(detailsQuantityLabel);
		quoteDetailsModelLabels.setAmountSummary(amountSummary);
		quoteDetailsModelLabels.setAddressDetailsLabel(addressDetailsLabel);
		quoteDetailsModelLabels.setSpecialInstructions(specialInstructions);
		quoteDetailsModelLabels.setNonPurchasable(nonPurchasable);
		quoteDetailsModelLabels.setProducts(products);
		JsonElement quoteDetailsLabels = json.toJsonTree(quoteDetailsModelLabels);
        
        accMgmtQuotesHistoryModelLabelBean.setQuoteListCardLabels(quoteListCardLabels);
        accMgmtQuotesHistoryModelLabelBean.setFiltersLabels(filtersLabels);
        accMgmtQuotesHistoryModelLabelBean.setQuoteDetailsLabels(quoteDetailsLabels);

        AccMgmtQuotesHistoryModelLabel AccMgmtQuotesHistoryModelLabel = new AccMgmtQuotesHistoryModelLabel(accMgmtQuotesHistoryModelLabelBean);
quoteHistoryLabels= json.toJson(AccMgmtQuotesHistoryModelLabel);

    }
    
    private void setUserQuoteHistoryConfig(ExcludeUtil excludeUtilObject) {
    	userQuoteHistoryConfig = StringUtils.EMPTY;
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		
		AccMgmtQuotesHistoryModelConfig accountQuotesHistoryConfig = new AccMgmtQuotesHistoryModelConfig();
		
		if (bdbApiEndpointService != null) {
			
			String getQuotesHistoryEndpoint = StringUtils.replace(
					bdbApiEndpointService.getGetQuotesHistoryEndpoint(),
					CommonConstants.HYBRIS_SITE_LITERAL,
					hybrisSiteId);
			Payload getQuotesHistoryEndpointPayload = new Payload(
					bdbApiEndpointService.getBDBHybrisDomain() + getQuotesHistoryEndpoint,
					HttpConstants.METHOD_GET);
			PayloadConfig getQuoteHistoryDetails = new PayloadConfig(getQuotesHistoryEndpointPayload);
			
			String getQuoteDetailsEndpoint = StringUtils.replace(
					bdbApiEndpointService.getGetQuoteDetailsEndpoint(),
					CommonConstants.HYBRIS_SITE_LITERAL,
					hybrisSiteId);
			Payload getQuoteDetailsPayload = new Payload(
					bdbApiEndpointService.getBDBHybrisDomain() + getQuoteDetailsEndpoint,
					HttpConstants.METHOD_POST);
			PayloadConfig getQuoteDetails = new PayloadConfig(getQuoteDetailsPayload);
			
			accountQuotesHistoryConfig.setGetQuoteHistoryDetails(getQuoteHistoryDetails);
			accountQuotesHistoryConfig.setGetQuoteDetails(getQuoteDetails);
			userQuoteHistoryConfig = json.toJson(accountQuotesHistoryConfig);
		}
	}

    public String getHybrisSiteId() {
        return hybrisSiteId;
    }

}
