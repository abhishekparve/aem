package com.bdb.aem.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.impl.BDBWorkflowConfigServiceImpl.Configuration;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class BDBWorkflowConfigServiceImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class BDBWorkflowConfigServiceImplTest {

	/** The BDB workflow config service impl. */
	private BDBWorkflowConfigServiceImpl BDBWorkflowConfigServiceImpl = new BDBWorkflowConfigServiceImpl();

	/** The config. */
	@Mock
	private Configuration config;

	/** The temp doc path. */
	private String TEMP_DOC_PATH = "tempDocumentPath";

	/** The var commerce base path. */
	private String VAR_COMMERCE_BASE_PATH = "varCommerceBasePath";

	/** The excel node base path. */
	private String EXCEL_NODE_BASE_PATH = "excelNodeBasePath";

	/** The dam asset path. */
	private String DAM_ASSET_PATH = "damAssetBasePath";
	
	/** The image base path. */
	private String IMAGE_BASE_PATH = "imageBasePath";

	/** The doc base path. */
	private String DOC_BASE_PATH = "docBasePath";

	/** The batch size. */
	private String BATCH_SIZE = "batchSize";
	
	/** The clinical vial. */
	private String CLINICAL_VIAL = "clinicalImagePath";

	/**
	 * Sets up.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {
		PrivateAccessor.setField(BDBWorkflowConfigServiceImpl, EXCEL_NODE_BASE_PATH, EXCEL_NODE_BASE_PATH);
		PrivateAccessor.setField(BDBWorkflowConfigServiceImpl, VAR_COMMERCE_BASE_PATH, VAR_COMMERCE_BASE_PATH);
		PrivateAccessor.setField(BDBWorkflowConfigServiceImpl, TEMP_DOC_PATH, TEMP_DOC_PATH);
		PrivateAccessor.setField(BDBWorkflowConfigServiceImpl, DAM_ASSET_PATH, DAM_ASSET_PATH);
		PrivateAccessor.setField(BDBWorkflowConfigServiceImpl, IMAGE_BASE_PATH, IMAGE_BASE_PATH);
		PrivateAccessor.setField(BDBWorkflowConfigServiceImpl, DOC_BASE_PATH, DOC_BASE_PATH);
		PrivateAccessor.setField(BDBWorkflowConfigServiceImpl, CLINICAL_VIAL, CLINICAL_VIAL);
		PrivateAccessor.setField(BDBWorkflowConfigServiceImpl, BATCH_SIZE, 1);
	}

	/**
	 * Gets the excel node base path test.
	 *
	 * @return the excel node base path test
	 */
	@Test
	void getExcelNodeBasePathTest() {
		assertEquals(EXCEL_NODE_BASE_PATH, BDBWorkflowConfigServiceImpl.getExcelNodeBasePath());
	}

	/**
	 * Gets the var commerce base path test.
	 *
	 * @return the var commerce base path test
	 */
	@Test
	void getVarCommerceBasePathTest() {
		assertEquals(VAR_COMMERCE_BASE_PATH, BDBWorkflowConfigServiceImpl.getVarCommerceBasePath());
	}

	/**
	 * Gets the temp document path test.
	 *
	 * @return the temp document path test
	 */
	@Test
	void getTempDocumentPathTest() {
		assertEquals(TEMP_DOC_PATH, BDBWorkflowConfigServiceImpl.getTempDocumentPath());
	}

	/**
	 * Gets the dam asset base path test.
	 *
	 * @return the dam asset base path test
	 */
	@Test
	void getDamAssetBasePathTest() {
		assertEquals(DAM_ASSET_PATH, BDBWorkflowConfigServiceImpl.getDamAssetBasePath());
	}
	
	/**
	 * Gets the image base path test.
	 *
	 * @return the image base path test
	 */
	@Test
	void getImageBasePathTest() {
		assertEquals(IMAGE_BASE_PATH, BDBWorkflowConfigServiceImpl.getImageBasePath());
	}
	
	/**
	 * Gets the doc base path test.
	 *
	 * @return the doc base path test
	 */
	@Test
	void getDocBasePathTest() {
		assertEquals(DOC_BASE_PATH, BDBWorkflowConfigServiceImpl.getDocBasePath());
	}
	
	/**
	 * Gets the batch size test.
	 *
	 * @return the batch size test
	 */
	@Test
	void getBatchSizeTest() {
		assertEquals(1, BDBWorkflowConfigServiceImpl.getBatchSize());
	}
	
	@Test
	void getClinicalImagePathTest() {
		assertEquals(CLINICAL_VIAL, BDBWorkflowConfigServiceImpl.getClinicalImagePath());
	}

	/**
	 * Activate test.
	 */
	@Test
	void activateTest() {
		when(config.tempDocumentPath()).thenReturn(TEMP_DOC_PATH);
		when(config.varCommerceBasePath()).thenReturn(VAR_COMMERCE_BASE_PATH);
		when(config.excelNodeBasePath()).thenReturn(EXCEL_NODE_BASE_PATH);
		when(config.damAssetBasePath()).thenReturn(DAM_ASSET_PATH);
		when(config.imageBasePath()).thenReturn(IMAGE_BASE_PATH);
		when(config.docBasePath()).thenReturn(DOC_BASE_PATH);
		when(config.clinicalImagePath()).thenReturn(CLINICAL_VIAL);
		when(config.batchSize()).thenReturn("1");
		BDBWorkflowConfigServiceImpl.activate(config);
	}

	/**
	 * Deactivate test.
	 */
	@Test
	void deactivateTest() {
		BDBWorkflowConfigServiceImpl.deactivate();
	}
}