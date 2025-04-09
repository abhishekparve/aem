package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.settings.SlingSettingsService;

import com.bdb.aem.core.services.BDBApiEndpointService;

/**
 * The Class NoIndexOnStageModel.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class NoIndexOnStageModel {

	@OSGiService 
	SlingSettingsService slingSettings;
	
	/** The bdb api endpoint service. */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;

	boolean stage;

	  @PostConstruct
	  protected void postConstruct()
	  {
		  String runmodes = bdbApiEndpointService.environmentType();
	   // Set<String> runmodes = slingSettings.getRunModes();
		  stage = runmodes.contains("stage");
	
	    return;
	  } 	
	  public boolean isStage(){ return stage; }

}