package com.bdb.aem.core.models.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.CtaModel;
import com.bdb.aem.core.models.NavigationLinkModel;

/**
 * The Class NavigationLinkModelImpl.
 */
@Model( adaptables = { Resource.class }, 
		adapters = { NavigationLinkModel.class }, 
		resourceType = { NavigationLinkModelImpl.RESOURCE_TYPE },
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NavigationLinkModelImpl implements NavigationLinkModel {
	
	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/navigationlink/v1/navigationlink";

	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(NavigationLinkModelImpl.class);

	/** The navigation links multifield. */
	@Inject
	private List<Resource> navigationLinksMultifield;
	
	/** The navigation link list. */
	private List<CtaModel> navigationLinkList = new ArrayList<>();
	
    /**
     * Inits the Navigation Link Model and populates the Titles and Links List.
     */
	@PostConstruct
	protected void init() {
		log.debug("Inside Navigation Error Model Init");
		populateNavigationLinkList();
	}
	
	/**
	 * Populate navigation link list.
	 */
	private void populateNavigationLinkList() {
		if (navigationLinksMultifield != null) {
			for (Resource resource : navigationLinksMultifield) {
				CtaModel navigationModel = resource.adaptTo(CtaModel.class);
				if (navigationModel.getLabel() != null && navigationModel.getPath() != null) {
					navigationLinkList.add(navigationModel);
				}
			}
		}
	}
	
	/**
	 * Gets the navigation link list.
	 *
	 * @return the navigation link list
	 */
	@Override
	public List<CtaModel> getNavigationLinkList() {
		return new ArrayList<>(navigationLinkList);
	}
}
