package com.bdb.aem.core.models.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.bdb.aem.core.models.GlobalFileUploadModel;
import com.bdb.aem.core.util.GlobalFileUploadUtil;
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

import com.bdb.aem.core.bean.AccountManagementCertificationsLabelsBean;
import com.bdb.aem.core.bean.AddressSelectionLabelsBean;
import com.bdb.aem.core.models.AccountManagementCertificationsModel;
import com.bdb.aem.core.models.UploadCertificationFileTypeModel;
import com.bdb.aem.core.pojo.AccountManagementCertificationsConfig;
import com.bdb.aem.core.pojo.AccountManagementCertificationsModelLabels;
import com.bdb.aem.core.pojo.AddressSelectionLabels;
import com.bdb.aem.core.pojo.Payload;
import com.bdb.aem.core.pojo.PayloadConfig;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.ExcludeUtil;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

/**
 * The Class AccountManagementCertificationsModelImpl.
 */
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class }, adapters = {
		AccountManagementCertificationsModel.class }, resourceType = {
				AccountManagementCertificationsModelImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AccountManagementCertificationsModelImpl implements AccountManagementCertificationsModel {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/accountmanagement/v1/accountmanagement";

	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(AccountManagementCertificationsModelImpl.class);

	/**
	 * The Constant GLOBAL_FILE_UPLOAD_RESOURCE_TYPE.
	 */
	protected static final String GLOBAL_FILE_UPLOAD_RESOURCE_TYPE = "bdb-aem/proxy/components/content/globalfileupload";


	/** The current page. */
	@Inject
	private Page currentPage;

	/** The request. */
	@Self
	private SlingHttpServletRequest request;

	/** The certifications image. */
	@Inject
	@Via("resource")
	private String certificationsImage;

	/** The certifications header. */
	@Inject
	@Via("resource")
	private String certificationsHeader;

	/** The upload documents cta label. */
	@Inject
	@Via("resource")
	private String uploadDocumentsCtaLabel;

	/** The no certificate text. */
	@Inject
	@Via("resource")
	private String noCertificateText;

	/** The upload instruction label. */
	@Inject
	@Via("resource")
	private String uploadInstructionLabel;

	/** The upload documents modal header. */
	@Inject
	@Via("resource")
	private String uploadDocumentsModalHeader;

	/** The file type text. */
	@Inject
	@Via("resource")
	private String fileTypeText;

	/** The drop documents text. */
	@Inject
	@Via("resource")
	private String dropDocumentsText;

	/** The choose files cta label. */
	@Inject
	@Via("resource")
	private String chooseFilesCtaLabel;

	/** The no linked address text. */
	@Inject
	@Via("resource")
	private String noLinkedAddressText;

	/** The select address cta label. */
	@Inject
	@Via("resource")
	private String selectAddressCtaLabel;

	/** The change linked address link label. */
	@Inject
	@Via("resource")
	private String changeLinkedAddressLinkLabel;

	/** The address linked text. */
	@Inject
	@Via("resource")
	private String addressLinkedText;

	/** The favorite shipping address text. */
	@Inject
	@Via("resource")
	private String favoriteShippingAddressText;

	/** The favIcon. */
	@Inject
	@Via("resource")
	private String favIcon;

	/** The favIconAltText. */
	@Inject
	@Via("resource")
	private String favIconAltText;
	
	/** The noFavouriteDescription. */
	@Inject
	@Via("resource")
	private String noFavouriteDescription;

	/** The upload success text. */
	@Inject
	@Via("resource")
	private String uploadSuccessText;
	
	/** The close icon. */
	@Inject
	@Via("resource")
	private String closeIcon;
	
	/** The close icon alt text. */
	@Inject
	@Via("resource")
	private String closeIconAltText;
	
	/** The location icon. */
	@Inject
	@Via("resource")
	private String locationIcon;
	
	/** The location icon alt text. */
	@Inject
	@Via("resource")
	private String locationIconAltText;
	
	/** The select address title. */
	@Inject
	@Via("resource")
	private String selectAddressTitle;
	
	/** The search address title. */
	@Inject
	@Via("resource")
	private String searchAddressTitle;
	
	/** The search place holder. */
	@Inject
	@Via("resource")
	private String searchCertificatePlaceHolder;
	
	/** The withLabel. */
	@Inject
	@Via("resource")
	private String withLabel;
	
	/** The rowsLabel. */
	@Inject
	@Via("resource")
	private String rowsLabel;
	
	/** The andLabel. */
	@Inject
	@Via("resource")
	private String andLabel;
	
	/** The colLabel. */
	@Inject
	@Via("resource")
	private String colLabel;
	
	/** The file type multi field. */
	@Inject
	@Via("resource")
	private List<Resource> fileTypeMultiField;

	/** The bdb api endpoint service. */
	@Inject
	private BDBApiEndpointService bdbApiEndpointService;

	/** The search place holder. */
	@Inject
	@Via("resource")
	private String globalFileUploadPath;

	/**
	 * The resource resolver.
	 */
	@SlingObject
	private ResourceResolver resourceResolver;

	/** The certifications labels. */
	private String certificationsLabels;

	/** The certificates config. */
	private String certificatesConfig;
	
	/** The certifications upload labels. */
	private String uploadCertificationLabels;
	
	/** The certifications upload config. */
	private String uploadCertificationConfig;
	
	/** The file types list. */
	private List<UploadCertificationFileTypeModel> fileTypesList = new ArrayList<>();

	/** The hybris site id. */
	private String hybrisSiteId;

	/** The fileUploadLabels. */
	private String fileUploadLabels;
	
	/** The Download Template link Label. */
	@Inject
	@Via("resource")
	private String downloadRUOCTA;

	/** The Download url  */
	@Inject
	@Via("resource")
	private String downloadRUOUrl;

	/** The downloadInfo. */
	@Inject
	@Via("resource")
	private String downloadRUOInfo;
	

	/**
	 * Creates the Certification Labels and Config.
	 */
	@PostConstruct
	void init() {
		log.debug("Inside Certifications Init Method");

		hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		ExcludeUtil excludeUtilObject = new ExcludeUtil();

		populateFilesTypesList();
		setCertificationsLabels(excludeUtilObject);
		setCertificatesConfig(excludeUtilObject);
		setUploadCertificationLabels(excludeUtilObject);
		setUploadCertificationConfig(excludeUtilObject);
		setFileUploadLabels();
	}

	/**
	 * Sets the certifications labels.
	 *
	 * @param excludeUtilObject the exclude util object
	 */
	private void setCertificationsLabels(ExcludeUtil excludeUtilObject) {

		certificationsLabels = StringUtils.EMPTY;
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();

		String certificationType = CommonHelper.getLabel(CommonConstants.CERTIFICATION_TYPE_LABEL, currentPage);
		String documentName = CommonHelper.getLabel(CommonConstants.DOCUMENT_NAME_LABEL, currentPage);
		String expiryDate = CommonHelper.getLabel(CommonConstants.EXPIRY_DATE_LABEL, currentPage);
		String linkedAddress = CommonHelper.getLabel(CommonConstants.LINKED_ADDRESS_LABEL, currentPage);
		String remove = CommonHelper.getLabel(CommonConstants.REMOVE_LABEL, currentPage);
		String shipToNumber = CommonHelper.getLabel(CommonConstants.SHIP_TO_NUMBER_LABEL, currentPage);
		String status = CommonHelper.getLabel(CommonConstants.STATUS_LABEL, currentPage);
		
		AccountManagementCertificationsLabelsBean accountManagementCertificationsLabelsBean = new AccountManagementCertificationsLabelsBean();
		accountManagementCertificationsLabelsBean.setCertificationsHeader(certificationsHeader);
		accountManagementCertificationsLabelsBean.setCertificationsImage(certificationsImage);
		accountManagementCertificationsLabelsBean.setCertificationType(certificationType);
		accountManagementCertificationsLabelsBean.setDocumentName(documentName);
		accountManagementCertificationsLabelsBean.setExpiryDate(expiryDate);
		accountManagementCertificationsLabelsBean.setLinkedAddress(linkedAddress);
		accountManagementCertificationsLabelsBean.setNoCertificateText(noCertificateText);
		accountManagementCertificationsLabelsBean.setRemove(remove);
		accountManagementCertificationsLabelsBean.setShipToNumber(shipToNumber);
		accountManagementCertificationsLabelsBean.setStatusFields(status);		
		accountManagementCertificationsLabelsBean.setUploadDocumentsCtaLabel(uploadDocumentsCtaLabel);
		accountManagementCertificationsLabelsBean.setUploadInstructionLabel(uploadInstructionLabel);
		accountManagementCertificationsLabelsBean.setUploadSuccessText(uploadSuccessText);
		accountManagementCertificationsLabelsBean.setRowsLabel(rowsLabel);
		accountManagementCertificationsLabelsBean.setWithLabel(withLabel);
		accountManagementCertificationsLabelsBean.setAndLabel(andLabel);
		accountManagementCertificationsLabelsBean.setColLabel(colLabel);

		AccountManagementCertificationsModelLabels accountManagementCertificationsModelLabels = new AccountManagementCertificationsModelLabels(accountManagementCertificationsLabelsBean);
		certificationsLabels = json.toJson(accountManagementCertificationsModelLabels);
	}

	/**
	 * Sets the certificates config.
	 *
	 * @param excludeUtilObject the new certificates config
	 */
	private void setCertificatesConfig(ExcludeUtil excludeUtilObject) {

		JsonElement getUserCertifictesConfig;
		JsonElement deleteUserCertifictesConfig;
		
		certificatesConfig = StringUtils.EMPTY;
		 
		if (bdbApiEndpointService != null) {
			String hybrisGetListOfUserCertificationsEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getListOfUserCertificationsEndpoint(), CommonConstants.HYBRIS_SITE_LITERAL, hybrisSiteId);		
			String hybrisDeleteCertificateEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.deleteCertificateEndpoint(), CommonConstants.HYBRIS_SITE_LITERAL, hybrisSiteId);
	
			Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
					.addSerializationExclusionStrategy(excludeUtilObject).create();
	
			Payload getUserCertifictesPayload = new Payload(hybrisGetListOfUserCertificationsEndpoint, HttpConstants.METHOD_GET);
			Payload deleteUserCertifictesPayload = new Payload(hybrisDeleteCertificateEndpoint, HttpConstants.METHOD_POST);
	
			PayloadConfig getUserCertifictesPayloadConfig = new PayloadConfig(getUserCertifictesPayload);
			getUserCertifictesConfig = json.toJsonTree(getUserCertifictesPayloadConfig);
	
			PayloadConfig deleteUserCertifictesPayloadConfig = new PayloadConfig(deleteUserCertifictesPayload);
			deleteUserCertifictesConfig = json.toJsonTree(deleteUserCertifictesPayloadConfig);
			AccountManagementCertificationsConfig accountManagementCertificationsConfig = new AccountManagementCertificationsConfig(
					getUserCertifictesConfig, deleteUserCertifictesConfig, null, null);
			certificatesConfig = json.toJson(accountManagementCertificationsConfig);
		}
	}

	/**
	 * Sets the certifications upload labels.
	 *
	 * @param excludeUtilObject the exclude util object
	 */
	private void setUploadCertificationLabels(ExcludeUtil excludeUtilObject) {
		
		AddressSelectionLabels addressSelectionJsonLabels;
		uploadCertificationLabels = StringUtils.EMPTY;
		
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		
		String shippingAddress = CommonHelper.getLabel(CommonConstants.SHIPPING_ADDRESS_LABEL, currentPage);
		String defaultLabel = CommonHelper.getLabel(CommonConstants.DEFAULT_LABEL, currentPage);
		String shipToNumber = CommonHelper.getLabel(CommonConstants.SHIP_TO_NUMBER_LABEL, currentPage);
		String linkedAddress = CommonHelper.getLabel(CommonConstants.LINKED_ADDRESS_LABEL, currentPage);
		String cancel = CommonHelper.getLabel(CommonConstants.CANCEL_LABEL, currentPage);
		String confirm = CommonHelper.getLabel(CommonConstants.CONFIRM_LABEL, currentPage);
		
		AddressSelectionLabelsBean addressLabelsBean = new AddressSelectionLabelsBean();
		addressLabelsBean.setShippingAddressLabel(shippingAddress);
		addressLabelsBean.setDefaultLabel(defaultLabel);
		addressLabelsBean.setShipToNumberLabel(shipToNumber);
		
		addressSelectionJsonLabels = new AddressSelectionLabels(addressLabelsBean);
		JsonElement addressLabelsJson = json.toJsonTree(addressSelectionJsonLabels);
		
		AddressSelectionLabelsBean searchAddressBean = new AddressSelectionLabelsBean();
		searchAddressBean.setSearchAddressTitle(searchAddressTitle);
		searchAddressBean.setSearchPlaceHolder(searchCertificatePlaceHolder);
		
		addressSelectionJsonLabels = new AddressSelectionLabels(searchAddressBean);
		JsonElement searchAddressJson = json.toJsonTree(addressSelectionJsonLabels);
		
		AddressSelectionLabelsBean addressSelectionBean = new AddressSelectionLabelsBean();
		addressSelectionBean.setNoLinkedAddressText(noLinkedAddressText);
		addressSelectionBean.setLocationIcon(locationIcon);
		addressSelectionBean.setLocationIconAltText(locationIconAltText);
		addressSelectionBean.setSelectAddressCtaLabel(selectAddressCtaLabel);
		addressSelectionBean.setSelectAddressTitle(selectAddressTitle);
		addressSelectionBean.setLinkedAddress(linkedAddress);
		addressSelectionBean.setChangeLinkedAddressLinkLabel(changeLinkedAddressLinkLabel);
		addressSelectionBean.setAddressLabels(addressLabelsJson);
		addressSelectionBean.setSearchAddress(searchAddressJson);
		addressSelectionBean.setFavoriteShippingAddressText(favoriteShippingAddressText);
		addressSelectionBean.setFavIcon(favIcon);
		addressSelectionBean.setFavIconAltTxt(favIconAltText);
		addressSelectionBean.setNoFavouriteDescription(noFavouriteDescription);
		addressSelectionBean.setSaveCta(CommonHelper.getLabel(CommonConstants.SAVE, currentPage));

		addressSelectionJsonLabels = new AddressSelectionLabels(addressSelectionBean);
		JsonElement addressSelectionLabels = json.toJsonTree(addressSelectionJsonLabels);
		
		AccountManagementCertificationsLabelsBean accountManagementCertificationsLabelsBean = new AccountManagementCertificationsLabelsBean();
		accountManagementCertificationsLabelsBean.setAddressLinkedText(addressLinkedText);
		accountManagementCertificationsLabelsBean.setCancel(cancel);
		accountManagementCertificationsLabelsBean.setChooseFilesCtaLabel(chooseFilesCtaLabel);
		accountManagementCertificationsLabelsBean.setConfirm(confirm);
		accountManagementCertificationsLabelsBean.setDropDocumentsText(dropDocumentsText);
		accountManagementCertificationsLabelsBean.setFileTypeText(fileTypeText);
		accountManagementCertificationsLabelsBean.setAddressSelectionLabels(addressSelectionLabels);
		accountManagementCertificationsLabelsBean.setUploadDocumentsModalHeader(uploadDocumentsModalHeader);
		accountManagementCertificationsLabelsBean.setFileTypesList(fileTypesList);
		accountManagementCertificationsLabelsBean.setCloseIcon(closeIcon);
		accountManagementCertificationsLabelsBean.setCloseIconAltText(closeIconAltText);
		
		accountManagementCertificationsLabelsBean.setDownloadRUOCTA(downloadRUOCTA);
		accountManagementCertificationsLabelsBean.setDownloadRUOInfo(downloadRUOInfo);
		accountManagementCertificationsLabelsBean.setDownloadRUOUrl(downloadRUOUrl);
		
		AccountManagementCertificationsModelLabels modelUploadCertificationLabels = new AccountManagementCertificationsModelLabels(accountManagementCertificationsLabelsBean);
		uploadCertificationLabels = json.toJson(modelUploadCertificationLabels);
	}

	/**
	 * Sets the certifications upload config.
	 *
	 * @param excludeUtilObject the exclude util object
	 */
	private void setUploadCertificationConfig(ExcludeUtil excludeUtilObject) {
		JsonElement uploadCertificatesConfig;
		JsonElement getAddressConfig;
		
		uploadCertificationConfig = StringUtils.EMPTY;
		
		if (bdbApiEndpointService != null) {
			String hybrisUploadRuoCertificateEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.uploadRuoCertificateEndpoint(), CommonConstants.HYBRIS_SITE_LITERAL, hybrisSiteId);
			String hybrisGetAddressesEndpoint = StringUtils.replace(bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getAddressWithNoRuoEndpoint(), CommonConstants.HYBRIS_SITE_LITERAL, hybrisSiteId);
	
			Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
					.addSerializationExclusionStrategy(excludeUtilObject).create();
	
			Payload uploadCertificatesPayload = new Payload(hybrisUploadRuoCertificateEndpoint, HttpConstants.METHOD_POST);
			Payload getAddressesPayload = new Payload(hybrisGetAddressesEndpoint, HttpConstants.METHOD_GET);
			
			PayloadConfig uploadCertificatesPayloadConfig = new PayloadConfig(uploadCertificatesPayload);
			uploadCertificatesConfig = json.toJsonTree(uploadCertificatesPayloadConfig);
			
			PayloadConfig getAddressPayloadConfig = new PayloadConfig(getAddressesPayload);
			getAddressConfig = json.toJsonTree(getAddressPayloadConfig);
			
			AccountManagementCertificationsConfig modelUploadCertificationConfig = new AccountManagementCertificationsConfig(null, null, uploadCertificatesConfig, getAddressConfig);
			uploadCertificationConfig = json.toJson(modelUploadCertificationConfig);
		}
	}
	
	/**
	 * Populate files types list.
	 */
	private void populateFilesTypesList() {
		if (fileTypeMultiField != null && !fileTypeMultiField.isEmpty()) {

			for (Resource resource : fileTypeMultiField) {
				UploadCertificationFileTypeModel fileTypeProperty = resource.adaptTo(UploadCertificationFileTypeModel.class);
				fileTypesList.add(fileTypeProperty);
			}
		}		
	}

	/**
	 * Populate globalFileUploadModel.
	 */
	private void setFileUploadLabels(){
		GlobalFileUploadModel globalFileUploadModel = CommonHelper.getGlobalFileUploadModel(globalFileUploadPath, resourceResolver, GLOBAL_FILE_UPLOAD_RESOURCE_TYPE);
		if(null != globalFileUploadModel)
		{
			fileUploadLabels = GlobalFileUploadUtil.createGlobalFileUploadLabels(globalFileUploadModel,null);
		}
	}

	/**
	 * Gets the certifications labels.
	 *
	 * @return the certifications labels
	 */
	@Override
	public String getCertificationsLabels() {
		return certificationsLabels;
	}

	/**
	 * Gets the certificates config.
	 *
	 * @return the certificates config
	 */
	@Override
	public String getCertificatesConfig() {
		return certificatesConfig;
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
	 * Gets the certifications upload labels.
	 *
	 * @return the certifications upload labels
	 */
	@Override
	public String getUploadCertificationLabels() {
		return uploadCertificationLabels;
	}

	/**
	 * Gets the certifications upload config.
	 *
	 * @return the certifications upload config
	 */
	@Override
	public String getUploadCertificationConfig() {
		return uploadCertificationConfig;
	}

	/**
	 * Gets the getFileUploadLabels.
	 *
	 * @return the getFileUploadLabels
	 */
	@Override
	public String getFileUploadLabels(){
		return fileUploadLabels;
	}
}