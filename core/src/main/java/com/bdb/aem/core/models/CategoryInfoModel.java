package com.bdb.aem.core.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;


/**
 * The Class CategoryInfoModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CategoryInfoModel {
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(CategoryInfoModel.class);

    /** The image path. */
    @Inject
    private String imagePath;
    
    /** The image path mobile. */
    @Inject
    private String imagePathMobile;
    
    /** The image caption. */
    @Inject
    private String imageCaption;
    
    /** The sectionName. */
    @Inject
    private String sectionTitle;
    
    /** The sub title. */
    @Inject
    private String subTitle;
    
    /** The title. */
    @Inject
    private String title;
    
    /** The description. */
    @Inject
    private String description;
    
    /** The url cta. */
    @Inject
    private String urlCta;
    
    /** The Image Link url */
    @Inject
    private String imageLinkUrl;
    
    /** The label cta. */
    @Inject
    private String labelCta;
    
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
    
    /** The alt text. */
    @Inject
    private String altText;
    
    /** The links. */
    @Inject
    private List<CategoryInfoCLPDetailsModel> links;
    
    /** The url cta add. */
    @Inject
    private String urlCtaAdd;
    
    /** The label cta add. */
    @Inject
    private String labelCtaAdd;
    
    /** The background img. */
    @Inject
    private String backgroundImg;
    
    /** The mobile background img. */
    @Inject
    private String mobileBackgroundImg;
    
    /** The modalImgFlag */
    @Inject
    private String modalImgFlag;
    
    /** The img enlarge Size. */
    @Inject
    private String imageEnlargeSize;
    
    /** The img Path. */
    @Inject
    private String imagePathModal;
    
    /** The imageTitleText. */
    @Inject
    private String imageModalTitleText;
    
    /** The imageAltModalText. */
    @Inject
    private String imageAltModalText;
    
    /** The imageDescText. */
    @Inject
    private String imageDescText;

	/** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;
    
    /** The bdb api endpoint service. */
    @Inject
    BDBApiEndpointService bdbApiEndpointService;
    
    /** The Magnify glass Color. */
	@Inject
	private String magnifiyGlassColor;
	
	/** The current resource. */
	@Inject
	Resource currentResource;
    

    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
        try {
        	if(StringUtils.isNotEmpty(description)) {
        		description = CommonHelper.HandleRTEAnchorLink(description, externalizerService, resourceResolver,StringUtils.EMPTY);
        	}
        	if(StringUtils.isNotEmpty(imageDescText)) {
        		imageDescText = CommonHelper.HandleRTEAnchorLink(imageDescText, externalizerService, resourceResolver,StringUtils.EMPTY);
        	}
        	if (StringUtils.isNotEmpty(imageModalTitleText)) {
        		imageModalTitleText = CommonHelper.HandleRTEAnchorLink(imageModalTitleText, externalizerService, resourceResolver,StringUtils.EMPTY);
        	}
        	imagePath = externalizerService.getFormattedUrl(imagePath, resourceResolver);
        	urlCta = externalizerService.getFormattedUrl(urlCta, resourceResolver);
        	imageLinkUrl = externalizerService.getFormattedUrl(imageLinkUrl, resourceResolver);
        	urlCtaAdd = externalizerService.getFormattedUrl(urlCtaAdd, resourceResolver);
        	imagePathMobile = externalizerService.getFormattedUrl(imagePathMobile, resourceResolver);
        	backgroundImg = externalizerService.getFormattedUrl(backgroundImg, resourceResolver);
        	mobileBackgroundImg = externalizerService.getFormattedUrl(mobileBackgroundImg, resourceResolver);
        	imagePathModal = externalizerService.getFormattedUrl(imagePathModal, resourceResolver);
        } catch (IOException e) {
            logger.error("LoginException {}", e.getMessage());
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
	 * Gets the image caption.
	 *
	 * @return the image caption
	 */
	public String getImageCaption() {
		return imageCaption;
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
	 * Gets the Image link url.
	 *
	 * @return the Image link url
	 */
	public String getImageLinkUrl() {
		return imageLinkUrl;
	}

	/**
	 * Gets the links.
	 *
	 * @return the links
	 */
	public List<CategoryInfoCLPDetailsModel> getLinks() {
		if(CollectionUtils.isNotEmpty(links)) {
			return new ArrayList<>(links);
		}
		else {
			return new ArrayList<>();
		}
		
	}

	/**
	 * Gets the url cta add.
	 *
	 * @return the url cta add
	 */
	public String getUrlCtaAdd() {
		return urlCtaAdd;
	}

	/**
	 * Gets the section name.
	 *
	 * @return the section name
	 */
	public String getSectionTitle() {
		return sectionTitle;
	}

	/**
	 * Gets the sub title.
	 *
	 * @return the sub title
	 */
	public String getSubTitle() {
		return subTitle;
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
	 * Gets the label cta.
	 *
	 * @return the label cta
	 */
	public String getLabelCta() {
		return labelCta;
	}

	/**
	 * Gets the alt text.
	 *
	 * @return the alt text
	 */
	public String getAltText() {
		return altText;
	}

	/**
	 * Gets the label cta add.
	 *
	 * @return the label cta add
	 */
	public String getLabelCtaAdd() {
		return labelCtaAdd;
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
	 * Gets the background img.
	 *
	 * @return the background img
	 */
	public String getBackgroundImg() {
		return backgroundImg;
	}

	/**
	 * Gets the mobile background img.
	 *
	 * @return the mobile background img
	 */
	public String getMobileBackgroundImg() {
		return mobileBackgroundImg;
	}

	/**
	 * Gets the article id.
	 *
	 * @return the article id
	 */
	public String getArticleId() {
		return currentResource.getParent().getName() + "-" + currentResource.getName();
	}
	
	public String getModalImgFlag() {
		if(null == modalImgFlag || modalImgFlag.equals("false"))
			return "false";
		else if(modalImgFlag.equals("true")) {
			return "true";
		}
		return "false";
	}

	public String getImageEnlargeSize() {
		return imageEnlargeSize;
	}

	public String getImagePathModal() {
		return imagePathModal;
	}

	public String getImageModalTitleText() {
		return imageModalTitleText;
	}

	public String getImageAltModalText() {
		return imageAltModalText;
	}

	public String getImageDescText() {
		return imageDescText;
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
    
    public String getMagnifiyGlassColor() {
		  if(StringUtils.isNotEmpty(magnifiyGlassColor))
		  {  return magnifiyGlassColor;
				  
		  }else {
			  return "dark-blue";
		  }
		}

}
