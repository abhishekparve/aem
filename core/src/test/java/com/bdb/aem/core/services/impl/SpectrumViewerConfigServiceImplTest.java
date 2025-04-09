package com.bdb.aem.core.services.impl;

import junitx.util.PrivateAccessor;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The Class GraphImplTest.
 */
@ExtendWith({ MockitoExtension.class })
class SpectrumViewerConfigServiceImplTest {
	
	/** The Graph impl model. */
	@InjectMocks
	SpectrumViewerConfigServiceImpl spectrumViewerConfigServiceImplTest;
	
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
		PrivateAccessor.setField(spectrumViewerConfigServiceImplTest, "spectrumViewerEncryptionKey", TEST_VALUE);
		PrivateAccessor.setField(spectrumViewerConfigServiceImplTest, "spectrumViewerLaserList", TEST_VALUE);
		PrivateAccessor.setField(spectrumViewerConfigServiceImplTest, "cytometerConfigFile", TEST_VALUE);
		PrivateAccessor.setField(spectrumViewerConfigServiceImplTest, "fluorochromeConfigFile", TEST_VALUE);
		PrivateAccessor.setField(spectrumViewerConfigServiceImplTest, "spectrumViewerIconsPath", TEST_VALUE);
		PrivateAccessor.setField(spectrumViewerConfigServiceImplTest, "svLaserList", TEST_VALUE);
		PrivateAccessor.setField(spectrumViewerConfigServiceImplTest, "svFluorochromesSortOrder", TEST_VALUE);
		PrivateAccessor.setField(spectrumViewerConfigServiceImplTest, "systemCytometerEndpoint", TEST_VALUE);
		PrivateAccessor.setField(spectrumViewerConfigServiceImplTest, "systemFluorochromeEndpoint", TEST_VALUE);
		PrivateAccessor.setField(spectrumViewerConfigServiceImplTest, "systemSsmdataEndpoint", TEST_VALUE);
	}

	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
		assertNotNull(spectrumViewerConfigServiceImplTest.getSpectrumViewerEncryptionKey());
		assertNotNull(spectrumViewerConfigServiceImplTest.getCytometerConfigFile());
		assertNotNull(spectrumViewerConfigServiceImplTest.getFluorochromeConfigFile());
		assertNotNull(spectrumViewerConfigServiceImplTest.getSpectrumViewerLaserList());
		assertNotNull(spectrumViewerConfigServiceImplTest.getSpectrumViewerIconsPath());
		assertNotNull(spectrumViewerConfigServiceImplTest.getSvLaserList());
		assertNotNull(spectrumViewerConfigServiceImplTest.getSvFluorochromesSortOrder());
		assertNotNull(spectrumViewerConfigServiceImplTest.getSystemCytometerEndpoint());
		assertNotNull(spectrumViewerConfigServiceImplTest.getSystemFluorochromeEndpoint());
		assertNotNull(spectrumViewerConfigServiceImplTest.getSystemSsmdataEndpoint());
	}
}