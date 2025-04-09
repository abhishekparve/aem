package com.bdb.aem.core.services.impl;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.granite.workflow.WorkflowException;
import com.bdb.aem.core.services.ProcessProductDocumentFutureStateService;
import com.day.cq.replication.ReplicationException;
import com.day.cq.tagging.InvalidTagFormatException;

/**
 * The Class BDBProcessBulkFutureStateDocJobTest.
 */
@ExtendWith({ MockitoExtension.class })
class BDBProcessBulkFutureStateDocJobTest {

	/** The process product document. */
	@InjectMocks
	BDBProcessBulkFutureStateDocJob bDBProcessBulkFutureStateDocJob;

	/** The process product document service. */
	@Mock
	private ProcessProductDocumentFutureStateService processProductDocumentFutureStateService;

	/** The workflow helper service. */
	@Mock
	private Job job;

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The resource resolver. */
	@Mock
	private ResourceResolver resourceResolver;

	/** The session. */
	@Mock
	private Session session;

	/** The list. */
	private List<String> list = new ArrayList<>();

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */

	@BeforeEach
	void setUp() throws Exception {
		list.add("/content/dam/bdb/temp-assets/550331.pdf");
	}

	/**
	 * Execute test.
	 *
	 * @throws WorkflowException         the workflow exception
	 * @throws LoginException            the login exception
	 * @throws RepositoryException       the repository exception
	 * @throws InvalidTagFormatException the invalid tag format exception
	 * @throws ReplicationException      the replication exception
	 */
	@Test
	void activateTest() throws WorkflowException, LoginException, RepositoryException, InvalidTagFormatException,
			ReplicationException {
		bDBProcessBulkFutureStateDocJob.activate();
	}

	/**
	 * Process test.
	 *
	 * @throws WorkflowException         the workflow exception
	 * @throws LoginException            the login exception
	 * @throws RepositoryException       the repository exception
	 * @throws InvalidTagFormatException the invalid tag format exception
	 * @throws ReplicationException      the replication exception
	 */
	@Test
	void processTest() throws WorkflowException, LoginException, RepositoryException, InvalidTagFormatException,
			ReplicationException {
		when(job.getProperty(Mockito.anyString())).thenReturn(list);
		when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		bDBProcessBulkFutureStateDocJob.process(job);
	}

	/**
	 * Process login exception test.
	 *
	 * @throws WorkflowException         the workflow exception
	 * @throws LoginException            the login exception
	 * @throws RepositoryException       the repository exception
	 * @throws InvalidTagFormatException the invalid tag format exception
	 * @throws ReplicationException      the replication exception
	 */
	@Test
	void processLoginExceptionTest() throws WorkflowException, LoginException, RepositoryException,
			InvalidTagFormatException, ReplicationException {
		when(job.getProperty(Mockito.anyString())).thenReturn(list);
		when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenThrow(new LoginException());
		bDBProcessBulkFutureStateDocJob.process(job);
	}

	/**
	 * Process null session test.
	 *
	 * @throws WorkflowException         the workflow exception
	 * @throws LoginException            the login exception
	 * @throws RepositoryException       the repository exception
	 * @throws InvalidTagFormatException the invalid tag format exception
	 * @throws ReplicationException      the replication exception
	 */
	@Test
	void processNullSessionTest() throws WorkflowException, LoginException, RepositoryException,
			InvalidTagFormatException, ReplicationException {
		when(job.getProperty(Mockito.anyString())).thenReturn(list);
		when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(null);
		bDBProcessBulkFutureStateDocJob.process(job);
	}

	/**
	 * Process exception test.
	 *
	 * @throws WorkflowException         the workflow exception
	 * @throws LoginException            the login exception
	 * @throws RepositoryException       the repository exception
	 * @throws InvalidTagFormatException the invalid tag format exception
	 * @throws ReplicationException      the replication exception
	 */
	@Test
	void processExceptionTest() throws WorkflowException, LoginException, RepositoryException,
			InvalidTagFormatException, ReplicationException {
		when(job.getProperty(Mockito.anyString())).thenReturn(list);
		when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		doThrow(new InvalidTagFormatException("Exception")).when(processProductDocumentFutureStateService)
				.processDocuments("/content/dam/bdb/temp-assets/550331.pdf", resourceResolver, session);
		bDBProcessBulkFutureStateDocJob.process(job);
	}
}
