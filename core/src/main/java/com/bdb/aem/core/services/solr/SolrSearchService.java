package com.bdb.aem.core.services.solr;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import com.bdb.aem.core.bean.BaseVariantHpResourceBean;
import com.day.cq.dam.api.Asset;
import com.google.gson.JsonObject;

/**
 * The Interface SolrSearchService.
 */
public interface SolrSearchService {

	/**
	 * Crawl content.
	 *
	 * @param resourcePath the resource path
	 * @param resourceType the resource type
	 * @param session the session
	 * @param resourceResolver 
	 * @return the json array
	 * @throws LoginException the login exception
	 */
	JsonObject crawlContent(String resourcePath, String resourceType, Session session, ResourceResolver resourceResolver) throws LoginException;


	/**
	 * Index pages to solr.
	 * @param indexPageData the index page data
	 * @param server the server
	 * @return true, if successful
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws LoginException the login exception
	 */
	boolean indexAllPagesToSolr(JsonObject indexPageData)
			throws  SolrServerException, IOException, LoginException;
	
	/**
	 * Index pages to solr.
	 *
	 * @param indexPageData the index page data
	 * @param server the server
	 * @return true, if successful
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws LoginException the login exception
	 */
	boolean indexPagesToSolr(JsonObject indexPageData, HttpSolrClient server)
			throws  SolrServerException, IOException, LoginException;
	
	/**
	 * Creates the page metadata object.
	 *
	 * @param pageContent the page content
	 * @return the json object
	 * @throws LoginException the login exception
	 */
	public JsonObject createPageMetadataObject(Resource pageContent,ResourceResolver resourceResolver) throws LoginException;
	
	/**
	 * Solr client.
	 *
	 * @param currentPage the current page
	 * @return creates the http solr client
	 */
	public HttpSolrClient solrClient(String country);
	
	/**
	 * Gets the all solr clients.
	 *
	 * @return the all solr clients
	 * @throws LoginException the login exception
	 */
	public List<HttpSolrClient> getAllSolrClients() throws LoginException;
	
	/**
	 * Gets the type from assets.
	 *
	 * @param asset the asset
	 * @return the type from assets
	 */
	public String getTypeFromAssets(Asset asset);
	
	/**
	 * Index assets to solr.
	 *
	 * @param asset the asset
	 * @param server the server
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SAXException the SAX exception
	 * @throws TikaException the tika exception
	 * @throws LoginException the login exception
	 */
	public void indexAssetsToSolr(Asset asset, ResourceResolver resourceResolver) throws IOException, SAXException, TikaException, LoginException;
	
	/**
	 * Index video thumbnail to solr.
	 *
	 * @param asset the asset
	 * @param serviceResolver 
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SAXException the SAX exception
	 * @throws TikaException the tika exception
	 * @throws LoginException the login exception
	 */
	public void indexVideoThumbnailToSolr(Asset asset, ResourceResolver serviceResolver) throws IOException, SAXException, TikaException, LoginException;
	
	/**
	 * Index all pdfs to solr.
	 *
	 * @param resolver the resolver
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SAXException the SAX exception
	 * @throws TikaException the tika exception
	 * @throws LoginException the login exception
	 */
	public void indexAllPdfsToSolr(ResourceResolver resolver, String payload) throws IOException, SAXException, TikaException, LoginException;

	/**
	 * Index Product data to solr.
	 *
	 * @param resourceResolver the resource resolver
	 * @param variantList the variant list
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws RepositoryException the repository exception
	 * @throws LoginException 
	 */
	public void indexProductDataToSolr(ResourceResolver resourceResolver, List<String> variantList) throws SolrServerException, IOException, RepositoryException, LoginException;

	/**
	 * Updates solr document when the variant products move to different categories.
	 * The function updates documents based on the variant material id which can be extracted from the given new path
	 *
	 * @param resourceResolver sling resource resolver
	 * @param variantList variant lists contains new paths to variants
	 * @throws LoginException when can't access solr server
	 */
	void updateProductDataLocation(ResourceResolver resourceResolver, List<String> variantList) throws LoginException;

