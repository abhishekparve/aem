package com.bdb.aem.core.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.AccessDeniedException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.config.ProductImageProcessingSchedulerConfiguration;
import com.bdb.aem.core.services.UpdateImageMetadataService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.replication.Replicator;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.TagConstants;

@Component(service = UpdateImageMetadataService.class, immediate = true)
@Designate(ocd = ProductImageProcessingSchedulerConfiguration.class)
public class UpdateImageMetadataServiceImpl implements UpdateImageMetadataService {

    /**
     * The Constant LOG
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateImageMetadataServiceImpl.class);
    
    /** The Resource Resolver Factory. */
    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    Replicator replicator;
    
    /** The solr search service. */
    @Reference
    private SolrSearchService solrSearchService;
    
    @Reference
    WorkflowHelperService workflowHelperService;
      
    /**
     * dc:imageAltText property name
     */
    private static final String DC_IMG_ALT_TXT = "dc:imageAltText";
    
    /**
     * dam:MIMEtype property name
     */
    private static final String DAM_MIME_TYPE = "dam:MIMEtype";
    
    /**
     * dc:caption property name
     */
    private static final String DC_CAPTION = "dc:caption";
    
    /**
     * mime type of an asset
     */
    private static final String MIME_TYPE = "image/png";
    
    /**
     * dc:imageRegion property name
     */
    private static final String DC_IMG_REG = "dc:imageRegion";
    
    /**
     * assetsReferencePathInVar
     */
    private String assetsReferencePathInVar = "";
 	
	@Activate
    public void activate(ProductImageProcessingSchedulerConfiguration config) {
        this.assetsReferencePathInVar = config.assetsReferencePathListInVar();
    }
    
	/**
	 * To check whether the image is processed through scheduler.
	 */
	@Override
	public SearchResult getMatchingFlatFileImageNode(ResourceResolver resourceResolver, String payload, SearchResult result) {
		Resource baseAssetResoruce = resourceResolver.getResource(payload);
		String imageName = baseAssetResoruce.getName();
		String baseNodeName = baseAssetResoruce.getParent().getName();
		if(baseNodeName.endsWith("_base")) {
			String materialNumber = baseNodeName.substring(0, baseNodeName.indexOf("_base"));
			result = getQueryResults(resourceResolver, assetsReferencePathInVar, materialNumber, imageName);
		}
		return result;
	}

	@Override
	public void updateFlatFileImageMetadata(ResourceResolver resourceResolver, Session session, Resource imageResoruce, SearchResult result) {
		
		try {
			if (null != result && result.getTotalMatches() > 0) {
	            for (Hit hit : result.getHits()) {
	                Resource varAssetResource = resourceResolver.getResource(hit.getPath());
	                if (null != varAssetResource) {
	                	updateMetadataNode(resourceResolver, imageResoruce, varAssetResource);
	                	deleteMetadataNodeFromVarFolder(resourceResolver, varAssetResource);
	                }
	            }
			}
		} catch (RepositoryException | PersistenceException e) {
			LOGGER.error("Exception occured {}", e);
		}
	}
	
	/**
	 * 
	 * @param resourceResolver
	 * @param varAssetResource
	 */
	private void deleteMetadataNodeFromVarFolder(ResourceResolver resourceResolver, Resource varAssetResource) {
		try {
			Resource recordResource = varAssetResource.getParent();
			if(recordResource.getName().startsWith(CommonConstants.RECORD_LABEL)) {
				 Iterator<Resource> iterator= recordResource.listChildren();
				 int count = 0;
				 while(iterator.hasNext()) {
					  Resource itemRes = iterator.next();
					  if (null != itemRes) {
			               count++;  
			          }
				 }
				 if(count > 1) {
					varAssetResource.adaptTo(Node.class).remove();
				 } else {
					 recordResource.adaptTo(Node.class).remove();
				 }
				 resourceResolver.commit();
			}
		} catch (AccessDeniedException e) {
			LOGGER.error("Exception occured {}", e);
		} catch (VersionException e) {
			LOGGER.error("Exception occured {}", e);
		} catch (LockException e) {
			LOGGER.error("Exception occured {}", e);
		} catch (ConstraintViolationException e) {
			LOGGER.error("Exception occured {}", e);
		} catch (RepositoryException e) {
			LOGGER.error("Exception occured {}", e);
		} catch (PersistenceException e) {
			LOGGER.error("Exception occured {}", e);
		}
		
	}

