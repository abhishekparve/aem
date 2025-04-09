package com.bdb.aem.core.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;


/**
 * The Class TextAndImageModel.
 */
@Model(	adaptables = { Resource.class },
		resourceType = {TextAndImageModel.RESOURCE_TYPE},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TextAndImageModel {
	
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/textAndImage/v1/textAndImage";

    /** The logger. */
    Logger logger = LoggerFactory.getLogger(TextAndImageModel.class);


    /** The play video icon. */
    @Inject
    private String playVideoIcon;

    /** The large url. */
    @Inject
    private String largeUrl;

    /** The large enlarged image path. */
    @Inject
    private String largeEnlargedImagePath;

    /** The image list. */
    @Inject
    private List<SmallImageModel> imageList;

    /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;
    
    /** The bdb api endpoint service. */
    @Inject
    BDBApiEndpointService bdbApiEndpointService;

    /** The title. */
    @Inject
    private String title;
    
    /** The section title. */
    @Inject
    private String sectionTitle;
    
    /** The description. */
    @Inject
    private String description;
    
    /** The disclaimer font size. */
    @Inject
    private String disclaimerFontSize;
    
    /** The Magnify glass Color. */
	@Inject
	private String magnifiyGlassColor;
	
	/** The current resource. */
	@Inject
	Resource currentResource;
    
	 /** The url cta. */
    @Inject
    private String urlCta;
	
	/** The large image link url. */
	@Inject
	private String largeImageLinkUrl;
	
    /** The label cta. */
    @Inject
    private String labelCta;

    @Inject
    private String urlCtaAdd;

    @Inject
    private String labelCtaAdd;
    
    @Inject
    private String largeImageTitle;
    
    @Inject
    private String largeImageDescription;
    
    @Inject
    private String smallImageTitle;
    
    @Inject
    private String smallImageDescription;
    

    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
    	try {
        	if(StringUtils.isNotEmpty(description)) {
        		description = CommonHelper.HandleRTEAnchorLink(description, externalizerService, resourceResolver,StringUtils.EMPTY);
    		}
        	if (StringUtils.isNotEmpty(smallImageTitle)) {
        		smallImageTitle = CommonHelper.HandleRTEAnchorLink(smallImageTitle, externalizerService, resourceResolver,StringUtils.EMPTY);
        	}
        	if (StringUtils.isNotEmpty(largeImageTitle)) {
        		largeImageTitle = CommonHelper.HandleRTEAnchorLink(largeImageTitle, externalizerService, resourceResolver,StringUtils.EMPTY);
        	}
        	if (StringUtils.isNotEmpty(smallImageDescription)) {
        		smallImageDescription = CommonHelper.HandleRTEAnchorLink(smallImageDescription, externalizerService, resourceResolver,StringUtils.EMPTY);
        	}
        	if (StringUtils.isNotEmpty(largeImageDescription)) {
        		largeImageDescription = CommonHelper.HandleRTEAnchorLink(largeImageDescription, externalizerService, resourceResolver,StringUtils.EMPTY);
        	}
        	if(StringUtils.isNotEmpty(playVideoIcon)) {
        		playVideoIcon = externalizerService.getFormattedUrl(playVideoIcon, resourceResolver);
            } else { 
            	playVideoIcon = StringUtils.EMPTY;
            }
        	if(StringUtils.isNotEmpty(largeUrl)) {
        		largeUrl = externalizerService.getFormattedUrl(largeUrl, resourceResolver);
            } else { 
            	largeUrl = StringUtils.EMPTY;
            }
        	if(StringUtils.isNotEmpty(largeEnlargedImagePath)) {
        		largeEnlargedImagePath = externalizerService.getFormattedUrl(largeEnlargedImagePath, resourceResolver);
            } else { 
            	largeEnlargedImagePath = StringUtils.EMPTY;
            }if(StringUtils.isNotEmpty(urlCta)) {
            	urlCta = externalizerService.getFormattedUrl(urlCta, resourceResolver);
            }else {
            	urlCta = StringUtils.EMPTY;
            }if(StringUtils.isNotEmpty(urlCtaAdd)) {
            	urlCtaAdd = externalizerService.getFormattedUrl(urlCtaAdd, resourceResolver);
            }else {
            	urlCtaAdd = StringUtils.EMPTY;
            }if(StringUtils.isNotEmpty(largeImageLinkUrl)) {
            	largeImageLinkUrl = externalizerService.getFormattedUrl(largeImageLinkUrl, resourceResolver);
            }else {
            	largeImageLinkUrl = StringUtils.EMPTY;
            }
        } catch (IOException e) {
            logger.error("Exception {}", e.getMessage());
        }
    }


	public String getDescription() {
		return description;
	}

	/**
     * Gets the article id.
     *
     * @return the article id
     */
    public String getArticleId() {
//		return currentResource.getName();
    	String name = currentResource.getParent().getName() + "-" + currentResource.getName();
		return name;
	}

    /**
     * Gets the image list.
     *
     * @return the image list
     */
    public List<SmallImageModel> getImageList() {
        if(null != imageList) {
            if(!imageList.isEmpty()) {
                return new ArrayList<>(imageList);
            } else {
                return new ArrayList<SmallImageModel>();
            }
        } else {
            return new ArrayList<SmallImageModel>();
        }
    }

    /**
     * Gets the play video icon.
     *
     * @return the play video icon
     */
    public String getPlayVideoIcon() {
        return playVideoIcon;
    }

    /**
     * Gets the large url.
     *
     * @return the large url
     */
    public String getLargeUrl() {
        return largeUrl;
    }

    /**
     * Gets the large enlarged image path.
     *
     * @return the large enlarged image path
     */
    public String getLargeEnlargedImagePath() {
        return largeEnlargedImagePath;
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
    
    /**
     * Gets the disclaimer font size.
     *
     * @return the disclaimer font size
     */
    public String getDisclaimerFontSize() {
		return disclaimerFontSize;
	}
    

	 public String getMagnifiyGlassColor() {
		  if(StringUtils.isNotEmpty(magnifiyGlassColor))
		  {  return magnifiyGlassColor;
				  
		  }else {
			  return "dark-blue";
		  }
	}

	/**
	 * @return the urlCta
	 */
	public String getUrlCta() {
		return urlCta;
	}

	/**
	 * @return the labelCta
	 */
	public String getLabelCta() {
		return labelCta;
	}

	/**
	 * @return the urlCtaAdd
	 */
	public String getUrlCtaAdd() {
		return urlCtaAdd;
	}

	/**
	 * @return the labelCtaAdd
	 */
	public String getLabelCtaAdd() {
		return labelCtaAdd;
	}

	/**
	 * @return the largeImageLinkUrl
	 */
	public String getLargeImageLinkUrl() {
		return largeImageLinkUrl;
	}
	/**
	 * @return the smallImageDescription
	 */
	public String getSmallImageDescription() {
		return smallImageDescription;
	}
	
	/**
	 * @return the largeImageDescription
	 */
	public String getLargeImageDescription() {
		return largeImageDescription;
	}

	/**
	 * @return the largeImageTitle
	 */
	public String getLargeImageTitle() {
		return largeImageTitle;
	}
	/**
	 * @return the smallImageTitle
	 */
	public String getSmallImageTitle() {
		return smallImageTitle;
	}
	
	
}
