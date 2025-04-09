package com.bdb.aem.core.services;

import org.apache.commons.mail.EmailException;
import org.apache.jackrabbit.api.security.user.UserManager;

import javax.activation.DataSource;
import javax.jcr.RepositoryException;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.Workflow;
import com.adobe.granite.workflow.metadata.MetaDataMap;
/**
 * The Interface FetchBearerTokenService.
 */
public interface SendEmailService {

	/**
	 * Sends Email to the recipients
	 *
	 */
	public boolean sendEmail(Resource templateRsrc, Map<String, Object> inputParams, String[] emailTo, String subject)
			throws MessagingException, EmailException, IOException;

	/**
	 * Sends Email with attachment to the recipients
	 *
	 */
	public List<String> sendEmailWithAttachment(Resource templateRsrc,Map<String, String> inputParams,Map<String, DataSource> attachments, String[] emailTo, String subject)
			throws MessagingException, EmailException, IOException;
	
	
	/**
	 * Sends Getting Details to Send Mail
	 *
	 */
	public void detailstoSendEmail(ResourceResolver resourceResolver, WorkflowSession workflowSession,
			WorkItem workItem, String payLoad, Resource resource)
			throws RepositoryException, WorkflowException, MessagingException, EmailException, IOException;

	/**
	 * This method accepts Workflow MetaDataMap as parameter Gets the email template
	 * type and email recipients from Process_Args
	 * 
	 * @param args
	 */
	public void fetchWorkflowProcessArgs(MetaDataMap args, Resource resource, Workflow workflow, WorkItem item,
			ResourceResolver resourceResolver);

	String getFromEmailAddress();

}
