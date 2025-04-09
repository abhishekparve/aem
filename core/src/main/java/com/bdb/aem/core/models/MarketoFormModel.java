package com.bdb.aem.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import javax.inject.Inject;

/**
 * The Class Marketo Form Model
 */
@Model( adaptables = {Resource.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MarketoFormModel {
	
	/** The sectionTitle. */
    @Inject
    private String sectionTitle;
    
    /** The Root URL. */
    @Inject
    private String rootURL;

    /** The Munchkin ID. */
    @Inject
    private String munchkinID;

    /** The Form ID. */
    @Inject
    private String formID;

    /** The Form Title. */
    @Inject
    private String title;

    /** The Form Body Text. */
    @Inject
    private String formBodyText;
    
    /** The current resource. */
	@Inject
	Resource currentResource;
    
    /**
	 * Gets the article id.
	 *
	 * @return the article id
	 */
	public String getArticleId() {
		return currentResource.getParent().getName() + "-" + currentResource.getName();
	}

    /**
	 * @return the sectionTitle
	 */
	public String getSectionTitle() {
		return sectionTitle;
	}

	/**
     * Get the Form Root URL.
     * @return the Form Root URL
     */
    public String getRootURL() {
        return rootURL;
    }

    /**
     * Get the Form Munchkin ID.
     * @return the Form Munchkin ID
     */
    public String getMunchkinID() {
        return munchkinID;
    }

    /**
     * Get the Form ID.
     * @return the Form ID
     */
    public String getFormID() {
        return formID;
    }

    /**
     * Get the Form Title
     * @return the Form title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the Form Body Text.
     * @return the Form Body Text
     */
    public String getFormBodyText() {
        return formBodyText;
    }
}
