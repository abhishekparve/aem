package com.bdb.aem.core.workflows;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.ParticipantStepChooser;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.WorkflowConstants;
import com.day.cq.wcm.api.Page;

@Component(service = ParticipantStepChooser.class, property = { "chooser.label=" + "Dynamic Participant Selection BDB" })
public class WorkFlowParticipantChooser implements ParticipantStepChooser {

	@Reference
	ResourceResolverFactory resourceResolverFactory;

	

	Resource resource;

	@Override
	public String getParticipant(WorkItem item, WorkflowSession arg1, MetaDataMap arg2) throws WorkflowException {
		ResourceResolver resourceResolver = null;
		String participant = "Admin";
		try {
			resourceResolver = CommonHelper.getReadServiceResolver(resourceResolverFactory);
		} catch (LoginException e) {

		}
		String groupArgs = arg2.get(WorkflowConstants.PROCESS_ARGS, String.class);
		String[] param = groupArgs.split("#");
		String payLoad = item.getWorkflowData().getPayload().toString();
		if (null != resourceResolver) {
			resource = resourceResolver.getResource(payLoad);
		}
		if(null == resource)
			return participant;
		Page page = resource.adaptTo(Page.class);
		if(null == page) {
			return participant;
		}
		String region = CommonHelper.getRegion(page);
		
		if (StringUtils.isNotEmpty(groupArgs) && param.length>0) {
			if(param[1].equalsIgnoreCase("reviewer")) {
				participant = param[0]+region.toLowerCase();
			}else if(param[1].equalsIgnoreCase("author")) {
				participant = param[0]+region;
			}else if(param[1].equalsIgnoreCase("legal")) {
				participant = param[0]+region;
			}else if(param[1].equalsIgnoreCase("publisher")) {
				participant = param[0]+region;
			}
		}
		
		if(null != resourceResolver && resourceResolver.isLive()) {
			resourceResolver.close();
		}
		
		return participant;
	}
}
