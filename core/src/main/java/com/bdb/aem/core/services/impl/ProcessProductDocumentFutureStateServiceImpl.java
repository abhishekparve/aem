package com.bdb.aem.core.services.impl;

import com.adobe.dam.print.ids.StringConstants;
import com.bdb.aem.core.bean.BaseVariantHpResourceBean;
import com.bdb.aem.core.bean.PdfMetaDataBean;
import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.ProcessProductDocumentFutureStateService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SolrSearchConstants;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.day.cq.replication.ReplicationException;
import com.day.cq.tagging.InvalidTagFormatException;
import com.day.cq.tagging.TagManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component(service = ProcessProductDocumentFutureStateService.class, immediate = true)
public class ProcessProductDocumentFutureStateServiceImpl implements ProcessProductDocumentFutureStateService {

	/** The Constant LOG. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessProductDocumentFutureStateServiceImpl.class);

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

	/** The Constant ASSET_TYPE. */
	private static final String ASSET_TYPE = "application/pdf";

	/** The Constant DOC_PART_ID. */
	private static final String DOC_PART_ID = "docPartId";

	/** The Constant PDFX_DOC_PART_ID. */
	private static final String PDFX_DOC_PART_ID = "pdfx:docPartId";

	/** The Constant DOC_TYPE. */
	private static final String DOC_TYPE = "docType";

	/** The Constant PDFX_DOC_TYPE. */
	private static final String PDFX_DOC_TYPE = "pdfx:docType";

	/** The Constant REGION. */
	private static final String REGION = "region";

	/** The Constant Global_REGION. */
	private static final String GLOBAL_REGION = "Global";

	/** The Constant LABEL_DESCRIPTION. */
	private static final String LABEL_DESCRIPTION = "labelDescription";

	/** The Constant TDS_DOCUMENT_TYPE. */
	private static final String TDS_DOCUMENT_TYPE = "tdsDocumentType";

	/** The Constant REGULATORY_STATUS. */
	private static final String REGULATORY_STATUS = "regulatoryStatus";

	/** The Constant TDS_REVISION. */
	private static final String TDS_REVISION = "tdsRevision";

	/** The Constant BRAND. */
	private static final String BRAND = "brand";

	/** The Constant TDS. */
	private static final String TDS = "TDS";

	/** The Constant CLONE. */
	private static final String CLONE = "clone";

	/** The Constant Basic tab description. */
	private static final String BASIC_DESC = "dc:description";

	/** The Constant MATERIAL_NUMBER. */
	private static final String MATERIAL_NUMBER = "materialNumber";

	/** The Constant REGION_TAG_NAMESPACE. */
	private static final String REGION_TAG_NAMESPACE = "/content/cq:tags/bdb/regions";

	/** The Constant PDF_KEYWORDS. */
	private static final String PDF_KEYWORDS = "pdf:Keywords";

	/** The Constant REGION_TAG_ID. */
	private static final String REGION_TAG_ID = "bdb:regions/";
	
	/** The Constant TDS_TYPE. */
	public static final String TDS_TYPE= "Technical data sheet (TDS)";

	/** The bDB workflow config service. */
	@Reference
	private BDBWorkflowConfigService bDBWorkflowConfigService;

	/** The workflow helper service. */
	@Reference
	private WorkflowHelperService workflowHelperService;

	/** The solr search service. */
	@Reference
	private SolrSearchService solrSearchService;

