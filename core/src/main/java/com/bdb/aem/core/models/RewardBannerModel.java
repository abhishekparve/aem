package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;

/**
 * The Class RewardsAndDescriptionModel.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class RewardBannerModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(RewardBannerModel.class);
	/** The rewards title. */
	@Inject
	private String rewardsTitle;

	/** The reward points. */
	@Inject
	private String rewardPoints;

	/** The rewards sub title. */
	@Inject
	private String rewardsSubTitle;

	/** The alt text. */
	@Inject
	private String altText;

	/** The sign up label. */
	@Inject
	private String signUpLabel;

	/** The image path. */
	@Inject
	private String imagePath;
	
	/** The image path. */
	@Inject
	private String imagePathMobile;	

	/** The link. */
	@Inject
	private String link;

	/** The resource resolver. */
	@SlingObject
    ResourceResolver resourceResolver;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/**
	 * Inits the.
	 *
	 * @throws LoginException the login exception
	 */
	@PostConstruct
	protected void init() throws LoginException {
		logger.debug("RewardsAndDescriptionModel Initiated");
		imagePath = externalizerService.getFormattedUrl(imagePath, resourceResolver);
		link = externalizerService.getFormattedUrl(link, resourceResolver);
	}

	/**
	 * Gets the rewards title.
	 *
	 * @return the rewards title
	 */
	public String getRewardsTitle() {
		return rewardsTitle;
	}

	/**
	 * Gets the reward points.
	 *
	 * @return the reward points
	 */
	public String getRewardPoints() {
		return rewardPoints;
	}

	/**
	 * Gets the alt text.
	 *
	 * @return the alt text
	 */
	public String getAltText() {
		return altText;
	}

	/**
	 * Gets the sign up label.
	 *
	 * @return the sign up label
	 */
	public String getSignUpLabel() {
		return signUpLabel;
	}

	/**
	 * Gets the image path.
	 *
	 * @return the image path
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * Gets the rewards sub title.
	 *
	 * @return the rewards sub title
	 */
	public String getRewardsSubTitle() {
		return rewardsSubTitle;
	}

	/**
	 * Gets the link.
	 *
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	
	public String getImagePathMobile() {
		imagePathMobile = StringUtils.isNotEmpty(imagePathMobile) ?  imagePathMobile :  StringUtils.EMPTY;
		return imagePathMobile;
	}

}
