package com.bdb.aem.core.servlets.solr;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.solr.FetchingCloneService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.servlets.solr.FetchingCloneData;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FetchClonesDataTest {
    @InjectMocks
    FetchingCloneData cloneData;
    /** Mock ResourceResolverFactory. */
    @Mock
    ResourceResolverFactory resolverFactory;
    /** Mock ResourceResolver. */
    @Mock
    ResourceResolver resourceResolver;
    @Mock
    BDBSearchEndpointService searchService;
    @Mock
    FetchingCloneService fetchingCloneService;
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
    @BeforeEach
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testDoGet1() throws IOException, SolrServerException {
        when(request.getParameter("baseId")).thenReturn("12334_base");
        when(request.getParameter("country")).thenReturn("us");
       // when(fetchingCloneService.getClonesData("us",searchService,solrSearchService,"TCR VÎ±7.2","BV421","cloneName")).thenReturn("response");
        when(response.getWriter()).thenReturn(writer);
        cloneData.doGet(request,response);
    }
    @Test
    public void testDoGet() throws IOException, SolrServerException {
        when(request.getParameter("baseId")).thenReturn("12334_base");
        when(request.getParameter("country")).thenReturn("us");
       // when(fetchingCloneService.getClonesData("us",searchService,solrSearchService,"","","")).thenThrow(exception);
        when(response.getWriter()).thenReturn(writer);
       cloneData.doGet(request,response);
    }
}
