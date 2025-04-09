package com.bdb.aem.core.services.impl;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.HistoryItem;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.bean.EmailTemplateBean;
import com.bdb.aem.core.bean.TemplateParameterBean;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.WorkflowConstants;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.commons.mail.MailTemplate;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class SendEmailServiceImplTest {
	@InjectMocks
	SendEmailServiceImpl sendEmailServiceImpl;

	/** Mock ResourceResolverFactory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** Mock ResourceResolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** Mock Resource */
	@Mock
	Resource resource, templateRsrc, jcrRes;
	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	@Mock
	Node variantNode;

	CommonHelper commonHelper;

	@Mock
	BDBSearchEndpointService searchConfig;

	@Mock
	Node node;
	@Mock
	SendEmailServiceImpl.Configuration config;
	@Mock
	MessageGatewayService messageGatewayService;
	@Mock
	ExternalizerService externalizerService;
	@Mock
	Map<String, Object> inputParams;
	@Mock
	Session session;
	@Mock
	MailTemplate mailTemplate;
	@Mock
	HtmlEmail email;
	@Mock
	MessageGateway<Email> messageGateway;
	@Mock
	DataSource dataSource;
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

	/** The workflow helper service. */
	@Mock
	private WorkflowHelperService workflowHelperService;
	@Mock
	com.adobe.granite.workflow.exec.Workflow workflow;
	@Mock
	ValueMap varientValueMap;
	@Mock
	TemplateParameterBean templateParameterBean;
	@Mock
	EmailTemplateBean emailTemplateBean;
	@Mock
	Map<String, String> userMap;

	String[] emailTo = { "test@bdb.com" };
	String subject = "subject";
	String path = "/content/bdb";
	@Mock
	Iterator<Map.Entry<String, String>> itr;
	@Mock
	HistoryItem HistoryItem;

	/**
	 * Set up.
	 *
	 * @throws Exception the exception
	 */

	@BeforeEach
	void setUp() throws Exception {
		commonHelper = mock(CommonHelper.class);
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(templateRsrc);
		lenient().when(templateRsrc.getPath()).thenReturn(path);
		lenient().when(templateRsrc.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(mailTemplate.getEmail(StrLookup.mapLookup(inputParams), HtmlEmail.class)).thenReturn(email);
		lenient().when(messageGatewayService.getGateway(Email.class)).thenReturn(messageGateway);
	}

	@Test
	void testCereateProductNodeStructure() throws LoginException, MessagingException, EmailException, IOException {
		sendEmailServiceImpl.sendEmail(null, inputParams, emailTo, subject);
		sendEmailServiceImpl.sendEmailWithAttachment(null, userMap, null, emailTo, subject);
	}

	@Test
	void testfetchWorkflowProcessArgsContent() throws LoginException, RepositoryException, MessagingException,
			EmailException, IOException, WorkflowException {
		lenient().when(resolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		lenient().when(resource.getChild(JcrConstants.JCR_CONTENT)).thenReturn(templateRsrc);
		lenient().when(templateRsrc.adaptTo(ValueMap.class)).thenReturn(varientValueMap);
		lenient().when(varientValueMap.get("contentReviewer", String.class)).thenReturn("partnerGroup");
		lenient().when(args.get(WorkflowConstants.PROCESS_ARGS, String.class))
				.thenReturn("con:tentp#group:checkContentReviewerSelection#pant:Args");
		sendEmailServiceImpl.fetchWorkflowProcessArgs(args, resource, workflow, workItem, resourceResolver);

	}

	@Test
	void testfetchWorkflowProcessArgsLegal() throws LoginException, RepositoryException, MessagingException,
			EmailException, IOException, WorkflowException {
		lenient().when(resolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		lenient().when(resource.getChild(JcrConstants.JCR_CONTENT)).thenReturn(templateRsrc);
		lenient().when(templateRsrc.adaptTo(ValueMap.class)).thenReturn(varientValueMap);
		lenient().when(varientValueMap.get("legaladmins", String.class)).thenReturn("partnerGroup");
		lenient().when(args.get(WorkflowConstants.PROCESS_ARGS, String.class))
				.thenReturn("con:tentp#group:checkLegalAdminSelection#pant:Args");
		sendEmailServiceImpl.fetchWorkflowProcessArgs(args, resource, workflow, workItem, resourceResolver);

	}

	@Test
	void testfetchWorkflowProcessArgsAdmin() throws LoginException, RepositoryException, MessagingException,
			EmailException, IOException, WorkflowException {
		lenient().when(resolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		lenient().when(resource.getChild(JcrConstants.JCR_CONTENT)).thenReturn(templateRsrc);
		lenient().when(templateRsrc.adaptTo(ValueMap.class)).thenReturn(varientValueMap);
		lenient().when(varientValueMap.get("contentAdmins", String.class)).thenReturn("partnerGroup");
		lenient().when(args.get(WorkflowConstants.PROCESS_ARGS, String.class))
				.thenReturn("con:tentp#group:checkContentAdminSelection#pant:Args");
		sendEmailServiceImpl.fetchWorkflowProcessArgs(args, resource, workflow, workItem, resourceResolver);

	}

	@Test
	void testfetchWorkflowProcessNull() throws LoginException, RepositoryException, MessagingException, EmailException,
			IOException, WorkflowException {
		lenient().when(resolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		lenient().when(resource.getChild(JcrConstants.JCR_CONTENT)).thenReturn(templateRsrc);
		// lenient().when(templateRsrc.adaptTo(ValueMap.class)).thenReturn(varientValueMap);
		// lenient().when(varientValueMap.get("contentAdmins",
		// String.class)).thenReturn("partnerGroup");
		lenient().when(args.get(WorkflowConstants.PROCESS_ARGS, String.class))
				.thenReturn("con:tentp#template:participant#pant:Args");
		sendEmailServiceImpl.fetchWorkflowProcessArgs(args, resource, workflow, workItem, resourceResolver);

	}

	@Test
	void testfetchWorkflowProcessArgsInit() throws LoginException, RepositoryException, MessagingException,
			EmailException, IOException, WorkflowException {
		List<String> index = new ArrayList<>();
		index.add("participants");
		lenient().when(resolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		lenient().when(resource.getChild(JcrConstants.JCR_CONTENT)).thenReturn(templateRsrc);
		lenient().when(templateRsrc.adaptTo(ValueMap.class)).thenReturn(varientValueMap);
		lenient().when(emailTemplateBean.getParticipants()).thenReturn(index);
		lenient().when(varientValueMap.get("contentReviewer", String.class)).thenReturn("partnerGroup");
		lenient().when(args.get(WorkflowConstants.PROCESS_ARGS, String.class))
				.thenReturn("con:tentp#group:workflowInit#pant:Args");
		sendEmailServiceImpl.fetchWorkflowProcessArgs(args, resource, workflow, workItem, resourceResolver);
		sendEmailServiceImpl.detailstoSendEmail(resourceResolver, workflowSession, workItem, path, resource);

	}

	@Test
	void testEmailTemplate()
			throws RepositoryException, WorkflowException, EmailException, IOException, LoginException {
		List<HistoryItem> historyList = new ArrayList<HistoryItem>();
		historyList.add(HistoryItem);
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(workItem.getWorkflow()).thenReturn(workflow);
		lenient().when(workflowSession.getHistory(workflow)).thenReturn(historyList);
		lenient().when(HistoryItem.getComment()).thenReturn("comment");
		lenient().when(emailTemplateBean.getTemplateSelector()).thenReturn("contentproducer");
		lenient().when(HistoryItem.getWorkItem()).thenReturn(workItem);
		lenient().when(workflow.getInitiator()).thenReturn("test@bd");
		sendEmailServiceImpl.emailTemplate(resourceResolver, resource, workflowSession, workItem, "recipient");
	}

	@Test
	void testEmailTemplateRejected()
			throws RepositoryException, WorkflowException, EmailException, IOException, LoginException {
		List<HistoryItem> historyList = new ArrayList<HistoryItem>();
		historyList.add(HistoryItem);
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		lenient().when(workItem.getWorkflow()).thenReturn(workflow);
		lenient().when(workflowSession.getHistory(workflow)).thenReturn(historyList);
		lenient().when(HistoryItem.getComment()).thenReturn("comment");
		lenient().when(emailTemplateBean.getTemplateSelector()).thenReturn("rejectedmail");
		lenient().when(HistoryItem.getWorkItem()).thenReturn(workItem);
		lenient().when(workflow.getInitiator()).thenReturn("test@bd");
		sendEmailServiceImpl.emailTemplate(resourceResolver, resource, workflowSession, workItem, "recipient");
	}

	@Test
	void testCreateProductNodeStructure() throws RepositoryException, MessagingException, EmailException, IOException {
		sendEmailServiceImpl.getCommentHtmlRender();
		sendEmailServiceImpl.getContentProducerEmailSubject();
		sendEmailServiceImpl.getContentProducerTemplate();
		sendEmailServiceImpl.getFromEmailAddress();
		sendEmailServiceImpl.getRejectedSubject();
		sendEmailServiceImpl.getrejectedTemplate();

	}

	@Test
	void testActivate() throws Exception {
		sendEmailServiceImpl.activate(config);
	}
}
