package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import junitx.util.PrivateAccessor;

/**
 * The Class FlurochromeModelTest.
 */
@ExtendWith({ MockitoExtension.class })
class FlurochromeModelTest {
	
	/** The flurochrome model. */
	@InjectMocks
	FlurochromeModel flurochromeModel;
	
	/** The color. */
	private final String COLOR = "color";
	
	/** The label. */
	private final String LABEL = "label";

	/**
	 * Setup.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setup() throws Exception {
		PrivateAccessor.setField(flurochromeModel, "color", COLOR);
		PrivateAccessor.setField(flurochromeModel, "label", LABEL);
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertEquals(flurochromeModel.getColor(), COLOR);
		assertEquals(flurochromeModel.getLabel(), LABEL);
	}
}