	/**
	 * Process documents.
	 *
	 * @param payloadPath     the payload path
	 * @param serviceResolver the service resolver
	 * @param session         the session
	 * @throws RepositoryException       the repository exception
	 * @throws InvalidTagFormatException the invalid tag format exception
	 * @throws ReplicationException
	 */
	@Override
	public void processDocuments(String payloadPath, ResourceResolver serviceResolver, Session session)
			throws RepositoryException, InvalidTagFormatException, ReplicationException {
		LOGGER.debug("Entry processDocuments method of ProcessProductDocumentFutureState");
		String assetName = CommonHelper.getAssetNameFromPath(payloadPath);
		if (StringUtils.isNotBlank(assetName)) {

			List<BaseVariantHpResourceBean> resourcesListByDocPartId = solrSearchService.getBaseVariantHpNodeResources(
					SolrSearchConstants.DOCPART_IDS_SS, assetName.trim(), serviceResolver);
			if (CollectionUtils.isNotEmpty(resourcesListByDocPartId)) {
				LOGGER.debug("Resources found with docPartIds");
				int resourceListIndex = 0;
				for (BaseVariantHpResourceBean resourceBean : resourcesListByDocPartId) {
					resourceListIndex++;
					PdfMetaDataBean pdfMetaDataBean = getPdfMetadataFromAsset(payloadPath, serviceResolver,
							resourceBean);
					if (canProcess(pdfMetaDataBean)) {
						pdfMetaDataBean.setDamFolderFileName(assetName);
						pdfMetaDataBean.setTempFolderFileName(assetName);
						pdfMetaDataBean.setTempFolderFilePath(payloadPath);
						processAssets(pdfMetaDataBean, serviceResolver, session, resourceBean);
						if(resourceListIndex == resourcesListByDocPartId.size()) {
							workflowHelperService.moveAssetFromTempAssetFolder(payloadPath, serviceResolver, session);
						}
					} else {
						LOGGER.error("Mendatory Info is not present in HP resource of the product found by docPartIds");
					}
				}
				

			} else {
				List<BaseVariantHpResourceBean> resourcesListByMatNum = solrSearchService.getBaseVariantHpNodeResources(
						CommonConstants.UNIQUE_IDENTIFIER, assetName.trim(), serviceResolver);
				if (CollectionUtils.isNotEmpty(resourcesListByMatNum)) {
					LOGGER.debug("Resources found with MatNumber");
					int resourceListIndex = 0;
					for (BaseVariantHpResourceBean resourceBean : resourcesListByMatNum) {
						resourceListIndex++;
						PdfMetaDataBean pdfMetaDataBean = getPdfMetadataFromAsset(payloadPath, serviceResolver,
								resourceBean);
						if (canProcess(pdfMetaDataBean)) {
							pdfMetaDataBean.setDamFolderFileName(assetName);
							pdfMetaDataBean.setTempFolderFileName(assetName);
							pdfMetaDataBean.setTempFolderFilePath(payloadPath);
							processAssets(pdfMetaDataBean, serviceResolver, session, resourceBean);
							if(resourceListIndex == resourcesListByMatNum.size()) {
								workflowHelperService.moveAssetFromTempAssetFolder(payloadPath, serviceResolver, session);
							}
						} else {
							LOGGER.error(
									"Mendatory Info is not present in HP resource of the product found by MatNumber");
						}
					}
					
				} else {
					
					if(assetName.contains("_")) {
						int underscoreIndex = assetName.lastIndexOf("_");
						String catalogNum = assetName.substring(0, underscoreIndex);
						List<BaseVariantHpResourceBean> resourcesListByMatNumMultiLang = solrSearchService.getBaseVariantHpNodeResources(
								CommonConstants.UNIQUE_IDENTIFIER, catalogNum.trim(), serviceResolver);
						
						if (CollectionUtils.isNotEmpty(resourcesListByMatNumMultiLang)) {
							LOGGER.debug("Resources found with Multi Language MatNumber");
							int resourceListIndex = 0;
							for (BaseVariantHpResourceBean resourceBean : resourcesListByMatNumMultiLang) {
								resourceListIndex++;
								PdfMetaDataBean pdfMetaDataBean = getPdfMetadataFromAsset(payloadPath, serviceResolver,
										resourceBean);
								if (canProcess(pdfMetaDataBean)) {
									pdfMetaDataBean.setDamFolderFileName(assetName);
									pdfMetaDataBean.setTempFolderFileName(assetName);
									pdfMetaDataBean.setTempFolderFilePath(payloadPath);
									processAssets(pdfMetaDataBean, serviceResolver, session, resourceBean);
									if(resourceListIndex == resourcesListByMatNumMultiLang.size()) {
										workflowHelperService.moveAssetFromTempAssetFolder(payloadPath, serviceResolver, session);
									}
								} else {
									LOGGER.error(
											"Mendatory Info is not present in HP resource of the product found by Multi Language MatNumber");
								}
							}
							
						}else {
							LOGGER.error("Could not find Asset in Product Catalogue with both DocpartId and MatNumber and Multi Language");
						}
					}else {
					
					LOGGER.error("Could not find Asset in Product Catalogue with both DocpartId and MatNumber and do not have any document for Multi Language Name");
					}
				}
			}
			//workflowHelperService.moveAssetFromTempAssetFolder(payloadPath, serviceResolver, session);

		} else {
			LOGGER.error("Asset name is not in correct format");
		}
		LOGGER.debug("Exit processDocuments method of ProcessProductDocumentFutureState");
	}

