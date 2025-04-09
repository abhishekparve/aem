package com.bdb.aem.core.models;

import com.bdb.aem.core.bean.PIPBannerBean;
import com.bdb.aem.core.bean.PIPHotSpotBean;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * PIP Banner Component
 */
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PIPBannerModel {
    Logger log = LoggerFactory.getLogger(PIPBannerModel.class);
    /** Title of the Component */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String title;
    /** Sub Title of the Component */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String subTitle;
    /** Primary CTA of the Component */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String ctaText;
    /** Primary CTA Redirection */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String ctaUrl;
    /** The Root URL. */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String rootURL;
    /** The Munchkin ID. */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String munchkinID;
    /** The Form ID. */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String formID;
    /** The Form Title. */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String formTitle;
    /** The Form Body Text. */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String formBodyText;
    /** Secondary CTA of the component */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String secondaryCTA;
    /** Secondary Redirection */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String secondaryCTAUrl;
    /** The Root URL. */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String rootURLSec;
    /** The Munchkin ID. */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String munchkinIDSec;
    /** The Form ID. */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String formIDSec;
    /** The Form Title. */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String formTitleSec;
    /** The Form Body Text. */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String formBodyTextSec;
    /** Background Image */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String bgImage;
    /** Background Image Mobile*/
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String bgImageMobile;
    /**
     * Background Color
     */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String bgColor;
    /**
     * Image Path of the Component
     */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String imagePath;
    /**
     * Image Path of the Component
     */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String imageLinkUrl;
    /**
     * Image Path of the Component
     */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String openNewImageLinkTab;
    /**
     * Alt text of the Image
     */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String imageAlt;
    /**
     * Position of Overlay
     */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String offsetPosition;
    /**
     * Position of Overlay
     */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String bgImageAlt;
    /**
     * Multifield Hotspot
     */
    @Inject
    @Via("resource")
    private Resource hotspotsList;
    
    /** The fontColorVariation service. */
	@Inject
	@Via("resource")
	@Default(values ="#fff")
	private String  fontColorVariation;
    /**
     * Externalizer Service of Bdb
     */
    @Inject
    ExternalizerService externalizerService;
    /**
     * Resource Resolver
     */
    @Inject
    private ResourceResolver resourceResolver;
    
    /** The cta variation for Primary CTA. */
	@Inject
	@Default(values="{\"ctaTxtColor\":\"#023970\", \"ctaBgColor\":\"#fff\"}")
	@Via("resource")
	private String ctaVariationPrimary;
	
	 /** The cta variation for Secondary CTA. */
	@Inject
	@Default(values ="blueWhite")
	@Via("resource")
	private String ctaVariationSecondary;
	
    /**
     * Hotspot Details
     */
    private String hotspotObject;
    
    List<PIPHotSpotBean> list=new ArrayList<>();
    /**
     * Main Method the class
     */
    @PostConstruct
    protected void init() {
        log.debug("Inside Init of PIPBanner Model");
        PIPBannerBean banner=new PIPBannerBean();
        
        if(null !=hotspotsList&&hotspotsList.hasChildren())
        {
            Iterator<Resource> resItr=hotspotsList.listChildren();
            while(resItr.hasNext())
            {
                PIPHotSpotBean obj=new PIPHotSpotBean();
                Resource childRes=resItr.next();
                ValueMap vM=childRes.getValueMap();
                String xAxis=vM.get("xCoordinate",StringUtils.EMPTY);
                if(StringUtils.isNotEmpty(xAxis)) {
                    obj.setXco_ordinate(xAxis+"px");
                }
                String yAxis=vM.get("yCoordinate",StringUtils.EMPTY);
                if(StringUtils.isNotEmpty(yAxis)) {
                    obj.setYco_cordinate(yAxis+"px");
                }
                obj.setDescription(vM.get("description",StringUtils.EMPTY));
                list.add(obj);
            }
        }
        banner.setHotspotsDetails(list);
        banner.setImagePath(imagePath);
        banner.setImageLinkUrl(getImageLinkUrl());
        banner.setOpenNewImageLinkTab(openNewImageLinkTab);
        banner.setImageAlt(imageAlt);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        hotspotObject=gson.toJson(banner);
    }

    /**
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return subTitle
     */
    public String getSubTitle() {
        return subTitle;
    }

    /**
     *
     * @return ctaText
     */
    public String getCtaText() {
        return ctaText;
    }

    /**
     *
     * @return rootURL
     */
    public String getRootURL() {
        return rootURL;
    }

    /**
     *
     * @return munchkinID
     */
    public String getMunchkinID() {
        return munchkinID;
    }

    /**
     *
     * @return formID
     */
    public String getFormID() {
        return formID;
    }

    /**
     *
     * @return form title
     */
    public String getFormTitle() {
        return formTitle;
    }

    /**
     *
     * @return form description text
     */
    public String getFormBodyText() {
        return formBodyText;
    }

    /**
     *
     * @return ctaUrl
     */
    public String getCtaUrl() {
        return externalizerService.getFormattedUrl(ctaUrl,resourceResolver);
    }

    /**
     *
     * @return secondaryCta
     */
    public String getSecondaryCTA() {
        return secondaryCTA;
    }

    /**
     *
     * @return rootURL
     */
    public String getRootURLSec() {
        return rootURLSec;
    }

    /**
     *
     * @return munchkinID
     */
    public String getMunchkinIDSec() {
        return munchkinIDSec;
    }

    /**
     *
     * @return formID
     */
    public String getFormIDSec() {
        return formIDSec;
    }

    /**
     *
     * @return form title
     */
    public String getFormTitleSec() {
        return formTitleSec;
    }

    /**
     *
     * @return form description text
     */
    public String getFormBodyTextSec() {
        return formBodyTextSec;
    }

    /**
     *
     * @return secondaryCtaUrl
     */
    public String getSecondaryCTAUrl() {
        return externalizerService.getFormattedUrl(secondaryCTAUrl,resourceResolver);
    }

    /**
     *
     * @return BgImage
     */
    public String getBgImage() {
        return bgImage;
    }

    /**
     *
     * @return bgColor
     */
    public String getBgColor() {
        return bgColor;
    }

    /**
     *
     * @return ImagePath
     */
    public String getImagePath() {
        return imagePath;
    }
    
    

    /**
	 * @return the imageLinkUrl
	 */
	public String getImageLinkUrl() {
		return externalizerService.getFormattedUrl(imageLinkUrl,resourceResolver);
	}

	/**
	 * @return the openNewImageLinkTab
	 */
	public String getOpenNewImageLinkTab() {
		return openNewImageLinkTab;
	}

	/**
     *
     * @return ImageAlt
     */
    public String getImageAlt() {
        return imageAlt;
    }

    /**
     *
     * @return HotspotsList
     */
    public Resource getHotspotsList() {
        return hotspotsList;
    }

    /**
     *
     * @return HotspotObject
     */
    public String getHotspotObject() {
        return hotspotObject;
    }

    /**
     *
     * @return Overlay Position
     */
    public String getOffsetPosition() {
        return offsetPosition;
    }

    /**
     *
     * @return Background Image Mobile Variation
     */
    public String getBgImageMobile() {
        return bgImageMobile;
    }

    /**
     *
     * @return Background Image Alt
     */
    public String getBgImageAlt() {
        return bgImageAlt;
    }
    
    public String getFontColorVariation() {
		return fontColorVariation;
	}
    
    /**
	 * @return the ctaVariationSecondary
	 */
	public String getCtaVariationSecondary() {
		return ctaVariationSecondary;
	}

	/**
	 * Gets the cta txt color.
	 *
	 * @return the cta txt color
	 */
	public String getCtaTxtColor() {
		return CommonHelper.getValueFromJsonString(ctaVariationPrimary, "ctaTxtColor");
	}

	/**
	 * Gets the cta bg color.
	 *
	 * @return the cta bg color
	 */
	public String getCtaBgColor() {
		return CommonHelper.getValueFromJsonString(ctaVariationPrimary, "ctaBgColor");
	}

	/**
	 * @return the HotSpot list
	 */
	public List<PIPHotSpotBean> getList() {
		return new ArrayList<>(list);
	}
	
}
