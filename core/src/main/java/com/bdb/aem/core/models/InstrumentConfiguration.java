package com.bdb.aem.core.models;

import org.osgi.annotation.versioning.ConsumerType;


/**
 * Defines the {@code Instrumentconfiguration} Sling Model used for the {@code bdb-aem/components/content/spectrumViewer/instrument-configuration} component.
 * 
 */
@ConsumerType
public interface InstrumentConfiguration {


    String getTitle();

    String getSearchCytometerPlaceholder();

    String getSearchConfigurationPlaceholder();

    String getDefaultInstrument();

    String getDefaultInstrumentConfiguration();
    
    String getDeleteLaserIcon();

    String getDeleteLaserIconAlt();

}
