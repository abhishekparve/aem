package com.bdb.aem.core.services.solr.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

/**
 * The Class FetchingToolsServiceImplTest.
 */
@ExtendWith(MockitoExtension.class)
class FetchingToolsServiceImplTest {

	/** The fetching tools service impl. */
	@InjectMocks
	FetchingToolsServiceImpl fetchingToolsServiceImpl;

	/** The solr config. */
	@Mock
	BDBSearchEndpointService solrConfig;

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The solr search service. */
	@Mock
	SolrSearchService solrSearchService;

	/** The server. */
	@Mock
	HttpSolrClient server;
	
	/** The solr query response. */
	@Mock
	QueryResponse solrQueryResponse;
	
	/** The solr document. */
	@Mock
	SolrDocument solrDocument;

	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;
	/** Mock ResourceResolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The catalog structure update service. */
	@Mock
	CatalogStructureUpdateService catalogStructureUpdateService;

	/**
	 * Setup.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws LoginException the login exception
	 */
	@BeforeEach
	public void setup() throws IOException, LoginException {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Test get cell type data.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SolrServerException the solr server exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void testGetCellTypeData() throws IOException, SolrServerException, RepositoryException {
		SolrDocumentList docList = new SolrDocumentList();
		List<SolrDocument> solrList = new ArrayList<>();
		LinkedHashMap<String, Object> m = new LinkedHashMap<String, Object>();
		String[] specificity = { "TCR VÎ±7.2" };
		m.put("secreted", specificity);
		String[] cloneName = { "CD27" };
		m.put("cellSurface", cloneName);
		String[] dyeName = { "STAT6" };
		m.put("intracellularTxn", dyeName);
		m.put("cellId", "e2486fc7-bd53-40a8-9d39-1f8b3a6d5e00");
		String[] subset = { "f3c51c32-43ee-462e-8921-0e1ed596d24a" };
		m.put("subsetCells", subset);
		String[] relevantPanels = { "BD Horizon Dri Memory T Cell Panel" };
		m.put("relevantPanels", relevantPanels);

		solrList.add(solrDocument);
		SolrDocument solrDoc = new SolrDocument(m);
		docList.add(solrDoc);
		lenient().when(solrSearchService.solrClient("us")).thenReturn(server);
		assertNotNull(server);
		lenient().when(server.query(Mockito.any())).thenReturn(solrQueryResponse);
		when(solrQueryResponse.getResults()).thenReturn(docList);
		String jsonString = "[\"e2486fc7-bd53-40a8-9d39-1f8b3a6d5e00\",\"2f925af3-6a7c-41f1-822a-70d229f35b4d\",\"fa6ab341-28b6-4b69-8cec-faf8884391ed\"]";
		JsonArray subSetArray = new JsonParser().parse(jsonString).getAsJsonArray();
		fetchingToolsServiceImpl.getCellTypeData("us", solrSearchService, subSetArray, "cellType",
				catalogStructureUpdateService, resourceResolver, "Human");
	}

	/**
	 * Test get panel type data.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SolrServerException the solr server exception
	 * @throws RepositoryException the repository exception
	 */
	@Test
	void testGetPanelTypeData() throws IOException, SolrServerException, RepositoryException {
		SolrDocumentList docList = new SolrDocumentList();
		List<SolrDocument> solrList = new ArrayList<>();
		LinkedHashMap<String, Object> m = new LinkedHashMap<String, Object>();
		String[] specificity = { "TCR VÎ±7.2" };
		m.put("secreted", specificity);
		String[] cloneName = { "CD27" };
		m.put("cellSurface", cloneName);
		String[] dyeName = { "STAT6" };
		m.put("intracellularTxn", dyeName);
		m.put("color", "yellow");
		m.put("panelType", "panelType");
		m.put("panelDescription",
				"<div><p class='panel-accordion__table-caption'>The BD Horizon Dri Memory T Cell Panel is a 7-color flow cytometry panel designed to identify major subsets of human CD4⁺ or CD8⁺ T cells, including naïve, stem cell memory, central memory, effector memory and terminally differentiated effector memory re-expressing CD45RA (EMRA) T cells. This panel is available as unit sized, preformulated and performance-optimized dried cocktail. </p><div class='panel-accordion__img-section'><img src='/etc.clientlibs/bdb-aem/clientlibs/clientlib-base/resources/images/modal-large.png' alt='image alt text' class='panel-accordion__img loading' data-ll-status='loading'></div><div> <p class='panel-accordion__desc-title'>Identification of naïve and memory T cell subsets using the BD Horizon Dri Memory T Cell Panel.</p><p class='panel-accordion__desc'>Representative analysis of whole blood from healthy human subjects (N=3). After surface marker staining, cells were lysed with BD FACS Lysing Solution. Lymphocytes were first identified based on light scatter properties (not shown). From the lymphocyte gate, CD3⁺ total T cells and CD4⁺ and CD8⁺ subsets thereof were then defined. From either CD4⁺ or CD8⁺ T cell gate, central memory (CM), effector memory (EM) and effector memory RA (EMRA) subsets were identified based on differential expression of CD45RA and CD197 (CCR7). From the CD45RA⁺CCR7⁺ gate, CD95⁺ stem cell memory and CD95⁻ naïve T cells were further identified. The histogram overlays show the different patterns of CD27 expression within the distinct T cell subsets. The BD Horizon Dri Memory T Cell Panel showed equivalent performance relative to its counterpart liquid panel (not shown). Samples were acquired on a 3-laser, 12-color BD FACSLyric Flow Cytometer. Data analysis was performed using FlowJo v10 Software. </p></div><div class='experimental-table panel-accordion__table'> <div class='table-header-section'> <p class='table-header-section__title'>Experimental Info</p></div><table class='table table-bordered'> <thead> <tr> <th>Application</th> <th>Description</th> </tr></thead> <tbody> <tr> <td class='laser-item tbl-row__cell' rowspan='1'><span>Panel Type</span></td><td class=''>BD Horizon™ Dri Small Batch Panels</td></tr><tr class='tbl-row'> <td class='laser-item tbl-row__cell' rowspan='2'><span>Surface Marker Stain</span></td><td class=''>Red Blood Cell Lysis (Wash) </td></tr><tr class='tbl-row'> <td class=''>3-Laser, 12-Color (2UV/6 violet/ 4 blue)</td></tr><tr class='tbl-row'> <td class='laser-item tbl-row__cell' rowspan='1'><span>Panel Tested on</span></td><td class=''>3-laser, 12-color (5 violet/4 blue/3 red) BD FACSLyric Flow Cytometer</td></tr></tbody> </table> </div><div class='panel-accordion__table'> <div class='table-header-section'> <p class='table-header-section__title'>Companion Products</p></div><table class='table table-bordered'> <thead> <tr> <th>Product</th> <th>Application</th> <th>Catalog Number</th> </tr></thead> <tbody> <tr class='tbl-row'> <td class='laser-item tbl-row__cell' rowspan='1'><span>BD Horizon Brilliant Stain Buffer</span> </td><td class=''>Optimal Stain Conditions</td><td class=''>563794</td></tr></tbody> </table> </div><a href='/dam/bdb/tools/icm/marketing-documents/BD Horizon Dri Memory T cell RUO.pdf' target='_blank' class='btn btn-secondary panel-accordion__learn-more'>Learn More about this Panel</a></div>");
		String[] subset = { "f3c51c32-43ee-462e-8921-0e1ed596d24a" };
		m.put("subsetCells", subset);
		String[] relevantPanels = { "BD Horizon Dri Memory T Cell Panel" };
		m.put("relevantPanels", relevantPanels);

		solrList.add(solrDocument);
		SolrDocument solrDoc = new SolrDocument(m);
		docList.add(solrDoc);
		lenient().when(solrSearchService.solrClient("us")).thenReturn(server);
		assertNotNull(server);
		lenient().when(server.query(Mockito.any())).thenReturn(solrQueryResponse);
		when(solrQueryResponse.getResults()).thenReturn(docList);
		String jsonString = "[\"TBMNK Backbone panel\",\"B cell subset panel - 3 Lasers\"]";
		JsonArray subSetArray = new JsonParser().parse(jsonString).getAsJsonArray();
		fetchingToolsServiceImpl.getCellTypeData("us", solrSearchService, subSetArray, "panelType",
				catalogStructureUpdateService, resourceResolver, "");
	}

	/**
	 * Gets the sub set cell id.
	 *
	 * @return the sub set cell id
	 */
	@Test
	public void getSubSetCellId() {
		String jsonString = "[{\"subsetCells\":[\"1000\",\"1001\"]}]";
		JsonArray subSetArray = new JsonParser().parse(jsonString).getAsJsonArray();
		assertNotNull(subSetArray);
		fetchingToolsServiceImpl.getSubCellIds(subSetArray);
	}

	/**
	 * Gets the solr result json.
	 *
	 * @return the solr result json
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void getSolrResultJson() throws SolrServerException, IOException {
		SolrDocumentList docList = new SolrDocumentList();
		SolrQuery solrQuery = new SolrQuery("q", "*:*");
		lenient().when(solrSearchService.solrClient("us")).thenReturn(server);
		assertNotNull(server);
		lenient().when(server.query(Mockito.any())).thenReturn(solrQueryResponse);
		when(solrQueryResponse.getResults()).thenReturn(docList);
		fetchingToolsServiceImpl.getSolrResultJson(server, solrQuery);
	}

}
