package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;

/**
 * This class is to generate the model attributes for Alert Banner component
 */
@Model(adaptables = {Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AlertBannerModel {
    /**
     * The logger.
     */
    Logger logger = LoggerFactory.getLogger(AlertBannerModel.class);

    /**
     * The Alert Icon Image Path.
     */
    @Inject
    private String alertIcon;

    /**
     * The Alert Information Link.
     */
    @Inject
    private String alertInfoLink;

    /**
     * The Alert Second Icon Image Path.
     */
    @Inject
    private String alertIconOptional;

    /**
     * The Alert Second Information Link.
     */
    @Inject
    private String alertInfoLinkOptional;


    /**
     * The ExternalizerService
     **/

    @Inject
    ExternalizerService externalizerService;
    
    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

	@PostConstruct
	protected void init() {
		logger.debug("AlertBannerModel Initialized :: ");
		alertIcon = externalizerService.getFormattedUrl(alertIcon, resourceResolver);
		alertInfoLink = externalizerService.getFormattedUrl(alertInfoLink, resourceResolver);
		alertIconOptional = externalizerService.getFormattedUrl(alertIconOptional, resourceResolver);
		alertInfoLinkOptional = externalizerService.getFormattedUrl(alertInfoLinkOptional, resourceResolver);
	}

    /**
     * Get Alert Icon
     *
     * @return
     */
    public String getAlertIcon() {
        return alertIcon;
    }

    /**
     * Get the Alert Info Link
     *
     * @return
     */
    public String getAlertInfoLink() {
        return alertInfoLink;
    }

    /**
     * Get the Alert Icon Optional
     *
     * @return
     */
    public String getAlertIconOptional() {
        return alertIconOptional;
    }

    /**
     * Get the Alert Info Link Optional
     *
     * @return
     */
    public String getAlertInfoLinkOptional() {
        return alertInfoLinkOptional;

    }
}
