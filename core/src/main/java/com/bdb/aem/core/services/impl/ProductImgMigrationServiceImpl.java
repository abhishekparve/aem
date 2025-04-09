package com.bdb.aem.core.services.impl;

import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ProductImgMigrationService;
import com.bdb.aem.core.services.UpdateImageMetadataService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.dam.api.Rendition;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

import org.apache.commons.collections.CollectionUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.jcr.Binary;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component(service = ProductImgMigrationService.class, name = "Product Image Migration Service", immediate = true)
public class ProductImgMigrationServiceImpl implements ProductImgMigrationService {

    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(ProductImgMigrationServiceImpl.class);

    /**
     * PNG Extension.
     */
    private static final String PNG = "png";
    /**
     * mime type of an asset
     */
    private static final String MIME_TYPE = "image/png";
    /**
     * Conditional folder name to decide the image types
     */
    private static final String CLINICAL_VIAL_IMAGES = "clinical-vial-images";
    /**
     * product images folder
     */
    private static final String IMAGES_FOLDER = "images";
    /**
     * The png rendition.
     */
    private static final String PNG_RENDITION = "fullsize.png";
    /**
     * The Constant DOT.
     */
    private static final String DOT = ".";

    /**
     * The Constant SLASH.
     */
    private static final String SLASH = "/";

    /**
     * extension the PDF file
     */
    private static final String PDF_EXTENSION = "application/pdf";
	
    /**
     * extension the PNG file
     */
    private static final String PNG_EXTENSION = "image/png";
    
    public static final String PATH = "path";
    public static final String NODE_NAME = "nodename";
    public static final String TYPE = "type";
    public static final String NT_BASE = "nt:base";
    public static final String STARTS_WITH_RECORD = "record*";
    public static final String FLATFILE_IMAGE_METADATA_IN_VAR = "/var/bdb/products/images/unprocessed-records";
    /**
     * The solr search service.
     */
    @Reference
    SolrSearchService solrSearchService;
    /**
     * The catalog structure update service.
     */
    @Reference
    CatalogStructureUpdateService catalogStructureUpdateService;
    /**
     * The workflow helper service.
     */
    @Reference
    private WorkflowHelperService workflowHelperService;
    
    @Reference
    UpdateImageMetadataService updateImageMetadataService;
    
    /** The bdb workflow config service. */
	@Reference
	BDBWorkflowConfigService bdbWorkflowConfigService;
    /**

     * Process product image.
     *
     * @param assetPath        the asset path
     * @param resourceResolver the resource resolver
     * @param wfSession        the wf session
     * @throws RepositoryException the repository exception
     */
    public void processProductImage(String assetPath, ResourceResolver resourceResolver, Session wfSession) throws RepositoryException {
    	
    	// check whether it is a flat file or non flat file image and accordingly process it
    	if(assetPath.contains(bdbWorkflowConfigService.getProductImageBasePath())) {
    		processFlatFileImages(resourceResolver, assetPath, wfSession);
        } else {
        	processNonFlatFileImages(resourceResolver, assetPath, wfSession);
        }
    }
    
    /**
     * 
     * @param resourceResolver the resource resolver
     * @param assetPath the asset path
     * @param wfSession the wf session
     * @throws RepositoryException the repository exception
     */
    private void processNonFlatFileImages(ResourceResolver resourceResolver, String assetPath, Session wfSession) throws RepositoryException {
    	/** name of an asset that got uploaded */
        String fileName = assetPath.substring(assetPath.lastIndexOf(CommonConstants.SLASH) + 1);
        log.debug("assetPath : {}", assetPath);
        Resource res = resourceResolver.getResource(assetPath);
        try {
	        Asset currentAsset = res.adaptTo(Asset.class);
	        InputStream assetStream = currentAsset.getOriginal().getStream();
	        ArrayList<Resource> assetResList = assetList(assetPath, resourceResolver, fileName);
	    	if (CollectionUtils.isNotEmpty(assetResList)) {
	    		processAssetList(assetPath, assetResList, currentAsset, assetStream, fileName, resourceResolver, wfSession);
	    	   } else {
	                // re-processing by scheduler in case of initial failure.
	                reprocessingByScheduler(res);
	            }
	            wfSession.save();
        	} catch (Exception e1) {
				log.error("Exception {}", e1.getMessage());

				/** adding extra reprocessing step via scheduler to remove manual step of reprocess Images in temp folder */
				reprocessingByScheduler(res);
			} 
    }
    
