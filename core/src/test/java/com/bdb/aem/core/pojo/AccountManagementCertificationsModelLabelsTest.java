package com.bdb.aem.core.pojo;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bdb.aem.core.bean.AccountManagementCertificationsLabelsBean;
import com.bdb.aem.core.models.UploadCertificationFileTypeModel;
import com.google.gson.JsonObject;

import junitx.framework.Assert;

/**
 * The Class AccountManagementCertificationsModelLabelsTest.
 */
class AccountManagementCertificationsModelLabelsTest {
	
	/** The certifications model test labels. */
	AccountManagementCertificationsModelLabels certificationsModelTestLabels;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		AccountManagementCertificationsLabelsBean certificationsLabelsTestBean;
		certificationsLabelsTestBean = new AccountManagementCertificationsLabelsBean();
		certificationsLabelsTestBean.setCertificationsHeader("certificationsHeader");
		certificationsLabelsTestBean.setCertificationsImage("certificationsImage");
		certificationsLabelsTestBean.setCertificationType("certificationType");
		certificationsLabelsTestBean.setDocumentName("documentName");
		certificationsLabelsTestBean.setExpiryDate("expiryDate");
		certificationsLabelsTestBean.setLinkedAddress("linkedAddress");
		certificationsLabelsTestBean.setNoCertificateText("noCertificateText");
		certificationsLabelsTestBean.setRemove("remove");
		certificationsLabelsTestBean.setShipToNumber("shipToNumber");
		certificationsLabelsTestBean.setStatusFields("status");
		certificationsLabelsTestBean.setUploadDocumentsCtaLabel("uploadDocumentsCtaLabel");
		certificationsLabelsTestBean.setCloseIcon("CloseIcon");
		certificationsLabelsTestBean.setCloseIconAltText("CloseIconAltText");
		certificationsLabelsTestBean.setUploadInstructionLabel("uploadInstructionLabel");
		certificationsLabelsTestBean.setAddressLinkedText("addressLinkedText");
		certificationsLabelsTestBean.setCancel("cancel");
		certificationsLabelsTestBean.setChooseFilesCtaLabel("chooseFilesCtaLabel");
		certificationsLabelsTestBean.setConfirm("confirm");
		certificationsLabelsTestBean.setDropDocumentsText("dropDocumentsText");
		certificationsLabelsTestBean.setFileTypeText("fileTypeText");
		certificationsLabelsTestBean.setUploadDocumentsModalHeader("uploadDocumentsModalHeader");
		certificationsLabelsTestBean.setUploadSuccessText("uploadSuccessText");
		certificationsLabelsTestBean.setFileTypesList(new ArrayList<UploadCertificationFileTypeModel>());
		certificationsLabelsTestBean.setAddressSelectionLabels(new JsonObject());
		
		certificationsModelTestLabels = new AccountManagementCertificationsModelLabels(certificationsLabelsTestBean);
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertEquals(certificationsModelTestLabels.getCertificationsHeader(), "certificationsHeader");
		Assert.assertEquals(certificationsModelTestLabels.getCertificationsImage(), "certificationsImage");
		Assert.assertEquals(certificationsModelTestLabels.getCertificationType(), "certificationType");
		Assert.assertNotNull(certificationsModelTestLabels.getDocumentName());
		Assert.assertNotNull(certificationsModelTestLabels.getExpiryDate());
		Assert.assertNotNull(certificationsModelTestLabels.getLinkedAddress());
		Assert.assertNotNull(certificationsModelTestLabels.getNoCertificateText());
		Assert.assertNotNull(certificationsModelTestLabels.getRemove());
		Assert.assertNotNull(certificationsModelTestLabels.getShipToNumber());
		Assert.assertNotNull(certificationsModelTestLabels.getStatusFields());
		Assert.assertNotNull(certificationsModelTestLabels.getUploadDocumentsCtaLabel());
		Assert.assertNotNull(certificationsModelTestLabels.getUploadInstructionLabel());
		
		Assert.assertEquals(certificationsModelTestLabels.getCloseIcon(),"CloseIcon");
		Assert.assertEquals(certificationsModelTestLabels.getCloseIconAltText(),"CloseIconAltText");
		Assert.assertNotNull(certificationsModelTestLabels.getAddressLinkedText());
		Assert.assertNotNull(certificationsModelTestLabels.getCancelCTALabel());
		Assert.assertNotNull(certificationsModelTestLabels.getChooseFilesCtaLabel());
		Assert.assertNotNull(certificationsModelTestLabels.getConfirmCTALabel());
		Assert.assertNotNull(certificationsModelTestLabels.getDropDocumentsText());
		Assert.assertNotNull(certificationsModelTestLabels.getFileTypeText());
		Assert.assertNotNull(certificationsModelTestLabels.getUploadDocumentsModalHeader());
		Assert.assertNotNull(certificationsModelTestLabels.getUploadSuccessText());
		Assert.assertNotNull(certificationsModelTestLabels.getFileTypesList());
		Assert.assertNotNull(certificationsModelTestLabels.getAddressSelectionLabels());
	}

}
