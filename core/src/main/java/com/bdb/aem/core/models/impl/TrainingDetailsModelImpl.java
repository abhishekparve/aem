package com.bdb.aem.core.models.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.bean.TrainingDetailsBean;
import com.bdb.aem.core.models.TrainingDetailsModel;

/**
 * 
 * TrainingDetailsModelImpl Class Implementation
 *
 */
@Model(
        adaptables = {Resource.class},
        adapters = {TrainingDetailsModel.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TrainingDetailsModelImpl implements TrainingDetailsModel{

	/** The logger. */
    Logger logger = LoggerFactory.getLogger(TrainingDetailsModelImpl.class);

	/** The Section Title */
	@Inject
	private String sectionTitle;

	 /** Training Details : a multi-field property fetching as List of Resources */
	@Inject
	List<Resource> trainingDetails;
	
	private List<TrainingDetailsBean> trainingDetailsList = new ArrayList<>();
	
	
	/**
     * Inits the.
	 * @throws LoginException 
     */
    @PostConstruct
    protected void init() {
    	logger.debug("Training Details Model Initiated");
            if (trainingDetails != null && !trainingDetails.isEmpty()) {
            	logger.debug("Training Details list :: {}", trainingDetails);
                for (Resource resource : trainingDetails) {
                	TrainingDetailsBean trainingDetailsModel = resource.adaptTo(TrainingDetailsBean.class); 
                	trainingDetailsList.add(trainingDetailsModel);
                }
            }
    }

    /**
     * getSectionTitle()
     * 
     * @return Section Title
     */
    public String getSectionTitle() {
    	return sectionTitle;
    }

	/**
	 * Getter to fetch the multi-field data of Training Details
	 * @return Training Details as Array List
	 */
	 @Override
	 public List<TrainingDetailsBean> getTrainingDetailsList() {
			return new ArrayList<>(trainingDetailsList);
	 }
}
