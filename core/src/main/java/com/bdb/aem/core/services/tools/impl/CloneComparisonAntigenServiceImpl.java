package com.bdb.aem.core.services.tools.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.bdb.aem.core.models.CloneComparisonAntigenModel;

/**
 * The Clone Comparison Antigen Service Impl.
 * 
 * @see CloneComparpisonAntigenModel
 * 
 * @author ronbanerjee
 *
 */
@Component(service = CloneComparisonAntigenServiceImpl.class)
public class CloneComparisonAntigenServiceImpl extends AbstractSlingModelService<CloneComparisonAntigenModel> {

	
	/** The Clone comparison Service Impl */
	@Reference
	private CloneComparisonServiceImpl cloneService;

	/**
	 * @see AbstractSlingModelService#updateSlingModel(com.bdb.aem.core.models.BaseSlingModel)
	 */
	@Override
	protected void updateSlingModel(CloneComparisonAntigenModel slingModel) {
		Optional.ofNullable(slingModel).ifPresent(s -> {
			s.setTargetMoleculeDropdown(getTargetMolecules(s.getResource()));
			s.setSortItems(getSortItems(s.getResource()));
			s.setDyeNamePlaceholder(cloneService.getDyeNamePlaceholder());
			s.setLinkToCatalogPlaceholder(cloneService.getLinkToCatalogPlaceholder());			
		});

	}

	/**
	 * Gets the Target Molecules.
	 * 
	 * @param cloneComAgenRes, the clone Compare Antigen Resource
	 * @return the {@link List}
	 */
	private List<String> getTargetMolecules(Resource cloneComAgenRes) {
		List<String> targetMolecules = new ArrayList<>();
		Iterator<Resource> itr = Optional.ofNullable(cloneComAgenRes).map(c -> c.getChild("targetMolecules")).map(r -> r.listChildren()).orElse(null);
		Optional.ofNullable(itr).ifPresent(i -> {
					i.forEachRemaining(
							v -> targetMolecules.add(getValueFromValueMap(v.getValueMap(), "targetMoleculeOption")));
				});
		return targetMolecules;

	}

	/**
	 * Gets the sort items
	 * 
	 * @param cloneComAgenRes
	 * @return {@link List}
	 */
	private List<Map<String, String>> getSortItems(Resource cloneComAgenRes) {
		List<Map<String, String>> sortItems = new ArrayList<>();
		Optional.ofNullable(cloneComAgenRes).map(c -> c.getChild("sortItems")).map(r -> r.listChildren())
				.ifPresent(i -> {
					i.forEachRemaining(v -> {
						Map<String, String> sortItem = new HashMap<>();
						sortItem.put("text", getValueFromValueMap(v.getValueMap(), "text"));
						sortItem.put("value", getValueFromValueMap(v.getValueMap(), "value"));
						sortItems.add(sortItem);
					});
				});
		return sortItems;
	}

	/**
	 * Gets the value from value map.
	 * 
	 * @param vM,  the {@link ValueMap}
	 * @param key, the key
	 * @return {@link String}
	 */
	private String getValueFromValueMap(ValueMap vM, String key) {
		return Optional.ofNullable(vM).map(v -> v.get(key, String.class)).orElse(StringUtils.EMPTY);

	}

}
