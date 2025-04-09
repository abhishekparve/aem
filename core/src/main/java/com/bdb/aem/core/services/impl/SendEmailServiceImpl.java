package com.bdb.aem.core.services.impl;


import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.HistoryItem;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.Workflow;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.bean.EmailTemplateBean;
import com.bdb.aem.core.bean.TemplateParameterBean;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.SendEmailService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.WorkflowConstants;
import com.bdb.aem.core.util.WorkflowHelper;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.commons.mail.MailTemplate;
import com.day.cq.mailer.MailingException;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import com.day.cq.wcm.api.Page;

import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataSource;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;

/**
 * The Class CookieNameServiceImpl.
 */
@Component(immediate = true, service = SendEmailService.class)
@Designate(ocd = SendEmailServiceImpl.Configuration.class)
public class SendEmailServiceImpl implements SendEmailService {

    private static final Logger LOG = LoggerFactory.getLogger(SendEmailServiceImpl.class);

    /**
     * The email template bean.
     */
    EmailTemplateBean emailTemplateBean;

    public static final String FEEDBACK_PLACEHOLDER = "${feedback}";

    @Reference
    private MessageGatewayService messageGatewayService;

    @Reference
    ExternalizerService externalizerService;


    /**
     * The from email address.
     */
    private String fromEmailAddress;

    private String commentHtmlRender;

    private String contentProducerTemplate;

    private String rejectedTemplate;

    private String contentProducerEmailSubject;

    private String rejectedSubject;
    
    private String checkContentReviewerSelectionGroup;

	private String checkLegalAdminSelectionGroup;
	
	private String checkAdminSelection;

	/**
     * Activate.
     *
     * @param config the config
     */
    @Activate
    @Modified
    protected final void activate(Configuration config) {
        this.fromEmailAddress = config.fromEmailAddress();
        this.commentHtmlRender = config.commentHtmlRender();
        this.contentProducerTemplate = config.contentProducerTemplate();
        this.contentProducerEmailSubject = config.contentProducerEmailSubject();
        this.rejectedTemplate = config.rejectedTemplate();
        this.rejectedSubject = config.rejectedSubject();
        this.checkContentReviewerSelectionGroup = config.checkContentReviewerSelectionGroup();
        this.checkLegalAdminSelectionGroup =  config.checkLegalAdminSelectionGroup();
        this.checkAdminSelection = config.checkAdminSelection();
    }

    @ObjectClassDefinition(name = "Email Configuration")
    public @interface Configuration {
        /**
         * From Email Address.
         *
         * @return the string
         */
        @AttributeDefinition(name = "fromEmailAddress", description = "From Email Address", type = AttributeType.STRING)
        String fromEmailAddress() default "no-reply@bd.com";

        /**
         * Comment HTML in Email Template.
         *
         * @return the string
         */
        @AttributeDefinition(name = "getCommentHtml", description = "Get Comment HTML", type = AttributeType.STRING)
        String commentHtmlRender() default "<tr style='width: 100%;'><td style='color:black;'><br>Comments:</td></tr><tr style='width: 100%;'><td style='color:black; white-space: normal;'><p style='width:700px;'>${feedback}</p></td></tr>";

        /**
         * From Email Address.
         *
         * @return the string
         */
        @AttributeDefinition(name = "contentProducerTemplate", description = "Content Producer Template", type = AttributeType.STRING)
        String contentProducerTemplate() default "/etc/notification/email/bdb/sendContentProducernotification.txt";

        /**
         * From Email Address.
         *
         * @return the string
         */
        @AttributeDefinition(name = "rejectedTemplate", description = "Rejected Template", type = AttributeType.STRING)
        String rejectedTemplate() default "/etc/notification/email/bdb/rejectednotification.txt";

        /**
         * From Email Address.
         *
         * @return the string
         */
        @AttributeDefinition(name = "contentProducerEmailSubject", description = "content Producer Email Subject", type = AttributeType.STRING)
        String contentProducerEmailSubject() default "Reviewer Selection Required";

        /**
         * From Email Address.
         *
         * @return the string
         */
        @AttributeDefinition(name = "rejectedSubject", description = "content Rejected Email Subject", type = AttributeType.STRING)
        String rejectedSubject() default "Content Rejected";
        
        /**
         * From Content Review Group.
         *
         * @return the string
         */
        @AttributeDefinition(name = "Content Review Group", type = AttributeType.STRING)
        String checkContentReviewerSelectionGroup() default "BDB_Authors_Publisher_";
        
        /**
         * From Legal Review Group.
         *
         * @return the string
         */
        @AttributeDefinition(name = "Legal Review Group", type = AttributeType.STRING)
        String checkLegalAdminSelectionGroup() default "BDB_Legal_";
        
