package com.bdb.aem.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jcr.AccessDeniedException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.Value;
import javax.jcr.ValueFactory;

import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.bean.EmailTemplateBean;
import com.bdb.aem.core.bean.UserInfoBean;
import com.day.cq.commons.jcr.JcrConstants;

import org.apache.sling.api.resource.*;
import org.apache.sling.jcr.base.util.AccessControlUtil;


/**
 * The Class WorkflowHelper.
 */
public final class WorkflowHelper {

	/** The rand. */
	static SecureRandom rand = new SecureRandom();

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(WorkflowHelper.class);

	/**
	 * Instantiates a new workflow helper.
	 */
	private WorkflowHelper() {
	}

	/**
	 * Gets the payload path.
	 *
	 * @param workItem the work item
	 * @return the payload path
	 */
	public static String getPayloadPath(WorkItem workItem) {
		return workItem.getWorkflowData().getPayload().toString();
	}

	/**
	 * Gets the User Info needed to send email.
	 *
	 * @param path     the path
	 * @param resolver the resolver
	 * @return the authorizable user info
	 */
	public static UserInfoBean getAuthorizableUserInfo(String path, ResourceResolver resolver) {
		UserInfoBean userInfo = new UserInfoBean();
		if (null == resolver || StringUtils.isEmpty(path))
			return userInfo;
		Resource resource = resolver.getResource(path);
		if (null != resource) {
			ValueMap valueMap = resource.getValueMap();
			userInfo.setEmail(valueMap.get(WorkflowConstants.PROFILE + WorkflowConstants.EMAIL, String.class));
			userInfo.setLastName(valueMap.get(WorkflowConstants.PROFILE + WorkflowConstants.FAMILY_NAME, String.class));
			userInfo.setFirstName(valueMap.get(WorkflowConstants.PROFILE + WorkflowConstants.GIVEN_NAME, String.class));
		}
		return userInfo;
	}

	/**
	 * This method is used to create a temporary user group dynamically.
	 *
	 * @param groupName      the group name
	 * @param userManager    the user manager
	 * @param session        the session
	 * @param groupGivenName the group given name
	 */
	public static void createTemporaryGroup(String groupName, UserManager userManager, Session session,
			String groupGivenName) {
		try {
			Group group = null;
			group = userManager.createGroup(groupName, new SimplePrincipal(groupName),
					"/home/groups/hillrom/tempworkflowgroup");
			ValueFactory valueFactory = session.getValueFactory();
			Value groupNameValue = valueFactory.createValue(groupGivenName, PropertyType.STRING);
			if (null != group) {
				group.setProperty("./profile/givenName", groupNameValue);
			}
			session.save();
		} catch (RepositoryException e) {
			LOG.error("Exception while creating temporaty group {}", e);
		}
	}

	/**
	 * The Class SimplePrincipal.
	 */
	protected static class SimplePrincipal implements Principal {

		/** The name. */
		protected final String name;

		/**
		 * Instantiates a new simple principal.
		 *
		 * @param name the name
		 */
		public SimplePrincipal(String name) {
			if (StringUtils.isBlank(name)) {
				LOG.debug("Principal name cannot be blank.");
			}
			this.name = name;
		}

		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Hash code.
		 *
		 * @return the int
		 */
		@Override
		public int hashCode() {
			return name.hashCode();
		}

		/**
		 * Equals.
		 *
		 * @param obj the obj
		 * @return true, if successful
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Principal) {
				return name.equals(((Principal) obj).getName());
			}
			return false;
		}
	}

	/**
	 * This method creates a unique group name using Random().
	 *
	 * @param group the group
	 * @return the string
	 */
	public static String createUniqueGroupName(String group) {

		return group + "_" + rand.nextInt(10000) + "_"
				+ System.currentTimeMillis();
	}

	/**
	 * This method is used to add a set of users to a group dynamically.
	 *
	 * @param userManager the user manager
	 * @param groupName   the group name
	 * @param groupUsers  the group users
	 * @param session     the session
	 * @throws RepositoryException the repository exception
	 */
	public static void addUsersToGroup(UserManager userManager, String groupName, String[] groupUsers, Session session)
			throws RepositoryException {
		Group addUserToGroup = (Group) (userManager.getAuthorizable(groupName));
		for (String name : groupUsers) {
			if (null != addUserToGroup) {
				addUserToGroup.addMember(userManager.getAuthorizable(name));
			}
		}
		session.save();
	}

	

