package com.bdb.aem.core.services.impl;

import java.io.IOException;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import com.bdb.aem.core.services.PrefixedProductDataResolver;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.event.jobs.JobManager;
import org.apache.solr.client.solrj.SolrServerException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.bdb.aem.core.services.OnDemandTdsGenerationService;
import com.bdb.aem.core.bean.SAPProductVariantPropertyBean;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.day.cq.commons.jcr.JcrUtil;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.tagging.InvalidTagFormatException;
import com.day.cq.tagging.TagManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Service Implementation to update catalog structure.
 */
@Component(service = CatalogStructureUpdateService.class, immediate = true)
public class CatalogStructureUpdateServiceImpl implements CatalogStructureUpdateService {
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(CatalogStructureUpdateServiceImpl.class);

    /** The Constant CATALOG_PATH. */
    private static final String CATALOG_PATH = "catalogPath";

    /** The Constant FALSE. */
    private static final String FALSE = "false";

    /** The solr search service. */
    @Reference
    SolrSearchService solrSearchService;

    /** The replicator. */

    @Reference
    Replicator replicator;

    /** The total time. */
    long totalTime;

    /** The search config. */
    @Reference
    BDBSearchEndpointService searchConfig;

    /** The job manager. */
    @Reference
    JobManager jobManager;

    /** The workflow helper service. */
    @Reference
    WorkflowHelperService workflowHelperService;

    @Reference
    PrefixedProductDataResolver productDataResolver;

    @Reference
    OnDemandTdsGenerationService onDemandTdsGenerationService;

    /**
     * Reads SAP json to check for variants .
     *
     * @param resourceResolver the resource resolver
     * @param jsonString       the json string
     * @return the string
     * @throws RepositoryException     the repository exception
     * @throws JsonProcessingException the json processing exception
     */
    @Override
    public JsonObject setSAPNodeProperty(ResourceResolver resourceResolver, String jsonString)
            throws RepositoryException, JsonProcessingException {
    	long catalogStartTime = System.currentTimeMillis();
        JsonObject jsonObj = new JsonParser().parse(jsonString).getAsJsonObject();
        JsonArray productsArray = jsonObj.getAsJsonArray(CommonConstants.PRODUCTS);
        JsonObject reponseJson = new JsonObject();
        JsonObject catalogResponse = new JsonObject();
        catalogResponse.addProperty(CommonConstants.DATA_SOURCE, "SAP");
        boolean isPartialUpdate = false;
        JsonArray errorSKUs = new JsonArray();
        ArrayList<String> variantList = new ArrayList<>();
        for (JsonElement product : productsArray) {
            String baseProductName = StringUtils.EMPTY;
            try {
                JsonObject baseProductObject = product.getAsJsonObject();
                JsonObject productObject = baseProductObject.getAsJsonObject(CommonConstants.BASE_PRODUCT);
                if (null != productObject.get(CommonConstants.CODE)
                        && !productObject.get(CommonConstants.CODE).getAsString().trim().isEmpty()) {
                    baseProductName = productObject.get(CommonConstants.CODE).getAsString().toLowerCase();
                    // check if product is in lookup
                    Resource baseProductResource = getProductFromLookUp(resourceResolver, baseProductName,
                            CommonConstants.BASE_MATERIAL_NUMBER);
                    logger.info("Looping base product: {}", baseProductName);
                    if (null != baseProductResource) {
                    	Calendar lastUpdated = Calendar.getInstance();
                    	Node baseProductNode = baseProductResource.adaptTo(Node.class);
                    	baseProductNode.setProperty(com.day.cq.commons.jcr.JcrConstants.JCR_LASTMODIFIED, lastUpdated);
                        isPartialUpdate = getVariantsOfProduct(productObject, resourceResolver, variantList, errorSKUs,
                                isPartialUpdate);
                    } else {
                        JsonObject errorBaseProduct = new JsonObject();
                        errorBaseProduct.addProperty(CommonConstants.BASE_MATERIAL_NUMBER,
                                productObject.get(CommonConstants.CODE).getAsString().toLowerCase());
                        errorBaseProduct.addProperty(CommonConstants.MATERIAL_NUMBER, "");
                        errorBaseProduct.addProperty(CommonConstants.STATUS_MESSAGE,
                                "Base Product unavailable in Catalog Structure.");
                        errorSKUs.add(errorBaseProduct);
                        logger.info("Base Product unavailable in Catalog Structure.: {}", baseProductName);
                    }
                }
            } catch (RepositoryException | JsonProcessingException e) {
                logger.error("Failure in Updating Base Product", baseProductName);
            }
        }
        Session session = resourceResolver.adaptTo(Session.class);
        session.save();

        long catalogEndTime = System.currentTimeMillis();
        logger.debug("TOTAL TIME in checking product in solr(SAP) in MS - {}", catalogEndTime - catalogStartTime);

        if (errorSKUs.size() == 0) {
            catalogResponse.addProperty(CommonConstants.STATUS, "Success");
        } else if (isPartialUpdate) {
            catalogResponse.addProperty(CommonConstants.STATUS, "Partial Update");
        } else {
            catalogResponse.addProperty(CommonConstants.STATUS, "Error");
        }
        catalogResponse.add(CommonConstants.ERROR_SKUS, errorSKUs);
        reponseJson.add(CommonConstants.CATALOG_RESPONSE, catalogResponse);

        Resource adminPageRes = resourceResolver.getResource(searchConfig.getAdminPagePath() + CommonConstants.JCR_CONTENT);
		if (null != adminPageRes) {
			ValueMap valueMap = adminPageRes.adaptTo(ValueMap.class);
			if (valueMap.containsKey("slingJobEnabler") && valueMap.get("slingJobEnabler").equals("true")) {
				workflowHelperService.addJobForReplication(variantList, jobManager, BDBDistributionServiceImpl.TOPIC,
						100, CommonConstants.REPLICATION_ALL_NODES);
				workflowHelperService.addJobForIndexing(variantList, jobManager, BDBIndexingJob.TOPIC,
						CommonConstants.TYPE_COMMERCE_CONTENT);
				//added to update the availability of related pdf
				workflowHelperService.addJobForIndexing(variantList, jobManager, BDBIndexingJob.TOPIC,
						CommonConstants.TYPE_COMMERCE_CONTENT_PDF);
			}
		}
        logger.debug("CatalogStructureUpdateServiceImpl - setSAPNodeProperty end");

        return reponseJson;
    }