	/**
	 * Updates solr document when the pdfs move to different categories.
	 * The function updates documents based on the pdf urls material id which can be extracted from the given new path
	 *
	 * @param resourceResolver sling resource resolver
	 * @param newPath pdf new path
	 * @param oldPath pdf old path
	 * @throws LoginException when can't access solr server
	 */
	void updatePdfDataLocation(ResourceResolver resourceResolver, String newPath, String oldPath) throws LoginException;

	/**
	 * Index pdp urls.
	 *
	 * @param resourceResolver the resource resolver
	 * @throws RepositoryException the repository exception
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws LoginException
	 */
	public void indexPdpUrls(ResourceResolver resourceResolver, String payload) throws RepositoryException, SolrServerException, IOException, LoginException;
	
	/**
	 * Un index content. 
	 *
	 * @param resourcePath the resource path
	 * @throws LoginException 
	 */
	public void unIndexContent(String resourcePath, String docType) throws LoginException;


	/**
	 * Solr client popular search.
	 *
	 * @return the http solr client
	 */
	HttpSolrClient solrClientPopSearch();
	
	/**
	 * Gets the all countries.
	 *
	 * @param resourceResolver the resource resolver
	 * @return the all countries
	 * @throws LoginException the login exception
	 */
	public List<String> getAllCountries(ResourceResolver resourceResolver) throws LoginException;
	
	/**
	 * Gets the hp node path.
	 *
	 * @param materialNo the material no
	 * @param country the country
	 * @return the hp node path
	 */
	public Resource getHpNodeResource(String materialNo, String country, ResourceResolver resourceResolver);
	
	/**
	 * Gets Hp node resource with Image Name inside the medias attribute.
	 *
	 * @param resourceResolver the resource resolver
	 * @param String Image Name.
	 * @return Hp node resource
	 * @throws LoginException the login exception
	 */
    public ArrayList<Resource> getMatchingImagesHpRes(String imageName, ResourceResolver resourceResolver);


	/**
	 * Gets the hp node resources.
	 *
	 * @param inputKey the input key
	 * @param inputValue the input value
	 * @param resourceResolver the resource resolver
	 * @return the hp node resources
	 */
	List<Resource> getHpNodeResources(String inputKey, String inputValue, ResourceResolver resourceResolver);
	
	/**
	 * Gets the hp node resources.
	 *
	 * @param inputKey the input key
	 * @param inputValue the input value
	 * @param resourceResolver the resource resolver
	 * @return the hp node resources
	 */
	List<BaseVariantHpResourceBean> getBaseVariantHpNodeResources(String inputKey, String inputValue, ResourceResolver resourceResolver);
	
	/**
	 * Gets the base product resource.
	 *
	 * @param baseMaterialNo the base material no
	 * @param resourceResolver the resource resolver
	 * @return the base product resource
	 */
	public Resource checkIfProductExists(String baseMaterialNo, String country, ResourceResolver resourceResolver, String productType);
	/**
     * 
     * @param docTypes
     * @param resourceResolver
     * @param startIndex
     * @param size
     * @param searchTerm
     * @return List of resources 
     */
    public Map<String,Object> getScientificResLib(String docTypes, int startIndex,int size,String searchTerm,String country);
    
    /**
	 * Index all Scientific Resource Thumbnail Images to solr.
	 *
	 * @param resolver the resolver
	 * @throws IOException    Signals that an I/O exception has occurred.
	 * @throws SAXException   the SAX exception
	 * @throws TikaException  the tika exception
	 * @throws LoginException the login exception
	 */
    public void indexAllScientificResourceThumbnailImagesToSolr(ResourceResolver resolver);
    
    /**
	 * Gets the releaseDate from AfsDate.
	 *
	 * @param string the date
	 */
    public String getReleaseDate(String date) throws ParseException;

    
}