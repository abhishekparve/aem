package com.bdb.aem.core.api.response.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.bdb.aem.core.api.response.DataResponse;
import com.bdb.aem.core.api.response.ErrorResponse;
import junit.framework.Assert;

/**
 * The Class BaseResponseImplTest.
 */
class BaseResponseImplTest {

	/** The base response impl. */
	private BaseResponseImpl baseResponseImpl;

	/** The api error. */
	private ErrorResponse apiError;

	/** The data. */
	private DataResponse data;

	/**
	 * Inits the.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	public void init() throws Exception {
		baseResponseImpl = new BaseResponseImpl();
	}

	/**
	 * Test error code.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testErrorCode() throws Exception {
		apiError = new ErrorResponseImpl(500, "server not found");
		baseResponseImpl.setError(apiError);
		Assert.assertEquals(apiError, baseResponseImpl.getError());
		assertNotNull(apiError);
		Assert.assertEquals(true, baseResponseImpl.hasError());
		Assert.assertEquals(data, baseResponseImpl.getResponseData());
		baseResponseImpl.setResponseData(data);
	}
}
