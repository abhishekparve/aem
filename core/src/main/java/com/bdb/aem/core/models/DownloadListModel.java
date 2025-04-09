package com.bdb.aem.core.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.bean.DownloadAccordionDetailBean;
import com.bdb.aem.core.bean.DownloadDocumentDetailBean;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.GetDocumentsService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.api.Page;

import lombok.Getter;

/**
 * The Class DownloadListModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DownloadListModel {

	/**
	 * The logger.
	 */
	protected Logger logger = LoggerFactory.getLogger(DownloadListModel.class);

	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String sectionTitle;

	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String imagePath;

	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String imagePathAltText;

	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String downloadBrochure;

	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String downloadBrochureLink;

	@Inject
	@Via("resource")
	public List<DownloadListAccordionModel> accordions;

	@Inject
	SlingHttpServletRequest request;

	/** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;
    
    /** The current resource. */
	@Inject
	Resource currentResource;
    

	/**
	 * The externalizer service.
	 */
	@Inject
	ExternalizerService externalizerService;

	/** The current page. */
	@Inject
	Page currentPage;

	/**
	 * The Image.
	 */
	@Getter
	public boolean image;

	/**
	 * The list of DownloadAccordionDetailBean.
	 */
	private List<DownloadAccordionDetailBean> accordionList;

	/**
	 * The Get Documents Service.
	 */
	@Inject
	private GetDocumentsService solrDocumentsService;

	/**
	 * The SolrSearchService.
	 */
	@Inject
	private SolrSearchService solrSearchService;
	
	

	/**
	 * Forms the bean
	 */
	@PostConstruct
	protected void init() throws IOException, SolrServerException {
		logger.debug("DownloadListModel - init() - start");
		String country = CommonHelper.getCountry(currentPage);
		buildDownloadListBean(accordions, country);
		image = StringUtils.isNotBlank(imagePath) ? true : false;
	}

	private List<DownloadAccordionDetailBean> buildDownloadListBean(List<DownloadListAccordionModel> accordions,
			String country) throws IOException, SolrServerException {

		accordionList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(accordions)) {
			for (DownloadListAccordionModel accordion : accordions) {
				List<DownloadDocumentDetailBean> documentBeanList = new ArrayList<>();
				DownloadAccordionDetailBean accordionBean = new DownloadAccordionDetailBean();
				accordionBean.setTitle(accordion.getAccordionTitle());
	
				if (CollectionUtils.isNotEmpty(accordion.getDocuments())) {
					for (DownloadListDocumentModel document : accordion.getDocuments()) {
						if (CommonConstants.MANUAL.equals(document.getUploadTypeSelection())) {
							documentBeanList.add(fetchManualUploadDocumentDetails(document.getDocumentTitle(),
									document.getDocumentLink(),document.getDownloadIcon()));
						}
						if (CommonConstants.AUTO.equals(document.getUploadTypeSelection())) {
							for( String prodType : document.getProductTypeSelectionList() ) {
								documentBeanList.addAll(fetchAutoUploadDocumentDetails(document.getProductName(),
										prodType, country, solrSearchService));
							}
						}
						accordionBean.setDocuments(documentBeanList);
					}
				}
				accordionList.add(accordionBean);
			}
		}
		return new ArrayList<>(accordionList);
	}

	public DownloadDocumentDetailBean fetchManualUploadDocumentDetails(String documentTitle, String documentLink,String downloadIcon) {

		String docTitle = StringUtils.defaultString(documentTitle, StringUtils.EMPTY);
		if (StringUtils.isBlank(documentTitle) && null != resourceResolver.getResource(documentLink)) {
			Resource documentPath = resourceResolver.getResource(documentLink);
			Asset documentPathAsset = documentPath.adaptTo(Asset.class);
			if (null != documentPathAsset && null != documentPathAsset.getMetadataValue(CommonConstants.DC_TITLE))
				docTitle = documentPathAsset.getMetadataValue(CommonConstants.DC_TITLE);
		}
	 
		return new DownloadDocumentDetailBean(docTitle,
				externalizerService.getFormattedUrl(documentLink, resourceResolver),downloadIcon);
	}

	public List<DownloadDocumentDetailBean> fetchAutoUploadDocumentDetails(String productName,
			String productDocumentType, String country, SolrSearchService solrSearchService)
			throws IOException, SolrServerException {
		List<DownloadDocumentDetailBean> autoUploadDocuments = new ArrayList<>();
		if (StringUtils.isNotBlank(productName) && StringUtils.isNotBlank(productDocumentType)) {
			if(productName.contains(CommonConstants.COLON)) {
				productName = productName.replace(CommonConstants.COLON, CommonConstants.SLASH_SLASH_COLON);
			}
			SolrDocumentList solrDocumentList = solrDocumentsService.getDocumentsByProductNameAndType(productName,
					productDocumentType, country, solrSearchService);
			autoUploadDocuments = buildDownloadDocuments(solrDocumentList);
		}
		return autoUploadDocuments;
	}

	public List<DownloadAccordionDetailBean> getAccordionList() {
		if (null != accordionList) {
			return new ArrayList<>(accordionList);
		} else {
			return Collections.emptyList();
		}
	}

	/**
	 *
	 * @param solrDocs
	 * @return Iterable JsonArray of the Query
	 */
	private List<DownloadDocumentDetailBean> buildDownloadDocuments(SolrDocumentList solrDocs) {
		Iterator<SolrDocument> iterator = solrDocs.iterator();
		List<DownloadDocumentDetailBean> documents = new ArrayList<>();
		while (iterator.hasNext()) {
			SolrDocument solrDocument = iterator.next();
			Iterator<Map.Entry<String, Object>> itr = solrDocument.iterator();
			DownloadDocumentDetailBean document = new DownloadDocumentDetailBean();
			while (itr.hasNext()) {
				Map.Entry<String, Object> map = itr.next();
				if (CommonConstants.URL_T.equals(map.getKey()) && null != map.getValue()) {
					document.setPath(externalizerService.getFormattedUrl(map.getValue().toString(), resourceResolver));
				}
				if (CommonConstants.DCTITLE_T.equals(map.getKey()) && null != map.getValue()) {
					document.setFileName(map.getValue().toString());
				}
			}
			documents.add(document);
		}
		return documents;
	}
	
	
	
	/**
	 * Gets the section title.
	 *
	 * @return the section title
	 */
	public String getSectionTitle() {
		return sectionTitle;
	}
	
	/**
	 * Gets the article id.
	 *
	 * @return the article id
	 */
	public String getArticleId() {
		return currentResource.getParent().getName() + "-" + currentResource.getName();
	}

}