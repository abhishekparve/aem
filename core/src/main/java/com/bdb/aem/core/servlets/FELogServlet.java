package com.bdb.aem.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.util.CommonConstants;

@Component(name = "FELogServlet", immediate = true, service = Servlet.class, property = 
				{Constants.SERVICE_DESCRIPTION + "=" + "FELogServlet",
				ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
				ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + FELogServlet.RESOURCE_TYPE
		})
public class FELogServlet extends SlingAllMethodsServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FELogServlet.class);
	public static final String RESOURCE_TYPE = "bdb/feLogs";
	@Override
	public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		BufferedReader reader = request.getReader();
		StringBuilder jb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			jb.append(line);
		}
		
		LOGGER.debug("FELogServlet response string :: {}", jb.toString());
		response.setContentType(CommonConstants.APPLICATION_JSON);
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(jb.toString());	
		
	}
}
