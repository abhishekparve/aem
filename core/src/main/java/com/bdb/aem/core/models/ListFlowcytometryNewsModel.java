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
 * The Class ListFlowcytometryNewsModel.
 */
@Model( adaptables = {Resource.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class ListFlowcytometryNewsModel {
	
	/** The logger. */
	Logger logger = LoggerFactory.getLogger(ListFlowcytometryNewsModel.class);
	
	/** The publication date. */
	@Inject
	private String publicationDate;
	
	/** The news title. */
	@Inject
	private String newsTitle;

	/** The news link. */
	@Inject
	private String newsLink;
	
	/** The read more label. */
	@Inject
	private String readMoreLabel;
	
	/** The read more link. */
	@Inject
	private String readMoreLink;

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
    	logger.debug("ListFlowcytometryNewsModel Initiated");
    	newsLink = externalizerService.getFormattedUrl(newsLink, resourceResolver);
    	readMoreLink = externalizerService.getFormattedUrl(readMoreLink, resourceResolver);
    }

	/**
	 * Gets the publication date.
	 *
	 * @return the publication date
	 */
	public String getPublicationDate() {
		return publicationDate;
	}

	/**
	 * Gets the news title.
	 *
	 * @return the news title
	 */
	public String getNewsTitle() {
		return newsTitle;
	}

	/**
	 * Gets the news link.
	 *
	 * @return the news link
	 */
	public String getNewsLink() {
		return newsLink;
	}

	/**
	 * Gets the read more label.
	 *
	 * @return the read more label
	 */
	public String getReadMoreLabel() {
		return readMoreLabel;
	}

	/**
	 * Gets the read more link.
	 *
	 * @return the read more link
	 */
	public String getReadMoreLink() {
		return readMoreLink;
	}
}
