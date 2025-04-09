package com.bdb.aem.core.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.pojo.PrefferedPdfInfo;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.TDSService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;

@Component(service = TDSService.class, immediate = true)
public class TDSServiceImpl implements TDSService {
	
	/** base path to product dam location */
	private static final String DAM_PRODUCT_PATH = "/content/dam/bdb/products/global/";
	/** relative path to product node */
	private static final String VAR_PRODUCT_FOLDER = "products/";
	
	/** Sku base extension*/
	private static final String BASE_PRODUCT = "_base";
	/** Product base pdf path*/
	private static final String PDF_NODE_NAME = "/pdf";
	/** materialNumber of the sku */
	private static final String MATERIAL_NO = "materialNumber";
	/** Hp property for docPartIDs of a Product */
	private static final String DOC_PART_IDS =  "docPartIDs";
	 /**Pipe symbol*/
    private static final String PIPE ="\\|";
    /** PDF extension */
	private static final String PDF_EXTENSION = ".pdf";
	/** TDS DOCUMENT TYPE VALUE */
	private static final String TDS_TYPE_VALUE = "Technical data sheet (TDS)";
    
    /**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TDSServiceImpl.class);
	
	/**
     * The bdb api endpoint service.
     */
    @Reference
    private transient BDBApiEndpointService bdbApiEndpointService;

