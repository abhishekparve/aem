package com.bdb.aem.core.models.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import com.bdb.aem.core.models.AreaOfFocusModel;
import com.bdb.aem.core.models.SignUpIndustryModel;

/**
 * The Class AreaOfFocusImpl.
 */
@Model(
        adaptables = {Resource.class},
        adapters = {AreaOfFocusModel.class},
        resourceType = {AreaOfFocusImpl.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class AreaOfFocusImpl implements AreaOfFocusModel {	
	
	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/areaoffocus/v1/areaoffocus";

    /** The industry title. */
    @Inject
    private String industryTitle;
    
    /** The industry description. */
    @Inject
    private String industryDescription;
    
    /** The industry selection text. */
    @Inject
    private String industrySelectionText;    
    
    /** The role title. */
    @Inject
    private String roleTitle;
    
    /** The role description. */
    @Inject
    private String roleDescription;
    
    /** The role selection text. */
    @Inject
    private String roleSelectionText;    
    
    /** The interest title. */
    @Inject
    private String interestTitle;
    
    /** The interest description. */
    @Inject
    private String interestDescription;
    
    /** The interest selection text. */
    @Inject
    private String interestSelectionText;
    
    /** The next industry button label. */
    @Inject
    private String nextIndustryButtonLabel;
    
    /** The skip industry link label. */
    @Inject
    private String skipIndustryLinkLabel;
    
    /** The back role button label. */
    @Inject
    private String backRoleButtonLabel;
    
    /** The next role button label. */
    @Inject
    private String nextRoleButtonLabel;
    
    /** The back interest button label. */
    @Inject
    private String backInterestButtonLabel;
    
    /** The next interest button label. */
    @Inject
    private String nextInterestButtonLabel;
    

    /** The multiple industry config. */
    @Inject
    public List<Resource> multipleIndustryConfig;    
    
    /**
     * Gets Industry Title Label as a String.
     *
     * @return the industry title
     */
    public String getIndustryTitle() {
		return industryTitle;
	}

	/**
	 * Gets Industry Description Label as a String.
	 *
	 * @return the industry description
	 */
	public String getIndustryDescription() {
		return industryDescription;
	}

	/**
	 * Gets Industry Selection Label Text as a String.
	 *
	 * @return the industry selection text
	 */
	public String getIndustrySelectionText() {
		return industrySelectionText;
	}

	/**
	 * Gets Role Title Label as a String.
	 *
	 * @return the role title
	 */
	public String getRoleTitle() {
		return roleTitle;
	}

	/**
	 * Gets Role Description Label as a String.
	 *
	 * @return the role description
	 */
	public String getRoleDescription() {
		return roleDescription;
	}
	
	/**
	 * Gets Role Selection Label Text as a String.
	 *
	 * @return the role selection text
	 */
	public String getRoleSelectionText() {
		return roleSelectionText;
	}

	/**
	 * Gets Interest Title Label as a String.
	 *
	 * @return the interest title
	 */
	public String getInterestTitle() {
		return interestTitle;
	}
	
	/**
	 * Gets Interest Description Label as a String.
	 *
	 * @return the interest description
	 */
	public String getInterestDescription() {
		return interestDescription;
	}

	/**
	 * Gets Interest Selection Label Text as a String.
	 *
	 * @return the interest selection text
	 */
	public String getInterestSelectionText() {
		return interestSelectionText;
	}

	
	/**
	 * Gets Industry's Next Button Label Text as a String.
	 *
	 * @return the next industry button label
	 */
	public String getNextIndustryButtonLabel() {
		return nextIndustryButtonLabel;
	}

	/**
	 * Gets Industry's Skip Button Label Text as a String.
	 *
	 * @return the skip industry link label
	 */
	public String getSkipIndustryLinkLabel() {
		return skipIndustryLinkLabel;
	}

	/**
	 * Gets Role's Back Button Label Text as a String.
	 *
	 * @return the back role button label
	 */
	public String getBackRoleButtonLabel() {
		return backRoleButtonLabel;
	}

	/**
	 * Gets Role's Next Button Label Text as a String.
	 *
	 * @return the next role button label
	 */
	public String getNextRoleButtonLabel() {
		return nextRoleButtonLabel;
	}

	/**
	 * Gets Interest's Back Button Label Text as a String.
	 *
	 * @return the back interest button label
	 */
	public String getBackInterestButtonLabel() {
		return backInterestButtonLabel;
	}

	/**
	 * Gets Interest's Next Button Label Text as a String.
	 *
	 * @return the next interest button label
	 */
	public String getNextInterestButtonLabel() {
		return nextInterestButtonLabel;
	}
	
	/** The industry list. */
	private List<SignUpIndustryModel> industryList = new ArrayList<>();
    
    /** The Area of Focus labels. */
    private String aoFLabels;
    
    
    /**
     * Populates the Industry List.
     */
    @PostConstruct    
    protected void init() {
        if (multipleIndustryConfig != null && !multipleIndustryConfig.isEmpty()) {
        	
            for (Resource resource : multipleIndustryConfig) {
                SignUpIndustryModel industry = resource.adaptTo(SignUpIndustryModel.class);                
                industryList.add(industry);
            }
        }
    }   
    
    
    /**
     * Gets the Industry List.
     *
     * @return - Industry List as a List of Industries
     */
    public List<SignUpIndustryModel> getIndustryList() {
    	return new ArrayList<>(industryList);
    }
    
	/**
	 * Gets Area of Focus Labels.
	 *
	 * @return the Area of Focus labels
	 */
	@Override
	public String getAoFLabels() {
		return aoFLabels;
	}
}