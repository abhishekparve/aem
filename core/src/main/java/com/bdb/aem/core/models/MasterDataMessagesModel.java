package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.annotations.SerializedName;

/**
 * The Class MasterDataMessagesModel.
 */
@Model(
        adaptables = {Resource.class},
        adapters = {MasterDataMessagesModel.class},
        resourceType = {MasterDataMessagesModel.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MasterDataMessagesModel {
	
	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/masterdata/v1/masterdata";
	
	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(MasterDataMessagesModel.class);
	
	/** The master data messages multi field. */
	@Inject
    @SerializedName("certificationMultiField")
    private List<Resource> certificationMultiField;
    
    /** The order source multi field. */
    @Inject
    @SerializedName("orderSourceMultiField")
    private List<Resource> orderSourceMultiField;
    
    /** The order status multi field. */
    @Inject
    @SerializedName("orderStatusMultiField")
    private List<Resource> orderStatusMultiField;
    
    /** The delivery option multi field. */
    @Inject
    @SerializedName("deliveryOptionsMultiField")
    private List<Resource> deliveryOptionsMultiField;


	/** The quote status multi field. */
	@Inject
	@SerializedName("quoteStatusMultiField")
	private List<Resource> quoteStatusMultiField;

	/** The Order Inquiry Types field. */
	@Inject
	@SerializedName("orderInquiryTypes")
	private List<Resource> orderInquiryTypes;
    
    /** The user dashboard messages multifield. */
    @Inject
    @SerializedName("userDashboardMessagesMultifield")
    private List<Resource> userDashboardMessagesMultifield;
    
    /** The certifications type multi field. */
    @Inject
    @SerializedName("certificationsTypeMultiField")
    private List<Resource> certificationsTypeMultiField;

    /** The order approval promotions multifield. */
    @Inject
    @SerializedName("orderApprovalPromotionsMultifield")
    private List<Resource> orderApprovalPromotionsMultifield;
    
    /** The abandoned cart label. */
    @Inject
    private String abandonedCartLabel;
    
    /** The blog label. */
    @Inject
    private String blogLabel;
    
    /** The news letter label. */
    @Inject
    private String newsLetterLabel;
    
    /** The tutorial label. */
    @Inject
    private String tutorialLabel;
    
    /** The webinar label. */
    @Inject
    private String webinarLabel;

    /** The certification messages. */
    @SerializedName("certificationMessages")
    private List<ApiErrorMessagesModel> certificationMessages = new ArrayList<>();
    
    /** The order source messages. */
    @SerializedName("orderSourceMessages")
    private List<ApiErrorMessagesModel> orderSourceMessages = new ArrayList<>();
    
    /** The order status messages. */
    @SerializedName("orderStatusMessages")
    private List<ApiErrorMessagesModel> orderStatusMessages = new ArrayList<>();
    
    /** The delivery option messages. */
    @SerializedName("deliverOptionMessages")
    private List<ApiErrorMessagesModel> deliverOptionMessages = new ArrayList<>();

	/** The quote status messages. */
	@SerializedName("quoteStatusMessages")
	private List<ApiErrorMessagesModel> quoteStatusMessages = new ArrayList<>();

	/** The order inquiry types options. */
	@SerializedName("orderInquiryTypesOptions")
	private List<ApiErrorMessagesModel> orderInquiryTypesOptions = new ArrayList<>();
    
    /** The user dashboard messages. */
    @SerializedName("userDashboardMessages")
    private List<ApiErrorMessagesModel> userDashboardMessages = new ArrayList<>();
    
    /** The certifications type. */
    @SerializedName("certificationsType")
    private List<ApiErrorMessagesModel> certificationsType = new ArrayList<>();
    
    /** The promotions code description. */
    @SerializedName("promotionsCodeDescription")
    private List<ApiErrorMessagesModel> promotionsCodeDescription = new ArrayList<>();

	/**
     * Creates and initialize populateCodeAndMessages.
     */
    @PostConstruct
    protected void init() {
    	log.debug("Inside Master Data Model Init");
        certificationMessages = populateCodeAndMessages(certificationMultiField, certificationMessages);
        orderSourceMessages = populateCodeAndMessages(orderSourceMultiField, orderSourceMessages);
        orderStatusMessages = populateCodeAndMessages(orderStatusMultiField, orderStatusMessages);
        deliverOptionMessages =  populateCodeAndMessages(deliveryOptionsMultiField, deliverOptionMessages);
		quoteStatusMessages = populateCodeAndMessages(quoteStatusMultiField, quoteStatusMessages);
		orderInquiryTypesOptions = populateCodeAndMessages(orderInquiryTypes, orderInquiryTypesOptions);
        userDashboardMessages = populateCodeAndMessages(userDashboardMessagesMultifield, userDashboardMessages);
        certificationsType = populateCodeAndMessages(certificationsTypeMultiField, certificationsType);
        promotionsCodeDescription = populateCodeAndMessages(orderApprovalPromotionsMultifield, promotionsCodeDescription);
    }

    /**
     * Populate code and messages.
     *
     * @param resouceMultiField the resouce multi field
     * @param resourceMessages the resource messages
     * @return the list
     */
    private List<ApiErrorMessagesModel> populateCodeAndMessages(List<Resource> resouceMultiField, List<ApiErrorMessagesModel> resourceMessages) {
    	if (resouceMultiField != null) {
            for (Resource resource : resouceMultiField) {
                ApiErrorMessagesModel message = resource.adaptTo(ApiErrorMessagesModel.class);
                if(message.getErrorCode() != null && message.getErrorMessage() != null) {
                	resourceMessages.add(message);
                }
            }            
        }
		return resourceMessages;
	}
    
	/**
	 * Gets the certification messages.
	 *
	 * @return the certification messages
	 */
	public List<ApiErrorMessagesModel> getCertificationMessages() {
		return new ArrayList<>(certificationMessages);
	}

	/**
	 * Gets the order source messages.
	 *
	 * @return the order source messages
	 */
	public List<ApiErrorMessagesModel> getOrderSourceMessages() {
		return new ArrayList<>(orderSourceMessages);
	}

	/**
	 * Gets the order status messages.
	 *
	 * @return the order status messages
	 */
	public List<ApiErrorMessagesModel> getOrderStatusMessages() {
		return new ArrayList<>(orderStatusMessages);
	}

	/**
	 * Gets the order status messages.
	 *
	 * @return the order status messages
	 */
	public List<ApiErrorMessagesModel> getDeliverOptionMessages() {
		return new ArrayList<>(deliverOptionMessages);
	}
	
	/**
	 * Gets the order Inquiry Types Options
	 *
	 * @return the order Inquiry Types Options
	 */
	public List<ApiErrorMessagesModel> getOrderInquiryTypesOptions() {
		return new ArrayList<>(orderInquiryTypesOptions);
	}

	/**
	 * Gets the quote status messages.
	 *
	 * @return the quote status messages
	 */
	public List<ApiErrorMessagesModel> getQuoteStatusMessages() {
		return new ArrayList<>(quoteStatusMessages);
	}
	
    /**
     * Gets the user dashboard messages.
     *
     * @return the user dashboard messages
     */
    public List<ApiErrorMessagesModel> getUserDashboardMessages() {
		return new ArrayList<>(userDashboardMessages);
	}

	/**
	 * Gets the certifications type.
	 *
	 * @return the certifications type
	 */
	public List<ApiErrorMessagesModel> getCertificationsType() {
		return new ArrayList<>(certificationsType);
	}

	/**
	 * Gets the promotions code description.
	 *
	 * @return the promotions code description
	 */
	public List<ApiErrorMessagesModel> getPromotionsCodeDescription() {
		return new ArrayList<>(promotionsCodeDescription);
	}
	
	/**
	 * Gets the abandoned cart label.
	 *
	 * @return the abandoned cart label
	 */
	public String getAbandonedCartLabel() {
		return abandonedCartLabel;
	}

	/**
	 * Gets the blog label.
	 *
	 * @return the blog label
	 */
	public String getBlogLabel() {
		return blogLabel;
	}

	/**
	 * Gets the news letter label.
	 *
	 * @return the news letter label
	 */
	public String getNewsLetterLabel() {
		return newsLetterLabel;
	}

	/**
	 * Gets the tutorial label.
	 *
	 * @return the tutorial label
	 */
	public String getTutorialLabel() {
		return tutorialLabel;
	}

	/**
	 * Gets the webinar label.
	 *
	 * @return the webinar label
	 */
	public String getWebinarLabel() {
		return webinarLabel;
	}
}
