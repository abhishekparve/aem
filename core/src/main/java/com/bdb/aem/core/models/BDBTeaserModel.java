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

import com.bdb.aem.core.services.ExternalizerService;

/**
 * The Class TestimonialTeaserModel.
 */
@Model(	adaptables = Resource.class,
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BDBTeaserModel {
	
	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(BDBTeaserModel.class);
	
	/** The pname. */
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String pname;

	/** The department. */
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String department;

	/** The university. */
	@Inject
	@Default(values = "false")
	private String university;
	
	/** The fileReference. */
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String fileReference;
	
	/** The university. */
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String brightcoveVideoId;
	
	/** The playIcon. */
	@Inject
	@Default(values =  StringUtils.EMPTY)
	private String playIcon;
	
	/** The video enabled. */
	@Inject
	@Default(values =  StringUtils.EMPTY)
	private String videoEnabled;
	
	/** The primaryCtaLabel. */
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String primaryCtaLabel;
	
	/** The primaryCtaLink. */
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String primaryCtaLink;
	
	/** The secondaryCtaLabel. */
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String secondaryCtaLabel;
	
	/** The secondaryCtaLink. */
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String secondaryCtaLink;
	
	/** The ctaTypeAsVideo. */
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String ctaTypeAsVideo;
	
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
		log.debug("Inside BDB Teaser Model Init");
		
		if(StringUtils.isNotEmpty(primaryCtaLink)) {
			primaryCtaLink = externalizerService.getFormattedUrl(primaryCtaLink, resourceResolver);
        }
		
		if(StringUtils.isNotEmpty(secondaryCtaLink)) {
			secondaryCtaLink = externalizerService.getFormattedUrl(secondaryCtaLink, resourceResolver);
        }
		
		if(StringUtils.isNotEmpty(fileReference)) {
			fileReference = externalizerService.getFormattedUrl(fileReference, resourceResolver);
        }
		
		if(StringUtils.isNotEmpty(playIcon)) {
			playIcon = externalizerService.getFormattedUrl(playIcon, resourceResolver);
        }
	}
	/**
	 * @return the pname
	 */
	public String getPname() {
		return pname;
	}
	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * @return the university
	 */
	public String getUniversity() {
		return university;
	}
	/**
	 * @return the brightcoveVideoId
	 */
	public String getBrightcoveVideoId() {
		return brightcoveVideoId;
	}
	/**
	 * @return the playIcon
	 */
	public String getPlayIcon() {
		return playIcon;
	}
	/**
	 * @return the primaryCtaLabel
	 */
	public String getPrimaryCtaLabel() {
		return primaryCtaLabel;
	}	
	/**
	 * @return the primaryCtaLink
	 */
	public String getPrimaryCtaLink() {
		return primaryCtaLink;
	}
	/**
	 * @return the secondaryCtaLabel
	 */
	public String getSecondaryCtaLabel() {
		return secondaryCtaLabel;
	}
	/**
	 * @return the secondaryCtaLink
	 */
	public String getSecondaryCtaLink() {
		return secondaryCtaLink;
	}
	/**
	 * @return the ctaTypeAsVideo
	 */
	public String getCtaTypeAsVideo() {
		return ctaTypeAsVideo;
	}
	/**
	 * @return the videoEnabled
	 */
	public String getVideoEnabled() {
		return videoEnabled;
	}
	/**
	 * @return the fileReference
	 */
	public String getFileReference() {
		return fileReference;
	}
	
}
