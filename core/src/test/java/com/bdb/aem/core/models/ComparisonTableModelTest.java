package com.bdb.aem.core.models;


import com.bdb.aem.core.services.ExternalizerService;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ComparisonTableModelTest {
    @InjectMocks
    ComparisonTableModel comparisonTableModel;
    @Mock
    ResourceResolver resourceResolver;
    @Mock
    ExternalizerService externalizerService;
    private String componentTitle="componentTitle";
    private Boolean greyBackground=true;
    private String tabTitle="tabTitle";
    @Mock
    Resource imageDetails;
    @Mock
    Iterator<Resource> resourceIterator;
    @Mock
    Iterator<Resource> rowItr,optionsItr;
    @Mock
    Resource childResource,rowResource,options;
    @Mock
    ValueMap valueMap,rowValueMap;
    @BeforeEach
    void setUp() throws NoSuchFieldException {
        PrivateAccessor.setField(comparisonTableModel, "componentTitle", componentTitle);
        PrivateAccessor.setField(comparisonTableModel, "greyBackground", greyBackground);
        PrivateAccessor.setField(comparisonTableModel, "description", "description");
        PrivateAccessor.setField(comparisonTableModel, "tabTitle", tabTitle);
        PrivateAccessor.setField(comparisonTableModel,"imageDetails",imageDetails);
        PrivateAccessor.setField(comparisonTableModel,"tabTitle2","tabTitle2");
        PrivateAccessor.setField(comparisonTableModel,"tabTitle3","tabTitle3");
       
    }
    @Test
    void initTest(){
        when(imageDetails.hasChildren()).thenReturn(Boolean.TRUE).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        when(imageDetails.listChildren()).thenReturn(resourceIterator);
        when(resourceIterator.hasNext()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        when(resourceIterator.next()).thenReturn(childResource);
        when(childResource.getValueMap()).thenReturn(valueMap);
        when(childResource.hasChildren()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        when(childResource.listChildren()).thenReturn(rowItr);
        when(rowItr.next()).thenReturn(rowResource);
        when(rowResource.listChildren()).thenReturn(optionsItr);
        when(optionsItr.hasNext()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        when(optionsItr.next()).thenReturn(options);
        when(options.getValueMap()).thenReturn(rowValueMap);
        when(valueMap.get("titleText", StringUtils.EMPTY)).thenReturn(componentTitle);
        when(valueMap.get("imageUrl", StringUtils.EMPTY)).thenReturn(componentTitle);
        when(valueMap.get("imageLinkUrl", StringUtils.EMPTY)).thenReturn(componentTitle);
        when(valueMap.get("openNewImageLinkTab", StringUtils.EMPTY)).thenReturn(componentTitle);
        when(valueMap.get("ctaText", StringUtils.EMPTY)).thenReturn(componentTitle);
        when(valueMap.get("ctaText2", StringUtils.EMPTY)).thenReturn(componentTitle);
        when(valueMap.get("redirectLink", StringUtils.EMPTY)).thenReturn(componentTitle);
        when(valueMap.get("subText", StringUtils.EMPTY)).thenReturn(componentTitle);
        when(valueMap.get("ctaLink", StringUtils.EMPTY)).thenReturn(componentTitle);
        when(valueMap.get("ctaLink2", StringUtils.EMPTY)).thenReturn(componentTitle);
        comparisonTableModel.init();
        assertNotNull(comparisonTableModel.getTabValue());
        comparisonTableModel.getCompUniqueName();
    }

}
