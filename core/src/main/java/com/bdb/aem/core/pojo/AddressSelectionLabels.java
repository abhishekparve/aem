package com.bdb.aem.core.pojo;

import com.bdb.aem.core.bean.AddressSelectionLabelsBean;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The Class AddressSelectionLabels.
 */
public class AddressSelectionLabels {
	
	/** The no linked address text. */
	@SerializedName("noAddressLabel")
	private String noLinkedAddressText;
	
	/** The location icon. */
	@SerializedName("locationIcon")
	private String locationIcon;
	
	/** The location icon alt text. */
	@SerializedName("locationIconAltText")
	private String locationIconAltText;
	
	/** The select address cta label. */
	@SerializedName("selectAddressCTALabel")
	private String selectAddressCtaLabel;
	
	/** The select address title. */
	@SerializedName("selectAddressTitle")
	private String selectAddressTitle;
	
	/** The linked address. */
	@SerializedName("linkedAddressLabel")
	private String linkedAddress;
	
	/** The change linked address link label. */
	@SerializedName("changeAddressCTATitle")
	private String changeLinkedAddressLinkLabel;
	
	/** The address labels. */
	@SerializedName("addressLabels")
	private JsonElement addressLabels;
	
	/** The search address. */
	@SerializedName("searchAddress")
	private JsonElement searchAddress;
	
	/** The shipping address label. */
	@SerializedName("shippingAddressLabel")
	private String shippingAddressLabel;
	
	/** The default label. */
	@SerializedName("defaultLabel")
	private String defaultLabel;
	
	/** The ship to number label. */
	@SerializedName("shipToNumberLabel")
	private String shipToNumberLabel;
	
	/** The search address title. */
	@SerializedName("title")
	private String searchAddressTitle;
	
	/** The search place holder. */
	@SerializedName("searchPlaceHolder")
	private String searchPlaceHolder;
	
	/** The favorite shipping address text. */
	@SerializedName("favoriteAddress")
	private String favoriteShippingAddressText;
	
	/** The noFavouriteDescription. */
	@SerializedName("noFavouriteDescription")
	private String noFavouriteDescription;

	/** The favIcon. */
	@SerializedName("favIcon")
	private String favIcon;

	/** The favIconAltTxt. */
	@SerializedName("favIconAltTxt")
	private String favIconAltTxt;

	/** The saveCTA. */
	@SerializedName("saveCTA")
	private String saveCTA;

	/**
	 * Instantiates a new address selection labels.
	 *
	 * @param addressSelectionBean the address selection bean
	 */
	public AddressSelectionLabels(AddressSelectionLabelsBean addressSelectionBean) {
		this.noLinkedAddressText = addressSelectionBean.getNoLinkedAddressText();
		this.locationIcon = addressSelectionBean.getLocationIcon();
		this.locationIconAltText = addressSelectionBean.getLocationIconAltText();
		this.selectAddressCtaLabel = addressSelectionBean.getSelectAddressCtaLabel();
		this.selectAddressTitle = addressSelectionBean.getSelectAddressTitle();
		this.linkedAddress = addressSelectionBean.getLinkedAddress();
		this.changeLinkedAddressLinkLabel = addressSelectionBean.getChangeLinkedAddressLinkLabel();
		this.addressLabels = addressSelectionBean.getAddressLabels();
		this.searchAddress = addressSelectionBean.getSearchAddress();
		this.shippingAddressLabel = addressSelectionBean.getShippingAddressLabel();
		this.defaultLabel = addressSelectionBean.getDefaultLabel();
		this.shipToNumberLabel = addressSelectionBean.getShipToNumberLabel();
		this.searchAddressTitle = addressSelectionBean.getSearchAddressTitle();
		this.searchPlaceHolder = addressSelectionBean.getSearchPlaceHolder();
		this.favoriteShippingAddressText = addressSelectionBean.getFavoriteShippingAddressText();
		this.noFavouriteDescription = addressSelectionBean.getNoFavouriteDescription();
		this.favIcon = addressSelectionBean.getFavIcon();
		this.favIconAltTxt = addressSelectionBean.getFavIconAltTxt();
		this.saveCTA = addressSelectionBean.getSaveCta();
	}

	/**
	 * Gets the no linked address text.
	 *
	 * @return the no linked address text
	 */
	public String getNoLinkedAddressText() {
		return noLinkedAddressText;
	}

	/**
	 * Gets the location icon.
	 *
	 * @return the location icon
	 */
	public String getLocationIcon() {
		return locationIcon;
	}

	/**
	 * Gets the location icon alt text.
	 *
	 * @return the location icon alt text
	 */
	public String getLocationIconAltText() {
		return locationIconAltText;
	}

	/**
	 * Gets the select address cta label.
	 *
	 * @return the select address cta label
	 */
	public String getSelectAddressCtaLabel() {
		return selectAddressCtaLabel;
	}

	/**
	 * Gets the select address title.
	 *
	 * @return the select address title
	 */
	public String getSelectAddressTitle() {
		return selectAddressTitle;
	}

	/**
	 * Gets the linked address.
	 *
	 * @return the linked address
	 */
	public String getLinkedAddress() {
		return linkedAddress;
	}

	/**
	 * Gets the change linked address link label.
	 *
	 * @return the change linked address link label
	 */
	public String getChangeLinkedAddressLinkLabel() {
		return changeLinkedAddressLinkLabel;
	}

	/**
	 * Gets the address labels.
	 *
	 * @return the address labels
	 */
	public JsonElement getAddressLabels() {
		return addressLabels;
	}

	/**
	 * Gets the search address.
	 *
	 * @return the search address
	 */
	public JsonElement getSearchAddress() {
		return searchAddress;
	}

	/**
	 * Gets the shipping address label.
	 *
	 * @return the shipping address label
	 */
	public String getShippingAddressLabel() {
		return shippingAddressLabel;
	}

	/**
	 * Gets the default label.
	 *
	 * @return the default label
	 */
	public String getDefaultLabel() {
		return defaultLabel;
	}

	/**
	 * Gets the ship to number label.
	 *
	 * @return the ship to number label
	 */
	public String getShipToNumberLabel() {
		return shipToNumberLabel;
	}

	/**
	 * Gets the search address title.
	 *
	 * @return the search address title
	 */
	public String getSearchAddressTitle() {
		return searchAddressTitle;
	}

	/**
	 * Gets the search place holder.
	 *
	 * @return the search place holder
	 */
	public String getSearchPlaceHolder() {
		return searchPlaceHolder;
	}

	/**
	 * Gets the favorite shipping address text.
	 *
	 * @return the favorite shipping address text
	 */
	public String getFavoriteShippingAddressText() {
		return favoriteShippingAddressText;
	}

	/**
	 * Gets the saveCTA
	 *
	 * @return the string saveCTA
	 */
	public String getSaveCTA() {
		return saveCTA;
	}

	/**
	 * @return the noFavouriteDescription
	 */
	public String getNoFavouriteDescription() {
		return noFavouriteDescription;
	}
}
