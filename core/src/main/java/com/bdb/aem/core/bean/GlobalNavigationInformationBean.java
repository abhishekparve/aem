package com.bdb.aem.core.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * The Class GlobalNavigationInformationBean.
 */
public class GlobalNavigationInformationBean {
	
    /** The model image desc. */
    private String modelImageDesc;
    
    /** The model image link. */
    private String modelImageLink;
    
    /** The product label. */
    private String productLabel;
    
    /** The model image path. */
    private String modelImagePath;
    
    /** The model image title. */
    private String modelImageTitle;
    
    /** The model image alt text. */
    private String modelImageAltText;
    
    /** The model image CTA label. */
    private String modelImageCTALabel;
    
    /** The children. */
    private List<GlobalNavigationInformationBean> children;
    
    /** The redirect path. */
    private String redirectPath;
	
	/** The Product Label Trimmed for using in ID */
    private String productLabelTrimmed;

	/**
	 * Sets the model image desc.
	 *
	 * @param modelImageDesc the new model image desc
	 */
	public void setModelImageDesc(String modelImageDesc) {
		this.modelImageDesc = modelImageDesc;
	}
	
	/**
	 * Sets the model image link.
	 *
	 * @param modelImageLink the new model image link
	 */
	public void setModelImageLink(String modelImageLink) {
		this.modelImageLink = modelImageLink;
	}

	/**
	 * Sets the product label.
	 *
	 * @param productLabel the new product label
	 */
	public void setProductLabel(String productLabel) {
		this.productLabel = productLabel;
	}

	/**
	 * Sets the model image path.
	 *
	 * @param modelImagePath the new model image path
	 */
	public void setModelImagePath(String modelImagePath) {
		this.modelImagePath = modelImagePath;
	}

	/**
	 * Sets the model image title.
	 *
	 * @param modelImageTitle the new model image title
	 */
	public void setModelImageTitle(String modelImageTitle) {
		this.modelImageTitle = modelImageTitle;
	}

	/**
	 * Gets the model image desc.
	 *
	 * @return the model image desc
	 */
	public String getModelImageDesc() {
		return modelImageDesc;
	}

	/**
	 * Gets the model image link.
	 *
	 * @return the model image link
	 */
	public String getModelImageLink() {
		return modelImageLink;
	}

	/**
	 * Gets the product label.
	 *
	 * @return the product label
	 */
	public String getProductLabel() {
		return productLabel;
	}

	/**
	 * Gets the model image path.
	 *
	 * @return the model image path
	 */
	public String getModelImagePath() {
		return modelImagePath;
	}

	/**
	 * Gets the model image title.
	 *
	 * @return the model image title
	 */
	public String getModelImageTitle() {
		return modelImageTitle;
	}

	/**
	 * Gets the model image alt text.
	 *
	 * @return the model image alt text
	 */
	public String getModelImageAltText() {
		return modelImageAltText;
	}

	/**
	 * Sets the model image alt text.
	 *
	 * @param modelImageAltText the new model image alt text
	 */
	public void setModelImageAltText(String modelImageAltText) {
		this.modelImageAltText = modelImageAltText;
	}

	/**
	 * Gets the model image CTA label.
	 *
	 * @return the model image CTA label
	 */
	public String getModelImageCTALabel() {
		return modelImageCTALabel;
	}

	/**
	 * Sets the model image CTA label.
	 *
	 * @param modelImageCTALabel the new model image CTA label
	 */
	public void setModelImageCTALabel(String modelImageCTALabel) {
		this.modelImageCTALabel = modelImageCTALabel;
	}

	/**
	 * Gets the children.
	 *
	 * @return the children
	 */
	public List<GlobalNavigationInformationBean> getChildren() {
		if (null != children) {
			return new ArrayList<>(children);
			} 
		else {
			return Collections.emptyList();
			}
	}

	/**
	 * Sets the children.
	 *
	 * @param children the new children
	 */
	public void setChildren(List<GlobalNavigationInformationBean> children) {
		this.children = new ArrayList<>(children);
	}

	/**
	 * Gets the redirect path.
	 *
	 * @return the redirect path
	 */
	public String getRedirectPath() {
		return redirectPath;
	}

	/**
	 * Sets the redirect path.
	 *
	 * @param redirectPath the new redirect path
	 */
	public void setRedirectPath(String redirectPath) {
		this.redirectPath = redirectPath;
	}

	/**
	 * Get the Trimmed Product Label
	 * 
	 * @return the Trimmed Product Label
	 */
	public String getProductLabelTrimmed() {
		productLabelTrimmed = StringUtils.EMPTY; 
		if (StringUtils.isNotEmpty(productLabel)) {
			productLabelTrimmed = productLabel.replaceAll("[(),&]", "_").trim();
			productLabelTrimmed = productLabelTrimmed.replaceAll("\\s", "").trim();
		}
		return productLabelTrimmed;
	}
}
