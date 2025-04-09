package com.bdb.aem.core.models;

import java.util.Map;

import org.osgi.annotation.versioning.ConsumerType;


/**
 * Defines the {@code Graph} Sling Model used for the {@code bdb-aem/components/content/spectrumviewer/instruments} component.
 * 
 */
@ConsumerType
public interface Fluorochromes {

    String getSearchTypePlaceholder();

    String getSearchInputPlaceholder();

    String[] getDefaultFluorochromes();
    
    String getDeleteFluorIcon();

    String getDeleteFluorIconAlt();

    String getInfoFluorIcon();
    
    String getInfoFluorIconAlt();

    String getSearchFluorIcon();

    String getSearchFluorIconAlt();


}
