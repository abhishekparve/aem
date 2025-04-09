package com.bdb.aem.core.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.dam.print.ids.StringConstants;
import com.bdb.aem.core.bean.PdfMetaDataBean;
import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.DocumentMetadataImportService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;


@Component(service = DocumentMetadataImportService.class, immediate = true)
public class DocumentMetadataImportServiceImpl implements DocumentMetadataImportService {

	/** The Constant DOC_TITLE. */
	private static final String DOC_TITLE = "docTitle";

	/** The Constant PDFX_DOC_TITLE. */
	private static final String PDFX_DOC_TITLE = "pdfx:docTitle";
	
	/** The Constant DOC_REGION. */
	private static final String DOC_REGION = "docRegion";

	/** The Constant PDFX_DOC_REGION. */
	private static final String PDFX_DOC_REGION = "pdfx:docRegion";

	/** The Constant DOC_LANG. */
	private static final String DOC_LANG = "docLang";

	/** The Constant PDFX_DOC_LANG. */
	private static final String PDFX_DOC_LANG = "pdfx:docLang";

	/** The Constant DOC_SKU. */
	private static final String DOC_SKU = "docSKU";

	/** The Constant PDFX_DOC_SKU. */
	private static final String PDFX_DOC_SKU = "pdfx:docSku";

	/** The Constant DOC_DESC. */
	private static final String DOC_DESC = "docDesc";

	/** The Constant PDFX_DOC_DESC. */
	private static final String PDFX_DOC_DESC = "pdfx:docDesc";

	/** The Constant DOC_EXPIRY_DATE. */
	private static final String DOC_EXPIRY_DATE = "docExpiryDate";

	/** The Constant PDFX_DOC_EXPIRY_DATE. */
	private static final String PDFX_DOC_EXPIRY_DATE = "pdfx:docExpiryDate";

	/** The Constant DOC_REG_STATUS. */
	private static final String DOC_REG_STATUS = "docRegStatus";

	/** The Constant PDFX_DOC_REG_STATUS. */
	private static final String PDFX_DOC_REG_STATUS = "pdfx:docRegStatus";

	/** The Constant DOC_REV. */
	private static final String DOC_REV = "docRevision";

	/** The Constant PDFX_DOC_REV. */
	private static final String PDFX_DOC_REV = "pdfx:docRevision";

	/** The Constant DOC_OWNER. */
	private static final String DOC_OWNER = "docOwner";

	/** The Constant PDFX_DOC_OWNER. */
	private static final String PDFX_DOC_OWNER = "pdfx:docOwner";

	/** The Constant DEFAULT_DOC_LANG. */
	private static final String DEFAULT_DOC_LANG = "EN";

	/** The Constant DEFAULT_DOC_OWNER. */
	private static final String DEFAULT_DOC_OWNER = "PT";

	/** The Constant DOC_PROD_NAME. */
	private static final String DOC_PROD_NAME = "productName";

	/** The Constant PDFX_DOC_PROD_NAME. */
	private static final String PDFX_DOC_PROD_NAME = "pdfx:productName";

	/** The Constant DOC_KEYWORDS. */
	private static final String DOC_KEYWORDS = "docKeywords";

	/** The Constant PDFX_DOC_KEYWORDS. */
	private static final String PDFX_DOC_KEYWORDS = "pdfx:docKeywords";
	
	/** dc:imageKeywords property name */
    private static final String DC_KEYWORDS = "dc:imageKeywords";
    
    /** dc:title property name */
    private static final String DC_TITLE = "dc:title";

	/** The Constant DOC_PART_ID. */
	private static final String DOC_PART_ID = "docPartId";

	/** The Constant PDFX_DOC_PART_ID. */
	private static final String PDFX_DOC_PART_ID = "pdfx:docPartId";

	/** The Constant DOC_TYPE. */
	private static final String DOC_TYPE = "docType";

	/** The Constant PDFX_DOC_TYPE. */
	private static final String PDFX_DOC_TYPE = "pdfx:docType";
	
	/** The Constant PDF_KEYWORDS. */
	private static final String PDF_KEYWORDS = "pdf:Keywords";

	/** The Constant REGION_TAG_ID. */
	private static final String REGION_TAG_ID = "bdb:regions/";

	/** The bDB workflow config service. */
	@Reference
	private BDBWorkflowConfigService bDBWorkflowConfigService;
	
	/** The workflow helper service. */
	@Reference
	private WorkflowHelperService workflowHelperService;
	

