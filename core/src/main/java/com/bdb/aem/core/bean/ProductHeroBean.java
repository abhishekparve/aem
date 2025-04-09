package com.bdb.aem.core.bean;

import lombok.Getter;
import lombok.ToString;

/**
 * The Class HeroBean.
 */
@ToString
public class ProductHeroBean {
	
    
    /** The image src. */
    private String image;
    
    /** The image src. */
    private String imgAtlText;
    
    /** The description. */
    private String desc;
    
    /** The description. */
    private String modelDescription;
    
    /** The preview image. */
    private String previewImage;
    
    /** The video id */
    @Getter
    private String videoId;
    
    /** The brightcove player id */
    @Getter
    private String brightcovePlayerId;
    
    /** The brightcove account id */
    @Getter
    private String brightcoveAccountId;

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public String getImage() {
		return image;
	}


	/**
	 * Gets the img atl text.
	 *
	 * @return the img atl text
	 */
	public String getImgAtlText() {
		return imgAtlText;
	}

	/**
	 * Gets the desc.
	 *
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}


	/**
	 * Gets the model description.
	 *
	 * @return the model description
	 */
	public String getModelDescription() {
		return modelDescription;
	}


	/**
	 * Gets the preview image.
	 *
	 * @return the preview image
	 */
	public String getPreviewImage() {
		return previewImage;
	}

}
