package com.bdb.aem.core.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;

/**
 * The Class PromoGridDetailsModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class PromoGridDetailsModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(PromoGridDetailsModel.class);

	/** The promo grid title. */
	@Inject
	private String promoGridTitle;

	/** The promo grid description. */
	@Inject
	private String promoGridDescription;

	/** The promo grid expiration label. */
	@Inject
	private String promoGridExpirationLabel;

	/** The promo grid expiration date. */
	@Inject
	private Date promoGridExpirationDate;

	/** The promo grid image. */
	@Inject
	private String promoGridImage;

	/** The promo grid alt text. */
	@Inject
	private String promoGridAltText;

	/** The border enable. */
	@Inject
	private String borderEnable;

	/** The promo grid cta label. */
	@Inject
	private String promoGridCtaLabel;

	/** The promo grid cta url. */
	@Inject
	private String promoGridCtaUrl;
	
	/** The image link url. */
	@Inject
	private String promoGridImageLinkUrl;

	/** The open new tab. */
	@Inject
	private String openNewTab;
	
	/** The open new image link tab. */
	@Inject
	private String openNewImageLinkTab;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** The resource resolver. */
	@SlingObject
	ResourceResolver resourceResolver;

	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("PromoGridDetailsModel Initiated");
		promoGridImage = externalizerService.getFormattedUrl(promoGridImage, resourceResolver);
		promoGridCtaUrl = externalizerService.getFormattedUrl(promoGridCtaUrl, resourceResolver);
		promoGridImageLinkUrl = externalizerService.getFormattedUrl(promoGridImageLinkUrl, resourceResolver);
	}

	/**
	 * Gets the promo grid image.
	 *
	 * @return the promo grid image
	 */
	public String getPromoGridImage() {
		return promoGridImage;
	}

	/**
	 * Gets the promo grid alt text.
	 *
	 * @return the promo grid alt text
	 */
	public String getPromoGridAltText() {
		return promoGridAltText;
	}

	/**
	 * Gets the border enable.
	 *
	 * @return the border enable
	 */
	public String getBorderEnable() {
		return borderEnable;
	}

	/**
	 * Gets the promo grid title.
	 *
	 * @return the promo grid title
	 */
	public String getPromoGridTitle() {
		return promoGridTitle;
	}

	/**
	 * Gets the promo grid description.
	 *
	 * @return the promo grid description
	 */
	public String getPromoGridDescription() {
		return promoGridDescription;
	}

	/**
	 * Gets the promo grid expiration label.
	 *
	 * @return the promo grid expiration label
	 */
	public String getPromoGridExpirationLabel() {
		return promoGridExpirationLabel;
	}

	/**
	 * Gets the promo grid expiration date.
	 *
	 * @return the promo grid expiration date
	 */
	public String getPromoGridExpirationDate() {
		return Optional.ofNullable(promoGridExpirationDate).map(d -> new SimpleDateFormat("dd MMMM yyyy").format(d))
				.orElse(StringUtils.EMPTY);

	}

	/**
	 * Gets the promo grid cta label.
	 *
	 * @return the promo grid cta label
	 */
	public String getPromoGridCtaLabel() {
		return promoGridCtaLabel;
	}

	/**
	 * Gets the promo grid cta url.
	 *
	 * @return the promo grid cta url
	 */
	public String getPromoGridCtaUrl() {
		return promoGridCtaUrl;
	}

	/**
	 * Gets the image link url.
	 *
	 * @return the image link url
	 */
	public String getPromoGridImageLinkUrl() {
		return promoGridImageLinkUrl;
	}
	
	/**
	 * Gets the open new image link tab.
	 *
	 * @return the open new image link tab
	 */
	public String getOpenNewImageLinkTab() {
		return openNewImageLinkTab;
	}
	
	/**
	 * Gets the open new tab.
	 *
	 * @return the open new tab
	 */
	public String getOpenNewTab() {
		return openNewTab;
	}

}