        /**
         * From Admin Group.
         *
         * @return the string
         */
        @AttributeDefinition(name = "Admin Group", type = AttributeType.STRING)
        String checkAdminSelection() default "BDB_Admin_Authors_Content";

    }


    /**
     * Gets From Email Address
     *
     * @return
     */
    @Override
    public String getFromEmailAddress() {
        return fromEmailAddress;
    }

    public String getCommentHtmlRender() {
        return commentHtmlRender;
    }

    public String getContentProducerTemplate() {
        return contentProducerTemplate;
    }

    public String getrejectedTemplate() {
        return rejectedTemplate;
    }

    public String getContentProducerEmailSubject() {
        return contentProducerEmailSubject;
    }

    public String getRejectedSubject() {
        return rejectedSubject;
    }
    
    public String getCheckContentReviewerSelectionGroup() {
		return checkContentReviewerSelectionGroup;
	}

	public String getCheckLegalAdminSelectionGroup() {
		return checkLegalAdminSelectionGroup;
	}
	
	public String getCheckAdminSelection() {
		return checkAdminSelection;
	}
    

    /**
     * Sends an email through SMTP server
     * Returns boolean to indicate email trigger success/failure
     *
     * @param templateRsrc
     * @param inputParams
     * @param emailTo
     * @param subject
     * @return
     * @throws MessagingException
     * @throws EmailException
     * @throws IOException
     */
    @Override
    public boolean sendEmail(Resource templateRsrc, Map<String, Object> inputParams, String[] emailTo, String subject)
            throws MessagingException, EmailException, IOException {
        boolean mailSent = false;
        try {
            if (null != templateRsrc) {
                MailTemplate mailTemplate = MailTemplate.create(templateRsrc.getPath(),
                        templateRsrc.getResourceResolver().adaptTo(Session.class));
                LOG.debug("Mail Template {}", mailTemplate);
                HtmlEmail email = mailTemplate.getEmail(StrLookup.mapLookup(inputParams), HtmlEmail.class);
                MessageGateway<Email> messageGateway;
                LOG.debug("getFromEmailAddress() {}", getFromEmailAddress());
                if (null != email && null != getFromEmailAddress()) {
                    email.addTo(emailTo);
                    for (String s : emailTo) {
                        LOG.debug("The email is being send to ------  {}", s);
                    }
                    email.setSubject(subject);
                    messageGateway = messageGatewayService.getGateway(Email.class);
                    messageGateway.send((Email) email);
                    mailSent = true;
                    LOG.debug("Mail sent");
                }
            }
        } catch (MessagingException me) {
            LOG.error("Messaging exception thrown");
            mailSent = false;
        } catch (MailingException me) {
            LOG.error("Mailing exception thrown");
            mailSent = false;
        } catch (EmailException ee) {
            LOG.error("Email exception thrown");
            mailSent = false;
        } catch (IOException ioe) {
            LOG.error("IO exception thrown");
            mailSent = false;
        }
        return mailSent;
    }


    /**
     * Sends an email through SMTP server
     * Returns boolean to indicate email trigger success/failure
     *
     * @param templateRsrc
     * @param inputParams
     * @param emailTo
     * @param subject
     * @return
     * @throws MessagingException
     * @throws EmailException
     * @throws IOException
     */
    @Override
    public List<String> sendEmailWithAttachment(Resource templateRsrc, Map<String, String> inputParams, Map<String, DataSource> attachments, String[] emailTo, String subject)
            throws MessagingException, EmailException, IOException {
        LOG.debug("Entering SendEmailServiceImpl.sendEmailWithAttachment");
        String mailSent = null;
        List<String> failureList = new ArrayList<>();
        try {
            if (null != templateRsrc) {
                MailTemplate mailTemplate = MailTemplate.create(templateRsrc.getPath(),
                        templateRsrc.getResourceResolver().adaptTo(Session.class));

                LOG.debug("Mail Template {}", mailTemplate);
                HtmlEmail email = mailTemplate.getEmail(StrLookup.mapLookup(inputParams), HtmlEmail.class);
                MessageGateway<Email> messageGateway;
                DataSource attachment = null;
                String documentName = null;
                LOG.debug("getFromEmailAddress() {}", inputParams.get("from"));
                if (null != email && StringUtils.isNoneBlank(inputParams.get("from"))) {
                    email.addTo(emailTo);
                    for (String s : emailTo)
                        LOG.debug("The email is being send to ------  {}", s);
                    for (Map.Entry<String, DataSource> entry : attachments.entrySet()) {
                        documentName = entry.getKey();
                        attachment = entry.getValue();
                    }
                    email.attach(attachment, documentName, documentName);
                    email.setSubject(subject);
                    email.setTextMsg(inputParams.get("message"));
                    messageGateway = messageGatewayService.getGateway(Email.class);
                    messageGateway.send((Email) email);
                    LOG.debug("Mail sent");
                }
            }
        } catch (MessagingException me) {
            LOG.error("Messaging exception thrown");
            mailSent = me.getCause().getLocalizedMessage();
            failureList.add(mailSent);
        } catch (MailingException me) {
            LOG.error("Mailing exception thrown");
            mailSent = me.getCause().getLocalizedMessage();
            failureList.add(mailSent);
        } catch (EmailException ee) {
            LOG.error("Email exception thrown");
            mailSent = ee.getCause().getLocalizedMessage();
            failureList.add(mailSent);
        } catch (IOException ioe) {
            LOG.error("IO exception thrown");
            mailSent = ioe.getCause().getLocalizedMessage();
            failureList.add(mailSent);
        }
        LOG.debug("Exiting SendEmailServiceImpl.sendEmailWithAttachment");
        return failureList;
    }


