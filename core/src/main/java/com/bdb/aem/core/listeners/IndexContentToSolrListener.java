package com.bdb.aem.core.listeners;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.IndexContentListenerService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.services.impl.BDBAkamaiPurgeJob;
import com.bdb.aem.core.services.impl.BDBIndexingJob;
import com.bdb.aem.core.services.impl.BDBIndexingJobForOnDemandTDS;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.replication.*;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonObject;
import org.apache.sling.api.resource.*;
import org.apache.sling.event.jobs.JobManager;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.*;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This listener indexes/un-indexes a page or an asset to solr on
 * publish/un-publish and delete.
 *
 * @see IndexContentToSolrEvent
 */
@Component(service = {EventHandler.class, IndexContentListenerService.class}, immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE, property = {Constants.SERVICE_DESCRIPTION + "=Index content to SOLR on page replication", EventConstants.EVENT_TOPIC + "=" + ReplicationAction.EVENT_TOPIC})
@Designate(ocd = IndexContentToSolrListener.Configuration.class)
public class IndexContentToSolrListener implements EventHandler, IndexContentListenerService {

    /**
     * The type png image.
     */
    public static final String TYPE_PNG_IMAGE = "PNG Image";
    /**
     * The preview Agent.
     */
    private static final String PREVIEW_AGENT = "preview";
    /**
     * The agent ID.
     */
    private static final String AGENT_ID = "agentIds";
    /**
     * The Adobe PDF.
     */
    private static final String CONTENT_PAGE = "contentPage";
    /**
     * The Adobe PDF.
     */
    private static final String ADOBE_PDF = "Adobe PDF";
    /**
     * relative path to product node
     */
    private static final String VAR_PRODUCT_FOLDER = "products/";
    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * The replicator service.
     */
    @Reference
    Replicator replicator;
    /**
     * The solr search service.
     */
    @Reference
    SolrSearchService solrSearchService;
    /**
     * The search config.
     */
    @Reference
    BDBSearchEndpointService searchConfig;
    /**
     * The workflow helper service.
     */
    @Reference
    WorkflowHelperService workflowHelperService;
    /**
     * The base page path.
     */
    String basePagePath;
    /**
     * The asset paths.
     */
    String[] assetPaths;
    /**
     * The scientific resources path.
     */
    String scientificResourcesPath;
    /**
     * The resolver factory.
     */
    @Reference
    private ResourceResolverFactory resolverFactory;
    /**
     * The job manager.
     */
    @Reference
    private JobManager jobManager;

    /**
     * Activate.
     *
     * @param config the config
     */
    @Activate
    @Modified
    protected final void activate(Configuration config) {
        this.basePagePath = config.basePagePath();
        this.assetPaths = config.baseAssetPath();
        this.scientificResourcesPath = config.getScientificResourceFolder();
    }

    /**
     * Handle event.
     *
     * @param event the event
     */
    public void handleEvent(final Event event) {
        long startTime = System.currentTimeMillis();
        ResourceResolver resolver = null;
        ReplicationActionType replicationType = ReplicationAction.fromEvent(event).getType();
        String resourcePath = ReplicationAction.fromEvent(event).getPath();

        /** Checking event agent ID **/
        List<String> agentIds = (List<String>) event.getProperty(AGENT_ID);
        Boolean isAgentIDPreview = isAgentIdPreview(agentIds);
        logger.debug("Event handler captures replication action {} on path {} with agentIds {}", replicationType, resourcePath, agentIds);

        /** Processing only for publish Agent **/
        if (Boolean.FALSE.equals(isAgentIDPreview)) {
            try {
                resolver = CommonHelper.getServiceResolver(resolverFactory);
                Resource adminPageRes = resolver.getResource(searchConfig.getAdminPagePath() + CommonConstants.JCR_CONTENT);
                if (null != adminPageRes) {
                    ValueMap valueMap = adminPageRes.adaptTo(ValueMap.class);
                    if (valueMap.containsKey("slingJobEnabler") && valueMap.get("slingJobEnabler").equals("true")) {
                        processReplicationAction(replicationType, resourcePath, resolver);
                    }
                }
            } catch (LoginException e) {
                logger.error("Exception is ", e);
            } finally {
                CommonHelper.closeResourceResolver(resolver);
            }
        } else {
            /** Only processing initial request for Asset and skipping other request from preview agent **/
            logger.debug("Skipping event handler process as agent ID is {}", agentIds);
        }

        long endtime = System.currentTimeMillis();
        logger.error("Total time when replication Event is {} at path {} is {}", replicationType, resourcePath, startTime - endtime);
    }

