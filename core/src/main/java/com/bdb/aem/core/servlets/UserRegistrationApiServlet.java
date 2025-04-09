package com.bdb.aem.core.servlets;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.exceptions.InvalidRequestException;
import com.bdb.aem.core.services.UserRegistrationApiService;
import com.bdb.aem.core.util.CommonConstants;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet to fetch the details related to user registration flow of SIgn up,
 * Area of focus, Reset password and purchase account.
 */

@Component(name = "UserRegistrationApiServlet", immediate = true, service = Servlet.class, property = {
		ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + UserRegistrationApiServlet.RESOURCE_TYPE,
		ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_GET,
		ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_POST,
		ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + CommonConstants.SIGN_UP_CALL,
		ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + CommonConstants.AREA_OF_FOCUS,
		ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + CommonConstants.RESET_PASS,
		ServletResolverConstants.SLING_SERVLET_SELECTORS + CommonConstants.EQUAL + CommonConstants.PURCHASE_ACCOUNT,
		ServletResolverConstants.SLING_SERVLET_EXTENSIONS + CommonConstants.EQUAL + CommonConstants.JSON })
public class UserRegistrationApiServlet extends BaseServlet {
	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationApiServlet.class);
	
	public static final String RESOURCE_TYPE = "bdb/register-user";

	/**
	 * The base api service for hybris calls.
	 */
	@Reference
	private transient UserRegistrationApiService userRegistrationApiService;

	/**
	 * Post method.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		LOGGER.debug("Entry doPost of UserRegistrationApiServlet");
		doGet(request, response);
		LOGGER.debug("Exit doPost of UserRegistrationApiServlet");
	}

	/**
	 * Do get.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

		LOGGER.debug("Entry doGet of UserRegistrationApiServlet");
		PrintWriter writer;
		String apiResponse = StringUtils.EMPTY;
		JsonObject requestObject = getRequestObject(request);

		try {
			RequestPathInfo requestPathInfo = request.getRequestPathInfo();
			String[] selectors = requestPathInfo.getSelectors();
			if (selectors.length != 0 && selectors.length <= 1) {
				switch (selectors[0]) {
				case CommonConstants.SIGN_UP_CALL:
					apiResponse = userRegistrationApiService.fetchSignUpDetails(request, requestObject, response);
					break;
				case CommonConstants.AREA_OF_FOCUS:
					apiResponse = userRegistrationApiService.areaOfFocus(request, requestObject, response);
					break;
				case CommonConstants.RESET_PASS:
					apiResponse = userRegistrationApiService.resetPassword(request, requestObject, response);
					break;
				case CommonConstants.PURCHASE_ACCOUNT:
					apiResponse = userRegistrationApiService.purchaseAccount(request, requestObject, response);
					break;
				default:
					throw new InvalidRequestException(CommonConstants.INVALID_REQUEST_TYPE);
				}
			} else {
				throw new InvalidRequestException(CommonConstants.INVALID_REQUEST_TYPE);
			}
		} catch (InvalidRequestException exception) {
			apiResponse = exception.getMessage();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			LOGGER.error("Invalid Request: {0} ", exception);
		} catch (AemInternalServerErrorException exception) {
			apiResponse = exception.getMessage();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			LOGGER.error("Exception Occurred during rest client execution {0}", exception.getCause());
		} finally {
			response.setContentType(CommonConstants.APPLICATION_JSON);
			response.setCharacterEncoding("UTF-8");
			writer = response.getWriter();
			if (null != writer) {
				writer.write(apiResponse);
				writer.flush();
				writer.close();
			}
		}

		LOGGER.debug("Exit doGet of UserRegistrationApiServlet");

	}
}
