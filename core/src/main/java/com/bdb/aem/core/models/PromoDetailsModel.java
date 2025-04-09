package com.bdb.aem.core.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.bdb.aem.core.services.BDBApiEndpointService;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.google.gson.annotations.SerializedName;


/**
 * The Class PromoDetailsModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PromoDetailsModel {
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(PromoDetailsModel.class);

    /** The title. */
    @Inject
	@SerializedName("title")
    private String title;
    
    /** The short description. */
    @Inject
    @SerializedName("description")
    private String shortDescription;
    
    /** The expiration date. */
    @Inject
    @SerializedName("expirationDate")
    private Date expirationDate;
    
    /** The promo description. */
    @Inject
    @SerializedName("promoDescription")
    private String promoDescription;
	
	/** The image path. */
	@Inject
	@SerializedName("imgUrl")
	private String imagePath;
	
	/** The alt text. */
	@Inject
	@SerializedName("imageAltText")
	private String altText;
    
    /** The promo code value. */
	@Inject
	@SerializedName("promoCodeValue")
    private String promoCodeValue;
	
	/** The promo label. */
	@Inject
    private String promoLabel;
	
    /** The expiration label. */
    @Inject
    private String expirationLabel;
	
    /** The label cta. */
    @Inject
    private String labelCta;
    
    /** The url cta. */
    @Inject
    private String urlCta;
    
    @SerializedName("urlCta")
    private String modifiedUrlCta;    
    
    /** The image Link Url. */
    @Inject
    private String imageLinkUrl;
    
    @SerializedName("imageLinkUrl")
    private String modifiedImageLinkUrl;
 
	/** The Promotion Id */
	@Inject
	private String promoId;
    
    /** The long description. */
    @Inject
    private String longDescription;
    
    /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;
	/** The bdb api endpoint service. */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;

	/** The resource resolver. */
	@SlingObject
    ResourceResolver resourceResolver;
	
     /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
        	logger.debug("Initialize method");
            imagePath = externalizerService.getFormattedUrl(imagePath, resourceResolver);
            
            if (!StringUtils.isEmpty(urlCta)) {
            	urlCta = getImageCtaFormatteddUrl(urlCta);
            }
            if (!StringUtils.isEmpty(imageLinkUrl)) {
            	imageLinkUrl = getImageCtaFormatteddUrl(imageLinkUrl);
            }
            modifiedUrlCta = getUrlCta();
            modifiedImageLinkUrl = getImageLinkUrl();
    }
    
    public String getImageCtaFormatteddUrl(String url) {
    	String runModes = bdbApiEndpointService.getCustomRunMode();
    	url = externalizerService.getFormattedUrl(url, resourceResolver);
    	if (StringUtils.equalsIgnoreCase(runModes, CommonConstants.AUTHOR)) {
    		logger.debug("author url before replace {}", url);
    		url = StringUtils.replace(url, "html", "promodetails.html?searchKey=".concat(promoId));
    		logger.debug("url {}", url);
    	}
    	else if (StringUtils.equalsIgnoreCase(runModes, CommonConstants.PUBLISH)) {
    		logger.debug("publish url before replace {}", url);
    		url = url.concat(".promodetails?searchKey=").concat(promoId);
    		logger.debug("url {}", url);
    	}
		return url;
    	
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
	 * Gets the alt text.
	 *
	 * @return the alt text
	 */
	public String getAltText() {
		return altText;
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
	 * Gets the short description.
	 *
	 * @return the short description
	 */
	public String getShortDescription() {
		return shortDescription;
	}

	/**
	 * Gets the expiration label.
	 *
	 * @return the expiration label
	 */
	public String getExpirationLabel() {
		return expirationLabel;
	}

	/**
	 * Gets the expiration date.
	 *
	 * @return the expiration date
	 */
	public String getExpirationDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");  
		return formatter.format(expirationDate);
	}

	/**
	 * Gets the promo label.
	 *
	 * @return the promo label
	 */
	public String getPromoLabel() {
		return promoLabel;
	}

	/**
	 * Gets the promo code value.
	 *
	 * @return the promo code value
	 */
	public String getPromoCodeValue() {
		return promoCodeValue;
	}

	/**
	 * Gets the promo description.
	 *
	 * @return the promo description
	 */
	public String getPromoDescription() {
		return promoDescription;
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
	 * @return the url cta - View All Eligible Reagents
	 */
	public String getUrlCta() {
		return urlCta;	
	}

	/**
	 * @return the imageLinkUrl
	 */
	public String getImageLinkUrl() {
		return imageLinkUrl;
	}

	/**
	 * Gets the long description.
	 *
	 * @return the long description
	 */
	public String getLongDescription() {
		return longDescription;
	}

	/**
	 * Gets the Promotion Id.
	 *
	 * @return the Promotion Id
	 */
	public String getPromoId() {
		return promoId;
	}

}
