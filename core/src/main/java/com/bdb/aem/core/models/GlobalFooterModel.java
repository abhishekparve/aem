package com.bdb.aem.core.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;


/**
 * The Class GlobalFooterModel.
 */
@Model(
		adaptables = {SlingHttpServletRequest.class, Resource.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class GlobalFooterModel {
	
	/** The logger. */
    Logger logger = LoggerFactory.getLogger(GlobalFooterModel.class);
	
	/** The current page. */
    @ScriptVariable
    private Page currentPage;
	
    /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;

    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

	/** The social. */
	@Inject
	@Via("resource")
	public List<GlobalFooterSocialModel> social; // the multifield name="./social"

	/** The footer links. */
	@Inject
	@Via("resource")
	public List<GlobalFooterLinksModel> footerLinks; // the multifield name="./footerLinks"

	/** The categories. */
	@Inject
	@Via("resource")
	public List<GlobalFooterCategoryModel> categories; // the multifield name="./categories"
	
	/** The url logo. */
	@Inject
	@Via("resource")
	private String urlLogo;
	
	/** The load in modal. */
	@Inject
	@Via("resource")
	private String loadInModal;
	
	/** The sub cat name. */
	@Inject
	@Via("resource")
	private String subCatName;

	/** The url icon. */
	@Inject
	@Via("resource")
	private String urlIcon;
	
	/** The embeded form code. */
	@Inject
	@Via("resource")
	private String embededFormCode;

	/** The bdb api endpoint service. */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;

	/**
	 * Inits the.
	 */
    @PostConstruct
    protected void init() {
        try {
        	logger.debug("GlobalFooterModel Initialized");
        	String runMode = bdbApiEndpointService.getCustomRunMode();
        	if( CollectionUtils.isNotEmpty(categories) ) {
	        	for(GlobalFooterCategoryModel category : categories) {
	    			for(GlobalFooterSubcategoryModel subCategory : category.getSubcategories()) {
	    				if(!subCategory.getUrl().trim().isEmpty() && subCategory.getUrl().startsWith("/content/bdb")) {	    					
	    					subCategory.setUrl(CommonHelper.getShortUrl(externalizerService.getFormattedUrl(subCategory.getUrl(), resourceResolver), currentPage, resourceResolver,runMode));
	    				}
	    				 subCategory.setUrl(externalizerService.getFormattedUrl(subCategory.getUrl(), resourceResolver));
	    			}
	    		}
        	}
        	if( CollectionUtils.isNotEmpty(footerLinks) ) {
	            for(GlobalFooterLinksModel footerLink : footerLinks) {
	            	if(!footerLink.getLinkURL().trim().isEmpty() &&  footerLink.getLinkURL().startsWith("/content/bdb")) {
	            		 footerLink.setLinkURL(CommonHelper.getShortUrl(externalizerService.getFormattedUrl(footerLink.getLinkURL(), resourceResolver),currentPage, resourceResolver, runMode));
	            	}else
	            		 footerLink.setLinkURL(externalizerService.getFormattedUrl(footerLink.getLinkURL(), resourceResolver));
	            	  
	    		}
        	}
        	urlLogo = StringUtils.isNotEmpty(CommonHelper.getLabel("urlLogo", currentPage))?CommonHelper.getLabel("urlLogo", currentPage):"#";
            urlLogo = externalizerService.getFormattedUrl(urlLogo, resourceResolver);
            urlIcon = externalizerService.getFormattedUrl(urlIcon, resourceResolver);
            if(StringUtils.isNotEmpty(embededFormCode)) {
            	String urlFormate = CommonHelper.getRegion(currentPage)+"/"+CommonHelper.getCountry(currentPage)+"/"+CommonHelper.getLanguage(currentPage)+"-"+CommonHelper.getCountry(currentPage);
            	embededFormCode = CommonHelper.HandleRTEAnchorLink(embededFormCode, externalizerService, resourceResolver,urlFormate);
        	}
        } catch (IOException e) {
            logger.error("LoginException {}", e.getMessage());
        } 
    }

	/**
	 * Gets the social.
	 *
	 * @return the social
	 */
	public List<GlobalFooterSocialModel> getSocial() {
		if( CollectionUtils.isNotEmpty(social) ) {
			return new ArrayList<>(social);
		}
		else {
			return new ArrayList<>();
		}
	}

	/**
	 * Gets the footer links.
	 *
	 * @return the footer links
	 */
	public List<GlobalFooterLinksModel> getFooterLinks() {
		if( CollectionUtils.isNotEmpty(footerLinks) ) {
			return new ArrayList<>(footerLinks);
		}
		else {
			return new ArrayList<>();
		}
	}

	/**
	 * Gets the categories.
	 *
	 * @return the categories
	 */
	public List<GlobalFooterCategoryModel> getCategories() {
		if( CollectionUtils.isNotEmpty(categories) ) {
			return new ArrayList<>(categories);
		}
		else {
			return new ArrayList<>();
		}
	}
	
	/**
	 * Gets the url logo.
	 *
	 * @return the url logo
	 */
	public String getUrlLogo() {
		return urlLogo;
	}
	
	/**
	 * Gets the url icon.
	 *
	 * @return the url icon
	 */
	public String getUrlIcon() {
		return urlIcon;
	}
	
	/**
	 * Checks if is load in modal.
	 *
	 * @return true, if is load in modal
	 */
	public boolean isLoadInModal() {
		if(null != loadInModal) {
			return true;
		}else
			return false;
		
	}
	
	/**
	 * Gets the sub cat name.
	 *
	 * @return the sub cat name
	 */
	public String getSubCatName() {
		if(StringUtils.isNotEmpty(subCatName)) {
			return subCatName.trim();
		}else {
			return StringUtils.EMPTY;
		}	
	}
	
	/**
	 * Gets the embeded form code.
	 *
	 * @return the embeded form code
	 */
	public String getEmbededFormCode() {
		if(StringUtils.isNotEmpty(embededFormCode)) {
			return embededFormCode;
		}else {
			return StringUtils.EMPTY;
		}	
	}
	
	/**
	 * Gets the footer links size.
	 *
	 * @return the footer links size
	 */
	public int getFooterLinksSize( ) {
		if( CollectionUtils.isNotEmpty(footerLinks) ) {
			return footerLinks.size();
		}
		else {
			return 0;
		}
	}
}