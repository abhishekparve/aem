package com.bdb.aem.core.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

/**
 * The Class DisclaimersModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class }, adapters = {
		DisclaimersModel.class }, resourceType = {
				DisclaimersModel.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DisclaimersModel {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/disclaimer/v1/disclaimer";

	/** The Constant TEXT_IMAGE_RESOURCE_TYPE. */
	protected static final String TEXT_IMAGE_RESOURCE_TYPE = "bdb-aem/components/content/textAndImage/v1/textAndImage";

	/** The Constant log. */
	protected static final Logger LOGGER = LoggerFactory.getLogger(DisclaimersModel.class);

	/** The current page. */
	@Inject
	private Page currentPage;

	/** The request. */
	@Self
	private SlingHttpServletRequest request;

	/** The resource resolver. */
	@Inject
	ResourceResolver resourceResolver;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** The primary disclaimer description. */
	@Inject
	@Via("resource")
	private String primaryDisclaimerDescription;

	/** The primary disclaimer font size. */
	@Inject
	@Via("resource")
	private String primaryDisclaimerFontSize;

	/** The secondary disclaimer description. */
	@Inject
	@Via("resource")
	private String secondaryDisclaimerDescription;

	/** The secondary disclaimer font size. */
	@Inject
	@Via("resource")
	private String secondaryDisclaimerFontSize;

	/** The tertiary disclaimer description. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String tertiaryDisclaimerDescription;

	/** The tertiary disclaimer font size. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String tertiaryDisclaimerFontSize;
	

	/** The Global Disclaimer Model */
	private TextAndImageModel gm;

	/** The Regional Disclaimer Model */
	private TextAndImageModel rm;

	private String globalDisclaimerDescription;

	private String globalDisclaimerFontSize;
	
	private String isPdpPage;

	private String regionalDisclaimerDescription;

	private String regionalDisclaimerFontSize;
	
	private String pdpGlobalDisclaimerDescription;

	private String pdpGlobalDisclaimerFontSize;
	
	private String pdpRegionalDisclaimerDescription;

	private String pdpRegionalDisclaimerFontSize;
	
	private String pdpGlobalDisclaimerPath;
	
	private String pdpRegionalDisclaimerPath;
	/**
	 * Initializes the Disclaimers Model.
	 */
	@PostConstruct
	protected void init() {
		String globalDisclaimerPath = CommonHelper.getLabel(CommonConstants.GLOBAL_DISCLAIMER_PATH, currentPage);
		String regionalDisclaimerPath = CommonHelper.getLabel(CommonConstants.REGIONAL_DISCLAIMER_PATH, currentPage);
		
		// Code to handle PDP Disclaimers - Starts Here
		isPdpPage = String
				.valueOf(Optional.ofNullable(currentPage).map(Page::getName).map(n -> n.equals("pdp")).orElse(false));
		if (Boolean.valueOf(isPdpPage)) {
			
			handleIfPdpPage();
			TextAndImageModel pdpGm = setDisclaimer(resourceResolver, pdpGlobalDisclaimerPath);
			TextAndImageModel pdpRm = setDisclaimer(resourceResolver, pdpRegionalDisclaimerPath);
			if (pdpGm != null) {
				pdpGlobalDisclaimerDescription = StringUtils.isNotBlank(pdpGm.getDescription()) ? pdpGm.getDescription()
						: StringUtils.EMPTY;
				pdpGlobalDisclaimerFontSize = StringUtils.isNotBlank(pdpGm.getDisclaimerFontSize())
						? pdpGm.getDisclaimerFontSize()
						: StringUtils.EMPTY;

			}
			if (pdpRm != null) {
				pdpRegionalDisclaimerDescription = StringUtils.isNotBlank(pdpRm.getDescription())
						? pdpRm.getDescription()
						: StringUtils.EMPTY;
				pdpRegionalDisclaimerFontSize = StringUtils.isNotBlank(pdpRm.getDisclaimerFontSize())
						? pdpRm.getDisclaimerFontSize()
						: StringUtils.EMPTY;
			}
		}
		// Code to handle PDP Disclaimers - Ends Here
		
		gm = setDisclaimer(resourceResolver, globalDisclaimerPath);
		rm = setDisclaimer(resourceResolver, regionalDisclaimerPath);

		// Starts Here
		// This is a fallback Mechanism to fetch already authored values
		// and can be removed at a later time
		globalDisclaimerDescription = CommonHelper.getLabel(CommonConstants.GLOBAL_DISCLAIMER_LABEL, currentPage);
		globalDisclaimerFontSize = CommonHelper.getLabel(CommonConstants.GLOBAL_DISCLAIMER_FONT_SIZE_LABEL,
				currentPage);
		regionalDisclaimerDescription = CommonHelper.getLabel(CommonConstants.REGIONAL_DISCLAIMER_LABEL, currentPage);
		regionalDisclaimerFontSize = CommonHelper.getLabel(CommonConstants.REGIONAL_DISCLAIMER_FONT_SIZE_LABEL,
				currentPage);
		// Ends Here
		if (gm != null) {
			globalDisclaimerDescription = StringUtils.isNotBlank(gm.getDescription()) ? gm.getDescription()
					: StringUtils.EMPTY;
			globalDisclaimerFontSize = StringUtils.isNotBlank(gm.getDisclaimerFontSize()) ? gm.getDisclaimerFontSize()
					: StringUtils.EMPTY;

		}
		if (rm != null) {
			regionalDisclaimerDescription = StringUtils.isNotBlank(rm.getDescription()) ? rm.getDescription()
					: StringUtils.EMPTY;
			regionalDisclaimerFontSize = StringUtils.isNotBlank(rm.getDisclaimerFontSize()) ? rm.getDisclaimerFontSize()
					: StringUtils.EMPTY;
		}
		
	}

	private void handleIfPdpPage() {
		if (Boolean.valueOf(isPdpPage)) {
			String regStatus = Optional.ofNullable(request.getAttribute("regulatoryStatus")).map(r -> r.toString())
					.orElse(StringUtils.EMPTY);

		
		String pdpDisclaimerRootPagePath = Optional.ofNullable(currentPage).map(Page::getContentResource)
				.map(r -> new HierarchyNodeInheritanceValueMap(r))
				.map(v -> v.getInherited("pdpDisclaimerRootPath", String.class)).orElse(StringUtils.EMPTY);
		if(findDisclaimerDataPage(regStatus, pdpDisclaimerRootPagePath,"global") != null) {
			pdpGlobalDisclaimerPath = findDisclaimerDataPage(regStatus, pdpDisclaimerRootPagePath, "global").getPath();
		}
		if(findDisclaimerDataPage(regStatus, pdpDisclaimerRootPagePath,"regional") != null) {
			pdpRegionalDisclaimerPath = findDisclaimerDataPage(regStatus, pdpDisclaimerRootPagePath, "regional").getPath();
		}
		}
	}
	
	private Page findDisclaimerDataPage(String regStatus, String pDisclaimer, String level) {
		PageManager m = request.getResourceResolver().adaptTo(PageManager.class);
		Iterator<Page> itr = Optional.ofNullable(pDisclaimer).filter(StringUtils::isNotEmpty)
				.map(p -> m.getContainingPage(p)).map(pa -> pa.listChildren()).orElse(null);
		if (itr != null) {
			while (itr.hasNext()) {
				Page page = itr.next();
				Tag[] tArr = page.getTags();
				List<String> tags = new ArrayList<>();
				for (Tag t : tArr) {
					String title = Optional.ofNullable(t).map(Tag::getTitle).map(l -> l.trim().toLowerCase())
							.orElse(StringUtils.EMPTY);
					if (StringUtils.isNotEmpty(title)) {
						tags.add(title);
					}
				}
				if (tags.contains(regStatus.trim().toLowerCase()) && tags.contains(level)) {
					return page;
				}
			}
		}
		return null;
	}

	/**
	 * Prioritrizes {@code pr1}. Incase {@code pr1} is empty or null, considers
	 * {@code pr2}.
	 * 
	 * @param pr1              the priority1 value
	 * @param pr2              the priority2 value
	 * @param handleAnchorLink the flag to denote, if its an Rich Text.
	 * @return the updated string
	 */
	private String handleValues(String pr1, String pr2, boolean handleAnchorLink) {
		if (handleAnchorLink) {
			return StringUtils.isBlank(pr1) ? handleRte(pr2) : handleRte(pr1);
		} else {
			return StringUtils.isBlank(pr1) ? pr2 : pr1;

		}

	}

	/**
	 * Externalizer anchor tags within the {@code rText}.
	 * 
	 * @param rtext the rich text
	 * @return the updated rich text
	 */
	private String handleRte(String rtext) {
		return Optional.ofNullable(rtext).filter(StringUtils::isNotEmpty).map(rt -> {
			try {
				return CommonHelper.HandleRTEAnchorLink(rt, externalizerService, resourceResolver,StringUtils.EMPTY);
			} catch (IOException e) {
				LOGGER.error("IOException occurred:{}", e);
			}
			return StringUtils.EMPTY;
		}).orElse(StringUtils.EMPTY);
	}

	/**
	 * Sets the disclaimer.
	 *
	 * @param resourceResolver the resource resolver
	 * @param disclaimerPath   the disclaimer path
	 * @return the text and image model
	 */
	private TextAndImageModel setDisclaimer(ResourceResolver resourceResolver, String disclaimerPath) {

		if ((null != disclaimerPath && StringUtils.isNotBlank(disclaimerPath)) && (null != resourceResolver)) {
			String componentNode = CommonHelper.getComponentNode(disclaimerPath, resourceResolver,
					TEXT_IMAGE_RESOURCE_TYPE);

			if (StringUtils.isNotBlank(componentNode)) {
				Resource modelResource = resourceResolver.getResource(componentNode);
				if (modelResource != null) {
					TextAndImageModel model = modelResource.adaptTo(TextAndImageModel.class);
					if (null != model) {
						return model;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Gets the global disclaimer description.
	 *
	 * @return the global disclaimer description
	 */
	public String getGlobalDisclaimerDescription() {

		return handleValues(primaryDisclaimerDescription, globalDisclaimerDescription, true);

	}

	/**
	 * Gets the global disclaimer font size.
	 *
	 * @return the global disclaimer font size
	 */
	public String getGlobalDisclaimerFontSize() {
		return handleValues(primaryDisclaimerFontSize, globalDisclaimerFontSize, false);
	}

	/**
	 * Gets the regional disclaimer description.
	 *
	 * @return the regional disclaimer description
	 */
	public String getRegionalDisclaimerDescription() {
		return handleValues(secondaryDisclaimerDescription, regionalDisclaimerDescription, true);
	}

	/**
	 * Gets the regional disclaimer font size.
	 *
	 * @return the regional disclaimer font size
	 */
	public String getRegionalDisclaimerFontSize() {
		return handleValues(secondaryDisclaimerFontSize, regionalDisclaimerFontSize, false);
	}

	/**
	 * Gets the tertiary disclaimer description.
	 *
	 * @return the tertiary disclaimer description
	 */
	public String getTertiaryDisclaimerDescription() {
		return handleRte(tertiaryDisclaimerDescription);
	}

	/**
	 * Gets the tertiary disclaimer font size.
	 *
	 * @return the tertiary disclaimer font size
	 */
	public String getTertiaryDisclaimerFontSize() {
		return tertiaryDisclaimerFontSize;
	}

	/**
	 * Gets the checks if is disabled.
	 *
	 * @return the checks if is disabled
	 */
	public String getIsDisabled() {
		return CommonHelper.getLabel(CommonConstants.DISABLE_DISCLAIMER, currentPage);
	}
	
	public String getPdpPage() {
		return isPdpPage;
	}

	public String getPdpGlobalDisclaimerDescription() {
		return pdpGlobalDisclaimerDescription;
	}

	public String getPdpRegionalDisclaimerDescription() {
		return pdpRegionalDisclaimerDescription;
	}

	public String getPdpGlobalDisclaimerFontSize() {
		return pdpGlobalDisclaimerFontSize;
	}

	public String getPdpRegionalDisclaimerFontSize() {
		return pdpRegionalDisclaimerFontSize;
	}
}