	/**
	 * 
	 * @param resourceResolver
	 * @param baseAssetResoruce
	 * @param varAssetResource
	 * @throws RepositoryException
	 * @throws PersistenceException
	 */
	private void updateMetadataNode(ResourceResolver resourceResolver, Resource baseAssetResoruce, Resource varAssetResource) throws RepositoryException, PersistenceException {
		ValueMap vm = varAssetResource.adaptTo(ValueMap.class);
		Resource metadataResource = baseAssetResoruce.getChild(CommonConstants.METADATA_PATH_AS_CHILD);
		if (metadataResource != null) {
			LOGGER.debug("Started updating Image metadata corresponding to :" + baseAssetResoruce.getPath());
			Node currentNode = metadataResource.adaptTo(Node.class);
	    	currentNode.setProperty(DamConstants.DC_TITLE, vm.get(CommonConstants.IMAGE_TITLE, StringUtils.EMPTY));
	    	currentNode.setProperty(DC_CAPTION, vm.get(CommonConstants.IMAGE_CAPTION, StringUtils.EMPTY));
	    	currentNode.setProperty(DamConstants.DC_DESCRIPTION, vm.get(CommonConstants.IMAGE_DESCRIPTION, StringUtils.EMPTY));
	    	currentNode.setProperty(DC_IMG_ALT_TXT, vm.get(CommonConstants.IMAGE_ALT_TEXT, StringUtils.EMPTY));
	    	currentNode.setProperty(DAM_MIME_TYPE, MIME_TYPE);	
	    	currentNode.setProperty(CommonConstants.IMAGE_NAME, vm.get(CommonConstants.IMAGE_NAME, StringUtils.EMPTY));
	    	currentNode.setProperty(CommonConstants.MATERIAL_NUMBER, vm.get(CommonConstants.MATERIAL_NUMBER, StringUtils.EMPTY));
	    	currentNode.setProperty(DC_IMG_REG, CommonConstants.GLOBAL);
	    	
	    	String imageType = vm.get(CommonConstants.IMAGE_TYPE, StringUtils.EMPTY);
	    	if(null != imageType) {
	    		String[] excelArray = imageType.split(",");
	    		List<String> imageTypes = new ArrayList<String>();
	    	    for(String value:excelArray) {
	    	       if(value.equalsIgnoreCase("Primary")) {
	    	    	   imageTypes.add(CommonConstants.PRIMARY_TAG);
	    	       } else if(value.equalsIgnoreCase("Gallery")) {
	    	    	   imageTypes.add(CommonConstants.GALLERY_TAG);
	    	       } else if(value.equalsIgnoreCase("Hero")) {
	    	    	   imageTypes.add(CommonConstants.HERO_TAG);
	    	       } else if(value.equalsIgnoreCase("Thumbnail")) {
	    	    	   imageTypes.add(CommonConstants.THUMBNAIL_TAG);
	    	       }
	    	    }
	    	    String[] tagsArray = new String[imageTypes.size()];
	    	    tagsArray = imageTypes.toArray(tagsArray);
	    	    currentNode.setProperty(TagConstants.PN_TAGS, tagsArray);
	    	}
		    resourceResolver.commit();
		    LOGGER.debug("Image metadata successfully updated for : " +baseAssetResoruce.getPath());
		} 		
		
	}

	/**
	 * 
	 * @param resourceResolver
	 * @param assetsReferencePathListInVar
	 * @param materialNumber
	 * @param imageName
	 * @return
	 */
	private SearchResult getQueryResults(ResourceResolver resourceResolver, String assetsReferencePathListInVar,String materialNumber, String imageName) {
        Map<String, Object> params = new HashMap<>();
        params.put(CommonConstants.PATH, assetsReferencePathListInVar);
        params.put(CommonConstants.TYPE, CommonConstants.NT_BASE);
        params.put(CommonConstants.NODE_NAME, materialNumber);
        params.put(SolrSearchConstants.QUERY_BUILDER_PROPERTY, CommonConstants.IMAGE_NAME);
		params.put(SolrSearchConstants.QUERY_BUILDER_PROPERTY_VALUE, imageName);
		params.put(SolrSearchConstants.QUERY_BUILDER_LIMIT, "-1");

        QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);
        Session session = resourceResolver.adaptTo(Session.class);
        Query query = queryBuilder.createQuery(PredicateGroup.create(params), session);

        return query.getResult();
    }
}