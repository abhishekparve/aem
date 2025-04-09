package com.bdb.aem.core.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
 * The Class RuoModel.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class RuoModel {	
	
	/** The logger. */
	Logger logger = LoggerFactory.getLogger(RuoModel.class);

    /** The ruo fields. */
    @Inject
    @Named("ruoFields")
    private Resource ruoFields;
    
    /** The ruo legendtitle. */
    @Inject
	@Named("ruoLegendTitle")
	private String ruoLegendtitle;
    

	/** The ruo description. */
    @Inject
	@Named("ruoDescription")
	private String ruoDescription;
	
	/** The resource resolver. */
	@SlingObject
    ResourceResolver resourceResolver;
	
	/** The ruo map. */
	HashMap<String, String> ruoMap = new LinkedHashMap<>();
    
	/**
     * Inits the.
     */
    @PostConstruct
	protected void init() {
			if(null != ruoFields) {
	    		Iterator<Resource> ruoResource = ruoFields.listChildren();
	    		 
	            while(ruoResource.hasNext()) {
					String itemResPath = ruoResource.next().getPath();
					Resource ruoItemResource = resourceResolver.getResource(itemResPath);
					ValueMap valueMap = ruoItemResource.adaptTo(ValueMap.class);
					String label = valueMap.get("label",String.class);
					String description = valueMap.get("description",String.class);
					ruoMap.put(label, description);
	            }
	    	}
    }
    
    /**
     * Gets the ruo legendtitle.
     *
     * @return the ruo legendtitle
     */
    public String getRuoLegendtitle() {
		return ruoLegendtitle;
	}

	/**
	 * Gets the ruo description.
	 *
	 * @return the ruo description
	 */
	public String getRuoDescription() {
		return ruoDescription;
	}
    
    /**
     * Gets the ruo map.
     *
     * @return the ruo map
     */
    public Map<String, String> getRuoMap() {
		return ruoMap;
	}
}
