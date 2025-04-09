package com.bdb.aem.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import junitx.util.PrivateAccessor;

/**
 * The class SV Model Test.
 */


@ExtendWith({ MockitoExtension.class })
public class SpectrumViewerModelTest {
	
		/** The searchPathUrl. */
		private final String searchPathUrl = "searchPathUrl";
		
		/** The sortingOrder. */
		private final String sortingOrder = "sortingOrder";
		
		/** The cta test model. */
	    @InjectMocks
		SpectrumViewerModel spectrumVieweTestrModel;
	    
	    /** The resolver factory. */
		@Mock
		ResourceResolverFactory resolverFactory;

		/** The resource resolver. */
		@Mock
		ResourceResolver resourceResolver;

		/** The test value. */
		private String TEST_VALUE = "test";
	    
		/**
		 * Sets the up.
		 *
		 * @throws Exception the exception
		 */
		@BeforeEach
		void setUp() throws Exception {
			PrivateAccessor.setField(spectrumVieweTestrModel, "searchPathUrl", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "sortingOrder", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "title", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "expandWorkspaceLabel", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "collapseWorkspaceLabel", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "exportIcon", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "expandWorkspaceIcon", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "collapseWorkspaceIcon", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "faqIcon", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "downIcon", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "exportIconAlt", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "expandWorkspaceIconAlt", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "collapseWorkspaceIconAlt", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "faqIconAlt", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "downIconAlt", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "popupTitle", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "popupMessageText", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "popupCancelButton", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "popupOkButton", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "popupCytometerTitle", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "popupCytometerNamePlaceholder", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "popupConfigurationNamePlaceholder", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "popupValidationMessage", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "fluorochromeSetNamePlaceholder", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "briefDescriptionOptional", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "alert", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "saveSuccessFlu", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "overwrite", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "confirmation", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "confirmationMessage", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "warningMessage", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "delete", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "dontShowMessage", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "systemCytometerConfigStr", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "systemFluorochromesStr", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "encryptedJsonStr", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "decryptedJsonStr", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "secureKeyPart1", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "secureKeyPart2", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "spectrumViewerConfig", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "spectrumViewerLables", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "errorIcon", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "errorIconAlt", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "outOfRange", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "saveWarningToast", TEST_VALUE);

			PrivateAccessor.setField(spectrumVieweTestrModel, "popupTitleMatrix", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "similarityScores", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "complexityScore", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "complexityScoreNotAvailable", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "popupMessageSubtitle", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "popupMessageContent", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "exportImgbutton", TEST_VALUE);

			PrivateAccessor.setField(spectrumVieweTestrModel, "similarityAndcomplexity", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "exportCSV", TEST_VALUE);

			PrivateAccessor.setField(spectrumVieweTestrModel, "failedMessage", TEST_VALUE);
			PrivateAccessor.setField(spectrumVieweTestrModel, "popupCytoAlertMessage", TEST_VALUE);
			

		}
		
		/**
		 * Test getters.
		 */
		@Test
		void testGetters() {
			assertNotNull(spectrumVieweTestrModel.getSearchPathUrl());
			assertNotNull(spectrumVieweTestrModel.getSortingOrder());
			assertNotNull(spectrumVieweTestrModel.getTitle());
			assertNotNull(spectrumVieweTestrModel.getExpandWorkspaceLabel());
			assertNotNull(spectrumVieweTestrModel.getExpandWorkspaceIcon());
			assertNotNull(spectrumVieweTestrModel.getExpandWorkspaceIconAlt());
			assertNotNull(spectrumVieweTestrModel.getExportIcon());
			assertNotNull(spectrumVieweTestrModel.getExportIconAlt());
			assertNotNull(spectrumVieweTestrModel.getDownIcon());
			assertNotNull(spectrumVieweTestrModel.getDownIconAlt());
			assertNotNull(spectrumVieweTestrModel.getFaqIcon());
			assertNotNull(spectrumVieweTestrModel.getFaqIconAlt());
			assertNotNull(spectrumVieweTestrModel.getExportIconAlt());
			assertNotNull(spectrumVieweTestrModel.getExpandWorkspaceIconAlt());
			assertNotNull(spectrumVieweTestrModel.getDownIconAlt());
			assertNotNull(spectrumVieweTestrModel.getPopupTitle());
			assertNotNull(spectrumVieweTestrModel.getPopupMessageText());
			assertNotNull(spectrumVieweTestrModel.getPopupCancelButton());
			assertNotNull(spectrumVieweTestrModel.getPopupOkButton());
			assertNotNull(spectrumVieweTestrModel.getPopupCytometerTitle());
			assertNotNull(spectrumVieweTestrModel.getPopupCytometerNamePlaceholder());
			assertNotNull(spectrumVieweTestrModel.getPopupConfigurationNamePlaceholder());
			assertNotNull(spectrumVieweTestrModel.getPopupValidationMessage());
			assertNotNull(spectrumVieweTestrModel.getCollapseWorkspaceLabel());
			assertNotNull(spectrumVieweTestrModel.getCollapseWorkspaceIcon());
			assertNotNull(spectrumVieweTestrModel.getCollapseWorkspaceIconAlt());
			assertNotNull(spectrumVieweTestrModel.getSearchPathUrl());
			assertNotNull(spectrumVieweTestrModel.getSortingOrder());
			assertNotNull(spectrumVieweTestrModel.getSpectrumViewerConfig());
			assertNotNull(spectrumVieweTestrModel.getSpectrumViewerLables());
			assertNotNull(spectrumVieweTestrModel.getEncryptedJsonStr());
			assertNotNull(spectrumVieweTestrModel.getDecryptedJsonStr());
			assertNotNull(spectrumVieweTestrModel.getSecureKeyPart1());
			assertNotNull(spectrumVieweTestrModel.getSecureKeyPart2());
			assertNotNull(spectrumVieweTestrModel.getErrorIcon());
			assertNotNull(spectrumVieweTestrModel.getErrorIconAlt());
			assertNotNull(spectrumVieweTestrModel.getOutOfRange());
			assertNotNull(spectrumVieweTestrModel.getSaveWarningToast());
			assertNotNull(spectrumVieweTestrModel.getPopupTitleMatrix());
			assertNotNull(spectrumVieweTestrModel.getComplexityScore());
			assertNotNull(spectrumVieweTestrModel.getComplexityScoreNotAvailable());
			assertNotNull(spectrumVieweTestrModel.getSimilarityScores());
			assertNotNull(spectrumVieweTestrModel.getPopupMessageSubtitle());
			assertNotNull(spectrumVieweTestrModel.getPopupMessageContent());
			assertNotNull(spectrumVieweTestrModel.getExportImgbutton());
			assertNotNull(spectrumVieweTestrModel.getSimilarityAndcomplexity());
			assertNotNull(spectrumVieweTestrModel.getExportCSV());
			assertNotNull(spectrumVieweTestrModel.getFailedMessage());
			assertNotNull(spectrumVieweTestrModel.getPopupCytoAlertMessage());			
		}
		
	}
	