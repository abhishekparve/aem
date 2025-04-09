package com.bdb.aem.core.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.annotations.SerializedName;

/**
 * The Class ContactUsSupportCategoriesBean.
 * 
 * @author phanindra
 * @return Multi-field data as a bean.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContactUsSupportCategoriesBean {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(ContactUsSupportCategoriesBean.class);

	/** Category Title */
	@Inject
	@Named("catTitle")
	@SerializedName("categoryTitle")
	private String categoryTitle;

	/** Multi-Field of Fields */
	@Inject
	private List<Resource> fieldMultifield;

	/** Holds the multifield data as list */
	@SerializedName("fields")
	private List<ContactUsSupportBean> fields = new ArrayList<>();

	@PostConstruct
	protected void init() throws LoginException {
		logger.debug("ContactUsSupportCategoriesBean Initiated {}", fieldMultifield);
		if (null != fieldMultifield && !fieldMultifield.isEmpty()) {
			for (Resource res : fieldMultifield) {
				ContactUsSupportBean cusBean = res.adaptTo(ContactUsSupportBean.class);
				fields.add(cusBean);
			}
		}
	}

	/**
	 * getFields()
	 * 
	 * @return fields
	 */
	public List<ContactUsSupportBean> getFields() {
		return new ArrayList<>(fields);
	}

	/**
	 * getCategoryTitle()
	 * 
	 * @return Category Title
	 */
	public String getCategoryTitle() {
		return categoryTitle;
	}

}
