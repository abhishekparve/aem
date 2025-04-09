package com.bdb.aem.core.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.Assert;

/**
 * The Class PdfMetaDataBeanTest.
 */
class PdfMetaDataBeanTest {

	/** The pdf meta data bean. */
	PdfMetaDataBean pdfMetaDataBean;

	/** The array. */
	private String[] array = { "US", "EU" };

	/**
	 * Sets the up.
	 */
	@BeforeEach
	void setUp() {
		pdfMetaDataBean = new PdfMetaDataBean();
		pdfMetaDataBean.setDamFolderFileName("");
		pdfMetaDataBean.setDocTitle("");
		pdfMetaDataBean.setDocType("");
		pdfMetaDataBean.setDocRegion(array);
		pdfMetaDataBean.setDocLang("");
		pdfMetaDataBean.setDocSku("");
		pdfMetaDataBean.setDocDesc("");
		pdfMetaDataBean.setDocExpiryDate("");
		pdfMetaDataBean.setDocRegStatus("");
		pdfMetaDataBean.setDocRevision("");
		pdfMetaDataBean.setDocOwner("");
		pdfMetaDataBean.setProductName("");
		pdfMetaDataBean.setDocKeywords("");
		pdfMetaDataBean.setDocPartId("");
		pdfMetaDataBean.setTempFolderFileName("");
		pdfMetaDataBean.setTempFolderFilePath("");
		pdfMetaDataBean.setDamFolderFileName("");
		pdfMetaDataBean.setDamFolderFilePath("");
	}

	/**
	 * Gets the methods test.
	 *
	 * @return the methods test
	 */
	@Test
	void getMethodsTest() {
		Assert.assertEquals(pdfMetaDataBean.getDamFolderFileName(), "");
		Assert.assertEquals(pdfMetaDataBean.getDocTitle(), "");
		Assert.assertEquals(pdfMetaDataBean.getDocType(), "");
		Assert.assertEquals(pdfMetaDataBean.getDocRegion()[0],array[0]);
		Assert.assertEquals(pdfMetaDataBean.getDocRegion()[1],array[1]);
		Assert.assertEquals(pdfMetaDataBean.getDocLang(), "");
		Assert.assertEquals(pdfMetaDataBean.getDocSku(), "");
		Assert.assertEquals(pdfMetaDataBean.getDocDesc(), "");
		Assert.assertEquals(pdfMetaDataBean.getDocExpiryDate(), "");
		Assert.assertEquals(pdfMetaDataBean.getDocRegStatus(), "");
		Assert.assertEquals(pdfMetaDataBean.getDocRevision(), "");
		Assert.assertEquals(pdfMetaDataBean.getDocOwner(), "");
		Assert.assertEquals(pdfMetaDataBean.getProductName(), "");
		Assert.assertEquals(pdfMetaDataBean.getDocKeywords(), "");
		Assert.assertEquals(pdfMetaDataBean.getDocPartId(), "");
		Assert.assertEquals(pdfMetaDataBean.getTempFolderFileName(), "");
		Assert.assertEquals(pdfMetaDataBean.getTempFolderFilePath(), "");
		Assert.assertEquals(pdfMetaDataBean.getDamFolderFileName(), "");
		Assert.assertEquals(pdfMetaDataBean.getDamFolderFilePath(), "");
	}
}
