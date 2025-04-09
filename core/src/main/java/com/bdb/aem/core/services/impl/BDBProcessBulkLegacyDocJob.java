package com.bdb.aem.core.services.impl;

import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ProcessProductDocumentService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.replication.ReplicationException;
import com.day.cq.tagging.InvalidTagFormatException;

/**
 * The Class BDBProcessBulkLegacyDocJob.
 */
@Component(service = JobConsumer.class, property = {
		JobConsumer.PROPERTY_TOPICS + "=legacy-Doc-Workflow" }, immediate = true)
public class BDBProcessBulkLegacyDocJob implements JobConsumer {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BDBProcessBulkLegacyDocJob.class);

	/** The resource resolver factory. */
	@Reference
	ResourceResolverFactory resourceResolverFactory;

	/** The process product document service. */
	@Reference
	ProcessProductDocumentService processProductDocumentService;

	/**
	 * Activate.
	 */
	@Activate
	@Modified
	protected final void activate() {
		LOGGER.debug("Activate of BDBProcessBulkLegacyDocJob");
	}

	/**
	 * Process.
	 *
	 * @param job the job
	 * @return the job consumer. job result
	 */
	@Override
	public JobConsumer.JobResult process(Job job) {
		LOGGER.debug("Entry process of BDBProcessBulkLegacyDocJob");
		List<String> list = (List<String>) job.getProperty(CommonConstants.PAYLOAD_PATHS);
		long startTime = System.currentTimeMillis();
		LOGGER.debug("BDBProcessBulkLegacyDocJob Start time - {}" ,startTime);
		processListAssets(list);
		long endTime = System.currentTimeMillis();
		LOGGER.debug("BDBProcessBulkLegacyDocJob End time - {}" ,endTime);
		LOGGER.debug("Total time taken for BDBProcessBulkLegacyDocJob - {}" ,endTime-startTime);
		LOGGER.debug("Exit process of BDBProcessBulkLegacyDocJob");
		return JobResult.OK;
	}

	/**
	 * Process list assets.
	 *
	 * @param list the list
	 */
	public void processListAssets(List<String> list) {
		LOGGER.debug("Entry processListAssets of BDBProcessBulkLegacyDocJob");
		ResourceResolver serviceResolver = null;
		Session session = null;
		try {
			serviceResolver = CommonHelper.getServiceResolver(resourceResolverFactory);
			LOGGER.debug("Service Resolver created in job");
			session = serviceResolver.adaptTo(Session.class);
			LOGGER.debug("Session created in job");
			if (null != session) {
				startProcessing(list, serviceResolver, session);
			}
		} catch (LoginException e) {
			LOGGER.error("Error in processing job : {0}", e);
		} finally {
			CommonHelper.closeResourceResolver(serviceResolver);
			CommonHelper.closeSession(session);
		}
		LOGGER.debug("Exit processListAssets of BDBProcessBulkLegacyDocJob");
	}

	/**
	 * Start processing.
	 *
	 * @param list            the list
	 * @param serviceResolver the service resolver
	 * @param session         the session
	 */
	private void startProcessing(List<String> list, ResourceResolver serviceResolver, Session session) {
		LOGGER.debug("Entry startProcessing of BDBProcessBulkLegacyDocJob");
		for (String assetPath : list) {
			try {
				LOGGER.debug("Asset sent {}", assetPath);
				processProductDocumentService.processDocuments(assetPath, serviceResolver, session);
			} catch (ReplicationException | RepositoryException | InvalidTagFormatException e) {
				LOGGER.error("Error in processing job for asset : {}", assetPath);
				LOGGER.error("Error : {0}", e);
			}
		}
		LOGGER.debug("Exit startProcessing of BDBProcessBulkLegacyDocJob");
	}
}