package com.bdb.aem.core.bean;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bdb.aem.core.models.UploadCertificationFileTypeModel;
import com.google.gson.JsonObject;

import junitx.framework.Assert;

/**
 * The Class AccountManagementCertificationsLabelsBeanTest.
 */
class AccountManagementCertificationsLabelsBeanTest {

	/** The certifications labels test bean. */
	AccountManagementCertificationsLabelsBean certificationsLabelsTestBean;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
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
		
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertEquals(certificationsLabelsTestBean.getCertificationsHeader(), "certificationsHeader");
		Assert.assertEquals(certificationsLabelsTestBean.getCertificationsImage(), "certificationsImage");
		Assert.assertEquals(certificationsLabelsTestBean.getCertificationType(), "certificationType");
		Assert.assertNotNull(certificationsLabelsTestBean.getDocumentName());
		Assert.assertNotNull(certificationsLabelsTestBean.getExpiryDate());
		Assert.assertNotNull(certificationsLabelsTestBean.getLinkedAddress());
		Assert.assertNotNull(certificationsLabelsTestBean.getNoCertificateText());
		Assert.assertNotNull(certificationsLabelsTestBean.getRemove());
		Assert.assertNotNull(certificationsLabelsTestBean.getShipToNumber());
		Assert.assertNotNull(certificationsLabelsTestBean.getStatusFields());
		Assert.assertNotNull(certificationsLabelsTestBean.getUploadDocumentsCtaLabel());
		Assert.assertNotNull(certificationsLabelsTestBean.getUploadInstructionLabel());
		
		Assert.assertEquals(certificationsLabelsTestBean.getCloseIcon(),"CloseIcon");
		Assert.assertEquals(certificationsLabelsTestBean.getCloseIconAltText(),"CloseIconAltText");
		Assert.assertNotNull(certificationsLabelsTestBean.getAddressLinkedText());
		Assert.assertNotNull(certificationsLabelsTestBean.getCancel());
		Assert.assertNotNull(certificationsLabelsTestBean.getChooseFilesCtaLabel());
		Assert.assertNotNull(certificationsLabelsTestBean.getConfirm());
		Assert.assertNotNull(certificationsLabelsTestBean.getDropDocumentsText());
		Assert.assertNotNull(certificationsLabelsTestBean.getFileTypeText());
		Assert.assertNotNull(certificationsLabelsTestBean.getUploadDocumentsModalHeader());
		Assert.assertNotNull(certificationsLabelsTestBean.getUploadSuccessText());
		Assert.assertNotNull(certificationsLabelsTestBean.getFileTypesList());
		Assert.assertNotNull(certificationsLabelsTestBean.getAddressSelectionLabels());
	}
}
