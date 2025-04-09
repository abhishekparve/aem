package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.bdb.aem.core.services.ExternalizerService;
import com.google.gson.annotations.SerializedName;

/**
 * The Class InstallationsInstructionsModel.
 */
@Model(adaptables = {Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class InstallationsInstructionsModel {

	/** The resource resolver. */
	@SlingObject
	ResourceResolver resourceResolver;
	
	/** The externalizer service. */
	@Inject
    ExternalizerService externalizerService;
	
     /** The category installation instructions label. */
    @Inject
    @Named("categoryInstallationInstructionsLabel")
    @SerializedName("categoryInstallationInstructionsLabel")
    private String categoryInstallationInstructionsLabel;

    /** The category installation instructions link. */
    @Inject
    @Named("categoryInstallationInstructionsLink")
    @SerializedName("categoryInstallationInstructionsLink")
    private String categoryInstallationInstructionsLink;

    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
    	categoryInstallationInstructionsLink = externalizerService.getFormattedUrl(categoryInstallationInstructionsLink, resourceResolver);
    }
    
    /**
     * Gets the category installation instructions label.
     *
     * @return the category installation instructions label
     */
    public String getCategoryInstallationInstructionsLabel() {
        return categoryInstallationInstructionsLabel;
    }

    /**
     * Gets the category installation instructions link.
     *
     * @return the category installation instructions link
     */
    public String getCategoryInstallationInstructionsLink() {
        return categoryInstallationInstructionsLink;
    }
    
}
