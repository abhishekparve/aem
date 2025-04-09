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
import com.bdb.aem.core.models.CtaModel;
import com.bdb.aem.core.models.ErrorPageModel;
import com.bdb.aem.core.services.ExternalizerService;

/**
 * The Class ErrorPageModelImpl.
 */
@Model( adaptables = { Resource.class }, 
		adapters = { ErrorPageModel.class }, 
		resourceType = { ErrorPageModelImpl.RESOURCE_TYPE },
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ErrorPageModelImpl implements ErrorPageModel {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/errorpage/v1/errorpage";

	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(ErrorPageModelImpl.class);

	/** The image src. */
	@Inject
	private String imageSrc;

	/** The image alt text. */
	@Inject
	private String imageAltText;

	/** The title. */
	@Inject
	private String title;

	/** The subtitle. */
	@Inject
	private String subtitle;

	/** The description. */
	@Inject
	private String description;

	/** The link info text. */
	@Inject
	private String linkInfoText;

	/** The cta labels multi field. */
	@Inject
	private List<Resource> ctaLabelsMultiField;

	/** The cta list. */
	private List<CtaModel> ctaList = new ArrayList<>();
	
	/** The externalizer service. */
	@Inject
    ExternalizerService externalizerService;
    
	/** The resource resolver. */
	@SlingObject
    ResourceResolver resourceResolver;
	
	/**
	 * Inits the Error Page Model and populates the CTA List
	 */
	@PostConstruct
	protected void init() {
			log.debug("Inside Error Page Model Init");
			imageSrc = externalizerService.getFormattedUrl(imageSrc, resourceResolver);
			populateCtaList();		
	}

	/**
	 * Populate cta list.
	 */
	private void populateCtaList() {
		if (ctaLabelsMultiField != null) {
			for (Resource resource : ctaLabelsMultiField) {
				CtaModel ctaModel = resource.adaptTo(CtaModel.class);
				if (ctaModel.getLabel() != null && ctaModel.getPath() != null) {
					ctaList.add(ctaModel);
				}
			}
		}
	}

	/**
	 * Gets the image src.
	 *
	 * @return the image src
	 */
	public String getImageSrc() {
		return imageSrc;
	}

	/**
	 * Gets the image alt text.
	 *
	 * @return the image alt text
	 */
	public String getImageAltText() {
		return imageAltText;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the subtitle.
	 *
	 * @return the subtitle
	 */
	public String getSubtitle() {
		return subtitle;
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
	 * Gets the link info text.
	 *
	 * @return the link info text
	 */
	public String getLinkInfoText() {
		return linkInfoText;
	}

	/**
	 * Gets the cta list.
	 *
	 * @return the cta list
	 */
	public List<CtaModel> getCtaList() {
		return new ArrayList<>(ctaList);
	}
}
