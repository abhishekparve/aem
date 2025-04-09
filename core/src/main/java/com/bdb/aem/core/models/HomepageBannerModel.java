package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

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
 * The Class HomepageBannerModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HomepageBannerModel {
	
	/** The logger. */
	Logger logger = LoggerFactory.getLogger(HomepageBannerModel.class);

	/** The Constant NO. */
	private static final String NO = "no";

	/** The image path. */
	@Inject
	private String imagePath;

	/** The image path mobile. */
	@Inject
	private String imagePathMobile;

	/** The cta url. */
	@Inject
	private String ctaUrl;
	
	/** The Root URL. */
    @Inject
    private String rootURL;
    
    /** The Munchkin ID. */
    @Inject
    private String munchkinID;
    
    /** The Form ID. */
    @Inject
    private String formID;
    
    /** The Form Title. */
    @Inject
    private String formTitle;
    
    /** The Form Body Text. */
    @Inject
    private String formBodyText;

	/** The url video. */
	@Inject
	private String urlVideo;

	/** The thumbnail. */
	@Inject
	private String thumbnail;

	/** The source. */
	@Inject
	private String source;

	/** The dark mode. */
	@Inject
	private String darkMode;

	/** The font color. */
	@Inject
	private String fontColor;

	/** The primary cta variation. */
	@Inject
	private String ctaVariation;
	
	/** The video enabled. */
	@Inject
	private String videoEnabled;

	/** The play video label. */
	@Inject
	private String playVideoLabel;

	/** The brightcove video id. */
	@Inject
	private String brightcoveVideoId;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;
	
	/** The fontColorVariation service. */
	@Inject
	@Default(values ="#fff")
	private String  fontColorVariation;

	/** The buttonURL service. */
	@Inject
	private String  buttonURL;
	
	/** The Root URL. */
    @Inject
    private String rootURLSec;
    
    /** The Munchkin ID. */
    @Inject
    private String munchkinIDSec;
    
    /** The Form ID. */
    @Inject
    private String formIDSec;
    
    /** The Form Title. */
    @Inject
    private String formTitleSec;
    
    /** The Form Body Text. */
    @Inject
    private String formBodyTextSec;

	private boolean isFileDownload;

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
		logger.debug("HomepageBannerModel Initiated");
		imagePath = externalizerService.getFormattedUrl(imagePath, resourceResolver);
		imagePathMobile = externalizerService.getFormattedUrl(imagePathMobile, resourceResolver);
		ctaUrl = externalizerService.getFormattedUrl(ctaUrl, resourceResolver);
		urlVideo = externalizerService.getFormattedUrl(urlVideo, resourceResolver);
		thumbnail = externalizerService.getFormattedUrl(thumbnail, resourceResolver);
		if(buttonURL!=null && !buttonURL.startsWith("/content/dam")){
			buttonURL = externalizerService.getFormattedUrl(buttonURL, resourceResolver);
		}else{
			isFileDownload = true;
		}
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
	public String getImagePathMobile() {
		return imagePathMobile;
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
	 * @return the rootURL
	 */
	public String getRootURL() {
		return rootURL;
	}

	/**
	 * @return the munchkinID
	 */
	public String getMunchkinID() {
		return munchkinID;
	}

	/**
	 * @return the formID
	 */
	public String getFormID() {
		return formID;
	}

	/**
	 * @return the formTitle
	 */
	public String getFormTitle() {
		return formTitle;
	}

	/**
	 * @return the formBodyText
	 */
	public String getFormBodyText() {
		return formBodyText;
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
	 * Gets the font color.
	 *
	 * @return the font color
	 */
	public String getFontColor() {
		return fontColor;
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
	 * Gets the video enabled.
	 *
	 * @return the video enabled
	 */
	public boolean getVideoEnabled() {
		return StringUtils.isNotBlank(videoEnabled) && videoEnabled.equals("yes")
				&& StringUtils.isNotBlank(brightcoveVideoId) && StringUtils.isNotBlank(playVideoLabel);
	}

	/**
	 * Gets the play video label.
	 *
	 * @return the play video label
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
	
	public String getFontColorVariation() {
		return fontColorVariation;
	}

	public String getButtonURL() {
		return buttonURL;
	}

	/**
	 * @return the rootURLSec
	 */
	public String getRootURLSec() {
		return rootURLSec;
	}

	/**
	 * @return the munchkinIDSec
	 */
	public String getMunchkinIDSec() {
		return munchkinIDSec;
	}

	/**
	 * @return the formIDSec
	 */
	public String getFormIDSec() {
		return formIDSec;
	}

	/**
	 * @return the formTitleSec
	 */
	public String getFormTitleSec() {
		return formTitleSec;
	}

	/**
	 * @return the formBodyTextSec
	 */
	public String getFormBodyTextSec() {
		return formBodyTextSec;
	}

	public boolean isFileDownload() {
		return isFileDownload;
	}

}
