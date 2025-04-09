package com.bdb.aem.core.pdmigration;

import com.adobe.acs.commons.fam.ActionManager;
import com.adobe.acs.commons.mcp.ProcessDefinition;
import com.adobe.acs.commons.mcp.ProcessInstance;
import com.adobe.acs.commons.mcp.form.CheckboxComponent;
import com.adobe.acs.commons.mcp.form.FormField;
import com.adobe.acs.commons.mcp.form.PathfieldComponent;
import com.adobe.acs.commons.mcp.model.ManagedProcess;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.ReplicationStatus;
import com.day.cq.replication.Replicator;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class QualifyingProductsTagsUpdater extends ProcessDefinition {

    public static final String SKU_TAG = "skuTag";
    public static final String UNDERSCOREBASE = "_base";
    public static final String SLASH = "/";
    public static final String XXX = "xxx";
    public static final String XX = "xx";
    public static final String JCR_CONTENT = "/jcr:content";
    public static final String COMPONENT_QUALIFYING_PRODUCTS = "bdb-aem/proxy/components/content/qualifyingProducts";
    public static final String PATH = "path";
    public static final String PROPERTY = "property";
    public static final String PROPERTY_VALUE = "property.value";
    public static final String P_LIMIT = "p.limit";
    private static final Logger logger = LoggerFactory.getLogger(QualifyingProductsTagsUpdater.class);
    private final Replicator replicator;
    ManagedProcess processInfo;
    @FormField(name = "Content Root Path",
            description = "Path to the Content Pages Root page. ",
            component = PathfieldComponent.NodeSelectComponent.class)
    private String contentRootPath;

    @FormField(name = "Skip Activation", component = CheckboxComponent.class, options = {"horizontal", "default=false"})
    private boolean skipActivation;

    public QualifyingProductsTagsUpdater(Replicator replicator) {
        this.replicator = replicator;
    }

    @Override
    public void buildProcess(ProcessInstance instance, ResourceResolver rr) throws LoginException, RepositoryException {
        logger.info("Inside buildProcess Method");
        processInfo = instance.getInfo();
        instance.getInfo().setDescription("Running Qualifying Products tags updater for content root -  " + contentRootPath);

        instance.defineCriticalAction("Update Tags", rr, this::updateTags);

    }

    protected void updateTags(ActionManager manager) {
        manager.deferredWithResolver(this::updateComponentTags);
    }

    protected void updateComponentTags(ResourceResolver resourceResolver) throws RepositoryException, ReplicationException {
        SearchResult result = findComponentInstances(resourceResolver);
        if (null != result && result.getTotalMatches() > 0) {
            for (Hit hit : result.getHits()) {
                Resource resource = resourceResolver.getResource(hit.getPath());
                Resource cellsResource = resource.getChild("cells");
                if (cellsResource != null && cellsResource.hasChildren()) {
                    Iterable<Resource> iterable = cellsResource.getChildren();
                    Iterator<Resource> iterator = iterable.iterator();
                    while (iterator.hasNext()) {
                        Resource itemResource = iterator.next();
                        ModifiableValueMap properties = itemResource.adaptTo(ModifiableValueMap.class);
                        String skuTag = properties.get(SKU_TAG, String.class);
                        String[] tagStringArr = skuTag.split(SLASH);
                        String[] baseProdName = Arrays.stream(tagStringArr).filter(s -> s.contains(UNDERSCOREBASE)).toArray(String[]::new);
                        String baseProductFolderName = baseProdName[0].replace(UNDERSCOREBASE, "");
                        String newBasePdPath = baseProductFolderName.substring(0, baseProductFolderName.length() - 3) +
                                XXX +
                                SLASH +
                                baseProductFolderName.substring(0, baseProductFolderName.length() - 2) +
                                XX +
                                SLASH +
                                baseProdName[0];

                        if (!skuTag.contains(newBasePdPath)) {
                            String updateSkuTag = skuTag.replace(baseProdName[0], newBasePdPath);
                            properties.put(SKU_TAG, updateSkuTag);
                        }

                    }

                }
                if (!skipActivation && !hit.getPath().contains("language-masters") && !hit.getPath().contains("msm_moved")) {
                    handleActivation(resourceResolver, hit.getPath());
                }
            }
        }


    }

    private void handleActivation(ResourceResolver resolver, String componentPath) throws ReplicationException {

        String pagePath = componentPath.substring(0, componentPath.lastIndexOf(JCR_CONTENT));
        ReplicationStatus repStatus = resolver.getResource(pagePath).adaptTo(ReplicationStatus.class);
        if (repStatus != null) {
            if (repStatus.isActivated() && repStatus.getLastReplicationAction().equals(ReplicationActionType.ACTIVATE)) {
                replicator.replicate(resolver.adaptTo(Session.class), ReplicationActionType.ACTIVATE, pagePath);
            }
        }

    }

    private SearchResult findComponentInstances(ResourceResolver resourceResolver) {
        Map<String, Object> params = new HashMap<>();
        params.put(PATH, contentRootPath);

        params.put(P_LIMIT, "-1");
        params.put(PROPERTY, "sling:resourceType");
        params.put(PROPERTY_VALUE, COMPONENT_QUALIFYING_PRODUCTS);

        QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);
        Session session = resourceResolver.adaptTo(Session.class);
        Query query = null;
        if (queryBuilder != null) {
            query = queryBuilder.createQuery(PredicateGroup.create(params), session);
        }

        return null==query ? null : query.getResult();

    }


    @Override
    public void storeReport(ProcessInstance instance, ResourceResolver rr) throws RepositoryException, PersistenceException {
        //Nothing to do here.
    }

    @Override
    public void init() throws RepositoryException {
        //Nothing to do here.
    }
}
