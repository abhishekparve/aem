package com.bdb.aem.core.models;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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

import com.bdb.aem.core.pojo.Payload;
import com.bdb.aem.core.pojo.RepToolConfig;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.ExcludeUtil;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * The Class RepToolModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FindYourRepModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(FindYourRepModel.class);

	/** The heading */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String heading;

	/** The Sub Heading */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String subHeading;

	/** The description. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String description;

	/** The mandatoryNote. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String mandatoryNote;

	/** The zip code label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String zipCodeLabel;

	/** The zipcode error */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String zipCodeError;

	/** The area of interestLabel */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String areaOfInterestLabel;

	/** The area of interest error. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String areaOfInterestError;

	/** The tncNote */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String tncNote;

	/** The submitBtnLabel */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String submitBtnLabel;

	/** The areaOfInterestLabels. */
	@Inject
	@Via("resource")
	public List<AreaOfInterestDetailModel> areaOfInterestLabels;

	/** The sales rep heading */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String salesRepHeading;

	/** The Sales Rep Sub Heading */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String salesRepSubHeading;

	/** The name lable */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String nameLabel;

	/** The area of speciality label */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String areaofSpecialtyLabel;

	/** The phone label */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String phoneLabel;

	/** The contact me btn lable */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String contactMeBtnLabel;
	
	/** The sales rep personal page heading */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String salesRepPersonalPageHeading;

	/** The Sales Rep personal page Sub Heading */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String salesRepPersonalPageSubHeading;
	
	/** The Sales Rep personal page Sub Heading */
	@Inject
	@Via("resource")
	private long marketoFormId;
	
	/** The Back to Sales Rep home page button label */
	@Inject
	@Via("resource")
	private String backToRepHomePageButton;
	
	/** The Back to Sales Rep personal page button label */
	@Inject
	@Via("resource")
	private String backToRepPageButton;



	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** CurrentPage. */
	@Inject
	private Page currentPage;

	/**
	 * The resource resolver.
	 */
	@SlingObject
	private ResourceResolver resourceResolver;

	/** The bdb search endpoint service. */
	@Inject
	BDBSearchEndpointService bdbSearchEndpointService;

	/**
	 * The bdb api endpoint service.
	 */
	@Inject
	private BDBApiEndpointService bdbApiEndpointService;

	private String fyrWelcomePageLabels;

	private String fyrSalesRepListPageLabels;

	/** The rep tool config. */
	String repToolConfig;

	/** The fyr sales rep list page config. */
	String fyrSalesRepListPageConfig;
	
	String fyrToolLabels;
	
	String fyrSalesRepCardLabels;
	String fyrContacttSalesRepPageLabels;

	String salesRepListPayload;

	/** The Hybris Site ID. */
	private String hybrisSiteId;

	/**
	 * Forms json out of all the injected values to provide it to the FE React
	 * component.
	 */
	@PostConstruct
	protected void init() {

		if (StringUtils.isNotEmpty(tncNote)) {
			try {
				tncNote = CommonHelper.HandleRTEAnchorLink(tncNote, externalizerService, resourceResolver,
						StringUtils.EMPTY);
			} catch (IOException e) {
				logger.error("Error while processing RTE text", e);
			}
		}
		// Set the Hybris Site Id
		hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		String imageRootDir = bdbApiEndpointService.getImageRootDir();
		String salesRepListEndpoint = StringUtils.replace(
				bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getHybrisSalesRepListEndpoint(),
				CommonConstants.BASE_SITE_ID, hybrisSiteId);
		Payload emailValidationPayload = new Payload(salesRepListEndpoint, HttpConstants.METHOD_POST);

		ExcludeUtil excludeUtilObject = new ExcludeUtil();
		Gson config = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();
		RepToolConfig repToolConfigObj = new RepToolConfig(emailValidationPayload, imageRootDir);

		repToolConfig = config.toJson(repToolConfigObj);
		
		fyrWelcomePageLabels = repToolLabels();
		fyrSalesRepListPageLabels = salesRepLabels();
		fyrSalesRepCardLabels = salesRepCardLabels();
		fyrToolLabels = findYourRepToolLabels();
		fyrContacttSalesRepPageLabels = getSalesRepPersonalPageLabels();

	}

	/**
	 * This method forms the json for credit card labels.
	 *
	 * @return the label json
	 */
	public String repToolLabels() {

		JsonObject labels = new JsonObject();
		labels.addProperty(CommonConstants.HEADING, heading);
		labels.addProperty(CommonConstants.SUB_HEADING, subHeading);
		labels.addProperty(CommonConstants.REP_TOOL_DESCRIPTION, description);
		labels.addProperty(CommonConstants.MANDATORY_NOTE, mandatoryNote);
		labels.addProperty(CommonConstants.ADD_ADDRESS_ZIPCODE_LABEL, zipCodeLabel);
		labels.addProperty(CommonConstants.ZIP_CODE_ERROR, zipCodeError);
		labels.addProperty(CommonConstants.AREA_OF_INTEREST_ERROR, areaOfInterestError);
		labels.addProperty(CommonConstants.AREA_OF_INTEREST_LABEL, areaOfInterestLabel);
		labels.addProperty(CommonConstants.TNC_NOTE, tncNote);
		labels.addProperty(CommonConstants.SUBMIT_BTN, submitBtnLabel);

		if (CollectionUtils.isNotEmpty(areaOfInterestLabels)) {
			JsonArray cards = new JsonArray();
			for (AreaOfInterestDetailModel areaOfInterestLabel : areaOfInterestLabels) {
				JsonObject card = new JsonObject();
				card.addProperty(CommonConstants.ID, areaOfInterestLabel.getAreaOfInterestLabel().replaceAll("\\s+", "_").toLowerCase());
				card.addProperty(CommonConstants.REP_LABEL, areaOfInterestLabel.getAreaOfInterestLabel());
				cards.add(card);
			}
			labels.add(CommonConstants.AREA_OF_INTEREST_LIST, cards);
		}
		return labels.toString();
	}

	/**
	 * This method forms the json for sales rep labels.
	 *
	 * @return the label json
	 */
	public String salesRepLabels() {

		JsonObject salesRepJsonLabel = new JsonObject();
		salesRepJsonLabel.addProperty(CommonConstants.HEADING, salesRepHeading);
		salesRepJsonLabel.addProperty(CommonConstants.SUB_HEADING, salesRepSubHeading);
		salesRepJsonLabel.addProperty(CommonConstants.BACK_TO_REP_HOME_PAGE_LABEL, backToRepHomePageButton);
		return salesRepJsonLabel.toString();
	}
	
	/**
	 * This method forms the json for sales rep labels.
	 *
	 * @return the label json
	 */
	public String salesRepCardLabels() {

		JsonObject salesRepCardJsonLabel = new JsonObject();
		salesRepCardJsonLabel.addProperty(CommonConstants.SALES_REP_NAME_LABEL, nameLabel);
		salesRepCardJsonLabel.addProperty(CommonConstants.AREA_OF_SPECIALITY_LABEL, areaofSpecialtyLabel);
		salesRepCardJsonLabel.addProperty(CommonConstants.SALES_REP_PHONE_LABEL, phoneLabel);
		salesRepCardJsonLabel.addProperty(CommonConstants.CONTACT_ME_BUTTON_LABEL, contactMeBtnLabel);
		return salesRepCardJsonLabel.toString();
	}
	
	/**
	 * This method forms the json for rep tool labels.
	 *
	 * @return the label json
	 */
	public String findYourRepToolLabels() {
		JsonObject fyrJsonLabel = new JsonObject();
		fyrJsonLabel.addProperty(areaOfInterestLabel, areaOfInterestError);
		fyrJsonLabel.addProperty(areaOfInterestLabel, areaOfInterestError);
		fyrJsonLabel.addProperty(areaOfInterestLabel, areaOfInterestError);
		return areaOfInterestError;
		
	}
	
	/**
	 * This method forms the json for sales rep labels.
	 *
	 * @return the label json
	 */
	public String getSalesRepPersonalPageLabels() {

		JsonObject salesRepJsonLabel = new JsonObject();
		salesRepJsonLabel.addProperty(CommonConstants.HEADING, salesRepPersonalPageHeading);
		salesRepJsonLabel.addProperty(CommonConstants.SUB_HEADING, salesRepPersonalPageSubHeading);
		salesRepJsonLabel.addProperty(CommonConstants.MARKETO_FOMR_ID, marketoFormId);
		salesRepJsonLabel.addProperty(CommonConstants.BACK_TO_REP_PERSONAL_PAGE_LABEL, backToRepPageButton);
		return salesRepJsonLabel.toString();
	}
	
	/**
	 * @return the heading
	 */
	public String getHeading() {
		return heading;
	}

	/**
	 * @return the subHeading
	 */
	public String getSubHeading() {
		return subHeading;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the mandatoryNote
	 */
	public String getMandatoryNote() {
		return mandatoryNote;
	}

	/**
	 * @return the zipCodeLabel
	 */
	public String getZipCodeLabel() {
		return zipCodeLabel;
	}

	/**
	 * @return the zipCodeError
	 */
	public String getZipCodeError() {
		return zipCodeError;
	}

	/**
	 * @return the areaOfInterestLabel
	 */
	public String getAreaOfInterestLabel() {
		return areaOfInterestLabel;
	}

	/**
	 * @return the areaOfInterestError
	 */
	public String getAreaOfInterestError() {
		return areaOfInterestError;
	}

	/**
	 * @return the tncNote
	 */
	public String getTncNote() {
		return tncNote;
	}

	/**
	 * @return the submitBtnLabel
	 */
	public String getSubmitBtnLabel() {
		return submitBtnLabel;
	}

	public String getFyrWelcomePageLabels() {
		return fyrWelcomePageLabels;
	}

	/**
	 * @param salesRepListPayload the salesRepListPayload to set
	 */
	public void setSalesRepListPayload(String salesRepListPayload) {
		this.salesRepListPayload = salesRepListPayload;
	}

	/**
	 * @return the fyrWelcomePageConfig
	 */
	public String getRepToolConfig() {
		return repToolConfig;
	}

	/**
	 * @return the fyrSalesRepListPageConfig
	 */
	public String getFyrSalesRepListPageConfig() {
		return fyrSalesRepListPageConfig;
	}

	/**
	 * @return the salesRepHeading
	 */
	public String getSalesRepHeading() {
		return salesRepHeading;
	}

	/**
	 * @return the salesRepSubHeading
	 */
	public String getSalesRepSubHeading() {
		return salesRepSubHeading;
	}

	/**
	 * @return the nameLabel
	 */
	public String getNameLabel() {
		return nameLabel;
	}

	/**
	 * @return the areaofSpecialtyLabel
	 */
	public String getAreaofSpecialtyLabel() {
		return areaofSpecialtyLabel;
	}

	/**
	 * @return the phoneLabel
	 */
	public String getPhoneLabel() {
		return phoneLabel;
	}

	/**
	 * @return the contactMeBtnLabel
	 */
	public String getContactMeBtnLabel() {
		return contactMeBtnLabel;
	}

	/**
	 * @return the fyrSalesRepListPageLabels
	 */
	public String getFyrSalesRepListPageLabels() {
		return fyrSalesRepListPageLabels;
	}
	
	/**
	 * @return the fyrToolLabels
	 */
	public String getFyrToolLabels() {
		return fyrToolLabels;
	}

	/**
	 * @return the fyrSalesRepCardLabels
	 */
	public String getFyrSalesRepCardLabels() {
		return fyrSalesRepCardLabels;
	}
	

	/**
	 * @return the salesRepPersonalPageHeading
	 */
	public String getSalesRepPersonalPageHeading() {
		return salesRepPersonalPageHeading;
	}

	/**
	 * @return the salesRepPersonalPageSubHeading
	 */
	public String getSalesRepPersonalPageSubHeading() {
		return salesRepPersonalPageSubHeading;
	}


	/**
	 * @return the fyrContacttSalesRepPageLabels
	 */
	public String getFyrContacttSalesRepPageLabels() {
		return fyrContacttSalesRepPageLabels;
	}


	/**
	 * @return the marketoFormId
	 */
	public long getMarketoFormId() {
		return marketoFormId;
	}

	/**
	 * @return the backToRepHomePageButton
	 */
	public String getBackToRepHomePageButton() {
		return backToRepHomePageButton;
	}

	/**
	 * @return the backToRepPageButton
	 */
	public String getBackToRepPageButton() {
		return backToRepPageButton;
	}
	
	

}