    /**
     * function to perform activation and deactivation activities based on the replicationType
     *
     * @param ReplicationActionType the replicationType
     * @param ResourceResolver      the resourcePath
     * @param String                the resourcePath
     * @return
     */
    private void processReplicationAction(ReplicationActionType replicationType, String resourcePath, ResourceResolver resolver) {
        ArrayList<String> pathList = new ArrayList<>();
        pathList.add(resourcePath);

        if (replicationType.equals(ReplicationActionType.ACTIVATE)) {
            handleActivateAction(resourcePath, resolver, pathList);
        } else if (replicationType.equals(ReplicationActionType.DEACTIVATE)) {
            handleDeactivateAction(resourcePath, resolver, pathList);
        }
    }

    /**
     * function to handle replication action
     *
     * @param String            the resourcePath
     * @param ResourceResolver  the resourcePath
     * @param ArrayList<String> pathList
     * @return
     */
    private void handleActivateAction(String resourcePath, ResourceResolver resolver, ArrayList<String> pathList) {
        try {
            if (resourcePath.startsWith(basePagePath)) {
                if (!checkIfPageIsSolrIndexable(resolver, resourcePath)) {
                    //un-indexing the page if the solrUnindexable property is enabled.
                    solrSearchService.unIndexContent(resourcePath, CONTENT_PAGE);
                } else {
                    logger.debug("Triggered activate on {}", resourcePath);
                    indexContentPage(resolver, resourcePath);
                }
            } else if (checkIfValidAssetPath(resourcePath)) {
                indexAsset(resourcePath, resolver, jobManager, pathList);
            }
        } catch (LoginException e) {
            logger.error("Login Exception occured due to ", e);
        }
    }

    /**
     * function to handle deactivate action
     *
     * @param String            the resourcePath
     * @param ResourceResolver  the resourcePath
     * @param ArrayList<String> pathList
     * @return
     */
    private void handleDeactivateAction(String resourcePath, ResourceResolver resolver, ArrayList<String> pathList) {
        try {
            if (resourcePath.startsWith(basePagePath)) {
                logger.debug("Triggered deactivate on {}", resourcePath);
                solrSearchService.unIndexContent(resourcePath, CONTENT_PAGE);
            } else if (checkIfValidAssetPath(resourcePath)) {
                unIndexAsset(resourcePath, resolver, jobManager, pathList);
            }
        } catch (LoginException e) {
            logger.error("Login Exception occured due to ", e);
        }
    }

    /**
     * Method for adding a job for indexing based on the assetType (pdf or png).
     *
     * @param String            the resourcePath
     * @param ResourceResolver  the resolver
     * @param JobManager        the jobManager
     * @param ArrayList<String> pathList
     */
    private void indexAsset(String resourcePath, ResourceResolver resolver, JobManager jobManager, ArrayList<String> pathList) {
        Asset asset = resolver.getResource(resourcePath).adaptTo(Asset.class);
        String assetType = asset != null ? solrSearchService.getTypeFromAssets(asset) : null;
        try {
            if (assetType != null && isIndexAble(resourcePath)) {
                if (ADOBE_PDF.equals(assetType)) {
                    workflowHelperService.addJobForIndexing(pathList, jobManager, BDBIndexingJob.TOPIC, CommonConstants.TYPE_PDF);
                } else if (TYPE_PNG_IMAGE.equals(assetType)) {
                    indexPngImage(resourcePath, pathList, resolver, jobManager);
                }
                /* Auto-Publishing to preview */
                publishToPreview(resolver, pathList);
                workflowHelperService.addJobForAkamaiPurge(pathList, jobManager, BDBAkamaiPurgeJob.TOPIC);
            }
        } catch (ReplicationException e) {
            logger.error("Exception occured during asset replication", e);
        }
    }

