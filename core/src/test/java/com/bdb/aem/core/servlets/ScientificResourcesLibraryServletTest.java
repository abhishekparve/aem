package com.bdb.aem.core.servlets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.solr.SolrSearchService;

@ExtendWith(MockitoExtension.class)
class ScientificResourcesLibraryServletTest {

	/** SolrSearchService for Query */
	@Mock
	SolrSearchService solrSearchService;
	/** ResourceResolverFactory */
	@Mock
	ResourceResolverFactory resolverFactory;
	/** SlingHttpServletRequest */
	@Mock
	SlingHttpServletRequest request;
	/** SolrSearchService for Query */
	@Mock
	SlingHttpServletResponse response;
	/** ScientificResourcesLibraryServlet */
	@InjectMocks
	ScientificResourcesLibraryServlet scientificResourcesLibraryServlet;
	
	@Mock
	PrintWriter out;
	
		
	@BeforeEach
	public void init() throws IOException {
		Map<String,Object> jsonMap = new HashMap<>();
		Map<String,String> map = new HashMap<>();
		ArrayList<Map<String,String>> resourceList = new ArrayList<>();
		map.put("resourceName","resourceName");
		map.put("fileType", "fileType");
		map.put("resourceTitle", "resourceTitle");
		map.put("resourceDesc", "resourceDesc");
		map.put("downloadLink", "/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/clinical-diagnostics/multicolor-cocktails-and-kits-(ivd/ce-ivds)/333176_base/pdf/23-7480.pdf");
		resourceList.add(map);
		jsonMap.put("cardsData", resourceList);
		when(request.getParameter("pageNumber")).thenReturn("1");
		when(request.getParameter("pageCount")).thenReturn("5");
		when(request.getParameter("resourceType")).thenReturn("Data%20Sheet Product%20List");
		when(request.getParameter("searchTerm")).thenReturn("bd%20bio");
		when(request.getParameter("country")).thenReturn("CA");
		when(solrSearchService.getScientificResLib("Data Sheet,Product List", 0, 5,
				"bd bio","ca")).thenReturn(jsonMap);
		when(response.getWriter()).thenReturn(out);
	}
	@Test
	void scientificTestCase() throws IOException {
		scientificResourcesLibraryServlet.doGet(request, response);
		assertEquals("bd bio",scientificResourcesLibraryServlet.decodeParam("bd%20bio"));
		assertEquals(1,scientificResourcesLibraryServlet.convetStringToInt("1"));
	}

}
