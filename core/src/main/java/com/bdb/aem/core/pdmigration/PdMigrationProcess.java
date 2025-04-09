package com.bdb.aem.core.pdmigration;

import com.adobe.acs.commons.fam.ActionManager;
import com.adobe.acs.commons.mcp.ProcessDefinition;
import com.adobe.acs.commons.mcp.ProcessInstance;
import com.adobe.acs.commons.mcp.form.CheckboxComponent;
import com.adobe.acs.commons.mcp.form.FormField;
import com.adobe.acs.commons.mcp.form.PathfieldComponent;
import com.adobe.acs.commons.mcp.form.RadioComponent;
import com.adobe.acs.commons.mcp.model.GenericReport;
import com.adobe.acs.commons.mcp.model.ManagedProcess;
import com.adobe.granite.asset.api.AssetManager;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.api.resource.*;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * The type Pd migration process.
 * This MCP process definition allows migration of Product data that includes Products , DAM assets and associated cq:Tags.
 * The migration process re-organises the product data based on the new product super category structure.
 */
@Component
public class PdMigrationProcess extends ProcessDefinition {

    /**
     * The constant LIVE_MODE.
     */
    public static final String LIVE_MODE = "live";
    /**
     * The constant DRY_RUN_MODE.
     */
    public static final String DRY_RUN_MODE = "dryrun";
    /**
     * The constant PRODUCTS.
     */
    public static final String PRODUCTS = "products";
    public static final String CUR_CAT = "CUR_CAT";
    public static final String NEW_CAT = "NEW_CAT";
    /**
     * The constant MIGRATION_FAILED.
     */
    public static final String MIGRATION_FAILED = "MigrationFailed";
    /**
     * The constant CONTENT_COMMERCE_PRODUCTS_BDB_PRODUCTS.
     */
    public static final String CONTENT_COMMERCE_PRODUCTS_BDB_PRODUCTS = "/content/commerce/products/bdb/products/";
    /**
     * The constant SLING_FOLDER.
     */
    public static final String SLING_FOLDER = "sling:Folder";
    /**
     * The constant ASSETS.
     */
    public static final String ASSETS = "assets";
    /**
     * The constant TAGS.
     */
    public static final String TAGS = "tags";
    /**
     * The constant CONTENT_DAM_BDB_PRODUCTS_GLOBAL.
     */
    public static final String CONTENT_DAM_BDB_PRODUCTS_GLOBAL = "/content/dam/bdb/products/global/";
    /**
     * The constant COMMERCE_PRODUCTS.
     */
    public static final String COMMERCE_PRODUCTS = "commerce/products";
    /**
     * The constant CQ_TAGS.
     */
    public static final String CQ_TAGS = com.day.cq.tagging.TagConstants.PN_TAGS;
    /**
     * The constant COMMERCE_LOOKUP_BASE_PRODUCT.
     */
    public static final String COMMERCE_LOOKUP_BASE_PRODUCT = "/content/commerce/lookup/baseProduct/";
    /**
     * The constant COMMERCE_LOOKUP_VARIANT.
     */
    public static final String COMMERCE_LOOKUP_VARIANT = "/content/commerce/lookup/variant/";

    /**
     * The constant HP.
     */
    public static final String HP = "hp";
    /**
     * The constant PRIMARY_SUPER_CATEGORY.
     */
    public static final String PRIMARY_SUPER_CATEGORY = "primarySuperCategory";
    /**
     * The constant SUPER_CATEGORY.
     */
    public static final String SUPER_CATEGORY = "superCategory";
    /**
     * The constant CATALOG_PATH.
     */
    public static final String CATALOG_PATH = "catalogPath";
    /**
     * The constant BASE_PRODUCT.
     */
    public static final String BASE_PRODUCT = "baseProduct";
    /**
     * The constant VARIANT.
     */
    public static final String VARIANT = "variant";
    /**
     * The constant CQ_TAG.
     */
    public static final String CQ_TAG = com.day.cq.tagging.TagConstants.NT_TAG;
    /**
     * The constant MIGRATION.
     */
    public static final String MIGRATION = "migration";
    /**
     * The constant MATERIAL_NUMBER.
     */
    public static final String MATERIAL_NUMBER = "materialNumber";
    /**
     * The constant SOURCE.
     */
    public static final String SOURCE = "source";
    /**
     * The constant TARGET.
     */
    public static final String TARGET = "target";
    /**
     * The constant ACTIVATED.
     */
    public static final String ACTIVATED = "activated";
    /**
     * The constant DEACTIVATED.
     */
    public static final String DEACTIVATED = "deactivated";
    /**
     * The constant INDEXED.
     */
    public static final String INDEXED = "indexed";
    /**
     * The constant CONTENT_DAM_BDB_GLOBAL.
     */
    public static final String CONTENT_DAM_BDB_GLOBAL = "/content/dam/bdb/global";
    /**
     * The constant CONTENT_TAGS.
     */
    public static final String CONTENT_TAGS = "/content/tags";
    /**
     * The constant FAILED_PRODUCTS.
     */
    public static final String FAILED_PRODUCTS = "Failed Products";
    /**
     * The constant TOTAL.
     */
    public static final String TOTAL = "Total";
    /**
     * The constant TOTAL_ALREADY_MIGRATED_PRODUCTS.
     */
    public static final String TOTAL_ALREADY_MIGRATED_PRODUCTS = "Total already Migrated Products";
    /**
     * The constant PRODUCT_CATEGORY.
     */
    public static final String PRODUCT_CATEGORY = "Category";


    /**
     * The constant EXECUTION_MODE.
     */
    public static final String EXECUTION_MODE = "Mode";
    /**
     * The constant IGNORE_INCORRECT.
     */
    public static final String IGNORE_INCORRECT = "Ignore Incorrect";
    /**
     * The constant VERBOSE.
     */
    public static final String VERBOSE = "Verbose";
    /**
     * The constant TOTAL_MIGRATED.
     */
    public static final String TOTAL_MIGRATED = "Total Migrated";
    /**
     * The constant XXX.
     */
    public static final String XXX = "xxx";
    /**
     * The constant XX.
     */
    public static final String XX = "xx";
    /**
     * The constant DELIMITER_SEMICOLON.
     */
    public static final String DELIMITER_SEMICOLON = ";";
    /**
     * The constant DELIMITER_EQUALS.
     */
    public static final String DELIMITER_EQUALS = "=";

