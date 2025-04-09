package com.bdb.aem.core.models;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.bdb.aem.core.services.ExternalizerService;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FACGatingImages extends BaseSlingModel {

	/** The constant Serial Version UUID */
	private static final long serialVersionUID = -2044679406953857297L;

	@ValueMapValue
	private String imageLabel;

	@ValueMapValue
	private String imageUrl;
	
	@ValueMapValue
	private String imageLinkUrl;
	
	@ValueMapValue
	private String openNewImageLinkTab;
	
	/** The resource resolver. */
	@SlingObject
    ResourceResolver resourceResolver;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	@ValueMapValue
	private String notesHeading;

	@ValueMapValue
	private String notesDescription;

	@ValueMapValue
	private String permLabel;

	@ValueMapValue
	private String permUrl;
	
	@ValueMapValue
	private String valueText;
	
	@ValueMapValue
	private String valueHref;

	/**
	 * Gets the Image Label.
	 * 
	 * @return the image label
	 */
	public String getImageLabel() {
		return imageLabel;
	}

	/**
	 * Gets the Image URL
	 * 
	 * @return the Image URL
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @return the imageLinkUrl
	 */
	public String getImageLinkUrl() {
		return externalizerService.getFormattedUrl(imageLinkUrl, resourceResolver);
	}

	/**
	 * @return the openNewImageLinkTab
	 */
	public String getOpenNewImageLinkTab() {
		return openNewImageLinkTab;
	}

	/**
	 * Gets the notes heading.
	 * 
	 * @return the notes heading
	 */
	public String getNotesHeading() {
		return notesHeading;
	}

	/**
	 * Gets the notes description
	 * 
	 * @return the notes description
	 */
	public String getNotesDescription() {
		return notesDescription;
	}

	/**
	 * Gets the perm label.
	 * 
	 * @return the perm label
	 */
	public String getPermLabel() {
		return permLabel;
	}

	/**
	 * Gets the perm URL.
	 * 
	 * @return the perm URL
	 */
	public String getPermUrl() {
		return permUrl;
	}
	
	/**
	 * Gets the value text.
	 * 
	 * @return the value text
	 */
	public String getValueText() {
		return valueText;
	}

	/**
	 * Gets the value href.
	 * 
	 * @return the value href
	 */
	public String getValueHref() {
		return valueHref;
	}
}
