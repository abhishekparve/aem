package com.bdb.aem.core.models;

import com.google.gson.annotations.SerializedName;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

/**
 * The Class CCList.
 */
@Model(adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CCList {

    /**
     * The ccId.
     */
    @Inject
    @SerializedName("ccId")
    private String ccId;

    /**
     * The ccIcon.
     */
    @Inject
    @SerializedName("ccIcon")
    private String ccIcon;

    /**
     * The ccLength.
     */
    @Inject
    @SerializedName("ccLength")
    private String ccLength;

    /**
     * Gets the ccId.
     *
     * @return the ccId
     */
    public String getCcId() {
        return ccId;
    }

    /**
     * Gets the ccIcon.
     *
     * @return the ccIcon
     */
    public String getCcIcon() {
        return ccIcon;
    }

    /**
     * Gets the ccLength.
     *
     * @return the ccLength
     */
    public String getCcLength() {
        return ccLength;
    }
}
