package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

/**
 * The Class HrefTagModelTest.
 */
class HrefTagModelTest {
	
	/** The href tag model. */
	@InjectMocks
	HrefTagModel hrefTagModel;
	
	/**
	 * Setup.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setup() throws Exception {
		hrefTagModel = new HrefTagModel();
		hrefTagModel.setHrefLangCode("hrefLangCode");
		hrefTagModel.setHrefLangUrl("hrefLangUrl");
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertEquals("hrefLangCode", hrefTagModel.getHrefLangCode());
		assertEquals("hrefLangUrl", hrefTagModel.getHrefLangUrl());
	}
}
