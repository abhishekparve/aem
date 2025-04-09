package com.bdb.aem.core.models;

import com.bdb.aem.core.bean.BeadlotsCategoryBean;
import com.bdb.aem.core.pojo.BeadlotsConfig;
import com.bdb.aem.core.pojo.Payload;
import com.bdb.aem.core.pojo.PayloadConfig;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.BeadlotsUtil;
import com.bdb.aem.core.util.ExcludeUtil;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * The BeadlotsMasterModel.
 */
@Model(
        adaptables = {SlingHttpServletRequest.class, Resource.class},
        adapters = {BeadlotsMasterModel.class},
        resourceType = {BeadlotsMasterModel.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BeadlotsMasterModel {

    Logger logger = LoggerFactory.getLogger(BeadlotsMasterModel.class);

    /** The Constant RESOURCE_TYPE. */
    protected static final String RESOURCE_TYPE = "bdb-aem/components/content/beadlotsmaster/v1/beadlotsmaster";

    /**
     * The title.
     */
    @Inject
    @Named("title")
    @Via("resource")
    @SerializedName("title")
    private String title;

    /**
     * The searchPlaceholder.
     */
    @Inject
    @Named("searchPlaceholder")
    @Via("resource")
    @SerializedName("searchPlaceHolder")
    private String searchPlaceholder;

    /**
     * The searchAllInfo.
     */
    @Inject
    @Named("searchAllInfo")
    @Via("resource")
    @SerializedName("searchInfoAll")
    private String searchAllInfo;

    /**
     * The searchAllInfoDynamic.
     */
    @Inject
    @Named("searchAllInfoDynamic")
    @Via("resource")
    @SerializedName("infoDynamic")
    private String searchAllInfoDynamic;

    /**
     * The categoryAll.
     */
    @Inject
    @Named("categoryAll")
    @Via("resource")
    @SerializedName("categoryAll")
    private String categoryAll;

    /**
     * The toolTipText.
     */
    @Inject
    @Named("toolTipText")
    @Via("resource")
    @SerializedName("toolTipText")
    private String toolTipText;

    /**
     * The toolTipImg.
     */
    @Inject
    @Named("toolTipImg")
    @Via("resource")
    @SerializedName("toolTipImg")
    private String toolTipImg;

    /**
     * The resultsText.
     */
    @Inject
    @Named("resultsText")
    @Via("resource")
    @SerializedName("resultsText")
    private String resultsText;

    /**
     * The noResults.
     */
    @Inject
    @Named("noResults")
    @Via("resource")
    @SerializedName("noResults")
    private String noResults;

    /**
     * The suggestedResults.
     */
    @Inject
    @Named("suggestedResults")
    @Via("resource")
    @SerializedName("suggestedResults")
    private String suggestedResults;

    /**
     * The noResultsText.
     */
    @Inject
    @Named("noResultsText")
    @Via("resource")
    @SerializedName("noResultsText")
    private String noResultsText;

    /**
     * The noResultsCta.
     */
    @Inject
    @Named("noResultsCta")
    @Via("resource")
    @SerializedName("noResultsCta")
    private String noResultsCta;

    /**
     * The noSearchResultsIcon.
     */
    @Inject
    @Named("noSearchResultsIcon")
    @Via("resource")
    @SerializedName("noSearchResultsIcon")
    private String noSearchResultsIcon;

    /**
     * The noResultsIcon.
     */
    @Inject
    @Named("noResultsIcon")
    @Via("resource")
    @SerializedName("noResultsIcon")
    private String noResultsIcon;

    /**
     * The altText.
     */
    @Inject
    @Named("altText")
    @Via("resource")
    @SerializedName("altText")
    private String altText;

    /**
     * The noSearchResults.
     */
    @Inject
    @Named("noSearchResults")
    @Via("resource")
    @SerializedName("noSearchResults")
    private String noSearchResults;
    
    @Inject
    @Named("showStatus")
    @Via("resource")
    @SerializedName("showStatus")
    private boolean showStatus;

    private String beadlotsJson;

    private String beadlotsConfig;

    private String beadlotsMasterJson;

    /**
     * The bdb api endpoint service.
     */
    @Inject
    private BDBApiEndpointService bdbApiEndpointService;

    /**
     * CurrentPage.
     */
    @Inject
    private Page currentPage;

    /**
     * The resource resolver.
     */
    @SlingObject
    private ResourceResolver resourceResolver;

    /**
     * The Constant BEADLOTS_RESOURCE_TYPE.
     */
    protected static final String BEADLOTS_RESOURCE_TYPE = "bdb-aem/proxy/components/content/beadlotscomponent";

    /**

    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
        ExcludeUtil excludeUtilObject = new ExcludeUtil();

        Gson beadLotMasterJson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                .addSerializationExclusionStrategy(excludeUtilObject).create();

        beadlotsMasterJson = beadLotMasterJson.toJson(this);


        List<BeadlotsCategoryBean> models = populateBeadlotsCategoryModels();
        beadlotsJson = setBeadlotsJson(models);
        beadlotsConfig = setBeadlotsConfig();

    }

    /**
     * Populate beadlot category models.
     */
    private List<BeadlotsCategoryBean> populateBeadlotsCategoryModels() {
        return BeadlotsUtil.getModelArray(currentPage.getPath(), resourceResolver, BEADLOTS_RESOURCE_TYPE);
    }

    /**
     * set the beadlotsJson.
     */
    private String setBeadlotsJson(List<BeadlotsCategoryBean> models) {

        BeadlotsConfig beadlotsConfigObj = new BeadlotsConfig(models);
        ExcludeUtil excludeUtilObject = new ExcludeUtil();
        Gson beadlotsGson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                .addSerializationExclusionStrategy(excludeUtilObject).create();
        String json = beadlotsGson.toJson(beadlotsConfigObj);
        if (StringUtils.isNotBlank(json)) {
            return json;
        }
        return StringUtils.EMPTY;
    }

    private String setBeadlotsConfig(){
        ExcludeUtil excludeUtilObject = new ExcludeUtil();

        String beadlotsDownloadEndpoint = bdbApiEndpointService.getBeadlotsFileDownloadServletPath();
        Payload beadlotsDownloadRequestPayload = new Payload(
                beadlotsDownloadEndpoint,
                HttpConstants.METHOD_POST);
        PayloadConfig beadlotsDownloadConfig = new PayloadConfig(beadlotsDownloadRequestPayload);


        Gson accountSettingConfigGson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                .addSerializationExclusionStrategy(excludeUtilObject).create();
        return accountSettingConfigGson.toJson(beadlotsDownloadConfig);

    }

    /**
     * get the beadlotsJson.
     */
    public String getBeadlotsJson() {
        return beadlotsJson;
    }

    /**
     * get the beadlotsConfig.
     */
    public String getBeadlotsConfig() {
        return beadlotsConfig;
    }

    /**
     * get the beadlotsMasterJson.
     */
    public String getBeadlotsMasterJson() {
        return beadlotsMasterJson;
    }
}


