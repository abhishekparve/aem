package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.impl.InstrumentConfigurationImpl;

import junitx.util.PrivateAccessor;

/**
 * The Class InstrumentConfigurationImplModel.
 */
@ExtendWith({ MockitoExtension.class })
class InstrumentConfigurationImplTest {
	
	/** The Instrument model. */
	@InjectMocks
	InstrumentConfigurationImpl instrumentImplModelTest;
	
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The test value. */
	private String TEST_VALUE = "test";
	
	/**
	 * Setup.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setup() throws Exception {
		PrivateAccessor.setField(instrumentImplModelTest, "title", TEST_VALUE);
		PrivateAccessor.setField(instrumentImplModelTest, "searchCytometerPlaceholder", TEST_VALUE);
		PrivateAccessor.setField(instrumentImplModelTest, "searchConfigurationPlaceholder", TEST_VALUE);
		PrivateAccessor.setField(instrumentImplModelTest, "defaultInstrument", TEST_VALUE);
		PrivateAccessor.setField(instrumentImplModelTest, "defaultInstrumentConfiguration", TEST_VALUE);
		PrivateAccessor.setField(instrumentImplModelTest, "deleteLaserIcon", TEST_VALUE);
		PrivateAccessor.setField(instrumentImplModelTest, "deleteLaserIconAlt", TEST_VALUE);
		
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertNotNull(instrumentImplModelTest.getTitle());
		assertNotNull(instrumentImplModelTest.getSearchCytometerPlaceholder());
		assertNotNull(instrumentImplModelTest.getSearchConfigurationPlaceholder());
		assertNotNull(instrumentImplModelTest.getDefaultInstrument());
		assertNotNull(instrumentImplModelTest.getDefaultInstrumentConfiguration());
		assertNotNull(instrumentImplModelTest.getDeleteLaserIcon());
		assertNotNull(instrumentImplModelTest.getDeleteLaserIconAlt());
		
	}
}