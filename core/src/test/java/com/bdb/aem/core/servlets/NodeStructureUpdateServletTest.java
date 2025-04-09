package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.service.component.annotations.Reference;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.MoveAssetsProcessedToTempService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

@ExtendWith({ MockitoExtension.class })
public class NodeStructureUpdateServletTest {
	@InjectMocks
	NodeStructureUpdateServlet nodeStructureUpdateServlet;
	@Mock
	private Resource hpresource, resource, variantHpResource, baseProductResource;
	@Mock
	private QueryBuilder queryBuilder;
	@Mock
	private ResourceResolver resourceResolver;
	@Mock
	private SlingHttpServletRequest request;
	@Mock
	private SlingHttpServletResponse response;
	@Mock
	private Session session;
	@Mock
	private Node tempNode, rootNode;
	@Mock
	private Hit hit;
	@Mock
	private Query query;
	@Mock
	private SearchResult searchListResult;
	@Mock
	private ResourceResolverFactory resourceResolverFactory;
	@Mock
	private ValueMap valueMap;
	@Mock
	private BDBSearchEndpointService searchConfig;
	@Mock
	private SearchResult searchResult;
	@Mock
	transient Replicator replicator;
	@Mock
	NodeIterator nodes;
	@Mock
	Object obj;
	@Mock
	PrintWriter writer;
	private static final String assetPath = "/content/dam/pankaj.pdf";
	String Path = "/content/dam/pankaj.pdf";
	String currentNodeType = "currentNodeType";
	String finalNodeType = "finalNodeType";
	String doReplicate = "true";
	Map<String, String> queryParams = new HashMap<>();

	@Test
	public void testDoPost() throws IOException, RepositoryException, LoginException, ReplicationException {
		queryParams.put("path", "testPath");
		queryParams.put("currentNodeType", "currentNodeType");
		queryParams.put("finalNodeType", "finalNodeType");
		queryParams.put("doReplicate", "true");
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(response.getWriter()).thenReturn(writer);
		lenient().when(session.getNode(Path)).thenReturn(rootNode);
		lenient().when(rootNode.getNodes()).thenReturn(nodes);
		lenient().when(nodes.hasNext()).thenReturn(true, false);
		lenient().when(nodes.nextNode()).thenReturn(tempNode);
		lenient().when(tempNode.hasNodes()).thenReturn(false);
		lenient().when(tempNode.getPath()).thenReturn(Path);
		lenient().when(tempNode.isNodeType(currentNodeType)).thenReturn(true);
		nodeStructureUpdateServlet.doPost(request, response);
		nodeStructureUpdateServlet.recursiveCall(session, Path, currentNodeType, finalNodeType, doReplicate);
		nodeStructureUpdateServlet.recursiveCall(session, Path, currentNodeType, finalNodeType, "doReplicate");
		nodeStructureUpdateServlet.replicate(session, tempNode);
	}

	@Test
	public void testGetLookUpPathFromProductId()
			throws IOException, RepositoryException, LoginException, ReplicationException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(response.getWriter()).thenReturn(writer);
		lenient().when(session.getNode(Path)).thenReturn(rootNode);
		lenient().when(rootNode.getNodes()).thenReturn(nodes);
		lenient().when(nodes.hasNext()).thenReturn(true, false);
		lenient().when(nodes.nextNode()).thenReturn(tempNode);
		lenient().when(tempNode.hasNodes()).thenReturn(true);
		lenient().when(tempNode.getPath()).thenReturn(Path);
		lenient().when(tempNode.isNodeType(currentNodeType)).thenReturn(true);
		nodeStructureUpdateServlet.doPost(request, response);
		nodeStructureUpdateServlet.recursiveCall(session, Path, currentNodeType, finalNodeType, doReplicate);
		nodeStructureUpdateServlet.recursiveCall(session, Path, currentNodeType, finalNodeType, "doReplicate");
		nodeStructureUpdateServlet.replicate(session, tempNode);
	}

}