    /**
     * The Constant LOG
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentMetadataImportServiceImpl.class);
    
    /** The Resource Resolver Factory. */
    @Reference
    private ResourceResolverFactory resolverFactory;
    
    /**
    *
    * @param payload         the path of the pdf in DAM
    * @param resourceResolver the serviceResolver
    */
    @Override
    public void processPdfAssets(String payload, ResourceResolver serviceResolver) {
    	LOGGER.debug("Entry in processPdfAssets method of DocumentMetadataImportWorkflow");
    	Session session = serviceResolver.adaptTo(Session.class); 
    	/** If the workflow is  triggered for the marketing-documents folder, getting all the documents
    	 *  through query builder and updating the metadata using updateDocumentMetadata method.*/
    		if (payload.equals("/content/dam/bdb/marketing-documents")) {
        		QueryBuilder builder = serviceResolver.adaptTo(QueryBuilder.class);
          		Map<String, String> predicate = new HashMap<>();
                predicate.put("path", payload);
                predicate.put("type", "dam:Asset");
                predicate.put("property", "jcr:content/jcr:primaryType");
                predicate.put("property.value","dam:AssetContent");
                predicate.put("p.limit", "-1");
                Query query = null;
                try {
                	query = builder.createQuery(PredicateGroup.create(predicate), session);
                	SearchResult searchResult = query.getResult();
                	LOGGER.debug("Found '{}' matches for query", searchResult.getTotalMatches());
                    for(Hit hit : searchResult.getHits()) {
                    	String path = null;
                    	try {
        					path = hit.getPath();
        				} catch (NullPointerException | RepositoryException e) {
        					LOGGER.error("Error Message for Exception:", e); 
        				}
                		updateDocumentMetadata(path);       	
            	}
                } catch (Exception e) {
                	LOGGER.error("Exception due to {}", e);  	
                }    
        	}
           else {
        		updateDocumentMetadata(payload);
        	}
    	
    	LOGGER.debug("Exit processPdfAssets method of DocumentMetadataImportWorkflow");
    }

    /**
     *
     * @param payload         the path of the pdf in DAM
     * @param resourceResolver the serviceResolver
     */
    
    public void updateDocumentMetadata(String payload) {
        LOGGER.debug("Entry in updateDocumentMetadata method of DocumentMetadataImportWorkflow");
        ResourceResolver resourceResolver = null;
        try {
        	resourceResolver = CommonHelper.getServiceResolver(resolverFactory);
        	PdfMetaDataBean pdfMetaDataBean = getPdfMetadataFromAsset(payload, resourceResolver);
            setMetadataForAsset(payload, pdfMetaDataBean, resourceResolver);
        } catch (Exception e) {
            LOGGER.error("Error Occurred in retrieving metadata : {}", e.getMessage());
        }finally {
			CommonHelper.closeResourceResolver(resourceResolver);
		}
        LOGGER.debug("Exit from updateDocumentMetadata method");
    }

	/**
	 * Gets the pdf metadata from asset.
	 *
	 * @param assetPath       the asset path
	 * @param serviceResolver the service resolver
	 * @return the pdf metadata from asset
	 */
	public PdfMetaDataBean getPdfMetadataFromAsset(String payload, ResourceResolver resourceResolver) {
		LOGGER.debug("Entry getPdfMetadataFromAsset method of ProcessProductDocumentFutureState");
		PdfMetaDataBean pdfMetaDataBean = null;
		Resource pdfMetadataResource = resourceResolver.getResource(payload + CommonConstants.METADATAPATH);
		if (null != pdfMetadataResource) {
			pdfMetaDataBean = getDetailsFromNode(pdfMetadataResource);
		}
		LOGGER.debug("Exit getPdfMetadataFromAsset method of DocumentMetadataImportWorkflow");
		return pdfMetaDataBean;
	}

