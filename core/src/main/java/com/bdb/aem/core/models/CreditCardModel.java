package com.bdb.aem.core.models;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
 * The Class CreditCardModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CreditCardModel {

	/** The logger. */
	protected static final Logger logger = LoggerFactory.getLogger(CreditCardModel.class);

	/** The credit card title label */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String title;

	/** The Accept info label */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String acceptInfo;

	/** The credit card number label */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String creditCardNumber;

	/** The name on card label */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String nameOnCard;

	/** The expiry date label */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String expiryDate;

	/** The CVV label */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String cvv;

	/** The Nick name label */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String nickName;

	/** The Set As Default label */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String setDefault;

	/** The Cancel CTA */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String cancelCTA;

	/** The Confirm CTA */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String confirmCTA;

	/** The CC Number error label */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String ccNumberError;

	/** The Mandatory Error label */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String mandatoryErrorLabel;

	/** The request. */
	@Inject
	SlingHttpServletRequest request;

	/** The current page. */
	@Inject
	Page currentPage;

	/** The hybris site id. */
	private String hybrisSiteId;

	/** The credit card labels. */
	@Getter
	private String creditCardLabels;

	/** The credit card config. */
	@Getter
	private String creditCardConfigs;

	/** The bdb api endpoint service. */
	@Inject
	private BDBApiEndpointService bdbApiEndpointService;

	@Inject
	@Via("resource")
	public List<CreditCardDetailModel> cardDetails;

	/**
	 * Forms json out of all the injected values to provide it to the FE React
	 * component.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("CreditCardModel init() - start");
		hybrisSiteId = CommonHelper.setHybrisSiteId(currentPage);
		creditCardLabels = creditCardLabels();
		creditCardConfigs = creditCardConfigs();
	}

	/**
	 * This method forms the json for credit card labels.
	 *
	 * @return the label json
	 */
	public String creditCardLabels() {

		JsonObject labels = new JsonObject();
		labels.addProperty(CommonConstants.CREDIT_TITLE, title);
		labels.addProperty(CommonConstants.ACCEPT_INFO, acceptInfo);
		labels.addProperty(CommonConstants.CREDIT_CARD_NUMBER, creditCardNumber);
		labels.addProperty(CommonConstants.NAME_ON_CARD, nameOnCard);
		labels.addProperty(CommonConstants.CREDIT_EXPIRY_DATE, expiryDate);
		labels.addProperty(CommonConstants.CVV, cvv);
		labels.addProperty(CommonConstants.CREDIT_NICK_NAME, nickName);
		labels.addProperty(CommonConstants.SET_DEFAULT, setDefault);
		labels.addProperty(CommonConstants.CREDIT_CANCEL_CTA, cancelCTA);
		labels.addProperty(CommonConstants.CREDIT_CONFIRM_CTA, confirmCTA);
		labels.addProperty(CommonConstants.CC_NUMBER_ERROR, ccNumberError);
		labels.addProperty(CommonConstants.MANDATORY_ERROR_LABEL, mandatoryErrorLabel);

		if (CollectionUtils.isNotEmpty(cardDetails)) {
			JsonArray cards = new JsonArray();
			for (CreditCardDetailModel cardDetail : cardDetails) {
				JsonObject card = new JsonObject();
				card.addProperty(CommonConstants.ID, cardDetail.getId());
				card.addProperty(CommonConstants.ICON, cardDetail.getIcon());
				card.addProperty(CommonConstants.ALT_TEXT, cardDetail.getIconAltText());
				card.addProperty(CommonConstants.LENGTH, cardDetail.getLength());
				cards.add(card);
			}
			labels.add(CommonConstants.ACCEPTED_CC_LIST, cards);
		}

		return labels.toString();
	}

	/**
	 * Sets the credit card config.
	 *
	 * @return the string
	 */
	public String creditCardConfigs() {

		JsonObject addCreditCardConfig = new JsonObject();
		JsonObject getPaymetricToken = new JsonObject();
		JsonObject getPaymetricTokenPayload = new JsonObject();

		getPaymetricTokenPayload.addProperty(CommonConstants.URL, bdbApiEndpointService.getBDBHybrisDomain()
				+ bdbApiEndpointService.paymetricTokenEndpoint().replace(CommonConstants.BASE_SITE_ID, hybrisSiteId));
		getPaymetricTokenPayload.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_GET);
		getPaymetricToken.add(CommonConstants.REQUEST_PAY_LOAD, getPaymetricTokenPayload);

		JsonObject addCreditCard = new JsonObject();
		JsonObject addCreditCardPayload = new JsonObject();

		addCreditCardPayload.addProperty(CommonConstants.URL, bdbApiEndpointService.getBDBHybrisDomain()
				+ bdbApiEndpointService.getAddCreditCardEndpoint().replace(CommonConstants.SITE, hybrisSiteId));
		addCreditCardPayload.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);
		addCreditCard.add(CommonConstants.REQUEST_PAY_LOAD, addCreditCardPayload);

		addCreditCardConfig.add(CommonConstants.GET_PAYMETRIC_TOKEN, getPaymetricToken);
		addCreditCardConfig.add(CommonConstants.ADD_CREDIT_CARD, addCreditCard);
		addCreditCardConfig.addProperty(CommonConstants.I_FRAME_URL,
				bdbApiEndpointService.paymetricDomain().concat(bdbApiEndpointService.paymetricIframeEndpoint()
						.replace(CommonConstants.MERCHANT_ID, CommonConstants.MERCH_ID)));

		return addCreditCardConfig.toString();
	}

}