    /**
     * Gets the authorized users for the email recipient list
     * Gets the Email Subject
     * Gets the template location
     * Calls the sendEmail Service to trigger an email through SMTP server
     *
     * @param resourceResolver
     * @param workflowSession
     * @param workItem
     * @param payLoad
     * @param resource
     * @throws RepositoryException
     * @throws WorkflowException
     * @throws MessagingException
     * @throws EmailException
     * @throws IOException
     */
    public void detailstoSendEmail(ResourceResolver resourceResolver, WorkflowSession workflowSession, WorkItem workItem, String payLoad, Resource resource)
            throws RepositoryException, WorkflowException, MessagingException, EmailException, IOException {

        /** Fetch Recepient details List */

        Map<String, String> userMap = new HashMap<>();
        userMap.putAll(fetchRecipientsDetails(resourceResolver));
        Iterator<Map.Entry<String, String>> itr = userMap.entrySet().iterator();

        while (itr.hasNext()) {
            Map.Entry<String, String> entry = itr.next();
            try {
                String[] emailTo = ArrayUtils.toArray(entry.getKey());
                invokeEmail(resourceResolver, workflowSession, workItem, resource, emailTo, entry.getValue());

            } catch (WorkflowException | MessagingException | EmailException | IOException ex) {
                LOG.error("Send Email threw exception");
            }
        }
    }

    private void invokeEmail(ResourceResolver resourceResolver, WorkflowSession workflowSession, WorkItem workItem,
                             Resource resource, String[] emailTo, String recipient)
            throws WorkflowException, MessagingException, EmailException, IOException {

        /** Fetch Email Template and set input params */
        Resource templateRes = emailTemplate(resourceResolver, resource, workflowSession, workItem, recipient);

        LOG.debug("Template is {}", templateRes.getPath());

        /** Fetch the email subject */
        String emailSub = emailTemplateBean.getEmailSubject();

        LOG.debug("Subject is {}", emailSub);
        sendEmail(templateRes, emailTemplateBean.getInputParams(), emailTo, emailSub);
    }

    /**
     * Gets the Email Template path based on Process_Args entry
     * Gets the previous step workflow comments
     * Sets the InputParams for Email Template according to the Template path
     *
     * @param resourceResolver
     * @param resource
     * @param workflowSession
     * @param workItem
     * @return
     * @throws WorkflowException
     */
    public Resource emailTemplate(ResourceResolver resourceResolver, Resource resource, WorkflowSession workflowSession, WorkItem workItem, String recipient)
            throws WorkflowException {
        TemplateParameterBean templateParameterBean = new TemplateParameterBean();
        templateParameterBean.setResource(resource);
        templateParameterBean.setRecipient(recipient);

        LOG.debug("Entered email template ()");


        /** Fetch Workflow Comment */
        List<HistoryItem> historyList = workflowSession.getHistory(workItem.getWorkflow());
        int listSize = historyList.size();
        HistoryItem lastItem = historyList.get(listSize - 1);
        String comment = lastItem.getComment();
        if (comment != null && comment.length() > 0) {
            LOG.debug("Previous Workflow Comment = {}", comment);
            String escapedComment = WorkflowHelper.escapeString(comment);
            if (StringUtils.isNotBlank(escapedComment)) {
                comment = escapedComment;
            }
        } else {
            comment = StringUtils.EMPTY;
        }
        templateParameterBean.setLastItem(lastItem);
        templateParameterBean.setComment(comment);
        return fetchTemplate(resourceResolver, templateParameterBean);
    }


