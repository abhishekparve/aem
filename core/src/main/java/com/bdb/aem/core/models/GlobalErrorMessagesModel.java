package com.bdb.aem.core.models;

import com.bdb.aem.core.models.impl.AccountSettingsModelImpl;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import com.google.gson.annotations.SerializedName;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class GlobalErrorMessagesModel.
 */
@Model(
        adaptables = {Resource.class},
        adapters = {GlobalErrorMessagesModel.class},
        resourceType = {GlobalErrorMessagesModel.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GlobalErrorMessagesModel{
    
    /**  the RESOURCE_TYPE. */
    protected static final String RESOURCE_TYPE = "bdb-aem/components/content/globalerror/v1/globalerror";

    /**
     * The Constant log.
     */
    protected static final Logger log = LoggerFactory.getLogger(GlobalErrorMessagesModel.class);

    /** The rules heading. */
    @Inject
    @SerializedName("heading")
    private String rulesHeading;
    
    /**  the lengthRuleLabel. */
    @Inject
    @SerializedName("lengthRuleLabel")
    private String lengthRuleLabel;

    /**  the caseRuleLabel. */
    @Inject
    @SerializedName("caseRuleLabel")
    private String caseRuleLabel;

    /**  the numericRuleLabel. */
    @Inject
    @SerializedName("numericRuleLabel")
    private String numericRuleLabel;    

    /**  the symbolRuleLabel. */
    @Inject
    @SerializedName("symbolRuleLabel")
    private String symbolRuleLabel;
    
    /**  the noSpacesLabel. */
    @Inject
    @SerializedName("noSpacesLabel")
    private String noSpacesLabel;

    /**  the passwordError. */
    @Inject
    @SerializedName("passwordError")
    private String passwordError;

    /**  the passwordFLNameError. */
    @Inject
    @SerializedName("passwordFLNameError")
    private String passwordFLNameError;

    /**  the passwordPatternError. */
    @Inject
    @SerializedName("passwordPatternError")
    private String passwordPatternError;

    /**  the confPasswordError. */
    @Inject
    @SerializedName("confPasswordError")
    private String confPasswordError;

    /**  the confPasswordNotMatchError. */
    @Inject
    @SerializedName("confPasswordNotMatchError")
    private String confPasswordNotMatchError;
    
    /** The address label error. */
    @Inject
    @SerializedName("mandatoryFieldError")
    private String mandatoryFieldError;
    
    /** The phone number label error. */
    @Inject
    @SerializedName("phoneNumberLabelError")
    private String phoneNumberLabelError;

    /** The vat number label error. */
    @Inject
    @SerializedName("vatNumberLabelError")
    private String vatNumberLabelError;

    /** The currentPwdRequiredError. */
    @Inject
    @SerializedName("currentPwdRequiredError")
    private String currentPwdRequiredError;

    /**
     * The first name error.
     */
    @Inject
    @SerializedName("firstNameError")
    private String firstNameError;

    /**
     * The last name error.
     */
    @Inject
    @SerializedName("lastNameError")
    private String lastNameError;

    /**
     * The phone number validation text.
     */
    @Inject
    @SerializedName("phoneNumberValidationText")
    private String phoneNumberValidationText;

    /**
     * The phone num pattern error.
     */
    @Inject
    @SerializedName("phoneNumPatternError")
    private String phoneNumPatternError;

    /**
     * The phone num pattern error.
     */
    @Inject
    @SerializedName("apiErrorMessagesMultiField")
    private List<Resource> apiErrorMessagesMultiField;

    /**
     * The errorMessages.
     */
    @SerializedName("errorMessages")
    private List<ApiErrorMessagesModel> errorMessages = new ArrayList<>();

    /**
     * Creates and initialize populateErrorMessages
     */
    @PostConstruct
    protected void init() {
        populateErrorMessages();
    }

    /**
 * Populate the ErrorMessagesList.
 */

    private void populateErrorMessages() {
        if (apiErrorMessagesMultiField != null && !apiErrorMessagesMultiField.isEmpty()) {

            for (Resource resource : apiErrorMessagesMultiField) {
                ApiErrorMessagesModel errorMessage = resource.adaptTo(ApiErrorMessagesModel.class);
                errorMessages.add(errorMessage);
            }
        }
    }

    /**
     * Gets the rules heading.
     *
     * @return the rules heading
     */
    public String getRulesHeading() {
		return rulesHeading;
	}

	/**
     * Gets Password LengthRule Label.
     *
     * @return the Password LengthRule Label
     */
    public String getLengthRuleLabel() {
        return lengthRuleLabel;
    }

    /**
     * Gets Password caseRule Label.
     *
     * @return the Password caseRule Label
     */
    public String getCaseRuleLabel() {
        return caseRuleLabel;
    }

    /**
     * Gets Password numericRule Label.
     *
     * @return the Password numericRule Label
     */
    public String getNumericRuleLabel() {
        return numericRuleLabel;
    }

    /**
     * Gets Password  symbolRule Label.
     *
     * @return the Password  symbolRule Label
     */
    public String getSymbolRuleLabel() {
        return symbolRuleLabel;
    }
    
    /**
	 * @return the noSpacesLabel
	 */
	public String getNoSpacesLabel() {
		return noSpacesLabel;
	}

	/**
     * Gets Password Password  error.
     *
     * @return the Password  error
     */
    public String getPasswordError() {
        return passwordError;
    }

    /**
     * Gets Password  Password  FL Name error.
     *
     * @return the Password  FL Name error
     */
    public String getPasswordFLNameError() {
        return passwordFLNameError;
    }

    /**
     * Gets Password  Password  Pattern error.
     *
     * @return the Password  Pattern error
     */
    public String getPasswordPatternError() {
        return passwordPatternError;
    }


    /**
     * Gets Password  confirm Password  error.
     *
     * @return the confirm Password  error
     */
    public String getConfPasswordError() {
        return confPasswordError;
    }

    /**
     * Gets Password  confirm Password  not match error.
     *
     * @return the confirm Password  not match error
     */
    public String getConfPasswordNotMatchError() {
        return confPasswordNotMatchError;
    }

	/**
	 * Gets the mandatory field error.
	 *
	 * @return the mandatory field error
	 */
	public String getMandatoryFieldError() {
		return mandatoryFieldError;
	}

	/**
	 * Gets the phone number label error.
	 *
	 * @return the phone number label error
	 */
	public String getPhoneNumberLabelError() {
		return phoneNumberLabelError;
	}

    /**
     * Gets the vat number label error.
     *
     * @return the vat number label error
     */
    public String getVatNumberLabelError() {
        return vatNumberLabelError;
    }

    /**
     * Gets thefirstNameError.
     *
     * @return the firstNameError
     */
    public String getFirstNameError() {
        return firstNameError;
    }

    /**
     * Gets the lastNameError.
     *
     * @return the lastNameError
     */
    public String getLastNameError() {
        return lastNameError;
    }

    /**
     * Gets the phoneNumberValidationText.
     *
     * @return the phoneNumberValidationText
     */
    public String getPhoneNumberValidationText() {
        return phoneNumberValidationText;
    }

    /**
     * Gets the phoneNumPatternError.
     *
     * @return the phoneNumPatternError
     */
    public String getPhoneNumPatternError() {
        return phoneNumPatternError;
    }

    public String getCurrentPwdRequiredError() {
        return currentPwdRequiredError;
    }

    /**
     * Gets the errorMessages.
     *
     * @return the List<ApiErrorMessagesModel>
     */
    public List<ApiErrorMessagesModel> getErrorMessages() {
        return new ArrayList<>(errorMessages);
    }
}
