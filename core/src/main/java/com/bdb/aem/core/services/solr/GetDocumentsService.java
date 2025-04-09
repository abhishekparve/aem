package com.bdb.aem.core.services.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;

public interface GetDocumentsService {
	/**
	 * @param productName
	 * @param productDocumentType
	 * @param solrConfig
	 * @param solrSearchService
	 * @return Related Clones Details
	 * @throws IOException
	 * @throws SolrServerException
	 */
	SolrDocumentList getDocumentsByProductNameAndType(String productName, String productDocumentType,
			String country, SolrSearchService solrSearchService) throws IOException, SolrServerException;
}
