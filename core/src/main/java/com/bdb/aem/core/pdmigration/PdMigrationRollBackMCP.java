package com.bdb.aem.core.pdmigration;

import com.adobe.acs.commons.fam.ActionManager;
import com.adobe.acs.commons.mcp.ProcessDefinition;
import com.adobe.acs.commons.mcp.ProcessInstance;
import com.adobe.acs.commons.mcp.form.FormField;
import com.adobe.acs.commons.mcp.form.PathfieldComponent;
import com.adobe.acs.commons.mcp.form.RadioComponent;
import com.adobe.acs.commons.mcp.model.GenericReport;
import com.adobe.acs.commons.mcp.model.ManagedProcess;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.*;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;
import java.util.*;

/**
 * The type Pd migration roll back mcp.
 */
public class PdMigrationRollBackMCP extends ProcessDefinition {


    /**
     * The constant PD_MIGRATION_ROLL_BACK_MCP_PROCESS.
     */
    public static final String PD_MIGRATION_ROLL_BACK_MCP_PROCESS = "PD Migration Roll Back MCP Process";
    /**
     * The constant ALL.
     */
    public static final String ALL = "all";
    /**
     * The constant SINGLE_PRD_REGEX.
     */
    public static final String SINGLE_PRD_REGEX = "[0-9]{6}$";
    /**
     * The constant FIRST_LEVEL_PRD_FOLDER.
     */
    public static final String FIRST_LEVEL_PRD_FOLDER = "^\\d\\d\\dxxx$";
    /**
     * The constant EXECUTION_MODE.
     */
    public static final String EXECUTION_MODE = "Mode";
    /**
     * The constant COMMERCE_NODE.
     */
    public static final String COMMERCE_NODE = "commerce";
    /**
     * The constant SOURCE.
     */
    public static final String SOURCE = "source";
    /**
     * The constant SLASH.
     */
    public static final String SLASH = "/";
    /**
     * The constant UNDERSCOREBASE.
     */
    public static final String UNDERSCOREBASE = "_base";
    /**
     * The constant CONTENT_COMMERCE_LOOKUP_VARIANT.
     */
    public static final String CONTENT_COMMERCE_LOOKUP_VARIANT = "/content/commerce/lookup/variant/";
    /**
     * The constant CATALOG_PATH.
     */
    public static final String CATALOG_PATH = "catalogPath";
    /**
     * The constant CONTENT_COMMERCE_LOOKUP_BASE_PRODUCT.
     */
    public static final String CONTENT_COMMERCE_LOOKUP_BASE_PRODUCT = "/content/commerce/lookup/baseProduct/";
    /**
     * The constant COMMA_SEPARATED_PRODUCT_LIST.
     */
    public static final String COMMA_SEPARATED_PRODUCT_LIST = "^(\\d{6},\\s*)*\\d{6}$";
    /**
     * The constant PRD_RANGE_INPUT.
     */
    public static final String PRD_RANGE_INPUT = "^[0-9]{6}:[0-9]{6}$";

    public static final String SERIES_PRD_RANGE_INPUT = "^[0-9]{3}:[0-9]{3}$";
    /**
     * The constant XXX.
     */
    public static final String XXX = "xxx";
    /**
     * The constant XX.
     */
    public static final String XX = "xx";
    public static final String F = "f";
    public static final String PUBLISH_INSTANCE = "publish";
    public static final String TARGET = "target";
    public static final String ASSETS = "assets";
    private static final Logger logger = LoggerFactory.getLogger(PdMigrationRollBackMCP.class);
    private static final GenericReport report = new GenericReport();
    private final Map<String, String> pdfPaths = new HashMap<>();
    /**
     * The Process info.
     */
    ManagedProcess processInfo;
    private Replicator replicator;

    Map<String, Integer> productRangeMap = new HashMap<>();
    private SolrSearchService solrSearchService;
    private boolean isDryRunMode = false;
    @FormField(name = EXECUTION_MODE, component = RadioComponent.EnumerationSelector.class, required = true, description = "MCP Execution Mode", options = {"horizontal", "default=DRY_RUN_MODE"})
    private PdMigrationRollBackMCP.Mode mode;