	/**
	 * Process assets.
	 *
	 * @param pdfMetaDataBean the pdf meta data bean
	 * @param serviceResolver the service resolver
	 * @param session         the session
	 * @throws RepositoryException       the repository exception
	 * @throws InvalidTagFormatException the invalid tag format exception
	 * @throws ReplicationException
	 */
	public void processAssets(PdfMetaDataBean pdfMetaDataBean, ResourceResolver serviceResolver, Session session,
			BaseVariantHpResourceBean hpResourcesBean)
			throws RepositoryException, InvalidTagFormatException, ReplicationException {
		LOGGER.debug("Entry processAssets method of ProcessProductDocumentFutureState");

		moveResourceToDam(hpResourcesBean, pdfMetaDataBean, serviceResolver, session);

		LOGGER.debug("Exit processAssets method of ProcessProductDocumentFutureState");
	}

	/**
	 * Can process.
	 *
	 * @param pdfMetaDataBean the pdf meta data bean
	 * @return true, if successful
	 */
	public boolean canProcess(PdfMetaDataBean pdfMetaDataBean) {
		LOGGER.debug("Entry canProcess method of ProcessProductDocumentFutureState");
		if (null != pdfMetaDataBean) {
			if (StringUtils.isNotBlank(pdfMetaDataBean.getDocType())
					&& StringUtils.isNotBlank(pdfMetaDataBean.getDocTitle())
					&& StringUtils.isNotBlank(pdfMetaDataBean.getDocRegStatus())
					&& pdfMetaDataBean.getDocRegion().length > 0) {
				LOGGER.debug("Asset is processable");
				return true;
			} else {
				LOGGER.debug("Cannot process this Asset");
				return false;
			}
		} else {
			LOGGER.debug("Could not get details from asset metadata");
			return false;
		}

	}

	/**
	 * Gets the pdf metadata from asset.
	 *
	 * @param assetPath       the asset path
	 * @param serviceResolver the service resolver
	 * @return the pdf metadata from asset
	 */
	public PdfMetaDataBean getPdfMetadataFromAsset(String assetPath, ResourceResolver serviceResolver,
			BaseVariantHpResourceBean resourcesBean) {
		LOGGER.debug("Entry getPdfMetadataFromAsset method of ProcessProductDocumentFutureState");
		PdfMetaDataBean pdfMetaDataBean = null;

		Resource baseHpResource = resourcesBean.getBaseHpResource();

		if (null != baseHpResource) {

			ValueMap baseHpValueMap = baseHpResource.adaptTo(ValueMap.class);
			Resource pdfMetadataResource = serviceResolver.getResource(assetPath + CommonConstants.METADATAPATH);
			if (null != pdfMetadataResource) {
				// Checking if regulatoryStatus = "RUO" on base HP node to Identify if the
				// document is TDS or not
				if (baseHpValueMap.get(REGULATORY_STATUS, StringUtils.EMPTY).equals("RUO")) {

					pdfMetaDataBean = getDetailsFromHPResource(pdfMetadataResource, resourcesBean);

				} else {

					pdfMetaDataBean = getDetailsFromNode(pdfMetadataResource);
				}
			}

		}
		LOGGER.debug("Exit getPdfMetadataFromAsset method of ProcessProductDocumentFutureState");
		return pdfMetaDataBean;
	}

