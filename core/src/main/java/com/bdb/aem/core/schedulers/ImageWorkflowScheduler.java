package com.bdb.aem.core.schedulers;

import com.bdb.aem.core.config.ImageWorkflowSchedulerConfiguration;
import com.bdb.aem.core.services.ProductImgMigrationService;
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
 * Image Workflow Scheduler
 * The scheduler will reprocess all stuck Images assets at configured time intervals fails due to unfound product data
 * till the retryLimit reached the defaultRetryLimit.
 * It should apply the same post-processing Image workflow on all the failed assets
 */

@Component(immediate = true, service = Runnable.class)
@Designate(ocd = ImageWorkflowSchedulerConfiguration.class)
public class ImageWorkflowScheduler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ImageWorkflowScheduler.class);

    private int schedulerID;

    @Reference
    private ProductImgMigrationService productImgMigrationService;

    @Reference
    private Scheduler scheduler;

    @Reference
    ResourceResolverFactory resolverFactory;

    private String isEnabled;

    private String[] srcAssetPathListInDAM;

    private long retryLimit;

    @Activate
    public void activate(ImageWorkflowSchedulerConfiguration config) {
        logger.info("==== Image Workflow Scheduler Activated ==== ");
        this.schedulerID = config.schedulerName().hashCode();
        this.retryLimit = Long.parseLong(config.defaultRetryLimit());
        this.isEnabled = config.enabled();
        this.srcAssetPathListInDAM = config.srcAssetPathListInDAM();
        addScheduler(config);
    }

    @Deactivate
    public void deactivate() {
        logger.info("==== Image Workflow Scheduler Deactivated ==== ");
        removeScheduler();
    }

    private void removeScheduler() {
        logger.info("Remove the scheduling Job '{}'", schedulerID);
        scheduler.unschedule(String.valueOf(schedulerID));
    }

    private void addScheduler(ImageWorkflowSchedulerConfiguration config) {
        ScheduleOptions scheduleOptions = scheduler.EXPR(config.cronExpression());
        scheduleOptions.name(config.schedulerName());
        scheduleOptions.canRunConcurrently(false);
        scheduler.schedule(this, scheduleOptions);
        logger.info("Adding the Job '{}'", schedulerID);
        logger.debug("== Workflow Name : {}", config.schedulerName());
        logger.debug("== Workflow Enabled : {}", config.enabled());
        logger.debug("== Workflow Retry Limit : {}", config.defaultRetryLimit());
        logger.debug("== Workflow Expression : {}", config.cronExpression());
        logger.debug("== Workflow Paths : {}", config.srcAssetPathListInDAM());
    }

    @Modified
    protected void modified(ImageWorkflowSchedulerConfiguration config) {
        // Removing the Scheduler
        removeScheduler();
        logger.info("Image Workflow Scheduler Configuration Modified");
        schedulerID = config.schedulerName().hashCode();
        // Adding Scheduler
        addScheduler(config);
    }

    @Override
    public void run() {
        logger.info("==== Image Workflow Scheduler Running ==== ");
        ResourceResolver resourceResolver = null;
        Session session = null;
        // Scheduler enable check. Job will only trigger when isEnabled is true
        if (isEnabled.equalsIgnoreCase(CommonConstants.FALSE)) {
            logger.info("Image Workflow Job is not activated, job is disabled");
            return;
        }
        try {
            resourceResolver = CommonHelper.getServiceResolver(resolverFactory);
            session = resourceResolver.adaptTo(Session.class);
            // Traversing all the asset paths config via Image workflow scheduler configuration
            for (String assetPath : srcAssetPathListInDAM) {
                Resource res = resourceResolver.getResource(assetPath);
                if (res != null) {
                    Iterator<Resource> nodes = res.listChildren();
                    while (nodes.hasNext()) {
                        Resource assetNode = nodes.next();
                        if (assetNode.getChild(JcrConstants.JCR_CONTENT) != null) {
                            ValueMap mp = assetNode.getChild(JcrConstants.JCR_CONTENT).adaptTo(ValueMap.class);
                            Asset currentAsset = assetNode.adaptTo(Asset.class);
                            if ((CommonConstants.DAM_ASSET_STATE_PROCESSED).equals(mp.get(CommonConstants.DAM_ASSET_STATE)) &&
                                    currentAsset.getMetadata(CommonConstants.FAILURE) != null && currentAsset.getMetadata(CommonConstants.ATTEMPTS) != null) {
                                boolean isFailureAsset = (boolean) currentAsset.getMetadata(CommonConstants.FAILURE);
                                long totalAttempts = (long) currentAsset.getMetadata(CommonConstants.ATTEMPTS);
                                String path = assetNode.getPath();
                                // Processed only when asset status is failure and total attempts is less than equal to the limit configured via Scheduler configuration.
                                if (isFailureAsset && retryLimit > totalAttempts) {
                                    logger.info("Image workflow is triggered for '{}'", path);
                                    // Calling the workflow service method
                                    productImgMigrationService.processProductImage(path, resourceResolver, session);
                                } else {
                                    logger.error("faulty product/Image '{}'", path);
                                }
                            }
                        }
                    }
                }
            }
        } catch (RepositoryException | LoginException e) {
            logger.error("Error occurred in triggering Image Workflow from scheduler", e);
        } finally {
            if (session != null && session.isLive()) {
                session.logout();
            }
            if (resourceResolver != null && resourceResolver.isLive()) {
                resourceResolver.close();
            }
        }
    }
}