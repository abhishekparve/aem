package com.bdb.aem.core.listeners;

import static org.mockito.Mockito.lenient;

import java.util.Collections;
import java.util.List;

import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.event.jobs.JobManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;

import com.bdb.aem.core.listeners.IndexContentToSolrListener.Configuration;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.servlets.UpdateProductSchemaServlet;
import com.day.cq.replication.ReplicationAction;

import uk.org.lidalia.slf4jtest.LoggingEvent;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

@ExtendWith({ MockitoExtension.class })
public class IndexContentToSolrListenerTest {
	
	@InjectMocks
	IndexContentToSolrListener indexContentToSolrListener;

	/** The solr search service. */
	@Mock
	SolrSearchService solrSearchService;

	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The search config. */
	@Mock
	BDBSearchEndpointService searchConfig;

	/** The job manager. */
	@Mock
	JobManager jobManager;

	/** The workflow helper service. */
	@Mock
	WorkflowHelperService workflowHelperService;
	@Mock
	Configuration config;
	@Mock
	ReplicationAction replicationAction;
	@Mock
	ResourceResolver resourceResolver;
	@Mock
	Resource pageResource;
	@Mock
	ValueMap properties;
	String[] assetPaths = { "/content/bdb/na/us/en-us/products/instruments" };

	@Test
	void handleEvent() {
		Event resourceEvent = new Event("event/topic",
				Collections.singletonMap(SlingConstants.PROPERTY_PATH, "/content/test"));
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(pageResource);
		lenient().when(pageResource.adaptTo(ValueMap.class)).thenReturn(properties);
		lenient().when(config.baseAssetPath()).thenReturn(assetPaths);
		indexContentToSolrListener.activate(config);
		indexContentToSolrListener.checkIfPageIsSolrIndexable(resourceResolver,
				"/content/bdb/na/us/en-us/products/instruments");
		indexContentToSolrListener.checkIfValidAssetPath("/content/bdb/na/us/en-us/products/instruments");
	}

	@Test
	void getters() {
		indexContentToSolrListener.getAssetPath();
		indexContentToSolrListener.getBasePagePath();
		indexContentToSolrListener.getScientificResPath();
	}

}
