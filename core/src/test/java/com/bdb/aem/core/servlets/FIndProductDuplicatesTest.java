package com.bdb.aem.core.servlets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.google.gson.JsonObject;

import junitx.util.PrivateAccessor;

@ExtendWith(MockitoExtension.class)
public class FIndProductDuplicatesTest {

	@InjectMocks
	FIndProductDuplicates findProductDuplicates;
	
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

	@Mock
	ResourceResolver resourceResolver;

	@Mock
	Session session;

	@Mock
	RequestPathInfo requestPathInfo;
	
	@Mock
	BDBSearchEndpointService searchConfig;
	
	/** The resources. */
	@Mock
	Iterator<Resource> resources;

	/** The next resource. */
	@Mock
	Resource nextResource, hpNodeResource, parentResource,varientResource, lookUpResource;

	/** The query. */
	@Mock
	Query query;

	/** The query builder. */
	@Mock
	QueryBuilder queryBuilder;

	/** The search result. */
	@Mock
	SearchResult searchResult;

	@Mock
	ValueMap varientValueMap;
	
	@Mock
	CatalogStructureUpdateService catalogUpdateService;

	/** The request object. */
	private JsonObject requestObject;
	
	private JsonObject variantJson = new JsonObject();
	private JsonObject baseProductJson = new JsonObject();
	
	/** The request 1. */
	private String request1;

	/** The request 2. */
	private String request2;

	@BeforeEach
	void setUp() throws IOException, NoSuchFieldException {
		lenient().when(request.getReader()).thenReturn(reader);
		request1 = "{\"test\":Test}";
		variantJson.addProperty("variantResource", "variantResource");
		baseProductJson.addProperty("baseResource", "baseResource");
		requestObject = new JsonObject();
		requestObject.addProperty("test", "Test");
		request2 = null;
		lenient().when(reader.readLine()).thenReturn(request1, request2);
		when(request.getResourceResolver()).thenReturn(resourceResolver);
		
		when(searchConfig.getCatalogStructureNodeType()).thenReturn("nt:SlingFolder");
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		
		lenient().when(hpNodeResource.getValueMap()).thenReturn(varientValueMap);
		PrivateAccessor.setField(findProductDuplicates, "variantJson", variantJson);
		PrivateAccessor.setField(findProductDuplicates, "baseProductJson", baseProductJson);

		when(response.getWriter()).thenReturn(printWriter);
	}

	@Test
	public void doPostTest() throws IOException, RepositoryException {

		lenient().when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		lenient().when(queryBuilder.createQuery(any(), any())).thenReturn(query);
		lenient().when(query.getResult()).thenReturn(searchResult);
		lenient().when(searchResult.getResources()).thenReturn(resources);
		lenient().when(resources.hasNext()).thenReturn(true).thenReturn(false);
		lenient().when(resources.next()).thenReturn(nextResource);
		lenient().when(nextResource.getParent()).thenReturn(parentResource);
		lenient().when(nextResource.getName()).thenReturn("variantResource");
		lenient().when(parentResource.getName()).thenReturn("baseResource");
		lenient().when(nextResource.hasChildren()).thenReturn(true).thenReturn(false);
		lenient().when(nextResource.getChild("hp")).thenReturn(hpNodeResource);
		lenient().when(catalogUpdateService.getProductFromLookUp(resourceResolver, "variantResource", CommonConstants.MATERIAL_NUMBER)).thenReturn(lookUpResource);
		lenient().when(lookUpResource.getPath()).thenReturn("lookupPath");
		when(response.getWriter()).thenReturn(printWriter);
		findProductDuplicates.doPost(request, response);
	}


}
