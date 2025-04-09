package com.bdb.aem.core.bean;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.google.gson.annotations.SerializedName;

/**
 * The Class TrainingDetailsBean.
 * 
 * @author phanindra
 * @return Multi-field data as a bean.
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TrainingDetailsBean {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(TrainingDetailsBean.class);

	/** The Training Image */
	@Inject
	@SerializedName("trainingImage")
	private String trainingImage;

	/** The Training Alt */
	@Inject
	@SerializedName("trainingAlt")
	private String trainingAlt;

	/** The Training Image Link Url */
	@Inject
	@SerializedName("imageLinkUrl")
	private String imageLinkUrl;

	/** The Training Image Link Url new Tab */
	@Inject
	@SerializedName("openNewImageLinkTab")
	private String openNewImageLinkTab;

	/** The Training Title */
	@Inject
	@SerializedName("trainingTitle")
	private String trainingTitle;

	/** The Description */
	@Inject
	@SerializedName("description")
	private String description;

	/** The Model Link Label */
	@Inject
	@SerializedName("modelLabel")
	private String modelLabel;

	/** The Model Link Url */
	@Inject
	@SerializedName("modelUrl")
	private String modelUrl;

	/** The Model Link Info Text */
	@Inject
	@SerializedName("modelText")
	private String modelText;

	/** The CTA Label */
	@Inject
	@SerializedName("ctaLabel")
	private String ctaLabel;

	/** The CTA Url */
	@Inject
	@SerializedName("ctaUrl")
	private String ctaUrl;

	/** The CTA Color */
	@Inject
	@SerializedName("color")
	private String color;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

	@PostConstruct
	protected void init() {
			logger.debug("TrainingDetailsBean Initiated");
			modelUrl = 	externalizerService.getFormattedUrl(modelUrl, resourceResolver);
			ctaUrl = externalizerService.getFormattedUrl(ctaUrl, resourceResolver);
			imageLinkUrl = externalizerService.getFormattedUrl(imageLinkUrl, resourceResolver);
	}

	/**
	 * getTrainingImage()
	 * 
	 * @return Training Image
	 */
	public String getTrainingImage() {
		return trainingImage;
	}

	/**
	 * getTrainingAlt()
	 * 
	 * @return Training Alt
	 */
	public String getTrainingAlt() {
		return trainingAlt;
	}

	/**
	 * getTrainingTitle()
	 * 
	 * @return Training Title
	 */
	public String getTrainingTitle() {
		return trainingTitle;
	}

	/**
	 * getDescription()
	 * 
	 * @return Description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * getModelLabel()
	 * 
	 * @return Model Label
	 */
	public String getModelLabel() {
		return modelLabel;
	}

	/**
	 * getModelUrl()
	 * 
	 * @return Model Url
	 */
	public String getModelUrl() {
		return modelUrl;
	}

	/**
	 * getModelText()
	 * 
	 * @return Model Text
	 */
	public String getModelText() {
		return modelText;
	}

	/**
	 * getCtaLabel()
	 * 
	 * @return CTA Label
	 */
	public String getCtaLabel() {
		return ctaLabel;
	}

	/**
	 * getCtaUrl()
	 * 
	 * @return CTA Url
	 */
	public String getCtaUrl() {
		return ctaUrl;
	}

	/**
	 * getColor()
	 * 
	 * @return Color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @return the imageLinkUrl
	 */
	public String getImageLinkUrl() {
		return imageLinkUrl;
	}

	/**
	 * @return the openNewImageLinkTab
	 */
	public String getOpenNewImageLinkTab() {
		return openNewImageLinkTab;
	}

}
