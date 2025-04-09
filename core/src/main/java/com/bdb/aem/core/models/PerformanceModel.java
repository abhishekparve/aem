package com.bdb.aem.core.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

/**
 * The Class PerformanceModel.
 */
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PerformanceModel {
	
	/** The logger. */
	Logger logger = LoggerFactory.getLogger(PerformanceModel.class);
	
	/** The section title. */
	@Inject
	@Named("sectionTitle")
    @Via("resource")
	private String sectionTitle;

	/** The title. */
	@Inject
	@Named("title")
    @Via("resource")
	private String title;
	
	/** The image grid list. */
	@Inject
    @Via("resource")
	public Resource imageGridList;
	
	/** The grid bean list. */
	private List<PerformanceDetailsModel> gridBeanList;
	
	/** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;
    
    /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;
    
    /** The current resource. */
	@Inject
	Resource currentResource;
    


	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("Inside PerformanceModel Init");
		try {
			gridBeanList = new ArrayList<>();
			if (null != imageGridList && imageGridList.hasChildren()) {
				Iterator<Resource> resItr = imageGridList.listChildren();
				while (resItr.hasNext()) {
					JsonObject gridObj = new JsonObject();
					Resource childItr = resItr.next();
					ValueMap vM = childItr.getValueMap();
					String subTitle = vM.get(CommonConstants.SUB_TITLE, StringUtils.EMPTY);
					gridObj.addProperty(CommonConstants.SUB_TITLE, subTitle);
					ObjectMapper objectMapper = new ObjectMapper()
							.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					PerformanceDetailsModel performanceDetailsModelObj = objectMapper.readValue(gridObj.toString(),
							PerformanceDetailsModel.class);
					if (childItr.hasChildren()) {
						getRowDetails(childItr, performanceDetailsModelObj);
					}
					gridBeanList.add(performanceDetailsModelObj);
				}
			}
		} catch ( JsonProcessingException e) {
			logger.error("LoginException|JsonProcessingException {}", e.getMessage());
		}
	}

	/**
	 * Gets the row details.
	 *
	 * @param childItr the child itr
	 * @param performanceDetailsModelObj the performance details model obj
	 * @return the row details
	 * @throws JsonMappingException the json mapping exception
	 * @throws JsonProcessingException the json processing exception
	 */
	private void getRowDetails(Resource childItr, PerformanceDetailsModel performanceDetailsModelObj) throws JsonProcessingException {
		List<PerformanceInnerModel> rowDetails = new ArrayList<>();
		Resource rowRes = childItr.listChildren().next();
		Iterator<Resource> optionsItr = rowRes.listChildren();
		while (optionsItr.hasNext()) {
			JsonObject nestedGridObj = new JsonObject();
			Resource options = optionsItr.next();
			ValueMap optVm = options.getValueMap();
			String imageAttr = optVm.get(CommonConstants.IMAGE_JSON_KEY, StringUtils.EMPTY);
			imageAttr = externalizerService.getFormattedUrl(imageAttr, resourceResolver);
			String heading = optVm.get(CommonConstants.HEADING, StringUtils.EMPTY);
			String description = optVm.get(CommonConstants.DESCRIPTION, StringUtils.EMPTY);
			String enlargedImagePath = externalizerService.getFormattedUrl(optVm.get(CommonConstants.ENLARGED_IMAGE_PATH, StringUtils.EMPTY), resourceResolver);
			String altImage = optVm.get(CommonConstants.ALT_IMAGE, StringUtils.EMPTY);
			String enlargeSize = optVm.get(CommonConstants.ENLARGE_SIZE, StringUtils.EMPTY);
			String magnifyingGlassColor = optVm.get(CommonConstants.MAGNIFYING_GLASS_COLOR, StringUtils.EMPTY);
			

			nestedGridObj.addProperty(CommonConstants.IMAGE_JSON_KEY, imageAttr);
			nestedGridObj.addProperty(CommonConstants.HEADING, heading);
			nestedGridObj.addProperty(CommonConstants.DESCRIPTION, description);

			String modalHeading = optVm.get(CommonConstants.MODAL_HEADING, StringUtils.EMPTY);
			String modalDescription = optVm.get(CommonConstants.MODAL_DESCRIPTION, StringUtils.EMPTY);
			nestedGridObj.addProperty(CommonConstants.MODAL_HEADING, modalHeading);
			nestedGridObj.addProperty(CommonConstants.MODAL_DESCRIPTION, modalDescription);
			nestedGridObj.addProperty(CommonConstants.ENLARGED_IMAGE_PATH,enlargedImagePath );
			nestedGridObj.addProperty(CommonConstants.MAGNIFYING_GLASS_COLOR,magnifyingGlassColor );
			
			nestedGridObj.addProperty(CommonConstants.ALT_IMAGE, altImage);
			nestedGridObj.addProperty(CommonConstants.ENLARGE_SIZE, enlargeSize);
			ObjectMapper objectMapper = new ObjectMapper()
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			PerformanceInnerModel performanceInnerModel = objectMapper.readValue(nestedGridObj.toString(), PerformanceInnerModel.class);
			rowDetails.add(performanceInnerModel);
		}
		performanceDetailsModelObj.setNestedDetails(rowDetails);
	}
	/**
	 * Gets the section title.
	 *
	 * @return the section title
	 */
	public String getSectionTitle() {
		return sectionTitle;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the grid bean list.
	 *
	 * @return the grid bean list
	 */
	public List<PerformanceDetailsModel> getGridBeanList() {
		 if(null != gridBeanList) {
	            return new ArrayList<>(gridBeanList);
	        }else {
	            return Collections.emptyList();
	        }
	}
	
	/**
	 * Gets the article id.
	 *
	 * @return the article id
	 */
	public String getArticleId() {
		return currentResource.getParent().getName() + "-" + currentResource.getName();
	}
}