    /**
     * AssetList.
     *
     * @param assetPath        the asset path
     * @param resourceResolver the resource resolver
     * @param fileName        the file name
     * @throws RepositoryException the repository exception
     */
    private ArrayList<Resource> assetList(String assetPath,ResourceResolver resourceResolver, String fileName) throws RepositoryException {
    	
        /** name of an asset without the extension for comparison */
        String propValue = fileName.substring(0, fileName.lastIndexOf(CommonConstants.SINGLE_DOT));
        String clinicalPropValue = fileName.contains("_") ? fileName.substring(0, fileName.indexOf(CommonConstants.UNDERSCORE)) : fileName.substring(0, fileName.lastIndexOf(CommonConstants.SINGLE_DOT));
        log.debug("propValue : {}", propValue);
        log.debug("clinicalPropValue : {}", clinicalPropValue);
        Resource res = null;
        res = resourceResolver.getResource(assetPath);
        ArrayList<Resource> assetResList = new ArrayList<>();
		try {
			
        /**
         * Query to get all the products contains this Asset
         *
         * @returns null if not product is found
         */
        if (assetPath.contains(CommonConstants.SLASH + CLINICAL_VIAL_IMAGES + CommonConstants.SLASH)) {
            Resource assetRes = catalogStructureUpdateService.getProductFromLookUp(resourceResolver,
                    clinicalPropValue, CommonConstants.MATERIAL_NUMBER);
            if (null != assetRes) {
                Resource assetHPRes = resourceResolver.getResource(assetRes.getPath() + "/hp");
                assetResList.add(assetHPRes);
            }
        } else if ((assetPath.substring(0, assetPath.lastIndexOf("/"))).endsWith(IMAGES_FOLDER)) {
            assetResList = solrSearchService.getMatchingImagesHpRes(propValue, resourceResolver);
        } else {
            log.debug("Workflow is not triggered in Images / Clinical-Vial folder.");
        }
			} catch (AccessControlException | IllegalArgumentException e1) {
            log.error("AccessControlException | IllegalArgumentException {}", e1.getMessage());
            /** adding extra reprocessing step via scheduler to remove manual step of reprocess Images in temp folder */
            reprocessingByScheduler(res);
        }

			return assetResList;
    }

    /**
     * Process Asset List.
     *
     * @param assetPath        the asset path
     * @param assetResList     the assetResList
     * @param currentAsset     the current Asset
     * @param assetStream     the asset Stream
     * @param fileName        the file name
     * @param resourceResolver the resource resolver
     * @param wfSession        the wf session
     * @throws RepositoryException the repository exception
     * @throws IOException the IO exception
     */
    public void processAssetList(String assetPath, ArrayList<Resource> assetResList, Asset currentAsset, InputStream assetStream, String fileName, ResourceResolver resourceResolver, Session wfSession) throws IOException, RepositoryException {
    	AssetManager assetMan = resourceResolver.adaptTo(AssetManager.class);
        com.adobe.granite.asset.api.AssetManager removeAssetMan = resourceResolver.adaptTo(com.adobe.granite.asset.api.AssetManager.class);
    	
        InputStream updatedIs = updatedInputStream(currentAsset, assetStream);
        InputStream temp;
        byte[] bytes;
		bytes = getBytesArray(updatedIs);
        temp = getInputStream(bytes);

        /** Creating the Asset using the Updated Input Stream */
        if (null != temp) {
            for (Resource hitRes : assetResList) {
                if (null != hitRes) {
                    log.debug("hitRes: {}", hitRes);
                    String destinationPath = hitRes.getPath().replace(CommonConstants.COMMERCE_PATH + CommonConstants.PRODUCTS + CommonConstants.SLASH, CommonConstants.DAM_PRODUCT_PATH);
                    destinationPath = destinationPath.substring(0, destinationPath.lastIndexOf(CommonConstants.BASE_WITH_SLASH)) + CommonConstants.BASE_WITH_SLASH;
                    destinationPath = destinationPath + fileName.substring(0, fileName.lastIndexOf(CommonConstants.SINGLE_DOT));
                    destinationPath = destinationPath.concat(CommonConstants.SINGLE_DOT).concat(PNG);
                    log.debug("destination path : {}", destinationPath);

                    createAsset(resourceResolver, assetMan, temp, destinationPath, wfSession, removeAssetMan);

                    temp.close();
                    temp = getInputStream(bytes);
                    
            }
                temp.close();
            /** Removing the asset only when it is moved */
            workflowHelperService.moveAssetFromTempAssetFolder(assetPath, resourceResolver, wfSession);
        }
        } else {
        	log.error("updatedInputStream method takes the current asset and checks whether the asset is a pdf or a png image and returns the updated stream."
    			+ "getInputStream method takes the updated stream."
    			+ "getInputStream method is returning a null value");
    }
    }
    
