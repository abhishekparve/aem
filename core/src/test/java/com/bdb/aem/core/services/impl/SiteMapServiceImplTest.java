package com.bdb.aem.core.services.impl;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.day.crx.JcrConstants;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.sling.api.resource.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.*;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

/**
 * The Class SiteMapServiceImplTest.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class SiteMapServiceImplTest {

	/** The value page resource type. */
	String VALUE_PAGE_RESOURCE_TYPE = "bdb/components/structure/page";

	/** The value template. */
	String VALUE_TEMPLATE = "/conf/bdb/settings/wcm/templates/content-page";

	/** The value page title. */
	String VALUE_PAGE_TITLE = "SAMPLE PAGE TITLE";

	String P_SITEMAP_INDEX_NAME = "sitemap_index.xml";

	/** The site map serivce. */
	@InjectMocks
	SiteMapServiceImpl siteMapSerivceImpl;

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;
	/** The resource. */
	@Mock
	Resource resource, hpResource, indexResource, jcrResources;

	/** The current page. */
	@Mock
	Page currentPage;

//	@Mock
//	Page page;

	/** The page manager. */
	@Mock
	PageManager pageManager;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The session. */
	@Mock
	Session session;
//
//	@Mock
//	Node fileNode;
//	
//    @Mock
//	Node resNode;

	/** The node. */
	@Mock
	Node node, fileNode, resNode;

	/** The page queue. */
	@Mock
	List<Page> pageQueue;

	@Mock
	Calendar lastModified;

	/** The output factory. */
	@Mock
	XMLOutputFactory outputFactory;

	@Mock
	InputStream is;
	@Mock
	Replicator replicator;
	/** The result. */
	@Mock
	StringWriter result;

	/** The stream. */
	@Mock
	XMLStreamWriter stream;

	@Mock
	PageFilter pageFilter;

	@Mock
	ValueMap varientValueMap, generateValueMap;
	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The parent resource. */
