package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.models.BDBBrightcoveVideo;
import com.bdb.aem.core.services.BDBApiEndpointService;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * The type Bdb brightcove video.
 */
@Model(adaptables = {
        SlingHttpServletRequest.class}, adapters = BDBBrightcoveVideo.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = BDBBrightcoveVideoImpl.RESOURCE_TYPE)

public class BDBBrightcoveVideoImpl implements BDBBrightcoveVideo {

    /**
     * The constant RESOURCE_TYPE.
     */
    protected static final String RESOURCE_TYPE = "bdb-aem/components/content/brightcove-video";
    /**
     * The constant log.
     */
    protected static final Logger log = LoggerFactory.getLogger(BDBBrightcoveVideoImpl.class);

    @Inject
    private BDBApiEndpointService serviceConfig;


    @ValueMapValue
    @Default(values = "")
    private String videoId;


    @ValueMapValue
    private String resolution;


    @ValueMapValue
    @Default(values = "")
    private String title;


    @ValueMapValue
    @Default(values = "")
    private String subtitle;


    @ValueMapValue
    @Default(values = "")
    private String caption;


    @ValueMapValue
    @Default(values = "")
    private String titleSize;


    @ValueMapValue
    @Default(values = "")
    private String description;


    @ValueMapValue
    @Default(values = "")
    private String togglePaddingTop;

    @ValueMapValue
    @Default(values = "")
    private String togglePaddingBottom;

    @ValueMapValue
    @Default(values = "")
    private String border;

    @ValueMapValue
    @Default(values = "left")
    private String textAlignment;

    @ValueMapValue
    @Default(values = "left")
    private String videoAlignment;
    
    /** The background color. */
    @ValueMapValue
	@Default(values = StringUtils.EMPTY)
	public String backgroundColor;
	
    /**
     * Init.
     */
    @PostConstruct
    protected void init() {
        log.debug("Inside BDBBrightcoveVideoImpl init method");

    }


    @Override
    public String getVideoId() {
        return this.videoId;
    }

    @Override
    public String getBrightcoveAccountId() {
        if (serviceConfig != null) {
            return serviceConfig.brightcoveAccountId();
        } else {
            return "";
        }
    }

    @Override
    public String getBrightcovePlayerId() {

        if (serviceConfig != null) {
            return serviceConfig.brightcovePlayerId();
        } else {
            return "";
        }

    }

    @Override
    public String getResolution() {
        return this.resolution;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getTitleSize() {
        return this.titleSize;
    }

    @Override
    public String getSubTitle() {
        return this.subtitle;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getCaption() {
        return this.caption;
    }


    @Override
    public String getTextAlignment() {
        return this.textAlignment;
    }

    @Override
    public String getVideoAlignment() {
        return this.videoAlignment;
    }

    @Override
    public String getTogglePaddingBottom() {
        return this.togglePaddingBottom;
    }

    @Override
    public String getTogglePaddingTop() {
        return this.togglePaddingTop;
    }

    @Override
    public String getBorder() { return this.border; }
    
    /**
	 * Gets the background color.
	 *
	 * @return the background color
	 */
	public String getBackgroundColor() {
		return this.backgroundColor;
	}
}
