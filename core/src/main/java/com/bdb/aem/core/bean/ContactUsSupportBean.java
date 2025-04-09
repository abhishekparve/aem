package com.bdb.aem.core.bean;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.components.ComponentContext;
import com.google.gson.annotations.SerializedName;

/**
 * The Class ContactUsSupportBean.
 * 
 * @author phanindra
 * @return Multi-field data as a bean.
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContactUsSupportBean {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(ContactUsSupportBean.class);

	/** The Title Text */
	@Inject
	@SerializedName("titleText")
	private String titleText;

	/** The Sub Text */
	@Inject
	@SerializedName("subText")
	private String subText;

	/** The Address */
	@Inject
	@SerializedName("address")
	private String address;

	/** The Description */
	@Inject
	@SerializedName("description")
	private String description;

	/** The Email Link Label */
	@Inject
	@SerializedName("emailLabel")
	private String emailLabel;

	/** The Email Link Url */
	@Inject
	@SerializedName("emailUrl")
	private String emailUrl;

	/** The Course Link Label */
	@Inject
	@SerializedName("courseLabel")
	private String courseLabel;

	/** The Course Link Url */
	@Inject
	@SerializedName("courseUrl")
	private String courseUrl;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

	@PostConstruct
	protected void init() {
			logger.debug("ContactUsSupportBean Initiated");
			emailUrl = externalizerService.getFormattedUrl(emailUrl, resourceResolver);
			courseUrl = externalizerService.getFormattedUrl(courseUrl, resourceResolver);
	}

	/**
	 * getTitleText()
	 * 
	 * @return Title Text
	 */
	public String getTitleText() {
		return titleText;
	}

	/**
	 * getDescription()
	 * 
	 * @return Description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * getSubText()
	 * 
	 * @return Sub Text
	 */
	public String getSubText() {
		return subText;
	}

	/**
	 * getAddress()
	 * 
	 * @return Address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * getEmailLabel()
	 * 
	 * @return Email Link Label
	 */
	public String getEmailLabel() {
		return emailLabel;
	}

	/**
	 * getEmailUrl()
	 * 
	 * @return Email Link Url
	 */
	public String getEmailUrl() {
		return emailUrl;
	}

	/**
	 * getCourseLabel()
	 * 
	 * @return Course Link Label
	 */
	public String getCourseLabel() {
		return courseLabel;
	}

	/**
	 * getCourseUrl()
	 * 
	 * @return Course Link Url
	 */
	public String getCourseUrl() {
		return courseUrl;
	}

}