    /**
     * Method for adding a job for un-indexing based on the assetType (pdf or png).
     *
     * @param String            the resourcePath
     * @param ResourceResolver  the resolver
     * @param JobManager        the jobManager
     * @param ArrayList<String> pathList
     */
    private void unIndexAsset(String resourcePath, ResourceResolver resolver, JobManager jobManager, ArrayList<String> pathList) {
        Asset asset = resolver.getResource(resourcePath).adaptTo(Asset.class);
        String assetType = asset != null ? solrSearchService.getTypeFromAssets(asset) : null;
        try {
            if (assetType != null && isIndexAble(resourcePath)) {
                if (ADOBE_PDF.equals(assetType)) {
                    solrSearchService.unIndexContent(resourcePath, "pdfAsset");
                } else if (TYPE_PNG_IMAGE.equals(assetType)) {
                    // Getting the metadata node
                    String assetMetadataPath = resourcePath + CommonConstants.METADATAPATH;
                    Resource assetMetadataResource = resolver.getResource(assetMetadataPath);
                    if (null != assetMetadataResource) {
                        ValueMap assetValueMap = assetMetadataResource.adaptTo(ValueMap.class);
                        if (null != assetValueMap && assetValueMap.containsKey(CommonConstants.VIDEO_TYPE)) {
                            unIndexVideo(resourcePath);
                        } else {
                            Resource assetJCRResource = assetMetadataResource.getParent();
                            unIndexPngImage(assetJCRResource, pathList, resolver, jobManager);
                        }
                    }
                }
                /* Auto un-publishing from preview */
                unpublishedFromPreview(resolver, resourcePath);
                workflowHelperService.addJobForAkamaiPurge(pathList, jobManager, BDBAkamaiPurgeJob.TOPIC);
            }
        } catch (ReplicationException | LoginException e) {
            logger.error("Exception occured during asset deactivation", e);
        }
    }

    /**
     * this function un-index the video from solr.
     *
     * @param resourcePath
     * @throws LoginException
     */
    private void unIndexVideo(String resourcePath) throws LoginException {
        solrSearchService.unIndexContent(resourcePath, "pdfAsset");
    }

    /**
     * function to check whether an asset is index able or not based on resource path
     *
     * @param resourcePath
     * @return
     */
    private boolean isIndexAble(String resourcePath) {
        if (resourcePath.startsWith(scientificResourcesPath)) return true;
        for (String assetPath : assetPaths) {
            if (resourcePath.startsWith(assetPath)) {
                return true;
            }
        }
        return false;
    }

    /**
     * function to unpublished assets from preview
     *
     * @param resolver
     * @param resourcePath
     * @throws ReplicationException
     */
    private void unpublishedFromPreview(ResourceResolver resolver, String resourcePath) throws ReplicationException {
        ReplicationOptions options = new ReplicationOptions();
        Session session = resolver.adaptTo(Session.class);
        options.setFilter(agent -> agent.getId().equals(PREVIEW_AGENT));
        replicator.replicate(session, ReplicationActionType.DEACTIVATE, resourcePath, options);
    }

    /**
     * function to replicate to preview
     *
     * @param resolver
     * @param pathList
     * @throws ReplicationException
     */
    private void publishToPreview(ResourceResolver resolver, ArrayList<String> pathList) throws ReplicationException {
        ReplicationOptions options = new ReplicationOptions();
        Session session = resolver.adaptTo(Session.class);
        options.setFilter(agent -> agent.getId().equals(PREVIEW_AGENT));
        for (String path : pathList) {
            replicator.replicate(session, ReplicationActionType.ACTIVATE, path, options);
        }
    }

    /**
     * Check if page is solr indexable.
     *
     * @param resourceResolver the resource resolver
     * @param pagePath         the page path
     * @return true, if successful
     */
    public boolean checkIfPageIsSolrIndexable(ResourceResolver resourceResolver, String pagePath) {
        if (!pagePath.startsWith(SolrSearchConstants.LANGUAGE_MASTERS_PAGE_PATH)) {
            Resource pageResource = resourceResolver.getResource(pagePath + CommonConstants.JCR_CONTENT);
            String[] pathSplit = pagePath.split("/");
            if (pathSplit.length > 6 && null != pageResource && !pathSplit[6].equals("datapage") && !pathSplit[6].equals("errorpages")) {
                ValueMap properties = pageResource.adaptTo(ValueMap.class);
                if (properties.containsKey(SolrSearchConstants.SOLR_UNINDEXABLE) && !properties.get(SolrSearchConstants.SOLR_UNINDEXABLE).equals("true")) {
                    return true;
                } else return !properties.containsKey(SolrSearchConstants.SOLR_UNINDEXABLE);
            }
        }
        return false;
    }

