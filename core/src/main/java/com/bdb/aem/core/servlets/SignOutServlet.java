package com.bdb.aem.core.servlets;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.SignOutService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Class SignOutServlet.
 */
@Component(name = "SignOutServlet", service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "SignOutServlet",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + SignOutServlet.RESOURCE_TYPE
})
public class SignOutServlet extends BaseServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public static final String RESOURCE_TYPE = "bdb/sign-out";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SignOutServlet.class);

	/** The sign out service. */
	@Reference
	private transient SignOutService signOutService;
	
	@Reference
	private transient BDBApiEndpointService bdbApiEndpointService;

	/**
	 * Do post.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		LOGGER.debug("Entry doPost of SignOutServlet");
		doGet(request, response);
		LOGGER.debug("Exit doPost of SignOutServlet");
	}

	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

		LOGGER.debug("Entry doGet of SignOutServlet");
		Boolean flag;
		JsonObject requestObject = getRequestObject(request);
		//String redirectURL = (null != request.getParameter("data-locale") ? request.getParameter("data-locale") : StringUtils.EMPTY);
		String redirectURL = (null != request.getParameter("redirectURL") ? request.getParameter("redirectURL") : StringUtils.EMPTY);
		LOGGER.debug("Redirect URL from redirectURL param : " + redirectURL);
		if(StringUtils.isEmpty(redirectURL)) {
			redirectURL = (null != request.getParameter("ui_locales") ? request.getParameter("ui_locales") : StringUtils.EMPTY);
			LOGGER.debug("Redirect URL from ui_locales param : " + redirectURL);
		}
		
		try {
			flag = signOutService.fetchSignOutDetails(request, requestObject, response);
		if(Boolean.TRUE.equals(flag)){
			LOGGER.debug("Remove Cookies");
			LOGGER.debug("Set Redirecting status - 200");
			pingSSOLogout(request, response, redirectURL);
		}else {
			LOGGER.debug("Logging out from PING SSO when no auth token is available");
			pingSSOLogout(request, response, redirectURL);
		}
		 } catch (AemInternalServerErrorException e) {
			LOGGER.error("removeSuccessCookies {}", e);
			pingSSOLogout(request, response, redirectURL);
		}
		LOGGER.debug("Exit doGet of SignOutServlet");
	}

	/**
	 * Do a signout from PING SSO
	 * @param request
	 * @param response
	 * @param redirectURL
	 * @throws IOException
	 */
	private void pingSSOLogout(SlingHttpServletRequest request, SlingHttpServletResponse response, String redirectURL) throws IOException {
		CommonHelper.removeSuccessCookies(response, CommonConstants.BOOLEAN_TRUE);
		JsonObject redirectObj = prepareRedirectionObj(request, bdbApiEndpointService, redirectURL);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(CommonConstants.APPLICATION_JSON);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(String.valueOf(redirectObj));
	}


	/**
	 * Preparing the redirect JSON Object
	 * @param bdbApiEndpointService
	 * @return
	 */

	private JsonObject prepareRedirectionObj(SlingHttpServletRequest request, BDBApiEndpointService bdbApiEndpointService, String redirectURL) {
		String bdCiamRedirectUrl;
		if(!redirectURL.isEmpty()){
			LOGGER.debug("redirectURL is not empty in prepareRedirectionObj ");
			bdCiamRedirectUrl = bdbApiEndpointService.ciamPingIdDomain()+bdbApiEndpointService.ciamSignOutEndpoint() +
			CommonConstants.INTERROGATION+CommonConstants.P+CommonConstants.EQUAL + bdbApiEndpointService.ciamB2CId() +		
			CommonConstants.AND+CommonConstants.REDIRECT_URI+CommonConstants.EQUAL + redirectURL;
		}else{
			LOGGER.debug("redirectURL is empty in prepareRedirectionObj ");
			bdCiamRedirectUrl = bdbApiEndpointService.ciamPingIdDomain()+bdbApiEndpointService.ciamSignOutEndpoint() +
			CommonConstants.INTERROGATION+CommonConstants.P+CommonConstants.EQUAL + bdbApiEndpointService.ciamB2CId() +
			CommonConstants.AND+CommonConstants.REDIRECT_URI+CommonConstants.EQUAL	+ 
			CommonConstants.HTTPS_LITERAL+CommonConstants.COLON_SLASH_SLASH+request.getServerName()+CommonConstants.SINGLE_SLASH;
		}
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("redirectURL",bdCiamRedirectUrl);
		return jsonObject;
	}

}
