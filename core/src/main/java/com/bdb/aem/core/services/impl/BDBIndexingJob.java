package com.bdb.aem.core.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.tika.exception.TikaException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.ProcessProductDocumentService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;

/**
 * The Class BDBIndexingJob.
 */
@Component(service = JobConsumer.class, property = {
		JobConsumer.PROPERTY_TOPICS + "=" + BDBIndexingJob.TOPIC }, immediate = true)
public class BDBIndexingJob implements JobConsumer {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BDBIndexingJob.class);

	/** The Constant TOPIC. */
	public static final String TOPIC = "bdb/indexing";

	/** The Constant ASSET_TYPE. */
	public static final String ASSET_TYPE = "assetType";

	/** The resource resolver factory. */
	@Reference
	ResourceResolverFactory resourceResolverFactory;

	/** The process product document service. */
	@Reference
	ProcessProductDocumentService processProductDocumentService;
	
	/** The bDB workflow config service. */
	@Reference
	BDBWorkflowConfigService bDBWorkflowConfigService;

	/** The solr search service. */
	@Reference
	SolrSearchService solrSearchService;

	/**
	 * Activate.
	 */
	@Activate
	@Modified
	protected final void activate() {
		LOGGER.debug("Activate of BDBIndexingJob");
	}

	/**
	 * Process.
	 *
	 * @param job the job
	 * @return the job consumer. job result
	 */
	@Override
	public JobConsumer.JobResult process(Job job) {
		LOGGER.debug("Entry process of BDBIndexingJob");
		List<String> list = requireParam(job, CommonConstants.PAYLOAD_PATHS, ArrayList.class);
		LOGGER.debug("BDBIndexingJob List of assets - {}", list);
		String assetType = requireParam(job, ASSET_TYPE, String.class);
		long startTime = System.currentTimeMillis();
		LOGGER.debug("BDBIndexingJob Start time - {}", startTime);
		processList(list, assetType);
		long endTime = System.currentTimeMillis();
		LOGGER.debug("BDBIndexingJob End time - {}", endTime);
		LOGGER.debug("Total time taken for BDBIndexingJob - {}", endTime - startTime);
		LOGGER.debug("Exit process of BDBIndexingJob");
		return JobResult.OK;
	}

	/**
	 * Process list.
	 *
	 * @param list      the list
	 * @param assetType the asset type
	 */
	public void processList(List<String> list, String assetType) {
		LOGGER.debug("Entry processList of BDBIndexingJob");
		ResourceResolver serviceResolver = null;
		try {
			serviceResolver = CommonHelper.getReadServiceResolver(resourceResolverFactory);
			LOGGER.debug("Service Resolver created in job");
			if (null != serviceResolver) {
				startIndexing(list, assetType, serviceResolver);
			}
		} catch (LoginException | SolrServerException | IOException | RepositoryException e) {
			LOGGER.error("Error in processing job : {0}", e);
		} finally {
			CommonHelper.closeResourceResolver(serviceResolver);
		}
		LOGGER.debug("Exit processList of BDBIndexingJob");
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
		LOGGER.debug("Entry startProcessing of BDBIndexingJob");
		if (StringUtils.equalsIgnoreCase(assetType, CommonConstants.TYPE_PDF)) {
			startIndexingPdfAssets(list, serviceResolver);
		} else if (StringUtils.equalsIgnoreCase(assetType, CommonConstants.TYPE_VIDEO_THUMBNAIL)) {
			startIndexingThumbnailImagesAssets(list, serviceResolver);
		} else if (StringUtils.equalsIgnoreCase(assetType, CommonConstants.TYPE_PNG_IMAGE)) {
			startIndexingProductForImagesAssets(list, serviceResolver);
		} else if (StringUtils.equalsIgnoreCase(assetType, CommonConstants.TYPE_COMMERCE_CONTENT)) {
			startIndexingCommerce(list, serviceResolver);
		} else if (StringUtils.equalsIgnoreCase(assetType, CommonConstants.TYPE_COMMERCE_CONTENT_PDF)) {
			//Indexing of product related pdf once the sap-cc data has been modified
			startIndexingPdfAssets(CommonHelper.getDocRelatedToVariants(serviceResolver, list, bDBWorkflowConfigService), serviceResolver);
		}

		LOGGER.debug("Exit startProcessing of BDBIndexingJob");
	}


	/**
	 * Start indexing assets.
	 *
	 * @param list            the list
	 * @param serviceResolver the service resolver
	 */
	private void startIndexingPdfAssets(List<String> list, ResourceResolver serviceResolver) {
		LOGGER.debug("Entry startIndexingPdfAssets of BDBIndexingJob");
		Asset asset;
		LOGGER.debug("startIndexingPdfAssets List - {}", list);
		for (String assetPath : list) {
			asset = serviceResolver.getResource(assetPath).adaptTo(Asset.class);
			try {
				solrSearchService.indexAssetsToSolr(asset, serviceResolver);
			} catch (IOException | SAXException | TikaException | LoginException e) {
				LOGGER.error("Error : {0}", e);
			}
		}
		LOGGER.debug("Exit startIndexingPdfAssets of BDBIndexingJob");
	}
	
	/**
	 * Start indexing product for Image assets.
	 *
	 * @param list            the list
	 * @param serviceResolver the service resolver
	 */
	private void startIndexingProductForImagesAssets(List<String> list, ResourceResolver serviceResolver) {
		LOGGER.debug("Entry startIndexingProductForImagesAssets of BDBIndexingJob");
		for (String assetPath : list) {
			try {
				String path = assetPath.substring(0, assetPath.lastIndexOf("/"));
				String productBasePath = path.replace(CommonConstants.DAM_PRODUCT_PATH,CommonConstants.COMMERCE_PRODUCT_PATH);
				solrSearchService.indexPdpUrls(serviceResolver, productBasePath);
			} catch (RepositoryException | IOException| SolrServerException | LoginException e) {
				LOGGER.error("Error : {0}", e);
			}
		}
		LOGGER.debug("Exit startIndexingProductForImagesAssets of BDBIndexingJob");
	}
	
	private void startIndexingThumbnailImagesAssets(List<String> list, ResourceResolver serviceResolver) {
		LOGGER.debug("Entry startIndexingThumbnailImagesAssets of BDBIndexingJob");
		Asset asset;
		for (String assetPath : list) {
			asset = serviceResolver.getResource(assetPath).adaptTo(Asset.class);
			try {
				solrSearchService.indexVideoThumbnailToSolr(asset,serviceResolver);
			} catch (IOException | SAXException | TikaException | LoginException e) {
				LOGGER.error("Error : {0}", e);
			}
		}
		LOGGER.debug("Exit startIndexingThumbnailImagesAssets of BDBIndexingJob");
	}

	/**
	 * Start indexing commerce.
	 *
	 * @param list            the list
	 * @param serviceResolver the service resolver
	 * @throws RepositoryException the repository exception
	 * @throws SolrServerException the solr server exception
	 * @throws IOException         Signals that an I/O exception has occurred.
	 * @throws LoginException      the login exception
	 */
	private void startIndexingCommerce(List<String> list, ResourceResolver serviceResolver)
			throws RepositoryException, SolrServerException, IOException, LoginException {
		LOGGER.debug("Entry startIndexingCommerce of BDBIndexingJob");
		solrSearchService.indexProductDataToSolr(serviceResolver, list);
		LOGGER.debug("Exit startIndexingCommerce of BDBIndexingJob");
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