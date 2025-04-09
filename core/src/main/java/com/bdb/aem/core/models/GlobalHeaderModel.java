package com.bdb.aem.core.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ResourcePath;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.Getter;

/**
 * The Class GlobalHeaderModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GlobalHeaderModel {

	/** The request. */
	@Self
	private SlingHttpServletRequest request;

	/** The current page. */
	@Inject
	private Page currentPage;

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(GlobalHeaderModel.class);

	/** The url logo action. */
	@Inject
	@Via("resource")
	private String urlLogoAction;

	/** The url logo. */
	@Inject
	@Via("resource")
	private String urlLogo;

	/** The url logo sticky. */
	@Inject
	@Via("resource")
	private String urlLogoSticky;

	/** The url cart action. */
	@Inject
	@Via("resource")
	private String urlCartAction;

	/** The url cart. */
	@Inject
	@Via("resource")
	private String urlCart;

	/** The order lookup icon alt text. */
	@Inject
	@Via("resource")
	private String orderLookupIconAltText;

	/** The order lookup icon url. */
	@Inject
	@Via("resource")
	private String orderLookupIconUrl;

	/** The order lookup label. */
	@Inject
	@Via("resource")
	private String orderLookupLabel;

	/** The order lookup url. */
	@Inject
	@Via("resource")
	private String orderLookupUrl;

	/** The url country. */
	@Inject
	@Via("resource")
	private String urlCountry;

	/** The change label url. */
	private String changeLabelUrl;

	/** The url icon. */
	@Inject
	@Via("resource")
	private String urlIcon;

	/** The register url. */
	private String registerUrl;
	
	/** The country List Label. */
	private String myLabels;
	
	/** The hidden pages path list Label. */
	private String pathList;

	/** The profile menu. */
	@Inject
	@Via("resource")
	private List<BDBProfileMenuMutlifieldModel> profileMenu;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

    /** The resource resolver factory. */
    @Inject
    ResourceResolverFactory resourceResolverFactory;

	/** The resource resolver. */
	@SlingObject
	ResourceResolver resourceResolver;

	/** The bdb api endpoint service. */
	@Inject
	private BDBApiEndpointService bdbApiEndpointService;

	/** The sign in url. */
	private String signInUrl;

	/** The profile menu array. */
	private JsonArray profileMenuArray = new JsonArray();

	/** The Old Browser Heading. */
	@Inject
	@Via("resource")
	@Default(values = "Old Browser")
	@Getter
	private String heading;

	/** The Browser Compatible Warning Message. */
	@Inject
	@Via("resource")
	@Default(values = "For the best web browsing experience, please use Chrome, Safari or Firefox, minimum versions 77.0.3865, 12.1.2 and 68, respectively.")
	@Getter
	private String warningMsg;

	/** The Browser Compatible Button label */
	@Inject
	@Via("resource")
	@Default(values = "Okay")
	@Getter
	private String ctaLable;


	/** ChromeMinimumVersion */
	@Inject
	@Via("resource")
	@Getter
	private String chromeMinimumVersion;

	/** IE Minimum Version */
	@Inject
	@Via("resource")
	@Getter
	private String IEMinimumVersion;

	/** Safari Minimum Version*/
	@Inject
	@Via("resource")
	@Getter
	private String safariMinimumVersion;

	/** Firefox Minimum Version */
	@Inject
	@Via("resource")
	@Getter
	private String firefoxMinimumVersion;

	/** The Product unavailable Message */
	@Inject
	@Via("resource")
	@Default(values = "This product is temporarily unavailable.")
	@Getter
	private String productUnavailableMsg;
	
	/** The Product discontinued Message */
	@Inject
	@Via("resource")
	@Default(values = "This product has been discontinued.")
	@Getter
	private String productDiscontinuedMsg;
	
	@Inject
	@Via("resource")
	private String countryDetectionTitle;
	
	@Inject
	@Via("resource")
	private String countryDetectionDescription;
	
	@Inject
	@Via("resource")
	private String redirectButton;
	
	@Inject
	@Via("resource")
	private String stayHereButton;
	
	@Inject
	@Via("resource")
	private String modalTitle;
	
	@Inject
	@Via("resource")
	private String modalDescription;
	
	@Inject
	@Via("resource")
	private String cancelCTALabel;
	
	@Inject
	@Via("resource")
	private String reactivateCTALabel;
	
	/** The Reactivation URL. */
	@Inject
	@Via("resource")
	private String reactivationURL;
	
	/** The Continue Shopping Modal Title. */
	@Inject
	@Via("resource")
	private String continueShoppingModalTitle;
	
	/** The Continue Shopping Modal Description. */
	@Inject
	@Via("resource")
	private String continueShoppingModalDescription;
	
	/** The Proceed To Checkout Btn Label. */
	@Inject
	@Via("resource")
	private String proceedToCheckoutBtnLabel;
	
	/** The Close Modal Btn Label. */
	@Inject
	@Via("resource")
	private String closeModalBtnLabel;
	
	/** The Expired Quote Modal Title. */
	@Inject
	@Via("resource")
	private String expiredQuoteModalTitle;
	
	/** The Expired Quote Modal Description. */
	@Inject
	@Via("resource")
	private String expiredQuoteModalDescription;
	
	/** The Clear Cart Btn Label. */
	@Inject
	@Via("resource")
	private String clearCartBtnLabel;

	/** The Warning Message */
	@Inject
	@Via("resource")
	@Default(values = "You are now leaving the BD Biosciences website. The site you are about to visit is operated by a third party. The link to this site neither makes nor implies any representation or warranty for any products or services offered on a third-party site and is intended only to enable convenient access to the third-party site and for no other purpose. Do you want to continue?")
	@Getter
	private String warningMessage;

	/** The Yes Button */
	@Inject
	@Via("resource")
	@Default(values = "Yes")
	@Getter
	private String yesButton;

	/** The Cancel Button */
	@Inject
	@Via("resource")
	@Default(values = "Cancel")
	@Getter
	private String noButton;

	/** The sticky Label. */
	@Inject
	@Via("resource")
	private String stickyFormNameLabel;

	/** The feedback form url */
	@Inject
	@Via("resource")
	private String feedBackFormURL;

	/** The canonicalUrl. */
	private String canonicalUrl;

	/** Check for home page. */
	private boolean isHomePage;
	
	/** Check for sameEventDate. */
	private boolean isSameEventDate;

	/** The content path. */
	private String currentPageContentPath;

    /** The homepage title. */
	private String homePageTitle;

    /** The homepage description. */
	private String homePageDescription;

	/** The language name. */
	private String languageName;

	/** The country name. */
	private String countryName;

	/** The PDP page path. */
	private String getPdpPath;

	/** The PDP product title. */
	private String getPdpTitle;

	/** The siteSearchJson data. */
	private String siteSearchJson;

	/** The Global Alert Banner Resource */
	@ResourcePath(path="/content/experience-fragments/bdb/na/us/en-us/global-header/master/jcr:content/root/globalheader")
	@Via("resource")
	Resource alertResource;

	/** The Global Alert Banner checkbox */
	private String allowAlertBanner;

	/** The Global Alert Banner Homepage Switch */
	private String homePageAlert;

	/** The Global Alert Banner Popup Label */
	private String msgPopupLabelAlert;

	/** The Global Alert Banner Icon Image Path */
	private String alertIconAlert;

	/** The Global Alert Banner Icon alternate text */
	private String iconAltTextAlert;
	
	/** The Global Alert Banner description*/
	private String alertDescAlert;

	/** The Global Alert Banner description One*/
	private String alertDescOne;
	
	/** The Global Alert Banner description Two*/
	private String alertDescTwo;
	
	/** The Global Alert Banner Icon alternate text */
	private String scheduledUnpublishDate;

	/** The Global Alert Banner Start Date and Time */
	private String isStartDateTime;

	/** The Global Alert Banner End Date and Time */
	private String isEndDateTime;

	/** The Global Alert Banner Desktop Label */
	private String desktopLabelAlert;

	/** The Global Alert Banner Mobile Label */
	private String mobileLabelAlert;

	/** The Global Alert Banner Information Link */
	private String alertInfoLinkAlert;

	/** The Global Alert Banner Second Icon Image Path */
	private String alertIconOptionalAlert;

	/** The Global Alert Banner Second Icon Alternate Text */
	private String iconAltTextOptionalAlert;

	/** The Global Alert Banner Second Description */
	private String alertDescOptionalAlert;

	/** The Global Alert Banner Desktop Label */
	private String desktopLabelOptionalAlert;

	/** The Global Alert Banner Second Mobile Label */
	private String mobileLabelOptionalAlert;

	/** The Global Alert Banner Second Information Link */
	private String alertInfoLinkOptionalAlert;
	
	/** The Constant STANDARD_DATE_FORMAT. */
	private static final String STANDARD_DATE_FORMAT ="yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    
    /** The Constant START_DATE_FORMAT. */
	private static final String DAY_MONTH_DATE_YEAR_FORMAT = "EEEE, MMMM dd, yyyy";
	
	/** The Global Alert Banner Start Time */
	private String alertBannerStartTime;
	
	/** The Global Alert Banner End Time */
	private String alertBannerEndDate;
	
	/** The Global Alert Banner Start Date */
	private String alertBannerStartDate;
	
	/** The Global Alert Banner End Date */
	private String alertBannerEndTime;
	
	/** The Global Alert schedules time only */
	private String scheduledTimings;
	
    /**
	 * The Init.
	 */
	@PostConstruct
	protected void init() {

		registerUrl = CommonHelper.getLabel("registerUrl", currentPage);
		logger.debug("Initialize method");
		signInUrl = CommonHelper.getSignInUrl(bdbApiEndpointService, externalizerService, resourceResolver, currentPage);
		String customRunMode = bdbApiEndpointService.getCustomRunMode();
		if( StringUtils.isNotEmpty(urlLogoAction) ) {
			urlLogoAction =  CommonHelper.getShortUrl(externalizerService.getFormattedUrl(urlLogoAction, resourceResolver), currentPage, resourceResolver, customRunMode);
		}
		else {
			urlLogoAction =  StringUtils.EMPTY;
		}
		urlLogo = externalizerService.getFormattedUrl(urlLogo, resourceResolver);
		if( StringUtils.isNotEmpty(urlCartAction) ) {
			urlCartAction = CommonHelper.getShortUrl(externalizerService.getFormattedUrl(urlCartAction, resourceResolver),currentPage, resourceResolver, customRunMode);
		}
		else {
			urlCartAction =  StringUtils.EMPTY;
		}
		if( StringUtils.isNotEmpty(reactivationURL) ) {
			reactivationURL = CommonHelper.getShortUrl(getExternalizedUrl(reactivationURL, resourceResolver), currentPage, resourceResolver, bdbApiEndpointService.getCustomRunMode());
		} else {
			reactivationURL =  StringUtils.EMPTY;
		}
		urlCart = externalizerService.getFormattedUrl(urlCart, resourceResolver);
		urlCountry = externalizerService.getFormattedUrl(urlCountry, resourceResolver);
		changeLabelUrl = CommonHelper.getLabel("changeLabelUrl", currentPage);
		changeLabelUrl = externalizerService.getFormattedUrl(changeLabelUrl, resourceResolver);
		urlIcon = externalizerService.getFormattedUrl(urlIcon, resourceResolver);
		orderLookupIconUrl = externalizerService.getFormattedUrl(orderLookupIconUrl, resourceResolver);
		orderLookupUrl = externalizerService.getFormattedUrl(orderLookupUrl, resourceResolver);
		canonicalUrl = externalizerService.getFormattedUrl(currentPage.getPath().toString(), resourceResolver);
		if(canonicalUrl.contains("/home-page")) {
			String separator ="/";
			int sepPos = canonicalUrl.lastIndexOf(separator);
			canonicalUrl = canonicalUrl.substring(0,sepPos);
		}
		if(canonicalUrl.contains("/pdp")) {
			String getProductTitleDetails = CommonHelper.getProductTitleDetails(request);
			String materialNumber = CommonHelper.getSelectorDetails(request);
			canonicalUrl = canonicalUrl.replace("/pdp", CommonConstants.SINGLE_SLASH +getProductTitleDetails+"."+materialNumber);
		}
		if(StringUtils.isNotEmpty(registerUrl)) {
			registerUrl = CommonHelper.getShortUrl(externalizerService.getFormattedUrl(registerUrl, resourceResolver),currentPage, resourceResolver, bdbApiEndpointService.getCustomRunMode());
		}
		else {
			registerUrl = StringUtils.EMPTY;
		}
		if (CollectionUtils.isNotEmpty(profileMenu)) {
			for (BDBProfileMenuMutlifieldModel menu : profileMenu) {
				JsonObject profileMenuObj = new JsonObject();
				profileMenuObj.addProperty(CommonConstants.LABEL, menu.getLabel());
				profileMenuObj.addProperty(CommonConstants.ALT_TEXT, menu.getAltText());
				profileMenuObj.addProperty(CommonConstants.IMG_SRC, menu.getImgSrc());
				profileMenuObj.addProperty(CommonConstants.IMG_SRC_MOBILE, menu.getImgSrcM());
				profileMenuObj.addProperty(CommonConstants.ID, menu.getId());

				if (menu.getId().equals(CommonConstants.LOG_OUT)) {
					profileMenuObj.addProperty(CommonConstants.REDIRECT_URL, CommonConstants.BIN_SIGNOUTSERVLET);
				} else {
					profileMenuObj.addProperty(CommonConstants.REDIRECT_URL,
							CommonHelper.getShortUrl(getExternalizedUrl(menu.getRedirectURL(), resourceResolver), currentPage,
									resourceResolver, bdbApiEndpointService.getCustomRunMode()));
				}
				profileMenuArray.add(profileMenuObj);
			}
		}
		if(StringUtils.isNotEmpty(urlLogoSticky))
			urlLogoSticky =  externalizerService.getFormattedUrl(urlLogoSticky, resourceResolver);
		else
			urlLogoSticky =  StringUtils.EMPTY;

		/* Get currentPage language and country */
		List genericDetails = getGenericListData(resourceResolverFactory);
		languageName = StringUtils.EMPTY;
		countryName = StringUtils.EMPTY;
		if  ((genericDetails.get(0)!=null) && (genericDetails.get(1)!=null)) {
			languageName = genericDetails.get(0).toString();
			countryName = genericDetails.get(1).toString();
		}
		else {
			genericDetails = Collections.emptyList();
		}
		/* Get currentPage content path */
		currentPageContentPath = CommonHelper.getLanguage(currentPage)+"-"+CommonHelper.getCountry(currentPage).toLowerCase();

		/* Check if currentPage is homePage */
		String pagePath = currentPage.getPath();
		String checkPath = pagePath.substring(pagePath.lastIndexOf('/') + 1);
		if (checkPath.equals(currentPageContentPath) || checkPath.equals("home-page")) {
			isHomePage = true;
		}

		/* Get homepage title and description */
		Resource detail = resourceResolver.resolve(CommonHelper.getHomePageUrl(currentPage) + CommonConstants.JCR_CONTENT);
		homePageTitle = detail.getValueMap().containsKey(CommonConstants.JCR_TITLE) ? detail.getValueMap().get(CommonConstants.JCR_TITLE).toString() : "";
		homePageDescription = detail.getValueMap().containsKey("jcr:description") ? detail.getValueMap().get("jcr:description").toString() : "";

		/* Get PDP page details */
		if (currentPage.getPath().contains("/pdp")) {
			getPdpPath = request.getPathInfo();
			String pdpPath = getPdpPath.substring(getPdpPath.lastIndexOf('/') + 1);
			String path = pdpPath.replace("pdp.", "");
			String newPath = path.replace(".html", "");
			if (pdpPath.contains("pdp")) {
				getPdpPath = getPdpPath.replace(pdpPath, newPath);
			}
			getPdpPath = externalizerService.getFormattedUrl(getPdpPath, resourceResolver);
			if (StringUtils.isNotEmpty(path)) {
				int findIndex = path.indexOf(".");
				if(findIndex == -1) {
					getPdpTitle="";
				}else {
					getPdpTitle = path.substring(0, findIndex);
				getPdpTitle = getPdpTitle.replace("-", " ");
				}
				
			}
				else {
					path = StringUtils.EMPTY;
					}
				}

		/* Global Alert Banner */
		allowAlertBanner = alertResource.getValueMap().containsKey("allowAlertBanner") ? alertResource.getValueMap().get("allowAlertBanner").toString() : "";
		if (allowAlertBanner != null && allowAlertBanner.equals("yes")) {
			homePageAlert =alertResource.getValueMap().containsKey("homePageAlert") ? alertResource.getValueMap().get("homePageAlert").toString() : "";
			msgPopupLabelAlert = alertResource.getValueMap().containsKey("msgPopupLabelAlert") ? alertResource.getValueMap().get("msgPopupLabelAlert").toString() : "";
			alertIconAlert = alertResource.getValueMap().containsKey("alertIconAlert") ? alertResource.getValueMap().get("alertIconAlert").toString() : "";
			iconAltTextAlert = alertResource.getValueMap().containsKey("iconAltTextAlert") ? alertResource.getValueMap().get("iconAltTextAlert").toString() : "";
			alertDescOne = alertResource.getValueMap().containsKey("alertDescOne") ? alertResource.getValueMap().get("alertDescOne").toString() : "";
			alertDescTwo = alertResource.getValueMap().containsKey("alertDescTwo") ? alertResource.getValueMap().get("alertDescTwo").toString() : "";
			if(alertResource.getValueMap().containsKey("alertEndDate")) {
				scheduledUnpublishDate = alertResource.getValueMap().get("alertEndDate").toString();
			}
			if(alertResource.getValueMap().containsKey("isStartDateTime")) {
				isStartDateTime = alertResource.getValueMap().get("isStartDateTime").toString();
			}
			if(alertResource.getValueMap().containsKey("isEndDateTime")) {
				isEndDateTime = alertResource.getValueMap().get("isEndDateTime").toString();
			}
			desktopLabelAlert = alertResource.getValueMap().containsKey("desktopLabelAlert") ? alertResource.getValueMap().get("desktopLabelAlert").toString() : "";
			mobileLabelAlert = alertResource.getValueMap().containsKey("mobileLabelAlert") ? alertResource.getValueMap().get("mobileLabelAlert").toString() : "";
			alertInfoLinkAlert = alertResource.getValueMap().containsKey("alertInfoLinkAlert") ? alertResource.getValueMap().get("alertInfoLinkAlert").toString() : "";
			alertIconOptionalAlert = alertResource.getValueMap().containsKey("alertIconOptionalAlert") ? alertResource.getValueMap().get("alertIconOptionalAlert").toString() : "";
			iconAltTextOptionalAlert = alertResource.getValueMap().containsKey("iconAltTextOptionalAlert") ? alertResource.getValueMap().get("iconAltTextOptionalAlert").toString() : "";
			alertDescOptionalAlert = alertResource.getValueMap().containsKey("alertDescOptionalAlert") ? alertResource.getValueMap().get("alertDescOptionalAlert").toString() : "";
			desktopLabelOptionalAlert = alertResource.getValueMap().containsKey("desktopLabelOptionalAlert") ? alertResource.getValueMap().get("desktopLabelOptionalAlert").toString() : "";
			mobileLabelOptionalAlert = alertResource.getValueMap().containsKey("mobileLabelOptionalAlert") ? alertResource.getValueMap().get("mobileLabelOptionalAlert").toString() : "";
			alertInfoLinkOptionalAlert = alertResource.getValueMap().containsKey("alertInfoLinkOptionalAlert") ? alertResource.getValueMap().get("alertInfoLinkOptionalAlert").toString() : "";

			alertIconAlert = externalizerService.getFormattedUrl(alertIconAlert, resourceResolver);
			alertInfoLinkAlert = externalizerService.getFormattedUrl(alertInfoLinkAlert, resourceResolver);
			alertIconOptionalAlert = externalizerService.getFormattedUrl(alertIconOptionalAlert, resourceResolver);
			alertInfoLinkOptionalAlert = externalizerService.getFormattedUrl(alertInfoLinkOptionalAlert, resourceResolver);

			//Handling countries to display and allow to homepage feature
			allowAlertBanner = "no";
			List<String> countryNames = getAlertCountryList();
			if (countryNames.size() > 0) {
				for (String country : countryNames) {
					if (country.equalsIgnoreCase(CommonHelper.getCountry(currentPage)) || country.equalsIgnoreCase(CommonConstants.GLOBAL)) {
						allowAlertBanner = "yes";
						setValidDateAndTime(allowAlertBanner);
					} else if (country.equalsIgnoreCase("emea")) {
						if(CommonConstants.EMEA_COUNTRIES_LIST.contains(CommonHelper.getCountry(currentPage))) {
							allowAlertBanner = "yes";
							setValidDateAndTime(allowAlertBanner);
						}
					} else if (country.equalsIgnoreCase(CommonConstants.APAC_EN)) {
						if(CommonConstants.APAC_EN_COUNTRIES_LIST.contains(CommonHelper.getCountry(currentPage))) {
							allowAlertBanner = "yes";
							setValidDateAndTime(allowAlertBanner);
						}
					} else if (country.equalsIgnoreCase(CommonConstants.APAC)) {
						if(CommonConstants.APAC_COUNTRIES_LIST.contains(CommonHelper.getCountry(currentPage))) {
							allowAlertBanner = "yes";
							setValidDateAndTime(allowAlertBanner);
						}
					}
				}
			}
			if (allowAlertBanner.equals("yes") && homePageAlert.equals("true") && !isHomePage) {
				allowAlertBanner = "no";
			}
		}

		/* Invoking siteSearchData method */
		siteSearchJson = getSiteSearchData();
		myLabels = countryDetectionLabelsJson();
		pathList = hiddenPagesPathList();
	}
	
	public void setValidDateAndTime(String allowAlertBannerFlag) {
		String country = CommonHelper.getCountry(currentPage);
		//change time as per timezone in description
		if (null != isStartDateTime && null != isEndDateTime) {
			String startDate = getAlertDiffDateFormat(CommonHelper.getZonedTime(country, isStartDateTime));
			String endDate = getAlertDiffDateFormat(CommonHelper.getZonedTime(country, isEndDateTime));
			
			String date1 = startDate.substring(startDate.lastIndexOf(" ")+1);
			String date2 = endDate.substring(endDate.lastIndexOf(" ")+1);
			
			alertBannerStartDate = getAlertBannerRegionDateFormat(date1);
			alertBannerStartTime = getAlertBannerTimeFormat(CommonHelper.getZonedTime(country, isStartDateTime)).replace("AM", "a.m").replace("PM", "p.m");		
			if(alertBannerStartTime.startsWith("0")) {
				alertBannerStartTime = alertBannerStartTime.substring(1);
		    }
			alertBannerEndDate = getAlertBannerRegionDateFormat(date2);
			alertBannerEndTime = getAlertBannerTimeFormat(CommonHelper.getZonedTime(country, isEndDateTime)).replace("AM", "a.m").replace("PM", "p.m");
			if(alertBannerEndTime.startsWith("0")) {
				alertBannerEndTime = alertBannerEndTime.substring(1);
		    }
			if (!date1.equals(date2)) {
				isSameEventDate = false;
			} else {
				isSameEventDate = true;
				scheduledTimings = getAlertSameDateFormat(startDate, endDate).replace("AM", "a.m").replace("PM", "p.m");
			}
		} else {
			alertDescAlert = alertDescOne + " " + alertDescTwo; 
		}
		if(null != scheduledUnpublishDate) {
			try {
				String regionPublishedDate = CommonHelper.getZonedTime(country, scheduledUnpublishDate);
				if(regionPublishedDate.contains("+")) { 
					regionPublishedDate = StringUtils.substringBeforeLast(regionPublishedDate, "+"); 
				} else { 
					String onlyTime = regionPublishedDate.substring(regionPublishedDate.indexOf("T")+1);
					if(onlyTime.contains("-")) {
						regionPublishedDate = StringUtils.substringBeforeLast(regionPublishedDate, "-");
					} else if(onlyTime.contains("[")) {
						regionPublishedDate = StringUtils.substringBeforeLast(regionPublishedDate, "[");
					}
				}
				SimpleDateFormat scheduledDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
				Date futurePublishDate = scheduledDateFormat.parse(regionPublishedDate);
	            String todayRegionDate = CommonHelper.getRegionDateTime(country);
	            todayRegionDate = StringUtils.substringBefore(todayRegionDate, ".");
	            Date currentDate = scheduledDateFormat.parse(todayRegionDate);
	            
	            allowAlertBanner = isPublishPage(currentDate, futurePublishDate,allowAlertBannerFlag);
			} catch (ParseException e) {
				logger.error("ParseException {}", e.getMessage());
			}
		}
		
	}
	

	/**
	 * 
	 * @param format
	 * @return currentDateTime
	 */
	public static String getToday(String format){
	     Date date = new Date();
	     return new SimpleDateFormat(format).format(date);
	 }
	
	/**
	 * 
	 * @param currentDate
	 * @param futurePublishDate
	 * @return
	 */
	public static String isPublishPage(Date currentDate, Date futurePublishDate, String allowAlertBanner) {
		if (currentDate.after(futurePublishDate)) {
			allowAlertBanner = "no";
	    } else if (currentDate.before(futurePublishDate)) {
	    	allowAlertBanner = "yes";
	    } else if (currentDate.equals(futurePublishDate)) {
			if(futurePublishDate.getTime() < currentDate.getTime()){
            	allowAlertBanner = "yes";
            }else if(futurePublishDate.getTime() == currentDate.getTime()){
            	allowAlertBanner = "no";
            }else{
            	allowAlertBanner = "no";
            }
	    }
		return allowAlertBanner;
	}
	
	public JsonArray setCountries() {
		   String countryListPath = "/content/bdb/na/us/en-us/country-selector/jcr:content/root/countryselector";
	       Resource countryListResource = resourceResolver.getResource(countryListPath);
	       JsonArray countryList = null;
	       if (null != countryListResource && countryListResource.hasChildren()) {
	    	   Resource regionMultifieldResource = countryListResource.getChild("regionmultifield");
		        countryList = new JsonArray();
		        if (null != regionMultifieldResource && regionMultifieldResource.hasChildren()) {
		        	 for (Resource childResource : regionMultifieldResource.getChildren()) {
		        		 if (null != childResource && childResource.hasChildren()) {
		        			 Resource countryResource = childResource.getChild("countrymultifield");
		        			 if (null != countryResource && countryResource.hasChildren()) {
		        				 for(Resource itemResource : countryResource.getChildren()) {
			        				JsonObject countryObj = new JsonObject(); 
									ValueMap vm = itemResource.adaptTo(ValueMap.class);
									String countryName = vm.get("country", StringUtils.EMPTY);
									String countryUrl = vm.get("urlCountry", StringUtils.EMPTY);
									String[] urlSplits = countryUrl.split("/");
									String countryCode = urlSplits[urlSplits.length - 1].split("-")[1].toUpperCase();
									countryObj.addProperty("countryName", countryName.split("\\(")[0].trim());
									countryUrl = externalizerService.getFormattedUrl(countryUrl, resourceResolver);
									countryObj.addProperty("homePageUrl", countryUrl);
									countryObj.addProperty("countryCode", countryCode);
									countryList.add(countryObj);
		        				 }
		        			 }
		        		 }
		        	 }
		        }
	       }
	       return countryList;
	}
	
	private String countryDetectionLabelsJson() {
			JsonObject countryDetection = new JsonObject();
			countryDetection.add("Countries", setCountries());
			return countryDetection.toString();
		}
	
	private String hiddenPagesPathList() {
		JsonObject pathList = new JsonObject();
		pathList.add("pathList", getHiddenPagesPath());
		return pathList.toString();
	}
	
	private JsonArray getHiddenPagesPath() {
		String list = "/etc/acs-commons/lists/bdb/pathList/jcr:content/list";
		 try {
			resourceResolver = CommonHelper.getReadServiceResolver(resourceResolverFactory);
		} catch (LoginException e) {
			e.printStackTrace();
		}
		Resource listResource = resourceResolver.getResource(list);
		JsonArray listArray = new JsonArray();
		if(listResource != null) {
			if(listResource.hasChildren()) {
				for(Resource child : listResource.getChildren()) {
					JsonObject listJson = new JsonObject();
					ValueMap vm = child.adaptTo(ValueMap.class);
					String value = vm.get("value", StringUtils.EMPTY);
					listJson.addProperty("value", value);
					listArray.add(listJson);
				}
			}
		}
		return listArray;
	}
	
	/**
	 * Gets the country list from global header master XF.
	 * @return countryList
	 */
	public List<String> getAlertCountryList() {
		List<String> countryList = new ArrayList<String>();
		try {
			Resource resource = alertResource;
			Node node = resource.adaptTo(Node.class);
			if (null != node && node.hasProperty("countryTags")) {
				Property tags = node.getProperty("countryTags");
				Value[] values = tags.getValues();
				for (Value v : values) {
					String country = v.toString();
					country = country.substring(country.indexOf("/")+1);
					countryList.add(country);
				}
			}
		} catch (RepositoryException e) {
			logger.error(e.getMessage());
		}
		return countryList;
	}

	/**
	 * Creates alert banner date format with different dates.
	 * @return scheduledDate
	 */
	public String getAlertDiffDateFormat(String scheduledDate) {
		if(null != scheduledDate) {
			String zone = StringUtils.substringAfterLast(scheduledDate, "_");
			String[] dateAndTime = scheduledDate.split("T");
			String[] alertDate = dateAndTime[0].split("-");
			
			String date = alertDate[2];
			String month = alertDate[1];
			String year = alertDate[0];
			
			String alertTime = dateAndTime[1].substring(0,5);
			alertTime = LocalTime.parse(alertTime, DateTimeFormatter.ofPattern("HH:mm")).format(DateTimeFormatter.ofPattern("hh:mm a"));
			scheduledDate = alertTime+" "+zone+" "+date+"/"+month+"/"+year;
		}
		return scheduledDate;
	}
	
	public String getAlertBannerTimeFormat(String scheduledDate) {
		if(null != scheduledDate) {
			String zone = StringUtils.substringAfterLast(scheduledDate, "_");
			String[] dateAndTime = scheduledDate.split("T");
			String alertTime = dateAndTime[1].substring(0,5);
			alertTime = LocalTime.parse(alertTime, DateTimeFormatter.ofPattern("HH:mm")).format(DateTimeFormatter.ofPattern("hh:mm a"));
			scheduledDate = alertTime+" "+zone;
		}
		return scheduledDate;
	}


	/**
	 * Creates alert banner date format with same dates.
	 * @return scheduledDate
	 */
	public String getAlertSameDateFormat(String startDate, String endDate) {
		String scheduledDate=null;
		if(null != startDate && null != endDate) {
			String[] start = startDate.split(" ");
			String startTime = start[0];
			String startMeridiem = start[1];
			String zone = start[2];
			String date = start[3];
			String[] end = endDate.split(" ");
			String endTime = end[0];
			String endMeridiem = end[1];
			if(startTime.startsWith("0")) {
				startTime = startTime.substring(1);
		    }
			if(endTime.startsWith("0")) {
				endTime = endTime.substring(1);
		    }
			scheduledDate = startTime+" "+startMeridiem+" - "+endTime+" "+endMeridiem+" "+zone;;
		}
		return scheduledDate;
	}
    
    /**
     * 
     * @param date
     * @return formattedDate
     */
    public  String getAlertBannerRegionDateFormat(String date) {
    	if(null != date) {
    		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        	try {
        		Date formattedDate = df.parse(date);
        		df = new SimpleDateFormat(DAY_MONTH_DATE_YEAR_FORMAT);
        		return df.format(formattedDate);
        	} catch (ParseException e) {
        		logger.error("ParseException {}", e.getMessage());
        	}
    	}
    	return null;
    }
	
	
	/**
	 * Get the homePageData and currentPageData.
	 */
	private String getSiteSearchData() {

		JsonObject currentPageJson = new JsonObject();
		JsonObject homePageJson = new JsonObject();
		JsonObject currentPageIdJson = new JsonObject();
		JsonObject potentialJson = new JsonObject();
		JsonArray potentialJsonArray = new JsonArray();
		JsonObject currentPagePotentialJson = new JsonObject();
		JsonArray currentPagePotentialJsonArray = new JsonArray();
		JsonObject currentOrgJson = new JsonObject();
		JsonObject addressJson = new JsonObject();

		homePageJson.addProperty("@type", "WebSite");
		homePageJson.addProperty("@id", "https://www.bdbiosciences.com/#website");
		String homePageUrl = "https://www.bdbiosciences.com/"+currentPageContentPath;
		homePageJson.addProperty("url", homePageUrl);
		homePageJson.addProperty("name", homePageTitle);
		homePageJson.addProperty("inLanguage", languageName);
		homePageJson.addProperty("description", homePageDescription);
		homePageJson.addProperty("keywords","");

		String targetValue = "https://www.bdbiosciences.com/"+currentPageContentPath+"/search-results?searchKey={search_term_string}";
		potentialJson.addProperty("@type","SearchAction");
		potentialJson.addProperty("target",targetValue);
		potentialJson.addProperty("query-input","required name=search_term_string");
		potentialJsonArray.add(potentialJson);
		homePageJson.add("potentialAction", potentialJsonArray);

		currentPageJson.addProperty("@type", "WebPage");
		if (!currentPage.getPath().contains("/pdp")) {
			String id = externalizerService.getFormattedUrl(currentPage.getPath(), resourceResolver)+"/#webpage";
			currentPageJson.addProperty("@id", id);
			currentPageJson.addProperty("url", externalizerService.getFormattedUrl(currentPage.getPath(), resourceResolver));
			currentPageJson.addProperty("name", CommonHelper.getLabel("jcr:title", currentPage));
		} else {
			currentPageJson.addProperty("@id", getPdpPath +"/#webpage");
			currentPageJson.addProperty("url", getPdpPath);
			currentPageJson.addProperty("name", getPdpTitle);
		}
		currentPageJson.addProperty("description", CommonHelper.getLabel("jcr:description", currentPage));
        currentPageIdJson.addProperty("@id", "https://www.bdbiosciences.com/#website");
        currentPageJson.add("isPartOf", currentPageIdJson);
        currentPageJson.addProperty("keywords","");
        currentPageJson.addProperty("inLanguage", languageName);
		currentPageJson.addProperty("dateModified", CommonHelper.getLabel("cq:lastModified", currentPage));

		currentPagePotentialJson.addProperty("@type","ReadAction");
		String currentPageTargetValue = externalizerService.getFormattedUrl(currentPage.getPath(), resourceResolver);
		JsonArray targetJsonArray = new JsonArray();
		targetJsonArray.add(currentPageTargetValue);
		currentPagePotentialJson.add("target", targetJsonArray);
		currentPagePotentialJsonArray.add(currentPagePotentialJson);
		currentPageJson.add("potentialAction", currentPagePotentialJsonArray);

		currentOrgJson.addProperty("@type", "Organization");
		currentOrgJson.addProperty("url", "bdbiosciences.com");
		currentOrgJson.addProperty("logo", "https://www.bd.com/assets/images/logos/bd-logo-white.png");
		addressJson.addProperty("@type", "PostalAddress");
		addressJson.addProperty("addressCountry", countryName);
		currentOrgJson.add("address", addressJson);

        String homeJson = homePageJson.toString();
        String currentJson = currentPageJson.toString();
		String orgJson = currentOrgJson.toString();
        String finalJson = StringUtils.EMPTY;
		if(isHomePage)
            finalJson = homeJson + ", " + orgJson;
        else
            finalJson = homeJson + ", " + currentJson + ", " + orgJson;

		return finalJson;
	}

	/**
	 * Fetches Current page language and country name from Generic List.
	 * @param resolverFactory
	 * @return genericData List
	 */
	public List getGenericListData(ResourceResolverFactory resolverFactory) {
		List genericData = new ArrayList<String>();
		try {
			ResourceResolver resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory);
			String language = CommonHelper.getLanguage(currentPage);
			if (null != bdbApiEndpointService && null != resourceResolver) {
				Resource genericListResource1 = resourceResolver.getResource(bdbApiEndpointService.languageDropdownEndpoint());
				Resource genericListResource2 = resourceResolver.getResource(bdbApiEndpointService.countryDropdownEndpoint());
				if(null != genericListResource1 && null != genericListResource2) {
					languageName = CommonHelper.getGenericListTitle(language, genericListResource1, resourceResolver);
					countryName = CommonHelper.getGenericListTitle(CommonHelper.getCountry(currentPage), genericListResource2, resourceResolver);
					if (languageName.equalsIgnoreCase("korea")) {
						languageName = "Korean";
					}
				}
			}
			genericData.add(languageName);
			genericData.add(countryName);
		} catch (LoginException e) {
			logger.error("Error occurred in getting language and country {}", e.getMessage());
		}
		return genericData;
	}

    /** Get the site search JSON data.  */
    public String getSiteSearchJson() {
        return siteSearchJson;
    }

	/**
	 * Gets the url logo action.
	 *
	 * @return the url logo action
	 */
	public String getUrlLogoAction() {
		return urlLogoAction;
	}

	/**
	 * Gets the url logo.
	 *
	 * @return the url logo
	 */
	public String getUrlLogo() {
		return urlLogo;
	}

	/**
	 * Gets the url cart action.
	 *
	 * @return the url cart action
	 */
	public String getUrlCartAction() {
		return urlCartAction;
	}

	/**
	 * Gets the url cart.
	 *
	 * @return the url cart
	 */
	public String getUrlCart() {
		return urlCart;
	}

	/**
	 * Gets the url country.
	 *
	 * @return the url country
	 */
	public String getUrlCountry() {
		return urlCountry;
	}

	/**
	 * Gets the change label url.
	 *
	 * @return the change label url
	 */
	public String getChangeLabelUrl() {
		return changeLabelUrl;
	}

	/**
	 * Gets the url icon.
	 *
	 * @return the url icon
	 */
	public String getUrlIcon() {
		return urlIcon;
	}
	
	/**
	 * @return the myLabels
	 */
	public String getMyLabels() {
		return myLabels;
	}

	/**
	 * @return the pathList
	 */
	public String getPathList() {
		return pathList;
	}

	/**
	 * Gets the order lookup icon alt text.
	 *
	 * @return the order lookup icon alt text
	 */
	public String getOrderLookupIconAltText() {
		return orderLookupIconAltText;
	}

	/**
	 * @return the countryDetectionTitle
	 */
	public String getCountryDetectionTitle() {
		return countryDetectionTitle;
	}

	/**
	 * @return the countryDetectionDescription
	 */
	public String getCountryDetectionDescription() {
		return countryDetectionDescription;
	}

	/**
	 * @return the redirectButton
	 */
	public String getRedirectButton() {
		return redirectButton;
	}

	/**
	 * @return the stayHereButton
	 */
	public String getStayHereButton() {
		return stayHereButton;
	}
	
	/**
	 * @return the modalTitle
	 */
	public String getModalTitle() {
		return modalTitle;
	}
	
	/**
	 * @return the modalDescription
	 */
	public String getModalDescription() {
		return modalDescription;
	}
	
	/**
	 * @return the cancelCTALabel
	 */
	public String getCancelCTALabel() {
		return cancelCTALabel;
	}
	
	/**
	 * @return the reactivateCTALabel
	 */
	public String getReactivateCTALabel() {
		return reactivateCTALabel;
	}
	
	/**
	 * @return the reactivation url
	 */
	public String getReactivationURL() {
		return reactivationURL;
	}
	
	/**
	 * @return the continueShoppingModalTitle
	 */
	public String getContinueShoppingModalTitle() {
		return continueShoppingModalTitle;
	}
	
	/**
	 * @return the continueShoppingModalDescription
	 */
	public String getContinueShoppingModalDescription() {
		return continueShoppingModalDescription;
	}
	
	/**
	 * @return the proceedToCheckoutBtnLabel
	 */
	public String getProceedToCheckoutBtnLabel() {
		return proceedToCheckoutBtnLabel;
	}
	
	/**
	 * @return the closeModalBtnLabel
	 */
	public String getCloseModalBtnLabel() {
		return closeModalBtnLabel;
	}
	
	/**
	 * @return the expiredQuoteModalTitle
	 */
	public String getExpiredQuoteModalTitle() {
		return expiredQuoteModalTitle;
	}
	
	/**
	 * @return the expiredQuoteModalDescription
	 */
	public String getExpiredQuoteModalDescription() {
		return expiredQuoteModalDescription;
	}
	
	/**
	 * @return the clearCartBtnLabel
	 */
	public String getClearCartBtnLabel() {
		return clearCartBtnLabel;
	}

	/**
	 * Gets the order lookup icon url.
	 *
	 * @return the order lookup icon url
	 */
	public String getOrderLookupIconUrl() {
		return orderLookupIconUrl;
	}

	/**
	 * Gets the order lookup label.
	 *
	 * @return the order lookup label
	 */
	public String getOrderLookupLabel() {
		return orderLookupLabel;
	}

	/**
	 * Gets the order lookup url.
	 *
	 * @return the order lookup url
	 */
	public String getOrderLookupUrl() {
		return orderLookupUrl;
	}

	/**
	 * Gets the sign in url.
	 *
	 * @return the sign in url
	 */
	public String getSignInUrl() {
		return signInUrl;
	}

	/**
	 * Gets the reg url.
	 *
	 * @return the reg url
	 */
	public String getRegUrl() {
		return registerUrl;
	}

	/**
	 * Gets the externalized url.
	 *
	 * @param urlPath the url path
	 * @param resourceResolver the resource resolver
	 * @return the externalized url
	 */
	public String getExternalizedUrl(String urlPath, ResourceResolver resourceResolver) {
		String finalUrl = StringUtils.EMPTY;
		if(!urlPath.isEmpty()) {
			String relativeUrl = StringUtils.substringBefore(urlPath, CommonConstants.DOT_HTML);
			String externalizedURL = externalizerService.getFormattedUrl(relativeUrl, resourceResolver);
			finalUrl = externalizedURL.concat(StringUtils.substringAfter(urlPath, CommonConstants.DOT_HTML));
		}
		return finalUrl;
	}

	/**
	 * Gets the profile menu.
	 *
	 * @return the profile menu
	 */
	public String getProfileMenu() {
		return profileMenuArray.toString();
	}

	/**
	 * Gets the url logo sticky.
	 *
	 * @return the url logo sticky
	 */
	public String getUrlLogoSticky() {
		return urlLogoSticky;
	}

	/**
	 * @return the canonicalUrl
	 */
	public String getCanonicalUrl() {
		return canonicalUrl;
	}

	/** The Global Alert Banner checkbox
	 * @return allowAlertBanner
	 */
	public String getAllowAlertBanner() {
		return allowAlertBanner;
	}
	/** The Global Alert Banner Popup Label
	 * @return msgPopupLabelAlert
	 */
	public String getMsgPopupLabelAlert() {
		return msgPopupLabelAlert;
	}
	/** The Global Alert Banner Icon Image Path
	 * @return alertIconAlert
	 */
	public String getAlertIconAlert() {
		return alertIconAlert;
	}
	/** The Global Alert Banner Icon alternate text
	 * @return iconAltTextAlert
	 */
	public String getIconAltTextAlert() {
		return iconAltTextAlert;
	}
	/** The Global Alert Banner description
	 * @return alertDescAlert
	 */
	public String getAlertDescAlert() {
		return alertDescAlert;
	}
	/** The Global Alert Banner Desktop Label
	 * @return desktopLabelAlert
	 */
	public String getDesktopLabelAlert() {
		return desktopLabelAlert;
	}
	/** The Global Alert Banner Mobile Label
	 * @return mobileLabelAlert
	 */
	public String getMobileLabelAlert() {
		return mobileLabelAlert;
	}
	/** The Global Alert Banner Alert Information Link
	 * @return alertInfoLinkAlert
	 */
	public String getAlertInfoLinkAlert() {
		return alertInfoLinkAlert;
	}
	/** The Global Alert Banner Alert Second Icon Image Path
	 * @return alertIconOptionalAlert
	 */
	public String getAlertIconOptionalAlert() {
		return alertIconOptionalAlert;
	}
	/** The Global Alert Banner Second Icon Alternate Text
	 * @return iconAltTextOptionalAlert
	 */
	public String getIconAltTextOptionalAlert() {
		return iconAltTextOptionalAlert;
	}
	/**
	 * @return the scheduledUnpublishDate
	 */
	public String getScheduledUnpublishDate() {
		return scheduledUnpublishDate;
	}
	/** The Global Alert Banner Second Description
	 * @return alertDescOptionalAlert
	 */
	public String getAlertDescOptionalAlert() {
		return alertDescOptionalAlert;
	}
	/** The Global Alert Banner Second Desktop Label
	 * @return desktopLabelOptionalAlert
	 */
	public String getDesktopLabelOptionalAlert() {
		return desktopLabelOptionalAlert;
	}
	/** The Global Alert Banner Second Mobile Label
	 * @return mobileLabelOptionalAlert
	 */
	public String getMobileLabelOptionalAlert() {
		return mobileLabelOptionalAlert;
	}
	/** The Global Alert Banner Second Information Link
	 * @return alertInfoLinkOptionalAlert
	 */
	public String getAlertInfoLinkOptionalAlert() {
		return alertInfoLinkOptionalAlert;
	}
	/**
	 * 
	 * @return alertDescOne
	 */
	public String getAlertDescOne() {
		return alertDescOne;
	}

	/**
	 * 
	 * @return alertDescTwo
	 */
	public String getAlertDescTwo() {
		return alertDescTwo;
	}
	
	/**
	 * @return the alertBannerStartTime
	 */
	public String getAlertBannerStartTime() {
		return alertBannerStartTime;
	}

	/**
	 * @return the alertBannerEndDate
	 */
	public String getAlertBannerEndDate() {
		return alertBannerEndDate;
	}

	/**
	 * @return the alertBannerStartDate
	 */
	public String getAlertBannerStartDate() {
		return alertBannerStartDate;
	}

	/**
	 * @return the alertBannerEndTime
	 */
	public String getAlertBannerEndTime() {
		return alertBannerEndTime;
	}
	
	/**
	 * @return the isSameEventDate
	 */
	public boolean isSameEventDate() {
		return isSameEventDate;
	}
	
	/**
	 * @return the scheduledTimings
	 */
	public String getScheduledTimings() {
		return scheduledTimings;
	}

	public String getStickyFormNameLabel() {
		return stickyFormNameLabel;
	}

	public String getFeedBackFormURL() {
		if(StringUtils.isNotEmpty(feedBackFormURL)) {
			return feedBackFormURL;
		}
		else {
			return "#";
		}
	}
	
}
