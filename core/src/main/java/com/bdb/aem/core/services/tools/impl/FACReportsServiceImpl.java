package com.bdb.aem.core.services.tools.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

import com.bdb.aem.core.models.FACReportsModel;
import com.bdb.aem.core.models.FluorochromesModel;
import com.bdb.aem.core.models.SpeciesModel;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

/**
 * The FAC Reports Service Impl.
 * 
 * @author ronbanerjee
 *
 */
@Component(service = FACReportsServiceImpl.class)
public class FACReportsServiceImpl extends AbstractSlingModelService<FACReportsModel> {

	/**
	 * @see AbstractSlingModelService#updateSlingModel(com.bdb.aem.core.models.BaseSlingModel)
	 */
	@Override
	protected void updateSlingModel(FACReportsModel s) {

		Optional.ofNullable(s).ifPresent(sm -> {
			setSpecificity(sm);
			setFluorochromes(sm);
			setSpecies(sm);
			setProtocolAltText(sm);
			setConjugates(sm);
		});
	}

	/**
	 * Sets the protocol text.
	 * 
	 * @param sm the sling model
	 */
	private void setProtocolAltText(FACReportsModel sm) {

		Optional.ofNullable(sm).map(s -> s.getProtocol()).ifPresent(p -> {
			if (p.equals("Std")) {
				sm.setProtocolAltText("Standard protocol");
			}

			if (p.equals("Alt 1")) {
				sm.setProtocolAltText("Alternative protocol 1");
			}

			if (p.equals("Alt 2")) {
				sm.setProtocolAltText("Alternative protocol 2");
			}
		});

	}

	/**
	 * Sets the list of conjugates based on conjugate resources
	 * 
	 * @param sm the {@link FACReportsModel}
	 */
	private void setConjugates(FACReportsModel sm) {
		List<LinkedHashMap<String, String>> conjugates = new ArrayList<>();
		Optional.ofNullable(sm).map(s -> s.getResource().getParent()).map(r -> r.listChildren())
				.ifPresent(r -> r.forEachRemaining(c -> {

					if (c.getResourceType().equals("bdb-aem/components/content/tools/facselect/conjugates")) {
						LinkedHashMap<String, String> item = new LinkedHashMap<>();
						item.put("conjugateName", c.getValueMap().get("conjugateName", StringUtils.EMPTY));
						item.put("conjugateId", c.getValueMap().get("conjugateName", StringUtils.EMPTY).toLowerCase()
								.replaceAll(" ", "_"));
						conjugates.add(item);
					}
				}));
		if(null != sm)
		sm.setConjugates(conjugates);
	}

	/**
	 * Sets all species
	 * 
	 * @param sm the {@link FACReportsModel}
	 */
	private void setSpecies(FACReportsModel sm) {
		// TBD: Replace ds by ACS Commons Generic List
		String[] ds = new String[] { "Human", "Mouse", "Surface", "Intracellular" };

		List<Map<String, Serializable>> speciesList = new ArrayList<>();
		
		Arrays.asList(ds).forEach(i -> {
			speciesList.add(
					handleSpecies(sm.getSelectedSpecies() == null ? new ArrayList<>() : sm.getSelectedSpecies(), i));
		});
		sm.setSpecies(speciesList);
	}

	/**
	 * Handles species based on conditions.
	 * 
	 * @param selectedSpecies the selected species
	 * @param currentItem     the current item in array
	 * @return the updated map
	 */
	private Map<String, Serializable> handleSpecies(List<SpeciesModel> selectedSpecies, String currentItem) {
		Map<String, Serializable> species = new HashMap<>();
		List<String> exists = new ArrayList<>();
		if (selectedSpecies != null && !selectedSpecies.isEmpty()) {
			selectedSpecies.forEach(sp -> {
				if (sp.getSpeciesLabel().equals(currentItem)) {
					species.put("label", currentItem);
					species.put("reagentsDataAvailable", sp.getData());
					species.put("onlyReagentsAvailable", sp.getReagent());
					species.put("notSuggestedAvailable", sp.getNotSuggested());
					exists.add(currentItem);
				} else if (!exists.contains(currentItem)) {
					species.put("label", currentItem);
					species.put("reagentsDataAvailable", false);
					species.put("onlyReagentsAvailable", false);
					species.put("notSuggestedAvailable", false);
				}

			});
		} else
			handleEmptySpecies(currentItem, species);

		return species;

	}

	/**
	 * Handles scenario for resource 'selectedSpecies' is empty or null.
	 * 
	 * @see FACReportsModel#getSelectedSpecies()
	 * 
	 * @param currentItem the current item in array
	 * @param species     the species
	 */
	private void handleEmptySpecies(String currentItem, Map<String, Serializable> species) {
		species.put("label", currentItem);
		species.put("reagentsDataAvailable", false);
		species.put("onlyReagentsAvailable", false);
		species.put("notSuggestedAvailable", false);
	}

