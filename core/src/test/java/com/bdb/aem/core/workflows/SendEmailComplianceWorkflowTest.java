package com.bdb.aem.core.workflows;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.HistoryItem;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.Workflow;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.SendEmailService;
import com.bdb.aem.core.services.solr.SolrSearchService;

/**
 * The Class SendEmailComplianceWorkflowTest.
 */
@ExtendWith({ MockitoExtension.class })
class SendEmailComplianceWorkflowTest {

	/** The send email compliance workflow. */
	@InjectMocks
	SendEmailComplianceWorkflow sendEmailComplianceWorkflow;

	/** The ResourceResolverFactory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The SolrSearchService. */
	@Mock
	SolrSearchService solrSearchService;

	/** The BDBSearchEndpointService. */
	@Mock
	BDBSearchEndpointService solrConfig;

	/** The send email service. */
	@Mock
	SendEmailService sendEmailService;

	/** The work item. */
	WorkItem workItem;

	/** The WorkflowSession. */
	@Mock
	WorkflowSession workflowSession;

	/** The MetaDataMap. */
	@Mock
	MetaDataMap args;

	/** The ResourceResolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The workflowData. */
	@Mock
	WorkflowData workflowData;

	/** The history item. */
	@Mock
	HistoryItem historyItem;

	/** The payload. */
	@Mock
	Object payload;

	/** The user manager. */
	@Mock
	UserManager userManager;

	/** The authorizable. */
	@Mock
	Authorizable authorizable;

	/** The resource. */
	@Mock
	Resource resource;

	/**
	 * Test execute.
	 *
	 * @throws WorkflowException   the workflow exception
	 * @throws LoginException      the login exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void testExecute() throws WorkflowException, LoginException, RepositoryException {
		Workflow wf = Mockito.mock(Workflow.class);
		workItem = Mockito.mock(WorkItem.class);
		List<HistoryItem> wfHistory = new ArrayList<>();
		wfHistory.add(historyItem);
		Map<String, Object> writeServiceAuth = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE,
				"writeService");

		when(workItem.getWorkflow()).thenReturn(wf);
		when(workflowSession.getHistory(wf)).thenReturn(wfHistory);
		when(workItem.getWorkflowData()).thenReturn(workflowData);
		when(workflowData.getPayload()).thenReturn(payload);
		when(payload.toString()).thenReturn("content/bdb/ContnentPath");
		when(resourceResolverFactory.getServiceResourceResolver(writeServiceAuth)).thenReturn(resourceResolver);
//		when(resourceResolver.adaptTo(UserManager.class)).thenReturn(userManager);
//		when(userManager.getAuthorizable("admin")).thenReturn(authorizable);
		when(resourceResolver.getResource("content/bdb/ContnentPath")).thenReturn(resource);

		sendEmailComplianceWorkflow.execute(workItem, workflowSession, args);
	}

	/**
	 * Test execute with exception.
	 *
	 * @throws WorkflowException the workflow exception
	 */
	@Test
	void testExecuteWithException() throws WorkflowException {
		//sendEmailComplianceWorkflow.execute(workItem, workflowSession, args);
	}

}
