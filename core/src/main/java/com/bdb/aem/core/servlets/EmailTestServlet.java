package com.bdb.aem.core.servlets;

import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.EligibleProductsApiService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.mail.MailTemplate;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import javax.mail.MessagingException;
import javax.servlet.Servlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The Servlet TilesDataServlet.
 */
@Component(service = Servlet.class, immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE,
property = {Constants.SERVICE_DESCRIPTION + "=" + "Send Email",
        ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + EmailTestServlet.RESOURCE_TYPE
})
public class EmailTestServlet extends SlingAllMethodsServlet {
	private static final long serialVersionUID = 1L;

	transient Logger logger = LoggerFactory.getLogger(EmailTestServlet.class);
	
	public static final String RESOURCE_TYPE = "bdb/send-email";

	@Reference
	transient BDBSearchEndpointService solrConfig;

	@Reference
	transient SolrSearchService solrSearchService;

	@Reference
	transient ExternalizerService externalizerService;

	@Reference
	transient ResourceResolverFactory resourceResolverFactory;

	@Reference
	transient EligibleProductsApiService eligibleProductsApiService;

	@Reference
	transient MessageGatewayService messageGatewayService;

	public static final String TEMPLATEPATH = "/etc/notification/email/acs-commons/health-check-status-email.txt";
	/** The Constant OPEN_BRACKETS. */
	public static final String OPEN_BRACKETS = "{";

	/** The Constant CLOSE_BRACKETS. */
	public static final String CLOSE_BRACKETS = "}";

	String reponse = StringUtils.EMPTY;

	/**
	 * doPost method which quires the data from solr and sends in response
	 *
	 * @param request  param
	 * @param response response param
	 * @throws IOException
	 */
	@Override
	protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
			throws IOException {
		ResourceResolver resourceResolver = null;

		try {
			String recepient = request.getParameter("mail");
			resourceResolver = CommonHelper.getReadServiceResolver(resourceResolverFactory);

			if (null != resourceResolver) {
				Resource res = resourceResolver.getResource(TEMPLATEPATH);
				reponse = "resoure null";

				if (null != res) {
					reponse = "inside res";
					Map<String, Object> map = new HashMap<String, Object>();

					String[] emailTo = { recepient };
					String sub = "testing mail";

					reponse = sendEmail(resourceResolver, res, map, emailTo, sub);

				}
			}

		} catch (LoginException e) {
			logger.error("exception in email testing {}", e.getMessage());
			reponse = e.getMessage();
		}  finally {
			response.setContentType(CommonConstants.CONTENT_TYPE_JSON);
			response.getWriter().write(reponse);
			CommonHelper.closeResourceResolver(resourceResolver);
		}

	}
	
	public String sendEmail(ResourceResolver resourceResolver, Resource templateRsrc, Map<String, Object> inputParams,
			String[] emailTo, String subject) {
		String mailSent = "false";
		Session session = null;
		try {
			if (null != templateRsrc) {
				session = resourceResolver.adaptTo(Session.class);
				MailTemplate mailTemplate = MailTemplate.create(templateRsrc.getPath(),
						session);
				if (null != mailTemplate) {
					HtmlEmail email;

					email = mailTemplate.getEmail(StrLookup.mapLookup(inputParams), HtmlEmail.class);
					
					MessageGateway<Email> messageGateway;
					if (null != email) {
						email.addTo(emailTo);
						email.setSubject(subject);
						messageGateway = messageGatewayService.getGateway(Email.class);
						messageGateway.send((Email) email);
						mailSent = "true";
					}
				}
			} else if (StringUtils.isNotBlank(TEMPLATEPATH)) {

				MessageGateway<HtmlEmail> messageGateway = messageGatewayService.getGateway(HtmlEmail.class);
				HtmlEmail email = new HtmlEmail();
				email.addTo(emailTo);
				email.setSubject(subject);
				messageGateway.send(email.setHtmlMsg(replaceHTMLResponseWithParams(TEMPLATEPATH, inputParams)));
			}
		}catch (IOException | MessagingException | EmailException e) {
			logger.error("exception in  send email method {}", e.getMessage());
			mailSent = e.getMessage();
		} finally {
			CommonHelper.closeSession(session);
		}
		return mailSent;
	}
	

	/**
	 * Replace HTML response with params.
	 *
	 * @param htmlResponse the html response
	 * @param inputParams  the input params
	 * @return the string
	 */
	private String replaceHTMLResponseWithParams(String htmlResponse, Map<String, Object> inputParams) {

		Iterator<Entry<String, Object>> itr = inputParams.entrySet().iterator();

		if (null != itr) {
			while (itr.hasNext()) {
				String key = itr.next().getKey();
				String htmlReplacableKeyValue = OPEN_BRACKETS.concat(key).concat(CLOSE_BRACKETS);
				if (htmlResponse.contains(htmlReplacableKeyValue)) {
					htmlResponse = htmlResponse.replace(htmlReplacableKeyValue, inputParams.get(key).toString());
				}
			}
		}
		return htmlResponse;
	}

}
