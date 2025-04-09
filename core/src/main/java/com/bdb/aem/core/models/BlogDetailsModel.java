package com.bdb.aem.core.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;


/**
 * The Class BlogDetailsModel.
 */
@Model( adaptables = {Resource.class, SlingHttpServletRequest.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class BlogDetailsModel {

	/** The logger. */
    Logger logger = LoggerFactory.getLogger(BlogDetailsModel.class);
    
    /** The Constant NO. */
    private static final String NO = "no";
    
    /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;
    
    /** The bdb api endpoint service. */
    @Inject
    BDBApiEndpointService bdbApiEndpointService;

    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;
    
    /** The banner Title. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String bannerTitleBlog;
	
	/** The banner sub title. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String bannerSubTitle;

	/** The banner image. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String bannerImageBlog;
	
	/** The dark mode. */
    @Inject
    @Via("resource")
    private String darkModeBlog;
    
    /** The Blog Brightcove Border. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
    private String blogBorder;
    
	/** The Blog Brightcove ID. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String blogVideoId;
	
	 /** The video caption. */
    @Inject
    @Via("resource")
	@Default(values = StringUtils.EMPTY)
    private String blogVideoCaption;
    
    /** The video Title. */
    @Inject
    @Via("resource")
	@Default(values = StringUtils.EMPTY)
    private String blogVideoTitle;
    
    /** The video Description. */
    @Inject
    @Via("resource")
	@Default(values = StringUtils.EMPTY)
    private String blogVideoDesc;
	
	 /** The Blog url cta. */
    @Inject
    @Via("resource")
    @Default(values = StringUtils.EMPTY)
    private String blogUrlCta;
    
    /** The blog label cta. */
    @Inject
    @Via("resource")
    @Default(values = StringUtils.EMPTY)
    private String blogLabelCta;
    
    /** The Blog label cta add. */
    @Inject
    @Via("resource")
    @Default(values = StringUtils.EMPTY)
    private String blogLabelCtaAdd;
    
    /** The Blog url cta add. */
    @Inject
    @Via("resource")
    @Default(values = StringUtils.EMPTY)
    private String blogUrlCtaAdd;
	
	/** The banner thumbnail image. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String bannerThumbnailImageBlog;

	/** The blog date. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
    private String blogDate;
	
	/** The multifield section. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public List<MultifieldSpeakerTextModel> multifieldSpeakerSectionBlog; 
	
    /**
     * Inits the.
     */
	@PostConstruct
	protected void init() {
		logger.debug("BlogDetailsModel Initialized");
		bannerImageBlog = externalizerService.getFormattedUrl(bannerImageBlog, resourceResolver);
		bannerThumbnailImageBlog = externalizerService.getFormattedUrl(bannerThumbnailImageBlog, resourceResolver);
		blogUrlCta = externalizerService.getFormattedUrl(blogUrlCta, resourceResolver);
    	blogUrlCtaAdd = externalizerService.getFormattedUrl(blogUrlCtaAdd, resourceResolver);
	}
    
    
	/**
	 * Gets the banner image.
	 *
	 * @return the banner image
	 */
	public String getBannerImageBlog() {
		return bannerImageBlog;
	}
	
	/**
	 * @return the darkModeBlog
	 */
	public String getDarkModeBlog() {
		return darkModeBlog;
	}

	/**
	 * Gets the banner title.
	 *
	 * @return the banner title
	 */
	public String getbannerTitleBlog() {
		return bannerTitleBlog;
	}
	
	/**
	 * Gets the banner sub title.
	 *
	 * @return the banner sub title
	 */
	public String getbannerSubTitle() {
		return bannerSubTitle;
	}

	/**
	 * Gets the banner thumbnail image.
	 *
	 * @return the banner thumbnail image
	 */
	public String getBannerThumbnailImageBlog() {
		return bannerThumbnailImageBlog;
	}
	 
    /**
	 * @return the blogBorder
	 */
	public String getBlogBorder() {
		return blogBorder;
	}

	/**
	 * @return the blogVideoId
	 */
	public String getBlogVideoId() {
		return blogVideoId;
	}
     
    /**
	 * @return the blogVideoCaption
	 */
	public String getBlogVideoCaption() {
		return blogVideoCaption;
	}

	/**
	 * @return the blogVideoTitle
	 */
	public String getBlogVideoTitle() {
		return blogVideoTitle;
	}

	/**
	 * @return the blogVideoDesc
	 */
	public String getBlogVideoDesc() {
		return blogVideoDesc;
	}

	/**
	 * @return the blogUrlCta
	 */
	public String getBlogUrlCta() {
		return blogUrlCta;
	}


	/**
	 * @return the blogLabelCta
	 */
	public String getBlogLabelCta() {
		return blogLabelCta;
	}


	/**
	 * @return the blogLabelCtaAdd
	 */
	public String getBlogLabelCtaAdd() {
		return blogLabelCtaAdd;
	}


	/**
	 * @return the blogUrlCtaAdd
	 */
	public String getBlogUrlCtaAdd() {
		return blogUrlCtaAdd;
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
	 * @return the multifieldSpeakerSection
	 */
	public List<MultifieldSpeakerTextModel> getMultifieldSpeakerSection() {
		return new ArrayList<>(multifieldSpeakerSectionBlog);
	}
	
	/**
	 * Gets the blog date.
	 *
	 * @return the blog date
	 */
	public String getBlogDate() {
		return Optional.ofNullable(blogDate).filter(s -> !s.equals("")).map(s -> {
			try {
				return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(s);
			} catch (ParseException e) {
				logger.error("ParseException {}", e.getMessage());
			}
			return null;
		}).map(d -> new SimpleDateFormat("MMMM dd, yyyy").format(d)).orElse("");
	}
	
}