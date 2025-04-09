package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;

/**
 * The Class BulletPointIconsModel.
 */
@Model(adaptables = { Resource.class }, adapters = { BulletPointIconsModel.class }, resourceType = {
		BulletPointIconsModel.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BulletPointIconsModel {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/bulletPointsWithIcons/v1/bulletPointsWithIcons";

	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(BulletPointIconsModel.class);
	
	/** The bullet multifield. */
	@Inject
	private List<Resource> bulletMultifield;
	
	/** The bullet point icon list. */
	private List<LabelImagePathAltModel> bulletPointIconList = new ArrayList<>();
	
	/** The description multifield. */
	@Inject
	@Getter
	private List<MultifieldLabelListModel> descriptionMultifield;
	
	/**
	 * Initializes the Bullet Points with Icons Model.
	 */
	@PostConstruct
	protected void init() {
		log.debug("Inside Bullet Points with Icons Model Init");
		populateBulletList();
	}	

	/**
	 * Populate bullet list.
	 */
	private void populateBulletList() {
		if (bulletMultifield != null) {
			for (Resource resource : bulletMultifield) {
				LabelImagePathAltModel bulletResource = resource.adaptTo(LabelImagePathAltModel.class);
				if (bulletResource.getLabel() != null) {
					bulletPointIconList.add(bulletResource);
				}
			}
		}		
	}

	/**
	 * Gets the bullet point icon list.
	 *
	 * @return the bullet point icon list
	 */
	public List<LabelImagePathAltModel> getBulletPointIconList() {
		return new ArrayList<>(bulletPointIconList);
	}
}
