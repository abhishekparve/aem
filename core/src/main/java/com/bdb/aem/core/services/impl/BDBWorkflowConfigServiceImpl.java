package com.bdb.aem.core.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.bdb.aem.core.services.BDBWorkflowConfigService;

/**
 * The Class BDBWorkflowConfigServiceImpl.
 */
@Component(immediate = true, service = BDBWorkflowConfigService.class)
@Designate(ocd = BDBWorkflowConfigServiceImpl.Configuration.class)
public class BDBWorkflowConfigServiceImpl implements BDBWorkflowConfigService {

	/** The temp document path. */
	private String tempDocumentPath;

	/** The var commerce base path. */
	private String varCommerceBasePath;

	/** The excel node base path. */
	private String excelNodeBasePath;

	/** The dam asset base path. */
	private String damAssetBasePath;

	/** The image base path. */
	private String imageBasePath;
	
	/** The processed image base path. */
	private String processedImageBasePath;

	/** The doc base path. */
	private String docBasePath;
	
	/** The processed doc base path. */
	private String processedDocBasePath;

	/** The clinical image path. */
	private String clinicalImagePath;
	
	/** The processed clinical image path. */
	private String processedClinicalImagePath;
	
	/** The temp asset base path. */
	private String tempAssetBasePath;
	
	/** The processed asset base path. */
	private String processedAssetBasePath;

	/** The batch size. */
	private int batchSize;
	
	/** The product image asset base path. */
	private String productImageBasePath;

	/**
	 * Activate.
	 *
	 * @param config the config
	 */
	@Activate
	@Modified
	protected final void activate(Configuration config) {
		this.tempDocumentPath = config.tempDocumentPath();
		this.varCommerceBasePath = config.varCommerceBasePath();
		this.excelNodeBasePath = config.excelNodeBasePath();
		this.damAssetBasePath = config.damAssetBasePath();
		this.imageBasePath = config.imageBasePath();
		this.processedImageBasePath = config.processedImageBasePath();
		this.docBasePath = config.docBasePath();
		this.processedDocBasePath = config.processedDocBasePath();
		this.clinicalImagePath = config.clinicalImagePath();
		this.processedClinicalImagePath = config.processedClinicalImagePath();
		this.tempAssetBasePath = config.tempAssetBasePath();
		this.processedAssetBasePath = config.processedAssetBasePath();
		this.batchSize = Integer.valueOf(config.batchSize());
		this.productImageBasePath = config.productImageBasePath();
	}

	/**
	 * Deactivate.
	 */
	@Deactivate
	protected void deactivate() {
		// DoNothing
	}

	/**
	 * Gets the temp document path.
	 *
	 * @return the temp document path
	 */
	@Override
	public String getTempDocumentPath() {
		return tempDocumentPath;
	}
	
	/**
	 * Gets the temp asset base path.
	 *
	 * @return the temp asset base path
	 */
	@Override
	public String getTempAssetBasePath() {
		return tempAssetBasePath;
	}
	
	/**
	 * Gets the processed asset base path.
	 *
	 * @return the processed asset base path
	 */
	@Override
	public String getProcessedAssetBasePath() {
		return processedAssetBasePath;
	}

	/**
	 * Gets the var commerce base path.
	 *
	 * @return the var commerce base path
	 */
	@Override
	public String getVarCommerceBasePath() {
		return varCommerceBasePath;
	}

	/**
	 * Gets the excel node base path.
	 *
	 * @return the excel node base path
	 */
	@Override
	public String getExcelNodeBasePath() {
		return excelNodeBasePath;
	}

	/**
	 * Gets the dam asset base path.
	 *
	 * @return the dam asset base path
	 */
	@Override
	public String getDamAssetBasePath() {
		return damAssetBasePath;
	}

	/**
	 * Gets the image base path.
	 *
	 * @return the image base path
	 */
	@Override
	public String getImageBasePath() {
		return imageBasePath;
	}
	
	/**
	 * Gets the processed image base path.
	 *
	 * @return the processed image base path
	 */
	@Override
	public String getProcessedImageBasePath() {
		return processedImageBasePath;
	}