	/**
	 * Gets the details from node.
	 *
	 * @param pdfMetadataResource the pdf metadata resource
	 * @return the details from node
	 */
	public PdfMetaDataBean getDetailsFromNode(Resource pdfMetadataResource) {
		LOGGER.debug("Entry getDetailsFromNode method of DocumentMetadataImportWorkflow");
		PdfMetaDataBean pdfMetaDataBean = null;
		ValueMap oldProperties = pdfMetadataResource.adaptTo(ValueMap.class);
		ModifiableValueMap properties = pdfMetadataResource.adaptTo(ModifiableValueMap.class);

		if (MapUtils.isNotEmpty(oldProperties)) {
			int count = oldProperties.size();
			List<String> keyToRemove = new ArrayList<>();
			Pattern p = Pattern.compile("^[A-Za-z]*:[A-Za-z]*[0-9]?");
			for (String x : oldProperties.keySet()) {
				if (count <= 0) break;
				Matcher m = p.matcher(x);
				int len = -1, start = 0;
				while (m.find()) {
					start = m.start();
					len = m.end() - start;
				}
				if (len == -1) {
					len = x.length();
				}
				String newKey = x.substring(start, len);
				if (!newKey.equals(x)  && !properties.containsKey(newKey)) {
					properties.put(newKey, oldProperties.get(x, StringUtils.EMPTY));
					keyToRemove.add(x);
				} else if (newKey.equals(x)  && properties.containsKey(newKey)) {
					if (CommonConstants.DOC_METADATA_LIST.contains(newKey)) {
						if (oldProperties.get(x) instanceof String[]) {
							properties.remove(x);
							properties.put(newKey, oldProperties.get(x, StringUtils.EMPTY));
						} else {
							properties.put(newKey, oldProperties.get(x, StringUtils.EMPTY));
						}
					}
				}
				count--;
			}

			for (String str : keyToRemove) {
				properties.remove(str);
			}
			
			pdfMetaDataBean = new PdfMetaDataBean();
			pdfMetaDataBean.setDocTitle(getMandatoryProp(properties, PDFX_DOC_TITLE, DC_TITLE));
			pdfMetaDataBean.setDocType(properties.get(PDFX_DOC_TYPE, StringUtils.EMPTY));
			pdfMetaDataBean.setDocRegion(getRegions(properties.get(PDFX_DOC_REGION, StringUtils.EMPTY)));
			pdfMetaDataBean.setDocLang(properties.get(PDFX_DOC_LANG, DEFAULT_DOC_LANG));
			pdfMetaDataBean.setDocSku(properties.get(PDFX_DOC_SKU, StringUtils.EMPTY));
			pdfMetaDataBean.setDocDesc(getMandatoryProp(properties, PDFX_DOC_DESC, StringConstants.METADATA_DESC));
			pdfMetaDataBean.setDocExpiryDate(properties.get(PDFX_DOC_EXPIRY_DATE, StringUtils.EMPTY));
			pdfMetaDataBean.setDocRegStatus(properties.get(PDFX_DOC_REG_STATUS, StringUtils.EMPTY));
			pdfMetaDataBean.setDocRevision(properties.get(PDFX_DOC_REV, StringUtils.EMPTY));
			pdfMetaDataBean.setDocOwner(properties.get(PDFX_DOC_OWNER, DEFAULT_DOC_OWNER));
			pdfMetaDataBean.setProductName(properties.get(PDFX_DOC_PROD_NAME, StringUtils.EMPTY));
			pdfMetaDataBean.setDocKeywords(getMandatoryProp(properties, PDFX_DOC_KEYWORDS, PDF_KEYWORDS));
			pdfMetaDataBean.setDocPartId(properties.get(PDFX_DOC_PART_ID, StringUtils.EMPTY));
		}

		LOGGER.debug("Exit getDetailsFromNode method of DocumentMetadataImportWorkflow");
		return pdfMetaDataBean;
	}

	/**
	 * Gets the mandatory prop.
	 *
	 * @param properties the properties
	 * @param firstKey   the first key
	 * @param secondKey  the second key
	 * @return the mandatory prop
	 */
	private String getMandatoryProp(ValueMap properties, String firstKey, String secondKey) {
		String value = properties.get(firstKey, StringUtils.EMPTY);
		if (!StringUtils.isNotBlank(value)) {
			value = properties.get(secondKey, StringUtils.EMPTY);
		}
		return value;
	}
	
	/**
	 * Gets the pdfKeywords.
	 *
	 * @param properties the properties
	 * @param firstKey   the first key
	 * @return the pdfKeywords
	 */
	private String getPdfKeywords(ValueMap properties, String firstKey) {
		String value = "";
		if (!StringUtils.isNotBlank(value)) {
			value = properties.get(firstKey, StringUtils.EMPTY);
		}
		return value;
	}


