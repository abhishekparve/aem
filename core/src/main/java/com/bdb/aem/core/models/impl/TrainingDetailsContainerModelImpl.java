package com.bdb.aem.core.models.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.TrainingDetailsCardModel;
import com.bdb.aem.core.models.TrainingDetailsContainerModel;

/**
 * The Class TrainingDetailsContainerModelImpl.
 */
@Model( adaptables = { Resource.class }, 
		adapters = { TrainingDetailsContainerModel.class }, 
		resourceType = { TrainingDetailsContainerModelImpl.RESOURCE_TYPE },
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TrainingDetailsContainerModelImpl implements TrainingDetailsContainerModel {
	
	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/trainingDetailsCard/v1/trainingDetailsCard";
	
	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(TrainingDetailsContainerModelImpl.class);

	/** The section name text. */
	@Inject
	private String sectionNameText;
	
	/** The sub title. */
	@Inject
	private String subTitle;
	
	/** The training details multi field. */
	@Inject
	private List<Resource> trainingDetailsMultiField;
	
	/** The training details list. */
	private List<TrainingDetailsCardModel> trainingDetailsList = new ArrayList<>();
	
	/**
     * Creates and initialize Model.
     */
    @PostConstruct
    protected void init() {
    	log.debug("Inside Training Details Container Model");
    	populateTrainingDetails();
    }
	
	/**
	 * Populate training details.
	 */
	private void populateTrainingDetails() {
		if (trainingDetailsMultiField != null) {
            for (Resource resource : trainingDetailsMultiField) {
            	TrainingDetailsCardModel model = resource.adaptTo(TrainingDetailsCardModel.class);
                if(model != null) {
                	trainingDetailsList.add(model);
                }
            }            
        }
	}
	
	/**
	 * Gets the section name text.
	 *
	 * @return the section name text
	 */
	@Override
	public String getSectionNameText() {
		return sectionNameText;
	}

	/**
	 * Gets the sub title.
	 *
	 * @return the sub title
	 */
	@Override
	public String getSubTitle() {
		return subTitle;
	}

	/**
	 * Gets the training details list.
	 *
	 * @return the training details list
	 */
	@Override
	public List<TrainingDetailsCardModel> getTrainingDetailsList() {
		return new ArrayList<>(trainingDetailsList);
	}
}
