package com.bdb.aem.core.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.bean.BeadlotJsonBean;
import com.bdb.aem.core.bean.BeadlotServletResponse;
import com.bdb.aem.core.bean.BeadlotStructureUpdateResponse;
import com.bdb.aem.core.bean.ErrorSKU;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.BDBWorkflowConfigService;
import com.bdb.aem.core.services.BeadLotStructureUpdateHelperService;
import com.bdb.aem.core.services.BeadLotStructureUpdateService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.WorkflowHelperService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.replication.ReplicationException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * The Class BeadLotStructureUpdateServiceImpl.
 * 
 * The implementation has been changed as per the ticket #
 * and now in place of part number "material number" is being used to fetch
 * documents available under the material number node. the field names are still
 * same at some places but values has been changed from part number to material
 * number. so the variable with part number are actually referring to material number
 */
@Component(service = BeadLotStructureUpdateService.class, immediate = true)
public class BeadLotStructureUpdateServiceImpl implements BeadLotStructureUpdateService {

	private static final String BEADLOT_META_DATA_UPDATED_SUCCESSFULLY = "Beadlot meta-data updated successfully";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BeadLotStructureUpdateServiceImpl.class);
	
	/** The bead lot structure update helper service. */
	@Reference
	BeadLotStructureUpdateHelperService beadLotStructureUpdateHelperService;
	
	/** The solr search service. */
	@Reference
	CatalogStructureUpdateService catalogStructureUpdateService;

	/** The b DB workflow config service. */
	@Reference
	BDBWorkflowConfigService bDBWorkflowConfigService;
	
	/** The search config. */
	@Reference
	BDBSearchEndpointService searchConfig;
	
	/** The job manager. */
	@Reference
	JobManager jobManager;
	
	/** The workflow helper service. */
	@Reference
	WorkflowHelperService workflowHelperService;
	
	/** The all paths. */
	private Set<String> allPaths;
	
	public static final String ERROR = "Error";
	public static final String SUCCESS = "Success";
	public static final String PARTIAL_UPDATE = "PartialUpdate";

	/**
	 * Creates the beadlot structure.
	 *
	 * @param serviceResolver the service resolver
	 * @param jsonObject      the json object
	 * @param session         the session
	 * @return the string
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException the replication exception
	 */
	@Override
	public String createBeadlotStructure(ResourceResolver serviceResolver, JsonObject jsonObject, Session session)
			throws RepositoryException, ReplicationException {
		LOGGER.debug("Entry createBeadlotStructure method");
		List<BeadlotJsonBean> beadlotJsonBeans = beadLotStructureUpdateHelperService.getBeadlotJsonAsList(jsonObject);
		Set<String> setMissingMaterialNumber = new LinkedHashSet<>();
		Set<String> setFoundMaterialNumber = new LinkedHashSet<>();
		if (CollectionUtils.isNotEmpty(beadlotJsonBeans)) {
			LOGGER.debug("Json is in correct format : proccessing");
			allPaths = new HashSet<>();
			for (BeadlotJsonBean beadlotJsonBean : beadlotJsonBeans) {
				if(StringUtils.isNotBlank(beadlotJsonBean.getMaterialnumber())) {
					createOrUpdateBeadlotStructure(serviceResolver, beadlotJsonBean, session, setMissingMaterialNumber,
							setFoundMaterialNumber);
					session.save();
				} else {
					return createResponse(ERROR, "Error occured while processing as material/partNumber is empty", new HashSet<>());
				}
			}
			autoReplicate(serviceResolver);
		}
		LOGGER.debug("Exit createBeadlotStructure method");
		return getResponse(setMissingMaterialNumber, setFoundMaterialNumber);
	}

	/**
	 * Auto replicate.
	 *
	 * @param serviceResolver the service resolver
	 */
	private void autoReplicate(ResourceResolver serviceResolver) {
		if (CollectionUtils.isNotEmpty(allPaths)) {
			Resource adminPageRes = serviceResolver.getResource(
					searchConfig.getAdminPagePath() + CommonConstants.JCR_CONTENT);
			if (null != adminPageRes) {
				ValueMap valueMap = adminPageRes.adaptTo(ValueMap.class);
				if (valueMap.containsKey("slingJobEnabler") && valueMap.get("slingJobEnabler").equals("true")) {
					workflowHelperService.addJobForReplication(new ArrayList<>(allPaths), jobManager,
							BDBDistributionServiceImpl.TOPIC, 100, CommonConstants.REPLICATION_ALL_NODES);
				}
			}
		}
	}

	/**
	 * Gets the response.
	 *
	 * @param setMissingMaterialNumber the set missing Material number
	 * @param setFoundMaterialNumber   the set found Material number
	 * @param jsonObject           the json object
	 * @return the response
	 */
	public String getResponse(Set<String> setMissingMaterialNumber, Set<String> setFoundMaterialNumber) {
		String response = StringUtils.EMPTY;
		String info;
		LOGGER.debug("Entry getResponse method");
		if (CollectionUtils.isEmpty(setMissingMaterialNumber) && !CollectionUtils.isEmpty(setFoundMaterialNumber)) {
			response = createResponse(SUCCESS, BEADLOT_META_DATA_UPDATED_SUCCESSFULLY, new HashSet<>());
		}
		if (!CollectionUtils.isEmpty(setMissingMaterialNumber) && !CollectionUtils.isEmpty(setFoundMaterialNumber)) {
			response = createResponse(SUCCESS, BEADLOT_META_DATA_UPDATED_SUCCESSFULLY, new HashSet<>());
			info = createResponse(SUCCESS, "Beadlot data updated but corresponsding product is not available for the material/part numbers below", getErrorSkus(setMissingMaterialNumber));
			LOGGER.debug("Inforamtion : {}", info);
		} 
		if (!CollectionUtils.isEmpty(setMissingMaterialNumber) && CollectionUtils.isEmpty(setFoundMaterialNumber)) {
			response = createResponse(SUCCESS, BEADLOT_META_DATA_UPDATED_SUCCESSFULLY, new HashSet<>());
			info = createResponse(SUCCESS, "Beadlot data updated but corresponsding product is not available for all the material/part numbers", new HashSet<>());
			LOGGER.debug("Inforamtion : {}", info);
		} 
		if (CollectionUtils.isEmpty(setMissingMaterialNumber) && CollectionUtils.isEmpty(setFoundMaterialNumber)) {
			response = createResponse(ERROR, "Error occured while processing as feed is empty", new HashSet<>());
		} 
		LOGGER.debug("Exit getResponse method");
		return response;
	}
	
	/**
	 * Gets the error skus.
	 *
	 * @param setMissingMaterialNumber the set missing Material number
	 * @return the error skus
	 */
	public Set<ErrorSKU> getErrorSkus(Set<String> setMissingMaterialNumber) {
		LOGGER.debug("Entry getErrorSkus method");
		Set<ErrorSKU> errorSKUs = new HashSet<>();
		for(String materialNumber : setMissingMaterialNumber) {
			ErrorSKU errorSKU = new ErrorSKU();
			errorSKU.setDocumentPartNumber(materialNumber);
			errorSKUs.add(errorSKU);
		}
		LOGGER.debug("Exit getErrorSkus method");
		return errorSKUs;
	}

	/**
	 * Creates the or update beadlot structure.
	 *
	 * @param serviceResolver      the service resolver
	 * @param beadlotJsonBean      the beadlot json bean
	 * @param session              the session
	 * @param setMissingMaterialNumber the set missing Material number
	 * @param setFoundMaterialNumber   the set found Material number
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException the replication exception
	 */
	public void createOrUpdateBeadlotStructure(ResourceResolver serviceResolver, BeadlotJsonBean beadlotJsonBean,
			Session session, Set<String> setMissingMaterialNumber, Set<String> setFoundMaterialNumber)
			throws RepositoryException, ReplicationException {
		LOGGER.debug("Entry createOrUpdateBeadlotStructure method");
		Resource productResource = catalogStructureUpdateService.getProductFromLookUp(serviceResolver, beadlotJsonBean.getMaterialnumber(), CommonConstants.MATERIAL_NUMBER);
		if (null != productResource) {
			Resource hpNodeResource = serviceResolver.getResource(productResource.getPath() + "/hp");
			LOGGER.debug("Hp node found for material number : {}", beadlotJsonBean.getMaterialnumber());
			setFoundMaterialNumber.add(beadlotJsonBean.getMaterialnumber());
			updateHpNode(hpNodeResource, session, beadlotJsonBean.getMaterialDescpription());
			craeteUpdateBeadlotNode(beadlotJsonBean, serviceResolver, session, "true");
		} else {
			LOGGER.debug("Hp node not found for material number : {}", beadlotJsonBean.getMaterialnumber());
			craeteUpdateBeadlotNode(beadlotJsonBean, serviceResolver, session, "false");
			setMissingMaterialNumber.add(beadlotJsonBean.getMaterialnumber());
		}
		LOGGER.debug("Exit createOrUpdateBeadlotStructure method");
	}

	/**
	 * Update hp node.
	 *
	 * @param resourcesByProperty the resources by property
	 * @param session             the session
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException the replication exception
	 */
	public void updateHpNode(Resource resourcesByProperty, Session session, String beadlotTitle)
			throws RepositoryException, ReplicationException {
		LOGGER.debug("Entry updateHpNode method");
		Node hpNode = resourcesByProperty.adaptTo(Node.class);
		String hpNodePath = hpNode.getPath();
		hpNode.setProperty(CommonConstants.HAS_BEADLOT_FILES, "true");
		hpNode.setProperty(CommonConstants.BEADLOT_TITLE, beadlotTitle);
		allPaths.add(hpNodePath);
		LOGGER.debug("Updated and replicated Hp node : {}", hpNodePath);
	}

	/**
	 * Craete update beadlot node.
	 *
	 * @param beadlotJsonBean the beadlot json bean
	 * @param serviceResolver the service resolver
	 * @param session         the session
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException the replication exception
	 */
	public void craeteUpdateBeadlotNode(BeadlotJsonBean beadlotJsonBean, ResourceResolver serviceResolver,
			Session session, String hasProduct) throws RepositoryException, ReplicationException {
		LOGGER.debug("Entry craeteUpdateBeadlotNode method");
		createIfNotExist(CommonConstants.BEADLOT_BASE_PATH, session, serviceResolver);
		String materialnumberNodePath = CommonConstants.BEADLOT_BASE_PATH + CommonConstants.SINGLE_SLASH + beadlotJsonBean.getMaterialnumber();
		if (!session.nodeExists(materialnumberNodePath)) {
			LOGGER.debug("material/part number node path exist : {}", materialnumberNodePath);
			Node createdNode = beadLotStructureUpdateHelperService.createNode(materialnumberNodePath, serviceResolver, session, searchConfig.getCatalogStructureNodeType());
			createdNode.setProperty("materialNumber", beadlotJsonBean.getMaterialnumber());
			createdNode.setProperty("materialDescription", beadlotJsonBean.getMaterialDescpription());
			createdNode.setProperty(CommonConstants.PART_NUMBER, beadlotJsonBean.getPartNumber());
			createdNode.setProperty(CommonConstants.DOC_DESCRIPTION, beadlotJsonBean.getDocumentDescription());
			createdNode.setProperty("hasProduct", hasProduct);
			allPaths.add(materialnumberNodePath);
			createDocumentNumberNode(beadlotJsonBean, session, serviceResolver);
		} else {
			LOGGER.debug("material/part number node path does not exist : {}", materialnumberNodePath);
			Resource materialNumberResource = serviceResolver.getResource(materialnumberNodePath);
			Node materialNumberNode = materialNumberResource.adaptTo(Node.class);
			materialNumberNode.setProperty("materialDescription", beadlotJsonBean.getMaterialDescpription());
			materialNumberNode.setProperty(CommonConstants.PART_NUMBER, beadlotJsonBean.getPartNumber());
			materialNumberNode.setProperty(CommonConstants.DOC_DESCRIPTION, beadlotJsonBean.getDocumentDescription());
			materialNumberNode.setProperty("hasProduct", hasProduct);
			allPaths.add(materialnumberNodePath);
			createDocumentNumberNode(beadlotJsonBean, session, serviceResolver);
		}
		LOGGER.debug("Exit craeteUpdateBeadlotNode method");
	}

	/**
	 * Creates the document number node.
	 *
	 * @param beadlotJsonBean the beadlot json bean
	 * @param session         the session
	 * @param serviceResolver the service resolver
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException the replication exception
	 */
	public void createDocumentNumberNode(BeadlotJsonBean beadlotJsonBean, Session session,
			ResourceResolver serviceResolver) throws RepositoryException, ReplicationException {
		LOGGER.debug("Entry createDocumentNumberNode method");
		String docNumber = beadlotJsonBean.getBeadlotFile().get("documentNumber");
		String docNumberNodePath = CommonConstants.BEADLOT_BASE_PATH + CommonConstants.SINGLE_SLASH + beadlotJsonBean.getMaterialnumber()
				+ CommonConstants.SINGLE_SLASH + docNumber;
		LOGGER.debug("Doc number node path : {}", docNumberNodePath);
		if (!session.nodeExists(docNumberNodePath)) {
			createSubNodeAndSetProperties(beadlotJsonBean, session, serviceResolver, docNumberNodePath);
		} else {
			deleteNode(serviceResolver, docNumberNodePath);
			createSubNodeAndSetProperties(beadlotJsonBean, session, serviceResolver, docNumberNodePath);
		}
		LOGGER.debug("Entry createDocumentNumberNode method");
	}

	/**
	 * Creates the sub node and set properties.
	 *
	 * @param beadlotJsonBean   the beadlot json bean
	 * @param session           the session
	 * @param serviceResolver   the service resolver
	 * @param docNumberNodePath the doc number node path
	 * @throws RepositoryException  the repository exception
	 * @throws ReplicationException the replication exception
	 */
	private void createSubNodeAndSetProperties(BeadlotJsonBean beadlotJsonBean, Session session,
			ResourceResolver serviceResolver, String docNumberNodePath)
			throws RepositoryException {
		LOGGER.debug("Entry createSubNodeAndSetProperties method");
		Node createdNode = beadLotStructureUpdateHelperService.createNode(docNumberNodePath, serviceResolver, session, searchConfig.getCatalogStructureNodeType());
		setDocumentNumberNodeProperties(createdNode, beadlotJsonBean.getBeadlotFile());
		LOGGER.debug("Exit createSubNodeAndSetProperties method");
	}

	/**
	 * Delete node.
	 *
	 * @param serviceResolver   the service resolver
	 * @param docNumberNodePath the doc number node path
	 * @throws RepositoryException the repository exception
	 */
	private void deleteNode(ResourceResolver serviceResolver, String docNumberNodePath) throws RepositoryException {
		LOGGER.debug("Entry deleteNode method");
		Resource docNumberResource = serviceResolver.getResource(docNumberNodePath);
		Node docNumberNode = docNumberResource.adaptTo(Node.class);
		docNumberNode.remove();
		docNumberNode.getSession().save();
		LOGGER.debug("Exit deleteNode method");
	}

	/**
	 * Sets the document number node properties.
	 *
	 * @param createdNode the created node
	 * @param beadlotMap  the beadlot map
	 * @throws RepositoryException the repository exception
	 */
	public void setDocumentNumberNodeProperties(Node createdNode, Map<String, String> beadlotMap)
			throws RepositoryException {
		LOGGER.debug("Entry setDocumentNumberNodeProperties method");
		for (Map.Entry<String, String> entry : beadlotMap.entrySet()) {
			createdNode.setProperty(entry.getKey(), entry.getValue());
		}
		LOGGER.debug("Exit setDocumentNumberNodeProperties method");
	}

	/**
	 * Creates the if not exist.
	 *
	 * @param path            the path
	 * @param session         the session
	 * @param serviceResolver the service resolver
	 * @throws RepositoryException the repository exception
	 */
	public void createIfNotExist(String path, Session session, ResourceResolver serviceResolver)
			throws RepositoryException {
		if (!session.nodeExists(path)) {
			LOGGER.debug("Creating node as it does not exist : {}", path);
			beadLotStructureUpdateHelperService.createNode(path, serviceResolver, session, searchConfig.getCatalogStructureNodeType());
			LOGGER.debug("Exit createIfNotExist method");
		}
	}
	
	/**
	 * Creates the response.
	 *
	 * @param status the status
	 * @param statusMessage the status message
	 * @param errorSKUs the error SK us
	 * @return the string
	 */
	@Override
	public String createResponse(String status, String statusMessage, Set<ErrorSKU> errorSKUs) {
		LOGGER.debug("Entry createResponse method");
		BeadlotServletResponse response = new BeadlotServletResponse();
		BeadlotStructureUpdateResponse structureUpdateResponse = new BeadlotStructureUpdateResponse();
		structureUpdateResponse.setStatus(status);
		structureUpdateResponse.setStatusMessage(statusMessage);
		structureUpdateResponse.setErrorSKUs(errorSKUs);
		response.setReadlotStructureUpdateResponse(structureUpdateResponse);
		Gson gsonInstance = CommonHelper.getGsonInstance();
		LOGGER.debug("Exit createResponse method");
		return gsonInstance.toJson(response);
	}
}