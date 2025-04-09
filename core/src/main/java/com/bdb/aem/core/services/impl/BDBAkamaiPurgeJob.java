package com.bdb.aem.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.AkamaiPurgeService;
import com.bdb.aem.core.util.CommonConstants;

/**
 * The Class BDBAkamaiPurgeJob.
 */
@Component(service = JobConsumer.class, property = {
		JobConsumer.PROPERTY_TOPICS + "=" + BDBAkamaiPurgeJob.TOPIC }, immediate = true)
public class BDBAkamaiPurgeJob implements JobConsumer {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BDBAkamaiPurgeJob.class);

	/** The Constant TOPIC. */
	public static final String TOPIC = "bdb/akamaiPurge";

	@Reference
	AkamaiPurgeService akamaiPurgeService;

	/** The resource resolver factory. */
	@Reference
	ResourceResolverFactory resourceResolverFactory;

	/**
	 * Activate.
	 */
	@Activate
	@Modified
	protected final void activate() {
		LOGGER.debug("Activate of BDBAkamaiPurgeJob");
	}

	/**
	 * Process.
	 *
	 * @param job the job
	 * @return the job consumer. job result
	 */
	@Override
	public JobConsumer.JobResult process(Job job) {
		LOGGER.debug("Entry process of BDBAkamaiPurgeJob");
		List<String> list = requireParam(job, CommonConstants.PAYLOAD_PATHS, ArrayList.class);
		LOGGER.debug("BDBAkamaiPurgeJob List of assets - {}", list);
		// String assetType = requireParam(job, ASSET_TYPE, String.class);
		long startTime = System.currentTimeMillis();
		LOGGER.debug("BDBAkamaiPurgeJob Start time - {}", startTime);
		akamaiPurgeService.purgeAssets(list);

		long endTime = System.currentTimeMillis();
		LOGGER.debug("BDBAkamaiPurgeJob End time - {}", endTime);
		LOGGER.debug("Total time taken for BDBAkamaiPurgeJob - {}", endTime - startTime);
		LOGGER.debug("Exit process of BDBAkamaiPurgeJob");
		return JobResult.OK;
	}

	/**
	 * Require param.
	 *
	 * @param <T>  the generic type
	 * @param job  the job
	 * @param key  the key
	 * @param type the type
	 * @return the t
	 */
	private <T> T requireParam(Job job, String key, Class<T> type) {
		return Objects.requireNonNull(job.getProperty(key, type), "No " + key + " parameter provided");
	}
}