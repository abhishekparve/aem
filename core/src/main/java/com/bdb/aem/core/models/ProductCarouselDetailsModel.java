package com.bdb.aem.core.models;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;

/**
 * The Class ProductCarouselDetailsModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductCarouselDetailsModel {
	/** The logger. */
	Logger logger = LoggerFactory.getLogger(ProductCarouselDetailsModel.class);

	/** The LINK_TYPE_STATIC */
	private static final String LINK_TYPE_STATIC = "static";

	/** The LINK_TYPE_PDP */
	private static final String LINK_TYPE_PDP = "pdp";

	/** The LINK_TYPE_PLP */
	private static final String LINK_TYPE_PLP = "plp";

	/** The resource */
	@Inject
	private Resource resource;

	/** The image path. */
	@Inject
	private String imagePath;

	/** The alt text. */
	@Inject
	private String altText;
	
	/** The image Link Url. */
	@Inject
	private String imageLinkUrl;
	
	/** The open New Image Link Tab. */
	@Inject
	private String openNewImageLinkTab;

	/** The product label. */
	@Inject
	private String productLabel;

	/** The product url. */
	@Inject
	private String productUrl;

	/** The link type. */
	@Inject
	private String linkType;

	/** The PDP product url */
	@Inject
	private String pdpProductUrl;

	/** The PLP product url */
	@Inject
	private String plpProductUrl;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** The resolver factory. */
	@Inject
	ResourceResolverFactory resolverFactory;

	/** The Solr search service. */
    @Inject
    SolrSearchService solrSearchService;
    
    @Inject
    Page currentPage;

	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		try (ResourceResolver resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory)) {
			imagePath = externalizerService.getFormattedUrl(imagePath, resourceResolver);
			imageLinkUrl = externalizerService.getFormattedUrl(imageLinkUrl, resourceResolver);
			productUrl = externalizerService.getFormattedUrl(productUrl, resourceResolver);
			String country = CommonHelper.getCountry(currentPage);
			pdpProductUrl = externalizerService.getFormattedUrl(CommonHelper.getPdpUrlFromTag(pdpProductUrl, country, resource, resourceResolver, solrSearchService), resourceResolver);
			plpProductUrl = Optional.ofNullable(plpProductUrl).map(u -> externalizerService.getFormattedUrl(u, resourceResolver))
			.map(e -> e.concat("?searchKey=" + getProductLabel())).orElse(StringUtils.EMPTY);
		} catch (LoginException e) {
			logger.error("LoginException {}", e.getMessage());
		}
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
	 * Gets the alt text.
	 *
	 * @return the alt text
	 */
	public String getAltText() {
		return altText;
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

	/**
	 * Gets the link type.
	 * 
	 * @return link type
	 */
	public String getLinkType() {
		return linkType;
	}

	/**
	 * Gets the product label.
	 *
	 * @return the product label
	 */
	public String getProductLabel() {
		return productLabel;
	}

	/**
	 * Gets the product url.
	 *
	 * @return the product url
	 */
	public String getProductUrl() {
		return Optional.ofNullable(linkType).filter(StringUtils::isNotBlank).map(l -> mapLinkUrl(l))
				.orElse(StringUtils.EMPTY);

	}

	/**
	 * Maps link url to correct method.
	 * 
	 * @param linkUrl
	 * @return
	 */
	private String mapLinkUrl(String linkUrl) {
		if (linkUrl.equals(LINK_TYPE_STATIC)) {
			return getStaticProductUrl();
		} else if (linkUrl.equals(LINK_TYPE_PLP)) {
			return getPlpProductUrl();
		} else if (linkUrl.equals(LINK_TYPE_PDP)) {
			return getPdpProductUrl();
		}
		return StringUtils.EMPTY;
	}

	public String getStaticProductUrl() {
		return productUrl;
	}

	/**
	 * Gets the PDP product url.
	 * 
	 * @return the PDP product url
	 */
	public String getPdpProductUrl() {
		return pdpProductUrl;
	}

	/**
	 * Gets the PLP product url.
	 * 
	 * @return the PLP product url
	 */
	public String getPlpProductUrl() {
		return plpProductUrl;

	}

}
