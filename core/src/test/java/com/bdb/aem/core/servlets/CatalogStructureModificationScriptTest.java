package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.lenient;

import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

/**
 * The Class CatalogStructureModificationScriptTest.
 */
@ExtendWith(MockitoExtension.class)
class CatalogStructureModificationScriptTest {
	
	/** The catalog structure modification script. */
	@InjectMocks
	CatalogStructureModificationScript catalogStructureModificationScript;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The response. */
	@Mock
	SlingHttpServletResponse response;

	/** The reader. */
	@Mock
	BufferedReader reader;
	
	/** The print writer. */
	@Mock
	PrintWriter printWriter;

	/** The catalog structure update service. */
	@Mock
	CatalogStructureUpdateService catalogStructureUpdateService;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The session. */
	@Mock
	Session session;

	/** The request path info. */
	@Mock
	RequestPathInfo requestPathInfo;
	
	/** The stream. */
	@Mock
	ServletInputStream stream;
	
	/** The query. */
	@Mock
	Query query;
	
	/** The query builder. */
	@Mock
	QueryBuilder queryBuilder;
	
	/** The search results. */
	@Mock
	SearchResult searchResults;
	
	/** The hit 1. */
	@Mock
	Hit hit1;
	
	/** The hits. */
	List <Hit> hits;
	
	/** The params. */
	Map<String, String> params = new HashMap<>();
	
	/** The hit resource. */
	@Mock
	Resource variantHpResource, hitResource;
	
	/** The variant hp node. */
	@Mock
	Node variantHpNode; 
	
	/** The property iterator. */
	@Mock
	PropertyIterator propertyIterator;
	
	/** The property. */
	@Mock
	Property property;

	/**
	 * Sets the up.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws RepositoryException the repository exception
	 */
	@BeforeEach
	void setUp() throws IOException, RepositoryException {
		when(response.getWriter()).thenReturn(printWriter);
		String str = "{\r\n" + 
				"    \"test\":\"test\",\r\n" + 
				"    \"rootPath\":\"/bdb/root\"\r\n" + 
				"}";
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
		ServletInputStream servletInputStream = new ServletInputStream() {
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}

			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener arg0) {
			}
		};
		lenient().when(request.getInputStream()).thenReturn(servletInputStream);
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		params.put(SolrSearchConstants.QUERY_BUILDER_PATH, "/bdb/root");
		params.put(SolrSearchConstants.QUERY_BUILDER_TYPE, JcrResourceConstants.NT_SLING_FOLDER);
		params.put(SolrSearchConstants.QUERY_BUILDER_PROPERTY, SolrSearchConstants.IS_VARIANT);
		params.put(SolrSearchConstants.QUERY_BUILDER_PROPERTY_VALUE, "true");
		params.put(SolrSearchConstants.QUERY_BUILDER_LIMIT, "-1");
		lenient().when(queryBuilder.createQuery(PredicateGroup.create(params), session)).thenReturn(query);
		lenient().when(query.getResult()).thenReturn(searchResults);
		hits = Arrays.asList(hit1);
		lenient().when(searchResults.getHits()).thenReturn(hits);
		lenient().when(hit1.getResource()).thenReturn(hitResource);
		lenient().when(hitResource.getChild(CommonConstants.HP)).thenReturn(variantHpResource);
		lenient().when(variantHpResource.adaptTo(Node.class)).thenReturn(variantHpNode);
		lenient().when(variantHpNode.getProperties()).thenReturn(propertyIterator);
		lenient().when(propertyIterator.hasNext()).thenReturn(true).thenReturn(false);
		lenient().when(propertyIterator.nextProperty()).thenReturn(property);
	}
	
	/**
	 * Test do post sling http servlet request sling http servlet response.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testDoPostSlingHttpServletRequestSlingHttpServletResponse() throws IOException {
		catalogStructureModificationScript.doPost(request, response);
	}
}
