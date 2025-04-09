package com.bdb.aem.core.pdmigration;

import com.adobe.acs.commons.mcp.ProcessDefinitionFactory;
import com.day.cq.replication.Replicator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service= ProcessDefinitionFactory.class,immediate = true)
public class ManagePDPropertiesImpl extends ProcessDefinitionFactory<ManagePDProperties> {

    @Reference
    private Replicator replicator;

    public static final String MCP_NAME = "Update Product Data Properties";

    @Override
    public String getName() {
        return MCP_NAME;
    }

    @Override
    protected ManagePDProperties createProcessDefinitionInstance() {
        return new ManagePDProperties(replicator);
    }
}
