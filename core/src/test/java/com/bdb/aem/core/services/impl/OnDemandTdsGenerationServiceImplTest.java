package com.bdb.aem.core.services.impl;

import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xml.sax.SAXException;

import javax.jcr.RepositoryException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.lenient;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class OnDemandTdsGenerationServiceImplTest {

    String TDS_TEMPLATE_BASE_PATH = "/content/dam/bdb/tds-templates";

    String TDS_XML_ABS_PATH = "TDS_Template_XML.xml";

    String ERROR = "Error";

    String FOP_XCONF = "/fop.xconf";

    String TDS_XSLT_TEMPLATE_BASE_NAME = "TDS_Template_";

    String XSLT_EXTENSION = ".xslt";

    @InjectMocks
    OnDemandTdsGenerationServiceImpl onDemandTdsGenerationServiceImpl;

    @Mock
    ResourceResolverFactory resolverFactory;

    @Mock
    ResourceResolver resourceResolver;

    @Mock
    Asset asset;

    @Mock
    Resource resource, childResource, resourceTds, res;

    @Mock
    InputStream stream;

    @Mock
    Rendition rendition;

    @Mock
    OnDemandTdsGenerationServiceImpl.Configuration config;
    Resource resourceTag;

    @Mock
    private transient CatalogStructureUpdateService catalogStructureUpdateService;

    private AemContext aemContext;

    @BeforeEach
    void setup() {
        Map<String, String> signUpProperties = new HashMap<>();
        signUpProperties.put("dam:assetState", "dam:assetState");
        signUpProperties.put("jcr:lastModifiedBy", "Administrator");
        signUpProperties.put("jcr:primaryType", "dam:AssetContent");
        resourceTag = aemContext.create().resource("/root/aof/accountmanagement", signUpProperties);
    }

    @Test
    void testGetPdfStream()
            throws IOException, TransformerException, SAXException, RepositoryException, ParserConfigurationException {
        lenient().when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver, "skuName", CommonConstants.MATERIAL_NUMBER)).thenReturn(res);
        lenient().when(resourceResolver.getResource(TDS_TEMPLATE_BASE_PATH)).thenReturn(resourceTds);
        lenient().when(resourceTds.getChild(TDS_XML_ABS_PATH)).thenReturn(resource);
        lenient().when(resource.adaptTo(Asset.class)).thenReturn(asset);
        lenient().when(resourceTds.getChild(TDS_XSLT_TEMPLATE_BASE_NAME + ERROR + XSLT_EXTENSION)).thenReturn(childResource);
        lenient().when(childResource.adaptTo(Asset.class)).thenReturn(asset);
        lenient().when(asset.getOriginal()).thenReturn(rendition);
        lenient().when(rendition.getStream()).thenReturn(stream);
        lenient().when(resourceTds.getChild(FOP_XCONF)).thenReturn(childResource);
        lenient().when(childResource.adaptTo(Asset.class)).thenReturn(asset);
        lenient().when(asset.getOriginal()).thenReturn(rendition);
        lenient().when(rendition.getStream()).thenReturn(stream);
        onDemandTdsGenerationServiceImpl.getPdfStream(res, resourceResolver, "skuName","us");
        lenient().when(config.authenticationEnabled()).thenReturn("true");
        onDemandTdsGenerationServiceImpl.getAuthenticationEnabled();
        onDemandTdsGenerationServiceImpl.activate(config);
    }
}