    /**
     * Gets the variants of product.
     *
     * @param productObject    the product object
     * @param resourceResolver the resource resolve
     * @param variantList      the variant list
     * @param errorSKUs        the error SK us
     * @param isPartialUpdate  the is partial update
     * @return the variants of product
     * @throws RepositoryException     the repository exception
     * @throws JsonProcessingException the json processing exception
     */
    private boolean getVariantsOfProduct(JsonObject productObject, ResourceResolver resourceResolver,
                                         List<String> variantList, JsonArray errorSKUs, boolean isPartialUpdate)
            throws RepositoryException, JsonProcessingException {
        if (productObject.has(CommonConstants.VARIANTS)) {
            JsonArray variantsArray = productObject.getAsJsonArray(CommonConstants.VARIANTS);
            for (JsonElement variant : variantsArray) {
                String variantName = StringUtils.EMPTY;
                JsonObject errorVariant = new JsonObject();
                JsonObject variantObj = variant.getAsJsonObject();
                try {
                    if (variantObj.has(CommonConstants.CODE)) {
                        variantName = variantObj.get(CommonConstants.CODE).getAsString().toLowerCase();
                        logger.info("Looping product variant: {}", variant);                        
                        Resource variantProductResource = getProductFromLookUp(resourceResolver, variantName,
                                CommonConstants.MATERIAL_NUMBER);
                        if (null != variantProductResource) {
                            addRequiredNodestoVariants(resourceResolver, variantProductResource.adaptTo(Node.class),
                                    variantObj, productObject);
                            Calendar lastUpdated = Calendar.getInstance();
                            Node variantNode = variantProductResource.adaptTo(Node.class);
                            variantNode.setProperty(com.day.cq.commons.jcr.JcrConstants.JCR_LASTMODIFIED, lastUpdated);
                            variantList.add(variantProductResource.getPath());
                            isPartialUpdate = true;
                        } else {
                            errorVariant.addProperty(CommonConstants.BASE_MATERIAL_NUMBER,
                                    productObject.get(CommonConstants.CODE).getAsString().toLowerCase());
                            errorVariant.addProperty(CommonConstants.MATERIAL_NUMBER,
                                    variantObj.get(CommonConstants.CODE).getAsString().toLowerCase());
                            errorVariant.addProperty(CommonConstants.STATUS_MESSAGE,
                                    "Variant unavailable in Catalog Structure.");
                            errorSKUs.add(errorVariant);
                        }
                    }
                } catch (RepositoryException | JsonProcessingException | ReplicationException e) {
                    logger.error("Failed to update SAP node in variant of Catalog Structure {}", variantName);
                    errorVariant.addProperty(CommonConstants.BASE_MATERIAL_NUMBER,
                            productObject.get(CommonConstants.CODE).getAsString().toLowerCase());
                    errorVariant.addProperty(CommonConstants.MATERIAL_NUMBER,
                            variantObj.get(CommonConstants.CODE).getAsString().toLowerCase());
                    errorVariant.addProperty(CommonConstants.STATUS_MESSAGE,
                            "Exception while adding variant to Catalog Structure");
                    errorSKUs.add(errorVariant);
                }
            }
        }
        logger.debug("CatalogStructureUpdateServiceImpl - getVariantsOfProduct end");
        return isPartialUpdate;
    }

    /**
     * Adds the variant node and required nodes to variants.
     *
     * @param resourceResolver the resource resolver
     * @param variantNode      the variant node
     * @param variantObj       the variant obj
     * @param productObject    the product object
     * @return the list
     * @throws RepositoryException     the repository exception
     * @throws JsonProcessingException the json processing exception
     * @throws ReplicationException    the replication exception
     */
    public void addRequiredNodestoVariants(ResourceResolver resourceResolver, Node variantNode, JsonObject variantObj,
                                           JsonObject productObject) throws RepositoryException, JsonProcessingException, ReplicationException {
        Session session = resourceResolver.adaptTo(Session.class);
        Node sapNode = variantNode.getNode(CommonConstants.SAP_CC_NODENAME);
        Node regionDetailsNode = variantNode.getNode(CommonConstants.SAP_CC_NODENAME)
                .getNode(CommonConstants.REGION_DETAILS_NODE_NAME);
        setVariantSAPProperties(variantObj, sapNode, productObject);
        // market availability
        getMarketAvailabilityFromJson(variantObj, regionDetailsNode);
        if (searchConfig.getAllowProductReplication().equals("true")) {
            replicator.replicate(session, ReplicationActionType.ACTIVATE, variantNode.getPath());
        }
        //session.save();
    }

    /**
     * Creates the variant node structure.
     *
     * @param variantNode the variant node
     * @throws RepositoryException the repository exception
     */
    public void createProductNodeStructure(Node variantNode) throws RepositoryException {
        if (!variantNode.hasNode(CommonConstants.HP_NODE)) {
            variantNode.addNode(CommonConstants.HP_NODE, searchConfig.getCatalogStructureNodeType());
        }
        if (!variantNode.hasNode(CommonConstants.SAP_CC_NODENAME)) {
            variantNode.addNode(CommonConstants.SAP_CC_NODENAME, searchConfig.getCatalogStructureNodeType());
            variantNode.getNode(CommonConstants.SAP_CC_NODENAME).addNode(CommonConstants.REGION_DETAILS_NODE_NAME,
                    searchConfig.getCatalogStructureNodeType());
        }
    }