    public static final String LATEST = "latest";
    public static final String ALREADY_MIGRATED_PRODUCTS = "Already Migrated Products";
    public static final String ALL = "all";
    public static final String PRD_COMMA_SEPARATED_LIST = "^(\\d{6},\\s*)*\\d{6}$";
    public static final String SPCL_PRD_COMMA_SEPARATED_LIST = "^(\\[ A-Za-z0-9_./+-],\\s*)*\\[ A-Za-z0-9_./+-]$";
    public static final String COMMA = ",";
    public static final String MIGRATION_FAILURE_AND_WARNINGS = "Migration Failure and Warnings";
    public static final String LOOKUP_FOLDER_SUFFIX = "f";
    public static final String PRD_SERIES_COMMA_SEPARATED_LIST = "^(\\d{3},\\s*)*\\d{3}$";
    public static final String TOTAL_FAILED_PRODUCTS = "Total Failed Products";
    public static final String DAM_BDB_CONTEXT_PATH = "dam/bdb/products/global";
    public static final String COMMERCE_PRODUCTS_BDB_PRODUCTS = "commerce/products/bdb/products";
    private static final Logger logger = LoggerFactory.getLogger(PdMigrationProcess.class);
    private static final String REPORT_NAME = "PD Migration Process Handler";
    @SuppressWarnings("squid:S1075")
    private static final String MIGRATION_LOGGING_BASE_PATH = "/var/bdb";
    private static final String SLASH = "/";
    private static final String UNDERSCOREBASE = "_base";
    private static final String SINGLE_PRD_REGEX = "[0-9]{6}$";
    private static final String PRD_RANGE_REGEX = "[0-9]{3}:[0-9]{3}$";
    private static final String PD_MIGRATION_MCP = "PD migration Process";
    private static final String PD_COMMERCE = "commerce";
    private static final GenericReport report = new GenericReport();
    private final List<EnumMap<ReportColumns, Object>> reportRows = Collections.synchronizedList(new ArrayList<>());

    List<String> newCategories = new ArrayList<>();
    /**
     * The Process info.
     */
    ManagedProcess processInfo;

    /**
     * The Sub list.
     */
    List<String> subList;
    /**
     * The Failed products.
     */
    List<String> failedProducts = new ArrayList<>();
    List<String> migratedProducts = new ArrayList<>();
    /**
     * The Already migrated products.
     */
    List<String> alreadyMigratedProducts = new ArrayList<>();
    List<String> lookUpTopLevelFolders = new ArrayList<>();

    private boolean isDryRunMode = false;
    private int successCount;
    private int alreadyMigratedCount;
    @FormField(
            name = EXECUTION_MODE,
            component = RadioComponent.EnumerationSelector.class,
            required = true,
            description = "MCP Execution Mode",
            options = {"horizontal", "default=DRY_RUN_MODE"}
    )
    private Mode mode;

    @FormField(name = "Material Number", required = true)
    private String matNum;
    @FormField(name = "Alpha Numeric Products?",
            description = "Select this option if you want to migrate ONLY special Product Numbers like 02-6073S-02S",
            component = CheckboxComponent.class,
            options = {"horizontal", "default=false"})
    private boolean specialProducts;

    @FormField(name = "PD Category Path",
            description = "Path to the Product category. " +
                    "Selecting this property along with the Material Number Field will only migrate the products that belong to the chosen product category",
            component = PathfieldComponent.NodeSelectComponent.class)
    private String category;
    @FormField(name = "OSGI Services to be Managed during Migration",
            description = "Comma separated list OSGI component/services names that need to be managed during the migration. " +
                    "Each of the values should be the component.name property of the OSGI component", required = false)
    private String osgiServices = "com.bdb.aem.core.schedulers.SimpleScheduledTask";


    @FormField(name = "Verbose Mode", component = CheckboxComponent.class, options = {"horizontal", "default=false"})
    private boolean verboseMode;

    @FormField(name = "Update Lookup Structure only for AlphaNumeric Products", component = CheckboxComponent.class, options = {"horizontal", "default=false"})
    private boolean updateLookUpSpecialProducts;


    /**
     * MCP Instance FAM definition and flow segregation based on the Execution mode (LIVE or DRYRUN).
     * There is a possibility to add a retry logic to the MCP instance if needed.
     */

