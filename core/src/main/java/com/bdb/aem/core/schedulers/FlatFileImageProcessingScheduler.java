package com.bdb.aem.core.schedulers;

import com.bdb.aem.core.config.ProductImageProcessingSchedulerConfiguration;
import com.bdb.aem.core.services.ProductImageProcessingService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;
import com.day.crx.JcrConstants;
import org.apache.sling.api.resource.*;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import java.util.Iterator;

/**
 * Product Image Processing Scheduler
 * The scheduler will process all the records(Images/assets)  configured under /var/bdb/products/images/unprocessed-records
 * till the retryLimit reached the defaultRetryLimit.
 * It should apply the same post-processing Image workflow on all the failed assets
 */

@Component(immediate = true, service = Runnable.class)
@Designate(ocd = ProductImageProcessingSchedulerConfiguration.class)
public class FlatFileImageProcessingScheduler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(FlatFileImageProcessingScheduler.class);

    private int schedulerID;

    @Reference
    private ProductImageProcessingService productImageProcessingService;

    @Reference
    private Scheduler scheduler;

    @Reference
    ResourceResolverFactory resolverFactory;

    private String isEnabled;

    private String assetsPathListInDAM;
    
    private String assetsReferencePathListInVar;

    private long retryLimit;
   
    private String failedRecordsPathInVar;

    @Activate
    public void activate(ProductImageProcessingSchedulerConfiguration config) {
        logger.info("==== Flat FIle Image Processing Scheduler Activated ==== ");
        this.schedulerID = config.schedulerName().hashCode();
        this.retryLimit = Long.parseLong(config.defaultRetryLimit());
        this.isEnabled = config.enabled();
        this.assetsPathListInDAM = config.assetsPathListInDAM();
        this.assetsReferencePathListInVar = config.assetsReferencePathListInVar();
        this.failedRecordsPathInVar = config.failedRecordsRepPathListInVar();
        addScheduler(config);
    }

    @Deactivate
    public void deactivate() {
        logger.info("==== Product Image Processing Scheduler Deactivated ==== ");
        removeScheduler();
    }

    private void removeScheduler() {
        logger.info("Remove the scheduling Job '{}'", schedulerID);
        scheduler.unschedule(String.valueOf(schedulerID));
    }

    /**
     * 
     * @param config
     */
    private void addScheduler(ProductImageProcessingSchedulerConfiguration config) {
        ScheduleOptions scheduleOptions = scheduler.EXPR(config.flatFileImageCronExpression());
        scheduleOptions.name(config.schedulerName());
        scheduleOptions.canRunConcurrently(false);
        scheduler.schedule(this, scheduleOptions);
        logger.info("Adding the Job '{}'", schedulerID);
        logger.debug("== Workflow Name : {}", config.schedulerName());
        logger.debug("== Workflow Enabled : {}", config.enabled());
        logger.debug("== Workflow Retry Limit : {}", config.defaultRetryLimit());
        logger.debug("== Workflow Expression : {}", config.flatFileImageCronExpression());
        logger.debug("== Product Images uploading path : {}", config.assetsPathListInDAM());
        logger.debug("== Product Images reference path under var : {}", config.assetsReferencePathListInVar());
    }

    @Modified
    protected void modified(ProductImageProcessingSchedulerConfiguration config) {
        // Removing the Scheduler
        removeScheduler();
        logger.info("Product Image Processing Scheduler Configuration Modified");
        schedulerID = config.schedulerName().hashCode();
        // Adding Scheduler
        addScheduler(config);
    }

    @Override
    public void run() {
        logger.info("==== Flat FIle Image Processing Scheduler Running ==== ");
        
        ResourceResolver resourceResolver = null;
        Session session = null;
        try {
			resourceResolver = CommonHelper.getServiceResolver(resolverFactory);
			session = resourceResolver.adaptTo(Session.class);
	        if (isEnabled.equalsIgnoreCase(CommonConstants.FALSE)) {
	            logger.info("Flat File Image Processing Job is not activated, job is disabled");
	            return;
	        }
	        	
	    	if (null != assetsPathListInDAM && null != assetsReferencePathListInVar) {
	            productImageProcessingService.processProductImages(resourceResolver, session, assetsPathListInDAM, assetsReferencePathListInVar, failedRecordsPathInVar, retryLimit);
	    	}
		} catch (LoginException e) {
			logger.error("Exception occured {}", e);
		}
    }
}