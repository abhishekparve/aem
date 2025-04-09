package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.bdb.aem.core.services.ExternalizerService;

/**
 * The class Contact Card List Model.
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContactCardListModel {

	/** The icon */
	@Inject
	private String icon;
	

	/** The iconLink Url */
	@Inject
	private String iconLinkUrl;
	
	/** The open New image link tab*/
	@Inject
	private String openNewIconLinkTab;
	
	/** The icon alt text */
	@Inject
	private String iconAltText;

	/** The contact type title */
	@Inject
	private String contactTypeTitle;

	/** The contact modes */
	@Inject
	private List<ContactCardModesModel> modes;
	
	   /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

	/**
	 * Gets the icon.
	 * 
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}
	
	
	/**
	 * @return the iconLinkUrl
	 */
	public String getIconLinkUrl() {
		return externalizerService.getFormattedUrl(iconLinkUrl, resourceResolver);
	}



	/**
	 * @return the openNewImageLinkTab
	 */
	public String getOpenNewIconLinkTab() {
		return openNewIconLinkTab;
	}



	/**
	 * Gets the icon alt text.
	 * 
	 * @return the icon alt text
	 */
	public String getIconAltText() {
		return iconAltText;
	}

	/**
	 * Gets the contact type title.
	 * 
	 * @return the contact type title
	 */
	public String getContactTypeTitle() {
		return contactTypeTitle;
	}

	/**
	 * Gets the contact modes.
	 * 
	 * @return the contact modes
	 */
	public List<ContactCardModesModel> getModes() {
		return Optional.ofNullable(modes).filter(m -> !m.isEmpty()).orElseGet(ArrayList::new);
	}

}
