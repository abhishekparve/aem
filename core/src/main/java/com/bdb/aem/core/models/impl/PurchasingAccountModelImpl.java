package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.bean.AddressLabelsBean;
import com.bdb.aem.core.bean.JoinBdRewardsBean;
import com.bdb.aem.core.bean.PurchaseAccountModelBean;
import com.bdb.aem.core.bean.PurchaseAccountModelConfirmationBean;
import com.bdb.aem.core.models.GlobalErrorMessagesModel;
import com.bdb.aem.core.models.PurchasingAccountModel;
import com.bdb.aem.core.pojo.*;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.*;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The Class PurchasingAccountModelImpl.
 */
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class }, adapters = { PurchasingAccountModel.class }, resourceType = {
		PurchasingAccountModelImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class PurchasingAccountModelImpl implements PurchasingAccountModel {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/purchasingaccount/v1/purchasingaccount";

	/** The Constant log. */
	protected static final Logger logger = LoggerFactory.getLogger(PurchasingAccountModelImpl.class);

	/** The Constant PASS_RULES_RESOURCE_TYPE. */
	protected static final String PASS_RULES_RESOURCE_TYPE = "bdb-aem/proxy/components/content/globalerror";

	/** The current page. */
	@Inject
	private Page currentPage;

	/** The title. */
	@Inject
	@Via("resource")
	private String title;

	/** The existing account. */
	@Inject
	@Via("resource")
	private String existingAccount;

	/** The yes label. */
	@Inject
	@Via("resource")
	private String yesLabel;

	/** The no with not sure label. */
	@Inject
	@Via("resource")
	private String noWithNotSureLabel;

	/** The no label. */
	@Inject
	@Via("resource")
	private String noLabel;
	
	/** The internal Approval. */
	@Inject
	@Via("resource")
	private String internalApproval;
	
	/** The internal Approval Label Info. */
	@Inject
	@Via("resource")
	private String internalApprovalLabelInfo;

	/** The existing distributor. */
	@Inject
	@Via("resource")
	private String existingDistributor;

	/** The payment title. */
	@Inject
	@Via("resource")
	private String paymentTitle;

	/** The purchase order. */
	@Inject
	@Via("resource")
	private String purchaseOrder;

	/** The credit card. */
	@Inject
	@Via("resource")
	private String creditCard;

	/** The credit app file path. */
	@Inject
	@Via("resource")
	private String downloadCreditNoteUrl;

	/** The download credit app CTA label. */
	@Inject
	@Via("resource")
	private String downloadCreditAppCTALabel;

	/** The download credit app format and size. */
	@Inject
	@Via("resource")
	private String downloadCreditAppFormatAndSize;

	/** The tax exempt label. */
	@Inject
	@Via("resource")
	private String taxExemptLabel;

	/** The file upload title label. */
	@Inject
	@Via("resource")
	private String fileUploadTitleLabel;

	/** The file upload info label. */
	@Inject
	@Via("resource")
	private String fileUploadInfoLabel;

	/** The drop zone label. */
	@Inject
	@Via("resource")
	private String dropZoneLabel;

	/** The drop zone info. */
	@Inject
	@Via("resource")
	private String dropZoneInfo;

	/** The choose file CTA label. */
	@Inject
	@Via("resource")
	private String chooseFileCTALabel;

	/** The vat exempt label. */
	@Inject
	@Via("resource")
	private String vatExemptLabel;

	/** The EAN code label. */
	@Inject
	@Via("resource")
	@Named("EANCodeLabel")
	private String eaNCodeLabel;
	
	/** The distributor label. */
	@Inject
	@Via("resource")
	@Named("distributorLabel")
	private String distributorLabel;
	
	/** The agencyselectLabel label. */
	@Inject
	@Via("resource")
	@Named("agencyselectLabel")
	private String agencyselectLabel;
	
	/** The VAT number label. */
	@Inject
	@Via("resource")
	@Named("VATNumberLabel")
	private String vaTNumberLabel;
	
	/** The type of business label. */
	@Inject
	@Via("resource")
	private String typeOfBusinessLabel;
	
	/** The public label. */
	@Inject
	@Via("resource")
	private String publicLabel;
	
	/** The private label. */
	@Inject
	@Via("resource")
	private String privateLabel;
	
	/** The account Office label. */
	@Inject
	@Via("resource")
	private String accountOfficeLabel;
	
	/** The processingUnit label. */
	@Inject
	@Via("resource")
	private String processingUnitLabel;
	
	/** The transactingUnit label. */
	@Inject
	@Via("resource")
	private String transactingUnitLabel;

	/** The duty exempt label. */
	@Inject
	@Via("resource")
	private String dutyExemptLabel;

	/** The gst pan title. */
	@Inject
	@Via("resource")
	private String gstPanTitle;

	/** The gst number label. */
	@Inject
	@Via("resource")
	private String gstNumberLabel;

	/** The pan number label. */
	@Inject
	@Via("resource")
	private String panNumberLabel;

	/** The ship to account number label. */
	@Inject
	@Via("resource")
	private String shipToAccountNumberLabel;

	/** The shipto account number label info. */
	@Inject
	@Via("resource")
	private String shiptoAccountNumberLabelInfo;

	/** The ship to account number place holder. */
	@Inject
	@Via("resource")
	private String shipToAccountNumberPlaceHolder;

	/** The sold to account number label. */
	@Inject
	@Via("resource")
	private String soldToAccountNumberLabel;

	/** The sold to account number label info. */
	@Inject
	@Via("resource")
	private String soldToAccountNumberLabelInfo;

	/** The sold to account number place holder. */
	@Inject
	@Via("resource")
	private String soldToAccountNumberPlaceHolder;

	/** The account number validation error. */
	@Inject
	@Via("resource")
	private String accountNumberValidationError;
	
	/** The editlabel. */
	@Inject
	@Via("resource")
	private String editlabel;

	/** The save and continue label. */
	@Inject
	@Via("resource")
	private String saveAndContinueLabel;
	
	/** The back label. */
	@Inject
	@Via("resource")
	private String backLabel;

	/** The submit CTA label. */
	@Inject
	@Via("resource")
	private String submitCTALabel;

	/** The company label. */
	@Inject
	@Via("resource")
	private String companyLabel;

	/** The address label first. */
	@Inject
	@Via("resource")
	private String addressLabelFirst;

	/** The address label second. */
	@Inject
	@Via("resource")
	private String addressLabelSecond;

	/** The street address label. */
	@Inject
	@Via("resource")
	private String streetAddressLabel;

	/** The detail address label. */
	@Inject
	@Via("resource")
	private String detailAddressLabel;

	@Inject
	@Via("resource")
	private String addressLabel;

	/** The building label. */
	@Inject
	@Via("resource")
	private String buildingLabel;

	/** The floor label. */
	@Inject
	@Via("resource")
	private String floorLabel;

	/** The room label. */
	@Inject
	@Via("resource")
	private String roomLabel;

	/** The department label. */
	@Inject
	@Via("resource")
	private String departmentLabel;

	/** The phone number label. */
	@Inject
	@Via("resource")
	private String phoneNumberLabel;

	/** The district label. */
	@Inject
	@Via("resource")
	private String districtLabel;

	/** The city label. */
	@Inject
	@Via("resource")
	private String cityLabel;

	/** The state label. */
	@Inject
	@Via("resource")
	private String stateLabel;

	/** The province label. */
	@Inject
	@Via("resource")
	private String provinceLabel;

	/** The postal code label. */
	@Inject
	@Via("resource")
	private String postalCodeLabel;

	/** The pin code label. */
	@Inject
	@Via("resource")
	private String pinCodeLabel;

	/** The zip code label. */
	@Inject
	@Via("resource")
	private String zipCodeLabel;

	/** The country label. */
	@Inject
	@Via("resource")
	private String countryLabel;

	/** The labNameLabel. */
	@Inject
	@Via("resource")
	private String labNameLabel;

	/** The postalCodeLabelError. */
	@Inject
	@Via("resource")
	private String postalCodeLabelError;

	/** The address section title. */
	@Inject
	@Via("resource")
	private String addressSectionTitle;

	/** The shipping address title. */
	@Inject
	@Via("resource")
	private String shippingAddressTitle;

	/** The shipping address sub title. */
	@Inject
	@Via("resource")
	private String shippingAddressSubTitle;

	/** The sold to address title. */
	@Inject
	@Via("resource")
	private String soldToAddressTitle;

	/** The billing address title. */
	@Inject
	@Via("resource")
	private String billingAddressTitle;

	/** The billing address sub title. */
	@Inject
	@Via("resource")
	private String billingAddressSubTitle;

	/** The payer address title. */
	@Inject
	@Via("resource")
	private String payerAddressTitle;

	/** The payer address sub title. */
	@Inject
	@Via("resource")
	private String payerAddressSubTitle;

	/** The same as shipto address label. */
	@Inject
	@Via("resource")
	private String sameAsShiptoAddressLabel;

	/** The purchase account confirmation image. */
	@Inject
	@Via("resource")
	private String purchaseAccountConfirmationImage;

	/** The purchase account confirmation title. */
	@Inject
	@Via("resource")
	private String purchaseAccountConfirmationTitle;

	/** The purchase account confirmation pending title. */
	@Inject
	@Via("resource")
	private String purchaseAccountConfirmationPendingTitle;

	/** The purchase account confirmation success title. */
	@Inject
	@Via("resource")
	private String purchaseAccountConfirmationSuccessTitle;

	/** The purchase account completed content. */
	@Inject
	@Via("resource")
	private String purchaseAccountCompletedContent;

	/** The purchase account pending content. */
	@Inject
	@Via("resource")
	private String purchaseAccountPendingContent;

	/** The purchase account rewards CTA label. */
	@Inject
	@Via("resource")
	private String purchaseAccountRewardsCTALabel;

	/** The purchase account continue CTA label. */
	@Inject
	@Via("resource")
	private String purchaseAccountContinueCTALabel;

	/** The purchase account continue CTA link. */
	@Inject
	@Via("resource")
	private String purchaseAccountContinueCTALink;

	/** The join rewards title. */
	@Inject
	@Via("resource")
	private String joinRewardsTitle;

	/** The join rewards subtitle. */
	@Inject
	@Via("resource")
	private String joinRewardsSubtitle;

	/** The not health care professional label. */
	@Inject
	@Via("resource")
	private String notHealthCareProfessionalLabel;

	/** The not govt employee label. */
	@Inject
	@Via("resource")
	private String notGovtEmployeeLabel;

	/** The not prohibited gifts accept. */
	@Inject
	@Via("resource")
	private String notProhibitedGiftsAccept;

	/** The rewards T n C label. */
	@Inject
	@Via("resource")
	private String rewardsTnCLabel;

	/** The join BD rewards CTA label. */
	@Inject
	@Via("resource")
	private String joinBdRewardsCTALabel;

	@Inject
	@Via("resource")
	private String joinBdRewardsCTALink;

	@Inject
	@Via("resource")
	private String redirectionUrl;

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/** The bdb api endpoint service. */
	@Inject
	private BDBApiEndpointService bdbApiEndpointService;


	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** The payment options. */
	private JsonElement paymentOptions;

	/** The file upload labels. */
	private JsonElement fileUploadLabels;

	/** The address labels. */
	private JsonElement addressLabels;

	/** The purchase account labels. */
	private String purchaseAccountLabels;

	/** The purchase account config. */
	private String purchaseAccountConfig;

	/** The join rewards modal labels. */
	private JsonElement joinRewardsModalLabels;

	/** The b D rewards config. */
	private JsonElement bDRewardsConfig;

	/** The purchase account confirmation labels. */
	private String purchaseAccountConfirmationLabels;
	
	/** The purchase account confirmation config. */
	private String purchaseAccountConfirmationConfig;

	/** The hybris site id. */
	private String hybrisSiteId;

	/**
	 * Creates the purchaseAccountLabels, purchaseAccountConfig
	 * purchaseAccountConfirmationLabels JSON Labels and Configs
	 */
	@PostConstruct
	protected void init() {
		logger.debug("Inside Purchase Init Method");
		if(null!=redirectionUrl) {
			redirectionUrl = externalizerService.getFormattedUrl(redirectionUrl,resourceResolver);
		}

		ExcludeUtil excludeUtilObject = new ExcludeUtil();
		if (bdbApiEndpointService != null) {
			hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
			setPurchaseAccountLables(excludeUtilObject);
			setPurchaseAccountConfig(excludeUtilObject);
			setPurchaseAccountConfirmationLabels(excludeUtilObject);
			setPurchaseAccountConfirmationConfig(excludeUtilObject);
		}
	}

	/**
	 * Sets the purchase account lables.
	 *
	 * @param excludeUtilObject the new purchase account lables
	 */
	private void setPurchaseAccountLables(ExcludeUtil excludeUtilObject) {
		PaymentOptionsLabelConfig paymentOptionsLabelConfig = new PaymentOptionsLabelConfig(purchaseOrder, creditCard);
		FileUploadLabelsConfig fileUploadLabelsConfig = new FileUploadLabelsConfig(fileUploadTitleLabel,
				fileUploadInfoLabel, dropZoneLabel, dropZoneInfo, chooseFileCTALabel);

		Gson paymentOptionsJson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		Gson fileUploadLabelsJson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();

		
    	if(StringUtils.isNotEmpty(rewardsTnCLabel)) {
    		try {
				rewardsTnCLabel = CommonHelper.HandleRTEAnchorLink(rewardsTnCLabel, externalizerService, resourceResolver,StringUtils.EMPTY);
			} catch (IOException e) {
				logger.error("Exception occured PurchasingAccountModelImpl{}", e.getMessage());
			}
		}
    	else {
    		rewardsTnCLabel = StringUtils.EMPTY;
    	}


		paymentOptions = paymentOptionsJson.toJsonTree(paymentOptionsLabelConfig);

		fileUploadLabels = fileUploadLabelsJson.toJsonTree(fileUploadLabelsConfig);
		
		Gson addressLabelsJson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		AddressLabelsBean addressLabelsBean = new AddressLabelsBean();
		addressLabelsBean.setCompanyLabel(companyLabel);
		addressLabelsBean.setAddressLabelFirst(addressLabelFirst);
		addressLabelsBean.setAddressLabelSecond(addressLabelSecond);
		addressLabelsBean.setStreetAddressLabel(streetAddressLabel);
		addressLabelsBean.setDetailAddressLabel(detailAddressLabel);
		addressLabelsBean.setAddressLabel(addressLabel);
		addressLabelsBean.setBuildingLabel(buildingLabel);
		addressLabelsBean.setFloorLabel(floorLabel);
		addressLabelsBean.setRoomLabel(roomLabel);
		addressLabelsBean.setDepartmentLabel(departmentLabel);
		addressLabelsBean.setPhoneNumberLabel(phoneNumberLabel);
		addressLabelsBean.setDistrictLabel(districtLabel);
		addressLabelsBean.setCityLabel(cityLabel);
		addressLabelsBean.setStateLabel(stateLabel);
		addressLabelsBean.setProvinceLabel(provinceLabel);
		addressLabelsBean.setPostalCodeLabel(postalCodeLabel);
		addressLabelsBean.setPinCodeLabel(pinCodeLabel);
		addressLabelsBean.setZipCodeLabel(zipCodeLabel);
		addressLabelsBean.setCountryLabel(countryLabel);
		addressLabelsBean.setLabNameLabel(labNameLabel);
		addressLabelsBean.setPostalCodeLabelError(postalCodeLabelError);

		String errorPagePath = CommonHelper.getErrorPagePath(currentPage);
		if (!StringUtils.isEmpty(errorPagePath)) {
			logger.debug("Inside IF");
			GlobalErrorMessagesModel errorModel = GlobalErrorMessagesModelUtil.getErrorMessageModel(
					errorPagePath, resourceResolver, PASS_RULES_RESOURCE_TYPE);

				String mandatoryFieldError = StringUtils.EMPTY;
				String phoneNumberLabelError = StringUtils.EMPTY;
				if(null != errorModel)
				{
					mandatoryFieldError = errorModel.getMandatoryFieldError();
					phoneNumberLabelError = errorModel.getPhoneNumberLabelError();
				}
				addressLabelsBean.setMandatoryFieldError(mandatoryFieldError);
				addressLabelsBean.setPhoneNumberLabelError(phoneNumberLabelError);
		}
		
		AddressLabelsConfig addressLabelsConfig = new AddressLabelsConfig(addressLabelsBean);
		addressLabels = addressLabelsJson.toJsonTree(addressLabelsConfig);

		PurchaseAccountModelLabels purchaseAccountModelLabels = createPurchaseAccountModelLabels();
		Gson purchaseAccountLabelJson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();

		purchaseAccountLabels = purchaseAccountLabelJson.toJson(purchaseAccountModelLabels);
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

		String hybrisPurchaseAccountEndpoint = CommonHelper.getEndPointUrlWithoutSiteId(
				bdbApiEndpointService,
				bdbApiEndpointService.getPurchasingAccountRegistrationEndpoint().trim(),
				true,
				currentPage);

		String hybrisUploadTaxCertificateEndpoint = CommonHelper.getEndPointUrlWithoutSiteId(
				bdbApiEndpointService,
				bdbApiEndpointService.getUploadTaxCertificateEndpoint().trim(),
				true,
				currentPage);
				Payload getStatesPayload = new Payload(bdbApiEndpointService.getStatesFromCountryServletPath(),
				HttpConstants.METHOD_POST);
		
		Payload getCountriesPayload = new Payload(bdbApiEndpointService.getCountriesFromRegionServletPath(),
                HttpConstants.METHOD_GET);

		Payload purchaseAccountPayload = new Payload(hybrisPurchaseAccountEndpoint, HttpConstants.METHOD_POST);
		
		Payload uploadTaxCertificatePayload = new Payload(hybrisUploadTaxCertificateEndpoint, HttpConstants.METHOD_POST);
		
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

		String getDistributersOptionsEndpoint =
				CommonHelper.getEndPointUrlWithoutSiteId(bdbApiEndpointService,
						bdbApiEndpointService.getGetDistributorsOptionsEndpoint().trim(),
						true,
						currentPage);
		Payload getDistributersOptionsPayload = new Payload(getDistributersOptionsEndpoint, HttpConstants.METHOD_GET);
		PayloadConfig getDistributersOptionsPayloadConfig = new PayloadConfig(getDistributersOptionsPayload);
		getDistributersOptions = json.toJsonTree(getDistributersOptionsPayloadConfig);


		String postSmartCartRegisterEndpoint =
				CommonHelper.getEndPointUrlWithoutSiteId(
						bdbApiEndpointService,
						bdbApiEndpointService.getPostSmartCartRegisterEndpoint().trim(),
						true,
						currentPage);
		Payload postSmartCartRegisterPayload = new Payload(postSmartCartRegisterEndpoint, HttpConstants.METHOD_POST);
		PayloadConfig postSmartCartRegisterPayloadConfig = new PayloadConfig(postSmartCartRegisterPayload);
		postSmartCartRegister = json.toJsonTree(postSmartCartRegisterPayloadConfig);


		
		PurchaseAccountConfig purchaseAccountConfiguration = new PurchaseAccountConfig(purchaseAccountSubmit,
				uploadTaxCertificateConfig, statesConfig, countriesConfig,getDistributersOptions,postSmartCartRegister);

		purchaseAccountConfig = json.toJson(purchaseAccountConfiguration);
	}

	/**
	 * Sets the purchase account confirmation labels.
	 *
	 * @param excludeUtilObject the new purchase account confirmation labels
	 */
	private void setPurchaseAccountConfirmationLabels(ExcludeUtil excludeUtilObject) {

		Payload bDRewardsPayload = new Payload(bdbApiEndpointService.getBDBHybrisDomain() + "/",
				HttpConstants.METHOD_POST);

		JoinBdRewardsBean joinBdRewardsBean = new JoinBdRewardsBean();
		joinBdRewardsBean.setJoinRewardsTitle(joinRewardsTitle);
		joinBdRewardsBean.setJoinRewardsSubtitle(joinRewardsSubtitle);
		joinBdRewardsBean.setNotHealthCareProfessionalLabel(notHealthCareProfessionalLabel);
		joinBdRewardsBean.setNotGovtEmployeeLabel(notGovtEmployeeLabel);
		joinBdRewardsBean.setNotProhibitedGiftsAccept(notProhibitedGiftsAccept);
		joinBdRewardsBean.setRewardsTnCLabel(rewardsTnCLabel);
		joinBdRewardsBean.setJoinBdRewardsCTALabel(joinBdRewardsCTALabel);
		joinBdRewardsBean.setJoinBdRewardsCTALink(joinBdRewardsCTALink);
		joinBdRewardsBean.setRedirectionUrl(redirectionUrl);

		JoinBDRewardsModalLabelsConfig joinBDRewardsModalLabelsConfig = new JoinBDRewardsModalLabelsConfig(
				joinBdRewardsBean);

		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();

		PayloadConfig bDRewardsPayloadConfig = new PayloadConfig(bDRewardsPayload);
		bDRewardsConfig = json.toJsonTree(bDRewardsPayloadConfig);

		joinRewardsModalLabels = json.toJsonTree(joinBDRewardsModalLabelsConfig);

		PurchaseAccountModelConfirmationLabels purchaseAccountModelConfirmationLabels = createPurchaseAccountModelConfirmationLabels();

		purchaseAccountConfirmationLabels = json.toJson(purchaseAccountModelConfirmationLabels);

	}
	
	private void setPurchaseAccountConfirmationConfig(ExcludeUtil excludeUtilObject) {
		PurchaseAccountModelConfirmationConfig purchaseAccountModelConfirmationConfig;
		if(null!=bdbApiEndpointService) {
			String updateRewardsPreferencesEndpoint = StringUtils.replace(
					bdbApiEndpointService.getUpdateRewardsPreferencesEndpoint(),
					CommonConstants.HYBRIS_SITE_LITERAL,
					"{{siteId}}");
			Payload updateRewardsPreferencesPayload = new Payload(
					bdbApiEndpointService.getBDBHybrisDomain() + updateRewardsPreferencesEndpoint,
					HttpConstants.METHOD_POST);
			PayloadConfig updateRewardsPreferences = new PayloadConfig(updateRewardsPreferencesPayload);

			purchaseAccountModelConfirmationConfig = new
					PurchaseAccountModelConfirmationConfig("false", updateRewardsPreferences);
		}
		else{
			purchaseAccountModelConfirmationConfig = new
					PurchaseAccountModelConfirmationConfig("false");
		}
		//PurchaseAccountModelConfirmationConfig purchaseAccountModelConfirmationConfig = new PurchaseAccountModelConfirmationConfig("false");

		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		
		purchaseAccountConfirmationConfig = json.toJson(purchaseAccountModelConfirmationConfig);
	}

	/**
	 * Creates the purchase account model labels.
	 *
	 * @return the purchase account model labels
	 */
	private PurchaseAccountModelLabels createPurchaseAccountModelLabels() {
		PurchaseAccountModelBean purchaseAccountModelBean = new PurchaseAccountModelBean();
		purchaseAccountModelBean.setTitle(title);
		purchaseAccountModelBean.setExistingAccount(existingAccount);
		purchaseAccountModelBean.setYes(yesLabel);
		purchaseAccountModelBean.setNoWithNotSure(noWithNotSureLabel);
		purchaseAccountModelBean.setNo(noLabel);
		purchaseAccountModelBean.setInternalApproval(internalApproval);
		purchaseAccountModelBean.setInternalApprovalLabelInfo(internalApprovalLabelInfo);
		purchaseAccountModelBean.setExistingDistributor(existingDistributor);
		purchaseAccountModelBean.setPaymentTitle(paymentTitle);
		purchaseAccountModelBean.setPaymentOptions(paymentOptions);
		purchaseAccountModelBean.setCreditAppFilePath(downloadCreditNoteUrl);
		purchaseAccountModelBean.setDownloadCreditAppCTALabel(downloadCreditAppCTALabel);
		purchaseAccountModelBean.setDownloadCreditAppFormatAndSize(downloadCreditAppFormatAndSize);
		purchaseAccountModelBean.setTaxExemptLabel(taxExemptLabel);
		purchaseAccountModelBean.setFileUploadLabels(fileUploadLabels);
		purchaseAccountModelBean.setVatExemptLabel(vatExemptLabel);
		purchaseAccountModelBean.setEanCodeLabel(eaNCodeLabel);
		purchaseAccountModelBean.setVatNumberLabel(vaTNumberLabel);
		purchaseAccountModelBean.setTypeOfBusinessLabel(typeOfBusinessLabel);
		purchaseAccountModelBean.setPublicLabel(publicLabel);
		purchaseAccountModelBean.setPrivateLabel(privateLabel);
		purchaseAccountModelBean.setAccountOfficeLabel(accountOfficeLabel);
		purchaseAccountModelBean.setProcessingUnitLabel(processingUnitLabel);
		purchaseAccountModelBean.setTransactingUnitLabel(transactingUnitLabel);
		purchaseAccountModelBean.setDutyExemptLabel(dutyExemptLabel);
		purchaseAccountModelBean.setGstPanTitle(gstPanTitle);
		purchaseAccountModelBean.setGstNumberLabel(gstNumberLabel);
		purchaseAccountModelBean.setPanNumberLabel(panNumberLabel);
		purchaseAccountModelBean.setShipToAccountNumberLabel(shipToAccountNumberLabel);
		purchaseAccountModelBean.setShipToAccountNumberLabelInfo(shiptoAccountNumberLabelInfo);
		purchaseAccountModelBean.setShipToAccountNumberPlaceHolder(shipToAccountNumberPlaceHolder);
		purchaseAccountModelBean.setSoldToAccountNumberLabel(soldToAccountNumberLabel);
		purchaseAccountModelBean.setSoldToAccountNumberLabelInfo(soldToAccountNumberLabelInfo);
		purchaseAccountModelBean.setSoldToAccountNumberPlaceHolder(soldToAccountNumberPlaceHolder);
		purchaseAccountModelBean.setAccountNumberValidationError(accountNumberValidationError);
		purchaseAccountModelBean.setAddressSectionTitle(addressSectionTitle);
		purchaseAccountModelBean.setShippingAddressTitle(shippingAddressTitle);
		purchaseAccountModelBean.setShippingAddressSubTitle(shippingAddressSubTitle);
		purchaseAccountModelBean.setSoldToAddressTitle(soldToAddressTitle);
		purchaseAccountModelBean.setBillingAddressTitle(billingAddressTitle);
		purchaseAccountModelBean.setBillingAddressSubTitle(billingAddressSubTitle);
		purchaseAccountModelBean.setPayerAddressTitle(payerAddressTitle);
		purchaseAccountModelBean.setPayerAddressSubTitle(payerAddressSubTitle);
		purchaseAccountModelBean.setSameAsShiptoAddressLabel(sameAsShiptoAddressLabel);
		purchaseAccountModelBean.setEditLabel(editlabel);
		purchaseAccountModelBean.setAddressLabels(addressLabels);
		purchaseAccountModelBean.setSaveAndContinue(saveAndContinueLabel);
		purchaseAccountModelBean.setBackCTA(backLabel);
		purchaseAccountModelBean.setSubmitCTA(submitCTALabel);
		purchaseAccountModelBean.setDistributorLabel(distributorLabel);
		purchaseAccountModelBean.setAgencyselectLabel(agencyselectLabel);
		return (new PurchaseAccountModelLabels(purchaseAccountModelBean));
	}

	/**
	 * Creates the purchase account model confirmation labels.
	 *
	 * @return the purchase account model confirmation labels
	 */
	private PurchaseAccountModelConfirmationLabels createPurchaseAccountModelConfirmationLabels() {
		PurchaseAccountModelConfirmationBean purchaseAccountModelConfirmationBean = new PurchaseAccountModelConfirmationBean();
		purchaseAccountModelConfirmationBean.setImage(purchaseAccountConfirmationImage);
		purchaseAccountModelConfirmationBean.setTitle(purchaseAccountConfirmationTitle);
		purchaseAccountModelConfirmationBean.setPendingTitle(purchaseAccountConfirmationPendingTitle);
		purchaseAccountModelConfirmationBean.setSuccessTitle(purchaseAccountConfirmationSuccessTitle);
		purchaseAccountModelConfirmationBean.setPendingContent(purchaseAccountPendingContent);
		purchaseAccountModelConfirmationBean.setCompletedContent(purchaseAccountCompletedContent);
		purchaseAccountModelConfirmationBean.setRewardsCTALabel(purchaseAccountRewardsCTALabel);
		purchaseAccountModelConfirmationBean.setContinueCTALabel(purchaseAccountContinueCTALabel);
		purchaseAccountModelConfirmationBean.setContinueCTALink(UrlHandler.getModifiedUrl(purchaseAccountContinueCTALink,resourceResolver));
		purchaseAccountModelConfirmationBean.setJoinRewards(joinRewardsModalLabels);
		purchaseAccountModelConfirmationBean.setbDRewardsConfig(bDRewardsConfig);
		return (new PurchaseAccountModelConfirmationLabels(purchaseAccountModelConfirmationBean));
	}

	/**
	 * Gets the purchase account labels.
	 *
	 * @return the purchase account labels
	 */
	@Override
	public String getPurchaseAccountLabels() {
		return purchaseAccountLabels;
	}

	/**
	 * Gets the purchase account config.
	 *
	 * @return the purchase account config
	 */
	@Override
	public String getPurchaseAccountConfig() {
		return purchaseAccountConfig;
	}

	/**
	 * Gets the purchase account confirmation labels.
	 *
	 * @return the purchase account confirmation labels
	 */
	@Override
	public String getPurchaseAccountConfirmationLabels() {
		return purchaseAccountConfirmationLabels;
	}

	/**
	 * Gets the purchase account confirmation config.
	 *
	 * @return the purchase account confirmation config
	 */
	@Override
	public String getPurchaseAccountConfirmationConfig() {
		return purchaseAccountConfirmationConfig;
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
