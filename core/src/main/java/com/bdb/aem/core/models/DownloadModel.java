package com.bdb.aem.core.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.Getter;

/**
 * The Class DownloadModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DownloadModel {

	/**
	 * The logger.
	 */
	protected Logger logger = LoggerFactory.getLogger(DownloadModel.class);
	
	/** The backgroundColor. */
    @Inject
    @Getter
    private String backgroundColor;
	
	/** The sectionAlign. */
    @Inject
    @Getter
    private String sectionAlign;

	/** The sign-in required enabled. */
	@Inject
	@Getter
	private boolean isSignInRequired;
	
	/** The sectionTitle. */
    @Inject
    @Getter
    private String sectionTitle;
    
    /** The title. */
    @Inject
    @Getter
    private String title;
    
    /** The subTitle. */
    @Inject
    @Getter
    private String subTitle;
    
    /** The description. */
    @Inject
    @Getter
    private String description;
    
    /** The enableImageBorder. */
    @Inject
    @Getter
    private String enableImageBorder;
    
    /** The image. */
    @Inject
    @Getter
    private String image;
    
    /** The altImage. */
    @Inject
    @Getter
    private String altImage;
    
    /** The imageCaption. */
    @Inject
    @Getter
    private String imageCaption;
    
    /** The modalImageFlag enabled. */
	@Inject
	@Getter
	private boolean modalImageFlag;
    
    /** The magnifyingGlassColor. */
    @Inject
    @Getter
    private String magnifyingGlassColor;
    
    /** The modalImageSize. */
    @Inject
    @Getter
    private String modalImageSize;
    
    /** The modalImage. */
    @Inject
    @Getter
    private String modalImage;
    
    /** The modalImageAltText. */
    @Inject
    @Getter
    private String modalImageAltText;
    
    /** The modalImageTitle. */
    @Inject
    @Getter
    private String modalImageTitle;
    
    /** The modalImageDesc. */
    @Inject
    @Getter
    private String modalImageDesc;
    
    /** The ctaType. */
    @Inject
    @Getter
    private String ctaType;
    
    /** The ctaButtonText. */
    @Inject
    @Getter
    private String ctaButtonText;
    
    /** The ctaButtonColor. */
    @Inject
    @Getter
    private String ctaButtonColor;
 
    /** The clpLink. */
    @Inject
    @Getter
    private String clpLink;
    
    /** The sections */
	@ChildResource
	private List<SoftwareVersionsModel> softwareVersions;
	
	/** The signInToolTipText. */
    @Inject
    @Getter
    private String signInToolTipText;
    
    /** The softwareModalTitle. */
    @Inject
    @Getter
    private String softwareModalTitle;
    
    /** The windowsRadioButtonText. */
    @Inject
    @Getter
    private String windowsRadioButtonText;
    
    /** The macRadioButtonText. */
    @Inject
    @Getter
    private String macRadioButtonText;
    
    /** The linuxRadioButtonText. */
    @Inject
    @Getter
    private String linuxRadioButtonText;
    
    /** The copyPageLinkText. */
    @Inject
    @Getter
    private String copyPageLinkText;
    
    /** The beginDownloadButtonText. */
    @Inject
    @Getter
    private String beginDownloadButtonText;
    
    /** The copyPageLinkSuccessIcon. */
    @Inject
    @Getter
    private String copyPageLinkSuccessIcon;
    
    /** The copyPageLinkSuccessMessage. */
    @Inject
    @Getter
    private String copyPageLinkSuccessMessage;
    
    /** The systemRequirementsLinkText. */
    @Inject
    @Getter
    private String systemRequirementsLinkText;
    
    /** The systemRequirementsLink. */
    @Inject
    private String systemRequirementsLink;
    
    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;
    
    /**
     * The ExternalizerService
     **/
    @Inject
    ExternalizerService externalizerService;
    
	private String versionsAndOsDetails = null;
	
	 /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
    	JsonArray softwareVersionsJsonList = new JsonArray();
    	JsonObject softwareVersionJson = new JsonObject();
    	try {
    		if(null != softwareVersions) {
    			Iterator<SoftwareVersionsModel> iterator = softwareVersions.iterator();
    			SoftwareVersionsModel softwareVersionsModel = new SoftwareVersionsModel();
    			while(iterator.hasNext()) {
    				softwareVersionsModel = iterator.next();
    				String softwareVersionName = softwareVersionsModel.getVersionName();
    				String versionLink = softwareVersionsModel.getVersionLink();
    				logger.debug("Software Version Link : "+versionLink);
    				if(null != versionLink) {
    					Resource softwareVersionResource = resourceResolver.getResource(versionLink);
    					if(null != softwareVersionResource) {
    						logger.debug("Software Version Resource Path : "+softwareVersionResource.getPath());
    						softwareVersionJson = getSoftwaresInfo(softwareVersionResource, softwareVersionName);
    						logger.debug("Software Version Links Json : "+softwareVersionJson.toString());
    					}
    					logger.debug("Software Version Resource is null");
    				}
    				softwareVersionsJsonList.add(softwareVersionJson);
    			}
    			versionsAndOsDetails = softwareVersionsJsonList.toString();
    			logger.debug("Final Json Array : "+versionsAndOsDetails);
    		}
		} catch (IOException e) {
			logger.error("LoginException {}", e.getMessage());
		}
    }
    
    /**
	 * This method forms the json for software version data.
     * @param radioButtonLabelsJson 
     * @param radioButtonLabelsJsonList 
	 *
	 * @return the version json
	 * @throws IOException 
	 */
	public JsonObject getSoftwaresInfo(Resource softwareVersionResource, String versionName) throws IOException {
		JsonObject versionJson = null;
		JsonObject osJson = new JsonObject();
    	JsonArray radioButtonLabelsJsonList = new JsonArray();
    	JsonObject radioButtonLabelsJson = null;
		if (null != softwareVersionResource && softwareVersionResource.hasChildren()) {
			versionJson = new JsonObject();
    		Iterator<Resource> items = softwareVersionResource.listChildren();
    		versionJson.addProperty(CommonConstants.VERSION_NAME, versionName);
    		while(items.hasNext()) {
  			  	Resource osSpecificResource = items.next();
  			  	radioButtonLabelsJson = new JsonObject();
  			  	if (null != osSpecificResource && !osSpecificResource.getPath().endsWith(CommonConstants.JCR_ROOT)) {
	  			  	String metadatapath = osSpecificResource.getPath()+CommonConstants.SLASH+CommonConstants.METADATA_PATH_AS_CHILD;
	  			  	Resource metadataResrouce = resourceResolver.getResource(metadatapath);
	  		  		if(null != metadataResrouce) {
	  		  			ValueMap properties = metadataResrouce.getValueMap();
	  		  			if(StringUtils.isNotEmpty(properties.get(CommonConstants.CQ_TAGS, String.class))) {
	  		            	String osTag = properties.get(CommonConstants.CQ_TAGS, String.class).trim();
	  		                String osTagTitle = getTag(resourceResolver, osTag);
	  		                if(osTagTitle.equalsIgnoreCase(CommonConstants.WINDOWS)) {
	  		                	osJson.addProperty(CommonConstants.WINDOWS, osSpecificResource.getPath());
	  		                	radioButtonLabelsJson.addProperty(CommonConstants.LABEL, windowsRadioButtonText);
	  		                	radioButtonLabelsJson.addProperty(CommonConstants.VALUE, CommonConstants.WINDOWS);
	  		                } else if(osTagTitle.equalsIgnoreCase(CommonConstants.MAC)) {
	  		                	osJson.addProperty(CommonConstants.MAC, osSpecificResource.getPath());
	  		                	radioButtonLabelsJson.addProperty(CommonConstants.LABEL, macRadioButtonText);
	  		                	radioButtonLabelsJson.addProperty(CommonConstants.VALUE, CommonConstants.MAC);
	  		                } else if(osTagTitle.equalsIgnoreCase(CommonConstants.LINUX)) {
	  		                	osJson.addProperty(CommonConstants.LINUX, osSpecificResource.getPath());
	  		                	radioButtonLabelsJson.addProperty(CommonConstants.LABEL, linuxRadioButtonText);
	  		                	radioButtonLabelsJson.addProperty(CommonConstants.VALUE, CommonConstants.LINUX);
	  		                }
	  		  			} 
	  		  		}
			  	}
  			  	if(radioButtonLabelsJson.size() > 0) {
  			  		radioButtonLabelsJsonList.add(radioButtonLabelsJson);
  			  	}
  		  	}
    		versionJson.add(CommonConstants.LABELS, radioButtonLabelsJsonList);
			versionJson.add(CommonConstants.OS_LINKS, osJson);
    	}
		return versionJson;
	}
	
	 /**
		 * Gets the tag.
		 *
		 * @param resourceResolver the resource resolver
		 * @param cqTagProperty the cq tag property
		 * @return the tag
		 */
		public String getTag(ResourceResolver resourceResolver, String cqTagProperty) {
			if (null != cqTagProperty) {
				TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
				Tag tag = tagManager.resolve(cqTagProperty);
				logger.debug("Tag selector resource Tag {}", tag);
				return (null != tag && StringUtils.isNotBlank(tag.getTitle())) ? tag.getTitle() : StringUtils.EMPTY;
			} else {
				return StringUtils.EMPTY;
			}
		}
	
	/**
	 * Gets the softwareVersions.
	 * 
	 * @return the softwareVersions
	 */
	public List<SoftwareVersionsModel> getSoftwareVersions() {
		return Optional.ofNullable(softwareVersions).filter(s -> !s.isEmpty()).orElseGet(ArrayList::new);
	}

	/**
	 * @return the versionsAndOsDetails
	 */
	public String getVersionsAndOsDetails() {
		return versionsAndOsDetails;
	}

	/**
	 * @param versionsAndOsDetails the versionsAndOsDetails to set
	 */
	public void setVersionsAndOsDetails(String versionsAndOsDetails) {
		this.versionsAndOsDetails = versionsAndOsDetails;
	}

	/**
	 * @return the systemRequirementsLink
	 */
	public String getSystemRequirementsLink() {
		return externalizerService.getFormattedUrl(systemRequirementsLink, resourceResolver);
	}
    
}