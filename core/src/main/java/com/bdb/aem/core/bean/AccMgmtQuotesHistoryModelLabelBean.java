package com.bdb.aem.core.bean;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class AccMgmtQuotesHistoryModelLabelBean {


    @SerializedName("heading")
    private String quotesHistoryheading;
    
    @SerializedName("addressChangeInfo")
    private String addressChangeInfo;

    @SerializedName("validQuoteTabLabel")
    private String validQuoteTabLabel;
    
    @SerializedName("expiredQuoteTabLabel")
    private String expiredQuoteTabLabel;
    
    @SerializedName("allQuoteTabLabel")
    private String allQuoteTabLabel;
    
    @SerializedName("serverErrorMsg")
    private String serverErrorMsg;


    @SerializedName("quoteNumber")
    private String quoteNumber;


    @SerializedName("validFrom")
    private String validFrom;


    @SerializedName("validThrough")
    private String validThrough;


    @SerializedName("total")
    private String total;


    @SerializedName("viewDetailsBtn")
    private String viewDetailsBtn;


    @SerializedName("quoteInputLabel")
    private String quoteInputLabel;


    @SerializedName("noQuoteFound")
    private String noQuoteFound;


    @SerializedName("noQuoteInfo")
    private String noQuoteInfo;


    @SerializedName("clearSearch")
    private String clearSearch;

    @SerializedName("noQuoteForDefaultShipTo")
    private String noQuoteForDefaultShipTo;
    
    @SerializedName("noQuoteForDefaultShipToInfo")
    private String noQuoteForDefaultShipToInfo;

    @SerializedName("quoteNumberHeading")
    private String detailsQuoteNumberHeading;


    @SerializedName("quoteDetails")
    private String detailsQuotesDetails;


    @SerializedName("quoteIssueText")
    private String detailsQuoteIssueText;


    @SerializedName("contactUsCTA")
    private String detailsContactUsCTA;


    @SerializedName("contactUsLink")
    private String detailsContactUsLink;


    @SerializedName("cancelCTA")
    private String detailsCancelCTA;


    @SerializedName("cancelLink")
    private String detailsCancelLink;


    @SerializedName("quoteDetailsHeading")
    private String detailsQuoteDetailsHeading;


    @SerializedName("printQuoteBtn")
    private String printQuoteBtn;
    
    @SerializedName("pricingInfoLabel")
    private String pricingInfoLabel;


    @SerializedName("detailsQuoteNumber")
    private String detailsQuoteNumber;


    @SerializedName("detailsValidFrom")
    private String detailsValidFrom;


    @SerializedName("detailsValidThrough")
    private String detailsValidThrough;


    @SerializedName("orderedBy")
    private String detailsOrderedBy;
    
    @SerializedName("itemsTitle")
    private String itemsTitle;
    
    @SerializedName("shipmentNumberLabel")
    private String detailsShipmentNumberLabel;
    
    @SerializedName("pricePerUnit")
    private String pricePerUnit;
    
    @SerializedName("yourPriceLabel")
    private String detailsYourPriceLabel;
    
    @SerializedName("listPriceLabel")
    private String detailsListPriceLabel;
    
    @SerializedName("totalPriceLabel")
    private String totalPriceLabel;
    
    @SerializedName("quantityLabel")
    private String detailsQuantityLabel;

    @SerializedName("addressDetailsHeading")
    private String addressDetailsHeading;


    @SerializedName("shipToLabel")
    private String shipToLabel;


    @SerializedName("billToLabel")
    private String billToLabel;


    @SerializedName("soldToLabel")
    private String soldToLabel;


    @SerializedName("shiptoNumberLabel")
    private String shiptoNumberLabel;


    @SerializedName("billtoNumberLabel")
    private String billtoNumberLabel;


    @SerializedName("soldtoNumberLabel")
    private String soldtoNumberLabel;


    @SerializedName("productsHeading")
    private String productsHeading;


    @SerializedName("estDeliveryDate")
    private String estDeliveryDate;


    @SerializedName("productQuantityLabel")
    private String quantityLabel;


    @SerializedName("defaultPRDImage")
    private String defaultPRDImage;


    @SerializedName("defaultPRDImageAltText")
    private String defaultPRDImageAltText;


    @SerializedName("specialInstructionsHeading")
    private String specialInstructionsHeading;
    
    @SerializedName("nonPurchasableMessage")
    private String nonPurchasableMessage;
    
    @SerializedName("nonPurchasableAltText")
    private String nonPurchasableAltText;


    @SerializedName("grantAmountUsed")
    private String grantAmountUsed;


    @SerializedName("amountSummaryHeading")
    private String amountSummaryHeading;


    @SerializedName("src")
    private String iconSrc;


    @SerializedName("altText")
    private String iconAltText;


    @SerializedName("infoMessage")
    private String infoMessage;


    @SerializedName("amountSubtotal")
    private String amountSubtotal;


    @SerializedName("shippingAndHandling")
    private String shippingAndHandling;


    @SerializedName("amountTaxes")
    private String amountTaxes;


    @SerializedName("amountSurcharge")
    private String amountSurcharge;


    @SerializedName("amountPromotion")
    private String amountPromotion;


    @SerializedName("amountTotalPromoSavings")
    private String amountTotalPromoSavings;


    @SerializedName("amountTotal")
    private String amountTotal;


    @SerializedName("amountSurchargesToolTip")
    private String amountSurchargesToolTip;


    @SerializedName("amountTaxToolTip")
    private String amountTaxToolTip;


    @SerializedName("amountToolTipAltText")
    private String amountToolTipAltText;
    
    @SerializedName("quoteListCardLabels")
    private JsonElement quoteListCardLabels;
    
    @SerializedName("filtersLabels")
    private JsonElement filtersLabels;
    
    @SerializedName("icon")
    private JsonElement icon;
    
    @SerializedName("message")
    private JsonElement message;
    
    @SerializedName("info")
    private JsonElement info;
    
    @SerializedName("amountSummary")
    private JsonElement amountSummary;
    
    @SerializedName("addressDetailsLabel")
    private JsonElement addressDetailsLabel;
    
    @SerializedName("specialInstructions")
    private JsonElement specialInstructions;
    
    @SerializedName("nonPurchasable")
    private JsonElement nonPurchasable;
    
    @SerializedName("products")
    private JsonElement products;
    
    @SerializedName("quoteDetailsLabels")
    private JsonElement quoteDetailsLabels;

    public String getQuotesHistoryheading() {
        return quotesHistoryheading;
    }

    public void setQuotesHistoryheading(String quotesHistoryheading) {
        this.quotesHistoryheading = quotesHistoryheading;
    }
    
    /**
	 * @return the addressChangeInfo
	 */
	public String getAddressChangeInfo() {
		return addressChangeInfo;
	}

	/**
	 * @param addressChangeInfo the addressChangeInfo to set
	 */
	public void setAddressChangeInfo(String addressChangeInfo) {
		this.addressChangeInfo = addressChangeInfo;
	}

	/**
	 * @return the validQuoteTabLabel
	 */
	public String getValidQuoteTabLabel() {
		return validQuoteTabLabel;
	}

	/**
	 * @param validQuoteTabLabel the validQuoteTabLabel to set
	 */
	public void setValidQuoteTabLabel(String validQuoteTabLabel) {
		this.validQuoteTabLabel = validQuoteTabLabel;
	}

	/**
	 * @return the expiredQuoteTabLabel
	 */
	public String getExpiredQuoteTabLabel() {
		return expiredQuoteTabLabel;
	}

	/**
	 * @param expiredQuoteTabLabel the expiredQuoteTabLabel to set
	 */
	public void setExpiredQuoteTabLabel(String expiredQuoteTabLabel) {
		this.expiredQuoteTabLabel = expiredQuoteTabLabel;
	}

	/**
	 * @return the allQuoteTabLabel
	 */
	public String getAllQuoteTabLabel() {
		return allQuoteTabLabel;
	}

	/**
	 * @param allQuoteTabLabel the allQuoteTabLabel to set
	 */
	public void setAllQuoteTabLabel(String allQuoteTabLabel) {
		this.allQuoteTabLabel = allQuoteTabLabel;
	}

	/**
	 * @return the serverErrorMsg
	 */
	public String getServerErrorMsg() {
		return serverErrorMsg;
	}

	/**
	 * @param serverErrorMsg the serverErrorMsg to set
	 */
	public void setServerErrorMsg(String serverErrorMsg) {
		this.serverErrorMsg = serverErrorMsg;
	}

	public String getQuoteNumber() {
        return quoteNumber;
    }

    public void setQuoteNumber(String quoteNumber) {
        this.quoteNumber = quoteNumber;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidThrough() {
        return validThrough;
    }

    public void setValidThrough(String validThrough) {
        this.validThrough = validThrough;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getViewDetailsBtn() {
        return viewDetailsBtn;
    }

    public void setViewDetailsBtn(String viewDetailsBtn) {
        this.viewDetailsBtn = viewDetailsBtn;
    }

    public String getQuoteInputLabel() {
        return quoteInputLabel;
    }

    public void setQuoteInputLabel(String quoteInputLabel) {
        this.quoteInputLabel = quoteInputLabel;
    }

    public String getNoQuoteFound() {
        return noQuoteFound;
    }

    public void setNoQuoteFound(String noQuoteFound) {
        this.noQuoteFound = noQuoteFound;
    }

    public String getNoQuoteInfo() {
        return noQuoteInfo;
    }

    public void setNoQuoteInfo(String noQuoteInfo) {
        this.noQuoteInfo = noQuoteInfo;
    }

    public String getClearSearch() {
        return clearSearch;
    }

    public void setClearSearch(String clearSearch) {
        this.clearSearch = clearSearch;
    }

    /**
	 * @return the noQuoteForDefaultShipTo
	 */
	public String getNoQuoteForDefaultShipTo() {
		return noQuoteForDefaultShipTo;
	}

	/**
	 * @param noQuoteForDefaultShipTo the noQuoteForDefaultShipTo to set
	 */
	public void setNoQuoteForDefaultShipTo(String noQuoteForDefaultShipTo) {
		this.noQuoteForDefaultShipTo = noQuoteForDefaultShipTo;
	}

	/**
	 * @return the noQuoteForDefaultShipToInfo
	 */
	public String getNoQuoteForDefaultShipToInfo() {
		return noQuoteForDefaultShipToInfo;
	}

	/**
	 * @param noQuoteForDefaultShipToInfo the noQuoteForDefaultShipToInfo to set
	 */
	public void setNoQuoteForDefaultShipToInfo(String noQuoteForDefaultShipToInfo) {
		this.noQuoteForDefaultShipToInfo = noQuoteForDefaultShipToInfo;
	}

	public String getDetailsQuoteNumberHeading() {
        return detailsQuoteNumberHeading;
    }

    public void setDetailsQuoteNumberHeading(String detailsQuoteNumberHeading) {
        this.detailsQuoteNumberHeading = detailsQuoteNumberHeading;
    }

    public String getDetailsQuotesDetails() {
        return detailsQuotesDetails;
    }

    public void setDetailsQuotesDetails(String detailsQuotesDetails) {
        this.detailsQuotesDetails = detailsQuotesDetails;
    }

    public String getDetailsQuoteIssueText() {
        return detailsQuoteIssueText;
    }

    public void setDetailsQuoteIssueText(String detailsQuoteIssueText) {
        this.detailsQuoteIssueText = detailsQuoteIssueText;
    }

    public String getDetailsContactUsCTA() {
        return detailsContactUsCTA;
    }

    public void setDetailsContactUsCTA(String detailsContactUsCTA) {
        this.detailsContactUsCTA = detailsContactUsCTA;
    }

    public String getDetailsContactUsLink() {
        return detailsContactUsLink;
    }

    public void setDetailsContactUsLink(String detailsContactUsLink) {
        this.detailsContactUsLink = detailsContactUsLink;
    }

    public String getDetailsCancelCTA() {
        return detailsCancelCTA;
    }

    public void setDetailsCancelCTA(String detailsCancelCTA) {
        this.detailsCancelCTA = detailsCancelCTA;
    }

    public String getDetailsCancelLink() {
        return detailsCancelLink;
    }

    public void setDetailsCancelLink(String detailsCancelLink) {
        this.detailsCancelLink = detailsCancelLink;
    }

    public String getDetailsQuoteDetailsHeading() {
        return detailsQuoteDetailsHeading;
    }

    public void setDetailsQuoteDetailsHeading(String detailsQuoteDetailsHeading) {
        this.detailsQuoteDetailsHeading = detailsQuoteDetailsHeading;
    }

    public String getPrintQuoteBtn() {
		return printQuoteBtn;
	}

	public void setPrintQuoteBtn(String printQuoteBtn) {
		this.printQuoteBtn = printQuoteBtn;
	}

	public String getPricingInfoLabel() {
		return pricingInfoLabel;
	}

	public void setPricingInfoLabel(String pricingInfoLabel) {
		this.pricingInfoLabel = pricingInfoLabel;
	}

	public String getDetailsQuoteNumber() {
        return detailsQuoteNumber;
    }

    public void setDetailsQuoteNumber(String detailsQuoteNumber) {
        this.detailsQuoteNumber = detailsQuoteNumber;
    }

    public String getDetailsValidFrom() {
        return detailsValidFrom;
    }

    public void setDetailsValidFrom(String detailsValidFrom) {
        this.detailsValidFrom = detailsValidFrom;
    }

    public String getDetailsValidThrough() {
        return detailsValidThrough;
    }

    public void setDetailsValidThrough(String detailsValidThrough) {
        this.detailsValidThrough = detailsValidThrough;
    }

    public String getDetailsOrderedBy() {
        return detailsOrderedBy;
    }

    public void setDetailsOrderedBy(String detailsOrderedBy) {
        this.detailsOrderedBy = detailsOrderedBy;
    }

    /**
	 * @return the itemsTitle
	 */
	public String getItemsTitle() {
		return itemsTitle;
	}

	/**
	 * @param itemsTitle the itemsTitle to set
	 */
	public void setItemsTitle(String itemsTitle) {
		this.itemsTitle = itemsTitle;
	}

	/**
	 * @return the detailsShipmentNumberLabel
	 */
	public String getDetailsShipmentNumberLabel() {
		return detailsShipmentNumberLabel;
	}

	/**
	 * @param detailsShipmentNumberLabel the detailsShipmentNumberLabel to set
	 */
	public void setDetailsShipmentNumberLabel(String detailsShipmentNumberLabel) {
		this.detailsShipmentNumberLabel = detailsShipmentNumberLabel;
	}

	/**
	 * @return the pricePerUnit
	 */
	public String getPricePerUnit() {
		return pricePerUnit;
	}

	/**
	 * @param pricePerUnit the pricePerUnit to set
	 */
	public void setPricePerUnit(String pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	/**
	 * @return the detailsYourPriceLabel
	 */
	public String getDetailsYourPriceLabel() {
		return detailsYourPriceLabel;
	}

	/**
	 * @param detailsYourPriceLabel the detailsYourPriceLabel to set
	 */
	public void setDetailsYourPriceLabel(String detailsYourPriceLabel) {
		this.detailsYourPriceLabel = detailsYourPriceLabel;
	}

	/**
	 * @return the detailsListPriceLabel
	 */
	public String getDetailsListPriceLabel() {
		return detailsListPriceLabel;
	}

	/**
	 * @param detailsListPriceLabel the detailsListPriceLabel to set
	 */
	public void setDetailsListPriceLabel(String detailsListPriceLabel) {
		this.detailsListPriceLabel = detailsListPriceLabel;
	}

	/**
	 * @return the totalPriceLabel
	 */
	public String getTotalPriceLabel() {
		return totalPriceLabel;
	}

	/**
	 * @param totalPriceLabel the totalPriceLabel to set
	 */
	public void setTotalPriceLabel(String totalPriceLabel) {
		this.totalPriceLabel = totalPriceLabel;
	}

	public String getDetailsQuantityLabel() {
		return detailsQuantityLabel;
	}

	public void setDetailsQuantityLabel(String detailsQuantityLabel) {
		this.detailsQuantityLabel = detailsQuantityLabel;
	}

	public String getAddressDetailsHeading() {
        return addressDetailsHeading;
    }

    public void setAddressDetailsHeading(String addressDetailsHeading) {
        this.addressDetailsHeading = addressDetailsHeading;
    }

    public String getShipToLabel() {
        return shipToLabel;
    }

    public void setShipToLabel(String shipToLabel) {
        this.shipToLabel = shipToLabel;
    }

    public String getBillToLabel() {
        return billToLabel;
    }

    public void setBillToLabel(String billToLabel) {
        this.billToLabel = billToLabel;
    }

    public String getSoldToLabel() {
        return soldToLabel;
    }

    public void setSoldToLabel(String soldToLabel) {
        this.soldToLabel = soldToLabel;
    }

    public String getShiptoNumberLabel() {
        return shiptoNumberLabel;
    }

    public void setShiptoNumberLabel(String shiptoNumberLabel) {
        this.shiptoNumberLabel = shiptoNumberLabel;
    }

    public String getBilltoNumberLabel() {
        return billtoNumberLabel;
    }

    public void setBilltoNumberLabel(String billtoNumberLabel) {
        this.billtoNumberLabel = billtoNumberLabel;
    }

    public String getSoldtoNumberLabel() {
        return soldtoNumberLabel;
    }

    public void setSoldtoNumberLabel(String soldtoNumberLabel) {
        this.soldtoNumberLabel = soldtoNumberLabel;
    }

    public String getProductsHeading() {
        return productsHeading;
    }

    public void setProductsHeading(String productsHeading) {
        this.productsHeading = productsHeading;
    }

    public String getEstDeliveryDate() {
        return estDeliveryDate;
    }

    public void setEstDeliveryDate(String estDeliveryDate) {
        this.estDeliveryDate = estDeliveryDate;
    }

    public String getQuantityLabel() {
        return quantityLabel;
    }

    public void setQuantityLabel(String quantityLabel) {
        this.quantityLabel = quantityLabel;
    }

    public String getDefaultPRDImage() {
        return defaultPRDImage;
    }

    public void setDefaultPRDImage(String defaultPRDImage) {
        this.defaultPRDImage = defaultPRDImage;
    }

    public String getDefaultPRDImageAltText() {
        return defaultPRDImageAltText;
    }

    public void setDefaultPRDImageAltText(String defaultPRDImageAltText) {
        this.defaultPRDImageAltText = defaultPRDImageAltText;
    }

    public String getSpecialInstructionsHeading() {
        return specialInstructionsHeading;
    }

    public void setSpecialInstructionsHeading(String specialInstructionsHeading) {
        this.specialInstructionsHeading = specialInstructionsHeading;
    }

    /**
	 * @return the nonPurchasableMessage
	 */
	public String getNonPurchasableMessage() {
		return nonPurchasableMessage;
	}

	/**
	 * @param nonPurchasableMessage the nonPurchasableMessage to set
	 */
	public void setNonPurchasableMessage(String nonPurchasableMessage) {
		this.nonPurchasableMessage = nonPurchasableMessage;
	}

	/**
	 * @return the nonPurchasableAltText
	 */
	public String getNonPurchasableAltText() {
		return nonPurchasableAltText;
	}

	/**
	 * @param nonPurchasableAltText the nonPurchasableAltText to set
	 */
	public void setNonPurchasableAltText(String nonPurchasableAltText) {
		this.nonPurchasableAltText = nonPurchasableAltText;
	}

	public String getGrantAmountUsed() {
        return grantAmountUsed;
    }

    public void setGrantAmountUsed(String grantAmountUsed) {
        this.grantAmountUsed = grantAmountUsed;
    }

    public String getAmountSummaryHeading() {
        return amountSummaryHeading;
    }

    public void setAmountSummaryHeading(String amountSummaryHeading) {
        this.amountSummaryHeading = amountSummaryHeading;
    }

    public String getIconSrc() {
        return iconSrc;
    }

    public void setIconSrc(String iconSrc) {
        this.iconSrc = iconSrc;
    }

    public String getIconAltText() {
        return iconAltText;
    }

    public void setIconAltText(String iconAltText) {
        this.iconAltText = iconAltText;
    }

    public String getInfoMessage() {
        return infoMessage;
    }

    public void setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
    }

    public String getAmountSubtotal() {
        return amountSubtotal;
    }

    public void setAmountSubtotal(String amountSubtotal) {
        this.amountSubtotal = amountSubtotal;
    }

    public String getShippingAndHandling() {
        return shippingAndHandling;
    }

    public void setShippingAndHandling(String shippingAndHandling) {
        this.shippingAndHandling = shippingAndHandling;
    }

    public String getAmountTaxes() {
        return amountTaxes;
    }

    public void setAmountTaxes(String amountTaxes) {
        this.amountTaxes = amountTaxes;
    }

    public String getAmountSurcharge() {
        return amountSurcharge;
    }

    public void setAmountSurcharge(String amountSurcharge) {
        this.amountSurcharge = amountSurcharge;
    }

    public String getAmountPromotion() {
        return amountPromotion;
    }

    public void setAmountPromotion(String amountPromotion) {
        this.amountPromotion = amountPromotion;
    }

    public String getAmountTotalPromoSavings() {
        return amountTotalPromoSavings;
    }

    public void setAmountTotalPromoSavings(String amountTotalPromoSavings) {
        this.amountTotalPromoSavings = amountTotalPromoSavings;
    }

    public String getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(String amountTotal) {
        this.amountTotal = amountTotal;
    }

    public String getAmountSurchargesToolTip() {
        return amountSurchargesToolTip;
    }

    public void setAmountSurchargesToolTip(String amountSurchargesToolTip) {
        this.amountSurchargesToolTip = amountSurchargesToolTip;
    }

    public String getAmountTaxToolTip() {
        return amountTaxToolTip;
    }

    public void setAmountTaxToolTip(String amountTaxToolTip) {
        this.amountTaxToolTip = amountTaxToolTip;
    }

    public String getAmountToolTipAltText() {
        return amountToolTipAltText;
    }

    public void setAmountToolTipAltText(String amountToolTipAltText) {
        this.amountToolTipAltText = amountToolTipAltText;
    }
    
    public JsonElement getQuoteListCardLabels() {
        return quoteListCardLabels;
    }
    
    public void setQuoteListCardLabels(JsonElement quoteListCardLabels) {
		this.quoteListCardLabels = quoteListCardLabels;
	}

	/**
	 * @return the filtersLabels
	 */
	public JsonElement getFiltersLabels() {
		return filtersLabels;
	}

	/**
	 * @param filtersLabels the filtersLabels to set
	 */
	public void setFiltersLabels(JsonElement filtersLabels) {
		this.filtersLabels = filtersLabels;
	}

	/**
	 * @return the icon
	 */
	public JsonElement getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(JsonElement icon) {
		this.icon = icon;
	}

	/**
	 * @return the message
	 */
	public JsonElement getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(JsonElement message) {
		this.message = message;
	}

	/**
	 * @return the info
	 */
	public JsonElement getInfo() {
		return info;
	}

	/**
	 * @param info the info to set
	 */
	public void setInfo(JsonElement info) {
		this.info = info;
	}

	/**
	 * @return the amountSummary
	 */
	public JsonElement getAmountSummary() {
		return amountSummary;
	}

	/**
	 * @param amountSummary the amountSummary to set
	 */
	public void setAmountSummary(JsonElement amountSummary) {
		this.amountSummary = amountSummary;
	}

	/**
	 * @return the addressDetailsLabel
	 */
	public JsonElement getAddressDetailsLabel() {
		return addressDetailsLabel;
	}

	/**
	 * @param addressDetailsLabel the addressDetailsLabel to set
	 */
	public void setAddressDetailsLabel(JsonElement addressDetailsLabel) {
		this.addressDetailsLabel = addressDetailsLabel;
	}

	/**
	 * @return the specialInstructions
	 */
	public JsonElement getSpecialInstructions() {
		return specialInstructions;
	}

	/**
	 * @param specialInstructions the specialInstructions to set
	 */
	public void setSpecialInstructions(JsonElement specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	public JsonElement getNonPurchasable() {
		return nonPurchasable;
	}

	public void setNonPurchasable(JsonElement nonPurchasable) {
		this.nonPurchasable = nonPurchasable;
	}

	/**
	 * @return the products
	 */
	public JsonElement getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(JsonElement products) {
		this.products = products;
	}

	/**
	 * @return the quoteDetailsLabels
	 */
	public JsonElement getQuoteDetailsLabels() {
		return quoteDetailsLabels;
	}

	/**
	 * @param quoteDetailsLabels the quoteDetailsLabels to set
	 */
	public void setQuoteDetailsLabels(JsonElement quoteDetailsLabels) {
		this.quoteDetailsLabels = quoteDetailsLabels;
	}
    
}
