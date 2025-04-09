package com.bdb.aem.core.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.distribution.DistributionRequest;
import org.apache.sling.distribution.DistributionRequestType;
import org.apache.sling.distribution.Distributor;
import org.apache.sling.distribution.SimpleDistributionRequest;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.apache.sling.event.jobs.consumer.JobExecutionContext;
import org.apache.sling.event.jobs.consumer.JobExecutionResult;
import org.apache.sling.event.jobs.consumer.JobExecutor;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
	
import com.bdb.aem.core.util.BDBMode;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.tagging.TagConstants;
import com.day.cq.wcm.api.NameConstants;
import com.bdb.aem.core.util.BDBTraverseNestedNode;

/**
 * The Class BDBDistributionServiceImpl.
 */
@Component(property = { JobConsumer.PROPERTY_TOPICS + "=" + BDBDistributionServiceImpl.TOPIC })
public class BDBDistributionServiceImpl implements JobExecutor {

	/** The Constant DEFAULT_CHUNK_SIZE. */
	public static final int DEFAULT_CHUNK_SIZE = 100;

	/** The Constant KEY_PATH. */
	public static final String KEY_PATH = "path";

	/** The Constant KEY_MODE. */
	public static final String KEY_MODE = "mode";

	/** The Constant KEY_CHUNK_SIZE. */
	public static final String KEY_CHUNK_SIZE = "chunkSize";

	/** The Constant TOPIC. */
	public static final String TOPIC = "bdb/distribution/chunked";

	/** The log. */
	private Logger log = LoggerFactory.getLogger(this.getClass());

	/** The shallow node types. */
	private Set<String> shallowNodeTypes = new HashSet<>();

	/** The distributor. */
	Distributor distributor;

	/** The resolver factory. */
	ResourceResolverFactory resolverFactory;

	/**
	 * Instantiates a new BDB distribution service impl.
	 *
	 * @param distributor     the distributor
	 * @param resolverFactory the resolver factory
	 */
	@Activate
	public BDBDistributionServiceImpl(@Reference Distributor distributor,
			@Reference ResourceResolverFactory resolverFactory) {
		this.distributor = distributor;
		this.resolverFactory = resolverFactory;
		this.shallowNodeTypes.add(JcrResourceConstants.NT_SLING_FOLDER);
		this.shallowNodeTypes.add(JcrResourceConstants.NT_SLING_ORDERED_FOLDER);
		this.shallowNodeTypes.add(NameConstants.NT_PAGE);
		this.shallowNodeTypes.add(TagConstants.NT_TAG);
	}

	/**
	 * Process.
	 *
	 * @param job     the job
	 * @param context the context
	 * @return the job execution result
	 */
	@Override
	public JobExecutionResult process(Job job, JobExecutionContext context) {
		log.debug("Entry process method of BDBDistributionServiceImpl");
		List<String> paths = (ArrayList<String>) requireParam(job, CommonConstants.PAYLOAD_PATHS, ArrayList.class);
		Integer chunkSize = requireParam(job, KEY_CHUNK_SIZE, Integer.class);
		String modeSt = requireParam(job, KEY_MODE, String.class);
		BDBMode mode = BDBMode.valueOf(modeSt);
		try {
			List<String> finalList = new ArrayList<>();
			try (ResourceResolver resolver = CommonHelper.getServiceResolverReplication(resolverFactory)) {
				for (String path : paths) {
					addPathsToFinalList(path, finalList, resolver);
				}
				distribute(resolver, mode, chunkSize, context, finalList);
			}
		} catch (LoginException e) {
			log.error("Error Occurred", e);
			context.log(e.getMessage());
			return context.result().message(e.getMessage()).cancelled();
		}
		log.debug("Exit process method of BDBDistributionServiceImpl");
		return context.result().succeeded();

	}

	/**
	 * Require param.
	 *
	 * @param <T>  the generic type
	 * @param job  the job
	 * @param key  the key
	 * @param type the type
	 * @return the t
	 */
	private <T> T requireParam(Job job, String key, Class<T> type) {
		return Objects.requireNonNull(job.getProperty(key, type), "No " + key + " parameter provided");
	}

	/**
	 * Distribute.
	 *
	 * @param resolver  the resolver
	 * @param mode      the mode
	 * @param chunkSize the chunk size
	 * @param context   the context
	 * @param paths the paths
	 */
	public void distribute(ResourceResolver resolver, BDBMode mode, Integer chunkSize,
			JobExecutionContext context, List<String> paths) {
//		Resource parent = Objects.requireNonNull(resolver.getResource(path), "No resource present at path " + path);
//		context.log("Getting tree nodes for path=" + path);
//		List<String> paths = BDBTraverseNestedNode.getPaths(parent);
		List<List<String>> chunks = getChunks(paths, chunkSize);
		context.initProgress(chunks.size(), -1);
		int progress = 0;
		for (List<String> chunk : chunks) {
			context.incrementProgressCount(1);
			progress++;
			String firstPath = chunk.iterator().next();
			String msg = String.format("Distributing chunk %d/%d starting with %s", progress, chunks.size(), firstPath);
			log.debug(msg);
			context.log(msg);
			distributeChunk(resolver, chunk, context);
			if (context.isStopped()) {
				throw new IllegalArgumentException("Job stopped");
			}
		}
	}

	/**
	 * Gets the chunks.
	 *
	 * @param paths     the paths
	 * @param chunkSize the chunk size
	 * @return the chunks
	 */
	private List<List<String>> getChunks(List<String> paths, Integer chunkSize) {
		List<List<String>> chunks = new ArrayList<>();
		int c = 0;
		while (c < paths.size()) {
			int next = Math.min(paths.size(), c + chunkSize);
			chunks.add(paths.subList(c, next));
			c = next;
		}
		return chunks;
	}

	/**
	 * Distribute chunk.
	 *
	 * @param resolver the resolver
	 * @param paths    the paths
	 * @param context  the context
	 */
	private void distributeChunk(ResourceResolver resolver, List<String> paths, JobExecutionContext context) {
		try {
			Set<String> allPaths = new HashSet<>();
			Set<String> deepPaths = new HashSet<>();

			for (String path : paths) {
				allPaths.add(path);
				Resource res = resolver.getResource(path);
				Iterator<Resource> childIt = res.getChildren().iterator();
				while (childIt.hasNext()) {
					Resource child = childIt.next();
					Node node = child.adaptTo(Node.class);
					String type = node.getPrimaryNodeType().getName();
					if (!shallowNodeTypes.contains(type)) {
						String childPath = child.getPath();
						allPaths.add(childPath);
						deepPaths.add(child.getPath());
					}
				}
			}

			String[] pathsAr = allPaths.toArray(new String[] {});
			log.debug("Distributing with {} deep paths {} ", pathsAr, deepPaths);
			DistributionRequest request = new SimpleDistributionRequest(DistributionRequestType.ADD, pathsAr,
					deepPaths);
			distributor.distribute("publish", resolver, request);
		} catch (RepositoryException e) {
			String firstPath = paths.iterator().next();
			String msg = "Error creating distribution request first path " + firstPath + " msg: " + e.getMessage();
			throw new IllegalArgumentException (msg, e);
		}
	}
	
	private void addPathsToFinalList(String rootPath, List<String> finalList, ResourceResolver resolver) {
		Resource parent = Objects.requireNonNull(resolver.getResource(rootPath), "No resource present at path " + rootPath);
		List<String> paths = BDBTraverseNestedNode.getPaths(parent);
		finalList.addAll(paths);
	}

}
