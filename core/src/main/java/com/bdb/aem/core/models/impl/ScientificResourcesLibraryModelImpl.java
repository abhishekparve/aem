package com.bdb.aem.core.models.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.ScientificResourceNameCodeModel;
import com.bdb.aem.core.models.ScientificResourcesDropdownModel;
import com.bdb.aem.core.models.ScientificResourcesLibraryModel;
import com.bdb.aem.core.pojo.Payload;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.ExcludeUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

/**
 * The Class ScientificResourcesLibraryModelImpl.
 */
@Model(adaptables = { Resource.class }, adapters = { ScientificResourcesLibraryModel.class }, resourceType = {
		ScientificResourcesLibraryModelImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ScientificResourcesLibraryModelImpl implements ScientificResourcesLibraryModel {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/scientificResourcesLibrary/v1/scientificResourcesLibrary";

	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(ScientificResourcesLibraryModelImpl.class);

	/** The header label. */
	@Inject
	@SerializedName("headerLabel")
	private String headerLabel;
	
	/** The search placeholder. */
	@Inject
	@SerializedName("searchPlaceholder")
	private String searchPlaceholder;
	
	/** The filter by category label. */
	@Inject
	@SerializedName("filterCategory")
	private String filterByCategoryLabel;
	
	/** The clear all label. */
	@Inject
	@SerializedName("cclearAll")
	private String clearAllLabel;
	
	/** The close product icon. */
	@Inject
	@SerializedName("closeProductIcon")
	private String closeProductIcon;
	
	/** The up arrow image. */
	@Inject
	@SerializedName("upArrowImage")
	private String upArrowImage;
	
	/** The up arrow alt text. */
	@Inject
	@SerializedName("upArrowAltText")
	private String upArrowAltText;
	
	/** The PDF icon. */
	@Inject
	@SerializedName("pdf")
	private String pdfIcon;
	
	/** The PDF icon alt text. */
	@Inject
	private String pdfAltTxt;
	
	/** The Video icon. */
	@Inject
	@SerializedName("video")
	private String videoIcon;
	
	/** The Video icon alt text. */
	@Inject
	private String videoAltText;
	
	/** The Image Icon. */
	@Inject
	@SerializedName("image")
	private String imgIcon;
	
	/** The Image Icon alt text. */
	@Inject
	private String imgAltText;
	
	/** The Image Icon. */
	@Inject
	@SerializedName("download")
	private String downloadIcon;
	
	/** The down arrow image. */
	@Inject
	@SerializedName("downArrowImage")
	private String downArrowImage;
	
	/** The down arrow mobile. */
	@Inject
	@SerializedName("downArrowMobile")
	private String downArrowMobile;
	
	/** The down arrow alt text. */
	@Inject
	@SerializedName("downArrowAltText")
	private String downArrowAltText;
	
	/** The close icon. */
	@Inject
	@SerializedName("closeIcon")
	private String closeIcon;
	
	/** The close icon text. */
	@Inject
	@SerializedName("closeIconText")
	private String closeIconText;
	
	/** The cart alt text. */
	@Inject
	@SerializedName("cartAltText")
	private String cartAltText;
	
	/** The status. */
	@SerializedName("status")
	private JsonElement status;
	
	/** The status. */
	@SerializedName("facetsConfig")
	private JsonElement facetsConfig;
	
	/** The status. */
	@SerializedName("altText")
	private JsonElement altText;
	
	/** The status. */
	@SerializedName("noResults")
	private JsonElement noResults;
	
	/** The dropdown list. */
	@SerializedName("dropdownList")
	private List<ScientificResourcesDropdownModel> dropdownList = new ArrayList<>();
	
	/** The all label. */
	@Inject
	private String allLabel;
	
	/** The upcoming label. */
	@Inject
	private String upcomingLabel;
	
	/** The current label. */
	@Inject
	private String currentLabel;
	
	/** The past label. */
	@Inject
	private String pastLabel;
	
	/** The show label. */
	@Inject
	@SerializedName("show")
	private String showLabel;
	
	/** The past label. */
	@Inject
	private String emptyIcon;
	
	/** The past label. */
	@Inject
	private String emptyAltText;
	
	/** The past label. */
	@Inject
	private String emptyHeading;
	
	/** The past label. */
	@Inject
	private String emptySubHeading;
	
	/** The bdb api endpoint service. */
	@Inject
    private BDBApiEndpointService bdbApiEndpointService;
	
	/** The dropdown items multifield. */
	@Inject
	private List<Resource> dropdownItemsMultifield;
	
	/** The name label. */
	@Inject
	@Getter
	private String nameLabel;
	
	/** The resource types multi field. */
	@Inject
	@Getter
	private List<ScientificResourceNameCodeModel> resourceTypesMultiField;

	/** The scientific resources label. */
	private String scientificResourcesLabel;
	
	/** The scientific resources config. */
	private String scientificResourcesConfig;
	
	/** The facets config. 
	private String facetsConfig;*/

	/**
	 * Initializes the Scientific Resources Model.
	 */
	@PostConstruct
	protected void init() {
		log.debug("Inside Scientific Resources Model Init");
		ExcludeUtil excludeUtilObject = new ExcludeUtil();

		populateResourceList();
		setScientificResourcesLabel(excludeUtilObject);
		setScientificResourcesConfig(excludeUtilObject);
		//setFacetsConfig(excludeUtilObject);
	}

	/**
	 * Populate resource list.
	 */
	private void populateResourceList() {
		if (dropdownItemsMultifield != null) {
			for (Resource resource : dropdownItemsMultifield) {
				ScientificResourcesDropdownModel dropdownResource = resource.adaptTo(ScientificResourcesDropdownModel.class);
				if (dropdownResource.getLabel() != null) {
					dropdownList.add(dropdownResource);
				}
			}
		}
	}
	
	/**
	 * Sets the scientific resources label.
	 *
	 * @param excludeUtilObject the new scientific resources label
	 */
	private void setScientificResourcesLabel(ExcludeUtil excludeUtilObject) {
		scientificResourcesLabel = StringUtils.EMPTY;
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		JsonArray facetsArray = new JsonArray();
		JsonObject facetsJson = new JsonObject();
		JsonElement children = json.toJsonTree(resourceTypesMultiField);
		facetsJson.addProperty(CommonConstants.NAME_SCIENTIFIC_RESOURCES, nameLabel);
		facetsJson.addProperty(CommonConstants.CODE_KEY_SCIENTIFIC_RESOURCES, CommonConstants.CODE_VALUE_SCIENTIFIC_RESOURCES);
		facetsJson.addProperty(CommonConstants.CODE_TARGET_KEY_SCIENTIFIC_RESOURCES, CommonConstants.CODE_TARGET_VALUE_SCIENTIFIC_RESOURCES);
		facetsJson.add(CommonConstants.CHILDREN_SCIENTIFIC_RESOURCES, children);
		facetsArray.add(facetsJson);
		JsonObject pdfAltText = new JsonObject();
		pdfAltText.addProperty("image", imgAltText);
		pdfAltText.addProperty("pdf", pdfAltTxt);
		pdfAltText.addProperty("video", videoAltText);
		altText=json.toJsonTree(pdfAltText);
		JsonObject emptyPlaceholder = new JsonObject();
		emptyPlaceholder.addProperty("icon", emptyIcon);
		emptyPlaceholder.addProperty("altText", emptyAltText);
		emptyPlaceholder.addProperty("heading", emptyHeading);
		emptyPlaceholder.addProperty("subHeading", emptySubHeading);
		noResults=json.toJsonTree(emptyPlaceholder);
		JsonObject statusJson = new JsonObject();
		statusJson.addProperty("all", allLabel);
		statusJson.addProperty("upcoming", upcomingLabel);
		statusJson.addProperty("current", currentLabel);
		statusJson.addProperty("past", pastLabel);
		status = json.toJsonTree(statusJson);
		facetsConfig=json.toJsonTree(facetsArray);
		scientificResourcesLabel = json.toJson(this);
	}

	/**
	 * Sets the scientific resources config.
	 */
	private void setScientificResourcesConfig(ExcludeUtil excludeUtilObject) {
		
		scientificResourcesConfig = StringUtils.EMPTY;
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		if (bdbApiEndpointService != null) {
			Payload getPaymentsPayload = new Payload(
					bdbApiEndpointService.getScientificResPath(),
					HttpConstants.METHOD_GET);
			scientificResourcesConfig = json.toJson(getPaymentsPayload);
		}
	}
	
	/**
	 * Gets the scientific resources label.
	 *
	 * @return the scientific resources label
	 */
	@Override
	public String getScientificResourcesLabel() {
		return scientificResourcesLabel;
	}

	/**
	 * Gets the scientific resources config.
	 *
	 * @return the scientific resources config
	 */
	@Override
	public String getScientificResourcesConfig() {
		return scientificResourcesConfig;
	}
	
}
