package com.bdb.aem.core.services.tools.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.BaseSlingModel;
import com.bdb.aem.core.services.tools.SlingModelService;

/**
 * The Abstract Sling Model Service.
 * 
 * @author ronbanerjee
 *
 * @param <T>
 */

public abstract class AbstractSlingModelService<T extends BaseSlingModel> implements SlingModelService {

	/** The constant LOGGER */
	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractSlingModelService.class);

	@SuppressWarnings("unchecked")
	@Override
	public void updateModel(BaseSlingModel slingModel) {
		updateSlingModel((T) slingModel);
	}

	/**
	 * Function to update sling model.
	 * 
	 * @param slingModel
	 */
	protected abstract void updateSlingModel(T slingModel);

}
