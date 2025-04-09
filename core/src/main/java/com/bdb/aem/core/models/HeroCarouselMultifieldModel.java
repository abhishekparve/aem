package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


/**
 * The Class HeroCarouselMultifieldModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeroCarouselMultifieldModel {
    
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(HeroCarouselMultifieldModel.class);

    /** The Constant NO. */
    private static final String NO = "no";
    
    /** The image path. */
    @Inject
    private String imagePath;
    
    /** The image path mobile. */
    @Inject
    private String mobileImagePath;
    
    /** The alt image. */
    @Inject
    private String altImage;
    
    /** The cta align. */
    @Inject
    private String ctaAlign;
    
    /** The hero title. */
    @Inject
    private String heroTitle;
   
    /** The description. */
    @Inject
    private String description;
    
    /** The label cta. */
    @Inject
    private String labelCta;
    
    /** The url cta. */
    @Inject
    private String urlCta;
    
    /** The label extra. */
    @Inject
    private String labelExtra;
    
    /** The url extra. */
    @Inject
    private String urlExtra;
    
    /** The carousel video enabled. */
    @Inject
    @Named("videoEnabled")
    private String carouselVideoEnabled;
    
    /** The carousel play video label. */
    @Inject
    @Named("playVideoLabel")
    private String carouselPlayVideoLabel;
    
    /** The thumbnail. */
    @Inject
    private String thumbnail;
    
    /** The carousel brightcove video id. */
    @Inject
    @Named("brightcoveVideoId")
    private String carouselBrightcoveVideoId;
    
    /** The allow audio. */
    @Inject
    private String allowAudio;
    
    /** The url video. */
    @Inject
    private String urlVideo;

    /** The source. */
    @Inject
    private String source;
    
    /** The dark mode. */
    @Inject
    private String darkMode;
    
    /** The fontColorVariation service. */
	@Inject
	@Default(values ="#fff")
	private String  fontColorVariation;

    
    /** The cta variation. */
    @Inject
    private String ctaVariation;
    
    /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;
    
    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;
    
    /** The bdb api endpoint service. */
    @Inject
    BDBApiEndpointService bdbApiEndpointService;

    
    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
    	imagePath = externalizerService.getFormattedUrl(imagePath, resourceResolver);
        mobileImagePath = externalizerService.getFormattedUrl(mobileImagePath, resourceResolver);
    	urlCta = externalizerService.getFormattedUrl(urlCta, resourceResolver);
    	urlExtra = externalizerService.getFormattedUrl(urlExtra, resourceResolver);
    	urlVideo = externalizerService.getFormattedUrl(urlVideo, resourceResolver);
    	thumbnail = externalizerService.getFormattedUrl(thumbnail, resourceResolver);
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
	 * Gets the image path mobile.
	 *
	 * @return the image path mobile
	 */
    public String getMobileImagePath() {
        return mobileImagePath;
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
	 * Gets the cta align.
	 *
	 * @return the cta align
	 */
	public String getCtaAlign() {
		return ctaAlign;
	}

	/**
	 * Gets the hero title.
	 *
	 * @return the hero title
	 */
	public String getHeroTitle() {
		return heroTitle;
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
	 * Gets the label cta.
	 *
	 * @return the label cta
	 */
	public String getLabelCta() {
		return labelCta;
	}
	
	/**
	 * Gets the url cta.
	 *
	 * @return the url cta
	 */
	public String getUrlCta() {
		return urlCta;
	}
	
	/**
	 * Gets the label extra.
	 *
	 * @return the label extra
	 */
	public String getLabelExtra() {
		return labelExtra;
	}
	
	/**
	 * Gets the url extra.
	 *
	 * @return the url extra
	 */
	public String getUrlExtra() {
		return urlExtra;
	}
	
	/**
	 * Gets the dark mode.
	 *
	 * @return the dark mode
	 */
    public Boolean getDarkMode() {
        return Boolean.valueOf(darkMode);
    }
    
    /**
     * Gets the cta variation.
     *
     * @return the cta variation
     */
    public JsonObject getCtaVariation() {
        if (StringUtils.isNotBlank(ctaVariation)) {
	        return new Gson().fromJson(ctaVariation, JsonObject.class);
        }
        return null;
    }
    
    /**
     * Gets the cta txt color.
     *
     * @return the cta txt color
     */
    public String getCtaTxtColor() {
        return CommonHelper.getValueFromJsonString(ctaVariation, "ctaTxtColor");
    }
	
    /**
     * Gets the cta bg color.
     *
     * @return the cta bg color
     */
    public String getCtaBgColor() {
        return CommonHelper.getValueFromJsonString(ctaVariation, "ctaBgColor");
    }
    
    /**
     * Gets the url video.
     *
     * @return the url video
     */
    public String getUrlVideo() {
        return urlVideo;
    }
    
    /**
     * Gets the thumbnail.
     *
     * @return the thumbnail
     */
    public String getThumbnail() {
        return thumbnail;
    }
    
    /**
     * Gets the source.
     *
     * @return the source
     */
    public String getSource() {
        return source;
    }
    
    /**
     * Gets the allow audio.
     *
     * @return the allow audio
     */
    public String getAllowAudio() {
        return StringUtils.isNotBlank(allowAudio)? allowAudio : NO ;
    }
    
    /**
     * Gets the carousel video enabled.
     *
     * @return the carousel video enabled
     */
    public boolean getCarouselVideoEnabled() {
		return StringUtils.isNotBlank(carouselVideoEnabled) && carouselVideoEnabled.equals("yes")
				&& StringUtils.isNotBlank(carouselBrightcoveVideoId) && StringUtils.isNotBlank(carouselPlayVideoLabel);
	}
    
    /**
     * Gets the carousel play video label.
     *
     * @return the carousel play video label
     */
    public String getCarouselPlayVideoLabel() {
        return carouselPlayVideoLabel;
    }
    
    /**
     * Gets the carousel brightcove video id.
     *
     * @return the carousel brightcove video id
     */
    public String getCarouselBrightcoveVideoId() {
        return carouselBrightcoveVideoId;
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
	
	public String getFontColorVariation() {
		return fontColorVariation;
	}
}