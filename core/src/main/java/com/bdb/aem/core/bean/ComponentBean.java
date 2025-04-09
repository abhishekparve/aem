package com.bdb.aem.core.bean;

import org.apache.commons.lang.StringUtils;

/**
 * The Class ComponentBean.
 */
public class ComponentBean {

	/** The Constant N_A. */
	protected static final String N_A = "N/A";
	
	/** The size. */
	private String size = N_A;
	
	/** The description. */
	private String description = N_A;
	
	/** The component material number. */
	private String componentMaterialNumber = N_A;
	
	/** The tds clone name. */
	private String tdsCloneName = N_A;
	
	/** The iso type. */
	private String isoType = N_A;
	
	/** The entrezGeneId. */
	private String entrezGeneID = N_A;

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public String getSize() {
		if(StringUtils.isEmpty(size.trim()))
			return N_A;
		return size;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		if(StringUtils.isEmpty(description.trim()))
			return N_A;
		return description;
	}

	/**
	 * Gets the component material number.
	 *
	 * @return the component material number
	 */
	public String getComponentMaterialNumber() {
		if(StringUtils.isEmpty(componentMaterialNumber.trim()))
			return N_A;
		return componentMaterialNumber;
	}

	/**
	 * Gets the tds clone name.
	 *
	 * @return the tds clone name
	 */
	public String getTdsCloneName() {
		if(StringUtils.isEmpty(tdsCloneName.trim()))
			return N_A;
		return tdsCloneName;
	}

	/**
	 * Gets the iso type.
	 *
	 * @return the iso type
	 */
	public String getIsoType() {
		if(StringUtils.isEmpty(isoType.trim()))
			return N_A;
		return isoType;
	}
	
	/**
	 * Gets the entrezGeneId.
	 *
	 * @return the entrezGeneId
	 */
	public String getEntrezGeneID() {
		if(StringUtils.isEmpty(entrezGeneID.trim()))
			return N_A;
		return entrezGeneID;
	}

	
}
