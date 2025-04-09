package com.bdb.aem.core.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BDBProfileMenuMutlifieldModel {

	/** The logger. */
	protected Logger logger = LoggerFactory.getLogger(BDBProfileMenuMutlifieldModel.class);

	/** The label. */
	private String label;

	/** The alt text. */
	private String altText;

	/** The image source. */
	private String imgSrc;
	
	/** The image source mobile. */
	private String imgSrcM;

	/** The id. */
	private String id;

	/** The redirectURL. */
	private String redirectURL;

	public BDBProfileMenuMutlifieldModel(String label, String altText, String imgSrc, String imgSrcM, String id, String redirectURL) {
		super();
		this.label = label;
		this.altText = altText;
		this.imgSrc = imgSrc;
		this.imgSrcM = imgSrcM;
		this.id = id;
		this.redirectURL = redirectURL;
	}

	public String getLabel() {
		return label;
	}

	public String getAltText() {
		return altText;
	}

	public String getImgSrc() {
		return imgSrc;
	}
	
	public String getImgSrcM() {
		return imgSrcM;
	}

	public String getId() {
		return id;
	}

	public String getRedirectURL() {
		return redirectURL;
	}
}
