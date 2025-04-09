package com.bdb.aem.core.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.jcr.RepositoryException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.apache.solr.client.solrj.SolrServerException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;

/**
 * The Class BDBIndexingJobForOnDemandTDS.
 */
@Component(service = JobConsumer.class, property = {
		JobConsumer.PROPERTY_TOPICS + "=" + BDBIndexingJobForOnDemandTDS.TOPIC }, immediate = true)
public class BDBIndexingJobForOnDemandTDS implements JobConsumer {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BDBIndexingJobForOnDemandTDS.class);

	/** The Constant TOPIC. */
	public static final String TOPIC = "bdb/onDemandTDSIndexing";

	/** The Constant ASSET_TYPE. */
	public static final String ASSET_TYPE = "assetType";
	
	/** relative path to product node */
    private static final String VAR_PRODUCT_FOLDER = "products/";
    
    /** base path to product dam location */
    private static final String DAM_PRODUCT_PATH = "/content/dam/bdb/products/global/";

	/** The resource resolver factory. */
	@Reference
	ResourceResolverFactory resourceResolverFactory;
	
	@Reference
    CatalogStructureUpdateService catalogStructureUpdateService;

	/**
	 * Activate.
	 */
	@Activate
	@Modified
	protected final void activate() {
		LOGGER.debug("Activate of BDBIndexingJobForOnDemandTDS");
	}

	/**
	 * Process.
	 *
	 * @param job the job
	 * @return the job consumer. job result
	 */
	@Override
	public JobConsumer.JobResult process(Job job) {
		LOGGER.debug("Entry process of BDBIndexingJobForOnDemandTDS");
		List<String> list = (List<String>) job.getProperty(CommonConstants.PAYLOAD_PATHS);
		LOGGER.debug("BDBIndexingJobForOnDemandTDS List of assets - {}", list);
		String assetType = requireParam(job, ASSET_TYPE, String.class);
		long startTime = System.currentTimeMillis();
		LOGGER.debug("BDBIndexingJobForOnDemandTDS Start time - {}", startTime);
		processList(list, assetType);
		long endTime = System.currentTimeMillis();
		LOGGER.debug("BDBIndexingJobForOnDemandTDS End time - {}", endTime);
		LOGGER.debug("Total time taken for BDBIndexingJobForOnDemandTDS - {}", endTime - startTime);
		LOGGER.debug("Exit process of BDBIndexingJobForOnDemandTDS");
		return JobResult.OK;
	}

	/**
	 * Process list.
	 *
	 * @param list      the list
	 * @param assetType the asset type
	 */
	public void processList(List<String> list, String assetType) {
		LOGGER.debug("Entry processList of BDBIndexingJobForOnDemandTDS");
		ResourceResolver serviceResolver = null;
		try {
			serviceResolver = CommonHelper.getServiceResolver(resourceResolverFactory);
			LOGGER.debug("Service Resolver created in job");
			if (null != serviceResolver) {
				startIndexing(list, assetType, serviceResolver);
			}
		} catch (LoginException | SolrServerException | IOException | RepositoryException e) {
			LOGGER.error("Error in BDBIndexingForOnDemandTDS processing job : {0}", e);
		} finally {
			CommonHelper.closeResourceResolver(serviceResolver);
		}
		LOGGER.debug("Exit processList of BDBIndexingJobForOnDemandTDS");
	}

	/**
	 * Start indexing.
	 *
	 * @param list            the list
	 * @param assetType       the asset type
	 * @param serviceResolver the service resolver
	 * @throws SolrServerException the solr server exception
	 * @throws IOException         Signals that an I/O exception has occurred.
	 * @throws LoginException      the login exception
	 * @throws RepositoryException the repository exception
	 */
	private void startIndexing(List<String> list, String assetType, ResourceResolver serviceResolver)
			throws SolrServerException, IOException, LoginException, RepositoryException {
		LOGGER.debug("Entry startProcessing of BDBIndexingJobForOnDemandTDS");
		if (StringUtils.equalsIgnoreCase(assetType, CommonConstants.TYPE_PDF)) {
			startIndexingPdfAssets(list, serviceResolver);
		} 

		LOGGER.debug("Exit startProcessing of BDBIndexingJobForOnDemandTDS");
	}


	/**
	 * Start indexing assets.
	 *
	 * @param list            the list
	 * @param serviceResolver the service resolver
	 */
	private void startIndexingPdfAssets(List<String> list, ResourceResolver serviceResolver) {
		LOGGER.debug("Entry startIndexingPdfAssets of BDBIndexingJobForOnDemandTDS");
		LOGGER.debug("startIndexingPdfAssets List - {}", list);
		for (String assetPath : list) {
			assetPath = assetPath.replace(DAM_PRODUCT_PATH,CommonConstants.COMMERCE_PATH+VAR_PRODUCT_FOLDER);
			String basePath = assetPath.substring(0,assetPath.lastIndexOf("/"));
			Resource base = serviceResolver.getResource(basePath);
			if(null != base && base.hasChildren()) {
				for(Resource itemResource : base.getChildren()) {
					if (! itemResource.getName().equalsIgnoreCase("hp") && ! itemResource.getName().equalsIgnoreCase("sap-cc")) {
						ValueMap vm = itemResource.adaptTo(ValueMap.class);
						if(vm.get("isVariant").toString().equalsIgnoreCase("true")) {
							try {
								String skuName = itemResource.getName().toString();
								catalogStructureUpdateService.generatingOnDemandTDS(serviceResolver, skuName, basePath);
								serviceResolver.commit();
							} catch (RepositoryException | IOException | ParserConfigurationException | TransformerException | SAXException e1) {
								e1.printStackTrace();
							}
						}
						
					}
				}
			}
		}
		LOGGER.debug("Exit startIndexingPdfAssets of BDBIndexingJobForOnDemandTDS");
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
}