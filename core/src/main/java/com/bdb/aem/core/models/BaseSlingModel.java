package com.bdb.aem.core.models;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.tools.SlingModelService;
import com.bdb.aem.core.services.tools.impl.DefaultComponentServiceImpl;
import com.bdb.aem.core.services.tools.impl.DefaultDataServiceImpl;
import com.day.cq.wcm.api.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Base Sling Model.
 * 
 * @author ronbanerjee
 *
 */
@JsonInclude(Include.NON_NULL)
public class BaseSlingModel implements Serializable {

	/** The constant serial version UUID */
	private static final long serialVersionUID = -3399719408411312979L;

	/** The constant LOGGER */
	protected static final Logger LOGGER = LoggerFactory.getLogger(BaseSlingModel.class);

	/** The resource */
	@Inject
	protected Resource resource;

	/** The current page */
	@Inject
	protected Page page;

	/** The Default Component Service */
	@Inject
	private DefaultComponentServiceImpl defaultComponentService;

	/** The Default Data Service */
	@Inject
	private DefaultDataServiceImpl defaultDataService;
	
	/** The Externalizer */
	@Inject
	protected ExternalizerService externalizer;
	
	/** The component json */
	private String componentJson;

	/** The unique component id */
	private String componentId;

	/** The init */
	@PostConstruct
	protected void init() {
		LOGGER.info("Into {}", this.getClass().getTypeName());
		getDataService().updateModel(this);
		defaultComponentService.updateModel(this);
	}
	/**
	 * Gets the resource.
	 * 
	 * @return the resource
	 */
	@JsonIgnore
	public Resource getResource() {
		return resource;
	}

	/**
	 * Gets the page.
	 * 
	 * @return the page
	 */
	@JsonIgnore
	public Page getCurrentPage() {
		return page;
	}

	/**
	 * Gets the component service.
	 * 
	 * @return the compoent service
	 */
	@JsonIgnore
	public SlingModelService getComponentService() {
		return defaultComponentService;
	}

	/**
	 * Gets the data service.
	 * 
	 * @return the data service
	 */
	@JsonIgnore
	public SlingModelService getDataService() {
		return defaultDataService;
	}

	/**
	 * Gets the component json.
	 * 
	 * @return the component json
	 */
	@JsonIgnore
	public String getComponentJson() {
		return componentJson;
	}

	/**
	 * Sets the compoent json.
	 * 
	 * @param componentJson the component JSON
	 */
	public void setComponentJson(String componentJson) {
		this.componentJson = componentJson;
	}

	/**
	 * Gets the resource resolver.
	 * 
	 * @return resource resolver
	 */
	@JsonIgnore
	public ResourceResolver getResolver() {
		return resource.getResourceResolver();
	}

	/**
	 * Gets the unique component ID.
	 * 
	 * @return the component Id
	 */
	public String getComponentId() {
		return componentId;
	}

	/**
	 * Sets the unique component ID.
	 * 
	 * @param componentId the component ID
	 */
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

}
