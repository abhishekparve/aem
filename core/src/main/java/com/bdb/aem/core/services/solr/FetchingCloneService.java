package com.bdb.aem.core.services.solr;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

import javax.jcr.RepositoryException;

public interface FetchingCloneService {
    /**
     *
     * @param baseId
     * @param solrConfig
     * @param solrSearchService
     * @return Related Clones Details
     * @throws IOException
     * @throws SolrServerException
     */
    String getClonesData(String country, BDBSearchEndpointService solrConfig,SolrSearchService solrSearchService, String specificity, String dyeName, String cloneId) throws IOException, SolrServerException;

    /**
     *
     * @param baseId
     * @param config
     * @param solrConfig
     * @param catalogStructureUpdateService 
     * @return Related formats Details
     * @throws IOException
     * @throws SolrServerException
     * @throws RepositoryException 
     * @throws Exception
     */
    String getFormatsData(String country, BDBSearchEndpointService config, SolrSearchService solrConfig, String dyeName, String cloneId) throws IOException, SolrServerException, RepositoryException;

    /**
    *
    * @param baseId
    * @param config
    * @param solrConfig
    * @param catalogStructureUpdateService 
    * @return Related formats Details
    * @throws IOException
    * @throws SolrServerException
    * @throws RepositoryException 
    * @throws Exception
    */
   String getSpecificityData(String country, BDBSearchEndpointService config, SolrSearchService solrConfig,  String dyeName, String specificity) throws IOException, SolrServerException, RepositoryException;

}
