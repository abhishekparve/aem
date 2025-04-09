package com.bdb.aem.core.servlets;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Servlet.class, immediate = true,
property = {Constants.SERVICE_DESCRIPTION + "=" + "Query Debug",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + QueryDebugServlet.RESOURCE_TYPE
})
public class QueryDebugServlet extends SlingAllMethodsServlet{
	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "bdb/query-debug";
	
	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryDebugServlet.class);

	/**
     * Get Method.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
    	try {
    		request.getRequestDispatcher("/bin/querybuilder.json").forward(request, response);
		} catch (ServletException | IOException e) {
			LOGGER.error("ServletException | IOException",e);
		}
    }

}