	/**
	 * This method is used to create a user group and add users to it dynamically
	 * and store the information in the workflow instance.
	 *
	 * @param resourceResolver the resource resolver
	 * @param groupUsers       the group users
	 * @param userNames        the user names
	 * @return the string
	 */
	public static String createGroupUser(ResourceResolver resourceResolver, String[] groupUsers, String[] userNames) {
		String groupName = createUniqueGroupName(WorkflowConstants.DYNAMIC_GROUPS);
		LOG.debug("Creating a new group named " + groupName);
		try {
			Session session = resourceResolver.adaptTo(Session.class);
			if (null != session) {
				final UserManager userManager = AccessControlUtil.getUserManager(session);
				if (userManager.getAuthorizable(groupName) == null) {
					createTemporaryGroup(groupName, userManager, session, String.join(", ", userNames));
					addUsersToGroup(userManager, groupName, groupUsers, session);

				} else {
					groupName = createUniqueGroupName(WorkflowConstants.DYNAMIC_GROUPS);
					createTemporaryGroup(groupName, userManager, session, String.join(", ", userNames));
					addUsersToGroup(userManager, groupName, groupUsers, session);
				}
			}
		} catch (AccessDeniedException e) {
			LOG.error("AccessDeniedException", e);
		} catch (UnsupportedRepositoryOperationException e) {
			LOG.error("UnsupportedRepositoryOperationException", e);
		} catch (RepositoryException e) {
			LOG.error("RepositoryException", e);
		}
		return groupName;
	}


	/**
	 * Parses the user list.
	 *
	 * @param userList the user list
	 * @return the string[]
	 */
	public static String[] parseUserList(String[] userList) {

		List<String> updatedUserList = new ArrayList<>();
		for (String user : userList) {
			if (StringUtils.isNotEmpty(user) && user.contains("(") && user.contains(")")) {
				user = StringUtils.substringBetween(user, "(", ")");

			}
			updatedUserList.add(user.trim());
		}
		return updatedUserList.stream().toArray(String[]::new);
	}

	/**
	 * Parses the user name list.
	 *
	 * @param userList the user list
	 * @return the string[]
	 */
	public static String[] parseUserNameList(String[] userList) {
		List<String> updatedUserList = new ArrayList<>();
		for (String user : userList) {
			if (StringUtils.isNotEmpty(user) && user.contains("(") && user.contains(")")) {
				user = StringUtils.substringBefore(user, "(");

			}

			updatedUserList.add(user.trim());
		}
		return updatedUserList.stream().toArray(String[]::new);
	}


	/**
	 * Check and trim nodes.
	 *
	 * @param session         the session
	 * @param args            the args
	 * @param pagePublishNode the page publish node
	 */
	public static void checkAndTrimNodes(Session session, MetaDataMap args, Node pagePublishNode) {
		try {
			Long nodeLimit = args.get(WorkflowConstants.PROCESS_ARGS, Long.class);
			NodeIterator nodeIterator = pagePublishNode.getNodes();
			if (nodeIterator.getSize() > nodeLimit) {
				while (nodeIterator.hasNext()) {
					Node lastNode = nodeIterator.nextNode();
					if (!nodeIterator.hasNext()) {
						lastNode.remove();
					}
				}
			}
		} catch (RepositoryException e) {
			LOG.error("Respository Exception thrown {}", e);
		}
	}

	/**
	 * Gets the recipients details.
	 *
	 * @param resourceResolver  the resource resolver
	 * @param assignee          the assignee
	 * @param emailTemplateBean the email template bean
	 * @return the recipients details
	 * @throws RepositoryException the repository exception
	 */
	public static Map<String, String> getRecipientsDetails(ResourceResolver resourceResolver, List<String> assignee) throws RepositoryException {
		Map<String, String> recepientsMap = new HashMap<>();
		if (null != assignee && !assignee.isEmpty()) {
			for (String assign : assignee) {
				recepientsMap.putAll(getRecipientsMap(assign, resourceResolver, false));
			}

		}
		return recepientsMap;
	}