    private Resource fetchTemplate(ResourceResolver resourceResolver, TemplateParameterBean templateParameterBean) {

        Resource templateRsrc = null;
        LOG.debug("Fetch template()");
        HistoryItem lastItem = templateParameterBean.getLastItem();
        Resource resource = templateParameterBean.getResource();
        /** Input Params for Email */
        Map<String, Object> inputParams = new HashMap<>();

        String temp = emailTemplateBean.getTemplateSelector();

        /** Check if the Email is to be sent to Alliance Partner through PROCESS_ARGS */
        if (StringUtils.equalsIgnoreCase(temp, WorkflowConstants.CONTENT_PRODUCER)) {
            templateRsrc = resourceResolver.getResource(getContentProducerTemplate());
            emailTemplateBean.setEmailSubject(getContentProducerEmailSubject());

            inputParams.put("payload", externalizerService.getFormattedUrl(resource.getPath(), resourceResolver));

            inputParams.put(WorkflowConstants.USER_ID_EMAIL, WorkflowHelper.escapeString(lastItem.getWorkItem().getWorkflow().getInitiator()));
        } else if (StringUtils.equalsIgnoreCase(temp, "rejectedmail")) {
            templateRsrc = resourceResolver.getResource(getrejectedTemplate());
            emailTemplateBean.setEmailSubject(getRejectedSubject());
            inputParams.put("payload", externalizerService.getFormattedUrl(resource.getPath(), resourceResolver));

            inputParams.put(WorkflowConstants.USER_ID_EMAIL, WorkflowHelper.escapeString(lastItem.getWorkItem().getWorkflow().getInitiator()));
        }

        inputParams.put(WorkflowConstants.FEEDBACK, templateParameterBean.getComment());

        emailTemplateBean.setInputParams(inputParams);


        return templateRsrc;
    }


    /**
     * Fetch recipients details.
     *
     * @param resourceResolver the resource resolver
     * @return the map
     * @throws RepositoryException the repository exception
     */
    private Map<String, String> fetchRecipientsDetails(ResourceResolver resourceResolver) throws RepositoryException {
        return WorkflowHelper.getRecipientsDetails(resourceResolver, emailTemplateBean.getParticipants());
    }

    @Override
    public void fetchWorkflowProcessArgs(MetaDataMap args, org.apache.sling.api.resource.Resource resource,
                                         com.adobe.granite.workflow.exec.Workflow workflow, WorkItem workItem, ResourceResolver resourceResolver) {

        emailTemplateBean = new EmailTemplateBean();
        String participantArgs = args.get(WorkflowConstants.PROCESS_ARGS, String.class);
        LOG.debug("Participant args is {}", participantArgs);

        /** Fetch Resource valuemap details */
        Resource jcrRes = resource.getChild(JcrConstants.JCR_CONTENT);
        ValueMap resVm = null != jcrRes ? jcrRes.adaptTo(ValueMap.class) : null;

        if (null != participantArgs && participantArgs.contains(CommonConstants.HASH)) {
            String[] arguments = participantArgs.split(CommonConstants.HASH);
            Map<String, String> map = new HashMap<>();
            for (String arg : arguments) {
                String[] wfDetails = arg.split(CommonConstants.COLON);
                map.put(wfDetails[0], wfDetails[1]);
            }

            for (Map.Entry<String, String> m : map.entrySet()) {
                String key = m.getKey();
                if (StringUtils.equalsIgnoreCase(key, WorkflowConstants.GROUP) && null != resVm) {
                    checkGroupParams(m, resVm, workflow, resource);
                } else if (StringUtils.equalsIgnoreCase(key, WorkflowConstants.TEMPLATE)) {
                    emailTemplateBean.setTemplateSelector(m.getValue());
                }
            }
        }

    }

    private void checkGroupParams(Map.Entry<String, String> m, ValueMap resVm, Workflow workflow, Resource res) {

        String value = m.getValue();
        Page page = res.adaptTo(Page.class);
        String region = CommonHelper.getRegion(page);
        if (StringUtils.equalsIgnoreCase(value, WorkflowConstants.WORKFLOW_INITIATOR)) {
            String workflowInitiator = workflow.getInitiator();
            emailTemplateBean.setParticipants(Arrays.asList(workflowInitiator));
        } else if (StringUtils.equalsIgnoreCase(value, "checkContentReviewerSelection")) {
            String partnerGroup = getCheckContentReviewerSelectionGroup()+region;
            emailTemplateBean.setParticipants(Arrays.asList(partnerGroup));
        }  else if (StringUtils.equalsIgnoreCase(value, "checkLegalAdminSelection")) {
            String partnerGroup = getCheckLegalAdminSelectionGroup()+region;
            emailTemplateBean.setParticipants(Arrays.asList(partnerGroup));     
        }else if(StringUtils.equalsIgnoreCase(value, "checkAdminSelection")) {
        	 String partnerGroup = getCheckAdminSelection();
        	 emailTemplateBean.setParticipants(Arrays.asList(partnerGroup));   
        }
    }


}
