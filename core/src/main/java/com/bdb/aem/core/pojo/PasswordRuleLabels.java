package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * The Class PasswordRuleLabels.
 */
public class PasswordRuleLabels {
	
	/** The rules heading. */
	@SerializedName("heading")
    private String rulesHeading;

    /** the length Rule Label */
    @SerializedName("lengthRuleLabel")
    private String lengthRuleLabel;

    /** the case Rule Label */
    @SerializedName("caseRuleLabel")
    private String caseRuleLabel;

    /** the numeric Rule Label */
    @SerializedName("numericRuleLabel")
    private String numericRuleLabel;

    /** the symbol Rule Label */
    @SerializedName("symbolRuleLabel")
    private String symbolRuleLabel;
    
    /** the no Spaces Label */
    @SerializedName("noSpacesLabel")
    private String noSpacesLabel;

   
    /**
     * Instantiates a new password rule labels.
     *
     * @param rulesHeading the rules heading
     * @param lengthRuleLabel the length rule label
     * @param caseRuleLabel the case rule label
     * @param numericRuleLabel the numeric rule label
     * @param symbolRuleLabel the symbol rule label
     */
    public PasswordRuleLabels(String rulesHeading, String lengthRuleLabel, String caseRuleLabel, String numericRuleLabel, String symbolRuleLabel, String noSpacesLabel) {
        this.rulesHeading = rulesHeading;
    	this.lengthRuleLabel = lengthRuleLabel;
        this.caseRuleLabel = caseRuleLabel;
        this.numericRuleLabel = numericRuleLabel;
        this.symbolRuleLabel = symbolRuleLabel;
        this.noSpacesLabel = noSpacesLabel;
    }

    public PasswordRuleLabels() {}

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
    
}