package com.bdb.aem.core.services;

import java.io.IOException;

import javax.jcr.RepositoryException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.solr.client.solrj.SolrServerException;
import org.xml.sax.SAXException;

import com.day.cq.tagging.InvalidTagFormatException;
import com.google.gson.JsonObject;

/**
 * The Interface to update Catalog Structure.
 */
public interface CatalogStructureUpdateService {

	/**
	 * Sets the SAP node property.
	 *
	 * @param resourceResolver the resource resolver
	 * @param jsonString the json string
	 * @return the string
	 * @throws RepositoryException the repository exception
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public JsonObject setSAPNodeProperty(ResourceResolver resourceResolver, String jsonString)throws RepositoryException, SolrServerException, IOException;

	/**
	 * Sets the HP node property.
	 *
	 * @param resourceResolver the resource resolver
	 * @param jsonString the json string
	 * @return the string
	 * @throws RepositoryException the repository exception
	 * @throws InvalidTagFormatException the invalid tag format exception
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws LoginException
	 */
	public JsonObject setHPNodeProperty(ResourceResolver resourceResolver, String jsonString)throws RepositoryException, InvalidTagFormatException, SolrServerException, IOException, LoginException;

	/**
	 * Gets the product from look up.
	 *
	 * @param resourceResolver the resource resolver
	 * @param productId the product id
	 * @param productType the product type
	 * @return the product from look up
	 * @throws RepositoryException the repository exception
	 */
	Resource getProductFromLookUp(ResourceResolver resourceResolver, String productId, String productType)
			throws RepositoryException;
	
	/**
	 * Index and replicate products.
	 *
	 * @param resourceResolver the resource resolver
	 * @param jsonString the json string
	 */
	public void indexAndReplicateProducts(ResourceResolver resourceResolver, String jsonString);
	
	/**
     * Generating and Processing ON_DEMAND_TDS
     *
     * @param resourceResolver
     * @param skuName
     * @param basePath
     * @throws RepositoryException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerException
     * @throws SAXException
     */
	public void generatingOnDemandTDS(ResourceResolver serviceResolver, String skuName, String basePath) throws RepositoryException, IOException, ParserConfigurationException, TransformerException, SAXException;
}
