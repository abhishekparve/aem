package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.models.Graph;
import com.bdb.aem.core.util.SVUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;


@Model(adaptables = {
    SlingHttpServletRequest.class
}, adapters = Graph.class, resourceType = "bdb-aem/components/content/spectrumViewer/v1/spectrumViewer/graph")
public class GraphImpl
    implements Graph
{

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String resetIcon;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String resetIconAlt;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String resetIconGray;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String resetIconGrayAlt;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String expandGraphLabel;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String expandGraphIcon;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String expandGraphIconAlt;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String zoomLabel;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String zoomPlusIcon;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String zoomPlusIconAlt;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String zoomPlusIconGray;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String zoomPlusIconGrayAlt;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String zoomMinusIcon;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String zoomMinusIconAlt;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String zoomMinusIconGray;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String zoomMinusIconGrayAlt;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String resetZoom;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String graphDisplayOptions;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String fluorochromeSets;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String userGuide;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String menuIcon;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String menuIconAlt;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String xAxis;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String yAxis;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String xaxisSignatureGraph;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String yaxisSignatureGraph;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String xaxisHeatMapGraph;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String yaxisHeatMapGraph;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String savedList;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String savedListIcon;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String savedListIconAlt;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String expandWorkspace;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String graphExportLabel;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String expandLabel;

    @Override
    public String getExpandGraphLabel() {
        return expandGraphLabel;
    }

    @Override
    public String getExpandGraphIcon() {
        return expandGraphIcon;
    }

    @Override
    public String getExpandGraphIconAlt() {
        return expandGraphIconAlt;
    }

    @Override
    public String getZoomLabel() {
        return zoomLabel;
    }

    @Override
    public String getZoomPlusIcon() {
        return zoomPlusIcon;
    }

    @Override
    public String getZoomPlusIconAlt() {
        return zoomPlusIconAlt;
    }

    @Override
    public String getZoomPlusIconGray() {
        return zoomPlusIconGray;
    }

    @Override
    public String getZoomPlusIconGrayAlt() {
        return zoomPlusIconGrayAlt;
    }

    @Override
    public String getZoomMinusIcon() {
        return zoomMinusIcon;
    }

    @Override
    public String getZoomMinusIconAlt() {
        return zoomMinusIconAlt;
    }

    @Override
    public String getZoomMinusIconGray() {
        return zoomMinusIconGray;
    }

    @Override
    public String getZoomMinusIconGrayAlt() {
        return zoomMinusIconGrayAlt;
    }

    @Override
    public String getXAxis() {
        return xAxis;
    }

    @Override
    public String getYAxis() {
        return yAxis;
    }

    @Override
    public String getXaxisSignatureGraph() {
        return xaxisSignatureGraph;
    }

    @Override
    public String getYaxisSignatureGraph() {
        return yaxisSignatureGraph;
    }

    @Override
    public String getXaxisHeatMapGraph() {
        return xaxisHeatMapGraph;
    }

    @Override
    public String getYaxisHeatMapGraph() {
        return yaxisHeatMapGraph;
    }

    @Override
    public String getResetIcon() {
        return resetIcon;
    }

    @Override
    public String getResetIconAlt() {
        return resetIconAlt;
    }

    @Override
    public String getResetIconGray() {
        return resetIconGray;
    }

    @Override
    public String getResetIconGrayAlt() {
        return resetIconGrayAlt;
    }

    @Override
    public String getMenuIcon() {
        return menuIcon;
    }

    @Override
    public String getMenuIconAlt() {
        return menuIconAlt;
    }

    @Override
    public String getResetZoom() {
        return resetZoom;
    }

    @Override
    public String getGraphDisplayOptions() {
        return graphDisplayOptions;
    }

    @Override
    public String getFluorochromeSets() {
        return fluorochromeSets;
    }

    @Override
    public String getUserGuide() {
        return userGuide;
    }
    
    @Override
    public String getSavedList() {
        return savedList;
    }

    @Override
    public String getSavedListIcon() {
        return savedListIcon;
    }

    @Override
    public String getSavedListIconAlt() {
        return savedListIconAlt;
    }
    
    @Override
    public String getExpandWorkspace() {
        return expandWorkspace;
    }

    @Override
    public String getGraphExportLabel() {
        return graphExportLabel;
    }

	@Override
    public String getExpandLabel() {
        return expandLabel;
    }

    @PostConstruct
    protected void init() {
            resetIcon = SVUtils.replaceIconPath(resetIcon);
            resetIconGray = SVUtils.replaceIconPath(resetIconGray);
            expandGraphIcon = SVUtils.replaceIconPath(expandGraphIcon);
            zoomPlusIcon = SVUtils.replaceIconPath(zoomPlusIcon);
            zoomPlusIconGray = SVUtils.replaceIconPath(zoomPlusIconGray);
            zoomMinusIcon = SVUtils.replaceIconPath(zoomMinusIcon);
            zoomMinusIconGray = SVUtils.replaceIconPath(zoomMinusIconGray);
            menuIcon = SVUtils.replaceIconPath(menuIcon);
            savedListIcon = SVUtils.replaceIconPath(savedListIcon);
    }

}
