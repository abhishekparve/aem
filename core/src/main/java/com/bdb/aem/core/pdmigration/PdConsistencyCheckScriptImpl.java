package com.bdb.aem.core.pdmigration;

import com.adobe.acs.commons.mcp.ProcessDefinitionFactory;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = ProcessDefinitionFactory.class, immediate = true)
public class PdConsistencyCheckScriptImpl extends ProcessDefinitionFactory<PdConsistencyCheckScript> {

    @Reference
    BDBApiEndpointService bdbApiEndpointService;

    @Reference
    SolrSearchService solrSearchService;

    @Reference
    BDBSearchEndpointService solrConfig;

    @Override
    public String getName() {
        return "PD Consistency Check Process";
    }

    @Override
    protected PdConsistencyCheckScript createProcessDefinitionInstance() {
        return new PdConsistencyCheckScript(bdbApiEndpointService, solrSearchService, solrConfig);
    }
}