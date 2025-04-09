package com.bdb.aem.core.pdmigration;

import com.adobe.acs.commons.mcp.ProcessDefinitionFactory;
import org.osgi.service.component.annotations.Component;


@Component(service=ProcessDefinitionFactory.class,immediate = true)
public class PdMigrationMcpProcessImpl extends ProcessDefinitionFactory<PdMigrationProcess> {


    @Override
    public String getName() {
        return "PD Migration MCP Process";
    }

    @Override
    protected PdMigrationProcess createProcessDefinitionInstance() {
        return new PdMigrationProcess();
    }

}