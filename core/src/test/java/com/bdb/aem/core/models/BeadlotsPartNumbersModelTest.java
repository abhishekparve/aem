package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class BeadlotsPartNumbersModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class BeadlotsPartNumbersModelTest {
	
	/** The beadlots part numbers model. */
	@InjectMocks
	BeadlotsPartNumbersModel beadlotsPartNumbersModel;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(beadlotsPartNumbersModel, "badge", "badge");
		PrivateAccessor.setField(beadlotsPartNumbersModel, "partNumber", "partNumber");
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertNotNull(beadlotsPartNumbersModel.getBadge());
		assertNotNull(beadlotsPartNumbersModel.getPartNumber());
	}
	
	/**
	 * Test fields.
	 */
	@Test
	void testFields() {
		assertEquals("badge",beadlotsPartNumbersModel.getBadge());
		assertEquals("partNumber",beadlotsPartNumbersModel.getPartNumber());
	}
}
