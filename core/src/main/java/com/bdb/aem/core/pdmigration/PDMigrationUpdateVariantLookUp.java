package com.bdb.aem.core.pdmigration;

import com.adobe.acs.commons.fam.ActionManager;
import com.adobe.acs.commons.mcp.ProcessDefinition;
import com.adobe.acs.commons.mcp.ProcessInstance;
import com.adobe.acs.commons.mcp.form.CheckboxComponent;
import com.adobe.acs.commons.mcp.form.FormField;
import com.adobe.acs.commons.mcp.model.ManagedProcess;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.*;

import static com.bdb.aem.core.pdmigration.PdMigrationProcess.CATALOG_PATH;
import static com.bdb.aem.core.pdmigration.PdMigrationProcess.PRD_SERIES_COMMA_SEPARATED_LIST;

@Component
public class PDMigrationUpdateVariantLookUp extends ProcessDefinition {

    public static final String F = "f";
    public static final String SLASH = "/";
    public static final String UNDERSCOREBASE = "_base";
    public static final String XXX = "xxx";
    public static final String XX = "xx";
    public static final String CONTENT_COMMERCE_LOOKUP_VARIANT = "content/commerce/lookup/variant";
    public static final String ALL = "all";
    public static final String SINGLE_PRODUCT = "[0-9]{6}$";
    private final Replicator replicator;
    ManagedProcess processInfo;
    @FormField(name = "Material Number", required = true)
    private String materialNumber;

    @FormField(name = "Activate Lookup Data", component = CheckboxComponent.class, options = {"horizontal", "default=false"})
    private boolean activateData;

    @FormField(name = "Update AlphaNumeric Products Lookup", component = CheckboxComponent.class, options = {"horizontal", "default=false"})
    private boolean alphaNumericProducts;

    public PDMigrationUpdateVariantLookUp(Replicator replicator) {
        this.replicator = replicator;
    }

    @Override
    public void buildProcess(ProcessInstance instance, ResourceResolver rr) throws LoginException, RepositoryException {
        processInfo = instance.getInfo();
        instance.getInfo().setDescription(" PD  Migration Update Variant LookUp process");
        instance.defineCriticalAction("PD Post Migration cleanup MCP", rr, this::delegateLookUpUpdate);
    }


    protected void delegateLookUpUpdate(ActionManager manager) {
        manager.deferredWithResolver(this::handleLookupUpdate);


    }

    protected void handleLookupUpdate(ResourceResolver resourceResolver) throws RepositoryException, ReplicationException {

        if (materialNumber.matches(PRD_SERIES_COMMA_SEPARATED_LIST)) {

            ArrayList<String> list = new ArrayList<>(Arrays.asList(materialNumber.split("\\s*,\\s*")));
            Set<String> seriesKeySet = new HashSet<>(list);
            Iterator<String> seriesKeys = seriesKeySet.iterator();
            while (seriesKeys.hasNext()) {
                String key = SLASH +
                        CONTENT_COMMERCE_LOOKUP_VARIANT +
                        SLASH + seriesKeys.next() + F;
                Resource resource = resourceResolver.getResource(key);
                if (resource != null) {
                    Node productSeriesNode = resource.adaptTo(Node.class);
                    if (productSeriesNode != null && productSeriesNode.hasNodes()) {
                        NodeIterator childNodesIterator = productSeriesNode.getNodes();
                        if (childNodesIterator != null) {
                            while (childNodesIterator.hasNext()) {
                                Node productNode = childNodesIterator.nextNode();
                                if (productNode != null) {
                                    String matNum = productNode.getName();
                                    updateVariantLookUp(resourceResolver, matNum);

                                }

                            }
                        }
                    }

                }

            }

        } else if (materialNumber.matches(SINGLE_PRODUCT)) {
            updateVariantLookUp(resourceResolver, materialNumber);
        } else if (materialNumber.equalsIgnoreCase(ALL)) {
            String variantLookUpBaseFolder = SLASH + CONTENT_COMMERCE_LOOKUP_VARIANT;
            Resource baseResource = resourceResolver.getResource(variantLookUpBaseFolder);
            if (baseResource != null) {
                Node topLevelLookUpNode = baseResource.adaptTo(Node.class);
                if (topLevelLookUpNode != null) {
                    if (topLevelLookUpNode.hasNodes()) {
                        NodeIterator iter = topLevelLookUpNode.getNodes();
                        if (iter != null) {
                            while (iter.hasNext()) {
                                Node childProductLookUpNode = iter.nextNode();
                                if (childProductLookUpNode.hasNodes()) {
                                    NodeIterator childNodesIterator = childProductLookUpNode.getNodes();
                                    if (childNodesIterator != null) {
                                        while (childNodesIterator.hasNext()) {
                                            Node productNode = childNodesIterator.nextNode();
                                            if (productNode != null) {
                                                String matNum = productNode.getName();
                                                updateVariantLookUp(resourceResolver, matNum);

                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }

        }else if(alphaNumericProducts){
            updateVariantLookUp(resourceResolver, materialNumber);
        }
    }

    private void updateVariantLookUp(ResourceResolver resourceResolver, String matNum) throws ReplicationException {
        String variantPath = SLASH +
                CONTENT_COMMERCE_LOOKUP_VARIANT +
                SLASH +
                matNum.substring(0, matNum.length() - 3) +
                F +
                SLASH +
                matNum;
        Resource resource = resourceResolver.getResource(variantPath);
        if (resource != null) {
            ModifiableValueMap props = resource.adaptTo(ModifiableValueMap.class);
            String productCatalogPath = props.get(CATALOG_PATH, String.class);
            if (!StringUtils.isEmpty(productCatalogPath) && productCatalogPath.contains(SLASH)) {
                String[] catalogPathArr = productCatalogPath.split(SLASH);
                String variant = catalogPathArr[catalogPathArr.length - 1];
                String baseProduct = catalogPathArr[catalogPathArr.length - 2].replace(UNDERSCOREBASE, "");
                String migratedPDStructure = baseProduct.substring(0, baseProduct.length() - 3) +
                        XXX +
                        SLASH +
                        baseProduct.substring(0, baseProduct.length() - 2) +
                        XX +
                        SLASH +
                        baseProduct +
                        UNDERSCOREBASE;
                if (!variant.equalsIgnoreCase(baseProduct) && !StringUtils.isEmpty(baseProduct) &&
                        !productCatalogPath.contains(migratedPDStructure)) {
                    String updatedCatalogPath = productCatalogPath.replace(baseProduct + UNDERSCOREBASE, migratedPDStructure);
                    props.put(CATALOG_PATH, updatedCatalogPath);
                    if (Boolean.TRUE.equals(activateData)) {
                        replicator.replicate(resourceResolver.adaptTo(Session.class), ReplicationActionType.ACTIVATE, resource.getPath());
                    }
                }

            }


        }
    }

    @Override
    public void storeReport(ProcessInstance instance, ResourceResolver rr) throws RepositoryException, PersistenceException {

    }

    @Override
    public void init() throws RepositoryException {
        //Nothing to do here.
    }
}
