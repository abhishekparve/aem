package com.bdb.aem.core.pojo;

import com.bdb.aem.core.bean.AccMgmtQuotesHistoryModelLabelBean;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class AccMgmtQuotesHistoryModelLabel {

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
    
    /** The quoteListCardLabels. */
	@SerializedName("quoteListCardLabels")
	private JsonElement quoteListCardLabels;

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
    
    /** The filtersLabels. */
	@SerializedName("filtersLabels")
	private JsonElement filtersLabels;
	
	/** The icon. */
	@SerializedName("icon")
	private JsonElement icon;
	
	/** The message. */
	@SerializedName("message")
	private JsonElement message;
	
	/** The info. */
	@SerializedName("info")
	private JsonElement info;
	
	/** The amountSummary. */
	@SerializedName("amountSummary")
	private JsonElement amountSummary;
	
	/** The addressDetailsLabel. */
	@SerializedName("addressDetailsLabel")
	private JsonElement addressDetailsLabel;
	
	/** The specialInstructions. */
	@SerializedName("specialInstructions")
	private JsonElement specialInstructions;
	
	/** The nonPurchasable. */
	@SerializedName("nonPurchasable")
	private JsonElement nonPurchasable;
	
	/** The products. */
	@SerializedName("products")
	private JsonElement products;
	
	/** The quoteDetailsLabels. */
	@SerializedName("quoteDetailsLabels")
	private JsonElement quoteDetailsLabels;


    public AccMgmtQuotesHistoryModelLabel(AccMgmtQuotesHistoryModelLabelBean accMgmtQuotesHistoryModelLabelBean){

        this.quotesHistoryheading = accMgmtQuotesHistoryModelLabelBean.getQuotesHistoryheading();
        this.addressChangeInfo = accMgmtQuotesHistoryModelLabelBean.getAddressChangeInfo();
        this.quoteInputLabel=accMgmtQuotesHistoryModelLabelBean.getQuoteInputLabel();
        this.validQuoteTabLabel= accMgmtQuotesHistoryModelLabelBean.getValidQuoteTabLabel();
        this.expiredQuoteTabLabel= accMgmtQuotesHistoryModelLabelBean.getExpiredQuoteTabLabel();
        this.allQuoteTabLabel= accMgmtQuotesHistoryModelLabelBean.getAllQuoteTabLabel();
        this.serverErrorMsg= accMgmtQuotesHistoryModelLabelBean.getServerErrorMsg();
        this.quoteNumber=accMgmtQuotesHistoryModelLabelBean.getQuoteNumber();
        this.validFrom=accMgmtQuotesHistoryModelLabelBean.getValidFrom();
        this.validThrough=accMgmtQuotesHistoryModelLabelBean.getValidThrough();
        this.total=accMgmtQuotesHistoryModelLabelBean.getTotal();
        this.viewDetailsBtn=accMgmtQuotesHistoryModelLabelBean.getViewDetailsBtn();
        this.noQuoteFound=accMgmtQuotesHistoryModelLabelBean.getNoQuoteFound();
        this.noQuoteInfo=accMgmtQuotesHistoryModelLabelBean.getNoQuoteInfo();
        this.clearSearch=accMgmtQuotesHistoryModelLabelBean.getClearSearch();
        this.noQuoteForDefaultShipTo = accMgmtQuotesHistoryModelLabelBean.getNoQuoteForDefaultShipTo();
        this.noQuoteForDefaultShipToInfo = accMgmtQuotesHistoryModelLabelBean.getNoQuoteForDefaultShipToInfo();
        this.detailsQuoteNumberHeading=accMgmtQuotesHistoryModelLabelBean.getDetailsQuoteNumberHeading();
        this.detailsQuotesDetails=accMgmtQuotesHistoryModelLabelBean.getDetailsQuotesDetails();
        this.detailsQuoteIssueText=accMgmtQuotesHistoryModelLabelBean.getDetailsQuoteIssueText();
        this.detailsContactUsCTA=accMgmtQuotesHistoryModelLabelBean.getDetailsContactUsCTA();
        this.detailsContactUsLink=accMgmtQuotesHistoryModelLabelBean.getDetailsContactUsLink();
        this.detailsCancelCTA=accMgmtQuotesHistoryModelLabelBean.getDetailsCancelCTA();
        this.detailsCancelLink=accMgmtQuotesHistoryModelLabelBean.getDetailsCancelLink();
        this.detailsQuoteDetailsHeading	=	accMgmtQuotesHistoryModelLabelBean.getDetailsQuoteDetailsHeading();
        this.printQuoteBtn	=	accMgmtQuotesHistoryModelLabelBean.getPrintQuoteBtn();
        this.pricingInfoLabel	=	accMgmtQuotesHistoryModelLabelBean.getPricingInfoLabel();
        this.detailsQuoteNumber	=	accMgmtQuotesHistoryModelLabelBean.getDetailsQuoteNumber();
        this.detailsValidFrom	=	accMgmtQuotesHistoryModelLabelBean.getDetailsValidFrom();
        this.detailsValidThrough	=	accMgmtQuotesHistoryModelLabelBean.getDetailsValidThrough();
        this.detailsOrderedBy	=	accMgmtQuotesHistoryModelLabelBean.getDetailsOrderedBy();
        this.itemsTitle	=	accMgmtQuotesHistoryModelLabelBean.getItemsTitle();
        this.detailsShipmentNumberLabel	=	accMgmtQuotesHistoryModelLabelBean.getDetailsShipmentNumberLabel();
        this.pricePerUnit	=	accMgmtQuotesHistoryModelLabelBean.getPricePerUnit();
        this.detailsYourPriceLabel	=	accMgmtQuotesHistoryModelLabelBean.getDetailsYourPriceLabel();
        this.detailsListPriceLabel	=	accMgmtQuotesHistoryModelLabelBean.getDetailsListPriceLabel();
        this.totalPriceLabel	=	accMgmtQuotesHistoryModelLabelBean.getTotalPriceLabel();
        this.detailsQuantityLabel	=	accMgmtQuotesHistoryModelLabelBean.getDetailsQuantityLabel();
        this.addressDetailsHeading	=	accMgmtQuotesHistoryModelLabelBean.getAddressDetailsHeading();
        this.shipToLabel	=	accMgmtQuotesHistoryModelLabelBean.getShipToLabel();
        this.billToLabel	=	accMgmtQuotesHistoryModelLabelBean.getBillToLabel();
        this.soldToLabel	=	accMgmtQuotesHistoryModelLabelBean.getSoldToLabel();
        this.shiptoNumberLabel	=	accMgmtQuotesHistoryModelLabelBean.getShiptoNumberLabel();
        this.billtoNumberLabel	=	accMgmtQuotesHistoryModelLabelBean.getBilltoNumberLabel();
        this.soldtoNumberLabel	=	accMgmtQuotesHistoryModelLabelBean.getSoldtoNumberLabel();
        this.productsHeading	=	accMgmtQuotesHistoryModelLabelBean.getProductsHeading();
        this.estDeliveryDate	=	accMgmtQuotesHistoryModelLabelBean.getEstDeliveryDate();
        this.quantityLabel	=	accMgmtQuotesHistoryModelLabelBean.getQuantityLabel();
        this.defaultPRDImage	=	accMgmtQuotesHistoryModelLabelBean.getDefaultPRDImage();
        this.defaultPRDImageAltText	=	accMgmtQuotesHistoryModelLabelBean.getDefaultPRDImageAltText();
        this.specialInstructionsHeading	=	accMgmtQuotesHistoryModelLabelBean.getSpecialInstructionsHeading();
        this.nonPurchasableMessage	=	accMgmtQuotesHistoryModelLabelBean.getNonPurchasableMessage();
        this.nonPurchasableAltText	=	accMgmtQuotesHistoryModelLabelBean.getNonPurchasableAltText();
        this.grantAmountUsed	=	accMgmtQuotesHistoryModelLabelBean.getGrantAmountUsed();
        this.amountSummaryHeading	=	accMgmtQuotesHistoryModelLabelBean.getAmountSummaryHeading();
        this.iconSrc	=	accMgmtQuotesHistoryModelLabelBean.getIconSrc();
        this.iconAltText	=	accMgmtQuotesHistoryModelLabelBean.getIconAltText();
        this.infoMessage	=	accMgmtQuotesHistoryModelLabelBean.getInfoMessage();
        this.amountSubtotal	=	accMgmtQuotesHistoryModelLabelBean.getAmountSubtotal();
        this.shippingAndHandling	=	accMgmtQuotesHistoryModelLabelBean.getShippingAndHandling();
        this.amountTaxes	=	accMgmtQuotesHistoryModelLabelBean.getAmountTaxes();
        this.amountSurcharge	=	accMgmtQuotesHistoryModelLabelBean.getAmountSurcharge();
        this.amountPromotion	=	accMgmtQuotesHistoryModelLabelBean.getAmountPromotion();
        this.amountTotalPromoSavings	=	accMgmtQuotesHistoryModelLabelBean.getAmountTotalPromoSavings();
        this.amountTotal	=	accMgmtQuotesHistoryModelLabelBean.getAmountTotal();
        this.amountSurchargesToolTip	=	accMgmtQuotesHistoryModelLabelBean.getAmountSurchargesToolTip();
        this.amountTaxToolTip	=	accMgmtQuotesHistoryModelLabelBean.getAmountTaxToolTip();
        this.amountToolTipAltText	=	accMgmtQuotesHistoryModelLabelBean.getAmountToolTipAltText();
        this.quoteListCardLabels	=	accMgmtQuotesHistoryModelLabelBean.getQuoteListCardLabels();
        this.filtersLabels	=	accMgmtQuotesHistoryModelLabelBean.getFiltersLabels();
        this.quoteDetailsLabels	=	accMgmtQuotesHistoryModelLabelBean.getQuoteDetailsLabels();
    }

    public String getQuotesHistoryheading() {
        return quotesHistoryheading;
    }    

    /**
	 * @return the addressChangeInfo
	 */
	public String getAddressChangeInfo() {
		return addressChangeInfo;
	}

	/**
	 * @return the validQuoteTabLabel
	 */
	public String getValidQuoteTabLabel() {
		return validQuoteTabLabel;
	}

	/**
	 * @return the expiredQuoteTabLabel
	 */
	public String getExpiredQuoteTabLabel() {
		return expiredQuoteTabLabel;
	}

	/**
	 * @return the allQuoteTabLabel
	 */
	public String getAllQuoteTabLabel() {
		return allQuoteTabLabel;
	}

	public String getServerErrorMsg() {
		return serverErrorMsg;
	}

	public String getQuoteNumber() {
        return quoteNumber;
    }

    public String getValidFrom() {
        return validFrom;
    }

    /**
	 * @return the validThrough
	 */
	public String getValidThrough() {
		return validThrough;
	}

    public String getTotal() {
        return total;
    }

    public String getViewDetailsBtn() {
        return viewDetailsBtn;
    }

    public String getQuoteInputLabel() {
        return quoteInputLabel;
    }

    public String getNoQuoteFound() {
        return noQuoteFound;
    }

    public String getNoQuoteInfo() {
        return noQuoteInfo;
    }

    public String getClearSearch() {
        return clearSearch;
    }
    
    /**
	 * @return the noQuoteForDefaultShipTo
	 */
	public String getNoQuoteForDefaultShipTo() {
		return noQuoteForDefaultShipTo;
	}

	/**
	 * @return the noQuoteForDefaultShipToInfo
	 */
	public String getNoQuoteForDefaultShipToInfo() {
		return noQuoteForDefaultShipToInfo;
	}

	public String getDetailsQuoteNumberHeading() {
        return detailsQuoteNumberHeading;
    }

    public String getDetailsQuotesDetails() {
        return detailsQuotesDetails;
    }

    public String getDetailsQuoteIssueText() {
        return detailsQuoteIssueText;
    }

    public String getDetailsContactUsCTA() {
        return detailsContactUsCTA;
    }

    public String getDetailsContactUsLink() {
        return detailsContactUsLink;
    }

    public String getDetailsCancelCTA() {
        return detailsCancelCTA;
    }

    public String getDetailsCancelLink() {
        return detailsCancelLink;
    }

    public String getDetailsQuoteDetailsHeading() {
        return detailsQuoteDetailsHeading;
    }

    public String getPrintQuoteBtn() {
		return printQuoteBtn;
	}

	public String getPricingInfoLabel() {
		return pricingInfoLabel;
	}

	public String getDetailsQuoteNumber() {
        return detailsQuoteNumber;
    }

    public String getDetailsValidFrom() {
        return detailsValidFrom;
    }

    public String getDetailsValidThrough() {
        return detailsValidThrough;
    }

    public String getDetailsOrderedBy() {
        return detailsOrderedBy;
    }

    /**
	 * @return the itemsTitle
	 */
	public String getItemsTitle() {
		return itemsTitle;
	}

	/**
	 * @return the detailsShipmentNumberLabel
	 */
	public String getDetailsShipmentNumberLabel() {
		return detailsShipmentNumberLabel;
	}

	/**
	 * @return the pricePerUnit
	 */
	public String getPricePerUnit() {
		return pricePerUnit;
	}

	/**
	 * @return the detailsYourPriceLabel
	 */
	public String getDetailsYourPriceLabel() {
		return detailsYourPriceLabel;
	}

	/**
	 * @return the detailsListPriceLabel
	 */
	public String getDetailsListPriceLabel() {
		return detailsListPriceLabel;
	}

	/**
	 * @return the totalPriceLabel
	 */
	public String getTotalPriceLabel() {
		return totalPriceLabel;
	}

	public String getDetailsQuantityLabel() {
		return detailsQuantityLabel;
	}

	public String getAddressDetailsHeading() {
        return addressDetailsHeading;
    }

    public String getShipToLabel() {
        return shipToLabel;
    }

    public String getBillToLabel() {
        return billToLabel;
    }

    public String getSoldToLabel() {
        return soldToLabel;
    }

    public String getShiptoNumberLabel() {
        return shiptoNumberLabel;
    }

    public String getBilltoNumberLabel() {
        return billtoNumberLabel;
    }

    public String getSoldtoNumberLabel() {
        return soldtoNumberLabel;
    }

    public String getProductsHeading() {
        return productsHeading;
    }

    public String getEstDeliveryDate() {
        return estDeliveryDate;
    }

    public String getQuantityLabel() {
        return quantityLabel;
    }

    public String getDefaultPRDImage() {
        return defaultPRDImage;
    }

    public String getDefaultPRDImageAltText() {
        return defaultPRDImageAltText;
    }

    public String getSpecialInstructionsHeading() {
        return specialInstructionsHeading;
    }
    
    /**
	 * @return the nonPurchasableMessage
	 */
	public String getNonPurchasableMessage() {
		return nonPurchasableMessage;
	}

	/**
	 * @return the nonPurchasableAltText
	 */
	public String getNonPurchasableAltText() {
		return nonPurchasableAltText;
	}

	public String getGrantAmountUsed() {
        return grantAmountUsed;
    }

    public String getAmountSummaryHeading() {
        return amountSummaryHeading;
    }

    public String getIconSrc() {
        return iconSrc;
    }

    public String getIconAltText() {
        return iconAltText;
    }

    public String getInfoMessage() {
        return infoMessage;
    }

    public String getAmountSubtotal() {
        return amountSubtotal;
    }

    public String getShippingAndHandling() {
        return shippingAndHandling;
    }

    public String getAmountTaxes() {
        return amountTaxes;
    }

    public String getAmountSurcharge() {
        return amountSurcharge;
    }

    public String getAmountPromotion() {
        return amountPromotion;
    }

    public String getAmountTotalPromoSavings() {
        return amountTotalPromoSavings;
    }

    public String getAmountTotal() {
        return amountTotal;
    }

    public String getAmountSurchargesToolTip() {
        return amountSurchargesToolTip;
    }

    public String getAmountTaxToolTip() {
        return amountTaxToolTip;
    }

    public String getAmountToolTipAltText() {
        return amountToolTipAltText;
    }
    
    public JsonElement getQuoteListCardLabels() {
		return quoteListCardLabels;
	}

	/**
	 * @return the filtersLabels
	 */
	public JsonElement getFiltersLabels() {
		return filtersLabels;
	}

	/**
	 * @return the icon
	 */
	public JsonElement getIcon() {
		return icon;
	}

	/**
	 * @return the message
	 */
	public JsonElement getMessage() {
		return message;
	}

	/**
	 * @return the info
	 */
	public JsonElement getInfo() {
		return info;
	}

	/**
	 * @return the amountSummary
	 */
	public JsonElement getAmountSummary() {
		return amountSummary;
	}

	/**
	 * @return the addressDetailsLabel
	 */
	public JsonElement getAddressDetailsLabel() {
		return addressDetailsLabel;
	}

	/**
	 * @return the specialInstructions
	 */
	public JsonElement getSpecialInstructions() {
		return specialInstructions;
	}

	public JsonElement getNonPurchasable() {
		return nonPurchasable;
	}

	/**
	 * @return the products
	 */
	public JsonElement getProducts() {
		return products;
	}

	/**
	 * @return the quoteDetailsLabels
	 */
	public JsonElement getQuoteDetailsLabels() {
		return quoteDetailsLabels;
	}

}
