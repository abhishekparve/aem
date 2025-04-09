package com.bdb.aem.core.util;

import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PropertyType;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;

import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.jcr.base.util.AccessControlUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.models.AreaOfFocusModel;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.impl.BDBApiEndpointServiceImpl;
import com.bdb.aem.core.util.WorkflowHelper.SimplePrincipal;
import com.bdb.aem.core.workflows.AllPDPIndexingWorkflow;

import junitx.util.PrivateAccessor;

@ExtendWith({ MockitoExtension.class })
public class WorkflowHelperTest {
	@InjectMocks
	WorkflowHelper workflowHelper;
	/** The resource resolver. */
	@Mock
	ResourceResolver resolver;

	/** The resource. */
	@Mock
	Resource resource;

	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The exclude util object. */
	@Mock
	ExcludeUtil excludeUtilObject;
	/** The WorkItem */
	@Mock
	WorkItem workItem;

	/** The WorkflowSession */
	@Mock
	WorkflowSession workflowSession;

	/** The MetaDataMap */
	@Mock
	MetaDataMap args;
	@Mock
	WorkflowData WorkFlowData;

	/** The object. */
	@Mock
	Object object;
	@Mock
	ValueMap valueMap;
	@Mock
	UserManager userManager;
	@Mock
	Session session;
	@Mock
	ValueFactory valueFactory;
	@Mock
	Value value;
	@Mock
	Group group;
	@Mock
	Node pagePublishNode;
	@Mock
	NodeIterator nodeIterator;
	@Mock
	Authorizable authorizable;
	Long nodeLimit = 3L;
	Long nodeitr = 5L;
	/** The Constant assetPath. */
	private static final String assetPath = "/content/dam/pankaj.pdf";
	String[] groupUsers = { "grpUser" };
	String[] userNames = { "userNames" };

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		bdbApiEndpointService = new BDBApiEndpointServiceImpl();
		PrivateAccessor.setField(bdbApiEndpointService, "bdbHybrisDomain", "https://api.domain.com");
		PrivateAccessor.setField(bdbApiEndpointService, "hybrisSignUpPreferenceEndpoint",
				"/hybris/{{site}}/hybrisSignUpPreferenceEndpoint");
	}

	/**
	 * Test get component node.
	 */
	@Test
	void testGetAuthorizableUserInfo() {
		lenient().when(workItem.getWorkflowData()).thenReturn(WorkFlowData);
		lenient().when(WorkFlowData.getPayload()).thenReturn(object);
		lenient().when(object.toString()).thenReturn(assetPath);
		lenient().when(resolver.getResource(assetPath)).thenReturn(resource);
		lenient().when(resource.getValueMap()).thenReturn(valueMap);
		workflowHelper.getPayloadPath(workItem);
		workflowHelper.getAuthorizableUserInfo(assetPath, resolver);

	}

	@Test
	void testCreateTemporaryGroup() throws Exception {
		lenient().when(session.getValueFactory()).thenReturn(valueFactory);
		lenient().when(valueFactory.createValue("groupGivenName", PropertyType.STRING)).thenReturn(value);
		lenient().when(userManager.createGroup("groupName", new SimplePrincipal("groupName"),
				"/home/groups/hillrom/tempworkflowgroup")).thenReturn(group);
		lenient().when(userManager.getAuthorizable("groupName")).thenReturn(group);
		lenient().when(resolver.adaptTo(Session.class)).thenReturn(session);
		// lenient().when(AccessControlUtil.getUserManager(session)).thenReturn(userManager);
		workflowHelper.createTemporaryGroup("groupName", userManager, session, "groupGivenName");
		workflowHelper.createUniqueGroupName("group");
		workflowHelper.addUsersToGroup(userManager, "groupName", groupUsers, session);
		workflowHelper.createGroupUser(resolver, groupUsers, userNames);
	}

	@Test
	void testParseUsers() throws Exception {
		workflowHelper.parseUserList(groupUsers);
		workflowHelper.parseUserNameList(groupUsers);
	}

	@Test
	void testCheckAndTrimNodes() throws Exception {
		lenient().when(args.get(WorkflowConstants.PROCESS_ARGS, Long.class)).thenReturn(nodeLimit);
		lenient().when(pagePublishNode.getNodes()).thenReturn(nodeIterator);
		lenient().when(nodeIterator.getSize()).thenReturn(nodeitr);
		lenient().when(nodeIterator.hasNext()).thenReturn(true, false);
		lenient().when(nodeIterator.nextNode()).thenReturn(pagePublishNode);
		workflowHelper.checkAndTrimNodes(session, args, pagePublishNode);
		workflowHelper.escapeString(assetPath);
		workflowHelper.hashCode();
	}

	@Test
	void testCheckAndTrimNohdes() throws Exception {
		List<String> assignee = new ArrayList<>();
		assignee.add("assignee");
		lenient().when(resolver.adaptTo(UserManager.class)).thenReturn(userManager);
		lenient().when(userManager.getAuthorizable("authorizableValue")).thenReturn(authorizable);
		workflowHelper.getAuthorizables(userManager, "(authorizableValue)", false);
		workflowHelper.getRecipientsMap("assign", resolver, false);
		workflowHelper.getRecipientsDetails(resolver, assignee);// fetchUsersFromGroup
		workflowHelper.fetchUsersFromGroup(resolver, "partnerGroup");
	}

}
