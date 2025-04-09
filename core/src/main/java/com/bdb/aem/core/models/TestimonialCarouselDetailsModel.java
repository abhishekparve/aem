package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;

/**
 * The Class TestimonialCarouselDetailsModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TestimonialCarouselDetailsModel {
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(TestimonialCarouselDetailsModel.class);

    
    /** The image path. */
    @Inject
    private String imagePath;
    
    /** The alt image. */
    @Inject
    private String altImage;
    
	/** The image link url. */
	@Inject
	private String imageLinkUrl;
	
	/** The open new image link tab. */
	@Inject
	private String openNewImageLinkTab;
	
    /** The description. */
    @Inject
    private String description;
    
    /** The pname. */
    @Inject
    private String pname;
    
    /** The department. */
    @Inject
    private String department;
    
    /** The university. */
    @Inject
    private String university;

	/** The cta label. */
	@Inject
    private String ctaLabel;
    
    /** The cta url. */
    @Inject
    private String ctaUrl;
    
    /** The extra cta label. */
	@Inject
    private String extraCtaLabel;
    
    /** The extra cta url. */
    @Inject
    private String extraCtaUrl;
    
    /** The background color */
    @Inject
    private String bgColor;

    /** The font color */
    @Inject
    private String fontColor;

    /** The font style */
    @Inject
    private String fontStyle;

    /** The cta text color */
    @Inject
    private String ctaTxtColor;
    
    /** The cta background color */
    @Inject
    private String ctaBgColor;
    
    /** The flag video enabled */
    @Inject
    private String videoEnabled;
    
    /** The play video label */
    @Inject
    private String playVideoLabel;
    
    /** The bright cove video id */
    @Inject
    private String brightcoveVideoId;
    
    /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

    /**
     * The BDB Api Endpoint Service.
     */
    @Inject
    BDBApiEndpointService bdbApiEndpointService;


    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
        	imagePath = externalizerService.getFormattedUrl(imagePath, resourceResolver);
        	ctaUrl = externalizerService.getFormattedUrl(ctaUrl, resourceResolver);
        	extraCtaUrl = externalizerService.getFormattedUrl(extraCtaUrl, resourceResolver);
        	imageLinkUrl = externalizerService.getFormattedUrl(imageLinkUrl, resourceResolver);
    }
    
    /**
     * Gets the image path.
     *
     * @return the image path
     */
    public String getImagePath() {
    	return imagePath;
	}

	/**
	 * Gets the alt image.
	 *
	 * @return the alt image
	 */
	public String getAltImage() {
		return altImage;
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
	 * Gets the pname.
	 *
	 * @return the pname
	 */
	public String getPname() {
		return pname;
	}

	/**
	 * Gets the department.
	 *
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * Gets the university.
	 *
	 * @return the university
	 */
	public String getUniversity() {
		return university;
	}

	/**
	 * Gets the cta label.
	 *
	 * @return the cta label
	 */
	public String getCtaLabel() {
		return ctaLabel;
	}

	/**
	 * Gets the cta url.
	 *
	 * @return the cta url
	 */
	public String getCtaUrl() {
		return ctaUrl;
	}
	
	/**
	 * @return the imageLinkUrl
	 */
	public String getImageLinkUrl() {
		return imageLinkUrl;
	}

	/**
	 * @return the openNewImageLinkTab
	 */
	public String getOpenNewImageLinkTab() {
		return openNewImageLinkTab;
	}

	/**
	 * Gets the extra cta label.
	 *
	 * @return the extra cta label
	 */
	public String getExtraCtaLabel() {
		return extraCtaLabel;
	}

	/**
	 * Gets the extra cta url.
	 *
	 * @return the extra cta url
	 */
	public String getExtraCtaUrl() {
		return extraCtaUrl;
	}

	/**
     * Gets the background color.
     * 
     * @return the background color
     */
    public String getBgColor() {
        return bgColor;
    }

    /**
     * Gets the font color.
     * 
     * @return the font color
     */
    public String getFontColor() {
        return fontColor;
    }

    /**
     * Gets the font style.
     * 
     * @return the font style
     */
    public String getFontStyle() {
        return StringUtils.isNotBlank(fontStyle) ? fontStyle : StringUtils.EMPTY;
    }

   
    /**
     * Gets the cta text color.
     * 
     * @return the cta text color
     */
    public String getCtaTxtColor() {
        return ctaTxtColor;
    }
	
    /**
     * Gets the cta background color.
     * 
     * @return the cta background color
     */
    public String getCtaBgColor() {
        return ctaBgColor;
    }
    
    /**
     * Gets the flag to enable/disable video.
     *
     * @return the flag to enable/disable video
     */
    public boolean isVideoEnabled() {
        return StringUtils.isNotBlank(videoEnabled) && videoEnabled.equals("yes");
    }
    
    /**
     * Gets the play video label.
     *
     * @return the play video label.
     */
    public String getPlayVideoLabel() {
        return playVideoLabel;
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
