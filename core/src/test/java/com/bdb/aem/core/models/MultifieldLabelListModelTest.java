package com.bdb.aem.core.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class MultifieldLabelListModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class MultifieldLabelListModelTest {
	
	/** The multifield label list model. */
	@InjectMocks
	MultifieldLabelListModel multifieldLabelListModel;

	/**
	 * Test.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void test() throws NoSuchFieldException {
		PrivateAccessor.setField(multifieldLabelListModel, "descriptionText", "descriptionText");
		multifieldLabelListModel.getDescriptionText();
		multifieldLabelListModel.toString();
	}
}
