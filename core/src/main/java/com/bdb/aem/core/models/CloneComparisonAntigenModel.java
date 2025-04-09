package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.tools.SlingModelService;
import com.bdb.aem.core.services.tools.impl.CloneComparisonAntigenServiceImpl;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Clone Comparison - Antigen Model.
 * 
 * @author ronbanerjee
 *
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CloneComparisonAntigenModel extends BaseSlingModel {

	/** The constant Serial Version UUID */
	private static final long serialVersionUID = -4464110049803102418L;

	@ValueMapValue
	private String title;

	@ValueMapValue
	private String description;

	@ValueMapValue
	private String imagePath;

	@ValueMapValue
	private String imageAltText;
	
	@ValueMapValue
	private String imageLinkUrl;
	
	@ValueMapValue
	private boolean openNewImageLinkTab;

	@ValueMapValue
	private String plusIconPath;

	@ValueMapValue
	private String minusIconPath;

	@ValueMapValue
	private String plusIconAlt;

	@ValueMapValue
	private String minusIconAlt;

	@ValueMapValue
	private String targetMoleculeLabel;

	@ValueMapValue
	private String chooseReactivityLabel;

	@ValueMapValue
	private String sortByLabel;

	@ValueMapValue
	private String filterKeywordsLabel;

	@ValueMapValue
	private String filterKeywordsMessageLabel;

	@ValueMapValue
	private String searchPlaceholderLabel;

	@ValueMapValue
	private String antigenNameLabel;

	@ValueMapValue
	private String geneNameLabel;

	@ValueMapValue
	private String distributionLabel;

	@ValueMapValue
	private String formatsLabel;

	@ValueMapValue
	private String viewClonesLabel;

	@ValueMapValue
	private String aliasLabel;

	@ValueMapValue
	private String functionLabel;

	@ValueMapValue
	private String availableFormatsLabel;

	@ValueMapValue
	private String descriptionLabel;

	@ValueMapValue
	private String mWlabel;

	@ValueMapValue
	private String linkCatalogLabel;

	@ValueMapValue
	private String featurePanelLabel;

	@ValueMapValue
	private String noResultsTitle;

	@ValueMapValue
	private String noResultsIcon;

	@ValueMapValue
	private String noResultsIconAlt;

	@ValueMapValue
	private String publicationLabel;

	@ValueMapValue
	private String loadMoreCtaLabel;

	@ValueMapValue
	private String searchPageUrl;
	
	@ValueMapValue
	private String entrezgeneIdLabel;

	private String dyeNamePlaceholder;

	private String linkCatalogPlaceholder;

	@ValueMapValue
	private String linkToCatalogText;
	
	/** The resource resolver. */
	@SlingObject
    ResourceResolver resourceResolver;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	private List<String> targetMolecules;

	private List<Map<String, String>> sortItems;

	private Map<String, String> noResults = new HashMap<>();

	@OSGiService
	private transient CloneComparisonAntigenServiceImpl cloneComparisonService;

	/**
	 * Gets the title.
	 * 
	 * @return the title;
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the description
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
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
	 * Gets the image alt text.
	 * 
	 * @return the image alt text
	 */
	public String getImageAltText() {
		return imageAltText;
	}

	/**
	 * @return the imageLinkUrl
	 */
	public String getImageLinkUrl() {
		return imageLinkUrl = externalizerService.getFormattedUrl(imageLinkUrl, resourceResolver);
	}

	/**
	 * @return the openNewImageLinkTab
	 */
	public boolean getOpenNewImageLinkTab() {
		return openNewImageLinkTab;
	}

	/**
	 * Gets the plus icon path.
	 * 
	 * @return the plus icon path
	 */
	public String getPlusIconPath() {
		return plusIconPath;
	}

	/**
	 * Gets the minus icon path.
	 * 
	 * @return the minus icon path
	 */
	public String getMinusIconPath() {
		return minusIconPath;
	}

	/**
	 * Gets the plus icon path.
	 * 
	 * @return the plus icon path
	 */
	public String getPlusIconAlt() {
		return plusIconAlt;
	}

	/**
	 * Gets the minus icon path.
	 * 
	 * @return the minus icon path
	 */
	public String getMinusIconAlt() {
		return minusIconAlt;
	}

	/**
	 * Gets the target molecule label.
	 * 
	 * @return the target molecule label
	 */
	public String getTargetMoleculeLabel() {
		return targetMoleculeLabel;
	}

	/**
	 * Gets the choose reactivity label.
	 * 
	 * @return the choose reactivity label
	 */
	public String getChooseReactivityLabel() {
		return chooseReactivityLabel;
	}

	/**
	 * Gets the sort by label.
	 * 
	 * @return the sort by label
	 */
	public String getSortByLabel() {
		return sortByLabel;
	}

	/**
	 * Gets the filter keywords label.
	 * 
	 * @return the filter keywords label
	 */
	@JsonProperty("filterTitle")
	public String getFilterKeywordsLabel() {
		return filterKeywordsLabel;
	}

	/**
	 * Gets the filter keywords message label.
	 * 
	 * @return the filter keywords message label
	 */
	public String getFilterKeywordsMessageLabel() {
		return filterKeywordsMessageLabel;
	}

	/**
	 * Gets the search placeholder label.
	 * 
	 * @return the search place holder label
	 */
	public String getSearchPlaceholderLabel() {
		return searchPlaceholderLabel;
	}

	/**
	 * Gets the antigen label.
	 * 
	 * @return the antigen label
	 */
	public String getAntigenNameLabel() {
		return antigenNameLabel;
	}

	/**
	 * Gets the gene label.
	 * 
	 * @return the gene label
	 */
	public String getGeneNameLabel() {
		return geneNameLabel;
	}

	/**
	 * Gets the distribution label.
	 * 
	 * @return the distribution label
	 */
	public String getDistributionLabel() {
		return distributionLabel;
	}

	/**
	 * Gets the formats label.
	 * 
	 * @return the formats label
	 */
	public String getFormatsLabel() {
		return formatsLabel;
	}

	/**
	 * Gets the view clones label.
	 * 
	 * @return the view clones label
	 */
	public String getViewClonesLabel() {
		return viewClonesLabel;
	}

	/**
	 * Gets the alias label.
	 * 
	 * @return the alias label
	 */
	public String getAliasLabel() {
		return aliasLabel;
	}

	/**
	 * Gets the function label.
	 * 
	 * @return the function label
	 */
	public String getFunctionLabel() {
		return functionLabel;
	}

	/**
	 * Gets the available formats label.
	 * 
	 * @return the available formats label
	 */
	public String getAvailableFormatsLabel() {
		return availableFormatsLabel;
	}

	/**
	 * Gets the description label.
	 * 
	 * @return the description label
	 */
	public String getDescriptionLabel() {
		return descriptionLabel;
	}

	/**
	 * Gets the mW label.
	 * 
	 * @return the mW label
	 */
	public String getmWlabel() {
		return mWlabel;
	}

	/**
	 * Gets the link catalog label.
	 * 
	 * @return the link catalog label
	 */
	public String getLinkCatalogLabel() {
		return linkCatalogLabel;
	}

	/**
	 * Gets the feature panel label.
	 * 
	 * @return the feature panel label
	 */
	public String getFeaturePanelLabel() {
		return featurePanelLabel;
	}

	/**
	 * Gets the publication label.
	 * 
	 * @return the publication label
	 */
	public String getPublicationLabel() {
		return publicationLabel;
	}

	/**
	 * Gets the no Results map.
	 * 
	 * @return the no results map
	 */
	public Map<String, String> getNoResults() {
		noResults.put("heading", noResultsTitle == null ? StringUtils.EMPTY : noResultsTitle);
		noResults.put("altText", noResultsIconAlt == null ? StringUtils.EMPTY : noResultsIconAlt);
		noResults.put("icon", noResultsIcon == null ? StringUtils.EMPTY : noResultsIcon);

		return noResults;
	}

	/**
	 * Gets the target molecules.
	 * 
	 * @return the target molecules
	 */
	public List<String> getTargetMoleculeDropdown() {
		return Optional.ofNullable(targetMolecules).orElseGet(ArrayList::new);	
	}

	/**
	 * Sets the target molecules.
	 * 
	 * @param targetMolecules
	 */
	public void setTargetMoleculeDropdown(List<String> targetMolecules) {
		this.targetMolecules = Collections.unmodifiableList(targetMolecules);
	}

	/**
	 * Gets the sort items.
	 * 
	 * @return the sort items
	 */
	public List<Map<String, String>> getSortItems() {
		return Optional.ofNullable(sortItems).orElseGet(ArrayList::new);
	}

	/**
	 * Sets the sort items.
	 * 
	 * @param sortItems
	 */
	public void setSortItems(List<Map<String, String>> sortItems) {
		this.sortItems = Collections.unmodifiableList(sortItems);
	}

	/**
	 * Gets the load more cta label.
	 * 
	 * @return the load more cta
	 */
	@JsonProperty("loadMoreCta")
	public String getLoadMoreCtaLabel() {
		return loadMoreCtaLabel;
	}

	/**
	 * @see BaseSlingModel#getDataService()
	 */
	@Override
	public SlingModelService getDataService() {
		return cloneComparisonService;
	}

	/**
	 * Gets the search page url.
	 * 
	 * @return the search page url
	 */
	public String getSearchPageUrl() {
		return externalizer.getFormattedUrl(searchPageUrl, getResolver());
	}

	/**
	 * Gets the Dye Name Placeholder.
	 * 
	 * @return the dye name placeholder
	 */
	public String getDyeNamePlaceholder() {
		return dyeNamePlaceholder;
	}

	/**
	 * Sets the dyename placeholder.
	 * 
	 * @param dyeNamePlaceholder, the dye name placeholder
	 */
	public void setDyeNamePlaceholder(String dyeNamePlaceholder) {
		this.dyeNamePlaceholder = dyeNamePlaceholder;
	}

	/**
	 * Gets the link catalog placeholder.
	 * 
	 * @return the link catalog placeholder
	 */
	public String getLinkCatalogPlaceholder() {
		return linkCatalogPlaceholder;
	}

	/**
	 * Sets the link to catalog placeholder.
	 * 
	 * @param linkCatalogPlaceholder, the link to catalog placeholder
	 */
	public void setLinkToCatalogPlaceholder(String linkCatalogPlaceholder) {
		this.linkCatalogPlaceholder = linkCatalogPlaceholder;
	}

	/**
	 * Gets the link catalog text.
	 * 
	 * @return the link catalog text
	 */
	public String getLinkToCatalogText() {
		return linkToCatalogText;
	}
	
	public String getEntrezgeneIdLabel() {
		return entrezgeneIdLabel;
	}
}
