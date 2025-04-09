package com.bdb.aem.core.bean;

import com.google.gson.JsonObject;

/**
 * The Class PDPImagesBean.
 */
public class PDPImagesBean {
	
    
    /** The image rank. */
    private int rank;

	/** The JsonObject imageAsset. */
	private JsonObject imageAsset;

	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * @return the imageAsset
	 */
	public JsonObject getImageAsset() {
		return imageAsset;
	}

	/**
	 * @param assetObj the imageAsset to set
	 */
	public void setImageAsset(JsonObject assetObj) {
		this.imageAsset = assetObj;
	}
	
}
