package com.bdb.aem.core.workflows;

import java.security.AccessControlException;
import java.util.List;
import java.util.ArrayList;

import javax.jcr.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.ProductImgMigrationService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.jcr.JcrConstants;

/**
 * The Class ProductImgMigration to migrate the assets from temp/images folder
 * to respective product folder with updated metadata.
 */

@Component(property = {
		Constants.SERVICE_DESCRIPTION + "=Used to move the images from temp folder to respective product folder",
		"process.label" + "=BDB Product Image Migration" })
public class ProductImgMigrationWorkflow implements WorkflowProcess {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImgMigrationWorkflow.class);

	/** The product img migration service. */
	@Reference
	ProductImgMigrationService productImgMigrationService;

	/** The bdb workflow config service. */
	@Reference
	BDBWorkflowConfigService bdbWorkflowConfigService;

	/** The workflow helper service. */
	@Reference
	WorkflowHelperService workflowHelperService;

	/** The job manager. */
	@Reference
	private JobManager jobManager;

	/** Resource Resolver Factory. */
	@Reference
	ResourceResolverFactory resolverFactory;

	/**
	 * Execute.
	 *
	 * @param workItem        the work item
	 * @param workflowSession the workflow session
	 * @param args            the args
	 * @throws WorkflowException the workflow exception
	 */
	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args) throws WorkflowException {
		LOGGER.debug("Entry Execute method of ProductImgMigrationWorkflow");
		ResourceResolver serviceResolver = null;
		Session session = null;
		List<String> listOfAssets = new ArrayList<String>();
		try {
			serviceResolver = CommonHelper.getServiceResolver(resolverFactory);
			LOGGER.debug("Service Resolver created");
			session = serviceResolver.adaptTo(Session.class);
			if (null != session) {
				LOGGER.debug("Session created");
				String payload = workItem.getWorkflowData().getPayload().toString();
				LOGGER.debug("Payload {}", payload);
				if (StringUtils.contains(payload, JcrConstants.JCR_CONTENT)) {
					payload = payload.substring(0, payload.lastIndexOf(CommonConstants.SINGLE_SLASH));
				}
				if (StringUtils.isNotBlank(payload)
						&& (workflowHelperService.canProcessPath(payload, bdbWorkflowConfigService.getImageBasePath())
								|| workflowHelperService.canProcessPath(payload,
										bdbWorkflowConfigService.getProcessedImageBasePath()))) {
					listOfAssets = workflowHelperService.getListOfImageAssets(payload, serviceResolver,
							session);
					
				} else if (StringUtils.isNotBlank(payload) && (workflowHelperService.canProcessPath(payload, bdbWorkflowConfigService.getProductImageBasePath()))) {
					listOfAssets = workflowHelperService.getListOfImageAssets(payload, serviceResolver,
							session);
				}
				workflowHelperService.assignJobInBatches(listOfAssets, jobManager, "image-Workflow");
			}
		} catch (LoginException | AccessControlException e) {
			LOGGER.error("Exception occurred during processing : {0}", e);
		} finally {
			CommonHelper.closeResourceResolver(serviceResolver);
			CommonHelper.closeSession(session);
		}
		LOGGER.debug("Exit Execute method of ProductImgMigrationWorkflow");
	}
}