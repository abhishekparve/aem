package com.bdb.aem.core.services.tools.impl;

import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.google.gson.JsonObject;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class IndexServiceImplTest {

	@InjectMocks
	IndexServiceImpl indexServiceImpl;

	@Mock
	SolrSearchService solrSearchService;
	
	@Mock
	BDBSearchEndpointService solrConfig;

	@Mock
	HttpSolrClient httpSolrClient;

	@Mock
	ResourceResolver resourceResolver;

	@Test
	void testAntigen() {
		List<JsonObject> jsonList = new ArrayList<>();
		JsonObject json = new JsonObject();
		json.addProperty("specificity", "CD34");
		json.addProperty("Specificity", "CD34");
		json.addProperty("Cell Surface", "CD34");
		json.addProperty("secreted", "secreted");
		json.add("otherformatsColorMapping", new JsonObject());
		jsonList.add(json);
		lenient().when(solrSearchService.solrClient("us")).thenReturn(httpSolrClient);
		indexServiceImpl.indexToSolr(jsonList, "us", "antigen", resourceResolver, "CD34");
	}
	
	@Test
	void testClone() {
		List<JsonObject> jsonList = new ArrayList<>();
		JsonObject json = new JsonObject();
		json.addProperty("cloneName", "cloneName");
		json.add("otherformatsColorMapping", new JsonObject());
		jsonList.add(json);
		lenient().when(solrSearchService.solrClient("us")).thenReturn(httpSolrClient);
		indexServiceImpl.indexToSolr(jsonList, "us", "clone", resourceResolver, "CD34");
	}
	
	@Test
	void testPanelType() {
		List<JsonObject> jsonList = new ArrayList<>();
		JsonObject json = new JsonObject();
		json.addProperty("panelName", "panelName");
		json.addProperty("Panel Accordion Description", "panelName");
		json.add("otherformatsColorMapping", new JsonObject());
		jsonList.add(json);
		lenient().when(solrSearchService.solrClient("us")).thenReturn(httpSolrClient);
		lenient().when(solrConfig.countryToRegionAndLanguge()).thenReturn("us");
		indexServiceImpl.indexToSolr(jsonList, "us", "panelType", resourceResolver, "CD34");
	}
	
	@Test
	void testCellType() {
		List<JsonObject> jsonList = new ArrayList<>();
		JsonObject json = new JsonObject();
		json.addProperty("panelName", "panelName");
		json.addProperty("Secreted", "panelName");
		json.add("otherformatsColorMapping", new JsonObject());
		jsonList.add(json);
		lenient().when(solrSearchService.solrClient("us")).thenReturn(httpSolrClient);
		indexServiceImpl.indexToSolr(jsonList, "us", "cellType", resourceResolver, "CD34");
	}
}
