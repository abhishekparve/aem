package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import junitx.util.PrivateAccessor;

/**
 * The Class IcmStepsListModelTest.
 */
@ExtendWith({ MockitoExtension.class })
class IcmStepsListModelTest {
	
	/** The icm steps list model. */
	@InjectMocks
	IcmStepsListModel icmStepsListModel;

	/**
	 * Setup.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setup() throws NoSuchFieldException {
		PrivateAccessor.setField(icmStepsListModel, "stepTitle", "stepTitle");
		PrivateAccessor.setField(icmStepsListModel, "firstWord", "firstWord");
		PrivateAccessor.setField(icmStepsListModel, "description", "description");
		PrivateAccessor.setField(icmStepsListModel, "stepImg", "stepImg");
		PrivateAccessor.setField(icmStepsListModel, "stepImgAlt", "stepImgAlt");
	}
	
	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertEquals("stepTitle", icmStepsListModel.getStepTitle());
		assertEquals("firstWord", icmStepsListModel.getFirstWord());
		assertEquals("description", icmStepsListModel.getDescription());
		assertEquals("stepImg", icmStepsListModel.getStepImg());
		assertEquals("stepImgAlt", icmStepsListModel.getStepImgAlt());
	}
}
