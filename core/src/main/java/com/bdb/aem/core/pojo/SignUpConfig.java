package com.bdb.aem.core.pojo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * The Class SignUpConfig.
 */
public class SignUpConfig {
    
    /**  the Sign In Url. */
    @SerializedName("signInUrl")
    private String signInUrl;

    /**  The re-captcha Site Key. */
    @SerializedName("siteKey")
    private String captchaSiteKey;

    /**  the getCountriesPayload. */
    @SerializedName("getCountriesPayload")
    private Payload getCountriesPayload;

    /**  the postRegisterPayload. */
    @SerializedName("postRegisterPayload")
    private Payload postRegisterPayload;
    
    /** The sing up OT consent data. */
    @SerializedName("singUpOTConsentData")
    private JsonObject singUpOTConsentData;
    
    /** The email validation payload. */
    @SerializedName("emailValidationPayload")
    private Payload emailValidationPayload;


    /**
     * Instantiates a new sign up config.
     *
     * @param signInUrl the sign in url
     * @param captchaSiteKey the captcha site key
     * @param getCountriesPayload the get countries payload
     * @param postRegisterPayload the post register payload
     * @param singUpOTConsentData the sing up OT consent data
     */
    public SignUpConfig(String signInUrl, String captchaSiteKey, Payload getCountriesPayload, 
    		Payload postRegisterPayload, JsonObject singUpOTConsentData){
        this.signInUrl=signInUrl;
        this.captchaSiteKey = captchaSiteKey;
        this.getCountriesPayload = getCountriesPayload;
        this.postRegisterPayload = postRegisterPayload;
        this.singUpOTConsentData = singUpOTConsentData;
    }

	/**
     * Gets the Sign In Url.
     *
     * @return the Sign In Url
     */
    public String getSignInUrl() {
        return signInUrl;
    }


    /**
     * Gets The re-captcha Site Key.
     *
     * @return the re-captcha Site Key
     */
    public String getCaptchaSiteKey() {
        return captchaSiteKey;
    }

    /**
     * Gets postRegisterPayload.
     *
     * @return the postRegisterPayload
     */

    public Payload getPostRegisterPayload() {
        return postRegisterPayload;
    }

    /**
     * Gets the getCountriesPayload .
     *
     * @return the getCountriesPayload
     */
    public Payload getGetCountriesPayload() {
        return getCountriesPayload;
    }

	/**
	 * Gets the sing up OT consent data.
	 *
	 * @return the sing up OT consent data
	 */
	public JsonElement getSingUpOTConsentData() {
		return singUpOTConsentData;
	}

	/**
	 * Gets the email validation payload.
	 *
	 * @return the email validation payload
	 */
	public Payload getEmailValidationPayload() {
		return emailValidationPayload;
	}

	/**
	 * Sets the email validation payload.
	 *
	 * @param emailValidationPayload the new email validation payload
	 */
	public void setEmailValidationPayload(Payload emailValidationPayload) {
		this.emailValidationPayload = emailValidationPayload;
	}

}