	/**
	 * Gets the doc base path.
	 *
	 * @return the doc base path
	 */
	@Override
	public String getDocBasePath() {
		return docBasePath;
	}
	
	/**
	 * Gets the processed doc base path.
	 *
	 * @return the processed doc base path
	 */
	@Override
	public String getProcessedDocBasePath() {
		return processedDocBasePath;
	}

	/**
	 * Gets the batch size.
	 *
	 * @return the batch size
	 */
	@Override
	public int getBatchSize() {
		return batchSize;
	}

	/**
	 * Gets the clinical image path.
	 *
	 * @return the clinical image path
	 */
	@Override
	public String getClinicalImagePath() {
		return clinicalImagePath;
	}
	
	/**
	 * Gets the processed clinical image path.
	 *
	 * @return the processed clinical image path
	 */
	@Override
	public String getProcessedClinicalImagePath() {
		return processedClinicalImagePath;
	}
	
	/**
	 * Gets the product image asset base path
	 * 
	 * @return the product image asset base path
	 */
	@Override
	public String getProductImageBasePath() {
		return productImageBasePath;
	}
	

	/**
	 * The Interface Configuration.
	 */
	@ObjectClassDefinition(name = "BDB Work Flow Configuration")
	public @interface Configuration {

		/**
		 * Temp document path.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Temp Document Path", description = "Temprary Document Path", type = AttributeType.STRING)
		String tempDocumentPath() default "/content/dam/bdb/temp-assets/documents/";

		/**
		 * Var commerce base path.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Var Commerce Base Path", description = "Var Commerce Base Path", type = AttributeType.STRING)
		String varCommerceBasePath() default "/content/commerce/products/bdb/products/";

		/**
		 * Excel node base path.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Excel Node Base Path", description = "Excel Node Base Path", type = AttributeType.STRING)
		String excelNodeBasePath() default "/content/commerce/products/bdb/document/excel/";

		/**
		 * Dam asset base path.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Dam Asset Base Path", description = "Dam Asset Base Path", type = AttributeType.STRING)
		String damAssetBasePath() default "/content/dam/bdb/products/global/";

		/**
		 * Image base path.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Temp images Base Path", description = "Temp images Base Path", type = AttributeType.STRING)
		String imageBasePath() default "/content/dam/bdb/temp-assets/images";
		
		/**
		 * Processed image base path.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Temp images Base Path", description = "Temp images Base Path", type = AttributeType.STRING)
		String processedImageBasePath() default "/content/dam/bdb/processed-assets/images";

		/**
		 * Clinical image path.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Clinical Image Path", description = "Clinical Image Path", type = AttributeType.STRING)
		String clinicalImagePath() default "/content/dam/bdb/temp-assets/images/packaging-images/clinical-vial-images";
		
		/**
		 * Processed clinical image path.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Clinical Image Path", description = "Clinical Image Path", type = AttributeType.STRING)
		String processedClinicalImagePath() default "/content/dam/bdb/processed-assets/images/packaging-images/clinical-vial-images";

		/**
		 * Doc base path.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Temp Doc Base Path", description = "Temp Doc Base Path", type = AttributeType.STRING)
		String docBasePath() default "/content/dam/bdb/temp-assets/documents";
		
		/**
		 * Processed doc base path.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Temp Doc Base Path", description = "Temp Doc Base Path", type = AttributeType.STRING)
		String processedDocBasePath() default "/content/dam/bdb/processed-assets/documents";
		
		/**
		 * Temp asset base path.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Temp Asset Base Path", description = "Temp Asset Base Path", type = AttributeType.STRING)
		String tempAssetBasePath() default "/content/dam/bdb/temp-assets";
		
		/**
		 * Processed asset base path.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Processed Asset Base Path", description = "Processed Asset Base Path", type = AttributeType.STRING)
		String processedAssetBasePath() default "/content/dam/bdb/processed-assets";

		/**
		 * Batch size.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Batch Size for processing bulk workflow", description = "Batch Size for processing bulk workflow", type = AttributeType.STRING)
		public String batchSize() default "500";
		
		/**
		 * Image base path.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "Product Images Base Path", description = "Product Images Base Path", type = AttributeType.STRING)
		String productImageBasePath() default "/content/dam/bdb/temp-assets/products/images";
	}
}
