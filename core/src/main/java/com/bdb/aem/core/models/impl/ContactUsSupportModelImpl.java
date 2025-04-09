package com.bdb.aem.core.models.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bdb.aem.core.bean.ContactUsSupportCategoriesBean;
import com.bdb.aem.core.models.ContactUsSupportModel;

/**
 * The Class ContactUsSupportModel.
 * @author phanindra
 */
@Model(
        adaptables = Resource.class,
        adapters = ContactUsSupportModel.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContactUsSupportModelImpl implements ContactUsSupportModel {
	
	/**Logger for the class*/
	Logger logger = LoggerFactory.getLogger(ContactUsSupportModelImpl.class);
	
	/** Section Title */
	@Inject
	private String secTitle;
	
	/** Section Sub Title */
	@Inject
	private String subTitle;
	
	/** Section Description */
	@Inject
	private String secDesc;
	
	/** supportCategories multi-field injection as List of Resource */
	@Inject
	private List<Resource> supportCategories;

	/** List holding the multifield data */
	private List<ContactUsSupportCategoriesBean> supportFields = new ArrayList<ContactUsSupportCategoriesBean>();
	
	@PostConstruct
	protected void init() throws LoginException 
	{
		logger.debug("ContactUsSupportModelImpl Initiated {}", supportCategories);
		if(null!=supportCategories && !supportCategories.isEmpty()) {
			for(Resource res:supportCategories) {
				ContactUsSupportCategoriesBean contactUsCatBean = res.adaptTo(ContactUsSupportCategoriesBean.class);
				supportFields.add(contactUsCatBean);
			}
		}
	}

    /**
     * getSecTitle()
     * 
     * @return Section Title
     */
	public String getSecTitle(){
		return secTitle;
	}
	 /**
     * getSecTitle()
     * 
     * @return Section Sub Title
     */
	public String getSubTitle() {
		return subTitle;
	}
	 /**
     * getSecTitle()
     * 
     * @return Section Description
     */
	public String getSecDesc() {
		return secDesc;
	}
	 /**
     * getSecTitle()
     * 
     * @return Supported fields multifield as list
     */
	public List<ContactUsSupportCategoriesBean> getSupportFields() {
		return new ArrayList<>(supportFields);
	}
}