    /**
     * Sets the SAP variant properties.
     *
     * @param variantObj    the variant obj
     * @param sapNode       the sap node
     * @param productObject the product object
     * @throws RepositoryException     the repository exception
     * @throws JsonProcessingException the json processing exception
     */
    public void setVariantSAPProperties(JsonObject variantObj, Node sapNode, JsonObject productObject)
            throws RepositoryException, JsonProcessingException {
        ObjectMapper variantPropertyObjectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SAPProductVariantPropertyBean productVariantPropertyBean = variantPropertyObjectMapper
                .readValue(variantObj.toString(), SAPProductVariantPropertyBean.class);
        sapNode.setProperty(CommonConstants.BATCH_MANAGEMENT_INDICATIOR,
                productVariantPropertyBean.getBatchManagementIndicator());
        sapNode.setProperty(CommonConstants.CODE, productVariantPropertyBean.getCode());
        sapNode.setProperty(CommonConstants.DANGEROUS_GOODS_INDICATOR,
                productVariantPropertyBean.getDangerousGoodsIndicator());
        sapNode.setProperty(CommonConstants.EAN, productVariantPropertyBean.getEan());
        sapNode.setProperty(CommonConstants.GLOBAL_WEB_AVAILABLE, productVariantPropertyBean.getGlobalWebAvailable());
        sapNode.setProperty(CommonConstants.GROSS_WEIGHT, productVariantPropertyBean.getGrossWeight());
        sapNode.setProperty(CommonConstants.HAZARDOUS_CODE, productVariantPropertyBean.getHazardousCode());
        sapNode.setProperty(CommonConstants.HAZARDOUS_DESCRIPTION,
                productVariantPropertyBean.getHazardousDescription());
        sapNode.setProperty(CommonConstants.NET_WEIGHT, productVariantPropertyBean.getNetWeight());
        sapNode.setProperty(CommonConstants.RESEARCH_ONLY, productVariantPropertyBean.getResearchOnly());
        sapNode.setProperty(CommonConstants.SHIPPING_CONDITION_CODE,
                productVariantPropertyBean.getShippingConditionCode());
        sapNode.setProperty(CommonConstants.SHIPPING_CONDITION_DESCRIPTION,
                productVariantPropertyBean.getShippingConditionDescription());
        sapNode.setProperty(CommonConstants.TEMPERATURE_CONDITION_INDICAOTR,
                productVariantPropertyBean.getTemperatureConditionIndicator());
        setNodeProperty(variantObj, CommonConstants.GROSS_WEIGHT_UNIT, sapNode);
        setNodeProperty(variantObj, CommonConstants.UNIT, sapNode);
        setNodeProperty(variantObj, CommonConstants.APPROVAL_STATUS, sapNode);
        setNodeProperty(productObject, CommonConstants.MPG_CATEGORY, sapNode);
        setNodeProperty(productObject, CommonConstants.CLASSIFICATION_CATEGOR_KEY, sapNode);
        setNodeProperty(variantObj, CommonConstants.PHANTOM_CHILDS, sapNode);
        setNodeProperty(variantObj, CommonConstants.PHANTOM_PARENTS, sapNode);
        logger.debug("CatalogStructureUpdateServiceImpl - setVariantSAPProperties end");
    }

    /**
     * Sets the node property.
     *
     * @param jsonObject the json object
     * @param myParam    the my param
     * @param node       the node
     * @throws RepositoryException the repository exception
     */
    public static void setNodeProperty(JsonObject jsonObject, String myParam, Node node) throws RepositoryException {
        if (null != jsonObject.get(myParam)) {
            node.setProperty(myParam, jsonObject.get(myParam).toString());
        }
    }

    /**
     * Gets the market availability fron json.
     *
     * @param variantObj        the variant obj
     * @param regionDetailsNode the region details node
     * @return the market availability from json
     * @throws RepositoryException the repository exception
     */
    private void getMarketAvailabilityFromJson(JsonObject variantObj, Node regionDetailsNode)
            throws RepositoryException {
        JsonArray marketAvailabilityArray = variantObj.getAsJsonArray(CommonConstants.MARKET_AVAILABILITY);
        for (JsonElement marketAvailability : marketAvailabilityArray) {
            JsonObject marketAvailabilityObj = marketAvailability.getAsJsonObject();
            JsonObject baseStoreObj = marketAvailabilityObj.getAsJsonObject(CommonConstants.BASE_STORE);
            if (null != baseStoreObj.get(CommonConstants.UID)) {
                String countryName = baseStoreObj.get(CommonConstants.UID).getAsString();
                regionDetailsNode.setProperty(countryName, marketAvailabilityObj.toString());
            }
        }
        logger.debug("CatalogStructureUpdateServiceImpl - getMarketAvailabilityFromJson end");
    }