    @FormField(name = "Material Number", description = "Single material number, " + "range of material numbers, " + "Comma Separated list of Material Numbers , " + "All material numbers under the tracking data path", required = true)
    private String materialNumber;

    @FormField(name = "PD Category Path", component = PathfieldComponent.NodeSelectComponent.class, required = true)
    private String category;

    @FormField(name = "PD Migration Tracking data base Path", component = PathfieldComponent.NodeSelectComponent.class, required = true)
    private String trackingDataPath;


    public PdMigrationRollBackMCP(Replicator replicator, SolrSearchService solrSearchService) {
        this.replicator = replicator;
        this.solrSearchService = solrSearchService;
    }


    @Override
    public void buildProcess(ProcessInstance instance, ResourceResolver rr) throws LoginException {
        processInfo = instance.getInfo();
        instance.getInfo().setDescription("Running PD Migration Rollback process");

        switch (mode) {
            case LIVE_MODE:
                instance.defineCriticalAction(PD_MIGRATION_ROLL_BACK_MCP_PROCESS, rr, this::startPDMigrationRollBackProcess);
                break;
            case DRY_RUN_MODE:
                isDryRunMode = true;
                instance.defineCriticalAction(PD_MIGRATION_ROLL_BACK_MCP_PROCESS, rr, this::startPDMigrationRollBackProcess);
                break;
        }

    }

    /**
     * Start pd migration roll back process.
     *
     * @param manager the manager
     */
    protected void startPDMigrationRollBackProcess(ActionManager manager) {
        manager.deferredWithResolver(this::handleRollback);
    }

