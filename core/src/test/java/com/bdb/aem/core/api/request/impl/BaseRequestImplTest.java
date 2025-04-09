package com.bdb.aem.core.api.request.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.api.request.util.HttpMethodType;



/**
 * The Class BaseRequestImplTest.
 */
@ExtendWith({ MockitoExtension.class })
class BaseRequestImplTest {

	/** The name value pairs. */
	List<NameValuePair> nameValuePairs = new ArrayList<>();

	/** The base request impl 1. */
	BaseRequestImpl baseRequestImpl1 = new BaseRequestImpl("url", HttpMethodType.GET, "data");

	/** The base request impl 2. */
	BaseRequestImpl baseRequestImpl2 = new BaseRequestImpl("url", HttpMethodType.GET, nameValuePairs);

	/** The base request impl 3. */
	BaseRequestImpl baseRequestImpl3 = new BaseRequestImpl("url", HttpMethodType.POST, "data");

	/** The base request impl 4. */
	BaseRequestImpl baseRequestImpl4 = new BaseRequestImpl("url", HttpMethodType.PUT, "data");

	/** The base request impl 5. */
	BaseRequestImpl baseRequestImpl5 = new BaseRequestImpl("url", HttpMethodType.DELETE, "data");

	/**
	 * Test base request impl string http method type string.
	 */
	@Test
	void testMethods() {
		baseRequestImpl1.getUrl();
		baseRequestImpl1.getData();
		baseRequestImpl1.gethttpMethodType();
		baseRequestImpl1.getMethod();
		baseRequestImpl3.getMethod();
		baseRequestImpl4.getMethod();
		assertNotNull(baseRequestImpl5.getMethod());
		baseRequestImpl2.getNameValuePairs();
	}

}