    /**
     * Sets the HP node properties.
     *
     * @param resourceResolver the resource resolver
     * @param jsonString       the json string
     * @return the string
     * @throws RepositoryException       the repository exception
     * @throws JsonProcessingException   the json processing exception
     * @throws InvalidTagFormatException the invalid tag format exception
     * @throws LoginException            the login exception
     */
    @Override
    public JsonObject setHPNodeProperty(ResourceResolver resourceResolver, String jsonString)
            throws RepositoryException, JsonProcessingException, InvalidTagFormatException, LoginException {
        long catalogStartTime = System.currentTimeMillis();
        JsonObject jsonObj = new JsonParser().parse(jsonString).getAsJsonObject();
        JsonArray baseProductArray = jsonObj.getAsJsonArray(CommonConstants.PRODUCTS);

        ArrayList<String> variantList = new ArrayList<>();
        ArrayList<String> baseProductList = new ArrayList<>();
        ArrayList<String> lookUpVariantList = new ArrayList<>();
        ArrayList<String> lookUpBaseList = new ArrayList<>();
        ArrayList<String> tagtList = new ArrayList<>();

        Session session = resourceResolver.adaptTo(Session.class);
        JsonObject reponseJson = new JsonObject();
        JsonObject catalogResponse = new JsonObject();
        boolean isPartialUpdate = false;
        catalogResponse.addProperty(CommonConstants.DATA_SOURCE, "HP/FF");
        JsonArray errorSKUs = new JsonArray();
        if (null != baseProductArray && baseProductArray.size()>0) {
            for (JsonElement baseProduct : baseProductArray) {
                JsonObject baseProductObj = baseProduct.getAsJsonObject();
                if (baseProductObj.has(CommonConstants.BASE_MATERIAL_NUMBER)) {
                    String baseProductName = StringUtils.EMPTY;
                    JsonObject errorBaseProduct = new JsonObject();
                    Node baseProductNode = null;
                    try {

                        baseProductName = baseProductObj.get(CommonConstants.BASE_MATERIAL_NUMBER).getAsString().toLowerCase();
                        if (null != baseProductObj.get("isVariantOnly")
                                && baseProductObj.get("isVariantOnly").getAsString().equalsIgnoreCase("Yes")) {
                            isPartialUpdate = addPropertiesForVariantOnlyJson(resourceResolver, variantList, errorSKUs,
                                    baseProductObj, baseProductName, errorBaseProduct, isPartialUpdate, lookUpVariantList);
                        } else {

                            // check from lookUp if baseProduct is present in taxonomy, else create it
                            String tagPath = getTagPathFromLookupForBaseProduct(baseProductObj, resourceResolver,
                                    baseProductName, CommonConstants.BASE_MATERIAL_NUMBER);
                            if (tagPath.equals(FALSE)) {
                                errorBaseProduct.addProperty(CommonConstants.BASE_MATERIAL_NUMBER, baseProductName);
                                errorBaseProduct.addProperty(CommonConstants.STATUS_MESSAGE,
                                        "SuperCategory missing in the base product.");
                                errorSKUs.add(errorBaseProduct);
                                continue;
                            }
                            tagtList.add(getTagPath(tagPath));
                            lookUpBaseList.add(getLookUpPathFromProductId(baseProductName, CommonConstants.BASE_MATERIAL_NUMBER));
                            Resource baseProductResource = resourceResolver.getResource(getCatalogPath(tagPath));
                            if (null != baseProductResource) {
                                baseProductNode = baseProductResource.adaptTo(Node.class);
                            } else {
                                baseProductNode = createbaseProductNode(resourceResolver, tagPath);
                                baseProductNode.setProperty("isBaseProduct", "true");
                            }
							Calendar lastUpdated = Calendar.getInstance();
							baseProductNode.setProperty(com.day.cq.commons.jcr.JcrConstants.JCR_LASTMODIFIED,
									lastUpdated);
							logger.debug("CatalogStructureUpdateServiceImpl - baseProductNode {}", baseProductNode);
							createProductNodeStructure(baseProductNode);
							baseProductList.add(baseProductNode.getPath());
							Node hpNode = baseProductNode.getNode(CommonConstants.HP_NODE);
							setHpNodeProperties(baseProductObj, hpNode, CommonConstants.BASE_MATERIAL_NUMBER);
							isPartialUpdate = addHpStructuretoVariants(baseProductObj, baseProductNode,
									resourceResolver, variantList, errorSKUs, isPartialUpdate, false,
									lookUpVariantList);

						}
                    } catch ( RepositoryException e) {
                        logger.error("Failed to update base product to Catalog Structure {}", baseProductName);
                        errorBaseProduct.addProperty(CommonConstants.BASE_MATERIAL_NUMBER, baseProductName);
                        errorBaseProduct.addProperty(CommonConstants.STATUS_MESSAGE,
                                "Exception while adding Base Product to Catalog Structure.");
                        errorSKUs.add(errorBaseProduct);
                    }
                }
            }

            session.save();
            long catalogEndTime = System.currentTimeMillis();

            logger.debug("TOTAL TIME in creating product/tags in MS - {}", catalogEndTime - catalogStartTime);
            logger.debug("TOTAL TIME in checking product in solr(HP) in MS - {}", totalTime);

            Set<String> finalListforReplication = new HashSet<>(); // final list for replication
            finalListforReplication.addAll(baseProductList);
            finalListforReplication.addAll(tagtList);
            finalListforReplication.addAll(lookUpBaseList);
            finalListforReplication.addAll(lookUpVariantList);
            List<String> finalArrayListforReplication = new ArrayList<>(finalListforReplication);

			Resource adminPageRes = resourceResolver.getResource(
					searchConfig.getAdminPagePath() + CommonConstants.JCR_CONTENT);
			if (null != adminPageRes) {
				ValueMap valueMap = adminPageRes.adaptTo(ValueMap.class);
				if (valueMap.containsKey("slingJobEnabler") && valueMap.get("slingJobEnabler").equals("true")) {
					workflowHelperService.addJobForReplication(finalArrayListforReplication, jobManager,
							BDBDistributionServiceImpl.TOPIC, 100, CommonConstants.REPLICATION_ALL_NODES);
					workflowHelperService.addJobForIndexing(variantList, jobManager, BDBIndexingJob.TOPIC,
							CommonConstants.TYPE_COMMERCE_CONTENT);
				}
			}
            if (errorSKUs.size() == 0) {
                catalogResponse.addProperty(CommonConstants.STATUS, "Success");
            } else if (isPartialUpdate) {
                catalogResponse.addProperty(CommonConstants.STATUS, "Partial Update");
            } else {
                catalogResponse.addProperty(CommonConstants.STATUS, "Error");
            }
            catalogResponse.add(CommonConstants.ERROR_SKUS, errorSKUs);
            reponseJson.add(CommonConstants.CATALOG_RESPONSE, catalogResponse);

        } else {
            reponseJson.addProperty("ERROR", "Base product array is empty/absent");
            logger.debug("Base product array is empty/absent****** {}", jsonString);
        }
        logger.debug("CatalogStructureUpdateServiceImpl - setHPNodeProperty end");
        return reponseJson;
    }

