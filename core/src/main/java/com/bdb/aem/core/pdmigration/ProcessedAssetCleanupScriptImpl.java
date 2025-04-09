package com.bdb.aem.core.pdmigration;


import com.adobe.acs.commons.mcp.ProcessDefinitionFactory;
import com.day.cq.replication.Replicator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service= ProcessDefinitionFactory.class,immediate = true)
public class ProcessedAssetCleanupScriptImpl extends ProcessDefinitionFactory<ProcessedAssetCleanUpScript>{

    @Reference
    Replicator replicator;
    public static final String PROCESSED_ASSET_CLEANUP_SCRIPT = "Processed Asset Cleanup Script";

    @Override
    public String getName() {
        return PROCESSED_ASSET_CLEANUP_SCRIPT;
    }

    @Override
    protected ProcessedAssetCleanUpScript createProcessDefinitionInstance() {
        return new ProcessedAssetCleanUpScript(replicator);
    }
}
