package com.bdb.aem.core.pdmigration;

import com.adobe.acs.commons.fam.ActionManager;
import com.adobe.acs.commons.mcp.ProcessDefinition;
import com.adobe.acs.commons.mcp.ProcessInstance;
import com.adobe.acs.commons.mcp.form.FormField;
import com.adobe.acs.commons.mcp.form.PathfieldComponent;
import com.adobe.acs.commons.mcp.form.RadioComponent;
import com.adobe.acs.commons.mcp.model.ManagedProcess;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class PDPostMigrationCleanUpScript extends ProcessDefinition {
    public static final String EXECUTION_MODE = "Mode";
    public static final String PRODUCTS = "products";
    public static final String XXX = "xxx";
    public static final String XX = "xx";
    public static final String COMMERCE = "commerce";

    public static final String MIGRATION_FAILED = "MigrationFailed";
    public static final String ACTIVATED = "activated";
    public static final String INDEXED = "indexed";
    public static final String PRODUCT_FOLDER_NAME;
    public static final String ASSETS = "assets";
    public static final String TAGS = "tags";
    public static final String SOURCE = "source";
    public static final String TARGET = "target";
    public static final String ALL = "all";
    public static final String SINGLE_PRODUCT;
    public static final String PRODUCT_RANGE;
    public static final String EMPTY_STRING = "";

    private static final String SLASH = "/";

    public static final String BASE_PRODUCT_LOOKUP_BASEPATH = SLASH+"content"+SLASH+COMMERCE+SLASH+"lookup"+SLASH+"baseProduct"+SLASH;
    private static final Logger logger = LoggerFactory.getLogger(PDPostMigrationCleanUpScript.class);

    static {
        PRODUCT_RANGE = "^[0-9]{3}:[0-9]{3}$";
        SINGLE_PRODUCT = "[0-9]{6}$";
        PRODUCT_FOLDER_NAME = SINGLE_PRODUCT;
    }

    private final Replicator replicator;


    Map<String, Integer> productRangeMap = new HashMap<>();
    ManagedProcess processInfo;
    private boolean isDryRunMode = false;
    @FormField(
            name = EXECUTION_MODE,
            component = RadioComponent.EnumerationSelector.class,
            required = true,
            description = "MCP Execution Mode",
            options = {"horizontal", "default=DRY_RUN_MODE"}
    )
    private PDPostMigrationCleanUpScript.Mode mode;

    @FormField(name = "Material Number", required = true)
    private String materialNumber;
    @FormField(name = "PD Product Tracking Category Path", component = PathfieldComponent.NodeSelectComponent.class, required = true)
    private String trackingProductPath;

    public PDPostMigrationCleanUpScript(Replicator replicator) {
        this.replicator = replicator;
    }

    @Override
    public void buildProcess(ProcessInstance instance, ResourceResolver rr) throws LoginException {

        processInfo = instance.getInfo();
        instance.getInfo().setDescription(" PD Post Migration Cleanup process");

        if (mode == Mode.LIVE_MODE) {
            instance.defineCriticalAction("PD Post Migration cleanup MCP", rr, this::startPDMigrationCleanUpProcess);
        } else if (mode == Mode.DRY_RUN_MODE) {
            isDryRunMode = true;
            instance.defineCriticalAction("PD Post Migration cleanup MCP", rr, this::startPDMigrationCleanUpProcess);
        }

    }

    protected void startPDMigrationCleanUpProcess(ActionManager manager) {

        if (trackingProductPath != null && materialNumber.equalsIgnoreCase(ALL)) {
            manager.deferredWithResolver(this::triggerCleanUp);
        } else if (trackingProductPath != null && materialNumber.matches(SINGLE_PRODUCT)) {
            manager.deferredWithResolver(this::triggerCleanupSingleProduct);
        } else if (trackingProductPath != null && materialNumber.matches(PRODUCT_RANGE)) {
            String[] products = materialNumber.split(":");
            if (products.length == 2) {
                productRangeMap.put("RangeStart", Integer.parseInt(products[0]));
                productRangeMap.put("RangeEnd", Integer.parseInt(products[1]));
                manager.deferredWithResolver(this::triggerCleanUp);
            }

        }


    }

    protected void triggerCleanUp(ResourceResolver resourceResolver) throws PersistenceException, ReplicationException {

        Resource resource = resourceResolver.getResource(trackingProductPath + SLASH + PRODUCTS);

        if (resource != null && resource.hasChildren()) {
            Iterable<Resource> iterable = resource.getChildren();
            Iterator<Resource> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                handleCleanUpMultipleProducts(resourceResolver, iterator);
            }
        }
    }

    private void handleCleanUpMultipleProducts(ResourceResolver resourceResolver, Iterator<Resource> iterator) throws PersistenceException, ReplicationException {
        Resource childProductFolder = iterator.next();
        if (materialNumber.equalsIgnoreCase(ALL)) {
            if (childProductFolder != null && childProductFolder.hasChildren()) {
                delegateSecondLevelFolderIteration(resourceResolver, childProductFolder);
            }
        } else if (materialNumber.matches(PRODUCT_RANGE)) {
            int productRange = Integer.parseInt(childProductFolder.getName().replace(XXX, EMPTY_STRING));
            if (productRange >= productRangeMap.get("RangeStart") && productRange <= productRangeMap.get("RangeEnd")) {
                delegateSecondLevelFolderIteration(resourceResolver, childProductFolder);
            }
        }
    }


    private void triggerCleanupSingleProduct(ResourceResolver resourceResolver) throws PersistenceException, ReplicationException {
        String productPath = trackingProductPath
                + SLASH
                + PRODUCTS
                + SLASH
                + materialNumber.substring(0, 3)
                + XXX
                + SLASH
                + materialNumber.substring(0, 4)
                + XX
                + SLASH
                + materialNumber;
        Resource resource = resourceResolver.getResource(productPath);
        if (resource != null) {
            handlePDDataDeletion(resourceResolver, resource);
        }
    }

    private void delegateSecondLevelFolderIteration(ResourceResolver resourceResolver, Resource childProductFolder) throws PersistenceException, ReplicationException {

        Iterable<Resource> childProductFolderIterable = childProductFolder.getChildren();
        for (Resource secondLevelChildProductFolder : childProductFolderIterable) {
            if (secondLevelChildProductFolder != null) {
                delegateThirdLevelFolderIteration(resourceResolver, secondLevelChildProductFolder);
            }
        }

    }

    private void delegateThirdLevelFolderIteration(ResourceResolver resourceResolver, Resource childProductFolder) throws PersistenceException, ReplicationException {
        Iterable<Resource> childProductFolderIterable = childProductFolder.getChildren();
        for (Resource secondLevelChildProductFolder : childProductFolderIterable) {
            if (secondLevelChildProductFolder != null) {
                handlePDDataDeletion(resourceResolver, secondLevelChildProductFolder);
            }
        }
    }


    private void handlePDDataDeletion(ResourceResolver resourceResolver, Resource resource) throws PersistenceException, ReplicationException {

        Resource commerceResource = resource.getChild(COMMERCE);
        Resource assetResource = resource.getChild(ASSETS);
        Resource tagsResource = resource.getChild(TAGS);
        String lookupResourcePath = getResourcePathFromLookUp(resourceResolver, resource);
        if (commerceResource != null) {
            ValueMap properties = commerceResource.adaptTo(ValueMap.class);
            if (properties != null) {
                String source = Objects.requireNonNull(properties.get(SOURCE, String.class));
                String target = Objects.requireNonNull(properties.get(TARGET, String.class));
                Resource sourceResource = resourceResolver.getResource(source);
                Resource targetResource = resourceResolver.getResource(target);
                // In case the MigrationFailed property value is empty or null,
                // we want to return true so that the cleanup for this product doesn't happen.
                // Also, we need to add additional check to ensure that lookupCatalogPath value and the target value on the tracking product path is same.
                if (!properties.get(MIGRATION_FAILED, true) &&
                        properties.get(INDEXED, false) &&
                        properties.get(ACTIVATED, false) &&
                        targetResource != null &&
                        sourceResource != null &&
                        lookupResourcePath.equalsIgnoreCase(target)) {
                    deactivateAndDelete(resourceResolver, sourceResource);
                    if (assetResource != null) {
                        cleanUpAssetsAndTags(resourceResolver, assetResource);
                    }
                    if (tagsResource != null) {
                        cleanUpAssetsAndTags(resourceResolver, tagsResource);
                    }


                }
            }
        }


    }



    private String getResourcePathFromLookUp(ResourceResolver resolver, Resource resource) {
        String catalogPath = "";
        Resource lookUpResource = resolver.getResource(BASE_PRODUCT_LOOKUP_BASEPATH + resource.getName().substring(0, 3) + "f" + SLASH + resource.getName());
        if (lookUpResource != null) {

            ValueMap properties = lookUpResource.adaptTo(ValueMap.class);
            if (properties != null) {
                catalogPath = properties.get("catalogPath", String.class);
            }
        }

        return catalogPath;
    }


    private void cleanUpAssetsAndTags(ResourceResolver resourceResolver, Resource resource) throws PersistenceException, ReplicationException {

        ValueMap assetProperties = resource.adaptTo(ValueMap.class);
        if (assetProperties != null) {
            String source = assetProperties.get(SOURCE, String.class);
            if (StringUtils.isNotEmpty(source)) {
                Resource sourceResource = resourceResolver.getResource(source);
                if (sourceResource != null) {
                    deactivateAndDelete(resourceResolver, sourceResource);
                }
            }
        }

    }


    private void deactivateAndDelete(ResourceResolver resourceResolver, Resource resource) throws ReplicationException, PersistenceException {
        if (Boolean.TRUE.equals(isDryRunMode)) {
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Deactivating and Deleting resource at path :: %s ", resource.getPath()));
            }
        } else {
            replicator.replicate(resourceResolver.adaptTo(Session.class), ReplicationActionType.DEACTIVATE, resource.getPath());
            resourceResolver.delete(resource);
        }

    }


    @Override
    public void storeReport(ProcessInstance instance, ResourceResolver rr) {
        logger.info("Inside Store Report method");
    }

    @Override
    public void init() throws RepositoryException {
        logger.info("Inside Init method");
    }

    private enum Mode {
        /**
         * Live mode mode.
         */
        LIVE_MODE,
        /**
         * Dry run mode mode.
         */
        DRY_RUN_MODE
    }

}
