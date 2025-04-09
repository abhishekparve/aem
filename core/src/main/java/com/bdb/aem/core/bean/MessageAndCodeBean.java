package com.bdb.aem.core.bean;

import com.google.gson.annotations.SerializedName;

/**
 * The Class MessageAndCodeBean.
 */
public class MessageAndCodeBean {
	
	/** The id. */
	@SerializedName("id")
	private String id;
	
	/** The label. */
	@SerializedName("label")
	private String label;

	/**
	 * Instantiates a new message and code bean.
	 *
	 * @param id the id
	 * @param label the label
	 */
	public MessageAndCodeBean(String id, String label) {
		this.id = id;
		this.label = label;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
}
