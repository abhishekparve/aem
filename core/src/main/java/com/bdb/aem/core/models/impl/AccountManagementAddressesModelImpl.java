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
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.bean.AddressLabelsBean;
import com.bdb.aem.core.models.AccountManagementAddressesModel;
import com.bdb.aem.core.models.GlobalErrorMessagesModel;
import com.bdb.aem.core.pojo.AccountManagementAddressesConfig;
import com.bdb.aem.core.pojo.AddressLabelsConfig;
import com.bdb.aem.core.pojo.Payload;
import com.bdb.aem.core.pojo.PayloadConfig;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.ExcludeUtil;
import com.bdb.aem.core.util.GlobalErrorMessagesModelUtil;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The Class AccountManagementAddressesModelImpl.
 */
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class }, 
	   adapters = { AccountManagementAddressesModel.class },
	   resourceType = { AccountManagementAddressesModelImpl.RESOURCE_TYPE },
	   defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AccountManagementAddressesModelImpl implements AccountManagementAddressesModel {
	
	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/accountmanagement/v1/accountmanagement";
	
	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(AccountManagementAddressesModelImpl.class);
	
	/** The Constant PASS_RULES_RESOURCE_TYPE. */
	protected static final String PASS_RULES_RESOURCE_TYPE = "bdb-aem/proxy/components/content/globalerror";

	/** The current page. */
	@Inject
	private Page currentPage;

	/** The request. */
	@Self
	private SlingHttpServletRequest request;
	
	/** The addresses header. */
	@Inject
	@Via("resource")
	@SerializedName("addrHeadingLabel")
	private String addressesHeader;
	
	/** The reactivation Sucess Text. */
	@Inject
	@Via("resource")
	@SerializedName("reactivationSuccessText")
	private String reactivationSuccessText;
	
	/** The add address cta label. */
	@Inject
	@Via("resource")
	@SerializedName("addrAddLabel")
	private String addAddressCtaLabel;
	
	/** The confirm Button Label. */
	@Inject
	@Via("resource")
	@SerializedName("confirmButtonLabel")
	private String confirmButtonLabel;

	/** The addr nodata alt text. */
	@Inject
	@Via("resource")
	@SerializedName("addrNodataAltText")
	private String addrNodataAltText;
	
	/** The manage addresses text. */
	@Inject
	@Via("resource")
	@SerializedName("addrNoData")
	private String manageAddressesText;
	
	/** The manage addresses description text. */
	@Inject
	@Via("resource")
	@SerializedName("addrNoDataInstruction")
	private String manageAddressesDescriptionText;
	
	/** The search place holder label. */
	@Inject
	@Via("resource")
	@SerializedName("addrSearchPlaceHolderLabel")
	private String searchPlaceHolderLabel;
	
	/** The edit nickname label. */
	@Inject
	@Via("resource")
	@SerializedName("addrEditNickLabel")
	private String editNicknameLabel;
	
	/** The save nickname label. */
	@Inject
	@Via("resource")
	@SerializedName("saveNicknameLabel")
	private String saveNicknameLabel;

	/** The add nickname label. */
	@Inject
	@Via("resource")
	@SerializedName("addrAddNickLabel")
	private String addNicknameLabel;
	
	/** The enter nickname label. */
	@Inject
	@Via("resource")
	@SerializedName("enterNickNameLabel")
	private String enterNicknameLabel;
	
	/** The addr all tab label. */
	@Inject
	@Via("resource")
	@SerializedName("addrAllTabLabel")	
	private String addrAllTabLabel;
	
	/** The addr ship tab label. */
	@Inject
	@Via("resource")
	@SerializedName("addrShipTabLabel")	
	private String addrShipTabLabel;
	
	/** The addr bill tab label. */
	@Inject
	@Via("resource")
	@SerializedName("addrBillTabLabel")	
	private String addrBillTabLabel;
		
	/** The default label. */
	@Inject
	@SerializedName("addrDefaultLabel")
	private String defaultLabel;
	
	/** The another default shipping address text. */
	@Inject
	@Via("resource")
	@SerializedName("anotherDefaultShippingAddressText")	
	private String anotherDefaultShippingAddressText;
	
	/** The another default billing address text. */
	@Inject
	@Via("resource")
	@SerializedName("anotherDefaultBillingAddressText")	
	private String anotherDefaultBillingAddressText;
	
	/** The set as default label. */
	@Inject
	@SerializedName("addrMakeDefaultLabel")
	private String setAsDefaultLabel;
	
	/** The default confirmation text. */
	@Inject
	@Via("resource")
	@SerializedName("defaultConfirmationText")	
	private String defaultConfirmationText;
	
	/** The default shipping confirmation text. */
	@Inject
	@Via("resource")
	@SerializedName("defaultShippingConfirmationText")	
	private String defaultShippingConfirmationText;
	
	/** The default billing confirmation text. */
	@Inject
	@Via("resource")
	@SerializedName("defaultBillingConfirmationText")	
	private String defaultBillingConfirmationText;
	
	/** The add to favorites header. */
	@Inject
	@Via("resource")
	@SerializedName("addToFavoritesHeader")	
	private String addToFavoritesHeader;
	
	/** The maximum addresses text. */
	@Inject
	@Via("resource")
	@SerializedName("maximumAddressesText")	
	private String maximumAddressesText;
	
	/** The add address header. */
	@Inject
	@Via("resource")
	@SerializedName("addAddressHeader")	
	private String addAddressHeader;
	
	/** The address details sub head label. */
	@Inject
	@SerializedName("addrDelatilsSubHeadLabel")
	private String addressDetailsSubHeadLabel;
	
	/** The phone number sub head label. */
	@Inject
	@SerializedName("addrPhoneNumSubHeadLabel")
	private String phoneNumberSubHeadLabel;

	/** The vat number sub head label. */
	@Inject
	@SerializedName("addrVatNumSubHeadLabel")
	private String vatNumberSubHeadLabel;
	
	/** The vat number label. */
	@Inject
	@SerializedName("vatNumber")
	private String vatNumber;
	
	/** The vat number user. */
	@Inject
	@SerializedName("vatNumberUser")
	private String vatNumberUser;

	/** The vat number . */
	@Inject
	@SerializedName("vatNumberLabel")
	private String vatNumberLabel;
	
	/** The shipping address label. */
	@Inject
	@SerializedName("addrShipAddrFullLabel")
	private String shippingAddressLabel;
	
	/** The billing address label. */
	@Inject
	@SerializedName("addrBillAddrFullLabel")
	private String billingAddressLabel;
	
	/** The ship to number label text. */
	@Inject
	@SerializedName("shipToNumberLabelText")
	private String shipToNumberLabelText;
	
	/** The bill to number label text. */
	@Inject
	@SerializedName("billToNumberLabelText")
	private String billToNumberLabelText;

	/** The pending approval label. */
	@Inject
	@Via("resource")
	@SerializedName("addrPendingApprovalLabel")
	private String pendingApprovalLabel;
	
	/** The address approval text. */
	@Inject
	@Via("resource")
	@SerializedName("addrAddAddrDescLabel")
	private String addressApprovalText;
	
	/** The address labels. */
	@SerializedName("addressLabels")
	private JsonElement addressLabels;	

	/** The confirm label. */
	@Inject
	@Via("resource")
	@SerializedName("addrFormConfirmLabel")
	private String confirmLabel;
	
	/** The cancel label. */
	@Inject
	@Via("resource")
	@SerializedName("addrFormCancelLabel")
	private String cancelLabel;

	/** The addr form okay label. */
	@Inject
	@Via("resource")
	@SerializedName("addrFormOkayLabel")
	private String addrFormOkayLabel;
	
	/** The billing address icon url. */
	@Inject
	@Via("resource")
	@SerializedName("billAddrIcon")
	private String billingAddressIconUrl;
	
	/** The billing address icon alt text. */
	@Inject
	@Via("resource")
	@SerializedName("billingAddressIconAltText")
	private String billingAddressIconAltText;
	
	/** The shipping address icon url. */
	@Inject
	@Via("resource")
	@SerializedName("shipAddrIcon")
	private String shippingAddressIconUrl;
	
	/** The shipping address icon alt text. */
	@Inject
	@Via("resource")
	@SerializedName("shippingAddressIconAltText")
	private String shippingAddressIconAltText;
	
	/** The fav addr icon filled. */
	@Inject
	@Via("resource")
	@SerializedName("favAddrIconFilled")
	private String favAddrIconFilled;
	
	/** The fav addr icon filled alt text. */
	@Inject
	@Via("resource")
	@SerializedName("favAddrIconFilledAltText")
	private String favAddrIconFilledAltText;
	
	/** The fav addr icon empty. */
	@Inject
	@Via("resource")
	@SerializedName("favAddrIcoonEmpty")
	private String favAddrIconEmpty;
	
	/** The fav addr icon empty alt text. */
	@Inject
	@Via("resource")
	@SerializedName("favAddrIcoonEmptyAltText")
	private String favAddrIconEmptyAltText;
	
	/** The fav addr icon disabled. */
	@Inject
	@Via("resource")
	@SerializedName("favAddrIconDisabled")
	private String favAddrIconDisabled;
	
	/** The fav addr icon disabled alt text. */
	@Inject
	@Via("resource")
	@SerializedName("favAddrIconDisabledAltText")
	private String favAddrIconDisabledAltText;
	
	/** The successful address text. */
	@Inject
	@Via("resource")
	@SerializedName("successfulAddressText")
	private String successfulAddressText;
	
	/** The default shipping address alert. */
	@Inject
	@Via("resource")
	@SerializedName("defaultShippingAddrAlert")
	private String defaultShippingAddrAlert;
	
	/** The default shipping address alert info. */
	@Inject
	@Via("resource")
	@SerializedName("defaultShippingAddrAlertInfo")
	private String defaultShippingAddrAlertInfo;
	
	/** The default shipping address alert Ok CTA. */
	@Inject
	@Via("resource")
	@SerializedName("defaultShippingAddrAlertOkCta")
	private String defaultShippingAddrAlertOkCta;
	
	/** The detail address label. */
	@Inject
	@Via("resource")
	private String detailAddressLabel;
	
	/** The bdb api endpoint service. */
	@Inject
	private BDBApiEndpointService bdbApiEndpointService;
	
	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;
	
	/** The user acc addr labels. */
	private String userAccAddrLabels;
	
	/** The user acc addr config. */
	private String userAccAddrConfig;
	
	/** The hybris site id. */
	private String hybrisSiteId;
	
	/**
	 * Creates and Initializes the model with the Labels and Configs.
	 */
	@PostConstruct
	void init() {
		log.debug("Inside Certifications Init Method");
		
			hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);

			ExcludeUtil excludeUtilObject = new ExcludeUtil();			
			setUserAccAddrLabels(excludeUtilObject);
			setUserAccAddrConfig(excludeUtilObject);
	}

	/**
	 * Sets the user acc addr labels.
	 *
	 * @param excludeUtilObject the exclude util object
	 */
	private void setUserAccAddrLabels(ExcludeUtil excludeUtilObject) {
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		
		userAccAddrLabels = StringUtils.EMPTY;
		
		defaultLabel = CommonHelper.getLabel(CommonConstants.DEFAULT_LABEL, currentPage);
		setAsDefaultLabel = CommonHelper.getLabel(CommonConstants.SET_AS_DEFAULT_LABEL, currentPage);
		addressDetailsSubHeadLabel = CommonHelper.getLabel(CommonConstants.ADDRESS_LABEL, currentPage);
		phoneNumberSubHeadLabel = CommonHelper.getLabel(CommonConstants.PHONE_NUMBER_LABEL, currentPage);
		shippingAddressLabel = CommonHelper.getLabel(CommonConstants.SHIPPING_ADDRESS_LABEL, currentPage); 
		billingAddressLabel = CommonHelper.getLabel(CommonConstants.BILLING_ADDRESS_LABEL, currentPage);
		shipToNumberLabelText = CommonHelper.getLabel(CommonConstants.SHIP_TO_NUMBER_LABEL, currentPage);
		billToNumberLabelText = CommonHelper.getLabel(CommonConstants.BILL_TO_NUMBER_LABEL, currentPage);
		vatNumberSubHeadLabel = CommonHelper.getLabel(CommonConstants.VAT_NUMBER_LABEL, currentPage);
	    vatNumber = CommonHelper.getLabel(CommonConstants.VAT_NUMBER_LABEL, currentPage);
			    //vatNumberUser = CommonHelper.getLabel(CommonConstants.VAT_NUMBER_LABEL, currentPage);

	    vatNumberLabel = CommonHelper.getLabel(CommonConstants.VAT_NUMBER_LABEL, currentPage);
		
		String companyLabel = CommonHelper.getLabel(CommonConstants.COMPANY_LABEL, currentPage);
		String addressLabelFirst = CommonHelper.getLabel(CommonConstants.ADDRESS_LINE_ONE_LABEL, currentPage);
		String addressLabelSecond = CommonHelper.getLabel(CommonConstants.ADDRESS_LINE_TWO_LABEL, currentPage);
		String streetAddressLabel = CommonHelper.getLabel(CommonConstants.STREET_ADDRESS_LABEL, currentPage);
		String address = CommonHelper.getLabel(CommonConstants.ADDRESS_LABEL, currentPage);
		String building = CommonHelper.getLabel(CommonConstants.BUILDING_LABEL, currentPage);
		String floor = CommonHelper.getLabel(CommonConstants.FLOOR_LABEL, currentPage);
		String room = CommonHelper.getLabel(CommonConstants.ROOM_LABEL, currentPage);
		String department = CommonHelper.getLabel(CommonConstants.DEPARTMENT_LABEL, currentPage);
		String phoneNumber = CommonHelper.getLabel(CommonConstants.PHONE_NUMBER_LABEL, currentPage);
		String vatNumber = CommonHelper.getLabel(CommonConstants.VAT_NUMBER_LABEL, currentPage);
		String district = CommonHelper.getLabel(CommonConstants.DISTRICT_LABEL, currentPage);
		String city = CommonHelper.getLabel(CommonConstants.CITY_LABEL, currentPage);
		String state = CommonHelper.getLabel(CommonConstants.STATE_LABEL, currentPage);
		String province = CommonHelper.getLabel(CommonConstants.PROVINCE_LABEL, currentPage);
		String postalCode = CommonHelper.getLabel(CommonConstants.POSTAL_CODE_LABEL, currentPage);
		String pinCode = CommonHelper.getLabel(CommonConstants.PIN_CODE_LABEL, currentPage);
		String zipcode = CommonHelper.getLabel(CommonConstants.ZIP_CODE_LABEL, currentPage);
		String country = CommonHelper.getLabel(CommonConstants.COUNTRY_LABEL, currentPage);
		
		Gson addressLabelsJson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		AddressLabelsBean addressLabelsBean = new AddressLabelsBean();
		addressLabelsBean.setCompanyLabel(companyLabel);
		addressLabelsBean.setAddressLabelFirst(addressLabelFirst);
		addressLabelsBean.setAddressLabelSecond(addressLabelSecond);
		addressLabelsBean.setStreetAddressLabel(streetAddressLabel);
		addressLabelsBean.setDetailAddressLabel(detailAddressLabel);
		addressLabelsBean.setAddressLabel(address);
		addressLabelsBean.setBuildingLabel(building);
		addressLabelsBean.setFloorLabel(floor);
		addressLabelsBean.setRoomLabel(room);
		addressLabelsBean.setDepartmentLabel(department);
		addressLabelsBean.setPhoneNumberLabel(phoneNumber);
		addressLabelsBean.setVatNumberLabel(vatNumber);
		addressLabelsBean.setDistrictLabel(district);
		addressLabelsBean.setCityLabel(city);
		addressLabelsBean.setStateLabel(state);
		addressLabelsBean.setProvinceLabel(province);
		addressLabelsBean.setPostalCodeLabel(postalCode);
		addressLabelsBean.setPinCodeLabel(pinCode);
		addressLabelsBean.setZipCodeLabel(zipcode);
		addressLabelsBean.setCountryLabel(country);
		
		String errorPagePath = CommonHelper.getErrorPagePath(currentPage);
		GlobalErrorMessagesModel errorModel = GlobalErrorMessagesModelUtil.getErrorMessageModel(
				errorPagePath, resourceResolver, PASS_RULES_RESOURCE_TYPE);

		if (errorModel != null) {			
			addressLabelsBean.setMandatoryFieldError(errorModel.getMandatoryFieldError());
			addressLabelsBean.setPhoneNumberLabelError(errorModel.getPhoneNumberLabelError());
			addressLabelsBean.setVatNumberLabelError(errorModel.getVatNumberLabelError());
		}
		
		AddressLabelsConfig addressLabelsConfig = new AddressLabelsConfig(addressLabelsBean);
		addressLabels = addressLabelsJson.toJsonTree(addressLabelsConfig);
		userAccAddrLabels = json.toJson(this);
	}

	/**
	 * Sets the user acc addr config.
	 *
	 * @param excludeUtilObject the new user acc addr config
	 */
	private void setUserAccAddrConfig(ExcludeUtil excludeUtilObject) {
		
		userAccAddrConfig = StringUtils.EMPTY;
		
		JsonElement getAddPayload;
		JsonElement addAddrPayload;
		JsonElement editNickPayload;
		JsonElement setDefaultPayload; 
		JsonElement setFavouritePayload;
		JsonElement reactivateUserPayload;
		
		
		if (bdbApiEndpointService != null) {
			String getAddressEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getAddressesEndpoint(), CommonConstants.HYBRIS_SITE_LITERAL, hybrisSiteId);
			String updateFavoriteAddressEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.updateFavoriteAddressEndpoint(), CommonConstants.BASE_SITE_ID, hybrisSiteId);
			String defaultAddressEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.defaultAddressEndpoint(), CommonConstants.HYBRIS_SITE_LITERAL, hybrisSiteId);
			String updateNicknameEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.updateNicknameEndpoint(), CommonConstants.BASE_SITE_ID, hybrisSiteId);
			String createAddressEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.createAddressEndpoint(), CommonConstants.HYBRIS_SITE_LITERAL, hybrisSiteId);
			String reactivateUserEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.reactivateUserEndpoint(), CommonConstants.BASE_SITE_ID, hybrisSiteId);

			Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
					.addSerializationExclusionStrategy(excludeUtilObject).create();
			
			Payload getAddressPayload =  new Payload(getAddressEndpoint , HttpConstants.METHOD_GET);
			Payload updateNicknamePayload = new Payload(updateNicknameEndpoint , HttpConstants.METHOD_POST);
			Payload setDefaultAddressPayload = new Payload(defaultAddressEndpoint, HttpConstants.METHOD_POST);
			Payload updateFavoriteAddressPayload = new Payload(updateFavoriteAddressEndpoint, HttpConstants.METHOD_POST);
			Payload addressPayload = new Payload(createAddressEndpoint, HttpConstants.METHOD_POST);
			Payload addReactivateUserPayload = new Payload(reactivateUserEndpoint, HttpConstants.METHOD_POST);
			
			PayloadConfig getAddressPayloadConfig = new PayloadConfig(getAddressPayload);
			getAddPayload = json.toJsonTree(getAddressPayloadConfig);
			PayloadConfig setDefaultAddressPayloadConfig = new PayloadConfig(setDefaultAddressPayload);
			setDefaultPayload = json.toJsonTree(setDefaultAddressPayloadConfig);
			PayloadConfig updateFavoriteAddressPayloadConfig = new PayloadConfig(updateFavoriteAddressPayload);
			setFavouritePayload = json.toJsonTree(updateFavoriteAddressPayloadConfig);
			PayloadConfig updateNicknamePayloadConfig = new PayloadConfig(updateNicknamePayload);
			editNickPayload = json.toJsonTree(updateNicknamePayloadConfig);
			PayloadConfig addressPayloadConfig = new PayloadConfig(addressPayload);
			addAddrPayload = json.toJsonTree(addressPayloadConfig);
			PayloadConfig addReactivateUserPayloadConfig = new PayloadConfig(addReactivateUserPayload);
			reactivateUserPayload = json.toJsonTree(addReactivateUserPayloadConfig);
			
			AccountManagementAddressesConfig accountManagementAddressesConfig = new AccountManagementAddressesConfig(getAddPayload, addAddrPayload, editNickPayload, setDefaultPayload, setFavouritePayload, reactivateUserPayload);
			userAccAddrConfig = json.toJson(accountManagementAddressesConfig);
		}
	}

	/**
	 * Gets the user acc addr labels.
	 *
	 * @return the user acc addr labels
	 */
	@Override
	public String getUserAccAddrLabels() {
		return userAccAddrLabels;
	}

	/**
	 * Gets the user acc addr config.
	 *
	 * @return the user acc addr config
	 */
	@Override
	public String getUserAccAddrConfig() {
		return userAccAddrConfig;
	}

	/**
	 * Gets the hybris site id.
	 *
	 * @return the hybris site id
	 */
	public String getHybrisSiteId() {
		return hybrisSiteId;
	}
}