	/**
	 * Gets the details from HP Resource.
	 *
	 * @param pdfMetadataResource the pdf metadata resource
	 * @param resourcesBean       the HP Resource of Base and Variant * @return the
	 *                            details from node
	 */
	public PdfMetaDataBean getDetailsFromHPResource(Resource pdfMetadataResource,
			BaseVariantHpResourceBean resourcesBean) {
		LOGGER.debug("Entry getDetailsFromNode method of ProcessProductDocumentFutureState");
		PdfMetaDataBean pdfMetaDataBean = null;

		ValueMap oldProperties = pdfMetadataResource.adaptTo(ValueMap.class);
		ModifiableValueMap properties = pdfMetadataResource.adaptTo(ModifiableValueMap.class);

		if (MapUtils.isNotEmpty(oldProperties)) {
			int count = oldProperties.size();
			List<String> keyToRemove = new ArrayList<>();
			Pattern p = Pattern.compile("^[A-Za-z]*:[A-Za-z]*[0-9]?");
			for (String x : oldProperties.keySet()) {
				if (count <= 0)
					break;
				Matcher m = p.matcher(x);
				int len = -1, start = 0;
				while (m.find()) {
					start = m.start();
					len = m.end() - start;
				}
				String newKey = x.substring(start, len);
				if (!newKey.equals(x) && !properties.containsKey(newKey)) {
					properties.put(newKey, oldProperties.get(x, StringUtils.EMPTY));
					keyToRemove.add(x);
				}
				count--;
			}

			for (String str : keyToRemove) {
				properties.remove(str);
			}

			pdfMetaDataBean = new PdfMetaDataBean();

			updateMetaDataFromHpResource(resourcesBean, pdfMetaDataBean);

			pdfMetaDataBean.setDocDesc(getMendatoryProp(properties, PDFX_DOC_DESC, StringConstants.METADATA_DESC));
			pdfMetaDataBean.setDocExpiryDate(properties.get(PDFX_DOC_EXPIRY_DATE, StringUtils.EMPTY));
			pdfMetaDataBean.setDocOwner(properties.get(PDFX_DOC_OWNER, DEFAULT_DOC_OWNER));
			pdfMetaDataBean.setDocKeywords(getMendatoryProp(properties, PDFX_DOC_KEYWORDS, PDF_KEYWORDS));
			pdfMetaDataBean.setDocPartId(properties.get(PDFX_DOC_PART_ID, StringUtils.EMPTY));

		}

		LOGGER.debug("Exit getDetailsFromNode method of ProcessProductDocumentFutureState");
		return pdfMetaDataBean;
	}

