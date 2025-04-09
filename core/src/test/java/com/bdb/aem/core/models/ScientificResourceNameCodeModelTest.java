package com.bdb.aem.core.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class ScientificResourceNameCodeModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ScientificResourceNameCodeModelTest {

	/** The scientific resource name code model. */
	@InjectMocks
	ScientificResourceNameCodeModel scientificResourceNameCodeModel;
	
	/**
	 * Test.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void test() throws NoSuchFieldException {
		PrivateAccessor.setField(scientificResourceNameCodeModel, "resourceName", "name");
		PrivateAccessor.setField(scientificResourceNameCodeModel, "resourceCode", "code");
		scientificResourceNameCodeModel.getResourceCode();
		scientificResourceNameCodeModel.getResourceName();
		scientificResourceNameCodeModel.toString();
	}

}
