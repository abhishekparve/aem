package com.bdb.aem.core.models.impl;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.OrderInquiryModel;
import com.bdb.aem.core.pojo.OrderInquiryConfig;
import com.bdb.aem.core.pojo.Payload;
import com.bdb.aem.core.pojo.PayloadConfig;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.ExcludeUtil;
import com.bdb.aem.core.util.UrlHandler;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

@Model( adaptables = {SlingHttpServletRequest.class, Resource.class },
        adapters = { OrderInquiryModel.class },
        resourceType = { OrderInquiryModelImpl.RESOURCE_TYPE },
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class OrderInquiryModelImpl implements OrderInquiryModel {

    /** The Constant RESOURCE_TYPE. */
    protected static final String RESOURCE_TYPE = "bdb-aem/components/content/orderinquiry/v1/orderinquiry";

    /**
     * The Constant log.
     */
    protected static final Logger logger = LoggerFactory.getLogger(OrderInquiryModelImpl.class);

    /**
     * The current page.
     */
    @Inject
    private Page currentPage;

    /**
     * The resource resolver.
     */
    @Inject
    private ResourceResolver resourceResolver;

    /**

    /**
     * The contactUsLabel .
     */
    @Inject
    @Via("resource")
    @SerializedName("contactUsLabel")
    private String contactUsLabel;

    /**
     * The areaOfInquiryLabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("areaOfInquiryLabel")
    private String areaOfInquiryLabel;

    /**
     * The mandatoryErrorLabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("mandatoryErrorLabel")
    private String mandatoryErrorLabel;

    /**
     * The phoneNumPatternError.
     */
    @Inject
    @Via("resource")
    @SerializedName("phoneNumPatternError")
    private String phoneNumPatternError;

    /**
     * The commentsLabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("commentsLabel")
    private String commentsLabel;

    /**
     * The inquiryNote.
     */
    @Inject
    @Via("resource")
    @SerializedName("inquiryNote")
    private String inquiryNote;

    /**
     * The submitBtnLabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("submitBtnLabel")
    private String submitBtnLabel;

    /**
     * The cancelBtnLabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("cancelBtnLabel")
    private String cancelBtnLabel;

    /**
     * The tncNote.
     */
    @Inject
    @Via("resource")
    @SerializedName("tncNote")
    private String tncNote;

    /**
     * The imagePath.
     */
    @Inject
    @Via("resource")
    @SerializedName("imagePath")
    private String imagePath;

    /**
     * The imageAltText.
     */
    @Inject
    @Via("resource")
    @SerializedName("imageAltText")
    private String imageAltText;

    /**
     * The successTitle.
     */
    @Inject
    @Via("resource")
    @SerializedName("successTitle")
    private String successTitle;

    /**
     * The successSubTitle.
     */
    @Inject
    @Via("resource")
    @SerializedName("successSubTitle")
    private String successSubTitle;

    /**
     * The askQuestionCTALabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("askQuestionCTALabel")
    private String askQuestionCTALabel;

    /**
     * The askQuestionCTALink.
     */
    @Inject
    @Via("resource")
    @SerializedName("askQuestionCTALink")
    private String askQuestionCTALink;

    /**
     * The backToOrderCTALabel.
     */
    @Inject
    @Via("resource")
    @SerializedName("backToOrderCTALabel")
    private String backToOrderCTALabel;
    
    @Inject
    @Via("resource")
    @SerializedName("invoiceNumberLabel")
    private String invoiceNumberLabel;
    
    /** The email format error. */
    @Inject
    @Via("resource")
    @SerializedName("emailFormatError")
    private String emailFormatError;

    private String orderInquiryLabels;

    private String orderInquiryConfig;


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
     * the init method
     */
    @PostConstruct
    protected void init() {
        logger.debug("Inside Order Inquiry Model Impl Init Method");

        //Set the Hybris Site Id
        hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);

        orderInquiryLabels = setOrderInquiryLabels();

        //Set the configs
        if (bdbApiEndpointService != null) {
            ExcludeUtil excludeUtilObject = new ExcludeUtil();
            orderInquiryConfig = setOrderInquiryConfig(excludeUtilObject);
        }

    }

    private String setOrderInquiryLabels(){
        JsonObject orderInquiryLabelsJson = new JsonObject();
        orderInquiryLabelsJson.addProperty(CommonConstants.CONTACT_US_LABEL_STRING, contactUsLabel);
        orderInquiryLabelsJson.addProperty(CommonConstants.PO_NUMBER_LABEL_TEXT, CommonHelper.getLabel(CommonConstants.PO_NUMBER_LABEL,currentPage));
        orderInquiryLabelsJson.addProperty(CommonConstants.ORDER_NUMBER_LABEL_TEXT, CommonHelper.getLabel(CommonConstants.ORDER_NUMBER_LABEL,currentPage));
        orderInquiryLabelsJson.addProperty(CommonConstants.SHIP_TO_LBL, CommonHelper.getLabel(CommonConstants.SHIP_TO_LABEL,currentPage));
        orderInquiryLabelsJson.addProperty(CommonConstants.SOLD_TO_LBL, CommonHelper.getLabel(CommonConstants.SOLD_TO_LABEL,currentPage));
        orderInquiryLabelsJson.addProperty(CommonConstants.SHIP_TO_NUMBER_LBL, CommonHelper.getLabel(CommonConstants.SHIP_TO_NUMBER_LABEL,currentPage));
        orderInquiryLabelsJson.addProperty(CommonConstants.SOLD_TO_NUMBER,  CommonHelper.getLabel(CommonConstants.SOLD_TO_NUMBER_LABEL,currentPage));
        orderInquiryLabelsJson.addProperty(CommonConstants.FIRST_NAME_LABEL_TEXT,  CommonHelper.getLabel(CommonConstants.FIRST_NAME_LABEL,currentPage));
        orderInquiryLabelsJson.addProperty(CommonConstants.JOB_TITLE_LABEL,  CommonHelper.getLabel(CommonConstants.JOB_TITLE_LABEL,currentPage));
        orderInquiryLabelsJson.addProperty(CommonConstants.LAST_NAME_LABEL_TEXT,  CommonHelper.getLabel(CommonConstants.LAST_NAME_LABEL,currentPage));
        orderInquiryLabelsJson.addProperty(CommonConstants.INSTITUTION_LABEL, CommonHelper.getLabel(CommonConstants.INSTITUTION_LABEL,currentPage));
        orderInquiryLabelsJson.addProperty(CommonConstants.PHONE_LABEL_TEXT, CommonHelper.getLabel(CommonConstants.PHONE_LABEL,currentPage));
        orderInquiryLabelsJson.addProperty(CommonConstants.EMAIL_LABEL, CommonHelper.getLabel(CommonConstants.EMAIL,currentPage));
        orderInquiryLabelsJson.addProperty(CommonConstants.AREA_OF_INQUIRY_LABELS, areaOfInquiryLabel);
        orderInquiryLabelsJson.addProperty(CommonConstants.MANDATORY_ERROR_LABELS, mandatoryErrorLabel);
        orderInquiryLabelsJson.addProperty(CommonConstants.PHONE_NUMBER_PATTERN_ERROR, phoneNumPatternError);
        orderInquiryLabelsJson.addProperty(CommonConstants.COMMENTS_LABEL, commentsLabel);
        orderInquiryLabelsJson.addProperty(CommonConstants.INQUIRY_NOTE, inquiryNote);
        orderInquiryLabelsJson.addProperty(CommonConstants.TNC_NOTE, tncNote);
        orderInquiryLabelsJson.addProperty(CommonConstants.SUBMIT_BTN, submitBtnLabel);
        orderInquiryLabelsJson.addProperty(CommonConstants.CANCEL_BTN_LABEL, cancelBtnLabel);
        orderInquiryLabelsJson.addProperty(CommonConstants.IMAGE_JSON_KEY, imagePath);
        orderInquiryLabelsJson.addProperty(CommonConstants.IMAGE_ALT_TEXT, imageAltText);
        orderInquiryLabelsJson.addProperty(CommonConstants.SUCCESS_TITLE, successTitle);
        orderInquiryLabelsJson.addProperty(CommonConstants.SUCCESS_SUB_TITLE, successSubTitle);
        orderInquiryLabelsJson.addProperty(CommonConstants.ASK_QUESTION_CTA_LABEL, askQuestionCTALabel);
        orderInquiryLabelsJson.addProperty(CommonConstants.ASK_QUESTION_CTA_LINK, UrlHandler.getModifiedUrl(askQuestionCTALink,resourceResolver));
        orderInquiryLabelsJson.addProperty(CommonConstants.BACK_TO_ORDER_CTA_LABEL, backToOrderCTALabel);
        orderInquiryLabelsJson.addProperty(CommonConstants.INVOICE_NUMBER_LABEL, invoiceNumberLabel);
        orderInquiryLabelsJson.addProperty(CommonConstants.EMAIL_FORMAT_ERROR_LABEL, emailFormatError);

        return orderInquiryLabelsJson.toString();

    }

    private String setOrderInquiryConfig(ExcludeUtil excludeUtilObject) {
        //set get the order inquiry detail
        String getOrderInquiryDetailEndpoint = StringUtils.replace(
                bdbApiEndpointService.getGetOrderInquiryDetailEndpoint(),
                CommonConstants.HYBRIS_SITE_LITERAL,
                hybrisSiteId);
        Payload getOrderInquiryDetailRequestPayload = new Payload(
                bdbApiEndpointService.getBDBHybrisDomain() + getOrderInquiryDetailEndpoint,
                HttpConstants.METHOD_GET);
        PayloadConfig getOrderInquiryDetailConfig = new PayloadConfig(getOrderInquiryDetailRequestPayload);

        // set post Order Inquiry Details endpoint
        String postOrderInquiryDetailsEndpoint = StringUtils.replace(
                bdbApiEndpointService.getPostOrderInquiryDetailsEndpoint(),
                CommonConstants.HYBRIS_SITE_LITERAL,
                hybrisSiteId);
        Payload postOrderInquiryDetailsRequestPayload = new Payload(
                bdbApiEndpointService.getBDBHybrisDomain() + postOrderInquiryDetailsEndpoint
                , HttpConstants.METHOD_POST);
        PayloadConfig postOrderInquiryDetailsConfig = new PayloadConfig(postOrderInquiryDetailsRequestPayload);

        // creating the final config
        OrderInquiryConfig orderInquiryConfigObj = new OrderInquiryConfig(getOrderInquiryDetailConfig,
                postOrderInquiryDetailsConfig);
        Gson orderInquiryConfigGson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                .addSerializationExclusionStrategy(excludeUtilObject).create();
        return orderInquiryConfigGson.toJson(orderInquiryConfigObj);
    }


    @Override
    public String getOrderInquiryLabels() {
        return orderInquiryLabels;
    }

    @Override
    public String getOrderInquiryConfig() {
        return orderInquiryConfig;
    }

}