	/**
	 * Gets the regions.
	 *
	 * @param regions the regions
	 * @return the regions
	 */
	public String[] getRegions(String regions) {
		LOGGER.debug("Entry getRegions method of ProcessProductDocumentFutureState");
		return StringUtils.isNotBlank(regions) ? regions.split("[,]") : new String[0];
	}
	/**
	 * Sets the metadata for asset.
	 *
	 * @param pdfMetaDataBean the pdf meta data bean
	 * @param serviceResolver the service resolver
	 * @throws RepositoryException the repository exception
	 * @throws LoginException 
	 */
	public void setMetadataForAsset(String payload, PdfMetaDataBean pdfMetaDataBean, ResourceResolver resourceResolver)
			throws RepositoryException, LoginException {
		LOGGER.debug("Entry setMetadataForAsset method of DocumentMetadataImportWorkflow");
		Resource pdfMetadataResource = resourceResolver.getResource(payload + CommonConstants.METADATAPATH);
		if (null != pdfMetadataResource) {
			ValueMap nodeProperties = pdfMetadataResource.adaptTo(ValueMap.class);
			Node currentNode = pdfMetadataResource.adaptTo(Node.class);
			LOGGER.debug("Working on Dam node : {}", currentNode.getPath());
			removeMetadata(currentNode);
			placeMetadata(pdfMetaDataBean, currentNode);
			//Placing the pdfKeywords data in keywords section of custom meta properties tab
			// Getting the pdfKeywords and setting it to dc:imageKeywords property
			currentNode.setProperty(DC_KEYWORDS, getPdfKeywords(nodeProperties, PDF_KEYWORDS));
			currentNode.getSession().save();	
		}	
		LOGGER.debug("Exit setMetadataForAsset method of DocumentMetadataImportWorkflow");
	}
	/*
	 * Remove metadata value from currentNode if property already exist.
	 */
	public void removeMetadata(Node currentNode) throws RepositoryException {
		String[] metadataList = CommonConstants.DOC_METADATA_LIST.split(",");
		for(String property: metadataList) {
			String trimmedProperty = property.trim();
			if(currentNode.hasProperty(trimmedProperty))
				currentNode.getProperty(trimmedProperty).remove();
		}
		currentNode.getSession().save();
		LOGGER.debug("Exit removeMetadata method of DocumentMetadataImportWorkflow");
	}
	/**
	 * Place metadata.
	 *
	 * @param pdfMetaDataBean the pdf meta data bean
	 * @param currentNode     the current node
	 * @throws RepositoryException the repository exception
	 */
	public void placeMetadata(PdfMetaDataBean pdfMetaDataBean, Node currentNode) throws RepositoryException {
		LOGGER.debug("Entry placeMetadata method of DocumentMetadataImportWorkflow");
		currentNode.setProperty(DOC_TITLE, pdfMetaDataBean.getDocTitle());
		currentNode.setProperty(DOC_TYPE, pdfMetaDataBean.getDocType());
		currentNode.setProperty(DOC_REGION, getAsTags(pdfMetaDataBean.getDocRegion()));
		currentNode.setProperty(DOC_LANG, pdfMetaDataBean.getDocLang());
		currentNode.setProperty(DOC_SKU, pdfMetaDataBean.getDocSku());
		currentNode.setProperty(DOC_DESC, pdfMetaDataBean.getDocDesc());
		currentNode.setProperty(DOC_EXPIRY_DATE, pdfMetaDataBean.getDocExpiryDate());
		currentNode.setProperty(DOC_REG_STATUS, pdfMetaDataBean.getDocRegStatus());
		currentNode.setProperty(DOC_REV, pdfMetaDataBean.getDocRevision());
		currentNode.setProperty(DOC_OWNER, pdfMetaDataBean.getDocOwner());
		currentNode.setProperty(DOC_PROD_NAME, pdfMetaDataBean.getProductName());
		currentNode.setProperty(DOC_KEYWORDS, pdfMetaDataBean.getDocKeywords());
		currentNode.setProperty(DOC_PART_ID, pdfMetaDataBean.getDocPartId());
		currentNode.getSession().save();
		LOGGER.debug("Exit placeMetadata method of DocumentMetadataImportWorkflow");
	}

	/**
	 * Gets the as tags.
	 *
	 * @param regions the regions
	 * @return the as tags
	 */
	public String[] getAsTags(String[] regions) {
		LOGGER.debug("Entry getAsTags method of PDocumentMetadataImportWorkflow");
		String tagBasePathId = REGION_TAG_ID;
		String[] regionAsTag = new String[regions.length];
		for (int i = 0; i < regionAsTag.length; i++) {
			regionAsTag[i] = tagBasePathId + regions[i].trim().toLowerCase();
		}
		LOGGER.debug("Exit getAsTags method of DocumentMetadataImportWorkflow");
		return regionAsTag;
	}
}