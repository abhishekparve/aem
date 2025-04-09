package com.bdb.aem.core.models;

import java.util.List;

/**
 * The Interface TrainingDetailsContainerModel.
 */
public interface TrainingDetailsContainerModel {
	
	/**
	 * Gets the section name text.
	 *
	 * @return the section name text
	 */
	String getSectionNameText();
	
	/**
	 * Gets the sub title.
	 *
	 * @return the sub title
	 */
	String getSubTitle();
	
	/**
	 * Gets the training details list.
	 *
	 * @return the training details list
	 */
	List<TrainingDetailsCardModel> getTrainingDetailsList();
}
