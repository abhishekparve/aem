package com.bdb.aem.core.pojo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

import junitx.framework.Assert;

/**
 * The Class SignUpConfigTest.
 */
class SignUpConfigTest {

	/** The sign up test config. */
	SignUpConfig signUpTestConfig;
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		signUpTestConfig = new SignUpConfig("signInUrl", "captchaSiteKey", new Payload("url", "method"), new Payload("url", "method"), new JsonObject());
		signUpTestConfig.setEmailValidationPayload(new Payload("Endpoint", "Url"));
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		assertEquals(signUpTestConfig.getSignInUrl(),"signInUrl");
		assertEquals(signUpTestConfig.getCaptchaSiteKey(),"captchaSiteKey");
		assertNotNull(signUpTestConfig.getGetCountriesPayload());
		assertNotNull(signUpTestConfig.getPostRegisterPayload());
		assertNotNull(signUpTestConfig.getSingUpOTConsentData());
		assertNotNull(signUpTestConfig.getEmailValidationPayload());
		
	}

}
