/*
package com.bdb.aem.core.servlets;

import static org.powermock.api.mockito.PowerMockito.mock;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.mail.MailTemplate;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({EmailTestServlet.class, CommonHelper.class, MailTemplate.class})
public class EmailTestServletTest {
	private EmailTestServlet emailTestServlet;
	private Resource resource;
	private ResourceResolver resourceResolver;
	private SlingHttpServletRequest request;
	private SlingHttpServletResponse response;
	private Session session;
	private ResourceResolverFactory resourceResolverFactory;
	private MessageGatewayService messageGatewayService;
	private String templatePath = "/etc/notification/email/acs-commons/health-check-status-email.txt";
	private MailTemplate mailTemplate;
	private MessageGateway<Email> messageGateway;
	private HtmlEmail email;
	Map<String, Object> inputParams;
	private PrintWriter printwriter;
	
	*/
/**
	 * Sets up Test.
	 *
	 * @throws Exception the exception
	 *//*

	@Before
	public void setUp() throws Exception {	
		emailTestServlet = PowerMockito.spy(new EmailTestServlet());
		resourceResolverFactory = PowerMockito.mock(ResourceResolverFactory.class);
		messageGatewayService = PowerMockito.mock(MessageGatewayService.class);
		resourceResolver = PowerMockito.mock(ResourceResolver.class);
		request = PowerMockito.mock(SlingHttpServletRequest.class);
		response = PowerMockito.mock(SlingHttpServletResponse.class);
		session = PowerMockito.mock(Session.class);
		resource = PowerMockito.mock(Resource.class);
		mailTemplate = PowerMockito.mock(MailTemplate.class);
		messageGateway = PowerMockito.mock(MessageGateway.class);
		email = PowerMockito.mock(HtmlEmail.class);
		PowerMockito.mockStatic(MailTemplate.class);
		printwriter = mock(PrintWriter.class);
		Whitebox.setInternalState(emailTestServlet, resourceResolverFactory, resourceResolverFactory);
		Map<String, Object> writeServiceAuth = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE,
				"writeService");
		PowerMockito.when(resourceResolverFactory.getServiceResourceResolver(writeServiceAuth)).thenReturn(resourceResolver);
		PowerMockito.when(CommonHelper.getServiceResolver(resourceResolverFactory)).thenReturn(resourceResolver);
		PowerMockito.when(request.getParameter("mail")).thenReturn("string");
		PowerMockito.when(resourceResolver.getResource(templatePath)).thenReturn(resource);
		PowerMockito.when(resource.getPath()).thenReturn("path");
		PowerMockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		PowerMockito.when(MailTemplate.create("path", session)).thenReturn(mailTemplate);
		PowerMockito.when(mailTemplate.getEmail(StrLookup.mapLookup(inputParams), HtmlEmail.class)).thenReturn(email);
		PowerMockito.when(messageGatewayService.getGateway(Email.class)).thenReturn(messageGateway);
		PowerMockito.when(response.getWriter()).thenReturn(printwriter);
	}
	
	*/
/**
	 * Tests doGet.
	 *
	 * @throws IOException
	 *//*

	@Test
	public void testDoGet() throws IOException {
		emailTestServlet.doGet(request, response);
	}
	
	
	*/
/**
	 * @throws IOException
	 *//*

	@Test
	public void IOExceptionTest() throws IOException {
		//PowerMockito.when(request.getParameter("mail")).thenThrow(IOException.class);
		emailTestServlet.doGet(request, response);
	}

}
*/
