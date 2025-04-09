package com.bdb.aem.core.pojo;

import com.bdb.aem.core.bean.PurchaseAccountModelBean;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The Class PurchaseAccountModelLabels.
 */
public class PurchaseAccountModelLabels {
	
	/** The title. */
	@SerializedName("title")
	private String title;
	
	/** The existing account. */
	@SerializedName("existingAccount")
	private String existingAccount;
	
	/** The yes. */
	@SerializedName("Yes")
	private String yes;
	
	/** The no with not sure. */
	@SerializedName("NowithNotSure")
	private String noWithNotSure;
	
	/** The no. */
	@SerializedName("No")
	private String no;
	
	/** The internal Approval. */
	@SerializedName("internalApproval")
	private String internalApproval;
	
	/** The internal approval label info. */
	@SerializedName("internalApprovalLabelInfo")
	private String internalApprovalLabelInfo;
	
	/** The existing distributor. */
	@SerializedName("existingDistributor")
	private String existingDistributor;
	
	/** The agency select distributor. */
	@SerializedName("agencyselectLabel")
	private String agencyselectLabel;
	
	/** The payment title. */
	@SerializedName("paymentTitle")
	private String paymentTitle;
	
	/** The purchase order. */
	@SerializedName("paymentOptions")
	private JsonElement paymentOptions;
	
	/** The credit app file path. */
	@SerializedName("downloadCreditNoteUrl")
	private String creditAppFilePath;
	
	/** The downlowd credit app CTA label. */
	@SerializedName("downloadCreditAppCTALabel")
	private String downloadCreditAppCTALabel;
	
	/** The download credit app format and size. */
	@SerializedName("downloadCreditAppFormatAndSize")
	private String downloadCreditAppFormatAndSize;
	
	/** The tax exempt label. */
	@SerializedName("taxExemptLabel")
	private String taxExemptLabel;
	
	/** The file upload labels. */
	@SerializedName("fileUploadLabels")
	private JsonElement fileUploadLabels;
	
	/** The vat exempt label. */
	@SerializedName("vatExemptLabel")
	private String vatExemptLabel;
	
	/** The EAN code label. */
	@SerializedName("EANCodeLabel")
	private String eanCodeLabel;
	
	/** The VAT number label. */
	@SerializedName("VATNumberLabel")
	private String vatNumberLabel;
	
	/** The typeOfBusiness label. */
	@SerializedName("typeOfBusinessLabel")
	private String typeOfBusinessLabel;
	
	/** The Public label. */
	@SerializedName("Public")
	private String publicLabel;
	
	/** The Private label. */
	@SerializedName("Private")
	private String privateLabel;
	
	/** The accountOffice label. */
	@SerializedName("accountOfficeLabel")
	private String accountOfficeLabel;
	
	/** The processingUnit label. */
	@SerializedName("processingUnitLabel")
	private String processingUnitLabel;
	
	/** The transactingUnit label. */
	@SerializedName("transactingUnitLabel")
	private String transactingUnitLabel;
	
	/** The duty exempt label. */
	@SerializedName("dutyExemptLabel")
	private String dutyExemptLabel;
	
	/** The gst pan title. */
	@SerializedName("gstPanTitle")
	private String gstPanTitle;
	
	/** The gst number label. */
	@SerializedName("gstNumberLabel")
	private String gstNumberLabel;
	
	/** The pan number label. */
	@SerializedName("panNumberLabel")
	private String panNumberLabel;
	
	/** The shipTo account number label. */
	@SerializedName("shiptoAccountNumberLabel")
	private String shipToAccountNumberLabel;
	
	/** The shipTo account number label info. */
	@SerializedName("shiptoAccountNumberLabelInfo")
	private String shipToAccountNumberLabelInfo;
	
	/** The shipTo account number place holder. */
	@SerializedName("shiptoAccountNumberPlaceHolder")
	private String shipToAccountNumberPlaceHolder;
	
