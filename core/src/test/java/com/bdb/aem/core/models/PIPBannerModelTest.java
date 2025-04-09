package com.bdb.aem.core.models;

import com.bdb.aem.core.services.ExternalizerService;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.mockito.Mockito.lenient;


@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class PIPBannerModelTest {
    @InjectMocks
    PIPBannerModel bannerModel;
    @Mock
    ResourceResolver resourceResolver;
    @Mock
    ExternalizerService externalizerService;
    @Mock
    Resource hotspotsList;
    @Mock
    Iterator<Resource> resItr;
    @Mock
    Resource childRes;
    @Mock
    ValueMap valueMap;
    @BeforeEach
    void setUp() throws NoSuchFieldException {
        PrivateAccessor.setField(bannerModel, "resourceResolver", resourceResolver);
        PrivateAccessor.setField(bannerModel,"externalizerService",externalizerService);
        PrivateAccessor.setField(bannerModel,"hotspotsList",hotspotsList);
    }
    @Test
    void testGetters()
    {
        lenient().when(hotspotsList.hasChildren()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        lenient().when(hotspotsList.listChildren()).thenReturn(resItr);
        lenient().when(resItr.hasNext()).thenReturn(Boolean.TRUE).thenReturn(Boolean.FALSE);
        lenient().when(resItr.next()).thenReturn(childRes);
        lenient().when(childRes.getValueMap()).thenReturn(valueMap);
        lenient().when(valueMap.get("xCoordinate", StringUtils.EMPTY)).thenReturn("xaxis");
        lenient().when(valueMap.get("yCoordinate",StringUtils.EMPTY)).thenReturn("yAxis");
        bannerModel.init();
        bannerModel.getBgColor();
        bannerModel.getBgImage();
        bannerModel.getCtaText();
        bannerModel.getCtaUrl();
        bannerModel.getSecondaryCTA();
        bannerModel.getSecondaryCTAUrl();
        bannerModel.getHotspotsList();
        bannerModel.getImageAlt();
        bannerModel.getImagePath();
        bannerModel.getImageLinkUrl();
        bannerModel.getOpenNewImageLinkTab();
        bannerModel.getSubTitle();
        bannerModel.getTitle();
        bannerModel.getList();
        bannerModel.getBgImageMobile();
        bannerModel.getOffsetPosition();
        bannerModel.getBgImageAlt();
        Assertions.assertNotNull(bannerModel.getHotspotObject());
    }
}
