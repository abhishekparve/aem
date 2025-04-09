package com.bdb.aem.core.pdmigration;

import com.adobe.acs.commons.fam.ActionManager;
import com.adobe.acs.commons.mcp.ProcessDefinition;
import com.adobe.acs.commons.mcp.ProcessInstance;

import com.adobe.acs.commons.mcp.form.FormField;
import com.adobe.acs.commons.mcp.form.PathfieldComponent;
import com.adobe.acs.commons.mcp.model.ManagedProcess;

import com.day.cq.replication.ReplicationException;
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

import java.util.HashMap;

import java.util.Map;

@Component
public class ProcessedAssetCleanUpScript extends ProcessDefinition {

	public static final String TYPE = "type";
	public static final String RELATIVE_DATE_RANGE_PROP = "relativedaterange.property";
	public static final String RELATIVE_DATE_RANGE_UPPER_BOUND = "relativedaterange.upperBound";
	public static final String PATH = "path";
	public static final String P_LIMIT = "p.limit";
	private static final Logger logger = LoggerFactory.getLogger(ProcessedAssetCleanUpScript.class);
	 private final Replicator replicator;
	 
	ManagedProcess processInfo;
	@FormField(name = "Asset Root Path", description = "Path to the Asset Root folder. ", component = PathfieldComponent.NodeSelectComponent.class)
	private String assetRootPath;
	
	 public ProcessedAssetCleanUpScript(Replicator replicator) {
	        this.replicator = replicator;
	    }

	@Override
	public void buildProcess(ProcessInstance instance, ResourceResolver rr) throws LoginException, RepositoryException {
		logger.info("Inside buildProcess Method");
		processInfo = instance.getInfo();
		instance.getInfo().setDescription("Running Processed Asset cleanup script for asset root -  " + assetRootPath);

		instance.defineCriticalAction("Clean old assets", rr, this::deleteAssets);

	}

	protected void deleteAssets(ActionManager manager) {
		manager.deferredWithResolver(this::deleteOldAssets);
	}

	protected void deleteOldAssets(ResourceResolver resourceResolver) throws RepositoryException, ReplicationException {
		Session session = resourceResolver.adaptTo(Session.class);
		SearchResult result = findAssetsToBeDeleted(resourceResolver, session);

		if (null != result && result.getTotalMatches() > 0) {
			int count = 0;
			for (Hit hit : result.getHits()) {
				count++;
				session.removeItem(hit.getPath());

				if (count >= 10) {
					session.save();
					count = 0;
				}
			}
		}

	}

	private SearchResult findAssetsToBeDeleted(ResourceResolver resourceResolver, Session session) {
		Map<String, Object> params = new HashMap<>();
		params.put(PATH, assetRootPath);
		params.put(P_LIMIT, "-1");
		params.put(TYPE, "dam:Asset");
		params.put(RELATIVE_DATE_RANGE_PROP, "jcr:content/jcr:lastModified");
		params.put(RELATIVE_DATE_RANGE_UPPER_BOUND, "-30d");

		QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);
		
		Query query = null;
		if (queryBuilder != null) {
			query = queryBuilder.createQuery(PredicateGroup.create(params), session);
		}
		if(null != query) {
			String st = query.getResult().getQueryStatement();
		}
		return null==query ? null : query.getResult();
        
	}

	@Override
	public void storeReport(ProcessInstance instance, ResourceResolver rr)
			throws RepositoryException, PersistenceException {
		// Nothing to do here.
	}

	@Override
	public void init() throws RepositoryException {
		// Nothing to do here.
	}
}
