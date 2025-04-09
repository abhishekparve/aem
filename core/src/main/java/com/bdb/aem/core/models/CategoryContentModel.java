package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.bean.CategoryContentBean;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.day.cq.wcm.api.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;


/**
 * The Class CategoryContentModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CategoryContentModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(CategoryContentModel.class);

	/** The request. */
	@SlingObject
	private SlingHttpServletRequest request;

	/** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

	/** The current page. */
	@Inject
	Page currentPage;

	/** The section label. */
	@Inject
	@Named("sectionLabel")
	@Via("resource")
	private String sectionLabel;
	
	/** The title. */
	@Inject
	@Named("title")
	@Via("resource")
	private String title;
	
	/** The description. */
	@Inject
	@Named("description")
	@Via("resource")
	private String description;
	
	/** The icon alignment. */
	@Inject
	@Named("iconAlignment")
	@Via("resource")
	private String iconAlignment;
	
	/** The column fields. */
	@Inject
	@Named("columnFields")
	@Via("resource")
	private Resource columnFields;
	
	/** The brightcove video id category content. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String brightcoveVideoIdCategoryContent;

	/** The video thumbnail and image path. */
	@Inject
	@Via("resource")
	private String videoImagePath;
	
	/** The video thumbnail and image alt. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String vidImageAlt;

	/** The video image description. */
	@Inject
	@Via("resource")
	private String videoImageDescription;
	
	
	/** The video image variation choice. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String videoImage;
	
	/** The image link. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String imageLink;


	/** The category content bean. */
	@Inject
	CategoryContentBean categoryContentBean;
	
	/** The category content list. */
	private List<CategoryContentBean> categoryContentList;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;
    
    /** The bdb api endpoint service. */
    @Inject
    BDBApiEndpointService bdbApiEndpointService;
    
    
	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("Inside CategoryContentModel Init");
		try {
			int colCount = 0;
			if(null != videoImagePath) {
				videoImagePath = externalizerService.getFormattedUrl(videoImagePath, resourceResolver);
			}
			if(null != columnFields) {
	    		Iterator<Resource> columnResource = columnFields.listChildren();
	    		if(videoImage.isEmpty() || videoImage.equals("no")) {
	    			colCount = 4;
	    		}
	    		else {
	    			colCount = 3;
	    		}
	    		 if(!imageLink.isEmpty()) {
	 	        	imageLink = externalizerService.getFormattedUrl(imageLink, resourceResolver);
	 	        }
	    		categoryContentList = new ArrayList<>();
	    		int counter = 0;
	            while(columnResource.hasNext() && counter < colCount) {
	            	JsonObject categoryJsonObject = new JsonObject();
					String itemResPath = columnResource.next().getPath();
					Resource columnItemResource = resourceResolver.getResource(itemResPath);
					ValueMap valueMap = columnItemResource.adaptTo(ValueMap.class);
					String iconType = getValueMapProperty(valueMap, "iconType");
					String iconUrl = getValueMapProperty(valueMap, "iconUrl");
					String iconLink = getValueMapProperty(valueMap, "iconLink");
					String formatedIconLink = externalizerService.getFormattedUrl(iconLink, resourceResolver);
					String formatedIcon = externalizerService.getFormattedUrl(iconUrl, resourceResolver);
					String iconDescription = getValueMapProperty(valueMap, "iconDescription");
					String iconHeading = getValueMapProperty(valueMap, "iconHeading");
					String iconAlt = getValueMapProperty(valueMap, "iconAlt");
					categoryJsonObject.addProperty("iconType", iconType);
					categoryJsonObject.addProperty("iconUrl", formatedIcon);
					categoryJsonObject.addProperty("iconLink", formatedIconLink);
					categoryJsonObject.addProperty("iconDescription", iconDescription);
					categoryJsonObject.addProperty("iconHeading", iconHeading);
					categoryJsonObject.addProperty("iconAlt", iconAlt);
					ObjectMapper objectMapper = new ObjectMapper()
							.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					
					CategoryContentBean categoryContentObj = objectMapper
								.readValue(categoryJsonObject.toString(), CategoryContentBean.class);
					categoryContentList.add(categoryContentObj);
					counter++;
	            }
	    	}
		}catch (JsonProcessingException e) {
			logger.error("Exception {}", e.getMessage());
		}
	}
	
	/**
	 * Gets the value map property.
	 *
	 * @param toolsMap the tools map
	 * @param myParam the my param
	 * @return the value map property
	 */
	private String getValueMapProperty(ValueMap toolsMap, String myParam) {
		String local = null;
		if (null != toolsMap.get(myParam)) {
			local = toolsMap.get(myParam, String.class);
		}
		return local;
	}
	
	/**
	 * Gets the section label.
	 *
	 * @return the section label
	 */
	public String getSectionLabel() {
		return sectionLabel;
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
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the category content list.
	 *
	 * @return the category content list
	 */
	public List<CategoryContentBean> getCategoryContentList() {
		if(null != categoryContentList) {
			return new ArrayList<>(categoryContentList);
		}else {
			return Collections.emptyList();
		}
	}
	
	/**
	 * Gets the icon alignment.
	 *
	 * @return the icon alignment
	 */
	public String getIconAlignment() {
		return iconAlignment;
	}
	
	/**
	 * Gets the brightcove video id category content.
	 *
	 * @return the brightcove video id category content
	 */
	public String getBrightcoveVideoIdCategoryContent() {
		return brightcoveVideoIdCategoryContent;
	}
	
	/**
	 * Gets the video image path.
	 *
	 * @return the video image path
	 */
	public String getVideoImagePath() {
		return videoImagePath;
	}
	
	/**
	 * Gets the video image description.
	 *
	 * @return the video image description
	 */
	public String getVideoImageDescription() {
		return videoImageDescription;
	}
	
	/**
	 * Gets the vid image alt.
	 *
	 * @return the vid image alt
	 */
	public String getVidImageAlt() {
		return vidImageAlt;
	}


	/**
	 * Gets the video image variation choice.
	 *
	 * @return the video image variation choice
	 */
	public String getVideoImage() {
		return videoImage;
	}
	
	/**
	 * Gets the image link.
	 *
	 * @return the image link
	 */
	public String getImageLink() {
		return imageLink;
	}
	
    /**
     * Gets the brightcove account id.
     *
     * @return the brightcove account id
     */
    public String getBrightcoveAccountId() {
        return bdbApiEndpointService.brightcoveAccountId();
    }

    /**
     * Gets the brightcove player id.
     *
     * @return the brightcove player id
     */
    public String getBrightcovePlayerId() {
        return bdbApiEndpointService.brightcovePlayerId();
    }


}
