package com.bdb.aem.core.services.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFactory;
import javax.jcr.Workspace;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ProductImageProcessingService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.crx.JcrConstants;

/**
 * The Class ProductImageProcessingServiceImpl.
 */
@Component(service = ProductImageProcessingService.class, immediate = true)
public class ProductImageProcessingServiceImpl implements ProductImageProcessingService {
	
	/** The workflow helper service. */
	@Reference
	WorkflowHelperService workflowHelperService;
	
	/** The bdb workflow config service. */
	@Reference
	BDBWorkflowConfigService bdbWorkflowConfigService;
	
	/** The job manager. */
	@Reference
	private JobManager jobManager;
	
	@Reference
	private transient CatalogStructureUpdateService catalogStructureUpdateService;
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImageProcessingServiceImpl.class);
	
	public static final String PATH = "path";
    public static final String NODE_NAME = "nodename";
    public static final String TYPE = "type";
    public static final String NT_BASE = "nt:base";
    public static final String STARTS_WITH_RECORD = "record*";
    public static final String ERROR_REASON = "errorReason";
    public static final String PROCESS_STATE = "processState";
    public static final String FAIL = "Fail";
    public static final String ERROR = "Error";
    
    private int retryCount = 0;
    
    /**
     * mime type of an asset
     */
    private static final String MIME_TYPE = "image/png";
    
    List<String> errorReason = null;
    

	@Override
	public void processProductImages(ResourceResolver resourceResolver, Session session, String assetsPathListInDAM, String assetsReferencePathListInVar, String failedRecordsPathInVar, long retryLimit) {
		try {
			List<String> listOfAssets = workflowHelperService.getListOfImageAssets(assetsPathListInDAM, resourceResolver,
					session);
			LOGGER.info("List of assets to process {} ", listOfAssets );
			SearchResult result = getQueryResults(resourceResolver, assetsPathListInDAM, assetsReferencePathListInVar);
			if (null != result && result.getTotalMatches() > 0) {
	            for (Hit hit : result.getHits()) {
	            	List<String> productList = new ArrayList<String>();
	                Resource recordResource = resourceResolver.getResource(hit.getPath());
	                if (null != recordResource) {
	                	Iterator<Resource> productResourceList = recordResource.listChildren();
	                	 while(productResourceList.hasNext()) {
	     			    	Resource productResource  = productResourceList.next();
	     			    	productList.add(productResource.getPath());
	     			    }
	                	processProductImages(resourceResolver, session, assetsPathListInDAM, productList, listOfAssets, assetsReferencePathListInVar, failedRecordsPathInVar, retryLimit);
	                }
	            }
	            session.save();
	            session.logout();
			}
			LOGGER.info("Successfully processed all the images.");
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param resourceResolver
	 * @param assetsPathListInDAM
	 * @param assetsReferencePathListInVar
	 * @return
	 */
	private SearchResult getQueryResults(ResourceResolver resourceResolver, String assetsPathListInDAM, String assetsReferencePathListInVar) {
        Map<String, Object> params = new HashMap<>();
        params.put(PATH, assetsReferencePathListInVar);
        params.put(TYPE, NT_BASE);
        params.put(NODE_NAME, STARTS_WITH_RECORD);

        QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);
        Session session = resourceResolver.adaptTo(Session.class);
        Query query = queryBuilder.createQuery(PredicateGroup.create(params), session);

        return query.getResult();
    }
	
	/**
	 * 
	 * @param resourceResolver
	 * @param session
	 * @param assetsPathListInDAM
	 * @param productList
	 * @param listOfAssets
	 * @param assetsReferencePathListInVar
	 * @param retryLimit 
	 * @param failedRecordsPathInVar 
	 */
	private void processProductImages(ResourceResolver resourceResolver, Session session, String assetsPathListInDAM, List<String> productList, List<String> listOfAssets, String assetsReferencePathListInVar, String failedRecordsPathInVar, long retryLimit) {
		try {
			int processedProductCount = 0;
			String imagePathInTempAssets = "";
			List<String> assetsListToProcess = new ArrayList<String>();
			for (String productPath: productList) {
				errorReason = new ArrayList<String>();
				int updatedRetryCount = 0;
				String materialNumber = session.getNode(productPath).getName().toString();
				String failedRecordNodePath = failedRecordsPathInVar+CommonConstants.SLASH+materialNumber+CommonConstants.UNDERSCORE+Calendar.getInstance().getTimeInMillis();
				
				Resource productResource = resourceResolver.getResource(productPath);
				ValueMap productVM = productResource.getValueMap();
				
				String imageName = productVM.containsKey(CommonConstants.IMAGE_NAME) ? productVM.get(CommonConstants.IMAGE_NAME).toString() : "";
				Resource variantResource = catalogStructureUpdateService.getProductFromLookUp(resourceResolver, materialNumber, CommonConstants.MATERIAL_NUMBER);
				Node recordNode = session.getNode(productPath).getParent();
				if(!checkIfRequiredMetadataAvailable(resourceResolver, productPath, errorReason)) {
					moveRecordToFailedSection(resourceResolver, session, failedRecordNodePath, productPath);
					updateErrorStatusIfMetadataNotAvailable(failedRecordNodePath, session, FAIL, errorReason);
					processedProductCount++;
					if(productList.size() == processedProductCount) {
						recordNode.remove();
						session.save();
					}
				} else if(!checkIfImageAvailable(imageName, listOfAssets)) {
					errorReason.add("Missing product image in temp-assets");
					updateErrorStatus(productResource, session, errorReason);
					updatedRetryCount = updateRetryCount(productResource, session, retryCount, updatedRetryCount, retryLimit);
					processedProductCount++;
					if(updatedRetryCount >= retryLimit) {
						moveRecordToFailedSection(resourceResolver, session, failedRecordNodePath, productPath);
						if(productList.size() == processedProductCount) {
							recordNode.remove();
							session.save();
						}
					}
				} else if(!checkIfImageIsInProcessedState(resourceResolver, imageName, listOfAssets)) {
					errorReason.add("Image still getting processed by asset compute service");
					updateErrorStatus(productResource, session, errorReason);
					updatedRetryCount = updateRetryCount(productResource, session, retryCount, updatedRetryCount, retryLimit);
					processedProductCount++;
					if(updatedRetryCount >= retryLimit) {
						moveRecordToFailedSection(resourceResolver, session, failedRecordNodePath, productPath);
						if(productList.size() == processedProductCount) {
							recordNode.remove();
							session.save();
						}
					}
				} else if(!checkIfProductAvailable(variantResource)) {
					errorReason.add("Corresponding product not yet available in AEM");
					updateErrorStatus(productResource, session, errorReason);
					updatedRetryCount = updateRetryCount(productResource, session, retryCount, updatedRetryCount, retryLimit);
					processedProductCount++;
					if(updatedRetryCount >= retryLimit) {
						moveRecordToFailedSection(resourceResolver, session, failedRecordNodePath, productPath);
						if(productList.size() == processedProductCount) {
							recordNode.remove();
							session.save();
						}
					}
				} else {
					processImageIfRequiredInfoAvailable(resourceResolver, session, productPath, listOfAssets, assetsPathListInDAM, imagePathInTempAssets, assetsListToProcess);
					processedProductCount++;
					if(productList.size() == processedProductCount) {
	            		/** Removing the asset only when it is moved */
	                    workflowHelperService.moveAssetFromTempAssetFolder(imagePathInTempAssets, resourceResolver, session);
	            	}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured {}", e);
		}
		
	}
	
	/**
	 * 
	 * @param resourceResolver
	 * @param productPath
	 * @param errorReason
	 * @return
	 */
	private Boolean checkIfRequiredMetadataAvailable(ResourceResolver resourceResolver, String productPath, List<String> errorReason) {
		Resource productResource = resourceResolver.getResource(productPath);
		ValueMap productVM = productResource.getValueMap();
		
		String imageName = productVM.containsKey(CommonConstants.IMAGE_NAME) ? productVM.get(CommonConstants.IMAGE_NAME).toString() : StringUtils.EMPTY;
		String materialNumber = productVM.containsKey(CommonConstants.MATERIAL_NUMBER) ? productVM.get(CommonConstants.MATERIAL_NUMBER).toString() : StringUtils.EMPTY;
		if(StringUtils.isNotEmpty(imageName) && StringUtils.isNotEmpty(materialNumber)) {
			return true;
		} else {
			if(StringUtils.isEmpty(imageName)) {
				errorReason.add("Missing Image Name metadata");
			} else if(StringUtils.isEmpty(materialNumber)) {
				errorReason.add("Missing Material Number metadata");
			}
			LOGGER.debug("Mandatory image metadata is not available");
			return false;
		}
	}
	
	/**
	 * 
	 * @param imageName
	 * @param listOfAssets
	 * @return
	 */
	private boolean checkIfImageAvailable(String imageName, List<String> listOfAssets) {
		for (String assetPath : listOfAssets) {
			String imageFromList = assetPath.substring(assetPath.lastIndexOf('/')).replaceAll("/","");
			if(StringUtils.equalsIgnoreCase(imageFromList, imageName))
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param resourceResolver 
	 * @param imageName
	 * @param listOfAssets
	 * @return
	 */
	private boolean checkIfImageIsInProcessedState(ResourceResolver resourceResolver, String imageName, List<String> listOfAssets) {
		String imagePath = bdbWorkflowConfigService.getProductImageBasePath()+CommonConstants.SLASH+ imageName;
		Resource imgResource = resourceResolver.getResource(imagePath);
		if (imgResource.getChild(JcrConstants.JCR_CONTENT) != null) {
            ValueMap jcrVm = imgResource.getChild(JcrConstants.JCR_CONTENT).adaptTo(ValueMap.class);
            if (CommonConstants.DAM_ASSET_STATE_PROCESSED.equals(jcrVm.get(CommonConstants.DAM_ASSET_STATE))) {
            	return true;
            }
		}
		return false;
	}
	
	/**
	 * 
	 * @param variantResource
	 * @return
	 */
	private boolean checkIfProductAvailable(Resource variantResource) {
		return null != variantResource ? true : false;
	}
	
	/**
	 * move record from unprocessed to failed records.
	 * 
	 * @param resourceResolver
	 * @param session
	 * @param failedRecordNodePath
	 * @param productPath
	 */
	private void moveRecordToFailedSection(ResourceResolver resourceResolver, Session session, String failedRecordNodePath, String productPath) {
		try {
			Workspace workspace = session.getWorkspace();
			workspace.move(productPath, failedRecordNodePath);
			session.save();	
		} catch (RepositoryException e) {
			LOGGER.error("Exception occured {}", e);
		}
	}
	
	/**
	 * 
	 * @param failedRecordNodePath
	 * @param session
	 * @param fail
	 * @param errorReasonIfMetadataNotAvailable
	 */
	private void updateErrorStatusIfMetadataNotAvailable(String failedRecordNodePath, Session session, String fail, List<String> errorReasonIfMetadataNotAvailable) {
		try {
			String[] errorReasonArray = new String[errorReasonIfMetadataNotAvailable.size()];
			errorReasonArray = errorReasonIfMetadataNotAvailable.toArray(errorReasonArray);
			Node failedNode = session.getNode(failedRecordNodePath);
			failedNode.setProperty(PROCESS_STATE, fail);
			failedNode.setProperty(ERROR_REASON, errorReasonArray);
			session.save();
		} catch (RepositoryException e) {
			LOGGER.error("Exception occured {}", e);
		}
	}
	
	/**
	 * 
	 * @param productResource
	 * @param session
	 * @param errorReasonIfImageNotAvailable
	 */
	private void updateErrorStatus(Resource productResource, Session session, List<String> errorReasonIfImageNotAvailable) {
		try {
			ModifiableValueMap modifiableVM = productResource.adaptTo(ModifiableValueMap.class);
			String[] errorReasonArray = new String[errorReasonIfImageNotAvailable.size()];
			errorReasonArray = errorReasonIfImageNotAvailable.toArray(errorReasonArray);
			modifiableVM.put(PROCESS_STATE, ERROR);
			modifiableVM.put(ERROR_REASON, errorReasonArray);
			session.save();
		} catch (RepositoryException e) {
			LOGGER.error("Exception occured {}", e);
		}
	}

	/**
	 * 
	 * @param resourceResolver
	 * @param session
	 * @param productPath
	 * @param listOfAssets
	 * @param assetsPathListInDAM
	 * @param imagePathInTempAssets
	 * @param assetsListToProcess 
	 */
	private void processImageIfRequiredInfoAvailable(ResourceResolver resourceResolver, Session session, String productPath, List<String> listOfAssets, String assetsPathListInDAM, String imagePathInTempAssets, List<String> assetsListToProcess) {
		try {
			Resource productResource = resourceResolver.getResource(productPath);
			ValueMap productVM = productResource.getValueMap();
			
			AssetManager assetManager = resourceResolver.adaptTo(AssetManager.class);
			com.adobe.granite.asset.api.AssetManager removeAssetMan = resourceResolver.adaptTo(com.adobe.granite.asset.api.AssetManager.class);
			
			String imageName = productVM.containsKey(CommonConstants.IMAGE_NAME) ? productVM.get(CommonConstants.IMAGE_NAME).toString() : "";
			String materialNumber = productVM.containsKey(CommonConstants.MATERIAL_NUMBER) ? productVM.get(CommonConstants.MATERIAL_NUMBER).toString() : "";
			Resource variantResource = catalogStructureUpdateService.getProductFromLookUp(resourceResolver, materialNumber, CommonConstants.MATERIAL_NUMBER);
			
			String destinationPath = getGlobalBasePath(resourceResolver, variantResource)+CommonConstants.SLASH+imageName;
				
			imagePathInTempAssets = assetsPathListInDAM+CommonConstants.SLASH+imageName;
			Resource tempAssetResource = resourceResolver.getResource(imagePathInTempAssets);
	        Asset currentAsset = tempAssetResource.adaptTo(Asset.class);
	        InputStream assetStream = currentAsset.getOriginal().getStream();
	        
	        byte[] bytes = getBytesArray(assetStream);
	        InputStream temp = getInputSteram(bytes);
	        
			assetsListToProcess.add(imagePathInTempAssets);
			createAsset(resourceResolver, assetManager, temp, destinationPath, session, removeAssetMan);
				
		} catch (RepositoryException | IOException e) {
			LOGGER.error("Exception occured {}", e);
		}
	}

	/**
	 * 
	 * @param productResource
	 * @param retryCount 
	 * @param updatedRetryCount 
	 * @param updatedRetryCount 
	 * @param retryLimit 
	 * @param productVM
	 */
	private int updateRetryCount(Resource productResource, Session session, int retryCount, int updatedRetryCount, long retryLimit) {
		try {
			ModifiableValueMap modifiableVM = productResource.adaptTo(ModifiableValueMap.class);
			retryCount = modifiableVM.containsKey(CommonConstants.RETRY_COUNT) ? Integer.valueOf(modifiableVM.get(CommonConstants.RETRY_COUNT).toString()) : 0 ;
			
			if (!modifiableVM.containsKey(CommonConstants.RETRY_COUNT)) {
				modifiableVM.put(CommonConstants.RETRY_COUNT, 0);
			} else if (retryCount >= retryLimit) {
				modifiableVM.put(CommonConstants.RETRY_COUNT, retryCount);
				modifiableVM.put(CommonConstants.PROCESS_STATE, FAIL);
				updatedRetryCount = retryCount;
			} else {
				retryCount++;
				modifiableVM.put(CommonConstants.RETRY_COUNT, retryCount);
			}
			session.save();
		} catch (RepositoryException e) {
			LOGGER.error("Exception occured {}", e);
		}
		return updatedRetryCount;
	}

	/**
     * Gets the bytes array.
     *
     * @param inputStream the input stream
     * @return the bytes array
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private byte[] getBytesArray(InputStream inputStream) throws IOException {
        byte[] bytes = null;
        if (null != inputStream) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            org.apache.commons.io.IOUtils.copy(inputStream, baos);
            bytes = baos.toByteArray();
        }
        return bytes;
    }

    /**
     * Gets the input steram.
     *
     * @param bytes the bytes
     * @return the input steram
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private InputStream getInputSteram(byte[] bytes) {
        return null != bytes ? new ByteArrayInputStream(bytes) : null;
    }
	
    /**
     * 
     * @param resourceResolver
     * @param variantResource
     * @return
     * @throws RepositoryException
     */
	private String getGlobalBasePath(ResourceResolver resourceResolver, Resource variantResource) throws RepositoryException {
		
		String damPath = "";
		if (null != variantResource) {
			Resource baseHpResource = CommonHelper.getBaseHpResourceFromLookUp(variantResource);
		    damPath = baseHpResource.getPath().replace(CommonConstants.VAR_COMMERCE_PATH, CommonConstants.CONTENT_DAM)
		            .replace(CommonConstants.PRODUCTS, CommonConstants.PRODUCTS_GLOBAL);
		    if(damPath.endsWith("/hp")) {
		    	damPath = damPath.substring(0, damPath.lastIndexOf("/"));
		    }
		}
		return damPath;
	}
	
	/**
     * to create assert at provided destination path
     *
     * @param assetMan
     * @param imgInputStream
     * @param destPath
     */
    public void createAsset(ResourceResolver serviceResolver, AssetManager assetMan, InputStream imgInputStream, String destPath, Session wfSession, com.adobe.granite.asset.api.AssetManager removeAssetMan) {
        ValueFactory factory;
        try {
            workflowHelperService.createFolderStructure(destPath, wfSession, serviceResolver);
			
			if (wfSession.nodeExists(destPath)) { 
				removeAssetMan.removeAsset(destPath); 
			}
            factory = wfSession.getValueFactory();
            Binary assetBinary = factory.createBinary(imgInputStream);
            assetMan.createOrUpdateAsset(destPath, assetBinary, MIME_TYPE, true);
            LOGGER.debug("Asset is successfully created at specified location : {0}");
        } catch (RepositoryException e) {
            LOGGER.error("Could not create asset at specified location : {0}", e);
        }
        LOGGER.debug("Exit CreateAsset method of ProductImageProcessing");
    }
	
}