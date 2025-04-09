/**
 * 
 */
package com.bdb.aem.core.workflows;

import java.util.Date;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;

/**
 * @author Nagarjuna Talari
 *
 */
/**
 * The Class ProcessProductDocument.
 */
@Component(immediate = true, service = WorkflowProcess.class, property = {
		"process.label = bulk rollout/publish workflow" })
public class BulkRollOutPublish implements WorkflowProcess {


	/** The Constant LOG. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BulkRollOutPublish.class);

	/** The resource resolver factory. */
	@Reference
	ResourceResolverFactory resourceResolverFactory;
	
	String urlFormat;
	
	/** The replicator. */
	@Reference
    Replicator replicator;
	
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
		Session session = null;
		
		String LANGUAGE_MASTERS_XF_EN_PATH= "/content/experience-fragments/bdb/language-masters/en";
		String LANGUAGE_MASTERS_XF_EN_EU_PATH= "/content/experience-fragments/bdb/language-masters/en-eu";
		String CONTENT_XF = "/content/experience-fragments";
		String XF = "/experience-fragments/";
		String LANGUAGE_MASTERS_EN_PATH = "/content/bdb/language-masters/en";
		String LANGUAGE_MASTERS_EN_EU_PATH = "/content/bdb/language-masters/en-eu";
		String LANGUAGE_MASTERS_EN= "/language-masters/en/";
		String LANGUAGE_MASTERS_EN_EU= "/language-masters/en-eu/";
		String APAC_PATH= "/bdb/apac/";
		String EMEA_PATH= "/bdb/eu/";
		String CN_PATH = "/bdb/cn/";
		
