package com.bdb.aem.core.servlets;


import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.day.cq.replication.Replicator;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class DeleteProductDuplicatesTest {

	/** The CatalogUpdateServlet servlet. */
	@InjectMocks
	DeleteProductDuplicates deleteProductDuplicates;

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

	@Mock
	ResourceResolver resourceResolver;
	
	@Mock 
	Resource resource;
	
	@Mock 
	Node node;

	@Mock
	Session session;

	@Mock
	RequestPathInfo requestPathInfo;
	
	@Mock
	BaseServlet baseServlet;
	
	@Mock 
	Replicator replicator;
	
	@Mock
	TagManager tagManager;
	
	@Mock
	Tag tag;
	
	/** The request object. */
	private JsonObject requestObject;

	/** The request 1. */
	private String request1;

	/** The request 2. */
	private String request2;

	@BeforeEach
	void setUp() throws IOException, RepositoryException {
		when(request.getReader()).thenReturn(reader);
		
		JsonObject variantObject = new JsonObject();
		JsonObject baseProductObject = new JsonObject();
		JsonObject variantJsonObject = new JsonObject();
		JsonObject baseProductJsonObject = new JsonObject();
		JsonArray jsonArray = new JsonArray();
		jsonArray.add("test");
		variantObject.add("Duplicate", jsonArray);
		variantObject.addProperty("LookupPath", "LookupPath");
		baseProductObject.add("Duplicate", jsonArray);
		baseProductObject.addProperty("LookupPath", "LookupPath");
		variantJsonObject.add("12345_base", variantObject);
		baseProductJsonObject.add("123456_base", baseProductObject);
		requestObject = new JsonObject();
		requestObject.add("variant", variantJsonObject);
		requestObject.add("baseProduct", baseProductJsonObject);
		request1 = requestObject.toString();
		request2 = null;
		when(reader.readLine()).thenReturn(request1, request2);
		lenient().when(baseServlet.getRequestObject(request)).thenReturn(requestObject);
		when(request.getResourceResolver()).thenReturn(resourceResolver);
		when(resourceResolver.getResource("test")).thenReturn(resource);
		when(resource.adaptTo(Node.class)).thenReturn(node);
		when(node.getPath()).thenReturn("path");
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		when(response.getWriter()).thenReturn(printWriter);
	}

	@Test
	public void doPostTest() throws IOException {

		when(response.getWriter()).thenReturn(printWriter);
		deleteProductDuplicates.doPost(request, response);
	}
	
}