    /**
     * Generating and Processing ON_DEMAND_TDS
     *
     * @param resourceResolver
     * @param skuName
     * @param basePath
     * @throws RepositoryException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerException
     * @throws SAXException
     */
    @Override
    public void generatingOnDemandTDS(ResourceResolver resourceResolver, String skuName, String basePath) throws RepositoryException, IOException, ParserConfigurationException, TransformerException, SAXException {
        logger.info("Entry generatingOnDemandTDS function of CatalogStructureUpdateServiceImpl");
        Resource variantRes = getProductFromLookUp(resourceResolver, skuName, CommonConstants.MATERIAL_NUMBER);
        String damPath = basePath.replace(CommonConstants.VAR_COMMERCE_PATH, CommonConstants.CONTENT_DAM)
                .replace(CommonConstants.PRODUCTS, CommonConstants.PRODUCTS_GLOBAL);
        damPath = new StringBuilder(damPath).append(CommonConstants.SINGLE_SLASH).append(CommonConstants.SLASH_PDF).append(skuName).append(CommonConstants.DOT_PDF).toString().trim();

        logger.debug("CatalogStructureUpdate On Demand TDS Generation SkuName : {}", skuName);
        logger.debug("CatalogStructureUpdate On Demand TDS Generation BasePath : {}", basePath);
        logger.debug("CatalogStructureUpdate On Demand TDS Generation DamPath : {}", damPath);

        ByteArrayOutputStream pdfStream = onDemandTdsGenerationService.getPdfStream(variantRes, resourceResolver, skuName, "");
        // Moving the file to dam path
        AssetManager assetMan = resourceResolver.adaptTo(AssetManager.class);
        Session session = resourceResolver.adaptTo(Session.class);
        ByteArrayInputStream assetInputStream = new ByteArrayInputStream(pdfStream.toByteArray());
        Asset asset = workflowHelperService.createAsset(assetMan, assetInputStream, damPath, CommonConstants.APPLICATION_PDF, resourceResolver, session);

        // setting Metadata
        Resource pdfMetadataResource = resourceResolver.getResource(damPath + CommonConstants.METADATAPATH);
        if (null != pdfMetadataResource) {
            Node currentNode = pdfMetadataResource.adaptTo(Node.class);
            // set tdsDocType and docType for indexing and PDP pages
            currentNode.setProperty(CommonConstants.DOC_TYPE, CommonConstants.DATA_TDS);
            currentNode.setProperty(CommonConstants.TDS_DOCUMENT_TYPE, CommonConstants.ON_DEMAND_TDS);
            // set docType metadata for scientific resource page
            currentNode.setProperty(CommonConstants.PDFX_DOC_TYPE, CommonConstants.DATA_TDS);
        }
        logger.info("Exit generatingOnDemandTDS function of CatalogStructureUpdateServiceImpl");
    }

    /**
     * Adds the properties for variant only json.
     *
     * @param resourceResolver the resource resolver
     * @param variantList the variant list
     * @param errorSKUs the error SK us
     * @param baseProductObj the base product obj
     * @param baseProductName the base product name
     * @param errorBaseProduct the error base product
     * @param isPartialUpdate the is partial update
     * @return true, if successful
     * @throws RepositoryException the repository exception
     */
    private boolean addPropertiesForVariantOnlyJson(ResourceResolver resourceResolver, ArrayList<String> variantList,
                                                    JsonArray errorSKUs, JsonObject baseProductObj, String baseProductName, JsonObject errorBaseProduct,
                                                    boolean isPartialUpdate, ArrayList<String> lookUpVarantList) throws RepositoryException {
        Resource baseProductResource = getProductFromLookUp(resourceResolver, baseProductName,
                CommonConstants.BASE_MATERIAL_NUMBER);
        if (null != baseProductResource) {
            isPartialUpdate = addHpStructuretoVariants(baseProductObj, baseProductResource.adaptTo(Node.class), resourceResolver,
                    variantList, errorSKUs, isPartialUpdate, true, lookUpVarantList);
        } else {
            errorBaseProduct.addProperty(CommonConstants.BASE_MATERIAL_NUMBER, baseProductName);
            errorBaseProduct.addProperty(CommonConstants.STATUS_MESSAGE,
                    "Base Product unavailable in Catalog Structure/Taxonomy.");
            errorSKUs.add(errorBaseProduct);
        }
        return isPartialUpdate;
    }

    /**
     * Creates the product taxonomy.
     *
     * @param resourceResolver the resource resolver
     * @param baseProductObj   the base product obj
     * @param baseProductName  the base product name
     * @return the string
     * @throws InvalidTagFormatException the invalid tag format exception
     * @throws RepositoryException       the repository exception
     */
	public String createProductTaxonomy(ResourceResolver resourceResolver, JsonObject baseProductObj,
			String baseProductName) throws InvalidTagFormatException, RepositoryException {
		TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
		String tagPath;
		String superCategory = CommonHelper.checkForMultipleSuperCategories(
				baseProductObj.get(CommonConstants.SUPER_CATEGORY).getAsString().trim());
		superCategory = CommonHelper.removeExtraCommas(superCategory.trim());
		tagPath = CommonConstants.BDB + CommonConstants.COMMA + superCategory.trim();
		String[] superCategoryLevel = tagPath.split(",(?![^()]*+\\))");
		StringBuilder tagPathBuilder = new StringBuilder();
		tagPathBuilder.append(CommonConstants.CQ_TAGS_PATH);
		for (int i = 0; i < superCategoryLevel.length; i++) {
			tagManager.createTag(tagPathBuilder
					.append(CommonHelper.nameValidator(superCategoryLevel[i].trim()))
					.toString(), superCategoryLevel[i].trim(), null, false);
			tagPathBuilder.append(CommonConstants.SINGLE_SLASH);
		}
        String prefixBasedProductPath = creatingPrefixStructure(baseProductName);
		tagPath = tagPathBuilder.append(prefixBasedProductPath).append(baseProductName).toString();
		tagManager.createTag(tagPath, baseProductName, null, false);
		logger.debug("CatalogStructureUpdateServiceImpl - createProductTaxonomy end");
		return tagPath;
	}

