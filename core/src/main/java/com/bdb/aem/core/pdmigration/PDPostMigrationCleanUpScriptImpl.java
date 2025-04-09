package com.bdb.aem.core.pdmigration;

import com.adobe.acs.commons.mcp.ProcessDefinitionFactory;
import com.day.cq.replication.Replicator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = ProcessDefinitionFactory.class, immediate = true)
public class PDPostMigrationCleanUpScriptImpl extends ProcessDefinitionFactory<PDPostMigrationCleanUpScript> {

    @Reference
    Replicator replicator;

    @Override
    public String getName() {
        return "PD Post Migration Clean Up Script";
    }

    @Override
    protected PDPostMigrationCleanUpScript createProcessDefinitionInstance() {
        return new PDPostMigrationCleanUpScript(replicator);
    }
}
