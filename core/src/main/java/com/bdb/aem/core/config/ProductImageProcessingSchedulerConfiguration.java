package com.bdb.aem.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * Product Image Processing Scheduler Configuration
 * <p>
 * Default configuration defined for Product Images Scheduler
 * All parameters are configurable via environment variable name defined below
 * schedulerName: env: [IWS_NAME]
 * enabled: env: [IWS_TOGGLE]
 * defaultRetryLimit: env: [IWS_RETRY_LIMIT]
 * srcAssetPathListInDAM: env: [IWS_ASSET_PATH]
 * cronExpression: env: [IWS_CRON]
 */


@ObjectClassDefinition(name = "Product Image Processing Scheduler Configuration", description = "Periodically triggers scheduler ot process product images")
public @interface ProductImageProcessingSchedulerConfiguration {

    @AttributeDefinition(name = "Product Image Processing Scheduler", description = "Product Image Processing Scheduler Configuration", type = AttributeType.STRING)
    String schedulerName() default "Flat File Image Processing Scheduler";

    @AttributeDefinition(name = "Enabled", description = "Check to enable the scheduler", type = AttributeType.STRING)
    String enabled() default "true";

    @AttributeDefinition(name = "Default Limit", description = "Asset Reprocessing limit", type = AttributeType.STRING)
    String defaultRetryLimit() default "4";

    @AttributeDefinition(name = "Cron Expression", description = "Cron expression used by the scheduler", type = AttributeType.STRING)
    String flatFileImageCronExpression() default "0 */3 * * *";

    @AttributeDefinition(name = "Asset upload path in repository", description = "Asset upload path in repository")
    String assetsPathListInDAM() default "/content/dam/bdb/temp-assets/products/images";
    
    @AttributeDefinition(name = "Unprocessed records repository path", description = "Unprocessed records repository path")
    String assetsReferencePathListInVar() default "/var/bdb/products/images/unprocessed-records";
    
    @AttributeDefinition(name = "Failed records repository path", description = "Failed records repository path")
    String failedRecordsRepPathListInVar() default "/var/bdb/products/images/failed-records";
    
    @AttributeDefinition(name = "Purge duration (in days) for failed records", description = "Purge duration (in days) for failed records", type = AttributeType.STRING)
    String purgeFialedRecordsDurationInDays() default "30";
}