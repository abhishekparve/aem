package com.bdb.aem.core.bean;

/**
 * Hotspots Details
 */
public class PIPHotSpotBean {

	/**
     * X Axis of Hotspot
     */
    private String xco_ordinate;
    /**
     * Y Axis of Hotspot
     */
    private String yco_cordinate;
    /**
     * Description of Hotspot
     */
    private String description;

    /**
     *
     * @return X axis
     */
    public String getXco_ordinate() {
        return xco_ordinate;
    }

    /**
     *
     * @param xco_ordinate
     */
    public void setXco_ordinate(String xco_ordinate) {
        this.xco_ordinate = xco_ordinate;
    }

    /**
     *
     * @return Y axis
     */
    public String getYco_cordinate() {
        return yco_cordinate;
    }

    /**
     *
     * @param yco_cordinate
     */
    public void setYco_cordinate(String yco_cordinate) {
        this.yco_cordinate = yco_cordinate;
    }

    /**
     *
     * @return Description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
