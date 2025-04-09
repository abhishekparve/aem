package com.bdb.aem.core.servlets;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.util.CommonConstants;

/** 
 * To check whether the user is logged or not based on signed_in_user cookie
 */
@Component(name = "AuthcheckerServlet", service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION + "=" + "Authentication Checker",
		ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_HEAD,
		ServletResolverConstants.SLING_SERVLET_PATHS + CommonConstants.EQUAL + "/bin/permissioncheck"})
public class AuthcheckerServlet extends SlingSafeMethodsServlet  {

	/**
     * The Constant serialVersionUID.
     */
	private static final long serialVersionUID = 1326654863854767752L;

	/** The LOGGER */
	private static final Logger logger = LoggerFactory.getLogger(AuthcheckerServlet.class);

	@Override
	public void doHead(SlingHttpServletRequest request, SlingHttpServletResponse response) {
		logger.info("Invoking AuthchekerServlet Servlet");
	     try {
	    	 logger.info("AuthchekerServlet Servlet inside try block");
	    	 // check whether signed_in_user cookie is being set in request obj and it's value is true
		      if (null != request.getCookie(CommonConstants.SIGNED_IN_USER) 
		    		  && request.getCookie(CommonConstants.SIGNED_IN_USER).toString().equalsIgnoreCase(CommonConstants.STRING_TRUE)) {
		    	  logger.info("authchecker says OK");
		    	  response.setStatus(SlingHttpServletResponse.SC_OK);
		      }
		      else {
		    	  logger.info("authchecker says READ access DENIED!");
		          response.setStatus(SlingHttpServletResponse.SC_FORBIDDEN);
		      }
	     } catch(Exception e) {
	    	 logger.error("authchecker servlet exception: {}", e.getMessage());
	    	 response.setStatus(SlingHttpServletResponse.SC_FORBIDDEN);
	     }
	}
}