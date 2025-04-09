package com.bdb.aem.core.models;

import java.util.List;

import com.bdb.aem.core.bean.ContactUsSupportCategoriesBean;

/**
 * Class ContactUsSupportModel
 * 
 * @author phanindra
 *
 */
public interface ContactUsSupportModel {

	/**Contact Us Support Section Title Text
	 * 
	 * @return Section Title Text
	 */
	String getSecTitle();
	/**Contact Us Support Sub Title Text 
	 * 
	 * @return Sub-Title Text
	 * */
	String getSubTitle();
	/**Contact Us Support Description 
	 * 
	 * @return Section Description
	 * */
	String getSecDesc();
	/**Contact Us Support List of Fields 
	 * 
	 * @return Multifield of list of fields
	 * */
	List<ContactUsSupportCategoriesBean> getSupportFields();
}
