package com.bdb.aem.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * Image Workflow Scheduler Configuration
 * <p>
 * Default configuration defined for Image workflow Scheduler
 * All parameters are configurable via environment variable name defined below
 * schedulerName: env: [IWS_NAME]
 * enabled: env: [IWS_TOGGLE]
 * defaultRetryLimit: env: [IWS_RETRY_LIMIT]
 * srcAssetPathListInDAM: env: [IWS_ASSET_PATH]
 * cronExpression: env: [IWS_CRON]
 */


@ObjectClassDefinition(name = "Image Workflow Scheduler Configuration", description = "Periodically triggers workflow for Image Asset fail due to unfound product data")
public @interface ImageWorkflowSchedulerConfiguration {

    @AttributeDefinition(name = "Image Workflow Scheduler", description = "Image Workflow Scheduler Configuration", type = AttributeType.STRING)
    String schedulerName() default "Image Workflow Scheduler";

    @AttributeDefinition(name = "Enabled", description = "Check to enable the scheduler", type = AttributeType.STRING)
    String enabled() default "true";

    @AttributeDefinition(name = "Default Limit", description = "Asset Reprocessing limit", type = AttributeType.STRING)
    String defaultRetryLimit() default "24";

    @AttributeDefinition(name = "Cron Expression", description = "Cron expression used by the scheduler", type = AttributeType.STRING)
    String cronExpression() default "0 0/30 * 1/1 * ? *";

    @AttributeDefinition(name = "Asset Path", description = "Available paths to traverse")
    String[] srcAssetPathListInDAM() default {"/content/dam/bdb/temp-assets/images", "/content/dam/bdb/temp-assets/images/packaging-images/clinical-vial-images"};
}