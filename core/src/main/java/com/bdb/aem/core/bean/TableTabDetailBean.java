package com.bdb.aem.core.bean;

import java.util.ArrayList;
import java.util.List;

public class TableTabDetailBean {
    private String tabTitle;
    private Boolean hideFirstColumn;
    private Boolean isRequestQuoteForm;
    
    public Boolean getIsRequestQuoteForm() {
		return isRequestQuoteForm;
	}

	public void setIsRequestQuoteForm(Boolean isRequestQuoteForm) {
		this.isRequestQuoteForm = isRequestQuoteForm;
	}

	public Boolean getHideFirstColumn() {
		return hideFirstColumn;
	}

	public void setHideFirstColumn(Boolean hideFirstColumn) {
		this.hideFirstColumn = hideFirstColumn;
	}

	private List<TableImageDetailBean> imageDetails;

    /**
     *
     * @return tabTitle
     */
    public String getTabTitle() {
        return tabTitle;
    }

    /**
     *
     * @param tabTitle
     */
    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    /**
     *
     * @return imageDetails
     */
    public List<TableImageDetailBean> getImageDetails() {
        return new ArrayList<>(imageDetails);
    }

    /**
     * 
     * @param imageDetails
     */
    public void setImageDetails(List<TableImageDetailBean> imageDetails) {
        if (null != imageDetails) {
            this.imageDetails = new ArrayList<>(imageDetails);
        } else {
            this.imageDetails = new ArrayList<>();
        }
    }
}
