package com.bdb.aem.core.models;

import static org.mockito.Mockito.lenient;

import java.util.Arrays;
import java.util.List;

import javax.script.Bindings;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;

import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.scripting.SlingBindings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * The Class FetchUsersFromGroupDropdownUseTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class FetchUsersFromGroupDropdownUseTest {
	
	/** The fetch users from group dropdown use. */
	@InjectMocks
	FetchUsersFromGroupDropdownUse fetchUsersFromGroupDropdownUse;
	
	/** The session. */
	@Mock
	Session session;
	
	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	
	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The mock bindings. */
	@Mock
	private Bindings mockBindings;
	
	/** The user manager. */
	@Mock
	UserManager userManager;

	/** The authorizable. */
	@Mock
	Authorizable authorizable;
	
	/** The temp 2. */
	@Mock
	Authorizable temp1, temp2;
	
	/** The assignees. */
	List<Authorizable> assignees;
	
	/**
	 * Test activate.
	 *
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void testActivate() throws RepositoryException {
		lenient().when(mockBindings.get("user")).thenReturn("(parentUserGroup)");
		lenient().when(mockBindings.get(SlingBindings.REQUEST)).thenReturn(request);
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(resourceResolver.adaptTo(UserManager.class)).thenReturn(userManager);
		lenient().when(userManager.getAuthorizable("parentUserGroup")).thenReturn(authorizable);
		fetchUsersFromGroupDropdownUse.activate();
	}
	
	/**
	 * Test repo exception.
	 *
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void testRepoException() throws RepositoryException {
		lenient().when(mockBindings.get("user")).thenReturn("(parentUserGroup)");
		lenient().when(mockBindings.get(SlingBindings.REQUEST)).thenReturn(request);
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(resourceResolver.adaptTo(UserManager.class)).thenReturn(userManager);
		lenient().when(userManager.getAuthorizable("parentUserGroup")).thenThrow(new RepositoryException());
		fetchUsersFromGroupDropdownUse.activate();
	}

	/**
	 * Test assignees list.
	 *
	 * @throws UnsupportedRepositoryOperationException the unsupported repository operation exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void testAssigneesList() throws UnsupportedRepositoryOperationException, RepositoryException {
		assignees = Arrays.asList(temp1, temp2);
		fetchUsersFromGroupDropdownUse.assigneesList(request, resourceResolver, assignees);
	}
}
