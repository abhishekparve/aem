package com.bdb.aem.core.models;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.ExcludeUtil;
import com.google.gson.annotations.SerializedName;

/**
 * The Class IcmCellTypeListModel.
 */
@Model(	adaptables = {SlingHttpServletRequest.class,Resource.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class IcmCellTypeListModel {
	
	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(IcmCellTypeListModel.class);
	
	/** The cellLabel. */
	@Inject
	@SerializedName("cellLabel")
    private String cellLabel;
	
	/** The cell. */
	@Inject
	@SerializedName("cell")
    private String cell;
	
	/** The parentCell. */
	@Inject
	@SerializedName("parentCell")
    private String parentCell;
	
	/** The parentCell. */
	@Inject
	@SerializedName("hideActionCta")
    private String hideActionCta;
	
	/**
	 * 
	 * @return cellLabel
	 */
	public String getCellLabel() {
		return cellLabel;
	}

	/**
	 * 
	 * @return cell
	 */
	public String getCell() {
		return cell;
	}
	
	/**
	 * 
	 * @return parentCell
	 */
	public String getParentCell() {
		return parentCell;
	}
	
	/**
	 * @return the hideActionCta
	 */
	public String getHideActionCta() {
		return (StringUtils.isNotEmpty(hideActionCta) ? hideActionCta : "false");
	}

}
