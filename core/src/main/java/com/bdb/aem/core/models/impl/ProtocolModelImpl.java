package com.bdb.aem.core.models.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bdb.aem.core.models.ProtocolModel;
import com.bdb.aem.core.models.ProtocolTextLinkModel;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.ExcludeUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 * The Class ProtocolModelImpl.
 */
@Model( adaptables = { Resource.class }, 
		adapters = { ProtocolModel.class }, 
		resourceType = { ProtocolModelImpl.RESOURCE_TYPE },
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProtocolModelImpl implements ProtocolModel {
	
	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/protocolLibrary/v1/protocolLibrary";

	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(ProtocolModelImpl.class);

	/** The protocol title. */
	@Inject
	@SerializedName("title")
	private String protocolTitle;
	
	/** The is included. */
	@Inject
	private String isIncluded;
	
	/** The description. */
	@Inject
	@SerializedName("description")
	private String description;
	
	/** The video enabled. */
	@Inject
	private boolean videoEnabled;
	
	/** The brightcove video id. */
	@Inject
	private String brightcoveVideoId;
	
	/** The image path. */
	@Inject
	@SerializedName("image")
	private String imagePath;
	
	/** The image alt text. */
	@Inject
	@SerializedName("alttext")
	private String imageAltText;
	
	/** The image link url. */
	@Inject
	@SerializedName("imageLinkUrl")
	private String imageLinkUrl;	
	
	/** The view more label. */
	@Inject
	@SerializedName("viewMore")
	private String viewMoreLabel;
    
    /** The view less label. */
    @Inject
	@SerializedName("viewLess")
	private String viewLessLabel;
	
	/** The protocol multifield. */
	@Inject
	private List<Resource> protocolMultifield;
	
	/** The externalizer service. */
	@Inject
    ExternalizerService externalizerService;

    /** The protocols list. */
    @SerializedName("protocolText")
    private List<ProtocolTextLinkModel> protocolsList = new ArrayList<>();
    
    /** The protocol count. */
    private int protocolCount;
    
    /** The protocol object json. */
    private String protocolObjectJson;
    
    @SlingObject
    ResourceResolver resourceResolver;
    
    /**
	 * Initializes the Model.
	 */
	@PostConstruct
    protected void init() {
    	log.debug("Inside Protocol Model Init");
			imagePath = externalizerService.getFormattedUrl(imagePath, resourceResolver);
			imageLinkUrl = externalizerService.getFormattedUrl(imageLinkUrl, resourceResolver);
    	populateProtocolsList();
    	ExcludeUtil excludeUtilObject = new ExcludeUtil();			
		setProtocolObjectJson(excludeUtilObject);
	}
	
	/**
	 * Populate protocols list.
	 */
	private void populateProtocolsList() {
		protocolCount = 0;
		if (protocolMultifield != null) {
			for (Resource resource : protocolMultifield) {
				ProtocolTextLinkModel protocolItemModel = resource.adaptTo(ProtocolTextLinkModel.class);
				if (protocolItemModel.getLabel() != null) {
					protocolsList.add(protocolItemModel);
					protocolCount++;
				}
			}
		}
	}
	
	/**
	 * Sets the protocol object json.
	 *
	 * @param excludeUtilObject the new protocol object json
	 */
	private void setProtocolObjectJson(ExcludeUtil excludeUtilObject) {
		protocolObjectJson = StringUtils.EMPTY;
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		protocolObjectJson = json.toJson(this);
	}
	
	/**
	 * Gets the protocol title.
	 *
	 * @return the protocol title
	 */
	@Override
	public String getProtocolTitle() {
		return protocolTitle;
	}
	
	/**
	 * Checks if is included.
	 *
	 * @return true, if is included
	 */
	@Override
	public String isIncluded() {
		return isIncluded;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * Checks if is video enabled.
	 *
	 * @return true, if is video enabled
	 */
	public boolean isVideoEnabled() {
		return videoEnabled;
	}

	/**
	 * Gets the brightcove video id.
	 *
	 * @return the brightcove video id
	 */
	public String getBrightcoveVideoId() {
		return brightcoveVideoId;
	}

	/**
	 * Gets the image path.
	 *
	 * @return the image path
	 */
	@Override
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * Gets the image alt text.
	 *
	 * @return the image alt text
	 */
	@Override
	public String getImageAltText() {
		return imageAltText;
	}
	
	/**
	 * @return the imageLinkUrl
	 */
	public String getImageLinkUrl() {
		return imageLinkUrl;
	}

	/**
	 * Gets the view more label.
	 *
	 * @return the view more label
	 */
	@Override
	public String getViewMoreLabel() {
		return viewMoreLabel;
	}

	/**
	 * Gets the view less label.
	 *
	 * @return the view less label
	 */
	@Override
	public String getViewLessLabel() {
		return viewLessLabel;
	}

	/**
	 * Gets the protocols list.
	 *
	 * @return the protocols list
	 */
	@Override
	public List<ProtocolTextLinkModel> getProtocolsList() {
		return new ArrayList<>(protocolsList);
	}

	/**
	 * Gets the protocol count.
	 *
	 * @return the protocol count
	 */
	@Override
	public int getProtocolCount() {
		return protocolCount;
	}

	/**
	 * Gets the protocol object json.
	 *
	 * @return the protocol object json
	 */
	@Override
	public String getProtocolObjectJson() {
		return protocolObjectJson;
	}
}
