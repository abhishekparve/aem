package com.bdb.aem.core.models.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.AccessLinksModel;
import com.bdb.aem.core.models.AreaOfFocusModel;
import com.bdb.aem.core.models.GlobalErrorMessagesModel;
import com.bdb.aem.core.models.SignUpModel;
import com.bdb.aem.core.pojo.ConfirmationModelConfig;
import com.bdb.aem.core.pojo.IndustryRoleInterestModelConfig;
import com.bdb.aem.core.pojo.PasswordRuleLabels;
import com.bdb.aem.core.pojo.Payload;
import com.bdb.aem.core.pojo.PayloadConfig;
import com.bdb.aem.core.pojo.SignUpConfig;
import com.bdb.aem.core.pojo.SignUpLabelConfig;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;

/**
 * The Class SignUpModelImpl.
 */
@Model(adaptables ={ SlingHttpServletRequest.class,
        Resource.class }, adapters = {SignUpModel.class}, resourceType = {
        SignUpModelImpl.RESOURCE_TYPE}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class SignUpModelImpl implements SignUpModel {

    /**
     * The Constant RESOURCE_TYPE.
     */
    protected static final String RESOURCE_TYPE = "bdb-aem/components/content/signup/v1/signup";

    /**
     * The Constant AREAOFFOCUS_RESOURCE_TYPE.
     */
    protected static final String AREAOFFOCUS_RESOURCE_TYPE = "bdb-aem/proxy/components/content/areaoffocus";

    /**
     * The Constant log.
     */
    protected static final Logger log = LoggerFactory.getLogger(SignUpModelImpl.class);

    /**
     * The Constant PASS_RULES_RESOURCE_TYPE.
     */
    protected static final String PASS_RULES_RESOURCE_TYPE = "bdb-aem/proxy/components/content/globalerror";
    
    /** CurrentPage. */
    @Inject
    private Page currentPage;
    
    @Self
    private SlingHttpServletRequest request;


    /**
     * The title.
     */
    @Inject
    @Via("resource")
    private String title;

    /**
     * The description.
     */
    @Inject
    @Via("resource")
    private String description;

    /**
     * The fields required.
     */
    @Inject
    @Via("resource")
    private String fieldsRequired;

    /**
     * The first name placeholder.
     */
    @Inject
    @Via("resource")
    private String firstNamePlaceholder;

    /**
     * The first name error.
     */
    @Inject
    @Via("resource")
    private String firstNameError;

    /**
     * The last name placeholder.
     */
    @Inject
    @Via("resource")
    private String lastNamePlaceholder;

    /**
     * The last name error.
     */
    @Inject
    @Via("resource")
    private String lastNameError;

    /**
     * The company institution placeholder.
     */
    @Inject
    @Via("resource")
    private String companyInstitutionPlaceholder;

    /**
     * The company error.
     */
    @Inject
    @Via("resource")
    private String companyError;

    /**
     * The mobile label.
     */
    @Inject
    @Via("resource")
    private String mobileLabel;

    /**
     * The phone number placeholder text.
     */
    @Inject
    @Via("resource")
    private String phoneNumberPlaceholderText;

    /**
     * The phone number validation text.
     */
    @Inject
    @Via("resource")
    private String phoneNumberValidationText;

    /**
     * The phone num pattern error.
     */
    @Inject
    @Via("resource")
    private String phoneNumPatternError;

    /**
     * The email placeholder text.
     */
    @Inject
    @Via("resource")
    private String emailPlaceholderText;

    /** The email tool tip. */
    @Inject
    @Via("resource")
    private String emailToolTip;
    
    /**
     * The email validation text.
     */
    @Inject
    @Via("resource")
    private String emailValidationText;

    /**
     * The email pattern error.
     */
    @Inject
    @Via("resource")
    private String emailPatternError;

    /**
     * The password placeholder text.
     */
    @Inject
    @Via("resource")
    private String passwordPlaceholderText;

    /**
     * The password error.
     */
    @Inject
    @Via("resource")
    private String passwordError;

    /**
     * The password FL name error.
     */
    @Inject
    @Via("resource")
    private String passwordFLNameError;

    /**
     * The password pattern error.
     */
    @Inject
    @Via("resource")
    private String passwordPatternError;

    /**
     * The confirm password placeholder text.
     */
    @Inject
    @Via("resource")
    private String confirmPasswordPlaceholderText;

    /**
     * The confirm password error.
     */
    @Inject
    @Via("resource")
    private String confpasswordError;

    /**
     * The confirm password not match error.
     */
    @Inject
    @Via("resource")
    private String confpasswordNotMatchError;

    /**
     * The offers checkbox text.
     */
    @Inject
    @Via("resource")
    private String offersCheckboxText;

    /**
     * The updates checkbox text.
     */
    @Inject
    @Via("resource")
    private String updatesCheckboxText;

    /**
     * The age checkbox text.
     */
    @Inject
    @Via("resource")
    private String ageCheckboxText;



    /**
     * The country label.
     */
    @Inject
    @Via("resource")
    private String countryLabel;

    /**
     * The country error.
     */
    @Inject
    @Via("resource")
    private String countryError;

    /**
     * The tnc note.
     */
    @Inject
    @Via("resource")
    private String tncNote;

    /**
     * The agree submit cta label.
     */
    @Inject
    @Via("resource")
    private String agreeSubmitCtaLabel;

    /**
     * The existing user label.
     */
    @Inject
    @Via("resource")
    private String existingUserLabel;

    /**
     * The sign in label.
     */
    @Inject
    @Via("resource")
    private String signInLabel;
    
    /**
     * The existing User Modal Title.
     */
    @Inject
    @Via("resource")
    private String existingUserModalTitle;
    
    /**
     * The existing User Modal Description.
     */
    @Inject
    @Via("resource")
    private String existingUserModalDescription;
    
    /**
     * The existing User Modal CTA.
     */
    @Inject
    @Via("resource")
    private String existingUserModalCTA;

    /**
     * The image.
     */
    @Inject
    @Via("resource")
    private String image;

    /**
     * The imageAltText.
     */
    @Inject
    @Via("resource")
    private String imageAltText;
    /**
     * The confirmation title.
     */
    @Inject
    @Via("resource")
    private String confirmationTitle;
    /**
     * The content.
     */
    @Inject
    @Via("resource")
    private String content;

    /**
     * The access text.
     */
    @Inject
    @Via("resource")
    private String accessText;


    /**
     * The linksTitle.
     */
    @Inject
    @Via("resource")
    private String linksTitle;

    /** The links multifield. */
    @Inject
    @Via("resource")
    private List<Resource> linksMultifield;

    /** The role list. */
    @SerializedName("links")
    private List<AccessLinksModel> accessLinks = new ArrayList<>();

    /**
     * The purchasing account label.
     */
    @Inject
    @Via("resource")
    private String purchasingAccountLabel;

    /**
     * The skip and browse label.
     */
    @Inject
    @Via("resource")
    private String skipAndBrowseLabel;

    /**
     * The skip and browse url.
     */
    @Inject
    @Via("resource")
    private String skipAndBrowseUrl;

    /** The aof page path. */
    @Inject
    @Via("resource")
    private String aofPagePath;

    /** The sign up ot consent url. */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String signUpOtConsentUrl;
    
    /** The sign up ot consent data payload. */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String signUpOtConsentDataPayload;
    
    /** The domain error. */
    @Inject
    @Via("resource")
    @Default(values=StringUtils.EMPTY)
    private String domainError;
    
    /**
     * The resource resolver.
     */
    @SlingObject
    private ResourceResolver resourceResolver;

    /**
     * The bdb api endpoint service.
     */
    @Inject
    private BDBApiEndpointService bdbApiEndpointService;

    /** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;

    /**
     * The iri labels.
     */
    private String iriLabels;

    /**
     * The sign up labels.
     */
    private String signUpLabels;

    /**
     * The confirmation labels.
     */
    private String confirmationLabels;

    /**
     * The sign up config.
     */
    private String signUpConfig;

    /**
     * The password rule labels.
     */
    private String passwordRuleLabels;

    /**
     * The area focus config.
     */
    private String areaFocusConfig;

    /** The password rule labels obj. */
    private PasswordRuleLabels passwordRuleLabelsObj;

    /** The Hybris Site ID. */
    private String hybrisSiteId;

    /**
     * Gets the area focus config.
     *
     * @return the area focus config
     */
    public String getAreaFocusConfig() {
        return areaFocusConfig;
    }
  

    /**
     * Creates Sign Up Labels, Confirmation Labels, Sign Up & Password Rules Config
     * Labels And Industry Rules Interest Labels JSON Elements.
     */
    @PostConstruct
    protected void init() {
        log.debug("Inside Init Method");
        
        if(StringUtils.isNotEmpty(tncNote)) {
        	try {
				tncNote = CommonHelper.HandleRTEAnchorLink(tncNote, externalizerService, resourceResolver,StringUtils.EMPTY);
			} catch (IOException e) {
				log.error("Error while processing RTE text",e);
			}
		}
        populateAccessLinks();

        ExcludeUtil excludeUtilObject = new ExcludeUtil();

        SignUpLabelConfig signUpLabelConfig = createSignUpLabelConfig();
        Gson signUpJson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                .addSerializationExclusionStrategy(excludeUtilObject).create();
        signUpLabels = signUpJson.toJson(signUpLabelConfig);

        ConfirmationModelConfig confirmationModelConfig = createConfirmationModelConfig();
        Gson confirmationJson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                .addSerializationExclusionStrategy(excludeUtilObject).create();
            confirmationLabels = confirmationJson.toJson(confirmationModelConfig);

        //To get the Area of Focus Labels
        if (StringUtils.isNotEmpty((aofPagePath))) {
            iriLabels = getAreaOfFocusLabel(aofPagePath, resourceResolver,
                    AREAOFFOCUS_RESOURCE_TYPE);
        }
        //Set the Hybris Site Id
        hybrisSiteId  = CommonHelper.setHybrisSiteId(currentPage);

        //Set the configs
        if (bdbApiEndpointService != null) {
            log.debug("bdbApiEndpointService" , bdbApiEndpointService);
            log.debug("testConfig value " , bdbApiEndpointService.getTestConfig());
          
            //To get the Area of Focus Configuration
            areaFocusConfig = getAreaFocusConfigurationJson(excludeUtilObject);


            // To get the SignUp config :
            Payload getCountriesPayload = new Payload(bdbApiEndpointService.getCountriesFromRegionServletPath(),
                    HttpConstants.METHOD_GET);

            String signUpEndpoint =
                    CommonHelper.getEndPointUrlWithoutSiteId(bdbApiEndpointService,
                            bdbApiEndpointService.getHybrisSignUpEndpoint().trim(),
                            true,
                            currentPage);


            Payload postRegisterPayload = new Payload(signUpEndpoint,
                    HttpConstants.METHOD_POST);
            
            String emailValidationEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain()
            		+ bdbApiEndpointService.emailValidationEndpoint(),
            		CommonConstants.BASE_SITE_ID, hybrisSiteId);
            Payload emailValidationPayload = new Payload(emailValidationEndpoint, HttpConstants.METHOD_POST);
            
        	JsonParser parser = new JsonParser();
        	JsonElement dataPayload;
        	try {
        		dataPayload = parser.parse(signUpOtConsentDataPayload);
			} catch (JsonParseException e) {
				log.error("JsonParseException {}", e.getMessage());
				dataPayload = new JsonObject();
			}
            JsonObject singUpOTConsentData = new JsonObject();
            singUpOTConsentData.addProperty(CommonConstants.URL, signUpOtConsentUrl);
            singUpOTConsentData.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);
            singUpOTConsentData.add(CommonConstants.DATA_KEY, dataPayload);
            
            SignUpConfig signUpConfigObj = new SignUpConfig(

                    CommonHelper.getSignInUrl(bdbApiEndpointService, externalizerService, resourceResolver,currentPage),

                    bdbApiEndpointService.getCaptchaClientKey(),

                    getCountriesPayload, postRegisterPayload, singUpOTConsentData);
            signUpConfigObj.setEmailValidationPayload(emailValidationPayload);
            
            Gson config = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                    .addSerializationExclusionStrategy(excludeUtilObject).create();

            signUpConfig = config.toJson(signUpConfigObj);

            String errorLabelsDataPage = CommonHelper.getErrorPagePath(currentPage);
            if (StringUtils.isEmpty(errorLabelsDataPage)
                    || errorLabelsDataPage != null) {
                // To get the passwordRuleLabels:
                GlobalErrorMessagesModel errorModel = GlobalErrorMessagesModelUtil.getErrorMessageModel(
                        errorLabelsDataPage, resourceResolver,
                        PASS_RULES_RESOURCE_TYPE);

                Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                        .addSerializationExclusionStrategy(excludeUtilObject).create();
                if (null != errorModel) {

                    PasswordRuleLabels passwordRuleLabelsObject = new PasswordRuleLabels(errorModel.getRulesHeading(), errorModel.getLengthRuleLabel(),
                            errorModel.getCaseRuleLabel(), errorModel.getNumericRuleLabel(),
                            errorModel.getSymbolRuleLabel(), errorModel.getNoSpacesLabel());

                    passwordRuleLabels = gson.toJson(passwordRuleLabelsObject);
                }
            }
        }
    }

    /**
     * Creates and returns an instance of IndustryRoleInterestModelConfig from the
     * Area of Focus Model.
     *
     * @param aofModel - Area of Field Model
     * @return - Instance of IndustryRoleInterestModelConfig
     */
    public IndustryRoleInterestModelConfig createIRIModelConfig(AreaOfFocusModel aofModel) {

        IndustryRoleInterestModelConfig iriLabelConfig = new IndustryRoleInterestModelConfig(
                aofModel.getIndustryTitle(), aofModel.getIndustryDescription(), aofModel.getIndustrySelectionText(),
                aofModel.getRoleTitle(), aofModel.getRoleDescription(), aofModel.getRoleSelectionText(),
                aofModel.getInterestTitle(), aofModel.getInterestDescription(), aofModel.getInterestSelectionText(),
                aofModel.getNextIndustryButtonLabel(), aofModel.getSkipIndustryLinkLabel(),
                aofModel.getBackRoleButtonLabel(), aofModel.getNextRoleButtonLabel(),
                aofModel.getBackInterestButtonLabel(), aofModel.getNextInterestButtonLabel(),
                aofModel.getIndustryList());
        return iriLabelConfig;
    }

    /**
     * Gets the area of focus config.
     *
     * @param pagePath         - The path to the data page
     * @param resourceResolver - Resource resolver object
     * @param resourceType     - Resource type
     * @return - Area of Focus Labels
     */
    public String getAreaOfFocusLabel(String pagePath, ResourceResolver resourceResolver, String resourceType) {

        String aoFLabels = StringUtils.EMPTY;

        AreaOfFocusModel aofModel = AreaOfFocusUtil.getAreaOfFocusModel(pagePath, resourceResolver, resourceType);
        if (aofModel != null) {
            log.debug("AOF Model is Not NULL");

            ExcludeUtil excludeUtilObject = new ExcludeUtil();

            IndustryRoleInterestModelConfig iRIModelConfig = createIRIModelConfig(aofModel);
            Gson iriJson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                    .addSerializationExclusionStrategy(excludeUtilObject).create();
            if (iRIModelConfig != null) {
                aoFLabels = iriJson.toJson(iRIModelConfig);
            }
        } else {
            log.debug("AOF Model is NULL");
        }
        return aoFLabels;
    }

    /**
     * Creates and returns an instance of SignUpLabelConfig.
     *
     * @return - Instance of SignUpLabelConfig
     */
    private SignUpLabelConfig createSignUpLabelConfig() {
        SignUpLabelConfig signUpLabelConfig = new SignUpLabelConfig(title, description, fieldsRequired,
                firstNamePlaceholder, firstNameError, lastNamePlaceholder, lastNameError, companyInstitutionPlaceholder,
                companyError, mobileLabel, phoneNumberPlaceholderText, phoneNumberValidationText, phoneNumPatternError,
                emailPlaceholderText, emailToolTip, emailValidationText, emailPatternError, countryLabel, countryError,
                passwordPlaceholderText, passwordError, passwordFLNameError, passwordPatternError,
                confirmPasswordPlaceholderText, confpasswordError, confpasswordNotMatchError, offersCheckboxText,
                updatesCheckboxText, ageCheckboxText, tncNote, agreeSubmitCtaLabel, existingUserLabel, signInLabel, existingUserModalTitle, existingUserModalDescription, existingUserModalCTA);
        signUpLabelConfig.setDomainError(domainError);
        return signUpLabelConfig;
    }

    /**
     * Creates and returns an instance of ConfirmationModelConfig.
     *
     * @return - Instance of ConfirmationModelConfig
     */
    private ConfirmationModelConfig createConfirmationModelConfig() {
        ConfirmationModelConfig confirmationLabelConfig = new ConfirmationModelConfig(
                image,
                imageAltText,
                confirmationTitle,
                content,
                accessText,
                linksTitle,
                accessLinks,
                purchasingAccountLabel,
                skipAndBrowseLabel,
                UrlHandler.getModifiedUrl(skipAndBrowseUrl, resourceResolver));
        return confirmationLabelConfig;
    }


    /**
     * Gets the area focus configuration json.
     *
     * @param excludeUtilObject the exclude util object
     * @return the area focus configuration json
     */
    private String getAreaFocusConfigurationJson(ExcludeUtil excludeUtilObject){

        String signUpPreferenceEndpoint =
                CommonHelper.getEndPointUrlWithoutSiteId(bdbApiEndpointService,
                        bdbApiEndpointService.getHybrisSignUpPreferenceEndpoint().trim(),
                        true,
                        currentPage);
        
        Payload areaFocusPayload = new Payload(signUpPreferenceEndpoint, HttpConstants.METHOD_POST);
        Gson areaFocusGson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                .addSerializationExclusionStrategy(excludeUtilObject).create();

            PayloadConfig areaFocusConfigModel = new PayloadConfig(areaFocusPayload);
               areaFocusConfig = areaFocusGson.toJson(areaFocusConfigModel);

        return areaFocusConfig;
    }

    /**
     * Populate access links.
     */
    private void populateAccessLinks(){

        if (linksMultifield != null && !linksMultifield.isEmpty()) {

            for (Resource resource : linksMultifield) {
                AccessLinksModel accessLink = resource.adaptTo(AccessLinksModel.class);
                accessLinks.add(accessLink);
            }
        }
    }

    /**
     * Gets the Confirmation Labels.
     *
     * @return the confirmation labels
     */
    @Override
    public String getConfirmationLabels() {
        return confirmationLabels;
    }

    /**
     * Get the Sign Up Configuration Labels.
     *
     * @return the sign up labels
     */
    @Override
    public String getSignUpLabels() {
        return signUpLabels;
    }

    /**
     * Get the Industry Role Interest Labels.
     *
     * @return - Industry Role Interest Labels as a String
     */
    public String getIriLabels() {
        return iriLabels;
    }

    /**
     * Gets the password rule labels.
     *
     * @return the password rule labels
     */
    public String getPasswordRuleLabels() {
        return passwordRuleLabels;
    }
    
    /**
     * Get the sign Up Config.
     *
     * @return - sign Up Config as a String
     */
    public String getSignUpConfig() {

        return signUpConfig;
    }

    /**
     * Gets the aof page path.
     *
     * @return the aof page path
     */
    public String getAofPagePath() {
        return aofPagePath;
    }
    
    /**
     * Sets aof page path.
     *
     * @param aofPagePath the aof page path
     */
    public void setAofPagePath(String aofPagePath) {
		this.aofPagePath = aofPagePath;
	}

    /**
     * Gets the hybris site id.
     *
     * @return the hybris site id
     */
    public String getHybrisSiteId() {
        return hybrisSiteId;
    }
    /**
     * Gets password rule labels obj.
     *
     * @return the password rule labels obj
     */
    public PasswordRuleLabels getPasswordRuleLabelsObj() {
        return passwordRuleLabelsObj;
    }
    
    /**
     * Sets password rule labels obj.
     *
     * @param passwordRuleLabelsObj the password rule labels obj
     */
    public void setPasswordRuleLabelsObj(PasswordRuleLabels passwordRuleLabelsObj) {
        this.passwordRuleLabelsObj = passwordRuleLabelsObj;
    }
}