    /**
     * Update Input Stream.
     *
     * @param currentAsset     the current Asset
     * @param assetStream     the asset Stream
     */
    public InputStream updatedInputStream(Asset currentAsset, InputStream assetStream) {
    	InputStream updatedIs;
        if (currentAsset.getMetadata(DamConstants.DC_FORMAT).equals(CommonConstants.APPLICATION_PDF)) {
            log.debug("It Is a PDF Asset !! {}", currentAsset.getName());
            updatedIs = pdfToImage(assetStream);
        } else if (currentAsset.getMetadata(DamConstants.DC_FORMAT).equals(MIME_TYPE)) {
            log.debug("It Is a PNG Image Asset !! {}", currentAsset.getName());
            updatedIs = assetStream;
        } else {
            log.debug("It Is a Image Asset !! {}", currentAsset.getName());
            updatedIs = processNonPngImages(currentAsset);
        }
        return updatedIs;
		
	}

    /**
     * 
     * @param resourceResolver the resource resolver
     * @param assetPath the asset path
     * @param wfSession the wf session
     */
	private void processFlatFileImages(ResourceResolver resourceResolver, String assetPath, Session wfSession) {
    	try {
    		 String fileName = assetPath.substring(assetPath.lastIndexOf(SLASH) + 1);
    		 SearchResult result = getFlatFileImages(resourceResolver, fileName, FLATFILE_IMAGE_METADATA_IN_VAR);
		 InputStream temp;
    		 if (null != result && result.getTotalMatches() > 0) {
    			 AssetManager assetManager = resourceResolver.adaptTo(AssetManager.class);
      			 com.adobe.granite.asset.api.AssetManager removeAssetMan = resourceResolver.adaptTo(com.adobe.granite.asset.api.AssetManager.class);
      			 Resource tempAssetResource = resourceResolver.getResource(assetPath);
      	         Asset currentAsset = tempAssetResource.adaptTo(Asset.class);
      	         InputStream assetStream = currentAsset.getOriginal().getStream();
      	       
      	         InputStream updatedIs;
                
                 if (currentAsset.getMetadata(DamConstants.DC_FORMAT).equals(PDF_EXTENSION)) {
                   log.debug("It Is a PDF Asset !! {}", currentAsset.getName());
                   updatedIs = pdfToImage(assetStream);
                 } else if (currentAsset.getMetadata(DamConstants.DC_FORMAT).equals(PNG_EXTENSION)) {
                   log.debug("It Is a PNG Image Asset !! {}", currentAsset.getName());
                   updatedIs = assetStream;
                 } else {
                   log.debug("It Is a Image Asset !! {}", currentAsset.getName());
                   updatedIs = processNonPngImages(currentAsset);
                 }
               
                 byte[] bytes = getBytesArray(updatedIs);
                 temp = getInputStream(bytes);
      	         
                 for (Hit hit : result.getHits()) {
                	 
	            	 Resource productResource = resourceResolver.getResource(hit.getPath());
	                 String materialNumber = productResource.getName();
	                 Resource variantResource = catalogStructureUpdateService.getProductFromLookUp(resourceResolver, materialNumber, CommonConstants.MATERIAL_NUMBER);
	                
	                 String modifiedFileName = fileName.substring(0, fileName.lastIndexOf(DOT));
	                 modifiedFileName = modifiedFileName.concat(DOT).concat(PNG);
          			 String destinationPath = getGlobalBasePath(resourceResolver, variantResource)+CommonConstants.SLASH+modifiedFileName;
          	         
          			 /** Creating the Asset using the Updated Input Stream */
                     if (null != temp) {
                    	 createAsset(resourceResolver, assetManager, temp, destinationPath, wfSession, removeAssetMan);
                    	 temp.close();
                         temp = getInputStream(bytes);
                     }
                 }
                 if (null != temp) {
                     temp.close();
                 }
                 /** Removing the asset only when it is moved */
                 workflowHelperService.moveAssetFromTempAssetFolder(assetPath, resourceResolver, wfSession);
    		}
    	} catch (RepositoryException | IOException e) {
    		log.error("Exception occured {}", e);
		}
    	
		
	}