	/** The sold to account number label. */
	@SerializedName("soldToAccountNumberLabel")
	private String soldToAccountNumberLabel;
	
	/** The sold to account number label info. */
	@SerializedName("soldToAccountNumberLabelInfo")
	private String soldToAccountNumberLabelInfo;
	
	/** The sold to account number place holder. */
	@SerializedName("soldToAccountNumberPlaceHolder")
	private String soldToAccountNumberPlaceHolder;
	
	/** The account number validation error. */
	@SerializedName("accountNumberValidationError")
	private String accountNumberValidationError;
	
	/** The address section title. */
	@SerializedName("addressSectionTitle")
	private String addressSectionTitle;
	
	/** The shipping address title. */
	@SerializedName("shippingAddressTitle")
	private String shippingAddressTitle;
	
	/** The shipping address sub title. */
	@SerializedName("shippingAddressSubTitle")
	private String shippingAddressSubTitle;
	
	/** The sold to address title. */
	@SerializedName("soldToAddressTitle")
	private String soldToAddressTitle;
	
	/** The billing address title. */
	@SerializedName("billingAddressTitle")
	private String billingAddressTitle;
	
	/** The billing address sub title. */
	@SerializedName("billingAddressSubTitle")
	private String billingAddressSubTitle;
	
	/** The payer address title. */
	@SerializedName("payerAddressTitle")
	private String payerAddressTitle;
	
	/** The payer address sub title. */
	@SerializedName("payerAddressSubTitle")
	private String payerAddressSubTitle;
	
	/** The same as shipto address label. */
	@SerializedName("sameAsShiptoAddressLabel")
	private String sameAsShiptoAddressLabel;
	
	/** The editlabel. */
	@SerializedName("editlabel")
	private String editLabel;
	
	/** The address labels. */
	@SerializedName("addressLabels")
	private JsonElement addressLabels;
	
	/** The save and continue. */
	@SerializedName("saveAndContinue")
	private String saveAndContinue;
	
	/** The back CTA. */
	@SerializedName("backCTA")
	private String backCTA;
	
	/** The submit CTA. */
	@SerializedName("submitCTA")
	private String submitCTA;
	
	/** The submit CTA. */
	@SerializedName("distributorLabel")
	private String distributorLabel;
	
