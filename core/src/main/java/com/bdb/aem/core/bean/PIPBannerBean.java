package com.bdb.aem.core.bean;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.bdb.aem.core.services.ExternalizerService;

/**
 * Bean class for PIP Banner
 */
public class PIPBannerBean {
    /**
     * Image Path of thr Hotspot Image
     */
    private String imagePath;
    /**
     * Image Path of the Component
     */
    private String imageLinkUrl;
    /**
     * Image Path of the Component
     */
    private String openNewImageLinkTab;
    /**
     * Alt Text for the same Image
     */
    private String imageAlt;
    /**
     * List of the Hotspots Details
     */
    private List<PIPHotSpotBean> hotspotsDetails;
    

	 /** The externalizer service. */
   @Inject
   ExternalizerService externalizerService;

   /** The resource resolver. */
   @SlingObject
   ResourceResolver resourceResolver;
    
    /**
     *
     * @return Image Path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     *
     * @param imagePath
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    
	public String getImageLinkUrl() {
		return externalizerService.getFormattedUrl(imageLinkUrl,resourceResolver);
	}

	public void setImageLinkUrl(String imageLinkUrl) {
		this.imageLinkUrl = imageLinkUrl;
	}

	/**
	 * @return the openNewImageLinkTab
	 */
	public String getOpenNewImageLinkTab() {
		return openNewImageLinkTab;
	}

	/**
	 * @param openNewImageLinkTab the openNewImageLinkTab to set
	 */
	public void setOpenNewImageLinkTab(String openNewImageLinkTab) {
		this.openNewImageLinkTab = openNewImageLinkTab;
	}

	/**
     *
     * @return Image Alt
     */
    public String getImageAlt() {
        return imageAlt;
    }

    /**
     *
     * @param imageAlt
     */
    public void setImageAlt(String imageAlt) {
        this.imageAlt = imageAlt;
    }

    public List<PIPHotSpotBean> getHotspotsDetails() {
        return new ArrayList<>(hotspotsDetails);
    }

    public void setHotspotsDetails(List<PIPHotSpotBean> hotspotsDetails) {
        this.hotspotsDetails = new ArrayList<>(hotspotsDetails);
    }
}
