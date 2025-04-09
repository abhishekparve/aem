package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.models.Fluorochromes;
import com.bdb.aem.core.util.SVUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;

/**
 * Model for fluorochromes component
 */
@Model(adaptables = {
    SlingHttpServletRequest.class
}, adapters = Fluorochromes.class, resourceType = "bdb-aem/components/content/spectrumViewer/v1/spectrumViewer/fluorochromes")
public class FluorochromesImpl
    implements Fluorochromes
{

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String searchTypePlaceholder;
	@ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
	public String searchInputPlaceholder;
	@ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
	public String[] defaultFluorochromes;
	@ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String deleteFluorIcon;
	@ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String deleteFluorIconAlt;
	@ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
	private String infoFluorIcon;
	@ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
	private String infoFluorIconAlt;
	@ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
	private String searchFluorIcon;
	@ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
	private String searchFluorIconAlt;

	@PostConstruct
	protected void init() {
			deleteFluorIcon = SVUtils.replaceIconPath(deleteFluorIcon);
			infoFluorIcon = SVUtils.replaceIconPath(infoFluorIcon);
			searchFluorIcon = SVUtils.replaceIconPath(searchFluorIcon);
	}


	@Override
	public String getSearchTypePlaceholder() {
		return searchTypePlaceholder;
	}

	@Override
	public String getSearchInputPlaceholder() {
		return searchInputPlaceholder;
	}

	@Override
	public String[] getDefaultFluorochromes() {
		return defaultFluorochromes;
	}

	@Override
	public String getDeleteFluorIcon() {
		return deleteFluorIcon;
	}

	@Override
	public String getDeleteFluorIconAlt() {
		return deleteFluorIconAlt;
	}

	@Override
	public String getInfoFluorIconAlt() {
		return infoFluorIconAlt;
	}

	@Override
	public String getSearchFluorIcon() {
		return searchFluorIcon;
	}

	@Override
	public String getSearchFluorIconAlt() {
		return searchFluorIconAlt;
	}

	@Override
	public String getInfoFluorIcon() {
		return infoFluorIcon;
	}

}
