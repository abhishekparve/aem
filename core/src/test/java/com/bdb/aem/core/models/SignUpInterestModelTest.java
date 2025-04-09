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
 * The Class SignUpInterestModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class SignUpInterestModelTest {
	
	/** The sign up interest model. */
	@InjectMocks
	SignUpInterestModel signUpInterestModel;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(signUpInterestModel, "label", "interestLabel");
		PrivateAccessor.setField(signUpInterestModel, "code", "interestCode");		
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertNotNull(signUpInterestModel.getInterestLabel());
		assertNotNull(signUpInterestModel.getInterestCode());
	}
	
	/**
	 * Test fields.
	 */
	@Test
	void testFields() {
		assertEquals("interestLabel",signUpInterestModel.getInterestLabel());
		assertEquals("interestCode",signUpInterestModel.getInterestCode());
	}
}
