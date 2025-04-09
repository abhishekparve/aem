package com.bdb.aem.core.models;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
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
import java.util.List;

/**
 * The Class MyCartPrintModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MyCartPrintModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(MyCartPrintModel.class);

	/** The my cart. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String myCart;

	/** The items. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String items;

	/** The item. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String item;

	/** The cart id. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String cartId;

	/** The products. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String products;

	/** The est delivery date. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String estDeliveryDate;

	/** The save L ater quantity label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String saveLAterQuantityLabel;

	/** The total price label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String totalPriceLabel;

	/** The heading. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String heading;

	/** The description. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String description;

	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String appliedSuccessfully;

	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String promotions;

	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String shipTo;

	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String billTo;

	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String shipToNumber;

	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String billToNumber;

	/** The request. */
	@Inject
	SlingHttpServletRequest request;

	/** The current page. */
	@Inject
	Page currentPage;

	/** The bdb api endpoint service. */
	@Inject
	BDBApiEndpointService bdbApiEndpointService;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** The my cart print labels. */
	@Getter
	private String myCartPrintPageLabels;

	/** The my cart print config. */
	@Getter
	private String myCartPrintPageConfig;

	@Getter
	private String iconsList;

	@Inject
	@Via("resource")
	private List<BDBStandardToolModel> tools;

	/**
	 * Forms json out of all the injected values to provide it to the FE React
	 * component.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("MyCartPrintModel - Init method started");
		myCartPrintPageLabels = myCartPrintLabels();
		myCartPrintPageConfig = myCartPrintJsonConfig();
		iconsList = myCartPrintHeaderLabels(tools);
	}

	public String myCartPrintHeaderLabels(List<BDBStandardToolModel> tools) {
		JsonArray toolArray = new JsonArray();
		JsonObject myCartPrintHeaderLabels = new JsonObject();
		if (CollectionUtils.isNotEmpty(tools)) {
			for (BDBStandardToolModel tool : tools) {
				JsonObject iconObj = new JsonObject();
				iconObj.addProperty(CommonConstants.ICON, tool.getIcon());
				iconObj.addProperty(CommonConstants.ALT_TEXT, tool.getIconAltText());
				toolArray.add(iconObj);
			}
		}
		myCartPrintHeaderLabels.add(CommonConstants.ICONS_LIST, toolArray);
		return myCartPrintHeaderLabels.toString();
	}

	/**
	 * This method forms the json for my cart print labels.
	 *
	 * @return the label json
	 */
	public String myCartPrintLabels() {
		JsonObject myCartJsonLabel = new JsonObject();
		JsonObject disclaimer = new JsonObject();
		JsonObject shipToLabels = new JsonObject();
		JsonObject billToLabels = new JsonObject();
		JsonObject shipToAddressLabels = new JsonObject();
		JsonObject billToAddressLabels = new JsonObject();

		myCartJsonLabel.addProperty(CommonConstants.MY_CART, myCart);
		myCartJsonLabel.addProperty(CommonConstants.ITEMS, items);
		myCartJsonLabel.addProperty(CommonConstants.ITEM, item);
		myCartJsonLabel.addProperty(CommonConstants.CART_ID, cartId);
		myCartJsonLabel.addProperty(CommonConstants.PRODUCTS, products);
		myCartJsonLabel.addProperty(CommonConstants.EST_DELIVERY_DATE, estDeliveryDate);
		myCartJsonLabel.addProperty(CommonConstants.SAVE_LATER_QUANTITY_LABEL, saveLAterQuantityLabel);
		myCartJsonLabel.addProperty(CommonConstants.TOTAL_PRICE, totalPriceLabel);
		myCartJsonLabel.addProperty(CommonConstants.APPLIED_SUCCESSFULLY, appliedSuccessfully);
		myCartJsonLabel.addProperty(CommonConstants.PROMOTIONS, promotions);
		myCartJsonLabel.addProperty(CommonConstants.QUANTITY,
				CommonHelper.getLabel(CommonConstants.QUANTITY_VALUE, currentPage));
		myCartJsonLabel.addProperty(CommonConstants.PRICE_PER_UNIT,
				CommonHelper.getLabel(CommonConstants.PRICE_PER_UNIT, currentPage));
		myCartJsonLabel.addProperty(CommonConstants.YOUR_PRICE_LABEL_ATC,
				CommonHelper.getLabel(CommonConstants.YOUR_PRICE_VALUE, currentPage));
		myCartJsonLabel.addProperty(CommonConstants.LIST_PRICE_LABEL_ATC,
				CommonHelper.getLabel(CommonConstants.LIST_PRICE_VALUE, currentPage));
		myCartJsonLabel.addProperty(CommonConstants.IS_PRINT, CommonConstants.TRUE.toUpperCase());

		disclaimer.addProperty(CommonConstants.HEADING, heading);
		disclaimer.addProperty(CommonConstants.DESCRIPTION, description);

		shipToLabels.addProperty(CommonConstants.SHIP_TO_LBL, shipTo);
		shipToLabels.addProperty(CommonConstants.SHIP_TO_NUMBER_LBL, shipToNumber);
		billToLabels.addProperty(CommonConstants.SHIP_TO_LBL, billTo);
		billToLabels.addProperty(CommonConstants.SHIP_TO_NUMBER_LBL, billToNumber);

		shipToAddressLabels.add(CommonConstants.ADDRESS_LABELS, shipToLabels);
		billToAddressLabels.add(CommonConstants.ADDRESS_LABELS, billToLabels);

		myCartJsonLabel.add(CommonConstants.SHIPPING_ADDRESS, shipToAddressLabels);
		myCartJsonLabel.add(CommonConstants.BILLING_ADDRESS, billToAddressLabels);
		myCartJsonLabel.add(CommonConstants.DISCLAIMER, disclaimer);

		return myCartJsonLabel.toString();
	}

	/**
	 * Gets My Cart print Json Config.
	 *
	 * @return the my cart print json config
	 */
	public String myCartPrintJsonConfig() {

		JsonObject myCartPrintPageConfig = new JsonObject();
		JsonObject getCartWithIdentifier = new JsonObject();

		String hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);

		if (null != hybrisSiteId) {
			getCartWithIdentifier.addProperty(CommonConstants.URL,
					bdbApiEndpointService.getBDBHybrisDomain() + bdbApiEndpointService.getCartWithIdentifier()
							.replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
			getCartWithIdentifier.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_GET);
		}

		myCartPrintPageConfig.add(CommonConstants.GET_CART_WITH_IDENTIFIER, getCartWithIdentifier);

		return myCartPrintPageConfig.toString();
	}

}
