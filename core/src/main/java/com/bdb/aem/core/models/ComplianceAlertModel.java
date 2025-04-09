package com.bdb.aem.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonObject;

import lombok.Getter;

/**
 * The Class ComplianceAlertModel.
 */
@Model(adaptables = {
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ComplianceAlertModel {


	/** The logger. */
	Logger logger = LoggerFactory.getLogger(ComplianceAlertModel.class);
	
	/** The current page. */
	@Inject
	Page currentPage;
	
	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;
	
	/** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

	/** The compliancealert add labels. */
	
	private String compliancealertLabelsString;
	private JsonObject compliancealertLabels = new JsonObject();
	/** The ComplianceModal alert Message */
	@Inject
	private String compTitle;
	

	/** The ComplianceModal description Message 1*/
	@Inject
	private String compMsg1;
	
	/** The ComplianceModal description Message 2*/
	@Inject
	private String compMsg2;
	
	/** The Yes Button */
	@Inject
	private String compYes;
	

	
	
	
	/** The NO Button */
	@Inject
	private String compNo;
	
	/** The NO Path */
	@Inject
	private String compLink;
	/**
	 * Init.
	 */
	@PostConstruct
	protected void init() {
		System.out.println("IN INIT method");
						logger.debug("Initialize method");
						compLink = externalizerService.getFormattedUrl(compLink, resourceResolver);
						compliancealertLabelsString = getLabelJson().toString();
						
						
	}
	

	/**
	 * Gets the label json.
	 *
	 * @return the label json
	 */
	public JsonObject getLabelJson(){
		
		compliancealertLabels.addProperty(CommonConstants.COMP_TITLE,compTitle);
		compliancealertLabels.addProperty(CommonConstants.COMP_MSG1,compMsg1);
		compliancealertLabels.addProperty(CommonConstants.COMP_MSG2,compMsg2);
		compliancealertLabels.addProperty(CommonConstants.COMP_YES,compYes);
		compliancealertLabels.addProperty(CommonConstants.COMP_NO,compNo);
		compliancealertLabels.addProperty(CommonConstants.COMP_LINK,compLink);
		return compliancealertLabels;
	}
public String getLabelString(){
		
	return compliancealertLabelsString;
	}
	
	
	public String getCompTitle() {
		return compTitle;
	}

	public String getCompMsg1() {
		return compMsg1;
	}
	public String getCompMsg2() {
		return compMsg2;
	}	
	

	public String getCompYes() {
		return compYes;
	}


/**	public String getYesPath() {
		return yesButtonpath;
	} */

	public String getCompNo() {
		return compNo;
	}

	public String getCompLink() {
		return compLink;
	}

	public Logger getLogger() {
		return logger;
	}
				

			

}