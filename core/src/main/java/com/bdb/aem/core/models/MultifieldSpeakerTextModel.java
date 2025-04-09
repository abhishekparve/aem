/**
 * 
 */
package com.bdb.aem.core.models;

/**
 * @author 10339214
 *
 */


import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;


/**
 * The Class MultifieldTextModel.
 */
@Model( adaptables = {Resource.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class MultifieldSpeakerTextModel {

    /** The logger. */
    Logger logger = LoggerFactory.getLogger(MultifieldSpeakerTextModel.class);

	/** The title. */
	@Inject
	private String speakerTitle;
	
	/** The description. */
	@Inject
	private String speakerDescription;
	
	/** The description. */
	@Inject
	private String speakerImage;
	
	/** The description. */
	@Inject
	private String speakerImageAlt;
	
	/** The description. */
	@Inject
	private String speakerName;
	
	/** The description. */
	@Inject
	private String speakerImageTitle;
	
	/** The description. */
	@Inject
	private String speakerImageDescription;
	
	/** The speaker title Blog. */
	@Inject
	private String speakerTitleBlog;
	
	/** The speaker description Blog. */
	@Inject
	private String speakerDescriptionBlog;
	
	/** The speaker image. */
	@Inject
	private String speakerImageBlog;
	
	/** The speaker image alt Blog. */
	@Inject
	private String speakerImageAltBlog;
	
	/** The speaker name Blog. */
	@Inject
	private String speakerNameBlog;
	
	/** The speaker image title Blog. */
	@Inject
	private String speakerImageTitleBlog;
	
	/** The description Blog. */
	@Inject
	private String speakerImageDescriptionBlog;
	
    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
		logger.debug("MultifieldSpeakerModel Initialized");
    }


	/**
	 * @return the speakerTitle
	 */
	public String getSpeakerTitle() {
		return speakerTitle;
	}

	/**
	 * @return the speakerDescription
	 */
	public String getSpeakerDescription() {
		return speakerDescription;
	}

	/**
	 * @return the speakerImage
	 */
	public String getSpeakerImage() {
		return speakerImage;
	}

	/**
	 * @return the speakerImageAlt
	 */
	public String getSpeakerImageAlt() {
		return speakerImageAlt;
	}

	/**
	 * @return the speakerName
	 */
	public String getSpeakerName() {
		return speakerName;
	}

	/**
	 * @return the speakerImageTitle
	 */
	public String getSpeakerImageTitle() {
		return speakerImageTitle;
	}

	/**
	 * @return the speakerImageDescription
	 */
	public String getSpeakerImageDescription() {
		return speakerImageDescription;
	}


	/**
	 * @return the speakerTitleBlog
	 */
	public String getSpeakerTitleBlog() {
		return speakerTitleBlog;
	}


	/**
	 * @return the speakerDescriptionBlog
	 */
	public String getSpeakerDescriptionBlog() {
		return speakerDescriptionBlog;
	}


	/**
	 * @return the speakerImageBlog
	 */
	public String getSpeakerImageBlog() {
		return speakerImageBlog;
	}


	/**
	 * @return the speakerImageAltBlog
	 */
	public String getSpeakerImageAltBlog() {
		return speakerImageAltBlog;
	}


	/**
	 * @return the speakerNameBlog
	 */
	public String getSpeakerNameBlog() {
		return speakerNameBlog;
	}


	/**
	 * @return the speakerImageTitleBlog
	 */
	public String getSpeakerImageTitleBlog() {
		return speakerImageTitleBlog;
	}


	/**
	 * @return the speakerImageDescriptionBlog
	 */
	public String getSpeakerImageDescriptionBlog() {
		return speakerImageDescriptionBlog;
	}
	
}