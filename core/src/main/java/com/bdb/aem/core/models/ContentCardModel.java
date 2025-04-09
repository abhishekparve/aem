package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ContentCardModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContentCardModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(ContentCardModel.class);

	/** The section title text. */
	@Inject
	private String sectionTitleText;

	/** The section description. */
	@Inject
	private String sectionDescription;

	/** The subtitle text. */
	@Inject
	private String subtitleText;

	/** The slides. */
	@Inject
	private List<ContentCardMultifieldModel> contentCard;

	/**
	 * Gets the section title text.
	 *
	 * @return the section title text
	 */
	public String getSectionTitleText() {
		return sectionTitleText;
	}

	/**
	 * Gets the section description.
	 *
	 * @return the section description
	 */
	public String getSectionDescription() {
		return sectionDescription;
	}

	/**
	 * Gets the subtitle text.
	 *
	 * @return the subtitle text
	 */
	public String getSubtitleText() {
		return subtitleText;
	}

	/**
	 * Gets the slides.
	 *
	 * @return the slides
	 */
	public List<ContentCardMultifieldModel> getContentCard() {
		if (!CollectionUtils.isEmpty(contentCard)) {
			return new ArrayList<>(contentCard);
		} else {
			return new ArrayList<>();
		}
	}
}