    /**
     * Create prefix based structure like 350xxx/3500xx/350001 ...
     * @param baseProductName
     * @return prefixPath
     */
    private String creatingPrefixStructure(String baseProductName) {
        StringBuilder prefixBasedProductPathBuilder = new StringBuilder();
        String materialNumber = baseProductName.split("_")[0];
        if(materialNumber.length() > 3) {
            prefixBasedProductPathBuilder.append(materialNumber, 0, materialNumber.length() - 3).append("xxx").append(CommonConstants.SINGLE_SLASH)
                    .append(materialNumber, 0, materialNumber.length() - 2).append("xx").append(CommonConstants.SINGLE_SLASH);
            return prefixBasedProductPathBuilder.toString();
        }
        return baseProductName;
    }

    /**
     * Adds the hp structure to variants.
     *
     * @param baseProductObj   the base product obj
     * @param baseProductNode  the base product node
     * @param resourceResolver the resource resolver
     * @param variantList      the variant list
     * @param errorSKUs        the error SK us
     * @param isPartialUpdate  the is partial update
     * @param isVariantOnly the is variant only
     * @return true, if successful
     * @throws AccessControlException the access control exception
     */
    public boolean addHpStructuretoVariants(JsonObject baseProductObj, Node baseProductNode,
                                            ResourceResolver resourceResolver, List<String> variantList, JsonArray errorSKUs, boolean isPartialUpdate,
                                            boolean isVariantOnly, List<String> lookupVariantList) {
        if (baseProductObj.has(CommonConstants.VARIANTS)) {
            JsonArray variantArray = baseProductObj.getAsJsonArray(CommonConstants.VARIANTS);
                Session session = resourceResolver.adaptTo(Session.class);
                for (JsonElement variant : variantArray) {
                    JsonObject variantObj = variant.getAsJsonObject();
                    if (variantObj.has(CommonConstants.MATERIAL_NUMBER)) {
                        String variantName = variantObj.get(CommonConstants.MATERIAL_NUMBER).getAsString().toLowerCase();
                        JsonObject errorVariant = new JsonObject();
                        try {
                            // check from lookUp if variant is present in taxonomy, else create it
                            String tagPath = getTagPathFromLookupForVariants(baseProductNode, resourceResolver,
                                    variantName);

                            if (null == resourceResolver.getResource(getCatalogPath(tagPath))) {
                                baseProductNode.addNode(variantName, searchConfig.getCatalogStructureNodeType());
                            }
                            Node variantNode = resourceResolver.getResource(getCatalogPath(tagPath)).adaptTo(Node.class);
                            variantNode.setProperty("isVariant", true);
                            variantList.add(variantNode.getPath());
                            lookupVariantList.add(getLookUpPathFromProductId(variantName, CommonConstants.MATERIAL_NUMBER));
                            logger.debug("CatalogStructureUpdateServiceImpl - variantNode {}", variantNode);
                            Calendar lastUpdated =  Calendar.getInstance();
                            variantNode.setProperty(com.day.cq.commons.jcr.JcrConstants.JCR_LASTMODIFIED, lastUpdated);
                            createProductNodeStructure(variantNode);
                            Node variantHpNode = variantNode.getNode(CommonConstants.HP_NODE);
                            setHpNodeProperties(variantObj, variantHpNode, CommonConstants.MATERIAL_NUMBER);

                            if (searchConfig.getAllowProductReplication().equals("true")) {
                                replicator.replicate(session, ReplicationActionType.ACTIVATE, variantNode.getPath());
                                replicator.replicate(session, ReplicationActionType.ACTIVATE,
                                        getTagPath(variantNode.getPath()));
                            }
                            isPartialUpdate = true;
                            if(baseProductNode.hasNode(CommonConstants.HP_NODE)) {
                            	Node hpNode = baseProductNode.getNode(CommonConstants.HP_NODE);
                                if(hpNode.hasProperty(CommonConstants.TDS_DOCUMENT_TYPE) && hpNode.getProperty(CommonConstants.TDS_DOCUMENT_TYPE).getString().equalsIgnoreCase(CommonConstants.ON_DEMAND_TDS)) {
                                	 generatingOnDemandTDS(resourceResolver, variantName, baseProductNode.getPath());
                                }
                            }
                        } catch (RepositoryException | InvalidTagFormatException | ReplicationException | IOException | ParserConfigurationException | TransformerException | SAXException e) {
                            logger.error("Failed to update variant to Catalog Structure {}", variantName);
                            errorVariant.addProperty(CommonConstants.BASE_MATERIAL_NUMBER,
                                    baseProductObj.get(CommonConstants.BASE_MATERIAL_NUMBER).getAsString().toLowerCase());
                            errorVariant.addProperty(CommonConstants.MATERIAL_NUMBER, variantName);
                            errorVariant.addProperty(CommonConstants.STATUS_MESSAGE,
                                    "Exception while adding Variant to Catalog Structure.");
                            errorSKUs.add(errorVariant);
                        }
                    }
                }
        }
        logger.debug("CatalogStructureUpdateServiceImpl - addHpStructuretoVariants end");
        return isPartialUpdate;
    }

    /**
     * Sets the hp node properties.
     *
     * @param productObj the product obj
     * @param hpNode     the hp node
     * @throws RepositoryException the repository exception
     */
	public void setHpNodeProperties(JsonObject productObj, Node hpNode, String productType) throws RepositoryException {
		for (Entry<String, JsonElement> baseProductEntry : productObj.entrySet()) {
			if (baseProductEntry.getValue().isJsonArray() || baseProductEntry.getValue().isJsonObject()) {
				hpNode.setProperty(baseProductEntry.getKey(), baseProductEntry.getValue().toString());
			} else {
				hpNode.setProperty(baseProductEntry.getKey(), baseProductEntry.getValue().getAsString());
			}
		}
		if (!hpNode.hasProperty("primarySuperCategory") && productType.equals(CommonConstants.BASE_MATERIAL_NUMBER)) {
			String primarySuperCategory = CommonHelper.checkForMultipleSuperCategories(
					productObj.get(CommonConstants.SUPER_CATEGORY).getAsString().trim());
			hpNode.setProperty("primarySuperCategory", primarySuperCategory);
		} else if (!hpNode.hasProperty("primarySuperCategory") && productType.equals(CommonConstants.MATERIAL_NUMBER)) {
			Node baseProductHpNode = hpNode.getParent().getParent().getNode(CommonConstants.HP_NODE);
			hpNode.setProperty("primarySuperCategory",
					baseProductHpNode.getProperty("primarySuperCategory").getString());
		}
		logger.debug("CatalogStructureUpdateServiceImpl - setHpNodeProperties end");
	}