	/**
	 * Sets the fluorochromes.
	 * 
	 * @param sm the FAC Reports Model
	 */
	private void setFluorochromes(FACReportsModel sm) {
		List<Map<String, Serializable>> fList = new ArrayList<>();
		// TBD: The tag path '/content/cq:tags/facselect' has to be read from OSGi
		// config.
		Iterator<Tag> ts = Optional.ofNullable(sm).map(s -> s.getResolver())
				.map(r -> this.getChildTags(r, "/content/cq:tags/facselect")).orElse(null);
		if (ts != null) {
			while (ts.hasNext()) {
				Tag currentTag = ts.next();
				Map<String, Serializable> fluorochromes = new HashMap<>();
				fluorochromes.put("band", checkNull(currentTag.getName()));
				fluorochromes.put("bandName", checkNull(currentTag.getTitle()));
				fluorochromes.put("items", setItemsList(currentTag, sm));
				fList.add(fluorochromes);
			}
		}
		Optional.ofNullable(sm).ifPresent(s -> s.setFluorochromes(fList));
	}

	/**
	 * Sets the items list
	 * 
	 * @param currentTag the current tag
	 * @param sm         the FAC Reports Model
	 * @return the items list
	 */
	private ArrayList<Map<String, Serializable>> setItemsList(Tag currentTag, FACReportsModel sm) {
		ArrayList<Map<String, Serializable>> itemsList = new ArrayList<>();

		final TagManager tm = Optional.ofNullable(sm).map(s -> s.getResolver()).map(r -> r.adaptTo(TagManager.class))
				.orElse(null);
		Iterator<Tag> ts = Optional.ofNullable(sm).map(s -> s.getResolver())
				.map(r -> this.getChildTags(r, currentTag.getPath())).orElse(null);
		if (ts != null) {
			while (ts.hasNext()) {
				if(null != sm) {
					Tag currentChildTag = ts.next();
					itemsList.add(handleItems(sm.getSelectedFluorochromes(), currentChildTag, tm));
				}
			}
		}
		return itemsList;

	}

	/**
	 * Handles items based on data in {@code selectedFluorochromes}
	 * 
	 * @param selectedFluorochromes the list of selected fluorochromes
	 * @param currentChildTag       the current child tag
	 * @param tm                    the tag Manager
	 * @return the updated list of items
	 */
	private Map<String, Serializable> handleItems(List<FluorochromesModel> selectedFluorochromes, Tag currentChildTag,
			TagManager tm) {
		Map<String, Serializable> items = new HashMap<>();
		List<String> exists = new ArrayList<>();
		if (!selectedFluorochromes.isEmpty() && tm != null) {
			selectedFluorochromes.forEach(i -> {
				Tag t = tm.resolve(i.getFname());
				if (t.getPath().equals(currentChildTag.getPath())) {
					items.put("label", checkNull(t.getTitle()));
					items.put("reagentsDataAvailable", i.getData());
					items.put("onlyReagentsAvailable", i.getReagent());
					items.put("notSuggestedAvailable", i.getNotSuggested());
					exists.add(currentChildTag.getPath());
				} else if (!exists.contains(currentChildTag.getPath())) {
					items.put("label", checkNull(currentChildTag.getTitle()));
					items.put("reagentsDataAvailable", false);
					items.put("onlyReagentsAvailable", false);
					items.put("notSuggestedAvailable", false);
				}
			});
		} else if (selectedFluorochromes.isEmpty()) {
			items.put("label", checkNull(currentChildTag.getTitle()));
			items.put("reagentsDataAvailable", false);
			items.put("onlyReagentsAvailable", false);
			items.put("notSuggestedAvailable", false);
		}
		return items;
	}

	/**
	 * Sets the specificity Map.
	 * 
	 * @param s the FAC Report Model
	 */
	private void setSpecificity(FACReportsModel s) {
		Map<String, Serializable> specificity = new HashMap<>();
		HashMap<String, String> targetMap = new HashMap<>();

		targetMap.put("label", checkNull(s.getTarget()));
		targetMap.put("linkHref", checkNull(s.getPath()));

		specificity.put("target", targetMap);
		specificity.put("clone", checkNull(s.getClone()));
		s.setSpecificity(specificity);
	}

	/**
	 * Gets the child tags from the parent tag {@code tag}
	 * 
	 * @param resolver the resource resolver
	 * @param tag      the parent tag
	 * @return the tag iterator
	 */
	private Iterator<Tag> getChildTags(ResourceResolver resolver, String tag) {
		return Optional.ofNullable(resolver).map(r -> r.adaptTo(TagManager.class)).map(tm -> tm.resolve(tag))
				.map(t -> t.listChildren()).orElse(null);
	}

	/**
	 * Utility to check if {@code val} null.
	 * 
	 * @param val the string to validate
	 * @return the {@code val}, if not null, else empty string
	 */
	private String checkNull(String val) {
		return Optional.ofNullable(val).orElse(StringUtils.EMPTY);
	}
}
