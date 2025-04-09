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

/**
 * The class FAC Conjugate Columns
 * 
 * @author ronbanerjee
 *
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FACConjugateColumns extends BaseSlingModel {

	/** The constant Serial Version UUID */
	private static final long serialVersionUID = 3046177724157702374L;

	@ValueMapValue
	private String colHeading;

	@ValueMapValue
	private String image;
	
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
	private String tableData;

	/**
	 * Gets the column heading.
	 * 
	 * @return column heading
	 */
	public String getColHeading() {
		return colHeading;
	}

	/**
	 * Gets the image path.
	 * 
	 * @return image path
	 */
	public String getImage() {
		return image;
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
	 * Gets the table data.
	 * 
	 * @return the table data
	 */
	public String getTableData() {
		return tableData;
	}
}
