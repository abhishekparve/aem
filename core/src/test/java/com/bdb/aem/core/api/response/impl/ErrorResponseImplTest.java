package com.bdb.aem.core.api.response.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.Assert;

/**
 * The Class ErrorResponseImplTest.
 */
public class ErrorResponseImplTest {

	/** The error response impl. */
	private ErrorResponseImpl errorResponseImpl;

	/**
	 * Inits the.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	public void init() throws Exception {

		errorResponseImpl = new ErrorResponseImpl(500, "Internal Server Error");

	}

	/**
	 * Test error code.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testErrorCode() throws Exception {
		Assert.assertEquals(500, errorResponseImpl.getErrorCode());

	}

	/**
	 * Test error message.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testErrorMessage() throws Exception {
		Assert.assertEquals("Internal Server Error", errorResponseImpl.getMessage());
	}

}
