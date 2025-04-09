package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.models.AccountSettingsModel;
import com.bdb.aem.core.models.GlobalErrorMessagesModel;
import com.bdb.aem.core.pojo.*;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.*;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * The Class AccountSettingsModelImpl.
 */
@Model(
        adaptables = {SlingHttpServletRequest.class, Resource.class},
        adapters = {AccountSettingsModel.class},
        resourceType = {AccountSettingsModelImpl.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class AccountSettingsModelImpl implements AccountSettingsModel {

    /**
     * The Constant RESOURCE_TYPE.
     */
    protected static final String RESOURCE_TYPE = "bdb-aem/components/content/accountmanagement/v1/accountmanagement";

    /**
     * The Constant log.
     */
    protected static final Logger log = LoggerFactory.getLogger(AccountSettingsModelImpl.class);

    private String accountSettingLabels;
    private String accountSettingConfig;

    /**
     * CurrentPage.
     */
    @Inject
    private Page currentPage;

    @Self
    private SlingHttpServletRequest request;

    /**
     * The accountSettingsHeader.
     */
    @Inject
    @Via("resource")
    private String accountSettingsHeader;
    
    /**
     * The personalInformation
     */
    @Inject
    @Via("resource")
    private String personalInformation;

    /**
     * The updateInformationLinkLabel
     */
    @Inject
    @Via("resource")
    private String updateInformationLinkLabel;

    /**
     * The mobilePhoneIndicatorText.
     */
    @Inject
    @Via("resource")
    private String mobilePhoneIndicatorText;

    /**
     * The updateInformationModalHeader.
     */
    @Inject
    @Via("resource")
    private String updateInformationModalHeader;

    /**
     * The updateCta
     */
    @Inject
    @Via("resource")
    private String updateCta;

    /**
     * The successfulInfoUpdateMsg.
     */
    @Inject
    @Via("resource")
    private String successfulInfoUpdateMsg;


    /**
     * The distributorTitle.
     */
    @Inject
    @Via("resource")
    private String distributorTitle;

    /**
     * The updatePasswordModalHeader.
     */
    @Inject
    @Via("resource")
    private String updatePasswordModalHeader;

    /**
     * The successfulPasswordUpdateMsg.
     */
    @Inject
    @Via("resource")
    private String successfulPasswordUpdateMsg;

    /**
     * The loginSettingsText.
     */
    @Inject
    @Via("resource")
    private String loginSettingsText;

    /**
     * The updatePasswordLabel.
     */
    @Inject
    @Via("resource")
    private String updatePasswordLabel;

    /**
     * The accountPreferencesText.
     */
    @Inject
    @Via("resource")
    private String accountPreferencesText;

    @Inject
    @Via("resource")
    private String accountPreferencesUpdateLabel;

    /**
     * The selectionsText.
     */
    @Inject
    @Via("resource")
    private String selectionsText;

    /**
     * The successfulPreferenceUpdateMsg.
     */
    @Inject
    @Via("resource")
    private String successfulPreferenceUpdateMsg;

    @Inject
    @Via("resource")
    private String aofPagePath;

    @Inject
    @Via("resource")
    private String industryLabel;

    @Inject
    @Via("resource")
    private String roleLabel;

    @Inject
    @Via("resource")
    private String interestLabel;

    @Inject
    @Via("resource")
    private String closeIcon;

    @Inject
    @Via("resource")
    private String closeIconAltText;

    @Inject
    @Via("resource")
    private String accountPreferenceCloseIcon;

    @Inject
    @Via("resource")
    private String accountPreferenceCloseIconAltText;

    @Inject
    @Via("resource")
    private String accountPreferenceLogoIcon;

    @Inject
    @Via("resource")
    private String accountPreferenceLogoIconAltText;
    
    @Inject
    @Via("resource")
    private String nextButtonLabel;


    /**
     * The bdb api endpoint service.
     */
    @Inject
    private BDBApiEndpointService bdbApiEndpointService;

    /**
     * The resource resolver.
     */
    @SlingObject
    private ResourceResolver resourceResolver;

    /**
     * The area of focus labels.
     */
    private String areaFocusLabels;

    /**
     * The area focus config.
     */
    private String areaFocusConfig;


    /**
     * The Hybris Site ID
     */
    private String hybrisSiteId;

    private String firstNameError = StringUtils.EMPTY;

    private String lastNameError = StringUtils.EMPTY;

    private String phoneNumberValidationText = StringUtils.EMPTY;

    private String phoneNumberPatternError = StringUtils.EMPTY;

    /**
     * The Constant AREAOFFOCUS_RESOURCE_TYPE.
     */
    protected static final String AREAOFFOCUS_RESOURCE_TYPE = "bdb-aem/proxy/components/content/areaoffocus";

    /**
     * The Constant PASS_RULES_RESOURCE_TYPE.
     */
    protected static final String PASS_RULES_RESOURCE_TYPE = "bdb-aem/proxy/components/content/globalerror";

    /**
     * Populates the accountSettingLabels.
     * Populates the accountSettingConfig.
     */

    @PostConstruct
    protected void init() {
        log.debug("Inside Init Method of AccountSettingModelImpl class");

        ExcludeUtil excludeUtilObject = new ExcludeUtil();
        setAccountSettingLabels(excludeUtilObject);

        //Set the Hybris Site Id
        hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);


        //To get the Area of Focus Labels
        if (StringUtils.isNotEmpty((aofPagePath))) {
            areaFocusLabels = AreaOfFocusUtil.getAreaOfFocusLabel(aofPagePath, resourceResolver,
                    AREAOFFOCUS_RESOURCE_TYPE);
        }
        //Set the configs
        if (bdbApiEndpointService != null) {
            setAccountSettingConfig(excludeUtilObject);

            //To get the Area of Focus Configuration
            areaFocusConfig = AreaOfFocusUtil.getAreaFocusConfigurationJson(
                    excludeUtilObject,
                    bdbApiEndpointService,
                    hybrisSiteId);
        }
    }

    private void setAccountSettingLabels(ExcludeUtil excludeUtilObject) {

        LoginInfoLabels loginInfoLabels;
        //Set the error page path
        String errorPagePath = CommonHelper.getErrorPagePath(currentPage);
        GlobalErrorMessagesModel errorModel = GlobalErrorMessagesModelUtil.getErrorMessageModel(
                errorPagePath, resourceResolver,
                PASS_RULES_RESOURCE_TYPE);
            if(null != errorModel) {
                firstNameError = errorModel.getFirstNameError();
                lastNameError = errorModel.getLastNameError();
                phoneNumberValidationText = errorModel.getPhoneNumberValidationText();
                phoneNumberPatternError = errorModel.getPhoneNumPatternError();

                PasswordRuleLabels passwordRuleLabelsObj = new PasswordRuleLabels(errorModel.getRulesHeading(), errorModel.getLengthRuleLabel(),
                        errorModel.getCaseRuleLabel(), errorModel.getNumericRuleLabel(),
                        errorModel.getSymbolRuleLabel(), errorModel.getNoSpacesLabel());

                loginInfoLabels = new LoginInfoLabels(
                        loginSettingsText,
                        CommonHelper.getLabel(CommonConstants.EMAIL_ADDRESS_LABEL, currentPage),
                        CommonHelper.getLabel(CommonConstants.PASS_LABEL, currentPage),
                        updatePasswordLabel,
                        CommonHelper.getLabel(CommonConstants.EDIT_PASS_LABEL, currentPage),
                        CommonHelper.getLabel(CommonConstants.ENTER_NEW_PASS_LABEL, currentPage),
                        CommonHelper.getLabel(CommonConstants.CONFIRM_NEW_PASS_LABEL, currentPage),
                        CommonHelper.getLabel(CommonConstants.CANCEL_LABEL, currentPage),
                        updateCta,
                        successfulPasswordUpdateMsg,
                        updatePasswordModalHeader,
                        errorModel.getCurrentPwdRequiredError(),
                        errorModel.getPasswordPatternError(),
                        errorModel.getPasswordError(),
                        passwordRuleLabelsObj,
                        errorModel.getConfPasswordError(),
                        errorModel.getConfPasswordNotMatchError(),
                        errorModel.getPasswordFLNameError());
            }
            else {
                loginInfoLabels = new LoginInfoLabels(
                        loginSettingsText,
                        CommonHelper.getLabel(CommonConstants.EMAIL_ADDRESS_LABEL, currentPage),
                        CommonHelper.getLabel(CommonConstants.PASS_LABEL, currentPage),
                        updatePasswordLabel,
                        CommonHelper.getLabel(CommonConstants.EDIT_PASS_LABEL, currentPage),
                        CommonHelper.getLabel(CommonConstants.ENTER_NEW_PASS_LABEL, currentPage),
                        CommonHelper.getLabel(CommonConstants.CONFIRM_NEW_PASS_LABEL, currentPage),
                        CommonHelper.getLabel(CommonConstants.CANCEL_LABEL, currentPage),
                        updateCta,
                        successfulPasswordUpdateMsg,
                        updatePasswordModalHeader);
            }

        PersonalInfoLabels personalInfoLabels = new PersonalInfoLabels(
                personalInformation,
                CommonHelper.getLabel(CommonConstants.FIRST_NAME_LABEL, currentPage),
                CommonHelper.getLabel(CommonConstants.LAST_NAME_LABEL, currentPage),
                CommonHelper.getLabel(CommonConstants.COMPANY_LABEL, currentPage),
                CommonHelper.getLabel(CommonConstants.PHONE_NUMBER_LABEL, currentPage),
                CommonHelper.getLabel(CommonConstants.EAN_CODE_LABEL, currentPage),
                CommonHelper.getLabel(CommonConstants.VAT_NUMBER_LABEL, currentPage),
                updateInformationLinkLabel,
                mobilePhoneIndicatorText,
                CommonHelper.getLabel(CommonConstants.CANCEL_LABEL, currentPage),
                updateCta,
                successfulInfoUpdateMsg,
                updateInformationModalHeader,
                closeIcon,
                closeIconAltText,
                firstNameError,
                lastNameError,
                phoneNumberValidationText,
                phoneNumberPatternError
                );

        DistributorInfoLabels distributorInfoLabels = new DistributorInfoLabels(
                distributorTitle,
                CommonHelper.getLabel(CommonConstants.NAME, currentPage),
                CommonHelper.getLabel(CommonConstants.EMAIL, currentPage),
                CommonHelper.getLabel(CommonConstants.ADDRESS_LABEL, currentPage));

        AccountPreferenceLabels accountPreferenceLabels = new AccountPreferenceLabels(
                accountPreferencesText,
                selectionsText,
                industryLabel,
                roleLabel,
                interestLabel,
                accountPreferencesUpdateLabel,
                successfulPreferenceUpdateMsg,
                accountPreferenceCloseIcon,
                accountPreferenceCloseIconAltText,
                accountPreferenceLogoIcon,               
                accountPreferenceLogoIconAltText);
                

        AccountSettingLabels accountSettingLabelsObj = new AccountSettingLabels(
                accountSettingsHeader,
                personalInfoLabels,
                distributorInfoLabels,
                loginInfoLabels,
                accountPreferenceLabels,
                nextButtonLabel);
                
        Gson accountSettingConfigGson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                .addSerializationExclusionStrategy(excludeUtilObject).create();
        accountSettingLabels = accountSettingConfigGson.toJson(accountSettingLabelsObj);
    }

    private void setAccountSettingConfig(ExcludeUtil excludeUtilObject) {
        //set get User Settings endpoint
        String userSettingsEndpoint = StringUtils.replace(
                bdbApiEndpointService.getUserSettingsEndpoint(),
                CommonConstants.HYBRIS_SITE_LITERAL,
                hybrisSiteId);
        Payload userSettingsRequestPayload = new Payload(
                bdbApiEndpointService.getBDBHybrisDomain() + userSettingsEndpoint,
                HttpConstants.METHOD_GET);
        PayloadConfig getUserSettingsConfig = new PayloadConfig(userSettingsRequestPayload);

        // set Update User Details endpoint
        String userDetailsEndpoint = StringUtils.replace(
                bdbApiEndpointService.updateUserDetailsEndpoint(),
                CommonConstants.HYBRIS_SITE_LITERAL,
                hybrisSiteId);
        Payload userDetailsRequestPayload = new Payload(
                bdbApiEndpointService.getBDBHybrisDomain() + userDetailsEndpoint
                , HttpConstants.METHOD_PUT);
        PayloadConfig updateUserDetailsConfig = new PayloadConfig(userDetailsRequestPayload);

        // set Update User password endpoint
        String userPwdEndpoint = StringUtils.replace(
                bdbApiEndpointService.updateUserPwdEndpoint(),
                CommonConstants.HYBRIS_SITE_LITERAL,
                hybrisSiteId);
        Payload userPwdRequestPayload = new Payload(
                bdbApiEndpointService.getBDBHybrisDomain() + userPwdEndpoint,
                HttpConstants.METHOD_PUT);
        PayloadConfig updateUserPwdConfig = new PayloadConfig(userPwdRequestPayload);

        // creating the final config
        AccountSettingConfig accountSettingConfigObj = new AccountSettingConfig(getUserSettingsConfig,
                updateUserDetailsConfig,
                updateUserPwdConfig);
        Gson accountSettingConfigGson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                .addSerializationExclusionStrategy(excludeUtilObject).create();
        accountSettingConfig = accountSettingConfigGson.toJson(accountSettingConfigObj);

    }

    /**
     * Get the Industry Role Interest Labels.
     *
     * @return - Industry Role Interest Labels as a String
     */
    @Override
    public String getAreaFocusLabels() {
        return areaFocusLabels;
    }

    /**
     * Gets the area focus config.
     *
     * @return the area focus config
     */
    @Override
    public String getAreaFocusConfig() {
        return areaFocusConfig;
    }

    /**
     * Get the accountSettingLabels.
     *
     * @return - accountSettingLabels as a String
     */
    @Override
    public String getAccountSettingLabels() {
        return accountSettingLabels;
    }

    /**
     * Gets the accountSettingConfig.
     *
     * @return the accountSettingConfig as a string
     */
    @Override
    public String getAccountSettingConfig() {
        return accountSettingConfig;
    }

}