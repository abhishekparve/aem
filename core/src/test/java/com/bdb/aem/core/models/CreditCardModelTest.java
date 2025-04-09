package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.day.cq.commons.inherit.InheritanceValueMap;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The class Contact Card Model Test.
 * 
 */

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class CreditCardModelTest {

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	
	/** The card model */
	@InjectMocks
	private CreditCardModel creditCardModel;

	/** The card mode model */
	@InjectMocks
	private CreditCardDetailModel creditCardDetailModel;

	@Mock
	InheritanceValueMap pageProperties;

	/** The BDB Api Endpoint Service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/**
	 * Sets up.
	 * 
	 * @throws NoSuchFieldException
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {

		PrivateAccessor.setField(creditCardModel, "creditCardLabels", "creditCardLabels");
		PrivateAccessor.setField(creditCardModel, "creditCardConfigs", "creditCardConfigs");

	}

	/**
	 * Tests all getters.
	 */
	@Test
	void testAllGetters() {
		assertNotNull(creditCardModel.getCreditCardLabels());
		assertNotNull(creditCardModel.getCreditCardConfigs());
	}

	@Test
	void testInit() {
		lenient().when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn("https://api-qa1.author.com");
		lenient().when(bdbApiEndpointService.paymetricDomain()).thenReturn("https://api-qa1.author.com");
		lenient().when(bdbApiEndpointService.paymetricIframeEndpoint()).thenReturn("https://api-qa1.author.com");
		lenient().when(bdbApiEndpointService.paymetricTokenEndpoint()).thenReturn("https://api-qa1.author.com");
		lenient().when(bdbApiEndpointService.getAddCreditCardEndpoint()).thenReturn("https://api-qa1.author.com");
		creditCardModel.init();
	}
}
