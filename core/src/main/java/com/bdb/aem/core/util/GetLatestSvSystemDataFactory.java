package com.bdb.aem.core.util;

import com.adobe.acs.commons.mcp.ProcessDefinitionFactory;
import com.bdb.aem.core.api.client.RestClient;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.SpectrumViewerConfigService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = ProcessDefinitionFactory.class, immediate = true)
public class GetLatestSvSystemDataFactory extends ProcessDefinitionFactory<GetLatestSvSystemData> {

    /**
     * The bdb api endpoint service.
     */
    @Reference
    private BDBApiEndpointService bdbApiEndpointService;

    /**
     * The SpectrumViewerConfig service.
     */
    @Reference
    private SpectrumViewerConfigService spectrumViewerConfigService;

    /** The rest client. */
    @Reference
    private RestClient restClient;

    @Override
    public String getName() {
        return "Fetch Latest System Data from CCV2";
    }
    @Override
    protected GetLatestSvSystemData createProcessDefinitionInstance() {
        return new GetLatestSvSystemData(bdbApiEndpointService, restClient, spectrumViewerConfigService);
    }

    public BDBApiEndpointService getBdbApiEndpointService() {
        return bdbApiEndpointService;
    }

    public RestClient getRestClient() {
        return restClient;
    }

}
