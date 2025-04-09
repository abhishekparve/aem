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
public class CreditCardDetailModelTest {

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The card model */
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

		PrivateAccessor.setField(creditCardDetailModel, "id", "id");
		PrivateAccessor.setField(creditCardDetailModel, "icon", "icon");
		PrivateAccessor.setField(creditCardDetailModel, "iconAltText", "iconAltText");
		PrivateAccessor.setField(creditCardDetailModel, "length", "length");

	}

	/**
	 * Tests all getters.
	 */
	@Test
	void testAllGetters() {
		assertNotNull(creditCardDetailModel.getId());
		assertNotNull(creditCardDetailModel.getIcon());
		assertNotNull(creditCardDetailModel.getIconAltText());
		assertNotNull(creditCardDetailModel.getLength());
	}

}
