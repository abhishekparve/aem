package com.bdb.aem.core.models.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.IcmCellHierarchyModel;
import com.bdb.aem.core.models.IcmCellTypeListModel;
import com.bdb.aem.core.models.IcmStepsListModel;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.IcmSolrSearchQueriesService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.ExcludeUtil;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * The Class IcmCellHierarchyModelImpl.
 */
@Model(adaptables ={ Resource.class,SlingHttpServletRequest.class }, adapters = { IcmCellHierarchyModel.class }, resourceType = {
		IcmCellHierarchyModelImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class IcmCellHierarchyModelImpl implements IcmCellHierarchyModel {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/ICM/v1/icmComponent";

	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(IcmCellHierarchyModelImpl.class);

	/** The cellPage Title. */
	@ValueMapValue
	@SerializedName("title")
	private String cellPageTitle;
	
	/** The Cell Page Description. */
	@ValueMapValue
	@SerializedName("description")
	private String cellPageDesc;
	
	
	/** The Cell Page Logo. */
	@ValueMapValue
	@SerializedName("cellMapImg")
	private String cellPageLogo;

	/** The Cell Page Logo Alt Text. */
	@ValueMapValue
	@SerializedName("cellMapImgAltTxt")
	private String cellPageLogoAlt;
	
	/** The getStartedctaText. */
	@ValueMapValue
	@SerializedName("getStartedctaText")
	private String getStartedctaText;
	
	
	/** The generalInfoLabel. */
	@ValueMapValue
	private String generalInfoLabel;
	
	/** The isPanelPage. */
	@ValueMapValue
	private String isPanelPage;
	
	/** The viewScientificResourcesLabel */
	@ValueMapValue
	private String viewScientificResourcesLabel;
	
	/** The launchIconPath */
	@ValueMapValue
	private String launchIconPath;

	/** The launchIconAltText */
	@ValueMapValue
	private String launchIconAltText;
	
	/** The seeMarkers */
	@ValueMapValue
	private String seeMarkers;
	
	/** The cellSurfaceLabel. */
	@ValueMapValue
	@Default(values = "cellSurface")
	private String cellSurfaceLabel;
	
	/** The intercellularLabel. */
	@ValueMapValue
	@Default(values = "intracellularTxn")
	private String intercellularLabel;
	
	/** The secretedProteinsLabel. */
	@ValueMapValue
	@Default(values = "secretedProteins")
	private String secretedProteinsLabel;
	
	/** The viewCellSubsets. */
	@ValueMapValue
	private String viewCellSubsets;
	
	/** The viewPanelButtonLabel. */
	@ValueMapValue
	private String viewPanelButtonLabel;
	
	/** The panelPageUrl. */
	@ValueMapValue
	private String panelPageUrl;
	
	/** The editCTAText. */
	@ValueMapValue
	private String editCTAText;
	
	/** The selectedCellDesc. */
	@ValueMapValue
	private String selectedCellDesc;
	
	/** The noPanelsTextAndLink. */
	@ValueMapValue
	private String noPanelsTextAndLink;
	
	/** The searchPageUrl. */
	@ValueMapValue
	private String searchPageUrl;
	
	/** The backCTAText. */
	@ValueMapValue
	@SerializedName("backCTAText")
	private String backCTAText;
	
	/** The leftArrow. */
	@ValueMapValue
	@SerializedName("leftArrow")
	private String leftArrow;
	
	/** The leftArrowAltText. */
	@ValueMapValue
	@SerializedName("leftArrowAltText")
	private String leftArrowAltText;
	
	/** The panelPageLabel. */
	@ValueMapValue
	@SerializedName("panelPageLabel")
	private String panelPageLabel;
	
	/** The allPanelTitle. */
	@ValueMapValue
	@SerializedName("allPanelTitle")
	private String allPanelTitle;
	
	/** The searchPlaceholder. */
	@ValueMapValue
	@SerializedName("searchPlaceholder")
	private String searchPlaceholder;
	
	/** The filterLabel. */
	@ValueMapValue
	private String filterLabel;
	
	/** The applyFilterBtnText. */
	@ValueMapValue
	private String applyFilterBtnText;
	
	/** The tooltipIconSrc. */
	@ValueMapValue
	private String tooltipIconSrc;
	
	/** The tooltipIconAltText. */
	@ValueMapValue
	private String tooltipIconAltText;
	
	/** The instrumentConfigLabel. */
	@ValueMapValue
	private String instrumentConfigLabel;
	
	/** The panelTypeLabel. */
	@ValueMapValue
	private String panelTypeLabel;
	
	/** The numberOfParametersLabel. */
	@ValueMapValue
	private String numberOfParametersLabel;
	
	/** Greater Than Eighteen Label. */
	@ValueMapValue
	private String gt18;
	
	/** Less Than or Equals of Eighteen Label */
	@ValueMapValue
	private String ltet18;
	
	/** Less Than or Equals of Twelve Label. */
	@ValueMapValue
	private String ltet12;
	
	/** Less Than or Equals of Eight Label. */
	@ValueMapValue
	private String ltet8;
	
	/** All Label. */
	@ValueMapValue
	private String All;
	
	/** The panelReagentsLabel. */
	@ValueMapValue
	private String panelReagentsLabel;
	
	/** The addSelectionToCartLabel. */
	@ValueMapValue
	private String addSelectionToCartLabel;
	
	/** The selectUnselectAllLabel. */
	@ValueMapValue
	private String selectUnselectAllLabel;
	
	/** The upArrowIcon. */
	@ValueMapValue
	private String upArrowIcon;
	
	/** The upArrowAltText. */
	@ValueMapValue
	private String upArrowAltText;
	
	/** The downArrowIcon. */
	@ValueMapValue
	private String downArrowIcon;
	
	/** The downArrowAltText. */
	@ValueMapValue
	private String downArrowAltText;
	
	/** The showingLabel. */
	@ValueMapValue
	private String showingLabel;
	
	/** The resultsForLabel. */
	@ValueMapValue
	private String resultsForLabel;
	
	
	
	/** The laserLineLabel. */
	@ValueMapValue
	private String laserLineLabel;
	
	/** The markerLabel. */
	@ValueMapValue
	private String markerLabel;
	
	/** The fluorochromeLabel. */
	@ValueMapValue
	private String fluorochromeLabel;
	
	/** The cloneLabel. */
	@ValueMapValue
	private String cloneLabel;
	
	/** The testPerKitLabel. */
	@ValueMapValue
	private String testPerKitLabel;
	
	/** The volumePerTestLabel. */
	@ValueMapValue
	private String volumePerTestLabel;
	
	/** The catalogNumberLabel. */
	@ValueMapValue
	private String catalogNumberLabel;
	
	/** The selectLabel. */
	@ValueMapValue
	private String selectLabel;
	
	/** The disclaimerLabel. */
	@ValueMapValue
	private String disclaimerLabel;
	
	/** The applicableCellType. */
	@ValueMapValue
	private String applicableCellType;
	
	/** The panelsTextLabel. */
	@ValueMapValue
	private String panelsTextLabel;
	
	/** The emptyStateImgSrc. */
	@ValueMapValue
	private String emptyStateImgSrc;
	
	/** The emptyStateImgAltText. */
	@ValueMapValue
	private String emptyStateImgAltText;
	
	/** The noPanelsTitle. */
	@ValueMapValue
	private String noPanelsTitle;
	
	/** The noPanelsDesc. */
	@ValueMapValue
	private String noPanelsDesc;
	
	/** The contactUsLabel. */
	@ValueMapValue
	private String contactUsLabel;
	
	/** The clearFiltersLabel. */
	@ValueMapValue
	private String clearFiltersLabel;
	
	/** The cell types multi field. */
	@ChildResource
	private List<IcmCellTypeListModel> cellTypes;
	
	/** The Steps multi field. */
	@ChildResource
	private List<IcmStepsListModel> steps;

	/** The cellSelectionLabels. */
	@SerializedName("cellSelectionLabels")
	private JsonElement cellSelectionLabels;
	
	/** The filterLabels. */
	@SerializedName("filterLabels")
	private JsonElement filterLabels;
	
	/** The stepsHolder. */
	@SerializedName("steps")
	private JsonElement stepsHolder;
	
	/** The panelLabels. */
	@SerializedName("panelLabels")
	private JsonElement panelLabels;
	
	/** The noFilterPanelsLabels. */
	@SerializedName("noFilterPanelsLabels")
	private JsonElement noFilterPanelsLabels;
	
	/** The icmToolConfig. */
	@SerializedName("icmToolConfig")
	private JsonElement icmToolConfig;
	
	/** The Reactivity multi field. */
	@ChildResource
	private List<Resource> reactivityList;
	
	@SerializedName("targetReactivity")
	private JsonElement targetReactivity;
	
	
	/** Inject public services */
	
	/** Inject bdbSearchEndpointService */
	@OSGiService
	BDBSearchEndpointService bdbSearchEndpointService;
	
	/** Inject solrSearchService */
	@OSGiService
	SolrSearchService solrSearchService;
	
	/** Inject icmSolrSearchQueriesService */
	@OSGiService
	IcmSolrSearchQueriesService icmSolrSearchQueriesService;
	
	/** Inject bdbApiEndpointService */
	@OSGiService
	BDBApiEndpointService bdbApiEndpointService;
	
	/** The current page. */
	@Inject
	Page currentPage;
	
	@OSGiService
	ExternalizerService externalizerService;
	
	@Inject
	ResourceResolver resourceResolver;
	
	/** Variable and Constants */
	
	/** cellPageData */
	private String cellPageLabels;
	
	/** cellPageData */
	private String cellPageConfig;
	

	/** SOLR_CELL_TYPE_VARIABLE */
	private static final String SOLR_CELL_TYPE_VARIABLE = "cellType";
	/** GET_PANEL_DATA */
	private static final String GET_PANEL_DATA = "getPanelData";
	/** GET_CELL_DATA */
	private static final String GET_CELL_DATA = "getCellData";
	
	/** GET_CELL_DATA */
	private static final String GET_PANEL_PAGE_INFO = "getPanelPageInfo";
	
	/** IS PANEL PAGE */
	private static final String IS_PANEL_PAGE = "isPanelPage";
	
	
	/** GET_CELL_DATA */
	private static final String SEARCH_PAYLOAD = "searchPayload";
	/** CELL_DESCRIPTION */
	private static final String CELL_DESCRIPTION = "cellDescription";
	/** REACTIVITY */
	private static final String REACTIVITY = "reactivity";
	/** SUBSETCELLS */
	private static final String SUBSETCELLS = "subsetCells";
	/** CELL_IMAGE */
	private static final String CELL_IMAGE = "cellImage";
	/** CELL_ID */
	private static final String CELL_ID = "cellId";
	/** INTRACELLULAR_TXN */
	private static final String INTRACELLULAR_TXN = "intracellularTxn";
	/** CELL_SURFACE */
	private static final String CELL_SURFACE = "cellSurface";
	/** SECRETED */
	private static final String SECRETED = "secreted";
	/** RELEVANT_PANELS */
	private static final String RELEVANT_PANELS = "relevantPanels";
	/** SUB_SET_CELL_IDS */
	private static final String SUB_SET_CELL_IDS = "subSetCellIds";
	/** PANEL_IDS */
	private static final String PANEL_IDS = "panelIds";
	/** CELL_NAME */
	private static final String CELL_NAME = "cellName";
	/** CELL_IMG */
	private static final String CELL_IMG = "cellImg";
	/** PARENT_ID */
	private static final String PARENT_ID = "parentId";
	
	/**
	 * HIDE ACTION CTA */
	private static final String HIDE_ACTION_CTA = "hideActionCta";
	/** MARKERS_LIST */
	private static final String MARKERS_LIST = "markersList";
	/** FILTER_LABEL */
	private static final String FILTER_LABEL = "filterLabel";
	/** APPLY_FILTER_BTN_TEXT */
	private static final String APPLY_FILTER_BTN_TEXT = "applyFilterBtnText";
	/** TOOL_TIP_ICON_SRC */
	private static final String TOOL_TIP_ICON_SRC = "tooltipIconSrc";
	/** TOOL_TIP_ICON_ALT_TEXT */
	private static final String TOOL_TIP_ICON_ALT_TEXT = "tooltipIconAltText";
	
	/** INSTRUMENT_CONFIG_LABEL */
	private static final String INSTRUMENT_CONFIG_LABEL = "instrumentConfigLabel";
	/** PANEL_TYPE_LABEL */
	private static final String PANEL_TYPE_LABEL = "panelTypeLabel";
	/** NUMBER_OF_PARAMS_LABEL */
	private static final String NUMBER_OF_PARAMS_LABEL = "numberOfParametersLabel";
	/** GREATER_THAN_EIGHTEEN_LABEL */
	private static final String GREATER_THAN_EIGHTEEN_LABEL = "gt18";
	/** LESS_THAN_OR_EQUALS_EIGHTEEN_LABEL */
	private static final String LESS_THAN_OR_EQUALS_EIGHTEEN_LABEL = "ltet18";
	/** LESS_THAN_OR_EQUALS_TWELVE_LABEL */
	private static final String LESS_THAN_OR_EQUALS_TWELVE_LABEL = "ltet12";
	/** LESS_THAN_OR_EQUALS_EIGHT_LABEL */
	private static final String LESS_THAN_OR_EQUALS_EIGHT_LABEL = "ltet8";
	/** ALL_LABEL */
	private static final String ALL_LABEL = "All";
	/** PANEL_REAGENTS_LABEL */
	private static final String PANEL_REAGENTS_LABEL = "panelReagentsLabel";
	/** ADD_SELECTION_TO_CART_LABEL */
	private static final String ADD_SELECTION_TO_CART_LABEL = "addSelectionToCartLabel";
	/** SELECT_UNSELECT_ALL_LABEL */
	private static final String SELECT_UNSELECT_ALL_LABEL = "selectUnselectAllLabel";
	/** UP_ARROW_ICON */
	private static final String UP_ARROW_ICON = "upArrowIcon";
	/** DOWN_ARROW_ICON */
	private static final String DOWN_ARROW_ICON ="downArrowIcon";
	/** UP_ARROW_ALT_TEXT */
	private static final String UP_ARROW_ALT_TEXT = "upArrowAltText";
	/** DOWN_ARROW_ALT_TEXT */
	private static final String DOWN_ARROW_ALT_TEXT = "downArrowAltText";
	
	/** LASER_LINE_LABEL */
	private static final String LASER_LINE_LABEL = "laserLineLabel";
	/** MARKER_LABEL */
	private static final String MARKER_LABEL = "markerLabel";
	/** FLUORO_CHROME_LABEL */
	private static final String FLUORO_CHROME_LABEL = "fluorochromeLabel";
	/** CLONE_LABEL */
	private static final String CLONE_LABEL = "cloneLabel";
	/** TEST_PER_KIT_LABEL */
	private static final String TEST_PER_KIT_LABEL = "testPerKitLabel";
	/** VOLUME_PER_TEST_LABEL */
	private static final String VOLUME_PER_TEST_LABEL = "volumePerTestLabel";
	/** CATALOG_NUMER_LABEL */
	private static final String CATALOG_NUMER_LABEL = "catalogNumberLabel";
	/** SELECT_LABEL */
	private static final String SELECT_LABEL = "selectLabel";
	/** DISCLAIMER_LABEL */
	private static final String DISCLAIMER_LABEL = "disclaimerLabel";
	/** APPLICABLE_CELL_TYPE */
	private static final String APPLICABLE_CELL_TYPE = "applicableCellType";
	/** PANELS_TEXT_LABEL */
	private static final String PANELS_TEXT_LABEL = "panelsTextLabel";
	/** EMPTY_STATE_IMG_SRC */
	private static final String EMPTY_STATE_IMG_SRC = "emptyStateImgSrc";
	/** EMPTY_STATE_IMG_ALT_TEXT */
	private static final String EMPTY_STATE_IMG_ALT_TEXT = "emptyStateImgAltText";
	/**NO PANEL TITLE */
	private static final String TITLE = "title";
	/** NO PANEL DESCRIPTION_INFO */
	private static final String DESCRIPTION_INFO = "descriptionInfo";
	/** CONTACT_US_LABEL */
	private static final String CONTACT_US_LABEL = "contactUsLabel";
	/** CLEAR_FILTERS_LABEL */
	private static final String CLEAR_FILTERS_LABEL = "clearFiltersLabel";
	/** CATEGORY_NAME */
	private static final String CATEGORY_NAME = "categoryName";
	/** LINKS_LIST */
	private static final String LINKS_LIST = "linksList";
	/** HUMAN */
	private static final String HUMAN = "Human";
	/** CELL_SELECTION_LIST */
	private static final String CELL_SELECTION_LIST = "cellSelectionList";
	/** GENERAL_INFO_LABEL */
	private static final String GENERAL_INFO_LABEL = "generalInfoLabel";
	/** viewScientificResourcesLabel_LABEL */
	private static final String VIEW_SCIENTIFIC_RESOURCES_LABEL = "viewScientificResourcesLabel";
	/** LAUNCH_ICON_PATH */
	private static final String LAUNCH_ICON_PATH = "launchIconPath";
	/** LAUNCH_ICON_ALT_TEXT */
	private static final String LAUNCH_ICON_ALT_TEXT = "launchIconAltText";
	/** SEE_MARKERS */
	private static final String SEE_MARKERS = "seeMarkers";
	/** VIEW_CELL_SUBSETS */
	private static final String VIEW_CELL_SUBSETS = "viewCellSubsets";
	/** VIEW_PANELBUTTON_LABELS */
	private static final String VIEW_PANELBUTTON_LABELS = "viewPanelButtonLabel";
	/** SHOWING_LABEL */
	private static final String SHOWING_LABEL = "showingLabel";
	/** RESULTS_FOR_LABEL */
	private static final String RESULTS_FOR_LABEL = "resultsForLabel";
	/** EDIT_CTS_TEXT */
	private static final String EDIT_CTS_TEXT = "editCTAText";
	/** SELECTED_CELL_DESC */
	private static final String SELECTED_CELL_DESC = "selectedCellDesc";
	/** NO_PANELS_TEXT_AND_LINK */
	private static final String NO_PANELS_TEXT_AND_LINK = "noPanelsTextAndLink";
	/** SEARCH_PAGE_URL */
	private static final String SEARCH_PAGE_URL = "searchPageUrl";
	/** SCIENTIFIC_RESOURCE_LINK */
	private static final String SCIENTIFIC_RESOURCE_LINK = "scientificResourcesLink";
	
	String urlFormate =  StringUtils.EMPTY;
	
	/**
	 * Initializes the Icm Cell Hierarchy Model.
	 */
	@PostConstruct
	protected void init() {
		log.debug("Inside Cell Page data fetch Model Init");
		ExcludeUtil excludeUtilObject = new ExcludeUtil();
		urlFormate = CommonHelper.getRegion(currentPage)+"/"+CommonHelper.getCountry(currentPage)+"/"+CommonHelper.getLanguage(currentPage)+"-"+CommonHelper.getCountry(currentPage);
		try {
			setCellSelectionList(excludeUtilObject);
		} catch (IOException e) {
			log.error("IOException",e);
		}
		
		setCellSelectionListConfig(excludeUtilObject);
	}

	/**
	 * Sets the Icm Cell Hierarchy label.
	 *
	 * @param excludeUtilObject the new Icm Cell Hierarchy label
	 * @throws IOException 
	 */
	private void setCellSelectionList(ExcludeUtil excludeUtilObject) throws IOException {
		log.debug("Inside the model class");
		if(StringUtils.isNotBlank(cellPageDesc)) {
		     cellPageDesc = CommonHelper.HandleRTEAnchorLink(cellPageDesc, externalizerService, resourceResolver, urlFormate);
		}
		 Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		String setOfCellTypes = StringUtils.EMPTY;
		JsonArray finalCellArrayList = new JsonArray();
		setOfCellTypes = cellTypesToQueryParam(setOfCellTypes);
		setOfCellTypes = setOfCellTypes.replaceFirst(CommonConstants.COMMA, StringUtils.EMPTY);
		log.debug("Set of cell Types :: {}",setOfCellTypes);
		
		
		try {
			String reactivityVar = HUMAN;
			ArrayList<String> reactivityArray = new ArrayList<>();
			if(null!=reactivityList && !reactivityList.isEmpty())
			{
				for(Resource res: reactivityList)
				{
					if(null!=res)
					{
						ValueMap cellVM = res.getValueMap();
						if(null!=cellVM && cellVM.containsKey(REACTIVITY))
						{
							reactivityVar = cellVM.get(REACTIVITY).toString();
							/* Currently Defaulting to human  since scope is to handle only one reactivity(Human).This logic has to be update for multiple reactivity in future*/
							if(!reactivityVar.equals(HUMAN))
								reactivityVar = HUMAN;
							reactivityArray.add(reactivityVar);
						}
					}
				}
			}
			targetReactivity = json.toJsonTree(reactivityArray);
			log.debug("country :: {}",CommonHelper.getCountry(currentPage));
			JsonArray cellsData = icmSolrSearchQueriesService.getCellsData(CommonHelper.getCountry(currentPage), bdbSearchEndpointService, solrSearchService, reactivityVar, setOfCellTypes);
			log.debug("fetched cell data :: {}",cellsData);
			if(null != cellTypes && null != cellsData &&  cellsData.size() > 0) {
			for(IcmCellTypeListModel eachCellObj:cellTypes)
			{
				for(JsonElement cellSolrData:cellsData)
				{
					JsonObject cellSolrDataObject = cellSolrData.getAsJsonObject();
					 String cellId = getJsonProperty(cellSolrDataObject, CELL_ID);
					if(StringUtils.isNotBlank(eachCellObj.getCell()) && eachCellObj.getCell().equals(cellId))
					{
						JsonObject finalCellJson = new JsonObject();
						log.debug("cell matching name ::: from dialog : {0} from solr: {1}",eachCellObj.getCell());
						finalCellJson.addProperty(CELL_ID, eachCellObj.getCell());
						finalCellJson.addProperty(CELL_NAME, eachCellObj.getCellLabel());
						finalCellJson.addProperty(CELL_IMG, getJsonProperty(cellSolrDataObject, CELL_IMAGE));
						if(!eachCellObj.getParentCell().equals("SELECT")) {
							finalCellJson.addProperty(PARENT_ID, eachCellObj.getParentCell());
						}else {
							finalCellJson.addProperty(PARENT_ID, StringUtils.EMPTY);	
						}
						finalCellJson.addProperty(HIDE_ACTION_CTA, eachCellObj.getHideActionCta());
						JsonArray markersList = new JsonArray();
						markerObject(cellSolrDataObject, markersList,"cellSurface", CELL_SURFACE);
						markerObject(cellSolrDataObject, markersList,"intracellularTxn", INTRACELLULAR_TXN);
						markerObject(cellSolrDataObject, markersList,"secretedProteins", SECRETED);
						
						finalCellJson.add(MARKERS_LIST, markersList);
						finalCellJson.addProperty(CELL_DESCRIPTION, getJsonProperty(cellSolrDataObject, CELL_DESCRIPTION));
						finalCellJson.addProperty(REACTIVITY, getJsonProperty(cellSolrDataObject, REACTIVITY));
						finalCellJson.add(SUB_SET_CELL_IDS, getSolrJsonArrayData(cellSolrDataObject, SUBSETCELLS));
						finalCellJson.add(PANEL_IDS, getSolrJsonArrayData(cellSolrDataObject, RELEVANT_PANELS));
						finalCellArrayList.add(finalCellJson);
					}
				}
			}
			}
		} catch (IOException | SolrServerException e) {
			
			log.error("error to fetch cell data :: {0}",e);
		}
		
		JsonObject cellSelectionLabelsObj = new JsonObject();
		cellSelectionLabelsObj.add(CELL_SELECTION_LIST, finalCellArrayList);
		cellSelectionLabelsObj.addProperty(GENERAL_INFO_LABEL, generalInfoLabel);
		cellSelectionLabelsObj.addProperty(VIEW_SCIENTIFIC_RESOURCES_LABEL, viewScientificResourcesLabel);
		cellSelectionLabelsObj.addProperty(LAUNCH_ICON_PATH, launchIconPath);
		cellSelectionLabelsObj.addProperty(LAUNCH_ICON_ALT_TEXT, launchIconAltText);
		cellSelectionLabelsObj.addProperty(SEE_MARKERS, seeMarkers);
		cellSelectionLabelsObj.addProperty(VIEW_CELL_SUBSETS,viewCellSubsets);
		cellSelectionLabelsObj.addProperty(VIEW_PANELBUTTON_LABELS, viewPanelButtonLabel);
		cellSelectionLabelsObj.addProperty(EDIT_CTS_TEXT, editCTAText);
		cellSelectionLabelsObj.addProperty(SELECTED_CELL_DESC, selectedCellDesc);
		cellSelectionLabelsObj.addProperty("cellSurface", cellSurfaceLabel);
		cellSelectionLabelsObj.addProperty("intracellularTxn", intercellularLabel);
		cellSelectionLabelsObj.addProperty("secretedProteins", secretedProteinsLabel);
		if(StringUtils.isNotBlank(noPanelsTextAndLink)) {
			cellSelectionLabelsObj.addProperty(NO_PANELS_TEXT_AND_LINK,CommonHelper.HandleRTEAnchorLink(noPanelsTextAndLink, externalizerService, resourceResolver, urlFormate));
		}	
		if(null != searchPageUrl) {
			String externalisedUrl = externalizerService.getFormattedUrl(searchPageUrl, resourceResolver);
			if(null != externalisedUrl) {
				String scientificResourcesLink = externalisedUrl.concat("?searchKey={{searchTerm}}&docType_facet_s::{{info-docs}}={{info-docs}}");
				externalisedUrl = externalisedUrl.concat("?searchKey={{searchTerm}}&docType_facet_s::{{products}}={{products}}&speciesReactivity_facet_ss::{{human}}={{human}}");
				cellSelectionLabelsObj.addProperty(SEARCH_PAGE_URL, externalisedUrl);
				cellSelectionLabelsObj.addProperty(SCIENTIFIC_RESOURCE_LINK,scientificResourcesLink);
			}
		}
	
		JsonElement stepsEle = json.toJsonTree(steps);
		log.debug("multifield tree structure :: {}",cellSelectionLabelsObj);
		log.debug("stepsEle multifield tree structure :: {}",stepsEle);
		JsonObject filterLabelsObj = new JsonObject();
		filterLabelsObj.addProperty(FILTER_LABEL, filterLabel);
		filterLabelsObj.addProperty(APPLY_FILTER_BTN_TEXT, applyFilterBtnText);
		filterLabelsObj.addProperty(TOOL_TIP_ICON_SRC, tooltipIconSrc);
		filterLabelsObj.addProperty(TOOL_TIP_ICON_ALT_TEXT, tooltipIconAltText);
		filterLabelsObj.addProperty(INSTRUMENT_CONFIG_LABEL, instrumentConfigLabel);
		filterLabelsObj.addProperty(PANEL_TYPE_LABEL, panelTypeLabel);
		filterLabelsObj.addProperty(NUMBER_OF_PARAMS_LABEL, numberOfParametersLabel);
		filterLabelsObj.addProperty(GREATER_THAN_EIGHTEEN_LABEL, gt18);
		filterLabelsObj.addProperty(LESS_THAN_OR_EQUALS_EIGHTEEN_LABEL, ltet18);
		filterLabelsObj.addProperty(LESS_THAN_OR_EQUALS_TWELVE_LABEL, ltet12);
		filterLabelsObj.addProperty(LESS_THAN_OR_EQUALS_EIGHT_LABEL, ltet8);
		filterLabelsObj.addProperty(ALL_LABEL, All);
		filterLabels = json.toJsonTree(filterLabelsObj);
		
		JsonObject panelLabelsObj = new JsonObject();
		panelLabelsObj.addProperty(PANEL_REAGENTS_LABEL, panelReagentsLabel);
		panelLabelsObj.addProperty(ADD_SELECTION_TO_CART_LABEL, addSelectionToCartLabel);
		panelLabelsObj.addProperty(SELECT_UNSELECT_ALL_LABEL, selectUnselectAllLabel);
		panelLabelsObj.addProperty(UP_ARROW_ICON, upArrowIcon);
		panelLabelsObj.addProperty(DOWN_ARROW_ICON, downArrowIcon);
		panelLabelsObj.addProperty(UP_ARROW_ALT_TEXT, upArrowAltText);
		panelLabelsObj.addProperty(DOWN_ARROW_ALT_TEXT, downArrowAltText);
		panelLabelsObj.addProperty(SHOWING_LABEL, showingLabel);
		panelLabelsObj.addProperty(RESULTS_FOR_LABEL, resultsForLabel);
		
		panelLabelsObj.addProperty(LASER_LINE_LABEL, laserLineLabel);
		panelLabelsObj.addProperty(MARKER_LABEL, markerLabel);
		panelLabelsObj.addProperty(FLUORO_CHROME_LABEL, fluorochromeLabel);
		panelLabelsObj.addProperty(CLONE_LABEL, cloneLabel);
		panelLabelsObj.addProperty(TEST_PER_KIT_LABEL, testPerKitLabel);
		panelLabelsObj.addProperty(VOLUME_PER_TEST_LABEL, volumePerTestLabel);
		panelLabelsObj.addProperty(CATALOG_NUMER_LABEL, catalogNumberLabel);
		panelLabelsObj.addProperty(SELECT_LABEL, selectLabel);
		panelLabelsObj.addProperty(DISCLAIMER_LABEL, disclaimerLabel);
		panelLabelsObj.addProperty(APPLICABLE_CELL_TYPE, applicableCellType);
		panelLabelsObj.addProperty(PANELS_TEXT_LABEL, panelsTextLabel);
		panelLabels = json.toJsonTree(panelLabelsObj);
		
		JsonObject noFilterPanelsLabelsObj = new JsonObject();
		noFilterPanelsLabelsObj.addProperty(EMPTY_STATE_IMG_SRC, emptyStateImgSrc);
		noFilterPanelsLabelsObj.addProperty(EMPTY_STATE_IMG_ALT_TEXT, emptyStateImgAltText);
		noFilterPanelsLabelsObj.addProperty(TITLE, noPanelsTitle);
		if(StringUtils.isNotBlank(noPanelsDesc)) {
			noFilterPanelsLabelsObj.addProperty(DESCRIPTION_INFO,  CommonHelper.HandleRTEAnchorLink(noPanelsDesc, externalizerService, resourceResolver, urlFormate));

		}
		noFilterPanelsLabelsObj.addProperty(CONTACT_US_LABEL, contactUsLabel);
		noFilterPanelsLabelsObj.addProperty(CLEAR_FILTERS_LABEL, clearFiltersLabel);
		noFilterPanelsLabels = json.toJsonTree(noFilterPanelsLabelsObj);
		cellSelectionLabels = json.toJsonTree(cellSelectionLabelsObj);
		stepsHolder = json.toJsonTree(stepsEle);
		cellPageLabels = json.toJson(this);
	}

	/**
	 * 
	 * @param setOfCellTypes
	 * @return converted the format to fit for Query
	 */
	private String cellTypesToQueryParam(String setOfCellTypes) {
		if(null != cellTypes) {
			for(IcmCellTypeListModel eachCellObj:cellTypes)
			{
				if(StringUtils.isNotBlank(eachCellObj.getCell())) {
					setOfCellTypes = setOfCellTypes.concat(CommonConstants.COMMA).concat(CommonConstants.DOUBLE_QOUTES).concat(eachCellObj.getCell().trim()).concat(CommonConstants.DOUBLE_QOUTES);
				}
			}	
		}
		return setOfCellTypes;
	}

	public JsonArray getSolrJsonArrayData(JsonObject cellSolrDataObject,String attribute) {
		JsonArray solrJsonArray = cellSolrDataObject.getAsJsonArray(attribute);
		return !solrJsonArray.get(0).getAsString().isEmpty() ?solrJsonArray:new JsonArray();
	}
	
	public String getJsonProperty(JsonObject cellJsonObject, String property) {
		String propertyValue = StringUtils.EMPTY;
		if (null != cellJsonObject.get(property)) {
			return cellJsonObject.get(property).getAsString().trim();
		} else {
			return propertyValue;
		}
	}

	private void setCellSelectionListConfig(ExcludeUtil excludeUtilObject)
	{
		cellPageConfig = StringUtils.EMPTY;
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		
		JsonObject icmToolConfigObj = new JsonObject();
		if (bdbApiEndpointService != null) {
			
			JsonObject isPanelJson = new JsonObject();
			if(isPanelPage == null) {
				isPanelJson.addProperty(IS_PANEL_PAGE, "false");
			}else {
				isPanelJson.addProperty(IS_PANEL_PAGE, isPanelPage);
			}
			
			icmToolConfigObj.add(GET_PANEL_PAGE_INFO, isPanelJson);
			icmToolConfigObj.add(GET_CELL_DATA, cellConfigJsons(bdbSearchEndpointService.getIcmToolsServicePath()));
			icmToolConfigObj.add(GET_PANEL_DATA, cellConfigJsons(bdbSearchEndpointService.getIcmToolsServicePath()));
			icmToolConfigObj.add(SEARCH_PAYLOAD, cellConfigJsons(bdbSearchEndpointService.getSearchPayloadPath()));
			
			String hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
			icmToolConfigObj.add(CommonConstants.BULK_ENTRY_QUICK_ADD, cellConfigBulkEntryJson(hybrisSiteId));
		}
			
			icmToolConfig = json.toJsonTree(icmToolConfigObj);
			
			cellPageConfig = json.toJson(icmToolConfig);
	}

	private void markerObject(JsonObject cellSolrDataObject, JsonArray markersList, String catName, String markerAttribute) {
		JsonObject markersJson = new JsonObject();
		markersJson.addProperty(CATEGORY_NAME, catName);
		markersJson.add(LINKS_LIST, getSolrJsonArrayData(cellSolrDataObject, markerAttribute));
		markersList.add(markersJson);
	}
	
	/**
	 * 
	 * @param url
	 * @return tempJson
	 */
	private JsonObject cellConfigJsons(String url)
	{
		JsonObject tempJson = new JsonObject();
		
		tempJson.addProperty(CommonConstants.URL, url);
		tempJson.addProperty(CommonConstants.METHOD, CommonConstants.POST);
		
		return tempJson;
		
	}
	
	/**
	 * 
	 * @param hybrisSiteId 
	 * @return bulkEntry
	 */
	private JsonObject cellConfigBulkEntryJson(String hybrisSiteId)
	{
		String bulkEntryEndpoint = bdbApiEndpointService.getBDBHybrisDomain()
				+ bdbApiEndpointService.getQuickAddBulkEntryEndpoint();
		JsonObject bulkEntry = new JsonObject();
		if(null != hybrisSiteId) {
			bulkEntry.addProperty(CommonConstants.URL, bulkEntryEndpoint.replace(CommonConstants.HYBRIS_SITE_LITERAL, hybrisSiteId));
			bulkEntry.addProperty(CommonConstants.METHOD, CommonConstants.POST);
		}
		return bulkEntry;
	}

	/**
	 * contains window labels 
	 */
	public String getCellPageLabels() {
		return cellPageLabels;
	}
	
	public String getCellPageConfig() {
		return cellPageConfig;
	}
	
	public List<IcmCellTypeListModel> getCellTypes() {
		return Optional.ofNullable(cellTypes).filter(f -> !f.isEmpty()).orElseGet(ArrayList::new);
	}

	public List<IcmStepsListModel> getSteps() {
		return Optional.ofNullable(steps).filter(f -> !f.isEmpty()).orElseGet(ArrayList::new);
	}
	
}
