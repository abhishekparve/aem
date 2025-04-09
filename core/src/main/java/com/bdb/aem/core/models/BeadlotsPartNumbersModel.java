package com.bdb.aem.core.models;


import com.google.gson.annotations.SerializedName;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * The Class BeadlotsPartNumbersModel.
 */
@Model(adaptables = {Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BeadlotsPartNumbersModel {

    /** The partNumber. */
    @Inject
    @Named("partNumber")
    @SerializedName("partNumber")
    private String partNumber;

    /** The badge. */
    @Inject
    @Named("badge")
    @SerializedName("badge")
    private String badge;

    /** get the partNumber. */
    public String getPartNumber() {
        return partNumber;
    }

    /** get the badge. */
    public String getBadge() {
        return badge;
    }
}
