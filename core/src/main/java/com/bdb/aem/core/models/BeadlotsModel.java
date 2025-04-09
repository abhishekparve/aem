package com.bdb.aem.core.models;

import com.bdb.aem.core.bean.BeadlotsCategoryBean;
import com.bdb.aem.core.bean.BeadlotsGroupBean;

import java.util.List;

public interface BeadlotsModel {

    /**
     * gets the BeadlotsGroupBeanList
     *
     * @return the beadlotsGroupBeanList
     */

    public List<BeadlotsGroupBean> getBeadlotsGroupBeanList();

    /**
     * gets the getBeadlotsCategoryTitle
     *
     * @return the getBeadlotsCategoryTitle
     */
    public String getBeadlotsCategoryTitle();

    /**
     * gets the getBeadlotsCategorySubTitle
     *
     * @return the getBeadlotsCategorySubTitle
     */
    public String getBeadlotsCategorySubTitle();

    /**
     * gets the getStatusLabel
     *
     * @return the getStatusLabel
     */
    public String getStatusLabel();

    /**
     * gets the getPartNumberLabel
     *
     * @return the getPartNumberLabel
     */
    public String getPartNumberLabel();

    /**
     * gets the getBeadlotsFilesTitle
     *
     * @return the getBeadlotsFilesTitle
     */
    public String getBeadlotsFilesTitle();

    /**
     * gets the getBeadlotsCategoryBean
     *
     * @return the getBeadlotsCategoryBean
     */
    public BeadlotsCategoryBean getBeadlotsCategoryBean();
}
