

package com.bdb.aem.core.models;

import com.google.gson.annotations.SerializedName;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

/**
 * The Class ApiErrorMessagesModel.
 */
@Model(adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ApiErrorMessagesModel {

    /**
     * The errorCode.
     */
    @Inject
    @SerializedName("errorCode")
    private String errorCode;

    /**
     * The errorMessage.
     */
    @Inject
    @SerializedName("errorMessage")
    private String errorMessage;

    /**
     * Gets the errorCode.
     *
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Gets the errorMessage.
     *
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
