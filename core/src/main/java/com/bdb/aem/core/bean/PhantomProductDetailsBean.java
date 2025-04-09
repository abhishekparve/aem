package com.bdb.aem.core.bean;

import org.apache.commons.lang.StringUtils;

public class PhantomProductDetailsBean {
	
	/** The Constant N_A. */
    protected static final String N_A = "N/A";

	/**
	 * The material number
	 */
   private String materialNumber;
   
   /**
    * the product url
    */
   private String productUrl;
   
   /**
    * the size and quantity
    */
   private String sizeQuantity = N_A;;
   
   /**
    * the product title
    */
   private String productTitle = N_A;

/**
 * @return the materialNumber
 */
public String getMaterialNumber() {
	return materialNumber;
}

/**
 * @param materialNumber the materialNumber to set
 */
public void setMaterialNumber(String materialNumber) {
	this.materialNumber = materialNumber;
}

/**
 * @return the productUrl
 */
public String getProductUrl() {
	return productUrl;
}

/**
 * @param productUrl the productUrl to set
 */
public void setProductUrl(String productUrl) {
	this.productUrl = productUrl;
}

/**
 * @return the sizeQuantity
 */
public String getSizeQuantity() {
	if(StringUtils.isEmpty(sizeQuantity.trim()))
        return N_A;
    return sizeQuantity;
}

/**
 * @param sizeQuantity the sizeQuantity to set
 */
public void setSizeQuantity(String sizeQuantity) {
	this.sizeQuantity = sizeQuantity;
}

/**
 * @return the productTitle
 */
public String getProductTitle() {
	if(StringUtils.isEmpty(productTitle.trim()))
        return N_A;
    return productTitle;
}

/**
 * @param productTitle the productTitle to set
 */
public void setProductTitle(String productTitle) {
	this.productTitle = productTitle;
}
   
   
}