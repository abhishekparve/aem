package com.bdb.aem.core.bean;

import org.apache.commons.lang.StringUtils;

public class IncludedPurchaseLabelBean {

    /** The Constant N_A. */
    protected static final String N_A = "N/A";

    /** The size. */
    private String quantitySize = N_A;

    /** The description. */
    private String productName = N_A;

    /** The material number. */
    private String catalogNumber = N_A;

    public String getQuantitySize(){
        if(StringUtils.isEmpty(quantitySize.trim()))
            return N_A;
        return quantitySize;
    }

    public String getProductName(){
        if(StringUtils.isEmpty(productName.trim()))
            return N_A;
        return productName;
    }

    public String getCatalogNumber(){
        if(StringUtils.isEmpty(catalogNumber.trim()))
            return N_A;
        return catalogNumber;
    }


}
