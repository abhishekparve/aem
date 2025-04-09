package com.bdb.aem.core.models;

import java.util.List;

import com.bdb.aem.core.bean.TrainingDetailsBean;

public interface TrainingDetailsModel {
	
	/**
     * getSectionTitle()
     * 
     * @return Section Title
     */
    String getSectionTitle();
    
	/**
	 * Gets the Training Details Multi-Field as a List
	 *
	 */
	List<TrainingDetailsBean> getTrainingDetailsList();	
}
