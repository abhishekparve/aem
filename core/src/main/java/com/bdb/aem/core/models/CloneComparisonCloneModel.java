package com.bdb.aem.core.models;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.bdb.aem.core.services.tools.impl.CloneComparisonServiceImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Clone Comparison - Clone Model.
 * 
 * @author ronbanerjee
 *
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CloneComparisonCloneModel extends BaseSlingModel {

	/** The constant Serial Version UUID */
	private static final long serialVersionUID = 2578653243496985958L;

	@ValueMapValue
	private String targetLabel;

	@ValueMapValue
	private String moleculeLabel;

	@ValueMapValue
	private String aliasesLabel;;

	@ValueMapValue
	private String noOfClonesLabel;

	@ValueMapValue
	private String cloneNameLabel;

	@ValueMapValue
	private String reactivityLabel;

	@ValueMapValue
	private String applicationsLabel;

	@ValueMapValue
	private String fixationLabel;

	@ValueMapValue
	private String isotypeLabel;

	@ValueMapValue
	private String regulatoryStatusLabel;

	@ValueMapValue
	private String hldaLabel;

	@ValueMapValue
	private String referencesLabel;

	@ValueMapValue
	private String availableFormatsLabel;
	
	@ValueMapValue
	private String formatLabel;
	
	@ValueMapValue
	private String viewProductsLabel;
	
	@ValueMapValue
	private String backToSelectionIcon;
	
	@ValueMapValue
	private String backToSelectionIconAlt;

	@ValueMapValue
	private String editLabel;

	private Map<String, Map<String, String>> columnLabels = new LinkedHashMap<>();

	@OSGiService
	private transient CloneComparisonServiceImpl cloneService;

	/**
	 * Gets the target label.
	 * 
	 * @return the target label
	 */
	@JsonProperty("reactivity")
	public String getTargetLabel() {
		return targetLabel;
	}

	/**
	 * Gets the molecule label.
	 * 
	 * @return the molecule label
	 */
	@JsonProperty("specificity")
	public String getMoleculeLabel() {
		return moleculeLabel;
	}
	
	/**
	 * Gets the format label.
	 * 
	 * @return the format label
	 */
	@JsonProperty("imageFormat")
	public String getFormatLabel() {
		return formatLabel;
	}


	/**
	 * Gets the aliases label.
	 * 
	 * @return the aliases label
	 */
	@JsonIgnore
	public String getAliasesLabel() {
		return aliasesLabel;
	}

	/**
	 * Gets the no of clones label.
	 * 
	 * @return the no of clones label
	 */
	public String getNoOfClonesLabel() {
		return noOfClonesLabel;
	}

	/**
	 * Gets the clone Name label.
	 * 
	 * @return the clone name label
	 */
	@JsonIgnore
	public String getCloneNameLabel() {
		return cloneNameLabel;
	}

	/**
	 * Gets the reactivity label.
	 * 
	 * @return the reactivity label
	 */
	@JsonIgnore
	public String getReactivityLabel() {
		return reactivityLabel;
	}

	/**
	 * Gets the application label.
	 * 
	 * @return the application label
	 */
	@JsonIgnore
	public String getApplicationLabel() {
		return applicationsLabel;
	}

	/**
	 * Gets the fixation label.
	 * 
	 * @return the fixation label
	 */
	@JsonIgnore
	public String getFixationLabel() {
		return fixationLabel;
	}

	/**
	 * Gets the isotype label.
	 * 
	 * @return the isotype label
	 */
	@JsonIgnore
	public String getIsotypeLabel() {
		return isotypeLabel;
	}

	/**
	 * Gets the regulatory status label.
	 * 
	 * @return the regulatory status label
	 */
	@JsonIgnore
	public String getRegulatoryStatusLabel() {
		return regulatoryStatusLabel;
	}

	/**
	 * Gets the HLDA label.
	 * 
	 * @return the HLDA label
	 */
	@JsonIgnore
	public String getHldaLabel() {
		return hldaLabel;
	}

	/**
	 * Gets the references label.
	 * 
	 * @return the references label
	 */
	@JsonIgnore
	public String getReferencesLabel() {
		return referencesLabel;
	}

	/**
	 * Gets the available formats label.
	 * 
	 * @return the available formats label
	 */
	@JsonIgnore
	public String getAvailableFormatsLabel() {
		return availableFormatsLabel;
	}

	/**
	 * Gets the view products label.
	 * 
	 * @return the view products label
	 */
	public String getViewProductsLabel() {
		return viewProductsLabel;
	}

	/**
	 * Gets the edit label.
	 * 
	 * @return the edit label
	 */
	public String getEditLabel() {
		return editLabel;
	}

	/**
	 * Gets the view products placeholder.
	 * 
	 * @return the view products placeholder
	 */
	public String getViewProductsPlaceholder() {
		return cloneService.getViewProductsPlaceholder();
	}
	
	/**
	 * Gets the Dye Name Placeholder.
	 * 
	 * @return the dye name placeholder
	 */
	public String getDyeNamePlaceholder() {
		return cloneService.getDyeNamePlaceholderForClone();
	}
	
	/**
	 * Gets the Back to Selection Icon.
	 * 
	 * @return the back to selection icon
	 */
	@JsonProperty("leftArrow")
	public String getBackToSelectionIcon() {
		return externalizer.getFormattedUrl(backToSelectionIcon, getResolver());
	}
	
	/**
	 * Gets the back to selection icon alt.
	 * 
	 * @return the back to selection icon alt.
	 */
	@JsonProperty("leftArrowAltText")
	public String getBackToSelectionIconAlt() {
		return backToSelectionIconAlt;
	}

	/**
	 * Gets the column labels.
	 * 
	 * @return the column labels
	 */
	public Map<String, Map<String, String>> getColumnLabels() {
		columnLabels.put("cloneName", getInnerMap(cloneNameLabel,"text"));
		columnLabels.put("aliases", getInnerMap(aliasesLabel,"text"));
		columnLabels.put("crossReactivity", getInnerMap(reactivityLabel,"text"));
		columnLabels.put("application", getInnerMap(applicationsLabel,"text"));
		columnLabels.put("isotype", getInnerMap(isotypeLabel,"text"));
		columnLabels.put("regulatoryStatus", getInnerMap(regulatoryStatusLabel,"text"));
		columnLabels.put("hldaId", getInnerMap(hldaLabel,"text"));
		columnLabels.put("otherFormats", getInnerMap(availableFormatsLabel,"link"));
		return columnLabels;

	}
	
	public Map<String, String> getInnerMap(String label, String type){
		Map<String, String> iMap = new HashMap<>();
		iMap.put("label", label);
		iMap.put("type", type);
		return iMap;
	}

}
