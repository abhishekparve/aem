package com.bdb.aem.core.services.workflows;

import static org.mockito.Mockito.when;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.WorkflowHelperService;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * Junit for ProcessProductDocumentTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ProcessProductDocumentTest {

	/** The process product document. */
	@InjectMocks
	ProcessProductDocument processProductDocument;

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The workflow helper service. */
	@Mock
	private WorkflowHelperService workflowHelperService;
	
	/** The b DB workflow config service. */
	@Mock
	BDBWorkflowConfigService bDBWorkflowConfigService;

	/** The resource resolver. */
	@Mock
	private ResourceResolver resourceResolver;

	/** The session. */
	@Mock
	private Session session;

	/** The work item. */
	@Mock
	private WorkItem workItem;

	/** The Work flow data. */
	@Mock
	private WorkflowData WorkFlowData;

	/** The object. */
	@Mock
	private Object object;

	/** The workflow session. */
	@Mock
	private WorkflowSession workflowSession;

	/** The args. */
	@Mock
	private MetaDataMap args;

	/** The Constant assetPath. */
	private static final String assetPath = "/content/dam/pankaj.pdf";

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */

	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Execute test.
	 *
	 * @throws WorkflowException   the workflow exception
	 * @throws LoginException      the login exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void executeTest() throws WorkflowException, LoginException, RepositoryException {
		when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(workItem.getWorkflowData()).thenReturn(WorkFlowData);
		when(WorkFlowData.getPayload()).thenReturn(object);
		when(object.toString()).thenReturn(assetPath);
		when(bDBWorkflowConfigService.getDocBasePath()).thenReturn("");
		when(workflowHelperService.canProcessPath(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		processProductDocument.execute(workItem, workflowSession, args);
	}

	/**
	 * Execute test else.
	 *
	 * @throws WorkflowException   the workflow exception
	 * @throws LoginException      the login exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void executeTestElse() throws WorkflowException, LoginException, RepositoryException {
		when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenThrow(new LoginException());
		processProductDocument.execute(workItem, workflowSession, args);
	}

	/**
	 * Execute session null test.
	 *
	 * @throws WorkflowException   the workflow exception
	 * @throws LoginException      the login exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void executeSessionNullTest() throws WorkflowException, LoginException, RepositoryException {
		when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(null);
		processProductDocument.execute(workItem, workflowSession, args);
	}

	/**
	 * Execute empty payload test.
	 *
	 * @throws WorkflowException   the workflow exception
	 * @throws LoginException      the login exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void executeEmptyPayloadTest() throws WorkflowException, LoginException, RepositoryException {
		when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(workItem.getWorkflowData()).thenReturn(WorkFlowData);
		when(WorkFlowData.getPayload()).thenReturn(object);
		when(object.toString()).thenReturn("");
		processProductDocument.execute(workItem, workflowSession, args);
	}

	/**
	 * Execute can process false test.
	 *
	 * @throws WorkflowException   the workflow exception
	 * @throws LoginException      the login exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void executeCanProcessFalseTest() throws WorkflowException, LoginException, RepositoryException {
		when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(workItem.getWorkflowData()).thenReturn(WorkFlowData);
		when(WorkFlowData.getPayload()).thenReturn(object);
		when(object.toString()).thenReturn(assetPath);
		when(bDBWorkflowConfigService.getDocBasePath()).thenReturn("");
		when(workflowHelperService.canProcessPath(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
		processProductDocument.execute(workItem, workflowSession, args);
	}
}