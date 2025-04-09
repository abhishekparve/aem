package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class ScientificResourcesDropdownModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ScientificResourcesDropdownModelTest {
	
	/** The scientific resources dropdown model. */
	@InjectMocks
	ScientificResourcesDropdownModel scientificResourcesDropdownModel;

	/** The label. */
	private final String LABEL = "label";
	
	/** The value. */
	private final int VALUE = 12345;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(scientificResourcesDropdownModel, "label", LABEL);
		PrivateAccessor.setField(scientificResourcesDropdownModel, "value", VALUE);
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertEquals(scientificResourcesDropdownModel.getLabel(), LABEL);
		assertEquals(scientificResourcesDropdownModel.getValue(), VALUE);
	}
}
