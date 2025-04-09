package com.bdb.aem.core.models;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class PerformanceInnerModel.
 */
@Model(adaptables = {Resource.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PerformanceInnerModel {

	/** The image. */
	@Inject
	@Named("image")
    @Via("resource")
	private String image;

	/** The enlargedImagePath. */
	@Inject
	@Named("enlargedImagePath")
	@Via("resource")
	private String enlargedImagePath;

	/** The altImage. */
	@Inject
	@Named("altImage")
	@Via("resource")
	private String altImage;

	/** The enlargeSize. */
	@Inject
	@Named("enlargeSize")
	@Via("resource")
	private String enlargeSize;
	
	/** The heading. */
	@Inject
	@Named("heading")
    @Via("resource")
	private String heading;

	/** The description. */
	@Inject
	@Named("description")
    @Via("resource")
	private String description;

	/** The modalHeading. */
	@Inject
	@Named("modalHeading")
	@Via("resource")
	private String modalHeading;

	/** The modalDescription. */
	@Inject
	@Named("modalDescription")
	@Via("resource")
	private String modalDescription;
	
	/** The Magnify glass Color. */
	@Inject
	@Named("magnifiyGlassColor")
	@Via("resource")
	private String magnifiyGlassColor;

	
	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public String getEnlargedImagePath() {
		return enlargedImagePath;
	}

	/**
	 * Gets the enlargeSize.
	 *
	 * @return the enlargeSize
	 */
	public String getEnlargeSize() {
		return enlargeSize;
	}

	/**
	 * Gets the altImage.
	 *
	 * @return the altImage
	 */
	public String getAltImage() {
		return altImage;
	}

	/**
	 * Gets the heading.
	 *
	 * @return the heading
	 */
	public String getHeading() {
		return heading;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the modalHeading.
	 *
	 * @return the modalHeading
	 */
	public String getModalHeading() {
		return modalHeading;
	}

	/**
	 * Gets the modalDescription.
	 *
	 * @return the modalDescription
	 */
	public String getModalDescription() {
		return modalDescription;
	}

	public String getMagnifiyGlassColor() {
		if(StringUtils.isNotEmpty(magnifiyGlassColor)) {
		  return magnifiyGlassColor;
		} 
		else {
			  return "dark-blue";
		}
	}

}