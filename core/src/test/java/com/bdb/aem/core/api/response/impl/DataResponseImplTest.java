package com.bdb.aem.core.api.response.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.Assert;

/**
 * The Class DataResponseImplTest.
 */
class DataResponseImplTest {

	/** The data response. */
	private DataResponseImpl dataResponse;
	/** The data. */
	private String data;
	/** The status code. */
	private int statusCode;

	/**
	 * Inits the.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	public void init() throws Exception {
		dataResponse = new DataResponseImpl();
	}
	
	/**
	 * Test get data.
	 */
	@Test
	void testGetData() {
		Assert.assertEquals(data, dataResponse.getData());
		assertNotNull(dataResponse);
		dataResponse.setData(data);
	}
	
	/**
	 * Test get status code.
	 */
	@Test
	void testGetStatusCode() {
		Assert.assertEquals(statusCode, dataResponse.getStatusCode());
		assertNotNull(dataResponse);
		dataResponse.setStatusCode(statusCode);
	}
}
