package com.bdb.aem.core.schedulers;

import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.config.FailedImageReportGenerationConfig;
import com.bdb.aem.core.services.GenerateFailedImageReportService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;

/**
 * Generate Failed Image Report Scheduler
 * The scheduler generate a failed records(Images/assets) report configured under /var/bdb/products/images/failed-records
*/

@Component(immediate = true, service = Runnable.class)
@Designate(ocd = FailedImageReportGenerationConfig.class)
public class GenerateFailedImageReportScheduler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(GenerateFailedImageReportScheduler.class);

    private int schedulerID;

    @Reference
    private GenerateFailedImageReportService generateFailedImageReportService;

    @Reference
    private Scheduler scheduler;

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    private String isEnabled;
   
    private String failedRecordsPath;
    
    private String failedImagesReportPath;

    @Activate
    public void activate(FailedImageReportGenerationConfig config) {
        logger.info("==== Generate Failed Image Report Scheduler Activated ==== ");
        this.schedulerID = config.schedulerName().hashCode();
        this.failedRecordsPath = config.failedRecordsInVar();
        this.isEnabled = config.enabled();
        this.failedImagesReportPath = config.failedReportDestinationPath();
        addScheduler(config);
    }

    @Deactivate
    public void deactivate() {
        logger.info("==== Generate Failed Image Report Scheduler Deactivated ==== ");
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
    private void addScheduler(FailedImageReportGenerationConfig config) {
        ScheduleOptions scheduleOptions = scheduler.EXPR(config.failedImagesCronExpression());
        scheduleOptions.name(config.schedulerName());
        scheduleOptions.canRunConcurrently(false);
        scheduler.schedule(this, scheduleOptions);
        logger.info("Adding the Job '{}'", schedulerID);
        logger.debug("== Scheduler Name : {}", config.schedulerName());
        logger.debug("== Failed Product Image Records under var : {}", config.failedRecordsInVar());
    }

    @Modified
    protected void modified(FailedImageReportGenerationConfig config) {
        // Removing the Scheduler
        removeScheduler();
        logger.info("Generate Failed Image Report Scheduler Configuration Modified");
        schedulerID = config.schedulerName().hashCode();
        // Adding Scheduler
        addScheduler(config);
    }

    @Override
    public void run() {
        logger.info("==== Generate Failed Image Report Scheduler Running ==== ");
        
        ResourceResolver serviceResolver = null;
        Session session = null;
        try {
        	serviceResolver = CommonHelper.getServiceResolver(resourceResolverFactory);
            Resource resource = serviceResolver.getResource(failedRecordsPath);
            session = serviceResolver.adaptTo(Session.class);
            
            if (isEnabled.equalsIgnoreCase(CommonConstants.FALSE)) {
                logger.info("Failed Images Report Genaration Job is not activated, job is disabled");
                return;
            }
            	
        	if (null != failedRecordsPath) {
        		generateFailedImageReportService.generateFailedImageReport(serviceResolver, resource, session, failedImagesReportPath);
        	}
        } catch (LoginException e) {
        	logger.error("Exception occurred while getting resource resolver {0}", e);
        } finally {
            if (null != session && session.isLive()) {
                session.logout();
            }
            if (null != serviceResolver) {
                CommonHelper.closeResourceResolver(serviceResolver);
            }
        }
        
    }
}