    /**
     * Check if variant exists in taxonomy.
     *
     * @param baseProductNode  the base product node
     * @param resourceResolver the resource resolver
     * @param variantName      the variant name
     * @return true, if successful
     * @throws RepositoryException the repository exception
     */
    public boolean checkIfVariantExistsInTaxonomy(Node baseProductNode, ResourceResolver resourceResolver,
                                                  String variantName) throws RepositoryException {
        boolean variantTagCheck = false;
        Resource variantTagResource = resourceResolver
                .getResource(getTagPath(baseProductNode.getPath()) + CommonConstants.SINGLE_SLASH + variantName);
        if (null != variantTagResource) {
            variantTagCheck = true;
        }
        return variantTagCheck;
    }

    /**
     * Adds the variants in taxonomy.
     *
     * @param baseProductNode  the base product node
     * @param resourceResolver the resource resolver
     * @param variantName      the variant name
     * @return the string
     * @throws RepositoryException       the repository exception
     * @throws InvalidTagFormatException the invalid tag format exception
     */
    public String addVariantsInTaxonomy(Node baseProductNode, ResourceResolver resourceResolver, String variantName)
            throws RepositoryException, InvalidTagFormatException {

        TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
        String baserProductTagPath = getTagPath(baseProductNode.getPath());
        String variantTagPath = baserProductTagPath + CommonConstants.SINGLE_SLASH + variantName;
        tagManager.createTag(variantTagPath, variantName, null, false);
        logger.debug("CatalogStructureUpdateServiceImpl - addVariantsInTaxonomy end");
        return variantTagPath;
    }

    /**
     * Create base product node.
     *
     * @param resourceResolver the resource resolver
     * @param tagPath          the tag path
     * @return the node
     * @throws RepositoryException the repository exception
     */
    private Node createbaseProductNode(ResourceResolver resourceResolver, String tagPath) throws RepositoryException {
        Node productNode = null;
        String catalogPath = getCatalogPath(tagPath);
        Session session = resourceResolver.adaptTo(Session.class);
        Resource productResource = resourceResolver.getResource(catalogPath);
        if (null != productResource) {
            productNode = productResource.adaptTo(Node.class);
        } else {
            JcrUtil.createPath(catalogPath, searchConfig.getCatalogStructureNodeType(), session);
            //session.save();
            productNode = resourceResolver.getResource(catalogPath).adaptTo(Node.class);
            productNode.setProperty("isBaseProduct", "true");
        }
        return productNode;
    }

    /**
     * Gets the tag path.
     *
     * @param catalogPath the catalog path
     * @return the tag path
     */
    public String getTagPath(String catalogPath) {
        return catalogPath.replace(SolrSearchConstants.CATALOG_CONTENT_HIERARCHY_PATH,
                CommonConstants.CATALOG_TAG_HIERARCHY_PATH);
    }

    /**
     * Gets the catalog path.
     *
     * @param tagPath the tag path
     * @return the catalog path
     */
    public String getCatalogPath(String tagPath) {
        return tagPath.replace(CommonConstants.CATALOG_TAG_HIERARCHY_PATH,
                SolrSearchConstants.CATALOG_CONTENT_HIERARCHY_PATH);
    }

    /**
     * Gets the look up path from product id.
     *
     * @param productId   the product id
     * @param productType the product type
     * @return the look up path from product id
     */
    public String getLookUpPathFromProductId(String productId, String productType) {
        String basePath = StringUtils.EMPTY;
        if (productType.equals(CommonConstants.MATERIAL_NUMBER)) {
            basePath = "/content/commerce/lookup/variant/";
        } else if (productType.equals(CommonConstants.BASE_MATERIAL_NUMBER)) {
            basePath = "/content/commerce/lookup/baseProduct/";
            productId = productId.replace("_base", "");
        }
        return getLookupPath(productId.toLowerCase(), basePath);
    }

    /**
     * Gets the lookup path.
     *
     * @param productId the product id
     * @param basePath the base path
     * @return the lookup path
     */
    private String getLookupPath(String productId, String basePath) {
        StringBuilder builder = new StringBuilder(basePath);
        String finalPath;
        String temp = productId;
        if(temp.length() > 3)
            builder.append(productId, 0, productId.length()-3).append("f").append(CommonConstants.SINGLE_SLASH);
        builder.append(productId);
        finalPath = builder.toString();
        return finalPath;
    }

    /**
     * Gets the tag path from lookup for base product.
     *
     * @param baseProductObj   the base product obj
     * @param resourceResolver the resource resolver
     * @param productId        the product id
     * @param productType      the product type
     * @return the tag path from lookup for base product
     * @throws RepositoryException       the repository exception
     * @throws InvalidTagFormatException the invalid tag format exception
     */
    public String getTagPathFromLookupForBaseProduct(JsonObject baseProductObj, ResourceResolver resourceResolver,
                                                     String productId, String productType) throws RepositoryException, InvalidTagFormatException {
        String tagPath = StringUtils.EMPTY;
        if (baseProductObj.has(CommonConstants.SUPER_CATEGORY)) {
            String superCategory = CommonHelper.checkForMultipleSuperCategories(
                    baseProductObj.get(CommonConstants.SUPER_CATEGORY).getAsString().trim());
            if (superCategory.isEmpty()) {
                return FALSE;
            }
            String lookUpPath = getLookUpPathFromProductId(productId, productType);
            Session session = resourceResolver.adaptTo(Session.class);
            Resource lookUpPathResource = resourceResolver.getResource(lookUpPath);
            if (null == lookUpPathResource) {
                // if lookup path is absent then creating it
                JcrUtil.createPath(lookUpPath, searchConfig.getCatalogStructureNodeType(), session);
                lookUpPathResource = resourceResolver.getResource(lookUpPath);
            }
            Node node = lookUpPathResource.adaptTo(Node.class);

            if (node.hasProperty(CATALOG_PATH)) {
                // checking if lookup path has product path in property
                tagPath = getTagPath(node.getProperty(CATALOG_PATH).getString());
            } else {
                // if lookup path does not have product path then creating the tag and setting
                // property
                tagPath = createProductTaxonomy(resourceResolver, baseProductObj, productId);
                node.setProperty(CATALOG_PATH, getCatalogPath(tagPath));
                node.setProperty("isBaseProductLookup", "true");
            }
            //session.save();
        }
        return tagPath;
    }