//	@Mock
//	Resource parentResource;

	@Mock
	Binary contentValue;

	/** The value factory. */
	@Mock
	ValueFactory valueFactory;
	@Mock
	BDBSearchEndpointService solrConfig;

	@Mock
	SolrSearchService solrSearchService;
	@Mock
	ExternalizerService externalizerService;
	
	@Mock
	HttpSolrClient solar;
	@Mock
	QueryResponse solrQueryResponse;
	@Mock
	SolrDocument solrDocs;
	@Mock
	Iterator<Page> countPage;
	@Mock
	Iterator<SolrDocument> iterators;
	@Mock
	SolrDocumentList solrDocsList;
	@Mock
	Iterator<Entry<String, Object>> itr;
	@Mock
	Object object;
	Entry<String, Object> map;
	Boolean lastSiteMap;

	private String path = "/content/bdb";
	private String solrContent = "bdbcontent";
	private String[] paths = { "/content/dam-india", "/content/bdb-USA" };
	private String specific = "/content/dam/assets";
	private String resourcePath = "/content/bdb/ecommerce";
	private String JCR_PRIORITY = "pagePriority";
	private String CHANGE_FREQ = "changefreq";
	private String PRIORITY = "priority";
	private String JCR_CHANGE_FREQ = "changeFrequency";

	@BeforeEach
	void setUp() throws Exception {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
	}

	@Test
	void testSiteMapToJCR()
			throws LoginException, RepositoryException, XMLStreamException, IOException, SolrServerException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(jcrResources);
		lenient().when(jcrResources.adaptTo(Session.class)).thenReturn(session);
		lenient().when(valueFactory.createBinary(is)).thenReturn(contentValue);
		lenient().when(session.getValueFactory()).thenReturn(valueFactory);
		lenient().when(jcrResources.adaptTo(Node.class)).thenReturn(node);
		lenient().when(node.hasNode(Mockito.any())).thenReturn(true);
		lenient().when(node.getNode(Mockito.any())).thenReturn(node);
		siteMapSerivceImpl.writeSiteMapToJCR(jcrResources, result, P_SITEMAP_INDEX_NAME, session);
	}

	@Test
	void testcreateSiteMapIndexFile()
			throws LoginException, RepositoryException, XMLStreamException, IOException, SolrServerException {
		lenient().when(resource.adaptTo(Node.class)).thenReturn(node);
		lenient().when(session.getValueFactory()).thenReturn(valueFactory);
		lenient().when(node.hasNode(P_SITEMAP_INDEX_NAME)).thenReturn(true);
		lenient().when(node.getNode(P_SITEMAP_INDEX_NAME)).thenReturn(fileNode);
		lenient().when(fileNode.getNode(JcrConstants.JCR_CONTENT)).thenReturn(resNode);
		siteMapSerivceImpl.createSiteMapIndexFile(resource, session, result);
	}

	@Test
	void testGetXMLStream() throws Exception {
		List<String> pageQueue = new ArrayList<>();
		pageQueue.add("pageQueue");
		lenient().when(pageManager.getPage(Mockito.anyString())).thenReturn(currentPage);
		lenient().when(currentPage.getPath()).thenReturn("content/path/pdp");
		lenient().when(externalizerService.getFormattedUrlForPublish("content/path/pdp", resourceResolver))
				.thenReturn("loc");
		lenient().when(currentPage.getProperties()).thenReturn(varientValueMap);
		lenient().when(varientValueMap.get(JCR_PRIORITY, StringUtils.EMPTY)).thenReturn("Activate");
		lenient().when(varientValueMap.get(JCR_CHANGE_FREQ, StringUtils.EMPTY)).thenReturn("Activate");
		siteMapSerivceImpl.getXMLStream(pageQueue, resourceResolver, 0, lastSiteMap, hpResource, pageManager,5000);
	}

	@Test
	void testGenerateMasterIndexSiteMap()
			throws LoginException, RepositoryException, XMLStreamException, IOException, SolrServerException {
		lenient().when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(indexResource);
		lenient().when(resourceResolver.resolve(Mockito.anyString())).thenReturn(indexResource);
		lenient().when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		lenient().when(pageManager.getContainingPage(indexResource)).thenReturn(currentPage);
		lenient().when(currentPage.listChildren()).thenReturn(countPage);
		lenient().when(countPage.hasNext()).thenReturn(true, false);
		lenient().when(countPage.next()).thenReturn(currentPage);
		lenient().when(currentPage.getPath()).thenReturn(path);
		lenient().when(resourceResolver.getResource(path + (CommonConstants.SINGLE_SLASH + P_SITEMAP_INDEX_NAME)))
				.thenReturn(indexResource);
		lenient().when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		lenient().when(indexResource.getValueMap()).thenReturn(varientValueMap);
		lenient().when(varientValueMap.get(Mockito.any())).thenReturn(lastModified);
		lenient().when(session.getValueFactory()).thenReturn(valueFactory);

		lenient().when(resource.adaptTo(Node.class)).thenReturn(node);
		lenient().when(session.getValueFactory()).thenReturn(valueFactory);
		lenient().when(node.hasNode(Mockito.any())).thenReturn(true);
		lenient().when(node.getNode(Mockito.any())).thenReturn(node);
		lenient().when(resourceResolver.resolve(Mockito.anyString())).thenReturn(resource);
		lenient()
				.when(externalizerService.getFormattedUrlForPublish("/content/bdb/sitemap_index.xml", resourceResolver))
				.thenReturn("loc");
		siteMapSerivceImpl.generateMasterIndexSiteMap(paths, resourceResolver);
	}

	/**
	 * Test generate site map.
	 *
	 * @throws LoginException      the login exception
	 * @throws RepositoryException the repository exception
	 * @throws XMLStreamException  the XML stream exception
	 */
	@Test
	void testGenerateSiteMap() throws Exception, LoginException, RepositoryException, XMLStreamException {
		final String[] paths = { "/content/dam/pdp./assets-image" };
		Long count = 4L;
		lenient().when(resourceResolver.resolve("/content/dam/pdp./assets-image")).thenReturn(resource);
		lenient().when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		lenient().when(pageManager.getContainingPage(resource)).thenReturn(currentPage);
		lenient().when(pageManager.getPage(Mockito.anyString())).thenReturn(currentPage);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(currentPage.listChildren()).thenReturn(countPage);
		lenient().when(currentPage.listChildren(Mockito.any(), Mockito.anyBoolean())).thenReturn(countPage);
		lenient().when(countPage.hasNext()).thenReturn(true, false);
		lenient().when(countPage.next()).thenReturn(currentPage);
		lenient().when(currentPage.getProperties()).thenReturn(varientValueMap);
		lenient().when(varientValueMap.get(Mockito.any(), Mockito.any())).thenReturn("Activate");
		lenient().when(currentPage.getPath()).thenReturn("content/path/pdp.");
		lenient().when(resource.getPath()).thenReturn("/bin");
		lenient().when(currentPage.getLastModified()).thenReturn(lastModified);
		lenient().when(varientValueMap.get("pagePriority", StringUtils.EMPTY)).thenReturn("pagePriority");
		lenient().when(varientValueMap.get("changeFrequency", StringUtils.EMPTY)).thenReturn("changeFrequency");
		lenient().when(resource.adaptTo(Node.class)).thenReturn(node);
		lenient().when(session.getValueFactory()).thenReturn(valueFactory);
		lenient().when(node.hasNode(Mockito.any())).thenReturn(true);
		lenient().when(node.getNode(Mockito.any())).thenReturn(node);
		lenient().when(resourceResolver.resolve(Mockito.anyString())).thenReturn(resource);
		lenient().when(resource.getValueMap()).thenReturn(varientValueMap);
		lenient().when(varientValueMap.get(Mockito.any())).thenReturn(lastModified);
		lenient().when(solrSearchService.solrClient(Mockito.any())).thenReturn(solar);
		lenient().when(solar.query(Mockito.any())).thenReturn(solrQueryResponse);
		lenient().when(solrQueryResponse.getResults()).thenReturn(solrDocsList);
		lenient().when(solrDocsList.getNumFound()).thenReturn(count);
		lenient().when(solrDocsList.iterator()).thenReturn(iterators);
		lenient().when(iterators.hasNext()).thenReturn(true, false);
		lenient().when(iterators.next()).thenReturn(solrDocs);
		lenient().when(solrDocs.iterator()).thenReturn(itr);
		lenient().when(externalizerService.getFormattedUrlForPublish("/bincontent/path/pdp.", resourceResolver))
				.thenReturn("text");
		lenient().when(externalizerService.getFormattedUrlForPublish("/bin/sitemap0.xml", resourceResolver))
				.thenReturn("loc");
		lenient().when(itr.hasNext()).thenReturn(false);
		lenient().when(itr.next()).thenReturn(map);
		siteMapSerivceImpl.generateSiteMap(paths, resourceResolver,5000);

	}

}