	/**
	 * Gets the published tds pdf and this method has been updated to accommodate the change related to global vs regional TDS document
	 * now on the basis of region document will be returned. i.e. if both japan and global tds are available then apart from japan 
	 * in all the other regions global TDS document will be shown and in japan document specific to japan will be shown.
	 *
	 * @param skuBaseRes       the sku base res
	 * @param skuBaseVM        the sku base VM
	 * @param resourceResolver the resource resolver
	 * @param countryCode      the country code
	 * @param catalogNumber    the catalog number
	 * @return pdfRes
	 */
	@Override
	public Resource getPublishedTdsPdf(Resource skuBaseRes, ValueMap skuBaseVM, ResourceResolver resourceResolver, String countryCode, String catalogNumber)  {

		String pdfBasePath = skuBaseRes.getPath().replace(CommonConstants.COMMERCE_PATH+VAR_PRODUCT_FOLDER,DAM_PRODUCT_PATH);
		pdfBasePath=pdfBasePath.substring(0, pdfBasePath.indexOf(BASE_PRODUCT)).concat(BASE_PRODUCT).concat(CommonConstants.FORWARD_SLASH_PDF);
		LOGGER.debug("pdfBasePath :: {}",pdfBasePath);
		String pdfResPath=StringUtils.EMPTY;
		Resource pdfRes=null;
		Set<String> fileNamesOfDocPartIdPdf = new HashSet<>();
		Set<String> fileNamesOfMatNumberPdf = new HashSet<>();
		PrefferedPdfInfo prefferedDocPartIdPdfInfo = null;
		PrefferedPdfInfo prefferedMatNumberPdfInfo = null;

		//checking if the document is docpartIDs doc
		if(skuBaseVM.containsKey(DOC_PART_IDS))
		{
			String[] docPartIDsFromMap = CommonHelper.getDocPartIDsFromMap(skuBaseVM);
			if (null != docPartIDsFromMap) {
				fileNamesOfDocPartIdPdf = CommonHelper.convertArrayToSet(docPartIDsFromMap);
				prefferedDocPartIdPdfInfo = getPdfRes(resourceResolver, countryCode, pdfBasePath, fileNamesOfDocPartIdPdf);
			}
		}
		//if pdf is global or not available then checking the document with material number 
		if (!catalogNumber.isEmpty() && ((null != prefferedDocPartIdPdfInfo && prefferedDocPartIdPdfInfo.isGlobal()) || null == prefferedDocPartIdPdfInfo)) {
				fileNamesOfMatNumberPdf.add(catalogNumber + CommonConstants.DOT_PDF);
				prefferedMatNumberPdfInfo = getPdfRes(resourceResolver, countryCode, pdfBasePath, fileNamesOfMatNumberPdf);
		}
		if (null != prefferedDocPartIdPdfInfo && null != prefferedMatNumberPdfInfo && !prefferedMatNumberPdfInfo.isGlobal()) {
			pdfResPath = prefferedMatNumberPdfInfo.getPreferredPdfResPath();
		} else if (null == prefferedDocPartIdPdfInfo && null != prefferedMatNumberPdfInfo){
			pdfResPath = prefferedMatNumberPdfInfo.getPreferredPdfResPath();
		} else {
			if (null != prefferedDocPartIdPdfInfo) {
				pdfResPath = prefferedDocPartIdPdfInfo.getPreferredPdfResPath();
			}
		}
		LOGGER.debug("pdfResPath of getPublishedTdsPdf method: {}", pdfResPath);
		if (StringUtils.isNotBlank(pdfResPath)) {
			pdfRes = resourceResolver.getResource(pdfResPath);
		}
		return pdfRes;
	}

	
	/**
	 * Gets the published tds pdf and this method has been updated to accommodate the change related to global vs regional TDS document
	 * now on the basis of region document will be returned. i.e. if both japan and global tds are available then apart from japan 
	 * in all the other regions global TDS document will be shown and in japan document specific to japan will be shown.
	 *
	 * @param skuBaseRes       the sku base res
	 * @param skuBaseVM        the sku base VM
	 * @param resourceResolver the resource resolver
	 * @param countryCode      the country code
	 * @param catalogNumber    the catalog number
	 * @return pdfRes
	 */
	@Override
	public List<Resource> getMultiLanguagePublishedTdsPdf(Resource skuBaseRes, ValueMap skuBaseVM, ResourceResolver resourceResolver, String countryCode, String catalogNumber)  {

		String pdfBasePath = skuBaseRes.getPath().replace(CommonConstants.COMMERCE_PATH+VAR_PRODUCT_FOLDER,DAM_PRODUCT_PATH);
		pdfBasePath=pdfBasePath.substring(0, pdfBasePath.indexOf(BASE_PRODUCT)).concat(BASE_PRODUCT).concat(CommonConstants.FORWARD_SLASH_PDF);
		LOGGER.debug("pdfBasePath :: {}",pdfBasePath);
		String pdfResPath=StringUtils.EMPTY;
		List<Resource> multiLangPdfResList= new ArrayList<Resource>();
		List<Resource> pathListMultiLangPdf = getMultiLangPdfRes(resourceResolver, countryCode, pdfBasePath,catalogNumber);

		//iterating the list of path of pdf of multi language TDS
		
		
		
		
		//checking if the document is docpartIDs doc
		/*
		 * if(skuBaseVM.containsKey(DOC_PART_IDS)) { String[] docPartIDsFromMap =
		 * CommonHelper.getDocPartIDsFromMap(skuBaseVM); if (null != docPartIDsFromMap)
		 * { fileNamesOfDocPartIdPdf =
		 * CommonHelper.convertArrayToSet(docPartIDsFromMap); prefferedDocPartIdPdfInfo
		 * = getPdfRes(resourceResolver, countryCode, pdfBasePath,
		 * fileNamesOfDocPartIdPdf); } } //if pdf is global or not available then
		 * checking the document with material number if (!catalogNumber.isEmpty() &&
		 * ((null != prefferedDocPartIdPdfInfo && prefferedDocPartIdPdfInfo.isGlobal())
		 * || null == prefferedDocPartIdPdfInfo)) {
		 * fileNamesOfMatNumberPdf.add(catalogNumber + CommonConstants.DOT_PDF);
		 * prefferedMatNumberPdfInfo = getPdfRes(resourceResolver, countryCode,
		 * pdfBasePath, fileNamesOfMatNumberPdf); } if (null !=
		 * prefferedDocPartIdPdfInfo && null != prefferedMatNumberPdfInfo &&
		 * !prefferedMatNumberPdfInfo.isGlobal()) { pdfResPath =
		 * prefferedMatNumberPdfInfo.getPreferredPdfResPath(); } else if (null ==
		 * prefferedDocPartIdPdfInfo && null != prefferedMatNumberPdfInfo){ pdfResPath =
		 * prefferedMatNumberPdfInfo.getPreferredPdfResPath(); } else { if (null !=
		 * prefferedDocPartIdPdfInfo) { pdfResPath =
		 * prefferedDocPartIdPdfInfo.getPreferredPdfResPath(); } }
		 * LOGGER.debug("pdfResPath of getPublishedTdsPdf method: {}", pdfResPath); if
		 * (StringUtils.isNotBlank(pdfResPath)) { pdfRes =
		 * resourceResolver.getResource(pdfResPath); }
		 */
		return pathListMultiLangPdf;
	}
	/**
	 * Gets the pdf res.
	 *
	 * @param resourceResolver the resource resolver
	 * @param countryCode      the country code
	 * @param pdfBasePath      the pdf base path
	 * @param fileNamesOfPdf   the file names of pdf
	 * @return the pdf res
	 */
	private PrefferedPdfInfo getPdfRes(ResourceResolver resourceResolver, String countryCode, String pdfBasePath,
			Set<String> fileNamesOfPdf) {
		LOGGER.debug("Entry getPdfRes of TDSServiceImpl");
		String emeaCountriesListPath = "/etc/acs-commons/lists/bdb/region-country-mapping/eu" + "/jcr:content/list";
    
		List<String> allPdfPaths = CommonHelper.getPdfResources(pdfBasePath, TDS_TYPE_VALUE, resourceResolver,
				countryCode, fileNamesOfPdf, emeaCountriesListPath);
		LOGGER.debug("allPdfPath : {}", allPdfPaths);
		LOGGER.debug("Exit getPdfRes of TDSServiceImpl");
		return CommonHelper.getPreferredPdfRes(resourceResolver, allPdfPaths, countryCode);
	}
	/**
	 * Gets the pdf res.
	 *
	 * @param resourceResolver the resource resolver
	 * @param countryCode      the country code
	 * @param pdfBasePath      the pdf base path
	 * @param fileNamesOfPdf   the file names of pdf
	 * @return the pdf res
	 */
	private List<Resource> getMultiLangPdfRes(ResourceResolver resourceResolver, String countryCode, String pdfBasePath,
			String catalogNumber) {
		LOGGER.debug("Entry getPdfRes of TDSServiceImpl");
		String emeaCountriesListPath = "/etc/acs-commons/lists/bdb/region-country-mapping/eu" + "/jcr:content/list";
    
		List<Resource> allPdfPaths = CommonHelper.getMultiLangPdfResources(pdfBasePath, TDS_TYPE_VALUE, resourceResolver,
				countryCode,  emeaCountriesListPath,catalogNumber);
		LOGGER.debug("allPdfPath : {}", allPdfPaths);
		LOGGER.debug("Exit getPdfRes of TDSServiceImpl");
		return allPdfPaths;
	}
}
