package com.bdb.aem.core.servlets;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.service.component.annotations.Reference;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.UpdateProductSchemaService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.PageManager;
import com.google.gson.JsonObject;

/**
 * The Class UpdateProductSchemaServletTest.
 */
@ExtendWith(MockitoExtension.class)
class UpdateProductSchemaServletTest {

	/** The update product schema servlet. */
	@InjectMocks
	UpdateProductSchemaServlet updateProductSchemaServlet;

	/** The request. */
	@Mock
	SlingHttpServletRequest request;

	/** The request parameter. */
	@Mock
	RequestParameter requestParameter;

	/** The response. */
	@Mock
	SlingHttpServletResponse response;

	/** The print writer. */
	@Mock
	PrintWriter printWriter;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The resource resolver factory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The update product schema service. */
	@Mock
	UpdateProductSchemaService updateProductSchemaService;

	/** The marketing resource. */
	@Mock
	Resource marketingResource, marketingParentResource, productResource, hpResource;

	/** The replicator. */
	@Mock
	Replicator replicator;

	/** The session. */
	@Mock
	Session session;

	/** The marketing node. */
	@Mock
	Node marketingNode, hpNode;

	@Mock
	PageManager pageManager;
	@Mock
	ExternalizerService externalizerService;
	@Mock
	SolrSearchService solrSearchService;

	/**
	 * Inits the.
	 *
	 * @throws LoginException the login exception
	 */
	@BeforeEach
	public void init() throws LoginException {
		lenient().when(CommonHelper.getServiceResolverReplication(resourceResolverFactory))
				.thenReturn(resourceResolver);
	}

	/**
	 * Testdo GET.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws RepositoryException 
	 */
	@Test
	public void testdoGET() throws IOException, RepositoryException {
		assertNotNull(request);
		getResponse();
		//updateProductSchemaServlet.doGet(request, response);
	}

	/**
	 * Testdo post.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws RepositoryException 
	 */
	@Test
	void testdoPost() throws IOException, RepositoryException {
		assertNotNull(request);
		getResponse();
		//updateProductSchemaServlet.doPost(request, response);
	}

	/**
	 * Gets the response.
	 *
	 * @return the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws RepositoryException 
	 */
	public void getResponse() throws IOException, RepositoryException {
		JsonObject productJson1 = new JsonObject();
		JsonObject productJson = new JsonObject();
		productJson.addProperty("test", "test");
		lenient().when(updateProductSchemaService.getMarketingResource(request, resourceResolver, solrSearchService))
				.thenReturn(marketingResource);
		lenient().when(updateProductSchemaService.getProductAnnouncement(marketingResource, request, productJson1,
				resourceResolver, externalizerService, null, solrSearchService)).thenReturn(productJson);
		lenient().when(request.getParameter("activate")).thenReturn("us");
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(marketingResource.adaptTo(Node.class)).thenReturn(marketingNode);
		lenient().when(marketingResource.getParent()).thenReturn(marketingParentResource);
		lenient().when(marketingParentResource.getParent()).thenReturn(productResource);
		lenient().when(productResource.getChild("hp")).thenReturn(hpResource);
		lenient().when(hpResource.adaptTo(Node.class)).thenReturn(hpNode);
		lenient().when(response.getWriter()).thenReturn(printWriter);
		printWriter.write(productJson.toString());
	}

}
