  
package com.bdb.aem.core.models;


import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonObject;


/**
 * The Class RequiredCompanionProductsModel.
 */
@Model(adaptables = {SlingHttpServletRequest.class,
        Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class RequiredCompanionProductsModel {

    /** The Constant REQUIRED. */
    protected static final String REQUIRED = "Required";

    /**
     * The logger.
     */
    Logger logger = LoggerFactory.getLogger(RequiredCompanionProductsModel.class);

    /**
     * The request.
     */
    @SlingObject
    private SlingHttpServletRequest request;

    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;
    
    /** The bdb api endpoint service. */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;


    /**
     * The externalizer service.
     */
    @Inject
    ExternalizerService externalizerService;
    
    @Inject
    @Named("heading")
    @Via("resource")
    private String heading;
    
    /** The description. */
    @Inject
    @Named("description")
    @Via("resource")
    private String description;
    
    /** The show more label. */
    @Inject
    @Named("showMoreLabel")
    @Via("resource")
    private String showMoreLabel;
    
    /** The show less label. */
    @Inject
    @Named("showLessLabel")
    @Via("resource")
    private String showLessLabel;
    
    /** The clone label. */
    @Inject
    @Named("cloneLabel")
    @Via("resource")
    private String cloneLabel;
    
    /** The size label. */
    @Inject
    @Named("sizeLabel")
    @Via("resource")
    private String sizeLabel;
    
    /** The status label. */
    @Inject
    @Named("statusLabel")
    @Via("resource")
    private String statusLabel;
    
   /**
     * The cat no label.
    */
    @Inject
    @Named("catNoLabel")
    @Via("resource")
    private String catNoLabel;

    /** The products label. */
    @Inject
    @Named("productImgAltText")
    @Via("resource")
    private String productImgAltText;
    
    /** The products label. */
    @Inject
    @Named("yourPrice")
    @Via("resource")
    private String yourPrice;
    
    /** The products label. */
    @Inject
    @Named("listPrice")
    @Via("resource")
    private String listPrice;
    
    /** The products label. */
    @Inject
    @Named("signIn")
    @Via("resource")
    private String signIn;
    
    /** The show count. */
    @Inject
	@Via("resource")
	@Default(values = "0")
    private int showCount;
    
    /** The addToCart. */
    @Inject
	@Via("resource")
	@Default(values = "addToCart")
    private String addToCart;
    
    /** The cartAltText. */
    @Inject
	@Via("resource")
	@Default(values = "cartAltText")
    private String cartAltText;
    
	/** The current page. */
	@Inject
    private Page currentPage;
	
	/** The hybris site id. */
	private String hybrisSiteId;
    
    /** The Solr search service. */
    @Inject
    SolrSearchService solrSearchService;
    
    private String requiredCompanionProductsLabels;
    private String requiredCompanionProductsConfig;

	/**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
        logger.debug("Inside RequiredCompanionProductsModel Init");

        hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
            requiredCompanionProductsLabels = setRequiredCompanionProductsLabelsJson();
            requiredCompanionProductsConfig = setRequiredCompanionProductsConfigJson();

    }

    private String setRequiredCompanionProductsConfigJson() {
		JsonObject getRequiredCompanionProducts = new JsonObject();
		JsonObject getPrice = new JsonObject();
		JsonObject configJson = new JsonObject();
		
		getRequiredCompanionProducts.addProperty(CommonConstants.URL,externalizerService.getFormattedUrl(bdbApiEndpointService.getRequiredCompanionProductsConfig(), resourceResolver)+"?catalogNo={{catalogNo}}&country={{country}}");
		getRequiredCompanionProducts.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_GET);
		if(StringUtils.isNoneEmpty(hybrisSiteId)) {
			getPrice.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getPriceConfig()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			getPrice.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);
		}
		
		configJson.add("getRequiredCompanionProducts", getRequiredCompanionProducts);
		configJson.add("getPrice", getPrice);
		
		return configJson.toString();
	}

	private String setRequiredCompanionProductsLabelsJson() {
		JsonObject obj = new JsonObject();
		JsonObject objProduct = new JsonObject();
		obj.addProperty("heading", heading);
		obj.addProperty("description", description);
		obj.addProperty("showMore", showMoreLabel);
		obj.addProperty("showLess", showLessLabel);
		objProduct.addProperty("clone", cloneLabel);
		objProduct.addProperty("size", sizeLabel);
		objProduct.addProperty("status", statusLabel);
		objProduct.addProperty("catNo", catNoLabel);
		objProduct.addProperty("productImgAltText", productImgAltText);
		objProduct.addProperty("yourPrice", yourPrice);
		objProduct.addProperty("listPrice", listPrice);
		objProduct.addProperty("signIn", signIn);
		obj.add("productLabels", objProduct);
		obj.addProperty("authorableCount", showCount);
		obj.addProperty("addToCart", addToCart);
		obj.addProperty("cartAltText", cartAltText);
		return obj.toString();
	}

	public String getRequiredCompanionProductsLabels() {
		return requiredCompanionProductsLabels;
	}
	
	public String getRequiredCompanionProductsConfig() {
		return requiredCompanionProductsConfig;
	}


}