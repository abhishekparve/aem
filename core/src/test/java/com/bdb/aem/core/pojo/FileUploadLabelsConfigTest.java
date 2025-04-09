package com.bdb.aem.core.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junitx.framework.Assert;

/**
 * The Class FileUploadLabelsConfigTest.
 */
class FileUploadLabelsConfigTest {
	
	/** The fileupload test config. */
	FileUploadLabelsConfig fileuploadTestConfig;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		fileuploadTestConfig = new FileUploadLabelsConfig("title", "info", "dropZoneLabel", "dropZoneInfo", "chooseFileCTALabel");
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		Assert.assertEquals(fileuploadTestConfig.getFieldTitle(), "title");
		Assert.assertEquals(fileuploadTestConfig.getDropZoneTitle(),"dropZoneLabel");
		Assert.assertNotNull(fileuploadTestConfig.getDropZoneNote());
		Assert.assertNotNull(fileuploadTestConfig.getInfo());
		Assert.assertNotNull(fileuploadTestConfig.getChooseFileCTALabel());	
	}
}
