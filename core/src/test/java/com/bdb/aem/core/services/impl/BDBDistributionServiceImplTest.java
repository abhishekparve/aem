package com.bdb.aem.core.services.impl;

import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.nodetype.NodeType;

import org.apache.poi.ss.formula.functions.T;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.distribution.Distributor;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobExecutionContext;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.ProcessProductDocumentService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.BDBMode;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;
@ExtendWith({ MockitoExtension.class })
public class BDBDistributionServiceImplTest {
	@InjectMocks
	BDBDistributionServiceImpl bDBDistributionServiceImpl;
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The solr search service. */
	@Mock
	SolrSearchService solrSearchService;

	/** The resource resolver. */

	@Mock
	ResourceResolver serviceResolver;
	@Mock
	Resource resource,res,child;
	@Mock
	private Asset finalAsset;

	/** The session. */

	@Mock
	Session session;

	/** The current node. */
	@Mock
	private Node currentNode;
	@Mock
	Job job;
	/** The Constant assetPath. */
	private static final String assetPath = "/content/dam/pankaj.pdf";
	@Mock
	Distributor distributor;
	@Mock
	JobExecutionContext context;
	BDBMode mode;
	List<String> paths=new ArrayList<>();;
	@Mock
	 Iterator<Resource> childIt;
	@Mock
	Iterable<Resource> childItr;
	@Mock
	NodeType type;
	Integer chunkSize=10;
	
	@Test
	void TestDistribute() throws SolrServerException, LoginException, RepositoryException {
		paths.add("/content/dam/pankaj.pdf");
		lenient().when(serviceResolver.getResource(assetPath)).thenReturn(res);
		lenient().when(res.getChildren()).thenReturn(childItr);
		lenient().when(childItr.iterator()).thenReturn(childIt);
		lenient().when(childIt.hasNext()).thenReturn(true,false);
		lenient().when(childIt.next()).thenReturn(child);
		lenient().when(child.adaptTo(Node.class)).thenReturn(currentNode);
		lenient().when(currentNode.getPrimaryNodeType()).thenReturn(type);
		lenient().when(type.getName()).thenReturn("primarynodename");
		bDBDistributionServiceImpl.distribute(serviceResolver, mode, chunkSize, context, paths);
	}
	
		
}
