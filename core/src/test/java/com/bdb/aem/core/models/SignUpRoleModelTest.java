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
 * The Class SignUpRoleModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class SignUpRoleModelTest {
	
	/** The sign up role model. */
	@InjectMocks
	SignUpRoleModel signUpRoleModel;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		PrivateAccessor.setField(signUpRoleModel, "label", "roleLabels");
		PrivateAccessor.setField(signUpRoleModel, "code", "roleCode");
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertNotNull(signUpRoleModel.getRoleLabel());
		assertNotNull(signUpRoleModel.getRoleCode());
	}
	
	/**
	 * Test fields.
	 */
	@Test
	void testFields() {
		assertEquals("roleLabels",signUpRoleModel.getRoleLabel());
		assertEquals("roleCode",signUpRoleModel.getRoleCode());
	}
}