	/**
	 * Gets the details from node.
	 *
	 * @param pdfMetadataResource the pdf metadata resource
	 * @return the details from node
	 */
	public PdfMetaDataBean getDetailsFromNode(Resource pdfMetadataResource) {
		LOGGER.debug("Entry getDetailsFromNode method of ProcessProductDocumentFutureState");
		PdfMetaDataBean pdfMetaDataBean = null;
		ValueMap oldProperties = pdfMetadataResource.adaptTo(ValueMap.class);
		ModifiableValueMap properties = pdfMetadataResource.adaptTo(ModifiableValueMap.class);

		if (MapUtils.isNotEmpty(oldProperties)) {
			int count = oldProperties.size();
			List<String> keyToRemove = new ArrayList<>();
			Pattern p = Pattern.compile("^[A-Za-z]*:[A-Za-z]*[0-9]?");
			for (String x : oldProperties.keySet()) {
				if (count <= 0)
					break;
				Matcher m = p.matcher(x);
				int len = -1, start = 0;
				while (m.find()) {
					start = m.start();
					len = m.end() - start;
				}
				String newKey = x.substring(start, len);
				if (!newKey.equals(x) && !properties.containsKey(newKey)) {
					properties.put(newKey, oldProperties.get(x, StringUtils.EMPTY));
					keyToRemove.add(x);
				}
				count--;
			}

			for (String str : keyToRemove) {
				properties.remove(str);
			}

			pdfMetaDataBean = new PdfMetaDataBean();
			pdfMetaDataBean.setDocTitle(getMendatoryProp(properties, PDFX_DOC_TITLE, JcrConstants.JCR_TITLE));
			pdfMetaDataBean.setDocType(properties.get(PDFX_DOC_TYPE, StringUtils.EMPTY));
			pdfMetaDataBean.setDocRegion(getRegions(properties.get(PDFX_DOC_REGION, StringUtils.EMPTY)));
			pdfMetaDataBean.setDocLang(properties.get(PDFX_DOC_LANG, DEFAULT_DOC_LANG));
			pdfMetaDataBean.setDocSku(properties.get(PDFX_DOC_SKU, StringUtils.EMPTY));
			pdfMetaDataBean.setDocDesc(getMendatoryProp(properties, PDFX_DOC_DESC, StringConstants.METADATA_DESC));
			pdfMetaDataBean.setDocExpiryDate(properties.get(PDFX_DOC_EXPIRY_DATE, StringUtils.EMPTY));
			pdfMetaDataBean.setDocRegStatus(properties.get(PDFX_DOC_REG_STATUS, StringUtils.EMPTY));
			pdfMetaDataBean.setDocRevision(properties.get(PDFX_DOC_REV, StringUtils.EMPTY));
			pdfMetaDataBean.setDocOwner(properties.get(PDFX_DOC_OWNER, DEFAULT_DOC_OWNER));
			pdfMetaDataBean.setProductName(properties.get(PDFX_DOC_PROD_NAME, StringUtils.EMPTY));
			pdfMetaDataBean.setDocKeywords(getMendatoryProp(properties, PDFX_DOC_KEYWORDS, PDF_KEYWORDS));
			pdfMetaDataBean.setDocPartId(properties.get(PDFX_DOC_PART_ID, StringUtils.EMPTY));

			String docTitle = getMendatoryProp(properties, PDFX_DOC_TITLE, JcrConstants.JCR_TITLE);
			String docSku = properties.containsKey(PDFX_DOC_SKU) ? properties.get(PDFX_DOC_SKU, StringUtils.EMPTY) : "";
			String pdfType = properties.containsKey(PDFX_DOC_TYPE) ? properties.get(PDFX_DOC_TYPE, StringUtils.EMPTY)
					: "";
			if (pdfType.contains("TDS") || pdfType.contains("Technical Data Sheet")) {
				pdfType = "TDS";
			}
			if (pdfType.contains("IFU") || pdfType.contains("Instruction for use")) {
				pdfType = "IFU";
			}

			if (pdfType.equals("TDS") || pdfType.equals("IFU") || pdfType.equalsIgnoreCase("Ingredient disclosure")
					|| pdfType.equalsIgnoreCase("Package/product insert") || pdfType.equalsIgnoreCase("eBook")
					|| pdfType.equalsIgnoreCase("User guide/manual") || pdfType.equalsIgnoreCase("CFDA license")) {
				pdfMetaDataBean.setDesc(docTitle + " - " + pdfType + " " + docSku);
			}
		}

		LOGGER.debug("Exit getDetailsFromNode method of ProcessProductDocumentFutureState");
		return pdfMetaDataBean;
	}

