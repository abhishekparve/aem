package com.bdb.aem.core.schedulers;

import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.config.ReportsSchedulerConfiguration;
import com.bdb.aem.core.services.ReportGenerationService;

/**
 * A simple demo for cron-job like tasks that get executed regularly. It also
 * demonstrates how property values can be set. Users can set the property
 * values in /system/console/configMgr
 */
@Designate(ocd = ReportsSchedulerConfiguration.class)
@Component(immediate = true, service = ReportsScheduler.class)
public class ReportsScheduler implements Runnable {

	/** Default log. */
	private static final Logger LOG = LoggerFactory.getLogger(ReportsScheduler.class);

	// Id of the scheduler based on its name
	private int schedulerId;

	//Scheduler instance injected
	@Reference
	private Scheduler scheduler;

	@Reference
	private ReportGenerationService reportGenerationService;

	@Activate
	protected void activate(ReportsSchedulerConfiguration config) {
		// Getting the scheduler id
		schedulerId = config.schdulerName().hashCode();
	}

	/**
	 * Modifies the scheduler id on modification
	 * @param config
	 */
	@Modified
	protected void modified(ReportsSchedulerConfiguration config) {
		//Removing the scheduler
		removeScheduler();
		//Updating the scheduler id
		schedulerId = config.schdulerName().hashCode();
		//Again adding the scheduler
		addScheduler(config);
	}

	/**
	 * This method removes the scheduler
	 */
	private void removeScheduler() {
		LOG.debug("Removing scheduler: {}", schedulerId);
		 //Unscheduling/removing the scheduler
		scheduler.unschedule(String.valueOf(schedulerId));
	}

	/**
	 * This method adds the scheduler
	 * @param config
	 */
	private void addScheduler(ReportsSchedulerConfiguration config) {
		LOG.debug("ReportsScheduler::addScheduler added");
		 //Check if the scheduler is enabled
		if (config.enabled()) {
			//Scheduler option takes the cron expression as a parameter and run accordingly
			ScheduleOptions scheduleOptions = scheduler.EXPR(config.cronExpression());
			//Adding some parameters
			scheduleOptions.name(config.schdulerName());
			scheduleOptions.canRunConcurrently(false);
			//Scheduling the job
			scheduler.schedule(this, scheduleOptions);
			LOG.debug("ReportsScheduler::addScheduler added");
		} else {
			LOG.debug("ReportsScheduler::addScheduler is disabled");
		}
	}

	public void run() {
		LOG.debug("Entering ReportsScheduler run");
		reportGenerationService.fetchReportData();
		LOG.debug("Exiting ReportsScheduler run");

	}

}
