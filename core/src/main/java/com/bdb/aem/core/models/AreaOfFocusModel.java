package com.bdb.aem.core.models;

import java.util.List;

/**
 * The Interface AreaOfFocusModel.
 */
public interface AreaOfFocusModel {
	
	/**
	 * Gets the industry title.
	 *
	 * @return the industry title
	 */
	String getIndustryTitle();
	
	/**
	 * Gets the industry description.
	 *
	 * @return the industry description
	 */
	String getIndustryDescription();
	
	/**
	 * Gets the industry selection text.
	 *
	 * @return the industry selection text
	 */
	String getIndustrySelectionText();
	
	/**
	 * Gets the role title.
	 *
	 * @return the role title
	 */
	String getRoleTitle();
	
	/**
	 * Gets the role description.
	 *
	 * @return the role description
	 */
	String getRoleDescription();	
	
	/**
	 * Gets the role selection text.
	 *
	 * @return the role selection text
	 */
	String getRoleSelectionText();
	
	/**
	 * Gets the interest title.
	 *
	 * @return the interest title
	 */
	String getInterestTitle();
	
	/**
	 * Gets the interest description.
	 *
	 * @return the interest description
	 */
	String getInterestDescription();
	
	/**
	 * Gets the interest selection text.
	 *
	 * @return the interest selection text
	 */
	String getInterestSelectionText();
	
	/**
	 * Gets the next industry button label.
	 *
	 * @return the next industry button label
	 */
	String getNextIndustryButtonLabel();
	
	/**
	 * Gets the skip industry link label.
	 *
	 * @return the skip industry link label
	 */
	String getSkipIndustryLinkLabel();
	
	/**
	 * Gets the back role button label.
	 *
	 * @return the back role button label
	 */
	String getBackRoleButtonLabel();
	
	/**
	 * Gets the next role button label.
	 *
	 * @return the next role button label
	 */
	String getNextRoleButtonLabel();
	
	/**
	 * Gets the back interest button label.
	 *
	 * @return the back interest button label
	 */
	String getBackInterestButtonLabel();
	
	/**
	 * Gets the next interest button label.
	 *
	 * @return the next interest button label
	 */
	String getNextInterestButtonLabel();
	
	/**
	 * Gets the industry list.
	 *
	 * @return the industry list
	 */
	List<SignUpIndustryModel> getIndustryList();

	/**
	 * Gets the Area of Focus labels.
	 *
	 * @return the Area of Focus labels
	 */
	String getAoFLabels();
}