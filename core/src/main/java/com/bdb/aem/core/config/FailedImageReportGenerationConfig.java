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


@ObjectClassDefinition(name = "Failed Image Report Generation Scheduler Configuration", description = "Periodically triggers scheduler to generate fialed image report")
public @interface FailedImageReportGenerationConfig {

    @AttributeDefinition(name = "Failed Image Report Generation Scheduler", description = "Failed Image Report Generation Scheduler Configuration", type = AttributeType.STRING)
    String schedulerName() default "Failed Image Report Generation Scheduler";

    @AttributeDefinition(name = "Enabled", description = "Check to enable the scheduler", type = AttributeType.STRING)
    String enabled() default "true";

    @AttributeDefinition(name = "Cron Expression", description = "Cron expression used by the scheduler", type = AttributeType.STRING)
    String failedImagesCronExpression() default "0 0 12 1 1/1 ? *";
    
    @AttributeDefinition(name = "Failed records repository path", description = "Failed records repository path")
    String failedRecordsInVar() default "/var/bdb/products/images/failed-records";
    
    @AttributeDefinition(name = "Failed images report repository path", description = "Failed images report repository path")
    String failedReportDestinationPath() default "/content/dam/bdb/temp-assets/products/images/failure-report/";
}