	/**
	 * 
	 * @param resourceResolver
	 * @param assetsPathListInDAM
	 * @param assetsReferencePathListInVar
     * @param flatfileImageMetadataInVar 
	 * @return
	 */
	private SearchResult getFlatFileImages(ResourceResolver resourceResolver, String imageName, String assetsReferencePathListInVar) {
        Map<String, Object> params = new HashMap<>();
        params.put(PATH, assetsReferencePathListInVar);
        params.put(TYPE, NT_BASE);
        params.put(CommonConstants.PROPERTY_STRING, CommonConstants.IMAGE_NAME);
        params.put(CommonConstants.PROPERTY_DOT_VALUE_STRING, imageName);

        QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);
        Session session = resourceResolver.adaptTo(Session.class);
        Query query = queryBuilder.createQuery(PredicateGroup.create(params), session);

        return query.getResult();
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
     * function to process images via ImageWorkflowScheduler
     *
     * @param res
     */
    private void reprocessingByScheduler(Resource res) {
        Resource metadataResource = res.getChild(CommonConstants.METADATA_PATH_AS_CHILD);
        if (metadataResource != null) {
            ModifiableValueMap map = metadataResource.adaptTo(ModifiableValueMap.class);
            // Adding properties in Metadata, required by scheduler to check and run the job accordingly.
            if (!map.containsKey(CommonConstants.ATTEMPTS) && !map.containsKey(CommonConstants.FAILURE)) {
                map.put(CommonConstants.ATTEMPTS, 0);
                map.put(CommonConstants.FAILURE, true);
            } else {
                // Incrementing attempts count if workflow still fails to process the asset
                long count = (long) map.get(CommonConstants.ATTEMPTS) + 1;
                map.replace(CommonConstants.ATTEMPTS, count);
            }
        }
        log.error("Mismatch of commerce data between AEM and Solr");
    }

    /**
     * @param currentAsset
     */
    private InputStream processNonPngImages(Asset currentAsset) {
        InputStream updatedIs = null;
        Rendition pngRendition = currentAsset.getRendition(PNG_RENDITION);
        if (null != pngRendition) {
            updatedIs = pngRendition.getStream();
        } else {
            updatedIs = defaultPngImgRendition(currentAsset);
        }
        return updatedIs;
    }

    /**
     * Taking the highest size png image rendition as fullSizePng is missing.
     *
     * @param currentAsset
     * @return
     */
    private InputStream defaultPngImgRendition(Asset currentAsset) {
        InputStream updatedIs;
        List<Rendition> assetRenditions = currentAsset.getRenditions();
        Rendition finalRendition = assetRenditions.get(0);
        long size = 0;
        for (Rendition currentRendition : assetRenditions) {
            if (finalRendition.getMimeType().equals(MIME_TYPE)) {
                if (currentRendition.getSize() > size && currentRendition.getMimeType().equals(MIME_TYPE)) {
                    size = currentRendition.getSize();
                    finalRendition = currentRendition;
                }
            } else {

                if (currentRendition.getMimeType().equals(MIME_TYPE)) {
                    size = currentRendition.getSize();
                    finalRendition = currentRendition;
                }
            }
        }
        updatedIs = finalRendition.getStream();
        log.debug("full size png renditions is not present so created with largest png : {}", finalRendition.getName());
        return updatedIs;
    }