	/**
	 * Update the metadata bean from HP Resource.
	 *
	 * @param pdfMetadataBean the pdf metadata resource
	 * @param resourceBean the bean containing the Base and Variant HP Resource
	 */
	public void updateMetaDataFromHpResource(BaseVariantHpResourceBean resourcesBean, PdfMetaDataBean pdfMetaDataBean) {

		Resource variantHpResource = resourcesBean.getVariantHpResource();
		Resource baseHpResource = resourcesBean.getBaseHpResource();

		if (null != baseHpResource && null != variantHpResource) {

			ValueMap baseHpValueMap = baseHpResource.adaptTo(ValueMap.class);
			ValueMap variantHpValueMap = variantHpResource.adaptTo(ValueMap.class);

			String tdsCloneName= "";
			JsonArray jsonArray = new JsonParser().parse(baseHpValueMap.get(CLONE, StringUtils.EMPTY)).getAsJsonArray();
			for (JsonElement jsonElement : jsonArray) {
				JsonObject productObj = jsonElement.getAsJsonObject();
				if (null != productObj.get(CommonConstants.TDS_CLONE_NAME)) {
					tdsCloneName = productObj.get(CommonConstants.TDS_CLONE_NAME).getAsString();
				}
			}
			
			String[] globalRegionArray = new String[] { GLOBAL_REGION };

			pdfMetaDataBean.setDocRegion(globalRegionArray);
			pdfMetaDataBean.setDocLang(DEFAULT_DOC_LANG);

			pdfMetaDataBean.setDocTitle(baseHpValueMap.get(LABEL_DESCRIPTION, StringUtils.EMPTY)
					+ " "+tdsCloneName+" "
					+ baseHpValueMap.get(REGULATORY_STATUS, StringUtils.EMPTY));
			pdfMetaDataBean.setDocType(TDS_TYPE);

			pdfMetaDataBean.setDocSku(variantHpValueMap.get(MATERIAL_NUMBER, StringUtils.EMPTY));
			pdfMetaDataBean.setDocRegStatus(baseHpValueMap.get(REGULATORY_STATUS, StringUtils.EMPTY));
			pdfMetaDataBean.setDocRevision(variantHpValueMap.get(TDS_REVISION, StringUtils.EMPTY));
			pdfMetaDataBean.setProductName(baseHpValueMap.get(BRAND, StringUtils.EMPTY));
			pdfMetaDataBean.setDesc(pdfMetaDataBean.getDocTitle() + " - " + TDS + " " + pdfMetaDataBean.getDocSku());

		}
	}

