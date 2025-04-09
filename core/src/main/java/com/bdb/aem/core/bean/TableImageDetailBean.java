package com.bdb.aem.core.bean;

import java.util.ArrayList;
import java.util.List;

public class TableImageDetailBean {
    private String image;
    private String imageLinkUrl;
    private String openNewImageLinkTab;
    private String titleText;
    private String redirectSectionId;
    private String redirectLink;
    private String subtext;
    private List<TableRowDetailsBean> rowDetails;
    private String ctaText;
    private String ctaLink;
    private String ctaText1;
    private String ctaLink1;

    /**
     *
     * @return Image Details
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
    * @return Image Link Url
    */
   public String getImageLinkUrl() {
       return imageLinkUrl;
   }

   /**
    *
    * @param Image Link Url
    */
   public void setImageLinkUrl(String imageLinkUrl) {
       this.imageLinkUrl = imageLinkUrl;
   }
   
   /**
   *
   * @return openNewImageLinkTab
   */
	public String getOpenNewImageLinkTab() {
	    return openNewImageLinkTab;
   }
	
	/**
    *
    * @param openNewImageLinkTab
    */
   public void setOpenNewImageLinkTab(String openNewImageLinkTab) {
	   this.openNewImageLinkTab = openNewImageLinkTab;
   }
   
    /**
     *
     * @return titleText
     */
    public String getTitleText() {
        return titleText;
    }

    /**
     *
     * @param titleText
     */
    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    /**
     *
     * @return Sub text
     */
    public String getSubtext() {
        return subtext;
    }

    /**
     *
     * @param subtext
     */
    public void setSubtext(String subtext) {
        this.subtext = subtext;
    }

    /**
     *
     * @return Row Details
     */
    public List<TableRowDetailsBean> getRowDetails() {
        return new ArrayList<>(rowDetails);
    }

    /**
     *
     * @param rowDetails
     */
    public void setRowDetails(List<TableRowDetailsBean> rowDetails) {
        if (null != rowDetails) {
            this.rowDetails = new ArrayList<>(rowDetails);
        } else {
            this.rowDetails = new ArrayList<>();
        }
    }

    /**
     *
     * @return ctaText
     */
    public String getCtaText() {
        return ctaText;
    }

    /**
     *
     * @param ctaText
     */
    public void setCtaText(String ctaText) {
        this.ctaText = ctaText;
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
     *
     * @return ctaText1
     */
    public String getCtaText1() {
        return ctaText1;
    }

    /**
     *
     * @param ctaText1
     */
    public void setCtaText1(String ctaText1) {
        this.ctaText1 = ctaText1;
    }

    /**
     *
     * @return ctaLink1
     */
    public String getCtaLink1() {
        return ctaLink1;
    }

    /**
     *
     * @param ctaLink1
     */
    public void setCtaLink1(String ctaLink1) {
        this.ctaLink1 = ctaLink1;
    }

    /**
     *
     * @return redirectSectionId
     */
    public String getRedirectSectionId() {
        return redirectSectionId;
    }

    /**
     *
     * @param redirectSectionId
     */
    public void setRedirectSectionId(String redirectSectionId) {
        this.redirectSectionId = redirectSectionId;
    }

    /**
     *
     * @return redirectLink
     */
    public String getRedirectLink() {
        return redirectLink;
    }

    /**
     *
     * @param redirectLink
     */
    public void setRedirectLink(String redirectLink) {
        this.redirectLink = redirectLink;
    }
}
