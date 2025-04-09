package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.models.AccMgmtPaymentMethodsModel;
import com.bdb.aem.core.models.CCList;
import com.bdb.aem.core.pojo.Payload;
import com.bdb.aem.core.pojo.PayloadConfig;
import com.bdb.aem.core.pojo.PaymentsConfig;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.ExcludeUtil;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of interface AccMgmtPaymentMethodsModel serving as Sling model
 * class for PaymentMethods dialog in accountmanagement component
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class}, adapters = {
        AccMgmtPaymentMethodsModel.class}, resourceType = {
        AccMgmtPaymentMethodsModelImpl.RESOURCE_TYPE}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class AccMgmtPaymentMethodsModelImpl implements AccMgmtPaymentMethodsModel {

    /**
     * The Constant log.
     */
    protected static final Logger loggerAccMgmtPaymentMethods = LoggerFactory.getLogger(AccMgmtPaymentMethodsModelImpl.class);

    /**
     * The Constant RESOURCE_TYPE.
     */
    protected static final String RESOURCE_TYPE = "bdb-aem/components/content/accountmanagement/v1/accountmanagement";

    /**
     * Payment Methods variable holding all the fields
     */
    private String paymentsMethodsLabels;

    /**
     * Payment Methods variable holding all the configs
     */
    private String paymentsMethodsConfig;

    /**
     * The Hybris Site ID
     */
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
     * The Payment Methods header.
     */
    @Inject
    @Via("resource")
    @SerializedName("paymentMethodsHeader")
    private String paymentMethodsHeader;

    /**
     * The You have not added a payment method. Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("youHaveNotAddedAPaymentMethodText")
    private String youHaveNotAddedAPaymentMethodText;


    /**
     * The No Payment Method Instruction
     */
    @Inject
    @Via("resource")
    @SerializedName("noPaymentMethodInstruction")
    private String noPaymentMethodInstruction;

    /**
     * The You have not added a payment method creditCardsSubTitle.
     */
    @Inject
    @Via("resource")
    @SerializedName("creditCardsSubTitle")
    private String creditCardsSubTitle;

    /**
     * The Add Credit Card CTA Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("addCreditCardCtaLabel")
    private String addCreditCardCtaLabel;

    /**
     * Download Credit Info.
     */
    @Inject
    @Via("resource")
    @SerializedName("downloadCreditInfo")
    private String downloadCreditInfo;
    
    /**
     * The Download Credit Url.
     */
    @Inject
    @Via("resource")
    @SerializedName("downloadCreditUrl")
    private String downloadCreditUrl;


    /**
     * The Download Credit CTA.
     */
    @Inject
    @Via("resource")
    @SerializedName("downloadCreditCTA")
    private String downloadCreditCTA;
    
    
    /**
    * The Download Credit Enabled.
    */
   @Inject
   @Via("resource")
   @SerializedName("downloadCreditEnabled")
   private String downloadCreditEnabled;

    

    
    /**
     * The Add Credit Card CTA Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("creditApplicationInfo")
    private String creditApplicationInfo;

    /**
     * The Add Credit Card Modal Header Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("addCreditCardModalHeader")
    private String addCreditCardModalHeader;

    /**
     * The Credit card Number Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("creditCardNumberLabel")
    private String creditCardNumberLabel;

    /**
     * The Name Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("acceptInfoLabel")
    private String acceptInfoLabel;

    /**
     * The Expiry Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("expiryLabel")
    private String expiryLabel;

    /**
     * The CVV Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("cvvLabel")
    private String cvvLabel;

    /**
     * The Card Nickname Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("cardNicknameLabel")
    private String cardNicknameLabel;

    /**
     * The Please enter a 16 digit card number Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("sixteenDigitCardNumberText")
    private String sixteenDigitCardNumberText;

    /**
     * The The credit card provided was not approved Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("creditCardNotApprovedText")
    private String creditCardNotApprovedText;

    /**
     * The Your credit card has been added successfully Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("creditCardAddedText")
    private String creditCardAddedText;

    /**
     * The Enter Nickname placeholder Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("enterNicknamePlaceholderText")
    private String enterNicknamePlaceholderText;

    /**
     * The Save Nick Name link Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("saveNickNameLinkLabel")
    private String saveNickNameLinkLabel;

    /**
     * The Add Nick Name link Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("addNickNameLinkLabel")
    private String addNickNameLinkLabel;

    /**
     * The Edit Nick Name link Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("editNickNameLinkLabel")
    private String editNickNameLinkLabel;

    /**
     * The Name on Card Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("nameOnCardLabel")
    private String nameOnCardLabel;

    /**
     * The Expiry Date Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("expiryDateLabel")
    private String expiryDateLabel;

    /**
     * The Are you sure you want to set this as your default credit card Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("areYouSureYouWantToSetDefaultText")
    private String areYouSureYouWantToSetDefaultText;

    /**
     * The Remove Credit Card Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("removeCreditCardText")
    private String removeCreditCardText;

    /**
     * The Are you sure you want to remove this payment method Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("areYouSureYouWantToRemoveText")
    private String areYouSureYouWantToRemoveText;

    /**
     * The Your card has expired Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("yourCardHasExpiredText")
    private String yourCardHasExpiredText;

    /**
     * The Your card will expire in 3 days Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("expireInThreeDaysText")
    private String expireInThreeDaysText;

    /**
     * The Credit Cards Header Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("creditCardsHeaderText")
    private String creditCardsHeaderText;

    /**
     * The Credit Card Number filter Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("creditCardFilterLabel")
    private String creditCardFilterLabel;

    /**
     * The Carrier Number Filter Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("carrierFilterLabel")
    private String carrierFilterLabel;

    /**
     * The Your account is currently able to prepay for purchases Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("currentlyAbleToPrepayText")
    private String currentlyAbleToPrepayText;

    /**
     * The Carrier Number header Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("carrierNumberHeaderText")
    private String carrierNumberHeaderText;

    /**
     * The If you already have a carrier text Text.
     */
    @Inject
    @Via("resource")
    @SerializedName("ifYouAlreadyHaveACarrierText")
    private String ifYouAlreadyHaveACarrierText;

    /**
     * The Carrier Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("carrierLabel")
    private String carrierLabel;

    /**
     * The Account Number Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("accountNumberLabel")
    private String accountNumberLabel;

    /**
     * The Add Carrier Number modal header Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("addCarrierNumberLabel")
    private String addCarrierNumberLabel;

    /**
     * The Select Carrier Modal Placeholder Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("selectCarrierPlaceholderLabel")
    private String selectCarrierPlaceholderLabel;

    /**
     * The Enter Carrier Account Number Modal placeholder Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("enterCarrierAccountLabel")
    private String enterCarrierAccountLabel;

    /**
     * The Add Nickname modal placeholder Label.
     */
    @Inject
    @Via("resource")
    @SerializedName("addNicknamePlaceholderLabel")
    private String addNicknamePlaceholderLabel;

    /**
     * The phone num pattern error.
     */
    @Inject
    @Via("resource")
    private List<Resource> acceptedCCListMultiField;

    /**
     * The acceptedCCList.
     */
    @SerializedName("acceptedCCList")
    private List<CCList> acceptedCCList = new ArrayList<>();


    /**
     * Populates the dashboardPageNavigationLabels.
     */
    @PostConstruct
    protected void init() {
        loggerAccMgmtPaymentMethods.debug("Inside Account Management Payment Methods Init Method");
        ExcludeUtil excludeUtilObject = new ExcludeUtil();
        Gson paymentsMethodConfigGson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                .addSerializationExclusionStrategy(excludeUtilObject).create();

        //Set the Hybris Site Id
        hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
        populateCCList();

        paymentsMethodsLabels = setPaymentsMethodsLabels();
        paymentsMethodsConfig = setPaymentsMethodsConfig(paymentsMethodConfigGson);
    }

    private String setPaymentsMethodsConfig(Gson paymentsMethodConfigGson) {

        if (null != bdbApiEndpointService) {

            //set the getPayments
            String getPaymentsEndpoint = StringUtils.replace(
                    bdbApiEndpointService.getGetPaymentsEndpoint(),
                    CommonConstants.HYBRIS_SITE_LITERAL,
                    hybrisSiteId);
            Payload getPaymentsPayload = new Payload(
                    bdbApiEndpointService.getBDBHybrisDomain() + getPaymentsEndpoint,
                    HttpConstants.METHOD_GET);
            PayloadConfig getPayments = new PayloadConfig(getPaymentsPayload);

            //set the addCreditCard
            String addCreditCardEndpoint = StringUtils.replace(
                    bdbApiEndpointService.getAddCreditCardEndpoint(),
                    CommonConstants.HYBRIS_SITE_LITERAL,
                    hybrisSiteId);
            Payload addCreditCardPayload = new Payload(
                    bdbApiEndpointService.getBDBHybrisDomain() + addCreditCardEndpoint,
                    HttpConstants.METHOD_POST);
            PayloadConfig addCreditCard = new PayloadConfig(addCreditCardPayload);

            //set the removeCreditCard
            String removeCreditCardEndpoint = StringUtils.replace(
                    bdbApiEndpointService.getRemoveCreditCardEndpoint(),
                    CommonConstants.HYBRIS_SITE_LITERAL,
                    hybrisSiteId);
            Payload removeCreditCardPayload = new Payload(
                    bdbApiEndpointService.getBDBHybrisDomain() + removeCreditCardEndpoint,
                    HttpConstants.METHOD_DELETE);
            PayloadConfig removeCreditCard = new PayloadConfig(removeCreditCardPayload);

            //set the updateCreditCardEndpoint
            String updateCreditCardEndpoint = StringUtils.replace(
                    bdbApiEndpointService.getUpdateCreditCardEndpoint(),
                    CommonConstants.HYBRIS_SITE_LITERAL,
                    hybrisSiteId);
            Payload updateCreditCardPayload = new Payload(
                    bdbApiEndpointService.getBDBHybrisDomain() + updateCreditCardEndpoint,
                    HttpConstants.METHOD_POST);
            PayloadConfig updateCreditCard = new PayloadConfig(updateCreditCardPayload);
            
            //Paymetric Token
            String paymetricTokenEndpoint = StringUtils.replace(
            		bdbApiEndpointService.paymetricTokenEndpoint(),
            		CommonConstants.BASE_SITE_ID,
            		hybrisSiteId);
            Payload paymetricTokenPayload = new Payload(
            		bdbApiEndpointService.getBDBHybrisDomain() + paymetricTokenEndpoint,
            		HttpConstants.METHOD_GET);
            PayloadConfig paymetricToken = new PayloadConfig(paymetricTokenPayload);
            
            //Paymetric URL            
            String paymetricUrl = bdbApiEndpointService.paymetricDomain() + bdbApiEndpointService.paymetricIframeEndpoint();
            		


            PaymentsConfig paymentsConfig = new PaymentsConfig(
            		getPayments,
                    addCreditCard,
                    removeCreditCard,
                    updateCreditCard,
                    paymetricToken,
                    paymetricUrl);

            return paymentsMethodConfigGson.toJson(paymentsConfig);
        }
        return StringUtils.EMPTY;
    }

    private String setPaymentsMethodsLabels() {
        JsonObject paymentsLabels = new JsonObject();

        paymentsLabels.addProperty(CommonConstants.TITLE_LABEL, paymentMethodsHeader);
        paymentsLabels.addProperty("creditCardCTA", creditCardFilterLabel);
        paymentsLabels.addProperty("carrierCTA", carrierFilterLabel);
        paymentsLabels.addProperty("nopaymentMethodLabels", youHaveNotAddedAPaymentMethodText);
        paymentsLabels.addProperty("noPaymentMethodInstruction", noPaymentMethodInstruction);
        paymentsLabels.addProperty("creditCardsSubTitle", creditCardsSubTitle);
        paymentsLabels.addProperty("addCreditCardCTA", addCreditCardCtaLabel);
        paymentsLabels.addProperty("downloadCreditInfo", downloadCreditInfo);
        paymentsLabels.addProperty("downloadCreditUrl", downloadCreditUrl);
        paymentsLabels.addProperty("downloadCreditCTA", downloadCreditCTA);
        paymentsLabels.addProperty("downloadCreditEnabled", downloadCreditEnabled);
        paymentsLabels.addProperty("creditApplicationInfo", creditApplicationInfo);
        paymentsLabels.addProperty("defaultLabel", CommonHelper.getLabel(CommonConstants.DEFAULT_LABEL, currentPage));
        paymentsLabels.addProperty("addNickName", addNickNameLinkLabel);
        paymentsLabels.addProperty("saveNickName", saveNickNameLinkLabel);
        paymentsLabels.addProperty("enterNickName", enterNicknamePlaceholderText);
        paymentsLabels.addProperty("removeLabel", CommonHelper.getLabel(CommonConstants.REMOVE_LABEL, currentPage));
        paymentsLabels.addProperty("expiryDate", expiryLabel);
        paymentsLabels.addProperty("editNickName",editNickNameLinkLabel);
        paymentsLabels.addProperty("nameOnCard", nameOnCardLabel);
        paymentsLabels.addProperty("setAsDefault", CommonHelper.getLabel(CommonConstants.SET_AS_DEFAULT_LABEL, currentPage));
        paymentsLabels.addProperty("endingIn", CommonHelper.getLabel(CommonConstants.ENDING_IN_LABEL, currentPage));

        JsonObject setAsDefaultConfirm = new JsonObject();

        setAsDefaultConfirm.addProperty(CommonConstants.TITLE_LABEL, CommonHelper.getLabel(CommonConstants.SET_AS_DEFAULT_LABEL, currentPage));
        setAsDefaultConfirm.addProperty("body", areYouSureYouWantToSetDefaultText);
        setAsDefaultConfirm.addProperty(CommonConstants.CANCEL_CTA, CommonHelper.getLabel(CommonConstants.CANCEL_LABEL, currentPage));
        setAsDefaultConfirm.addProperty(CommonConstants.CONFIRM_CTA, CommonHelper.getLabel(CommonConstants.CONFIRM_LABEL, currentPage));

        paymentsLabels.add("setAsDefaultConfirm", setAsDefaultConfirm);

        JsonObject removeCreditcardConfirm = new JsonObject();

        removeCreditcardConfirm.addProperty(CommonConstants.TITLE_LABEL, removeCreditCardText);
        removeCreditcardConfirm.addProperty("body", areYouSureYouWantToRemoveText);
        removeCreditcardConfirm.addProperty(CommonConstants.CANCEL_CTA, CommonHelper.getLabel(CommonConstants.CANCEL_LABEL, currentPage));
        removeCreditcardConfirm.addProperty(CommonConstants.CONFIRM_CTA, CommonHelper.getLabel(CommonConstants.CONFIRM_LABEL, currentPage));

        paymentsLabels.add("removeCreditcardConfirm", removeCreditcardConfirm);

        JsonObject addCreditCardModal = new JsonObject();

        addCreditCardModal.addProperty(CommonConstants.TITLE_LABEL, addCreditCardModalHeader);
        addCreditCardModal.addProperty("acceptInfo", acceptInfoLabel);
        addCreditCardModal.addProperty("creditCardNumber", creditCardNumberLabel);
        addCreditCardModal.addProperty("nameOnCard", nameOnCardLabel);
        addCreditCardModal.addProperty("expiryDate", expiryDateLabel);
        addCreditCardModal.addProperty("cvv", cvvLabel);
        addCreditCardModal.addProperty("nickName", cardNicknameLabel);
        addCreditCardModal.addProperty("setDefault", CommonHelper.getLabel(CommonConstants.SET_AS_DEFAULT_LABEL, currentPage));
        addCreditCardModal.addProperty(CommonConstants.CANCEL_CTA, CommonHelper.getLabel(CommonConstants.CANCEL_LABEL, currentPage));
        addCreditCardModal.addProperty(CommonConstants.CONFIRM_CTA, CommonHelper.getLabel(CommonConstants.CONFIRM_LABEL, currentPage));
        addCreditCardModal.addProperty("ccNumberError", sixteenDigitCardNumberText);
        addCreditCardModal.addProperty("creditCardNotApprovedError", creditCardNotApprovedText);


        paymentsLabels.add("addCreditCardModal", addCreditCardModal);

        paymentsLabels.addProperty("successMessage", creditCardAddedText);
        paymentsLabels.addProperty("expirationWarningMessage", expireInThreeDaysText);
        paymentsLabels.addProperty("expiredMessage", yourCardHasExpiredText);

        JsonArray acceptedCClist = new JsonArray();
        for (CCList card :
                acceptedCCList) {
            JsonObject cardObj = new JsonObject();
            cardObj.addProperty("id", card.getCcId());
            cardObj.addProperty("icon", card.getCcIcon());
            cardObj.addProperty("length", card.getCcLength());
            acceptedCClist.add(cardObj);
        }

        paymentsLabels.add("acceptedCClist", acceptedCClist);
        return paymentsLabels.toString();

    }

    private void populateCCList() {
        if (acceptedCCListMultiField != null && !acceptedCCListMultiField.isEmpty()) {

            for (Resource resource : acceptedCCListMultiField) {
                CCList ccList = resource.adaptTo(CCList.class);
                acceptedCCList.add(ccList);
            }
        }
    }

    /**
     * This method returns the labels related to payment Methods menu in account
     * management page
     *
     * @return paymentsMethodsLabels
     */
    @Override
    public String getPaymentsMethodsLabels() {
        return paymentsMethodsLabels;
    }

    /**
     * This method returns the labels related to payment Methods configs in account
     * management page
     *
     * @return paymentsMethodsConfig
     */
    @Override
    public String getPaymentsMethodsConfig() {
        return paymentsMethodsConfig;
    }
}