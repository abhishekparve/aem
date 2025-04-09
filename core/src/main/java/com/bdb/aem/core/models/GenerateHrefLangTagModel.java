package com.bdb.aem.core.models;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.CatalogStructureUpdateService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.List;

/**
 * GenerateHrefLangTagModel to add the href lang tag in the page source.
 * We need to check the page existing in the corresponding country or not
 * And also need to check if it is a pdp page and also the products exists are not
 * then add it to list and loop in the page level.
 *
 *  For ex: Adding below script in every page
 *
 * <link rel="alternate" href="http://localhost:4503/en-us/products/reagents/flow-cytometry-reagents/research-reagents/asfsd.747177" hreflang="en-us"/></link>
 *
 * <link rel="alternate" href="http://localhost:4503/en-in/products/reagents/flow-cytometry-reagents/research-reagents/asfsd.747177" hreflang="en-in"/></link>
 */
@Model(adaptables = {SlingHttpServletRequest.class,
        Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GenerateHrefLangTagModel {
    /**
     * The logger.
     */
    Logger logger = LoggerFactory.getLogger(GenerateHrefLangTagModel.class);

    /**
     * The current page.
     */
    @Inject
    Page currentPage;

    /**
     * The resolver factory.
     */
    @Inject
    ResourceResolverFactory resolverFactory;

    /**
     * The resource resolver.
     */
    ResourceResolver resourceResolver;

    /**
     * Href Tag List
     */
    private List<HrefTagModel> hrefTagList;

    /**
     * The externalizer service.
     */
    @Inject
    ExternalizerService externalizerService;

    /** The request. */
    @Inject
    SlingHttpServletRequest request;

    /**
     * The SolrSearchService
     */
    @Inject
    SolrSearchService solrSearchService;
    /**
     * The CatalogStructureUpdateService
     */
    @Inject
    CatalogStructureUpdateService catalogStructureUpdateService;

    /**
     * The BDBSearchEndpointService
     */
    @Inject
    BDBSearchEndpointService solrConfig;

    /**
     * Inits the GenerateHrefLangTagModel
     */
    @PostConstruct
    protected void init() {
        logger.debug("Start of GenerateHrefLangTagModel Model");
        try {
            resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory);
            String materialNumber = CommonHelper.getSelectorDetails(request);
            String getProductTitleDetails = CommonHelper.getProductTitleDetails(request);
            String currentPageSelected = currentPage.getPath();
            logger.debug("current page path {}", currentPageSelected);
            if (null != resourceResolver) { 
            	List<String> fullCountryList = solrSearchService.getAllCountries(resourceResolver);
            hrefTagList = new ArrayList<>();
            if (null != currentPageSelected && !currentPageSelected.contains("/language-masters/") && null != fullCountryList) {
                loopingThroughCountryList(fullCountryList, currentPageSelected, materialNumber, getProductTitleDetails);
            }
            logger.debug("hrefLangUrl {}", hrefTagList);
            }
           
        } catch (LoginException | RepositoryException e) {
            logger.error("LoginException {}", e.getMessage());
        }finally {
            CommonHelper.closeResourceResolver(resourceResolver);
        }
    }

    /**
     * Looping through the list of countries
     * @param fullCountryList
     * @param currentPageSelected
     * @param materialNumber
     * @param getProductTitleDetails
     * @throws RepositoryException
     */
    private void loopingThroughCountryList(List<String> fullCountryList, String currentPageSelected, String materialNumber, String getProductTitleDetails) throws RepositoryException {
        String currentPagePath;
        for (int i = 0; i < fullCountryList.size(); i++) {
            String[] modifiedUrl = currentPageSelected.split(CommonConstants.SINGLE_SLASH);
            String countryPrefix = null != fullCountryList.get(i) ? CommonHelper.getRegionAndLanFromCountryList(fullCountryList.get(i),resourceResolver, solrConfig) : StringUtils.EMPTY;
            if(modifiedUrl.length >=  7 && null != modifiedUrl[6] && !countryPrefix.isEmpty()){
                currentPageSelected = currentPageSelected.replaceAll(currentPageSelected.substring(0, currentPageSelected.indexOf(modifiedUrl[6])), CommonConstants.CONST_BDB_ROOT_PATH + CommonConstants.SINGLE_SLASH+countryPrefix+CommonConstants.SINGLE_SLASH);
                String hrefLangCode = null != currentPageSelected.split(CommonConstants.SINGLE_SLASH)[5] ? currentPageSelected.split(CommonConstants.SINGLE_SLASH)[5] : StringUtils.EMPTY;
                String hrefCountryCode = null != currentPageSelected.split(CommonConstants.SINGLE_SLASH)[4] ? currentPageSelected.split(CommonConstants.SINGLE_SLASH)[4] : StringUtils.EMPTY;
                Resource resource= resourceResolver.getResource(currentPageSelected);
                if(null != resource){
                    currentPagePath = externalizerService.getFormattedUrl(currentPageSelected, resourceResolver);
                    HrefTagModel hrefTagModel = new HrefTagModel();
                    // Checking if this is a PDP page
                    boolean pdpExists = false;
                    if( null != materialNumber){
                        pdpExists = CommonHelper.getProductAvailabilityInRegion(materialNumber, hrefCountryCode,
                                resourceResolver, catalogStructureUpdateService);
                        pdpPageTagAdd(pdpExists, currentPagePath, getProductTitleDetails,materialNumber,hrefTagModel,hrefLangCode);
                    }else{
                        hrefTagModel.setHrefLangUrl(currentPagePath);
                        hrefTagModel.setHrefLangCode(hrefLangCode);
                        hrefTagList.add(hrefTagModel);
                    }
                }
            }
        }
    }

    /**
     * Adding the href tag to hrefTagModel if this is a pdp page
     * @param pdpExists
     * @param currentPagePath
     * @param getProductTitleDetails
     * @param materialNumber
     * @param hrefTagModel
     * @param hrefLangCode
     */
    private void pdpPageTagAdd(boolean pdpExists, String currentPagePath, String getProductTitleDetails, String materialNumber, HrefTagModel hrefTagModel, String hrefLangCode) {
        if(pdpExists && currentPagePath.contains("/pdp")){
            currentPagePath = currentPagePath.replace("/pdp", CommonConstants.SINGLE_SLASH +getProductTitleDetails+"."+materialNumber);
            hrefTagModel.setHrefLangUrl(currentPagePath);
            hrefTagModel.setHrefLangCode(hrefLangCode);
            hrefTagList.add(hrefTagModel);
        }
    }

    /**
     * Get Href Tag List
     * @return
     */
    public List<HrefTagModel> getHrefTagList() {
        return new ArrayList<>(hrefTagList);
    }
}
