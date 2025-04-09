package com.bdb.aem.core.services;

/**
 * The Interface BDBWorkflowConfigService.
 */
public interface BDBWorkflowConfigService extends BaseService {

	/**
	 * Gets the temp document path.
	 *
	 * @return the temp document path
	 */
	public String getTempDocumentPath();

	/**
	 * Gets the var commerce base path.
	 *
	 * @return the var commerce base path
	 */
	public String getVarCommerceBasePath();

	/**
	 * Gets the excel node base path.
	 *
	 * @return the excel node base path
	 */
	public String getExcelNodeBasePath();

	/**
	 * Gets the dam asset base path.
	 *
	 * @return the dam asset base path
	 */
	String getDamAssetBasePath();

	/**
	 * Gets the image base path.
	 *
	 * @return the image base path
	 */
	String getImageBasePath();

	/**
	 * Gets the doc base path.
	 *
	 * @return the doc base path
	 */
	String getDocBasePath();

	/**
	 * Gets the batch size.
	 *
	 * @return the batch size
	 */
	int getBatchSize();

	/**
	 * Gets the clinical image path.
	 *
	 * @return the clinical image path
	 */
	String getClinicalImagePath();

	/**
	 * Gets the temp asset base path.
	 *
	 * @return the temp asset base path
	 */
	String getTempAssetBasePath();

	/**
	 * Gets the processed asset base path.
	 *
	 * @return the processed asset base path
	 */
	String getProcessedAssetBasePath();

	/**
	 * Gets the processed image base path.
	 *
	 * @return the processed image base path
	 */
	String getProcessedImageBasePath();

	/**
	 * Gets the processed clinical image path.
	 *
	 * @return the processed clinical image path
	 */
	String getProcessedClinicalImagePath();

	/**
	 * Gets the processed doc base path.
	 *
	 * @return the processed doc base path
	 */
	String getProcessedDocBasePath();
	
	/**
	 * Gets the product image asset base path
	 * @return the product image asset base path
	 */
	String getProductImageBasePath();
}
