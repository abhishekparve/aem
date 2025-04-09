package com.bdb.aem.core.models;

import com.bdb.aem.core.pojo.Payload;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.ExcludeUtil;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;


/**
 * The Class CompareProductsModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CompareProductsModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(CompareProductsModel.class);

	
	/** The product comparison label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String productComparisonLabel;
	
	/** The compare histograms label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String compareHistogramsLabel;
	
	/** The compare histogram heading label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String compareHistogramHeadingLabel;
	
	/** The est delivery date label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String estDeliveryDateLabel;
	
	
	/** The add to shopping list label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String addToShoppingListLabel;
	
	/** The clone label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String cloneLabel;
	
	/** The brand label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String brandLabel;
	
	/** The alternative name label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String alternativeNameLabel;
	
	/** The vol per test label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String volPerTestLabel;
	
	/** The isotype label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String isotypeLabel;
	
	/** The reactivity label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String reactivityLabel;
	
	/** The application label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String applicationLabel;
	
	/** The immunogen label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String immunogenLabel;
	
	/** The workshop number label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String workshopNumberLabel;
	
	/** The entrez gene ID label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String entrezGeneIDLabel;
	
	/** The storage buffer label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String storageBufferLabel;
	
	/** The regulatory status label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String regulatoryStatusLabel;
	
	/** The excitation source label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String excitationSourceLabel;
	
	/** The excitation max label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String excitationMaxLabel;
	
	/** The emission max label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String emissionMaxLabel;
	
	/** The current format label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String currentFormatLabel;
	
	/** The other formats label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String otherFormatsLabel;
	
	/** The viewMoreLabel. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String viewMoreLabel;
	
	/** The backButtonLabel. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String backButtonLabel;


	/** The request. */
	@Inject
	SlingHttpServletRequest request;

	/** The current page. */
	@Inject
	Page currentPage;

	/** The bdb api endpoint service. */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;

	
	/** The product comparison labels. */
	private String productComparisonLabels;

	
	/** The product comparison config. */
	private String productComparisonConfig;
	

	/**
	 * Forms json out of all the injected values to provide it to the FE React
	 * component.
	 */
	@PostConstruct
	protected void init() {
		try {
			logger.debug("CompareProductsModel - Init method started");
			ExcludeUtil excludeUtilObject = new ExcludeUtil();
			productComparisonLabels = createProductComparisonLabels();
			productComparisonConfig = createProductComparisonConfig(excludeUtilObject);
		} catch (LoginException e) {
			logger.error("LoginException {}", e.getMessage());
		}

	}
	
	
	/**
	 * Creates the product comparison labels.
	 *
	 * @return the string
	 * @throws LoginException the login exception
	 */
	public String createProductComparisonLabels() throws LoginException {
		JsonObject productComparisionLabelsJson = new JsonObject();
		JsonObject comparisionTableLabelsJson = new JsonObject();
		
		productComparisionLabelsJson.addProperty(CommonConstants.PRODUCT_COMPARISON, productComparisonLabel);
		productComparisionLabelsJson.addProperty(CommonConstants.COMPARE_HISTOGRAMS, compareHistogramsLabel);
		productComparisionLabelsJson.addProperty(CommonConstants.COMPARE_HISTOGRAM_HEADING, compareHistogramHeadingLabel);
		productComparisionLabelsJson.addProperty(CommonConstants.YOUR_PRICE_LABEL_ATC,
				CommonHelper.getLabel(CommonConstants.YOUR_PRICE_VALUE, currentPage));
		productComparisionLabelsJson.addProperty(CommonConstants.LIST_PRICE_LABEL_ATC,
				CommonHelper.getLabel(CommonConstants.LIST_PRICE_VALUE, currentPage));
		productComparisionLabelsJson.addProperty(CommonConstants.EST_DELIVERY_DATE_LABEL_CP, estDeliveryDateLabel);
		productComparisionLabelsJson.addProperty(CommonConstants.ADD_TO_CART_CP,
				CommonHelper.getLabel(CommonConstants.ADD_TO_CART, currentPage));
		productComparisionLabelsJson.addProperty(CommonConstants.ADD_TO_SHOPPING_LIST_LABEL, addToShoppingListLabel);
		productComparisionLabelsJson.addProperty(CommonConstants.VIEW_MORE_LABEL_KEY, viewMoreLabel);
		productComparisionLabelsJson.addProperty(CommonConstants.BACK_BUTTON_LABEL, backButtonLabel);

		//GE-17706 Adding the Save to shipping or Save to Quote List
		productComparisionLabelsJson.addProperty(CommonConstants.SAVE_TO_QUOTE_LIST, CommonHelper.getLabel(CommonConstants.SAVE_TO_QUOTE_LIST,currentPage));
		productComparisionLabelsJson.addProperty(CommonConstants.SAVE_TO_SHOPPING_LIST, CommonHelper.getLabel(CommonConstants.SAVE_TO_SHOPPING_LIST_KEY,currentPage));

		
		comparisionTableLabelsJson.addProperty(CommonConstants.CLONE, cloneLabel);
		comparisionTableLabelsJson.addProperty(CommonConstants.BRAND_CP, brandLabel);
		comparisionTableLabelsJson.addProperty(CommonConstants.ALTERNATIVE_NAME, alternativeNameLabel);
		comparisionTableLabelsJson.addProperty(CommonConstants.VOL_PER_TEST, volPerTestLabel);
		comparisionTableLabelsJson.addProperty(CommonConstants.ISOTYPE, isotypeLabel);
		comparisionTableLabelsJson.addProperty(CommonConstants.REACTIVITY, reactivityLabel);
		comparisionTableLabelsJson.addProperty(CommonConstants.APPLICATION, applicationLabel);
		comparisionTableLabelsJson.addProperty(CommonConstants.IMMUNOGEN, immunogenLabel);
		comparisionTableLabelsJson.addProperty(CommonConstants.WORKSHOP_NUMBER, workshopNumberLabel);
		comparisionTableLabelsJson.addProperty(CommonConstants.ENTREZ_GENE_ID, entrezGeneIDLabel);
		comparisionTableLabelsJson.addProperty(CommonConstants.STORAGE_BUFFER, storageBufferLabel);
		comparisionTableLabelsJson.addProperty(CommonConstants.REGULATORY_STATUS, regulatoryStatusLabel);
		comparisionTableLabelsJson.addProperty(CommonConstants.EXCITATION_SOURCE, excitationSourceLabel);
		comparisionTableLabelsJson.addProperty(CommonConstants.EXCITATION_MAX_CP, excitationMaxLabel);
		comparisionTableLabelsJson.addProperty(CommonConstants.EMISSION_MAX, emissionMaxLabel);
		comparisionTableLabelsJson.addProperty(CommonConstants.CURRENT_FORMAT, currentFormatLabel);
		comparisionTableLabelsJson.addProperty(CommonConstants.OTHER_FORMATS_CP, otherFormatsLabel);
		
		productComparisionLabelsJson.add(CommonConstants.COMPARISON_TABLE_LABELS, comparisionTableLabelsJson);
		return productComparisionLabelsJson.toString();
	}

	
	/**
	 * Creates the product comparison config.
	 *
	 * @param excludeUtilObject the exclude util object
	 * @return the string
	 */
	public String createProductComparisonConfig(ExcludeUtil excludeUtilObject) {
		JsonObject productComparisionConfigJson = new JsonObject();		

		String hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);

		if (null != hybrisSiteId) {
			Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
					.addSerializationExclusionStrategy(excludeUtilObject).create();
			String getProductDetailsEndpoint = bdbApiEndpointService.getProductDetailsEndpoint();
			Payload getProductDetailsPayload = new Payload(getProductDetailsEndpoint, HttpConstants.METHOD_POST);
			
			String getPriceDataEndpoint = StringUtils.replace(
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getPriceDataEndPoint(),
					CommonConstants.BASE_SITE_ID, hybrisSiteId);
			
			Payload getPriceDataPayload = new Payload(getPriceDataEndpoint, HttpConstants.METHOD_POST);
			
			productComparisionConfigJson.add(CommonConstants.GET_PRODUCT_DETAILS,gson.toJsonTree(getProductDetailsPayload));
			productComparisionConfigJson.add(CommonConstants.GET_PRICE_DATA,gson.toJsonTree(getPriceDataPayload));

		}

		
		return productComparisionConfigJson.toString();
	}


	/**
	 * Gets the product comparison labels.
	 *
	 * @return the product comparison labels
	 */
	public String getProductComparisonLabels() {
		return productComparisonLabels;
	}


	/**
	 * Gets the product comparison config.
	 *
	 * @return the product comparison config
	 */
	public String getProductComparisonConfig() {
		return productComparisonConfig;
	}

}
