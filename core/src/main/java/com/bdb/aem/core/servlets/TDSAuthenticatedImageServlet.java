package com.bdb.aem.core.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.Servlet;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonConstants;

@Component(name = "TDSImageAuthentication", immediate = true, service = Servlet.class, property = {
		"sling.auth.requirements" + CommonConstants.EQUAL + "-/bin/tdsAuthentication",
		ServletResolverConstants.SLING_SERVLET_PATHS + CommonConstants.EQUAL + "/bin/tdsAuthentication",
		ServletResolverConstants.SLING_SERVLET_METHODS + CommonConstants.EQUAL + HttpConstants.METHOD_GET})
public class TDSAuthenticatedImageServlet extends BaseServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String PROD_ENDPOINT_URL="https://prd-gl.bdbiosciences.com";

	public static final String RESOURCE_TYPE = "bdb/tdsAuthentication";
	/**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TDSAuthenticatedImageServlet.class);
    
    @Reference
    private transient BDBApiEndpointService bdbApiEndpointService;
	
	@Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        
		String imagePath = request.getParameter("imagePath");
		String userName = bdbApiEndpointService.getProdUserName();
		String password = bdbApiEndpointService.getProdUserPassword();
		String endPoint = PROD_ENDPOINT_URL.concat(imagePath);
		
		int scketTimeoutConfig = bdbApiEndpointService.getSocketTimeoutConfig();
		int requestTimeoutConfig = bdbApiEndpointService.getRequestTimeoutConfig();
		int connectTimeoutConfig = bdbApiEndpointService.getConnectTimeoutConfig();

		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES)
				.setSocketTimeout(scketTimeoutConfig * 1000).setConnectTimeout(connectTimeoutConfig * 1000)
				.setConnectionRequestTimeout(requestTimeoutConfig * 1000).build();
		
		String userCredentials =userName + CommonConstants.COLON
				+ password;
		String basicAuth = CommonConstants.BASIC + CommonConstants.SPACE
				+ new String(Base64.getEncoder().encode(userCredentials.getBytes()));
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
		
		HttpGet httpRequest = new HttpGet(endPoint);
		httpRequest.addHeader(CommonConstants.HTTP_RESPONSE_CONTENT_TYPE, "image/png");
		httpRequest.setHeader(CommonConstants.AUTHORIZATION, basicAuth);
		HttpResponse imgResponse=httpClient.execute(httpRequest);
		
		if (imgResponse.getStatusLine().getStatusCode() == 200) {
			LOGGER.debug("request succeful for prod {}",imgResponse.getStatusLine().getStatusCode());
			InputStream is = imgResponse.getEntity().getContent();
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			BufferedImage inputImage = ImageIO.read(is);
			ImageIO.write(inputImage, "png", out);
			
			response.setContentType("image/png");
			LOGGER.debug("size of out file :: {}",out.size());
			response.setContentLength(out.size());
			response.getOutputStream().write(out.toByteArray());
			response.getOutputStream().flush();
		} 
		
		
    }
}


