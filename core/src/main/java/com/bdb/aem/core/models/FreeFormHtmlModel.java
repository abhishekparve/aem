package com.bdb.aem.core.models;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;


/**
 * The Class FreeFormHtmlModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FreeFormHtmlModel {
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(FreeFormHtmlModel.class);
    
    /** The title. */
    @Inject
    @Via("resource")
    private String title;
    
    @Inject
    @Via("resource")
    private String description;
    
    @Inject
    Page currentPage;
    
    @Inject
    SlingHttpServletRequest request;
    
    /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;
    
    /** The current resource. */
	@Inject
	Resource currentResource;
    

    
    @PostConstruct
    protected void init() {
    	
        try{
        	ResourceResolver resourceResolver = request.getResourceResolver();
        	if(StringUtils.isNotEmpty(description)) {
        		String urlFormate = CommonHelper.getRegion(currentPage)+"/"+CommonHelper.getCountry(currentPage)+"/"+CommonHelper.getLanguage(currentPage)+"-"+CommonHelper.getCountry(currentPage);
        		description = CommonHelper.HandleRTEAnchorLink(description, externalizerService, resourceResolver,urlFormate);
    		}
        } catch (IOException e) {
            logger.error("LoginException {}", e.getMessage());
        	}
        }
    
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Gets the article id.
	 *
	 * @return the article id
	 */
	public String getArticleId() {
		return currentResource.getParent().getName() + "-" + currentResource.getName();
	}
	
}
