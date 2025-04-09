package com.bdb.aem.core.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The class FAQ List Model.
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FaqListModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(FaqListModel.class);
	/** The question */
	@Inject
	private String question;
	
	/** The resource */
	@Inject
	private Resource resource;

	/** The answer */
	@Inject
	@Default(values = StringUtils.EMPTY)
	private String answer;
	
	/** The answer */
	@Inject
	private String answerPath;

	/** The image */
	@Inject
	private List<FaqListImageModel> imageList;

	@Inject
	private List<FaqListImageModel> imageListDesktop;

	@Inject
	private String imageType;
	
	@Inject
	ResourceResolverFactory resolverFactory;
	
	/** The resource resolver. */
	ResourceResolver resourceResolver;
	
	@Inject
	ExternalizerService externalizerService;
	
	
	@PostConstruct
	protected void init(){
		resourceResolver = resource.getResourceResolver();
		if(StringUtils.isNotEmpty(answerPath) && null != resourceResolver) {
			Resource res = resourceResolver.getResource(answerPath);
			if(null != res) {
				ValueMap valueMap = res.getValueMap();
				this.answer = valueMap.get("answerFaq")!= null?valueMap.get("answerFaq").toString():"";
				if(StringUtils.isNotEmpty(answer)) {
					try {
						this.answer = CommonHelper.HandleRTEAnchorLink(answer, externalizerService, resourceResolver, StringUtils.EMPTY);
					} catch (IOException e) {
						LOGGER.error("Error occcured while parsing the HTML", e);
					}
				}
			}
			
		}
	}

	/**
	 * Gets the question.
	 * 
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * Gets the answer.
	 * 
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * Gets the image.
	 * 
	 * @return the image
	 */
	@JsonGetter("imageList")
	public List<Map<String, String>> getImageList() {

		return getIsLargeImage() ? handleImageListDesktop() : handleImageList();

	}

	/**
	 * Handles image list.
	 * 
	 * @return image list.
	 */
	private List<Map<String, String>> handleImageList() {
		List<Map<String, String>> modifiedImageList = new ArrayList<>();

		Optional.ofNullable(imageList).filter(i -> !i.isEmpty()).ifPresent(i -> {
			i.forEach(item -> {
				if (item.getUrl() != null && !item.getUrl().equals(StringUtils.EMPTY)) {
					modifiedImageList.add(constructImageList(item));
				}
			});
		});
		return modifiedImageList;
	}

	/**
	 * Handles image list for desktop.
	 * 
	 * @return image list
	 */
	private List<Map<String, String>> handleImageListDesktop() {
		List<Map<String, String>> modifiedImageList = new ArrayList<>();

		Optional.ofNullable(imageListDesktop).filter(i -> !i.isEmpty()).ifPresent(i -> {
			i.forEach(item -> {
				if (item.getUrlLarge() != null && !item.getUrlLarge().equals(StringUtils.EMPTY)) {
					modifiedImageList.add(constructImageList(item));
				}
			});
		});
		return modifiedImageList;
	}

	/**
	 * Gets the flag isLargeImage.
	 * 
	 * @return islargeImage flag
	 */
	@JsonProperty("isLargeImage")
	public boolean getIsLargeImage() {
		return Optional.ofNullable(imageType).filter(StringUtils::isNotEmpty).map(i -> i.equals("large")).orElse(false);
	}

	/**
	 * Constructs image list based on image type: Large OR Small
	 * 
	 * @return the modified list
	 */
	private Map<String, String> constructImageList(FaqListImageModel item) {

		Map<String, String> imageData = new HashMap<>();
		imageData.put("url", getIsLargeImage() ? item.getUrlLarge() : item.getUrl());
		imageData.put("imageLinkUrl",
				getIsLargeImage() ? Optional.ofNullable(item.getImageLinkUrlLarge()).orElse(StringUtils.EMPTY)
						: Optional.ofNullable(item.getImageLinkUrl()).orElse(StringUtils.EMPTY));
		imageData.put("altText",
				getIsLargeImage() ? Optional.ofNullable(item.getAltTextLarge()).orElse(StringUtils.EMPTY)
						: Optional.ofNullable(item.getAltText()).orElse(StringUtils.EMPTY));
		imageData.put("caption", 
				getIsLargeImage() ? Optional.ofNullable(item.getCaptionLarge()).orElse(StringUtils.EMPTY)
						: Optional.ofNullable(item.getCaption()).orElse(StringUtils.EMPTY));
		imageData.put("enableBorder",
				getIsLargeImage() ? item.getEnableBorderLarge() : item.getEnableBorder());
		imageData.put("openNewImageLinkTab",
				getIsLargeImage() ? item.getOpenNewImageLinkTabLarge() : item.getOpenNewImageLinkTab());
		return imageData;
	}

}
