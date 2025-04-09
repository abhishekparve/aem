package com.bdb.aem.core.services.tools.impl;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.BaseSlingModel;
import com.bdb.aem.core.services.tools.SlingModelService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * The Default Component Service Impl
 * 
 * @author ronbanerjee
 *
 */
@Component(service = DefaultComponentServiceImpl.class)
public class DefaultComponentServiceImpl implements SlingModelService {

	/** The constant LOGGER */
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultComponentServiceImpl.class);

	/* @see com.bdb.aem.core.models.tools.BaseSlingModel#updateModel */
	@Override
	public void updateModel(BaseSlingModel slingModel) {

		String json = generateComponentJson(slingModel);
		Optional.ofNullable(json).ifPresent(j -> {
			slingModel.setComponentJson(j);
			slingModel.setComponentId(String.valueOf(System.currentTimeMillis()));
		});

	}

	/**
	 * Generates the component json.
	 * 
	 * @param slingModel sling model
	 * @return json string.
	 */
	private String generateComponentJson(BaseSlingModel slingModel) {

		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		try {
			String json = mapper.writeValueAsString(slingModel);
			return json;
		} catch (JsonProcessingException e) {
			LOGGER.error("JSON Exception: {}", e);
		}
		return StringUtils.EMPTY;
	}

}