    /**
     * Handle rollback of the Product data based on the input product data chosen by the user (ALL PD, Specific PD, PD Range)
     *
     * @param resourceResolver the resource resolver
     * @throws RepositoryException  the repository exception
     * @throws ReplicationException the replication exception
     * @throws SolrServerException  the solr server exception
     * @throws LoginException       the login exception
     * @throws IOException          the io exception
     */
    protected void handleRollback(ResourceResolver resourceResolver) throws RepositoryException, ReplicationException, SolrServerException, LoginException, IOException {

        if (materialNumber.equalsIgnoreCase(ALL)) {

            if (isDryRunMode) {
                logger.info("Rolling Back all Products");
            }

            getProductsFromTrackingData(resourceResolver);

        } else if (materialNumber.matches(SINGLE_PRD_REGEX)) {
            if (isDryRunMode) {
                logger.info(String.format("Rolling Back Material Number :: %s", materialNumber));
            }

            String productTrackingPath = trackingDataPath + SLASH + materialNumber.substring(0, materialNumber.length() - 3) + XXX + SLASH + materialNumber.substring(0, materialNumber.length() - 2) + XX + SLASH + materialNumber;

            if (resourceResolver.getResource(productTrackingPath) != null) {
                Node productNode = Objects.requireNonNull(resourceResolver.getResource(productTrackingPath)).adaptTo(Node.class);

                if (productNode != null) {
                    triggerRollBack(resourceResolver, productNode);
                    // Once the Rollback is done, the product entry from the tracking data needs to be removed
                    // since the product returns to its original state.
                    deleteTrackingProduct(resourceResolver, productNode);
                }
            }
        } else if (materialNumber.matches(COMMA_SEPARATED_PRODUCT_LIST)) {
            List<String> list = new ArrayList<>(Arrays.asList(materialNumber.split("\\s*,\\s*")));
            for (String mat_num : list) {

                String productTrackingPath = trackingDataPath + SLASH + mat_num.substring(0, mat_num.length() - 3) + XXX + SLASH + mat_num.substring(0, mat_num.length() - 2) + XX + SLASH + mat_num;
                if (resourceResolver.getResource(productTrackingPath) != null) {
                    Node productNode = Objects.requireNonNull(resourceResolver.getResource(productTrackingPath)).adaptTo(Node.class);
                    if (productNode != null) {
                        if (isDryRunMode) {
                            logger.info(String.format("Rolling Back Material Number:: %s", mat_num));
                        }
                        triggerRollBack(resourceResolver, productNode);
                        // Once the Rollback is done, the product entry from the tracking data needs to be removed
                        // since the product returns to its original state.
                        deleteTrackingProduct(resourceResolver, productNode);
                    }
                }
            }

        } else if (materialNumber.matches(PRD_RANGE_INPUT)) {

            // Handle Range Input data.
            Resource commerceResource = resourceResolver.getResource(category);
            if (commerceResource != null && commerceResource.hasChildren()) {
                Node parentCategoryNode = Objects.requireNonNull(commerceResource).adaptTo(Node.class);
                if (parentCategoryNode != null) {
                    Node productNode;
                    String startMatNumber = materialNumber.split(":")[0];
                    String endMatNumber = materialNumber.split(":")[1];
                    if ((Integer.parseInt(startMatNumber) > Integer.parseInt(endMatNumber)))
                        throw new IllegalArgumentException(String.format("Range Start: %s1 is greater than Range End : %s2", startMatNumber, endMatNumber));
                    else if (startMatNumber.equalsIgnoreCase(endMatNumber)) {
                        productNode = parentCategoryNode.getNode(startMatNumber + UNDERSCOREBASE);
                        triggerRollBack(resourceResolver, productNode);
                        // Once the Rollback is done, the product entry from the tracking data needs to be removed
                        // since the product returns to its original state.
                        deleteTrackingProduct(resourceResolver, productNode);
                    } else {
                        NodeIterator parentNodeIterator = parentCategoryNode.getNodes();
                        while (parentNodeIterator.hasNext()) {
                            productNode = parentNodeIterator.nextNode();
                            if (productNode.getName().contains(UNDERSCOREBASE)) {
                                String productName = productNode.getName().substring(0, productNode.getName().indexOf(UNDERSCOREBASE));
                                if (Integer.parseInt(productName) >= Integer.parseInt(startMatNumber) &&
                                        Integer.parseInt(productName) <= Integer.parseInt(endMatNumber)) {
                                    if (isDryRunMode) {
                                        logger.info(String.format("Rolling Back Material Number:: %s", productName));
                                    }
                                    triggerRollBack(resourceResolver, productNode);
                                    // Once the Rollback is done, the product entry from the tracking data needs to be removed
                                    // since the product returns to its original state.
                                    deleteTrackingProduct(resourceResolver, productNode);
                                }
                            }
                        }
                    }
                }
            }
        }
        else if(trackingDataPath != null && materialNumber.matches(SERIES_PRD_RANGE_INPUT)){

            String[] products = materialNumber.split(":");
            if (products.length == 2 ) {
                productRangeMap.put("RangeStart", Integer.parseInt(products[0]));
                productRangeMap.put("RangeEnd", Integer.parseInt(products[1]));

            }
            getProductsFromTrackingData(resourceResolver);

        }
    }

    private void getProductsFromTrackingData(ResourceResolver resolver) throws RepositoryException, ReplicationException, SolrServerException, LoginException, IOException {

        if (resolver.getResource(trackingDataPath) != null) {
            Node parentNode = Objects.requireNonNull(resolver.getResource(trackingDataPath)).adaptTo(Node.class);
            if (parentNode != null && parentNode.hasNodes()) {
                NodeIterator parentNodeIterator = parentNode.getNodes();
                while (parentNodeIterator.hasNext()) {
                    Node firstLevelChildNode = parentNodeIterator.nextNode();

                    if(materialNumber.matches(SERIES_PRD_RANGE_INPUT)){
                        int productRange = Integer.parseInt(firstLevelChildNode.getName().replace(XXX, ""));
                        if (productRange >= productRangeMap.get("RangeStart") && productRange <= productRangeMap.get("RangeEnd")) {
                            delegateFolderCleanUp(resolver, firstLevelChildNode);
                        }
                    }else {

                        delegateFolderCleanUp(resolver, firstLevelChildNode);

                    }
                }
            }
        }

    }

