package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.bean.CountryLocaleBean;
import com.bdb.aem.core.bean.GlobalNavigationInformationBean;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
	
/**
 * The Class GlobalHeaderNavigationModel.
 */
@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GlobalHeaderNavigationModel {
    
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(GlobalHeaderNavigationModel.class);
    
    /** The bdb api endpoint service. */
    @Inject
    BDBApiEndpointService bdbApiEndpointService;

    /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;
    
    /** The resolver factory. */
    @Inject
    ResourceResolverFactory resolverFactory;

    /** The current page. */
    @ScriptVariable
	private Page currentPage;
    
    /** The dropdown items. */
    private List<CountryLocaleBean> dropdownItems;
    
    /** The navigation items. */
    private List<GlobalNavigationInformationBean> navigationItems;
    
    /** The country name. */
    private String countryName;
    
    /** The language name. */
    private String languageName;
    
    /** The current country-language name. */
    private String currentCL;
    
    /** The compare current country-language name. */
    private String compareCurrentCL;
    
    /** The region value. */
    private String regionValue;
    
    /** The country value. */
    private String countryValue;
    
    /** The language value. */
    private String languageValue;
    
    /**
     * Inits the.
     */
    @PostConstruct
    protected void init(){
    	logger.debug("Initialize method for GlobalHeaderNavigationModel");
    	
        try (ResourceResolver resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory)){
    		
            regionValue = CommonHelper.getRegion(currentPage);
            countryValue = CommonHelper.getCountry(currentPage);
			languageValue = CommonHelper.getLanguage(currentPage);
    		logger.debug("Region - {}",regionValue);
        	logger.debug("Country - {}",countryValue);
        	logger.debug("Language - {}",languageValue);
        	
        	if (null != bdbApiEndpointService && null != resourceResolver) {
					Resource genericListResource1 = resourceResolver.getResource(bdbApiEndpointService.countryDropdownEndpoint());
					if(null != genericListResource1) {
						countryName = CommonHelper.getGenericListTitle(countryValue, genericListResource1, resourceResolver);
						logger.debug("Country Name - {}", countryName);
					}
					Resource genericListResource2 = resourceResolver.getResource(bdbApiEndpointService.languageDropdownEndpoint());
					if(null != genericListResource2) {
						languageName = CommonHelper.getGenericListTitle(languageValue, genericListResource2, resourceResolver);
						logger.debug("Language Name - {}", languageName);
					}
        	}
        	if(StringUtils.isEmpty(countryName))
        		countryName = CommonConstants.CONST_DEFAULT_COUNTRY_NAME;
        	if(StringUtils.isEmpty(languageName))
        		languageName = CommonConstants.CONST_DEFAULT_LANGUAGE_NAME;
        	
        	currentCL = getCountryLanguageCombo(countryName, languageName);
        	compareCurrentCL = currentCL.replace(" ", "").toLowerCase();

        	if(null != resourceResolver){
				populateDropdownItems(resourceResolver);
				populateNavigationItems(resourceResolver);
			}
        	
        } catch (LoginException e) {
            logger.error("LoginException {}", e.getMessage());
        } catch (RepositoryException e) {
        	 logger.error("NullPointerException {}", e.getMessage());
		}
    }
    
    
    
	/**
	 * Populate the dropdownItems.
	 *
	 * @param resourceResolver the resource resolver
	 */
	private void populateDropdownItems(ResourceResolver resourceResolver) {
    	StringBuilder path = new StringBuilder();
    	path.append(CommonConstants.CONST_BDB_ROOT_PATH);
    	path.append(CommonConstants.SINGLE_SLASH);
    	path.append(regionValue);
    	path.append(CommonConstants.SINGLE_SLASH);
    	path.append(countryValue); //CA
    	Resource root = resourceResolver.getResource(path.toString());
    	if(root!=null) {
    		Node rootNode = root.adaptTo(Node.class);
    		dropdownItems = constructDropdown(rootNode, new ArrayList<>(), resourceResolver);
    		logger.debug("DropdownItems returned.");
    	}
	}
	
    /**
     * Construct the dropdownItems.
     *
     * @param rootNode the root node
     * @param dropdownList the dropdown list
     * @param resourceResolver the resource resolver
     * @return the list
     */
    public List<CountryLocaleBean> constructDropdown(Node rootNode, List<CountryLocaleBean> dropdownList, ResourceResolver resourceResolver) {
    	
    	NodeIterator nodeIterator;
		try {
			nodeIterator = rootNode.getNodes();
    		while(nodeIterator.hasNext()) {
    			Node parent = nodeIterator.nextNode();
    			if(parent.getProperty(JcrConstants.JCR_PRIMARYTYPE).getString().equals(CommonConstants.CQ_PAGE)){
		    		Node jcrContent = parent.getNode(JcrConstants.JCR_CONTENT);
		    		
		    		CountryLocaleBean countryInfo = setDropdownBean(jcrContent, resourceResolver);
		    		
		    		dropdownList.add(countryInfo);
    			}
    		}
		} catch (RepositoryException e) {
            logger.error("RepositoryException {}", e.getMessage());
		}
    	return dropdownList;
    }
    
    /**
     * Sets the dropdownItems bean.
     *
     * @param jcrContent the jcr content
     * @param resourceResolver the resource resolver
     * @return the country locale bean
     */
    private CountryLocaleBean setDropdownBean(Node jcrContent, ResourceResolver resourceResolver) {
    	CountryLocaleBean countryInfo = new CountryLocaleBean();
    	String locale = StringUtils.EMPTY;
		String langValue = StringUtils.EMPTY;
		String langName = StringUtils.EMPTY;
		
    	try {
			if(jcrContent.hasProperty(CommonConstants.LANGUAGE)) {
				langValue = jcrContent.getProperty(CommonConstants.LANGUAGE).getString(); 
				logger.debug("langValue -",langValue);
				//EN
				locale = (langValue +CommonConstants.HYPHEN + countryValue).trim().toLowerCase();
				logger.debug("locale -",locale);
				//en-ca
				countryInfo.setLocaleName(locale);
				if (null!=resourceResolver) {
					Resource genericListResource = resourceResolver.getResource(bdbApiEndpointService.languageDropdownEndpoint());
					if(null != genericListResource) {
						langName = CommonHelper.getGenericListTitle(langValue, genericListResource, resourceResolver);
						logger.debug("langName-",langName);
						//English
					}
				}
				if(!StringUtils.isEmpty(langName))
					countryInfo.setCountryName(getCountryLanguageCombo(countryName, langName));
				
				return countryInfo;
			}
		} catch (RepositoryException e) {
			logger.error("RepositoryException {}", e.getMessage());
		} 
    	return null;
    }
	
    
    /**
     * Populate the navigationItems.
     *
     * @param resourceResolver the resource resolver
     * @throws RepositoryException the repository exception
     */
    private void populateNavigationItems(ResourceResolver resourceResolver) throws RepositoryException {
    	StringBuilder path = new StringBuilder();
    	path.append(CommonConstants.CONST_BDB_ROOT_PATH);
    	path.append(CommonConstants.SINGLE_SLASH);
    	path.append(regionValue);
    	path.append(CommonConstants.SINGLE_SLASH);
    	path.append(countryValue);
    	path.append(CommonConstants.SINGLE_SLASH);
    	path.append(languageValue);
    	path.append(CommonConstants.HYPHEN);
    	path.append(countryValue);
    	
    	logger.debug("Site structure path - {}",path);

    	Resource root = resourceResolver.getResource(path.toString());
    	if(root!=null) {
    		Node rootNode = root.adaptTo(Node.class);
    		navigationItems = constructModel(rootNode, 0, new ArrayList<GlobalNavigationInformationBean>(),resourceResolver);
		logger.debug("navigationItems returned");
    	}
	}
    
    /**
     * Construct the navigationItems model.
     *
     * @param node the node
     * @param index the index
     * @param bdbInfoList the bdb info list
     * @param resourceResolver the resource resolver
     * @return the list
     * @throws RepositoryException the repository exception
     */
    public List<GlobalNavigationInformationBean> constructModel(Node node, int index, List<GlobalNavigationInformationBean> bdbInfoList, ResourceResolver resourceResolver) throws RepositoryException{  	
    	if(index==CommonConstants.CONST_NAV_DEPTH) {
    		return bdbInfoList;
    	}
    	if(null !=node) {
	    	NodeIterator nodeIterator = node.getNodes();
			while(nodeIterator.hasNext()) {
				Node parent = nodeIterator.nextNode();
				if(parent.getProperty(JcrConstants.JCR_PRIMARYTYPE).getString().equals(CommonConstants.CQ_PAGE)){
					GlobalNavigationInformationBean bdbInfo = getBDBInformation(parent, resourceResolver);
					if(bdbInfo!=null) {
						if(parent.hasNode(JcrConstants.JCR_CONTENT)) {
	                        Node jcrContent = parent.getNode(JcrConstants.JCR_CONTENT);
	                           if(jcrContent.hasProperty("hideInNavChildren") && jcrContent.getProperty("hideInNavChildren").getString().equalsIgnoreCase("true")) {
	                        	   bdbInfoList.add(bdbInfo);
	                        } else {
	                            bdbInfo.setChildren(constructModel(parent, index+1, new ArrayList<>(), resourceResolver));
	                            bdbInfoList.add(bdbInfo);
	                        }
	                    }				
					}
				}				
			}
		}
		return bdbInfoList;	
    }
     
    /**
     * Gets the BDB information for navigationItems.
     *
     * @param node the node
     * @param resourceResolver the resource resolver
     * @return the BDB information
     * @throws RepositoryException the repository exception
     */
    private GlobalNavigationInformationBean getBDBInformation(Node node, ResourceResolver resourceResolver) throws RepositoryException {
    	GlobalNavigationInformationBean bdbInfo = new GlobalNavigationInformationBean();
    	if(node.hasNode(JcrConstants.JCR_CONTENT)) {
    		Node jcrContent = node.getNode(JcrConstants.JCR_CONTENT);
    		if(jcrContent.hasProperty(CommonConstants.CONST_HIDEINNAV)) {
    			return null;
    		}if(jcrContent.hasProperty("navTitle")) {
    			bdbInfo.setProductLabel(jcrContent.getProperty("navTitle").getString());
    		}else if(jcrContent.hasProperty("pageTitle")){
    			bdbInfo.setProductLabel(jcrContent.getProperty("pageTitle").getString());
    		}else if(jcrContent.hasProperty(JcrConstants.JCR_TITLE)) {
    			bdbInfo.setProductLabel(jcrContent.getProperty(JcrConstants.JCR_TITLE).getString());
    		}
    		if(jcrContent.hasProperty(CommonConstants.CONST_REDIRECT_LINK)) {
    			String redirectLink = jcrContent.getProperty(CommonConstants.CONST_REDIRECT_LINK).getString();
    			bdbInfo.setRedirectPath(externalizerService.getFormattedUrl(redirectLink, resourceResolver));
    		}
    		else {
    			bdbInfo.setRedirectPath(externalizerService.getFormattedUrl(node.getPath(), resourceResolver));
    		}
    		
    		
    		
    		setBDBInfoPromotionContent(node, bdbInfo, 1, resourceResolver);
    		return bdbInfo;
    	}
    	return null;
    }

	/**
	 * Sets the BDB info promotion content for navigationItems.
	 *
	 * @param node the node
	 * @param bdbInfo the bdb info
	 * @param index the index
	 * @param resourceResolver the resource resolver
	 * @throws RepositoryException the repository exception
	 */
	private void setBDBInfoPromotionContent(Node node, GlobalNavigationInformationBean bdbInfo, int index, ResourceResolver resourceResolver)
			throws RepositoryException {
		
			boolean getParentProperties = true;
		
			if(node.hasNode(JcrConstants.JCR_CONTENT) && index>=0) {
				Node jcrContent = node.getNode(JcrConstants.JCR_CONTENT);
				if(jcrContent.hasProperty(CommonConstants.CONST_IMAGE_DESC)) {
					bdbInfo.setModelImageDesc(jcrContent.getProperty(CommonConstants.CONST_IMAGE_DESC).getString());
					getParentProperties = false;
				}
	    			
				if(jcrContent.hasProperty(CommonConstants.CONST_IMAGE_LINK)) {
					String imageLink = jcrContent.getProperty(CommonConstants.CONST_IMAGE_LINK).getString();
					bdbInfo.setModelImageLink(externalizerService.getFormattedUrl(imageLink, resourceResolver));
				}
				if(jcrContent.hasProperty(CommonConstants.CONST_IMAGE_PATH)) {
					String imagePath = jcrContent.getProperty(CommonConstants.CONST_IMAGE_PATH).getString();
					bdbInfo.setModelImagePath(externalizerService.getFormattedUrl(imagePath, resourceResolver));
					getParentProperties = false;
				}
				if(jcrContent.hasProperty(CommonConstants.CONST_IMAGE_TITLE)) {
					bdbInfo.setModelImageTitle(jcrContent.getProperty(CommonConstants.CONST_IMAGE_TITLE).getString());
					getParentProperties = false;
				}
				if(jcrContent.hasProperty(CommonConstants.CONST_IMAGE_ALT))
					bdbInfo.setModelImageAltText(jcrContent.getProperty(CommonConstants.CONST_IMAGE_ALT).getString());
				if(jcrContent.hasProperty(CommonConstants.CONST_IMAGE_CTA_LABEL))
					bdbInfo.setModelImageCTALabel(jcrContent.getProperty(CommonConstants.CONST_IMAGE_CTA_LABEL).getString());
				if(getParentProperties)
					setBDBInfoPromotionContent(node.getParent(),bdbInfo, index-1, resourceResolver);
		}
	}
	
	
	
	/**
	 * Gets the dropdownItems.
	 *
	 * @return the dropdown items
	 * @throws LoginException the login exception
	 */
    public List<CountryLocaleBean> getDropdownItems() throws LoginException {
    	if(null!=dropdownItems)
    		return new ArrayList<>(dropdownItems);
    	else
    		return new ArrayList<>() ;
	}
    
	/**
	 * Gets the navigationItems.
	 *
	 * @return the navigation items
	 * @throws RepositoryException the repository exception
	 * @throws LoginException the login exception
	 */
    public List<GlobalNavigationInformationBean> getNavigationItems() throws RepositoryException, LoginException {
    	if(null!=navigationItems)
    		return new ArrayList<>(navigationItems);
    	else
    		return new ArrayList<>();
    }

	/**
	 * Gets the current page.
	 *
	 * @return the current page
	 */
	public Page getCurrentPage() {
		return currentPage;
	}

	/**
	 * Gets the country name.
	 *
	 * @return the country name
	 */
	public String getCountryName() {
		return countryName;
	}
	
	/**
	 * Gets the language name.
	 *
	 * @return the language name
	 */
	public String getLanguageName() {
		return languageName;
	}
	
	/**
	 * Gets the current country-language name.
	 *
	 * @return the current country-language name
	 */
	public String getCurrentCL() {
		return currentCL;
	}

	/**
	 * Gets the compare current CL.
	 *
	 * @return the compare current CL
	 */
	public String getCompareCurrentCL() {
		return compareCurrentCL;
	}

	/**
	 * Gets the region value.
	 *
	 * @return the region value
	 */
	public String getRegionValue() {
		return regionValue;
	}

	/**
	 * Gets the country value.
	 *
	 * @return the country value
	 */
	public String getCountryValue() {
		return countryValue;
	}

	/**
	 * Gets the language value.
	 *
	 * @return the language value
	 */
	public String getLanguageValue() {
		return languageValue;
	}
	
	/**
	 * Gets the country language combo.
	 *
	 * @param countryName the country name
	 * @param languageName the language name
	 * @return the country language combo
	 */
	public String getCountryLanguageCombo(String countryName, String languageName) {
		return (countryName +" (" + languageName + ")").trim();
	}
	
}