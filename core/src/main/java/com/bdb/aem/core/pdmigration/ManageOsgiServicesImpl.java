package com.bdb.aem.core.pdmigration;


import com.adobe.acs.commons.mcp.ProcessDefinitionFactory;
import org.osgi.service.component.annotations.Component;

@Component(service= ProcessDefinitionFactory.class,immediate = true)
public class ManageOsgiServicesImpl extends ProcessDefinitionFactory<ManageOsgiServices>{

    public static final String MCP_NAME = "Manage OSGI Component and Services";

    @Override
    public String getName() {
        return MCP_NAME;
    }

    @Override
    protected ManageOsgiServices createProcessDefinitionInstance() {
        return new ManageOsgiServices();
    }
}
