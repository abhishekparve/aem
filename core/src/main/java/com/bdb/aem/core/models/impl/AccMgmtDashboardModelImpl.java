package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.models.AccMgmtDashboardModel;
import com.bdb.aem.core.models.ApiErrorMessagesModel;
import com.bdb.aem.core.models.MasterDataMessagesModel;
import com.bdb.aem.core.pojo.DashboardConfig;
import com.bdb.aem.core.pojo.Payload;
import com.bdb.aem.core.pojo.PayloadConfig;
import com.bdb.aem.core.pojo.PurchaseAccountConfig;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.ExcludeUtil;
import com.bdb.aem.core.util.UrlHandler;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
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

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Implementation of interface AccMgmtDashboardModel serving as Sling model
 * class for Dashboard dialog in accountmanagement component.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class}, adapters = {
        AccMgmtDashboardModel.class}, resourceType = {
        AccMgmtDashboardModelImpl.RESOURCE_TYPE}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class AccMgmtDashboardModelImpl implements AccMgmtDashboardModel {

    /**
     * The Constant log.
     */
    protected static final Logger loggerAccMgmtDashboard = LoggerFactory.getLogger(AccMgmtDashboardModelImpl.class);

    /**
     * The Constant RESOURCE_TYPE.
     */
    protected static final String RESOURCE_TYPE = "bdb-aem/components/content/accountmanagement/v1/accountmanagement";

    /** The Constant MASTER_DATA_RESOURCE_TYPE. */
    protected static final String MASTER_DATA_RESOURCE_TYPE = "bdb-aem/proxy/components/content/masterdata";

    /** The Constant MESAGE_CENTER. */
    public static final String MESAGE_CENTER = "messageCenter";

    /** The Constant RECENT_ORDERS. */
    public static final String RECENT_ORDERS = "recentOrders";

    /** The Constant RECENT_QUOTES. */
    public static final String RECENT_QUOTES = "recentQuotes";

    /** Dashboard Labels variable holding all the fields. */
    private String dashboardLabels;


    /** Dashboard Configs variable holding all the configs. */
    private String dashboardConfig;

    /** The purchase account config. */
    private String purchaseAccountConfig;

    /** The Hybris Site ID. */
    private String hybrisSiteId;

    /**
     * The bdb api endpoint service.
     */
    @Inject
    private BDBApiEndpointService bdbApiEndpointService;


    /**
     * The current page.
     */
    @Inject
    private Page currentPage;

    /**
     * The resource resolver.
     */
    @SlingObject
    private ResourceResolver resourceResolver;


    /**
     * The Dashboard Header.
     */
    @Inject
    @Via("resource")
    @SerializedName("dashboardHeaderLabel")
    private String dashboardHeaderLabel;

    /**
     * The Welcome Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("welcomeText")
    private String welcomeText;

    /**
     * The Account Summary.
     */
    @Inject
    @Via("resource")
    @SerializedName("accountSummaryText")
    private String accountSummaryText;

    /**
     * The Enable Buy Online.
     */
    @Inject
    @Via("resource")
    @SerializedName("enableBuyOnlineText")
    private String enableBuyOnlineText;

    /**
     * The Enable Buy Online Url.
     */
    @Inject
    @Via("resource")
    @SerializedName("enableBuyOnlineIcon")
    private String enableBuyOnlineIcon;

    /**
     * The Enable Buy Online.
     */
    @Inject
    @Via("resource")
    @SerializedName("enableBuyOnlineAltText")
    private String enableBuyOnlineAltText;

    /**
     * The Account Under Review.
     */
    @Inject
    @Via("resource")
    @SerializedName("accountUnderReviewText")
    private String accountUnderReviewText;


    /**
     * The Account Under Review.
     */
    @Inject
    @Via("resource")
    @SerializedName("accountUnderReviewIcon")
    private String accountUnderReviewIcon;

    /**
     * The Account Under Review.
     */
    @Inject
    @Via("resource")
    @SerializedName("accountUnderReviewAltText")
    private String accountUnderReviewAltText;

    /**
     * The Buying Online Enabled.
     */
    @Inject
    @Via("resource")
    @SerializedName("buyingOnlineEnabledText")
    private String buyingOnlineEnabledText;

    /**
     * The Buying Online Enabled.
     */
    @Inject
    @Via("resource")
    @SerializedName("buyingOnlineEnabledIcon")
    private String buyingOnlineEnabledIcon;


    /**
     * The Buying Online Enabled.
     */
    @Inject
    @Via("resource")
    @SerializedName("buyingOnlineEnabledAltText")
    private String buyingOnlineEnabledAltText;

    /**
     * The Change Role Preferences Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("changeRolePreferencesText")
    private String changeRolePreferencesText;

    /**
     * The Change Role Preferences icon.
     */
    @Inject
    @Via("resource")
    @SerializedName("updateRoleIcon")
    private String updateRoleIcon;

    /**
     * The Change Role Preferences alt text.
     */
    @Inject
    @Via("resource")
    @SerializedName("updateRoleAltText")
    private String updateRoleAltText;

    /**
     * The Update Password Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("updatePasswordText")
    private String updatePasswordText;

    /**
     * The Update Password Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("updatePasswordIcon")
    private String updatePasswordIcon;

    /**
     * The Update Password Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("updatePasswordAltText")
    private String updatePasswordAltText;

    /**
     * The Purchase Account Success Message.
     */
    @Inject
    @Via("resource")
    @SerializedName("purchaseAccountSuccessMessage")
    private String purchaseAccountSuccessMessage;

    /**
     * bannerImage.
     */
    @Inject
    @Via("resource")
    @SerializedName("bannerImage")
    private String bannerImage;
    
    /**
     * bannerImageAltText.
     */
    @Inject
    @Via("resource")
    @SerializedName("bannerImageAltText")
    private String bannerImageAltText;

    /**
     * The Messages Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("bannerTitle")
    private String bannerTitle;

    /**
     * The Messages Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("bannerText")
    private String bannerText;

    /**
     * The Messages Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("bannerCtaLabel")
    private String bannerCtaLabel;

    /**
     * The Messages Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("bannerCtaLink")
    private String bannerCtaLink;

    /**
     * The Messages Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("messagesText")
    private String messagesText;

    /**
     * The Recent Orders Title.
     */
    @Inject
    @Via("resource")
    @SerializedName("recentOrdersTitle")
    private String recentOrdersTitle;

    /**
     * The Recent Quotes Title.
     */
    @Inject
    @Via("resource")
    @SerializedName("recentQuotesTitle")
    private String recentQuotesTitle;


    /**
     * The Recent Orders Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("recentOrdersText")
    private String recentOrdersText;

    /**
     * The No Orders Title.
     */
    @Inject
    @Via("resource")
    @SerializedName("noOrdersTitle")
    private String noOrdersTitle;

    /**
     * The Mark all as read label.
     */
    @Inject
    @Via("resource")
    @SerializedName("markAllAsReadLabel")
    private String markAllAsReadLabel;

    /**
     * The No orders placed text.
     */
    @Inject
    @Via("resource")
    @SerializedName("noOrdersPlacedText")
    private String noOrdersPlacedText;

    /**
     * Populates the dashboardPageNavigationLabels.
     */
    @PostConstruct
    protected void init() {
        loggerAccMgmtDashboard.debug("Inside Account Management Dashboard Init Method");
        ExcludeUtil excludeUtilObject = new ExcludeUtil();
        Gson dashboardConfigGson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                .addSerializationExclusionStrategy(excludeUtilObject).create(); //Set the Hybris Site Id
        hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);

        dashboardLabels = setDashboardLabels();
        dashboardConfig = setDashboardConfig(dashboardConfigGson);

        setPurchaseAccountConfig(excludeUtilObject);
    }

    /**
     * Sets the dashboard labels.
     *
     * @return the string
     */
    private String setDashboardLabels() {
        JsonObject dashboardLabelsJson = new JsonObject();
        dashboardLabelsJson.addProperty(CommonConstants.TITLE_LABEL, dashboardHeaderLabel);

        JsonObject accountSummary = new JsonObject();
        accountSummary.addProperty(CommonConstants.TITLE_LABEL, accountSummaryText);
        accountSummary.addProperty(CommonConstants.WELCOME_LABEL, welcomeText);
        accountSummary.addProperty(CommonConstants.ENABLE_BUY_ONLINE, enableBuyOnlineText);
        accountSummary.addProperty(CommonConstants.ENABLE_BUY_ONLINE_ICON, enableBuyOnlineIcon);
        accountSummary.addProperty(CommonConstants.ENABLE_BUY_ONLINE_ALT, enableBuyOnlineAltText);
        accountSummary.addProperty(CommonConstants.ACCOUNT_UNDER_REVIEW, accountUnderReviewText);
        accountSummary.addProperty(CommonConstants.ACCOUNT_UNDER_REVIEW_ICON, accountUnderReviewIcon);
        accountSummary.addProperty(CommonConstants.ACCOUNT_UNDER_REVIEW_ALT, accountUnderReviewAltText);
        accountSummary.addProperty(CommonConstants.BUY_ONLINE_ENABLED, buyingOnlineEnabledText);
        accountSummary.addProperty(CommonConstants.BUY_ONLINE_ENABLED_ICON, buyingOnlineEnabledIcon);
        accountSummary.addProperty(CommonConstants.BUY_ONLINE_ENABLED_ALT, buyingOnlineEnabledAltText);
        accountSummary.addProperty(CommonConstants.UPDATE_ROLE, changeRolePreferencesText);
        accountSummary.addProperty(CommonConstants.UPDATE_ROLE_ICON, updateRoleIcon);
        accountSummary.addProperty(CommonConstants.UPDATE_ROLE_ICON_ALT, updateRoleAltText);
        accountSummary.addProperty(CommonConstants.UPDATE_PASS, updatePasswordText);
        accountSummary.addProperty(CommonConstants.UPDATE_PASS_ICON, updatePasswordIcon);
        accountSummary.addProperty(CommonConstants.UPDATE_PASS_ALT, updatePasswordAltText);
        accountSummary.addProperty(CommonConstants.BASIC_CONFIRMATION_LABELS, CommonConstants.BASIC_CONFIRMATION_LABELS);
        accountSummary.addProperty(CommonConstants.PURCHASE_ACCOUNT_LABELS, CommonConstants.PURCHASE_ACCOUNT_LABELS);
        accountSummary.addProperty(CommonConstants.PURCHASE_ACCOUNT_CONFIG, CommonConstants.PURCHASE_ACCOUNT_CONFIG);
        accountSummary.addProperty(CommonConstants.PURCHASE_ACCOUNT_SUCCESS_MESSAGE, purchaseAccountSuccessMessage);

        JsonObject banner = new JsonObject();
        banner.addProperty(CommonConstants.IMAGE ,bannerImage);
        banner.addProperty(CommonConstants.ALT, bannerImageAltText);
        banner.addProperty(CommonConstants.TITLE_LABEL, bannerTitle);
        banner.addProperty(CommonConstants.TEXT, bannerText);
        banner.addProperty(CommonConstants.CTA_LABEL, bannerCtaLabel);
        banner.addProperty(CommonConstants.CTA_LINK, UrlHandler.getModifiedUrl(bannerCtaLink, resourceResolver));

        accountSummary.add(CommonConstants.BANNER, banner);

        dashboardLabelsJson.add(CommonConstants.ACCOUNT_SUMMARY, accountSummary);
        dashboardLabelsJson.addProperty(CommonConstants.TEXT_IMAGE_BANNER, CommonConstants.TEXT_IMAGE_BANNER);

        JsonObject messageCenter = new JsonObject();
        messageCenter.addProperty(CommonConstants.TITLE_LABEL,messagesText);
        messageCenter.addProperty(CommonConstants.MARK_ALL_READ,CommonHelper.getLabel(CommonConstants.MARK_ALL_READ,currentPage));
        messageCenter.addProperty(CommonConstants.VIEW_ALL_LABEL,CommonHelper.getLabel(CommonConstants.VIEW_ALL_LABEL,currentPage));
        messageCenter.addProperty(CommonConstants.VIEW_LESS_LABEL,CommonHelper.getLabel(CommonConstants.VIEW_LESS_LABEL,currentPage));
        messageCenter.addProperty(CommonConstants.UNREAD_EMAIL_CAPS,CommonHelper.getLabel(CommonConstants.UNREAD_EMAIL,currentPage));
        messageCenter.addProperty(CommonConstants.UNREAD_MESSAGE_CAPS,CommonHelper.getLabel(CommonConstants.UNREAD_MESSAGE,currentPage));
        messageCenter.addProperty(CommonConstants.READ_MESSAGE_CAPS,CommonHelper.getLabel(CommonConstants.READ_MESSAGE,currentPage));
        setDashboardMessagesLabels(messageCenter);

        dashboardLabelsJson.add(MESAGE_CENTER,messageCenter);

        JsonObject recentOrders = new JsonObject();
        recentOrders.addProperty(CommonConstants.TITLE_LABEL,recentOrdersTitle);
        recentOrders.addProperty(CommonConstants.NO_ORDERS_TITLE,noOrdersTitle);
        recentOrders.addProperty(CommonConstants.NO_ORDERS_INFO,recentOrdersText);
        recentOrders.addProperty(CommonConstants.DATE_LABEL,CommonHelper.getLabel(CommonConstants.DATE_LABEL,currentPage));
        recentOrders.addProperty(CommonConstants.ORDER_NUMBER_LABEL,CommonHelper.getLabel(CommonConstants.ORDER_NUMBER_LABEL,currentPage));
        recentOrders.addProperty(CommonConstants.PO_NUMBER,CommonHelper.getLabel(CommonConstants.PO_NUMBER_LABEL,currentPage));
        recentOrders.addProperty(CommonConstants.ORDER_TOTAL_LABEL,CommonHelper.getLabel(CommonConstants.ORDER_TOTAL_LABEL,currentPage));
        recentOrders.addProperty(CommonConstants.STATUS_LABEL,CommonHelper.getLabel(CommonConstants.STATUS_LABEL,currentPage));
        recentOrders.addProperty(CommonConstants.VIEW_ALL_LABEL,CommonHelper.getLabel(CommonConstants.VIEW_ALL_LABEL,currentPage));
        recentOrders.addProperty(CommonConstants.VIEW_LESS_LABEL,CommonHelper.getLabel(CommonConstants.VIEW_LESS_LABEL,currentPage));

        JsonObject recentQuotes = new JsonObject();
        recentQuotes.addProperty(CommonConstants.TITLE_LABEL,recentQuotesTitle);
        recentQuotes.addProperty(CommonConstants.QUOTE_NUMBER,CommonHelper.getLabel(CommonConstants.QUOTE_NUMBER,currentPage));
        recentQuotes.addProperty(CommonConstants.DATE_CREATED,CommonHelper.getLabel(CommonConstants.DATE_CREATED,currentPage));
        recentQuotes.addProperty(CommonConstants.DATE_MODIFIED,CommonHelper.getLabel(CommonConstants.DATE_MODIFIED,currentPage));
        recentQuotes.addProperty(CommonConstants.STATUS_LABEL,CommonHelper.getLabel(CommonConstants.STATUS_LABEL,currentPage));
        recentQuotes.addProperty(CommonConstants.VIEW_ALL_LABEL,CommonHelper.getLabel(CommonConstants.VIEW_ALL_LABEL,currentPage));
        recentQuotes.addProperty(CommonConstants.VIEW_LESS_LABEL,CommonHelper.getLabel(CommonConstants.VIEW_LESS_LABEL,currentPage));
        recentQuotes.addProperty(CommonConstants.NO_QUOTE_TITLE,CommonHelper.getLabel(CommonConstants.NO_QUOTE_TITLE,currentPage));
        recentQuotes.addProperty(CommonConstants.NO_QUOTE_INFO,CommonHelper.getLabel(CommonConstants.NO_QUOTE_INFO,currentPage));

        dashboardLabelsJson.add(RECENT_QUOTES,recentQuotes);
        dashboardLabelsJson.add(RECENT_ORDERS,recentOrders);
        return dashboardLabelsJson.toString();
    }

    /**
     * Sets the dashboard config.
     *
     * @param dashboardConfigGson the dashboard config gson
     * @return the string
     */
    private String setDashboardConfig(Gson dashboardConfigGson) {

        if (null != bdbApiEndpointService) {
            //set the getMessages
            String getMessagesEndpoint = StringUtils.replace(
                    bdbApiEndpointService.getGetMessagesEndpoint(),
                    CommonConstants.HYBRIS_SITE_LITERAL,
                    hybrisSiteId);
            Payload getMessagesPayload = new Payload(
                    bdbApiEndpointService.getBDBHybrisDomain() + getMessagesEndpoint,
                    HttpConstants.METHOD_GET);
            PayloadConfig getMessages = new PayloadConfig(getMessagesPayload);

            //set the readMessage
            String readMessageEndpoint = StringUtils.replace(
                    bdbApiEndpointService.getReadMessageEndpoint(),
                    CommonConstants.HYBRIS_SITE_LITERAL,
                    hybrisSiteId);
            Payload readMessagePayload = new Payload(
                    bdbApiEndpointService.getBDBHybrisDomain() + readMessageEndpoint,
                    HttpConstants.METHOD_POST);
            PayloadConfig readMessage = new PayloadConfig(readMessagePayload);

            //set the getOrders
            String getOrdersEndpoint = StringUtils.replace(
                    bdbApiEndpointService.getGetOrdersEndpoint(),
                    CommonConstants.HYBRIS_SITE_LITERAL,
                    hybrisSiteId);
            Payload getOrdersPayload = new Payload(
                    bdbApiEndpointService.getBDBHybrisDomain() + getOrdersEndpoint,
                    HttpConstants.METHOD_POST);
            PayloadConfig getOrders = new PayloadConfig(getOrdersPayload);

            //set the getQuotes
            String getQuotesEndpoint = StringUtils.replace(
                    bdbApiEndpointService.getGetQuotesEndpoint(),
                    CommonConstants.HYBRIS_SITE_LITERAL,
                    hybrisSiteId);
            Payload getQuotesPayload = new Payload(
                    bdbApiEndpointService.getBDBHybrisDomain() + getQuotesEndpoint,
                    HttpConstants.METHOD_GET);
            PayloadConfig getQuotes = new PayloadConfig(getQuotesPayload);

            DashboardConfig dashboardConfigJson = new DashboardConfig(getMessages,
                    readMessage,
                    getOrders,
                    getQuotes);

            return dashboardConfigGson.toJson(dashboardConfigJson);
        }
        return StringUtils.EMPTY;
    }

    /**
     * Sets the purchase account config.
     *
     * @param excludeUtilObject the new purchase account config
     */
    private void setPurchaseAccountConfig(ExcludeUtil excludeUtilObject) {

        JsonElement purchaseAccountSubmit;
        JsonElement uploadTaxCertificateConfig;
        JsonElement statesConfig;
        JsonElement countriesConfig;
        JsonElement getDistributersOptions;
        JsonElement postSmartCartRegister;

        String hybrisPurchaseAccountEndpoint = StringUtils.replace(
                bdbApiEndpointService.getPurchasingAccountRegistrationEndpoint(),
                CommonConstants.HYBRIS_SITE_LITERAL,
                hybrisSiteId);
        Payload purchaseAccountPayload = new Payload(
                bdbApiEndpointService.getBDBHybrisDomain() + hybrisPurchaseAccountEndpoint,
                HttpConstants.METHOD_POST);

        String hybrisUploadTaxCertificateEndpoint = StringUtils.replace(
                bdbApiEndpointService.getUploadTaxCertificateEndpoint(),
                CommonConstants.HYBRIS_SITE_LITERAL,
                hybrisSiteId);
        Payload uploadTaxCertificatePayload = new Payload(
                bdbApiEndpointService.getBDBHybrisDomain() + hybrisUploadTaxCertificateEndpoint,
                HttpConstants.METHOD_POST);


        Payload getStatesPayload = new Payload(bdbApiEndpointService.getStatesFromCountryServletPath(),
                HttpConstants.METHOD_POST);

        Payload getCountriesPayload = new Payload(bdbApiEndpointService.getCountriesFromRegionServletPath(),
                HttpConstants.METHOD_GET);

        Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                .addSerializationExclusionStrategy(excludeUtilObject).create();

        PayloadConfig statesPayloadConfig = new PayloadConfig(getStatesPayload);
        statesConfig = json.toJsonTree(statesPayloadConfig);

        PayloadConfig countriesPayloadConfig = new PayloadConfig(getCountriesPayload);
        countriesConfig = json.toJsonTree(countriesPayloadConfig);

        PayloadConfig purchaseAccountPayloadConfig = new PayloadConfig(purchaseAccountPayload);
        purchaseAccountSubmit = json.toJsonTree(purchaseAccountPayloadConfig);

        PayloadConfig uploadTaxCertificatePayloadConfig = new PayloadConfig(uploadTaxCertificatePayload);
        uploadTaxCertificateConfig = json.toJsonTree(uploadTaxCertificatePayloadConfig);

        String getDistributersOptionsEndpoint = CommonHelper.getEndPointUrl(
                bdbApiEndpointService,
                bdbApiEndpointService.getGetDistributorsOptionsEndpoint(),
                false,
                currentPage);
        Payload getDistributersOptionsPayload = new Payload(getDistributersOptionsEndpoint, HttpConstants.METHOD_GET);
        PayloadConfig getDistributersOptionsPayloadConfig = new PayloadConfig(getDistributersOptionsPayload);
        getDistributersOptions = json.toJsonTree(getDistributersOptionsPayloadConfig);


        String postSmartCartRegisterEndpoint = CommonHelper.getEndPointUrl(
                bdbApiEndpointService,
                bdbApiEndpointService.getPostSmartCartRegisterEndpoint(),
                false,
                currentPage);
        Payload postSmartCartRegisterPayload = new Payload(postSmartCartRegisterEndpoint, HttpConstants.METHOD_POST);
        PayloadConfig postSmartCartRegisterPayloadConfig = new PayloadConfig(postSmartCartRegisterPayload);
        postSmartCartRegister = json.toJsonTree(postSmartCartRegisterPayloadConfig);

        PurchaseAccountConfig purchaseAccountConfiguration = new PurchaseAccountConfig(purchaseAccountSubmit,
                uploadTaxCertificateConfig, statesConfig, countriesConfig,getDistributersOptions,postSmartCartRegister);

        purchaseAccountConfig = json.toJson(purchaseAccountConfiguration);
    }

    /**
     * Sets the dashboard messages labels.
     *
     * @param message the new dashboard messages labels
     */
    private void setDashboardMessagesLabels(JsonObject message) {
    	String masterDataPagePath = CommonHelper.getMasterDataPagePath(currentPage);
        MasterDataMessagesModel messagesModel = CommonHelper.getMasterDataMessageModel(
        		masterDataPagePath, resourceResolver, MASTER_DATA_RESOURCE_TYPE);
        JsonObject messagesJson = message;
        if (null != messagesModel) {
            for (ApiErrorMessagesModel errorMessagesModel : messagesModel.getUserDashboardMessages()) {
            	if (errorMessagesModel.getErrorCode() != null && errorMessagesModel.getErrorMessage()!= null) {
            		messagesJson.addProperty(errorMessagesModel.getErrorCode(),errorMessagesModel.getErrorMessage());
            	}
            }
        }
	}

    /**
     * This method returns the labels related to dashboard menu in account
     * management page.
     *
     * @return dashboardMenuLabels
     */
    @Override
    public String getDashboardLabels() {
        return dashboardLabels;
    }

    /**
     * This method returns the labels related to dashboard menu in account
     * management page.
     *
     * @return dashboardMenuLabels
     */
    @Override
    public String getDashboardConfig() {
        return dashboardConfig;
    }

    /**
     * This method returns the labels related to dashboard menu in account
     * management page.
     *
     * @return dashboardMenuLabels
     */
    @Override
    public String getPurchaseAccountConfig() {
        return purchaseAccountConfig;
    }
}