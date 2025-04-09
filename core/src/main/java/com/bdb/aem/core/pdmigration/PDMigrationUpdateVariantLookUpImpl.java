package com.bdb.aem.core.pdmigration;

import com.adobe.acs.commons.mcp.ProcessDefinitionFactory;
import com.day.cq.replication.Replicator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service= ProcessDefinitionFactory.class,immediate = true)
public class PDMigrationUpdateVariantLookUpImpl extends ProcessDefinitionFactory<PDMigrationUpdateVariantLookUp>{
    @Reference
    Replicator replicator;

    @Override
    public String getName() {
        return  "PD Migration MCP Process - Update LookUp Variants";
    }

    @Override
    protected PDMigrationUpdateVariantLookUp createProcessDefinitionInstance() {
        return new PDMigrationUpdateVariantLookUp(replicator);
    }
}
