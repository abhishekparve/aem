package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

/**
 * The Class PromoGridModel.
 */
@Model(adaptables = Resource.class)

public class PromoGridModel {
	
	/** The cells. */
	@Inject
	@Optional
	public List<PromoGridDetailsModel> cells; // the multifield name="./cells"

	/**
	 * Gets the cells.
	 *
	 * @return the cells
	 */
	public List<PromoGridDetailsModel> getCells() {
		return new ArrayList<>(cells);
	}

}