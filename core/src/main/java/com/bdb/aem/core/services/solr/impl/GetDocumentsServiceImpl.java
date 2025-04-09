package com.bdb.aem.core.services.solr.impl;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.osgi.service.component.annotations.Component;

import com.bdb.aem.core.services.solr.GetDocumentsService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.SolrSearchConstants;

@Component(service = GetDocumentsService.class, immediate = true)
public class GetDocumentsServiceImpl implements GetDocumentsService {

	/**
	 * @param productName
	 * @param productDocumentType
	 * @param solrSearchService
	 * @return DownloadDocumentDetailBean list
	 * @throws IOException
	 * @throws SolrServerException
	 */
	@Override
	public SolrDocumentList getDocumentsByProductNameAndType(String productName, String productDocumentType,
			String country, SolrSearchService solrSearchService) throws IOException, SolrServerException {

		HttpSolrClient server = solrSearchService.solrClient(country);
		SolrQuery solrQuery = getDocumentsByProductNameAndType(productName, productDocumentType);
		QueryResponse sitesQueryResponse = server.query(solrQuery);
		SolrDocumentList sitesSolrDocs = sitesQueryResponse.getResults();
		return sitesSolrDocs;
	}

	/**
	 * @param productName
	 * @param productDocumentType
	 * @return Gives the Document Details
	 */
	private SolrQuery getDocumentsByProductNameAndType(String productName, String productDocumentType) {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(SolrSearchConstants.DOCUMENT_TYPE + SolrSearchConstants.UNDERSCORE_T
				+ SolrSearchConstants.CONSTANT_COLON + SolrSearchConstants.PDF);
		solrQuery.addFilterQuery(SolrSearchConstants.SOLRDOC_FIELD_DCTITLE + SolrSearchConstants.UNDERSCORE_T
				+ SolrSearchConstants.CONSTANT_COLON + productName);
		solrQuery.addFilterQuery(SolrSearchConstants.SOLR_FIELD_PDFX_DOC_TYPE + SolrSearchConstants.UNDERSCORE_T
				+ SolrSearchConstants.CONSTANT_COLON + productDocumentType);
		solrQuery.setRows(10);
		solrQuery.setStart(0);
		return solrQuery;
	}

}