    private void delegateFolderCleanUp(ResourceResolver resolver, Node firstLevelChildNode) throws RepositoryException, ReplicationException, SolrServerException, LoginException, IOException {
        if (firstLevelChildNode.hasNodes()) {
            NodeIterator firstLevelChildNodeIterator = firstLevelChildNode.getNodes();
            while (firstLevelChildNodeIterator.hasNext()) {
                Node secondLevelChildNode = firstLevelChildNodeIterator.nextNode();
                if (secondLevelChildNode.hasNodes()) {
                    NodeIterator secondLevelChildNodeIterator = secondLevelChildNode.getNodes();
                    while (secondLevelChildNodeIterator.hasNext()) {
                        Node productNode = secondLevelChildNodeIterator.nextNode();
                        triggerRollBack(resolver, productNode);
                        // Once the Rollback is done, the product entry from the tracking data needs to be removed
                        // since the product returns to its original state.
                        deleteTrackingProduct(resolver, productNode);

                    }
                }

            }

        }
    }

    private Map<String, String> getAllPdfs(String resourcePath, String oldPath, ResourceResolver resolver) {
        Map<String, String> assetsMap = new HashMap<>();
        Resource pdfFolder = resolver.getResource(resourcePath + "/pdf");


        if (pdfFolder != null) {
            Iterator<Resource> resourceIterator = pdfFolder.listChildren();
            while (resourceIterator.hasNext()) {
                Resource next = resourceIterator.next();
                Asset asset = next.adaptTo(Asset.class);
                if (asset != null && "application/pdf".equalsIgnoreCase(asset.getMetadataValue("dc:format"))) {
                    assetsMap.put(asset.getPath(), oldPath+SLASH+"pdf"+SLASH+asset.getName());
                }
            }
        }

        return assetsMap;
    }

    private void triggerRollBack(ResourceResolver resolver, Node productTrackingNode) throws RepositoryException, ReplicationException, SolrServerException, LoginException, IOException {
        // ProductNode is the product base folder in the tracking data hierarchy.
        // eg. /var/bdb/content/commerce/products/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/products/550xxx/5500xx/550000
        Node commerceNode = productTrackingNode.getNode(COMMERCE_NODE);

        // Product Asset Data Map
        Node assetsNode = productTrackingNode.getNode(ASSETS);
        if (assetsNode != null) {
            // During Rollback Source path becomes the target and vice versa
            String targetPath = assetsNode.getProperty(SOURCE).getString();
            String sourcePath = assetsNode.getProperty(TARGET).getString();
            Map<String, String> allPdfs = getAllPdfs(targetPath, sourcePath, resolver);
            if (StringUtils.isNotBlank(targetPath)
                    && resolver.getResource(targetPath) != null) {
                pdfPaths.putAll(allPdfs);
            }

        }
        // The commerce node holds the SOURCE property that references the old PD category structure
        String previousBaseProductPath = commerceNode.getProperty(SOURCE).getString();
        if (!StringUtils.isEmpty(previousBaseProductPath)) {
            if (resolver.getResource(previousBaseProductPath) != null) {
                Node previousProductNode = Objects.requireNonNull(resolver.getResource(previousBaseProductPath)).adaptTo(Node.class);
                if (previousProductNode != null && previousProductNode.hasNodes()) {
                    // This loop ensures that the lookup data for product base folder along with its all variants is updated.
                    NodeIterator previousProductNodeIterator = previousProductNode.getNodes();
                    while (previousProductNodeIterator.hasNext()) {
                        Node node = previousProductNodeIterator.nextNode();
                        if (node.getName().matches(SINGLE_PRD_REGEX)) {
                            updateLookUpData(resolver, previousProductNode, false);
                            if ((node.getName() + UNDERSCOREBASE).equalsIgnoreCase(previousProductNode.getName())) {
                                updateLookUpData(resolver, previousProductNode, true);
                            }

                        }

                    }

                }
            }
        }
    }

