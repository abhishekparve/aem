package com.bdb.aem.core.pdmigration;

import com.adobe.acs.commons.fam.ActionManager;
import com.adobe.acs.commons.mcp.ProcessDefinition;
import com.adobe.acs.commons.mcp.ProcessInstance;
import com.adobe.acs.commons.mcp.form.CheckboxComponent;
import com.adobe.acs.commons.mcp.form.FormField;
import com.adobe.acs.commons.mcp.form.PathfieldComponent;
import com.adobe.acs.commons.mcp.form.RadioComponent;
import com.adobe.acs.commons.mcp.model.ManagedProcess;
import com.adobe.acs.commons.mcp.util.DeserializeException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;
import com.google.gson.JsonObject;
import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.resource.*;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PdConsistencyCheckScript extends ProcessDefinition {

    private static final Logger logger = LoggerFactory.getLogger(PdConsistencyCheckScript.class);

    /**
     * String Constants used in the scripts
     */
    private static final String PD_CONSISTENCY_MCP = "PD Consistency Check Process";
    private static final String UNDERSCORE_BASE = "_base";
    private static final String COMMERCE_PATH = "/content/commerce/products/bdb/products/";
    private static final String DAM_PRODUCT_PATH = "/content/dam/bdb/products/global/";
    private static final String CQ_PRODUCT_PATH = "/content/cq:tags/bdb/products/";
    private static final String VARIANT_LOOKUP_BASE_PATH = "/content/commerce/lookup/variant/";
    private static final String CONSISTENCY_LOGGING_BASE_PATH = "/var/bdb";
    private static final String IS_BASE_PRODUCT = "isBaseProduct";
    private static final String HP_SUPER_CATEGORY = "hpSuperCategory";
    private static final String PRODUCT_APPROVAL_STATUS = "productApprovalStatus";
    private static final String IS_VARIANT = "isVariant";
    private static final String PDF_DOC_PART_ID = "pdfDocPartID";
    private static final String PDF_DOC_REGION = "pdfDocRegion";
    private static final String PDF_DOC_SKU = "pdfDocSku";
    private static final String PDF_DOC_TYPE = "pdfDocType";
    private static final String PDP_TEMPLATE = "pdpTemplate";
    private static final String SAP_APPROVAL_STATUS = "approvalStatus";
    private static final String SAP_CODE = "code";
    private static final String SAP_WEB_AVAILABLE = "globalWebAvailable";
    private static final String SAP_REGION = "sapRegionalDetails";
    private static final String BASE_SKU = "baseMaterialNumber";
    private static final String VARIANT_SKU = "variantMaterialNumber";
    private static final String ERROR_NA_COMM = "[E][C] Not Available";
    private static final String ERROR_NA_ASSET = "[E][A] Not available";
    private static final String WAR_INVALID = "[W][C] Invalid";
    private static final String ERROR_INVALID = "[E][C] Invalid";
    private static final String VALID = "Valid";
    private static final String LOOKUP_PATH = "pathInLookup";
    private static final String SAP_CC = "sap-cc";
    private static final String FAILED_PRODUCTS = "failedProduct";
    private static final String SOURCE_PATH = "sourcePath";
    private static final String TARGET_PATH = "targetPath";

    /**
     * Excel Constants
     */
    private static final String SINGLE_PRD_REGEX = "\\d{6}$";
    private static final String PRD_RANGE_REGEX = "^\\d{6}:\\d{6}$";
    private static final String ALL = "all";
    private static final String PRD_COMMA_SEPARATED_LIST = "^(\\d{6},\\s*)*\\d{6}$";

    /**
     * Utility Service files
     */
    private final BDBApiEndpointService bdbApiEndpointService;
    private final SolrSearchService solrSearchService;
    private final BDBSearchEndpointService solrConfig;

    /**
     * Variable to map column name and column title value
     */
    private final HashMap<String, String> colNameBasedOnConfig = new HashMap<>();

    /**
     * Variable to store job execution details
     */
    private final HashMap<List<String>, HashMap<String, String>> executionDetails = new HashMap<>();

    ManagedProcess processInfo;

    /**
     * Variable to store excel configuration
     */
    private ArrayList<String> excelConfiguration = new ArrayList<>();
    private JsonObject excelObject = null;
    private JsonObject data;
    private int failedProducts = 0;
    private String newPdCategory = null;
    private Session session;
    private List<String> sublist = new ArrayList<>();

    @FormField(
            name = "Mode",
            component = RadioComponent.EnumerationSelector.class,
            required = true,
            description = "MCP Execution Mode",
            options = {"horizontal", "default=DRY_RUN_MODE"}
    )
    private Mode mode;
    @FormField(name = "Variant Material Number", required = true)
    private String variantMaterialNumber;
    @FormField(name = "HP CSV File",
            component = PathfieldComponent.AssetSelectComponent.class)
    private String hpFileName;
    @FormField(name = "Excel Config")
    private String excelConfig;
    @FormField(name = "PD Category", component = PathfieldComponent.NodeSelectComponent.class, required = true)
    private String pdCategory;
    @FormField(name = "Fix Incorrect", component = CheckboxComponent.class, options = {"horizontal", "default=false"})
    private boolean fixIncorrect;
    @FormField(name = "Verbose Mode", component = CheckboxComponent.class, options = {"horizontal", "default=false"})
    private boolean verboseMode;

    /**
     * Constructor to get all services in mcp
     */
    public PdConsistencyCheckScript(BDBApiEndpointService bdbApiEndpointService, SolrSearchService solrSearchService, BDBSearchEndpointService solrConfig) {
        this.bdbApiEndpointService = bdbApiEndpointService;
        this.solrConfig = solrConfig;
        this.solrSearchService = solrSearchService;
    }

    @Override
    public void init() {
        logger.info("Inside init Method");
    }

    @Override
    public void buildProcess(ProcessInstance instance, ResourceResolver resourceResolver) throws LoginException {
        processInfo = instance.getInfo();
        instance.getInfo().setDescription("Running PD Consistency Check Script");
        switch (mode) {
            case LIVE_MODE:
                // There is a provision to add retry policy as well if needed.
                instance.defineCriticalAction(PD_CONSISTENCY_MCP, resourceResolver, this::startPDConsistencyScriptProcess);
                instance.defineCriticalAction(PD_CONSISTENCY_MCP, resourceResolver, this::startValidatingRules);
                break;
            case DRY_RUN_MODE:
                instance.defineCriticalAction(PD_CONSISTENCY_MCP, resourceResolver, this::startPDConsistencyScriptProcessDryRunMode);
                instance.defineCriticalAction(PD_CONSISTENCY_MCP, resourceResolver, this::startValidatingRulesDryRunMode);
                break;
        }
    }

    private void startValidatingRulesDryRunMode(ActionManager actionManager) {
        actionManager.deferredWithResolver(this::readingInputAndValidatingRules);
    }

    private void startValidatingRules(ActionManager actionManager) {
        actionManager.deferredWithResolver(this::readingInputAndValidatingRules);
    }

    /**
     * function to read inputs and start rules validation accordingly
     *
     * @param resourceResolver
     */
    private void readingInputAndValidatingRules(ResourceResolver resourceResolver) {
        Set<String> keySet = null;
        Iterator<String> keys = null;
        try {
            session = resourceResolver.adaptTo(Session.class);
            if (null != excelObject) {
                keySet = excelObject.keySet();
                keys = keySet.iterator();
            }
            if (variantMaterialNumber.equalsIgnoreCase(ALL)) {
                if (null != excelObject) {
                    // Validating for all products present in Excel
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (key.isEmpty()) continue;
                        sublist.add(key);
                        data = (JsonObject) excelObject.get(key);
                        startValidation(resourceResolver, key, data);
                    }
                } else {
                    logger.info("Excel/Config is not provided, Considering PD category location as a reference for validation check !!");
                    Resource res = resourceResolver.getResource(pdCategory);
                    if (null != res) {
                        Iterator<Resource> nodes = res.listChildren();
                        while (nodes.hasNext()) {
                            Resource node = nodes.next();
                            if ((node.getValueMap().containsKey(IS_BASE_PRODUCT))) {
                                String matNumber = node.getName().split("_")[0];
                                sublist.add(matNumber);
                                startValidation(resourceResolver, matNumber, null);
                            }
                        }
                    } else {
                        HashMap<String, String> updateCheckStatus = new HashMap<>();
                        updateCheckStatus.put("pdCategory", pdCategory);
                        updateCheckStatus.put(FAILED_PRODUCTS, "All. Rules can't run. PD Category doesn't exist in AEM");
                        addConsistencyJobDetails(resourceResolver, updateCheckStatus, getDateTime());
                        return;
                    }
                }
            } else if (variantMaterialNumber.matches(SINGLE_PRD_REGEX)) {
                sublist.add(variantMaterialNumber);
                singleProductValidation(variantMaterialNumber, resourceResolver);
            } else if (variantMaterialNumber.matches(PRD_COMMA_SEPARATED_LIST)) {
                sublist = new ArrayList<>(Arrays.asList(variantMaterialNumber.split("\\s*,\\s*")));
                for (String mat : sublist) {
                    singleProductValidation(mat, resourceResolver);
                }
            } else if (variantMaterialNumber.matches(PRD_RANGE_REGEX)) {
                List<String> list = new ArrayList<>();
                String start_mat_num = variantMaterialNumber.split(":")[0];
                String end_mat_num = variantMaterialNumber.split(":")[1];
                if ((Integer.parseInt(start_mat_num) > Integer.parseInt(end_mat_num)))
                    throw new IllegalArgumentException(String.format("Range Start: %s1 is greater than Range End : %s2", start_mat_num, end_mat_num));
                else if (null != excelObject) {
                    if (start_mat_num.equalsIgnoreCase(end_mat_num)) {
                        list.add(start_mat_num);
                    } else {
                        list = new ArrayList<>(keySet);
                        Collections.sort(list);
                    }
                    /**
                     * Assumption: Start and end material Number should present in Excel
                     * If the case is otherwise, the logic needs to be modified.
                     */
                    sublist = list.subList(list.indexOf(start_mat_num), list.indexOf(end_mat_num) + 1);
                    for (String matNum : sublist) {
                        data = (JsonObject) excelObject.get(matNum);
                        startValidation(resourceResolver, matNum, data);
                    }
                } else {
                    if (start_mat_num.equalsIgnoreCase(end_mat_num)) {
                        sublist.add(start_mat_num);
                        startValidation(resourceResolver, start_mat_num, null);
                    } else {
                        /** for now, Iterating all resources and filtering out based on range */
                        Resource categoryResource = resourceResolver.getResource(pdCategory);
                        if (null != categoryResource) {
                            Iterator<Resource> nodes = categoryResource.listChildren();
                            while (nodes.hasNext()) {
                                Resource node = nodes.next();
                                if (node.getValueMap().containsKey(IS_BASE_PRODUCT)) {
                                    String matNumber = node.getName().split("_")[0];
                                    if (matNumber.compareToIgnoreCase(start_mat_num) >= 0 && matNumber.compareToIgnoreCase(end_mat_num) <= 0) {
                                        sublist.add(matNumber);
                                        startValidation(resourceResolver, matNumber, null);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            List<String> dateTime = getDateTime();
            addExecutionMetadata(resourceResolver, dateTime);
            addConsistencyJobDetails(resourceResolver, null, dateTime);
        } catch (RepositoryException e) {
            logger.error("Repository Exception", e);
        } finally {
            if (session != null && session.isLive()) {
                session.logout();
            }
        }
    }

    /**
     * Validation for a material Number
     */
    private void singleProductValidation(String variantMaterialNumber, ResourceResolver resourceResolver) {
        if (null != excelObject) {
            data = (JsonObject) excelObject.get(variantMaterialNumber);
            startValidation(resourceResolver, variantMaterialNumber, data);
        } else
            startValidation(resourceResolver, variantMaterialNumber, null);
    }

    /**
     * function to validate pdCategory based on Excel Config
     *
     * @param data
     * @return
     */
    private String validateCategoryPathBasedOnExcelConfig(JsonObject data) {
        // If Excel is not present then we need to return the pdCategory itself.
        if (null == data) return pdCategory;
        StringBuilder pathBuilding = new StringBuilder();
        // looping through the Excel category config (Ex- D,E,F ..) and creating a reference path.
        // 0 and 1 index of Excel Configuration list contains sheetName and material Number column respectively.
        for (int i = 2; i < excelConfiguration.size(); i++) {
            String getNewCategoryFromExcel = colNameBasedOnConfig.get(excelConfiguration.get(i));
            String migratedCategory = data.get(getNewCategoryFromExcel).toString();
            migratedCategory = migratedCategory.replace("\"", "").trim().replace(",", "/").replace(" ", "-").replaceAll("[()]", "").toLowerCase();
            pathBuilding.append(migratedCategory + CommonConstants.SINGLE_SLASH);
        }
        String pathBasedOnConfig = pathBuilding.toString();
        // Extracting last category from provided pdCategory.
        String lastCategory = pdCategory.substring(pdCategory.lastIndexOf("/") + 1);
        // Comparing given pdCategory with the path generated based on config
        if (!pathBasedOnConfig.contains(lastCategory)) return null;
        // If category structure matches then replacing the path and returning the new category.
        newPdCategory = pdCategory.replace(lastCategory, pathBasedOnConfig);
        newPdCategory = newPdCategory.substring(0, newPdCategory.lastIndexOf("/"));
        return newPdCategory;
    }

    /**
     * Add Job details into node structure for each Material Number
     *
     * @param resolver
     * @throws RepositoryException
     */
    private void addConsistencyJobDetails(ResourceResolver resolver, HashMap<String, String> updateCheckStatus, List<String> dateTime) throws RepositoryException {
        String path = CONSISTENCY_LOGGING_BASE_PATH + pdCategory;
        // Creating path under /var/bdb/...
        path = path + CommonConstants.SINGLE_SLASH + "consistency" + CommonConstants.SINGLE_SLASH + dateTime.get(0) + CommonConstants.SINGLE_SLASH + dateTime.get(1).replace(":", "-");
        JcrUtils.getOrCreateByPath(path, "sling:Folder", session);
        Resource resource = resolver.getResource(path);
        ModifiableValueMap props = resource.adaptTo(ModifiableValueMap.class);
        if (variantMaterialNumber.matches("^\\d{6}$")) props.put(VARIANT_SKU, Arrays.toString(new
                String[]{variantMaterialNumber}));
        else props.put(VARIANT_SKU, Arrays.toString(sublist.toArray()));
        // Storing Job details
        props.put("pdCategory", pdCategory);
        props.put("Excel Path", hpFileName);
        props.put("Excel config", excelConfig);
        props.put("Mode", mode.name());
        props.put("Fix Incorrect", fixIncorrect);
        props.put("Verbose", verboseMode);
        props.put("Total Products", sublist.size());
        if (null != updateCheckStatus && updateCheckStatus.size() > 0) {
            for (Map.Entry<String, String> entry : updateCheckStatus.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                props.put(key, value);
            }
        }
        JcrUtils.setLastModified(resource.adaptTo(Node.class), Calendar.getInstance());
        session.save();
    }

    /**
     * function to get current date and time
     *
     * @return
     */
    private List<String> getDateTime() {
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String date = dateFormat.format(new Date());
        DateFormat format = new SimpleDateFormat("hh:mm:ss");
        String time = format.format(new Date());
        // 0 index stores date and 1 index stores time
        return Arrays.asList(date, time);
    }

    /**
     * Starting function for validate all rules
     *
     * @param resourceResolver
     * @param materialNumber
     * @param data
     */
    private void startValidation(ResourceResolver resourceResolver, String materialNumber, JsonObject data) {
        try {
            // refreshing session for every new validation
            session.refresh(false);
            HashMap<String, String> updateCheckStatus = new HashMap<>();
            String productCatalogPath = getPathFromLookup(materialNumber);
            boolean isCatalogPathCorrect = false;
            // Rules executes only when material have lookup path available in variant as well as in base
            newPdCategory = validateCategoryPathBasedOnExcelConfig(data);
            if (null != productCatalogPath) {
                if (null != data && null == newPdCategory) {
                    failedProducts++;
                    updateCheckStatus.put("referencePath", ERROR_INVALID);
                    updateCheckStatus.put("referencePathDetails", productCatalogPath);
                    addJobDetails(materialNumber, updateCheckStatus);
                    return;
                }
                if (productCatalogPath.substring(0, productCatalogPath.lastIndexOf("/")).equalsIgnoreCase(newPdCategory)) {
                    isCatalogPathCorrect = true;
                    updateCheckStatus.put(LOOKUP_PATH, VALID);
                    if (verboseMode) logger.debug("Product category location is valid !!! {}", productCatalogPath);

                    // function call for validating each rules
                    validateCommerceRules(resourceResolver, materialNumber, productCatalogPath, updateCheckStatus);
                    validateAssetRules(resourceResolver, materialNumber, productCatalogPath, updateCheckStatus);
                    validateTagRules(resourceResolver, productCatalogPath, updateCheckStatus);
                }
                updateCheckStatus.put("pathInLookupDetails", productCatalogPath);
            } else {
                updateCheckStatus.put("pathInLookupDetails", "N/A, Node or property is missing in lookup.");
            }
            if (!isCatalogPathCorrect) {
                failedProducts++;
                updateCheckStatus.put(LOOKUP_PATH, ERROR_INVALID);
                logger.error("Invalid lookup path for {}", materialNumber);
            }
            // Adding Job details
            addJobDetails(materialNumber, updateCheckStatus);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    /**
     * function to add job details
     *
     * @param materialNumber
     * @param updateCheckStatus
     */
    private void addJobDetails(String materialNumber, HashMap<String, String> updateCheckStatus) {
        String path = CONSISTENCY_LOGGING_BASE_PATH + pdCategory;
        // logging src and target path for Excel inputs
        if (null != data && null != newPdCategory) {
            updateCheckStatus.put(SOURCE_PATH, pdCategory);
            updateCheckStatus.put(TARGET_PATH, newPdCategory);
        }
        List<String> executedJobDetailList = Arrays.asList(materialNumber, path, String.valueOf(failedProducts));
        executionDetails.put(executedJobDetailList, updateCheckStatus);
        // Resetting failed products to zero after each material Number
        failedProducts = 0;
    }


    /**
     * function to validate tag Rules
     *
     * @param resourceResolver
     * @param catalogPath
     * @param updateCheckStatus
     */
    private void validateTagRules(ResourceResolver resourceResolver, String catalogPath, HashMap<String, String> updateCheckStatus) {
        logger.info("Entry process of validating cq tag rules");
        long startTime = System.currentTimeMillis();
        logger.info("Validating CQ tag Rules Start time - {}", startTime);
        String cqPath = catalogPath.replace(COMMERCE_PATH, CQ_PRODUCT_PATH);
        Resource productTagNode = resourceResolver.getResource(cqPath);
        if (null != productTagNode) {
            updateCheckStatus.put("productCqTagNode", VALID);
        } else {
            failedProducts++;
            updateCheckStatus.put("productCqTagNode", "[E][T] Invalid. Doesn't exist");
        }
        updateCheckStatus.put("productCqTagNodePathDetails", cqPath);
        long endTime = System.currentTimeMillis();
        logger.info("Validating Cq tag Rules end time - {}", endTime);
        logger.info("Total time taken for validating cq tag rules - {}", endTime - startTime);
        logger.info("Exit process of validating cq tag rules");
    }

    /**
     * function to add job metadata into nodes (/var/bdb/...) for each product
     *
     * @param resolver
     */
    private void addExecutionMetadata(ResourceResolver resolver, List<String> dateTime) {
        for (Map.Entry<List<String>, HashMap<String, String>> entry : executionDetails.entrySet()) {
            List<String> list = entry.getKey();
            HashMap<String, String> updateCheckStatus = entry.getValue();
            String materialNumber = list.get(0);
            String path = list.get(1);
            path = path + CommonConstants.SINGLE_SLASH + "consistency" + CommonConstants.SINGLE_SLASH +
                    "products" + CommonConstants.SINGLE_SLASH + dateTime.get(0) + CommonConstants.SINGLE_SLASH + dateTime.get(1).replace(":", "-") +
                    CommonConstants.SINGLE_SLASH + materialNumber.substring(0, materialNumber.length() - 3) + "xxx" + CommonConstants.SINGLE_SLASH +
                    materialNumber.substring(0, materialNumber.length() - 2) + "xx" + CommonConstants.SINGLE_SLASH + materialNumber;
            try {
                JcrUtils.getOrCreateByPath(path, "sling:Folder", session);
                Resource res = resolver.getResource(path);
                ModifiableValueMap props = res.adaptTo(ModifiableValueMap.class);
                if (Integer.parseInt(list.get(2)) > 0) 
                    props.put("consistencyValidationCheck", "Invalid");
                else 
                    props.put("consistencyValidationCheck", VALID);
                for (Map.Entry<String, String> map : updateCheckStatus.entrySet()) {
                    String key = map.getKey();
                    String value = map.getValue();
                    props.put(key, value);
                }
                session.save();
            } catch (RepositoryException e) {
                logger.error("Repository Exception", e);
            }
        }
    }

    /**
     * function to get base node path for variant/base
     *
     * @param materialNumber
     */
    private String getPathFromLookup(String materialNumber) throws RepositoryException {
        String variantPath = VARIANT_LOOKUP_BASE_PATH + materialNumber.substring(0, 3) + "f";
        Node variantNode = JcrUtils.getNodeIfExists(variantPath + CommonConstants.SINGLE_SLASH + materialNumber, session);
        if (null != variantNode) {
            if (variantNode.hasProperty("catalogPath")) {
                variantPath = variantNode.getProperty("catalogPath").getString();
                variantPath = variantPath.substring(0, variantPath.lastIndexOf("/"));
            }
        } else {
            logger.error("Variant Product Node is missing in lookup for {}", materialNumber);
            return null;
        }
        return variantPath;
    }

    /**
     * Validate asset rules on materialNumber
     */
    private void validateAssetRules(ResourceResolver resourceResolver, String materialNumber, String catalogPath, HashMap<String, String> updateCheckStatus) {
        logger.info("Entry process of validating asset rules");
        long startTime = System.currentTimeMillis();
        logger.info("Validating Asset Rules Start time - {}", startTime);
        String damPath = catalogPath.replace(COMMERCE_PATH, DAM_PRODUCT_PATH);
        Resource productAssetNode = resourceResolver.getResource(damPath);
        if (null != productAssetNode) {
            updateCheckStatus.put("damPath", VALID);
            validatingAssetProperties(productAssetNode, materialNumber, updateCheckStatus);
        } else {
            failedProducts++;
            updateCheckStatus.put("damPath", "[E][A] Invalid. Doesn't exist");
        }
        updateCheckStatus.put("damPathDetails", damPath);
        long endTime = System.currentTimeMillis();
        logger.info("Validating Asset Rules end time - {}", endTime);
        logger.info("Total time taken for validating asset rules - {}", endTime - startTime);
        logger.info("Exit process of validating asset rules");
    }

    /**
     * Validate commerce rules on materialNumber
     */
    private void validateCommerceRules(ResourceResolver resourceResolver, String materialNumber, String catalogPath, HashMap<String, String> updateCheckStatus) {
        logger.info("Entry process of validating commerce rules");
        long startTime = System.currentTimeMillis();
        logger.info("Validating Commerce Rules Start time - {}", startTime);
        Resource productBaseNode = resourceResolver.getResource(catalogPath);
        if (null != productBaseNode) {
            updateCheckStatus.put("baseNodePath", VALID);
            validatingBaseProductProperties(productBaseNode, catalogPath, materialNumber, updateCheckStatus);
        } else {
            failedProducts++;
            updateCheckStatus.put("baseNodePath", "[E][C] Invalid. Doesn't exist");
        }
        Resource productVariantNode = resourceResolver.getResource(catalogPath + CommonConstants.SINGLE_SLASH + materialNumber);
        if (null != productVariantNode) {
            updateCheckStatus.put("variantNodePath", VALID);
            validatingVariantProductProperties(productVariantNode, materialNumber, resourceResolver, updateCheckStatus);
        } else {
            failedProducts++;
            updateCheckStatus.put("variantNodePath", "[E][C] Invalid. Doesn't exist");
        }
        updateCheckStatus.put("baseNodePathDetails", catalogPath);
        updateCheckStatus.put("variantNodePathDetails", catalogPath + CommonConstants.SINGLE_SLASH + materialNumber);
        long endTime = System.currentTimeMillis();
        logger.info("validating Commerce Rules end time - {}", endTime);
        logger.info("Total time taken for validating commerce rules - {}", endTime - startTime);
        logger.info("Exit process of validating commerce rules");
    }

    /**
     * Validate variant product nodes based on commerce consistency rules
     */
    private void validatingVariantProductProperties(Resource variantNode, String mat_num, ResourceResolver resolver, HashMap<String, String> updateCheckStatus) {
        logger.info("Entry Check Rules for variant product {}", variantNode.getName());
        String variantProduct = variantNode.getValueMap().get(IS_VARIANT).toString();
        if (null != variantProduct) {
            if (variantProduct.replace("\"", "").trim().equalsIgnoreCase("true")) {
                if (verboseMode) logger.debug("Variant Product Status is true for product {}", mat_num);
                updateCheckStatus.put(IS_VARIANT, VALID);
            } else {
                updateCheckStatus.put(IS_VARIANT, WAR_INVALID);
            }
        } else {
            failedProducts++;
            updateCheckStatus.put(IS_VARIANT, ERROR_NA_COMM);
        }

        ValueMap hpNode = variantNode.getChild("hp") == null ? null : variantNode.getChild("hp").adaptTo(ValueMap.class);
        ValueMap sapNode = variantNode.getChild(SAP_CC) == null ? null : variantNode.getChild(SAP_CC).adaptTo(ValueMap.class);
        if (null != hpNode) {
            // Variant HP data validation
            if (null != hpNode.get(BASE_SKU)) {
                if (hpNode.get(BASE_SKU).toString().equalsIgnoreCase(variantNode.getParent().getName())) {
                    if (verboseMode)
                        logger.debug("Variant base is equal to product base !!");
                    updateCheckStatus.put(BASE_SKU, VALID);
                } else {
                    updateCheckStatus.put(BASE_SKU, WAR_INVALID);
                }
            } else {
                failedProducts++;
                updateCheckStatus.put(BASE_SKU, ERROR_NA_COMM);
            }

            if (null != hpNode.get("materialNumber")) {
                if (hpNode.get("materialNumber").toString().equalsIgnoreCase(variantNode.getName().toLowerCase())) {
                    if (verboseMode) logger.debug("Variant material Number matched with variant node Name");
                    updateCheckStatus.put(VARIANT_SKU, VALID);
                } else {
                    updateCheckStatus.put(VARIANT_SKU, WAR_INVALID);
                }
            } else {
                failedProducts++;
                updateCheckStatus.put(VARIANT_SKU, ERROR_NA_COMM);
            }
        } else {
            failedProducts++;
            updateCheckStatus.put("variantHPNode", "[E][C] Invalid. Variant HP Node is missing");
        }
        if (null != sapNode) {
            // SAP Data Validation
            if (null != sapNode.get(SAP_WEB_AVAILABLE)) {
                if ((boolean) sapNode.get(SAP_WEB_AVAILABLE)) {
                    if (verboseMode) logger.debug("Global web Available is true for !! {}", mat_num);
                    updateCheckStatus.put(SAP_WEB_AVAILABLE, VALID);
                } else {
                    updateCheckStatus.put(SAP_WEB_AVAILABLE, WAR_INVALID);
                }
            } else {
                failedProducts++;
                updateCheckStatus.put(SAP_WEB_AVAILABLE, ERROR_NA_COMM);
            }
            if (null != sapNode.get(SAP_APPROVAL_STATUS)) {
                if (sapNode.get(SAP_APPROVAL_STATUS).toString().replace("\"", "").trim().equalsIgnoreCase("approved")) {
                    if (verboseMode) logger.debug("SAP approval status is approved !! for {}", mat_num);
                    updateCheckStatus.put(SAP_APPROVAL_STATUS, "Approved");
                } else {
                    updateCheckStatus.put(SAP_APPROVAL_STATUS, "[W][C] Not Approved");
                }
            } else {
                failedProducts++;
                updateCheckStatus.put(SAP_APPROVAL_STATUS, ERROR_NA_COMM);
            }

            if (null != sapNode.get(SAP_CODE)) {
                if (sapNode.get(SAP_CODE).toString().equalsIgnoreCase(variantNode.getName())) {
                    if (verboseMode) logger.debug("Code name in sap equals to Product Name !! {}", mat_num);
                    updateCheckStatus.put(SAP_CODE, "Valid SAP Code");
                } else {
                    updateCheckStatus.put(SAP_CODE, "[W][C] Invalid SAP Code");
                }
            } else {
                failedProducts++;
                updateCheckStatus.put(SAP_CODE, "[E][C] SAP Code Not Available");
            }

            // SAP Region data validation
            ValueMap regionNode = variantNode.getChild(SAP_CC).getChild("region-details") == null ? null : variantNode.getChild(SAP_CC).getChild("region-details").adaptTo(ValueMap.class);
            if (null != regionNode) {
                try {
                    List<String> countries = CommonHelper.getAllCountries(resolver, bdbApiEndpointService);
                    List<String> missingRegionDetails = new ArrayList<>();
                    for (String country : countries) {
                        if (null != regionNode.get(country)) {
                            if (!regionNode.get(country).toString().isEmpty()) {
                                if (verboseMode)
                                    logger.debug("SAP-CC regional details data is available for product: {}", mat_num);
                            } else {
                                missingRegionDetails.add(country);
                            }
                        } else {
                            missingRegionDetails.add(country);
                        }
                    }
                    if (missingRegionDetails.isEmpty())
                        updateCheckStatus.put(SAP_REGION, "Valid. All Country codes are available");
                    else
                        updateCheckStatus.put(SAP_REGION, "[W][C] Country codes are not available : " + missingRegionDetails);
                } catch (LoginException e) {
                    logger.error("Exception occurred while reading region nodes", e);
                }
            } else {
                failedProducts++;
                updateCheckStatus.put(SAP_REGION, "[E][C] Invalid. Region details node is missing");
            }

        } else {
            failedProducts++;
            updateCheckStatus.put("variantSAPNode", "[E][C] Invalid. SAP Node is missing");
        }
        logger.info("Exit Check Rules for variant product {}", variantNode.getName());
    }

    /**
     * Validate base product content nodes based on commerce consistency rules
     */
    private void validatingBaseProductProperties(Resource node, String referencePath, String mat_num, HashMap<String, String> updateCheckStatus) {
        logger.info("Entry Check rules for base product");
        // Base product data validation
        String baseProduct = node.getValueMap().get(IS_BASE_PRODUCT).toString();
        if (null != baseProduct) {
            if (baseProduct.replace("\"", "").trim().equalsIgnoreCase("true")) {
                if (verboseMode) logger.debug("Base Product Status is true for product {}", mat_num);
                updateCheckStatus.put(IS_BASE_PRODUCT, VALID);
            } else {
                updateCheckStatus.put(IS_BASE_PRODUCT, WAR_INVALID);
            }
        } else {
            failedProducts++;
            updateCheckStatus.put(IS_BASE_PRODUCT, ERROR_NA_COMM);
        }

        // Base Product HP data validation
        ValueMap mp = node.getChild("hp") == null ? null : node.getChild("hp").adaptTo(ValueMap.class);
        if (null != mp) {
            if (null != mp.get("superCategory")) {
                String[] superCategories = mp.get("superCategory").toString().split("\\|");
                boolean correctCategory = false;
                for (String superCategory : superCategories) {
                    superCategory = COMMERCE_PATH + superCategory.trim().replace(",", "/").replace(" ", "-").replaceAll("[()]", "").toLowerCase().substring(9)
                            + CommonConstants.SINGLE_SLASH + mat_num + UNDERSCORE_BASE;
                    if (superCategory.equalsIgnoreCase(referencePath)) {
                        correctCategory = true;
                        if (verboseMode)
                            logger.debug("Super Category matches with the reference category for product : {}", mat_num);
                        updateCheckStatus.put(HP_SUPER_CATEGORY, VALID);
                        break;
                    }
                }
                if (!correctCategory) {
                    failedProducts++;
                    updateCheckStatus.put(HP_SUPER_CATEGORY, "[E][C] Invalid. Different from reference category");
                }
            } else {
                failedProducts++;
                updateCheckStatus.put(HP_SUPER_CATEGORY, ERROR_NA_COMM);
            }
            if (null != mp.get(PRODUCT_APPROVAL_STATUS)) {
                if (mp.get(PRODUCT_APPROVAL_STATUS).toString().replace("\"", "").trim().equals("approved")) {
                    if (verboseMode) logger.debug("Product Status is in approved State for : {}", mat_num);
                    updateCheckStatus.put(PRODUCT_APPROVAL_STATUS, "Approved");
                } else {
                    updateCheckStatus.put(PRODUCT_APPROVAL_STATUS, "[W][C] Not Approved");
                }
            } else {
                updateCheckStatus.put(PRODUCT_APPROVAL_STATUS, "[W][C] Not Approved");
            }

            if (null != mp.get(PDP_TEMPLATE)) {
                if (!mp.get(PDP_TEMPLATE).toString().isEmpty()) {
                    if (verboseMode) logger.debug("PDP template is available for product {}", mat_num);
                    updateCheckStatus.put(PDP_TEMPLATE, "Available");
                } else {
                    updateCheckStatus.put(PDP_TEMPLATE, "[W][C] No pdp template");
                }
            } else {
                updateCheckStatus.put(PDP_TEMPLATE, "[W][C] No pdp template");
            }
        } else {
            failedProducts++;
            updateCheckStatus.put("baseHPNode", "[E][C] Invalid. HP Node is missing");
        }
        logger.info("Exit Check rules for base product");
    }

    /**
     * Validating rules on Images and PDfs
     */
    private void validatingAssetProperties(Resource productAssetNode, String mat_num, HashMap<String, String> updateCheckStatus) {
        // Validating Images
        boolean hasImages = false;
        List<String> invalidImgRegion = new ArrayList<>();
        List<String> validImgRegion = new ArrayList<>();
        Iterator<Resource> res = productAssetNode.listChildren();
        while (res.hasNext()) {
            Resource assetNode = res.next();
            if (assetNode.getValueMap().get(JcrConstants.JCR_PRIMARYTYPE).equals(CommonConstants.DAM_ASSET)) {
                hasImages = true;
                Asset image = assetNode.adaptTo(Asset.class);
                if (null != image) {
                    boolean imgRegionCheck = false;
                    if (null != image.getMetadata("dc:imageRegion")) {
                        if (!image.getMetadata("dc:imageRegion").toString().isEmpty()) {
                            imgRegionCheck = true;
                            validImgRegion.add(image.getName());
                        } else
                            invalidImgRegion.add(image.getName());
                    }
                    if (!imgRegionCheck) {
                        invalidImgRegion.add(image.getName());
                    }
                }
            }
        }

        if (!hasImages) {
            updateCheckStatus.put("productImage", "[W][A] Invalid. No Product Images are available");
        } else {
            if (!validImgRegion.isEmpty())
                updateCheckStatus.put("dc:imageRegionValid", "Valid Image Regions " + validImgRegion);
            if (!invalidImgRegion.isEmpty())
                updateCheckStatus.put("dc:imageRegionInvalid", "[W][A] Invalid. No regions are tagged. " + invalidImgRegion);
        }

        // Validating PDFs
        Set<String> associatedPDF = new HashSet<>();
        associatedPDF.add(mat_num);
        SolrClient server = solrSearchService.solrClient("us");
        QueryResponse queryResponse;
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("materialNumber_t:".concat(mat_num));
        solrQuery.setParam("fl", "docPartIDs_t");
        solrQuery.setRows(solrConfig.getProductsSolrQueryResultLimit());

        try {
            queryResponse = server.query(solrQuery);
            SolrDocumentList sitesSolrDocs = queryResponse.getResults();
            for (SolrDocument solrDocument : sitesSolrDocs) {
                if (!solrDocument.isEmpty()) {
                    String ids = solrDocument.get("docPartIDs_t").toString();
                    if (!ids.isEmpty()) {
                        if (!ids.contains("|")) associatedPDF.add(ids);
                        else {
                            String[] id = ids.split("\\|");
                            associatedPDF.addAll(Arrays.asList(id));
                        }
                    }
                }
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }

        if (null != productAssetNode.getChild("pdf") && productAssetNode.getChild("pdf").hasChildren()) {
            res = productAssetNode.getChild("pdf").listChildren();
            boolean hasPDF = false;
            while (res.hasNext()) {
                Resource assetNode = res.next();
                if (assetNode.getValueMap().get(JcrConstants.JCR_PRIMARYTYPE).equals(CommonConstants.DAM_ASSET) && associatedPDF.contains(assetNode.getName().split("\\.")[0])) {
                    hasPDF = true;
                    ValueMap pdf = assetNode.getChild(CommonConstants.METADATA_PATH_AS_CHILD) == null ? null : assetNode.getChild(CommonConstants.METADATA_PATH_AS_CHILD).adaptTo(ValueMap.class);
                    if (null != pdf) {
                        if (null != pdf.get(CommonConstants.PDF_DOC_REGION)) {
                            String[] docRegion = (String[]) pdf.get(CommonConstants.PDF_DOC_REGION);
                            if (docRegion.length <= 0) {
                                updateCheckStatus.put(PDF_DOC_REGION, "[W][A] Invalid. No Region are tagged");
                            } else {
                                updateCheckStatus.put(PDF_DOC_REGION, VALID);
                            }
                        } else {
                            updateCheckStatus.put(PDF_DOC_REGION, ERROR_NA_ASSET);
                        }

                        if (null != pdf.get(CommonConstants.DOC_TYPE)) {
                            if (pdf.get(CommonConstants.DOC_TYPE).toString().isEmpty()) {
                                updateCheckStatus.put(PDF_DOC_TYPE, "[W][A] Invalid. No docType is available");
                            } else {
                                updateCheckStatus.put(PDF_DOC_TYPE, VALID);
                            }
                        } else {
                            updateCheckStatus.put(PDF_DOC_TYPE, ERROR_NA_ASSET);
                        }

                        if (null != pdf.get(CommonConstants.DOC_SKU)) {
                            if (pdf.get(CommonConstants.DOC_SKU).toString().isEmpty() || !pdf.get(CommonConstants.DOC_SKU).equals(assetNode.getName().split("\\.")[0])) {
                                updateCheckStatus.put(PDF_DOC_SKU, "[W][A] Invalid docSku is tagged");
                            } else {
                                updateCheckStatus.put(PDF_DOC_SKU, VALID);
                            }
                        } else {
                            updateCheckStatus.put(PDF_DOC_SKU, ERROR_NA_ASSET);
                        }

                        if (null != pdf.get(CommonConstants.DOC_PART_ID)) {
                            if (pdf.get(CommonConstants.DOC_PART_ID).toString().isEmpty()) {
                                updateCheckStatus.put(PDF_DOC_PART_ID, "[W][A] Invalid docPartId");
                            } else {
                                updateCheckStatus.put(PDF_DOC_PART_ID, VALID);
                            }
                        } else {
                            updateCheckStatus.put(PDF_DOC_PART_ID, ERROR_NA_ASSET);
                        }
                    }
                    updateCheckStatus.put("pdfPathDetails", assetNode.getPath());
                    break;
                }
            }
            if (!hasPDF) {
                updateCheckStatus.put("productPdf", "[W][A] Invalid. No Product pdfs are available");
            }
        } else {
            updateCheckStatus.put("productPdf", "[W][A] Invalid. No Product pdfs are available");
        }
    }

    private void startPDConsistencyScriptProcessDryRunMode(ActionManager actionManager) {
        actionManager.deferredWithResolver(this::collectReferences);
    }

    private void startPDConsistencyScriptProcess(ActionManager actionManager) {
        actionManager.deferredWithResolver(this::collectReferences);
    }

    private void collectReferences(ResourceResolver resourceResolver) {
        logger.info("Collecting References");
        if (!hpFileName.isEmpty() && !excelConfig.isEmpty()) {
            excelConfiguration = getExcelConfiguration();
            readExcelFile(resourceResolver, hpFileName);
        }
    }

    private void readExcelFile(ResourceResolver resourceResolver, String
            hpFileName) {
        Resource excelPathResource = resourceResolver.getResource(hpFileName);
        Asset asset;
        InputStream stream;
        if (null != excelPathResource) {
            asset = excelPathResource.adaptTo(Asset.class);
            if (null != asset) {
                stream = asset.getOriginal().getStream();
                excelObject = createJSONAndTextFileFromExcel(stream, asset.getMimeType());
            }
        }
    }

    private JsonObject createJSONAndTextFileFromExcel(InputStream stream, String mimeType) {
        JsonObject tableObject = new JsonObject();

        try {
            Workbook excelWorkBook;
            if (mimeType.contains("application/vnd.ms-excel")) {
                excelWorkBook = new HSSFWorkbook(stream);
            } else {
                excelWorkBook = new XSSFWorkbook(stream);
            }

            // Get all excel sheet count.
            int totalSheetNumber = excelWorkBook.getNumberOfSheets();

            for (int i = 0; i < totalSheetNumber; i++) {
                // Get current sheet.
                Sheet sheet = excelWorkBook.getSheetAt(i);

                // Get sheet name.
                String sheetName = sheet.getSheetName();

                if (sheetName != null && sheetName.length() > 0 && sheetName.equalsIgnoreCase(excelConfiguration.get(0))) {
                    // Get current sheet data in a list table.
                    List<List<String>> sheetDataTable = getSheetDataList(sheet);
                    // Generate JSON format of above sheet data and write to a JSON file.
                    tableObject = getJsonObjectFromList(sheetDataTable, excelConfiguration.get(1));
                }
            }
            // Close excel work book object.
            excelWorkBook.close();
        } catch (IOException e) {
            logger.error("IOException error occurred", e);
        }
        return tableObject;
    }

    private JsonObject getJsonObjectFromList(List<List<String>> sheetDataTable, String materialColumnName) {
        JsonObject tableJsonObject = new JsonObject();
        if (sheetDataTable != null) {
            int rowCount = sheetDataTable.size();
            if (rowCount > 1) {
                // The first row is the header row, store each column name.
                List<String> headerRow = sheetDataTable.get(0);

                int columnCount = headerRow.size();

                // Loop in the row data list.
                for (int i = 1; i < rowCount; i++) {
                    // Get current row data.
                    List<String> dataRow = sheetDataTable.get(i);

                    // Create a JSONObject object to store row data.
                    JsonObject rowJsonObject = new JsonObject();

                    for (int j = 0; j < columnCount; j++) {
                        String columnName = headerRow.get(j);
                        String columnValue = dataRow.get(j);
                        rowJsonObject.addProperty(columnName, columnValue);
                    }
                    tableJsonObject.add(rowJsonObject.get(colNameBasedOnConfig.get(materialColumnName)).toString().replace("\"", ""), rowJsonObject);
                }
            }
        }
        return tableJsonObject;
    }

    private List<List<String>> getSheetDataList(Sheet sheet) {
        List<List<String>> ret = new ArrayList<>();

        // Get the first and last sheet row number.
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();

        if (lastRowNum > 0) {
            // Loop in sheet rows.
            int lastCellNum = 0; /* Last column will always be equal to first row column */
            int firstCellNum = 0;
            for (int i = firstRowNum; i < lastRowNum + 1; i++) {
                // Get current row object.
                Row row = sheet.getRow(i);
                if (i == 0) {
                    // Get first and last cell number.
                    lastCellNum = row.getLastCellNum();
                    firstCellNum = row.getFirstCellNum();
                }

                // Create a String list to save column data in a row.
                List<String> rowDataList = new ArrayList<>();

                // Loop in the row cells.
                for (int j = firstCellNum; j < lastCellNum; j++) {
                    // Get current cell.
                    Cell cell = row.getCell(j);

                    if (i == 0) // Mapping first row to get dynamic data based on excel config
                        colNameBasedOnConfig.put(String.valueOf((char) (cell.getColumnIndex() + 65)), cell.getStringCellValue());
                    if (null != cell) {
                        // Get cell type.
                        CellType cellType = cell.getCellType();

                        if (cellType == CellType.NUMERIC) {
                            int numberValue = (int) cell.getNumericCellValue();

                            // BigDecimal is used to avoid double value is counted use Scientific counting
                            // method.
                            // For example the original double variable value is 12345678, but jdk
                            // translated the value to 1.2345678E7.
                            String stringCellValue = BigDecimal.valueOf(numberValue).toPlainString();

                            rowDataList.add(stringCellValue);

                        } else if (cellType == CellType.STRING) {
                            String cellValue = cell.getStringCellValue();
                            rowDataList.add(cellValue);
                        } else if (cellType == CellType.BOOLEAN) {
                            boolean numberValue = cell.getBooleanCellValue();

                            String stringCellValue = String.valueOf(numberValue);

                            rowDataList.add(stringCellValue);

                        } else if (cellType == CellType.BLANK) {
                            rowDataList.add("".trim());
                        }
                    } else
                        rowDataList.add(null);
                }

                // Add current row data list in the return list.
                ret.add(rowDataList);
            }
        }
        return ret;
    }

    /**
     * function to get excel config
     */
    private ArrayList<String> getExcelConfiguration() {
        if (excelConfig != null && excelConfig.length() > 0 && excelConfig.contains(";")) {
            String[] config = excelConfig.split(";");
            for (String cfg : config) {
                if (cfg.contains(":")) {
                    excelConfiguration.add(cfg.split(":")[0]);
                    excelConfiguration.add(cfg.split(":")[1].split("=")[1]);
                } else {
                    if (cfg.contains(",")) {
                        String[] subCategory = cfg.split("=")[1].split(",");
                        Collections.addAll(excelConfiguration, subCategory);
                    } else
                        excelConfiguration.add(cfg.split("=")[1]);
                }
            }
        }
        return excelConfiguration;
    }

    @Override
    public void storeReport(ProcessInstance processInstance, ResourceResolver resourceResolver) {
        logger.info("Inside store Report method");
    }

    @Override
    public void parseInputs(ValueMap input) throws DeserializeException, RepositoryException {
        super.parseInputs(input);
    }

    private enum Mode {
        LIVE_MODE, DRY_RUN_MODE
    }
}
