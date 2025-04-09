package com.bdb.aem.core.models;

import com.adobe.cq.sightly.WCMUsePojo;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.bdb.aem.core.bean.UserInfoBean;
import com.bdb.aem.core.util.WorkflowHelper;

import com.day.cq.commons.jcr.JcrConstants;

import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;

public class FetchUsersFromGroupDropdownUse extends WCMUsePojo {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(FetchUsersFromGroupDropdownUse.class);

    /**
     * This method is overridden from WCMUsePojo to form a list of users for the dropdown in
     * Content Approval Workflow
     * @throws Exception
     */
    @Override
    public void activate() {
        LOG.debug("Inside FetchUsersFromGroupDropdownUse  --> activate()");
        String parentUserGroup = get("user", String.class);
        SlingHttpServletRequest request = getRequest();
        ResourceResolver resourceResolver = request.getResourceResolver();
        UserManager userManager = resourceResolver.adaptTo(UserManager.class);
        try {
        	//true to fetch groups
        List<Authorizable> assignees = WorkflowHelper.getAuthorizables(userManager, parentUserGroup, true);
        if (!assignees.isEmpty()) {
        	assigneesList(request, resourceResolver, assignees);
        }
    }
     catch(RepositoryException e)
        {
        	LOG.error("ClassName : FetchUsersFromGroupDropdownUse - Error while fetching users {}", e.getMessage());
        }

    }

	/**
	 * @param request
	 * @param resourceResolver
	 * @param assignees
	 * @throws UnsupportedRepositoryOperationException
	 * @throws RepositoryException
	 */
	public void assigneesList(SlingHttpServletRequest request, ResourceResolver resourceResolver,
			List<Authorizable> assignees) throws UnsupportedRepositoryOperationException, RepositoryException {
		List<Resource> propMap = new ArrayList<>();
		ValueMap vm;
		for (Authorizable temp : assignees) {
		    UserInfoBean userInfo = WorkflowHelper.getAuthorizableUserInfo(temp.getPath(), resourceResolver);
		    vm = new ValueMapDecorator(new HashMap<String, Object>());
		    vm.put("value", temp.getID());
		    String text;
		    if(null!=userInfo.getFirstName()){
		        text = userInfo.getFirstName() + (null!=userInfo.getLastName() ? (" " + userInfo.getLastName()) : StringUtils.EMPTY);
		    }
		    else {
		        text = (null!=userInfo.getLastName() ? userInfo.getLastName() : StringUtils.EMPTY);
		    }
		    LOG.debug("User dropdown text is {}", text);
		    if(text!=null && !(text.trim().isEmpty())) {
		    	vm.put("text", text);
		    }else {
		    	vm.put("text", temp.getID());	
		    }
		    propMap.add(new ValueMapResource(resourceResolver, new ResourceMetadata(), JcrConstants.NT_UNSTRUCTURED, vm));
		}
		DataSource ds = new SimpleDataSource(propMap.iterator());
		request.setAttribute(DataSource.class.getName(), ds);
	}
}
