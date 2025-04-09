package com.bdb.aem.core.bean;

import java.util.ArrayList;
import java.util.List;

public class TableDetailsBean {
    private String componentTitle;
    
    /** The grey background. */
    private Boolean greyBackground;
    private List<TableTabDetailBean> data;
    
    /** Reduce Top padding. */
    private Boolean togglePaddingTop;
    
    /** Reduce Bottom Padding. */
    private Boolean togglePaddingBottom;
    
    /** The component desc. */
    private String componentDesc;

    /**
     *
     * @return List of tab Details
     */
    public List<TableTabDetailBean> getData() {
        return new ArrayList<>(data);
    }

    /**
     *
     * @param data
     */
    public void setData(List<TableTabDetailBean> data) {
        if (null != data) {
            this.data = new ArrayList<>(data);
        } else {
            this.data = new ArrayList<>();
        }
    }

    /**
     *
     * @return componentTitle
     */
    public String getComponentTitle() {
        return componentTitle;
    }

    /**
     *
     * @param componentTitle
     */
    public void setComponentTitle(String componentTitle) {
        this.componentTitle = componentTitle;
    }

	/**
	 * Gets the grey background.
	 *
	 * @return the grey background
	 */
	public Boolean getGreyBackground() {
		return greyBackground;
	}

	/**
	 * Sets the grey background.
	 *
	 * @param greyBackground the new grey background
	 */
	public void setGreyBackground(Boolean greyBackground) {
		this.greyBackground = greyBackground;
	}
	
	/**
	 * Gets the toggle padding top.
	 *
	 * @return the toggle padding top
	 */
	public Boolean getTogglePaddingTop() {
		return togglePaddingTop;
	}

	/**
	 * Sets the toggle padding top.
	 *
	 * @param togglePaddingTop the new togglePaddingTop
	 */
	public void setTogglePaddingTop(Boolean togglePaddingTop) {
		this.togglePaddingTop = togglePaddingTop;
	}
	
	/**
	 * Gets the toggle padding Bottom.
	 *
	 * @return the toggle padding Bottom
	 */
	public Boolean getTogglePaddingBottom() {
		return togglePaddingBottom;
	}

	/**
	 * Sets the toggle padding Bottom.
	 *
	 * @param togglePaddingBottom the new togglePaddingBottom
	 */
	public void setTogglePaddingBottom(Boolean togglePaddingBottom) {
		this.togglePaddingBottom = togglePaddingBottom;
	}

	/**
	 * Gets the component desc.
	 *
	 * @return the component desc
	 */
	public String getComponentDesc() {
		return componentDesc;
	}

	/**
	 * Sets the component desc.
	 *
	 * @param componentDesc the new component desc
	 */
	public void setComponentDesc(String componentDesc) {
		this.componentDesc = componentDesc;
	}
}
