package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.bean.AccordionListBean;

/**
 * The Class AccordionTableModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AccordionTableModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(AccordionTableModel.class);

	/** The section title. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String sectionTitle;

	/** The title. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String title;

	/** The sub title. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String subTitle;
	
	/** The background color. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String backgroundColor;

	/** The view table label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String viewTableLabel;
	
	/** The hide table label. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	public String hideTableLabel;
	
	/** The accordions. */
	@Inject
	@Via("resource")
	public List<AccordionListModel> accordions;
	
	/** The request. */
	@Inject
	SlingHttpServletRequest request;
	
	/** The accordion list. */
	private List<AccordionListBean> accordionList;

	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("AccordionTableModel - init() - start");
			if(null != accordions) {
				accordionList = new ArrayList<>();
				for(AccordionListModel accordionItem :accordions) {
					AccordionListBean accordionListBean =  new AccordionListBean();
					accordionListBean.setSectionHeading(accordionItem.getSectionHeading());					
					accordionListBean.setSectionDescription(accordionItem.getSectionDescription());
					accordionList.add(accordionListBean);
				}
			}
	}
	
	/**
	 * Gets the accordion list.
	 *
	 * @return the accordion list
	 */
	public List<AccordionListBean> getAccordionList() {
		if (null != accordionList) {
			return new ArrayList<>(accordionList);
		} else {
			return Collections.emptyList();
		}
	}
	

	/**
	 * Gets the section title.
	 *
	 * @return the section title
	 */
	public String getSectionTitle() {
		return sectionTitle;
	}
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Gets the sub title.
	 *
	 * @return the sub title
	 */
	public String getSubTitle() {
		return subTitle;
	}
	
	/**
	 * Gets the background color.
	 *
	 * @return the background color
	 */
	public String getBackgroundColor() {
		return backgroundColor;
	}
	
	/**
	 * Gets the view table label.
	 *
	 * @return the view table label
	 */
	public String getViewTableLabel() {
		return viewTableLabel;
	}
	
	/**
	 * Gets the hide table label.
	 *
	 * @return the hide table label
	 */
	public String getHideTableLabel() {
		return hideTableLabel;
	}
}
