package com.bdb.aem.core.bean;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

import junitx.framework.Assert;

/**
 * The Class PurchaseAccountModelBeanTest.
 */
class PurchaseAccountModelBeanTest {
	
	/** The purchase account model test bean. */
	PurchaseAccountModelBean purchaseAccountModelTestBean;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		purchaseAccountModelTestBean = new PurchaseAccountModelBean();
		purchaseAccountModelTestBean.setAccountNumberValidationError("AccountNumberValidationError");
		purchaseAccountModelTestBean.setAddressLabels(new JsonObject());
		purchaseAccountModelTestBean.setAddressSectionTitle("AddressSectionTitle");
		purchaseAccountModelTestBean.setBillingAddressSubTitle("BillingAddressSubTitle");
		purchaseAccountModelTestBean.setBillingAddressTitle("String");
		purchaseAccountModelTestBean.setCreditAppFilePath("String");
		purchaseAccountModelTestBean.setDownloadCreditAppCTALabel("String");
		purchaseAccountModelTestBean.setDownloadCreditAppFormatAndSize("String");
		purchaseAccountModelTestBean.setDutyExemptLabel("String");
		purchaseAccountModelTestBean.setEanCodeLabel("String");
		purchaseAccountModelTestBean.setEditLabel("String");
		purchaseAccountModelTestBean.setExistingAccount("String");
		purchaseAccountModelTestBean.setExistingDistributor("String");
		purchaseAccountModelTestBean.setFileUploadLabels(new JsonObject());
		purchaseAccountModelTestBean.setGstNumberLabel("String");
		purchaseAccountModelTestBean.setGstPanTitle("String");
		purchaseAccountModelTestBean.setNo("String");
		purchaseAccountModelTestBean.setNoWithNotSure("String");
		purchaseAccountModelTestBean.setPanNumberLabel("String");
		purchaseAccountModelTestBean.setPayerAddressSubTitle("String");
		purchaseAccountModelTestBean.setPayerAddressTitle("String");
		purchaseAccountModelTestBean.setPaymentOptions(new JsonObject());
		purchaseAccountModelTestBean.setPaymentTitle("String");
		purchaseAccountModelTestBean.setSameAsShiptoAddressLabel("String");
		purchaseAccountModelTestBean.setSaveAndContinue("String");
		purchaseAccountModelTestBean.setShippingAddressSubTitle("String");
		purchaseAccountModelTestBean.setShippingAddressTitle("String");
		purchaseAccountModelTestBean.setShipToAccountNumberLabel("String");
		purchaseAccountModelTestBean.setShipToAccountNumberLabelInfo("String");
		purchaseAccountModelTestBean.setShipToAccountNumberPlaceHolder("String");
		purchaseAccountModelTestBean.setSoldToAccountNumberLabel("String");
		purchaseAccountModelTestBean.setSoldToAccountNumberLabelInfo("String");
		purchaseAccountModelTestBean.setSoldToAccountNumberPlaceHolder("String");
		purchaseAccountModelTestBean.setSoldToAddressTitle("String");
		purchaseAccountModelTestBean.setSubmitCTA("String");
		purchaseAccountModelTestBean.setTaxExemptLabel("String");
		purchaseAccountModelTestBean.setTitle("String");
		purchaseAccountModelTestBean.setVatExemptLabel("String");
		purchaseAccountModelTestBean.setVatNumberLabel("String");
		purchaseAccountModelTestBean.setTypeOfBusinessLabel("String");
		purchaseAccountModelTestBean.setPublicLabel("String");
		purchaseAccountModelTestBean.setPrivateLabel("String");
		purchaseAccountModelTestBean.setAccountOfficeLabel("String");
		purchaseAccountModelTestBean.setProcessingUnitLabel("String");
		purchaseAccountModelTestBean.setTransactingUnitLabel("String");
		purchaseAccountModelTestBean.setDistributorLabel("String");
		purchaseAccountModelTestBean.setBackCTA("backCTA");
		purchaseAccountModelTestBean.setYes("Yes");
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertEquals(purchaseAccountModelTestBean.getAccountNumberValidationError(), "AccountNumberValidationError");
		Assert.assertEquals(purchaseAccountModelTestBean.getBillingAddressSubTitle(), "BillingAddressSubTitle");
		Assert.assertEquals(purchaseAccountModelTestBean.getAddressSectionTitle(), "AddressSectionTitle");
		Assert.assertNotNull(purchaseAccountModelTestBean.getAddressLabels());
		Assert.assertNotNull(purchaseAccountModelTestBean.getBillingAddressTitle());
		Assert.assertNotNull(purchaseAccountModelTestBean.getCreditAppFilePath());
		Assert.assertNotNull(purchaseAccountModelTestBean.getDownloadCreditAppCTALabel());
		Assert.assertNotNull(purchaseAccountModelTestBean.getDownloadCreditAppFormatAndSize());
		Assert.assertNotNull(purchaseAccountModelTestBean.getDutyExemptLabel());
		Assert.assertNotNull(purchaseAccountModelTestBean.getEanCodeLabel());
		Assert.assertNotNull(purchaseAccountModelTestBean.getEditLabel());
		Assert.assertNotNull(purchaseAccountModelTestBean.getExistingAccount());
		Assert.assertNotNull(purchaseAccountModelTestBean.getExistingDistributor());
		Assert.assertNotNull(purchaseAccountModelTestBean.getFileUploadLabels());
		Assert.assertNotNull(purchaseAccountModelTestBean.getGstNumberLabel());
		Assert.assertNotNull(purchaseAccountModelTestBean.getGstPanTitle());
		Assert.assertNotNull(purchaseAccountModelTestBean.getNo());
		Assert.assertNotNull(purchaseAccountModelTestBean.getNoWithNotSure());
		Assert.assertNotNull(purchaseAccountModelTestBean.getPanNumberLabel());
		Assert.assertNotNull(purchaseAccountModelTestBean.getPayerAddressSubTitle());
		Assert.assertNotNull(purchaseAccountModelTestBean.getPayerAddressTitle());
		Assert.assertNotNull(purchaseAccountModelTestBean.getPaymentOptions());
		Assert.assertNotNull(purchaseAccountModelTestBean.getPaymentTitle());
		Assert.assertNotNull(purchaseAccountModelTestBean.getSameAsShiptoAddressLabel());
		Assert.assertNotNull(purchaseAccountModelTestBean.getSaveAndContinue());
		Assert.assertNotNull(purchaseAccountModelTestBean.getShippingAddressSubTitle());
		Assert.assertNotNull(purchaseAccountModelTestBean.getShippingAddressTitle());
		Assert.assertNotNull(purchaseAccountModelTestBean.getShipToAccountNumberLabel());
		Assert.assertNotNull(purchaseAccountModelTestBean.getShipToAccountNumberLabelInfo());
		Assert.assertNotNull(purchaseAccountModelTestBean.getShipToAccountNumberPlaceHolder());
		Assert.assertNotNull(purchaseAccountModelTestBean.getSoldToAccountNumberLabel());
		Assert.assertNotNull(purchaseAccountModelTestBean.getSoldToAccountNumberLabelInfo());
		Assert.assertNotNull(purchaseAccountModelTestBean.getSoldToAccountNumberPlaceHolder());
		Assert.assertNotNull(purchaseAccountModelTestBean.getSoldToAddressTitle());
		Assert.assertNotNull(purchaseAccountModelTestBean.getSubmitCTA());
		Assert.assertNotNull(purchaseAccountModelTestBean.getTaxExemptLabel());
		Assert.assertNotNull(purchaseAccountModelTestBean.getTitle());
		Assert.assertNotNull(purchaseAccountModelTestBean.getVatExemptLabel());
		Assert.assertNotNull(purchaseAccountModelTestBean.getVatNumberLabel());
		Assert.assertNotNull(purchaseAccountModelTestBean.getTypeOfBusinessLabel());
		Assert.assertNotNull(purchaseAccountModelTestBean.getPublicLabel());
		Assert.assertNotNull(purchaseAccountModelTestBean.getPrivateLabel());
		Assert.assertNotNull(purchaseAccountModelTestBean.getAccountOfficeLabel());
		Assert.assertNotNull(purchaseAccountModelTestBean.getProcessingUnitLabel());
		Assert.assertNotNull(purchaseAccountModelTestBean.getTransactingUnitLabel());
		Assert.assertNotNull(purchaseAccountModelTestBean.getDistributorLabel());
		assertEquals("Yes", purchaseAccountModelTestBean.getYes());
		assertEquals("backCTA", purchaseAccountModelTestBean.getBackCTA());
	}

}
