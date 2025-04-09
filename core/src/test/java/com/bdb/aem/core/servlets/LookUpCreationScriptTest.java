package com.bdb.aem.core.servlets;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
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

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.lenient;

/**
 * The Class LookUpCreationScriptTest.
 */

@ExtendWith({ MockitoExtension.class })
public class LookUpCreationScriptTest {
	@InjectMocks
	LookUpCreationScript lookUpCreationScript;
	@Mock
	private Resource hpresource, resource, variantHpResource, baseProductResource;
	@Mock
	private QueryBuilder queryBuilder;
	@Mock
	private ResourceResolver resourceResolver;
	@Mock
	private SlingHttpServletRequest request;
	@Mock
	private SlingHttpServletResponse response;
	@Mock
	private Session session;
	@Mock
	private Node baseMaterialNoLookUpNode, materialNoLookUpNode, baseNode;
	@Mock
	private Hit hit;
	@Mock
	private Query query;
	@Mock
	private ResourceResolverFactory resourceResolverFactory;
	@Mock
	private ValueMap valueMap;
	@Mock
	private SearchResult searchResult;
	@Mock
	transient BDBSearchEndpointService searchConfig;
	@Mock
	Object obj;
	private static final String assetPath = "/content/dam/pankaj.pdf";
	private Long resultCount = 1L;
	List<Hit> hitList = new ArrayList();
	@Test
	public void testDoGet() throws IOException, RepositoryException {
		hitList.add(hit);
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		lenient().when(resourceResolver.getResource(assetPath)).thenReturn(hpresource);
		lenient().when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);
		lenient().when(query.getResult()).thenReturn(searchResult);
		lenient().when(searchResult.getTotalMatches()).thenReturn(resultCount);
		lenient().when(searchConfig.getCatalogStructureNodeType()).thenReturn("sling:Folder");
		lookUpCreationScript.doGet(request, response);
	}

	@Test
	public void testGetLookUpPathFromProductId() throws IOException, RepositoryException {
		lookUpCreationScript.getLookUpPathFromProductId("12345", "materialNumber");
		lookUpCreationScript.getLookUpPathFromProductId("12345", "baseMaterialNumber");
	}

}