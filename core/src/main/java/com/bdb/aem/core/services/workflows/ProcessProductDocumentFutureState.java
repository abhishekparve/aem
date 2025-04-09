package com.bdb.aem.core.services.workflows;

import java.security.AccessControlException;
import java.util.List;

import javax.jcr.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.JobManager;
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
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.jcr.JcrConstants;

/**
 * The Class ProcessProductDocumentFutureState.
 */
@Component(immediate = true, service = WorkflowProcess.class, property = {
		"process.label = Product documents processing workflow Future State" })
public class ProcessProductDocumentFutureState implements WorkflowProcess {

	/** The Constant LOG. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessProductDocumentFutureState.class);

	/** The resource resolver factory. */
	@Reference
	ResourceResolverFactory resourceResolverFactory;
	
	/** The workflow helper service. */
	@Reference
	WorkflowHelperService workflowHelperService;
	
	@Reference
	BDBWorkflowConfigService bDBWorkflowConfigService;;
	
	/** The job manager. */
	@Reference
	private JobManager jobManager;

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
		LOGGER.debug("Entry Execute method of ProcessProductDocumentFutureState");
		ResourceResolver serviceResolver = null;
		Session session = null;
		try {
			serviceResolver = CommonHelper.getServiceResolver(resourceResolverFactory);
			LOGGER.debug("Service Resolver created");
			session = serviceResolver.adaptTo(Session.class);
			if (null != session) {
				LOGGER.debug("Session created");
				String payload = workItem.getWorkflowData().getPayload().toString();
				LOGGER.debug("Payload {}", payload);
				if (StringUtils.contains(payload, JcrConstants.JCR_CONTENT)) {
					payload = payload.substring(0, payload.lastIndexOf(CommonConstants.SINGLE_SLASH));
				}
				if (StringUtils.isNotBlank(payload) && (workflowHelperService.canProcessPath(payload, bDBWorkflowConfigService.getDocBasePath())
						|| workflowHelperService.canProcessPath(payload, bDBWorkflowConfigService.getProcessedDocBasePath()))) {
					List<String> listOfAssets = workflowHelperService.getListOfPDFAssets(payload, serviceResolver, session);
					workflowHelperService.assignJobInBatches(listOfAssets, jobManager, "futureState-Doc-Workflow");
				}
			}
		} catch (LoginException | AccessControlException  e) {
			LOGGER.error("Exception occurred during processing : {0}", e);
		} finally {
			CommonHelper.closeResourceResolver(serviceResolver);
			CommonHelper.closeSession(session);
		}
		LOGGER.debug("Exit Execute method of ProcessProductDocumentFutureState");
	}
}