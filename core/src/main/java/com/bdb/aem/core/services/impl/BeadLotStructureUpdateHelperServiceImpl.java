package com.bdb.aem.core.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.bean.BeadlotJsonBean;
import com.bdb.aem.core.services.BeadLotStructureUpdateHelperService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.commons.jcr.JcrUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * The Class BeadLotStructureUpdateHelperServiceImpl.
 */
@Component(service = BeadLotStructureUpdateHelperService.class, immediate = true)
public class BeadLotStructureUpdateHelperServiceImpl implements BeadLotStructureUpdateHelperService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BeadLotStructureUpdateHelperServiceImpl.class);

	/**
	 * Creates the node.
	 *
	 * @param path             the path
	 * @param resourceResolver the resource resolver
	 * @param session          the session
	 * @param nodeType         the node type
	 * @return the node
	 * @throws RepositoryException the repository exception
	 */
	@Override
	public Node createNode(String path, ResourceResolver resourceResolver, Session session, String nodeType)
			throws RepositoryException {
		LOGGER.debug("Entry createNode method");
		JcrUtil.createPath(path, nodeType, session);
		return resourceResolver.getResource(path).adaptTo(Node.class);
	}

	/**
	 * Gets the beadlot json as list.
	 *
	 * @param jsonObject the json object
	 * @return the beadlot json as list
	 */
	@Override
	public List<BeadlotJsonBean> getBeadlotJsonAsList(JsonObject jsonObject) {
		LOGGER.debug("Entry getBeadlotJsonAsList method");
		List<BeadlotJsonBean> beadlotJsonBeans = new ArrayList<>();
		JsonArray beadlotsArray = jsonObject.getAsJsonArray("beadlots");
		BeadlotJsonBean beadlotJsonBean;
		Map<String, String> documentJsonMap;
		String partNumber = StringUtils.EMPTY;
		String documentDescription = StringUtils.EMPTY;
		String materialnumber = StringUtils.EMPTY;
		String materialDescpription = StringUtils.EMPTY;
		for (JsonElement element : beadlotsArray) {
			beadlotJsonBean = new BeadlotJsonBean();
			documentJsonMap = new HashMap<>();
			JsonObject asJsonObject = element.getAsJsonObject();
			JsonElement jsonElementPartNumber = asJsonObject.get(CommonConstants.PART_NUMBER);
			JsonElement jsonElementDescription = asJsonObject.get(CommonConstants.DOC_DESCRIPTION);
			JsonElement jsonElementMaterialnumber = asJsonObject.get("materialNumber");
			JsonElement jsonElementMaterialDescpription = asJsonObject.get("materialDescription");
			if (null != jsonElementMaterialnumber) {
				materialnumber = removeFirstLastQuotes(jsonElementMaterialnumber.toString());
			} else {
				if (null != jsonElementPartNumber) {
					materialnumber = removeFirstLastQuotes(jsonElementPartNumber.toString());
				}
			}
			if (null != jsonElementMaterialDescpription) {
				materialDescpription = removeFirstLastQuotes(jsonElementMaterialDescpription.toString());
			} else {
				if (null != jsonElementDescription) {
					materialDescpription = removeFirstLastQuotes(jsonElementDescription.toString());
				}
			}
			if (null != jsonElementPartNumber && null != jsonElementDescription) {
				partNumber = removeFirstLastQuotes(jsonElementPartNumber.toString());
				documentDescription = removeFirstLastQuotes(jsonElementDescription.toString());
			}
			LOGGER.debug("Part number : {}", partNumber);
			beadlotJsonBean.setPartNumber(partNumber);
			LOGGER.debug("Document Description : {}", documentDescription);
			beadlotJsonBean.setDocumentDescription(documentDescription);
			LOGGER.debug("Material number : {}", materialnumber);
			beadlotJsonBean.setMaterialnumber(materialnumber);
			LOGGER.debug("Material Description : {}", materialDescpription);
			beadlotJsonBean.setMaterialDescpription(materialDescpription);
			JsonObject asJsonObject2 = asJsonObject.get("beadlotFile").getAsJsonObject();
			for (Entry<String, JsonElement> baseProductEntry : asJsonObject2.entrySet()) {
				documentJsonMap.put(baseProductEntry.getKey().trim(),
						removeFirstLastQuotes(baseProductEntry.getValue().toString().trim()));
			}
			beadlotJsonBean.setBeadlotFile(documentJsonMap);
			beadlotJsonBeans.add(beadlotJsonBean);
		}
		LOGGER.debug("Exit getBeadlotJsonAsList method");
		return beadlotJsonBeans;
	}

	/**
	 * Removes the first last quotes.
	 *
	 * @param input the input
	 * @return the string
	 */
	@Override
	public String removeFirstLastQuotes(String input) {
		LOGGER.debug("Entry removeFirstLastQuotes method");
		String output = StringUtils.EMPTY;
		if (StringUtils.isNotBlank(input) && input.length() > 1) {
			output = input.substring(1, input.length() - 1);
		}
		LOGGER.debug("Exit removeFirstLastQuotes method");
		return output;
	}
}