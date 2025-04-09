package com.bdb.aem.core.pdmigration;


import com.adobe.acs.commons.mcp.ProcessDefinitionFactory;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.day.cq.replication.Replicator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service= ProcessDefinitionFactory.class,immediate = true)
public class PdMigrationRollBackMCPImpl extends ProcessDefinitionFactory<PdMigrationRollBackMCP> {

@Reference
private Replicator replicator;

@Reference
private SolrSearchService solrSearchService;
    @Override
    public String getName() {
        return "PD Migration Roll Back MCP Process";
    }

    @Override
    protected PdMigrationRollBackMCP createProcessDefinitionInstance() {

        return new PdMigrationRollBackMCP(replicator,solrSearchService);
    }
}
