package com.bdb.aem.core.workflows;

import static org.mockito.Mockito.when;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
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
import com.bdb.aem.core.util.WorkflowConstants;
import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * Junit for WorkFlowParticipantChooserTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class WorkFlowParticipantChooserTest {

	/** The work flow participant chooser. */
	@InjectMocks
	WorkFlowParticipantChooser workFlowParticipantChooser;

	/** The resource. */
	@Mock
	Resource resource;

	/** The page. */
	@Mock
	Page page;

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The resource resolver. */
	@Mock
	private ResourceResolver resourceResolver;

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
	 * Gets the participant returning reviewer test.
	 *
	 * @return the participant returning reviewer test
	 * @throws WorkflowException   the workflow exception
	 * @throws LoginException      the login exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void getParticipantReturningReviewerTest() throws WorkflowException, LoginException, RepositoryException {
		when(workItem.getWorkflowData()).thenReturn(WorkFlowData);
		when(WorkFlowData.getPayload()).thenReturn(object);
		when(object.toString()).thenReturn(assetPath);
		when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		when(resourceResolver.getResource(assetPath)).thenReturn(resource);
		when(resource.adaptTo(Page.class)).thenReturn(page);
		when(args.get(WorkflowConstants.PROCESS_ARGS, String.class)).thenReturn("xyz#reviewer");
		when(resourceResolver.isLive()).thenReturn(true);
		workFlowParticipantChooser.getParticipant(workItem, workflowSession, args);
	}

	/**
	 * Gets the participant returning author test.
	 *
	 * @return the participant returning author test
	 * @throws WorkflowException   the workflow exception
	 * @throws LoginException      the login exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void getParticipantReturningAuthorTest() throws WorkflowException, LoginException, RepositoryException {
		when(workItem.getWorkflowData()).thenReturn(WorkFlowData);
		when(WorkFlowData.getPayload()).thenReturn(object);
		when(object.toString()).thenReturn(assetPath);
		when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		when(resourceResolver.getResource(assetPath)).thenReturn(resource);
		when(resource.adaptTo(Page.class)).thenReturn(page);
		when(args.get(WorkflowConstants.PROCESS_ARGS, String.class)).thenReturn("xyz#author");
		when(resourceResolver.isLive()).thenReturn(true);
		workFlowParticipantChooser.getParticipant(workItem, workflowSession, args);
	}

	/**
	 * Gets the participant returning legal test.
	 *
	 * @return the participant returning legal test
	 * @throws WorkflowException   the workflow exception
	 * @throws LoginException      the login exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void getParticipantReturningLegalTest() throws WorkflowException, LoginException, RepositoryException {
		when(workItem.getWorkflowData()).thenReturn(WorkFlowData);
		when(WorkFlowData.getPayload()).thenReturn(object);
		when(object.toString()).thenReturn(assetPath);
		when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		when(resourceResolver.getResource(assetPath)).thenReturn(resource);
		when(resource.adaptTo(Page.class)).thenReturn(page);
		when(args.get(WorkflowConstants.PROCESS_ARGS, String.class)).thenReturn("xyz#legal");
		when(resourceResolver.isLive()).thenReturn(true);
		workFlowParticipantChooser.getParticipant(workItem, workflowSession, args);
	}

	/**
	 * Gets the participant returning publisher test.
	 *
	 * @return the participant returning publisher test
	 * @throws WorkflowException   the workflow exception
	 * @throws LoginException      the login exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void getParticipantReturningPublisherTest() throws WorkflowException, LoginException, RepositoryException {
		when(workItem.getWorkflowData()).thenReturn(WorkFlowData);
		when(WorkFlowData.getPayload()).thenReturn(object);
		when(object.toString()).thenReturn(assetPath);
		when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		when(resourceResolver.getResource(assetPath)).thenReturn(resource);
		when(resource.adaptTo(Page.class)).thenReturn(page);
		when(args.get(WorkflowConstants.PROCESS_ARGS, String.class)).thenReturn("xyz#publisher");
		when(resourceResolver.isLive()).thenReturn(true);
		workFlowParticipantChooser.getParticipant(workItem, workflowSession, args);
	}

	/**
	 * Gets the participant null page test.
	 *
	 * @return the participant null page test
	 * @throws WorkflowException   the workflow exception
	 * @throws LoginException      the login exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void getParticipantNullPageTest() throws WorkflowException, LoginException, RepositoryException {
		when(workItem.getWorkflowData()).thenReturn(WorkFlowData);
		when(WorkFlowData.getPayload()).thenReturn(object);
		when(object.toString()).thenReturn(assetPath);
		when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		when(resourceResolver.getResource(assetPath)).thenReturn(resource);
		when(args.get(WorkflowConstants.PROCESS_ARGS, String.class)).thenReturn("xyz#reviewer");
		workFlowParticipantChooser.getParticipant(workItem, workflowSession, args);
	}

	/**
	 * Gets the participant null resource test.
	 *
	 * @return the participant null resource test
	 * @throws WorkflowException   the workflow exception
	 * @throws LoginException      the login exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void getParticipantNullResourceTest() throws WorkflowException, LoginException, RepositoryException {
		when(workItem.getWorkflowData()).thenReturn(WorkFlowData);
		when(WorkFlowData.getPayload()).thenReturn(object);
		when(object.toString()).thenReturn(assetPath);
		when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		when(args.get(WorkflowConstants.PROCESS_ARGS, String.class)).thenReturn("xyz#reviewer");
		workFlowParticipantChooser.getParticipant(workItem, workflowSession, args);
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
		when(workItem.getWorkflowData()).thenReturn(WorkFlowData);
		when(WorkFlowData.getPayload()).thenReturn(object);
		when(object.toString()).thenReturn(assetPath);
		when(args.get(WorkflowConstants.PROCESS_ARGS, String.class)).thenReturn("xyz#reviewer");
		when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenThrow(new LoginException());
		workFlowParticipantChooser.getParticipant(workItem, workflowSession, args);
	}
}