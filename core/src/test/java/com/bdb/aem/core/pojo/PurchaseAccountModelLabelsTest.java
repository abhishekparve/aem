package com.bdb.aem.core.pojo;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bdb.aem.core.bean.PurchaseAccountModelBean;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import junitx.framework.Assert;

/**
 * The Class PurchaseAccountModelLabelsTest.
 */
class PurchaseAccountModelLabelsTest {
	
	/** The test string. */
	private String testString = "value";
	
	/** The test json obj. */
	private JsonElement testJsonObj = new JsonObject();
	
	/** The purchase account model test labels. */
	PurchaseAccountModelLabels purchaseAccountModelTestLabels;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
PurchaseAccountModelBean purchaseAccountModelBean = new PurchaseAccountModelBean();
		
		purchaseAccountModelBean.setTitle("title");
		purchaseAccountModelBean.setExistingAccount("existingAccount");
		purchaseAccountModelBean.setYes("Yes");
		purchaseAccountModelBean.setNoWithNotSure(testString);
		purchaseAccountModelBean.setNo(testString);
		purchaseAccountModelBean.setExistingDistributor("existingDistributor");
		purchaseAccountModelBean.setPaymentTitle("paymentTitle");
		purchaseAccountModelBean.setPaymentOptions(testJsonObj);
		purchaseAccountModelBean.setCreditAppFilePath("downloadCreditNoteUrl");
		purchaseAccountModelBean.setDownloadCreditAppCTALabel("downloadCreditAppCTALabel");
		purchaseAccountModelBean.setDownloadCreditAppFormatAndSize("downloadCreditAppFormatAndSize");
		purchaseAccountModelBean.setTaxExemptLabel("taxExemptLabel");
		purchaseAccountModelBean.setFileUploadLabels(testJsonObj);
		purchaseAccountModelBean.setVatExemptLabel("vatExemptLabel");
		purchaseAccountModelBean.setEanCodeLabel("eaNCodeLabel");
		purchaseAccountModelBean.setVatNumberLabel("vaTNumberLabel");
		purchaseAccountModelBean.setTypeOfBusinessLabel("typeOfBusinessLabel");
		purchaseAccountModelBean.setPublicLabel("publicLabel");
		purchaseAccountModelBean.setPrivateLabel("privateLabel");
		purchaseAccountModelBean.setAccountOfficeLabel("accountOfficeLabel");
		purchaseAccountModelBean.setProcessingUnitLabel("processingUnitLabel");
		purchaseAccountModelBean.setTransactingUnitLabel("transactingUnitLabel");
		purchaseAccountModelBean.setDutyExemptLabel("dutyExemptLabel");
		purchaseAccountModelBean.setGstPanTitle("gstPanTitle");
		purchaseAccountModelBean.setGstNumberLabel("gstNumberLabel");
		purchaseAccountModelBean.setPanNumberLabel("panNumberLabel");
		purchaseAccountModelBean.setShipToAccountNumberLabel("shipToAccountNumberLabel");
		purchaseAccountModelBean.setShipToAccountNumberLabelInfo("shiptoAccountNumberLabelInfo");
		purchaseAccountModelBean.setShipToAccountNumberPlaceHolder("shipToAccountNumberPlaceHolder");
		purchaseAccountModelBean.setSoldToAccountNumberLabel("soldToAccountNumberLabel");
		purchaseAccountModelBean.setSoldToAccountNumberLabelInfo("soldToAccountNumberLabelInfo");
		purchaseAccountModelBean.setSoldToAccountNumberPlaceHolder("soldToAccountNumberPlaceHolder");
		purchaseAccountModelBean.setAccountNumberValidationError("accountNumberValidationError");
		purchaseAccountModelBean.setAddressSectionTitle("addressSectionTitle");
		purchaseAccountModelBean.setShippingAddressTitle("shippingAddressTitle");
		purchaseAccountModelBean.setShippingAddressSubTitle("shippingAddressSubTitle");
		purchaseAccountModelBean.setSoldToAddressTitle("soldToAddressTitle");
		purchaseAccountModelBean.setBillingAddressTitle("billingAddressTitle");
		purchaseAccountModelBean.setBillingAddressSubTitle("billingAddressSubTitle");
		purchaseAccountModelBean.setPayerAddressTitle("payerAddressTitle");
		purchaseAccountModelBean.setPayerAddressSubTitle("payerAddressSubTitle");
		purchaseAccountModelBean.setSameAsShiptoAddressLabel("sameAsShiptoAddressLabel");
		purchaseAccountModelBean.setEditLabel("editlabel");
		purchaseAccountModelBean.setAddressLabels(testJsonObj);
		purchaseAccountModelBean.setSaveAndContinue("saveAndContinueLabel");
		purchaseAccountModelBean.setBackCTA("backCTA");
		purchaseAccountModelBean.setSubmitCTA("submitCTALabel");
		purchaseAccountModelBean.setDistributorLabel("submitCTALabel");
		
		purchaseAccountModelTestLabels = new PurchaseAccountModelLabels(purchaseAccountModelBean);
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertEquals(purchaseAccountModelTestLabels.getTitle(), "title");
		Assert.assertEquals(purchaseAccountModelTestLabels.getExistingAccount(),"existingAccount");
		Assert.assertEquals(purchaseAccountModelTestLabels.getYes(), "Yes");
		Assert.assertNotNull(purchaseAccountModelTestLabels.getAddressSectionTitle());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getBillingAddressSubTitle());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getBillingAddressTitle());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getCreditAppFilePath());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getDownloadCreditAppCTALabel());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getDownloadCreditAppFormatAndSize());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getDutyExemptLabel());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getEanCodeLabel());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getEditLabel());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getExistingAccount());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getExistingDistributor());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getGstNumberLabel());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getGstPanTitle());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getNo());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getNoWithNotSure());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getPanNumberLabel());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getPayerAddressSubTitle());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getPayerAddressTitle());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getPaymentTitle());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getSameAsShiptoAddressLabel());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getSaveAndContinue());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getShippingAddressSubTitle());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getShippingAddressTitle());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getShipToAccountNumberLabel());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getShipToAccountNumberLabelInfo());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getShipToAccountNumberPlaceHolder());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getSoldToAccountNumberLabel());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getSoldToAccountNumberLabelInfo());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getSoldToAccountNumberPlaceHolder());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getAccountNumberValidationError());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getSoldToAddressTitle());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getSubmitCTA());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getTaxExemptLabel());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getVatExemptLabel());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getVatNumberLabel());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getTypeOfBusinessLabel());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getPublicLabel());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getPrivateLabel());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getAccountOfficeLabel());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getProcessingUnitLabel());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getTransactingUnitLabel());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getAddressLabels());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getFileUploadLabels());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getPaymentOptions());
		Assert.assertNotNull(purchaseAccountModelTestLabels.getDistributorLabel());
		assertEquals("backCTA",purchaseAccountModelTestLabels.getBackCTA());
	}
}