	/**
	 * Gets the mendatory prop.
	 *
	 * @param properties the properties
	 * @param firstKey   the first key
	 * @param secondKey  the second key
	 * @return the mendatory prop
	 */
	private String getMendatoryProp(ValueMap properties, String firstKey, String secondKey) {
		String value = properties.get(firstKey, StringUtils.EMPTY);
		if (!StringUtils.isNotBlank(value)) {
			value = properties.get(secondKey, StringUtils.EMPTY);
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
	 * Delete asset from dam folder.
	 *
	 * @param assetPath       the asset path
	 * @param serviceResolver the service resolver
	 * @param session         the session
	 * @throws RepositoryException the repository exception
	 */
	public void deleteAssetFromDamFolder(String assetPath, ResourceResolver serviceResolver, Session session)
			throws RepositoryException {
		LOGGER.debug("Entry deleteAssetFromTempFolder method of ProcessProductDocumentFutureState");
		com.adobe.granite.asset.api.AssetManager removeAssetMan = serviceResolver
				.adaptTo(com.adobe.granite.asset.api.AssetManager.class);
		LOGGER.debug("Removing resource : {}", assetPath);
		removeAssetMan.removeAsset(assetPath);
		session.save();
		LOGGER.debug("Exit deleteAssetFromTempFolder method of ProcessProductDocumentFutureState");
	}
	
	/**
	 * Delete the asset from solr.
	 *
	 * @param assetPath       the asset path
	 * @throws SolrServerException the solr server exception
	 */
	public void deleteAssetFromSolr(String pdfPath) {
		try {
			ArrayList<HttpSolrClient> solrClients = (ArrayList<HttpSolrClient>) solrSearchService.getAllSolrClients();
			for (HttpSolrClient server : solrClients) {
				server.deleteById(pdfPath);
				server.commit();
				server.close();
			}
		} catch (SolrServerException | LoginException | IOException e) {
			LOGGER.error("Exception occured due to", e);
		}
	}

	/**
	 * Move resource to dam.
	 *
	 * @param resourceFromQuery the resource from query
	 * @param pdfMetaDataBean   the pdf meta data bean
	 * @param serviceResolver   the service resolver
	 * @param session           the session
	 * @return the list
	 * @throws RepositoryException       the repository exception
	 * @throws InvalidTagFormatException the invalid tag format exception
	 * @throws ReplicationException
	 */
	public void moveResourceToDam(BaseVariantHpResourceBean resourceBeanFromQuery, PdfMetaDataBean pdfMetaDataBean,
			ResourceResolver serviceResolver, Session session)
			throws RepositoryException, InvalidTagFormatException, ReplicationException {
		LOGGER.debug("Entry moveResourceToDam method of ProcessProductDocumentFutureState");

		pdfMetaDataBean.setDamFolderFilePath(CommonHelper.getDamPathFromVarComResource(bDBWorkflowConfigService,
				resourceBeanFromQuery.getVariantHpResource().getPath(), pdfMetaDataBean.getTempFolderFileName()));
		createResourceInDam(pdfMetaDataBean, serviceResolver, session);

		LOGGER.debug("Entry moveResourceToDam method of ProcessProductDocumentFutureState");

	}

	/**
	 * Creates the resource in dam.
	 *
	 * @param pdfMetaDataBean the pdf meta data bean
	 * @param serviceResolver the service resolver
	 * @param session         the session
	 * @throws RepositoryException       the repository exception
	 * @throws InvalidTagFormatException the invalid tag format exception
	 * @throws ReplicationException
	 */
	public void createResourceInDam(PdfMetaDataBean pdfMetaDataBean, ResourceResolver serviceResolver, Session session)
			throws RepositoryException, InvalidTagFormatException, ReplicationException {
		LOGGER.debug("Entry createResourceInDam method of ProcessProductDocumentFutureState");
		createTagsForAsset(pdfMetaDataBean, serviceResolver, session);
		createResource(pdfMetaDataBean, serviceResolver, session);
		session.save();
		LOGGER.debug("Exit createResourceInDam method of ProcessProductDocumentFutureState");
	}

	/**
	 * Creates the tags for asset.
	 *
	 * @param pdfMetaDataBean the pdf meta data bean
	 * @param serviceResolver the service resolver
	 * @throws InvalidTagFormatException the invalid tag format exception
	 * @throws ReplicationException
	 */
	public void createTagsForAsset(PdfMetaDataBean pdfMetaDataBean, ResourceResolver serviceResolver, Session session)
			throws InvalidTagFormatException, ReplicationException {
		LOGGER.debug("Entry createTagsForAsset method of ProcessProductDocumentFutureState");
		TagManager tagManager = serviceResolver.adaptTo(TagManager.class);
		String[] regions = pdfMetaDataBean.getDocRegion();
		if (null != tagManager && null != regions && regions.length > 0) {
			tagManager.createTag(REGION_TAG_NAMESPACE, REGION, "regions tag for pdf regions");
			for (String region : regions) {
				tagManager.createTag(REGION_TAG_NAMESPACE + CommonConstants.SINGLE_SLASH + region.trim().toLowerCase(),
						region.trim().toUpperCase(), StringUtils.EMPTY);
				// workflowHelperService.replicateResourceToPublish(session,
				// REGION_TAG_NAMESPACE + CommonConstants.SINGLE_SLASH +
				// region.trim().toLowerCase());
				LOGGER.debug("Tags created successfully for region {}", region);
			}

		}
		LOGGER.debug("Exit createTagsForAsset method of ProcessProductDocumentFutureState");
	}

	/**
	 * Creates the resource.
	 *
	 * @param pdfMetaDataBean the pdf meta data bean
	 * @param serviceResolver the service resolver
	 * @param session         the session
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException
	 */
	public void createResource(PdfMetaDataBean pdfMetaDataBean, ResourceResolver serviceResolver, Session session)
			throws RepositoryException, ReplicationException {
		LOGGER.debug("Entry createResource method of ProcessProductDocumentFutureState");
		AssetManager assetMan = serviceResolver.adaptTo(AssetManager.class);
		Asset currentAsset = serviceResolver.getResource(pdfMetaDataBean.getTempFolderFilePath()).adaptTo(Asset.class);
		InputStream assetStream = currentAsset.getOriginal().getStream();
		Asset asset = createAsset(assetMan, assetStream, pdfMetaDataBean.getDamFolderFilePath(), ASSET_TYPE,
				serviceResolver, session);
		if (null != asset) {
			LOGGER.debug("Asset has been created at DAM location : {}", asset.getPath());
			setMetadataForAsset(pdfMetaDataBean, serviceResolver);
		} else {
			LOGGER.error("Could not create asset at the DAM location : {}", pdfMetaDataBean.getDamFolderFilePath());
		}
		LOGGER.debug("Exit createResource method of ProcessProductDocumentFutureState");
	}

	/**
	 * Sets the metadata for asset.
	 *
	 * @param pdfMetaDataBean the pdf meta data bean
	 * @param serviceResolver the service resolver
	 * @throws RepositoryException the repository exception
	 */
	public void setMetadataForAsset(PdfMetaDataBean pdfMetaDataBean, ResourceResolver serviceResolver)
			throws RepositoryException {
		LOGGER.debug("Entry setMetadataForAsset method of ProcessProductDocumentFutureState");
		Resource pdfMetadataResource = serviceResolver
				.getResource(pdfMetaDataBean.getDamFolderFilePath() + CommonConstants.METADATAPATH);
		if (null != pdfMetadataResource) {
			Node currentNode = pdfMetadataResource.adaptTo(Node.class);
			LOGGER.debug("Working on Dam node : {}", currentNode.getPath());
			placeMetadata(pdfMetaDataBean, currentNode);
		}
		LOGGER.debug("Exit setMetadataForAsset method of ProcessProductDocumentFutureState");
	}

	/**
	 * Place metadata.
	 *
	 * @param pdfMetaDataBean the pdf meta data bean
	 * @param currentNode     the current node
	 * @throws RepositoryException the repository exception
	 */
	public void placeMetadata(PdfMetaDataBean pdfMetaDataBean, Node currentNode) throws RepositoryException {
		LOGGER.debug("Entry placeMetadata method of ProcessProductDocumentFutureState");
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
		currentNode.setProperty(BASIC_DESC, pdfMetaDataBean.getDesc());
		LOGGER.debug("Exit placeMetadata method of ProcessProductDocumentFutureState");
	}

	/**
	 * Gets the as tags.
	 *
	 * @param regions the regions
	 * @return the as tags
	 */
	public String[] getAsTags(String[] regions) {
		LOGGER.debug("Entry getAsTags method of ProcessProductDocumentFutureState");
		String tagBasePathId = REGION_TAG_ID;
		String[] regionAsTag = new String[regions.length];
		for (int i = 0; i < regionAsTag.length; i++) {
			regionAsTag[i] = tagBasePathId + regions[i].trim().toLowerCase();
		}
		LOGGER.debug("Exit getAsTags method of ProcessProductDocumentFutureState");
		return regionAsTag;
	}

	/**
	 * Creates the asset.
	 *
	 * @param assetMan         the asset man
	 * @param assetInputStream the asset input stream
	 * @param destPath         the dest path
	 * @param assetType        the asset type
	 * @param serviceResolver  the service resolver
	 * @param session          the session
	 * @return the asset
	 * @throws RepositoryException the repository exception
	 */
	public Asset createAsset(AssetManager assetMan, InputStream assetInputStream, String destPath, String assetType,
			ResourceResolver serviceResolver, Session session) throws RepositoryException {
		LOGGER.debug("Entry CreateAsset method of ProcessProductDocumentFutureState");
		ValueFactory factory;
		Asset asset = null;
		workflowHelperService.createFolderStructure(destPath, session, serviceResolver);
		if (session.nodeExists(destPath)) {
			// Deleting the document from solr
			deleteAssetFromSolr(destPath);
			deleteAssetFromDamFolder(destPath, serviceResolver, session);
		}
		factory = session.getValueFactory();
		Binary assetBinary = factory.createBinary(assetInputStream);
		asset = assetMan.createOrUpdateAsset(destPath, assetBinary, assetType, true);
		LOGGER.debug("Exit CreateAsset method of ProcessProductDocumentFutureState");
		return asset;
	}
}