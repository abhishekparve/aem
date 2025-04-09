package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.google.gson.annotations.SerializedName;

/**
 * The Class ProtocolTextLinkModel.
 */
@Model(	adaptables = Resource.class,
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProtocolTextLinkModel {
	
	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(ProtocolTextLinkModel.class);
	
	/** The label. */
	@Inject
	@SerializedName("label")
    private String label;

    /** The path. */
    @Inject
    @Default(values = StringUtils.EMPTY)
    @SerializedName("downLoadLink")
    private String path;
    
    /** The use as asset. */
    @Inject
	private String useAsAsset;    
    
    /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;
    
    @SlingObject
    ResourceResolver resourceResolver;
    
	/**
	 * Initializes the model.
	 */
	@PostConstruct
    protected void init() {
    	log.debug("Inside ProtocolTextLink Model Init");
    		path = externalizerService.getFormattedUrl(path, resourceResolver);
	}

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Gets the use as asset.
	 *
	 * @return the use as asset
	 */
	public String getUseAsAsset() {
		return useAsAsset;
	}	
}