    @Override
    public void buildProcess(ProcessInstance instance, ResourceResolver rr) throws LoginException {
        logger.info("Inside buildProcess Method");
        processInfo = instance.getInfo();
        instance.getInfo().setDescription("Running PD reorganization process");
        switch (mode) {
            case LIVE_MODE:
                manageOSGIServices(false);
                try {
                    if (Boolean.FALSE.equals(updateLookUpSpecialProducts)) {
                        instance.defineCriticalAction(PD_MIGRATION_MCP, rr, this::startPDMigrationProcess);
                        instance.defineCriticalAction(PD_MIGRATION_MCP, rr, this::persistMigration);
                    } else {

                        instance.defineCriticalAction(PD_MIGRATION_MCP, rr, this::refractorLookupData);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                } finally {
                    manageOSGIServices(true);
                    logger.info("Exiting the MCP process with Errors.");
                }
                break;
            case DRY_RUN_MODE:
                isDryRunMode = true;
                instance.defineCriticalAction(PD_MIGRATION_MCP, rr, this::startPDMigrationProcessDryRunMode);
                instance.defineCriticalAction(PD_MIGRATION_MCP, rr, this::persistMigration);
                break;
        }


        report.setName(REPORT_NAME);


    }

    /**
     * Manage osgi services. This method allows to enable/disable OSGI Components and services.
     */
    protected void manageOSGIServices(boolean enable) {

        Bundle osgiBundle = FrameworkUtil.getBundle(this.getClass());
        ServiceComponentRuntime scr = (ServiceComponentRuntime) osgiBundle.
                getBundleContext().
                getService(osgiBundle.
                        getBundleContext().
                        getServiceReference(ServiceComponentRuntime.class.getName()));
        if (enable) {
            logger.info("Enabling OSGI Components/Services");
        } else {
            logger.info("Disabling OSGI Components/Services");
        }

        if (osgiServices != null) {
            if (!osgiServices.isEmpty()) {

                if (osgiServices.contains(COMMA)) {
                    Arrays.stream(osgiServices.split(COMMA)).forEach(item -> {
                        processOSGIComponent(scr, osgiBundle, item, enable);
                    });
                } else {
                    processOSGIComponent(scr, osgiBundle, osgiServices, enable);

                }
            }
        }

    }

    protected void processOSGIComponent(ServiceComponentRuntime scr, Bundle osgiBundle, String osgiServiceComponent, boolean enable) {

        ComponentDescriptionDTO dto = scr.getComponentDescriptionDTO(osgiBundle, osgiServiceComponent.trim());
        if (dto != null) {
            if (enable) {
                scr.enableComponent(dto);
                logger.info("Component {} enabled by configuration.", dto.implementationClass);
            } else {
                scr.disableComponent(dto);
                logger.info("Component {} disabled by configuration.", dto.implementationClass);
            }
        }
    }


    /**
     * Persist migration.
     *
     * @param manager the manager
     */
    protected void persistMigration(ActionManager manager) {
        manager.deferredWithResolver(this::persistMigrationData);
    }

    protected void refractorLookupData(ActionManager manager) {
        manager.deferredWithResolver(this::updateSpecialProductsLookUp);
    }


    protected void updateSpecialProductsLookUp(ResourceResolver resolver) throws RepositoryException, PersistenceException {

        List<String> specialPDList = new ArrayList<>(Arrays.asList(matNum.split("\\s*,\\s*")));
        for (String item : specialPDList) {
            if (item.length() > 9) {
                StringBuilder sb = new StringBuilder(COMMERCE_LOOKUP_BASE_PRODUCT);
                // Create a Structure Like - "/content/commerce/lookup/baseProduct/02-f/607f/90-f/02-60790-01S"
             Resource baseResource = resolver.getResource(String.valueOf(getLookupPath(sb,item)));
                if (baseResource != null) {
                    String updatedBaseLookUpPath = COMMERCE_LOOKUP_BASE_PRODUCT + item.substring(0, item.length() - 3) + LOOKUP_FOLDER_SUFFIX;
                    if (resolver.getResource(updatedBaseLookUpPath) == null) {
                        JcrUtils.getOrCreateByPath(updatedBaseLookUpPath, SLING_FOLDER, Objects.requireNonNull(resolver.adaptTo(Session.class)));
                        resolver.copy(baseResource.getPath(), updatedBaseLookUpPath);
                    }
                }

                StringBuilder variantSb = new StringBuilder(COMMERCE_LOOKUP_VARIANT);
                // Create a Structure Like - "/content/commerce/lookup/variant/02-f/607f/90-f/02-60790-01S"
                Resource variantResource = resolver.getResource(String.valueOf(getLookupPath(variantSb,item)));

                if (variantResource != null) {
                    String updateVariantLookUpPath = COMMERCE_LOOKUP_VARIANT + item.substring(0, item.length() - 3) + LOOKUP_FOLDER_SUFFIX;
                    if (resolver.getResource(updateVariantLookUpPath) == null) {
                        JcrUtils.getOrCreateByPath(updateVariantLookUpPath, SLING_FOLDER, Objects.requireNonNull(resolver.adaptTo(Session.class)));
                        resolver.copy(variantResource.getPath(), updateVariantLookUpPath);
                    }
                }

            }

        }
    }


    private StringBuilder buildFolderPath(StringBuilder sb, String item) {
        int folderLevels = (item.length()) / 3;
        int charIndex = 0;
        for (int i = 0; i < folderLevels; i++) {
            sb.append(item.substring(charIndex, charIndex + 3)).append(LOOKUP_FOLDER_SUFFIX).append(SLASH);
            charIndex = charIndex + 3;

        }
        sb.append(item);
        return sb;
    }


    private StringBuilder getLookupPath(StringBuilder builder,String productId) {

        String temp = productId;
        while (temp.length() > 0) {
            if (temp.length() > 2 && temp.length() != 3) {
                builder.append(temp.substring(0, 3)).append("f").append("/");
                temp = temp.substring(3);
            } else {
                temp = "";
            }
        }
        builder.append(productId);
        return builder;
    }


    protected int iterateLookUpTopFolders(ResourceResolver resolver, Iterator<String> keys) throws RepositoryException, PersistenceException {
        int pdSize = 0;
        while (keys.hasNext()) {
            String key = keys.next() + LOOKUP_FOLDER_SUFFIX;
            Resource res = resolver.getResource(COMMERCE_LOOKUP_BASE_PRODUCT + key);
            if (res != null) {
                Node topLevelLookUpNode = res.adaptTo(Node.class);
                if (topLevelLookUpNode != null) {
                    if (topLevelLookUpNode.hasNodes()) {
                        NodeIterator iter = topLevelLookUpNode.getNodes();
                        if (iter != null) {
                            while (iter.hasNext()) {
                                Node childProductLookUpNode = iter.nextNode();
                                Resource childResource = resolver.getResource(childProductLookUpNode.getPath());
                                if (childResource != null) {

                                    ValueMap props = childResource.adaptTo(ValueMap.class);
                                    if (props != null) {
                                        if (props.containsKey(CATALOG_PATH)) {
                                            String productCatalogPath = props.get(CATALOG_PATH, String.class);
                                            if (!Objects.equals(category, "")) {
                                                if ((category + SLASH + childProductLookUpNode.getName() + UNDERSCOREBASE).equals(productCatalogPath)) {
                                                    handleMigration(childProductLookUpNode.getName(), productCatalogPath, resolver);
                                                    pdSize++;
                                                }
                                            } else {
                                                handleMigration(childProductLookUpNode.getName(), productCatalogPath, resolver);
                                                pdSize++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return pdSize;
    }

    protected void persistMigrationData(ResourceResolver resolver) throws RepositoryException, PersistenceException {

        Collections.sort(lookUpTopLevelFolders);
        Set<String> keySet = new HashSet<>(lookUpTopLevelFolders);
        Iterator<String> keys = keySet.iterator();

        int pdSize = 0;
        List<String> list = new ArrayList<>();
        if (matNum.equalsIgnoreCase(ALL) && Boolean.FALSE.equals(specialProducts)) {
            if (keys != null) {
                pdSize = iterateLookUpTopFolders(resolver, keys);
            }
        } else if (matNum.matches(PRD_SERIES_COMMA_SEPARATED_LIST)) {
            // Handle top level Product series Input data like [550,530,520]
            subList = new ArrayList<>(Arrays.asList(matNum.split("\\s*,\\s*")));
            pdSize = subList.size();
            Set<String> seriesKeySet = new HashSet<>(subList);
            Iterator<String> seriesKeys = seriesKeySet.iterator();
            if (seriesKeys != null) {
                pdSize = iterateLookUpTopFolders(resolver, seriesKeys);

            }
        } else if (matNum.matches(SINGLE_PRD_REGEX)) {
            // handle input of type [550000]
            Resource res = resolver.getResource(COMMERCE_LOOKUP_BASE_PRODUCT + matNum.substring(0, matNum.length() - 3) + LOOKUP_FOLDER_SUFFIX + SLASH + matNum);
            if (res != null) {
                ValueMap props = res.adaptTo(ValueMap.class);
                pdSize = 1;
                if (props != null) {
                    handleMigration(matNum, props.get(CATALOG_PATH, String.class), resolver);
                }
            }
        } else if (matNum.matches(PRD_COMMA_SEPARATED_LIST)) {
            // This handles input of type [550000,553300,670088]
            subList = new ArrayList<>(Arrays.asList(matNum.split("\\s*,\\s*")));
            pdSize = subList.size();
            for (String mat : subList) {
                Resource res = resolver.getResource(COMMERCE_LOOKUP_BASE_PRODUCT + matNum.substring(0, matNum.length() - 3) +
                        LOOKUP_FOLDER_SUFFIX +
                        SLASH +
                        mat);
                if (res != null) {
                    ValueMap props = res.adaptTo(ValueMap.class);
                    if (props != null) {
                        handleMigration(mat, props.get(CATALOG_PATH, String.class), resolver);
                    }
                }
            }

        } else if (matNum.matches(PRD_RANGE_REGEX)) {
            // This handles input of type [550:590]. This is the range of the top level lookup Series folders.
            logger.info("matched  product range");
            String startMaterialNumber = matNum.split(":")[0];
            String endMaterialNumber = matNum.split(":")[1];
            if (startMaterialNumber.equalsIgnoreCase(endMaterialNumber)) {
                list.add(startMaterialNumber);
                subList = lookUpTopLevelFolders
                        .subList(lookUpTopLevelFolders
                                        .indexOf(startMaterialNumber),
                                lookUpTopLevelFolders
                                        .indexOf(endMaterialNumber));
            }
            if ((Integer.parseInt(startMaterialNumber) > Integer.parseInt(endMaterialNumber))) {

                throw new IllegalArgumentException(String.format("Range Start: %s1 is greater than Range End : %s2", startMaterialNumber, endMaterialNumber));

            } else {
                list = new ArrayList<>(keySet);
                Collections.sort(list);

                if (list.contains(startMaterialNumber) && list.contains(endMaterialNumber)) {
                    subList = list.subList(list.indexOf(startMaterialNumber), list.indexOf(endMaterialNumber) + 1);
                } else {
                    list.forEach(item -> {
                        if (Integer.parseInt(item) >= Integer.parseInt(startMaterialNumber) &&
                                Integer.parseInt(item) <= Integer.parseInt(endMaterialNumber)) {
                            subList.add(item);
                        }
                    });

                }
            }
            Set<String> rangeKeysSet = new HashSet<>(subList);
            Iterator<String> rangeKeys = rangeKeysSet.iterator();
            pdSize = iterateLookUpTopFolders(resolver, rangeKeys);

        } else if (Boolean.TRUE.equals(specialProducts)) {
            // Handle Inputs like [02-60791-01S, 02-61529-00S]
            subList = new ArrayList<>(Arrays.asList(matNum.split("\\s*,\\s*")));
            for (String item : subList) {
                if (item.length() > 9) {
                    StringBuilder sb = new StringBuilder(COMMERCE_LOOKUP_BASE_PRODUCT);
                    // Create a Structure Like - "/content/commerce/lookup/baseProduct/02-60790-f/02-60790-01S"
                    Resource res = resolver.getResource(String.valueOf(sb.append(item.substring(0, item.length()-3))
                            .append(LOOKUP_FOLDER_SUFFIX)
                            .append(SLASH)
                            .append(item)));

                    if (res != null) {
                        ValueMap props = res.adaptTo(ValueMap.class);
                        if (props != null) {
                            try {
                                handleMigration(item, props.get(CATALOG_PATH, String.class), resolver);
                            } catch (PersistenceException e) {
                                throw new RuntimeException(e);
                            } catch (RepositoryException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    }
                }
                pdSize++;
            }


        }
        if (keys != null) {
            addMigrationJobdetails(resolver, pdSize, subList, successCount, failedProducts);
        }
    }


    /**
     * Persist migration data.
     * Here we are parsing the input product data list / range / specific Product number and then delegating to Migration handler method.
     *
     * @param resolver the resolver
     * @throws RepositoryException  the repository exception
     * @throws PersistenceException the persistence exception
     */

    private void handleMigration(String mat, String currentPdPath, ResourceResolver resolver) throws PersistenceException, RepositoryException {

        boolean isVariant = false;
        List<String> failureReason = new ArrayList<>();
        String lookUpPath = getCurrentLookUpPath(isVariant, mat);

        String currentAssetPath = getCurrentAssetPath(currentPdPath);
        String currentTagsPath = getCurrentTagsPath(currentPdPath);
        //Create the migration tracking data folder Structure under /Var

        String path = MIGRATION_LOGGING_BASE_PATH +
                currentPdPath.replace(mat + UNDERSCOREBASE, "") +
                PRODUCTS +
                SLASH +
                mat.substring(0, mat.length() - 3) +
                XXX +
                SLASH +
                mat.substring(0, mat.length() - 2) +
                XX +
                SLASH +
                mat;
        Resource trackingProductResource = resolver.getResource(path + SLASH + PD_COMMERCE);
        boolean isProductAlreadyMigrated = alreadyMigratedPD(resolver, currentPdPath, mat, trackingProductResource);
        boolean migrationFailed = true;
        // Check if the product has already been migrated and if the previous migration was successful.
        if (trackingProductResource != null) {
            ValueMap map = trackingProductResource.adaptTo(ValueMap.class);
            if (map != null && map.containsKey(MIGRATION_FAILED)) {
                migrationFailed = Boolean.TRUE.equals(map.get(MIGRATION_FAILED, Boolean.class));

            }
        }
        boolean isFailureProduct = false;
        if (Boolean.TRUE.equals(migrationFailed)) {
            if (resolver.getResource(currentPdPath) == null ||
                    resolver.getResource(lookUpPath) == null ||
                    !checkPDLookUpPath(resolver, currentPdPath)) {
                isFailureProduct = true;
                failedProducts.add(mat);
                if (resolver.getResource(currentPdPath) == null) {
                    failureReason.add("[E]: Base Product Folder does not exist");
                }
                if (resolver.getResource(lookUpPath) == null) {

                    failureReason.add("[E]:  Base Product LookUp Folder does not exist");
                }
                if (resolver.getResource(currentAssetPath) == null) {
                    failureReason.add("[W]:  Product Assets Folder does not exist");

                }
                if (resolver.getResource(currentTagsPath) == null) {
                    failureReason.add("[W]:  Product Tags Folder does not exist");

                }
                if (Boolean.FALSE.equals(checkPDLookUpPath(resolver, currentPdPath))) {
                    failureReason.add("[E]:  One or more Product Variants Product-lookUp Folder does not exist");
                }
            }
        }
        if (isProductAlreadyMigrated) {
            alreadyMigratedCount++;
            alreadyMigratedProducts.add(mat);
        } else if ((!(isFailureProduct)) && Boolean.FALSE.equals(isProductAlreadyMigrated)) {
            try {

                movePdData(resolver,
                        mat,
                        currentPdPath,
                        lookUpPath,
                        currentAssetPath,
                        currentTagsPath);
                migratedProducts.add(mat);
                successCount++;
            } catch (PersistenceException e) {
                throw new PersistenceException(e.getMessage());
            } catch (RepositoryException e) {
                throw new RepositoryException(e.getMessage());
            }

        }
        if (!isDryRunMode) {
            if (Boolean.FALSE.equals(isProductAlreadyMigrated)) {
                try {
                    JcrUtils.getOrCreateByPath(path, SLING_FOLDER, Objects.requireNonNull(resolver.adaptTo(Session.class)));
                    JcrUtils.getOrCreateByPath(path + SLASH + PD_COMMERCE, SLING_FOLDER, Objects.requireNonNull(resolver.adaptTo(Session.class)));
                    JcrUtils.getOrCreateByPath(path + SLASH + ASSETS, SLING_FOLDER, Objects.requireNonNull(resolver.adaptTo(Session.class)));
                    JcrUtils.getOrCreateByPath(path + SLASH + TAGS, SLING_FOLDER, Objects.requireNonNull(resolver.adaptTo(Session.class)));
                    addExecutionMetadata(resolver, path, currentPdPath, isFailureProduct, failureReason, mat);

                } catch (PersistenceException e) {
                    throw new PersistenceException(e.getMessage());
                } catch (RepositoryException e) {
                    throw new RepositoryException(e.getMessage());
                }
            }

        } else {

            logger.info(String.format("Dry Run Execution Mode - Creating tracking data for Material Number :: %s", mat));
        }


    }


    private boolean alreadyMigratedPD(ResourceResolver resolver, String currentPDPath, String materialNumber, Resource trackingProductResource) {

        boolean migrationFailed = true;
        if (currentPDPath.contains(materialNumber.substring(0, materialNumber.length() - 3)
                + XXX + SLASH
                + materialNumber.substring(0, materialNumber.length() - 2)
                + XX + SLASH + materialNumber + UNDERSCOREBASE)) {

            return true;
        }
        // check whether the product tracking data folder exists already from a previous migration run.
        if (trackingProductResource != null) {
            ValueMap map = trackingProductResource.adaptTo(ValueMap.class);
            //check whether the tracking data folder contains a property called MigrationFailed
            // and if it is false(meaning the last migration was successful).
            if (map != null && map.containsKey(MIGRATION_FAILED)) {
                migrationFailed = Boolean.TRUE.equals(map.get(MIGRATION_FAILED, Boolean.class));
                if (Boolean.FALSE.equals(migrationFailed)) {
                    String target = map.get(TARGET, String.class);
                    if (target != null) {
                        //check whether the target folder for the product was created successfully in the last migration run.
                        if (resolver.getResource(target) != null) {
                            //check whether the target folder path is the same as the lookUp folder catalog Path value.
                            // That means that during the previous migration, the product folder was successfully migrated
                            // and the lookUp property was also updated
                            if (target.equalsIgnoreCase(currentPDPath)) {
                                // Finally check whether the target path value contains a sequence like 550xxx/550000_base.
                                // This means that migration of this product is already done and the product is marked as already migrated.s
                                if (target.contains(materialNumber.substring(0, materialNumber.length() - 3) + XXX + SLASH + materialNumber + UNDERSCOREBASE)) {
                                    logger.info("Product ::" + materialNumber + "  is already migrated.");
                                    return true;
                                }

                            }

                        }
                    }

                }

            }

        }

        return false;
    }

    private boolean checkPDLookUpPath(ResourceResolver resolver, String currentPdPath) throws RepositoryException {
        logger.info(String.format("Current PD Path :: %s", currentPdPath));
        if (resolver.getResource(currentPdPath) != null) {
            Node parentNode = Objects.requireNonNull(resolver.getResource(currentPdPath)).adaptTo(Node.class);
            if (parentNode != null) {
                NodeIterator iterator = parentNode.getNodes();
                while (iterator.hasNext()) {
                    Node node = iterator.nextNode();
                    if (node.getName().matches(SINGLE_PRD_REGEX)) {
                        String variantLookUpPath = COMMERCE_LOOKUP_VARIANT + node.getName().substring(0, node.getName().length() - 3) + LOOKUP_FOLDER_SUFFIX + SLASH + node.getName();
                        if (resolver.getResource(variantLookUpPath) == null) {
                            return false;
                        }
                    }

                }

            }

            return true;
        } else {
            return false;
        }
    }


    private String getCurrentAssetPath(String currentPdPath) {
        return currentPdPath.replace(CONTENT_COMMERCE_PRODUCTS_BDB_PRODUCTS, CONTENT_DAM_BDB_PRODUCTS_GLOBAL);
    }

    private String getCurrentTagsPath(String currentPdPath) {
        return currentPdPath.replace(COMMERCE_PRODUCTS, CQ_TAGS);
    }


    private String getCurrentLookUpPath(boolean isVariant, String mat) {

        String pdLookUpBasePath = "";
        if (!isVariant) {
            pdLookUpBasePath = COMMERCE_LOOKUP_BASE_PRODUCT;
        } else {

            pdLookUpBasePath = COMMERCE_LOOKUP_VARIANT;
        }

        return pdLookUpBasePath + mat.substring(0, mat.length() - 3) + LOOKUP_FOLDER_SUFFIX + SLASH + mat;

        // Handle lookuppath creation for alphanumeric products.

    }


    /**
     * Move pd data boolean.
     *
     * @param res              the resource Resolver Object
     * @param materialNumber   the material number
     * @param currentPdPath    the current pd path
     * @param lookUpPath       the look-up path
     * @param currentAssetPath the current asset path
     * @param currentTagsPath  the current tags path
     * @throws RepositoryException  the repository exception
     * @throws PersistenceException the persistence exception
     */
    protected void movePdData(ResourceResolver res,
                              String materialNumber,
                              String currentPdPath,
                              String lookUpPath,
                              String currentAssetPath,
                              String currentTagsPath) throws RepositoryException, PersistenceException {


        String targetPdPath = currentPdPath.replace(materialNumber + UNDERSCOREBASE, "")
                + materialNumber.substring(0, materialNumber.length() - 3)
                + XXX + SLASH
                + materialNumber.substring(0, materialNumber.length() - 2)
                + XX;
        Boolean pdResourceMoved = false;
        if (!isDryRunMode) {
            JcrUtils.getOrCreateByPath(targetPdPath, SLING_FOLDER, Objects.requireNonNull(res.adaptTo(Session.class)));

            try {

                if (res.getResource(targetPdPath + SLASH + materialNumber + UNDERSCOREBASE) == null) {
                    res.copy(currentPdPath, targetPdPath);
                    if (res.getResource(currentAssetPath) != null) {
                        updateAssetsPath(res, targetPdPath, currentAssetPath, materialNumber);
                    }
                    if (res.getResource(currentTagsPath) != null) {
                        updateTagsPath(res, targetPdPath, currentTagsPath, materialNumber);
                    }
                    res.commit();
                    pdResourceMoved = true;
                }

            } catch (PersistenceException e) {
                res.revert();
                throw new PersistenceException(e.getMessage());
            } catch (RuntimeException e) {
                res.revert();
                throw new RuntimeException(e.getMessage());
            }

            if (Boolean.TRUE.equals(pdResourceMoved)) {

                try {
                    updatePdLookUp(res, targetPdPath, materialNumber, lookUpPath);
                    updatePrimarySuperCategory(res, targetPdPath, materialNumber, lookUpPath);
                    res.commit();
                } catch (PersistenceException e) {
                    res.revert();
                    throw new PersistenceException(e.getMessage());
                } catch (RuntimeException e) {
                    res.revert();
                    throw new RuntimeException(e.getMessage());
                }
            }
        } else {
            if (verboseMode) {
                logger.info(String.format("Dry Run Mode Execution :: Migrating Material number :: %s ", materialNumber));
            }
        }

    }


    private void updatePrimarySuperCategory(ResourceResolver resolver,
                                            String updatedPathValue,
                                            String materialNumber,
                                            String lookUpPath) throws RepositoryException {

        String targetPath = updatedPathValue + SLASH + materialNumber + UNDERSCOREBASE;
        if (resolver.getResource(targetPath) != null) {
            Node parentNode = Objects.requireNonNull(resolver.getResource(targetPath)).adaptTo(Node.class);
            if (parentNode != null) {
                if (parentNode.hasNodes()) {
                    NodeIterator iterator = parentNode.getNodes();
                    if (iterator != null) {
                        while (iterator.hasNext()) {
                            Node node = iterator.nextNode();
                            if (node != null) {
                                if (node.getName().matches(SINGLE_PRD_REGEX)) {
                                    updateVariantsPdLookup(resolver, lookUpPath, updatedPathValue, materialNumber, node.getName());
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private void updatePdLookUp(ResourceResolver resolver,
                                String updatedPathValue,
                                String materialNumber,
                                String lookUpPath) {
        Resource resource = resolver.getResource(lookUpPath);
        if (resource != null) {
            ModifiableValueMap map = resource.adaptTo(ModifiableValueMap.class);
            if (map != null) {
                map.put(CATALOG_PATH, updatedPathValue + SLASH + materialNumber + UNDERSCOREBASE);
            }
        }
        updateVariantsPdLookup(resolver, lookUpPath, updatedPathValue, materialNumber, materialNumber);

    }

    private void updateVariantsPdLookup(ResourceResolver resolver, String lookUpPath, String updatedPathValue, String materialNumber, String variantMatNumber) {

        Resource variantResource = resolver.
                getResource(lookUpPath.
                        replace(BASE_PRODUCT, VARIANT).
                        replaceAll(materialNumber.substring(0, materialNumber.length() - 3), variantMatNumber.substring(0, variantMatNumber.length() - 3)));
        if (variantResource != null) {
            ModifiableValueMap variantsMap = variantResource.adaptTo(ModifiableValueMap.class);
            if (variantsMap != null) {
                variantsMap.put(CATALOG_PATH, updatedPathValue + SLASH + materialNumber + UNDERSCOREBASE + SLASH + variantMatNumber);
            }
        }

    }


    private void updateAssetsPath(ResourceResolver resolver,
                                  String updatedPathValue,
                                  String currentAssetPath,
                                  String materialNumber) throws RepositoryException {


        String newAssetPath = updatedPathValue.replace(CONTENT_COMMERCE_PRODUCTS_BDB_PRODUCTS, CONTENT_DAM_BDB_PRODUCTS_GLOBAL) +
                SLASH +
                materialNumber +
                UNDERSCOREBASE;

        JcrUtils.getOrCreateByPath(newAssetPath, SLING_FOLDER, Objects.requireNonNull(resolver.adaptTo(Session.class)));
        if (resolver.getResource(newAssetPath) != null) {
            Objects.requireNonNull(resolver.adaptTo(AssetManager.class)).copyAsset(currentAssetPath, newAssetPath);
        }

    }


    private void updateTagsPath(ResourceResolver resolver,
                                String updatedPathValue,
                                String currentTagsPath,
                                String materialNumber) throws PersistenceException, RepositoryException {


        String newTagsPath = updatedPathValue.replace(COMMERCE_PRODUCTS, CQ_TAGS);

        JcrUtils.getOrCreateByPath(newTagsPath, CQ_TAG, Objects.requireNonNull(resolver.adaptTo(Session.class)));
        if (resolver.getResource(newTagsPath + SLASH + materialNumber + UNDERSCOREBASE) == null) {
            resolver.copy(currentTagsPath, newTagsPath);
        }

    }


    private void addMigrationJobdetails(ResourceResolver resolver, int size, List<String> sublist, int successCount, List<String> failedProducts) throws RepositoryException {

        String pattern = "MM-dd-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String date = dateFormat.format(new Date());
        DateFormat format = new SimpleDateFormat("hh:mm:ss");
        String time = format.format(new Date());

        String path = MIGRATION_LOGGING_BASE_PATH + SLASH + MIGRATION + SLASH + date + SLASH + time.replaceAll(":", "-");
        JcrUtils.getOrCreateByPath(path, SLING_FOLDER, Objects.requireNonNull(resolver.adaptTo(Session.class)));
        Resource resource = resolver.getResource(path);
        if (resource != null) {
            ModifiableValueMap props = resource.adaptTo(ModifiableValueMap.class);
            if (props != null) {
             /*   if (matNum.matches(SINGLE_PRD_REGEX)) {
                    props.put(MATERIAL_NUMBER, matNum);

                } else {
                    props.put(MATERIAL_NUMBER, Arrays.toString(migratedProducts.toArray()));
                }*/

                props.put(EXECUTION_MODE, mode.name());
                props.put(VERBOSE, verboseMode);
                props.put(TOTAL_MIGRATED, successCount);
                props.put(TOTAL, size);
                if (failedProducts != null) {
                    props.put(FAILED_PRODUCTS, Arrays.toString(failedProducts.toArray()));
                    props.put(TOTAL_FAILED_PRODUCTS, failedProducts.size());
                }
                if (alreadyMigratedProducts != null) {
                    props.put(ALREADY_MIGRATED_PRODUCTS, Arrays.toString(alreadyMigratedProducts.toArray()));
                    props.put(TOTAL_ALREADY_MIGRATED_PRODUCTS, alreadyMigratedCount);

                }
            }
            JcrUtils.setLastModified(Objects.requireNonNull(resource.adaptTo(Node.class)), Calendar.getInstance());
        }
        logRecentRun(resolver, path);

    }


    private void logRecentRun(ResourceResolver resolver, String migrationLastRunPath) {
        String path = MIGRATION_LOGGING_BASE_PATH + SLASH + MIGRATION + SLASH + LATEST;

        try {

            if (resolver.getResource(path) != null) {
                resolver.delete(Objects.requireNonNull(resolver.getResource(path)));
            }
            JcrUtils.getOrCreateByPath(path, SLING_FOLDER, Objects.requireNonNull(resolver.adaptTo(Session.class)));
            resolver.copy(migrationLastRunPath, path);

            resolver.commit();
        } catch (Exception e) {
            resolver.revert();

        }
    }


    /**
     * Create a Tracking data Folder structure under /Var
     * to keep track of the progress of the current state of the product data migration per product.
     * For Each product base folder, a product folder containing three sub folders - commerce, assets and tags will be created
     * to keep track of all three types of PD.
     */
    private void addExecutionMetadata(ResourceResolver res,
                                      String path,
                                      String currentPdPath,
                                      boolean isFailureProduct, List<String> failureReason, String materialNumber) throws RepositoryException, PersistenceException {

        String commerceNodePath = path + SLASH + PD_COMMERCE;
        String targetPdPath = currentPdPath.replace(materialNumber + UNDERSCOREBASE, "")
                + materialNumber.substring(0, materialNumber.length() - 3)
                + XXX + SLASH
                + materialNumber.substring(0, materialNumber.length() - 2)
                + XX + SLASH + materialNumber + UNDERSCOREBASE;
        if (res.getResource(commerceNodePath) == null) {
            JcrUtils.getOrCreateByPath(commerceNodePath, SLING_FOLDER, Objects.requireNonNull(res.adaptTo(Session.class)));
        }
        Resource commerceResource = res.getResource(commerceNodePath);
        if (commerceResource != null) {
            ModifiableValueMap commerceProps = commerceResource.adaptTo(ModifiableValueMap.class);
            if (commerceProps != null) {
                commerceProps.put(SOURCE, currentPdPath);
                commerceProps.put(TARGET, targetPdPath);
                commerceProps.put(ACTIVATED, false);
                commerceProps.put(DEACTIVATED, false);
                commerceProps.put(INDEXED, false);
                commerceProps.put(MIGRATION_FAILED, isFailureProduct);
                if (!failureReason.isEmpty()) {
                    commerceProps.put(MIGRATION_FAILURE_AND_WARNINGS, Arrays.toString(failureReason.toArray()));
                }
                JcrUtils.setLastModified(Objects.requireNonNull(commerceResource.adaptTo(Node.class)), Calendar.getInstance());
            }
        }
        // Start creating/updating asset node metadata

        String assetsNodePath = path + SLASH + ASSETS;
        if (res.getResource(assetsNodePath) == null) {
            JcrUtils.getOrCreateByPath(assetsNodePath, SLING_FOLDER, Objects.requireNonNull(res.adaptTo(Session.class)));
        }

        Resource assetsResource = res.getResource(assetsNodePath);

        if (assetsResource != null) {
            ModifiableValueMap assetsProps = assetsResource.adaptTo(ModifiableValueMap.class);
            if (assetsProps != null) {
                assetsProps.put(SOURCE, currentPdPath.replace(COMMERCE_PRODUCTS_BDB_PRODUCTS, DAM_BDB_CONTEXT_PATH));
                assetsProps.put(TARGET, targetPdPath.replace(COMMERCE_PRODUCTS_BDB_PRODUCTS, DAM_BDB_CONTEXT_PATH));
                assetsProps.put(ACTIVATED, false);
                assetsProps.put(DEACTIVATED, false);
                assetsProps.put(INDEXED, false);

                JcrUtils.setLastModified(Objects.requireNonNull(assetsResource.adaptTo(Node.class)), Calendar.getInstance());
            }
        }
        // Start creating/updating tags node metadata

        String tagsNodePath = path + SLASH + TAGS;
        if (res.getResource(tagsNodePath) == null) {
            JcrUtils.getOrCreateByPath(tagsNodePath, SLING_FOLDER, Objects.requireNonNull(res.adaptTo(Session.class)));
        }

        Resource taxonomyResource = res.getResource(tagsNodePath);
        if (taxonomyResource != null) {
            ModifiableValueMap taxonomyProps = taxonomyResource.adaptTo(ModifiableValueMap.class);

            if (taxonomyProps != null) {
                taxonomyProps.put(SOURCE, currentPdPath.replace(COMMERCE_PRODUCTS, CQ_TAGS));
                taxonomyProps.put(TARGET, targetPdPath.replace(COMMERCE_PRODUCTS, CQ_TAGS));
                taxonomyProps.put(ACTIVATED, false);
                taxonomyProps.put(DEACTIVATED, false);
                taxonomyProps.put(INDEXED, false);
                JcrUtils.setLastModified(Objects.requireNonNull(taxonomyResource.adaptTo(Node.class)), Calendar.getInstance());
                res.commit();
            }
        }
    }


    /**
     * Start pd migration process and delegate the execution.
     *
     * @param manager the manager
     */
    protected void startPDMigrationProcess(ActionManager manager) {
        manager.deferredWithResolver(this::migratePDData);
    }

    /**
     * Start pd migration process dry run mode.
     *
     * @param manager the manager
     */
    protected void startPDMigrationProcessDryRunMode(ActionManager manager) {
        manager.deferredWithResolver(this::migratePDData);

    }


    /**
     * Initializing class objects with the input lookup product data.
     *
     * @param resourceResolver the resource resolver
     */
    protected void migratePDData(ResourceResolver resourceResolver) throws RepositoryException {
        logger.info("Migrate PD Data");
        readPDLookUpData(resourceResolver);
    }


    private void readPDLookUpData(ResourceResolver resolver) throws RepositoryException {
        Resource baseProductLookUpRes = resolver.getResource(COMMERCE_LOOKUP_BASE_PRODUCT);
        if (baseProductLookUpRes != null) {
            Node basePDLookUpNode = baseProductLookUpRes.adaptTo(Node.class);
            if (basePDLookUpNode != null) {
                if (basePDLookUpNode.hasNodes()) {
                    NodeIterator nodeIter = basePDLookUpNode.getNodes();
                    while (nodeIter.hasNext()) {
                        Node node = nodeIter.nextNode();
                        lookUpTopLevelFolders.add(node.getName().substring(0, 3));
                    }
                }
            }
        }
    }


    @Override
    public void storeReport(ProcessInstance instance, ResourceResolver rr) throws RepositoryException, PersistenceException {
        logger.info("Inside storeReport Method");

        report.setName(REPORT_NAME);
        report.setRows(reportRows, ReportColumns.class);
        report.persist(rr, instance.getPath() + "/jcr:content/report");
    }

    @Override
    public void init() throws RepositoryException {
        logger.info("Inside init Method");

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

    /**
     * The enum Report columns.
     */
    public enum ReportColumns {
        /**
         * Serial report columns.
         */
        SERIAL,
        /**
         * Matnum report columns.
         */
        MATNUM,
        /**
         * Currentcategory report columns.
         */
        CURRENTCATEGORY,
        /**
         * Newcategory report columns.
         */
        NEWCATEGORY
    }

}
