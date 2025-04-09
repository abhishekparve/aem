package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ExternalizerService;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class InstallationsInstructionsModelTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class InstallationsInstructionsModelTest {
	
	/** The installations instructions model. */
	@InjectMocks
	InstallationsInstructionsModel installationsInstructionsModel;
	
	/** The externalizer service. */
    @Mock
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @Mock
    ResourceResolver resourceResolver;
	
	/** The category installation instructions link. */
	private String categoryInstallationInstructionsLink;
	
	/** The category installation instructions label. */
	private String categoryInstallationInstructionsLabel;
	
	/**
	 * Setup.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setup() throws NoSuchFieldException {
		PrivateAccessor.setField(installationsInstructionsModel, "categoryInstallationInstructionsLink", "categoryInstallationInstructionsLink");
		PrivateAccessor.setField(installationsInstructionsModel, "categoryInstallationInstructionsLabel", "categoryInstallationInstructionsLabel");
	}
	
	/**
	 * Test getters.
	 */
	@Test
	void testGetters() {
        lenient().when(externalizerService.getFormattedUrl("imagePath", resourceResolver)).thenReturn("/content/dam/image.png");
		assertNotNull(installationsInstructionsModel.getCategoryInstallationInstructionsLabel());
		assertNotNull(installationsInstructionsModel.getCategoryInstallationInstructionsLink());
		installationsInstructionsModel.init();
	}

}
