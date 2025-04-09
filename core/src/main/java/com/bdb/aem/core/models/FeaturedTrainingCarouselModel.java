package com.bdb.aem.core.models;

import java.util.List;

/**
 * The Interface FeaturedTrainingCarouselModel.
 */
public interface FeaturedTrainingCarouselModel {
	
	/**
	 * Gets the section title.
	 *
	 * @return the section title
	 */
	String getSectionTitle();
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	String getTitle();
	
	/**
	 * Gets the common cta label.
	 *
	 * @return the common cta label
	 */
	String getCommonCtaLabel();
	
	/**
	 * Gets the common cta path.
	 *
	 * @return the common cta path
	 */
	String getCommonCtaPath();
	
	/**
	 * Gets the featured training models.
	 *
	 * @return the featured training models
	 */
	List<TrainingDetailsCardModel> getFeaturedTrainingModels();
}
