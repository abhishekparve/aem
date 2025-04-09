package com.bdb.aem.core.services.impl;

import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.service.component.annotations.Reference;

import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.search.QueryBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class ReplicationServiceImplTest {
	@InjectMocks
	ReplicationServiceImpl replicationServiceImpl;

	/** Mock ResourceResolverFactory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** Mock ResourceResolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** Mock Resource */
	@Mock
	Resource resource, templateRsrc, jcrRes;
	/** The request. */
	@Mock
	SlingHttpServletRequest request;
	/** The query builder. */
	@Mock
	QueryBuilder queryBuilder;

	/** The replicator. */
	@Mock
	Replicator replicator;
	@Mock
	Session session;
	private String Path = "/content/bdb_base";

	@Test
	void testReplicate() throws RepositoryException, LoginException, ReplicationException {
		JsonObject jsonObject = new JsonObject();
		JsonArray hitsArray = new JsonArray();
		jsonObject.add("hits", hitsArray);
		jsonObject.addProperty("key", "value");
		lenient().when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		replicationServiceImpl.replicate(jsonObject, resourceResolver, session);
		replicationServiceImpl.removeQoutes(Path);
		
	}
	@Test
	void testGetSubLists() throws RepositoryException, LoginException, ReplicationException {
		List<String> list = new ArrayList<>();
		list.add("resourcesFromQuery");
		replicationServiceImpl.getSubLists(list, 100);
	}
}