	/**
	 * Gets the recipients map.
	 *
	 * @param assign            the assign
	 * @param resourceResolver  the resource resolver
	 * @param emailTemplateBean the email template bean
	 * @param flag              the flag
	 * @return the recipients map
	 * @throws RepositoryException the repository exception
	 */
	public static Map<String, String> getRecipientsMap(String assign, ResourceResolver resourceResolver,
			 boolean flag) throws RepositoryException {
		LOG.debug("Entering WorkflowHelper.getRecipientsMap");
		UserManager userManager = resourceResolver.adaptTo(UserManager.class);
		Map<String, String> recepientsMap = new HashMap<>();
		List<Authorizable> assignees = getAuthorizables(userManager, assign, flag);
		for (Authorizable temp : assignees) {
			if (null != temp) {
				UserInfoBean userInfo = getAuthorizableUserInfo(temp.getPath(), resourceResolver);
				EmailTemplateBean emailTemplateBean = new EmailTemplateBean();
				if (StringUtils.isNotEmpty(userInfo.getFirstName()) && StringUtils.isNotEmpty(userInfo.getLastName())) {

					emailTemplateBean.setRecipientUser(
							userInfo.getFirstName() + CommonConstants.SPACE + userInfo.getLastName());
				} else if (StringUtils.isNotEmpty(userInfo.getFirstName())) {
					emailTemplateBean.setRecipientUser(userInfo.getFirstName());
				} else if (StringUtils.isNotEmpty(userInfo.getLastName())) {
					emailTemplateBean.setRecipientUser(userInfo.getLastName());
				} else {
					emailTemplateBean.setRecipientUser(temp.getID());
				}

				if (StringUtils.isNotEmpty(userInfo.getEmail())) {
					recepientsMap.put(userInfo.getEmail(), emailTemplateBean.getRecipientUser());
				}
			}
		}

		return recepientsMap;
	}
	
    /**
     * Method to fetch members from given Authorizable Group.
     *
     * @param userManager
     * @param authorizableValue
     * @return
     * @throws RepositoryException
     */
    public static List<Authorizable> getAuthorizables(UserManager userManager, String authorizableValue, boolean addGroups) throws RepositoryException {
        List<Authorizable> authorizables = new ArrayList<>();
        if (!StringUtils.isEmpty(authorizableValue)) {
	        if(authorizableValue.contains("(") && authorizableValue.contains(")")){
	        	authorizableValue = StringUtils.substringBetween(authorizableValue, "(", ")");
	        }
	        Authorizable authorizable = userManager.getAuthorizable(authorizableValue);
	        if (null != authorizable && authorizable.isGroup()) {
	            authorizables = getGroupAuthorizables(authorizableValue, authorizable, addGroups);
	        } else {
	            if(!addGroups)
	                authorizables.add(authorizable);
	        }
        }
        return authorizables;
    }
	
    
    private static List<Authorizable> getGroupAuthorizables(String authStr, Authorizable authorizable, boolean addGroups) throws RepositoryException{
        Group group = (Group) authorizable;
        Iterator<Authorizable> iterator = group.getDeclaredMembers();
        List<Authorizable> authorizables = new ArrayList<>();
        while (iterator.hasNext()) {
            Authorizable temp = iterator.next();
            if (temp.isGroup()) {
                if(addGroups){
                    authorizables.add(temp);
                }
            }
            else {
                if(!addGroups)
                    authorizables.add(temp);
            }
        }

        /* Iterator<Group> groupIterator = authorizable.declaredMemberOf();
        List<Authorizable> groupAuthorizables = new ArrayList<>();
        while (groupIterator.hasNext()) {
            Authorizable groupTemp = groupIterator.next();
            if(!groupTemp.getID().contains("everyone")) {
                groupAuthorizables.add(groupTemp);
            }
        }

        List<Authorizable> taggedGrpAuthorizables = new ArrayList<>();
        for (Authorizable parent:groupAuthorizables) {
            Group taggedGroup = (Group) parent;
            Iterator<Authorizable> taggedGroupIterator = taggedGroup.getDeclaredMembers();
            while (taggedGroupIterator.hasNext()) {
                Authorizable temp = taggedGroupIterator.next();
                if (!temp.isGroup()) {
                    taggedGrpAuthorizables.add(temp);
                }
            }
        }
        if(!taggedGrpAuthorizables.isEmpty()) {
            taggedGrpAuthorizables.addAll(authorizables);
        } */

        return !authorizables.isEmpty()? authorizables : new LinkedList<>();
    }
        
	/**
	 * Escape string.
	 *
	 * @param escape the escape
	 * @return the string
	 */
	public static String escapeString(String escape) {
		if (null != escape && StringUtils.isNotBlank(escape)) {
			return escape.replaceAll(" ", "&nbsp;");
		} else {
			return StringUtils.EMPTY;
		}
	}

	public static List<Authorizable> fetchUsersFromGroup(ResourceResolver resourceResolver,String partnerGroup) throws RepositoryException {
		
		List<Authorizable> authorizables = new ArrayList<>();
		
		UserManager userManager = resourceResolver.adaptTo(UserManager.class);
		
		Authorizable authorizable = userManager.getAuthorizable(partnerGroup);
        if (null != authorizable && authorizable.isGroup()) {
            authorizables = getGroupAuthorizables(partnerGroup, authorizable, false);
        }
		
		return authorizables;
	}
}