    private void updateLookUpData(ResourceResolver resolver, Node productName, boolean isLookUpBasePath) throws RepositoryException, ReplicationException, SolrServerException, LoginException, IOException {
        String lookUpProductPath;
        String product = productName.getName().replace(UNDERSCOREBASE, "");
        String shortenedPrdName = product.substring(0, 3);
        if (isLookUpBasePath) {
            lookUpProductPath = CONTENT_COMMERCE_LOOKUP_BASE_PRODUCT + shortenedPrdName + F + SLASH + product;
        } else {
            lookUpProductPath = CONTENT_COMMERCE_LOOKUP_VARIANT + shortenedPrdName + F + SLASH + product;
        }

        Resource resource = resolver.getResource(lookUpProductPath);
        if (resource != null) {
            ModifiableValueMap map = resource.adaptTo(ModifiableValueMap.class);
            if (map != null && !isDryRunMode) {
                map.put(CATALOG_PATH, productName.getPath());
            } else if (Boolean.TRUE.equals(isDryRunMode)) {
                logger.info(String.format("Updating Catalog Path value for product path :: %s", productName.getPath()));
            }

            if (!isDryRunMode) {
                // Trigger activation of the lookUp path
                Session session = resolver.adaptTo(Session.class);
                replicator.replicate(session, ReplicationActionType.ACTIVATE, lookUpProductPath);

                //trigger re-indexing of the product
                if (Boolean.FALSE.equals(isLookUpBasePath)) {
                    //pass a list of all Variants for this specific Material Number
                    List<String> variantList = new ArrayList<>();

                    Resource baseProductRes = resolver.getResource(productName.getPath());
                    if (baseProductRes != null && baseProductRes.hasChildren()) {
                        Node baseProductNode = baseProductRes.adaptTo(Node.class);
                        if (baseProductNode != null) {
                            NodeIterator itr = baseProductNode.getNodes();
                            if (itr != null) {
                                while (itr.hasNext()) {
                                    Node childNode = itr.nextNode();
                                    if (childNode.getName().matches(SINGLE_PRD_REGEX)) {
                                        variantList.add(childNode.getPath());
                                    }
                                }
                            }
                        }
                    }

                    solrSearchService.indexProductDataToSolr(resolver, variantList);
                    reIndexPdfData(resolver);

                }
            } else {
                logger.info(String.format("Activating and Indexing Product data for Product :: %s", productName.getName()));
            }

        }

    }

    private void reIndexPdfData(ResourceResolver resolver){
        if (!pdfPaths.isEmpty()) {
            pdfPaths.forEach((newPath, oldPath) -> {
                try {
                    Resource assetResource = resolver.getResource(newPath);
                    if (assetResource != null) {
                        // Asset asset = assetResource.adaptTo(Asset.class);
                        solrSearchService.updatePdfDataLocation(resolver, newPath, oldPath);
                       // solrSearchService.indexAssetsToSolr(asset, resolver);
                    }
                } catch (LoginException e) {
                    logger.error("Failed to index pdf in Solr: {}", newPath);
                }
            });
        }

    }


    private void deleteTrackingProduct(ResourceResolver resolver, Node productTrackingNode) throws RepositoryException, PersistenceException {

        if (resolver.getResource(productTrackingNode.getPath()) != null) {
            resolver.delete(Objects.requireNonNull(resolver.getResource(productTrackingNode.getPath())));
            resolver.commit();
        }

    }

    @Override
    public void storeReport(ProcessInstance instance, ResourceResolver rr) {
        logger.debug("Inside storeReport Method");
        report.setName("PD Rollback Process Report");
    }

    @Override
    public void init() throws RepositoryException {
        logger.debug("Inside init Method");
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
