package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.bdb.aem.core.services.ExternalizerService;

/**
 * The Class GlobalFooterSocialModel.
 */
@Model( adaptables = {Resource.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class EventDetailsSocialModel {
	
	/** The social alt. */
	@Inject
	private String socialAltLinkedIn;
	
	/** The social alt. */
	@Inject
	private String linkedIn;
	
	/** The social Id. */
	@Inject
	private String socialIdLinkedIn; 
	
	/** The social icon. */
	@Inject
	private String socialIconLinkedIn;
	
	@Inject
	private String facebook;
	
	/** The social alt. */
	@Inject
	private String socialAltFacebook;
	
	/** The social Id. */
	@Inject
	private String socialIdFacebook; 
	
	/** The social icon. */
	@Inject
	private String socialIconFacebook;
	
	@Inject
	private String twitter;
	
	/** The social alt. */
	@Inject
	private String socialAltTwitter;
	
	/** The social Id. */
	@Inject
	private String socialIdTwitter; 
	
	/** The social icon. */
	@Inject
	private String socialIconTwitter;
	
	@Inject
	private String email;
	
	/** The social alt. */
	@Inject
	private String socialAltEmail;
	
	/** The social Id. */
	@Inject
	private String socialIdEmail; 
	
	/** The social icon. */
	@Inject
	private String socialIconEmail;
	
	@Inject
	private String print;
	
	/** The social alt. */
	@Inject
	private String socialAltPrint;
	
	/** The social Id. */
	@Inject
	private String socialIdPrint; 
	
	/** The social icon. */
	@Inject
	private String socialIconPrint;
	
	@Inject
	private String shareLink;
	
	/** The social alt. */
	@Inject
	private String socialAltShareLink;
	
	/** The social Id. */
	@Inject
	private String socialIdShareLink; 
	
	/** The social icon. */
	@Inject
	private String socialIconShareLink;

    /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;
    
    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
    	socialIconLinkedIn = externalizerService.getFormattedUrl(socialIconLinkedIn, resourceResolver);
    	socialIconFacebook = externalizerService.getFormattedUrl(socialIconFacebook, resourceResolver);
    	socialIconTwitter = externalizerService.getFormattedUrl(socialIconTwitter, resourceResolver);
    	socialIconEmail = externalizerService.getFormattedUrl(socialIconEmail, resourceResolver);
    	socialIconPrint = externalizerService.getFormattedUrl(socialIconPrint, resourceResolver);
    	socialIconShareLink = externalizerService.getFormattedUrl(socialIconShareLink, resourceResolver);
    }
	
	/**
	 * @return the linkedIn
	 */
	public String getLinkedIn() {
		return linkedIn;
	}

	/**
	 * @return the socialAltLinkedIn
	 */
	public String getSocialAltLinkedIn() {
		return (StringUtils.isNotEmpty(socialAltLinkedIn)) ? socialAltLinkedIn: StringUtils.EMPTY;
	}

	/**
	 * @return the socialIdLinkedIn
	 */
	public String getSocialIdLinkedIn() {
		return (StringUtils.isNotEmpty(socialIdLinkedIn)) ? socialIdLinkedIn: StringUtils.EMPTY;
	}

	/**
	 * @return the socialIconLinkedIn
	 */
	public String getSocialIconLinkedIn() {
		return (StringUtils.isNotEmpty(socialIconLinkedIn)) ? socialIconLinkedIn: StringUtils.EMPTY;
	}

	/**
	 * @return the facebook
	 */
	public String getFacebook() {
		return facebook;
	}

	/**
	 * @return the socialAltFacebook
	 */
	public String getSocialAltFacebook() {
		return (StringUtils.isNotEmpty(socialAltFacebook)) ? socialAltFacebook: StringUtils.EMPTY;
	}

	/**
	 * @return the socialIdFacebook
	 */
	public String getSocialIdFacebook() {
		return (StringUtils.isNotEmpty(socialIdFacebook)) ? socialIdFacebook: StringUtils.EMPTY;
	}

	/**
	 * @return the socialIconFacebook
	 */
	public String getSocialIconFacebook() {
		return (StringUtils.isNotEmpty(socialIconFacebook)) ? socialIconFacebook: StringUtils.EMPTY;
	}

	/**
	 * @return the twitter
	 */
	public String getTwitter() {
		return twitter;
	}

	/**
	 * @return the socialAltTwitter
	 */
	public String getSocialAltTwitter() {
		return (StringUtils.isNotEmpty(socialAltTwitter)) ? socialAltTwitter: StringUtils.EMPTY;
	}

	/**
	 * @return the socialIdTwitter
	 */
	public String getSocialIdTwitter() {
		return (StringUtils.isNotEmpty(socialIdTwitter)) ? socialIdTwitter: StringUtils.EMPTY;
	}

	/**
	 * @return the socialIconTwitter
	 */
	public String getSocialIconTwitter() {
		return (StringUtils.isNotEmpty(socialIconTwitter)) ? socialIconTwitter: StringUtils.EMPTY;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the socialAltEmail
	 */
	public String getSocialAltEmail() {
		return (StringUtils.isNotEmpty(socialAltEmail)) ? socialAltEmail: StringUtils.EMPTY;
	}

	/**
	 * @return the socialIdEmail
	 */
	public String getSocialIdEmail() {
		return (StringUtils.isNotEmpty(socialIdEmail)) ? socialIdEmail: StringUtils.EMPTY;
	}

	/**
	 * @return the socialIconEmail
	 */
	public String getSocialIconEmail() {
		return (StringUtils.isNotEmpty(socialIconEmail)) ? socialIconEmail: StringUtils.EMPTY;
	}

	/**
	 * @return the print
	 */
	public String getPrint() {
		return print;
	}

	/**
	 * @return the socialAltPrint
	 */
	public String getSocialAltPrint() {
		return (StringUtils.isNotEmpty(socialAltPrint)) ? socialAltPrint: StringUtils.EMPTY;
	}

	/**
	 * @return the socialIdPrint
	 */
	public String getSocialIdPrint() {
		return (StringUtils.isNotEmpty(socialIdPrint)) ? socialIdPrint: StringUtils.EMPTY;
	}

	/**
	 * @return the socialIconPrint
	 */
	public String getSocialIconPrint() {
		return (StringUtils.isNotEmpty(socialIconPrint)) ? socialIconPrint: StringUtils.EMPTY;
	}

	/**
	 * @return the shareLink
	 */
	public String getShareLink() {
		return shareLink;
	}

	/**
	 * @return the socialAltShareLink
	 */
	public String getSocialAltShareLink() {
		return (StringUtils.isNotEmpty(socialAltShareLink)) ? socialAltShareLink: StringUtils.EMPTY;
	}

	/**
	 * @return the socialIdShareLink
	 */
	public String getSocialIdShareLink() {
		return (StringUtils.isNotEmpty(socialIdShareLink)) ? socialIdShareLink: StringUtils.EMPTY;
	}

	/**
	 * @return the socialIconShareLink
	 */
	public String getSocialIconShareLink() {
		return (StringUtils.isNotEmpty(socialIconShareLink)) ? socialIconShareLink: StringUtils.EMPTY;
	} 

}