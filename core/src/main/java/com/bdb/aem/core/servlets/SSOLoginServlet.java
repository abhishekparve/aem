
package com.bdb.aem.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.exceptions.InvalidRequestException;
import com.bdb.aem.core.services.SSOLoginService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;

/**
 * The SSO login connectivity servlet.
 */
@Component(name = "SSOLoginServlet", immediate = true, service = Servlet.class, property = {
		 ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + SSOLoginServlet.RESOURCE_TYPE,
		ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_GET,
		ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_POST})
public class SSOLoginServlet extends BaseServlet {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "bdb/sso-customer-login";

	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SSOLoginServlet.class);

	/** The Constant STATE_CODE_ERROR_TEXT. */
	private static final String STATE_CODE_ERROR_TEXT = "State code did not match";

	/** The SSO login service. */
	@Reference
	private transient SSOLoginService sSOLoginService;
	
	/** The resource resolver factory. */
	@Reference
	transient ResourceResolverFactory resourceResolverFactory;

	/**
	 * Post method.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		LOGGER.debug("Entry doPost of SSOLoginServlet");
		doGet(request, response);
		LOGGER.debug("Exit doPost of SSOLoginServlet"); 
	}

	/**
	 * Do get.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

		LOGGER.debug("SSOLoginServlet- request: {}", request);
		Map<String, String> queryParams = Collections.emptyMap();
		ResourceResolver resourceResolver = null;
		String apiResponse = StringUtils.EMPTY;
		String redirectUrl = StringUtils.EMPTY;
		String countryCode = null;

		try {
			queryParams = CommonHelper.getRequiredParams(request);
			resourceResolver = request.getResourceResolver();
			redirectUrl = sSOLoginService.getRedirectUrl(resourceResolver, queryParams);
			String specificCookie = CommonHelper.getSpecificCookie(request, CommonConstants.FE_SSO_COOKIE_NAME);
			LOGGER.debug("In SSOLoginServlet Specific Cookie : {}, Query Params : {}", specificCookie, queryParams);
			if (!MapUtils.isEmpty(queryParams) && sSOLoginService.isRequiredQueryParamAvailable(queryParams)
					&& StringUtils.isNotBlank(specificCookie)) {
				if (CommonHelper.isSecurityCodeSame(queryParams, specificCookie.trim())) {
					countryCode = sSOLoginService.fetchUserDetails(request, response, queryParams);
					LOGGER.debug("Country code from CCV2 :"+ countryCode);
					// To get new region redirect url when user enters wrong country credentials
					if(!CommonHelper.isSameCountryUrl(redirectUrl, countryCode)) {
						redirectUrl = sSOLoginService.getRegionRedirectUrl(redirectUrl, countryCode);
						LOGGER.debug(" New redirectUrl in doGet:"+ redirectUrl);
					}
				} else {
					throw new AemInternalServerErrorException(CommonConstants.AEM_SSO_ERROR_CODE,
							new InvalidRequestException(STATE_CODE_ERROR_TEXT));
				}
			} else {
				throw new InvalidRequestException(CommonConstants.MISSING_QUERYPARAM_ERROR_TEXT);
			}
		} catch (AemInternalServerErrorException exception) {
			LOGGER.error("Exception Occurred in SSO Login {}, Message : {}", exception.getCause(),exception.getCause().getMessage());
			sSOLoginService.setFailureResponseCookies(response, exception.getMessage());
		} catch (InvalidRequestException exception) {
			apiResponse = exception.getMessage();
			setErrorResponse(response, exception);
		} finally {
			if (StringUtils.isNotBlank(apiResponse)) {
				response.setContentType(CommonConstants.APPLICATION_JSON);
				response.setCharacterEncoding("UTF-8");
				PrintWriter writer = response.getWriter();
				if (null != writer) {
					writer.write(apiResponse);
					writer.flush();
					writer.close();
				}
			} else {
				response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
				response.sendRedirect(redirectUrl);
			}
		}

		LOGGER.debug("SSOLoginServlet Exit");

	}

	/**
	 * Sets the error response.
	 *
	 * @param response the response
	 * @param exception the exception
	 */
	private void setErrorResponse(SlingHttpServletResponse response, Exception exception) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		sSOLoginService.removeFailureCookies(response, CommonConstants.BOOLEAN_TRUE);
		CommonHelper.removeSuccessCookies(response, CommonConstants.BOOLEAN_TRUE);
		LOGGER.error("Invalid Request: {0} ", exception);
	}
}
