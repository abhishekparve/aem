package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;

/**
 * The Class NewsLetterRegistrationModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NewsLetterRegistrationModel {
    
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(NewsLetterRegistrationModel.class);

    /** The news letter reg image path. */
    @Inject
    private String newsLetterRegImagePath;
    
	/** The image Link Url. */
	@Inject
	private String imageLinkUrl;

    /** The news letter reg latest article link. */
    @Inject
    private String newsLetterRegLatestArticleLink;

    /** The news letter reg view all url. */
    @Inject
    private String newsLetterRegViewAllUrl;

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
    	logger.debug("NewsLetterRegistrationModel Initiated");
    	newsLetterRegImagePath = externalizerService.getFormattedUrl(newsLetterRegImagePath, resourceResolver);
    	imageLinkUrl = externalizerService.getFormattedUrl(imageLinkUrl, resourceResolver);
    	newsLetterRegLatestArticleLink = externalizerService.getFormattedUrl(newsLetterRegLatestArticleLink, resourceResolver);
    	newsLetterRegViewAllUrl = externalizerService.getFormattedUrl(newsLetterRegViewAllUrl, resourceResolver);
    }

    /**
     * Gets the news letter reg image path.
     *
     * @return the news letter reg image path
     */
    public String getNewsLetterRegImagePath() {
        return  newsLetterRegImagePath;
    }
    
    

    /**
	 * @return the imageLinkUrl
	 */
	public String getImageLinkUrl() {
		return imageLinkUrl;
	}

	/**
     * Gets the news letter reg latest article link.
     *
     * @return the news letter reg latest article link
     */
    public String getNewsLetterRegLatestArticleLink() {
        return newsLetterRegLatestArticleLink;
    }

    /**
     * Gets the news letter reg view all url.
     *
     * @return the news letter reg view all url
     */
    public String getNewsLetterRegViewAllUrl() {
        return newsLetterRegViewAllUrl; 
    }

}
