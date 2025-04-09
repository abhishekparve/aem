package com.bdb.aem.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.impl.MonetateEndpointServiceImpl.Configuration;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;


/**
 * The Class MonetateEndpointServiceImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class MonetateEndpointServiceImplTest {

	/** The monetate endpoint service impl. */
	@InjectMocks
	private MonetateEndpointServiceImpl monetateEndpointServiceImpl = new MonetateEndpointServiceImpl();

	/** The config. */
	@Mock
	Configuration config;
	
	/** The monetate products endpoint. */
	private String MONETATE_PRODUCTS_ENDPOINT = "/occ/v2/{{site}}/users/{userId}/updateinformation";
	
	
	
	/**
	 * Sets the up.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {
		PrivateAccessor.setField(monetateEndpointServiceImpl, "monetateProductsEndpoint", MONETATE_PRODUCTS_ENDPOINT);
	}
	
	
	/**
	 * Test activate.
	 */
	@Test
	void testActivate() {
		when(config.monetateProductsEndpoint()).thenReturn("//se.monetate.net/js/2/a-f427f57e/d/dev-gl.bdbiosciences.com/entry.js");
		
		monetateEndpointServiceImpl.activate(config);
	}
	
	
	/**
	 * Deactivate.
	 */
	@Test
	void deactivate() {
		monetateEndpointServiceImpl.deactivate();
	}
	
	
	
	/**
	 * Gets the monetate products endpoint.
	 *
	 * @return the monetate products endpoint
	 */
	@Test
	void getMonetateProductsEndpoint() {
		assertEquals(MONETATE_PRODUCTS_ENDPOINT, monetateEndpointServiceImpl.getMonetateProductsEndpoint());
	}
	
}