package com.bdb.aem.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.IcmCellHierarchyModel;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.IcmSolrSearchQueriesService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.bdb.aem.core.models.IcmCellTypeListModel;
import com.bdb.aem.core.models.IcmStepsListModel;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

/**
 * The Class IcmCellHierarchyModelImplTest.
 * @author phphanindra
 * @version 1.0
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class IcmCellHierarchyModelImplTest {

	@InjectMocks
	IcmCellHierarchyModelImpl icmCellHierarchyModelImpl;
	
	@Mock
	IcmSolrSearchQueriesService icmSolrSearchQueriesService;
	
	@InjectMocks
	IcmCellTypeListModel icmCellTypeListModel;
	
	@Mock
	ResourceResolver resourceResolver;
	
	@Mock
	ExternalizerService externalizerService;
	
	@Mock
	BDBSearchEndpointService bdbSearchEndpointService;
	
	@Mock
	SolrSearchService solrSearchService;
	
	@Mock
	BDBApiEndpointService bdbApiEndpointService;
	
	@InjectMocks
	IcmStepsListModel icmStepsListModel;
	
	@InjectMocks
	CommonHelper commonHelper;
	
	@Mock
	Page cp;
	
	
	/** The dropdown items multifield. */
	private List<IcmCellTypeListModel> cellTypes;
	
	private List<IcmStepsListModel> steps;
	
	private List<Resource> reactivityList;
	
	/** The scientific resource. */
	private Resource reactivityResource;
	
	/** The context aem. */
	private AemContext contextAem;
	
	
	/** The current page. */
	@Mock
	Page currentPage;
	
	@Mock
	ValueMap vp;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		String jsonString = "[\"CD2\",\r\n" + 
				"          \"CD25 [upregulated]\",\r\n" + 
				"          \"CD27\",\r\n" + 
				"          \"CD28\",\r\n" + 
				"          \"CD3 (pan)\",\r\n" + 
				"          \"CD5\",\r\n" + 
				"          \"CD7\",\r\n" + 
				"          \"CD8\"]";
		JsonArray markers = new JsonParser().parse(jsonString).getAsJsonArray();
		JsonArray cellsData = new JsonArray();
		JsonObject facetObject = new JsonObject();
		facetObject.addProperty("cellId", "ef19ff64-8ae5-4057-9e94-d63e5161b55d");
		facetObject.addProperty("cellImage", "cellImage");
		facetObject.addProperty("cellDescription", "cellDescription");
		facetObject.add("subsetCells", markers);
		
		facetObject.add("cellSurface", markers);
		facetObject.add("relevantPanels",markers);
		facetObject.add("intracellularTxn", markers);
		facetObject.add("secreted", markers);
		cellsData.add(facetObject);
		
		Map<String, Object> reactivityResourceProperty = new HashMap<>();		
		reactivityResourceProperty.put("reactivity", "Human");
		reactivityResource = contextAem.create().resource("/root/scientificLibrary", reactivityResourceProperty);
		reactivityList = Arrays.asList(reactivityResource);
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "reactivityList", reactivityList);
		
		PrivateAccessor.setField(icmCellTypeListModel, "cellLabel", "CMP");
		PrivateAccessor.setField(icmCellTypeListModel, "cell", "ef19ff64-8ae5-4057-9e94-d63e5161b55d");
		PrivateAccessor.setField(icmCellTypeListModel, "parentCell", "e9c37901-3a09-4173-96a1-340f128252de");
		cellTypes = Arrays.asList(icmCellTypeListModel);
		
		PrivateAccessor.setField(icmStepsListModel, "stepTitle", "stepTitle");
		PrivateAccessor.setField(icmStepsListModel, "firstWord", "firstWord");
		PrivateAccessor.setField(icmStepsListModel, "description", "description");
		PrivateAccessor.setField(icmStepsListModel, "stepImg", "stepImg");
		PrivateAccessor.setField(icmStepsListModel, "stepImgAlt", "stepImgAlt");
		steps = Arrays.asList(icmStepsListModel);
		
		
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "cellPageTitle", "Interactive Cell Mapping");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "cellPageDesc", "cellPageDesc");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "cellPageLogo", "/icons/cell-map.svg"); // cellMapImg response name
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "cellPageLogoAlt", "cell map");			// cellMapImgAltTxt
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "getStartedctaText", "Get Started");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "generalInfoLabel", "generalInfoLabel");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "viewScientificResourcesLabel", "viewScientificResourcesLabel");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "launchIconPath", "launchIconPath");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "launchIconAltText", "launchIconAltText");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "seeMarkers", "seeMarkers");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "cellSurfaceLabel", "cellSurfaceLabel");
		
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "intercellularLabel", "intercellularLabel");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "secretedProteinsLabel", "secretedProteinsLabel");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "viewCellSubsets", "viewCellSubsets"); 
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "viewPanelButtonLabel", "viewPanelButtonLabel");			
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "editCTAText", "editCTAText");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "selectedCellDesc", "selectedCellDesc");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "noPanelsTextAndLink", "noPanelsTextAndLink");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "searchPageUrl", "/content/bdb/language-masters/en/search-results");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "backCTAText", "backCTAText");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "leftArrow", "leftArrow");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "leftArrowAltText", "leftArrowAltText");
		
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "filterLabel", "filterLabel");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "applyFilterBtnText", "applyFilterBtnText");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "tooltipIconSrc", "tooltipIconSrc"); 
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "tooltipIconAltText", "tooltipIconAltText");			
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "instrumentConfigLabel", "instrumentConfigLabel");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "panelTypeLabel", "panelTypeLabel");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "numberOfParametersLabel", "numberOfParametersLabel");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "gt18", "gt18");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "ltet18", "ltet18");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "ltet12", "ltet12");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "ltet8", "ltet8");
		
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "panelReagentsLabel", "panelReagentsLabel");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "addSelectionToCartLabel", "addSelectionToCartLabel");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "selectUnselectAllLabel", "selectUnselectAllLabel"); 
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "upArrowIcon", "upArrowIcon");			
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "upArrowAltText", "upArrowAltText");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "downArrowIcon", "downArrowIcon");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "downArrowAltText", "downArrowAltText");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "laserLineLabel", "laserLineLabel");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "markerLabel", "markerLabel");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "fluorochromeLabel", "fluorochromeLabel");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "cloneLabel", "cloneLabel");
		
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "volumePerTestLabel", "volumePerTestLabel");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "catalogNumberLabel", "catalogNumberLabel");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "selectLabel", "selectLabel"); 
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "disclaimerLabel", "disclaimerLabel");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "emptyStateImgSrc", "emptyStateImgSrc");			
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "emptyStateImgAltText", "emptyStateImgAltText");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "noPanelsTitle", "noPanelsTitle");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "noPanelsDesc", "noPanelsDesc");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "contactUsLabel", "contactUsLabel");
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "clearFiltersLabel", "clearFiltersLabel");
		
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "cellTypes", cellTypes);
		PrivateAccessor.setField(icmCellHierarchyModelImpl, "steps", steps);
		lenient().when(currentPage.getAbsoluteParent(4)).thenReturn(cp);
		lenient().when(currentPage.getProperties()).thenReturn(vp);
		lenient().when(vp.containsKey(CommonConstants.REGION)).thenReturn(true);
		lenient().when(vp.get(CommonConstants.REGION, String.class)).thenReturn(CommonConstants.CONST_DEFAULT_REGION);
		lenient().when(vp.containsKey(CommonConstants.LANGUAGE)).thenReturn(true);
		lenient().when(vp.get(CommonConstants.LANGUAGE, String.class)).thenReturn(CommonConstants.CONST_DEFAULT_LANGUAGE);
		lenient().when(vp.containsKey(CommonConstants.COUNTRY)).thenReturn(true);
		lenient().when(vp.get(CommonConstants.COUNTRY, String.class)).thenReturn(CommonConstants.CONST_DEFAULT_COUNTRY);
		
		lenient().when(icmSolrSearchQueriesService.getCellsData(CommonConstants.CONST_DEFAULT_COUNTRY, bdbSearchEndpointService, solrSearchService, "Human", "\"ef19ff64-8ae5-4057-9e94-d63e5161b55d\"")).thenReturn(cellsData);
		lenient().when(externalizerService.getFormattedUrl("/content/bdb/language-masters/en/search-results", resourceResolver)).thenReturn("/en/search-results");
		lenient().when(bdbSearchEndpointService.getIcmToolsServicePath()).thenReturn("/resources/mock/api/interactiveCellMapping/getCellSelectionData/GET.json");
		lenient().when(vp.containsKey(CommonConstants.HYBRIS_SITE_ID)).thenReturn(true);
		lenient().when(vp.get(CommonConstants.HYBRIS_SITE_ID, String.class)).thenReturn(CommonConstants.HYBRIS_SITE_ID);
		lenient().when(bdbApiEndpointService.getBDBHybrisDomain()).thenReturn("hybrisDomain");
		lenient().when(bdbApiEndpointService.getQuickAddBulkEntryEndpoint()).thenReturn("hybrisPoint "+CommonConstants.HYBRIS_SITE_LITERAL);
	}

	/**
	 * Test init.
	 */
	@Test
	void testInit() {
		icmCellHierarchyModelImpl.init();
		assertNotNull(icmCellHierarchyModelImpl.getCellPageLabels());
		assertNotNull(icmCellHierarchyModelImpl.getCellPageConfig());
	}
	
}
