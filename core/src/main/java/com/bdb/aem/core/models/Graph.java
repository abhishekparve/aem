package com.bdb.aem.core.models;

import org.osgi.annotation.versioning.ConsumerType;


/**
 * Defines the {@code Graph} Sling Model used for the {@code bdb-aem/components/content/spectrumviewer/graph} component.
 * 
 */
@ConsumerType
public interface Graph {

    String getResetIcon();

    String getResetIconAlt();

    String getResetIconGray();

    String getResetIconGrayAlt();

    String getExpandGraphLabel();

    String getExpandGraphIcon();

    String getExpandGraphIconAlt();

    String getZoomLabel();

    String getZoomPlusIcon();

    String getZoomPlusIconAlt();

    String getZoomPlusIconGray();

    String getZoomPlusIconGrayAlt();

    String getZoomMinusIcon();

    String getZoomMinusIconAlt();

    String getZoomMinusIconGray();

    String getZoomMinusIconGrayAlt();
    
    String getResetZoom();
    
    String getGraphDisplayOptions();
    
    String getFluorochromeSets();
    
    String getUserGuide();

    String getMenuIcon();

    String getMenuIconAlt();

    String getXAxis();

    String getYAxis();

    String getXaxisSignatureGraph();

    String getYaxisSignatureGraph();

    String getXaxisHeatMapGraph();

    String getYaxisHeatMapGraph();

    String getSavedList();

    String getSavedListIcon();

    String getSavedListIconAlt();
    
    String getExpandWorkspace();
    
    String getGraphExportLabel();
    
    String getExpandLabel();

}
