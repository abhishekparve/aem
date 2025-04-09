package com.bdb.aem.core.workflows;

import static org.mockito.Mockito.lenient;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.service.component.annotations.Reference;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.services.workflows.BDBDocReplicationIndexingWorkflow;
import com.day.cq.dam.api.Rendition;
@ExtendWith({ MockitoExtension.class })
public class AllScientificThumbnailImagesIndexingWorkflowTest {
	@InjectMocks
	AllScientificThumbnailImagesIndexingWorkflow AllScientificThumbnailImagesIndexingWorkflow;

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The b DB workflow config service. */
	@Mock
	BDBWorkflowConfigService bDBWorkflowConfigService;

	/** The resource resolver. */
	@Mock
	private ResourceResolver resourceResolver;

	/** The session. */
	@Mock
	private Session session;

	/** The work item. */
	@Mock
	private WorkItem workItem;

	/** The Work flow data. */
	@Mock
	private WorkflowData WorkFlowData;

	/** The object. */
	@Mock
	private Object object;

	/** The workflow session. */
	@Mock
	private WorkflowSession workflowSession;
	@Mock
	SolrSearchService solrSearchService;
	@Mock
	private Resource adminPageRes;

	/** The args. */
	@Mock
	private MetaDataMap args;

	/** The workflow helper service. */
	@Mock
	private WorkflowHelperService workflowHelperService;
	@Mock
	BDBSearchEndpointService searchConfig;
	@Mock
	ValueMap valueMap;
	/** The Constant assetPath. */
	private static final String assetPath = "/content/dam/pankaj.pdf";
	@Test
	void executeTest() throws WorkflowException, LoginException, RepositoryException {
		lenient().when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		AllScientificThumbnailImagesIndexingWorkflow.execute(workItem, workflowSession, args);
	}
}
