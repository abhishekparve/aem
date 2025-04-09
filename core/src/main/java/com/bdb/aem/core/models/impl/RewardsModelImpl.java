package com.bdb.aem.core.models.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
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

import com.bdb.aem.core.models.RewardsModel;
import com.bdb.aem.core.pojo.Payload;
import com.bdb.aem.core.pojo.PayloadConfig;
import com.bdb.aem.core.pojo.RewardsConfig;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.ExcludeUtil;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * The Class RewardsModelImpl.
 */
@Model(
        adaptables = { SlingHttpServletRequest.class, Resource.class },
        adapters = {RewardsModel.class},
        resourceType = {RewardsModelImpl.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class RewardsModelImpl implements RewardsModel {
	
	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/rewards/v1/rewards";

    /** The Constant logger. */
    protected static final Logger logger = LoggerFactory.getLogger(RewardsModelImpl.class);

    /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;
    /**
     * The resource resolver.
     */
    @SlingObject
    private ResourceResolver resourceResolver;

    /** The title. */
    @Inject
    @Via("resource")
    private String title;

	/** The myRewardsTitle. */
	@Inject
    @Via("resource")
	private String myRewardsTitle;
    
    /** The industry description. */
    @Inject
    @Via("resource")
    private String rewardsImageHeader;
    
    /** The Rewards Points Text. */
    @Inject
    @Via("resource")
    private String rewardsPointsText;
    
    /** The Rewards Image Body. */
    @Inject
    @Via("resource")
    private String rewardsImageBody;
    
    /** The Rewards Image. */
    @Inject
    @Via("resource")
    private String rewardsImage;
    
    /** The Rewards Mobile Image. */
    @Inject
    @Via("resource")
    private String rewardsMobileImage;
    
    /** The Rewards Alt Text. */
    @Inject
    @Via("resource")
    private String rewardsAltText;
    
    /** The Rewards Sign Up Btn. */
    @Inject
    @Via("resource")
    private String rewardsSignUpBtn;
    
    /** The Rewards Benefit Title. */
    @Inject
    @Via("resource")
    private String rewardsBenefitTitle;
    
    /** The join Rewards Title. */
    @Inject
    @Via("resource")
    private String joinRewardsTitle;
    
    /** The subtitle. */
    @Inject
    @Via("resource")
    private String subtitle;
    
    /** The notHealthCareLabel. */
    @Inject
    @Via("resource")
    private String notHealthCareLabel;
    
    /** The not Govt Label. */
    @Inject
    @Via("resource")
    private String notGovtLabel;
    
    /** The no Gits Policy Label. */
    @Inject
    @Via("resource")
    private String noGitsPolicyLabel;
    
    /** The rewards TnC Label. */
    @Inject
    @Via("resource")
    private String rewardsTnCLabel;


	/** The rewards TnC Label. */
	@Inject
    @Via("resource")
	private String joinBdRewardsCTALabel;

    /** The redirection Url. */
    @Inject
    @Via("resource")
    private String redirectionUrl;

	/** The rewardsTabLabel. */
	@Inject
    @Via("resource")
	private String rewardsTabLabel;

	/** The earnTabLabels. */
	@Inject
    @Via("resource")
	private String earnTabLabels;

	/** The activityTabLabel. */
	@Inject
    @Via("resource")
	private String activityTabLabel;

	/** The benefits Tab Label. */
	@Inject
    @Via("resource")
	private String benefitsTabLabel;

	/** The benefits Tab Content. */
	@Inject
    @Via("resource")
	private String benefitsTabContent;

	/** The bd REwards TnC Label. */
	@Inject
    @Via("resource")
	private String bdREwardsTnCLabel;

	/** The bd REwards TnC Link. */
	@Inject
    @Via("resource")
	private String bdREwardsTnCLink;

    /** The Successful Rewards Text. */
    @Inject
    @Via("resource")
    private String successfulRewardsText;

	private String rewardsLabels;

	private String rewardsConfig;

    /** The hybris site id. */
    private String hybrisSiteId;


    /** The bdb api endpoint service. */
    @Inject
    private BDBApiEndpointService bdbApiEndpointService;


    /** The current page. */
    @Inject
    private Page currentPage;

    /**
     * Populates the Industry List.
     */
    @PostConstruct    
    protected void init() {
        hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
        if(null!=redirectionUrl) {
            redirectionUrl = externalizerService.getFormattedUrl(redirectionUrl,resourceResolver);
        }
        ExcludeUtil excludeUtilObject = new ExcludeUtil();
        rewardsLabels =  populateRewardsLabels();
        rewardsConfig =  populateRewardsConfig(excludeUtilObject);

    }

    private String populateRewardsLabels(){
    	
    	if(StringUtils.isNotEmpty(rewardsTnCLabel)) {
    		try {
				rewardsTnCLabel = CommonHelper.HandleRTEAnchorLink(rewardsTnCLabel, externalizerService, resourceResolver,StringUtils.EMPTY);
			} catch (IOException e) {
				logger.error("Exception occured RewardsModelImpl{}", e.getMessage());
			}
		}
    	else {
    		rewardsTnCLabel = StringUtils.EMPTY;
    	}

        JsonObject rewardsLabels = new JsonObject();
        rewardsLabels.addProperty(CommonConstants.TITLE_LABEL,title);
        rewardsLabels.addProperty("myRewardsTitle",myRewardsTitle);
        rewardsLabels.addProperty("rewardsImageHeader",rewardsImageHeader);
        rewardsLabels.addProperty("rewardsPointsText",rewardsPointsText);
        rewardsLabels.addProperty("rewardsImageBody",rewardsImageBody);
        rewardsLabels.addProperty("rewardsImage",rewardsImage);
        rewardsLabels.addProperty("rewardsMobileImage",rewardsMobileImage);
        rewardsLabels.addProperty("rewardsAltText",rewardsAltText);
        rewardsLabels.addProperty("rewardsSignUpBtn",rewardsSignUpBtn);
        rewardsLabels.addProperty("rewardsBenefitTitle",rewardsBenefitTitle);
        rewardsLabels.addProperty("successfulRewardsText",successfulRewardsText);

        JsonObject joinRewards = new JsonObject();
        joinRewards.addProperty(CommonConstants.TITLE_LABEL,joinRewardsTitle);
        joinRewards.addProperty("subtitle",subtitle);
        joinRewards.addProperty("notHealthCareLabel",notHealthCareLabel);
        joinRewards.addProperty("notGovtLabel",notGovtLabel);
        joinRewards.addProperty("noGiftsPolicyLabel",noGitsPolicyLabel);
        joinRewards.addProperty("rewardsTnCLabel",rewardsTnCLabel);
        joinRewards.addProperty("joinBdRewardsCTALabel",joinBdRewardsCTALabel);
        joinRewards.addProperty("redirectionUrl",redirectionUrl);

        rewardsLabels.add("joinRewards",joinRewards);

        JsonObject tabsContainer = new JsonObject();
        tabsContainer.addProperty("rewardsTabLabel",rewardsTabLabel);
        tabsContainer.addProperty("earnTabLabels",earnTabLabels);
        tabsContainer.addProperty("activityTabLabel",activityTabLabel);
        tabsContainer.addProperty("benefitsTabLabel",benefitsTabLabel);
        tabsContainer.addProperty("benefitsTabContent",benefitsTabContent);
        tabsContainer.addProperty("bdREwardsTnCLabel",bdREwardsTnCLabel);
        tabsContainer.addProperty("bdREwardsTnCLink", externalizerService.getFormattedUrl(bdREwardsTnCLink, resourceResolver));

        rewardsLabels.add("tabsContainer",tabsContainer);

        return rewardsLabels.toString();
    }

    private String populateRewardsConfig(ExcludeUtil excludeUtilObject ){
        //set the updateRewardsPreferences
        if(null!=bdbApiEndpointService) {
            String updateRewardsPreferencesEndpoint = StringUtils.replace(
                    bdbApiEndpointService.getUpdateRewardsPreferencesEndpoint(),
                    CommonConstants.HYBRIS_SITE_LITERAL,
                    hybrisSiteId);
            Payload updateRewardsPreferencesPayload = new Payload(
                    bdbApiEndpointService.getBDBHybrisDomain() + updateRewardsPreferencesEndpoint,
                    HttpConstants.METHOD_POST);
            PayloadConfig updateRewardsPreferences = new PayloadConfig(updateRewardsPreferencesPayload);

            String annexSiteId = bdbApiEndpointService.getAnnexSiteId();
            
            String annexJs = bdbApiEndpointService.getAnnexSiteJs();
            
            String summaryTabSrc = bdbApiEndpointService.getAnnexSiteDomain() +
                    bdbApiEndpointService.getSummaryTabSrcEndpoint();
            String rewardsTabSrc = bdbApiEndpointService.getAnnexSiteDomain() +
                    bdbApiEndpointService.getRewardsTabSrcEndpoint();
            String earnTabSrc = bdbApiEndpointService.getAnnexSiteDomain() +
                    bdbApiEndpointService.getEarnTabSrcEndpoint();
            String activityTabSrc = bdbApiEndpointService.getAnnexSiteDomain() +
                    bdbApiEndpointService.getActivityTabSrcEndpoint();


            RewardsConfig rewardsConfig = new RewardsConfig(updateRewardsPreferences,
                    annexSiteId,
                    summaryTabSrc,
                    rewardsTabSrc,
                    earnTabSrc,
                    activityTabSrc,
                    annexJs);

            Gson rewardsConfigGson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                    .addSerializationExclusionStrategy(excludeUtilObject).create();

            return rewardsConfigGson.toJson(rewardsConfig);
        }
        return StringUtils.EMPTY;

    }

	/**
	 * Gets rewards Labels as a String.
	 *
	 * @return the rewards Labels
	 */

	@Override
	public String getRewardsLabels() {
		return rewardsLabels;
	}

	/**
	 * Gets rewards Config as a String.
	 *
	 * @return the rewards Config
	 */
	@Override
	public String getRewardsConfig() {
		return rewardsConfig;
	}

}