	/**
	 * Instantiates a new purchase account model labels.
	 *
	 * @param purchaseAccountModelBean the purchase account model bean
	 */
	public PurchaseAccountModelLabels(PurchaseAccountModelBean purchaseAccountModelBean) {
		this.title = purchaseAccountModelBean.getTitle();
		this.existingAccount = purchaseAccountModelBean.getExistingAccount();
		this.yes = purchaseAccountModelBean.getYes();
		this.noWithNotSure = purchaseAccountModelBean.getNoWithNotSure();
		this.no = purchaseAccountModelBean.getNo();
		this.internalApproval = purchaseAccountModelBean.getInternalApproval();
		this.internalApprovalLabelInfo = purchaseAccountModelBean.getInternalApprovalLabelInfo();
		this.existingDistributor = purchaseAccountModelBean.getExistingDistributor();
		this.paymentTitle = purchaseAccountModelBean.getPaymentTitle();
		this.paymentOptions = purchaseAccountModelBean.getPaymentOptions();
		this.creditAppFilePath = purchaseAccountModelBean.getCreditAppFilePath();
		this.downloadCreditAppCTALabel = purchaseAccountModelBean.getDownloadCreditAppCTALabel();
		this.downloadCreditAppFormatAndSize = purchaseAccountModelBean.getDownloadCreditAppFormatAndSize();
		this.taxExemptLabel = purchaseAccountModelBean.getTaxExemptLabel();
		this.fileUploadLabels = purchaseAccountModelBean.getFileUploadLabels();
		this.vatExemptLabel = purchaseAccountModelBean.getVatExemptLabel();
		this.eanCodeLabel = purchaseAccountModelBean.getEanCodeLabel();
		this.vatNumberLabel = purchaseAccountModelBean.getVatNumberLabel();
		this.typeOfBusinessLabel = purchaseAccountModelBean.getTypeOfBusinessLabel();
		this.publicLabel = purchaseAccountModelBean.getPublicLabel();
		this.privateLabel = purchaseAccountModelBean.getPrivateLabel();
		this.accountOfficeLabel = purchaseAccountModelBean.getAccountOfficeLabel();
		this.processingUnitLabel = purchaseAccountModelBean.getProcessingUnitLabel();
		this.transactingUnitLabel = purchaseAccountModelBean.getTransactingUnitLabel();
		this.dutyExemptLabel = purchaseAccountModelBean.getDutyExemptLabel();
		this.gstPanTitle = purchaseAccountModelBean.getGstPanTitle();
		this.gstNumberLabel = purchaseAccountModelBean.getGstNumberLabel();
		this.panNumberLabel = purchaseAccountModelBean.getPanNumberLabel();
		this.shipToAccountNumberLabel = purchaseAccountModelBean.getShipToAccountNumberLabel();
		this.shipToAccountNumberLabelInfo = purchaseAccountModelBean.getShipToAccountNumberLabelInfo();
		this.shipToAccountNumberPlaceHolder = purchaseAccountModelBean.getShipToAccountNumberPlaceHolder();
		this.soldToAccountNumberLabel = purchaseAccountModelBean.getSoldToAccountNumberLabel();
		this.soldToAccountNumberLabelInfo = purchaseAccountModelBean.getSoldToAccountNumberLabelInfo();
		this.soldToAccountNumberPlaceHolder = purchaseAccountModelBean.getSoldToAccountNumberPlaceHolder();
		this.accountNumberValidationError = purchaseAccountModelBean.getAccountNumberValidationError();
		this.addressSectionTitle = purchaseAccountModelBean.getAddressSectionTitle();
		this.shippingAddressTitle = purchaseAccountModelBean.getShippingAddressTitle();
		this.shippingAddressSubTitle = purchaseAccountModelBean.getShippingAddressSubTitle();
		this.soldToAddressTitle = purchaseAccountModelBean.getSoldToAddressTitle();
		this.billingAddressTitle = purchaseAccountModelBean.getBillingAddressTitle();
		this.billingAddressSubTitle = purchaseAccountModelBean.getBillingAddressSubTitle();
		this.payerAddressTitle = purchaseAccountModelBean.getPayerAddressTitle();
		this.payerAddressSubTitle = purchaseAccountModelBean.getPayerAddressSubTitle();
		this.sameAsShiptoAddressLabel = purchaseAccountModelBean.getSameAsShiptoAddressLabel();
		this.editLabel = purchaseAccountModelBean.getEditLabel();
		this.addressLabels = purchaseAccountModelBean.getAddressLabels();
		this.saveAndContinue = purchaseAccountModelBean.getSaveAndContinue();
		this.backCTA = purchaseAccountModelBean.getBackCTA();
		this.submitCTA = purchaseAccountModelBean.getSubmitCTA();
		this.distributorLabel = purchaseAccountModelBean.getDistributorLabel();
		this.agencyselectLabel= purchaseAccountModelBean.getAgencyselectLabel();
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the existing account.
	 *
	 * @return the existing account
	 */
	public String getExistingAccount() {
		return existingAccount;
	}
	/**
	 * Gets the agencyselect text.
	 *
	 * @return the agencyselect text
	 */
	public String getAgencyselectLabel() {
		return agencyselectLabel;
	}

	/**
	 * Gets the yes.
	 *
	 * @return the yes
	 */
	public String getYes() {
		return yes;
	}

	/**
	 * Gets the no with not sure.
	 *
	 * @return the no with not sure
	 */
	public String getNoWithNotSure() {
		return noWithNotSure;
	}

	/**
	 * Gets the no.
	 *
	 * @return the no
	 */
	public String getNo() {
		return no;
	}
	
	/**
	 * @return the internalApproval
	 */
	public String getInternalApproval() {
		return internalApproval;
	}

	/**
	 * @return the internalApprovalLabelInfo
	 */
	public String getInternalApprovalLabelInfo() {
		return internalApprovalLabelInfo;
	}

	/**
	 * Gets the existing distributor.
	 *
	 * @return the existing distributor
	 */
	public String getExistingDistributor() {
		return existingDistributor;
	}

	/**
	 * Gets the payment title.
	 *
	 * @return the payment title
	 */
	public String getPaymentTitle() {
		return paymentTitle;
	}

	/**
	 * Gets the payment options.
	 *
	 * @return the payment options
	 */
	public JsonElement getPaymentOptions() {
		return paymentOptions;
	}

	/**
	 * Gets the credit app file path.
	 *
	 * @return the credit app file path
	 */
	public String getCreditAppFilePath() {
		return creditAppFilePath;
	}

	/**
	 * Gets the download credit app CTA label.
	 *
	 * @return the download credit app CTA label
	 */
	public String getDownloadCreditAppCTALabel() {
		return downloadCreditAppCTALabel;
	}

	/**
	 * Gets the download credit app format and size.
	 *
	 * @return the download credit app format and size
	 */
	public String getDownloadCreditAppFormatAndSize() {
		return downloadCreditAppFormatAndSize;
	}

	/**
	 * Gets the tax exempt label.
	 *
	 * @return the tax exempt label
	 */
	public String getTaxExemptLabel() {
		return taxExemptLabel;
	}

	/**
	 * Gets the file upload labels.
	 *
	 * @return the file upload labels
	 */
	public JsonElement getFileUploadLabels() {
		return fileUploadLabels;
	}

	/**
	 * Gets the vat exempt label.
	 *
	 * @return the vat exempt label
	 */
	public String getVatExemptLabel() {
		return vatExemptLabel;
	}

	/**
	 * Gets the ean code label.
	 *
	 * @return the ean code label
	 */
	public String getEanCodeLabel() {
		return eanCodeLabel;
	}

	/**
	 * Gets the vat number label.
	 *
	 * @return the vat number label
	 */
	public String getVatNumberLabel() {
		return vatNumberLabel;
	}

	/**
	 * @return the typeOfBusinessLabel
	 */
	public String getTypeOfBusinessLabel() {
		return typeOfBusinessLabel;
	}

	/**
	 * @return the publicLabel
	 */
	public String getPublicLabel() {
		return publicLabel;
	}

	/**
	 * @return the privateLabel
	 */
	public String getPrivateLabel() {
		return privateLabel;
	}

	/**
	 * @return the accountOfficeLabel
	 */
	public String getAccountOfficeLabel() {
		return accountOfficeLabel;
	}

	/**
	 * @return the processingUnitLabel
	 */
	public String getProcessingUnitLabel() {
		return processingUnitLabel;
	}

	/**
	 * @return the transactingUnitLabel
	 */
	public String getTransactingUnitLabel() {
		return transactingUnitLabel;
	}

	/**
	 * Gets the duty exempt label.
	 *
	 * @return the duty exempt label
	 */
	public String getDutyExemptLabel() {
		return dutyExemptLabel;
	}

	/**
	 * Gets the gst pan title.
	 *
	 * @return the gst pan title
	 */
	public String getGstPanTitle() {
		return gstPanTitle;
	}

	/**
	 * Gets the gst number label.
	 *
	 * @return the gst number label
	 */
	public String getGstNumberLabel() {
		return gstNumberLabel;
	}

	/**
	 * Gets the pan number label.
	 *
	 * @return the pan number label
	 */
	public String getPanNumberLabel() {
		return panNumberLabel;
	}

	/**
	 * Gets the ship to account number label.
	 *
	 * @return the ship to account number label
	 */
	public String getShipToAccountNumberLabel() {
		return shipToAccountNumberLabel;
	}

	/**
	 * Gets the ship to account number label info.
	 *
	 * @return the ship to account number label info
	 */
	public String getShipToAccountNumberLabelInfo() {
		return shipToAccountNumberLabelInfo;
	}

	/**
	 * Gets the ship to account number place holder.
	 *
	 * @return the ship to account number place holder
	 */
	public String getShipToAccountNumberPlaceHolder() {
		return shipToAccountNumberPlaceHolder;
	}

	/**
	 * Gets the sold to account number label.
	 *
	 * @return the sold to account number label
	 */
	public String getSoldToAccountNumberLabel() {
		return soldToAccountNumberLabel;
	}

	/**
	 * Gets the sold to account number label info.
	 *
	 * @return the sold to account number label info
	 */
	public String getSoldToAccountNumberLabelInfo() {
		return soldToAccountNumberLabelInfo;
	}

	/**
	 * Gets the sold to account number place holder.
	 *
	 * @return the sold to account number place holder
	 */
	public String getSoldToAccountNumberPlaceHolder() {
		return soldToAccountNumberPlaceHolder;
	}

	/**
	 * Gets the account number validation error.
	 *
	 * @return the account number validation error
	 */
	public String getAccountNumberValidationError() {
		return accountNumberValidationError;
	}
	/**
	 * Gets the address section title.
	 *
	 * @return the address section title
	 */
	public String getAddressSectionTitle() {
		return addressSectionTitle;
	}

	/**
	 * Gets the shipping address title.
	 *
	 * @return the shipping address title
	 */
	public String getShippingAddressTitle() {
		return shippingAddressTitle;
	}

	/**
	 * Gets the shipping address sub title.
	 *
	 * @return the shipping address sub title
	 */
	public String getShippingAddressSubTitle() {
		return shippingAddressSubTitle;
	}

	/**
	 * Gets the sold to address title.
	 *
	 * @return the sold to address title
	 */
	public String getSoldToAddressTitle() {
		return soldToAddressTitle;
	}

	/**
	 * Gets the billing address title.
	 *
	 * @return the billing address title
	 */
	public String getBillingAddressTitle() {
		return billingAddressTitle;
	}

	/**
	 * Gets the billing address sub title.
	 *
	 * @return the billing address sub title
	 */
	public String getBillingAddressSubTitle() {
		return billingAddressSubTitle;
	}

	/**
	 * Gets the payer address title.
	 *
	 * @return the payer address title
	 */
	public String getPayerAddressTitle() {
		return payerAddressTitle;
	}

	/**
	 * Gets the payer address sub title.
	 *
	 * @return the payer address sub title
	 */
	public String getPayerAddressSubTitle() {
		return payerAddressSubTitle;
	}

	/**
	 * Gets the same as shipto address label.
	 *
	 * @return the same as shipto address label
	 */
	public String getSameAsShiptoAddressLabel() {
		return sameAsShiptoAddressLabel;
	}

	/**
	 * Gets the edits the label.
	 *
	 * @return the edits the label
	 */
	public String getEditLabel() {
		return editLabel;
	}

	/**
	 * Gets the save and continue.
	 *
	 * @return the save and continue
	 */
	public String getSaveAndContinue() {
		return saveAndContinue;
	}

	/**
	 * Gets the back CTA.
	 *
	 * @return the back CTA
	 */
	public String getBackCTA() {
		return backCTA;
	}

	/**
	 * Gets the submit CTA.
	 *
	 * @return the submit CTA
	 */
	public String getSubmitCTA() {
		return submitCTA;
	}

	/**
	 * Gets the address labels.
	 *
	 * @return the address labels
	 */
	public JsonElement getAddressLabels() {
		return addressLabels;
	}

	/**
	 * Gets the distributor label.
	 *
	 * @return the distributor label
	 */
	public String getDistributorLabel() {
		return distributorLabel;
	}	
}
