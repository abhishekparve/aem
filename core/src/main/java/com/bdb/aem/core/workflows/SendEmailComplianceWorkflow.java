package com.bdb.aem.core.workflows;

import java.io.IOException;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.mail.MessagingException;

import org.apache.commons.mail.EmailException;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.HistoryItem;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.Workflow;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.services.SendEmailService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.Externalizer;



/**
 * The class SendEmailComplianceWorkflow
 */
@Component(immediate = true, service=WorkflowProcess.class, property = { "process.label = Send Email Compliance Workflow"})
public class SendEmailComplianceWorkflow implements WorkflowProcess {

    @Reference
    SendEmailService sendEmailService;

    @Reference
    ResourceResolverFactory resourceResolverFactory;
    

    private static final Logger LOG = LoggerFactory.getLogger(SendEmailComplianceWorkflow.class);

    /**
     * This is an overriden method from Workflow process class
     * This method gets the email details through process Args and sends email
     * @param item
     * @param wfsession
     * @param args
     * @throws WorkflowException
     */
    public void execute(WorkItem item, WorkflowSession wfsession, MetaDataMap args) throws WorkflowException {
        ResourceResolver resourceResolver = null;
        try {

            Workflow wf = item.getWorkflow();
            List<HistoryItem> wfHistory = wfsession.getHistory(wf);
            LOG.debug("WF is empty? {} ",wfHistory.isEmpty());
            if (!wfHistory.isEmpty()) {
                int listSize = wfHistory.size();
                HistoryItem lastItem = wfHistory.get(listSize-1);
                String payLoad = item.getWorkflowData().getPayload().toString();
                resourceResolver = CommonHelper.getServiceResolver(resourceResolverFactory);
                if(null!=resourceResolver) {
                    Resource resource = resourceResolver.getResource(payLoad);

                    if (null != resource) {
                        sendEmailService.fetchWorkflowProcessArgs(args, resource, wf, item, resourceResolver);
                        sendEmailService.detailstoSendEmail(resourceResolver, wfsession, item, payLoad, resource);
                    }
                }
            }

        } catch (LoginException | RepositoryException | MessagingException | EmailException | IOException e) {
                LOG.error("Error while trying to fetch email details {}", e);
		} 
        finally
        {
            if(null!=resourceResolver)
            {
                resourceResolver.close();
            }
        }
    }

}
