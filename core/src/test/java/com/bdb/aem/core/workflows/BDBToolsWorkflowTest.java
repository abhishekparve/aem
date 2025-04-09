package com.bdb.aem.core.workflows;

import static org.mockito.Mockito.lenient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.services.tools.impl.IndexServiceImpl;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.WorkflowConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * @author Rohith A
 *
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class BDBToolsWorkflowTest {

	private final AemContext aemContext = new AemContext();

	@InjectMocks
	BDBToolsWorkflow bdbToolsWorkflow;

	@Mock
	ResourceResolverFactory resourceResolverFactory;

	@Mock
	SolrSearchService solrSearchService;

	@Mock
	BDBSearchEndpointService solrConfig;

	@Mock
	WorkItem workItem;

	@Mock
	WorkflowData workflowData;

	@Mock
	WorkflowSession workflowSession;

	@Mock
	MetaDataMap args;

	@Mock
	HttpSolrClient server;

	@Mock
	ResourceResolver resourceResolver;

	@Mock
	QueryResponse queryResponse;

	@Mock
	SolrDocumentList solrDocumentList;

	@Mock
	SolrDocument solrDocument;

	@Mock
	Iterator<SolrDocument> solrDocumentItr;

	@Mock
	Iterator<Entry<String, Object>> solrDocItr;

	@Mock
	Entry<String, Object> map;

	@Mock
	Resource resource;

	@Mock
	Asset asset;

	@Mock
	Rendition rendition;

	@Mock
	InputStream inputStream;

	@Mock
	IndexServiceImpl indexServiceImpl;

	/**
	 * Sets the up.
	 *
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws LoginException
	 */
	@BeforeEach
	void setUp() throws IOException, LoginException {
		lenient().when(workItem.getWorkflowData()).thenReturn(workflowData);
		lenient().when(workflowData.getPayload()).thenReturn(args);
		lenient().when(solrConfig.getBasePagePath()).thenReturn("args");
		lenient().when(CommonHelper.getReadServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
	}

	@Test
	public void test() throws WorkflowException, SolrServerException, IOException {

		lenient().when(args.get(WorkflowConstants.PROCESS_ARGS, String.class)).thenReturn("Mouse#Mouse@cellType@US#CA");
		lenient().when(workflowData.getPayload()).thenReturn("/path/to/excel");

		lenient().when(solrSearchService.solrClient(Mockito.any())).thenReturn(server);
		lenient().when(server.query(Mockito.any())).thenReturn(queryResponse);

		lenient().when(queryResponse.getResults()).thenReturn(solrDocumentList);
		solrDocumentList.add(solrDocument);
		lenient().when(solrDocumentList.iterator()).thenReturn(solrDocumentItr);
		lenient().when(solrDocumentItr.hasNext()).thenReturn(true).thenReturn(false);
		lenient().when(solrDocumentItr.next()).thenReturn(solrDocument);
		lenient().when(solrDocument.iterator()).thenReturn(solrDocItr);
		lenient().when(solrDocItr.hasNext()).thenReturn(true).thenReturn(false);
		lenient().when(solrDocItr.next()).thenReturn(map);
		lenient().when(map.getKey()).thenReturn("key");
		lenient().when(map.getValue()).thenReturn("value");
		InputStream is = new FileInputStream("src/test/resources/com/bdb/aem/core/workflows/PanelTypeExcel.xls");
		lenient().when(resourceResolver.getResource("/path/to/excel")).thenReturn(resource);

		lenient().when(resource.adaptTo(Asset.class)).thenReturn(asset);
		lenient().when(asset.getOriginal()).thenReturn(rendition);
		lenient().when(asset.getMimeType()).thenReturn("application/vnd.ms-excel");
		lenient().when(rendition.getStream()).thenReturn(is);
		lenient().when(rendition.adaptTo(InputStream.class)).thenReturn(is);

		bdbToolsWorkflow.execute(workItem, workflowSession, args);
	}

}
