package com.bdb.aem.core.servlets.solr;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.settings.SlingSettingsService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
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

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@ExtendWith(MockitoExtension.class)
class GenericSearchServletTest {
	@InjectMocks
	GenericSearchServlet genericSearchServlet;
	
	/** The solr search service. */
	@Mock
	SolrSearchService solrSearchService;

	/** The solr config. */
	@Mock
	BDBSearchEndpointService solrConfig;
	
	@Mock
	BufferedReader reader;

	/** The sling settings service. */
	@Mock
	SlingSettingsService slingSettingsService;

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/** The SlingHttpServletRequest. */
	@Mock
	SlingHttpServletRequest request;

	/** The SlingHttpServletResponse. */
	@Mock
	SlingHttpServletResponse response;

	/** The PrintWriter. */
	@Mock
	PrintWriter writer;

	/** The HttpSolrClient. */
	@Mock
	HttpSolrClient server;

	/** The ResourceResolverFactory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;
	
	/** The ResourceResolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The ServletInputStream. */
	@Mock
	ServletInputStream stream;

	/** The RequestPathInfo. */
	@Mock
	RequestPathInfo requestPathInfo;

	/** The QueryResponse. */
	@Mock
	QueryResponse sitesQueryResponse;

	/** The SolrQuery. */
	@Mock
	SolrQuery solrQuery;

	/** The SolrDocumentList. */
	@Mock
	SolrDocumentList sitesSolrDocs;

	/** The Iterator. */
	@Mock
	Iterator<SolrDocument> iterator;


	/** The Iterator. */
	@Mock
	Iterator<Entry<String, Object>> itr;

	/** The List. */
	@Mock
	List<FacetField> sitesFacetFieldList;

	/** The Iterator. */
	@Mock
	Iterator<FacetField> facetItr;
	
	@Mock
	CatalogStructureUpdateService catalogStructureUpdateService;

	@BeforeEach
	void setUp() throws Exception {
		String st = "{\"query\":\"celltype:HSC\",\"country\":\"us\",\"sort\":{\"cellType\":\"asc\",\"sortVar\":\"desc\"},\"fields\":[\"aiy\",\"test\"],\"pagination\":{\"start\":1,\"rows\":5},\"filters\":{\"filter1\":\"test one\",\"filter2\":\"test two\"}}";
		Reader inputString = new StringReader(st);
		BufferedReader reader = new BufferedReader(inputString);
		SolrDocumentList docList = new SolrDocumentList();
		SolrDocument solrDocument = new SolrDocument();
		solrDocument.addField("key", "value");
		docList.add(solrDocument);

		lenient().when(solrConfig.getContentPageCollectionName()).thenReturn("contentCollection");
		lenient().when(solrSearchService.solrClient("us")).thenReturn(server);
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		lenient().when(request.getReader()).thenReturn(reader);

		lenient().when(response.getWriter()).thenReturn(writer);
		lenient().when(server.query(Mockito.any())).thenReturn(sitesQueryResponse);
		lenient().when(sitesQueryResponse.getResults()).thenReturn(docList);
		lenient().when(sitesSolrDocs.iterator()).thenReturn(iterator);
		lenient().when(iterator.hasNext()).thenReturn(true, false);
		lenient().when(iterator.next()).thenReturn(solrDocument);
		
	}

	@Test
	public void doPostTest()
	{
		genericSearchServlet.doPost(request, response);
		assertNotNull(response);
	}
}