		try {
			
			serviceResolver = CommonHelper.getServiceResolver(resourceResolverFactory);
			session = workflowSession.adaptTo(javax.jcr.Session.class);
			String payload = workItem.getWorkflowData().getPayload().toString();
			String payloadRes = payload + "/jcr:content";
			
			Resource resource = serviceResolver.getResource(payloadRes);
			ValueMap valueMap = resource.adaptTo(ValueMap.class);
			String publish = null;
			String unPublish = null;
			String[] regionsList = null;
			String intiator = null;
			if (valueMap.containsKey("region")) {

				regionsList = valueMap.get("region", new String[] {});

			}
			if (valueMap.containsKey("publish") || valueMap.containsKey("unpublish")) {
				publish = valueMap.get("publish", StringUtils.EMPTY);
				unPublish = valueMap.get("unpublish", StringUtils.EMPTY);
			}

			Date date = null;
			if (valueMap.containsKey("activationdate")) {
				date = valueMap.get("activationdate", Date.class);
			}
			intiator=workItem.getWorkflow().getInitiator();
			workItem.getWorkflowData().getMetaDataMap().put("abosluteTime", "600000");

			if (StringUtils.contains(payload, "language-masters")) {
				Node payloadNode = session.getNode(payloadRes);
				String countryListPath = "/content/bdb/na/us/en-us/country-selector/jcr:content/root/countryselector";
				Resource countryListResource = serviceResolver.getResource(countryListPath);
				if (null != countryListResource && countryListResource.hasChildren()) {
					Resource regionMultifieldResource = countryListResource.getChild("regionmultifield");
					if (null != regionMultifieldResource && regionMultifieldResource.hasChildren()) {
						for (Resource childResource : regionMultifieldResource.getChildren()) {
							if (null != childResource && childResource.hasChildren()) {
								Resource countryResource = childResource.getChild("countrymultifield");
								if (null != countryResource && countryResource.hasChildren()) {
									for (Resource itemResource : countryResource.getChildren()) {
										ValueMap vm = itemResource.adaptTo(ValueMap.class);
										String countryUrl = vm.get("urlCountry", StringUtils.EMPTY);
										String[] urlSplits = countryUrl.split("/");
										String countryCode = urlSplits[urlSplits.length - 1].split("-")[1];
										String countryLang = urlSplits[urlSplits.length - 1].split("-")[0];
										for (int i = 0; i < regionsList.length; i++) {
											String publishPageLink = null;
											if (StringUtils.contains(regionsList[i].split("/")[1].toLowerCase(), CommonConstants.GLOBAL)) {
												if(payload.contains(XF)) {
													publishPageLink = payload.replace(LANGUAGE_MASTERS_XF_EN_PATH, countryUrl);
													publishPageLink = publishPageLink.replace(CommonConstants.CONTENT, CONTENT_XF);
													LOGGER.debug("publishPageLink after XF replacement : " + publishPageLink);
												} else {
													LOGGER.debug("This is a content page to publish/unpublish globally");
													publishPageLink = payload
															.replace(LANGUAGE_MASTERS_EN_PATH, countryUrl);
												}
												if (publish.toLowerCase().contains("true")) {
													replicator.replicate(session, ReplicationActionType.ACTIVATE,
															publishPageLink);
													payloadNode.setProperty("cq:lastReplicatedBy", intiator);

												} else if (unPublish.toLowerCase().contains("true")) {
													replicator.replicate(session, ReplicationActionType.DEACTIVATE,
															publishPageLink);
												}
											}
											if (StringUtils.contains(regionsList[i].split("/")[1].toLowerCase(), CommonConstants.EMEA)) {
												if(StringUtils.contains(countryUrl, EMEA_PATH)) {
													if(payload.contains(XF)) {
														if (StringUtils.contains(payload,LANGUAGE_MASTERS_EN)) {
															publishPageLink = payload.replace(LANGUAGE_MASTERS_XF_EN_PATH, countryUrl);
															publishPageLink = publishPageLink.replace(CommonConstants.CONTENT, CONTENT_XF);
															LOGGER.debug("publishPageLink after XF replacement : " + publishPageLink);
															
														} else if (StringUtils.contains(payload,LANGUAGE_MASTERS_EN_EU)) {
															publishPageLink = payload
																	.replace(LANGUAGE_MASTERS_XF_EN_EU_PATH, countryUrl);
															publishPageLink = publishPageLink.replace(CommonConstants.CONTENT, CONTENT_XF);
														}												
													} else {
														if (StringUtils.contains(payload,
																LANGUAGE_MASTERS_EN)) {
															publishPageLink = payload.replace(LANGUAGE_MASTERS_EN_PATH, countryUrl);
														}
														else if (StringUtils.contains(payload,
																LANGUAGE_MASTERS_EN_EU)) {
															publishPageLink = payload.replace(LANGUAGE_MASTERS_EN_EU_PATH, countryUrl);
														}
													}
													if (publish.toLowerCase().contains("true")) {
														replicator.replicate(session, ReplicationActionType.ACTIVATE,
																publishPageLink);
														payloadNode.setProperty("cq:lastReplicatedBy", intiator);
													} else if (unPublish.toLowerCase().contains("true")) {
														replicator.replicate(session, ReplicationActionType.DEACTIVATE,
																publishPageLink);
													}
												}
											}
											if (StringUtils.contains(regionsList[i].split("/")[1].toLowerCase(), CommonConstants.APAC_EN)) {
												if(StringUtils.contains(countryUrl, APAC_PATH) && StringUtils.contains(countryLang, CommonConstants.CONST_DEFAULT_LANGUAGE) ){
													if(payload.contains(XF)) {
														publishPageLink = payload.replace(LANGUAGE_MASTERS_XF_EN_PATH, countryUrl);
														publishPageLink = publishPageLink.replace(CommonConstants.CONTENT, CONTENT_XF);
														LOGGER.debug("publishPageLink after XF replacement : " + publishPageLink);
													} else {
														publishPageLink = payload.replace(LANGUAGE_MASTERS_EN_PATH, countryUrl);
													}
													if (publish.toLowerCase().contains("true")) {
														replicator.replicate(session, ReplicationActionType.ACTIVATE,publishPageLink);
														payloadNode.setProperty("cq:lastReplicatedBy", intiator);
	
													} else if (unPublish.toLowerCase().contains("true")) {
														replicator.replicate(session, ReplicationActionType.DEACTIVATE,publishPageLink);
													}
												}
											}
											if (StringUtils.contains(regionsList[i].split("/")[1].toLowerCase(), CommonConstants.APAC)) {
												if(StringUtils.contains(countryUrl, APAC_PATH) || StringUtils.contains(countryUrl, CN_PATH) ){
													if(payload.contains(XF)) {
														publishPageLink = payload.replace(LANGUAGE_MASTERS_XF_EN_PATH, countryUrl);
														publishPageLink = publishPageLink.replace(CommonConstants.CONTENT, CONTENT_XF);
														LOGGER.debug("publishPageLink after XF replacement : " + publishPageLink);
													} else {
														publishPageLink = payload.replace(LANGUAGE_MASTERS_EN_PATH, countryUrl);
													}
													if (publish.toLowerCase().contains("true")) {
														replicator.replicate(session, ReplicationActionType.ACTIVATE,publishPageLink);
														payloadNode.setProperty("cq:lastReplicatedBy", intiator);
	
													} else if (unPublish.toLowerCase().contains("true")) {
														replicator.replicate(session, ReplicationActionType.DEACTIVATE,publishPageLink);
													}
												}
											}
											if (StringUtils.contains(regionsList[i].split("/")[1].toLowerCase(), countryCode)) {
												if(payload.contains(XF)) {
													publishPageLink = payload.replace(LANGUAGE_MASTERS_XF_EN_PATH, countryUrl);
													publishPageLink = publishPageLink.replace(CommonConstants.CONTENT, CONTENT_XF);
												} else {
													publishPageLink = payload.replace(LANGUAGE_MASTERS_EN_PATH, countryUrl);
												}
												if (publish.toLowerCase().contains("true")) {
													 replicator.replicate(session,ReplicationActionType.ACTIVATE,publishPageLink);
													
													payloadNode.setProperty("cq:lastReplicatedBy", intiator);
												} else if (unPublish.toLowerCase().contains("true")) {
													replicator.replicate(session, ReplicationActionType.DEACTIVATE,
															publishPageLink);
												}
											}
										}

									}
								}
							}
						}

					}
				}
			}
		}
		catch (LoginException | ReplicationException | RepositoryException e) {
			LOGGER.error("Exception occured {}", e);
		} finally {
			if (null != serviceResolver) {
				CommonHelper.closeResourceResolver(serviceResolver);
			}
		}

	}
	

}
