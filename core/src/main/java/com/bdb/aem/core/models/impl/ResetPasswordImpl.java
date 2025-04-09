package com.bdb.aem.core.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.bdb.aem.core.models.GlobalErrorMessagesModel;
import com.bdb.aem.core.models.ResetPasswordModel;
import com.bdb.aem.core.pojo.PasswordRuleLabels;
import com.bdb.aem.core.pojo.ResetPasswordModelConfig;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.ExcludeUtil;
import com.bdb.aem.core.util.GlobalErrorMessagesModelUtil;
import com.bdb.aem.core.util.UrlHandler;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * The Class ResetPasswordImpl.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, adapters = { ResetPasswordModel.class }, resourceType = {
		ResetPasswordImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class ResetPasswordImpl implements ResetPasswordModel {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/resetpassword/v1/resetpassword";

	/** The title. */
	@Inject
	@Via("resource")
	private String title;

	/** The description. */
	@Inject
	@Via("resource")
	@Named("description")
	private String description;

	/** The create password placeholder label. */
	@Inject
	@Via("resource")
	@Named("createPasswordPlaceholderLabel")
	private String createPasswordPlaceholderLabel;

	/** The confirm password placeholder label. */
	@Inject
	@Via("resource")
	@Named("confirmPasswordPlaceholderLabel")
	private String confirmPasswordPlaceholderLabel;

	/** The reset password CTA label. */
	@Inject
	@Via("resource")
	@Named("resetPasswordCTALabel")
	private String resetPasswordCTALabel;

	/** The reset password redirect url. */
	@Inject
	@Via("resource")
	@Named("resetPasswordRedirectUrl")
	private String resetPasswordRedirectUrl;

	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;

	/** The bdb api endpoint service. */
	@Inject
    private BDBApiEndpointService bdbApiEndpointService;

	/** The current page. */
	@Inject
	private Page currentPage;

	/** The reset password labels. */
	private String resetPasswordLabels;

	/** The reset password config. */
	private String resetPasswordConfig;

	/** The Constant PASS_RULES_RESOURCE_TYPE. */
	protected static final String PASS_RULES_RESOURCE_TYPE = "bdb-aem/proxy/components/content/globalerror";

	
	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {

		ExcludeUtil excludeUtilObject = new ExcludeUtil();
		if(null != bdbApiEndpointService) {

		 //To get Reset Password Labels
		   setResetPasswordLabels(excludeUtilObject);
		   
		//To set the Reset password config json
		   resetPasswordConfig = setResetPasswordConfiguration();
		}
	}

	
    /**
     * Sets the reset password labels.
     *
     * @param excludeUtilObject the new reset password labels
     */
	private void setResetPasswordLabels(ExcludeUtil excludeUtilObject){
        GlobalErrorMessagesModel errorModel = GlobalErrorMessagesModelUtil.getErrorMessageModel(
				CommonHelper.getErrorPagePath(currentPage), resourceResolver,
                PASS_RULES_RESOURCE_TYPE);

        ResetPasswordModelConfig resetPasswordModelConfig = createResetPasswordModelLabels(errorModel);
        Gson resetPasswordJson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                .addSerializationExclusionStrategy(excludeUtilObject).create();
        if (resetPasswordModelConfig != null) {
            resetPasswordLabels = resetPasswordJson.toJson(resetPasswordModelConfig);
        }
    }

	/**
	 * Creates the reset password model labels.
	 *
	 * @param errorModel the error model
	 * @return the reset password model config
	 */
	private ResetPasswordModelConfig createResetPasswordModelLabels(GlobalErrorMessagesModel errorModel) {
		PasswordRuleLabels passwordRuleLabelsObj =  new PasswordRuleLabels() ;
		ResetPasswordModelConfig resetPasswordModelConfig;
		if(null!= errorModel){
        passwordRuleLabelsObj = new PasswordRuleLabels(errorModel.getRulesHeading(), errorModel.getLengthRuleLabel(),
                errorModel.getCaseRuleLabel(), errorModel.getNumericRuleLabel(),
                errorModel.getSymbolRuleLabel(), errorModel.getNoSpacesLabel());

		resetPasswordModelConfig = new ResetPasswordModelConfig(title, description, createPasswordPlaceholderLabel,
				confirmPasswordPlaceholderLabel, UrlHandler.getModifiedUrl(resetPasswordRedirectUrl, resourceResolver), resetPasswordCTALabel,
				errorModel.getPasswordError(),errorModel.getPasswordFLNameError(),errorModel.getPasswordPatternError(),
				errorModel.getConfPasswordError(),errorModel.getConfPasswordNotMatchError(),passwordRuleLabelsObj);
		}
		else{
			resetPasswordModelConfig = new ResetPasswordModelConfig(title, description, createPasswordPlaceholderLabel,
					confirmPasswordPlaceholderLabel, resetPasswordRedirectUrl, resetPasswordCTALabel,passwordRuleLabelsObj);

		}
		return resetPasswordModelConfig;
	}

    /**
     * Sets the reset password configuration.
     *
     * @return the string
     */
	private String setResetPasswordConfiguration()
    {
		String hybrisResetPasswordEndpoint = CommonHelper.getEndPointUrl( bdbApiEndpointService,
						bdbApiEndpointService.getHybrisResetPasswordEndpoint().trim(),
						true, currentPage);

		String hybrisValidatePasswordEndpoint = CommonHelper.getEndPointUrl( bdbApiEndpointService,
						bdbApiEndpointService.getHybrisValidatePasswordEndpoint().trim(),
						true, currentPage);

		JsonObject passwordConfig = new JsonObject();
		JsonObject postResetPasswordPayload = new JsonObject();
		JsonObject validatePasswordToken = new JsonObject();
		
		if (StringUtils.isNotEmpty(hybrisResetPasswordEndpoint)||StringUtils.isNotEmpty(hybrisValidatePasswordEndpoint)) {
			postResetPasswordPayload.addProperty(CommonConstants.URL, hybrisResetPasswordEndpoint);
			postResetPasswordPayload.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);
			
			validatePasswordToken.addProperty(CommonConstants.URL, hybrisValidatePasswordEndpoint);
			validatePasswordToken.addProperty(CommonConstants.METHOD, HttpConstants.METHOD_POST);

			passwordConfig.add("postResetPasswordPayload", postResetPasswordPayload);
			passwordConfig.add("validatePasswordToken", validatePasswordToken);
		}

		return passwordConfig.toString();
    }

	
	/**
	 * Gets the reset password labels.
	 *
	 * @return the reset password labels
	 */
	@Override
	public String getResetPasswordLabels() {
		return resetPasswordLabels;
	}

	/**
	 * Gets the reset password config.
	 *
	 * @return the reset password config
	 */
	@Override
	public String getResetPasswordConfig() {
		return resetPasswordConfig;
	}
}