    /**
     * Method for checking if it is a preview agent ID.
     *
     * @param List<String> the agentIds
     *                     returns boolean
     */
    private boolean isAgentIdPreview(List<String> agentIds) {
        for (String agentId : agentIds) {
            if (agentId.contains(PREVIEW_AGENT)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method for indexing content pages on solr.
     *
     * @param ResourceResolver the resolver
     * @param String           the resourcePath
     *                         <p>
     *                         indexes content pages to solr
     */

    private void indexContentPage(ResourceResolver resolver, String resourcePath) {
        // check if resourcePath is valid cq:Page
        Resource resource = resolver.getResource(resourcePath);
        try {
            if (resource != null) {
                String primaryType = resource.getResourceType();
                Page page = resource.adaptTo(Page.class);
                Resource resourceResolver = resolver.getResource(resourcePath).getParent();
                // get the parent page path
                String parentPath = resourceResolver.getPath() + CommonConstants.JCR_CONTENT;
                Resource parentResource = resolver.getResource(parentPath);
                ValueMap valueMapParent = parentResource.adaptTo(ValueMap.class);

                if (NameConstants.NT_PAGE.equals(primaryType)) {
                    // un-indexing the page if the solrChildPagesUnindexable property is true for the parent page
                    if (valueMapParent.containsKey(SolrSearchConstants.SOLR_CHILD_PAGES_UNINDEXABLE) && valueMapParent.get(SolrSearchConstants.SOLR_CHILD_PAGES_UNINDEXABLE).equals("true")) {
                        solrSearchService.unIndexContent(resourcePath, CONTENT_PAGE);
                    } else {
                        JsonObject pageIndexObject = solrSearchService.createPageMetadataObject(resource, resolver);
                        String country = CommonHelper.getCountry(page);
                        HttpSolrClient server = solrSearchService.solrClient(country);
                        solrSearchService.indexPagesToSolr(pageIndexObject, server);
                    }
                    if (!checkIfPageIsSolrIndexable(resolver, resourcePath)) {
                        solrSearchService.unIndexContent(resourcePath, CONTENT_PAGE);
                    }
                }
            }
        } catch (SolrServerException | LoginException | IOException e) {
            logger.error("Exception occured due to ", e);
        }

    }

    /**
     * Method for indexing images on solr.
     *
     * @param String            the resourcePath
     * @param ResourceResolver  the resolver
     * @param ArrayList<String> pathList
     * @param JobManager        the jobManager
     *                          <p>
     *                          This method triggers the product indexing because the thumbnail_t is a field of product Solr JSON object.
     */
    private void indexPngImage(String resourcePath, List<String> pathList, ResourceResolver resolver, JobManager jobManager) {
        // Getting the image jcr:content node path
        String imageJcrPath = resourcePath + CommonConstants.JCR_CONTENT;
        Resource imageMetadataResource = resolver.getResource(imageJcrPath);
        try {
            if (imageMetadataResource != null) {
                ModifiableValueMap properties = imageMetadataResource.adaptTo(ModifiableValueMap.class);
                // Adding a customReplicationProperty as PUBLISHED
                properties.put(CommonConstants.CUSTOM_REPLICATION_STATUS, CommonConstants.PUBLISHED);
                resolver.commit();
                logger.debug("Indexing initiated for published image {}", pathList);

                // Indexing product in Solr as thumbnail_t is a field of product Solr JSON object
                workflowHelperService.addJobForIndexing(pathList, jobManager, BDBIndexingJob.TOPIC, CommonConstants.TYPE_PNG_IMAGE);
                for (String path : pathList) {
                    // Generating onDemandTDS on image activation
                    processPathForOnDemandTdsGeneration(path, pathList, resolver);
                }
                logger.debug("Indexing completed for published image {}", pathList);
            }

            // Add job for video thumbnail indexing
            workflowHelperService.addJobForIndexing(pathList, jobManager, BDBIndexingJob.TOPIC, CommonConstants.TYPE_VIDEO_THUMBNAIL);
        } catch (IOException e) {
            logger.error("IOException occured due to ", e);
        }

    }

    /**
     * Method for un-indexing images on solr.
     *
     * @param imageMetadataResource
     * @param pathList
     * @param resolver
     * @param jobManager            This method triggers the product indexing because the thumbnail_t is a field of product Solr JSON object.
     */
    private void unIndexPngImage(Resource imageMetadataResource, List<String> pathList, ResourceResolver resolver, JobManager jobManager) {
        try {
            if (null != imageMetadataResource) {
                ModifiableValueMap properties = imageMetadataResource.adaptTo(ModifiableValueMap.class);
                // Adding a customReplication property as UN-PUBLISHED
                properties.put(CommonConstants.CUSTOM_REPLICATION_STATUS, CommonConstants.UNPUBLISHED);
                resolver.commit();
                logger.debug("Indexing initaited for unpublished image {}", pathList);
                // indexing product in solr as thumbnail_t is a field of product solr json object
                workflowHelperService.addJobForIndexing(pathList, jobManager, BDBIndexingJob.TOPIC, CommonConstants.TYPE_PNG_IMAGE);
                for (String path : pathList) {
                    // Generating onDemandTDS on image deactivation
                    processPathForOnDemandTdsGeneration(path, pathList, resolver);
                }
                logger.debug("Indexing completed for unpublished image {}", pathList);
            }
        } catch (IOException e) {
            logger.error("IOException occured due to ", e);
        }
    }

    /**
     * Method for adding a job for generating OnDemandTDS.
     *
     * @param String            the path
     * @param ArrayList<String> pathList
     * @param ResourceResolver  the resolver
     * @return
     */
    private void processPathForOnDemandTdsGeneration(String path, List<String> pathList, ResourceResolver resolver) {
        String assetPath = path.replace(CommonConstants.DAM_PRODUCT_PATH, CommonConstants.COMMERCE_PATH + VAR_PRODUCT_FOLDER);
        String basePath = assetPath.substring(0, assetPath.lastIndexOf("/"));
        Resource base = resolver.getResource(basePath);

        if (base != null && base.hasChildren()) {
            for (Resource itemResource : base.getChildren()) {
                if (isOnDemandTDS(itemResource)) {
                    workflowHelperService.addJobForIndexingOnDemandTDS(pathList, jobManager, BDBIndexingJobForOnDemandTDS.TOPIC, CommonConstants.TYPE_PDF);
                }
            }
        }
    }

    /**
     * Method for checking if the product HP node contains the property tdsDocumentType
     * and its value is ON_DEMAND_TDS.
     *
     * @param Resource itemResource
     * @return boolean
     */
    private boolean isOnDemandTDS(Resource itemResource) {
        if (itemResource.getName().equalsIgnoreCase("hp")) {
            ValueMap vm = itemResource.adaptTo(ValueMap.class);
            return "ON_DEMAND_TDS".equalsIgnoreCase(vm.get("tdsDocumentType", String.class));
        }
        return false;
    }

    /**
     * function to check asset path
     *
     * @param resourcePath
     * @return
     */
    public boolean checkIfValidAssetPath(String resourcePath) {
        return resourcePath.startsWith(CommonConstants.CONST_BDB_DAM_ROOT_PATH);
    }

    /**
     * Gets the base page path.
     *
     * @return the base page path
     */
    @Override
    public String getBasePagePath() {
        return basePagePath;
    }

    /**
     * Gets the asset path.
     *
     * @return the asset path
     */
    @Override
    public String[] getAssetPath() {
        return assetPaths;
    }

    /**
     * Gets the scientific res path.
     *
     * @return the scientific res path
     */
    @Override
    public String getScientificResPath() {
        return scientificResourcesPath;
    }

    /**
     * The Interface Configuration.
     */
    @ObjectClassDefinition(name = "BDB Component Disabler")
    public @interface Configuration {

        /**
         * Base page path.
         *
         * @return the string
         */
        @AttributeDefinition(name = "Content page path", defaultValue = "/content/bdb", description = "Content page path from where solr has to index the pages") String basePagePath();

        /**
         * Base asset path.
         *
         * @return the string
         */
        @AttributeDefinition(name = "Asset Base path", defaultValue = "/content/dam/bdb/products", description = "Assets Root path from where solr has to index the assets") String[] baseAssetPath();

        /**
         * getScientificResourceFolder.
         *
         * @return the string
         */
        @AttributeDefinition(name = "getScientificResourceFolder", description = "Provide the Path for scientific resource folder", type = AttributeType.STRING) String getScientificResourceFolder() default "/content/dam/bdb/ScientificResources/images";

    }

}