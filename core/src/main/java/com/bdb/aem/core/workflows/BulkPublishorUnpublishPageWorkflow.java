package com.bdb.aem.core.workflows;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.WorkflowConstants;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;

/**
 * The process to index/un-index content pages and pdf assets.
 */
@Component(immediate = true, service = BulkPublishorUnpublishPageWorkflow.class, property = {
		"process.label = Bulk Publish/Unpublish Page" })
public class BulkPublishorUnpublishPageWorkflow implements WorkflowProcess {

	/** The Constant LOG. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BulkPublishorUnpublishPageWorkflow.class);

	/** The resource resolver factory. */
	@Reference
	ResourceResolverFactory resourceResolverFactory;
	
	String urlFormat;
	
	/** The replicator. */
	@Reference
    Replicator replicator;
	
	String payload = null;
	
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
		ResourceResolver serviceResolver = null;
		String[] path = {"/content/bdb/na/us/en-us", "/content/bdb/na/ca/en-ca",
                "/content/bdb/eu/at/en-at", "/content/bdb/eu/be/en-be", "/content/bdb/eu/dk/en-dk", "/content/bdb/eu/fi/en-fi",
                "/content/bdb/eu/fr/en-fr", "/content/bdb/eu/de/en-de", "/content/bdb/eu/ie/en-ie", "/content/bdb/eu/it/en-it",
                "/content/bdb/eu/lu/en-lu", "/content/bdb/eu/nl/en-nl", "/content/bdb/eu/no/en-no", "/content/bdb/eu/pl/en-pl",
                "/content/bdb/eu/pt/en-pt", "/content/bdb/eu/es/en-es", "/content/bdb/eu/se/en-se", "/content/bdb/eu/ch/en-ch",
                "/content/bdb/eu/gb/en-gb", "/content/bdb/eu/eu/en-eu", "/content/bdb/apac/au/en-au", "/content/bdb/apac/nz/en-nz",
                "/content/bdb/apac/tw/en-tw", "/content/bdb/apac/kr/ko-kr", "/content/bdb/apac/in/en-in", "/content/bdb/apac/sg/en-sg", "/content/bdb/apac/jp/ja-jp",
                "/content/bdb/cn/cn/zh-cn", "/content/bdb/latam/br/en-br"};
		
		try {
			serviceResolver = CommonHelper.getServiceResolver(resourceResolverFactory);
			payload = workItem.getWorkflowData().getPayload().toString();
			String participantArgs = args.get(WorkflowConstants.PROCESS_ARGS, String.class);
			//String payloadPath = "/var/workflow/models";
			/*Resource root = serviceResolver.getResource("/var/workflow/models");
			Node rootNode = root.adaptTo(Node.class);
			if(rootNode!= null && !rootNode.hasProperty("bulkPublishPayload")) {
				rootNode.addNode("bulkPublishPayload");
			}
			
			rootNode.setProperty("bulkPublishPayload", payload);
			String payloadPath = rootNode.getProperty("bulkPublishPayload").getString();*/
			//bulkPublishorUnpublishPage.bulkPublishorUnpublishPage(payload, participantArgs, serviceResolver);
			bulkPublishorUnpublishPage(path, participantArgs, serviceResolver);
		}
		
		catch (LoginException e) {
			LOGGER.error("Exception occured {}", e);
		} finally {
			if (null != serviceResolver) {
				CommonHelper.closeResourceResolver(serviceResolver);
			}
		}

	}
	public void bulkPublishorUnpublishPage(String[] path, String participantArgs, ResourceResolver resourceResolver) {
		String[] argsFromWorkflow = participantArgs.toLowerCase().split("\n");
		String[] regions = argsFromWorkflow[0].split(":");
		String[] country = regions[1].split("#");
		String[] publish = argsFromWorkflow[1].split(":");
		String[] unPublish = argsFromWorkflow[2].split(":");
		
		if (StringUtils.contains(payload, "language-masters")) {
			for(String countryPath : path) {
				String countryCode = countryPath.split("-")[1];
				for(int i=0; i<country.length; i++) {
					if(StringUtils.contains(country[i], "global")) {
						String publishPageLink = payload.replace("/content/bdb/language-masters/en", countryPath);
						
						publishorUnpublish(publishPageLink,publish,unPublish, resourceResolver);
					}
					if (StringUtils.contains(country[i], "emea")) {
						if(StringUtils.contains(countryPath, "/content/bdb/eu")) {
							String publishPageLink = null;
							if(StringUtils.contains(payload, "/content/bdb/language-masters/en/"))
								publishPageLink = payload.replace("/content/bdb/language-masters/en", countryPath);
							else if(StringUtils.contains(payload, "/content/bdb/language-masters/en-eu/"))
								publishPageLink = payload.replace("/content/bdb/language-masters/en-eu", countryPath);
							
							publishorUnpublish(publishPageLink,publish,unPublish, resourceResolver);
		            		
						}
					}
					if(StringUtils.contains(country[i], countryCode)) {
						
						String publishPageLink = payload.replace("/content/bdb/language-masters/en", countryPath);
						
						publishorUnpublish(publishPageLink,publish,unPublish, resourceResolver);
	            		
					}
				}
				
				
			 }
		 }
	 }
			 
	
	public void publishorUnpublish(String publishPageLink, String[] publish, String[] unPublish, ResourceResolver resourceResolver) {
		Session session = resourceResolver.adaptTo(Session.class);
			try {
				if(publish[1].toLowerCase().contains("true")) {
					replicator.replicate(session,ReplicationActionType.ACTIVATE,publishPageLink);
				}
				else if(unPublish[1].toLowerCase().contains("true")) {
					replicator.replicate(session,ReplicationActionType.DEACTIVATE,publishPageLink);
				}
			}catch (ReplicationException e) {
				LOGGER.error("Exception occured in Publish/Unpublish Page", e);
			}
		}
	
}
