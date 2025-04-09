package com.bdb.aem.core.servlets.solr;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.solr.FetchingCloneService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;

import static org.mockito.Mockito.when;

public class FetchFormatsDataTest {
    @InjectMocks
    FetchingFormatsData fetchFormatsData;
    /** Mock ResourceResolverFactory. */
    @Mock
    ResourceResolverFactory resolverFactory;
    /** Mock ResourceResolver. */
    @Mock
    ResourceResolver resourceResolver;
    @Mock
    BDBSearchEndpointService searchService;
    @Mock
    FetchingCloneService cloneService;
    @Mock
    SlingHttpServletRequest request;
    @Mock
    SlingHttpServletResponse response;
    @Mock
    SolrSearchService solrSearchService;
    @Mock
    PrintWriter writer;
    @Mock
    SolrServerException exception;
    @Mock
	CatalogStructureUpdateService catalogStructureUpdateService;
    @Mock
    Resource resource;
    @Mock
    ValueMap baseHpMap;
    @Mock
    Object object;
    @BeforeEach
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testDoGet() throws Exception {
    	when(CommonHelper.getServiceResolver(resolverFactory)).thenReturn(resourceResolver);
        when(request.getParameter("baseId")).thenReturn("12334_base");
        when(request.getParameter("country")).thenReturn("us");
        when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver, "12334_base", CommonConstants.BASE_MATERIAL_NUMBER)).thenReturn(resource);
        when(resource.getChild(CommonConstants.HP)).thenReturn(resource);
        when(resource.adaptTo(ValueMap.class)).thenReturn(baseHpMap);
        when(baseHpMap.get(CommonConstants.BRAND)).thenReturn(object);
        when(baseHpMap.get(CommonConstants.BRAND,String.class)).thenReturn("brand");
        when(cloneService.getFormatsData("us",searchService,solrSearchService,"dyeName","cloneId")).thenReturn("response");
        when(cloneService.getSpecificityData("us",searchService,solrSearchService,"dyeName","specificity")).thenReturn("response");
        when(request.getResourceResolver()).thenReturn(resourceResolver);
        when(catalogStructureUpdateService.getProductFromLookUp(resourceResolver, "12334_base", CommonConstants.BASE_MATERIAL_NUMBER)).thenReturn(resource);
        when(response.getWriter()).thenReturn(writer);
        fetchFormatsData.doGet(request,response);
    }
    @Test
    public void testDoGet1() throws Exception {
        when(request.getParameter("baseId")).thenReturn("12334_base");
        when(request.getParameter("country")).thenReturn("us");
        when(cloneService.getFormatsData("us",searchService,solrSearchService,"dyeName","cloneId")).thenThrow(exception);
        when(cloneService.getSpecificityData("us",searchService,solrSearchService,"dyeName","specificity")).thenThrow(exception);
        when(response.getWriter()).thenReturn(writer);
        fetchFormatsData.doGet(request,response);
    }


}
