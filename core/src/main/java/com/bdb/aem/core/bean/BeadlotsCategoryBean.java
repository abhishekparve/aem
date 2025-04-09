package com.bdb.aem.core.bean;

import com.bdb.aem.core.models.InstallationsInstructionsModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BeadlotsCategoryBean {

    /**
     * The categoryLabel.
     */
    @SerializedName("categoryLabel")
    private String categoryLabel;

    /**
     * The beadlotsCategoryTitle.
     */
    @SerializedName("categoryTitle")
    private String beadlotsCategoryTitle;

    /**
     * The beadlotsCategorySubTitle.
     */
    @SerializedName("categorySubTitle")
    private String beadlotsCategorySubTitle;

    /**
     * The statusLabel.
     */
    @SerializedName("statusLabel")
    private String statusLabel;

    /**
     * The partNumberLabel.
     */
    @SerializedName("materialNumberLabel")
    private String partNumberLabel;

    /**
     * The beadlotsFilesTitle.
     */
    @SerializedName("filesTitle")
    private String beadlotsFilesTitle;
    
    /** The show status. */
    @SerializedName("showStatus")
    private boolean showStatus;

	/**
     * The installationsInstructionsModels list.
     */
    @SerializedName("documents")
    private List<InstallationsInstructionsModel> getDocumentsBeanList = new ArrayList<>();


    /**
     * The beadlots group bean list.
     */
    @SerializedName("groupList")
    private List<BeadlotsGroupBean> beadlotsGroupBeanList = new ArrayList<>();

    /**
     * get the beadlotsCategoryTitle.
     */
    public String getBeadlotsCategoryTitle() {
        return beadlotsCategoryTitle;
    }


    /**
     * get the beadlotsCategorySubTitle.
     */
    public String getBeadlotsCategorySubTitle() {
        return beadlotsCategorySubTitle;
    }


    /**
     * get the statusLabel.
     */
    public String getStatusLabel() {
        return statusLabel;
    }

    /**
     * get the partNumberLabel.
     */
    public String getPartNumberLabel() {
        return partNumberLabel;
    }


    /**
     * get the beadlotsFilesTitle.
     */
    public String getBeadlotsFilesTitle() {
        return beadlotsFilesTitle;
    }

    /**
     * get the beadlotsGroupBeanList.
     */
    public List<BeadlotsGroupBean> getBeadlotsGroupBeanList() {
          return(beadlotsGroupBeanList);

    }

    /**
     * get the categoryLabel.
     */
    public String getCategoryLabel() {
        return categoryLabel;
    }

    /**
     * get the getDocumentsBeanList.
     */
    public List<InstallationsInstructionsModel> getDocumentsBeanList() {
        return(getDocumentsBeanList);

    }

    public void setCategoryLabel(String categoryLabel) {
        this.categoryLabel = categoryLabel;
    }

    public void setBeadlotsCategoryTitle(String beadlotsCategoryTitle) {
        this.beadlotsCategoryTitle = beadlotsCategoryTitle;
    }

    public void setBeadlotsCategorySubTitle(String beadlotsCategorySubTitle) {
        this.beadlotsCategorySubTitle = beadlotsCategorySubTitle;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public void setPartNumberLabel(String partNumberLabel) {
        this.partNumberLabel = partNumberLabel;
    }

    public void setBeadlotsFilesTitle(String beadlotsFilesTitle) {
        this.beadlotsFilesTitle = beadlotsFilesTitle;
    }

    public void setGetDocumentsBeanList(List<InstallationsInstructionsModel> getDocumentsBeanList) {
    	if (getDocumentsBeanList == null)
	        throw new IllegalArgumentException("Parameter getDocumentsBeanList is null");
	    this.getDocumentsBeanList.clear();
	    this.getDocumentsBeanList.addAll(getDocumentsBeanList);
    }

    public void setBeadlotsGroupBeanList(List<BeadlotsGroupBean> beadlotsGroupBeanList) {
    	if (beadlotsGroupBeanList == null)
	        throw new IllegalArgumentException("Parameter beadlotsGroupBeanList is null");
	    this.beadlotsGroupBeanList.clear();
	    this.beadlotsGroupBeanList.addAll(beadlotsGroupBeanList);
    }
    
    /**
     * Gets the show status.
     *
     * @return the show status
     */
    public boolean getShowStatus() {
		return showStatus;
	}

	/**
	 * Sets the show status.
	 *
	 * @param showStatus the new show status
	 */
	public void setShowStatus(boolean showStatus) {
		this.showStatus = showStatus;
	}
}
