package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AccountQuoteListsConfig.
 */
public class AccountQuoteListsConfig {
	
	/** The create quote list. */
	@SerializedName("createQuoteList")
	private PayloadConfig createQuoteList;
	
	/** The get quote list details. */
	@SerializedName("getQuoteListDetails")
	private PayloadConfig getQuoteListDetails; 
	
	/** The get quote list. */
	@SerializedName("getQuoteList")
	private PayloadConfig getQuoteList; 
	
	/** The file upload quote list. */
	@SerializedName("fileUploadQuoteList")
	private PayloadConfig fileUploadQuoteList; 
	
	/** The file upload quote list entries. */
	@SerializedName("fileUploadQuoteListentries")
	private PayloadConfig fileUploadQuoteListEntries;
	
	/** The remove quote list. */
	@SerializedName("removeQuoteList")
	private PayloadConfig removeQuoteList; 
	
	/** The share quote list. */
	@SerializedName("shareQuoteList")
	private PayloadConfig shareQuoteList;
	
	/** The remove quote list entries. */
	@SerializedName("removeQuoteListEntries")
	private PayloadConfig removeQuoteListEntries;
	
	/** The update quote list entries. */
	@SerializedName("updateQuoteListEntries")
	private PayloadConfig updateQuoteListEntries; 
	
	/** The get product announcements. */
	@SerializedName("getProductAnnouncements")
	private PayloadConfig getProductAnnouncements; 
	
	/** The add all items to cart. */
	@SerializedName("addAllItemsToCart")
	private PayloadConfig addAllItemsToCart;

	/**
	 * Gets the creates the quote list.
	 *
	 * @return the creates the quote list
	 */
	public PayloadConfig getCreateQuoteList() {
		return createQuoteList;
	}

	/**
	 * Sets the creates the quote list.
	 *
	 * @param createQuoteList the new creates the quote list
	 */
	public void setCreateQuoteList(PayloadConfig createQuoteList) {
		this.createQuoteList = createQuoteList;
	}

	/**
	 * Gets the gets the quote list details.
	 *
	 * @return the gets the quote list details
	 */
	public PayloadConfig getGetQuoteListDetails() {
		return getQuoteListDetails;
	}

	/**
	 * Sets the gets the quote list details.
	 *
	 * @param getQuoteListDetails the new gets the quote list details
	 */
	public void setGetQuoteListDetails(PayloadConfig getQuoteListDetails) {
		this.getQuoteListDetails = getQuoteListDetails;
	}

	/**
	 * Gets the gets the quote list.
	 *
	 * @return the gets the quote list
	 */
	public PayloadConfig getGetQuoteList() {
		return getQuoteList;
	}

	/**
	 * Sets the gets the quote list.
	 *
	 * @param getQuoteList the new gets the quote list
	 */
	public void setGetQuoteList(PayloadConfig getQuoteList) {
		this.getQuoteList = getQuoteList;
	}

	/**
	 * Gets the file upload quote list.
	 *
	 * @return the file upload quote list
	 */
	public PayloadConfig getFileUploadQuoteList() {
		return fileUploadQuoteList;
	}

	/**
	 * Sets the file upload quote list.
	 *
	 * @param fileUploadQuoteList the new file upload quote list
	 */
	public void setFileUploadQuoteList(PayloadConfig fileUploadQuoteList) {
		this.fileUploadQuoteList = fileUploadQuoteList;
	}

	/**
	 * Gets the file upload quote list entries.
	 *
	 * @return the file upload quote list entries
	 */
	public PayloadConfig getFileUploadQuoteListEntries() {
		return fileUploadQuoteListEntries;
	}

	/**
	 * Sets the file upload quote list entries.
	 *
	 * @param fileUploadQuoteListEntries the new file upload quote list entries
	 */
	public void setFileUploadQuoteListEntries(PayloadConfig fileUploadQuoteListEntries) {
		this.fileUploadQuoteListEntries = fileUploadQuoteListEntries;
	}

	/**
	 * Gets the removes the quote list.
	 *
	 * @return the removes the quote list
	 */
	public PayloadConfig getRemoveQuoteList() {
		return removeQuoteList;
	}

	/**
	 * Sets the removes the quote list.
	 *
	 * @param removeQuoteList the new removes the quote list
	 */
	public void setRemoveQuoteList(PayloadConfig removeQuoteList) {
		this.removeQuoteList = removeQuoteList;
	}

	/**
	 * Gets the share quote list.
	 *
	 * @return the share quote list
	 */
	public PayloadConfig getShareQuoteList() {
		return shareQuoteList;
	}

	/**
	 * Sets the share quote list.
	 *
	 * @param shareQuoteList the new share quote list
	 */
	public void setShareQuoteList(PayloadConfig shareQuoteList) {
		this.shareQuoteList = shareQuoteList;
	}

	/**
	 * Gets the removes the quote list entries.
	 *
	 * @return the removes the quote list entries
	 */
	public PayloadConfig getRemoveQuoteListEntries() {
		return removeQuoteListEntries;
	}

	/**
	 * Sets the removes the quote list entries.
	 *
	 * @param removeQuoteListEntries the new removes the quote list entries
	 */
	public void setRemoveQuoteListEntries(PayloadConfig removeQuoteListEntries) {
		this.removeQuoteListEntries = removeQuoteListEntries;
	}

	/**
	 * Gets the update quote list entries.
	 *
	 * @return the update quote list entries
	 */
	public PayloadConfig getUpdateQuoteListEntries() {
		return updateQuoteListEntries;
	}

	/**
	 * Sets the update quote list entries.
	 *
	 * @param updateQuoteListEntries the new update quote list entries
	 */
	public void setUpdateQuoteListEntries(PayloadConfig updateQuoteListEntries) {
		this.updateQuoteListEntries = updateQuoteListEntries;
	}

	/**
	 * Gets the gets the product announcements.
	 *
	 * @return the gets the product announcements
	 */
	public PayloadConfig getGetProductAnnouncements() {
		return getProductAnnouncements;
	}

	/**
	 * Sets the gets the product announcements.
	 *
	 * @param getProductAnnouncements the new gets the product announcements
	 */
	public void setGetProductAnnouncements(PayloadConfig getProductAnnouncements) {
		this.getProductAnnouncements = getProductAnnouncements;
	}

	/**
	 * Gets the adds the all items to cart.
	 *
	 * @return the adds the all items to cart
	 */
	public PayloadConfig getAddAllItemsToCart() {
		return addAllItemsToCart;
	}

	/**
	 * Sets the adds the all items to cart.
	 *
	 * @param addAllItemsToCart the new adds the all items to cart
	 */
	public void setAddAllItemsToCart(PayloadConfig addAllItemsToCart) {
		this.addAllItemsToCart = addAllItemsToCart;
	}
}