    /**
     * @param assetStream
     * @return Input Stream
     */
    public InputStream pdfToImage(InputStream assetStream) {
        InputStream imgInputStream = null;
        try {
            if (null != assetStream) {
                PDDocument document = PDDocument.load(assetStream);
                PDFRenderer pdfRenderer = new PDFRenderer(document);
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                BufferedImage bufferImage = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);
                bufferImage = getTrimmedImage(bufferImage);
                if (null != bufferImage) {
                    log.debug("pdf not empty");
                    ImageIO.write(bufferImage, PNG, outStream);
                    imgInputStream = new ByteArrayInputStream(outStream.toByteArray());
                }
                document.close();
            }
        } catch (IOException e) {
            log.error("Exception {}", e.getMessage());
        }
        return imgInputStream;
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
        } catch (RepositoryException e) {
            log.error("Could not create asset at specified location : {0}", e);
        }
        log.debug("Exit CreateAsset method of ProductImgMigration");
    }

    /**
     * @param img
     * @return Trimmed Image.
     */
    public BufferedImage getTrimmedImage(BufferedImage img) {
        /** get the width and height of the image after whitespace is removed*/
        int width = getTrimmedWidth(img);
        int height = getTrimmedHeight(img);
        log.debug("width: {} height {}", width, height);
        if (width > 0 && height > 0) {
            int x = getXaxis(img);
            int y = getYaxis(img);
            /**Percentage of the image formed when compared to actual image*/
            int xPercent = (width * 100) / img.getWidth();
            int yPercent = (height * 100) / img.getHeight();
            x = (x >= xPercent) ? (x - xPercent) : 0;
            y = (y >= yPercent) ? (y - yPercent) : 0;
            if (img.getWidth() - width >= xPercent) {
                width += xPercent;
            }
            if (img.getHeight() - height >= yPercent) {
                height += yPercent;
            }
            if (width < img.getWidth() && height < img.getHeight()) {
                log.debug("xAxis:{} ;YAxis:{} ;width:{} ; height :{} ", x, y, width - x, height - y);
                img = img.getSubimage(x, y, width - x, height - y);
            }
        } else {
            log.debug("It is an empty PDF");
            img = null;
        }
        return img;
    }

    /**
     * @param img
     * @return the Shifted X-axis.
     */
    private int getXaxis(BufferedImage img) {
        int height = img.getHeight();
        int width = img.getWidth();
        int xAxis = img.getWidth();
        log.debug("height : {} and width : {} of the image ", height, width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (img.getRGB(j, i) != Color.WHITE.getRGB() &&
                        j < xAxis) {
                    xAxis = j;
                    break;
                }
            }
        }
        log.debug("xAxis: {}", xAxis);
        return xAxis;
    }

    /**
     * @param img
     * @return the Shifted Y-axis.
     */
    private int getYaxis(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int yAxis = img.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (img.getRGB(i, j) != Color.WHITE.getRGB() &&
                        j < yAxis) {
                    yAxis = j;
                    break;
                }
            }
        }
        log.debug("yAxis: {}", yAxis);
        return yAxis;
    }

    /**
     * @param img
     * @return Width of the trimmed image.
     */
    private int getTrimmedWidth(BufferedImage img) {
        int height = img.getHeight();
        int width = img.getWidth();
        int trimmedWidth = 0;

        for (int i = 0; i < height; i++) {
            for (int j = width - 1; j >= 0; j--) {
                if (img.getRGB(j, i) != Color.WHITE.getRGB() &&
                        j > trimmedWidth) {
                    trimmedWidth = j;
                    break;
                }
            }
        }
        log.debug("trimmedWidth: {}", trimmedWidth);
        return trimmedWidth;
    }

    /**
     * @param img
     * @return Height of the trimmed image.
     */
    private int getTrimmedHeight(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int trimmedHeight = 0;

        for (int i = 0; i < width; i++) {
            for (int j = height - 1; j >= 0; j--) {
                if (img.getRGB(i, j) != Color.WHITE.getRGB() &&
                        j > trimmedHeight) {
                    trimmedHeight = j;
                    break;
                }
            }
        }
        log.debug("trimmedHeight: {}", trimmedHeight);
        return trimmedHeight;
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
    private InputStream getInputStream(byte[] bytes) {
        return null != bytes ? new ByteArrayInputStream(bytes) : null;
    }
}
