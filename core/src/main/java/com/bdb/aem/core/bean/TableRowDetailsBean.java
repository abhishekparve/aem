package com.bdb.aem.core.bean;

public class    TableRowDetailsBean {
    private String dropDownValue;
    private String image;
    private String imagePathUrl;
    private String openNewImagePathTab;
    private String text;
    private String ctaLink;
    private String textLeftAlignment;

	/**
     *
     * @return dropDownValue
     */
    public String getDropDownValue() {
        return dropDownValue;
    }

    /**
     *
     * @param dropDownValue
     */
    public void setDropDownValue(String dropDownValue) {
        this.dropDownValue = dropDownValue;
    }

    /**
     *
     * @return Image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }
    
    /**
    *
    * @return Image Path Url
    */
    public String getImagePathUrl() {
		return imagePathUrl;
	}
    
    /**
    *
    * @param image Path Url
    */
	public void setImagePathUrl(String imagePathUrl) {
		this.imagePathUrl = imagePathUrl;
	}
	
	/**
    *
    * @return OpenNewImagePathTab
    */    
	public String getOpenNewImagePathTab() {
		return openNewImagePathTab;
	}

	/**
    *
    * @param OpenNewImagePathTab
    */
	public void setOpenNewImagePathTab(String openNewImagePathTab) {
		this.openNewImagePathTab = openNewImagePathTab;
	}
	

	/**
     *
     * @return Text value
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     *
     * @return ctaLink
     */
    public String getCtaLink() {
        return ctaLink;
    }

    /**
     *
     * @param ctaLink
     */
    public void setCtaLink(String ctaLink) {
        this.ctaLink = ctaLink;
    }
    
    /**
	 * @return the textLeftAlignment
	 */
	public String getTextLeftAlignment() {
		return textLeftAlignment;
	}

	/**
	 * @param textLeftAlignment
	 */
	public void setTextLeftAlignment(String textLeftAlignment) {
		this.textLeftAlignment = textLeftAlignment;
	}
}
