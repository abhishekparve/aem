package com.bdb.aem.core.pdmigration;


import com.adobe.acs.commons.mcp.ProcessDefinitionFactory;
import com.day.cq.replication.Replicator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service= ProcessDefinitionFactory.class,immediate = true)
public class QualifyingProductsTagsUpdaterImpl extends ProcessDefinitionFactory<QualifyingProductsTagsUpdater>{

    @Reference
    Replicator replicator;
    public static final String QUALIFYING_PRODUCTS_COMPONENT_UPDATER = "Qualifying Products Component updater";

    @Override
    public String getName() {
        return QUALIFYING_PRODUCTS_COMPONENT_UPDATER;
    }

    @Override
    protected QualifyingProductsTagsUpdater createProcessDefinitionInstance() {
        return new QualifyingProductsTagsUpdater(replicator);
    }
}
