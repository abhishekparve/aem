package com.bdb.aem.core.services.impl;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.mail.MessagingException;

import org.apache.commons.mail.EmailException;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
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
import org.osgi.service.component.annotations.Reference;

import com.bdb.aem.core.bean.AssetBean;
import com.bdb.aem.core.config.ReportGenerateConfig;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.SendEmailService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.day.cq.replication.ReplicationException;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class ReportGenerationServiceImplTest {
	@InjectMocks
	ReportGenerationServiceImpl reportGenerationServiceImpl;

	/** Mock ResourceResolverFactory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** Mock ResourceResolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** Mock Resource */
	@Mock
	Resource resource, resourceRes, lookUpResource, hpNodeRes;

	@Mock
	Node variantNode, node;

	@Mock
	private List<AssetBean> assetBean;

	@Mock
	private SendEmailService sendEmailService;

	@Mock
	private SendEmailService emailService;

	private String reportRecipients;
	@Mock
	private Query query;
	@Mock
	QueryBuilder queryBuilder;
	@Mock
	private Iterator<Resource> resources;

	/** The search result. */
	@Mock
	private SearchResult searchResult;
	@Mock
	Session session;
	@Mock
	SolrSearchService solrSearchService;
	@Mock
	BDBSearchEndpointService searchConfig;
	@Mock
	ReportGenerateConfig config;
	@Mock
	ValueMap valueMap;
	@Mock
	Property value;
	@Mock
	HttpSolrClient server;
	@Mock
	QueryResponse sitesQueryResponse;
	@Mock
	SolrDocumentList sitesSolrDocs;
	@Mock
	Iterator<SolrDocument> iterator;
	@Mock
	SolrDocument solrDocument;
	@Mock
	Iterator<Map.Entry<String, Object>> itr;
	@Mock
	Map.Entry<String, Object> map;
	@Mock
	Object obj;
	private String commerceContentPath = "/content/bdb";

	private Long resultCount = 1L;
	private static final String HP_NODE_NAME = "hp";
	private static final String NON_CLICNICAL_NODE = "medias";

	private static final String BASE_PREFIX = "_base";
	@Mock
	Hit hit;

	@BeforeEach
	void setUp() throws Exception {
		lenient().when(CommonHelper.getReadServiceResolver(resolverFactory)).thenReturn(resourceResolver);
		Map<String, String> params = new HashMap<>();
		params.put("path", "path");
		params.put("p.limit", "1");
		params.put("property.value", "true");
		params.put("property", "isVariant");
		params.put("type", "Page");
		List<Hit> hitList = new ArrayList<>();
		hitList.add(hit);
		lenient().when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);
		lenient().when(query.getResult()).thenReturn(searchResult);
		lenient().when(searchResult.getTotalMatches()).thenReturn(resultCount);
		lenient().when(searchResult.getResources()).thenReturn(resources);
		lenient().when(searchResult.getHits()).thenReturn(hitList);
		lenient().when(hit.getPath()).thenReturn("hitPath");
		lenient().when(resourceResolver.getResource("hitPath")).thenReturn(resourceRes);
		lenient().when(resourceRes.getName()).thenReturn("materialNumber");
	}

	@Test
	void testActivate() throws Exception {
		lenient().when(config.damAssetPath()).thenReturn("/content/dam/bdb/products/global");
		reportGenerationServiceImpl.activate(config);
	}
	@Test
	void testFetchReportData() throws Exception {
		lenient().when(solrSearchService.solrClient("us")).thenReturn(server);
		lenient().when(server.query(Mockito.any())).thenReturn(sitesQueryResponse);
		lenient().when(sitesQueryResponse.getResults()).thenReturn(sitesSolrDocs);
		lenient().when(sitesSolrDocs.iterator()).thenReturn(iterator);
		lenient().when(iterator.hasNext()).thenReturn(true,false);
		lenient().when(iterator.next()).thenReturn(solrDocument);
		lenient().when(solrDocument.iterator()).thenReturn(itr);
		lenient().when(itr.hasNext()).thenReturn(true,false);
		lenient().when(itr.next()).thenReturn(map);
		lenient().when(map.getKey()).thenReturn(BASE_PREFIX);
		reportGenerationServiceImpl.fetchReportData();
	}
	}

