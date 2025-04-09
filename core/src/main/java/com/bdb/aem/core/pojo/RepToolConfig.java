package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * The Class SignUpConfig.
 */
public class RepToolConfig {
    
    /** The sales rep list payload. */
    @SerializedName("getSalesRepListPayload")
    private Payload getSalesRepListPayload;
    
    /** The sales rep list imageRootDir. */
    @SerializedName("imageRootDir")
    private String imageRootDir;

	/**
     * Instantiates a new sign up config.
     *
     * @param getSalesRepListPayload the get sales rep list payload
     */
    public RepToolConfig(Payload getSalesRepListPayload, String imageRootDir){
    	this.getSalesRepListPayload = getSalesRepListPayload;
    	this.imageRootDir = imageRootDir;
    }
    
	 /**
	 * @return the getSalesRepListPayload
	 */
	public Payload getGetSalesRepListPayload() {
		return getSalesRepListPayload;
	}
	
	/**
	 * @return the imageRootDir
	 */
	public String getImageRootDir() {
		return imageRootDir;
	}

}