    /**
     * Gets the tag path from lookup for variants.
     *
     * @param baseProductNode  the base product node
     * @param resourceResolver the resource resolver
     * @param productId        the product id
     * @return the tag path from lookup for variants
     * @throws RepositoryException       the repository exception
     * @throws InvalidTagFormatException the invalid tag format exception
     */
    public String getTagPathFromLookupForVariants(Node baseProductNode, ResourceResolver resourceResolver,
                                                  String productId) throws RepositoryException, InvalidTagFormatException {
        String tagPath;
        String lookUpPath = getLookUpPathFromProductId(productId, CommonConstants.MATERIAL_NUMBER);
        Session session = resourceResolver.adaptTo(Session.class);
        Resource lookUpPathResource = resourceResolver.getResource(lookUpPath);
        if (null == lookUpPathResource) {
            // if lookup path is absent then creating it
            JcrUtil.createPath(lookUpPath, searchConfig.getCatalogStructureNodeType(), session);
            lookUpPathResource = resourceResolver.getResource(lookUpPath);
        }
        Node node = lookUpPathResource.adaptTo(Node.class);

        if (node.hasProperty(CATALOG_PATH)) {
            // checking if lookup path has product path in property
            tagPath = getTagPath(node.getProperty(CATALOG_PATH).getString());
        } else {
            // if lookup path does not have product path then creating the tag and setting
            // property
            tagPath = addVariantsInTaxonomy(baseProductNode, resourceResolver, productId);
            node.setProperty(CATALOG_PATH, getCatalogPath(tagPath));
            node.setProperty("isVariantLookup", "true");
        }
        //session.save();
        return tagPath;
    }

    /**
     * Gets the product from look up.
     *
     * @param resourceResolver the resource resolver
     * @param productId        the product id
     * @param productType      the product type
     * @return the product from look up
     * @throws RepositoryException   the repository exception
     */

    @Override
    public Resource getProductFromLookUp(ResourceResolver resourceResolver, String productId, String productType)
            throws RepositoryException {
        String lookUpPath = getLookUpPathFromProductId(productId, productType);
        if(null != resourceResolver) {
        	 Resource lookUpResource = resourceResolver.getResource(lookUpPath);
             if (null != lookUpResource) {
                 ValueMap valueMap = lookUpResource.adaptTo(ValueMap.class);

                 if (valueMap.containsKey(CATALOG_PATH)) {
                     // checking if lookup path has product path in property
                     String catalogPath = valueMap.get(CATALOG_PATH).toString();
                     return resourceResolver.getResource(catalogPath);
                 }
             }
        }

        return null;
    }

    /**
     * Iterate over json for variant list.
     *
     * @param resourceResolver the resource resolver
     * @param jsonString the json string
     * @return the array list
     */
    public ArrayList<String> iterateOverJsonForVariantList(ResourceResolver resourceResolver, String jsonString) {
        JsonObject jsonObj = new JsonParser().parse(jsonString).getAsJsonObject();
        JsonArray baseProductArray = jsonObj.getAsJsonArray(CommonConstants.PRODUCTS);
        ArrayList<String> variantList = new ArrayList<>();
        for (JsonElement baseProduct : baseProductArray) {
            JsonObject baseProductObj = baseProduct.getAsJsonObject();
            if (baseProductObj.has(CommonConstants.VARIANTS)) {
                JsonArray variantArray = baseProductObj.getAsJsonArray(CommonConstants.VARIANTS);
                for (JsonElement variant : variantArray) {
                    JsonObject variantObj = variant.getAsJsonObject();
                    if (variantObj.has(CommonConstants.MATERIAL_NUMBER)) {
                        String variantName = variantObj.get(CommonConstants.MATERIAL_NUMBER).getAsString().toLowerCase();
                        try {
                            Resource variantRes = getProductFromLookUp(resourceResolver, variantName,
                                    CommonConstants.MATERIAL_NUMBER);
                            if (null != variantRes) {
                                variantList.add(variantRes.getPath());
                            }
                        } catch (RepositoryException e) {
                            logger.error("Getting exception while iterating over json for variant list {}",e);
                        }
                    }
                }
            }
        }
        return variantList;
    }

    /**
     * Replicate variants.
     *
     * @param resourceResolver the resource resolver
     * @param variantList the variant list
     */
    public void replicateVariants(ResourceResolver resourceResolver, ArrayList<String> variantList) {
        Session session = resourceResolver.adaptTo(Session.class);
        for(String variant : variantList) {
            try {
                replicator.replicate(session, ReplicationActionType.ACTIVATE, variant);
            } catch (ReplicationException e) {
                logger.error("Failed to replicate variant", variant);
            }
        }
    }

    /**
     * Index and replicate products.
     *
     * @param resourceResolver the resource resolver
     * @param jsonString the json string
     */
    @Override
    public void indexAndReplicateProducts(ResourceResolver resourceResolver, String jsonString) {

        ArrayList<String> variantList = iterateOverJsonForVariantList(resourceResolver, jsonString);
        replicateVariants(resourceResolver, variantList);
        try {
            solrSearchService.indexProductDataToSolr(resourceResolver, variantList);
        } catch (SolrServerException | IOException | RepositoryException | LoginException e) {
            logger.error("Failed SOLR Indexing {}", e);
        }
    }

}
