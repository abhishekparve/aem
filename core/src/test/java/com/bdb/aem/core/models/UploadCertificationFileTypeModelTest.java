package com.bdb.aem.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.framework.Assert;
import junitx.util.PrivateAccessor;

/**
 * The Class UploadCertificationFileTypeModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class UploadCertificationFileTypeModelTest {
	
	/** The upload certification file type test model. */
	@InjectMocks
	UploadCertificationFileTypeModel uploadCertificationFileTypeTestModel;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(uploadCertificationFileTypeTestModel, "id", "100");
		PrivateAccessor.setField(uploadCertificationFileTypeTestModel, "label", "TestCertificate");
	}

	/**
	 * Test get id.
	 */
	@Test
	void testGetId() {
		Assert.assertEquals(uploadCertificationFileTypeTestModel.getId(), "100");
	}

	/**
	 * Test get label.
	 */
	@Test
	void testGetLabel() {
		Assert.assertEquals(uploadCertificationFileTypeTestModel.getLabel(), "TestCertificate");
	}
}