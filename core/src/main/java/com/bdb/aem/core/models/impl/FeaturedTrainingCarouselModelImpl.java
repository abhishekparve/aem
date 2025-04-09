package com.bdb.aem.core.models.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bdb.aem.core.models.FeaturedTrainingCarouselModel;
import com.bdb.aem.core.models.TrainingDetailsCardModel;
import com.bdb.aem.core.models.TrainingDetailsContainerModel;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.FeaturedTrainingUtil;

/**
 * The Class FeaturedTrainingCarouselModelImpl.
 */
@Model( adaptables = { Resource.class }, 
		adapters = { FeaturedTrainingCarouselModel.class }, 
		resourceType = { FeaturedTrainingCarouselModelImpl.RESOURCE_TYPE },
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FeaturedTrainingCarouselModelImpl implements FeaturedTrainingCarouselModel {
	
/** The Constant RESOURCE_TYPE. */
protected static final String RESOURCE_TYPE = "bdb-aem/components/content/featuredTrainingCarousel/v1/featuredTrainingCarousel";
	
	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(FeaturedTrainingCarouselModelImpl.class);
	
	/** The Constant TRAINING_DETAILS_RESOURCE_TYPE. */
	protected static final String TRAINING_DETAILS_RESOURCE_TYPE = "bdb-aem/proxy/components/content/trainingDetailsCard";
	
	protected static final int CAROUSEL_LIMIT = 16;
	
	/** The section title. */
	@Inject
	private String sectionTitle;
	
	/** The title. */
	@Inject
	private String title;
	
	/** The common cta label. */
	@Inject
	private String commonCtaLabel;
	
	/** The common cta path. */
	@Inject
	private String commonCtaPath;
	
	/** The featured training models. */
	private List<TrainingDetailsCardModel> featuredTrainingModels = new ArrayList<>();
	
	/** The training details page path. */
	@Inject
	private String trainingDetailsPagePath;
	
	/** The externalizer service. */
	@Inject
    ExternalizerService externalizerService;

	/** The resource resolver. */
	@SlingObject
    ResourceResolver resourceResolver;

	/**
	 * Initializes the Featured Training Model.
	 */
	@PostConstruct
    protected void init() {
    	log.debug("Inside Featured Training Carousel Model Init");
    		populateTrainingDetails(resourceResolver);
    		commonCtaPath = externalizerService.getFormattedUrl(commonCtaPath, resourceResolver);
	}
	
	/**
	 * Populate training details.
	 */
	private void populateTrainingDetails(ResourceResolver resourceResolver) {
		List<TrainingDetailsContainerModel> trainingDetailsModels = FeaturedTrainingUtil.getModelArray(trainingDetailsPagePath, resourceResolver, TRAINING_DETAILS_RESOURCE_TYPE);
		if (trainingDetailsModels != null) {
			addTrainingCardModel(trainingDetailsModels);
		}
	}
	
	private void addTrainingCardModel(List<TrainingDetailsContainerModel> trainingDetailsModels) {
		int limitCounter = 0;
		for (TrainingDetailsContainerModel trainingDetailsContainerModel : trainingDetailsModels) {
			if (limitCounter >= CAROUSEL_LIMIT) {
				break;
			}
			List<TrainingDetailsCardModel> trainingDetailsCard = trainingDetailsContainerModel.getTrainingDetailsList();
			for (TrainingDetailsCardModel trainingDetailsCardModel : trainingDetailsCard) {
				if (limitCounter >= CAROUSEL_LIMIT) {
					break;
				}
				if(trainingDetailsCardModel.isFeatured()) {
					featuredTrainingModels.add(trainingDetailsCardModel);
					limitCounter++;
				}
			}
		}
	}

	/**
	 * Gets the section title.
	 *
	 * @return the section title
	 */
	@Override
	public  String getSectionTitle() {
	    return sectionTitle;
	}
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	@Override
	public  String getTitle() {
	    return title;
	}
	
	/**
	 * Gets the common cta label.
	 *
	 * @return the common cta label
	 */
	@Override
	public  String getCommonCtaLabel() {
	    return commonCtaLabel;
	}
	
	/**
	 * Gets the common cta path.
	 *
	 * @return the common cta path
	 */
	@Override
	public  String getCommonCtaPath() {
	    return commonCtaPath;
	}
	
	/**
	 * Gets the featured training models.
	 *
	 * @return the featured training models
	 */
	@Override
	public List<TrainingDetailsCardModel> getFeaturedTrainingModels() {
		return new ArrayList<>(featuredTrainingModels);
	}	
}
