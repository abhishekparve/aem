
package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.models.InstrumentConfiguration;
import com.bdb.aem.core.util.SVUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;

@Model(adaptables = {
    SlingHttpServletRequest.class
}, adapters = InstrumentConfiguration.class, resourceType = "bdb-aem/components/content/spectrumViewer/instrument-configuration")
public class InstrumentConfigurationImpl
    implements InstrumentConfiguration
{

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String title;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String searchCytometerPlaceholder;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String searchConfigurationPlaceholder;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String defaultInstrument;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String defaultInstrumentConfiguration;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String deleteLaserIcon;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String deleteLaserIconAlt;

    @PostConstruct
    protected void init() {
            deleteLaserIcon = SVUtils.replaceIconPath(deleteLaserIcon);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSearchCytometerPlaceholder() {
        return searchCytometerPlaceholder;
    }

    @Override
    public String getSearchConfigurationPlaceholder() {
        return searchConfigurationPlaceholder;
    }

    @Override
    public String getDefaultInstrument() {
        return defaultInstrument;
    }

    @Override
    public String getDefaultInstrumentConfiguration() {
        return defaultInstrumentConfiguration;
    }

	@Override
	public String getDeleteLaserIcon() {
		return deleteLaserIcon;
	}

	@Override
	public String getDeleteLaserIconAlt() {
		return deleteLaserIconAlt;
	}

}
