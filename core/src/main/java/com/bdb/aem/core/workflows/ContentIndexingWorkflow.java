package com.bdb.aem.core.workflows;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.tika.exception.TikaException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonObject;

/**
 * The process to index/un-index content pages and pdf assets.
 */
@Component(immediate = true, service = WorkflowProcess.class, property = {
		"process.label = Index Content/Asset to SOLR" })
public class ContentIndexingWorkflow implements WorkflowProcess {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(ContentIndexingWorkflow.class);

	/** The resource resolver factory. */
	@Reference
	ResourceResolverFactory resourceResolverFactory;

	/** The solr search service. */
	@Reference
	SolrSearchService solrSearchService;

	/** The serverconfig. */
	@Reference
	BDBSearchEndpointService serverconfig;

	/** The bdbApiService. */
	@Reference
	BDBApiEndpointService bdbApiService;

	/**
	 * Execute.
	 *
	 * @param workItem        the work item
	 * @param workflowSession the workflow session
	 * @param args            the args
	 * @throws WorkflowException the workflow exception
	 */
	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args) throws WorkflowException {
		ResourceResolver resourceResolver = null;
		try {
			resourceResolver = CommonHelper.getReadServiceResolver(resourceResolverFactory);
			String resourcePath = workItem.getWorkflowData().getPayload().toString();
			if (resourcePath != null) {
				if (resourcePath.startsWith(serverconfig.getBasePagePath())
						&& !resourcePath.startsWith(SolrSearchConstants.LANGUAGE_MASTERS_PAGE_PATH)) {
					forContentPages(resourcePath, resourceResolver);
				} else if (resourcePath.startsWith(serverconfig.getAssetPath())) {
					forPdfAssets(resourcePath, resourceResolver);
				}
			}
		} catch (LoginException | SolrServerException | IOException | SAXException | TikaException e) {
			LOG.error("Exception occured {}", e);
		} finally {
			if (null != resourceResolver) {
				CommonHelper.closeResourceResolver(resourceResolver);
			}
		}

	}

	public void forContentPages(String resourcePath, ResourceResolver resourceResolver) throws LoginException, SolrServerException, IOException {
		Resource pageResource = resourceResolver.getResource(resourcePath);
		if (null != pageResource) {
			ValueMap properties = pageResource.getValueMap();
			if (checkIfUnpublishEvent(properties)) {
				solrSearchService.unIndexContent(resourcePath, "contentPage");
			} else {
				indexContentPage(resourceResolver, resourcePath);
			}
		} else {
			// for deleted pages
			solrSearchService.unIndexContent(resourcePath, "contentPage");
		}
	}

	public void forPdfAssets(String resourcePath, ResourceResolver resourceResolver) throws IOException, SAXException, TikaException, LoginException {
		Resource assetResource = resourceResolver.getResource(resourcePath);
		if (null != assetResource) {
			ValueMap properties = resourceResolver.getResource(resourcePath).getValueMap();
			if (checkIfUnpublishEvent(properties)) {
				solrSearchService.unIndexContent(resourcePath, "pdfAsset");
			} else {
				indexPdfAsset(resourceResolver, resourcePath);
			}
		} else {
			// for deleted pdfs
			solrSearchService.unIndexContent(resourcePath, "pdfAsset");
		}
	}

	/**
	 * Check if unpublish event.
	 *
	 * @param properties the properties
	 * @return true, if successful
	 */
	public boolean checkIfUnpublishEvent(ValueMap properties) {
		return properties.containsKey(SolrSearchConstants.CQ_LAST_REPLICATION_ACTION) && properties
				.get(SolrSearchConstants.CQ_LAST_REPLICATION_ACTION).equals(SolrSearchConstants.DEACTIVATE);
	}

	/**
	 * Index content page.
	 *
	 * @param resourceResolver the resource resolver
	 * @param resourcePath     the resource path
	 * @throws LoginException      the login exception
	 * @throws SolrServerException the solr server exception
	 * @throws IOException         Signals that an I/O exception has occurred.
	 */
	public void indexContentPage(ResourceResolver resourceResolver, String resourcePath)
			throws LoginException, SolrServerException, IOException {
		Resource pageResource = resourceResolver.getResource(resourcePath);
		Resource contentResource = resourceResolver.getResource(resourcePath + CommonConstants.JCR_CONTENT);
		if (null != contentResource) {
			ValueMap properties = contentResource.adaptTo(ValueMap.class);
			Resource resource = resourceResolver.getResource(resourcePath).getParent();
			// get the parent page path
			String parentPath = resource.getPath() + CommonConstants.JCR_CONTENT;
			Resource parentResource = resourceResolver.getResource(parentPath);
			if (null != parentResource) {
				ValueMap valueMapParent = parentResource.adaptTo(ValueMap.class);
				// un-indexing the page if the solrChildPagesUnindexable property is true for parent page
				if(valueMapParent.containsKey(SolrSearchConstants.SOLR_CHILD_PAGES_UNINDEXABLE) && valueMapParent.get(SolrSearchConstants.SOLR_CHILD_PAGES_UNINDEXABLE).equals("true")) {
					solrSearchService.unIndexContent(resourcePath, "contentPage");
				}
				else if (!properties.containsKey(SolrSearchConstants.SOLR_UNINDEXABLE)) {
					JsonObject pageIndexObject = solrSearchService.createPageMetadataObject(pageResource,resourceResolver);
					Page page = resourceResolver.getResource(pageResource.getParent().getPath()).adaptTo(Page.class);
					String country = CommonHelper.getCountry(page);
					HttpSolrClient server = solrSearchService.solrClient(country);
					solrSearchService.indexPagesToSolr(pageIndexObject, server);
				} else {
					solrSearchService.unIndexContent(resourcePath,"contentPage");
				}
			}	
		}
	}

	/**
	 * Index pdf asset.
	 *
	 * @param resourceResolver the resource resolver
	 * @param resourcePath     the resource path
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws SAXException   the SAX exception
	 * @throws TikaException  the tika exception
	 * @throws LoginException the login exception
	 */
	public void indexPdfAsset(ResourceResolver resourceResolver, String resourcePath)
			throws IOException, SAXException, TikaException, LoginException {
		resourcePath = resourcePath.replace(CommonConstants.JCR_CONTENT, "");
		Asset asset = resourceResolver.getResource(resourcePath).adaptTo(Asset.class);
		String scientificResFolder = bdbApiService.getScientificResourceFolder();
		String assetType = solrSearchService.getTypeFromAssets(asset);
		if (assetType.equals("Adobe PDF")) {
			solrSearchService.indexAssetsToSolr(asset,resourceResolver);
		} else if (assetType.equals("PNG Image") && resourcePath.contains(scientificResFolder)) {
			solrSearchService.indexVideoThumbnailToSolr(asset, resourceResolver);
		}
	}

}