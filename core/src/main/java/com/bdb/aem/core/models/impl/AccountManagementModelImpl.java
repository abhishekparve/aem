package com.bdb.aem.core.models.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.AccountManagementModel;
import com.bdb.aem.core.pojo.DashboardMenuItems;
import com.bdb.aem.core.pojo.DashboardPageNavigationLabels;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.ExcludeUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 * The Class AccountManagementModelImpl.
 */
@Model(
        adaptables = {SlingHttpServletRequest.class, Resource.class},
        adapters = {AccountManagementModel.class},
        resourceType = {AccountManagementModelImpl.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class AccountManagementModelImpl implements AccountManagementModel {

    /**
     * The Constant RESOURCE_TYPE.
     */
    protected static final String RESOURCE_TYPE = "bdb-aem/components/content/accountmanagement/v1/accountmanagement";


    /**
     * The Constant log.
     */
    protected static final Logger log = LoggerFactory.getLogger(AccountManagementModelImpl.class);

    private String dashboardPageNavigationLabels;

    /**
     * The welcome Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("welcomeLabel")
    private String welcomeText;

    /**
     * The dashboard Label
     */
    @Inject
    @Via("resource")
    @SerializedName("dashboardLabels")
    private String dashboardLabel;

    /**
     * The dashboard Icon
     */
    @Inject
    @Via("resource")
    @SerializedName("dashboardIcon")
    private String dashboardIcon;

    /**
     * The dashboard Icon Alt text
     */
    @Inject
    @Via("resource")
    @SerializedName("dashboardIconAltText")
    private String dashboardIconAltText;

    /**
     * The orders Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("ordersLabels")
    private String ordersLabel;

    /**
     * The orders Icon.
     */
    @Inject
    @Via("resource")
    @SerializedName("ordersIcon")
    private String ordersIcon;

    /**
     * The orders Icon Alt text.
     */
    @Inject
    @Via("resource")
    @SerializedName("ordersIconAltText")
    private String ordersIconAltText;

    /**
     * The ordersApprovalLabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("ordersApprovalLabel")
    private String ordersApprovalLabel;

    /**
     * The ordersApprovalIcon
     *
     * */
    @Inject
    @Via("resource")
    @SerializedName("ordersApprovalIcon")
    private String ordersApprovalIcon;

    /**
     * The ordersApprovalIconAltText.
     */
    @Inject
    @Via("resource")
    @SerializedName("ordersApprovalIconAltText")
    private String ordersApprovalIconAltText;

    /**
     * The shopping Lists Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("shoppingListsLabel")
    private String shoppingListsLabel;

    /**
     * The shoppingListsIcon
     */
    @Inject
    @Via("resource")
    @SerializedName("shoppingListsIcon")
    private String shoppingListsIcon;

    /**
     * The shoppingListsIconAltText
     */
    @Inject
    @Via("resource")
    @SerializedName("shoppingListsIconAltText")
    private String shoppingListsIconAltText;

    /**
     * The rewardsLabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("rewardsLabel")
    private String rewardsLabel;

    /**
     * The rewardsIcon.
     */
    @Inject
    @Via("resource")
    @SerializedName("rewardsIcon")
    private String rewardsIcon;


    /**
     * The rewardsIconAltText.
     */
    @Inject
    @Via("resource")
    @SerializedName("rewardsIconAltText")
    private String rewardsIconAltText;

    /**
     * The certificationsLabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("certificationsLabel")
    private String certificationsLabel;

    /**
     * The certificationsIcon.
     */
    @Inject
    @Via("resource")
    @SerializedName("certificationsIcon")
    private String certificationsIcon;

    /**
     * The certificationsIconAltText.
     */
    @Inject
    @Via("resource")
    @SerializedName("certificationsIconAltText")
    private String certificationsIconAltText;

    /**
     * The paymentMethodsLabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("paymentMethodsLabel")
    private String paymentMethodsLabel;

    /**
     * The paymentMethodsIcon.
     */
    @Inject
    @Via("resource")
    @SerializedName("paymentMethodsIcon")
    private String paymentMethodsIcon;

    /**
     * The paymentMethodsIconAltText.
     */
    @Inject
    @Via("resource")
    @SerializedName("paymentMethodsIconAltText")
    private String paymentMethodsIconAltText;

    /**
     * The addressesLabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("addressesLabel")
    private String addressesLabel;

    /**
     * The addressesIcon.
     */
    @Inject
    @Via("resource")
    @SerializedName("addressesIcon")
    private String addressesIcon;

    /**
     * The addressesIconAltText.
     */
    @Inject
    @Via("resource")
    @SerializedName("addressesIconAltText")
    private String addressesIconAltText;

    /**
     * The accountSettingsLabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("accountSettingsLabel")
    private String accountSettingsLabel;

    /**
     * The accountSettingsIcon.
     */
    @Inject
    @Via("resource")
    @SerializedName("accountSettingsIcon")
    private String accountSettingsIcon;

    /**
     * The accountSettingsIconAltText.
     */
    @Inject
    @Via("resource")
    @SerializedName("accountSettingsIconAltText")
    private String accountSettingsIconAltText;

    /**
     * The communicationSettingsLabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("communicationSettingsLabel")
    private String communicationSettingsLabel;

    /**
     * The communicationSettingsIcon.
     */
    @Inject
    @Via("resource")
    @SerializedName("communicationSettingsIcon")
    private String communicationSettingsIcon;

    /**
     * The communicationSettingsIconAltText.
     */
    @Inject
    @Via("resource")
    @SerializedName("communicationSettingsIconAltText")
    private String communicationSettingsIconAltText;

    /**
     * The grantsLabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("grantsLabel")
    private String grantsLabel;

    /**
     * The grantsIcon.
     */
    @Inject
    @Via("resource")
    @SerializedName("grantsIcon")
    private String grantsIcon;

    /**
     * The grantsIconAltText.
     */
    @Inject
    @Via("resource")
    @SerializedName("grantsIconAltText")
    private String grantsIconAltText;

    /**
     * The quotesLabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("quotesLabel")
    private String quotesLabel;

    /**
     * The quotesIcon.
     */
    @Inject
    @Via("resource")
    @SerializedName("quotesIcon")
    private String quotesIcon;

    /**
     * The quotesIconAltText.
     */
    @Inject
    @Via("resource")
    @SerializedName("quotesIconAltText")
    private String quotesIconAltText;

    @Inject
    @Via("resource")
    @SerializedName("quoteHistoryLabel")
    private String quoteHistoryLabel;

    /**
     * The quotesIcon.
     */
    @Inject
    @Via("resource")
    @SerializedName("quoteHistoryIcon")
    private String quoteHistoryIcon;

    /**
     * The quotesIconAltText.
     */
    @Inject
    @Via("resource")
    @SerializedName("quoteHistoryIconAltText")
    private String quoteHistoryIconAltText;

    @Inject
    @Via("resource")
    @SerializedName("quoteHistoryADALabel")
    private String quoteHistoryADALabel;


    /**
     * The quoteListLabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("quoteListLabel")
    private String quoteListLabel;

    /**
     * The quoteListIcon.
     */
    @Inject
    @Via("resource")
    @SerializedName("quoteListIcon")
    private String quoteListIcon;

    /**
     * The quoteListIconAltText.
     */
    @Inject
    @Via("resource")
    @SerializedName("quoteListIconAltText")
    private String quoteListIconAltText;

    /**
     * The needHelpLabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("needHelpCTALabel")
    private String needHelpLabel;

    /**
     * The needHelpUrl.
     */
    @Inject
    @Via("resource")
    @SerializedName("needHelpCTALink")
    private String needHelpUrl;
    
    /**
     * The dashboardADALabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("dashboardADALabel")
    private String dashboardADALabel;
    
    /**
     * The ordersADALabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("ordersADALabel")
    private String ordersADALabel;
    
    /**
     * The ordersApprovalADALabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("ordersApprovalADALabel")
    private String ordersApprovalADALabel;
    
    /**
     * The shoppingListsADALabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("shoppingListsADALabel")
    private String shoppingListsADALabel;
    
    /**
     * The rewardsADALabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("rewardsADALabel")
    private String rewardsADALabel;
    
    /**
     * The certificationsADALabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("certificationsADALabel")
    private String certificationsADALabel;
    
    /**
     * The paymentMethodsADALabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("paymentMethodsADALabel")
    private String paymentMethodsADALabel;
    
    /**
     * The addressesADALabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("addressesADALabel")
    private String addressesADALabel;
    
    /**
     * The accountSettingsADALabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("accountSettingsADALabel")
    private String accountSettingsADALabel;
    
    /**
     * The communicationSettingsADALabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("communicationSettingsADALabel")
    private String communicationSettingsADALabel;
    
    /**
     * The grantsADALabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("grantsADALabel")
    private String grantsADALabel;
    
    /**
     * The quotesADALabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("quotesADALabel")
    private String quotesADALabel;
    
    /**
     * The quoteListADALabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("quoteListADALabel")
    private String quoteListADALabel;

    /**
     * The resource resolver.
     */
    @SlingObject
    private ResourceResolver resourceResolver;


    /** The externalizer. */
    @Inject
    ExternalizerService externalizer;



    /**
     * Populates the dashboardPageNavigationLabels.
     */
    @PostConstruct
    protected void init() {
        log.debug("Starting Init() method : AccountManagementModelImpl class");
        populateDashboardLabels();
        log.debug("exiting Init() method : AccountManagementModelImpl class");
    }


    /**
     * get the dashboardPageNavigationLabels.
     */
    @Override
    public String getDashboardPageNavigationLabels() {
        return dashboardPageNavigationLabels;
    }

    private void populateDashboardLabels()
    {
        List<DashboardMenuItems> dashboardMenuItems = new ArrayList<>();
        if(StringUtils.isNotBlank(dashboardLabel))
        {
            dashboardMenuItems.add(new DashboardMenuItems(dashboardLabel,dashboardIcon,dashboardIconAltText, dashboardADALabel,CommonConstants.DASHBOARD_PATH));
        }
        if(StringUtils.isNotBlank(ordersLabel))
        {
            dashboardMenuItems.add(new DashboardMenuItems(ordersLabel,ordersIcon,ordersIconAltText,ordersADALabel,CommonConstants.ORDERS_PATH));
        }
        if(StringUtils.isNotBlank(ordersApprovalLabel))
        {
            dashboardMenuItems.add(new DashboardMenuItems(ordersApprovalLabel,ordersApprovalIcon,ordersApprovalIconAltText,ordersApprovalADALabel,CommonConstants.ORDERS_APPROVAL_PATH));
        }
        if(StringUtils.isNotBlank(shoppingListsLabel))
        {
            dashboardMenuItems.add(new DashboardMenuItems(shoppingListsLabel,shoppingListsIcon,shoppingListsIconAltText,shoppingListsADALabel,CommonConstants.SHOPPING_LISTS_PATH));
        }
        if(StringUtils.isNotBlank(rewardsLabel))
        {
            dashboardMenuItems.add(new DashboardMenuItems(rewardsLabel,rewardsIcon,rewardsIconAltText,rewardsADALabel,CommonConstants.REWARDS_PATH));
        }
        if(StringUtils.isNotBlank(certificationsLabel))
        {
            dashboardMenuItems.add(new DashboardMenuItems(certificationsLabel,certificationsIcon,certificationsIconAltText,certificationsADALabel,CommonConstants.CERTIFICATIONS_PATH));
        }
        if(StringUtils.isNotBlank(paymentMethodsLabel))
        {
            dashboardMenuItems.add(new DashboardMenuItems(paymentMethodsLabel,paymentMethodsIcon,paymentMethodsIconAltText,paymentMethodsADALabel,CommonConstants.PAYMENT_METHODS_PATH));
        }
        if(StringUtils.isNotBlank(addressesLabel))
        {
            dashboardMenuItems.add(new DashboardMenuItems(addressesLabel,addressesIcon,addressesIconAltText,addressesADALabel,CommonConstants.ADDRESSES_PATH));
        }
        if(StringUtils.isNotBlank(accountSettingsLabel))
        {
            dashboardMenuItems.add(new DashboardMenuItems(accountSettingsLabel,accountSettingsIcon,accountSettingsIconAltText,accountSettingsADALabel,CommonConstants.ACCOUNT_SETTINGS_PATH));
        }
        if(StringUtils.isNotBlank(communicationSettingsLabel))
        {
            dashboardMenuItems.add(new DashboardMenuItems(communicationSettingsLabel,communicationSettingsIcon,communicationSettingsIconAltText,communicationSettingsADALabel,CommonConstants.COMMUNICATION_SETTINGS_PATH));
        }
        if(StringUtils.isNotBlank(grantsLabel))
        {
            dashboardMenuItems.add(new DashboardMenuItems(grantsLabel,grantsIcon,grantsIconAltText,grantsADALabel,CommonConstants.GRANTS_PATH));
        }
        if(StringUtils.isNotBlank(quotesLabel))
        {
            dashboardMenuItems.add(new DashboardMenuItems(quotesLabel,quotesIcon,quotesIconAltText,quotesADALabel,CommonConstants.QUOTES_PATH));
        }
        if(StringUtils.isNotBlank(quoteListLabel))
        {
            dashboardMenuItems.add(new DashboardMenuItems(quoteListLabel,quoteListIcon,quoteListIconAltText,quoteListADALabel,CommonConstants.QUOTE_LIST_PATH));
        }
        if(StringUtils.isNotBlank(quoteHistoryLabel))
        {
            dashboardMenuItems.add(new DashboardMenuItems(quoteHistoryLabel,quoteHistoryIcon,quoteHistoryIconAltText,quoteHistoryADALabel,CommonConstants.QUOTE_HISTORY_PATH));

        }

        DashboardPageNavigationLabels dashboardPageNavigationLabelsObj = new DashboardPageNavigationLabels(
                welcomeText,
                dashboardMenuItems,
                needHelpLabel,
                externalizer.getFormattedUrl(needHelpUrl, resourceResolver)
        );
        ExcludeUtil excludeUtilObject = new ExcludeUtil();
        Gson accountManagementConfigGson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                .addSerializationExclusionStrategy(excludeUtilObject).create();
        dashboardPageNavigationLabels = accountManagementConfigGson.toJson(dashboardPageNavigationLabelsObj);
    }
}