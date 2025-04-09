package com.bdb.aem.core.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.bdb.aem.core.services.tools.SlingModelService;
import com.bdb.aem.core.services.tools.impl.FACReportsServiceImpl;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.PageManager;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The FAC Reports Model.
 * 
 * @author ronbanerjee
 *
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@JsonPropertyOrder({ "specificity", "species", "displaySource", "sourceAltText", "protocol", "protocolAltText",
		"fluorochromes" })
public class FACReportsModel extends BaseSlingModel {

	/** The constant serial version UUID */
	private static final long serialVersionUID = -5674652143015296683L;

	private Map<String, Serializable> specificity = new HashMap<>();

	private List<Map<String, Serializable>> species = new ArrayList<>();
	
	@ValueMapValue
	private String displaySource;

	@ValueMapValue
	private String protocol;

	private String protocolAltText;

	private List<Map<String, Serializable>> fluorochromes = new ArrayList<>();

	@ValueMapValue
	private String target;

	@ValueMapValue
	private String clone;

	@ChildResource
	private List<SpeciesModel> selectedSpecies;

	@ValueMapValue
	private String source;

	@ChildResource
	private List<FluorochromesModel> selectedFluorochromes;

	@OSGiService
	private transient FACReportsServiceImpl facSelectService;

	@ChildResource
	private List<FACListNavigation> navigation;

	private List<LinkedHashMap<String, String>> conjugates;

	@ChildResource
	private List<FACGatingImages> entrezLinks;

	/**
	 * Gets the specificity.
	 * 
	 * @return the specificity
	 */
	public Map<String, Serializable> getSpecificity() {
		return specificity;
	}

	/**
	 * Sets the specificity.
	 * 
	 * @param specificity
	 */
	public void setSpecificity(Map<String, Serializable> specificity) {
		this.specificity = specificity;
	}

	/**
	 * Gets all species.
	 * 
	 * @return all species
	 */
	public List<Map<String, Serializable>> getSpecies() {
		return Optional.ofNullable(species).orElseGet(ArrayList::new);
	}

	/**
	 * Sets all species.
	 * 
	 * @param species
	 */
	public void setSpecies(List<Map<String, Serializable>> species) {
		this.species = Collections.unmodifiableList(species);
	}

	/**
	 * Gets the display source.
	 * 
	 * @return the display source
	 */
	@JsonProperty("source")
	public String getDisplaySource() {
		return displaySource;
	}

	/**
	 * Gets the source alt text.
	 * 
	 * @return the source alt text
	 */
	public String getSourceAltText() {
		return source;
	}

	/**
	 * Gets the protocol.
	 * 
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * Gets the protocol alternative text.
	 * 
	 * @return the protocol alt text
	 */
	public String getProtocolAltText() {
		return protocolAltText;
	}

	/**
	 * Sets the protocol alternative text
	 * 
	 * @param protocolAltText
	 */
	public void setProtocolAltText(String protocolAltText) {
		this.protocolAltText = protocolAltText;
	}

	/**
	 * Gets all fluorochromes.
	 * 
	 * @return all fluorochromes
	 */
	public List<Map<String, Serializable>> getFluorochromes() {
		return Optional.ofNullable(fluorochromes).filter(f -> !f.isEmpty()).orElseGet(ArrayList::new);
	}

	/**
	 * Sets all fluorochromes.
	 * 
	 * @param fluorochromes
	 */
	public void setFluorochromes(List<Map<String, Serializable>> fluorochromes) {
		this.fluorochromes = Collections.unmodifiableList(fluorochromes);
	}

	/**
	 * Gets the page path.
	 * 
	 * @return the page path
	 */
	public String getPath() {
		return Optional.ofNullable(getResolver()).map(r -> r.adaptTo(PageManager.class))
				.map(p -> p.getContainingPage(resource)).map(p -> p.getPath())
				.map(u -> externalizer.getFormattedUrl(u, getResolver())).orElse(StringUtils.EMPTY);
	}

	/**
	 * Gets the target.
	 * 
	 * @return the target
	 */
	@JsonIgnore
	public String getTarget() {
		return target;
	}

	/**
	 * Gets the clone.
	 * 
	 * @return the clone
	 */
	@JsonIgnore
	public String getClone() {
		return clone;
	}

	/**
	 * Gets the species.
	 * 
	 * @return the species
	 */
	@JsonIgnore
	public List<SpeciesModel> getSelectedSpecies() {
		return Optional.ofNullable(selectedSpecies).filter(s -> !s.isEmpty()).orElseGet(ArrayList::new);
	}

	/**
	 * Gets the source.
	 * 
	 * @return the source
	 */
	@JsonIgnore
	public String getSource() {
		return source;
	}

	/**
	 * Gets the fluorochromes.
	 * 
	 * @return the fluorochromes
	 */
	@JsonIgnore
	public List<FluorochromesModel> getSelectedFluorochromes() {
		return Optional.ofNullable(selectedFluorochromes).filter(s -> !s.isEmpty()).orElseGet(ArrayList::new);
	}

	/**
	 * Gets the navigation items.
	 * 
	 * @return the navigation items
	 */
	@JsonIgnore
	public List<FACListNavigation> getNavigation() {
		return Optional.ofNullable(navigation).orElseGet(ArrayList::new);
	}

	/**
	 * Gets the reactive species.
	 * 
	 * @return the reactive species
	 */
	@JsonIgnore
	public String getReactiveSpecies() {

		return Optional.ofNullable(selectedSpecies).filter(sp -> !sp.isEmpty()).map(s -> s.get(0).getSpeciesLabel())
				.orElse(StringUtils.EMPTY);
	}

	/**
	 * @see BaseSlingModel#getDataService()
	 */
	@Override
	public SlingModelService getDataService() {
		return facSelectService;
	}

	/**
	 * Gets the conjugates.
	 * 
	 * @return the conjugates
	 */
	@JsonIgnore
	public List<LinkedHashMap<String, String>> getConjugates() {
		LinkedHashMap<Integer, String> fluorochome = getMappingList();
		if (!fluorochome.isEmpty()) {
			AtomicInteger ordinal = new AtomicInteger(0);
			for (Map<String, String> mp : conjugates) {
				mp.put("conjugateName", fluorochome.get(ordinal.getAndIncrement()));
			}
		}

		return Optional.ofNullable(conjugates).orElseGet(ArrayList::new);

	}

	/**
	 * Sets the conjugates.
	 * 
	 * @param conjugates
	 */
	public void setConjugates(List<LinkedHashMap<String, String>> conjugates) {
		this.conjugates = Collections.unmodifiableList(conjugates);
	}

	@JsonIgnore
	public LinkedHashMap<Integer, String> getMappingList() {
		LinkedHashMap<Integer, String> lhmap = new LinkedHashMap<Integer, String>();
		AtomicInteger ordinal = new AtomicInteger(0);
		if (null != selectedFluorochromes) {
			for (FluorochromesModel fc : selectedFluorochromes) {
				if (fc.getData() && fc.getReagent() && fc.getNotSuggested()) {
					String tagValue = fc.getFname();
					TagManager tagManager = getResolver().adaptTo(TagManager.class);
					Tag tag = tagManager.resolve(tagValue);
					if (tag != null) {
						String tagTitle = tag.getTitle();
						lhmap.put(ordinal.getAndIncrement(), tagTitle);
					}
				}

			}
		}
		return lhmap;
	}

	@JsonIgnore
	public List<FACGatingImages> getEntrezLinks() {
		return Optional.ofNullable(entrezLinks).orElseGet(ArrayList::new);
	}
}
